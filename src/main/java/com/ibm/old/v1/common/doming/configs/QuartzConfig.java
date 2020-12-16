package com.ibm.old.v1.common.doming.configs;
/**
 * @Description: 定时器配置类
 * @Author: Dongming
 * @Date: 2018-12-07 17:52
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface QuartzConfig {

	String CLIENT_DATA_CLEANING="数据清理_%s";
	String CLIENT_DATA_GROUP_CLEANING="data_group_cleaning_%s";


	String DESCRIPTION_LOGIN = "%s_登陆%s盘口%s";
	String IDENTITY_NAME_LOGIN = "%s_login_%s_%s";
	String IDENTITY_GROUP_LOGIN = "login_%s_Group";
	
	String DESCRIPTION_BET="%s_%s投注%s";
	String IDENTITY_NAME_BET="%s_BET_%s_%s_%s_%s";
	String IDENTITY_GROUP_BET="BET_%s_GROUP";

	String DESCRIPTION_PROFIT="%s_%s盈亏信息%s";
	String IDENTITY_NAME_PROFIT="%s_PROFIT_%s_%s_%s_%s";
	String IDENTITY_GROUP_PROFIT="PROFIT_%s_GROUP";

	String DESCRIPTION_CHECK="%S_%s校验%s";
	String IDENTITY_NAME_CHECK="%s_CHECK_%s_%s";
	String IDENTITY_GROUP_CHECK="CHECK_%s_GROUP";

}
