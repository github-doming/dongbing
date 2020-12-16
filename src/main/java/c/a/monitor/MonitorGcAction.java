package c.a.monitor;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.CommAction;
public class MonitorGcAction extends CommAction {
	public MonitorGcAction() {
		this.database = false;
		this.transaction = false;
	}
	@Override
	public String execute() throws Exception {
		/**
		 * 垃圾回收
		 */
		System.gc();
		return CommViewEnum.Default.toString();
	}
}
