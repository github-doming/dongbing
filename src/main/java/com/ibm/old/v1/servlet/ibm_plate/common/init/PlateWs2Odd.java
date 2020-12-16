package com.ibm.old.v1.servlet.ibm_plate.common.init;

import c.a.util.core.test.CommTest;
import com.ibm.old.v1.servlet.ibm_plate.plate_ws2_odd.t.entity.PlateWs2OddT;
import com.ibm.old.v1.servlet.ibm_plate.service.Ws2PlateService;
import org.junit.Test;

import java.util.Date;

public class PlateWs2Odd extends CommTest {

	@Test
	public void demo() {
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
//			bothSides();
//			ballNO();
			sumDT();
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}
	}

	private void sumDT() throws Exception {
		Ws2PlateService ws2PlateService = new Ws2PlateService();
		PlateWs2OddT plateWs2OddT = new PlateWs2OddT();
		
		String[] ranks={"冠军","亚军","第三名","第四名","第五名","第六名","第七名","第八名","第九名","第十名"};
		String[] plays={"大","小","单","双","龙","虎"};
		String gamePlayName = "冠亚和|冠亚双";
		String gamePlayCode = "0361";
		String gamePlayType = "sumDT";

		plateWs2OddT.setGameplayName(gamePlayName);
		plateWs2OddT.setGameplayCode(gamePlayCode);
		plateWs2OddT.setOddsT(2150);
		plateWs2OddT.setGameCode("XYFT");
		plateWs2OddT.setGameplayType(gamePlayType);
		plateWs2OddT.setCreateUser("doming");
		plateWs2OddT.setCreateTime(new Date());
		plateWs2OddT.setCreateTimeLong(plateWs2OddT.getCreateTime().getTime());
		plateWs2OddT.setState("OPEN");
		ws2PlateService.save(plateWs2OddT);


	
		
	}

	private void ballNO() throws Exception {
		
		Ws2PlateService ws2PlateService = new Ws2PlateService();
		PlateWs2OddT plateWs2OddT = new PlateWs2OddT();
		
		String[] ranks={"冠军","亚军","第三名","第四名","第五名","第六名","第七名","第八名","第九名","第十名"};
		String[] plays={"大","小","单","双","龙","虎"};
		String gamePlayName = "|";
		
		String gamePlayType = "ballNO";
		Long code=(long) 301;
//		for(String str:ranks){
//				str=str.concat(gamePlayName).concat(plays[5]);					
//				plateWs2OddT.setGameplayName(str);
//				plateWs2OddT.setGameplayCode(gamePlayCode.concat(code.toString()));
//				plateWs2OddT.setOddsT(1983);
//				plateWs2OddT.setGameCode("XYFT");
//				plateWs2OddT.setGameplayType(gamePlayType);
//				plateWs2OddT.setCreateUser("doming");
//				plateWs2OddT.setCreateTime(new Date());
//				plateWs2OddT.setCreateTimeLong(plateWs2OddT.getCreateTime().getTime());
//				plateWs2OddT.setState("OPEN");
//				ws2PlateService.save(plateWs2OddT);
//			    code=code+10;
//		}
		String str;
		Integer integer=1;	
		String gamePlayCode = "009";
		for (int i = 0; i < ranks.length; i++) {

			str = ranks[9].concat(gamePlayName).concat(integer.toString());
			plateWs2OddT.setGameplayName(str);
			plateWs2OddT.setGameplayCode(gamePlayCode.concat(integer.toString()));
			plateWs2OddT.setOddsT(9912);
			plateWs2OddT.setGameCode("XYFT");
			plateWs2OddT.setGameplayType(gamePlayType);
			plateWs2OddT.setCreateUser("doming");
			plateWs2OddT.setCreateTime(new Date());
			plateWs2OddT.setCreateTimeLong(plateWs2OddT.getCreateTime().getTime());
			plateWs2OddT.setState("OPEN");
			ws2PlateService.save(plateWs2OddT);
			integer = integer + 1;


		}
		
		
	}

	private void bothSides() throws Exception {
		Ws2PlateService ws2PlateService = new Ws2PlateService();
		PlateWs2OddT plateWs2OddT = new PlateWs2OddT();

		/**
		 * 0100 0110
		 * 0101 0111
		 * 0200 0210
		 * 0201 0211
		 * 0300 0310
		 * 0301 0311
		 */
		String[] ranks={"冠军","亚军","第三名","第四名","第五名","第六名","第七名","第八名","第九名","第十名"};
		String[] types={"大小","单双","龙虎"};
		String[] plays={"大","小","单","双","龙","虎"};
		
		String gamePlayName = "|";
		String gamePlayCode = "0";
		String gamePlayType = "bothSides";
		
		if("bothSides".equals(gamePlayType)){
			Long code=(long) 301;
			String str;
			for(int i=0;i<ranks.length;i++){
				str=ranks[i];
			
				gamePlayName=str.concat(gamePlayName).concat(types[2]).concat(gamePlayName).concat(plays[5]);
				
				plateWs2OddT.setGameplayName(str);
				plateWs2OddT.setGameplayCode(gamePlayCode.concat(code.toString()));
				plateWs2OddT.setOddsT(1983);
				plateWs2OddT.setGameCode("XYFT");
				plateWs2OddT.setGameplayType(gamePlayType);
				plateWs2OddT.setCreateUser("doming");
				plateWs2OddT.setCreateTime(new Date());
				plateWs2OddT.setCreateTimeLong(plateWs2OddT.getCreateTime().getTime());
				plateWs2OddT.setState("OPEN");
				ws2PlateService.save(plateWs2OddT);
								
				code=code+10;
				
			}
						
		}
	
	}
}
