package com.ibm.common.utils.http.utils.member;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.common.enums.HcCodeEnum;
import com.ibm.common.core.configs.ComConfig;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.BallCodeTool;
import com.ibm.common.utils.http.tools.HandicapHttpClientTool;
import com.ibm.common.utils.http.tools.member.ComMemberTool;
import com.ibm.common.utils.http.utils.entity.AccountInfo;
import com.ibm.common.utils.http.utils.entity.ComGameInfo;
import com.ibm.common.utils.http.utils.entity.MemberCrawler;
import com.ibm.common.utils.http.utils.entity.MemberUserInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: COM会员工具类
 * @Author: null
 * @Date: 2020-04-22 15:21
 * @Version: v1.0
 */
public class ComMemberUtil extends BaseMemberUtil {
	private HandicapUtil.Code handicapCode=HandicapUtil.Code.COM;

    private static volatile ComMemberUtil instance = null;

    public static ComMemberUtil findInstance() {
        if (instance == null) {
            synchronized (ComMemberUtil.class) {
                if (instance == null) {
                    ComMemberUtil instance = new ComMemberUtil();
                    // 初始化
                    instance.init();
                    ComMemberUtil.instance = instance;
                }
            }
        }
        return instance;
    }

    /**
     * 销毁工厂
     */
    public static void destroy() {
        if (instance == null) {
            return;
        }
        if(ContainerTool.notEmpty(instance.memberCrawlers)){
            for (MemberCrawler memberCrawler : instance.memberCrawlers.values()) {
                memberCrawler.getHcConfig().destroy();
            }
        }
        instance.memberCrawlers=null;
        instance = null;
    }
    /**
     * 登陆
     *
     * @param existHmId       盘口会员存在id
     * @param memberAccount   会员账号
     * @param memberPassword  会员密码
     * @param handicapUrl     盘口地址
     * @param handicapCaptcha 盘口验证码
     */
    @Override
    public JsonResultBeanPlus login(String existHmId, String memberAccount, String memberPassword, String handicapUrl, String handicapCaptcha) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        //已存在数据
        MemberCrawler member;
        if(memberCrawlers.containsKey(existHmId)){
            member=  memberCrawlers.get(existHmId);
            if(StringTool.notEmpty(member.getProjectHost())){
                bean.setData(memberCrawlers.get(existHmId));
                bean.success();
                return bean;
            }
        }else{
            member=  new MemberCrawler();
            member.setExistId(existHmId);
        }
        AccountInfo accountInfo=member.getAccountInfo();
        accountInfo.setItemInfo(memberAccount,memberPassword,handicapUrl,handicapCaptcha);

        try {
            //获取配置类
            HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(member);
            httpConfig.httpTimeOut(10 * 1000);
            bean = login(httpConfig, accountInfo);
            if (!bean.isSuccess()) {
                return bean;
            }
            Map<String, String> data = (Map<String, String>) bean.getData();

            member.setProjectHost(data.get("projectHost"));
            member.setBrowserCode(data.get("browserCode"));
            member.getMemberUserInfo().setMemberAccount(accountInfo.getAccount());
            if(!memberCrawlers.containsKey(existHmId)){
                memberCrawlers.put(existHmId,member);
            }
        } catch (Exception e) {
			log.error(message,handicapCode.name(),existHmId, "登录失败,失败原因为："+e);
            bean.error(e.getMessage());
        }
        return bean;
    }

    /**
     * 登录
     * @param httpConfig        htpp请求配置类
     * @param accountInfo       账号信息对象
     * @return
     */
    @Override
    public JsonResultBeanPlus login(HttpClientConfig httpConfig, AccountInfo accountInfo) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        String handicapUrl=accountInfo.getHandicapUrl();
        if(!accountInfo.getHandicapUrl().endsWith("/")){
            handicapUrl=handicapUrl.concat("/");
        }
        httpConfig.headers(null);
        httpConfig.httpContext(null);
        try {
            httpConfig.httpContext(HttpClientContext.create());
            //获取线路选择页面
            String routeHtml= ComMemberTool.getSelectRoutePage(httpConfig,handicapUrl,accountInfo.getHandicapCaptcha());
            if (StringTool.isEmpty(routeHtml) || !StringTool.isContains(routeHtml, "線路選擇")) {
				log.info(message,handicapCode.name(),accountInfo.getAccount(), "获取线路页面失败："+routeHtml);
                bean.putEnum(HcCodeEnum.IBS_403_PAGE_NAVIGATE);
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
            //会员线路数组
            String[] routes =  ComMemberTool.getMemberRoute(routeHtml);
			httpConfig.httpContext().getCookieStore().clear();

            //选择登录线路
            String loginSrc =ComMemberTool.getLoginHtml(httpConfig,routes);
            if (StringTool.isEmpty(loginSrc)) {
                bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            //TODO,暂未处理验证码

            //处理登录结果json
            String loginHtml = ComMemberTool.getLogin(httpConfig,accountInfo.getAccount(),accountInfo.getPassword(),loginSrc);
            if (StringTool.isEmpty(loginHtml)) {
                bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            JSONObject loginInfo=JSONObject.parseObject(loginHtml);
            //错误处理和是否第一次登录盘口
            if(loginInfo.getInteger("success")!=200||StringTool.notEmpty(loginInfo.get("tipinfo"))){
                bean.putEnum(ComMemberTool.loginError(loginInfo.getString("tipinfo")));
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
            //获取browserCode
            String browserCode=ComMemberTool.getIndex(httpConfig,loginSrc);
            if (StringTool.isEmpty(browserCode)) {
                bean.putEnum(HcCodeEnum.IBS_404_INDEX_INFO);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }

            Map<String, String> data = new HashMap<>(1);
            data.put("projectHost", loginSrc.concat("/"));
            data.put("browserCode",browserCode);
            //会员账户
            data.put("memberAccount", accountInfo.getAccount());

            bean.setData(data);
            bean.success();
        } catch (Exception e) {
			log.error(message,handicapCode.name(),accountInfo.getAccount(), "登录失败,失败原因为："+e);
            bean.error(e.getMessage());
        } finally {
            httpConfig.defTimeOut();
        }
        return bean;
    }

    /**
     * 校验登录
     *
     * @param handicapUrl     盘口url
     * @param handicapCaptcha 盘口验证码
     * @param memberAccount   盘口账号
     * @param memberPassword  盘口密码
     * @return 校验登录结果
     */
    @Override
    public JsonResultBeanPlus valiLogin(String handicapUrl, String handicapCaptcha, String memberAccount, String memberPassword) {
        // 获取配置类
        HttpClientConfig httpConfig = new HttpClientConfig();
        httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());
        AccountInfo accountInfo=new AccountInfo();
        accountInfo.setItemInfo(memberAccount,memberPassword,handicapUrl,handicapCaptcha);

        JsonResultBeanPlus bean = login(httpConfig, accountInfo);
        if (bean.isSuccess()) {
            String existHmId = RandomTool.getNumLetter32();
            //存储账号信息
            MemberCrawler member=  new MemberCrawler();
            //存储爬虫信息
            Map<String, String> data = (Map<String, String>) bean.getData();
            member.setAccountInfo(accountInfo);
            member.setProjectHost(data.get("projectHost"));
            member.setBrowserCode(data.get("browserCode"));
            member.getMemberUserInfo().setMemberAccount(accountInfo.getAccount());
            member.setHcConfig(httpConfig);
            member.setExistId(existHmId);

            memberCrawlers.put(existHmId,member);
            bean.setData(existHmId);
        }
        return bean;
    }
    //endregion

    //region 用户信息
    /**
     * 用户基本信息
     *
     * @param existHmId 盘口会员存在id
     * @return 用户基本信息
     */
    public JsonResultBeanPlus userInfo(String existHmId) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        // 获取用户信息
        MemberCrawler member=  memberCrawlers.get(existHmId);
        if(StringTool.isEmpty(member.getProjectHost(),member.getBrowserCode(),member.getHcConfig())){
            bean = login(existHmId);
            if (!bean.isSuccess()) {
                return bean;
            }
            bean.setData(null);
            bean.setSuccess(false);
        }
        try{
            JSONObject userObj = ComMemberTool.getUserInfo(member);
            if (ContainerTool.isEmpty(userObj)) {
				log.info(message,handicapCode.name(),existHmId, "获取会员账号信息失败");
                bean.putEnum(HcCodeEnum.IBS_404_USER_INFO);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            if(userObj.getInteger("success")==100||StringTool.isEmpty(userObj.get("data"))){
				log.info(message,handicapCode.name(),existHmId, "获取会员账号信息错误,错误内容="+userObj);
                member.setProjectHost(null);
                bean.putEnum(HcCodeEnum.IBS_404_USER_INFO);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            if(!userObj.getJSONObject("data").containsKey("game_2")){
                bean.putEnum(HcCodeEnum.IBS_404_USER_INFO);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            JSONObject userInfo=userObj.getJSONObject("data").getJSONObject("game_2");
            //获取用户信息
            MemberUserInfo memberUserInfo=member.getMemberUserInfo();
            //使用金额
            memberUserInfo.setUsedAmount("0");
            //信用额度
            memberUserInfo.setCreditQuota(userInfo.getString("credit"));
            //可用额度
            memberUserInfo.setUsedQuota( String.valueOf(userInfo.getInteger("usable_credit")));
            //盈亏金额
            memberUserInfo.setProfitAmount(userInfo.getString("profit"));
            //会员盘
            memberUserInfo.setMemberType(userInfo.getString("kind"));
            bean.success();
            bean.setData(memberUserInfo);
        } catch (Exception e) {
			log.error(message,handicapCode.name(),existHmId, "获取基本信息异常，失败原因为:"+e);
            bean.error(e.getMessage());
        }
        return bean;
    }

    /**
     * 获取用户信息
     *
     * @param existHmId 已存在盘口会员id
     * @param flag      执行状态
     * @return 用户信息
     */
    @Override
    public MemberUserInfo getUserInfo(String existHmId, boolean flag) {
        if(!memberCrawlers.containsKey(existHmId)){
            return new MemberUserInfo();
        }
        if(flag){
            //获取用户信息
            JsonResultBeanPlus bean = userInfo(existHmId);
            //获取用户信息失败，返回空
            if (!bean.isSuccess()) {
                return new MemberUserInfo();
            }
        }
        return memberCrawlers.get(existHmId).getMemberUserInfo();
    }
    /**
     * 检验信息,上次成功检验时间超过两分钟，删除用户信息
     *
     * @param existHmId 已存在盘口会员id
     * @return 校验信息
     */
    @Override
    public JsonResultBeanPlus checkInfo(String existHmId) {
        synchronized (existHmId) {
            if(!memberCrawlers.containsKey(existHmId)){
                return new JsonResultBeanPlus();
            }
            JsonResultBeanPlus bean;
            MemberCrawler member= memberCrawlers.get(existHmId);
            //上次校验时间
            long lastTime;
            if(member.getCheckTime()==0){
                lastTime = System.currentTimeMillis();
                member.setCheckTime(lastTime);
            }else{
                lastTime=member.getCheckTime();
            }
            //是否大于上次校验时间
            boolean flag = System.currentTimeMillis() - lastTime > MIN_CHECK_TIME;
            if(flag||member.getMemberUserInfo().getUsedQuota()==null){
                //获取用户信息
                bean = userInfo(existHmId);
                //获取用户信息失败，返回空
                if (!bean.isSuccess()) {
                    return bean;
                }
            }else {
                //使用内存数据
                bean = new JsonResultBeanPlus().success(member.getMemberUserInfo());
            }
            if (ContainerTool.isEmpty(bean.getData())) {
                if (System.currentTimeMillis() - lastTime > MAX_CHECK_TIME) {
                    member.setProjectHost(null);
                    member.setCheckTime(System.currentTimeMillis());
                }
            }
            if (flag) {
                member.setCheckTime(System.currentTimeMillis());
            }
            return bean;

        }
    }

    /**
     * 获取游戏限额信息
     * @param existHmId     已存在盘口会员id
     * @param gameCode      游戏code
     * @return
     */
    @Override
    public JsonResultBeanPlus getQuotaList(String existHmId, GameUtil.Code gameCode) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        if(!memberCrawlers.containsKey(existHmId)){
            bean.putEnum(HcCodeEnum.IBS_404_DATA);
            bean.putSysEnum(HcCodeEnum.CODE_404);
            return bean;
        }
        MemberCrawler member=  memberCrawlers.get(existHmId);
        if(StringTool.isEmpty(member.getProjectHost(),member.getHcConfig())){
            bean.putEnum(HcCodeEnum.IBS_404_DATA);
            bean.putSysEnum(HcCodeEnum.CODE_404);
            return bean;
        }
        String game= ComConfig.BET_ID.get(gameCode.name());
        // 获取配置类
        try {
            JSONArray gameLimit = ComMemberTool.getQuotaList(member, game);
			log.trace(message,handicapCode.name(),existHmId, "游戏【" + game + "】限额信息为：" + gameLimit);
            if (gameLimit==null) {
                bean.putEnum(HcCodeEnum.IBS_404_BET_LIMIT);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            bean.setData(gameLimit);
            bean.success();
        } catch (Exception e) {
			log.error(message,handicapCode.name(),existHmId, "获取游戏限额信息失败,失败原因为：" + e);
            bean.error(e.getMessage());
        }
        return bean;
    }

    /**
     * 获取盘口赔率
     *
     * @param existHmId 已存在盘口会员id
     * @param gameCode  游戏code
     * @param betType   投注类型
     */
    public void getOddsInfo(String existHmId, GameUtil.Code gameCode, String betType, int... index)  {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] >= MAX_RECURSIVE_SIZE) {
			log.error(message,handicapCode.name(),existHmId, "获取赔率信息失败");
            return;
        }
        MemberCrawler member=memberCrawlers.get(existHmId);
        if(StringTool.isEmpty(member.getProjectHost(),member.getBrowserCode(),member.getHcConfig())){
            JsonResultBeanPlus bean = login(existHmId);
            if (!bean.isSuccess()) {
				log.error(message,handicapCode.name(),existHmId, "重新登录失败");
                return;
            }
        }
        //赔率信息
        Map<GameUtil.Code, Map<String, Object>> memberOdds=member.getOdds();
        Map<String, Object> odds;
       if(memberOdds.containsKey(gameCode)){
           odds=memberOdds.get(gameCode);
       }else{
           odds=new HashMap<>();
           memberOdds.put(gameCode,odds);
       }
        //盘口游戏code
        String betUrl=ComConfig.BET_URL.get(gameCode.name());
        String game= BallCodeTool.getGameType(HandicapUtil.Code.COM,gameCode,betType);

        String oddsCode = BallCodeTool.getGameOddsCode(HandicapUtil.Code.COM,gameCode,betType);

        try {
            JSONObject oddsInfo =ComMemberTool.getOddsInfo(member,betUrl,game,oddsCode);
            if (ContainerTool.isEmpty(oddsInfo)) {
				log.info(message,handicapCode.name(),existHmId, "游戏【" + gameCode +"】获取赔率信息失败");
                if (!odds.containsKey(betType)) {
                    member.setProjectHost(null);
                    member.setHcConfig(null);
                    getOddsInfo(existHmId, gameCode, betType, ++index[0]);
                }
                return;
            }
            //判断success状态码
            if(oddsInfo.getInteger("success")!=200||StringTool.isEmpty(oddsInfo.get("data"))||
                    !oddsInfo.getJSONObject("data").containsKey("play_odds")){
				log.info(message,handicapCode.name(),existHmId, "游戏【" + gameCode + "】获取赔率信息失败,错误信息="+oddsInfo);
                if (!odds.containsKey(betType)) {
                    member.setProjectHost(null);
                    member.setHcConfig(null);
                    getOddsInfo(existHmId, gameCode, betType, ++index[0]);
                }
                return;
            }
            JSONObject data=oddsInfo.getJSONObject("data");

            //获取用户信息
            MemberUserInfo userInfo=member.getMemberUserInfo();
            userInfo.setCreditQuota(data.getString("credit"));
            userInfo.setUsedQuota(String.valueOf(data.getInteger("usable_credit")));
            userInfo.setProfitAmount(data.getString("profit"));

            //p_id，赔率
            Map<GameUtil.Code, ComGameInfo> pIdMap=member.getPIdMap();
            ComGameInfo gameInfo;
            if(pIdMap.containsKey(gameCode)){
                gameInfo=pIdMap.get(gameCode);
            }else{
                gameInfo=new ComGameInfo();
                pIdMap.put(gameCode,gameInfo);
            }
            gameInfo.setPeriod(data.getString("nn"));
            gameInfo.setPId(data.getString("p_id"));

            odds.put(betType, data.getJSONObject("play_odds"));
        } catch (Exception e) {
			log.error(message,handicapCode.name(),existHmId, "游戏【" + gameCode + "】获取赔率信息失败,失败原因为："+e);
        }
    }
    /**
     * 获取盘口投注验证信息
     * @param member
     * @param gameCode
     */
    private void getJeuValidate(MemberCrawler member,GameUtil.Code gameCode) throws InterruptedException {
        for(int i=0;i<100;i++){
            if(member.getJeuValidateStatus()){
               break;
            }
            Thread.sleep(100L);
        }
        while (member.getJeuValidateStatus()){
            if(StringTool.isEmpty(member.getJeuValidate())){
                String lId=ComConfig.BET_ID.get(gameCode.name());
                String gameUrl=ComConfig.BET_URL.get(gameCode.name());

                String jeuValidate=ComMemberTool.getJeuValidate(member,lId,gameUrl);
                if(StringTool.isEmpty(jeuValidate)){
					log.error(message,handicapCode.name(),member.getExistId(), "获取jeuValidate失败");
                    return ;
                }
                member.setJeuValidate(jeuValidate);
            }
            member.setJeuValidateStatus(false);
        }
    }

    /**
     * 投注
     *
     * @param existHmId 已存在盘口会员id
     * @param gameCode  游戏code
     * @param roundno   期数字符串
     * @param betItems  投注信息
     * @param betType   投注类型
     * @return 投注结果
     */
    public JsonResultBeanPlus betting(String existHmId, GameUtil.Code gameCode,String  roundno,
                                      List<String> betItems, String betType,int... index) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] >= MAX_RECURSIVE_SIZE) {
			log.error(message,handicapCode.name(),existHmId, "投注失败");
            return bean;
        }
        if(!memberCrawlers.containsKey(existHmId)){
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        MemberCrawler member=memberCrawlers.get(existHmId);

        if(StringTool.isEmpty(member.getProjectHost(),member.getBrowserCode(),member.getHcConfig())){
            bean = login(existHmId);
            if (!bean.isSuccess()) {
                return bean;
            }
			member.setJeuValidate(null);
			member.setJeuValidateStatus(true);
            bean.setData(null);
            bean.setSuccess(false);
        }
        if(ContainerTool.isEmpty(member.getOdds().get(gameCode).get(betType))){
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        //判断期数是否一致,不一致要重新获取pid
        if(!roundno.equals(member.getPIdMap().get(gameCode).getPeriod())){
            getOddsInfo(existHmId,gameCode,betType);
            if(!roundno.equals(member.getPIdMap().get(gameCode).getPeriod())){
                bean.putEnum(CodeEnum.IBS_404_DATA);
                bean.putSysEnum(CodeEnum.CODE_404);
                return bean;
            }
        }
        String betUrl=ComConfig.BET_URL.get(gameCode.name());
        String playpage= BallCodeTool.getGameType(HandicapUtil.Code.COM,gameCode,betType);
        JSONObject odds= (JSONObject) member.getOdds().get(gameCode).get(betType);

        Map<String,Object> betInfos=ComMemberTool.getBetItemInfo(gameCode,betItems,odds);
        betInfos.put("action","put_money");
        betInfos.put("phaseid", member.getPIdMap().get(gameCode).getPId());
        betInfos.put("playpage",playpage);
        try {
            getJeuValidate(member,gameCode);
            if(member.getJeuValidateStatus()){
				log.error(message,handicapCode.name(),existHmId, "游戏【"+gameCode.name()+"】获取JeuValidate失败");
				member.setProjectHost(null);
				return betting(existHmId,gameCode,roundno,betItems,betType,++index[0]);
			}
            betInfos.put("JeuValidate",member.getJeuValidate());

            JSONObject result=ComMemberTool.betting(member,betUrl,betInfos);
			//处理投注结果
			log.trace(message,handicapCode.name(),existHmId, "投注结果为：" + result);
			bean = resultProcess(member, result,gameCode,betItems);
			if(!bean.isSuccess()&&HcCodeEnum.IBS_404_RESULT_PAGE.getCode().equals(bean.getCode())){
				return betting(existHmId,gameCode,roundno,betItems,betType,++index[0]);
			}
        } catch (Exception e) {
			log.error(message,handicapCode.name(),existHmId, "投注失败,失败原因为："+e);
            bean.error(e.getMessage());
        }
        return bean;
    }
	/**
	 * 处理投注结果
	 *
	 * @param member 	  会员信息
	 * @param betInfo   投注结果
	 * @return 投注结果
	 */
	private JsonResultBeanPlus resultProcess(MemberCrawler member, JSONObject betInfo, GameUtil.Code gameCode,List<String> betItems) throws InterruptedException {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		if (ContainerTool.isEmpty(betInfo)) {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		if(StringTool.isEmpty(betInfo.get("data"))){
			member.setProjectHost(null);
			bean.putEnum(HcCodeEnum.IBS_404_BET_INFO);
			bean.putSysEnum(HcCodeEnum.CODE_404);
			return bean;
		}
		if(betInfo.getInteger("success")==200){
			bean.success();
		}else if(betInfo.getInteger("success")==400){
			String game= ComConfig.BET_ID.get(gameCode.name());
			int page = 1;
			int pageSize = 10;
			String masterid="";
			while (pageSize >= 10) {
				String html = ComMemberTool.getIsSettlePage(member,game,page,masterid);
				if(StringTool.isEmpty(html)){
					bean.putEnum(HcCodeEnum.IBS_404_RULE_ERROR);
					bean.putSysEnum(HcCodeEnum.CODE_404);
					break;
				}
				JSONArray data=ComMemberTool.getIsSettleInfo(html,gameCode,member.getPIdMap().get(gameCode).getPeriod());
				if(ContainerTool.isEmpty(data)){
					bean.putEnum(HcCodeEnum.IBS_404_RESULT_PAGE);
					bean.putSysEnum(HcCodeEnum.CODE_404);
					break;
				}
				for(Object bet:data){
					betItems.remove(bet);
				}
				if(ContainerTool.isEmpty(betItems)){
					bean.success();
					break;
				}
				masterid=StringUtils.substringBetween(html,"var masterid = '","';");
				page++;
				pageSize = data.size();
			}
			if(ContainerTool.notEmpty(betItems)){
				bean.putEnum(HcCodeEnum.IBS_404_RESULT_PAGE);
				bean.putSysEnum(HcCodeEnum.CODE_404);
			}
		}else{
			member.setProjectHost(null);
			bean.putEnum(HcCodeEnum.IBS_403_UNKNOWN);
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return bean;
		}
		JSONObject data=betInfo.getJSONObject("data");
		member.setJeuValidate(data.getString("JeuValidate"));
		member.setJeuValidateStatus(true);

		return bean;
	}

}
