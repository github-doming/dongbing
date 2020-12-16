package com.ibm.follow.servlet.client.core.cache;
import com.ibm.common.utils.game.GameUtil;

import java.util.HashMap;
import java.util.Map;
/**
 * @Author: Dongming
 * @Date: 2020-01-03 17:15
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class UseCacheData {
	private Map<GameUtil.Code, Long> gameSealTime;
	private double usedQuota;
	private Object period;

	public UseCacheData() {
		gameSealTime = new HashMap<>();
	}

	public double usedQuota() {
		return usedQuota;
	}
	public void usedQuota(double usedQuota) {
		this.usedQuota = usedQuota;
	}
	public Object period() {
		return period;
	}
	public void period(Object period) {
		this.period = period;
	}
	public Long sealTime(GameUtil.Code gameCode) {
		return gameSealTime.getOrDefault(gameCode,0L);
	}
	public void sealTime(GameUtil.Code gameCode, long sealTime) {
		gameSealTime.put(gameCode,sealTime);
	}

	public void refresh(double usedQuota, Object period) {
		this.usedQuota = usedQuota;
		this.period = period;
	}
}
