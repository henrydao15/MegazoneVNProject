package com.megazone.core.feign.oa.entity;

import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.servlet.upload.FileEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ExamineVO extends OaExamine {

	@ApiModelProperty
	private Integer examineStepId;
	@ApiModelProperty
	private String categoryTitle;
	@ApiModelProperty
	private Integer type;
	@ApiModelProperty
	private String icon;

	private SimpleUser createUser;

	private String examineName;

	private List<FileEntity> img;

	private List<FileEntity> file;

	private String causeTitle;

	private Map<String, Integer> permission;

	private List<SimpleCrmEntity> customerList;
	private List<SimpleCrmEntity> contactsList;
	private List<SimpleCrmEntity> businessList;
	private List<SimpleCrmEntity> contractList;

	private OaExamineRecord record;

	private List<OaExamineTravel> examineTravelList;
}
