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
            <td colspan="4">平台开卡</td>
        </tr>

        <tr>
            <td>
                <p>1.新建卡类《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/card/addCardTree?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/card/addCardTree</a>
                </p>
                <p> 新建卡类</p>
            </td>
            <td>
                <p> cardName : 名称</p>
                <p> cardType : 类型</p> ADMIN_PARTNER_AGENT
                <p> cardPrice : 价格(数字)</p>
                <P> cardTreePoint:点数(数字) </P>
                <P> cardSn : 次序</P>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>2.获取卡种列表《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/card/listCardTree?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/card/listCardTree</a>
                </p>
                <p> 获取卡种列表《</p>
            </td>
            <td>
            </td>
            <td>
                <p> IBM_CARD_TREE_ID_ : cardTreeId</p>
                <p> CARD_TREE_NAME_ : 卡种名称</p>
                <p> CARD_PRICE_T_ : 价格</p>
                <P> CARD_TREE_POINT_:点数 </P>
                <P> CARD_TREE_TYPE_ : 分类</P>
                <P> SN_ : 次序</P>
                <P> CREATE_TIME_LONG_ : 创建时间戳</P>
            </td>
        </tr>

        <tr>
            <td>
                <p>3.修改卡种《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/card/editCardTree?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/card/editCardTree</a>
                </p>
                <p> 修改卡种《</p>
            </td>
            <td>
                <p> cardTreeId : cardTreeId</p>
                <p> cardTreePrice : 价格</p>
                <P> cardTreeType : 分类</P>
                <P> cardState : 状态</P>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>4.删除卡种《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/card/deleteCardTree?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/card/deleteCardTree</a>
                </p>
                <p> 删除卡种《</p>
            </td>
            <td>
                <p> cardTreeId : cardTreeId</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>5.新增点卡《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/card/addCard?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/card/addCard</a>
                </p>
                <p> 新增点卡《</p>
            </td>
            <td>
                <p> cardTreeId : cardTreeId</p>
                <p> cardTreeName : 卡种名称</p>
                <p> cardNum : 数量</p>
                <p> cardState : 分配状态（）</p>
                <p> agentId : 代理Id</p>
                <p> cardPrice : 价格</p>
            </td>
            <td>
                <p> 卡密列表</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>5.1 获取用户卡类列表《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/card/showUserCardTree?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/card/showUserCardTree</a>
                </p>
                <p> 获取用户卡类列表《</p>
            </td>
            <td>
                <p> IBM_CARD_TREE_ID_ ：分类Id </p>
                <p> CARD_TREE_NAME_ ：分类名</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>5.2 管理员获取代理列表《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/card/showAllAgentInfo?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/card/showAllAgentInfo</a>
                </p>
                <p> 管理员获取代理列表《</p>
            </td>
            <td>
                <p> USER_NAME_ ：代理名 </p>
                <p> APP_USER_ID_ ：代理ID</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>6 获取点卡列表《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/card/listCard?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/card/listCard</a>
                </p>
                <p> 获取点卡列表《</p>
            </td>
            <td>
                <p> 默认查询不传参</p>
                <br>
                <p> 筛选查询</p>
                <p> cardName : 卡种分类</p>
                <p> cardState : 卡种状态</p>
                <p> timeStart : 开始时间</p>
                <p> timeend : 截止时间</p>
                <br>
                <p> 主键查询（查看功能）</p>
                <p> cardId : 点卡主键（查看单个点卡是传参）</p>
            </td>
            <td>
                <p> IBM_CARD_ID_ : cardId 点卡主键</p>
                <p> CARD_TREE_NAME_ : 卡种名字</p>
                <p> CARD_TREE_POINT_ : 卡种点数</p>
                <p> CARD_TREE_NAME_ : 卡种名字</p>
                <p> CARD_PASSWORD_ : 卡密</p>
                <p> CREATE_TIME_LONG_ : 创建时间戳</p>
                <p> USE_USER_NAME_ : 用卡人</p>
                <p> STATE_ : 卡种状态 </p>
            </td>
        </tr>

        <tr>
            <td>
                <p>7 修改删除点卡《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/card/editCard?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/card/editCard</a>
                </p>
                <p> 修改点卡《</p>
            </td>
            <td>
                <p> cardState : 状态 (禁用：DISABLE,分配:ALLOT,删除：DEL,默认：DEF(包含未分配))</p>
                <p> cardId : ID主键</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>8.1 报表查询-按代理分类《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/card/reportByAgent?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/card/reportByAgent</a>
                </p>
                <p> 报表查询 </p>
            </td>
            <td>
                <p> cardTreeId :分类Id</p>
                <p> agentId : 代理Id （ALL 空值）</p>
                <p> timeStart : 开始时间</p>
                <p> timeEnd : 截止时间</p>
                <p> pageIndex : 起始页</p>
                <p> pageSize : 页大小</p>
            </td>
            <td>
                <p> totalPrice: 总价格</p>
                <p> totalPoint: 总点数 </p>
                <p> rows：{USER_NAME_ : 代理名</p>
                <p> CARD_TREE_NAME_ : 卡类名称</p>
                <p> CARD_TOTAL_ : 创建张数</p>
                <p> CARD_ACTIVATE_TOTAL_ : 激活张数</p>
                <p> POINT_TOTAL_T_ : 点数</p>
                <p> PRICE_TOTAL_T_ : 价格 }</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>8.2 报表查询-按卡种分类《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/card/reportByCardTree?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/card/reportByCardTree</a>
                </p>
                <p> 报表查询 </p>
            </td>
            <td>
                <p> cardTreeId :分类Id</p>
                <p> agentId : 代理Id （ALL 空值）</p>
                <p> timeStart : 开始时间</p>
                <p> timeEnd : 截止时间</p>
            </td>
            <td>
                <p> totalPrice: 总价格</p>
                <p> totalPoint: 总点数 </p>
                <p> rows：{USER_NAME_ : 代理名</p>
                <p> CARD_TREE_NAME_ : 卡类名称</p>
                <p> CARD_TOTAL_ : 创建张数</p>
                <p> CARD_ACTIVATE_TOTAL_ : 激活张数</p>
                <p> POINT_TOTAL_T_ : 点数</p>
                <p> PRICE_TOTAL_T_ : 价格 }</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>8.3 报表查询-特殊开卡《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/card/reportSpecial?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/card/reportSpecial</a>
                </p>
                <p> 报表查询 </p>
            </td>
            <td>
                <p> cardTreeId :分类Id</p>
                <p> agentId : 代理Id （ALL 空值）</p>
                <p> timeStart : 开始时间</p>
                <p> timeEnd : 截止时间</p>
                <p> pageIndex : 起始页</p>
                <p> pageSize : 页大小</p>
            </td>
            <td>
                <p> totalPrice: 总价格</p>
                <p> totalPoint: 总点数 </p>
                <p> rows：{USER_NAME_ : 代理名</p>
                <p> CARD_TREE_NAME_ : 卡类名称</p>
                <p> CARD_TOTAL_ : 创建张数</p>
                <p> CARD_ACTIVATE_TOTAL_ : 激活张数</p>
                <p> POINT_TOTAL_T_ : 点数</p>
                <p> PRICE_TOTAL_T_ : 价格 }</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>8.4 报表查询—查看代理报表详情《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/card/reportAgentItem?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/card/reportAgentItem</a>
                </p>
                <p> 报表查询 </p>
            </td>
            <td>
                <p> cardTreeId :分类Id</p>
                <p> agentId : 代理Id （ALL 空值）</p>
                <p> timeStart : 开始时间</p>
                <p> timeEnd : 截止时间</p>
            </td>
            <td>
                <p> totalPrice: 总价格</p>
                <p> totalPoint: 总点数 </p>
                <p> rows：{USER_NAME_ : 代理名</p>
                <p> CARD_TREE_NAME_ : 卡类名称</p>
                <p> CARD_TOTAL_ : 创建张数</p>
                <p> CARD_ACTIVATE_TOTAL_ : 激活张数</p>
                <p> POINT_TOTAL_T_ : 点数</p>
                <p> PRICE_TOTAL_T_ : 价格 }</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>8.5 报表查询-按分类查看详情《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/card/cardTreeReportItem?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/card/cardTreeReportItem</a>
                </p>
                <p> 报表查询 </p>
            </td>
            <td>
                <p> cardTreeId :分类Id</p>
                <p> agentId : 代理Id （ALL 空值）</p>
                <p> timeStart : 开始时间</p>
                <p> timeEnd : 截止时间</p>
                <p> pageIndex : 起始页</p>
                <p> pageSize : 页大小</p>
            </td>
            <td>
                <p> totalPrice: 总价格</p>
                <p> totalPoint: 总点数 </p>
                <p> rows：{USER_NAME_ : 代理名</p>
                <p> CARD_TREE_NAME_ : 卡类名称</p>
                <p> CARD_TOTAL_ : 创建张数</p>
                <p> CARD_ACTIVATE_TOTAL_ : 激活张数</p>
                <p> POINT_TOTAL_T_ : 点数</p>
                <p> PRICE_TOTAL_T_ : 价格 }</p>
            </td>
        </tr>

        <tr>
            <td colspan="4">ADMIN 充值 -- 用户直充</td>
        </tr>

        <tr>
            <td>
                <p>用户验证 </p>
                <p> - 用户验证 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/user/check?json={token:\'30301c8766674e90947d22136145b0ee\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/user/check</a>
                </p>
                <p> 用户验证 </p>
                <p> /ibm/admin/manage/user/check</p>
            </td>
            <td>
                <p> appUserName：用户名 </p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>指定用户开卡 </p>
                <p> - 指定用户开卡 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/card/card4User?json={token:\'30301c8766674e90947d22136145b0ee\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/card/card4User</a>
                </p>
                <p> 指定用户开卡 </p>
                <p> /ibm/admin/manage/card/card4User</p>
            </td>
            <td>
                <p> userName：用户名 </p>
                <p> cardType：卡类型 日卡 DAY 周卡 WEEK 月卡MONTH</p>
            </td>
            <td>
            </td>
        </tr>

        <!--<editor-fold desc="账户管理">-->
        <tr>
            <td colspan="4">账户管理接口 《cmd 必须添加项，每一个接口都添加 - show：查询、展示&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- edit : 修改、新增、删除</td>
        </tr>
        <tr>
            <td>
                <p>1.充值卡管理员信息 </p>
                <p> - 下级管理首页信息 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/card/agent/info?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/card/agent/info</a>
                </p>
                <p> 充值卡管理员信息 </p>
                <p> /ibm/admin/manage/card/agent/info </p>
            </td>
            <td>
                <p> token：用户令牌</p>
            </td>
            <td>
                <p> adminInfo - 管理员信息</p>
                <p> APP_USER_NAME_：用户账户</p>
                <p> APP_GROUP_NAME_：角色名称</p>
                <p> IS_ADD_：允许添加子代</p>
                <p> PARENT_USER_NAME_：上级代理</p>
                <p> STATE_：状态</p>
                <p> adminPrices - 管理员默认价格</p>

                <p> CARD_TREE_ID_：卡种ID</p>
                <p> CARD_TREE_NAME_：卡种名称</p>
                <p> CARD_TREE_NAME_：卡种名称</p>
                <p> CARD_PRICE_：卡种价格</p>
                <p> SN_：排序</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>2.充值卡管理下级列表 </p>
                <p> 类似于菜单 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/card/agent/subList?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\',data:{userId:\'21ab311a0a814c7b9fa48d941df23f73\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/card/agent/subList</a>
                </p>
                <p> 充值卡管理下级列表 </p>
                <p> ibm/admin/manage/card/agent/subList </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> userId：用户主键</p>
            </td>
            <td>
                <p> APP_USER_ID_：下级代理主键</p>
                <p> USER_NAME_：下级代理名称</p>
                <p> PARENT_USER_ID_：父级代理主键</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>3.子充值卡管理员信息 </p>
                <p> - 点击子代理显示信息 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/card/agent/subInfo?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\',data:{subAgentId:\'21ab311a0a814c7b9fa48d941df23f73\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/card/agent/subInfo</a>
                </p>
                <p> 充值卡管理员信息 </p>
                <p> /ibm/admin/manage/card/agent/info </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> subAgentId：子代理主键</p>
            </td>
            <td>
                <p> adminInfo - 管理员信息</p>
                <p> APP_USER_NAME_：用户账户</p>
                <p> APP_GROUP_NAME_：角色名称</p>
                <p> IS_ADD_：允许添加子代</p>
                <p> PARENT_USER_NAME_：上级代理</p>
                <p> STATE_：状态</p>
                <p> adminPrices - 管理员默认价格</p>

                <p> CARD_TREE_ID_：卡种ID</p>
                <p> CARD_TREE_NAME_：卡种名称</p>
                <p> CARD_TREE_NAME_：卡种名称</p>
                <p> CARD_PRICE_：卡种价格</p>
                <p> SN_：排序</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>4.新增充值卡管理员 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/card/agent/save?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'edit\',data:{accountName:\'test01\',password:\'test01\',state:\'OPEN\',isAdd:\'true\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/card/agent/save</a>
                </p>
                <p> 保存充值卡管理员 </p>
                <p> ibm/admin/manage/card/agent/save </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> accountName：用户名称</p>
                <p> password：用户密码</p>
                <p> isAdd：是否允许添加子代（true/false）</p>
                <p> userState：状态(OPEN/CLOSE)</p>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>5.修改充值卡管理员基本信息 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/card/agent/editInfo?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'edit\',data:{subAgentId:\'test01\',password:\'test01\',state:\'OPEN\',isAdd:\'true\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/card/agent/editInfo</a>
                </p>
                <p> 修改充值卡管理员基本信息 </p>
                <p> ibm/admin/manage/card/agent/editInfo </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> subAgentId：用户名称(为空则修改自己)</p>
                <p> password：用户密码</p>
                <p> isAdd：是否允许添加子代（true/false）</p>
                <p> state：状态(OPEN/CLOSE)</p>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>6.修改充值卡管理员价格信息 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/card/agent/editPrice?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'edit\',data:{subAgentId:\'test01\',password:\'test01\',state:\'OPEN\',isAdd:\'true\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/card/agent/editPrice</a>
                </p>
                <p> 修改充值卡管理员价格信息 </p>
                <p> ibm/admin/manage/card/agent/editPrice </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> subAgentId：用户名称(为空则修改自己)</p>
                <p> priceInfos：价格信息JSONArray</p>
                <p>[{cardTreeId:'',cardTreePrice:0},{cardTreeId:'',cardTreePrice:0}]</p>

            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>7.上级给自己设置的价格列表 </p>
                <p> - 上级给自己设置的价格列表 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/card/agent/price?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/card/agent/price</a>
                </p>
                <p> 充值卡管理员信息 </p>
                <p> /ibm/admin/manage/card/agent/price </p>
            </td>
            <td>
                <p> token：用户令牌</p>
            </td>
            <td>
                <p> CARD_TREE_ID_：卡种ID</p>
                <p> CARD_TREE_NAME_：卡种名称</p>
                <p> CARD_TREE_NAME_：卡种名称</p>
                <p> CARD_PRICE_：卡种价格</p>
                <p> SN_：排序</p>
            </td>
        </tr>

        <tr>
            <td colspan="4">pc充值</td>
        </tr>

        <tr>
            <td>
                <p>1.充值 </p>
                <p> - 充值 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/home/rechargePoint?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/ibm/pc/home/rechargePoint</a>
                </p>
                <p> 充值 </p>
                <p> /ibm/pc/home/rechargePoint </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> cardPassword：充值卡密码</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>2.续费 </p>
                <p> - 续费 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/home/usePoint?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/ibm/pc/home/usePoint</a>
                </p>
                <p> 续费 </p>
                <p> /ibm/pc/home/usePoint </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> proLongMonth：续费月数</p>
                <p> pointTotal：需消耗总点数 (前端需验证一次点数是否足够)</p>
            </td>
            <td>
                <p> 时间戳,续费成功后的到期时间</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>3.消费记录 </p>
                <p> - 消费记录 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/home/pointRecord?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/ibm/pc/home/pointRecord</a>
                </p>
                <p> 消费记录 </p>
                <p> /ibm/pc/home/pointRecord </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> pageIndex：</p>
                <p> pageSize：</p>
            </td>
            <td>
                <p> CREATE_TIME_LONG_：时间</p>
                <p> TITLE_：说明</p>
                <p> BALANCE_T_：剩余点数</p>
                <p> pointOut：支出</p>
                <p> pointIn：存入</p>
            </td>
        </tr>


        <tr>
            <td colspan="4">admin基础配置</td>
        </tr>
        <tr>
            <td>
                <p>1.盘口列表 </p>
                <p> - 盘口列表 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/handicap/list?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/handicap/list</a>
                </p>
                <p> 盘口列表 </p>
                <p> /ibm/admin/manage/handicap/list </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> handicapCode：盘口名称</p>
                <p> handicapCategory：盘口类别</p>
                <p> handicapType：盘口类型</p>
                <p> pageIndex：</p>
                <p> pageSize：</p>
            </td>

            <td>
                <p>IBM_HANDICAP_ID_ : 盘口ID主键</p>
                <p> HANDICAP_NAME_：名称</p>
                <p> HANDICAP_TYPE_：类型（FREE：免费,CHARGE:付费）</p>
                <p> HANDICAP_CATEGORY_：类别（MEMBER：会员，AGENT：代理）</p>
                <p> HANDICAP_WORTH_T_：价格</p>
                <p> CAPACITY_：已使用容量</p>
                <p> CAPACITY_MAX_：总容量</p>
                <p>STATE_:状态</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>1.1 盘口游戏列表 </p>
                <p> - 盘口游戏列表 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/handicap/game/list?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/handicap/game/list</a>
                </p>
                <p> 盘口游戏列表 </p>
                <p> /ibm/admin/manage/handicap/game/list </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> handicapId：盘口ID主键</p>

            </td>
            <td>
                <p>handicapId : 盘口ID主键</p>
                <p> HANDICAP_GAME_ID_：盘口游戏ID主键</p>
                <p> GAME_NAME_：游戏名</p>
                <p> GAME_CODE_：游戏编码</p>
                <p> ICON_：图标</p>
                <p> TYPE_：类型</p>
                <p> CLOSE_TIME_：封盘时间</p>
                <p>STATE_:状态</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>1.2 盘口游戏- 修改/新增 -form表单 </p>
                <p> - 盘口游戏- 修改/新增 -form表单 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/handicap/game/form?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/handicap/game/form</a>
                </p>
                <p> 盘口游戏- 修改/新增 -form表单 </p>
                <p> /ibm/admin/manage/handicap/game/form </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> 新增参数：handicapId：盘口ID主键</p>
                <p>修改参数: handicapGameId：盘口游戏ID主键</p>

            </td>
            <td>
                <p>新增</p>
                <p>handicapId : 盘口ID主键</p>
                <p> gameInfo：盘口游戏名（为空表示没有游戏可加了）</p>
                <p>修改</p>
                <p> GAME_NAME_：游戏名</p>
                <p> ICON_：图标</p>
                <p> TYPE_：类型</p>
                <p> CLOSE_TIME_：封盘时间</p>
                <p>STATE_:状态</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>1.3 盘口游戏-修改-保存 </p>
                <p> - 盘口游戏-修改-保存 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/handicap/game/edit?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/handicap/game/edit</a>
                </p>
                <p> 盘口游戏-修改-保存 </p>
                <p> /ibm/admin/manage/handicap/game/edit </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> handicapGameId：盘口游戏ID主键</p>
                <p> type：游戏类型</p>
                <p> closeTime：封盘时间</p>
                <p> icon：盘口游戏图标</p>
                <p> sn：次序</p>
                <p> state：状态</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>1.4 盘口游戏-新增-保存 </p>
                <p> - 盘口游戏-新增-保存 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/handicap/game/save?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/handicap/game/save</a>
                </p>
                <p> 盘口游戏-新增-保存 </p>
                <p> /ibm/admin/manage/handicap/game/save </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> handicapId：盘口ID</p>
                <p> gameCode：游戏code</p>
                <p> gameName：游戏名称</p>
                <p> type：游戏类型</p>
                <p> closeTime：封盘时间</p>
                <p> icon：盘口游戏图标</p>
                <p> sn：次序</p>
            </td>
            <td></td>
        </tr>

        <tr>
            <td>
                <p>1.5 盘口列表-导航信息 </p>
                <p> - 盘口列表-导航信息 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/handicap/item/list?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/handicap/item/list</a>
                </p>
                <p> 盘口列表-导航信息 </p>
                <p> /ibm/admin/manage/handicap/item/list </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> pageIndex：</p>
                <p> pageSize：</p>
            </td>
            <td>
                <p> HANDICAP_CODE_：盘口code</p>
                <p> HANDICAP_CATEGORY_：盘口类别</p>
                <p> pageIndex：</p>
                <p> pageSize：</p>
                <p>rows{ IBM_HANDICAP_ITEM_ID_ : 盘口分类Id主键</p>
                <p>HANDICAP_URL_:网址,HANDICAP_CAPTCHA_:验证码</p>
                <P>UPDATE_TIME_LONG_:更新时间}</P>
            </td>
        </tr>

        <tr>
            <td>
                <p>1.6 盘口列表-导航信息-修改-form </p>
                <p> - 盘口列表-导航信息-修改-form </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/handicap/item/form?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/handicap/item/form</a>
                </p>
                <p> 盘口列表-导航信息-修改-form </p>
                <p> /ibm/admin/manage/handicap/item/form </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> handicapItemId：盘口分类Id主键</p>
            </td>
            <td>
                <p>IBM_HANDICAP_ITEM_ID_ : 盘口分类Id主键</p>
                <p>HANDICAP_URL_:网址</p>
                <P>HANDICAP_CAPTCHA_:验证码</P>
            </td>
        </tr>

        <tr>
            <td>
                <p>1.7 盘口列表-导航信息-修改-提交 </p>
                <p> - 盘口列表-导航信息-修改-提交 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/handicap/item/edit?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/handicap/item/edit</a>
                </p>
                <p> 盘口列表-导航信息-修改-提交 </p>
                <p> /ibm/admin/manage/handicap/item/edit </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> handicapItemId：盘口分类Id主键</p>
                <p> handicapUrl：网址</p>
                <p> handicapCaptcha：验证码</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>1.8 盘口列表-导航信息-删除 </p>
                <p> - 盘口列表-导航信息-删除 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/handicap/item/del?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/handicap/item/del</a>
                </p>
                <p> 盘口列表-导航信息-删除 </p>
                <p> /ibm/admin/manage/handicap/item/del </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> handicapItemId：盘口分类Id主键</p>
            </td>
            <td>
            </td>
        </tr>


        <tr>
            <td>
                <p>2 盘口新增 </p>
                <p> - 盘口新增 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/handicap/save?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/handicap/save</a>
                </p>
                <p> 盘口新增</p>
                <p>/ibm/admin/manage/handicap/save </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> handicapCode：盘口CODE</p>
                <p> handicapType：盘口类型</p>
                <p> category：类别</p>
                <p> handicapWorth：盘口价值</p>
                <p> desc：备注</p>
                <p> sn：次序</p>
                <p> version：盘口版本</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>3 盘口修改 </p>
                <p> - 盘口修改 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/handicap/edit\n?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/handicap/edit</a>
                </p>
                <p> 盘口修改</p>
                <p>/ibm/admin/manage/handicap/edit </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> handicapId ：盘口id </p>
                <p> handicapType：盘口类型</p>
                <p> state ：盘口状态</p>
                <p> handicapWorth：盘口价值</p>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>4 游戏列表 </p>
                <p> - 游戏列表 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/game/list?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/game/list</a>
                </p>
                <p> 游戏列表</p>
                <p>/ibm/admin/manage/game/list </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> gameCode：查询时传</p>

            </td>
            <td>
                <p> GAME_ID_：游戏Id</p>
                <p> GAME_NAME_：游戏名称</p>
                <p> GAME_CODE_：游戏code</p>
                <p> ICON_：图标</p>
                <p> DRAW_TIME_：开奖时间</p>
                <p>STATE_:状态</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>4.1 游戏修改-form </p>
                <p> - 游戏修改-form </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/game/form?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/game/form</a>
                </p>
                <p> 游戏修改-form</p>
                <p>/ibm/admin/manage/game/form </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> gameId：游戏Id</p>

            </td>
            <td>
                <p> GAME_ID_：游戏Id</p>
                <p> GAME_NAME_：游戏名称</p>
                <p> GAME_CODE_：游戏code</p>
                <p> ICON_：图标</p>
                <p> DRAW_TIME_：开奖时间</p>
                <p>STATE_:状态</p>
                <p>SN_:次序</p>
                <p>REP_GRAB_TABLE_NAME_:开奖表</p>
                <p>REP_DRAW_TABLE_NAME_:结果表</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>4.2 游戏修改-提交 </p>
                <p> - 游戏修改-提交 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/game/edit?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/game/edit</a>
                </p>
                <p> 游戏修改-提交</p>
                <p>/ibm/admin/manage/game/edit</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> gameId：游戏Id</p>
                <p> gameCode：游戏code</p>
                <p> icon：图标</p>
                <p> drawTime：开奖时间</p>
                <p>state:状态</p>
                <p>sn:次序</p>
                <p>repGrabTableName:开奖表</p>
                <p>repDrawTableName:结果表</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>5 新增游戏 </p>
                <p> - 新增游戏 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/game/save?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/game/save</a>
                </p>
                <p> 新增游戏</p>
                <p>/ibm/admin/manage/game/save</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> gameName：游戏名称</p>
                <p> gameCode：游戏code</p>
                <p> icon：图标</p>
                <p> drawTime：开奖时间</p>
                <p>sn:次序</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td colspan="4">admin消息管理</td>
        </tr>
        <tr>
            <td>
                <p>1.反馈列表 </p>
                <p> - 反馈列表 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/feedback/list?json={token:\'30301c8766674e90947d22136145b0ee\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/feedback/list</a>
                </p>
                <p> 盘口列表 </p>
                <p> /ibm/admin/manage/feedback/list</p>
            </td>
            <td>
                <p> feedbackType：反馈类型（BUG、USERS）</p>
                <p> feedbackTitle：反馈标题</p>
                <p> feedbackCode：反馈编码</p>
                <p> state：反馈状态 （FAILED("未通过"),AUDIT_PASS("审核通过"),MODIFY_FINIS("修改完成")</p>
                <p> ,MODIFY_FAIL("修改未通过"),FINISHED("完成")）</p>
                <p> timeStart：开始时间</p>
                <p> timeEnd：结束时间</p>
                <p> pageIndex：</p>
                <p> pageSize：</p>
            </td>
            <td>
                <p> userName : 反馈人</p>
                <p> feedbackCode：反馈编码</p>
                <p> info：反馈内容</p>
                <p> title：反馈标题</p>
                <p> time：反馈时间</p>
                <p> state：处理状态</p>
                <p> pk：主键</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>2.查看反馈信息详情 </p>
                <p> - 查看反馈信息详情 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/feedback/info?json={token:\'30301c8766674e90947d22136145b0ee\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/feedback/info</a>
                </p>
                <p> 查看反馈信息详情 </p>
                <p> /ibm/admin/manage/feedback/info</p>
            </td>
            <td>
                <p> feedbackCode：反馈编码</p>
            </td>
            <td>
                <p> feedBackInfo ：{反馈信息详情}</p>
                <p> userName : 反馈人</p>
                <p> feedbackCode：反馈编码</p>
                <p> info：反馈内容</p>
                <p> title：反馈标题</p>
                <p> time：反馈时间</p>
                <p> state：处理状态</p>
                <p> pk：主键</p>
                <p> feedBackResults ：{反馈结果列表}</p>
                <p> updateUser：处理人</p>
                <p> result:处理结果</p>
                <p> state：处理状态</p>
                <p> time：处理时间</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>2.更新反馈 </p>
                <p> - 更新反馈 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/feedback/update?json={token:\'30301c8766674e90947d22136145b0ee\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/feedback/update</a>
                </p>
                <p> 更新反馈 </p>
                <p> /ibm/admin/manage/feedback/update</p>
            </td>
            <td>
                <p> feedbackCode：反馈编码</p>
                <p> resultMsg：处理结果</p>
                <p> state：处理状态</p>
                <p> pk：主键</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>3.保存用户反馈 </p>
                <p> - 保存用户反馈 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/home/feedback/users/save?json={token:\'30301c8766674e90947d22136145b0ee\'}');"
                    > http://IP:PORT/PROJECT/ibm/pc/home/feedback/users/save</a>
                </p>
                <p> 保存用户反馈 </p>
                <p> /ibm/pc/home/feedback/users/save</p>
            </td>
            <td>
                <p> feedbackInfo：反馈内容</p>
                <p> feedbackType：反馈类型（可为空）</p>
            </td>
            <td>
                <p> feedbackCode：反馈编码</p>
            </td>
            </td>
        </tr>

        <tr>
            <td>
                <p>4.保存BUG反馈 </p>
                <p> - 保存BUG反馈 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/feedback/bug/save?json={token:\'30301c8766674e90947d22136145b0ee\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/feedback/bug/save</a>
                </p>
                <p> 保存BUG反馈 </p>
                <p> /ibm/admin/manage/feedback/bug/save</p>
            </td>
            <td>
                <p> feedbackInfo：反馈内容</p>
                <p> feedbackType：反馈类型（可为空）</p>
            </td>
            <td>
                <p> feedbackCode：反馈编码</p>
            </td>
            </td>
        </tr>

        <tr>
            <td>
                <p>5.消息创建（公告、提醒、消息） </p>
                <p> - 消息创建 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/cms/notify/save?json={token:\'30301c8766674e90947d22136145b0ee\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/cms/notify/save</a>
                </p>
                <p> 消息创建 </p>
                <p> /ibm/admin/manage/cms/notify/save</p>
            </td>
            <td>
                <p> notifyType：消息类型（announce：公告,remind：提醒,memmage 消息）</p>
                <p> notifyContent：消息内容</p>
                <p> notifyTitle：消息标题</p>
                <p> notifyChannel：公告平台（APP、PC、ADMIN）</p>
                <p> cancelTimeLong ：过期时间</p>
                <p> notifySite：消息位置-消息显示的位置（弹窗WINDOW还是轮播ROLL）</p>
                <p> notifyCategory：提醒类别（pay（支付）、other默认为空）</p>
                <p> userUserName：需要提醒的用户</p>
                <p> notifyState : 消息状态</p>
                <p> notifyLevel : 消息等级</p>
            </td>
            <td>
            </td>
        </tr>


        <tr>
            <td>
                <p>6.消息列表（公告、消息、提醒） </p>
                <p> - 消息列表 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/cms/notify/list?json={token:\'30301c8766674e90947d22136145b0ee\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/cms/notify/list</a>
                </p>
                <p> 消息列表 </p>
                <p> /ibm/admin/manage/cms/notify/list</p>
            </td>
            <td>
                <p> notifyType：消息类型（announce：公告,remind：提醒,message：消息、私信）</p>
                <p> notifyState：消息状态</p>
                <p> notifyTitle：消息标题</p>
                <p> userName：消息接收人/创建人</p>
                <p> timeStart：开始时间</p>
                <p> timeEnd：结束时间</p>
                <p> pageIndex：</p>
                <p> pageSize：</p>
            </td>
            <td>
                <p> IBM_CMS_NOTIFY_ID_:消息主键</p>
                <p> NOTIFY_TITLE_：消息标题</p>
                <p> USER_NAME_ :消息接收人/创建人</p>
                <p> NOTIFY_CODE_：消息编码</p>
                <p> ANNOUNCE_CHANNEL_：公告平台</p>
                <p> NOTIFY_SITE_：位置</p>
                <p> NOTIFY_LEVEL_：等级</p>
                <p> REMIND_CATEGORY_ : 提醒类型</p>
                <p> CREATE_TIME_LONG_：时间</p>
                <p> IS_CANCEL_：是否撤销</p>
                <p> IS_READ_:是否阅读</p>
                <p> STATE_ :状态</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>7.消息详情（公告、消息、提醒） </p>
                <p> - 消息详情 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/cms/notify/form?json={token:\'30301c8766674e90947d22136145b0ee\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/cms/notify/form</a>
                </p>
                <p> 消息列表 </p>
                <p> /ibm/admin/manage/cms/notify/form</p>
            </td>
            <td>
                <p> notifyType：消息类型（announce：公告,remind：提醒,message：消息、私信）</p>
                <p> IBM_CMS_NOTIFY_ID_：消息主键</p>
            </td>
            <td>
                <p> IBM_CMS_NOTIFY_ID_:消息主键</p>
                <p> NOTIFY_TITLE_：消息标题</p>
                <p> USER_NAME_ :消息接收人/创建人</p>
                <p> NOTIFY_CONTENT_：内容</p>
                <p> ANNOUNCE_CHANNEL_：公告平台</p>
                <p> NOTIFY_SITE_：位置</p>
                <p> NOTIFY_LEVEL_：等级</p>
                <p> CANCEL_TIME_LONG_:到期时间</p>
                <p> CREATE_TIME_LONG_：时间</p>
                <p> IS_CANCEL_：是否撤销</p>
                <p> STATE_ :状态</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>8.消息修改（公告、消息、提醒） </p>
                <p> - 消息修改 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/cms/notify/edit?json={token:\'30301c8766674e90947d22136145b0ee\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/cms/notify/edit</a>
                </p>
                <p> 消息修改 </p>
                <p> /ibm/admin/manage/cms/notify/edit</p>
            </td>
            <td>
                <p> notifyType：消息类型（announce：公告,remind：提醒,message：消息、私信）</p>
                <p> IBM_CMS_NOTIFY_ID_：消息主键</p>
                <p> notifyTitle：消息标题</p>
                <p> notifyContent：消息正文</p>
                <p> cancelTimeLong：到期时间</p>
                <p> notifyChannel：公告平台</p>
                <p> notifySite：公告位置</p>
                <p> notifyLevel：公告等级</p>
                <p> notifyState：消息状态</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>9.消息删除/撤销（公告、消息、提醒） </p>
                <p> - 消息删除/撤销 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/cms/notify/del?json={token:\'30301c8766674e90947d22136145b0ee\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/cms/notify/del</a>
                </p>
                <p> 消息删除/撤销 </p>
                <p> /ibm/admin/manage/cms/notify/del</p>
            </td>
            <td>
                <p> notifyType：消息类型（announce：公告,remind：提醒,message：消息、私信）</p>
                <p> IBM_CMS_NOTIFY_ID_：消息主键</p>
                <p> notifyState：（撤销 CANCEL、删除 DEL、关闭 CLOSE）</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td colspan="4">PC消息</td>
        </tr>


        <tr>
            <td>
                <p>1.查询用户提醒消息 </p>
                <p> - 查询用户提醒消息 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/cms/notify/list?json={token:\'30301c8766674e90947d22136145b0ee\'}');"
                    > http://IP:PORT/PROJECT/ibm/pc/cms/notify/list</a>
                </p>
                <p> 查询用户提醒消息 </p>
                <p> /ibm/pc/cms/notify/list</p>
            </td>
            <td>
                <p> notifyType : 消息类型（ANNOUNCE，MESSAGE）</p>
                <p> pageIndex </p>
                <p> pageSize </p>/ibm/pc/cms/notify/list
            </td>
            <td>
                <p> CMS_NOTIFY_ID_:消息主键</p>
                <p> NOTIFY_TITLE_：消息标题</p>
                <p> NOTIFY_CONTENT_：内容</p>
                <p> CREATE_TIME_LONG_：时间</p>
                <p> IS_READ_：是否已读</p>
                <p> messageCount:未读消息条数</p>
                <p> remind:提醒消息</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>2.阅读消息 </p>
                <p> - 阅读消息 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/cms/notify/read?json={token:\'30301c8766674e90947d22136145b0ee\'}');"
                    > http://IP:PORT/PROJECT/ibm/pc/cms/notify/read</a>
                </p>
                <p> 阅读消息 </p>
                <p> /ibm/pc/cms/notify/read</p>
            </td>
            <td>
                <p> cmsNotifyId：消息主键 </p>
            </td>
            <td>
                <p> message : 内容</p>
                <p> NOTIFY_TITLE_：消息标题</p>
                <p> NOTIFY_CONTENT_：内容</p>
                <p> CREATE_TIME_LONG_：时间</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>3.消息删除 </p>
                <p> - 消息删除 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/cms/notify/del?json={token:\'30301c8766674e90947d22136145b0ee\'}');"
                    > http://IP:PORT/PROJECT/ibm/pc/cms/notify/del</a>
                </p>
                <p> 消息删除 </p>
                <p> /ibm/pc/cms/notify/del</p>
            </td>
            <td>
                <p> notifyType : 消息类型（REMIND 不再提醒功能，MESSAGE）</p>

                <p> cmsNotifyId：消息主键 </p>
            </td>
            <td>
            </td>
        </tr>


        <!--<editor-fold desc="客户端管理">-->
        <tr>
            <td colspan="4">客户端管理接口 《cmd 必须添加项，每一个接口都添加 - show：查询、展示&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- edit : 修改、新增、删除</td>
        </tr>

        <tr>
            <td>
                <p>1.获取客户机列表信息《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/list?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/list</a>
                </p>
                <p> 获取所有的客户机信息</p>
                <p> /ibm/admin/manage/client/list</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> ip：注册IP</p>
                <p> clientCode：客户端编码</p>
                <p> pageIndex：页数</p>
                <p> pageSize：条数</p>
            </td>
            <td>
                <p> ip：注册IP</p>
                <p> clientCode：客户端编码</p>
                <p> {REGISTER_IP_：注册ip,CLIENT_CODE_：客户端编码,</p>
                <p> CLIENT_CAPACITY_MAX_：客户端最大容量,</p>
                <p> CLIENT_CAPACITY_：客户端使用容量,</p>
                <p> UPDATE_TIME_LONG_：末次检测时间,</p>
                <p> STATE_：状态,hearBeatState:客户端心跳状态}</p>
            </td>
        </tr>


        <tr>
            <td>
                <p>2.客户端修改表单《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/form?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/form</a>
                </p>
                <p> 客户端容量修改表单</p>
                <p> /ibm/admin/manage/client/form</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> clientCode：客户端编码</p>
            </td>
            <td>
                <p> ip：注册IP</p>
                <p> clientCode：客户端编码</p>
                <p> CLIENT_CAPACITY_MAX_：客户端最大容量</p>
                <p> STATE_：状态</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>3.客户端容量设置《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/edit?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/edit</a>
                </p>
                <p> 客户端容量设置</p>
                <p> /ibm/admin/manage/client/edit</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> clientCode：客户端编码</p>
                <p> clientCapacityMax:最大容量</p>
                <p> state：状态</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>4.客户机心跳控制《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/heartbeat?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/heartbeat</a>
                </p>
                <p> 客户机心跳控制</p>
                <p> /ibm/admin/manage/client/heartbeat</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> clientCode：客户端编码</p>
                <p> heartbeatState：心跳状态（OPEN/CLOSE）</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>5.客户机切换表单《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/clientReplaceForm?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/clientReplaceForm</a>
                </p>
                <p> 客户机切换表单</p>
                <p> /ibm/admin/manage/client/clientReplaceForm</p>
            </td>
            <td>
                <p> token：用户令牌</p>
            </td>
            <td>
                <p> CLIENT_CODE_：客户端编码</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>6.切换客户机《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/clientReplace?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/clientReplace</a>
                </p>
                <p> 切换客户机</p>
                <p> /ibm/admin/manage/client/clientReplace</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> sendClientCode：发送端客户机编码</p>
                <p> receiveClientCode：接收端客户机编码</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>7.盘口迁移表单《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/handicapMigrateForm?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/handicapMigrateForm</a>
                </p>
                <p> 盘口迁移表单</p>
                <p> /ibm/admin/manage/client/handicapMigrateForm</p>
            </td>
            <td>
                <p> token：用户令牌</p>
            </td>
            <td>
                <p> customers：类型（MEMBER/AGENT）</p>
                <p> hmCodes：会员盘口编码</p>
                <p> haCodes：代理盘口编码</p>
                <p> clientList：客户机编码</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>8.盘口迁移《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/handicapMigrate?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/handicapMigrate</a>
                </p>
                <p> 盘口迁移</p>
                <p> /ibm/admin/manage/client/handicapMigrate</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> customer：类型（MEMBER/AGENT）</p>
                <p> handicapCode：盘口编码</p>
                <p> sendClientCode：发送端客户机编码</p>
                <p> receiveClientCode：接收端客户机编码</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>9.盘口容量列表信息《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/handicap/list?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/handicap/list</a>
                </p>
                <p> 盘口容量列表信息</p>
                <p> /ibm/admin/manage/client/handicap/list</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> clientCode：客户端编码</p>
            </td>
            <td>
                <p> CLIENT_CODE_：客户端编码</p>
                <p> HANDICAP_CODE_：盘口编码</p>
                <p> CAPACITY_HANDICAP_MAX_：客户端盘口最大容量</p>
                <p> CAPACITY_HANDICAP_：盘口使用容量</p>
                <p> CAPACITY_HA_MAX_：客户端代理最大容量</p>
                <p> CAPACITY_HA_：代理使用容量</p>
                <p> CAPACITY_HM_MAX_：客户端会员最大容量</p>
                <p> CAPACITY_HM_：会员使用容量</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>10.盘口容量修改表单《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/handicap/form?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/handicap/form</a>
                </p>
                <p> 盘口容量修改表单</p>
                <p> /ibm/admin/manage/client/handicap/form</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> clientCode：客户端编码</p>
                <p> handicapCode：盘口编码</p>
            </td>
            <td>
                <p> CLIENT_CODE_：客户端编码</p>
                <p> HANDICAP_CODE_：盘口编码</p>
                <p> CAPACITY_HANDICAP_MAX_：客户端盘口最大容量</p>
                <p> CAPACITY_HA_MAX_：客户端代理最大容量</p>
                <p> CAPACITY_HM_MAX_：客户端会员最大容量</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>11.盘口容量设置《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/handicap/edit?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/handicap/edit</a>
                </p>
                <p> 盘口容量设置</p>
                <p> /ibm/admin/manage/client/handicap/edit</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> clientCode：客户端编码</p>
                <p> handicapCode：盘口编码</p>
                <p> capacityHandicapMax：盘口最大容量</p>
                <p> capacityHaMax：代理最大容量</p>
                <p> capacityHmMax：会员最大容量</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>12.新增盘口容量信息《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/handicap/save?json={token:\'64e03c1450234e168537df37ed4cd9d1\',data:{key:\'1\'}}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/handicap/save</a>
                </p>
                <p> 新增盘口容量信息</p>
                <p> /ibm/admin/manage/client/handicap/save</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> clientCode：客户端编码</p>
                <p> handicapCode：盘口编码</p>
                <p> capacityHandicapMax：盘口最大容量</p>
                <p> capacityHaMax：代理最大容量</p>
                <p> capacityHmMax：会员最大容量</p>
            </td>
            <td>
            </td>
        </tr>


        <tr>
            <td>
                <p>13.盘口封盘时间列表《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/seal/list?json={token:\'64e03c1450234e168537df37ed4cd9d1\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/seal/list</a>
                </p>
                <p> 盘口封盘时间列表</p>
                <p> /ibm/admin/manage/client/seal/list</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> handicapCode：盘口编码</p>
                <p> gameCode：游戏编码</p>

            </td>
            <td>
                <p> handicaps：盘口列表</p>
                <p> games：游戏列表</p>
                <p> （查询条件为空时，回传上面信息）</p>
                <p> IBM_CONFIG_ID_：配置主键</p>
                <p> HANDICAP_NAME：盘口名称</p>
                <p> GAME_CODE_：游戏编码</p>
                <p> GAME_NAME_：游戏名称</p>
                <p> SEAL_TIME_：封盘时间</p>
                <p> STATE_：状态</p>

            </td>
        </tr>

        <tr>
            <td>
                <p>14.封盘时间详情《GET》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/manage/client/seal/form?json={token:\'64e03c1450234e168537df37ed4cd9d1\'}');"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/seal/form</a>
                </p>
                <p> 封盘时间详情</p>
                <p> /ibm/admin/manage/client/seal/form</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> configId：配置主键</p>
                <p> （可为空，为空则是新增界面，非空则是修改）</p>
            </td>
            <td>
                <p> handicaps：盘口列表</p>
                <p> games：游戏列表</p>
                <p> sealTimeInfo：封盘时间信息（同列表页面）</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>15.封盘时间保存《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/seal/save</a>
                </p>
                <p> 封盘时间保存</p>
                <p> /ibm/admin/manage/client/seal/save</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> handicapCode：盘口编码（*）</p>
                <p> gameCode：游戏编码（*）</p>
                <p> sealTime：封盘时间（*）</p>
                <p> state：状态（*）</p>
            </td>
            <td>

            </td>
        </tr>

        <tr>
            <td>
                <p>16.封盘时间修改《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/seal/edit</a>
                </p>
                <p> 用户角色信息修改</p>
                <p> /ibm/admin/manage/client/seal/edit</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> configId：配置主键（*）</p>
                <p> sealTime：封盘时间 </p>
                <p> state：状态（*）</p>
            </td>
            <td>

            </td>
        </tr>

        <tr>
            <td>
                <p>16.封盘时间发送《POST》</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/client/seal/send</a>
                </p>
                <p> 封盘时间发送</p>
                <p> /ibm/admin/manage/client/seal/send</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> configId：配置主键 (发送全部传空值)</p>
            </td>
            <td>

            </td>
        </tr>

        <tr>
            <td>
                <p>17.服务器列表 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/server/list</a>
                </p>
                <p> 服务器列表</p>
                <p> /ibm/admin/manage/server/list</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> ip：Ip</p>
                <p> serverState：服务器状态</p>
            </td>
            <td>
                <p> IBM_SYS_SERVLET_IP_ID_：主键</p>
                <p> SERVLET_IP_：Ip</p>
                <p> START_TIME_LONG_：开始时间</p>
                <p> END_TIME_LONG_：结束时间</p>
                <p> STATE_：状态</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>18.服务器新增 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/server/save</a>
                </p>
                <p> 服务器新增</p>
                <p> /ibm/admin/manage/server/save</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> ip：ip</p>
                <p> timeStart：开始时间</p>
                <p> timeEnd：结束时间</p>
            </td>
            <td>
                <p> IBM_SYS_SERVLET_IP_ID_：主键</p>
                <p> SERVLET_IP_：Ip</p>
                <p> START_TIME_LONG_：开始时间</p>
                <p> END_TIME_LONG_：结束时间</p>
                <p> STATE_：状态</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>19.服务器详情 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/server/form</a>
                </p>
                <p> 服务器详情</p>
                <p> /ibm/admin/manage/server/form</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> IBM_SYS_SERVLET_IP_ID_：主键</p>
            </td>
            <td>
                <p> IBM_SYS_SERVLET_IP_ID_：主键</p>
                <p> SERVLET_IP_：Ip</p>
                <p> START_TIME_LONG_：开始时间</p>
                <p> END_TIME_LONG_：结束时间</p>
                <p> STATE_：状态</p>
            </td>
        </tr>

        <tr>
            <td>
                <p>19.服务器修改 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                    > http://IP:PORT/PROJECT/ibm/admin/manage/server/edit</a>
                </p>
                <p> 服务器修改</p>
                <p> /ibm/admin/manage/server/edit</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> IBM_SYS_SERVLET_IP_ID_：主键</p>
                <p> serverState：服务器状态</p>
                <p> timeStart：开始时间</p>
                <p> timeEnd：结束时间</p>
            </td>
            <td>

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
