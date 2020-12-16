package c.x.platform.admin.feng.upload.sys_lob_info.action;

import java.util.Map;

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
 * 
 * URLDecoder.decode 对外开放
 * 
 * @ClassName: SysLobInfoActionSave_comm
 * @Description:
 * @author
 * @date 2016年1月6日 下午8:22:38
 * 
 */
public class SysLobInfoSaveAction_URLDecoder_comm extends CommAction {
	@Override
	public String execute() throws Exception {

		FileUtil fileUtil = new FileUtil();
		UploadUtil uploadUtil = new UploadUtil();
		// 得到上传文件
		Map map = uploadUtil.findFile_URLDecoder(this.servletContext,
				request, response);
		if (map != null) {
			// 文件字段
			SysLobInfo sysLobInfo = new SysLobInfo();
			SysLobInfoService service = new SysLobInfoService();
			UploadBean uploadBean = (UploadBean) map.get("file");
			if (uploadBean != null) {
				// 转换file成字节
				sysLobInfo
						.setFile(fileUtil.doFile2byte(uploadBean.getFile()));
				sysLobInfo.setFile_name(BeanThreadLocal.findThreadLocal().get().find(
						map.get("sys_lob_info.file_name"), ""));
				// 转换时间
				sysLobInfo.setCreate_time(DateThreadLocal.findThreadLocal().get().doString2Date((String) map
						.get("sys_lob_info.create_time")));
			}
			if (StringUtil.isBlank((String) map.get("sys_lob_info.id"))) {
				service.insert(sysLobInfo);
			} else {
				service.update(sysLobInfo);
			}
		} else {
			// 是普通的字段
			String file_name = request.getParameter("sys_lob_info.file_name");
			BaseLog.trace("file_name=" + file_name);
			String id = request.getParameter("sys_lob_info.id");

			SysLobInfo sysLobInfo = (SysLobInfo) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(
					SysLobInfoVo.class, SysLobInfo.class, request);
			BaseLog.trace("file_name=" + file_name);
			SysLobInfoService service = new SysLobInfoService();
			if (StringUtil.isBlank(id)) {
				service.insert(sysLobInfo);
			} else {
				service.update(sysLobInfo);
			}
		}
		return "index";
	}
}
