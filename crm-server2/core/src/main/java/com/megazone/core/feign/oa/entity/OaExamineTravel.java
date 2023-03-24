package com.megazone.core.feign.oa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.megazone.core.servlet.upload.FileEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-07-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_oa_examine_travel")
@ApiModel(value = "OaExamineTravel", description = "")
public class OaExamineTravel implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "travel_id", type = IdType.AUTO)
	private Integer travelId;

	@ApiModelProperty(value = "ID")
	private Integer examineId;

	@ApiModelProperty(value = "")
	private String startAddress;

	@ApiModelProperty(value = "")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;

	@ApiModelProperty(value = "")
	private String endAddress;

	@ApiModelProperty(value = "")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;

	@ApiModelProperty(value = "")
	private BigDecimal traffic;

	@ApiModelProperty(value = "")
	private BigDecimal stay;

	@ApiModelProperty(value = "")
	private BigDecimal diet;

	@ApiModelProperty(value = "")
	private BigDecimal other;

	@ApiModelProperty(value = "")
	private BigDecimal money;

	@ApiModelProperty(value = "")
	private String vehicle;

	@ApiModelProperty(value = "（、）")
	private String trip;

	@ApiModelProperty(value = "")
	private BigDecimal duration;

	@ApiModelProperty(value = "")
	private String description;

	@ApiModelProperty(value = "id")
	private String batchId;

	@TableField(exist = false)
	private List<FileEntity> img = new ArrayList<>();
	@TableField(exist = false)
	private List<FileEntity> file = new ArrayList<>();

}
