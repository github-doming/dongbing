package com.ibm.follow.servlet.client.core.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.tools.ParamTool;
import com.ibm.follow.servlet.client.ibmc_config.service.IbmcConfigService;
import org.doming.core.common.TransactionsBase;
import org.doming.develop.http.jsoup.HttpJsoupTool;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
/**
 * @Description: 注册控制器
 * @Author: zjj
 * @Date: 2019-08-28 15:52
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class ClientServletController extends TransactionsBase {

	/**
	 * 中心端客户机
	 */
	private static final String REGISTER_URL = IbmMainConfig.HOST + IbmMainConfig.PROJECT + "/ibm/cloud/manage/registerClient";
    /**
     * 注销客户机，清除客户机数据
     */
	private static final String CANCEL_URL = IbmMainConfig.HOST + IbmMainConfig.PROJECT + "/ibm/cloud/manage/cancelClient";
    /**
     * 停止客户机，不清除数据
     */
    private static final String STOP_URL = IbmMainConfig.HOST + IbmMainConfig.PROJECT + "/ibm/cloud/manage/stopClient";

	public boolean register(Object clientCode) throws SQLException {

		JSONObject data = new JSONObject();
		data.put("clientCode", clientCode);

		// 获取容量信息
		super.transaction = Connection.TRANSACTION_READ_UNCOMMITTED;
		super.transactionBegin();
		Map<String, Object> capacityMax = new IbmcConfigService().findAllMaxCapacity();
		super.transactionEnd();
		data.put("capacityMax", new JSONObject(capacityMax));

		boolean flag = false;
		for (int i = 0; i < 5; i++) {
			try {
				String html = HttpJsoupTool.doGetJson(60 * 1000, REGISTER_URL, ParamTool.paramJson(data));
				JSONObject result = JSON.parseObject(html);
				if (result.getBoolean("success")) {
					flag = true;
					break;
				}
			} catch (IOException e) {
				log.info("智能投注客户端，注册失败正在重试" + e.getMessage());
			}
		}
		if (flag) {
			log.info("智能投注客户端，注册完成");
		} else {
			log.error("智能投注客户端，注册失败");
		}
		return flag;
	}

	/**
	 * 注销客户端
	 *
	 * @param clientCode 客户端编码
	 * @return 注销结果
	 */
	public boolean cancel(Object clientCode) throws SQLException {
		JSONObject data = new JSONObject();
		data.put("clientCode", clientCode);
		boolean flag = false;
		for (int i = 0; i < 5; i++) {
			try {
				String html = HttpJsoupTool.doGetJson(60 * 1000, CANCEL_URL, ParamTool.paramJson(data));
				JSONObject result = JSON.parseObject(html);
				if (result.getBoolean("success")) {
					flag = true;
					break;
				}
			} catch (IOException e) {
				log.info("智能投注客户端，注销失败正在重试" + e.getMessage());
			}
		}
		if (flag) {
			log.info("智能投注客户端，注销客户机完成");
			transaction = Connection.TRANSACTION_READ_UNCOMMITTED;
			try {
				super.transactionBegin();
				new IbmcConfigService().cancelClientInfo();
			} finally {
				super.transactionEnd();
			}
		} else {
			log.error("智能投注客户端，注销客户机失败");
		}
		return flag;
	}
    /**
     * 关闭客户端不清除数据
     *
     * @param clientCode 客户端编码
     * @return 注销结果
     */
    public boolean stop(Object clientCode) {
        JSONObject data = new JSONObject();
        data.put("clientCode", clientCode);
        boolean flag = false;
        for (int i = 0; i < 5; i++) {
            try {
                String html = HttpJsoupTool.doGetJson(60 * 1000, STOP_URL, ParamTool.paramJson(data));
                JSONObject result = JSON.parseObject(html);
                if (result.getBoolean("success")) {
                    flag = true;
                    break;
                }
            } catch (IOException e) {
                log.info("智能投注客户端，注销失败正在重试" + e.getMessage());
            }
        }
        if (flag) {
            log.info("智能投注客户端，注销客户机完成");
        } else {
            log.error("智能投注客户端，注销客户机失败");
        }
        return flag;
    }

}
