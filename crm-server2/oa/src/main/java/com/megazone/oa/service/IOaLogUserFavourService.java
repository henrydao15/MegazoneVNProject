package com.megazone.oa.service;

import com.megazone.core.common.JSONObject;
import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.core.servlet.BaseService;
import com.megazone.oa.entity.PO.OaLogUserFavour;

import java.util.List;

public interface IOaLogUserFavourService extends BaseService<OaLogUserFavour> {

	List<SimpleUser> queryFavourLogUserList(Integer logId);

	boolean queryUserWhetherFavourLog(Integer logId);

	JSONObject userFavourOrCancel(boolean isFavour, Integer logId);


}
