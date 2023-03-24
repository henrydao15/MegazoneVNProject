package com.megazone.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.megazone.admin.common.AdminCodeEnum;
import com.megazone.admin.common.AdminConst;
import com.megazone.admin.entity.BO.*;
import com.megazone.admin.entity.PO.*;
import com.megazone.admin.entity.VO.AdminUserVO;
import com.megazone.admin.entity.VO.HrmSimpleUserVO;
import com.megazone.admin.entity.VO.UserBookVO;
import com.megazone.admin.mapper.AdminUserMapper;
import com.megazone.admin.service.*;
import com.megazone.core.common.*;
import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.UserInfo;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.core.feign.crm.service.CrmService;
import com.megazone.core.feign.hrm.entity.HrmEmployee;
import com.megazone.core.feign.hrm.service.HrmService;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-04-27
 */
@Service
@Slf4j
public class AdminUserServiceImpl extends BaseServiceImpl<AdminUserMapper, AdminUser> implements IAdminUserService {

	@Autowired
	private IAdminRoleService adminRoleService;
	@Autowired
	private IAdminDeptService adminDeptService;
	@Autowired
	private IAdminUserConfigService adminUserConfigService;
	@Autowired
	private IAdminUserRoleService adminUserRoleService;
	@Autowired
	private IAdminAttentionService adminAttentionService;

	@Autowired
	private CrmService crmService;
	@Autowired
	private HrmService hrmService;
	//@Value("${seata.config.nacos.serverAddr}")
	private String serverAddr;
	@Autowired
	private DataSourceProperties dataSourceProperties;

	@Override
	public List<Map<String, Object>> findByUsername(String username) {
		List<Map<String, Object>> userInfoList = getBaseMapper().findByUsername(username);
		userInfoList.forEach(userInfo -> {
			userInfo.put("superUserId", UserUtil.getSuperUser());
			userInfo.put("superRoleId", UserUtil.getSuperRole());
		});
		return userInfoList;
	}

	/**
	 * @param adminUserBO
	 * @return ids
	 */
	@Override
	public BasePage<AdminUserVO> queryUserList(AdminUserBO adminUserBO) {
		if (adminUserBO == null) {
			BasePage<AdminUserVO> userBasePage = new BasePage<>();
			List<AdminUserVO> adminUserVOList = this.lambdaQuery().ne(AdminUser::getUserId, UserUtil.getSuperUser())
					.list().stream().map(adminUser -> this.convertAdminUserToVo(adminUser, 2)).collect(Collectors.toList());

			AdminUser adminSuperUser = this.getById(UserUtil.getSuperUser());
			adminUserVOList.add(0, this.convertAdminUserToVo(adminSuperUser, 0));
			userBasePage.setRecords(adminUserVOList);
			return userBasePage;
		}
		Long deptOwnerUserId = null;
		adminUserBO.setUserId(UserUtil.getSuperUser());
		if (adminUserBO.getDeptId() != null) {
			List<Integer> list;
			if (Objects.equals(adminUserBO.getIsNeedChild(), 1)) {
				list = adminDeptService.queryChildDept(adminUserBO.getDeptId());
			} else {
				list = new ArrayList<>();
			}
			list.add(adminUserBO.getDeptId());
			adminUserBO.setDeptIdList(list);
			deptOwnerUserId = adminDeptService.getById(adminUserBO.getDeptId()).getOwnerUserId();
			adminUserBO.setDeptOwnerUserId(deptOwnerUserId);
		}
		BasePage<AdminUserVO> basePage = getBaseMapper().queryUserList(adminUserBO.parse(), adminUserBO);

		if (deptOwnerUserId != null) {
			if (adminUserBO.getPage() == 1) {
				for (AdminUserVO adminUserVO : basePage.getRecords()) {
					if (Objects.equals(adminUserVO.getUserId(), deptOwnerUserId)) {
						adminUserVO.setUserIdentity(1);
					}
				}
			}
		} else {

			if (adminUserBO.getPage() == 1) {
				for (AdminUserVO adminUserVO : basePage.getRecords()) {
					if (Objects.equals(adminUserVO.getUserId(), UserUtil.getSuperUser())) {
						adminUserVO.setUserIdentity(0);
					}
				}
			}
		}
		LambdaQueryWrapper<AdminUserHisTable> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.select(AdminUserHisTable::getUserId);
		queryWrapper.eq(AdminUserHisTable::getHisTable, 1);
		List<Long> longs = ApplicationContextHolder.getBean(IAdminUserHisTableService.class).listObjs(queryWrapper, user -> Long.valueOf(user.toString()));
		basePage.getRecords().forEach(adminUserVO -> {
			if (longs.contains(adminUserVO.getUserId())) {
				adminUserVO.setHisTable(1);
			}
			List<AdminRole> adminRoleList = adminRoleService.queryRoleListByUserId(adminUserVO.getUserId());
			adminUserVO.setRoleId(adminRoleList.stream().map(adminRole -> adminRole.getRoleId().toString()).collect(Collectors.joining(",")));
			adminUserVO.setRoleName(adminRoleList.stream().map(AdminRole::getRoleName).collect(Collectors.joining(",")));
		});
		return basePage;
	}

	private AdminUserVO convertAdminUserToVo(AdminUser adminUser, Integer userIdentity) {
		AdminUserVO userVO = BeanUtil.copyProperties(adminUser, AdminUserVO.class);
		userVO.setUserIdentity(userIdentity);
		userVO.setDeptName(UserCacheUtil.getDeptName(userVO.getDeptId()));
		return userVO;
	}

	@Override
	public JSONObject countUserByLabel() {
		JSONObject jsonObject = new JSONObject();
		jsonObject
				.fluentPut("allUserCount", getBaseMapper().countUserByLabel(0, null))
				.fluentPut("addNewlyCount", getBaseMapper().countUserByLabel(1, null))
				.fluentPut("activateCount", getBaseMapper().countUserByLabel(null, 1))
				.fluentPut("inactiveCount", getBaseMapper().countUserByLabel(2, null))
				.fluentPut("disableCount", getBaseMapper().countUserByLabel(3, null));
		return jsonObject;
	}

	/**
	 * @param userId ID 0
	 * @return data
	 */
	@Override
	public List<Long> queryChildUserId(Long userId) {
		return RecursionUtil.getChildList(list(), "parentId", userId, "userId", "userId");
	}

	/**
	 * @param adminUserVO data
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void setUser(AdminUserVO adminUserVO) {
		if (adminUserVO.getParentId() == null) {
			adminUserVO.setParentId(0L);
		}
		List<Long> userList = queryChildUserId(adminUserVO.getUserId());
		if (userList.contains(adminUserVO.getParentId())) {
			throw new CrmException(AdminCodeEnum.ADMIN_PARENT_USER_ERROR);
		}
		if (adminUserVO.getUserId().equals(adminUserVO.getParentId())) {
			throw new CrmException(AdminCodeEnum.ADMIN_PARENT_USER_ERROR1);
		}

		Integer nameCount = query().eq("realname", adminUserVO.getRealname()).ne("user_id", adminUserVO.getUserId()).count();
		if (nameCount > 0) {
			throw new CrmException(AdminCodeEnum.ADMIN_USER_REAL_NAME_EXIST_ERROR);
		}

		adminUserVO.setUsername(null);

		adminUserVO.setPassword(null);
		AdminUser adminUser = BeanUtil.copyProperties(adminUserVO, AdminUser.class);
		adminUserRoleService.saveByUserId(adminUserVO.getUserId(), true, StrUtil.splitTrim(adminUserVO.getRoleId(), Const.SEPARATOR));
		updateById(adminUser);
		crmService.batchUpdateEsData(adminUser.getUserId().toString(), adminUser.getRealname());
	}

	@Override
	public void setUserDept(AdminUserBO adminUserBO) {
		Integer deptId = adminUserBO.getDeptId();
		List<Long> userIdList = adminUserBO.getUserIdList();
		if (CollUtil.isEmpty(userIdList) || deptId == null) {
			return;
		}
		AdminDept dept = adminDeptService.getById(deptId);
		if (dept == null) {
			throw new CrmException(AdminCodeEnum.ADMIN_DEPT_NOT_EXIST_ERROR);
		}
		this.lambdaUpdate().set(AdminUser::getDeptId, deptId).in(AdminUser::getUserId, userIdList).update();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addUser(AdminUserVO adminUser) {
		if (adminUser.getParentId() == null) {
			adminUser.setParentId(0L);
		}
		if (!ReUtil.isMatch(AdminConst.DEFAULT_PASSWORD_INTENSITY, adminUser.getPassword())) {
			throw new CrmException(AdminCodeEnum.ADMIN_PASSWORD_INTENSITY_ERROR);
		}
		Integer count = query().eq("username", adminUser.getUsername()).count();
		if (count > 0) {
			throw new CrmException(AdminCodeEnum.ADMIN_USER_EXIST_ERROR);
		}
		Integer nameCount = query().eq("realname", adminUser.getRealname()).count();
		if (nameCount > 0) {
			throw new CrmException(AdminCodeEnum.ADMIN_USER_REAL_NAME_EXIST_ERROR);
		}
		String salt = IdUtil.fastSimpleUUID();
		AdminUser adminUserPO = BeanUtil.copyProperties(adminUser, AdminUser.class);
		adminUserPO.setCreateTime(new Date());
		adminUserPO.setNum(RandomUtil.randomNumbers(15));
		adminUserPO.setMobile(adminUserPO.getUsername());
		adminUserPO.setSalt(salt);
		adminUserPO.setPassword(UserUtil.sign((adminUser.getUsername().trim() + adminUser.getPassword().trim()), salt));
		save(adminUserPO);
		adminUserConfigService.initUserConfig(adminUserPO.getUserId());
		adminUserRoleService.saveByUserId(adminUserPO.getUserId(), false, StrUtil.splitTrim(adminUser.getRoleId(), Const.SEPARATOR));
	}

	/**
	 * @param adminUser
	 */
	@Override
	public void updateUser(AdminUser adminUser) {
		if (!UserUtil.getUser().getUsername().equals(adminUser.getUsername())) {
			throw new CrmException(AdminCodeEnum.ADMIN_USERNAME_EDIT_ERROR);
		}
		adminUser.setUserId(UserUtil.getUserId());
		boolean b = false;
		if (StrUtil.isNotEmpty(adminUser.getPassword())) {
			if (!ReUtil.isMatch(AdminConst.DEFAULT_PASSWORD_INTENSITY, adminUser.getPassword())) {
				throw new CrmException(AdminCodeEnum.ADMIN_PASSWORD_INTENSITY_ERROR);
			}
			b = true;
			adminUser.setSalt(IdUtil.simpleUUID());
			adminUser.setPassword(UserUtil.sign((adminUser.getUsername().trim() + adminUser.getPassword().trim()), adminUser.getSalt()));
		}
		adminUser.setStatus(null);
		boolean update = updateById(adminUser);
		crmService.batchUpdateEsData(adminUser.getUserId().toString(), adminUser.getRealname());
		if (b && update) {
			UserUtil.userExit(adminUser.getUserId(), null);
		}
	}

	/**
	 * @param id       ID
	 * @param username
	 * @param password
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Integer usernameEdit(Integer id, String username, String password) {
		if (!ReUtil.isMatch(AdminConst.DEFAULT_PASSWORD_INTENSITY, password)) {
			throw new CrmException(AdminCodeEnum.ADMIN_PASSWORD_INTENSITY_ERROR);
		}
		AdminUser adminUser = getById(id);
		if (adminUser == null) {
			throw new CrmException(AdminCodeEnum.ADMIN_USER_NOT_EXIST_ERROR);
		}
		if (adminUser.getUsername().equals(username)) {
			throw new CrmException(AdminCodeEnum.ADMIN_ACCOUNT_ERROR);
		}
		Integer count = lambdaQuery().eq(AdminUser::getUsername, username).count();
		if (count > 0) {
			throw new CrmException(AdminCodeEnum.ADMIN_PHONE_REGISTER_ERROR);
		}
		adminUser.setUsername(username);
		adminUser.setMobile(username);
		adminUser.setPassword(UserUtil.sign(username + password, adminUser.getSalt()));
		UserUtil.userExit(adminUser.getUserId(), null);
		updateById(adminUser);
		return null;
	}

	/**
	 * excel import employee
	 *
	 * @param file file
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject excelImport(MultipartFile file) {
		List<List<Object>> errList = new ArrayList<>();
		String filePath = getFilePath(file);
		AtomicReference<Integer> num = new AtomicReference<>(0);
		ExcelUtil.readBySax(filePath, 0, (int sheetIndex, int rowIndex, List<Object> rowList) -> {
			if (rowIndex > 1) {
				num.getAndSet(num.get() + 1);
				if (StrUtil.isEmptyIfStr(rowList.get(0))) {
					rowList.add(0, "Username cannot be empty");
					errList.add(rowList);
					return;
				}
				if (StrUtil.isEmptyIfStr(rowList.get(1))) {
					rowList.add(0, "Password cannot be empty");
					errList.add(rowList);
					return;
				}
				if (!ReUtil.isMatch("^(?=.*[a-zA-Z])(?=.*\\d).{6,20}$", rowList.get(1).toString())) {
					rowList.add(0, "The password must consist of 6-20 letters and numbers");
					errList.add(rowList);
					return;
				}

				if (StrUtil.isEmptyIfStr(rowList.get(2))) {
					rowList.add(0, "Name cannot be empty");
					errList.add(rowList);
					return;
				} else {
					Integer count = query().eq("realname", rowList.get(2).toString().trim()).count();
					if (count > 0) {
						rowList.add(0, "Duplicate name");
						errList.add(rowList);
						return;
					}
				}
				String username = rowList.get(0).toString().trim();
				Integer count = lambdaQuery().eq(AdminUser::getUsername, username).count();
				if (count > 0) {
					rowList.add(0, "Mobile phone number already exists");
					errList.add(rowList);
					return;
				}
				if (!ReUtil.isMatch("^[1][3,4,5,6,7,8,9][0-9]{9}$", username)) {
					rowList.add(0, "The phone number format is incorrect");
					errList.add(rowList);
					return;
				}
				AdminUser adminUser = new AdminUser();
				if (rowList.size() < 6) {
					rowList.add(0, "Department cannot be empty");
					errList.add(rowList);
					return;
				}
				String deptNames = Optional.ofNullable(rowList.get(5)).orElse("").toString().trim();
				if (!StrUtil.isEmpty(deptNames)) {
					String[] strArr = deptNames.split("/");
					Integer deptId = null;
					for (int i = 0; i < strArr.length; i++) {
						AdminDept dept;
						if (i == 0) {
							dept = adminDeptService.lambdaQuery().select(AdminDept::getDeptId)
									.eq(AdminDept::getName, strArr[0])
									.last("limit 1").one();

						} else {
							dept = adminDeptService.lambdaQuery().select(AdminDept::getDeptId)
									.eq(AdminDept::getName, strArr[i]).eq(AdminDept::getPid, deptId)
									.last("limit 1").one();
						}
						if (dept == null) {
							Integer a = i + 1;
							rowList.add(0, a + "level department does not exist");
							errList.add(rowList);
							return;
						}
						deptId = dept.getDeptId();
					}

					if (deptId == null) {
						rowList.add(0, "Department does not exist");
						errList.add(rowList);
						return;
					}
					adminUser.setDeptId(deptId);

				} else {
					rowList.add(0, "Department cannot be empty");
					errList.add(rowList);
					return;
				}

				String password = rowList.get(1).toString().trim();
				String realname = rowList.get(2).toString().trim();
				String sex = null;
				if (rowList.get(3) != null) {
					sex = Optional.ofNullable(rowList.get(3)).orElse("").toString().trim();
				}

				String email = null;
				if (rowList.get(4) != null) {
					email = Optional.ofNullable(rowList.get(4)).orElse("").toString().trim();
				}
				String post = null;
				if (rowList.size() > 6) {
					post = Optional.ofNullable(rowList.get(6)).orElse("").toString().trim();
				}
				String salt = IdUtil.fastSimpleUUID();
				adminUser.setUsername(username);
				adminUser.setPassword(UserUtil.sign((adminUser.getUsername().trim() + password.trim()), salt));
				adminUser.setSalt(salt);
				adminUser.setNum(RandomUtil.randomNumbers(15));
				adminUser.setCreateTime(new Date());
				adminUser.setRealname(realname);
				adminUser.setMobile(username);
				adminUser.setEmail(email);
				adminUser.setPost(post);
				adminUser.setStatus(0);
				if (StrUtil.isNotEmpty(sex)) {
					if ("female".equals(sex)) {
						adminUser.setSex(2);
					} else {
						adminUser.setSex(1);
					}
				}
				save(adminUser);
				adminUserConfigService.initUserConfig(UserUtil.getUserId());
			} else {
				if (rowIndex == 1) {
					rowList.add(0, "Error message");
				}
				errList.add(rowIndex, rowList);
			}
		});
		FileUtil.del(filePath);
		JSONObject result = new JSONObject().fluentPut("totalSize", num.get()).fluentPut("errSize", 0);
		if (errList.size() > 2) {
			BigExcelWriter writer = null;
			try {
				String token = IdUtil.simpleUUID();
				writer = ExcelUtil.getBigWriter(FileUtil.getTmpDirPath() + "/" + token);
				// Cancel the black border of the data and align the data to the left
				CellStyle cellStyle = writer.getCellStyle();
				cellStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
				cellStyle.setBorderTop(BorderStyle.NONE);
				cellStyle.setBorderBottom(BorderStyle.NONE);
				cellStyle.setBorderLeft(BorderStyle.NONE);
				cellStyle.setBorderRight(BorderStyle.NONE);
				cellStyle.setAlignment(HorizontalAlignment.LEFT);
				Font defaultFont = writer.createFont();
				defaultFont.setFontHeightInPoints((short) 11);
				cellStyle.setFont(defaultFont);
				// Cancel the black border of the data in number format and align the data to the left
				CellStyle cellStyleForNumber = writer.getStyleSet().getCellStyleForNumber();
				cellStyleForNumber.setBorderTop(BorderStyle.NONE);
				cellStyleForNumber.setBorderBottom(BorderStyle.NONE);
				cellStyleForNumber.setBorderLeft(BorderStyle.NONE);
				cellStyleForNumber.setBorderRight(BorderStyle.NONE);
				cellStyleForNumber.setAlignment(HorizontalAlignment.LEFT);
				cellStyleForNumber.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
				cellStyleForNumber.setFont(defaultFont);

				CellStyle textStyle = writer.getWorkbook().createCellStyle();
				DataFormat format = writer.getWorkbook().createDataFormat();
				textStyle.setDataFormat(format.getFormat("@"));

				writer.merge(errList.get(1).size() + 1, errList.get(0).get(0).toString().trim(), true);
				writer.getHeadCellStyle().setAlignment(HorizontalAlignment.LEFT);
				writer.getHeadCellStyle().setWrapText(true);
				Font headFont = writer.createFont();
				headFont.setFontHeightInPoints((short) 11);
				writer.getHeadCellStyle().setFont(headFont);
				writer.getHeadCellStyle().setFillPattern(FillPatternType.NO_FILL);
				writer.getOrCreateRow(0).setHeightInPoints(120);
				writer.setRowHeight(-1, 20);

				//writer.merge(6, "System user import template (*) is required");
				for (int i = 0; i < errList.get(1).size(); i++) {
					writer.getSheet().setDefaultColumnStyle(i, textStyle);
				}
				errList.remove(0);
				writer.write(errList);
				result.fluentPut("errSize", errList.size() - 1).fluentPut("token", token);
			} finally {
				if (writer != null) {
					writer.close();
				}
			}
		}
		return result;
	}

	private String getFilePath(MultipartFile file) {
		String dirPath = FileUtil.getTmpDirPath();
		try {
			InputStream inputStream = file.getInputStream();
			File fromStream = FileUtil.writeFromStream(inputStream, dirPath + "/" + IdUtil.simpleUUID() + file.getOriginalFilename());
			return fromStream.getAbsolutePath();
		} catch (IOException e) {
			throw new CrmException(SystemCodeEnum.SYSTEM_UPLOAD_FILE_ERROR);
		}
	}

	/**
	 * set state
	 *
	 * @param adminUserStatusBO status
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void setUserStatus(AdminUserStatusBO adminUserStatusBO) {
		for (Long id : adminUserStatusBO.getIds()) {
			if (BaseStatusEnum.CLOSE.getStatus().equals(adminUserStatusBO.getStatus())) {
				if (id.equals(UserUtil.getSuperUser())) {
					throw new CrmException(AdminCodeEnum.ADMIN_SUPER_USER_DISABLED_ERROR);
				}
				UserUtil.userExit(id, null);
			} else if (BaseStatusEnum.OPEN.getStatus().equals(adminUserStatusBO.getStatus())) {
				Integer roleCount = adminUserRoleService.lambdaQuery().eq(AdminUserRole::getUserId, id).count();
				if (roleCount == 0) {
					throw new CrmException(AdminCodeEnum.ADMIN_USER_NOT_ROLE_ERROR);
				}
				AdminUser adminUser = getById(id);
				if (adminUser.getDeptId() == null) {
					throw new CrmException(AdminCodeEnum.ADMIN_USER_NOT_DEPT_ERROR);
				}
			}
			lambdaUpdate().set(AdminUser::getStatus, adminUserStatusBO.getStatus()).eq(AdminUser::getUserId, id).update();
		}
	}

	/**
	 * set state
	 *
	 * @param adminUserStatusBO status
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void activateUser(AdminUserStatusBO adminUserStatusBO) {

		for (Long id : adminUserStatusBO.getIds()) {
			if (BaseStatusEnum.CLOSE.getStatus().equals(adminUserStatusBO.getStatus())) {
				if (id.equals(UserUtil.getSuperUser())) {
					throw new CrmException(AdminCodeEnum.ADMIN_SUPER_USER_DISABLED_ERROR);
				}
				UserUtil.userExit(id, null);
			} else if (BaseStatusEnum.OPEN.getStatus().equals(adminUserStatusBO.getStatus())) {
				Integer roleCount = adminUserRoleService.lambdaQuery().eq(AdminUserRole::getUserId, id).count();
				if (roleCount == 0) {
					throw new CrmException(AdminCodeEnum.ADMIN_USER_NOT_ROLE_ERROR);
				}
				AdminUser adminUser = getById(id);
				if (adminUser.getDeptId() == null) {
					throw new CrmException(AdminCodeEnum.ADMIN_USER_NOT_DEPT_ERROR);
				}

			}
			lambdaUpdate().set(AdminUser::getStatus, adminUserStatusBO.getStatus()).eq(AdminUser::getUserId, id).update();
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void resetPassword(AdminUserStatusBO adminUserStatusBO) {
		if (!ReUtil.isMatch(AdminConst.DEFAULT_PASSWORD_INTENSITY, adminUserStatusBO.getPassword())) {
			throw new CrmException(AdminCodeEnum.ADMIN_PASSWORD_INTENSITY_ERROR);
		}
		for (Long id : adminUserStatusBO.getIds()) {
			AdminUser adminUser = getById(id);
			String password = UserUtil.sign(adminUser.getUsername() + adminUserStatusBO.getPassword(), adminUser.getSalt());
			lambdaUpdate().set(AdminUser::getPassword, password).eq(AdminUser::getUserId, id).update();
			UserUtil.userExit(adminUser.getUserId(), null);
		}
	}

	/**
	 * Query role ID based on user ID
	 *
	 * @param userId userId
	 * @return ids
	 */
	@Override
	public List<Integer> queryUserRoleIds(Long userId) {
		QueryWrapper<AdminUserRole> queryWrapper = new QueryWrapper<>();
		queryWrapper.select("role_id").eq("user_id", userId);
		return adminUserRoleService.listObjs(queryWrapper, obj -> Integer.valueOf(obj.toString()));
	}

	/**
	 * Recursively query the subordinate users of the user
	 */
	private List<Long> queryChildUserId(List<AdminUser> userList, Long parentId, int depth) {
		depth--;
		List<Long> arrList = new ArrayList<>();
		if (depth < 0) {
			return arrList;
		}
		for (AdminUser adminUser : userList) {
			if (Objects.equals(parentId, adminUser.getParentId())) {
				arrList.add(adminUser.getUserId());
				arrList.addAll(queryChildUserId(userList, adminUser.getUserId(), depth));
			}
		}
		return arrList;
	}

	/**
	 * Contacts query
	 *
	 * @param userBookBO data
	 * @return
	 */
	@Override
	public BasePage<UserBookVO> queryListName(UserBookBO userBookBO) {
		userBookBO.setUserId(UserUtil.getUserId());
		return getBaseMapper().queryListName(userBookBO.parse(), userBookBO);
	}

	/**
	 * Toggle attention status
	 *
	 * @param userId User ID 0 means all
	 * @return
	 */
	@Override
	public void attention(Long userId) {
		QueryWrapper<AdminAttention> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("be_user_id", userId);
		int count = adminAttentionService.count(queryWrapper);
		if (count > 0) {
			adminAttentionService.remove(queryWrapper);
		} else {
			AdminAttention attention = new AdminAttention();
			attention.setBeUserId(userId);
			attention.setAttentionUserId(UserUtil.getUserId());
			adminAttentionService.save(attention);
		}
	}

	/**
	 * Query user information based on ids
	 *
	 * @param ids list of ids
	 * @return data
	 */
	@Override
	public List<SimpleUser> queryUserByIds(List<Long> ids) {
		if (ids.size() == 0) {
			return new ArrayList<>();
		}
		LambdaQueryWrapper<AdminUser> queryWrapper = new LambdaQueryWrapper<>();
		String idStr = ids.stream().map(String::valueOf).collect(Collectors.joining(Const.SEPARATOR));
		queryWrapper.select(AdminUser::getUserId, AdminUser::getImg, AdminUser::getRealname).in(AdminUser::getUserId, ids)
				.last(" ORDER BY instr('," + idStr + ",',CONCAT(',',user_id,','))");
		List<AdminUser> list = list(queryWrapper);
		return list.stream().map(obj -> BeanUtil.copyProperties(obj, SimpleUser.class)).collect(Collectors.toList());
	}

	@Override
	public List<Long> queryNormalUserByIds(List<Long> ids) {
		List<Long> userIdList = new ArrayList<>();
		if (ids.size() == 0) {
			return userIdList;
		}
		LambdaQueryWrapper<AdminUser> queryWrapper = new LambdaQueryWrapper<>();
		String idStr = ids.stream().map(String::valueOf).collect(Collectors.joining(Const.SEPARATOR));
		queryWrapper.select(AdminUser::getUserId, AdminUser::getImg, AdminUser::getRealname)
				.in(AdminUser::getUserId, ids)
				.eq(AdminUser::getStatus, 1)
				.last(" ORDER BY instr('," + idStr + ",',CONCAT(',',user_id,','))");
		List<AdminUser> list = list(queryWrapper);
		if (CollUtil.isNotEmpty(list)) {
			userIdList = list.stream().map(AdminUser::getUserId).collect(Collectors.toList());
		}
		return userIdList;
	}

	/**
	 * Query user list based on department ids
	 *
	 * @param ids list of ids
	 * @return data
	 */
	@Override
	public List<Long> queryUserByDeptIds(List<Integer> ids) {
		if (ids.size() == 0) {
			return new ArrayList<>();
		}
		LambdaQueryWrapper<AdminUser> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.select(AdminUser::getUserId);
		if (ids.size() > 1) {
			queryWrapper.in(AdminUser::getDeptId, ids);
		} else {
			queryWrapper.eq(AdminUser::getDeptId, ids.get(0));
		}
		return listObjs(queryWrapper, obj -> Long.valueOf(obj.toString()));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void hrmAddUser(HrmAddUserBO hrmAddUserBO) {
		Result<Set<HrmEmployee>> listResult = hrmService.queryEmployeeListByIds(hrmAddUserBO.getEmployeeIds());
		Set<HrmEmployee> employeeList = listResult.getData();
		for (HrmEmployee hrmEmployee : employeeList) {
			AdminUserVO adminUserVO = new AdminUserVO();
			adminUserVO.setRealname(hrmEmployee.getEmployeeName());
			adminUserVO.setUsername(hrmEmployee.getMobile());
			adminUserVO.setSex(hrmEmployee.getSex());
			adminUserVO.setMobile(hrmEmployee.getMobile());
			adminUserVO.setPassword(hrmAddUserBO.getPassword());
			adminUserVO.setEmail(hrmEmployee.getEmail());
			adminUserVO.setDeptId(hrmAddUserBO.getDeptId());
			adminUserVO.setParentId(hrmAddUserBO.getParentId());
			adminUserVO.setRoleId(hrmAddUserBO.getRoleId());
			adminUserVO.setPost(hrmEmployee.getPost());
			addUser(adminUserVO);
		}
	}

	@Override
	public DeptUserListVO queryDeptUserList(Integer deptId, boolean isAllUser) {
		DeptUserListVO deptUserListVO = new DeptUserListVO();
		List<DeptVO> deptList = adminDeptService.queryDeptUserList();
		createTree(deptId, deptList);
		List<HrmSimpleUserVO> userList;
		if (isAllUser) {
			userList = getBaseMapper().querySimpleUserByDeptId(deptId);
		} else {
			userList = getBaseMapper().querySimpleUserByDeptIdAndExamine(deptId);
		}
		List<DeptVO> collect = deptList.stream().filter(dept -> dept.getPid().equals(deptId)).collect(Collectors.toList());
		deptUserListVO.setDeptList(collect);
		deptUserListVO.setUserList(userList);
		return deptUserListVO;
	}

	private List<DeptVO> createTree(int pid, List<DeptVO> deptList) {
		List<DeptVO> treeDept = new ArrayList<>();
		for (DeptVO dept : deptList) {
			if (pid == dept.getPid()) {
				treeDept.add(dept);
				List<DeptVO> children = createTree(dept.getDeptId(), deptList);
				if (CollUtil.isNotEmpty(children)) {
					for (DeptVO child : children) {
						dept.setAllNum(dept.getAllNum() + child.getAllNum());
					}
					dept.setHasChildren(1);
				} else {
					dept.setHasChildren(0);
				}
			}
		}
		return treeDept;
	}

	@Override
	public Set<HrmSimpleUserVO> queryDeptUserListByHrm(DeptUserListByHrmBO deptUserListByHrmBO) {
		Set<HrmSimpleUserVO> userVOSet = new HashSet<>();
		if (CollUtil.isNotEmpty(deptUserListByHrmBO.getDeptIdList())) {
			List<AdminUser> userList = findChildUserList(deptUserListByHrmBO.getDeptIdList());
			List<HrmSimpleUserVO> hrmSimpleUserVOS = TransferUtil.transferList(userList, HrmSimpleUserVO.class);
			userVOSet.addAll(hrmSimpleUserVOS);
		}
		if (CollUtil.isNotEmpty(deptUserListByHrmBO.getUserIdList())) {
			List<AdminUser> userList = query().select("user_id", "realname", "img", "sex", "username as mobile", "post").in("user_id", deptUserListByHrmBO.getUserIdList())
					.ne("status", 0).list();
			List<HrmSimpleUserVO> hrmSimpleUserVOS = TransferUtil.transferList(userList, HrmSimpleUserVO.class);
			userVOSet.addAll(hrmSimpleUserVOS);
		}
		return userVOSet;
	}

	private List<AdminUser> findChildUserList(List<Integer> deptIds) {
		List<AdminUser> empList = new ArrayList<>();
		for (Integer deptId : deptIds) {
			List<AdminUser> list = query().select("user_id", "realname", "img", "sex", "username as mobile", "post").eq("dept_id", deptId).ne("status", 0).list();
			empList.addAll(list);
			List<AdminDept> childList = adminDeptService.lambdaQuery().select(AdminDept::getDeptId).eq(AdminDept::getPid, deptId).list();
			if (CollUtil.isNotEmpty(childList)) {
				List<Integer> childDeptIds = childList.stream().map(AdminDept::getDeptId).collect(Collectors.toList());
				empList.addAll(findChildUserList(childDeptIds));
			}
		}
		return empList;
	}

	@Override
	public List<Long> queryUserIdByRealName(List<String> realNames) {
		if (CollUtil.isEmpty(realNames)) {
			return new ArrayList<>();
		}
		return lambdaQuery().select(AdminUser::getUserId)
				.in(AdminUser::getRealname, realNames)
				.list().stream().map(AdminUser::getUserId).collect(Collectors.toList());
	}

	@Override
	public UserInfo queryLoginUserInfo(Long userId) {
		UserInfo userInfo = getBaseMapper().queryLoginUserInfo(userId);
		userInfo.setSuperUserId(UserUtil.getSuperUser());
		AdminRole role = adminRoleService.lambdaQuery().eq(AdminRole::getRemark, "admin").last("limit 1").one();
		userInfo.setSuperRoleId(role.getRoleId());
		userInfo.setRoles(queryUserRoleIds(userInfo.getUserId()));
		return userInfo;
	}

	/**
	 * Query whether the current system is initialized
	 *
	 * @return data
	 */
	@Override
	public Integer querySystemStatus() {
		Integer count = lambdaQuery().count();
		return count > 0 ? 1 : 0;
	}

	/**
	 * System user initialization
	 */
	@Override
	public void initUser(SystemUserBO systemUserBO) {
		Integer integer = querySystemStatus();
		if (integer > 0) {
			return;
		}
		JSONObject jsonObject = new JSONObject();
//        try {
//            RSA rsa = SecureUtil.rsa((String) null, AdminConst.userPublicKey);
//            String fromBcd = rsa.decryptStrFromBcd(systemUserBO.getCode(), KeyType.PublicKey);
//            jsonObject = JSON.parseObject(fromBcd);
//        } catch (Exception e) {
//            throw new CrmException(AdminCodeEnum.ADMIN_PHONE_VERIFY_ERROR);
//        }
//        if (jsonObject == null) {
//            throw new CrmException(AdminCodeEnum.ADMIN_PHONE_VERIFY_ERROR);
//        }

		AdminUser adminUser = new AdminUser();
		adminUser.setUsername(systemUserBO.getUsername());
		adminUser.setSalt(IdUtil.simpleUUID());
		adminUser.setPassword(UserUtil.sign(systemUserBO.getUsername() + systemUserBO.getPassword(), adminUser.getSalt()));
		adminUser.setCreateTime(new Date());
		adminUser.setRealname("admin");
		adminUser.setMobile(systemUserBO.getUsername());
		adminUser.setDeptId(14852);
		adminUser.setPost("Standard Post");
		adminUser.setStatus(1);
		adminUser.setParentId(0L);
		save(adminUser);
		lambdaUpdate().set(AdminUser::getUserId, UserUtil.getSuperUser())
				.eq(AdminUser::getUserId, adminUser.getUserId()).update();
		adminUserConfigService.save(new AdminUserConfig(null, UserUtil.getSuperUser(), 1, "InitUserConfig", jsonObject.toJSONString(), "User Information"));
		//registerSeataToNacos();
	}

	private void registerSeataToNacos() {
		Map<String, String> nacosMap = new HashMap<>();
		nacosMap.put("service.vgroupMapping.crm_tx_group", "default");
		nacosMap.put("service.vgroupMapping.admin_tx_group", "default");
		nacosMap.put("service.vgroupMapping.examine_tx_group", "default");
		nacosMap.put("service.vgroupMapping.oa_tx_group", "default");
		nacosMap.put("service.vgroupMapping.hrm_tx_group", "default");
		nacosMap.put("service.vgroupMapping.jxc_tx_group", "default");
		nacosMap.put("store.mode", "db");
		nacosMap.put("store.db.datasource", "druid");
		nacosMap.put("store.db.dbType", "mysql");
		nacosMap.put("store.db.driverClassName", "com.mysql.jdbc.Driver");
		String host = dataSourceProperties.getUrl().replace("jdbc:mysql://", "").split(":")[0];
		nacosMap.put("store.db.url", "jdbc:mysql://" + host + ":3306/seata?useUnicode=true");
		nacosMap.put("store.db.user", dataSourceProperties.getUsername());
		nacosMap.put("store.db.password", dataSourceProperties.getPassword());
		nacosMap.put("store.db.minConn", "5");
		nacosMap.put("store.db.maxConn", "30");
		nacosMap.put("store.db.globalTable", "global_table");
		nacosMap.put("store.db.branchTable", "branch_table");
		nacosMap.put("store.db.queryLimit", "100");
		nacosMap.put("store.db.lockTable", "lock_table");
		nacosMap.put("store.db.maxWait", "5000");
		String group = "SEATA_GROUP";
		nacosMap.forEach((k, v) -> {
//            try {
//                Properties properties = new Properties();
//                properties.put("serverAddr", serverAddr);
//                ConfigService configService = NacosFactory.createConfigService(properties);
//                boolean isPublishOk = configService.publishConfig(k, group, v);
//                log.warn("seata initialization: {}", isPublishOk);
//            } catch (NacosException e) {
//                log.error("sync seata failed", e);
//            }
		});

	}

	/**
	 * Query all employees
	 *
	 * @return
	 */
	@Override
	public List<UserInfo> queryAllUserInfoList() {
		List<AdminUser> adminUserList = lambdaQuery().list();
		List<AdminUserRole> userRoles = adminUserRoleService.query().list();
		Map<Long, List<AdminUserRole>> longListMap = userRoles.stream().collect(Collectors.groupingBy(AdminUserRole::getUserId));
		List<UserInfo> userInfoList = adminUserList.stream().map(user -> BeanUtil.copyProperties(user, UserInfo.class)).collect(Collectors.toList());
		for (UserInfo userInfo : userInfoList) {
			List<AdminUserRole> roleList = longListMap.get(userInfo.getUserId());
			if (roleList == null) {
				roleList = new ArrayList<>();
			}
			userInfo.setRoles(roleList.stream().map(AdminUserRole::getRoleId).collect(Collectors.toList()));
		}
		return userInfoList;
	}

	@Override
	public void downloadExcel(HttpServletResponse response) throws IOException {
		List<JSONObject> list = queryField();
		ExcelParseUtil.importExcel(new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "employee";
			}
		}, list, response, "user");
	}

	private List<JSONObject> queryField() {
		List<JSONObject> list = new ArrayList<>();
		list.add(queryField("username", FieldEnum.TEXT.getFormType(), FieldEnum.TEXT.getType(), "mobile number", 1));
		list.add(queryField("password", FieldEnum.TEXT.getFormType(), FieldEnum.TEXT.getType(), "login password", 1));
		list.add(queryField("realname", FieldEnum.TEXT.getFormType(), FieldEnum.TEXT.getType(), "name", 1));
		list.add(queryField("sex", FieldEnum.TEXT.getFormType(), FieldEnum.TEXT.getType(), "sex", 0));
		list.add(queryField("email", FieldEnum.TEXT.getFormType(), FieldEnum.TEXT.getType(), "mailbox", 0));
		list.add(queryField("deptName", FieldEnum.TEXT.getFormType(), FieldEnum.TEXT.getType(), "dept", 1));
		list.add(queryField("post", FieldEnum.TEXT.getFormType(), FieldEnum.TEXT.getType(), "post", 0));
		return list;
	}

	private JSONObject queryField(String fieldName, String formType, Integer type, String name, Integer isNull) {
		JSONObject json = new JSONObject();
		json.fluentPut("fieldName", fieldName)
				.fluentPut("formType", formType)
				.fluentPut("type", type)
				.fluentPut("name", name).fluentPut("isNull", isNull);
		return json;
	}
}
