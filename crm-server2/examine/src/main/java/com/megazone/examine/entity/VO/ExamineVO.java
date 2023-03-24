package com.megazone.examine.entity.VO;

import com.megazone.core.feign.admin.entity.SimpleDept;
import com.megazone.core.feign.admin.entity.SimpleUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "Examine", description = "")
public class ExamineVO {

	@ApiModelProperty(value = "ID")
	private Long examineId;

	@ApiModelProperty(value = "0 OA 1  2  3 4 5  6 7 8  910 1112")
	private Integer label;

	@ApiModelProperty(value = "")
	private String examineIcon;

	@ApiModelProperty(value = " 1  2 ")
	private Integer recheckType;

	@ApiModelProperty(value = "")
	private String examineName;

	@ApiModelProperty(value = "")
	private Date createTime;

	@ApiModelProperty(value = "ID")
	private Long createUserId;

	@ApiModelProperty(value = "")
	private String createUserName;

	@ApiModelProperty(value = "")
	private String updateUserName;

	@ApiModelProperty(value = "")
	private Date updateTime;

	@ApiModelProperty(value = "1  2  3  ")
	private Integer status;

	@ApiModelProperty(value = "")
	private String remarks;

	@ApiModelProperty(value = "")
	private List<Long> managerList;

	@ApiModelProperty(value = "（）")
	private String userIds;

	@ApiModelProperty(value = "（）")
	private String deptIds;

	@ApiModelProperty(value = "1  2  3  4  5  6  0 ")
	private Integer oaType;


	private List<SimpleUser> userList;

	private List<SimpleDept> deptList;
}
