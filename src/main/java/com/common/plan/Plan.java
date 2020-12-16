package com.common.plan;

import com.alibaba.fastjson.JSONObject;
import com.common.entity.PlanItem;

import java.util.Map;

/**
 * @Description: 方案
 * @Author: zjj
 * @Date: 2020-05-19 13:53
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public interface Plan {


	/**
	 * 验证数据
	 * @param planItem	方案详情
	 * @return
	 */
	boolean valiData(PlanItem planItem);
	/**
	 * 方案组预处理
	 * @param activeKeys	激活方案组key
	 * @param planGroupData	方案组数据
	 * @return
	 */
	JSONObject advance(String[] activeKeys, JSONObject planGroupData);

	/**
	 * 方案组组装
	 * @return 组装结果
	 * @param historyMap	历史结果map
	 * @param expandInfo	扩展信息
	 */
	JSONObject splice(Map<String, Object> historyMap, Object expandInfo) ;

	/**
	 * 投注正文
	 *
	 * @param groupData  投注组数据
	 * @param fundTh     投注金额
	 * @param expandInfo 扩展信息
	 * @param historyMap 历史执行结果
	 * @return 投注正文
	 */
	String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap);

	/**
	 * 放入方案组正文
	 * @param planGroup 方案组正文
	 * @return 放入结果
	 */
	boolean planGroup(PlanGroup planGroup);

	/**
	 * 获取方案组信息
	 * @return
	 */
	PlanGroup getPlanGroup();
}
