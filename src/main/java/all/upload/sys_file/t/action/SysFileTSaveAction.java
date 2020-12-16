package all.upload.sys_file.t.action;
import java.util.Map;
import all.gen.sys_bytes.t.entity.SysBytesT;
import all.gen.sys_file.t.entity.SysFileT;
import all.gen.sys_file.t.vo.SysFileTVo;
import all.upload.sys_bytes.t.service.SysBytesTService;
import all.upload.sys_file.t.service.SysFileTService;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.file.FileUtil;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.a.util.core.upload.UploadBean;
import c.a.util.core.upload.UploadUtil;
import c.x.platform.root.common.action.BaseAction;
public class SysFileTSaveAction extends BaseAction {
	/**
	 * 上传文件
	 * 
	 * @Description:
	 * @Title: execute 
	 * @param @return
	 * @param @throws Exception  参数说明 
	 * @throws
	 */
	@Override
	public String execute() throws Exception {
		SysFileTService sysFileTService = new SysFileTService();
		SysBytesTService sysBytesTService = new SysBytesTService();
		FileUtil fileUtil = new FileUtil();
		UploadUtil uploadUtil = new UploadUtil();
		// 得到上传文件
		Map<String, Object> map = uploadUtil.findFile(this.servletContext, request, response);
		if (map != null) {
			SysFileT entity = (SysFileT) BeanThreadLocal.findThreadLocal().get().doMap2BeanByTableName("sys_file", map,
					SysFileT.class);
			// 文件字段
			SysBytesT sysBytesT = new SysBytesT();
			UploadBean uploadBean = (UploadBean) map.get("sys_bytes.bytes");
			if (uploadBean != null) {
				entity.setFileName(BeanThreadLocal.findThreadLocal().get().find(uploadBean.getName(), ""));
				entity.setFilePath(BeanThreadLocal.findThreadLocal().get().find(uploadBean.getFilePath(), ""));
				// 转换file成字节
				sysBytesT.setBytesContent(fileUtil.doFile2byte(uploadBean.getFile()));
				sysBytesT.setFileName(BeanThreadLocal.findThreadLocal().get().find(uploadBean.getName(), ""));
			}
			String id = BeanThreadLocal.findThreadLocal().get().find(map.get("sys_file.sysFileId"), "");
			if (StringUtil.isBlank(id)) {
				String sysFileId = sysFileTService.save(entity);
				sysBytesT.setSysFileId(sysFileId);
				sysBytesTService.save(sysBytesT);
			} else {
				sysFileTService.update(entity);
			}
		} else {
			// 是普通的字段
			String id = request.getParameter("sys_file.sysFileId");
			SysFileT entity = null;
			entity = (SysFileT) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(SysFileTVo.class,
					SysFileT.class, request);
			if (StringUtil.isBlank(id)) {
				sysFileTService.save(entity);
			} else {
				sysFileTService.update(entity);
			}
		}
		return CommViewEnum.Default.toString();
	}
}
