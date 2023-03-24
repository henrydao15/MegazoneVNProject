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
@TableName("wk_admin_user_his_table")
@ApiModel(value = "AdminUserHisTable object", description = "authorized agent")
public class AdminUserHisTable implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "his_table_id", type = IdType.AUTO)
	private Integer hisTableId;

	private Long userId;

	@ApiModelProperty(value = "0 does not have 1 has")
	private Integer hisTable;


	@ApiModelProperty(value = "1. Agent authorization 2. Set default business card 3. Associate employee")
	private Integer type;


}
