package com.ibs.plan.module.client.core.controller;

import c.a.config.SysConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.enums.CodeEnum;
import com.ibs.common.tools.EncryptTool;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.common.core.configs.PlanMainConfig;
import com.ibs.plan.module.client.core.CustomerCache;
import com.ibs.plan.module.client.core.PlatformCache;
import com.ibs.plan.module.client.ibsc_config.service.IbscConfigService;
import com.ibs.plan.module.client.ibsc_exist_hm.service.IbscExistHmService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.IpTool;
import org.doming.core.tools.Md5Tool;
import org.doming.develop.http.HttpTool;
import org.doming.develop.http.jsoup.HttpJsoupTool;

import java.io.IOException;
import java.util.List;
import java.util.Map;
/**
 * 客户端模块 - 控制器
 *
 * @Author: null
 * @Date: 2020-05-09 14:19
 * @Version: v1.0
 */
public class ClientModuleController {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 主服务器地址
	 */
	private static String host;
	/**
	 * 主服务器项目
	 */
	private static String project;

	/**
	 * 注册
	 *
	 * @param clientCode 客户端code
	 * @return 注册结果
	 */
	public boolean register(String clientCode,Object clientType) throws Exception {
		Map<String, Object> capacity = new IbscConfigService().capacity();
		JSONObject data = new JSONObject();
		data.put("ip", IpTool.getIpExtranet());
		data.put("clientCode", clientCode);
		data.put("capacity", capacity);
		data.put("clientType", clientType);
		String time = System.currentTimeMillis() + "";
		data.put("time", time);
		data.put("valiDate", Md5Tool.generate(time));
		String registerUrl = host() + project() + "/ibs/cloud/manage/registerClient";
		for (int i = 0; i < 5; i++) {
			try {
				String html = HttpJsoupTool.doGetJson(60 * 1000, registerUrl, HttpTool.paramJson(data));
				JSONObject result = JSON.parseObject(html);
				if (CodeEnum.IBS_403_EXIST.getCode().equals(result.getString("code"))) {
					log.error("注册客户端失败，客户端编码已存在");
					return false;
				}
				if (CodeEnum.IBS_404_DATA.getCode().equals(result.getString("code"))) {
					log.error("注册客户端失败，注册IP不存在");
					return false;
				}
				if (CodeEnum.IBS_403_ERROR.getCode().equals(result.getString("code"))) {
					log.error("注册客户端失败，其他错误");
					return false;
				}
				if (result.getBoolean("success")) {
					//保存封盘时间信息
					new ClientDefineController().sealTime(result.getJSONObject("data"));
					return true;
				}
			} catch (Exception e) {
				log.info("智能投注客户端，注册失败正在重试:{}", e.getMessage());
			}

		}
		return false;
	}
	/**
	 * 注销
	 *
	 * @param clientCode 客户端code
	 * @return 注销结果
	 */
	public boolean stop(String clientCode) throws Exception {
		JSONObject data = new JSONObject();
		data.put("clientCode", clientCode);
		data.put("ip", IpTool.getIpExtranet());
		String time = System.currentTimeMillis() + "";
		data.put("time", time);
		data.put("valiDate", Md5Tool.generate(time));
		String stopUrl = host() + project() + "/ibs/cloud/manage/stopClient";
		for (int i = 0; i < 5; i++) {
			try {
				String html = HttpJsoupTool.doGetJson(60 * 1000, stopUrl, HttpTool.paramJson(data));
				JSONObject result = JSON.parseObject(html);
				if (CodeEnum.IBS_404_DATA.getCode().equals(result.getString("code"))) {
					log.error("停止客户端失败，客户端编码不存在");
					return false;
				}
				if (result.getBoolean("success")) {
					return true;
				}
			} catch (IOException e) {
				log.info("智能投注客户端，停止失败正在重试:{}", e.getMessage());
			}
		}
		return false;
	}

	/**
	 * 重新加载
	 */
	public void reload() throws Exception {
		//缓存内存信息
		PlatformCache.cache();
		CustomerCache.cache();

		// 加载会员基本信息到爬虫类
		List<Map<String, Object>> reloadInfos = new IbscExistHmService().listInfo2reload();
		String existId, customerAccount, customerPassword, handicapUrl, handicapCaptcha;
		for (Map<String, Object> reloadInfo : reloadInfos) {
			HandicapUtil.Code handicapCode = HandicapUtil.Code.valueOf(reloadInfo.get("HANDICAP_CODE_").toString());
			//获取信息
			existId = reloadInfo.get("IBSC_EXIST_HM_ID_").toString();
			customerAccount = reloadInfo.get("MEMBER_ACCOUNT_").toString();
			customerPassword = EncryptTool.decode(EncryptTool.Type.ASE, reloadInfo.get("MEMBER_PASSWORD_").toString());
			handicapUrl = reloadInfo.get("HANDICAP_URL_").toString();
			handicapCaptcha = reloadInfo.get("HANDICAP_CAPTCHA_").toString();

			handicapCode.getMemberFactory()
					.userInfo(existId, handicapUrl, handicapCaptcha, customerAccount, customerPassword);

		}

	}

	private String host() throws Exception {
		if (host == null) {
			host = SysConfig.findInstance().findMap().getOrDefault("ibs.boot.host", PlanMainConfig.HOST).toString();
		}
		return host;
	}
	private String project() throws Exception {
		if (project == null) {
			project = SysConfig.findInstance().findMap().getOrDefault("ibs.boot.project", PlanMainConfig.PROJECT)
					.toString();
		}
		return project;
	}
}
