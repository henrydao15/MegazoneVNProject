package com.megazone.authorization.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class AuthorizationUserInfo implements Serializable {
	private List<AuthorizationUser> authorizationUserList = new ArrayList<>();

	public void addAuthorizationUser(AuthorizationUser user) {
		this.authorizationUserList.add(user);
	}
}
