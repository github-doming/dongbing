package all.cms.msg.action;
import java.util.Map;

import all.app.common.action.AppAction;
import all.cms.msg.service.CmsMsgService;
import all.cms.msg.service.CmsMsgTopicService;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.log.LogThreadLocal;
public class CmsMsgTopicListAction extends AppAction {
	@Override
	public String run() throws Exception {
		JsonTcpBean jrb = new JsonTcpBean();
		try {
			if (this.isFindJson()) {
			} else {
				jrb.setCode(ReturnCodeEnum.app400JSON.toString());
				jrb.setMsg(ReturnCodeEnum.app400JSON.getMsgCn());
				jrb.setCodeSys(ReturnCodeEnum.code400.toString());
				jrb.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
				jrb.setSuccess(false);
				return this.returnJson(jrb);
			}
			this.findAppUserByJsonParameter();
			if (appUserT == null) {
				jrb = LogThreadLocal.findLog();
				return this.returnJson(jrb);
			}
			CmsMsgTopicService cmsMsgTopicService = new CmsMsgTopicService();
			CmsMsgService cmsMsgService = new CmsMsgService(); // pageIndex
			// pageSize
			String pageIndex = BeanThreadLocal.find(dataMap.get("pageIndex"), "");
			String pageSize = BeanThreadLocal.find(dataMap.get("pageSize"), "");
			String appUserId = appUserT.getAppUserId();
			Integer pageIndexInt = BeanThreadLocal.find(pageIndex, 1);
			Integer pageSizeInt = BeanThreadLocal.find(pageSize, 10);
			PageCoreBean<Map<String, Object>> page = cmsMsgTopicService.findAll(appUserId, pageIndexInt, pageSizeInt);
			// 返回json
			// 未读消息数量(总条数)
			String cnt = cmsMsgService.findStateNewCntTotalByCmsBoxMsg(appUserId);
			jrb.setTotal(Integer.parseInt(cnt));
			jrb.setData(page);
			jrb.setCodeSys(ReturnCodeEnum.code200.toString());
			jrb.setMsgSys(ReturnCodeEnum.code200.getMsgCn());
			jrb.setSuccess(true);
			return this.returnJson(jrb);
		} catch (Exception e) {
			e.printStackTrace();
			jrb.setCodeSys(ReturnCodeEnum.code500.toString());
			jrb.setMsgSys(ReturnCodeEnum.code500.getMsgCn());
			jrb.setSuccess(false);
			this.returnJson(jrb);
			throw e;
		}
	}
}
