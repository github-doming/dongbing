package all.upload.sys_file.t.action;
import java.util.Map;

import all.gen.sys_bytes.t.entity.SysBytesT;
import all.gen.sys_file.t.entity.SysFileT;
import all.gen.sys_file.t.vo.SysFileTVo;
import all.upload.sys_bytes.t.service.SysBytesTService;
import all.upload.sys_file.t.service.SysFileTService;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.file.FileUtil;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.a.util.core.upload.UploadBean;
import c.a.util.core.upload.UploadUtil;
import c.a.util.mongodb.MongoDBUtil;
import c.x.platform.root.common.action.CommAction;
public class SysFileTSaveJsonCommAction extends CommAction {
	@Override
	public String execute() throws Exception {
		SysFileTService sysFileTService = new SysFileTService();
		SysBytesTService sysBytesTService = new SysBytesTService();
		FileUtil fileUtil = new FileUtil();
		UploadUtil uploadUtil = new UploadUtil();
		String filePath = null;
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
				System.out.println("sysBytesT.getFileName()=" + sysBytesT.getFileName());
				System.out.println("uploadBean.getName()=" + uploadBean.getName());
				System.out.println("uploadBean.getFilePath()=" + uploadBean.getFilePath());
				System.out.println("uploadBean.getFile().getName()=" + uploadBean.getFile().getName());
				System.out.println("uploadBean.getFile().getPath()=" + uploadBean.getFile().getPath());
				String id = BeanThreadLocal.findThreadLocal().get().find(map.get("sys_file.sysFileId"), "");
				if (StringUtil.isBlank(id)) {
					MongoDBUtil mongoDBUtil = MongoDBUtil.findInstance();
					SysFileT imgSysFileT= mongoDBUtil.upload(uploadBean.getFile(),null);
					// save file
					entity.setFileCode(imgSysFileT.getFileCode());
					entity.setFileUrl(imgSysFileT.getFileUrl());
					entity.setFileName(BeanThreadLocal.findThreadLocal().get().find(uploadBean.getName(), ""));
					entity.setFilePath(BeanThreadLocal.findThreadLocal().get().find(uploadBean.getFilePath(), ""));
					String sysFileId = sysFileTService.save(entity);
					// save SysBytesT
					sysBytesT.setBytesContent(fileUtil.doFile2byte(uploadBean.getFile()));
					sysBytesT.setFileName(BeanThreadLocal.findThreadLocal().get().find(uploadBean.getName(), ""));
					sysBytesT.setSysFileId(sysFileId);
					sysBytesTService.save(sysBytesT);
				} else {
					sysFileTService.update(entity);
				}
			} else {
				uploadBean = (UploadBean) map.get("file");
				if (uploadBean != null) {
					MongoDBUtil mongoDBUtil = MongoDBUtil.findInstance();
					SysFileT imgSysFileT= mongoDBUtil.upload(uploadBean.getFile(),null);
					// save file
					entity.setFileCode(imgSysFileT.getFileCode());
					entity.setFileUrl(imgSysFileT.getFileUrl());
					entity.setFileName(BeanThreadLocal.findThreadLocal().get().find(uploadBean.getName(), ""));
					entity.setFilePath(BeanThreadLocal.findThreadLocal().get().find(uploadBean.getFilePath(), ""));
					String sysFileId = sysFileTService.save(entity);
					// save SysBytesT
					sysBytesT.setBytesContent(fileUtil.doFile2byte(uploadBean.getFile()));
					sysBytesT.setFileName(BeanThreadLocal.findThreadLocal().get().find(uploadBean.getName(), ""));
					sysBytesT.setSysFileId(sysFileId);
					sysBytesTService.save(sysBytesT);
				}
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
		// int i=1/0;
		return this.returnJson(true, "保存成功,filePath=" + filePath);
	}
}
