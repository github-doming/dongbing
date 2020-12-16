package com.ibm.old.v1.common.zjj.test;
import c.a.util.core.test.CommTest;
import com.ibm.old.v1.cloud.ibm_cms_message_content.t.entity.IbmCmsMessageContentT;
import com.ibm.old.v1.cloud.ibm_cms_message_content.t.service.IbmCmsMessageContentTService;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import org.doming.core.tools.DateTool;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;
/**
 * @Description:
 * @Author: zjj
 * @Date: 2019-06-21 16:21
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class IntTest extends CommTest {

	@Test
	public void test02() throws ParseException {



	}
	@Test
	public void test01() throws Exception {
		String gameCode = IbmGameEnum.PK10.name();
		//获取游戏code
		IbmGameEnum game = IbmGameEnum.valueOf(gameCode);

		String period=PeriodTool.findPeriod(gameCode).toString();

		//获取日期字符串
		String date;
		switch (game) {
			case PK10:
				date = DateTool.getDate(new Date());
				break;
			case XYFT:
				String[] str = period.split("-");
				StringBuilder stringBuilder = new StringBuilder(str[0]);
				stringBuilder.insert(6, "-");
				stringBuilder.insert(4, "-");
				date = stringBuilder.toString();
				period=str[0].concat(str[1]);
				break;
			default:
				throw new Exception("获取日期异常");
		}
		System.out.println(date);
		System.out.println(period);
	}




	@Test
	public void demo() {
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			String gameCode = IbmGameEnum.PK10.name();
			String period= PeriodTool.findPeriod(gameCode).toString();
			System.out.println(period);

			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}

	}
	private void hmInfo() throws Exception {
		IbmCmsMessageContentTService messageContentTService=new IbmCmsMessageContentTService();
		IbmCmsMessageContentT messageContentT=new IbmCmsMessageContentT();
		messageContentT.setReceiveUserId("5852284e40ba40e1bd29052f4fcb7259");
		messageContentT.setTitle("盘口【WS2】账号【123】消息");
		messageContentT.setContent("z1231321");
		messageContentT.setReplyState(IbmStateEnum.PROCESS.name());
		messageContentT.setReadingState(IbmStateEnum.UNREAD.name());
		messageContentT.setKeyword(IbmStateEnum.LOGOUT_EXCEPTION.getMsgCn());
		messageContentT.setMessageType("系统消息");
		messageContentT.setCreateTime(new Date());
		messageContentT.setCreateTimeLong(messageContentT.getCreateTime().getTime());
		messageContentT.setUpdateTime(new Date());
		messageContentT.setUpdateTimeLong(messageContentT.getUpdateTime().getTime());
		messageContentT.setState(IbmStateEnum.OPEN.name());
		messageContentTService.save(messageContentT);
	}
}
