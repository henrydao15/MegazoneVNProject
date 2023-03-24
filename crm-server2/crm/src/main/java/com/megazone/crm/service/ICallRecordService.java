package com.megazone.crm.service;

import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.crm.entity.BiParams;
import com.megazone.core.servlet.BaseService;
import com.megazone.crm.entity.BO.CallRecordBO;
import com.megazone.crm.entity.PO.CallRecord;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Ian
 */
public interface ICallRecordService extends BaseService<CallRecord> {

	/**
	 * @return
	 */
	CallRecord saveRecord(CallRecord callRecord);

	/**
	 * @param callRecordBO
	 * @return
	 */
	BasePage<JSONObject> pageCallRecordList(CallRecordBO callRecordBO);

	/**
	 * @param file
	 * @param id     id
	 * @param prefix
	 * @return
	 */
	boolean upload(MultipartFile file, String id, String prefix);

	/**
	 * @return
	 */
	void download(String id, HttpServletResponse response);

	/**
	 * @param search
	 * @return
	 */
	JSONObject searchPhone(String search);

	/**
	 * @return
	 */
	List<JSONObject> queryPhoneNumber(String model, String modelId);


	/**
	 * year：；lastYear：；quarter：；lastQuarter：；month：；：start_time：2019-04-19；end_time：2019-04-22
	 * user_id 	 	int id
	 *
	 * @return
	 */
	BasePage<JSONObject> analysis(BiParams biParams);
}
