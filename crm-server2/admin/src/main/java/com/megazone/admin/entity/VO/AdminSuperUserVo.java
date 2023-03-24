package com.megazone.admin.entity.VO;

import com.megazone.core.common.JSONObject;

public class AdminSuperUserVo extends AdminUserVO {
	private JSONObject serverUserInfo;

	public JSONObject getServerUserInfo() {
		return serverUserInfo;
	}

	public void setServerUserInfo(JSONObject serverUserInfo) {
		this.serverUserInfo = serverUserInfo;
	}
}
