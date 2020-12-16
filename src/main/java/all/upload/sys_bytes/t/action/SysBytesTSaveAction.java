package all.upload.sys_bytes.t.action;
import java.util.Map;
import all.gen.sys_bytes.t.entity.SysBytesT;
import all.gen.sys_bytes.t.vo.SysBytesTVo;
import all.upload.sys_bytes.t.service.SysBytesTService;
import c.a.tools.log.custom.common.BaseLog;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.file.FileUtil;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.a.util.core.upload.UploadBean;
import c.a.util.core.upload.UploadUtil;
import c.x.platform.root.common.action.BaseAction;
public class SysBytesTSaveAction extends BaseAction {
	/**
	 * 
	 * 上传数据库
	 * @Description:
	 * @Title: execute 
	 * @param @return
	 * @param @throws Exception  参数说明 
	 * @throws
	 */
	@Override
	public String execute() throws Exception {
		SysBytesTService service = new SysBytesTService();
		FileUtil fileUtil = new FileUtil();
		UploadUtil uploadUtil = new UploadUtil();
		// 得到上传文件
		Map<String, Object> map = uploadUtil.findFile(this.servletContext, request, response);
		if (map != null) {
			// 文件字段
			SysBytesT sysBytesT =  (SysBytesT) BeanThreadLocal.findThreadLocal().get()
					.doMap2BeanByTableName("sys_bytes", map, SysBytesT.class);
			UploadBean uploadBean = (UploadBean) map.get("sys_bytes.bytes");
			if (uploadBean != null) {
				// 转换file成字节
				sysBytesT.setBytesContent(fileUtil.doFile2byte(uploadBean.getFile()));
				sysBytesT.setFileName(BeanThreadLocal.findThreadLocal().get().find(uploadBean.getName(), ""));
			}
			String id = BeanThreadLocal.findThreadLocal().get().find(map.get("sys_bytes.sysBytesId"), "");
			if (StringUtil.isBlank(id)) {
				service.save(sysBytesT);
			} else {
				sysBytesT.setSysBytesId(id);
				service.update(sysBytesT);
			}
		} else {
			// 是普通的字段
			String fileName = request.getParameter("sys_lob_info.name");
			BaseLog.trace("file_name=" + fileName);
			SysBytesT entity = null;
			String id = request.getParameter("sys_bytes.sysBytesId");
			entity = (SysBytesT) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(SysBytesTVo.class,
					SysBytesT.class, request);
			if (StringUtil.isBlank(id)) {
				service.save(entity);
			} else {
				service.update(entity);
			}
		}
		return CommViewEnum.Default.toString();
	}
}
