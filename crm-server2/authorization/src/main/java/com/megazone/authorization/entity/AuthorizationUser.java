package com.megazone.authorization.entity;

import cn.hutool.core.bean.BeanUtil;
import com.megazone.core.common.JSON;
import com.megazone.core.entity.UserInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "authority authentication object")
public class AuthorizationUser extends UserInfo implements UserDetails {

	/**
	 * SMS verification code
	 */
	@ApiModelProperty
	private String smscode;

	@ApiModelProperty
	private String password;

	@ApiModelProperty
	private String salt;

	/**
	 * Login type 1 Password login 2 Verification code login 3 Enterprise WeChat login 4 Enterprise WeChat scan code login
	 */
	@ApiModelProperty(value = "Login Type", allowableValues = "1,2,3,4")
	private Integer loginType = 1;

	@ApiModelProperty(value = "Type 1 pc 2 ", allowableValues = "1,2")
	private Integer type = 1;

	private List<UserInfo> userInfoList = new ArrayList<>();

	public static AuthorizationUser toAuthorizationUser(UserInfo systemUser) {
		return BeanUtil.copyProperties(systemUser, AuthorizationUser.class);
	}

	public AuthorizationUser setUserInfoList(List<Object> objList) {
		objList.forEach(obj -> {
			this.userInfoList.add(BeanUtil.copyProperties(obj, AuthorizationUser.class));
		});
		return this;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorityList = new ArrayList<>();
		getAuthoritiesUrlList().forEach(authoritiesUrl -> {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authoritiesUrl);
			authorityList.add(grantedAuthority);
		});
		return authorityList;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public UserInfo toUserInfo() {
		return BeanUtil.copyProperties(this, UserInfo.class);
	}

	public String toJSON() {
		return JSON.toJSONString(this);
	}
}
