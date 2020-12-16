package com.ibm.old.v1.servlet.ibm_plate.common.test;
import c.a.util.core.test.CommTest;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.servlet.ibm_plate.plate_ws2_odd.t.entity.PlateWs2OddT;
import com.ibm.old.v1.servlet.ibm_plate.service.Ws2PlateService;
import org.junit.Test;

import java.util.Date;
/**
 * @Description: 导入ws2盘口赔率
 * @Author: Dongming
 * @Date: 2018-10-20 10:50
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ImportWs2PlateTest extends CommTest {
	private String[] rank = {"第一名", "第二名", "第三名", "第四名", "第五名", "第六名", "第七名", "第八名", "第九名", "第十名"};
	private String[] type = {"号码", "大小","单双", "龙虎"};
	private String[] size = {"大", "小"};
	private String[] sop = {"单", "双"};
	private String[] dor = {"龙", "虎"};

	@Test public void test() {
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			initWs2Odd();
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}
	}
	private void initWs2Odd() throws Exception {

		Ws2PlateService plateService = new Ws2PlateService();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				PlateWs2OddT ws2OddT = new PlateWs2OddT();
				ws2OddT.setGameplayName(rank[i] + "_" + type[0] + "_" + j);
				ws2OddT.setGameplayCode("00" + i + j);
				ws2OddT.setOddsT(9912);
				ws2OddT.setGameCode("PK10");
				ws2OddT.setGameplayType("ballNO");
				ws2OddT.setCreateTime(new Date());
				ws2OddT.setCreateTimeLong(ws2OddT.getCreateTime().getTime());
				ws2OddT.setUpdateTime(new Date());
				ws2OddT.setUpdateTimeLong(ws2OddT.getUpdateTime().getTime());
				ws2OddT.setState(IbmStateEnum.OPEN.name());
				plateService.save(ws2OddT);
			}
		}

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 2; j++) {
				PlateWs2OddT ws2OddT = new PlateWs2OddT();
				ws2OddT.setGameplayName(rank[i] + "_" + type[1] + "_" + size[j]);
				ws2OddT.setGameplayCode("01" + i + j);
				ws2OddT.setOddsT(1983);
				ws2OddT.setGameCode("PK10");
				ws2OddT.setGameplayType("bothSides");
				ws2OddT.setCreateTime(new Date());
				ws2OddT.setCreateTimeLong(ws2OddT.getCreateTime().getTime());
				ws2OddT.setUpdateTime(new Date());
				ws2OddT.setUpdateTimeLong(ws2OddT.getUpdateTime().getTime());
				ws2OddT.setState(IbmStateEnum.OPEN.name());
				plateService.save(ws2OddT);
			}
		}

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 2; j++) {
				PlateWs2OddT ws2OddT = new PlateWs2OddT();
				ws2OddT.setGameplayName(rank[i] + "_" + type[2] + "_" + sop[j]);
				ws2OddT.setGameplayCode("02" + i + j);
				ws2OddT.setOddsT(1983);
				ws2OddT.setGameCode("PK10");
				ws2OddT.setGameplayType("bothSides");
				ws2OddT.setCreateTime(new Date());
				ws2OddT.setCreateTimeLong(ws2OddT.getCreateTime().getTime());
				ws2OddT.setUpdateTime(new Date());
				ws2OddT.setUpdateTimeLong(ws2OddT.getUpdateTime().getTime());
				ws2OddT.setState(IbmStateEnum.OPEN.name());
				plateService.save(ws2OddT);
			}
		}

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 2; j++) {
				PlateWs2OddT ws2OddT = new PlateWs2OddT();
				ws2OddT.setGameplayName(rank[i] + "_" + type[3] + "_" + dor[j]);
				ws2OddT.setGameplayCode("03" + i + j);
				ws2OddT.setOddsT(1983);
				ws2OddT.setGameCode("PK10");
				ws2OddT.setGameplayType("bothSides");
				ws2OddT.setCreateTime(new Date());
				ws2OddT.setCreateTimeLong(ws2OddT.getCreateTime().getTime());
				ws2OddT.setUpdateTime(new Date());
				ws2OddT.setUpdateTimeLong(ws2OddT.getUpdateTime().getTime());
				ws2OddT.setState(IbmStateEnum.OPEN.name());
				plateService.save(ws2OddT);
			}
		}
		for (int i = 3; i < 20; i++) {
			PlateWs2OddT ws2OddT = new PlateWs2OddT();
			ws2OddT.setGameplayName("冠亚和_" + type[0] + "_" + i);
			ws2OddT.setGameplayCode("037" + i);
			ws2OddT.setOddsT(9912);
			ws2OddT.setGameCode("PK10");
			ws2OddT.setGameplayType("sumDT");
			ws2OddT.setCreateTime(new Date());
			ws2OddT.setCreateTimeLong(ws2OddT.getCreateTime().getTime());
			ws2OddT.setUpdateTime(new Date());
			ws2OddT.setUpdateTimeLong(ws2OddT.getUpdateTime().getTime());
			ws2OddT.setState(IbmStateEnum.OPEN.name());
			plateService.save(ws2OddT);
		}

		for (int i = 0; i < 2; i++) {
			PlateWs2OddT ws2OddT = new PlateWs2OddT();
			ws2OddT.setGameplayName("冠亚和_" + "_" + type[1] + "_" + size[i]);
			ws2OddT.setGameplayCode("035" + i);
			ws2OddT.setOddsT(1774);
			ws2OddT.setGameCode("PK10");
			ws2OddT.setGameplayType("sumDT");
			ws2OddT.setCreateTime(new Date());
			ws2OddT.setCreateTimeLong(ws2OddT.getCreateTime().getTime());
			ws2OddT.setUpdateTime(new Date());
			ws2OddT.setUpdateTimeLong(ws2OddT.getUpdateTime().getTime());
			ws2OddT.setState(IbmStateEnum.OPEN.name());
			plateService.save(ws2OddT);

		}
		for (int i = 0; i < 2; i++) {
			PlateWs2OddT ws2OddT = new PlateWs2OddT();
			ws2OddT.setGameplayName("冠亚和_" + "_" + type[2] + "_" + sop[i]);
			ws2OddT.setGameplayCode("036" + i);
			ws2OddT.setOddsT(1774);
			ws2OddT.setGameCode("PK10");
			ws2OddT.setGameplayType("sumDT");
			ws2OddT.setCreateTime(new Date());
			ws2OddT.setCreateTimeLong(ws2OddT.getCreateTime().getTime());
			ws2OddT.setUpdateTime(new Date());
			ws2OddT.setUpdateTimeLong(ws2OddT.getUpdateTime().getTime());
			ws2OddT.setState(IbmStateEnum.OPEN.name());
			plateService.save(ws2OddT);

		}

	}

}
