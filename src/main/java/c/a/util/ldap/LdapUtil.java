package c.a.util.ldap;
import java.util.Hashtable;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.log.LogThreadLocal;
import c.a.util.core.string.StringUtil;
/**
 * 
 * Windows Server 2008 R2 配置AD(Active Directory)域控制器
 * 
 * @Description:
 * @ClassName: LdapUtil
 * @date 2016年10月16日 下午7:05:10
 * @author cxy
 * @Email:  使用范围：
 * 
 */
public class LdapUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 同步数据
	 * 
	 * @Description:
	 * @Title: syncData
	 * @param config
	 * @return
	 * @throws NamingException 参数说明
	 * @return LdapGroup 返回类型
	 * @throws
	 */
	public LdapGroup syncData(LdapConfig config) throws NamingException {
		SearchControls searchControls = new SearchControls();
		DirContext dirContext = this.findDirContext(config);
		if (LogThreadLocal.isSuccess()) {
		} else {
			return null;
		}
		config.setAttDeptDescription("description");
		config.setAttUserDescription("description");
		String[] attributeKeyArray = new String[14];
		attributeKeyArray[0] = config.getAttUserNo();
		attributeKeyArray[1] = config.getAttUserName();
		attributeKeyArray[2] = config.getAttUserAcc();
		attributeKeyArray[3] = config.getAttUserPwd();
		attributeKeyArray[4] = config.getAttUserTel();
		attributeKeyArray[5] = config.getAttUserMail();
		attributeKeyArray[6] = config.getAttUserDescription();
		attributeKeyArray[7] = config.getAttDeptName();
		attributeKeyArray[8] = config.getAttDeptDescription();
		attributeKeyArray[9] = "whenCreated";
		attributeKeyArray[10] = "whenChanged";
		attributeKeyArray[11] = "uSNCreated";
		attributeKeyArray[12] = "uSNChanged";
		attributeKeyArray[13] = "distinguishedName";
		LdapGroup ldapGroupRoot = traversalRoot(config, dirContext,
				config.getDnDatum(), attributeKeyArray, searchControls);
		return ldapGroupRoot;
	}
	/**
	 * 初始化
	 * 
	 * @Description
	 * @Title findDirContext
	 * @param config
	 * @return 参数说明
	 * @return DirContext 返回类型
	 * @throws
	 */
	public DirContext findDirContext(LdapConfig config) {
		JsonTcpBean returnCode = new JsonTcpBean();
		DirContext dirContext = null;
		String account = config.getAccount();
		if (StringUtil.isBlank(account)) {
			returnCode.setDesc("认证失败,账号不能为空");
			returnCode.setSuccess(false);
			returnCode.setCode(ReturnCodeEnum.code404.name());
			returnCode.setMsg(ReturnCodeEnum.code404.getMsgCn());
			LogThreadLocal.setLog(returnCode);
			return dirContext;
		}
		String password = config.getPassword();
		if (StringUtil.isBlank(password)) {
			returnCode.setDesc("认证失败,密码不能为空");
			returnCode.setSuccess(false);
			returnCode.setCode(ReturnCodeEnum.code404.name());
			returnCode.setMsg(ReturnCodeEnum.code404.getMsgCn());
			LogThreadLocal.setLog(returnCode);
			return dirContext;
		}
		String root = config.getDnRoot();
		if (config.getUrlList() != null) {
			for (String url : config.getUrlList()) {
				Hashtable env = new Hashtable();
				env.put(Context.INITIAL_CONTEXT_FACTORY,
						"com.sun.jndi.ldap.LdapCtxFactory");
				env.put(Context.PROVIDER_URL, url + "/" + root);
				env.put(Context.SECURITY_AUTHENTICATION, "simple");
				env.put(Context.SECURITY_PRINCIPAL, account);
				env.put(Context.SECURITY_CREDENTIALS, password);
				try {
					// 连接ldap
					dirContext = new InitialDirContext(env);
					log.trace("认证成功");
					returnCode.setDesc("认证成功");
					returnCode.setSuccess(true);
					returnCode.setCode(ReturnCodeEnum.code200.name());
					returnCode.setMsg(ReturnCodeEnum.code200.getMsgCn());
					LogThreadLocal.setLog(returnCode);
					break;
				} catch (javax.naming.AuthenticationException e) {
					// e.printStackTrace();
					log.error(e.getStackTrace(), e);
					log.trace("认证失败");
					returnCode.setDesc("认证失败,error=" + e.getMessage());
					returnCode.setSuccess(false);
					returnCode.setCode(ReturnCodeEnum.code500.name());
					returnCode.setMsg(ReturnCodeEnum.code500.getMsgCn());
					LogThreadLocal.setLog(returnCode);
				} catch (Exception e) {
					// e.printStackTrace();
					log.error(e.getStackTrace(), e);
					log.trace("认证出错");
					returnCode.setDesc("认证出错,error=" + e.getMessage());
					returnCode.setSuccess(false);
					returnCode.setCode(ReturnCodeEnum.code500.name());
					returnCode.setMsg(ReturnCodeEnum.code500.getMsgCn());
					LogThreadLocal.setLog(returnCode);
				}
			}
		}
		return dirContext;
	}
	/**
	 * 遍历根节点
	 * 
	 * @Description:
	 * @Title: traversalRoot
	 * @param config
	 * @param dirContext
	 * @param rootGroupName
	 * @param attributeKeyArray
	 * @param searchControls
	 * @return
	 * @throws NamingException 参数说明
	 * @return LdapGroup 返回类型
	 * @throws
	 */
	public LdapGroup traversalRoot(LdapConfig config, DirContext dirContext,
			String rootGroupName, String[] attributeKeyArray,
			SearchControls searchControls) throws NamingException {
		LdapGroup ldapGroupRoot = new LdapGroup();
		// 父部门属性
		ldapGroupRoot = this.initGroupAttributes(config, dirContext,
				rootGroupName, attributeKeyArray, ldapGroupRoot);
		// 搜索基准DN
		// 搜索部门
		NamingEnumeration namingEnumerationGroup = dirContext
				.listBindings(rootGroupName);
		// 搜索用户
		NamingEnumeration namingEnumerationUser = null;
		if (StringUtils.isBlank(config.getFilter())) {
			namingEnumerationUser = dirContext.listBindings(rootGroupName);
		} else {
			namingEnumerationUser = dirContext.search(rootGroupName,
					config.getFilter(), searchControls);
		}
		while (namingEnumerationGroup.hasMore()) {
			Binding binding = (Binding) namingEnumerationGroup.next();
			String childGroupName = binding.getName();
			// 子部门
			if (childGroupName.startsWith("OU=")) {
				String childGroupNameNew = childGroupName + "," + rootGroupName;
				LdapGroup ldapGroup = this.traversalChild(config, dirContext,
						childGroupNameNew, attributeKeyArray, ldapGroupRoot,
						searchControls);
				ldapGroupRoot.getGroupList().add(ldapGroup);
			}
		}
		while (namingEnumerationUser.hasMore()) {
			Binding binding = (Binding) namingEnumerationUser.next();
			String childGroupName = binding.getName();
			// 父部门 用户
			if (childGroupName.startsWith("CN=")) {
				LdapUser ldapUser = new LdapUser();
				String childGroupNameNew = childGroupName + "," + rootGroupName;
				ldapUser = this.initUserAttributes(config, dirContext,
						childGroupNameNew, attributeKeyArray, ldapUser);
				ldapGroupRoot.getUserList().add(ldapUser);
			}
		}
		return ldapGroupRoot;
	}
	/**
	 * 遍历孩子节点
	 * 
	 * @Description:
	 * @Title: traversalChild
	 * @param config
	 * @param dirContext
	 * @param parentGroupName
	 * @param attributeKeyArray
	 * @param ldapGroupRoot
	 * @param searchControls
	 * @return
	 * @throws NamingException 参数说明
	 * @return LdapGroup 返回类型
	 * @throws
	 */
	public LdapGroup traversalChild(LdapConfig config, DirContext dirContext,
			String parentGroupName, String[] attributeKeyArray,
			LdapGroup ldapGroupRoot, SearchControls searchControls)
			throws NamingException {
		LdapGroup ldapGroupChild = new LdapGroup();
		// 父部门属性
		ldapGroupChild = this.initGroupAttributes(config, dirContext,
				parentGroupName, attributeKeyArray, ldapGroupChild);
		// 搜索基准DN
		NamingEnumeration namingEnumerationGroup = dirContext
				.listBindings(parentGroupName);
		NamingEnumeration namingEnumerationUser = null;
		if (StringUtils.isBlank(config.getFilter())) {
			namingEnumerationUser = dirContext.listBindings(parentGroupName);
		} else {
			namingEnumerationUser = dirContext.search(parentGroupName,
					config.getFilter(), searchControls);
		}
		while (namingEnumerationGroup.hasMore()) {
			Binding binding = (Binding) namingEnumerationGroup.next();
			String childGroupName = binding.getName();
			// 子部门
			if (childGroupName.startsWith("OU=")) {
				String childGroupNameNew = childGroupName + ","
						+ parentGroupName;
				LdapGroup ldapGroup = this.traversalChild(config, dirContext,
						childGroupNameNew, attributeKeyArray, ldapGroupChild,
						searchControls);
				// log.trace("name2="+ldapGroup.getName());
				ldapGroupChild.getGroupList().add(ldapGroup);
			}
		}
		while (namingEnumerationUser.hasMore()) {
			Binding binding = (Binding) namingEnumerationUser.next();
			String childGroupName = binding.getName();
			// 父部门用户
			if (childGroupName.startsWith("CN=")) {
				LdapUser ldapUser = new LdapUser();
				String childGroupNameNew = childGroupName + ","
						+ parentGroupName;
				ldapUser = this.initUserAttributes(config, dirContext,
						childGroupNameNew, attributeKeyArray, ldapUser);
				ldapGroupChild.getUserList().add(ldapUser);
			}
		}
		return ldapGroupChild;
	}
	/**
	 * 设置部门的属性
	 * 
	 * @Description:
	 * @Title: initGroupAttributes
	 * @param dirContext
	 * @param groupName
	 * @param attributeKeyArray
	 * @param ldapGroup
	 * @return
	 * @throws NamingException 参数说明
	 * @return LdapGroup 返回类型
	 * @throws
	 */
	public LdapGroup initGroupAttributes(LdapConfig config,
			DirContext dirContext, String groupName,
			String[] attributeKeyArray, LdapGroup ldapGroup)
			throws NamingException {
		// 父部门属性
		Attributes attributeList = dirContext.getAttributes(groupName,
				attributeKeyArray);
		NamingEnumeration attributeNamingEnumeration = attributeList.getAll();
		while (attributeNamingEnumeration.hasMore()) {
			Attribute attribute = (Attribute) attributeNamingEnumeration.next();
			String attributeKey = attribute.getID();
			StringBuilder attributeValueStringBuilder = new StringBuilder();
			NamingEnumeration attributeValueNamingEnumeration = attribute
					.getAll();
			while (attributeValueNamingEnumeration.hasMore()) {
				attributeValueStringBuilder
						.append(attributeValueNamingEnumeration.next());
				attributeValueStringBuilder.append(",");
			}
			attributeValueStringBuilder.deleteCharAt(attributeValueStringBuilder
					.length() - 1);
			if (attributeKey.equals(config.getAttDeptName())) {
				ldapGroup.setName(attributeValueStringBuilder.toString());
			}
			if (attributeKey.equals(config.getAttDeptDescription())) {
				ldapGroup.setDescription(attributeValueStringBuilder.toString());
			}
			if (attributeKey.equals("whenCreated")) {
				ldapGroup.setWhenCreated(attributeValueStringBuilder.toString());
			}
			if (attributeKey.equals("whenChanged")) {
				ldapGroup.setWhenChanged(attributeValueStringBuilder.toString());
			}
			if (attributeKey.equals("uSNCreated")) {
				ldapGroup.setUsnCreated(attributeValueStringBuilder.toString());
			}
			if (attributeKey.equals("uSNChanged")) {
				ldapGroup.setUsnChanged(attributeValueStringBuilder.toString());
			}
			if (attributeKey.equals("distinguishedName")) {
				ldapGroup.setDistinguishedName(attributeValueStringBuilder
						.toString());
			}
		}
		return ldapGroup;
	}
	/**
	 * 
	 * 设置用户的属性
	 * 
	 * @Description:
	 * @Title: initUserAttributes
	 * @param dirContext
	 * @param groupNameChildNew
	 * @param attributeKeyArray
	 * @param ldapUser
	 * @return
	 * @throws NamingException 参数说明
	 * @return LdapUser 返回类型
	 * @throws
	 */
	public LdapUser initUserAttributes(LdapConfig config,
			DirContext dirContext, String groupNameChildNew,
			String[] attributeKeyArray, LdapUser ldapUser)
			throws NamingException {
		Attributes attributeListChild = dirContext.getAttributes(
				groupNameChildNew, attributeKeyArray);
		NamingEnumeration attributeChildNamingEnumeration = attributeListChild
				.getAll();
		while (attributeChildNamingEnumeration.hasMore()) {
			Attribute attribute = (Attribute) attributeChildNamingEnumeration
					.next();
			String attributeKey = attribute.getID();
			StringBuilder attributeValueStringBuilder = new StringBuilder();
			NamingEnumeration attributeValueNamingEnumeration = attribute
					.getAll();
			while (attributeValueNamingEnumeration.hasMore()) {
				attributeValueStringBuilder
						.append(attributeValueNamingEnumeration.next());
				attributeValueStringBuilder.append(",");
			}
			attributeValueStringBuilder.deleteCharAt(attributeValueStringBuilder
					.length() - 1);
			if (attributeKey.equals(config.getAttUserNo())) {
				ldapUser.setCode(attributeValueStringBuilder.toString());
			}
			if (attributeKey.equals(config.getAttUserAcc())) {
				ldapUser.setAccount(attributeValueStringBuilder.toString());
			}
			if (attributeKey.equals(config.getAttUserName())) {
				ldapUser.setName(attributeValueStringBuilder.toString());
			}
			if (attributeKey.equals(config.getAttUserTel())) {
				ldapUser.setTel(attributeValueStringBuilder.toString());
			}
			if (attributeKey.equals(config.getAttUserMail())) {
				ldapUser.setMail(attributeValueStringBuilder.toString());
			}
			if (attributeKey.equals(config.getAttUserDescription())) {
				ldapUser.setDescription(attributeValueStringBuilder.toString());
			}
			if (attributeKey.equals("whenCreated")) {
				ldapUser.setWhenCreated(attributeValueStringBuilder.toString());
			}
			if (attributeKey.equals("whenChanged")) {
				ldapUser.setWhenChanged(attributeValueStringBuilder.toString());
			}
			if (attributeKey.equals("uSNCreated")) {
				ldapUser.setUsnCreated(attributeValueStringBuilder.toString());
			}
			if (attributeKey.equals("uSNChanged")) {
				ldapUser.setUsnChanged(attributeValueStringBuilder.toString());
			}
			if (attributeKey.equals("distinguishedName")) {
				ldapUser.setDistinguishedName(attributeValueStringBuilder
						.toString());
			}
		}
		return ldapUser;
	}
}
