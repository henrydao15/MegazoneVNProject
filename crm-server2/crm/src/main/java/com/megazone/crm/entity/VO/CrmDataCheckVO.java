package com.megazone.crm.entity.VO;

import com.megazone.core.common.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CrmDataCheckVO {

	private Integer id;

	private String name;

	private Date createTime;

	private Long ownerUserId;

	private String ownerUserName;

	private Integer type;

	private Date lastTime;

	private String poolName;

	private String poolIds;

	private Integer contactsId;

	private String contactsName;

	private JSONObject poolAuthList;
}
