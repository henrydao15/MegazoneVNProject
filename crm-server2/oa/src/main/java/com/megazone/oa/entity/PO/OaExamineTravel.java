package com.megazone.oa.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_oa_examine_travel")
@ApiModel(value = "OaExamineTravel object", description = "Travel schedule")
public class OaExamineTravel implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "travel_id", type = IdType.AUTO)
	private Integer travelId;

	@ApiModelProperty(value = "Approval ID")
	private Integer examineId;

	@ApiModelProperty(value = "Origin")
	private String startAddress;

	@ApiModelProperty(value = "Departure time")
	private Date startTime;

	@ApiModelProperty(value = "destination")
	private String endAddress;

	@ApiModelProperty(value = "arrival time")
	private Date endTime;

	@ApiModelProperty(value = "Transportation cost")
	private BigDecimal traffic;

	@ApiModelProperty(value = "Accommodation")
	private BigDecimal stay;

	@ApiModelProperty(value = "Food & Beverage")
	private BigDecimal diet;

	@ApiModelProperty(value = "Other charges")
	private BigDecimal other;

	@ApiModelProperty(value = "Amount")
	private BigDecimal money;

	@ApiModelProperty(value = "vehicle")
	private String vehicle;

	@ApiModelProperty(value = "One-way round-trip (one-way, round-trip)")
	private String trip;

	@ApiModelProperty(value = "Duration")
	private BigDecimal duration;

	@ApiModelProperty(value = "remarks")
	private String description;

	@ApiModelProperty(value = "batch id")
	private String batchId;


	@TableField(exist = false)
	private List<FileEntity> img = new ArrayList<>();
	@TableField(exist = false)
	private List<FileEntity> file = new ArrayList<>();

}
