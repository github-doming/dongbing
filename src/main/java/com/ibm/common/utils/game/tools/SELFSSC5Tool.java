package com.ibm.common.utils.game.tools;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_self_ssc_5.entity.IbmRepDrawSelfSsc5;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_self_ssc_5.entity.IbmRepGrabSelfSsc5;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.IpTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.ReflectTool;

import java.net.SocketException;
import java.util.Date;
/**
 * 自有时时彩五分彩
 *
 * @Author: Dongming
 * @Date: 2020-04-30 16:54
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SELFSSC5Tool extends SSCTool {

	/**
	 * 获取结果数据
	 *
	 * @param grabId       爬取id
	 * @param grabSelfSsc5 爬取内容
	 * @return 结果数据
	 */
	public static IbmRepDrawSelfSsc5 draw(String grabId, IbmRepGrabSelfSsc5 grabSelfSsc5) throws SocketException {
		String[] drawNumberStrs = grabSelfSsc5.getDrawNumber().split(",");
		Integer[] drawNumbers = NumberTool.intValue(drawNumberStrs);
		if (ContainerTool.isEmpty(drawNumbers) || 5 != drawNumbers.length) {
			return null;
		}
		IbmRepDrawSelfSsc5 drawJsssc = new IbmRepDrawSelfSsc5();
		drawJsssc.setRepGrabSelfSsc5Id(grabId);
		drawJsssc.setHandicapCode(grabSelfSsc5.getHandicapCode());
		drawJsssc.setType(grabSelfSsc5.getType());
		drawJsssc.setGameId(grabSelfSsc5.getGameId());
		drawJsssc.setPeriod(grabSelfSsc5.getPeriod());
		drawJsssc.setDrawTime(grabSelfSsc5.getDrawTime());
		drawJsssc.setDrawTimeLong(grabSelfSsc5.getDrawTimeLong());
		drawJsssc.setDrawNumber(grabSelfSsc5.getDrawNumber());
		for (int i = 0; i < 5; i++) {
			ReflectTool.set(drawJsssc, "setP" + (i + 1) + "Number", drawNumberStrs[i]);
			ReflectTool.set(drawJsssc, "setP" + (i + 1) + "NumberEn", NumberTool.getEn(drawNumberStrs[i]));
			ReflectTool.set(drawJsssc, "setP" + (i + 1) + "Size", JSSSCTool.size(drawNumbers[i]));
			ReflectTool.set(drawJsssc, "setP" + (i + 1) + "SizeEn", JSSSCTool.sizeEn(drawNumbers[i]));
			ReflectTool.set(drawJsssc, "setP" + (i + 1) + "Single", JSSSCTool.single(drawNumbers[i]));
			ReflectTool.set(drawJsssc, "setP" + (i + 1) + "SingleEn", JSSSCTool.singleEn(drawNumbers[i]));
		}
		drawJsssc.setDragonTiger(JSSSCTool.dragon(drawNumbers[0], drawNumbers[4]));
		drawJsssc.setDragonTigerEn(CQSSCTool.dragonEn(drawNumbers[0], drawNumbers[4]));

		int total = 0;
		for (int i = 0; i < 5; i++) {
			total += drawNumbers[i];
		}
		drawJsssc.setTotal(total);
		drawJsssc.setTopEn(NumberTool.getEn(total));
		drawJsssc.setTotalSingle(JSSSCTool.single(total));
		drawJsssc.setTotalSingleEn(JSSSCTool.singleEn(total));
		drawJsssc.setTotalSize(JSSSCTool.sizeTotal(total));
		drawJsssc.setTotalSizeEn(JSSSCTool.sizeTotalEn(total));
		drawJsssc.setTop(JSSSCTool.threeBalls(drawNumbers[0], drawNumbers[1], drawNumbers[2]));
		drawJsssc.setTopEn(JSSSCTool.threeBallsEn(drawJsssc.getTop()));
		drawJsssc.setCentre(JSSSCTool.threeBalls(drawNumbers[1], drawNumbers[2], drawNumbers[3]));
		drawJsssc.setCentreEn(JSSSCTool.threeBallsEn(drawJsssc.getCentre()));
		drawJsssc.setLater(JSSSCTool.threeBalls(drawNumbers[2], drawNumbers[3], drawNumbers[4]));
		drawJsssc.setLaterEn(JSSSCTool.threeBallsEn(drawJsssc.getLater()));
		drawJsssc.setCreateTime(new Date());
		drawJsssc.setCreateTimeLong(System.currentTimeMillis());
		drawJsssc.setUpdateTimeLong(System.currentTimeMillis());
		drawJsssc.setState(IbmStateEnum.OPEN.name());
		drawJsssc.setDesc(IpTool.getLocalIpList().toString());
		return drawJsssc;
	}
}
