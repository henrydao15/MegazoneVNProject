package com.megazone.hrm.entity.PO;

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
 * @since 2020-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_hrm_recruit_channel")
@ApiModel(value = "HrmRecruitChannel", description = "")
public class HrmRecruitChannel implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "channel_id", type = IdType.AUTO)
	private Integer channelId;

	@ApiModelProperty(value = "0  1 ")
	private Integer isSys;

	@ApiModelProperty(value = " 0  1 ")
	private Integer status;

	private String value;


}
