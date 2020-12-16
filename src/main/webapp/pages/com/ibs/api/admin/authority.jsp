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
            <td colspan="4">IBS后台管理-权限管理--菜单列表</td>
        </tr>

        <tr>
            <td colspan="4">菜单列表接口 </td>
        </tr>
        <tr>
            <td>
                <p>1.菜单列表页面</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/authority/menu/list?json={\'token\':\'30301c8766674e90947d22136145b0ee\'}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/authority/menu/list</a>
                </p>
                <p> 菜单列表页面 </p>
                <p> sys/manage/authority/menu/list </p>
            </td>
            <td>
                <p> token：用户令牌</p>
            </td>
            <td>
                <p> id：菜单ID</p>
                <p> pid：父菜单ID</p>
                <p> APP_MENU_NAME_：菜单名称</p>
                <p> sys_URL_：菜单地址</p>
                <p> sys_PIC_：菜单图片</p>
                <p> SN_：菜单排序</p>
                <p> PERMISSION_CODE_：菜单权限码</p>
                <p> STATE_：菜单状态</p>
                <p> MENU_TYPE_：菜单类型</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>2.菜单项展示</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/authority/menu/show?json={\'token\':\'30301c8766674e90947d22136145b0ee\',data:{menuId:\'0f8820c6dd214130863e78bf10f8c6d9\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/authority/menu/show</a>
                </p>
                <p> 菜单项展示 </p>
                <p> sys/manage/authority/menu/show </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> menuId：菜单ID</p>
            </td>
            <td>
                <p> menu-菜单详情</p>
                <p> APP_MENU_ID_ : 菜单ID</p>
                <p> APP_MENU_NAME_ : 菜单名称</p>
                <p> APP_MENU_CODE_ : 菜单编码</p>
                <p> sys_URL_ : 菜单地址</p>
                <p> PERMISSION_CODE_：权限码</p>
                <p> sys_PIC_ : 菜单图片</p>
                <p> PARENT_NAME_ : 父名称</p>
                <p> MENU_TYPE_ : 菜单类型</p>
                <p> SN_：菜单排序</p>
                <p> STATE_：菜单状态</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>3.新增新的菜单项</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/authority/menu/add?json={\'token\':\'30301c8766674e90947d22136145b0ee\',data:{parentId:\'1\',menuName:\'测试10\',menuCode:\'test1\',url:\'1230\',permissionCode:\'123\',pic:\'123\',state:\'OPEN\',sn:\'1000\',permissionGrade:\'123\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/authority/menu/add</a>
                </p>
                <p> 新增新的菜单项 (POST) </p>
                <p> sys/manage/authority/menu/add </p>
                <p> * - 必填项</p>
                <p> # - 所有标示项中必填一项</p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> parentId : 父级主键 (*)</p>
                <p> menuName : 菜单名称 (*)</p>
                <p> menuCode : 菜单编码</p>
                <p> url : 菜单地址 (#)</p>
                <p> permissionCode：权限码 (#)</p>
                <p> permissionGrade：权限等级</p>
                <p> pic : 菜单图片</p>
                <p> sn：菜单排序</p>
                <p> state：菜单状态 (*)</p>
                <p> menuType : 菜单类型</p>
            </td>
            <td>
                <p> state-状态</p>
                <p> OPEN ： 启用</p>
                <p> OPEN ： 禁用</p>
                <p> menuType-菜单状态</p>
                <p> MENU ： 菜单 - 会展示在菜单列表中</p>
                <p> RESOURCES ： 资源 - 不做展示，进作为权限判断使用</p>
                <p> 自定义项 ： 具体CODE不指定，作为以后扩张项目 -默认</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>4.菜单项修改</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/authority/menu/edit?json={\'token\':\'30301c8766674e90947d22136145b0ee\',data:{parentId:\'1\',menuId:\'6LR1\',menuName:\'测试2\',menuCode:\'test1\',url:\'123\',permissionCode:\'123\',pic:\'123\',state:\'OPEN\',sn:\'1000\',permissionGrade:\'123\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/authority/menu/edit</a>
                </p>
                <p> 菜单项修改 (POST)</p>
                <p> sys/manage/authority/menu/edit </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> parentId : 父级主键 (*)</p>
                <p> menuId：菜单ID (*)</p>
                <p> menuName : 菜单名称</p>
                <p> menuCode : 菜单编码</p>
                <p> url : 菜单地址</p>
                <p> permissionCode：权限码</p>
                <p> permissionGrade：权限等级</p>
                <p> pic : 菜单图片</p>
                <p> sn：菜单排序</p>
                <p> state：菜单状态 (*)</p>
                <p> menuType : 菜单类型</p>
            </td>
            <td>
                <p> state-状态</p>
                <p> OPEN ： 启用</p>
                <p> OPEN ： 禁用</p>
                <p> menuType-菜单状态</p>
                <p> MENU ： 菜单 - 会展示在菜单列表中</p>
                <p> RESOURCES ： 资源 - 不做展示，进作为权限判断使用</p>
                <p> BUTTON ： 按钮 - 不做展示，进作为权限判断使用</p>
                <p> 自定义项 ： 具体CODE不指定，作为以后扩张项目 -默认</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>5.查询菜单的上级菜单</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/authority/menu/showParent?json={\'token\':\'30301c8766674e90947d22136145b0ee\',data:{menuId:\'0f8820c6dd214130863e78bf10f8c6d9\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/authority/menu/showParent</a>
                </p>
                <p> 菜单项展示  </p>
                <p> sys/manage/authority/menu/showParent  </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> menuId：菜单ID</p>
            </td>
            <td>
                <p> PARENT_ID_ : 父类ID</p>
                <p> PARENT_NAME_ : 父类名称</p>
                <p> menuList - 菜单列表 </p>
            </td>
        </tr>
        <tr>
            <td>
                <p>6.修改上级菜单</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/authority/menu/editParent?json={\'token\':\'30301c8766674e90947d22136145b0ee\',data:{parentId:\'Q6Q7\',menuId:\'6LR1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/authority/menu/editParent</a>
                </p>
                <p> 菜单项修改 (POST)</p>
                <p> sys/manage/authority/menu/editParent </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> parentId : 父级主键</p>
                <p> menuId：菜单ID</p>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>7.删除菜单</p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/authority/menu/del?json={\'token\':\'30301c8766674e90947d22136145b0ee\',data:{menuId:\'6LR1\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/authority/menu/del</a>
                </p>
                <p> 删除菜单 (POST)</p>
                <p> sys/manage/authority/menu/del </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> menuId：菜单ID</p>
            </td>
            <td>
            </td>
        </tr>
        <!--</editor-fold>-->

        <!--<editor-fold desc="角色">-->
        <tr>
            <td colspan="4">角色管理接口 《cmd 必须添加项，每一个接口都添加 - show：查询、展示&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- edit : 修改、新增、删除</td>
        </tr>
        <tr>
            <td>
                <p>1.角色管理列表 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/authority/role/list?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/authority/role/list</a>
                </p>
                <p> 角色管理列表 </p>
                <p> sys/manage/authority/role/list </p>
            </td>
            <td>
                <p> token：用户令牌</p>
            </td>
            <td>
                <p> APP_GROUP_ID_：角色ID</p>
                <p> APP_GROUP_NAME_：角色名称</p>
                <p> APP_GROUP_CODE_：角色编码</p>
                <p> PERMISSION_GRADE_：角色等级</p>
                <p> STATE_：状态</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>2.角色详情信息展示 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/authority/role/show?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\',data:{roleId:\'f940e119cc3945a2b2982fdf431f794e\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/authority/role/show</a>
                </p>
                <p> 角色管理列表 </p>
                <p> sys/manage/authority/role/show </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> roleId：角色ID</p>
            </td>
            <td>
                <p> APP_GROUP_ID_：角色ID</p>
                <p> APP_GROUP_NAME_：角色名称</p>
                <p> APP_GROUP_CODE_：角色编码</p>
                <p> PERMISSION_GRADE_：角色等级</p>
                <p> STATE_：状态</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>3.新增新的角色 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/authority/role/add?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'edit\',data:{roleName:\'测试角色1\',roleCode:\'test01\',state:\'OPEN\',roleGrade:\'10\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/authority/role/add</a>
                </p>
                <p> 新增新的角色 </p>
                <p> sys/manage/authority/role/add </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> roleName：角色名称</p>
                <p> roleCode：角色编码</p>
                <p> roleGrade：角色等级</p>
                <p> state：状态</p>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>4.角色修改 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/authority/role/edit?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'edit\',data:{roleId:\'7C2B8CB22BDA486D9AF58FF435077EFA\',roleName:\'测试角色1\',roleCode:\'test01\',state:\'OPEN\',roleGrade:\'8\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/authority/role/edit</a>
                </p>
                <p> 角色修改 </p>
                <p> sys/manage/authority/role/edit </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> roleId：角色ID</p>
                <p> roleName：角色名称</p>
                <p> roleCode：角色编码</p>
                <p> roleGrade：角色等级</p>
                <p> state：状态</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>5.展示角色资源 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/authority/role/resourcesShow?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\',data:{roleId:\'7C2B8CB22BDA486D9AF58FF435077EFA\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/authority/role/resourcesShow</a>
                </p>
                <p> 展示角色资源 </p>
                <p> sys/manage/authority/role/resourcesShow </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> roleId：角色ID</p>
            </td>
            <td>
                <p> menuTree-菜单树（同菜单列表）</p>
                <p> roleId：角色ID</p>
                <p> roleName：角色名称</p>
                <p> roleMenuIds：角色拥有的菜单数组</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>6.修改保存角色资源 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/authority/role/resourcesEdit?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'edit\',data:{roleId:\'7C2B8CB22BDA486D9AF58FF435077EFA\',menuIds:[\'Q6Q7\',\'12\']}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/authority/role/resourcesEdit</a>
                </p>
                <p> 修改保存角色资源 </p>
                <p> sys/manage/authority/role/resourcesEdit </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> roleId：角色ID</p>
                <p> menuIds：菜单ID数组</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>7.角色删除 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/authority/role/del?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'edit\',data:{roleId:\'7C2B8CB22BDA486D9AF58FF435077EFA\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/authority/role/del</a>
                </p>
                <p> 角色删除 </p>
                <p> sys/manage/authority/role/del </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> roleId：角色ID</p>
            </td>
            <td>
            </td>
        </tr>
        <!--</editor-fold>-->

        <!--<editor-fold desc="用户">-->
        <tr>
            <td colspan="4">操作员管理接口 《cmd 必须添加项，每一个接口都添加 - show：查询、展示&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- edit : 修改、新增、删除</td>
        </tr>
        <tr>
            <td>
                <p>1.操作人列表 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/authority/user/list?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\'}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/authority/user/list</a>
                </p>
                <p> 操作人列表 </p>
                <p> sys/manage/authority/user/list </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> userName：用户名称</p>
            </td>
            <td>
                <p> NICKNAME_：用户名称</p>
                <p> APP_USER_NAME_：用户账户</p>
                <p> APP_GROUP_NAME_：角色名称</p>
                <p> IP_：登录IP</p>
                <p> CREATE_TIME_：登录事件</p>
                <p> STATE_：状态</p>
                <p> APP_USER_ID_：用户主键</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>2.展示操作人信息 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/authority/user/show?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'show\',data:{userId:\'21ab311a0a814c7b9fa48d941df23f73\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/authority/user/show</a>
                </p>
                <p> 展示操作人信息 </p>
                <p> sys/manage/authority/user/show </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> userId：用户主键</p>
            </td>
            <td>
                <p> NICKNAME_：用户名称</p>
                <p> APP_USER_NAME_：用户账户</p>
                <p> APP_GROUP_NAME_：角色名称</p>
                <p> STATE_：状态</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>3.操作员新增 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/authority/user/add?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'edit\',data:{userName:\'测试用户1\',userAccount:\'test01\',userPassWord:\'test01\',state:\'OPEN\',roleId:\'f940e119cc3945a2b2982fdf431f794e\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/authority/user/add</a>
                </p>
                <p> 操作员新增 </p>
                <p> sys/manage/authority/user/add </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> userName：用户名称</p>
                <p> userAccount：用户账户</p>
                <p> userPassWord：用户密码</p>
                <p> roleId：角色ID</p>
                <p> state：状态</p>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <p>4.操作员修改 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/authority/user/edit?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'edit\',data:{userId:\'7C2B8CB22BDA486D9AF58FF435077EFA\',userName:\'测试角色1\',userPassWord:\'test01\',state:\'OPEN\',roleId:\'7C2B8CB22BDA486D9AF58FF435077EFA\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/authority/user/edit</a>
                </p>
                <p> 操作员修改 </p>
                <p> sys/manage/authority/role/edit </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> roleId：角色ID</p>
                <p> userId：用户ID</p>
                <p> userName：用户名称</p>
                <p> userPassWord：用户密码</p>
                <p> state：状态</p>
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                <p>6

                    .操作人员删除 </p>
            </td>
            <td>
                <p>
                    <a href="javascript:void(0);"
                       onClick="findUrl('${pageContext.request.contextPath}/ibs/sys/manage/authority/user/del?json={token:\'30301c8766674e90947d22136145b0ee\',cmd:\'edit\',data:{userId:\'c205da6ed3104b0db09eba58bd91b319\'}}');"
                    > http://IP:PORT/PROJECT/ibs/sys/manage/authority/user/del</a>
                </p>
                <p> 操作人员删除 </p>
                <p> sys/manage/authority/user/del </p>
            </td>
            <td>
                <p> token：用户令牌</p>
                <p> userId：用户ID</p>
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
