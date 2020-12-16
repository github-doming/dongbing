package com.ibs.plan.connector.admin.manage.client;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_client.service.IbspClientService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @Description: 获取所有客户机信息
 * @Author: null
 * @Date: 2020-03-24 16:17
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/client/list")
public class ClientListAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
        String ip = StringTool.getString(dataMap,"ip","");
        String clientCode = StringTool.getString(dataMap,"clientCode","");

        // 分页
        Integer pageIndex = NumberTool.getInteger(dataMap.get("pageIndex"), 0);
        Integer pageSize = NumberTool.getInteger(dataMap.get("pageSize"), 15);
		Map<String, Object> data = new HashMap<>(5);
		try{

            //获取所有客户机
            PageCoreBean basePage = new IbspClientService().listShow(ip,clientCode,pageIndex,pageSize);

            data.put("ip", ip);
            data.put("clientCode",clientCode);
			data.put("rows", basePage.getList());
			data.put("index", pageIndex);
			data.put("total", basePage.getTotalCount());
        } catch (Exception e) {
            log.error("客户机信息列表错误", e);
			data.put("rows", null);
			data.put("index", 0);
			data.put("total", 0);
        }
        return data;
    }
}
