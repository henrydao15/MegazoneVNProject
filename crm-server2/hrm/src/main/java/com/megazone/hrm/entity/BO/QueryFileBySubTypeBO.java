package com.megazone.hrm.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryFileBySubTypeBO {
	@ApiModelProperty(value = "11、 12、 13、 14、 15、 16、 17、 18、 19、 21、 22、 23、 24、 25、 26、 27、 31、 32、 33 、 ")
	private Integer subType;

	@ApiModelProperty
	private Integer employeeId;
}
