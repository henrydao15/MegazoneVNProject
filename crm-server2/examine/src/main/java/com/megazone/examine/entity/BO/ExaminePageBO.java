package com.megazone.examine.entity.BO;

import com.megazone.core.entity.PageEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author
 * @date 2020/12/19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ExaminePageBO extends PageEntity {
	@ApiModelProperty(value = "0 OA 1  2  3 4 5  6 7 8  910 1112")
	private Integer label;

	private Boolean isPart;

	private Integer status;

	private Integer categoryId;

}
