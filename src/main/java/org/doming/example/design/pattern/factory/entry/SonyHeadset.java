package org.doming.example.design.pattern.factory.entry;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-11-27 15:01
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SonyHeadset extends Headset {
	@Override void play() {
		// Sony 耳机播放逻辑...
		System.out.println("Sony 耳机播放完成");

	}
}
