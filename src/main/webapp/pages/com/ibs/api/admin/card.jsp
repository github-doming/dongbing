<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp" %>
    <title>接口测试页</title>
</head>
<body>
<div class="container-fluid">
    <h1>api接口说明</h1>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>接口名称</th>
            <th>接口调用示例</th>
            <th>入参说明</th>
            <th>返参说明</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td colspan="4">充值卡管理平台</td>
        </tr>

        <tr>
            <td>
                <p>用户登录验证</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/cloud/recharge/pc/index?json={token:\'64e03c1450234e168537df37ed4cd9d1\',name:\'1\'}');"
                    > http://IP:PORT/PROJECT/cloud/recharge/pc/index</a>
                </p>
                <p> 用户登录验证</p>
            </td>
            <td>
                <p> name：用户名</p>
                <p> token：令牌</p>
            </td>
            <td>
                <p>用户类型</p>
            </td>
        </tr>

        <tr>
            <td colspan="4">充值卡管理平台--用户管理</td>
        </tr>

        <tr>
            <td>
                <p>1.充值卡管理员信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/cloud/recharge/pc/account/info?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/cloud/recharge/pc/account/info</a>
                </p>
                <p> 充值卡管理员信息</p>
            </td>
            <td>
            </td>
            <td>
                <p> adminInfo{管理员基本信息}</p>
                <p> adminPrices{下级点卡价格信息}</p>
                <p> parentPrices{点卡价格信息}</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>2.修改充值卡管理员价格信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/cloud/recharge/pc/account/editPrice?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/cloud/recharge/pc/account/editPrice</a>
                </p>
                <p> 修改充值卡管理员价格信息</p>
            </td>
            <td>
                <p>subUserId:下级Id(修改自己传空)</p>
                <p>priceInfos:价格信息</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>3.修改充值卡管理员基本信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/cloud/recharge/pc/account/edit?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/cloud/recharge/pc/account/edit</a>
                </p>
                <p> 修改充值卡管理员基本信息</p>
            </td>

            <td>
                <p> subUserId : 用户Id</p>
                <p> password : 密码</p>
                <p> isAdd : 是否允许添加子代</p>
                <p> userState : 用户状态</p>
                <p> userName : 用户名称</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>4.保存充值卡管理员</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/cloud/recharge/pc/account/save?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/cloud/recharge/pc/account/save</a>
                </p>
                <p> 保存充值卡管理员</p>
            </td>
            <td>
                <p>accountName:用户名</p>
                <p>password:密码</p>
                <p>aliasName:用户别名</p>
                <p>isAdd:是否允许添加子代</p>
                <p>userState:用户状态</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>5.获取个人或子代理信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/cloud/recharge/pc/account/subInfo?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/cloud/recharge/pc/account/subInfo</a>
                </p>
                <p> 获取个人或子代理信息</p>
            </td>
            <td>
                <p>subUserId:下级用户Id</p>
            </td>
            <td>
                <p>subAdminInfo{下级用户基本信息}</p>
                <p>subAdminPrices{下级价格信息}</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>6.充值卡管理下级列表</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/cloud/recharge/pc/account/subList?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/cloud/recharge/pc/account/subList</a>
                </p>
                <p> 充值卡管理下级列表</p>
            </td>
            <td>
            </td>
            <td>

            </td>
        </tr>

        <tr>
            <td>
                <p>7.获取用户拥有的卡种卡种列表</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/cloud/recharge/pc/card/treeList?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/cloud/recharge/pc/card/treeList</a>
                </p>
                <p> 卡种列表</p>
            </td>
            <td>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td colspan="4">充值卡管理平台---充值管理-卡种</td>
        </tr>

        <tr>
            <td>
                <p>1.充值卡树创建</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/cloud/recharge/pc/card/treeSave?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/cloud/recharge/pc/card/treeSave</a>
                </p>
                <p> 充值卡树创建</p>
            </td>
            <td>
                <p>cardType:卡种类型</p>
                <p>cardPrice:卡种价格</p>
                <p>cardName:卡种名称</p>
                <p>desc:备注</p>
                <p>cardTreePoint:卡种点数</p>
                <p>cardSn:卡种序号</p>
                <p>isDirect:是否允许直充</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>2.修改卡种树信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/cloud/recharge/pc/card/treeEdit?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/cloud/recharge/pc/card/treeEdit</a>
                </p>
                <p> 修改卡种树信息</p>
            </td>
            <td>
                <p>cardTreeId:卡种ID</p>
                <p>cardTreeType:卡种类型</p>
                <p>cardTreePrice:卡种价格</p>
                <p>cardTreeName:卡种名称</p>
                <p>desc:备注</p>
                <p>cardTreePoint:卡种点数</p>
                <p>cardTreeSn:卡种序号</p>
                <p>isDirect:是否允许直充</p>
                <p>cardState:卡种状态</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>3.删除卡种信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/cloud/recharge/pc/card/treeDelete?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/cloud/recharge/pc/card/treeDelete</a>
                </p>
                <p> 删除卡种信息</p>
            </td>

            <td>
                <p>cardTreeId:卡种ID</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>4.用户直充-卡种列表</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/cloud/recharge/pc/card/treeDirect?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/cloud/recharge/pc/card/treeDirect</a>
                </p>
                <p> 卡种列表</p>
            </td>
            <td>
            </td>
            <td>
                <p> CARD_TREE_NAME_：卡种名称</p>
                <p> CARD_TREE_ID_：卡种Id</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>5.获取用户卡类列表(用户下拉列表使用)</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/cloud/recharge/pc/card/treeSelect?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/cloud/recharge/pc/card/treeSelect</a>
                </p>
                <p> 获取用户卡类列表(用户下拉列表使用)</p>
            </td>
            <td>

            </td>
            <td>
                <p> CARD_TREE_NAME_：卡种名称</p>
                <p> CARD_TREE_ID_：卡种Id</p>
            </td>
        </tr>


        <tr>
            <td colspan="4">充值卡管理平台---充值管理---点卡</td>
        </tr>

        <tr>
            <td>
                <p>1.查询点卡列表</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/cloud/recharge/pc/card/list?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/cloud/recharge/pc/card/list</a>
                </p>
                <p> 查询点卡列表</p>
            </td>
            <td>
                <p> cardPassword：充值卡密码</p>
                <p> cardTreeId：卡种分类</p>
                <p> cardState：状态</p>
            </td>
            <td>
                <p>CARD_RECHARGE_ID_:充值卡ID</p>
                <p>CARD_TREE_ID_:卡种ID</p>
                <p>CARD_TREE_NAME_:卡种名称</p>
                <p>CARD_TREE_POINT_:卡种点数</p>
                <p>CARD_PASSWORD_:充值卡密码</p>
                <p>USE_USER_ID_:使用用户Id</p>
                <p>USER_NAME_:使用人</p>
                <p>CARD_RECHARGE_STATE_:充值卡状态</p>
                <p>CREATE_TIME_LONG_:创建时间</p>
                <p>UPDATE_TIME_LONG_:修改时间</p>
                <p>CREATOR_NAME_:创建者名</p>
                <p>OWNER_NAME_:拥有者名</p>
                <p>DESC_:备注</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>2.新增充值卡--制卡</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/cloud/recharge/pc/card/save?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/cloud/recharge/pc/card/save</a>
                </p>
                <p> 制卡</p>
            </td>
            <td>
                <p> cardNum：数量</p>
                <p> cardTreeId：卡种分类Id</p>
                <p> cardState：状态</p>
                <p> desc：备注</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>3.修改充值卡信息</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/cloud/recharge/pc/card/edit?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/cloud/recharge/pc/card/edit</a>
                </p>
                <p> 修改充值卡信息</p>
            </td>
            <td>
                <p> cardId：卡id</p>
                <p> cardState：卡使用状态</p>
                <p> state：状态</p>
                <p> desc：备注</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>4.新增充值卡--用户直充</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/cloud/recharge/pc/card/direct?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/cloud/recharge/pc/card/direct</a>
                </p>
                <p> 用户直充</p>
            </td>
            <td>
                <p> directUserId：需要直充的用户ID（5. 检查是否存在用户名获取）</p>
                <p> directUserName：需要直充的用户名（/cloud/recharge/pc/card/treeDirect）</p>
                <p> cardTreeId：卡种Id</p>
                <p> desc：备注</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>5. 检查是否存在用户名</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/cloud/user/out/checkHasName?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/cloud/user/out/checkHasName</a>
                </p>
                <p>  检查是否存在用户名</p>
            </td>
            <td>
                <p> checkName：用户名</p>
                <p> userType：用户类型(用户直-USER)</p>
            </td>
            <td>
                <p> 用户名ID</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>6.新增充值卡--特殊开卡 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/cloud/recharge/pc/card/direct?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/cloud/recharge/pc/card/direct</a>
                </p>
                <p> 特殊开卡 </p>
            </td>
            <td>
                <p> cardNum：数量</p>
                <p> cardTreeId：卡种分类Id</p>
                <p> agentId：代理Id（20.获取子级的信息）</p>
                <p> cardPrice：卡价格</p>
                <p> desc：备注</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>7.获取子级的信息 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/cloud/recharge/pc/account/subSelect?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/cloud/recharge/pc/account/subSelect</a>
                </p>
                <p> 获取子级的信息 </p>
            </td>
            <td>
            </td>
            <td>
                <p> APP_USER_ID_：用户Id</p>
                <p> USER_ALIAS_：用户别称</p>
                <p> PARENT_USER_ID_：上级Id</p>

            </td>
        </tr>

        <tr>
            <td colspan="4">充值卡管理平台---充值管理---报表</td>
        </tr>
        <tr>
            <td>
                <p>1.代理分类查询充值卡报表 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/cloud/recharge/pc/card/reportListAgent?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/cloud/recharge/pc/card/reportListAgent</a>
                </p>
                <p> 代理分类查询充值卡报表 </p>
            </td>
            <td>
                <p> cardTreeId：卡种Id</p>
                <p> agentId：代理id</p>
                <p> timeStart：开始时间</p>
                <p> timeEnd：结束时间</p>
            </td>
            <td>
                <p> activateCardTotal：激活总数</p>
                <p> pointTotal：总积分</p>
                <p> priceDownTotal：下收金额总数</p>
                <p>reportItems{USER_NAME_:用户名,USER_ID_:ID,CREATE_TOTAL_:创建数据，ACTIVATE_TOTAL_：激活数据，</p>
                <p>PRICE_TOTAL_T_：上缴金额，POINT_TOTAL_：点数,PRICE_TOTAL_DOWN_T_:下收金额,}</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>2.代理分类查询充值卡详情 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/cloud/recharge/pc/card/reportItemAgent?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/cloud/recharge/pc/card/reportItemAgent</a>
                </p>
                <p> 代理分类查询充值卡详情 </p>
            </td>
            <td>
                <p> cardTreeId：卡种Id</p>
                <p> agentId：代理id</p>
                <p> timeStart：开始时间</p>
                <p> timeEnd：结束时间</p>
            </td>
            <td>
                <p> activateCardTotal：激活总数</p>
                <p> pointTotal：总积分</p>
                <p> priceDownTotal：下收金额总数</p>
                <p>reportItems{USER_NAME_:用户名,USER_ID_:ID,CREATE_TOTAL_:创建数据，ACTIVATE_TOTAL_：激活数据，</p>
                <p>PRICE_TOTAL_T_：上缴金额，POINT_TOTAL_：点数,PRICE_TOTAL_DOWN_T_:下收金额,}</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>3.卡种分类查询充值卡报表 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/cloud/recharge/pc/card/reportListCard?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/cloud/recharge/pc/card/reportListCard</a>
                </p>
                <p> 卡种分类查询充值卡报表 </p>
            </td>
            <td>
                <p> cardTreeId：卡种Id</p>
                <p> agentId：代理id</p>
                <p> timeStart：开始时间</p>
                <p> timeEnd：结束时间</p>
            </td>
            <td>
                <p> activateCardTotal：激活总数</p>
                <p> pointTotal：总积分</p>
                <p> priceDownTotal：下收金额总数</p>
                <p>reportItems{USER_NAME_:用户名,USER_ID_:ID,CREATE_TOTAL_:创建数据，ACTIVATE_TOTAL_：激活数据，</p>
                <p>PRICE_TOTAL_T_：上缴金额，POINT_TOTAL_：点数,PRICE_TOTAL_DOWN_T_:下收金额,}</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>4.卡种分类查询充值卡详情 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/cloud/recharge/pc/card/reportItemCard?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/cloud/recharge/pc/card/reportItemCard</a>
                </p>
                <p> 卡种分类查询充值卡详情 </p>
            </td>
            <td>
                <p> cardTreeId：卡种Id</p>
                <p> agentId：代理id</p>
                <p> timeStart：开始时间</p>
                <p> timeEnd：结束时间</p>
            </td>
            <td>
                <p> activateCardTotal：激活总数</p>
                <p> pointTotal：总积分</p>
                <p> priceDownTotal：下收金额总数</p>
                <p>reportItems{USER_NAME_:用户名,USER_ID_:ID,CREATE_TOTAL_:创建数据，ACTIVATE_TOTAL_：激活数据，</p>
                <p>PRICE_TOTAL_T_：上缴金额，POINT_TOTAL_：点数,PRICE_TOTAL_DOWN_T_:下收金额,}</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>5.查询特殊卡种的报表 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/cloud/recharge/pc/card/reportListSpecial?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/cloud/recharge/pc/card/reportListSpecial</a>
                </p>
                <p> 查询特殊卡种的报表 </p>
            </td>
            <td>
                <p> cardTreeId：卡种Id</p>
                <p> timeStart：开始时间</p>
                <p> timeEnd：结束时间</p>
            </td>
            <td>
                <p> pointTotal：总积分</p>
                <p> priceDownTotal：下收金额总数</p>
                <p>reportItems{USER_NAME_:用户名,USER_ID_:ID,CREATE_TOTAL_:创建数据，ACTIVATE_TOTAL_：激活数据，</p>
                <p>PRICE_TOTAL_T_：上缴金额，POINT_TOTAL_：点数,PRICE_TOTAL_DOWN_T_:下收金额,}</p>
            </td>
        </tr>

        </tbody>
    </table>
</div>
</body>
<script type="text/javascript">
    function findUrl(url) {
        console.log(url);
        url = encodeURI(url);
        window.open(url);
        return url;
    }
</script>
</html>
