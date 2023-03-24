package com.megazone.hrm.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import com.megazone.core.servlet.upload.FileEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

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
@Accessors(chain = true)
@TableName("wk_hrm_employee_file")
@ApiModel(value = "HrmEmployeeFile", description = "")
public class HrmEmployeeFile implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "employee_file_id", type = IdType.AUTO)
	private Integer employeeFileId;

	@ApiModelProperty(value = "id")
	private Integer employeeId;

	@ApiModelProperty(value = "adminid")
	private String fileId;

	@ApiModelProperty(value = "1  2  3")
	private Integer type;

	@ApiModelProperty(value = "11、 12、 13、 14、 15、 16、 17、 18、 19、 21、 22、 23、 24、 25、 26、 27、 31、 32、 33 、 ")
	private Integer subType;

	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;


	@TableField(exist = false)
	@ApiModelProperty
	private FileEntity file;


}
