package com.ibm.common.core.configs;
/**
 * @Description: 定时器配置类
 * @Author: Dongming
 * @Date: 2019-07-02 10:25
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface QuartzConfig {

	String DESCRIPTION_CLEANING = "%s_数据清理";
	String NAME_CLEANING = "%s_cleaning_";
	String GROUP_CLEANING = "%s_group_cleaning_";

	String DESCRIPTION_HEARTBEAT = "%s_心跳检测";
	String NAME_HEARTBEAT = "%s_group_heartbeat_";
	String GROUP_HEARTBEAT = "%s_group_heartbeat_";

	String DESCRIPTION_BET = "向{%s}中[%s]投注";
	String NAME_BET = "%s_BET_%s_%s_%s_";
	String GROUP_BET = "BET_%s_GROUP";

	String DESCRIPTION_MERGE = "合并{%s}中%s的投注信息";
	String NAME_MERGE = "%s_BET_%s_%s_%s_";
	String GROUP_MERGE = "BET_%s_GROUP";

	String DESCRIPTION_PROFIT = "%s_%s盈亏信息%s";
	String NAME_PROFIT = "%s_PROFIT_%s_%s_%s_%s";
	String GROUP_PROFIT = "PROFIT_%s_GROUP";

	String DESCRIPTION_CHECK = "校验{%s}中%s的投注信息";
	String NAME_CHECK = "%s_CHECK_%s";
	String GROUP_CHECK = "CHECK_%s_GROUP";



	String DESCRIPTION_GRAB_BET = "抓取{%s}中[%s]投注信息";
	String NAME_GRAB_BET = "%s_GRAB_BET_%s_%s_";
	String GROUP_GRAB_BET = "GRAB_%s_BET";


}
