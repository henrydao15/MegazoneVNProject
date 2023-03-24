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
@TableName("wk_oa_event_update_record")
@ApiModel(value = "OaEventUpdateRecord object", description = "Schedule")
public class OaEventUpdateRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	private Integer eventId;

	@ApiModelProperty(value = "title")
	private Long time;

	@ApiModelProperty(value = "1 delete this 2 modify this 3 modify this and later")
	private Integer status;

	private Integer newEventId;

	private String batchId;

}
