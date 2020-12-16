package com.ibm.follow.connector.admin.manage3.handicap.member;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapMemberService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.common.servlet.ModelAndView;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author wwj
 * @Description 盘口会员列表
 * @Date 18:08 2019/11/8
 * @Param
 * @return
 **/
@ActionMapping(value = "/ibm/admin/manage/handicap/member/list", method = HttpConfig.Method.GET)
public class MemberListAction extends CommAdminAction {

    @Override
    public Object run() throws Exception {
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
//        if (!threadJrb.isSuccess()) {
//            return returnJson(threadJrb);
//        }
        String handicapCode = request.getParameter("HANDICAP_CODE_");
        String key = request.getParameter("key");
        try{
            Map<String, Object> data = new HashMap<>(4);

            //盘口
            List<Map<String, Object>> handicaps = new ArrayList<>();
            for (HandicapUtil.Code code : HandicapUtil.codes()) {
                Map<String, Object> handicap = new HashMap<>(2);
                handicap.put("code", code.name());
                handicap.put("name", code.getName());
                handicaps.add(handicap);
            }
            data.put("handicaps", handicaps);

            String handicapId = null;

            if (StringTool.notEmpty(handicapCode)) {
                handicapId = HandicapUtil.id(handicapCode, IbmTypeEnum.MEMBER);
            }
            IbmAdminHandicapMemberService handicapMemberService = new IbmAdminHandicapMemberService();
            List<Map<String, Object>>  handicapUsers = handicapMemberService.listHandicapMembers(IbmTypeEnum.MEMBER, handicapId, key);
            handicapUsers.forEach(map->{
                if("LOGIN".equals(map.get("STATE_"))){
                    map.put("STATE_","在线");
                }else{
                    map.put("STATE_","离线");
                }
            });
            data.put("handicapMembers", handicapUsers);

            //回显数据
            data.put("HANDICAP_CODE_", handicapCode);
            data.put("key", key);

            return new ModelAndView("/pages/com/ibm/admin/manager/handicap/member.jsp", data);
        }catch (Exception e){
            log.error("盘口会员列表错误", e);
            return new JsonResultBeanPlus().error(e.getMessage());
        }
    }
}
