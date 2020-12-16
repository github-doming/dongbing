package org.doming.example.design.pattern.factory.entry;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-11-27 15:01
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class AppleHeadset extends Headset {
	@Override void play() {
		// Apple 耳机播放逻辑 ...
		System.out.println("Apple 耳机播放完成");
	}
}
