package com.ibm.follow.connector.admin.manage3.periods;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.ibm_sys_periods.entity.IbmSysPeriods;
import com.ibm.follow.servlet.cloud.ibm_sys_periods.service.IbmSysPeriodsService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @Description: 保存 禁止开奖数据至数据库
 * @Author: lxl
 * @Date: 2019-10-11 09:24
 * @Email: 2543908257@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/periods/savePeriodsCondition")
public class PeriodsSaveAction extends CommAdminAction {

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }

        //获取前端数据
        String firstTime = request.getParameter("firstTime");
        String endTime = request.getParameter("endTime");
        String game = request.getParameter("gameCode");

        GameUtil.Code gameCode = GameUtil.value(game);
        if (StringTool.isEmpty(firstTime, endTime,gameCode)) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean;
        }
        savePeriodsCondition(firstTime,endTime,gameCode);
        return bean.success();
    }

    /**
     * 保存 禁止开奖数据至数据库
     * @param firstTime
     * @param endTime
     * @param gameCode
     * @throws ParseException
     */
    public void savePeriodsCondition(String firstTime,String endTime,GameUtil.Code gameCode) throws ParseException {
        Date pauseStartTime = stringToDate(firstTime);
        Date pauseEndTime = stringToDate(endTime);
        try{
            IbmSysPeriods periods = new IbmSysPeriods();
            periods.setGameId(GameUtil.id(gameCode));
            periods.setGameCode(gameCode);
            periods.setPauseStartTime(pauseStartTime);
            periods.setPauseStartTimeLong(pauseStartTime.getTime());
            periods.setPauseEndTime(pauseEndTime);
            periods.setPauseEndTimeLong(pauseEndTime.getTime());
            periods.setCreateTime(new Date());
            periods.setCreateTimeLong(System.currentTimeMillis());
            periods.setUpdateTime(new Date());
            periods.setUpdateTimeLong(System.currentTimeMillis());
            periods.setState(IbmStateEnum.OPEN.name());
            periods.setDesc("添加游戏禁止开奖时间");

            IbmSysPeriodsService periodsService = new IbmSysPeriodsService();
            periodsService.save(periods);
            PeriodDataUtil.data = new HashMap<>(1);
            PeriodDataUtil.getInstance().getGamePauseList(gameCode);
        }catch (Exception e){
            log.info("插入期数查询语句错误");
        }
    }

    /**
     * String 转 Date
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String str) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = df.parse(str);
        return date;
    }

}
