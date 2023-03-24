package com.megazone.hrm.entity.VO;

import com.megazone.hrm.entity.PO.HrmEmployeeCertificate;
import com.megazone.hrm.entity.PO.HrmEmployeeEducationExperience;
import com.megazone.hrm.entity.PO.HrmEmployeeTrainingExperience;
import com.megazone.hrm.entity.PO.HrmEmployeeWorkExperience;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@ApiModel(description = "")
public class PersonalInformationVO {

	@ApiModelProperty
	private List<InformationFieldVO> information;

	@ApiModelProperty
	private List<InformationFieldVO> communicationInformation;

	@ApiModelProperty
	private List<HrmEmployeeEducationExperience> educationExperienceList;

	@ApiModelProperty
	private List<HrmEmployeeWorkExperience> workExperienceList;

	@ApiModelProperty
	private List<HrmEmployeeCertificate> certificateList;

	@ApiModelProperty
	private List<Map<String, Object>> hrmEmployeeContacts;

	@ApiModelProperty
	private List<HrmEmployeeTrainingExperience> trainingExperienceList;

}
