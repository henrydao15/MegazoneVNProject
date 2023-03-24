package com.megazone.authorization.entity.VO;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@ApiModel(value = "Login successful return object", description = "Login successful return object")
@Accessors(chain = true)
@Data
@ToString
public class LoginVO {

	private String adminToken;
}
