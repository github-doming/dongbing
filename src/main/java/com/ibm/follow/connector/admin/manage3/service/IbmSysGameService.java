package com.ibm.follow.connector.admin.manage3.service;

import com.ibm.follow.servlet.cloud.ibm_game.service.IbmGameService;
import com.ibm.follow.servlet.cloud.ibm_handicap_game.entity.IbmHandicapGame;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Description: 盘口业务代码
 * @Author: Dongming
 * @Date: 2019-09-03 15:09
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class IbmSysGameService extends IbmGameService {


    public List<Map<String, Object>> findShow() throws Exception {
        String sql = "SELECT * from ibm_game where state_!='DEL' order by UPDATE_TIME_ desc";

        return super.dao.findMapList(sql, null);
    }

}
