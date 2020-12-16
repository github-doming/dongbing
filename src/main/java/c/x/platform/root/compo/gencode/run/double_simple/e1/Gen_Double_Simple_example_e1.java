package c.x.platform.root.compo.gencode.run.double_simple.e1;
import java.sql.Connection;

import org.junit.Test;

import c.x.platform.root.compo.gencode.util.common.BaseGenDouble;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.nut.JdbcToolFactory;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.test.CommTest;
import c.a.tools.velocity.VelocityConfig;
/**
 * 
 * 生成部门与人员_简单功能
 * 
 * @author yourname
 * 
 */
public class Gen_Double_Simple_example_e1 extends CommTest {
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
		BaseGenDouble gen = new BaseGenDouble();
		String final_folderPath = "d:\\gen";
		try {
			if (true) {
				// 生成代码_部门与人员_简单
				gen.gen_doubleSimple(final_folderPath,
						"config.mvc.c.x.platform.admin.gen",
						"c.x.platform.admin.gen", "c.x.platform.admin.gen",
						"gen_double_simple", "sys_org", "sys_user",
						"sys_org_user");
			}

			if (false) {
				// 生成代码_部门与人员_简单
				gen.gen_doubleSimple(final_folderPath,
						"config.mvc.c.x.platform.admin.gen",
						"c.x.platform.admin.gen", "c.x.platform.admin.gen",
						"gen_double_simple", "sys_org",
						"config.mvc.c.x.platform.admin.gen",
						"c.x.platform.admin.gen", "c.x.platform.admin.gen",
						"gen_double_simple", "sys_user",
						"config.mvc.c.x.platform.admin.gen",
						"c.x.platform.admin.gen", "c.x.platform.admin.gen",
						"gen_double_simple", "sys_org_user");
			}
			if (false) {
				// miniui
				// 生成代码_部门与人员_简单
				gen.gen_doubleSimple(final_folderPath,
						"config.mvc.c.x.all.admin.third.miniui.bs.admin",
						"pages.c.x.all.admin.third.miniui.bs.admin",
						"c.x.all.admin.third.miniui.bs.admin", "cx_org_user",
						"sys_org", "sys_user", "sys_org_user");
			}
			if (false) {
				// 生成代码_产品分组与产品_简单
				gen.gen_doubleSimple(final_folderPath,
						"config.mvc.c.x.platform.admin.feng.pss",
						"pages.c.x.platform.admin.feng.pss",
						"c.x.platform.admin.feng.pss", "gen3",
						"pss_productgroup_info", "pss_product_info",
						"pss_productgroup_product");
			}
			if (false) {
				// 生成代码_论坛_简单
				gen.gen_doubleSimple(final_folderPath,
						"config.mvc.c.x.platform.admin.feng.bbs",
						"pages.c.x.platform.admin.feng.bbs",
						"c.x.platform.admin.feng.bbs", "buyer",
						"bbs_forum_info", "bbs_post_info", "bbs_forum_post");
			}
			if (false) {
				// 生成代码_记事分组与记事_简单
				gen.gen_doubleSimple(final_folderPath,
						"config.mvc.c.x.platform.admin.feng.bbs",
						"pages.c.x.platform.admin.feng.bbs",
						"c.x.platform.admin.feng.bbs", "sys",
						"bbs_notegroup_info", "bbs_note_info",
						"bbs_notegroup_note");
			}
			if (false) {
				// 生成代码_布局_菜单
				gen.gen_doubleSimple_sameTable(final_folderPath, "config_zz",
						"pages_zz", "co.zzsoft", "layout_menu_first",
						"zz_sys_menu_info", "layout_menu_second",
						"layout_menu_third");
			}
			if (false) {
				// 生成代码_布局_菜单
				gen.gen_doubleSimple_sameTable(final_folderPath, "config_zz_a",
						"pages_zz_b", "co_c", "layout_menu_first",
						"zz_sys_menu_info", "layout_menu_second",
						"layout_menu_third");
			}
			if (false) {
				// 生成代码_布局_菜单
				gen.gen_doubleSimple_sameTable(final_folderPath, "config_zz.a",
						"pages_zz.b", "co.c", "layout_menu_first",
						"zz_sys_menu_info", "layout_menu_second",
						"layout_menu_third");
			}
			if (false) {
				// 生成代码_布局_菜单
				gen.gen_doubleSimple_sameTable(final_folderPath,
						"config.config_zz", "pages_zz", "co.zzsoft",
						"layout_menu_first", "zz_sys_menu_info",
						"layout_menu_second", "layout_menu_third");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.trace("end");
	}
}
