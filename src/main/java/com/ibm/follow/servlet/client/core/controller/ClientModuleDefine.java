package com.ibm.follow.servlet.client.core.controller;

import c.a.config.SysConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.tools.EncryptTool;
import com.ibm.common.tools.ParamTool;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.http.tools.CrawlerFactory;
import com.ibm.common.utils.http.utils.agent.BaseAgentUtil;
import com.ibm.follow.servlet.client.core.cache.CustomerCache;
import com.ibm.follow.servlet.client.core.cache.PlatformCache;
import com.ibm.follow.servlet.client.core.controller.config.SealTimeSetController;
import com.ibm.follow.servlet.client.ibmc_config.service.IbmcConfigService;
import com.ibm.follow.servlet.client.ibmc_exist_ha.service.IbmcExistHaService;
import com.ibm.follow.servlet.client.ibmc_exist_hm.service.IbmcExistHmService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.develop.http.jsoup.HttpJsoupTool;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 客户端模块 - 定义类
 *
 * @Author: Dongming
 * @Date: 2020-01-02 10:45
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ClientModuleDefine {
	protected static final Logger log = LogManager.getLogger(ClientModuleDefine.class);
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
	 * @param clientCode 客户端 code
	 * @return 注册结果
	 */
	public boolean register(String clientCode) throws Exception {
		Map<String, Object> capacityMax = new IbmcConfigService().findAllMaxCapacity();
		JSONObject data = new JSONObject();
		data.put("clientCode", clientCode);
		data.put("capacityMax", new JSONObject(capacityMax));
		String registerUrl = host() + project() + "/ibm/cloud/manage/registerClient";
		for (int i = 0; i < 5; i++) {
			try {
				String html = HttpJsoupTool.doGetJson(60 * 1000, registerUrl, ParamTool.paramJson(data));
				JSONObject result = JSON.parseObject(html);
				if (IbmCodeEnum.IBM_403_EXIST_CODE.getCode().equals(result.getString("code"))) {
					log.error("注册客户端失败，客户端编码已存在");
					return false;
				}
				if (IbmCodeEnum.IBM_403_REGISTER.getCode().equals(result.getString("code"))) {
					log.error("注册客户端失败，注册IP不存在");
					return false;
				}
				if (IbmCodeEnum.IBM_404_DATA.getCode().equals(result.getString("code"))) {
					log.error("注册客户端失败，寻找到的state错误");
					return false;
				}
				if (result.getBoolean("success")) {
				    //保存封盘时间信息
                    new SealTimeSetController().execute(result.getJSONObject("data"));
					return true;
				}
			} catch (IOException e) {
				log.info("智能投注客户端，注册失败正在重试:{}", e.getMessage());
			}
		}
		return false;
	}
	/**
	 * 注册
	 * @param clientCode 客户端 code
	 * @return 注册结果
	 */
	public boolean registerVr(String clientCode) throws Exception {
		Map<String, Object> capacityMax = new IbmcConfigService().findVrMaxCapacity();
		JSONObject data = new JSONObject();
		data.put("clientCode", clientCode);
		data.put("capacityMax", new JSONObject(capacityMax));
		String registerUrl = host() + project() + "/ibm/cloud/manage/registerClient";
		for (int i = 0; i < 5; i++) {
			try {
				String html = HttpJsoupTool.doGetJson(60 * 1000, registerUrl, ParamTool.paramJson(data));
				JSONObject result = JSON.parseObject(html);
				if (IbmCodeEnum.IBM_403_EXIST_CODE.getCode().equals(result.getString("code"))) {
					log.error("注册客户端失败，客户端编码已存在");
					return false;
				}
				if (IbmCodeEnum.IBM_403_REGISTER.getCode().equals(result.getString("code"))) {
					log.error("注册客户端失败，注册IP不存在");
					return false;
				}
				if (IbmCodeEnum.IBM_404_DATA.getCode().equals(result.getString("code"))) {
					log.error("注册客户端失败，寻找到的state错误");
					return false;
				}
				if (result.getBoolean("success")) {
					//保存封盘时间信息
					new SealTimeSetController().execute(result.getJSONObject("data"));
					return true;
				}
			} catch (IOException e) {
				log.info("智能投注客户端，注册失败正在重试:{}", e.getMessage());
			}
		}
		return false;
	}

	/**
	 * 注销
	 *
	 * @param clientCode 客户端 code
	 */
	public boolean migrate(String clientCode) throws Exception {
		JSONObject data = new JSONObject();
		data.put("clientCode", clientCode);
		String cancelUrl = host() + project() + "/ibm/cloud/manage/migrateClient";
		for (int i = 0; i < 5; i++) {
			try {
				String html = HttpJsoupTool.doGetJson(60 * 1000, cancelUrl, ParamTool.paramJson(data));
				JSONObject result = JSON.parseObject(html);
				if (IbmCodeEnum.IBM_404_DATA.getCode().equals(result.getString("code"))) {
					log.error("注销客户端失败，客户端编码不存在");
					return false;
				}
				if (result.getBoolean("success")) {
					return true;
				}
			} catch (IOException e) {
				log.info("智能投注客户端，注销失败正在重试:{}", e.getMessage());
			}
		}
		return false;
	}
	/**
	 * 注销
	 *
	 * @param clientCode 客户端 code
	 */
	public boolean cancelled(String clientCode) throws Exception {
		JSONObject data = new JSONObject();
		data.put("clientCode", clientCode);
		String cancelUrl = host() + project() + "/ibm/cloud/manage/cancelClient";
		for (int i = 0; i < 5; i++) {
			try {
				String html = HttpJsoupTool.doGetJson(60 * 1000, cancelUrl, ParamTool.paramJson(data));
				JSONObject result = JSON.parseObject(html);
				if (IbmCodeEnum.IBM_404_DATA.getCode().equals(result.getString("code"))) {
					log.error("注销客户端失败，客户端编码不存在");
					return false;
				}
				if (result.getBoolean("success")) {
					return true;
				}
			} catch (IOException e) {
				log.info("智能投注客户端，注销失败正在重试:{}", e.getMessage());
			}
		}
		return false;
	}

	/**
	 * 停止
	 *
	 * @param clientCode 客户端 code
	 */
	public boolean stop(String clientCode) throws Exception {
		JSONObject data = new JSONObject();
		data.put("clientCode", clientCode);
		String stopUrl = host() + project() + "/ibm/cloud/manage/stopClient";
		for (int i = 0; i < 5; i++) {
			try {
				String html = HttpJsoupTool.doGetJson(60 * 1000, stopUrl, ParamTool.paramJson(data));
				JSONObject result = JSON.parseObject(html);
				if (IbmCodeEnum.IBM_404_DATA.getCode().equals(result.getString("code"))) {
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

		// 加载用户基本信息到爬虫类
		List<Map<String, Object>> reloadInfos = new IbmcExistHmService().listInfo2reload();
		String existId, customerAccount, customerPassword, handicapUrl, handicapCaptcha;
		for (Map<String, Object> reloadInfo : reloadInfos) {
			HandicapUtil.Code handicapCode = HandicapUtil.Code.valueOf(reloadInfo.get("HANDICAP_CODE_").toString());
			//获取信息
			existId = reloadInfo.get("IBMC_EXIST_HM_ID_").toString();
			customerAccount = reloadInfo.get("MEMBER_ACCOUNT_").toString();
			customerPassword = EncryptTool.decode(EncryptTool.Type.ASE, reloadInfo.get("MEMBER_PASSWORD_").toString());
			handicapUrl = reloadInfo.get("HANDICAP_URL_").toString();
			handicapCaptcha = reloadInfo.get("HANDICAP_CAPTCHA_").toString();
			handicapCode.getMemberFactory()
					.userInfo(existId, handicapUrl, handicapCaptcha, customerAccount, customerPassword);
		}

		reloadInfos = new IbmcExistHaService().listInfo2reload();
		Map<HandicapUtil.Code, BaseAgentUtil> agentCrawlerInfo = new HashMap<>(5);
		for (Map<String, Object> reloadInfo : reloadInfos) {
			HandicapUtil.Code handicapCode = HandicapUtil.Code.value(reloadInfo.get("HANDICAP_CODE_"));
			if (handicapCode == null) {
				continue;
			}
			BaseAgentUtil crawlerInfo;
			if (agentCrawlerInfo.containsKey(handicapCode)) {
				crawlerInfo = agentCrawlerInfo.get(handicapCode);
			} else {
				crawlerInfo = CrawlerFactory.getFactory().getAgentCrawler(handicapCode);
				agentCrawlerInfo.put(handicapCode, crawlerInfo);
			}

			//获取信息
			existId = reloadInfo.get("IBMC_EXIST_HA_ID_").toString();
			customerAccount = reloadInfo.get("AGENT_ACCOUNT_").toString();
			customerPassword = EncryptTool.decode(EncryptTool.Type.ASE, reloadInfo.get("AGENT_PASSWORD_").toString());
			handicapUrl = reloadInfo.get("HANDICAP_URL_").toString();
			handicapCaptcha = reloadInfo.get("HANDICAP_CAPTCHA_").toString();

			//放入账户信息
			crawlerInfo.accountInfo(existId, customerAccount, customerPassword, handicapUrl, handicapCaptcha);
		}
	}

	/**
	 * 删除所有的客户信息
	 */
	public void destroy() {

	}

	/**
	 * 迁移数据
	 */
	public void migrateData(String clientCode) throws Exception {
		// 迁移会员
		/*
			1. 将需要迁移的会员信息读取出来，
			2. 读取出信息后按盘口分类
		 */
		List<Map<String, Object>>  hmMigrateInfo = new IbmcExistHmService().listInfo2migrate();

		// 迁移代理
		List<Map<String, Object>>  haMigrateInfo = new IbmcExistHaService().listInfo2migrate();

		// 发送迁移数据
		JSONObject message = new JSONObject();
		message.put("method", IbmMethodEnum.CLIENT_MIGRATE.name());
		message.put("hmMigrateInfo",hmMigrateInfo);
		message.put("haMigrateInfo",haMigrateInfo);
		RabbitMqTool.sendInfoReceipt(message.toJSONString(), "client");
	}

	public String host() throws Exception {
		if (host == null) {
			host = SysConfig.findInstance().findMap().getOrDefault("ibm.boot.host", IbmMainConfig.HOST).toString();
		}
		return host;
	}
    public String project() throws Exception {
		if (project == null) {
			project = SysConfig.findInstance().findMap().getOrDefault("ibm.boot.project", IbmMainConfig.PROJECT).toString();
		}
		return project;
	}
}
