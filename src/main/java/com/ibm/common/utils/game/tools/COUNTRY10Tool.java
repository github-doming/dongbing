package com.ibm.common.utils.game.tools;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_country_10.entity.IbmRepDrawCountry10;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_country_10.entity.IbmRepGrabCountry10;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.IpTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.ReflectTool;

import java.net.SocketException;
import java.util.Date;
/**
 * 国家赛车
 *
 * @Author: Dongming
 * @Date: 2020-04-30 17:10
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class COUNTRY10Tool  extends BallTool {

	/**
	 * 获取结果数据
	 *
	 * @param grabId       爬取id
	 * @param grabCountry10 爬取内容
	 * @return 结果数据
	 */
	public static IbmRepDrawCountry10 draw(String grabId,IbmRepGrabCountry10 grabCountry10)
			throws SocketException {
		String[] drawNumberStrs = grabCountry10.getDrawNumber().split(",");
		Integer[] drawNumbers = NumberTool.intValue(drawNumberStrs);
		if (ContainerTool.isEmpty(drawNumbers) || 10 != drawNumbers.length) {
			return null;
		}
		IbmRepDrawCountry10 drawCountry10 = new IbmRepDrawCountry10();
		drawCountry10.setRepGrabCountry10Id(grabId);
		drawCountry10.setHandicapCode(grabCountry10.getHandicapCode());
		drawCountry10.setType(grabCountry10.getType());
		drawCountry10.setGameId(grabCountry10.getGameId());
		drawCountry10.setPeriod(grabCountry10.getPeriod());
		drawCountry10.setDrawTime(grabCountry10.getDrawTime());
		drawCountry10.setDrawTimeLong(grabCountry10.getDrawTimeLong());
		drawCountry10.setDrawNumber(grabCountry10.getDrawNumber());
		for (int i = 0; i < 10; i++) {
			ReflectTool.set(drawCountry10, "setP" + (i + 1) + "Number", drawNumberStrs[i]);
			ReflectTool.set(drawCountry10, "setP" + (i + 1) + "NumberEn", NumberTool.getEn(drawNumberStrs[i]));
			ReflectTool.set(drawCountry10, "setP" + (i + 1) + "Size", size(drawNumbers[i]));
			ReflectTool.set(drawCountry10, "setP" + (i + 1) + "SizeEn", sizeEn(drawNumbers[i]));
			ReflectTool.set(drawCountry10, "setP" + (i + 1) + "Single", single(drawNumbers[i]));
			ReflectTool.set(drawCountry10, "setP" + (i + 1) + "SingleEn", singleEn(drawNumbers[i]));
		}
		for (int i = 0; i < 5; i++) {
			ReflectTool.set(drawCountry10, "setP" + (i + 1) + "Dragon", dragon(drawNumbers[i], drawNumbers[9 - i]));
			ReflectTool.set(drawCountry10, "setP" + (i + 1) + "DragonEn",
					dragonEn(drawNumbers[i], drawNumbers[9 - i]));
		}
		int championSum = drawNumbers[0] + drawNumbers[1];
		drawCountry10.setChampionSumNunmber(championSum);
		drawCountry10.setChampionSumNunmberEn(NumberTool.getEn(championSum));
		drawCountry10.setChampionSumSize(sizeChampionSum(championSum));
		drawCountry10.setChampionSumSizeEn(sizeChampionSumEn(championSum));
		drawCountry10.setChampionSumSingle(singleChampionSum(championSum));
		drawCountry10.setChampionSumSingleEn(singleChampionSumEn(championSum));
		drawCountry10.setCreateTime(new Date());
		drawCountry10.setCreateTimeLong(drawCountry10.getCreateTime().getTime());
		drawCountry10.setUpdateTime(new Date());
		drawCountry10.setUpdateTimeLong(drawCountry10.getUpdateTime().getTime());
		drawCountry10.setState(IbmStateEnum.OPEN.name());
		drawCountry10.setDesc(IpTool.getLocalIpList().toString());
		return drawCountry10;
	}
}
