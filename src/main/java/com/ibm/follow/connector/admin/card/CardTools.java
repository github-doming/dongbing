package com.ibm.follow.connector.admin.card;

import com.google.common.collect.ImmutableMap;
import com.ibm.common.enums.IbmTypeEnum;

import java.util.Map;

/**
 * @Description:
 * @Author: wwj
 * @Date: 2020/4/9 10:57
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
public class CardTools {
    public static Map<String, Integer> CARD_ADMIN_TYPE = ImmutableMap.<String, Integer>builder().put("ADMIN", 50)
            .put("PARTNER", 80).put("AGENT", 90).build();

    public static final int CARD_ADMIN = 50;
    public static final int PARTNER_GRADE = 80;
    public static final int AGENT_GRADE = 90;

    public static String selectUserGroup(int permGrade) {
        if (permGrade < PARTNER_GRADE) {
            return IbmTypeEnum.CARD_ADMIN.name();
        } else if (permGrade == PARTNER_GRADE) {
            return IbmTypeEnum.CARD_PARTNER.name();
        } else if (permGrade == AGENT_GRADE) {
            return IbmTypeEnum.CARD_AGENT.name();
        } else {
            return IbmTypeEnum.FALSE.name();
        }

    }
}
