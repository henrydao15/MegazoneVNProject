package com.megazone.crm.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Objects;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-19
 */
@Data
@Accessors(chain = true)
@TableName("wk_crm_field_sort")
@ApiModel(value = "CrmFieldSort", description = "")
public class CrmFieldSort implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty(value = "ID")
	private Integer fieldId;

	@ApiModelProperty(value = "")
	private String fieldName;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = " 1  2  3  4  5  6  78. 18 ")
	private Integer label;

	@ApiModelProperty(value = "")
	private Integer type;

	@ApiModelProperty(value = "")
	private Integer style;

	@ApiModelProperty(value = "")
	private Integer sort;

	@ApiModelProperty(value = "id")
	private Long userId;

	@ApiModelProperty(value = " 0、 1、")
	private Integer isHide;


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CrmFieldSort that = (CrmFieldSort) o;
		return fieldName.equals(that.fieldName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(fieldName);
	}
}
