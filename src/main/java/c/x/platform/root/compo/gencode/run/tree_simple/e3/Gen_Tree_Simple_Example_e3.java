package c.x.platform.root.compo.gencode.run.tree_simple.e3;
import java.sql.Connection;

import org.junit.Test;

import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.nut.JdbcToolFactory;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.x.platform.root.compo.gencode.util.common.BaseGen;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.test.CommTest;
import c.a.tools.velocity.VelocityConfig;
/**
 * 
 * 生成树_简单功能
 * 
 * @author yourname
 * 
 */
public class Gen_Tree_Simple_Example_e3 extends CommTest {
	@Test
	public void execute() {
		// 数据库设置
		String driver = c.x.platform.root.compo.gencode.util.config.Database.driver;
		String url = c.x.platform.root.compo.gencode.util.config.Database.url;
		String username = c.x.platform.root.compo.gencode.util.config.Database.user;
		String password = c.x.platform.root.compo.gencode.util.config.Database.pwd;
		IJdbcTool jdbcTool = JdbcToolFactory.createApi(url);
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = null;
		try {
			conn = jdbcUtil.openConnection(url, username, password);
			jdbcUtil.setConnection(conn);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		/**
		 * 保存连接到ThreadLocal
		 */
		JdbcThreadLocal.findJdbcToolThreadLocal().set(jdbcTool);
		VelocityConfig.velocityProperties = "/config/platform/root/velocity/velocity.properties";
		BaseGen gen = new BaseGen();
		String final_folderPath = "d:\\gen";
		try {
			if (false) {
				gen.gen_treeSimple(final_folderPath,
						"config.mvc.c.x.platform.sys", "c.x.platform.sys",
						"c.x.platform.sys", "cx", "sys_menu");
			}
			if (true) {
				// 生成代码_树简单
				gen.gen_treeSimple(final_folderPath,
						"config.mvc.c.x.platform.admin.gen",
						"c.x.platform.admin.gen", "c.x.platform.admin.gen",
						"gen_tree_simple", "fun_single_simple");
			}
			if (false) {
				// 微信公共号,业务类
				gen.gen_treeSimple(final_folderPath,
						"config.mvc.c.x.all.admin.third.weixin.pub.bs",
						"pages.c.x.all.admin.third.weixin.pub.bs",
						"c.x.all.admin.third.weixin.pub.bs", "gen",
						"wx_p_menu_info");
			}
			if (false) {
				gen.gen_treeSimple(final_folderPath,
						"config.mvc.c.x.platform.admin.gen",
						"c.x.platform.admin.gen", "c.x.platform.admin.gen",
						"gen", "sys_area_info");
			}
			if (false) {
				gen.gen_treeSimple(final_folderPath,
						"config.mvc.c.x.platform.admin.gen",
						"c.x.platform.admin.gen", "c.x.platform.admin.gen",
						"gen", "sys_map_info");
			}
			if (false) {
				// 生成代码_商品分类_菜单
				gen.gen_treeSimple(final_folderPath,
						"config.mvc.c.x.platform.admin.feng.pss",
						"pages.c.x.platform.admin.feng.pss",
						"c.x.platform.admin.feng.pss", "gen2",
						"pss_productgroup_info");
			}
			if (false) {
				// 生成代码_布局_菜单
				gen.gen_treeSimple(final_folderPath, "config_a", "pages_b",
						"co_c", "gen_tree_simple", "fun_single_simple");
			}
			if (false) {
				// 生成代码_布局_菜单
				gen.gen_treeSimple(final_folderPath, "config.a", "pages.b",
						"co.c", "gen_tree_simple", "fun_single_simple");
			}
			if (false) {
				gen.gen_treeSimple(final_folderPath, "",
						"pages5.zzsoft6.sysadmin7", "org1.zzsoft2.sysadmin3",
						"fun", "sys_menu_info");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.trace("end");
	}
}
