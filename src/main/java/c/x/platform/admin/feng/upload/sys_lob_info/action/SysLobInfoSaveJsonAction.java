package c.x.platform.admin.feng.upload.sys_lob_info.action;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import c.x.platform.admin.feng.upload.sys_lob_info.entity.SysLobInfo;
import c.x.platform.admin.feng.upload.sys_lob_info.service.SysLobInfoService;
import c.x.platform.admin.feng.upload.sys_lob_info.vo.SysLobInfoVo;
import c.x.platform.root.common.action.CommAction;
import c.a.tools.log.custom.common.BaseLog;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.date.DateThreadLocal;
import c.a.util.core.file.FileUtil;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.a.util.core.upload.UploadBean;
import c.a.util.core.upload.UploadUtil;

/**
 * 对外开放
 * 
 * @ClassName:
 * @Description:
 * @author
 * @date 2016年1月18日 上午9:51:11
 * 
 */
public class SysLobInfoSaveJsonAction extends CommAction {
	@Override
	public String execute() throws Exception {
		FileUtil fileUtil = new FileUtil();
		UploadUtil uploadUtil = new UploadUtil();
		// 得到上传文件
		Map mapUpload = uploadUtil.findFile(this.servletContext, request,
				response);
		if (mapUpload != null) {
			// 文件字段
			SysLobInfo cSysLobInfo = new SysLobInfo();
			SysLobInfoService service = new SysLobInfoService();
			UploadBean cUploadBean = (UploadBean) mapUpload.get("file");
			if (cUploadBean != null) {
				// 转换file成字节

				cSysLobInfo
						.setFile(fileUtil.doFile2byte(cUploadBean.getFile()));
				cSysLobInfo.setFile_name(BeanThreadLocal.findThreadLocal().get().find(
						mapUpload.get("sys_lob_info.file_name"), ""));
				// 转换时间

				cSysLobInfo.setCreate_time(DateThreadLocal.findThreadLocal().get()
						.doString2Date((String) mapUpload
								.get("sys_lob_info.create_time")));
			}
			if (StringUtil.isBlank((String) mapUpload.get("sys_lob_info.id"))) {
				service.insert(cSysLobInfo);
			} else {
				service.update(cSysLobInfo);
			}
		} else {
			// 是普通的字段
			String file_name = request.getParameter("sys_lob_info.file_name");
			BaseLog.trace("file_name=" + file_name);
			String id = request.getParameter("sys_lob_info.id");

			SysLobInfo cSysLobInfo = (SysLobInfo) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(
					SysLobInfoVo.class, SysLobInfo.class, request);
			BaseLog.trace("file_name=" + file_name);
			SysLobInfoService service = new SysLobInfoService();
			if (StringUtil.isBlank(id)) {
				service.insert(cSysLobInfo);
			} else {
				service.update(cSysLobInfo);
			}
		}
		if (true) {

			Map data = new HashMap();
			data.put("username", "熊熊,民工之路..");
			data.put("times", 100);
			// EXT 输出初始化
			Map map = new HashMap();
			map.put("success", true);
			map.put("data", data);
			// 转换成对象，不要转换成数组
			JSONObject obj = JSONObject.fromObject(map);
			// 输出到列表
			response.getWriter().print(obj);

		}

		return null;
	}
}
