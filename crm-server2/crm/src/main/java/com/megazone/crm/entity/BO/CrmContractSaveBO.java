package com.megazone.crm.entity.BO;

import com.megazone.core.feign.examine.entity.ExamineRecordSaveBO;
import com.megazone.crm.entity.PO.CrmContractProduct;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@ApiModel
public class CrmContractSaveBO extends CrmModelSaveBO {
	@ApiModelProperty
	private List<CrmContractProduct> product;

	@ApiModelProperty
	private Integer contactsId;

	@ApiModelProperty
	private Long checkUserId;

	@ApiModelProperty
	private ExamineRecordSaveBO examineFlowData;

	public @NotNull ExamineRecordSaveBO getExamineFlowData() {
		if (examineFlowData != null) {
			return examineFlowData;
		}
		return new ExamineRecordSaveBO();
	}
}
