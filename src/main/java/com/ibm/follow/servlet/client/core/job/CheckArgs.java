package com.ibm.follow.servlet.client.core.job;
import org.doming.core.tools.DateTool;
/**
 * 校验会员参数对象
 *
 * @Author: Dongming
 * @Date: 2019-12-27 11:30
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class CheckArgs {
	private String existId;
	/**
	 * 上次检验是否结束
	 */
	private boolean lastCheck = false;
	private long lastCheckTime=0L;
	private int checkError = 0;
	private int checkFatal = 0;
	private boolean isCheck = false;

	public CheckArgs(String existId) {
		this.existId = existId;
	}

	public boolean getLastCheck(){
		return this.lastCheck;
	}
	public void setLastCheck(boolean flag){
		this.lastCheck=flag;
	}

	public boolean isCheck() {
		return this.isCheck;
	}
	public void isCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	public boolean checkError() {
		if (System.currentTimeMillis() - lastCheckTime > DateTool.getMillisecondsMinutes(10)){
			return true;
		}
		checkError++;
		return checkFatal > 5;
	}

	public boolean checkFatal() {
		if (System.currentTimeMillis() - lastCheckTime > DateTool.getMillisecondsMinutes(10)){
			return true;
		}
		++checkFatal;
		return checkFatal > 5;
	}

	public void checkSuccess() {
		checkError = 0;
		checkFatal = 0;
		lastCheckTime = System.currentTimeMillis();
	}

}
