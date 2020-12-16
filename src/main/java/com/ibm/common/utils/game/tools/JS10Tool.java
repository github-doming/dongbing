package com.ibm.common.utils.game.tools;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_js10.entity.IbmRepDrawJs10;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_js10.entity.IbmRepGrabJs10;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.IpTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.ReflectTool;

import java.net.SocketException;
import java.util.Date;
/**
 * @Description: 极速赛车工具类
 * @Author: Dongming
 * @Date: 2019-10-17 18:37
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class JS10Tool extends BallTool {

	//TODO  结算数据区
	/**
	 * 获取结果数据
	 *
	 * @param grabId       爬取id
	 * @param grabJs10     爬取内容
	 * @return 结果数据
	 */
	public static IbmRepDrawJs10 draw(String grabId, IbmRepGrabJs10 grabJs10)
			throws SocketException {
		String[] drawNumberStrs = grabJs10.getDrawNumber().split(",");
		Integer[] drawNumbers = NumberTool.intValue(drawNumberStrs);
		if (ContainerTool.isEmpty(drawNumbers) || 10 != drawNumbers.length) {
			return null;
		}
		IbmRepDrawJs10 drawJs10 = new IbmRepDrawJs10();
		drawJs10.setRepGrabJs10Id(grabId);
		drawJs10.setHandicapCode(grabJs10.getHandicapCode());
		drawJs10.setGameId(grabJs10.getGameId());
        drawJs10.setType(grabJs10.getType());
		drawJs10.setPeriod(grabJs10.getPeriod());
		drawJs10.setDrawTime(grabJs10.getDrawTime());
		drawJs10.setDrawTimeLong(grabJs10.getDrawTimeLong());
		drawJs10.setDrawNumber(grabJs10.getDrawNumber());
		for (int i = 0; i < 10; i++) {
			ReflectTool.set(drawJs10, "setP" + (i + 1) + "Number", drawNumberStrs[i]);
			ReflectTool.set(drawJs10, "setP" + (i + 1) + "NumberEn", NumberTool.getEn(drawNumberStrs[i]));
			ReflectTool.set(drawJs10, "setP" + (i + 1) + "Size", JS10Tool.size(drawNumbers[i]));
			ReflectTool.set(drawJs10, "setP" + (i + 1) + "SizeEn", JS10Tool.sizeEn(drawNumbers[i]));
			ReflectTool.set(drawJs10, "setP" + (i + 1) + "Single", JS10Tool.single(drawNumbers[i]));
			ReflectTool.set(drawJs10, "setP" + (i + 1) + "SingleEn", JS10Tool.singleEn(drawNumbers[i]));
		}
		for (int i = 0; i < 5; i++) {
			ReflectTool.set(drawJs10, "setP" + (i + 1) + "Dragon", JS10Tool.dragon(drawNumbers[i], drawNumbers[9 - i]));
			ReflectTool.set(drawJs10, "setP" + (i + 1) + "DragonEn",
					JS10Tool.dragonEn(drawNumbers[i], drawNumbers[9 - i]));
		}
		int championSum = drawNumbers[0] + drawNumbers[1];
		drawJs10.setChampionSumNunmber(championSum);
		drawJs10.setChampionSumNunmberEn(NumberTool.getEn(championSum));
		drawJs10.setChampionSumSize(JS10Tool.sizeChampionSum(championSum));
		drawJs10.setChampionSumSizeEn(JS10Tool.sizeChampionSumEn(championSum));
		drawJs10.setChampionSumSingle(JS10Tool.singleChampionSum(championSum));
		drawJs10.setChampionSumSingleEn(JS10Tool.singleChampionSumEn(championSum));
		drawJs10.setCreateTime(new Date());
		drawJs10.setCreateTimeLong(System.currentTimeMillis());
		drawJs10.setUpdateTimeLong(System.currentTimeMillis());
		drawJs10.setState(IbmStateEnum.OPEN.name());
		drawJs10.setDesc(IpTool.getLocalIpList().toString());
		return drawJs10;
	}
	//  结算数据区
}
