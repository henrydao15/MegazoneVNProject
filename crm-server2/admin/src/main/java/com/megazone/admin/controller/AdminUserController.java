package com.megazone.admin.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.megazone.admin.common.AdminCodeEnum;
import com.megazone.admin.common.log.AdminUserLog;
import com.megazone.admin.entity.BO.*;
import com.megazone.admin.entity.PO.AdminConfig;
import com.megazone.admin.entity.PO.AdminUser;
import com.megazone.admin.entity.PO.AdminUserConfig;
import com.megazone.admin.entity.VO.AdminSuperUserVo;
import com.megazone.admin.entity.VO.AdminUserVO;
import com.megazone.admin.entity.VO.HrmSimpleUserVO;
import com.megazone.admin.service.*;
import com.megazone.core.common.*;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.UserInfo;
import com.megazone.core.exception.NoLoginException;
import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.core.feign.email.EmailService;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.servlet.upload.UploadEntity;
import com.megazone.core.utils.UserCacheUtil;
import com.megazone.core.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/adminUser")
@Api(tags = "Employee management related interface")
@Slf4j
@SysLog(subModel = SubModelType.ADMIN_STAFF_MANAGEMENT, logClass = AdminUserLog.class)
public class AdminUserController {

	@Autowired
	private IAdminUserService adminUserService;

	@Autowired
	private IAdminUserConfigService adminUserConfigService;

	@Autowired
	private IAdminFileService adminFileService;
	@Autowired
	private IAdminDeptService deptService;

	@RequestMapping("/findByUsername")
	@ApiOperation(value = "Query user by name", httpMethod = "POST")
	public Result<List<Map<String, Object>>> findByUsername(String username) {
		List<Map<String, Object>> userInfoList = adminUserService.findByUsername(username);
		return Result.ok(userInfoList);
	}

	@ApiOperation("Query employee list through conditional paging")
	@PostMapping("/queryUserList")
	public Result<BasePage<AdminUserVO>> queryUserList(@RequestBody AdminUserBO adminUserBO) {
		return R.ok(adminUserService.queryUserList(adminUserBO));
	}

	@ApiOperation("Query employee status number")
	@PostMapping("/countNumOfUser")
	public Result<JsonNode> countUserByLabel() {
		return R.ok(adminUserService.countUserByLabel().node);
	}

	@ApiExplain("Query employee list through conditional paging")
	@PostMapping("/queryAllUserList")
	public Result<List<Long>> queryAllUserList(@RequestParam(value = "type", required = false) Integer type) {
		LambdaQueryWrapper<AdminUser> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.select(AdminUser::getUserId);
		/* type=2 means do not query disabled employees */
		if (Objects.equals(2, type)) {
			queryWrapper.ne(AdminUser::getStatus, 0);
		}
		return R.ok(adminUserService.listObjs(queryWrapper, obj -> Long.parseLong(obj.toString())));
	}

	@ApiExplain("Query employee list through conditional paging")
	@PostMapping("/queryAllUserInfoList")
	public Result<List<UserInfo>> queryAllUserInfoList() {
		List<UserInfo> userInfoList = adminUserService.queryAllUserInfoList();
		return R.ok(userInfoList);
	}

	@PostMapping("/setUser")
	@ApiOperation("Modify user")
	public Result setUser(@RequestBody AdminUserVO adminUserVO) {
		adminUserService.setUser(adminUserVO);
		return R.ok();
	}

	@PostMapping("/setUserDept")
	@ApiOperation("Batch modify user department")
	public Result setUserDept(@RequestBody AdminUserBO adminUserBO) {
		adminUserService.setUserDept(adminUserBO);
		return R.ok();
	}

	@PostMapping("/addUser")
	@ApiOperation("Add User")
	@SysLogHandler(behavior = BehaviorEnum.SAVE, object = "#adminUserVO.realname", detail = "'Added employee:'+#adminUserVO.realname")
	public Result addUser(@RequestBody AdminUserVO adminUserVO) {
		adminUserService.addUser(adminUserVO);
		return R.ok();
	}

	@PostMapping("/usernameEdit")
	@ApiOperation("Reset login account")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result<Integer> usernameEdit(@RequestParam("id") Integer id, @RequestParam("username") String username, @RequestParam("password") String password) {
		Integer integer = adminUserService.usernameEdit(id, username, password);
		return R.ok(integer);
	}

	@PostMapping("/excelImport")
	@ApiOperation("Excel import employees")
	@SysLogHandler(behavior = BehaviorEnum.EXCEL_IMPORT, object = "excel import employee", detail = "excel import employee")
	public Result<JsonNode> excelImport(@RequestParam("file") MultipartFile file) {
		JSONObject object = adminUserService.excelImport(file);
		return R.ok(object.node);
	}

	@PostMapping("/downloadExcel")
	@ApiOperation("Download import template")
	public void downloadExcel(HttpServletResponse response) throws IOException {
		adminUserService.downloadExcel(response);
	}

	@PostMapping("/downExcel")
	@ApiOperation("excel download error data")
	public void downExcel(@RequestParam("token") String token, HttpServletResponse response) {
		String path = FileUtil.getTmpDirPath() + "/" + token;
		if (FileUtil.exist(path)) {
			File file = FileUtil.file(path);
			final String fileName = file.getName();
			final String contentType = ObjectUtil.defaultIfNull(FileUtil.getMimeType(fileName), "application/octet-stream");
			BufferedInputStream in = null;
			try {
				in = FileUtil.getInputStream(file);
				ServletUtil.write(response, in, contentType, "import_error.xls");
			} finally {
				IoUtil.close(in);
			}
			FileUtil.del(path);
		}
	}

	@PostMapping("/hrmAddUser")
	@ApiOperation("Add employee from HR")
	public Result hrmAddUser(@RequestBody HrmAddUserBO hrmAddUserBO) {
		adminUserService.hrmAddUser(hrmAddUserBO);
		return R.ok();
	}

	@PostMapping("/setUserStatus")
	@ApiOperation("disable enable")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result setUserStatus(@RequestBody AdminUserStatusBO adminUserStatusBO) {
		adminUserService.setUserStatus(adminUserStatusBO);
		return R.ok();
	}

	@PostMapping("/activateUser")
	@ApiOperation("Activate account")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result activateUser(@RequestBody AdminUserStatusBO adminUserStatusBO) {
		adminUserService.activateUser(adminUserStatusBO);
		return R.ok();
	}

	@PostMapping("/resetPassword")
	@ApiOperation("Reset password")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result resetPassword(@RequestBody AdminUserStatusBO adminUserStatusBO) {
		adminUserService.resetPassword(adminUserStatusBO);
		return R.ok();
	}

	@PostMapping("/updateImg")
	@ApiOperation("Modify Avatar")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE, object = "Modify Avatar", detail = "Modify Avatar")
	public Result updateImg(@RequestParam("file") MultipartFile file) throws IOException {
		UploadEntity img = adminFileService.upload(file, null, "img", "0");
		AdminUser byId = adminUserService.getById(UserUtil.getUserId());
		byId.setImg(img.getUrl());
		adminUserService.updateById(byId);
		return R.ok();
	}

	@PostMapping("/updatePassword")
	@ApiOperation("Modify login password")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE, object = "Modify login password", detail = "Modify login password")
	public Result updatePassword(@RequestParam("oldPwd") String oldPass, @RequestParam("newPwd") String newPass) {
		AdminUser adminUser = adminUserService.getById(UserUtil.getUserId());
		if (!UserUtil.verify(adminUser.getUsername() + oldPass, adminUser.getSalt(), adminUser.getPassword())) {
			return R.error(AdminCodeEnum.ADMIN_PASSWORD_ERROR);
		}
		adminUser.setPassword(newPass);
		return updateUser(adminUser);
	}

	@PostMapping("/updateUser")
	@ApiOperation("Modify user information")
	public Result updateUser(@RequestBody AdminUser adminUser) {
		adminUserService.updateUser(adminUser);
		return R.ok();
	}

	@PostMapping("/queryLoginUser")
	@ApiOperation("Query currently logged in user")
	public Result<AdminUserVO> queryLoginUser(HttpServletRequest request, HttpServletResponse response) {
		String name = "readNotice";
		AdminUser user = adminUserService.getById(UserUtil.getUserId());
		if (user == null) {
			throw new NoLoginException();
		}
		AdminSuperUserVo adminUser = BeanUtil.copyProperties(user, AdminSuperUserVo.class);
		adminUser.setIsAdmin(UserUtil.isAdmin());
		AdminUserConfig userConfig = adminUserConfigService.queryUserConfigByName(name);
		adminUser.setIsReadNotice(userConfig != null ? userConfig.getStatus() : 0);
		adminUser.setPassword(null);
		String deptName = deptService.getNameByDeptId(adminUser.getDeptId());
		adminUser.setDeptName(deptName);
		adminUser.setParentName(UserCacheUtil.getUserName(adminUser.getParentId()));
		AdminConfig config = ApplicationContextHolder.getBean(IAdminConfigService.class).queryConfigByName("admin");
		if (config != null && config.getStatus() == 1) {
			Integer data = ApplicationContextHolder.getBean(EmailService.class).getEmailId(adminUser.getUserId()).getData();
			adminUser.setEmailId(data);
		}
		AdminUserConfig userConfigByName = adminUserConfigService.queryUserConfigByName("InitUserConfig");
		if (userConfigByName != null) {
			adminUser.setServerUserInfo(JSON.parseObject(userConfigByName.getValue()));
		}
		return R.ok(adminUser);
	}

	@RequestMapping("/queryUserRoleIds")
	@ApiExplain("Query user role list")
	public Result<List<Integer>> queryUserRoleIds(@RequestParam("userId") @NotNull Long userId) {
		return R.ok(adminUserService.queryUserRoleIds(userId));
	}

	@RequestMapping("/queryListName")
	@ApiExplain("Query address book")
	public Result queryListName(@RequestBody UserBookBO userBookBO) {
		return R.ok(adminUserService.queryListName(userBookBO));
	}

	@RequestMapping("/attention")
	@ApiExplain("Switch attention state")
	public Result attention(@RequestParam("userId") Long userId) {
		adminUserService.attention(userId);
		return R.ok();
	}

	@RequestMapping("/queryChildUserId")
	@ApiExplain("Sub-users under user ID")
	public Result<List<Long>> queryChildUserId(@NotNull Long userId) {
		List<Long> longList = adminUserService.queryChildUserId(userId);
		return R.ok(longList);
	}

	@RequestMapping("/queryUserInfo")
	@ApiOperation("Query user information")
	public Result<AdminUser> queryUserInfo(@RequestParam("userId") Long userId) {
		AdminUser byId = adminUserService.getById(userId);
		String nameByDeptId = ApplicationContextHolder.getBean(IAdminDeptService.class).getNameByDeptId(byId.getDeptId());
		byId.setDeptName(nameByDeptId);
		byId.setSalt(null);
		byId.setPassword(null);
		return R.ok(byId);
	}

	@RequestMapping("/queryInfoByUserId")
	@ApiExplain("Get user by user ID")
	public Result<UserInfo> queryInfoByUserId(@NotNull Long userId) {
		AdminUser byId = adminUserService.getById(userId);
		UserInfo userInfo = null;
		if (byId != null) {
			userInfo = BeanUtil.copyProperties(byId, UserInfo.class);
			if (byId.getDeptId() != null) {
				String nameByDeptId = UserCacheUtil.getDeptName(byId.getDeptId());
				userInfo.setDeptName(nameByDeptId);
			}
			userInfo.setRoles(adminUserService.queryUserRoleIds(userInfo.getUserId()));
		}
		return R.ok(userInfo);
	}

	@PostMapping("/queryNormalUserByIds")
	@ApiExplain("Get normal user based on user ID")
	public Result<List<Long>> queryNormalUserByIds(@RequestBody List<Long> ids) {
		return R.ok(adminUserService.queryNormalUserByIds(ids));
	}


	@PostMapping("/queryUserById")
	@ApiExplain("Get user by user ID")
	public Result<SimpleUser> queryUserById(@RequestParam("userId") Long userId) {
		AdminUser adminUser = adminUserService.getById(userId);
		if (adminUser != null) {
			adminUser.setDeptName(deptService.getNameByDeptId(adminUser.getDeptId()));
		}
		return R.ok(BeanUtil.copyProperties(adminUser, SimpleUser.class));
	}

	@PostMapping("/queryUserByDeptIds")
	@ApiExplain("Get user ids based on department ID")
	public Result<List<Long>> queryUserByDeptIds(@RequestBody List<Integer> ids) {
		List<Long> userIds = adminUserService.queryUserByDeptIds(ids);
		return R.ok(userIds);
	}

	@PostMapping("/readNotice")
	@ApiOperation("Set the changelog to read")
	public Result readNotice() {
		Long userId = UserUtil.getUserId();
		String name = "readNotice";
		Integer count = adminUserConfigService.lambdaQuery().eq(AdminUserConfig::getUserId, userId).eq(AdminUserConfig::getName, name).count();
		if (count > 1) {
			adminUserConfigService.lambdaUpdate().set(AdminUserConfig::getStatus, 1).eq(AdminUserConfig::getUserId, userId).eq(AdminUserConfig::getName, name).update();
		} else {
			AdminUserConfig adminUserConfig = new AdminUserConfig();
			adminUserConfig.setValue("");
			adminUserConfig.setName(name);
			adminUserConfig.setUserId(userId);
			adminUserConfig.setStatus(1);
			adminUserConfig.setDescription("Upgrade log reading status");
			adminUserConfigService.save(adminUserConfig);
		}
		return R.ok();
	}


	@PostMapping("/queryAuthUserList")
	@ApiOperation("Query permissions for users")
	public Result<List<SimpleUser>> queryAuthUserList() {
		List<SimpleUser> userList = new ArrayList<>();
		if (UserUtil.isAdmin()) {
			userList.addAll(adminUserService.list().stream().map(user -> BeanUtil.copyProperties(user, SimpleUser.class)).collect(Collectors.toList()));
		} else {
			List<Long> childUserId = adminUserService.queryChildUserId(UserUtil.getUserId());
			userList.addAll(adminUserService.queryUserByIds(childUserId));
		}
		return R.ok(userList);
	}

	@PostMapping("/queryDeptUserList/{deptId}")
	@ApiOperation("Query department user list (form use)")
	public Result<DeptUserListVO> queryDeptUserList(@PathVariable Integer deptId) {
		DeptUserListVO deptUserListVO = adminUserService.queryDeptUserList(deptId, true);
		return Result.ok(deptUserListVO);
	}

	@PostMapping("/queryDeptUserByExamine/{deptId}")
	@ApiOperation("Query department user list (approval use)")
	public Result<DeptUserListVO> queryDeptUserByExamine(@PathVariable Integer deptId) {
		DeptUserListVO deptUserListVO = adminUserService.queryDeptUserList(deptId, false);
		return Result.ok(deptUserListVO);
	}

	@PostMapping("/queryDeptUserListByHrm")
	@ApiOperation("Query department user list (hrm adds employees to use)")
	public Result<Set<HrmSimpleUserVO>> queryDeptUserListByHrm(@RequestBody DeptUserListByHrmBO deptUserListByHrmBO) {
		Set<HrmSimpleUserVO> userList = adminUserService.queryDeptUserListByHrm(deptUserListByHrmBO);
		return Result.ok(userList);
	}

	@PostMapping("/queryUserIdByRealName")
	@ApiOperation("Query user id based on real name")
	public Result<List<Long>> queryUserIdByRealName(@RequestParam("realNames") List<String> realNames) {
		List<Long> userIdList = adminUserService.queryUserIdByRealName(realNames);
		return Result.ok(userIdList);
	}

	@PostMapping("/queryLoginUserInfo")
	@ApiExplain("Simulated query login user information")
	public Result<UserInfo> queryLoginUserInfo(@RequestParam("userId") Long userId) {
		UserInfo userInfo = adminUserService.queryLoginUserInfo(userId);
		return Result.ok(userInfo);
	}

	@PostMapping("/querySystemStatus")
	@ApiOperation("Query the current system initial state")
	@ParamAspect
	public Result<Integer> querySystemStatus() {
		Integer status = adminUserService.querySystemStatus();
		return R.ok(status);
	}

	@PostMapping("/initUser")
	@ApiOperation("Initialize system user")
	@ParamAspect
	public Result initUser(@Validated @RequestBody SystemUserBO systemUserBO) {
		adminUserService.initUser(systemUserBO);
		return R.ok();
	}


	@PostMapping("/queryUserIdByUserName")
	@ApiExplain("Query user id by username")
	public Result<Long> queryUserIdByUserName(@RequestParam("userName") String userName) {
		Long userId = adminUserService.lambdaQuery().select(AdminUser::getUserId).eq(AdminUser::getUsername, userName).oneOpt().map(AdminUser::getUserId).orElse(0L);
		return Result.ok(userId);
	}
}
