package com.megazone.crm.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-06-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_crm_instrument_sort")
@ApiModel(value = "CrmInstrumentSort", description = "")
public class CrmInstrumentSort implements Serializable {

	private static final long serialVersionUID = 1L;
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	@ApiModelProperty(value = "id")
	private Long userId;
	@ApiModelProperty(value = "id 1、 2、 3、 4、 5、 6、 7、")
	private Integer modelId;
	@ApiModelProperty(value = " 1 2")
	private Integer list;
	@ApiModelProperty(value = "")
	private Integer sort;
	@ApiModelProperty(value = " 0 1")
	private Integer isHidden;

	public CrmInstrumentSort() {
	}

	public CrmInstrumentSort(Integer modelId, Integer isHidden, Integer list) {
		this.modelId = modelId;
		this.isHidden = isHidden;
		this.list = list;
	}


}
