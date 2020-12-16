package com.ibm.follow.servlet.module.tools;

import com.ibm.follow.servlet.module.event.*;
import org.doming.core.tools.AssertTool;
import org.doming.core.tools.StringTool;
import org.doming.core.tools.ThreadTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 事件模块工具类
 *
 * @Author: Dongming
 * @Date: 2019-12-24 10:45
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class EventDriver {

	private EventControl controlCenter;
	private ScheduledThreadPoolExecutor eventScheduledExecutor;
	private static final EventDriver INSTANCE = new EventDriver();

	private EventDriver() {
		this.controlCenter = new EventControl();
	}

	private static EventControl eventControl() {
		return INSTANCE.controlCenter;
	}

	private static void eventControlClear() {
		INSTANCE.controlCenter = null;
	}

	private static ScheduledExecutorService eventScheduledExecutor() {
		return INSTANCE.eventScheduledExecutor;
	}

	private static void eventScheduledExecutor(ScheduledThreadPoolExecutor eventScheduledExecutor) {
		INSTANCE.eventScheduledExecutor = eventScheduledExecutor;
	}

	public static EventDriver register() {
		return INSTANCE;
	}

	/**
	 * 初始化事件模块
	 * <p>1.启动定时校验事件主线程</p>
	 * <p>2.5s后启动，每次运行间隔时间 IbmMainConfig.EVENT_DELAY</p>
	 */
	public static void init(int delay) {
		AssertTool.isNull(eventScheduledExecutor(), "加载事件模块错误，事件调度器不为空");
        ScheduledThreadPoolExecutor eventScheduledExecutor = ThreadTool.createScheduledExecutor("event", 1);
		eventScheduledExecutor
				.scheduleWithFixedDelay(new MasterCheckEvent(), 5, delay, TimeUnit.SECONDS);
		eventScheduledExecutor(eventScheduledExecutor);
	}


	/**
	 * 销毁
	 *
	 * @throws InterruptedException 销毁线程池出现错误
	 */
	public static void destroy() throws InterruptedException {
		/*
			1.关闭所有正在运行的线程
			2.关闭所有的线程池
			3.关闭定时器主线程池
			4.置空所有的对象
		 */
		eventControl().destroy();
		Code.Type.destroy();
		ThreadTool.close(eventScheduledExecutor());
		eventScheduledExecutor(null);
		eventControlClear();
	}

	/**
	 * 事件编码转换为枚举类型
	 *
	 * @param eventCode 事件编码
	 * @return 编码枚举类型
	 */
	public Code valueCodeOf(String eventCode) {
		if (StringTool.isEmpty(eventCode)) {
			return null;
		}
		return Code.valueOf(eventCode);
	}

	//region 事件操作

	/**
	 * 获取正在运行的 事件数量
	 *
	 * @param code 查询事件编码
	 * @return 正在运行的数量
	 */
	public int getEventRunCount(Code code) {
		return eventControl().getEventRunCount(code);
	}

	/**
	 * 移除多余的事件线程
	 *
	 * @param code  事件编码
	 * @param index 事件在线程中的索引
	 */
	public void removeEvent(Code code, Integer index) {
		ControlEventThread eventThread = eventControl().removeEvent(code, index);
		eventThread.stop();
		eventThread.setExecutor(null);
	}

	/**
	 * 移除多余的事件线程
	 *
	 * @param code        事件编码
	 * @param eventThread 事件在线程中的索引
	 */
	public void removeEvent(Code code, ControlEventThread eventThread) {
		if (eventControl() == null) {
			return;
		}
		eventControl().removeEvent(code, eventThread);
		eventThread.stop();
		eventThread.setExecutor(null);
	}

	/**
	 * 添加事件线程
	 *
	 * @param code 事件编码
	 */
	public void addEvent(Code code) {
		ThreadPoolExecutor executor = code.getExecutor();
		ControlEventThread cet = code.getEventThread();
		cet.setExecutor(executor);
		executor.execute(cet);
		eventControl().addEvent(code, cet);
	}
	//endregion

	//region 事件控制

	static class EventControl {
		private Map<EventDriver.Code, List<ControlEventThread>> eventCenter;

		public EventControl() {
			eventCenter = new HashMap<>(5);
		}

		public int getEventRunCount(EventDriver.Code code) {
			return eventCenter.containsKey(code) ? eventCenter.get(code).size() : 0;
		}

		/**
		 * 移除事件线程
		 *
		 * @param code  事件编码
		 * @param index 事件在线程中的索引
		 * @return 当前被移除的对象
		 */
		public ControlEventThread removeEvent(EventDriver.Code code, int index) {
			return eventCenter.get(code).remove(index);
		}

		/**
		 * 移除事件线程
		 *
		 * @param code        事件编码
		 * @param eventThread 当前被移除的对象
		 * @return 移除结果
		 */
		public boolean removeEvent(EventDriver.Code code, ControlEventThread eventThread) {
			return eventCenter.get(code).remove(eventThread);
		}

		/**
		 * 添加事件线程
		 *
		 * @param code  事件编码
		 * @param event 事件线程
		 */
		public void addEvent(EventDriver.Code code, ControlEventThread event) {
			if (eventCenter.containsKey(code)) {
				eventCenter.get(code).add(event);
			} else {
				List<ControlEventThread> eventCenters = new ArrayList<>();
				eventCenters.add(event);
				eventCenter.put(code, eventCenters);
			}
		}

		/**
		 * 销毁
		 */
		public void destroy() {
			if (eventCenter == null) {
				return;
			}
			for (Map.Entry<Code, List<ControlEventThread>> entry : eventCenter.entrySet()) {
				for (ControlEventThread eventThread : entry.getValue()) {
					eventThread.stop();
					eventThread.setExecutor(null);
				}
			}
			eventCenter = null;
		}
	}
	//endregion

	//region 事件编码

	public enum Code {
		/**
		 * 事件编码
		 */
		VALI_LOGIN {
			@Override
			public ControlEventThread getEventThread() {
				return new LoginValiControlThread();
			}
		}, LOGIN {
			@Override
			public ControlEventThread getEventThread() {
				return new LoginControlThread();
			}
		}, SET_CONFIG {
			@Override
			public ControlEventThread getEventThread() {
				return new ConfigSetControlThread();
			}
		}, OPEN_CLIENT {
			@Override
			public ControlEventThread getEventThread() {
				return new ClientOpenControlThread();
			}
		}, CLOSE_CLIENT {
			@Override
			public ControlEventThread getEventThread() {
				return new ClientCloseControlThread();
			}
		}, INFO_CONFIG {
			@Override
			public ControlEventThread getEventThread() {
				return new ConfigInfoControlThread();
			}
		}, SNATCH_LOGIN {
			@Override
			public ControlEventThread getEventThread() {
				return null;
			}
		};

		public ThreadPoolExecutor getExecutor() {
			return getType().getExecutor();
		}

		/**
		 * 获取事件编码的类别
		 *
		 * @return 类别
		 */
		public Type getType() {
			switch (this) {
				case VALI_LOGIN:
				case LOGIN:
					return Type.LOGIN;
				case SET_CONFIG:
					return Type.SET;
				case OPEN_CLIENT:
				case CLOSE_CLIENT:
					return Type.MANAGER;
				case INFO_CONFIG:
				case SNATCH_LOGIN:
				default:
					return Type.OTHER;
			}
		}

		public abstract ControlEventThread getEventThread();

		//region  事件和线程池的类别

		enum Type {
			/**
			 * 类型
			 */
			LOGIN(15), SET(30), MANAGER(10), OTHER(5);
			/**
			 * 线程池大小
			 */
			private int corePoolSize;

			Type(int corePoolSize) {
				this.corePoolSize = corePoolSize;
			}

			//region 线程池

			private final static Map<Type, ThreadPoolExecutor> EXECUTOR_MAP = new HashMap<>(5);

			/**
			 * 获取线程池执行器
			 */
			private ThreadPoolExecutor getExecutor() {
				if (!EXECUTOR_MAP.containsKey(this)) {
					ThreadPoolExecutor executor = ThreadTool.createExecutor(this.name(), this.corePoolSize);
					EXECUTOR_MAP.put(this, executor);
				}
				return EXECUTOR_MAP.get(this);
			}

			public static void destroy() throws InterruptedException {
				for (Map.Entry<Type, ThreadPoolExecutor> entry : EXECUTOR_MAP.entrySet()) {
					ThreadTool.close(entry.getValue());
				}
				EXECUTOR_MAP.clear();
			}
			//endregion

		}
		//endregion

	}
	//endregion

}
