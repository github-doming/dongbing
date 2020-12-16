package com.ibm.common.test.doming;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableList;
import com.ibm.common.core.CommTest;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.connector.service.authority.AuthorityService;
import com.ibm.connector.service.authority.Menu;
import com.ibm.connector.service.user.AppUserService;
import com.ibm.follow.servlet.client.ibmc_config.entity.IbmcConfig;
import com.ibm.follow.servlet.client.ibmc_config.service.IbmcConfigService;
import com.ibm.follow.servlet.cloud.ibm_config.entity.IbmConfig;
import com.ibm.follow.servlet.cloud.ibm_config.service.IbmConfigService;
import com.ibm.follow.servlet.cloud.ibm_exp_user.entity.IbmExpUser;
import com.ibm.follow.servlet.cloud.ibm_exp_user.service.IbmExpUserService;
import com.ibm.follow.servlet.cloud.ibm_game.entity.IbmGame;
import com.ibm.follow.servlet.cloud.ibm_game.service.IbmGameService;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.entity.IbmHaGameSet;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.service.IbmHaGameSetService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import com.ibm.follow.servlet.cloud.ibm_handicap_game.entity.IbmHandicapGame;
import com.ibm.follow.servlet.cloud.ibm_handicap_game.service.IbmHandicapGameService;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.entity.IbmHmGameSet;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.service.IbmHmGameSetService;
import com.ibm.follow.servlet.cloud.ibm_sys_bet_odds.entity.IbmSysBetOdds;
import com.ibm.follow.servlet.cloud.ibm_sys_bet_odds.service.IbmSysBetOddsService;
import com.ibm.follow.servlet.cloud.ibm_sys_bet_odds_tree.entity.IbmSysBetOddsTree;
import com.ibm.follow.servlet.cloud.ibm_sys_bet_odds_tree.service.IbmSysBetOddsTreeService;
import com.ibm.follow.servlet.cloud.ibm_sys_servlet_ip.entity.IbmSysServletIp;
import com.ibm.follow.servlet.cloud.ibm_sys_servlet_ip.service.IbmSysServletIpService;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.doming.core.tools.*;
import org.junit.Test;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 初始化测试类
 * @Author: Dongming
 * @Date: 2019-09-04 16:53
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */

public class InitTest extends CommTest {

	//region 资源

	class Resource {
		String id;
		String resourceName;
		String resourceCode;
		Integer grade;
		String url;
		String parentId;
		String path;
		Integer sn;
		String desc;
		List<Resource> childResource;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getResourceName() {
			return resourceName;
		}

		public void setResourceName(String resourceName) {
			this.resourceName = resourceName;
		}

		public String getResourceCode() {
			return resourceCode;
		}

		public void setResourceCode(String resourceCode) {
			this.resourceCode = resourceCode;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getParentId() {
			return parentId;
		}

		public void setParentId(String parentId) {
			this.parentId = parentId;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public Integer getSn() {
			return sn;
		}

		public void setSn(Integer sn) {
			this.sn = sn;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public List<Resource> getChildResource() {
			return childResource;
		}

		public void setChildResource(List<Resource> childResource) {
			this.childResource = childResource;
		}

		public void attr(String id, String resourceName, String resourceCode, String url, Object grade, String parentId,
				String path, Object sn, String desc) {
			this.id = id;
			this.resourceName = resourceName;
			this.resourceCode = resourceCode;
			this.grade = NumberTool.getInteger(grade);
			this.url = url;
			this.parentId = parentId;
			this.path = path;
			this.sn = NumberTool.getInteger(sn);
			this.desc = desc;
		}

		public void addChild(Resource child) {
			if (childResource == null) {
				childResource = new ArrayList<>();
			}
			childResource.add(child);
		}
	}

	@Test public void testInitResources() {
		super.transactionBegin();
		try {
			//1.创建Reader对象
			SAXReader reader = new SAXReader();
			//2.加载xml
			Document document = reader.read(new File("src/main/resources/config/com/ibm/resources.xml"));
			Element rootElement = document.getRootElement();

			Resource menu = analyzeResource(rootElement);
			saveResource(menu, null, null);
			System.out.println(JSON.toJSONString(menu));
		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}

	private void saveResource(Resource resource, String parentId, String parentPath) throws SQLException {
		AuthorityService authorityService = new AuthorityService();
		String menuId = resource.getId();
		if (StringTool.isEmpty(menuId)) {
			menuId = RandomTool.getNumLetter(4);
		}
		//校验至id不重复
		while (authorityService.findMenu(menuId) != null) {
			menuId = RandomTool.getNumLetter(4);
		}
		Menu menu = new Menu(menuId);
		menu.attr(resource.getResourceName(), resource.getResourceCode(), resource.getUrl(), "", "", resource.getSn(),
				IbmStateEnum.OPEN.name());
		parentId = StringTool.isEmpty(resource.getParentId()) ? parentId : resource.getParentId();
		String path = StringTool.isEmpty(resource.getPath()) ? parentPath + menuId + "." : resource.getPath();
		authorityService.saveMenu(menu, parentId, path, resource.grade, resource.desc, IbmMainConfig.TENANT_CODE, "1");
		if (ContainerTool.notEmpty(resource.childResource)) {
			for (Resource childResource : resource.childResource) {
				saveResource(childResource, menuId, path);
			}
		}

	}

	private Resource analyzeResource(Element element) {
		String id = element.attributeValue("id");
		String resourceName = element.attributeValue("resourceName");
		String resourceCode = element.attributeValue("resourceCode");
		String url = element.attributeValue("url");
		String sn = element.attributeValue("sn");
		String grade = element.attributeValue("grade");
		String parentId = element.attributeValue("parentId");
		String path = element.attributeValue("path");
		String desc = element.attributeValue("desc");

		id = StringTool.isEmpty(id) ? element.elementText("id") : id;
		resourceName = StringTool.isEmpty(resourceName) ? element.elementText("resourceName") : resourceName;
		resourceCode = StringTool.isEmpty(resourceCode) ? element.elementText("resourceCode") : resourceCode;
		url = StringTool.isEmpty(url) ? element.elementText("url") : url;
		sn = StringTool.isEmpty(sn) ? element.elementText("sn") : sn;
		grade = StringTool.isEmpty(grade) ? element.elementText("grade") : grade;
		parentId = StringTool.isEmpty(parentId) ? element.elementText("parentId") : parentId;
		path = StringTool.isEmpty(path) ? element.elementText("path") : path;
		desc = StringTool.isEmpty(desc) ? element.elementText("desc") : desc;

		Resource menu = new Resource();
		menu.attr(id, resourceName, resourceCode, url, grade, parentId, path, sn, desc);

		Element subResources = element.element("subResources");
		if (subResources != null) {
			List<Element> subElements = subResources.elements();
			for (Element subElement : subElements) {
				Resource child = analyzeResource(subElement);
				menu.addChild(child);
			}
		}
		return menu;

	}
	//endregion

	//region 初始化权限
	@Test public void testInitAuthority() {
		super.transactionBegin();
		try {

			AuthorityService authorityService = new AuthorityService();
			String roleId = authorityService.saveRole("平台管理员", "SYSTEM", 0, IbmStateEnum.OPEN.name(), "1");
			List<String> menuIds = authorityService.listAllMenuIds();
			authorityService.updateRoleResources(roleId, menuIds, "1");
			authorityService.saveUserRole("1", roleId, "1", new Date());
		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}

	}

	private void initGroup() throws SQLException {

	}
	//endregion

	//region 客户端

	@Test public void testInitClientIp() {
		super.transactionBegin();
		try {
			Date endTime = DateTool.getDay("20991231");
			Date nowTime = new Date();
			IbmSysServletIp servletIp = new IbmSysServletIp();
			servletIp.setServletIp("127.0.0.1");
			servletIp.setStartTime(nowTime);
			servletIp.setStartTimeLong(System.currentTimeMillis());
			servletIp.setEndTime(endTime);
			servletIp.setEndTimeLong(endTime.getTime());
			servletIp.setCreateUser("Doming");
			servletIp.setCreateTime(nowTime);
			servletIp.setCreateTimeLong(System.currentTimeMillis());
			servletIp.setUpdateTimeLong(System.currentTimeMillis());
			servletIp.setState(IbmStateEnum.OPEN.name());
			new IbmSysServletIpService().save(servletIp);
		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}
	//endregion

	//region 盘口配置

	@Test public void testEventHandicapConfig() {
		super.transactionBegin();
		try {
			String str = "OPEN_CLIENT_3,CLOSE_CLIENT_2,VALI_LOGIN_3,LOGIN_5,SNATCH_LOGIN_2,SET_CONFIG_8,INFO_CONFIG_1,";

			String[] moduleInfos = str.split(",");

			Date nowTime = new Date();
			IbmConfigService configService = new IbmConfigService();

			for (String moduleInfo : moduleInfos) {
				String key = moduleInfo.substring(0, moduleInfo.lastIndexOf("_"));
				String val = moduleInfo.substring(moduleInfo.lastIndexOf("_") + 1);

				key = key.concat("_EVENT");
				createConfig(nowTime, configService, key, val);
			}

		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}

	@Test public void testInitHandicapConfig() {
		super.transactionBegin();
		try {
			Date nowTime = new Date();
			IbmConfigService configService = new IbmConfigService();
			String key;
			String value = "60";
			for (HandicapUtil.Code handicap : HandicapUtil.codes()) {
				for (GameUtil.Code gameCode : GameUtil.codes()) {
					key = handicap.name().concat("#").concat(gameCode.name()).concat("#SEAL_TIME");
					createConfig(nowTime, configService, key, value);
				}
			}
		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}

	private void createConfig(Date nowTime, IbmConfigService configService, String key, String value) throws Exception {
		IbmConfig config = new IbmConfig();
		config.setConfigKey(key);
		config.setConfigValue(value);
		config.setCreateUser("doming");
		config.setCreateTime(nowTime);
		config.setCreateTimeLong(System.currentTimeMillis());
		config.setUpdateTimeLong(System.currentTimeMillis());
		config.setState(IbmStateEnum.OPEN.name());
		configService.save(config);
	}
	//endregion

	//region 客户端配置

	@Test public void testInitClientConfig() {
		super.transactionBegin();
		try {
			Date nowTime = new Date();
			IbmcConfigService configService = new IbmcConfigService();
			String key;
			String value = "60";
			for (HandicapUtil.Code handicap : HandicapUtil.codes()) {
				for (GameUtil.Code gameCode : GameUtil.codes()) {
					key = handicap.name().concat("#").concat(gameCode.name()).concat("#SEAL_TIME");
					createClientConfig(nowTime, configService, key, value);
				}
			}
		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}

	}

	private void createClientConfig(Date nowTime, IbmcConfigService configService, String key, String value)
			throws Exception {
		IbmcConfig config = new IbmcConfig();
		config.setClientConfigKey(key);
		config.setClientConfigValue(value);
		config.setCreateUser("doming");
		config.setCreateTime(nowTime);
		config.setCreateTimeLong(System.currentTimeMillis());
		config.setUpdateTimeLong(System.currentTimeMillis());
		config.setState(IbmStateEnum.OPEN.name());
		configService.save(config);
	}
	//endregion

	//region 游戏
	@Test public void createGame() {
		super.transactionBegin();
		try {
			Date nowTime = new Date();
			IbmGame game = new IbmGame();
			game.setGameName("国家时时彩");
			game.setGameCode("COUNTRY_SSC");
			game.setIcon("/pages/com/ibm/image/COUNTRY_SSC.png");
			game.setRepGrabTableName("IBM_REP_GRAB_COUNTRY_SSC");
			game.setRepDrawTableName("IBM_REP_DRAW_COUNTRY_SSC");
			game.setDrawTime(0);
			game.setSn(7);
			game.setCreateUser("Doming");
			game.setCreateTime(nowTime);
			game.setCreateTimeLong(System.currentTimeMillis());
			game.setUpdateTime(nowTime);
			game.setUpdateTimeLong(System.currentTimeMillis());
			game.setState(IbmStateEnum.OPEN.name());
			String gameId = new IbmGameService().save(game);

			IbmHandicapGameService handicapGameService = new IbmHandicapGameService();
			IbmHandicapGame handicapGame = new IbmHandicapGame();
			handicapGame.setHandicapId(HandicapUtil.id("IDC", "MEMBER"));
			handicapGame.setGameId(gameId);
			handicapGame.setGameCode("COUNTRY_SSC");
			handicapGame.setGameName("澳洲幸运5");
			handicapGame.setHandicapName("IDC");
			handicapGame.setHandicapCode("IDC");
			handicapGame.setType(1);
			handicapGame.setCloseTime(180);
			handicapGame.setIcon("/pages/com/ibm/image/COUNTRY_SSC.png");
			handicapGame.setSn(6);
			handicapGame.setCreateUser("Doming");
			handicapGame.setCreateTime(nowTime);
			handicapGame.setCreateTimeLong(System.currentTimeMillis());
			handicapGame.setUpdateTime(nowTime);
			handicapGame.setUpdateTimeLong(System.currentTimeMillis());
			handicapGame.setState(IbmStateEnum.OPEN.name());
			handicapGameService.save(handicapGame);

			handicapGame.setIbmHandicapGameId(null);
			handicapGame.setHandicapId(HandicapUtil.id("IDC", "AGENT"));
			handicapGameService.save(handicapGame);

		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}

	@Test public void createGameOdd() {
		super.transactionBegin();
		try {

			String[] NUMBER_RANK = {"冠亚"};

			String[] NUMBER_TYPE = {"大", "双"};
			String gameCode = "SELF_10_2";
			int oddT = 2150;
			Date nowTime = new Date();

			String gameId = GameUtil.id(gameCode);

			IbmSysBetOddsTree betOddsTree = new IbmSysBetOddsTree();
			betOddsTree.setGameId(gameId);
			betOddsTree.setPlayTypeCode("CHAMPION_SUM_BIG_DOUBLE");
			betOddsTree.setPlayTypeName("冠亚和_大双");
			betOddsTree.setOddsT(oddT);
			betOddsTree.setCreateTime(nowTime);
			betOddsTree.setCreateTimeLong(System.currentTimeMillis());
			betOddsTree.setUpdateTime(nowTime);
			betOddsTree.setUpdateTimeLong(System.currentTimeMillis());
			betOddsTree.setState(IbmStateEnum.OPEN.name());
			String oddTreeId = new IbmSysBetOddsTreeService().save(betOddsTree);

			IbmSysBetOddsService betOddsService = new IbmSysBetOddsService();
			for (String rank : NUMBER_RANK) {
				for (String type : NUMBER_TYPE) {
					String item = rank.concat("|").concat(type);
					IbmSysBetOdds betOdds = new IbmSysBetOdds();
					betOdds.setSysBetOddsTreeId(oddTreeId);
					betOdds.setGameId(gameId);
					betOdds.setGameCode(gameCode);
					betOdds.setGamePlayName(item);
					betOdds.setOddsT(oddT);
					betOdds.setCreateTime(nowTime);
					betOdds.setCreateTimeLong(System.currentTimeMillis());
					betOdds.setUpdateTime(nowTime);
					betOdds.setUpdateTimeLong(System.currentTimeMillis());
					betOdds.setState(IbmStateEnum.OPEN.name());
					betOddsService.save(betOdds);
				}
			}

		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}

	}
	@Test public void createGameOdd2() {
		super.transactionBegin();
		try {
			String oldGame = "PK10";
			String oldGameId = GameUtil.id(oldGame);

			String game = "COUNTRY_10";
			String gameId = GameUtil.id(game);
			Date nowTime = new Date();

			// TODO: 2020/5/5   初始化树数据
			IbmSysBetOddsTreeService betOddsTreeService = new IbmSysBetOddsTreeService();
			IbmSysBetOddsService betOddsService = new IbmSysBetOddsService();

			List<IbmSysBetOddsTree> betOddsTrees = betOddsTreeService.listByGameId(oldGameId);
			for (IbmSysBetOddsTree betOddsTree : betOddsTrees) {
				String oldTreeId = betOddsTree.getIbmSysBetOddsTreeId();
				betOddsTree.setIbmSysBetOddsTreeId(null);
				betOddsTree.setGameId(gameId);
				betOddsTree.setCreateTime(nowTime);
				betOddsTree.setCreateTimeLong(System.currentTimeMillis());
				betOddsTree.setUpdateTime(nowTime);
				betOddsTree.setUpdateTimeLong(System.currentTimeMillis());
				String treeId = betOddsTreeService.save(betOddsTree);

				List<IbmSysBetOdds> betOdds = betOddsService.listByTreeId(oldTreeId);
				for (IbmSysBetOdds betOdd : betOdds) {
					betOdd.setIbmSysBetOddsId(null);
					betOdd.setSysBetOddsTreeId(treeId);
					betOdd.setGameId(gameId);
					betOdd.setGameCode(game);
					betOdd.setCreateTime(nowTime);
					betOdd.setCreateTimeLong(System.currentTimeMillis());
					betOdd.setUpdateTime(nowTime);
					betOdd.setUpdateTimeLong(System.currentTimeMillis());
					betOddsService.save(betOdd);
				}

			}

		} catch (Exception e) {
			super.transactionRoll();
			log.error("测试错误", e);
		} finally {
			super.transactionEnd();
		}

	}

	//endregion

	//region 更新盘口游戏
	@Test public void updateHandicapGame() {
		super.transactionBegin();
		try {
			/*
				1. 查询出用户所拥有的游戏
				2. 查询出用户所拥有的的盘口
				3. 依次查询用户所拥有的盘口的游戏是否添加给下属会员或代理
				4. 没有游戏，则添加游戏
			 */
			List<IbmTypeEnum> types = ImmutableList.<IbmTypeEnum>builder().add(IbmTypeEnum.USER).build();
			List<String> userIds = new AppUserService().listIdByTypes(types);
			for (String userId : userIds) {
				IbmExpUser expUser = new IbmExpUserService().findByUserId(userId);
				if(expUser == null){
					continue;
				}
				String[] games = expUser.getAvailableGame().split(",");

				IbmHandicapGameService handicapGameService = new IbmHandicapGameService();
				Date nowTime = new Date();

				//region 代理
				String[] handicaps = expUser.getAgentAvailableHandicap().split(",");
				IbmHandicapAgentService handicapAgentService = new IbmHandicapAgentService();
				IbmHaGameSetService haGameSetService = new IbmHaGameSetService();

				IbmHaGameSet haGameSetDef = haGameSetService.findDef();
				IbmHaGameSet haGameSet = new IbmHaGameSet();
				haGameSet.setUserId(userId);
				haGameSet.setBetState(haGameSetDef.getBetState());
				haGameSet.setBetFollowMultipleT(haGameSetDef.getBetFollowMultipleT());
				haGameSet.setBetLeastAmountT(haGameSetDef.getBetLeastAmountT());
				haGameSet.setBetMostAmountT(haGameSetDef.getBetMostAmountT());
				haGameSet.setBetFilterNumber(haGameSetDef.getBetFilterNumber());
				haGameSet.setNumberOpposing(haGameSetDef.getNumberOpposing());
				haGameSet.setTwoSideOpposing(haGameSetDef.getTwoSideOpposing());
				haGameSet.setBetRecordRows(haGameSetDef.getBetRecordRows());
				haGameSet.setCreateTime(nowTime);
				haGameSet.setUpdateTime(nowTime);
				haGameSet.setState(IbmStateEnum.OPEN.name());

				for (String handicap : handicaps) {
					String handicapId = HandicapUtil.id(handicap, IbmTypeEnum.AGENT.name());
					haGameSet.setHandicapId(handicapId);

					List<String> handicapAgentIds = handicapAgentService.listId(userId, handicap);
					if (ContainerTool.isEmpty(handicapAgentIds)) {
						continue;
					}
					for (String game : games) {
						String gameId = GameUtil.id(game);
						if (StringTool.isEmpty(gameId)) {
							continue;
						}
						haGameSet.setGameId(gameId);

						if (handicapGameService.isExist(handicap, game, IbmTypeEnum.AGENT.name())) {
							for (String handicapAgentId : handicapAgentIds) {
								if (StringTool.isEmpty(haGameSetService.findId(handicapAgentId, gameId))) {
									haGameSet.setIbmHaGameSetId(null);
									haGameSet.setHandicapAgentId(handicapAgentId);
									haGameSet.setCreateTimeLong(System.currentTimeMillis());
									haGameSet.setUpdateTimeLong(System.currentTimeMillis());
									haGameSetService.save(haGameSet);
								}
							}

						}

					}
				}
				//endregion

				//region 会员
				handicaps = expUser.getMemberAvailableHandicap().split(",");
				IbmHandicapMemberService handicapMemberService = new IbmHandicapMemberService();
				IbmHmGameSetService hmGameSetService = new IbmHmGameSetService();

				IbmHmGameSet hmGameSetDef = hmGameSetService.findDef();

				IbmHmGameSet hmGameSet = new IbmHmGameSet();
				hmGameSet.setUserId(userId);
				hmGameSet.setBetState(hmGameSetDef.getBetState());
				hmGameSet.setBetMode(hmGameSetDef.getBetMode());
				hmGameSet.setBetAutoStop(hmGameSetDef.getBetAutoStop());
				hmGameSet.setBetAutoStopTimeLong(hmGameSetDef.getBetAutoStopTimeLong());
				hmGameSet.setBetAutoStart(hmGameSetDef.getBetAutoStart());
				hmGameSet.setBetAutoStartTimeLong(hmGameSetDef.getBetAutoStartTimeLong());
				hmGameSet.setBetSecond(hmGameSetDef.getBetSecond());
				hmGameSet.setSplitTwoSideAmount(hmGameSetDef.getSplitTwoSideAmount());
				hmGameSet.setSplitNumberAmount(hmGameSetDef.getSplitNumberAmount());
				hmGameSet.setCreateTime(nowTime);
				hmGameSet.setUpdateTime(nowTime);
				hmGameSet.setState(IbmStateEnum.OPEN.name());

				for (String handicap : handicaps) {
					String handicapId = HandicapUtil.id(handicap, IbmTypeEnum.MEMBER.name());
					hmGameSet.setHandicapId(handicapId);

					List<String> handicapMemberIds = handicapMemberService.listId(userId, handicap);
					if (ContainerTool.isEmpty(handicapMemberIds)) {
						continue;
					}
					for (String game : games) {
						String gameId = GameUtil.id(game);
						if (StringTool.isEmpty(gameId)) {
							continue;
						}
						hmGameSet.setGameId(gameId);

						if (handicapGameService.isExist(handicap, game, IbmTypeEnum.MEMBER.name())) {
							for (String handicapMemberId : handicapMemberIds) {
								if (StringTool.isEmpty(hmGameSetService.findId(handicapMemberId, gameId))) {
									hmGameSet.setIbmHmGameSetId(null);
									hmGameSet.setHandicapMemberId(handicapMemberId);
									hmGameSet.setCreateTimeLong(System.currentTimeMillis());
									hmGameSet.setUpdateTimeLong(System.currentTimeMillis());
									hmGameSetService.save(hmGameSet);
								}
							}

						}

					}
				}
				//endregion
			}

		} catch (Exception e) {
			super.transactionRoll();
			log.error("测试错误", e);
		} finally {
			super.transactionEnd();
		}

	}
	//endregion

}
