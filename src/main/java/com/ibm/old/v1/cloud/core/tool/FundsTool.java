package com.ibm.old.v1.cloud.core.tool;
import com.ibm.old.v1.common.doming.enums.IbmModeEnum;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import org.doming.core.tools.StringTool;
/**
 * @Description: 资金方案工具类
 * @Author: Dongming
 * @Date: 2018-12-27 14:42
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class FundsTool {

	/**
	 * 获取 资金组key<br>
	 * fundsList不为空则读取list<br>
	 * 仅有fundsListId时执行高级资金方案
	 *
	 * @param fundsList    资金列表
	 * @param fundsListId  资金列表id
	 * @param fundSwapMode 资金切换方式
	 * @param lastFundKey  上一次的资金组key
	 * @param execResult   执行结果
	 * @return 资金组key
	 */
	public static Object fundsKey(String fundsList, String fundsListId, String fundSwapMode, String lastFundKey,
			boolean execResult) {
		String fundsKey = null;
		if (StringTool.notEmpty(fundsList)) {
			int fundsLen = fundsList.split(",").length;
			fundsKey = IbmModeEnum.fundSwap(fundSwapMode, lastFundKey, execResult,fundsLen);
			if (StringTool.isEmpty(fundsKey)) {
				return null;
			} else if (fundsLen <= Integer.parseInt(fundsKey)) {
				return IbmTypeEnum.BLAST;
			}
		} else if (StringTool.notEmpty(fundsListId)) {
			//FIXME 高级资金方案尚未补充
			throw new RuntimeException("高级资金方案尚未补充");
		}
		return fundsKey;
	}

	/**
	 * 获取 资金组value<br>
	 * fundsList不为空则读取list<br>
	 * 仅有fundsListId时执行高级资金方案
	 *
	 * @param fundsList   资金列表
	 * @param fundsListId 资金列表id
	 * @param fundKey     资金组key
	 * @return 资金组value
	 */
	public static String fundsValue(String fundsList, String fundsListId, String fundKey) {
		if (StringTool.notEmpty(fundsList)) {
			return fundsList.split(",")[Integer.parseInt(fundKey)];
		} else if (StringTool.notEmpty(fundsListId)) {
			//FIXME 高级资金方案尚未补充
			throw new RuntimeException("高级资金方案尚未补充");
		}
		return null;
	}
}
