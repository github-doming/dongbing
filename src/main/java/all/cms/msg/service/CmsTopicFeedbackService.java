package all.cms.msg.service;
import java.util.Date;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.cms_topic_user.t.entity.CmsTopicUserT;
import all.gen.cms_topic_user.t.service.CmsTopicUserTService;
import all.gen.cms_board.t.entity.CmsBoardT;
import all.gen.cms_topic.t.entity.CmsTopicT;
import all.gen.cms_topic.t.service.CmsTopicTService;
import all.gen.cms_topic_board.t.entity.CmsTopicBoardT;
import all.gen.cms_topic_board.t.service.CmsTopicBoardTService;
import c.a.util.core.enums.bean.cms.CmsBoardCodeEnum;
/**
 * 
 * 反馈
 * 
 * @Description:
 * @ClassName:
 * @date 2018年5月31日 下午4:06:33
 * @author cxy
 * @Email: 
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class CmsTopicFeedbackService extends CmsTopicTService {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 
	 * 发反馈
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String save(String content, String sendUser) throws Exception {
		// 取内容前十个字作为标题
		String title = content;
		if (content.length() > 10) {
			title = content.substring(0, 10);
		}
		Date date = new Date();
		CmsBoardService cmsBoardService = new CmsBoardService();
		CmsTopicTService cmsTopicTService = new CmsTopicTService();
		CmsTopicBoardTService cmsTopicBoardTService=new CmsTopicBoardTService();
		CmsTopicUserTService cmsTopicUserTService = new CmsTopicUserTService();
		CmsTopicT cmsTopicT = new CmsTopicT();
		CmsTopicUserT cmsTopicUserT = new CmsTopicUserT();
		// 版块
		String cmsBoardId = null;
		CmsBoardT cmsBoardT = cmsBoardService.findByCode(CmsBoardCodeEnum.FEEDBACK.getCode());
		if (cmsBoardT == null) {
			cmsBoardT = new CmsBoardT();
			cmsBoardT.setCode(CmsBoardCodeEnum.FEEDBACK.getCode());
			cmsBoardT.setName(CmsBoardCodeEnum.FEEDBACK.getMsgCn());
			cmsBoardId = cmsBoardService.save(cmsBoardT);
		}
		// 主题
		cmsTopicT.setCmsTopicTitle(title);
		cmsTopicT.setCmsContent(content);
		cmsTopicT.setCreateTime(date);
		cmsTopicT.setCreateTimeLong(date.getTime());
		cmsTopicT.setUpdateTime(date);
		cmsTopicT.setUpdateTimeLong(date.getTime());
		String cmsTopicId = cmsTopicTService.save(cmsTopicT);
		// 版块与主题
		CmsTopicBoardT cmsTopicBoardT=new CmsTopicBoardT();
		cmsTopicBoardT.setCmsBoardId(cmsBoardId);
		cmsTopicBoardT.setCmsTopicId(cmsTopicId);
		cmsTopicBoardTService.save(cmsTopicBoardT);
		// 用户与主题(权限访问)
		cmsTopicUserT.setAppUserId(sendUser);
		cmsTopicUserT.setCmsTopicId(cmsTopicId);
		cmsTopicUserT.setCreateTime(date);
		cmsTopicUserT.setCreateTimeLong(date.getTime());
		cmsTopicUserT.setUpdateTime(date);
		cmsTopicUserT.setUpdateTimeLong(date.getTime());
		cmsTopicUserTService.save(cmsTopicUserT);
		return cmsTopicId;
	}
}
