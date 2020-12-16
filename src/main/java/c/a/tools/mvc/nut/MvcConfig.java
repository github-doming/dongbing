package c.a.tools.mvc.nut;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServlet;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import c.a.util.core.path.PathThreadLocal;
import c.a.util.core.path.PathUtil;
import c.a.util.core.properties.PropertiesUtil;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.boot.BootServlet;
import c.a.config.SysConfig;
import c.a.tools.mvc.dto.MvcActionDto;
import c.a.tools.mvc.dto.MvcResultDto;
import c.a.tools.mvc.xml.MvcXmlUtil;
/**
 * 
 * mvc的核心api(所有配置)
 * 
 * @Description:
 * @date 2002年5月10日 上午10:28:38
 * @author cxy
 * @Email:
 * @Copyright
 * 
 */
public class MvcConfig extends HttpServlet {
	protected Logger log = LogManager.getLogger(this.getClass());
	// public static String configMvcXml = "/config/mvc/mvc.xml";
	// public static String configMvcProperties = "/config/mvc/mvc.properties";
	public static String configMvcProperties = null;
	private static Map<String, MvcActionDto> map = null;
	private static List<MvcActionDto> list = null;
	private static MvcConfig instance = null;
	private final static Object key = new Object();
	/**
	 * 私有的默认构造子
	 */
	private MvcConfig() {
	}
	public static MvcConfig findInstance() {
		if (instance == null) {
			synchronized (key) {
				if (instance == null) {
					instance = new MvcConfig();
				}
			}
		}
		return instance;
	}
	/**
	 * 查找 ConfigResultVo
	 * 
	 * @Title: findMvcResult
	 * @Description:
	 *
	 * 				参数说明
	 * @param resultName
	 * @param resultList
	 * @return
	 * @throws Exception
	 *             返回类型 MvcResultDto
	 */
	public MvcResultDto findMvcResult(String resultName, List<MvcResultDto> resultList) throws Exception {
		boolean isFind = false;
		MvcResultDto mvcResultDtoRetrun = null;
		for (MvcResultDto mvcResultDto : resultList) {
			if (resultName.equals(mvcResultDto.getName())) {
				mvcResultDtoRetrun = mvcResultDto;
				isFind = true;
				break;
			}
		}
		// 如果找不到与action所关联的Result，将去全局的页面查找
		if (isFind) {
			return mvcResultDtoRetrun;
		} else {
			return null;
		}
	}
	/**
	 * 查找ConfigActionVo
	 * 
	 * @Title: findMvcAction
	 * @Description:
	 *
	 * 				参数说明
	 * @param url
	 * @return
	 * @throws Exception
	 *             返回类型 MvcActionDto
	 */
	public MvcActionDto findMvcAction(String url) throws Exception {
		MvcActionDto mvcActionDtoReturn = findMap().get(url);
		if (mvcActionDtoReturn == null) {
			log.trace("找不到action,url=" + url);
		} else {
			if (false) {
				log.trace("找到action,url=" + url);
				log.trace("找到action,action=" + mvcActionDtoReturn.getActionClass());
			}
		}
		return mvcActionDtoReturn;
	}

	/**
	 * 查找MvcActionDto
	 * 
	 * @Title: findMap
	 * @Description:
	 *
	 * 				参数说明
	 * @return
	 * @throws Exception
	 *             返回类型 Map<String,MvcActionDto>
	 */
	public Map<String, MvcActionDto> findMap() throws Exception {
		if (map == null) {
			map = new HashMap<String, MvcActionDto>();
			List<MvcActionDto> mvcActionDtoList = this.findList();
			for (MvcActionDto mvcActionDto : mvcActionDtoList) {
				map.put(mvcActionDto.getUrl(), mvcActionDto);
			}
		}
		return map;
	}
	/**
	 * 查找MvcActionDto
	 * 
	 * @Title: findMvcActionList
	 * @Description:
	 *
	 * 				参数说明
	 * @return
	 * @throws Exception
	 *             返回类型 List<MvcActionDto>
	 */
	public List<MvcActionDto> findList() throws Exception {
		if (list == null) {
			list = new ArrayList<MvcActionDto>();
			PathUtil pathUtil = PathThreadLocal.findThreadLocal().get();
			MvcXmlUtil mvcXml = new MvcXmlUtil();
			PropertiesUtil propertiesUtil = new PropertiesUtil();
			// 第1次加载公用的mvc配置
			String mvcLocalProperties = SysConfig.findInstance().findMap().get("mvc.local.properties").toString();
			log.trace("mvcLocalProperties=" + mvcLocalProperties);
			Set<String> configKeyList = propertiesUtil.findKeyList2Set(mvcLocalProperties);
			if (configKeyList != null) {
				for (String configKey : configKeyList) {
					if (StringUtil.isBlank(configKey)) {
						continue;
					}
					if (mvcXml.build(pathUtil.findPath(configKey))) {
					} else {
						continue;
					}
					List<MvcActionDto> mvcActionList = mvcXml.findMvcActionDtoList();
					if (mvcActionList != null) {
						list.addAll(mvcActionList);
					}
				}
			}
			// 第2次加载布局的mvc配置
			mvcXml = new MvcXmlUtil();
			String commLocalProject = SysConfig.findInstance().findMap().get("comm.local.type").toString();
			String mvcLayoutProperties = null;
			if ("sys".equals(commLocalProject)) {
				mvcLayoutProperties = SysConfig.findInstance().findMap().get("mvc.sys.properties").toString();
			}
			if ("app".equals(commLocalProject)) {
				mvcLayoutProperties = SysConfig.findInstance().findMap().get("mvc.app.properties").toString();
			}
			log.trace("mvcLayoutProperties=" + mvcLayoutProperties);
			configKeyList = propertiesUtil.findKeyList2Set(mvcLayoutProperties);
			if (configKeyList != null) {
				for (String configKey : configKeyList) {
					if (StringUtil.isBlank(configKey)) {
						continue;
					}
					if (mvcXml.build(pathUtil.findPath(configKey))) {
					} else {
						continue;
					}
					List<MvcActionDto> mvcActionList = mvcXml.findMvcActionDtoList();
					if (mvcActionList != null) {
						list.addAll(mvcActionList);
					}
				}
			}
		}
		return list;
	}
	/**
	 * 查找MvcActionDto
	 * 
	 * @deprecated
	 * @Description:
	 *
	 * 				参数说明
	 * @return
	 * @throws Exception
	 *             返回类型 List<MvcActionDto>
	 */
	public List<MvcActionDto> findList_v1() throws Exception {
		if (list == null) {
			list = new ArrayList<MvcActionDto>();
			PathUtil pathUtil = PathThreadLocal.findThreadLocal().get();
			MvcXmlUtil mvcXml = new MvcXmlUtil();
			PropertiesUtil propertiesUtil = new PropertiesUtil();
			MvcConfig.configMvcProperties = SysConfig.findInstance().findMap().get("mvc.local.properties").toString();
			log.trace("ConfigMvcProperties=" + MvcConfig.configMvcProperties);
			Set<String> configKeyList = propertiesUtil.findKeyList2Set(MvcConfig.configMvcProperties);
			if (configKeyList != null) {
				for (String configKey : configKeyList) {
					if (false) {
						log.trace("ConfigMvcProperties key=" + configKey);
						if (StringUtil.isBlank(configKey)) {
							continue;
						}
						// log.trace("ConfigMvcProperties key=" +
						// key.indexOf("/*"));
						// 好像是注释/* */
						if (configKey.indexOf("/*") >= 0) {
							log.trace("ConfigMvcProperties key /*=" + configKey);
							continue;
						}
					}
					if (StringUtil.isBlank(configKey)) {
						continue;
					}
					if (mvcXml.build(pathUtil.findPath(configKey))) {
					} else {
						continue;
					}
					List<MvcActionDto> mvcActionList = mvcXml.findMvcActionDtoList();
					if (mvcActionList != null) {
						list.addAll(mvcActionList);
					}
				}
			}
		}
		return list;
	}
}