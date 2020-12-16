package org.doming.develop.mq.bean;
/**
 * @Description: 消息队列路由
 * @Author: cjx
 * @Author: Dongming
 * @Date: 2018-11-14 18:41
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 * @Copyright © 2018-2018 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public class MqRouting {

	private String id;
	private String key;
	private String desc;


	// -- 下面的方法不再更新 --//

	public String getId() {
		return id;
	}
	public MqRouting setId(String id) {
		if (id != null) {
			this.id = id;
		}
		return this;
	}

	public String getKey() {
		return key;
	}
	public MqRouting setKey(String key) {
		if (key != null) {
			this.key = key;
		}
		return this;
	}
	public String getDesc() {
		return desc;
	}
	public MqRouting setDesc(String desc) {
		if (desc != null) {
			this.desc = desc;
		}
		return this;
	}

	// -- 下面的方法不再更新 --//

}
