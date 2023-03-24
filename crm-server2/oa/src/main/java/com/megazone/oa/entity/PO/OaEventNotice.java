package com.megazone.oa.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_oa_event_notice")
@ApiModel(value = "OaEventNotice object", description = "Schedule reminder setting table")
public class OaEventNotice implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty(value = "Schedule ID")
	private Integer eventId;

	@ApiModelProperty(value = "1 minute 2 hours 3 days")
	private Integer type;

	private Integer value;


}
