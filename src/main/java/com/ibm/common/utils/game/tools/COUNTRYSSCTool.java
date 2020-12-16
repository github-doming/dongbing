package com.ibm.common.utils.game.tools;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_country_ssc.entity.IbmRepDrawCountrySsc;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_country_ssc.entity.IbmRepGrabCountrySsc;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.IpTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.ReflectTool;

import java.net.SocketException;
import java.util.Date;
/**
 * 国家时时彩
 *
 * @Author: Dongming
 * @Date: 2020-04-30 17:27
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class COUNTRYSSCTool  extends SSCTool {

	/**
	 * 获取结果数据
	 *
	 * @param grabId       爬取id
	 * @param grabCountrySsc 爬取内容
	 * @return 结果数据
	 */
	public static IbmRepDrawCountrySsc draw(String grabId, IbmRepGrabCountrySsc grabCountrySsc) throws SocketException {
		String[] drawNumberStrs = grabCountrySsc.getDrawNumber().split(",");
		Integer[] drawNumbers = NumberTool.intValue(drawNumberStrs);
		if (ContainerTool.isEmpty(drawNumbers) || 5 != drawNumbers.length) {
			return null;
		}
		IbmRepDrawCountrySsc drawCountrySsc = new IbmRepDrawCountrySsc();
		drawCountrySsc.setRepGrabCountrySscId(grabId);
		drawCountrySsc.setHandicapCode(grabCountrySsc.getHandicapCode());
		drawCountrySsc.setType(grabCountrySsc.getType());
		drawCountrySsc.setGameId(grabCountrySsc.getGameId());
		drawCountrySsc.setPeriod(grabCountrySsc.getPeriod());
		drawCountrySsc.setDrawTime(grabCountrySsc.getDrawTime());
		drawCountrySsc.setDrawTimeLong(grabCountrySsc.getDrawTimeLong());
		drawCountrySsc.setDrawNumber(grabCountrySsc.getDrawNumber());
		for (int i = 0; i < 5; i++) {
			ReflectTool.set(drawCountrySsc, "setP" + (i + 1) + "Number", drawNumberStrs[i]);
			ReflectTool.set(drawCountrySsc, "setP" + (i + 1) + "NumberEn", NumberTool.getEn(drawNumberStrs[i]));
			ReflectTool.set(drawCountrySsc, "setP" + (i + 1) + "Size", JSSSCTool.size(drawNumbers[i]));
			ReflectTool.set(drawCountrySsc, "setP" + (i + 1) + "SizeEn", JSSSCTool.sizeEn(drawNumbers[i]));
			ReflectTool.set(drawCountrySsc, "setP" + (i + 1) + "Single", JSSSCTool.single(drawNumbers[i]));
			ReflectTool.set(drawCountrySsc, "setP" + (i + 1) + "SingleEn", JSSSCTool.singleEn(drawNumbers[i]));
		}
		drawCountrySsc.setDragonTiger(JSSSCTool.dragon(drawNumbers[0], drawNumbers[4]));
		drawCountrySsc.setDragonTigerEn(JSSSCTool.dragonEn(drawNumbers[0], drawNumbers[4]));

		int total = 0;
		for (int i = 0; i < 5; i++) {
			total += drawNumbers[i];
		}
		drawCountrySsc.setTotal(total);
		drawCountrySsc.setTopEn(NumberTool.getEn(total));
		drawCountrySsc.setTotalSingle(JSSSCTool.single(total));
		drawCountrySsc.setTotalSingleEn(JSSSCTool.singleEn(total));
		drawCountrySsc.setTotalSize(JSSSCTool.sizeTotal(total));
		drawCountrySsc.setTotalSizeEn(JSSSCTool.sizeTotalEn(total));
		drawCountrySsc.setTop(JSSSCTool.threeBalls(drawNumbers[0], drawNumbers[1], drawNumbers[2]));
		drawCountrySsc.setTopEn(JSSSCTool.threeBallsEn(drawCountrySsc.getTop()));
		drawCountrySsc.setCentre(JSSSCTool.threeBalls(drawNumbers[1], drawNumbers[2], drawNumbers[3]));
		drawCountrySsc.setCentreEn(JSSSCTool.threeBallsEn(drawCountrySsc.getCentre()));
		drawCountrySsc.setLater(JSSSCTool.threeBalls(drawNumbers[2], drawNumbers[3], drawNumbers[4]));
		drawCountrySsc.setLaterEn(JSSSCTool.threeBallsEn(drawCountrySsc.getLater()));
		drawCountrySsc.setCreateTime(new Date());
		drawCountrySsc.setCreateTimeLong(System.currentTimeMillis());
		drawCountrySsc.setUpdateTimeLong(System.currentTimeMillis());
		drawCountrySsc.setState(IbmStateEnum.OPEN.name());
		drawCountrySsc.setDesc(IpTool.getLocalIpList().toString());
		return drawCountrySsc;
	}
}
