package com.ibs.common.configs;
/**
 * @Author: Dongming
 * @Date: 2020-05-09 15:41
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface QuartzConfig {

	String DESCRIPTION_CLEANING = "%s_数据清理";
	String NAME_CLEANING = "%s_group_heartbeat_";
	String GROUP_CLEANING = "%s_group_cleaning_";

	String DESCRIPTION_HEARTBEAT = "%s_心跳检测";
	String NAME_HEARTBEAT = "%s_group_heartbeat_";
	String GROUP_HEARTBEAT = "%s_group_heartbeat_";


	String DESCRIPTION_CHECK = "校验{%s}中%s的个人信息";
	String NAME_CHECK = "%s_CHECK_%s";
	String GROUP_CHECK = "CHECK_%s_GROUP";

	String DESCRIPTION_BET = "向{%s}中[%s]投注";
	String NAME_BET = "%s_BET_%s_%s_%s_";
	String GROUP_BET = "BET_%s_GROUP";
}
