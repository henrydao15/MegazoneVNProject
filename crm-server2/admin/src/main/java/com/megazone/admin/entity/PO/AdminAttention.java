package com.megazone.admin.entity.PO;

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
@TableName("wk_admin_attention")
@ApiModel(value = "AdminAttention object", description = "Address book user attention table")
public class AdminAttention implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "attention_id", type = IdType.AUTO)
	private Integer attentionId;

	@ApiModelProperty(value = "followed person")
	private Long beUserId;

	@ApiModelProperty(value = "Following people")
	private Long attentionUserId;
}
