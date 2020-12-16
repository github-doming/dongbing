package com.ibm.follow.connector.admin.manage.base.handicap;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapService;
import com.ibm.follow.servlet.cloud.ibm_handicap.entity.IbmHandicap;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;

/**
 * @Description: 新增盘口
 * @Author: null
 * @Date: 2020-03-21 17:41
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/handicap/save")
public class HandicapSaveAction extends CommAdminAction {
    private String handicapCode, handicapType, handicapIcon, desc;
    private IbmTypeEnum category;
    private double handicapWorth;
    private int sn;
    private Object version;

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        if (valiParameters()) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return super.returnJson(bean);
        }
        try {
            IbmAdminHandicapService handicapService = new IbmAdminHandicapService();
            if (handicapService.isExist(handicapCode, category.name())) {
                bean.putEnum(IbmCodeEnum.IBM_403_EXIST_HANDICAP);
                bean.putSysEnum(IbmCodeEnum.CODE_403);
                return bean;
            }
            desc = this.getClass().getSimpleName().concat(",").concat(adminUser.getUserName()).concat(",新增盘口");

            //新增盘口
            saveHandicap(handicapService);

            bean.success();
        } catch (Exception e) {
            log.error("新增盘口错误", e);
            bean.error(e.getMessage());
        }
        return bean;
    }


    private void saveHandicap(IbmAdminHandicapService handicapService) throws Exception {
        Date nowTime = new Date();
        IbmHandicap handicap = new IbmHandicap();
        handicap.setHandicapName(handicapCode);
        handicap.setHandicapCode(handicapCode);
        handicap.setHandicapCategory(category.name());
        handicap.setHandicapType(handicapType);
        handicap.setHandicapWorthT(NumberTool.intValueT(handicapWorth));
        handicap.setHandicapVersion(version);
        handicap.setHandicapIcon(handicapIcon);
        handicap.setDesc(desc);
        handicap.setSn(sn);
        handicap.setCreateUser(adminUser.getUserName());
        handicap.setCreateTime(nowTime);
        handicap.setCreateTimeLong(System.currentTimeMillis());
        handicap.setUpdateTimeLong(System.currentTimeMillis());
        handicap.setState(IbmStateEnum.OPEN);
        handicapService.save(handicap);
    }

    private boolean valiParameters() {

        //盘口code或名称
        handicapCode = StringTool.getString(dataMap, "handicapCode", "");
        //盘口类型
        handicapType = StringTool.getString(dataMap, "handicapType", "");
        //盘口类别
        String handicapCategory = StringTool.getString(dataMap, "category", "");
        //盘口版本HANDICAP_VERSION_
        version = StringTool.getString(dataMap, "version", "");
        //盘口价值
        handicapIcon = StringTool.getString(dataMap, "handicapIcon", "");

        //盘口价值
        handicapWorth = NumberTool.getDouble(dataMap.get("handicapWorth"), 0);
        //盘口次序
        sn = NumberTool.getInteger(dataMap, "sn", 0);


        if (StringTool.isEmpty(handicapCode, handicapType, handicapCategory, handicapWorth)) {
            return true;
        }
        category = IbmTypeEnum.valueCustomerTypeOf(handicapCategory);

        return category == null;
    }
}
