package com.megazone.crm.entity.VO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class CrmPrintFieldVO {

	private List<CrmModelFiledVO> business;

	private List<CrmModelFiledVO> contract;

	private List<CrmModelFiledVO> contacts;

	private List<CrmModelFiledVO> receivables;

	private List<CrmModelFiledVO> customer;

	private List<CrmModelFiledVO> product;
}
