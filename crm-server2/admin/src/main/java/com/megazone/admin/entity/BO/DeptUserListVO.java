package com.megazone.admin.entity.BO;

import com.megazone.admin.entity.VO.HrmSimpleUserVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeptUserListVO {


	private List<DeptVO> deptList;

	private List<HrmSimpleUserVO> userList;
}
