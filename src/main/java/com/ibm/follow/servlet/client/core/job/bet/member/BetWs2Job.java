package com.ibm.follow.servlet.client.core.job.bet.member;
import com.ibm.common.utils.game.GameUtil;
import org.doming.core.common.quartz.BaseCommJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
/**
 * @Description: 投注进WS2工作类
 * @Author: Dongming
 * @Date: 2019-09-16 14:19
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class BetWs2Job extends BaseCommJob {
	private String existHmId;
	private GameUtil.Code gameCode;
	private Object period;
	private Object hmBetId;
	@Override public void executeJob(JobExecutionContext context) {
		// TODO: 2019/9/12 定时投注
		if (context != null){
			JobDataMap map = context.getMergedJobDataMap();
			existHmId = map.getString("existHmId");
			gameCode = (GameUtil.Code) map.get("gameCode");
			period = map.get("period");
		}
		execute();
	}

	public void execute() {
		if (hmBetId != null){
			// TODO: 2019/9/16 手动投注

			return;
		}
		// TODO: 2019/9/16 跟随投注投注

	}

	/**
	 * 跟随投注
	 *
	 * @param existHmId 存在盘口会员主键
	 * @param gameCode  游戏code
	 * @param period    期数
	 * @return 当前对象
	 */
	public BetWs2Job followBet(String existHmId, GameUtil.Code gameCode, Object period) {
		this.existHmId = existHmId;
		this.gameCode = gameCode;
		this.period = period;
		return this;
	}
	/**
	 * @param existHmId 存在盘口会员主键
	 * @param gameCode  游戏code
	 * @param period    期数
	 * @param hmBetId   投注信息主键
	 * @return 当前对象
	 */
	public BetWs2Job manualBet(String existHmId, GameUtil.Code gameCode, Object period, String hmBetId) {
		this.existHmId = existHmId;
		this.gameCode = gameCode;
		this.period = period;
		this.hmBetId = hmBetId;
		return this;
	}
}
