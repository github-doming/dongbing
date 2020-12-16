package com.ibm.follow.connector.admin.manage3.periods;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.utils.game.GameUtil;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: 查询期数
 * @Author: lxl
 * @Date: 2019-10-11 09:24
 * @Email: 2543908257@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/periods/findPeriodsFind")
public class PeriodsFindAction extends CommAdminAction {
    /**
     * 基准期数 2019-09-01第一期 @@ 2019-09-01 00:00:00
     */
    int benchmarkPeriods = 0;
    Long timePeriods = 0L;

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }

        //获取前端数据
        String findTime = request.getParameter("time");
        String game = request.getParameter("gameCode");
        String findNumber = request.getParameter("number");
		GameUtil.Code gameCode =GameUtil.value(game);
        if (StringTool.isEmpty(findTime,gameCode)) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean;
        }
        if (GameUtil.Code.PK10.equals(gameCode)){
            benchmarkPeriods = 738280;
            timePeriods = 1567267200000L;
        }

        List<Map<String, Object>> list = findData(findTime, findNumber, gameCode);
        if (ContainerTool.isEmpty(list)) {
            bean.setMessage("查询时间不开奖");
        } else {
            bean.success(list);
        }
        return bean;
    }

    public List<Map<String, Object>> findData(String findTime, String findNumber,GameUtil.Code gameCode) throws Exception {

        //获取前端数据进行转化
        Date findTimeDate = stringToDate(findTime);

        List<Map<String, Object>> listAll = new ArrayList<>();
        if (!findPeriodsRule(findTimeDate.getTime(),gameCode)) {
            List<Map<String, Object>> list = findPeriods(findTimeDate.getTime(),gameCode);
            if (list.size() == 0) {
                //查询条件为输入时间之前无间隔时间的
                listAll = notIntervalTime(findTimeDate, benchmarkPeriods, timePeriods, findNumber);
            } else {
                listAll = intervalTime(findTimeDate, benchmarkPeriods, timePeriods, findNumber, list);
            }
        } else {
            log.info("查询时间不开奖");
            return null;
        }
        return listAll;
    }

    /**
     * 查询条件为输入时间之前无间隔时间的
     *
     * @param findTimeDate     输入时间
     * @param benchmarkPeriods 基准期数
     * @param timePeriods      基准时间
     * @param findNumber       查询条数
     * @return
     */
    public List<Map<String, Object>> notIntervalTime(Date findTimeDate, int benchmarkPeriods,
                                                     Long timePeriods, String findNumber) {
        List<Map<String, Object>> listAll = new ArrayList<>();
        Date startTime = DateTool.getHm(findTimeDate, "9:30");
        Long startTimeLong = startTime.getTime();
        int dayDifference = DateTool.getDaysBetween(new Date(timePeriods), startTime).intValue();
        int todayPeriod = 0;
        if (startTime.before(findTimeDate)) {
            todayPeriod = (int) DateTool.getPeriod(startTime, findTimeDate, 20 * 60 * 1000L).intValue();
        }
        int number = NumberTool.getInteger(findNumber);
        int tiaojian1 = getChuShu(number);
        int tiaojian2 = todayPeriod;
        for (int i = 0; i < tiaojian1; i++) {
            for (int j = tiaojian2; j >= 0; j--) {
                if (listAll.size() >= number) {
                    return listAll;
                }
                int periods = benchmarkPeriods + (dayDifference - i) * 44 + todayPeriod - listAll.size();
                Long a = startTimeLong + 20 * 60 * 1000L * (todayPeriod - listAll.size());
                String drawTime = stampToDate(String.valueOf(a));
                if (a < startTimeLong) {
                    break;
                }
                if (a < 1549848600000L) {
                    return listAll;
                }
                Map<String, Object> map = new HashMap<>(2);
                map.put("periods", periods);
                map.put("drawTime", drawTime);
                listAll.add(map);
            }
            tiaojian2 = 43;
            todayPeriod = todayPeriod + 44;
            if ((number - listAll.size()) >= 0) {
                tiaojian1 = tiaojian1 + 1;
            }
            startTimeLong = startTimeLong - new Date(24 * 60 * 60 * 1000L).getTime();
        }
        return listAll;
    }

    /**
     * 查询条件为输入时间之前you间隔时间的
     *
     * @param findTimeDate     输入时间
     * @param benchmarkPeriods 基准期数
     * @param timePeriods      基准时间
     * @param findNumber       查询条数
     * @param list             条件
     * @return
     */
    public List<Map<String, Object>> intervalTime(Date findTimeDate, int benchmarkPeriods, Long timePeriods,
                                                  String findNumber, List<Map<String, Object>> list) throws ParseException {
        List<Map<String, Object>> listAll = new ArrayList<>();
        int days = 0;
        int outTiaoJian = list.size();
        int todayPeriod = 0;
        int count = list.size();
        Long betweenTimeOne = 0L;
        Long betweenTimeTwo = 0L;
        int dayOne = 0;
        Date startTime = DateTool.getHm(findTimeDate, "9:30");
        Long startTimeLong = startTime.getTime();
        int dayDifference = DateTool.getDaysBetween(new Date(timePeriods), startTime).intValue();
        if (startTime.before(findTimeDate)) {
            todayPeriod = (int) DateTool.getPeriod(startTime, findTimeDate, 20 * 60 * 1000L).intValue();
        }
        for (Map<String, Object> map : list) {
            days = days + Integer.parseInt((map.get("BETWEEN_DAYS").toString()));
        }
        for (int out = 0; out < outTiaoJian; out++) {
            if (count > 0) {
                betweenTimeOne = (Long) list.get(out).get("PAUSE_START_TIME_LONG_");
                betweenTimeTwo = (Long) list.get(out).get("PAUSE_END_TIME_LONG_");
                dayOne = (Integer) list.get(out).get("BETWEEN_DAYS");
            }

            int number = NumberTool.getInteger(findNumber);
            String drawTime;
            int tiaojian1 = getChuShu(number);
            int tiaojian2 = todayPeriod;
            day:
            for (int i = 0; i < tiaojian1; i++) {
                for (int j = tiaojian2; j >= 0; j--) {
                    if (listAll.size() >= number) {
                        return listAll;
                    }
                    int periods = benchmarkPeriods + (dayDifference - days) * 44 + todayPeriod - listAll.size();
                    Long a = startTimeLong + 20 * 60 * 1000L * (todayPeriod - listAll.size());
                    drawTime = stampToDate(String.valueOf(a));
                    Date drawTimeDate = stringToDate(drawTime);
                    if (a < startTimeLong) {
                        break;
                    }
                    if (a < 1549848600000L) {
                        return listAll;
                    }
                    if (drawTimeDate.getTime() >= betweenTimeOne && drawTimeDate.getTime() <= betweenTimeTwo) {
                        startTimeLong = startTimeLong - dayOne * 24 * 60 * 60 * 1000L;
                        outTiaoJian = outTiaoJian + 1;
                        count = count - 1;
                        break day;
                    }
                    Map<String, Object> mapTwo = new HashMap<>(2);
                    mapTwo.put("periods", periods);
                    mapTwo.put("drawTime", drawTime);
                    listAll.add(mapTwo);
                }
                dayDifference = dayDifference - 1;
                tiaojian2 = 43;
                todayPeriod = todayPeriod + 44;
                if ((number - listAll.size()) >= 0) {
                    tiaojian1 = tiaojian1 + 1;
                }
                startTimeLong = startTimeLong - new Date(24 * 60 * 60 * 1000L).getTime();
            }
        }
        return listAll;
    }

    /**
     * 判断是否为在时间段之内 或者 小于基准时间
     *
     * @param findTime 输入查询时间
     * @return
     */
    public boolean findPeriodsRule(long findTime,GameUtil.Code gameCode) throws SQLException {
        List<Map<String, Object>> maps = PeriodDataUtil.getInstance().getGamePauseList(gameCode);
        for (Map<String, Object> map : maps) {
            Long betweenTimeOne = (Long) map.get("PAUSE_START_TIME_LONG_");
            Long betweenTimeTwo = (Long) map.get("PAUSE_END_TIME_LONG_");
            if (betweenTimeOne <= findTime && betweenTimeTwo >= findTime) {
                return true;
            }
            if (timePeriods > findTime) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否为
     *
     * @param findTime 输入查询时间
     * @return
     */
    public List<Map<String, Object>> findPeriods(long findTime,GameUtil.Code gameCode) throws SQLException {
        //所有数据
        List<Map<String, Object>> maps = PeriodDataUtil.getInstance().getGamePauseList(gameCode);
        //所有数据
        List<Map<String, Object>> list = new ArrayList<>();

        for (Map<String, Object> map : maps) {
            if ((long) map.get("PAUSE_END_TIME_LONG_") < findTime) {
                Map<String, Object> hashMap = new HashMap<>(2);
                int betweenDays = DateTool.getDaysBetween(new Date((Long)map.get("PAUSE_START_TIME_LONG_")),
                            new Date((Long)map.get("PAUSE_END_TIME_LONG_"))).intValue();
                hashMap.put("BETWEEN_DAYS", betweenDays+1);
                hashMap.put("PAUSE_START_TIME_LONG_", map.get("PAUSE_START_TIME_LONG_"));
                hashMap.put("PAUSE_END_TIME_LONG_", map.get("PAUSE_END_TIME_LONG_"));
                list.add(hashMap);
            }
        }
        return list;
    }


    /**
     * String 转 Date
     *
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String str) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = df.parse(str);
        return date;
    }

    /**
     * 数据库string转Date
     *
     * @param s
     * @return
     * @throws ParseException
     */
    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public int getChuShu(int number) {
        int cycle = 44;
        if (number % cycle == 0) {
            return number / cycle;
        } else {
            return number / cycle + 1;
        }
    }

}
