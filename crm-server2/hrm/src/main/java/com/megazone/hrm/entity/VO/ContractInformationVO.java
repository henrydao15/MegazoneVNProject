package com.megazone.hrm.entity.VO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.megazone.core.servlet.upload.FileEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel
public class ContractInformationVO {

	@ApiModelProperty
	private Integer contractId;

	@ApiModelProperty
	private Integer employeeId;

	@ApiModelProperty(value = "")
	private String contractNum;

	@ApiModelProperty(value = "1、 2、 3、 4、 5、 6、 7、 8、 9、")
	private Integer contractType;

	@ApiModelProperty
	private Date startTime;

	@ApiModelProperty
	private Date endTime;

	@ApiModelProperty(value = "")
	private Integer term;

	@ApiModelProperty(value = "  0 1 、 2、 ")
	private Integer status;

	@ApiModelProperty(value = "")
	private String signCompany;

	@ApiModelProperty(value = "")
	private Date signTime;

	@ApiModelProperty(value = "")
	private String remarks;

	@ApiModelProperty(value = " 0  1 ")
	private Integer isExpireRemind;

	@ApiModelProperty
	private Integer sort;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	private String batchId;

	@ApiModelProperty
	private List<FileEntity> fileList;


}
