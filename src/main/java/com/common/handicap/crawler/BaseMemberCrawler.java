package com.common.handicap.crawler;


import com.common.core.JsonResultBeanPlus;
import com.common.enums.TypeEnum;
import com.common.handicap.crawler.entity.MemberCrawlerImage;
import com.common.handicap.crawler.entity.MemberUser;
import com.common.handicap.crawler.entity.UserCrawlerImage;

/**
 * 盘口会员爬虫 预实现类
 *
 * @Author: Dongming
 * @Date: 2020-05-09 16:23
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class BaseMemberCrawler<T extends GrabMember & GrabHandicap> extends BaseHandicapCrawler<T>
		implements CrawlerMember {

	protected MemberCrawlerImage memberImage;

	protected BaseMemberCrawler() {
		customerType = TypeEnum.MEMBER;
		memberImage = new MemberCrawlerImage();
	}

	/**
	 * 用户基本信息
	 *
	 * @return 用户信息
	 */
	protected abstract JsonResultBeanPlus userInfo();

	@Override public MemberUser userInfo(boolean refresh) {
		if (memberImage.member() == null || refresh) {
			//获取用户信息
			JsonResultBeanPlus bean = checkUserInfo();
			//获取用户信息失败，返回空
			if (bean.notSuccess()) {
				return null;
			}
			memberImage.member((MemberUser) bean.getData());
		}
		return memberImage.member();
	}

	/**
	 * 判断是否存在用户画像，<br>
	 * 判断是否需要登录，<br>
	 * 判断是否有会员信息，<br>
	 * 进行校验
	 *
	 * @return 校验结果
	 */
	@Override public JsonResultBeanPlus checkInfo() {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		//还没有会员信息
		if (memberImage.member() == null) {
			bean = checkUserInfo();
			if (bean.notSuccess()) {
				return bean;
			}
			memberImage.member((MemberUser) bean.getData());
			return bean;

		}
		//上次校验时间
		if (memberImage.checkTime() == 0) {
			memberImage.checkTime(System.currentTimeMillis());
		}

		long lastTime = memberImage.checkTime();
		boolean flag = System.currentTimeMillis() - lastTime > MIN_CHECK_TIME;
		//到了检查时间 或者可用金额为 0.0
		if (flag || memberImage.usedQuota() - 0.0 == 0) {
			//获取用户信息
			bean = checkUserInfo();
			//获取用户信息失败，返回空
			if (bean.isSuccess()) {
				memberImage.member((MemberUser) bean.getData());
				memberImage.checkTime(System.currentTimeMillis());
			}
		} else {
			//使用内存数据
			bean.success(memberImage.member());
		}
		//校验失败，且上次校验成功时间大于超时校验时间
		if (bean.notSuccess() && System.currentTimeMillis() - lastTime > MAX_CHECK_TIME) {
			needLogin();
		}

		return bean;
	}

	@Override void userImageHolder(UserCrawlerImage userImage) {
		memberImage.userImage(userImage);
		crawlerGrab.userImage(userImage);
	}

	/**
	 * 带有登录校验的获取用户信息
	 *
	 * @return 用户信息 结果
	 */
	private JsonResultBeanPlus checkUserInfo() {
		JsonResultBeanPlus bean;
		//需要登录
		if (memberImage.needLogin()) {
			log.info(getLogTitle() + ",用户需要登录");
			bean = login();
			if (bean.notSuccess()) {
				return bean;
			}
		}
		return userInfo();
	}

}
