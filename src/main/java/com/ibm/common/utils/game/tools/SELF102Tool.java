package com.ibm.common.utils.game.tools;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_self_10_2.entity.IbmRepDrawSelf102;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_self_10_2.entity.IbmRepGrabSelf102;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.IpTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.ReflectTool;

import java.net.SocketException;
import java.util.Date;
/**
 * 自有赛车两分彩
 *
 * @Author: Dongming
 * @Date: 2020-04-29 15:59
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SELF102Tool extends BallTool {

	/**
	 * 获取结果数据
	 *
	 * @param grabId       爬取id
	 * @param grabSelf102 爬取内容
	 * @return 结果数据
	 */
	public static IbmRepDrawSelf102 draw(String grabId,  IbmRepGrabSelf102 grabSelf102)
			throws SocketException {
		String[] drawNumberStrs = grabSelf102.getDrawNumber().split(",");
		Integer[] drawNumbers = NumberTool.intValue(drawNumberStrs);
		if (ContainerTool.isEmpty(drawNumbers) || 10 != drawNumbers.length) {
			return null;
		}
		IbmRepDrawSelf102 drawSelf102 = new IbmRepDrawSelf102();
		drawSelf102.setRepGrabSelf102Id(grabId);
		drawSelf102.setHandicapCode(grabSelf102.getHandicapCode());
		drawSelf102.setType(grabSelf102.getType());
		drawSelf102.setGameId(grabSelf102.getGameId());
		drawSelf102.setPeriod(grabSelf102.getPeriod());
		drawSelf102.setDrawTime(grabSelf102.getDrawTime());
		drawSelf102.setDrawTimeLong(grabSelf102.getDrawTimeLong());
		drawSelf102.setDrawNumber(grabSelf102.getDrawNumber());
		for (int i = 0; i < 10; i++) {
			ReflectTool.set(drawSelf102, "setP" + (i + 1) + "Number", drawNumberStrs[i]);
			ReflectTool.set(drawSelf102, "setP" + (i + 1) + "NumberEn", NumberTool.getEn(drawNumberStrs[i]));
			ReflectTool.set(drawSelf102, "setP" + (i + 1) + "Size", size(drawNumbers[i]));
			ReflectTool.set(drawSelf102, "setP" + (i + 1) + "SizeEn", sizeEn(drawNumbers[i]));
			ReflectTool.set(drawSelf102, "setP" + (i + 1) + "Single", single(drawNumbers[i]));
			ReflectTool.set(drawSelf102, "setP" + (i + 1) + "SingleEn", singleEn(drawNumbers[i]));
		}
		for (int i = 0; i < 5; i++) {
			ReflectTool.set(drawSelf102, "setP" + (i + 1) + "Dragon", dragon(drawNumbers[i], drawNumbers[9 - i]));
			ReflectTool.set(drawSelf102, "setP" + (i + 1) + "DragonEn",
					dragonEn(drawNumbers[i], drawNumbers[9 - i]));
		}
		int championSum = drawNumbers[0] + drawNumbers[1];
		drawSelf102.setChampionSumNunmber(championSum);
		drawSelf102.setChampionSumNunmberEn(NumberTool.getEn(championSum));
		drawSelf102.setChampionSumSize(sizeChampionSum(championSum));
		drawSelf102.setChampionSumSizeEn(sizeChampionSumEn(championSum));
		drawSelf102.setChampionSumSingle(singleChampionSum(championSum));
		drawSelf102.setChampionSumSingleEn(singleChampionSumEn(championSum));
		drawSelf102.setCreateTime(new Date());
		drawSelf102.setCreateTimeLong(drawSelf102.getCreateTime().getTime());
		drawSelf102.setUpdateTime(new Date());
		drawSelf102.setUpdateTimeLong(drawSelf102.getUpdateTime().getTime());
		drawSelf102.setState(IbmStateEnum.OPEN.name());
		drawSelf102.setDesc(IpTool.getLocalIpList().toString());
		return drawSelf102;
	}
}
