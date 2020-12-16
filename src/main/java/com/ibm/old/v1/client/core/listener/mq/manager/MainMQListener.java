package com.ibm.old.v1.client.core.listener.mq.manager;
import com.ibm.old.v1.client.core.controller.ClientExecutor;
import com.ibm.old.v1.client.core.controller.manage.CheckLoginController;
import com.ibm.old.v1.client.core.controller.manage.HmMqCloseController;
import com.ibm.old.v1.client.core.controller.manage.HmMqOpenController;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.core.MessageReceipt;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmMethodEnum;
import net.sf.json.JSONObject;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
/**
 * @Description: mq调度控制类 RPC
 * 校验是否能否在本服务器开启队列
 * @Author: Dongming
 * @Date: 2018-11-13 16:46	2019-3-7 11:22
 * @Email: job.dongming@foxmail.com
 * @Version: v0.2
 */
public class MainMQListener extends BaseCommMq implements MessageReceipt {

	private JsonResultBeanPlus bean = new JsonResultBeanPlus();

	private JSONObject msgObj;
	private IbmMethodEnum method;

	//FIXME 初步完成构想，具体实际效果需要进行验证后才能知道，需要调整
	@Override public String execute(String message) throws Exception {
		log.trace("调度控制，接收的消息是：" + message);

		if (!valiParameter(message)) {
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return returnJson(bean);
		}
		try {
			ClientExecutor execute = null;
			switch (method) {
				// 盘口会员开启一个mq链接
				case OPEN:
					execute = new HmMqOpenController();
					break;
				// 盘口会员关闭一个mq链接
				case CLOSE:
					execute = new HmMqCloseController();
					break;
				//检验登录
				case CHECK_LOGIN:
					execute = new CheckLoginController();
					break;
				default:
					bean.putEnum(IbmCodeEnum.IBM_404_NOT_CAPTURE_METHOD);
					bean.putSysEnum(IbmCodeEnum.CODE_404);
			}
			if (execute != null) {
				CurrentTransaction.transactionEnd();
				bean = execute.execute(msgObj);
				CurrentTransaction.transactionBegin();
			}
			if (bean.isSuccess()) {
				log.trace(method.getMsgCn() + "队列成功");
			} else {
				log.error(method.getMsgCn() + "队列失败," + bean.getMessage());
			}
			log.trace("调度控制，返回的结果是：" + returnJson(bean));
			return returnJson(bean);
		} catch (Exception e) {
			log.error("mq调度控制类出错" + this.getClass(), e);
			throw e;
		}
	}

	@Override protected boolean valiParameter(String message) {

		if (StringTool.isEmpty(message)) {
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_MESSAGE);
			return false;
		}
		msgObj = JSONObject.fromObject(message);
		if (ContainerTool.isEmpty(msgObj)) {
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			return false;
		}
		method = IbmMethodEnum.getMethod(msgObj.get("method"));
		if (method == null) {
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_METHOD);
			return false;
		}
		return true;
	}

}
