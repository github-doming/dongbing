package org.doming.develop.mq.bean;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.ArrayList;
import java.util.List;
/**
 * @Description: 消息队列接收端
 * @Author: cjx
 * @Author: Dongming
 * @Date: 2018-11-14 18:36
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 * @Copyright © 2018-2018 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public class MqReceiveClient {
	private String id;
	private String name;
	private Integer size;
	private String className;

	private List<MqRouting> routingList;

	// -- 下面的方法不再更新 --//

	public String getId() {
		return id;
	}
	public MqReceiveClient setId(String id) {
		if (id != null) {
			this.id = id;
		}
		return this;
	}
	public String getName() {
		return name;
	}
	public MqReceiveClient setName(String name) {
		if (name != null) {
			this.name = name;
		}
		return this;
	}
	public Integer getSize() {
		return size;
	}
	public MqReceiveClient setSize(Object size) {
		if (size == null){
			this.size = 1;
		}else {
			if (size instanceof Integer){
				this.size = (Integer) size;
			}else{
				this.size = Integer.parseInt(size.toString());
			}
		}
		return this;
	}
	public String getClassName() {
		return className;
	}
	public MqReceiveClient setClassName(String className) {
		if (className != null) {
			this.className = className;
		}
		return this;
	}
	public List<MqRouting> getRoutingList() {
		return routingList;
	}

	public MqReceiveClient setRoutingList(List<MqRouting> routingList) {
		if (routingList != null) {
			this.routingList = routingList;
		}
		return this;
	}

	public void setRouting(MqRouting routing) {
		if (getRoutingList() == null) {
			setRoutingList(new ArrayList<>());
		}
		getRoutingList().add(routing);
	}

	// -- 上面的方法不再更新 --//

	/**
	 * 获取路由键数组
	 * @param routingName 路由name
	 * @return 路由键数组
	 */
	public String[] getRoutingKeys(String routingName) {
		String[] routingKeys = null;
		if (ContainerTool.notEmpty(routingList)) {
			routingKeys = new String[routingList.size()];
			for (int i = 0; i < routingList.size(); i++) {
				if (StringTool.isEmpty(routingName)){
					routingKeys[i] = routingList.get(i).getKey();
				}else{
					routingKeys[i] = String.format(routingList.get(i).getDesc(),routingName);
				}
			}
		}
		return routingKeys;
	}
}
