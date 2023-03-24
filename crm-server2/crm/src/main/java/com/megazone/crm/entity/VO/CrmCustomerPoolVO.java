package com.megazone.crm.entity.VO;

import com.megazone.core.feign.admin.entity.SimpleDept;
import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.crm.entity.PO.CrmCustomerPool;
import com.megazone.crm.entity.PO.CrmCustomerPoolFieldSetting;
import com.megazone.crm.entity.PO.CrmCustomerPoolRule;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class CrmCustomerPoolVO extends CrmCustomerPool {

	@ApiModelProperty(value = "")
	private Integer customerNum;

	@ApiModelProperty(value = "")
	private List<SimpleUser> adminUser;

	@ApiModelProperty(value = "")
	private List<SimpleUser> memberUser;

	@ApiModelProperty(value = "")
	private List<SimpleDept> memberDept;

	@ApiModelProperty(value = "")
	private List<CrmCustomerPoolRule> rule;

	@ApiModelProperty(value = "")
	private List<CrmCustomerPoolFieldSetting> field;
}
