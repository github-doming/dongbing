package com.ibm.common.utils.game.tools;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_self_10_5.entity.IbmRepDrawSelf105;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_self_10_5.entity.IbmRepGrabSelf105;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.IpTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.ReflectTool;

import java.net.SocketException;
import java.util.Date;

/**
 * 自有赛车五分彩
 *
 * @Author: Dongming
 * @Date: 2020-04-29 15:59
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SELF105Tool extends BallTool {

	/**
	 * 获取结果数据
	 *
	 * @param grabId      爬取id
	 * @param grabSelf105 爬取内容
	 * @return 结果数据
	 */
	public static IbmRepDrawSelf105 draw(String grabId, IbmRepGrabSelf105 grabSelf105)
			throws SocketException {
		String[] drawNumberStrs = grabSelf105.getDrawNumber().split(",");
		Integer[] drawNumbers = NumberTool.intValue(drawNumberStrs);
		if (ContainerTool.isEmpty(drawNumbers) || 10 != drawNumbers.length) {
			return null;
		}
		IbmRepDrawSelf105 drawSelf105 = new IbmRepDrawSelf105();
		drawSelf105.setRepGrabSelf105Id(grabId);
		drawSelf105.setHandicapCode(grabSelf105.getHandicapCode());
		drawSelf105.setType(grabSelf105.getType());
		drawSelf105.setGameId(grabSelf105.getGameId());
		drawSelf105.setPeriod(grabSelf105.getPeriod());
		drawSelf105.setDrawTime(grabSelf105.getDrawTime());
		drawSelf105.setDrawTimeLong(grabSelf105.getDrawTimeLong());
		drawSelf105.setDrawNumber(grabSelf105.getDrawNumber());
		for (int i = 0; i < 10; i++) {
			ReflectTool.set(drawSelf105, "setP" + (i + 1) + "Number", drawNumberStrs[i]);
			ReflectTool.set(drawSelf105, "setP" + (i + 1) + "NumberEn", NumberTool.getEn(drawNumberStrs[i]));
			ReflectTool.set(drawSelf105, "setP" + (i + 1) + "Size", size(drawNumbers[i]));
			ReflectTool.set(drawSelf105, "setP" + (i + 1) + "SizeEn", sizeEn(drawNumbers[i]));
			ReflectTool.set(drawSelf105, "setP" + (i + 1) + "Single", single(drawNumbers[i]));
			ReflectTool.set(drawSelf105, "setP" + (i + 1) + "SingleEn", singleEn(drawNumbers[i]));
		}
		for (int i = 0; i < 5; i++) {
			ReflectTool.set(drawSelf105, "setP" + (i + 1) + "Dragon", dragon(drawNumbers[i], drawNumbers[9 - i]));
			ReflectTool.set(drawSelf105, "setP" + (i + 1) + "DragonEn",
					dragonEn(drawNumbers[i], drawNumbers[9 - i]));
		}
		int championSum = drawNumbers[0] + drawNumbers[1];
		drawSelf105.setChampionSumNunmber(championSum);
		drawSelf105.setChampionSumNunmberEn(NumberTool.getEn(championSum));
		drawSelf105.setChampionSumSize(sizeChampionSum(championSum));
		drawSelf105.setChampionSumSizeEn(sizeChampionSumEn(championSum));
		drawSelf105.setChampionSumSingle(singleChampionSum(championSum));
		drawSelf105.setChampionSumSingleEn(singleChampionSumEn(championSum));
		drawSelf105.setCreateTime(new Date());
		drawSelf105.setCreateTimeLong(drawSelf105.getCreateTime().getTime());
		drawSelf105.setUpdateTime(new Date());
		drawSelf105.setUpdateTimeLong(drawSelf105.getUpdateTime().getTime());
		drawSelf105.setState(IbmStateEnum.OPEN.name());
		drawSelf105.setDesc(IpTool.getLocalIpList().toString());
		return drawSelf105;
	}
}
