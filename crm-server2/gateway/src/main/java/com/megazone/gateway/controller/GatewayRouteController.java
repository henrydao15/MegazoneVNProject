package com.megazone.gateway.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.megazone.core.common.JSON;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.Result;
import com.megazone.gateway.service.GatewayRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/gateway")
public class GatewayRouteController {

	@Autowired
	private GatewayRouteService gatewayRouteService;

	@Value("${upgradeFile.url}")
	private String fileUrl;

	@RequestMapping("/")
	public Result index() {
		return Result.ok();
	}

	@RequestMapping("/reloadConfig")
	public Result reloadConfig() {
		gatewayRouteService.loadConfig();
		return Result.ok();
	}

	@RequestMapping("/queryUpgradeFile")
	public Result<JsonNode> queryUpgradeFile() {
		if (FileUtil.exist(fileUrl)) {
			FileReader fileReader = new FileReader(fileUrl);
			String str = fileReader.readString();
			if (StrUtil.isNotEmpty(str)) {
				JSONObject json = JSON.parseObject(str);
				return Result.ok(json.node);
			}
		}
		return Result.ok(new JSONObject().node);
	}

}
