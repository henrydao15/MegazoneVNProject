package com.megazone.crm.entity.BO;

import com.megazone.crm.entity.VO.CrmModelFiledVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * crmBO
 *
 * @author
 */
@Data
@ToString
@ApiModel
public class CrmModelSaveBO {
	@ApiModelProperty(value = "")
	private Map<String, Object> entity;
	@ApiModelProperty(value = "")
	private List<CrmModelFiledVO> field;
}
