package com.megazone.admin.entity.BO;

import com.megazone.core.entity.PageEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class AdminLanguagePackBO extends PageEntity {

	@ApiModelProperty(value = "Language Pack ID")
	private Integer languagePackId;

	@ApiModelProperty
	private String languagePackName;

}
