package com.ibm.common.test.zjj.handicap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: null
 * @Date: 2019-12-23 10:29
 * @Version: v1.0
 */
public class AgentGrab {
	class Member {
		private String name;
		private Agent parent;
		private boolean online;

		private Member(Agent parent, String name, boolean online) {
			this.name = name;
			this.parent = parent;
			this.online = online;
		}

		@Override public String toString() {
			return "Member{" + "name='" + name + '\'' + ", online=" + online + '}';
		}
	}

	class Agent {
		private String name;
		private List<Agent> subagent;
		private List<Member> submember;
		private Agent parent;

		private Agent(String name) {
			this(null, name);
		}

		private Agent(Agent parent, String name) {
			this.name = name;
			subagent = new ArrayList<>();
			submember = new ArrayList<>();
			this.parent = parent;
		}

		/**
		 * 通过代理名称找到代理对象
		 *
		 * @param name 代理名称
		 * @return 代理对象
		 */
		private Agent getAgent(String name) {
			if (this.name.equals(name)) {
				return this;
			}
			for (Agent agent : subagent) {
				Agent find = agent.getAgent(name);
				if (find != null) {
					return find;
				}
			}
			return null;
		}

		/**
		 * 将子会员放入到 子会员列表中
		 *
		 * @param member 子会员
		 * @return 当前代理对象
		 */
		public Agent member(Member member) {
			submember.add(member);
			return this;
		}

		/**
		 * 将子代理放入到 子会员列表中
		 *
		 * @param agent 子代理
		 * @return 当前代理对象
		 */
		public Agent agent(Agent agent) {
			subagent.add(agent);
			return this;
		}

		/**
		 * 指定代理的父代理
		 *
		 * @param parent 父代理
		 * @return 当前代理对象
		 */
		public Agent parent(Agent parent) {
			this.parent = parent;
			return this;
		}

		@Override public String toString() {
			return "Agent{" + "name='" + name + '\'' + ", subagent=" + subagent + ", submember=" + submember + '}';
		}
	}

	private Agent root;

	private Map<String, Member> members;
	private Map<String, Agent> agents;

	public AgentGrab(String name) {
		this.root = new Agent(name);
		members = new HashMap<>();
		agents = new HashMap<>();
	}

	/**
	 * 将json数据解析成代理树
	 *
	 * @param dataArr IDC 代理数据
	 * @return 当前抓取对象
	 */
	public AgentGrab analyze(JSONArray dataArr) {
		//创建未绑定代理临时map
		Map<String, Agent> unBind = new HashMap<>();
		for (int i = 0; i < dataArr.size(); i++) {
			JSONArray data = dataArr.getJSONArray(i);

			String subName = data.getString(0);
			String name = data.getString(1);
			int state = data.getInteger(3);
			int online = data.getInteger(4);

			//会员
			if (2 - state == 0) {
				Agent agent = root.getAgent(subName);
				//如果代理不在代理树中
				if (agent == null) {
					//去未绑定的代理map中查找
					if (unBind.containsKey(subName)) {
						agent = unBind.get(subName);
					} else {
						//创建一个代理 - 并放入到未绑定的代理map
						agent = new Agent(subName);
						unBind.put(subName, agent);
					}
				}
				//创建会员
				Member member = new Member(agent, name, online == 1);
				//将会员放入父代理的会员列表中
				agent.member(member);

				//将会员放入会员列表中
				members.put(name, member);
			}
			//代理
			else if (1 - state == 0) {
				Agent subagent = root.getAgent(subName);
				//如果父代理不在代理树中
				if (subagent == null) {
					//去未绑定的代理map中查找
					if (unBind.containsKey(subName)) {
						subagent = unBind.get(subName);
					} else {
						//创建一个代理 - 并放入到未绑定的代理map
						subagent = new Agent(subName);
						unBind.put(subName, subagent);
					}
				}
				Agent agent;
				//去未绑定的代理map中查找
				if (unBind.containsKey(name)) {
					//找到组织，不属于未绑定
					agent = unBind.remove(name);
					//放入组织
					agent.parent(subagent);
				} else {
					//创建一个代理
					agent = new Agent(subagent, name);
				}
				//将代理放入父代理的代理列表中
				subagent.agent(agent);

				//将会员放入代理列表中
				agents.put(name, agent);
			}
		}
		//存在一个最终的未绑定代理 - 那么他就是终极节点
		if (unBind.size() == 1) {
			for (Agent agent : unBind.values()) {
				root = agent;

				//将会员放入代理列表中
				agents.put(agent.name, agent);
			}
		}
		return this;
	}

	/*
		1.获取指定会员的投注信息
		2.获取指定代理下所有会员的投注信息
		3.获取所有会员的投注信息
	 */


	@Override public String toString() {
		return root.toString();
	}

	public static void main(String[] args) {
		String jsonStr = "{\"code\":\"0\",\"msg\":\"\",\"ticket\":\"\",\"data\":[[\"ah77161\",\"ah1881\",\"ff\",2,1],[\"ahh559\",\"ah1882\",\"ff\",2,1],[\"ah77161\",\"ah77151\",\"151\",2,0],[\"ah77161\",\"ah77152\",\"152\",2,0],[\"ahh559\",\"ah77161\",\"66\",1,1],[\"ahh559\",\"ah881\",\"881\",2,1]]}";
		JSONObject json = JSON.parseObject(jsonStr);
		AgentGrab agent = new AgentGrab("ah8389");
		agent.analyze(json.getJSONArray("data"));

		System.out.println(agent);

	}

}
