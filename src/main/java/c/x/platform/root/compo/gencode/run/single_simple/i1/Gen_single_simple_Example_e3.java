package c.x.platform.root.compo.gencode.run.single_simple.i1;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.nut.JdbcToolFactory;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.x.platform.root.compo.gencode.util.common.BaseGen;
import c.a.util.core.file.FileUtil;
import c.a.util.core.jdbc.bean.create.TableBean;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.string.StringUtil;
import c.a.util.core.test.CommTest;
import c.a.tools.velocity.VelocityConfig;
public class Gen_single_simple_Example_e3 extends CommTest {
	@Test
	public void execute() {
		// 数据库设置
		String driver = c.x.platform.root.compo.gencode.util.config.Database.driverT;
		String url = c.x.platform.root.compo.gencode.util.config.Database.urlT;
		String username = c.x.platform.root.compo.gencode.util.config.Database.userT;
		String password = c.x.platform.root.compo.gencode.util.config.Database.pwdT;
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

			
			if (true) {

				gen.gen_singleSimple(final_folderPath, "all.gen", "all.gen", "all.gen", "t", "sys_quartz_config");
				gen.gen_singleSimple(final_folderPath, "all.gen", "all.gen", "all.gen", "t", "sys_quartz_job");
				gen.gen_singleSimple(final_folderPath, "all.gen", "all.gen", "all.gen", "t", "sys_quartz_log");
				gen.gen_singleSimple(final_folderPath, "all.gen", "all.gen", "all.gen", "t", "sys_quartz_trigger");

			}
			if (false) {
				gen.gen_singleSimple(final_folderPath, "all.upload", "all.upload", "all.upload", "t", "sys_file");
				gen.gen_singleSimple(final_folderPath, "all.upload", "all.upload", "all.upload", "t", "sys_bytes");

				gen.gen_singleSimple(final_folderPath, "all.gen", "all.gen", "all.gen", "t", "sys_map");
				gen.gen_singleSimple(final_folderPath, "all.gen", "all.gen", "all.gen", "t", "sys_list");

			}
			if (false) {

				gen.gen_singleSimple(final_folderPath, "config.mvc.tbank.gen", "tbank.gen", "tbank.gen", "cx",
						"tbank_log_notify");
				gen.gen_singleSimple(final_folderPath, "config.mvc.tbank.gen", "tbank.gen", "tbank.gen", "cx",
						"tbank_config");

			}
			if (false) {

				gen.gen_singleSimple(final_folderPath, "config.mvc.wsd.gen", "wsd.gen", "wsd.gen", "cx",
						"tbank_log_req");
				gen.gen_singleSimple(final_folderPath, "config.mvc.wsd.gen", "wsd.gen", "wsd.gen", "cx",
						"tbank_log_resp");

				gen.gen_singleSimple(final_folderPath, "config.mvc.wsd.gen", "wsd.gen", "wsd.gen", "cx",
						"tbank_account");

				gen.gen_singleSimple(final_folderPath, "config.mvc.wsd.gen", "wsd.gen", "wsd.gen", "cx",
						"tbank_acc_balance");

				gen.gen_singleSimple(final_folderPath, "config.mvc.wsd.gen", "wsd.gen", "wsd.gen", "cx",
						"tbank_trade_status");

			}

			if (false) {

				gen.gen_singleSimple(final_folderPath, "config.mvc.wsd.gen", "wsd.gen", "wsd.gen", "cx",
						"lend_project_user");

				gen.gen_singleSimple(final_folderPath, "config.mvc.wsd.gen", "wsd.gen", "wsd.gen", "cx",
						"lend_invest_user");

				gen.gen_singleSimple(final_folderPath, "config.mvc.wsd.gen", "wsd.gen", "wsd.gen", "cx",
						"lend_loan_evaluation");

				gen.gen_singleSimple(final_folderPath, "config.mvc.wsd.gen", "wsd.gen", "wsd.gen", "cx",
						"lend_loan_info");

				gen.gen_singleSimple(final_folderPath, "config.mvc.wsd.gen", "wsd.gen", "wsd.gen", "cx",
						"lend_loan_user");

				gen.gen_singleSimple(final_folderPath, "config.mvc.wsd.gen", "wsd.gen", "wsd.gen", "cx",
						"lend_project");
				gen.gen_singleSimple(final_folderPath, "config.mvc.wsd.gen", "wsd.gen", "wsd.gen", "cx",
						"lend_session");

				gen.gen_singleSimple(final_folderPath, "config.mvc.wsd.gen", "wsd.gen", "wsd.gen", "cx",
						"lend_user_bank_card");

				gen.gen_singleSimple(final_folderPath, "config.mvc.wsd.gen", "wsd.gen", "wsd.gen", "cx",
						"lend_user_evaluation");

				gen.gen_singleSimple(final_folderPath, "config.mvc.wsd.gen", "wsd.gen", "wsd.gen", "cx",
						"lend_user_limit");

				gen.gen_singleSimple(final_folderPath, "config.mvc.wsd.gen", "wsd.gen", "wsd.gen", "cx", "lend_user");

				gen.gen_singleSimple(final_folderPath, "config.mvc.wsd.gen", "wsd.gen", "wsd.gen", "cx",
						"lend_pay_info");

				gen.gen_singleSimple(final_folderPath, "config.mvc.wsd.gen", "wsd.gen", "wsd.gen", "cx",
						"lend_pay_plan");
			}
			if (false) {
				// 快速开发框架
				// 参数
				String sql = null;
				ArrayList<Object> parameterList = null;
				String filePath = "d://gen//mvc.txt";
				FileUtil fileUtil = new FileUtil();
				String schemata = "c2";
				String tableName = null;
				String tableComment = null;
				// 业务
				Connection connDB = jdbcUtil.openConnection(url, username, password);
				List<TableBean> tableBeanList = jdbcUtil.findTableBeanListByApi(connDB, schemata, null);
				for (TableBean table : tableBeanList) {
					log.trace("table name=" + table.getTableName());
					tableName = table.getTableName().toLowerCase();
					tableComment = table.getComment();
					if (StringUtil.isBlank(tableComment)) {
						tableComment = tableName;
					}
					String tableNameClass = StringUtil.findClassName(tableName);
					String content = "/config/mvc/wsd/" + tableName + "/" + tableNameClass + "Cx.xml";
					fileUtil.appendByAbsolutePath(filePath, content);

					// 生成代码
					gen.gen_singleSimple(final_folderPath, "config.mvc.wsd", "wsd", "wsd", "cx", tableName);
					// 菜单生成
					sql = "insert into SYS_MENU (SYS_MENU_ID_,PARENT_,PATH_,NAME_,URL_,PERMISSION_GRADE_,IS_SHOW_) values(?,?,?,?,?,?,?) ";
					parameterList = new ArrayList<Object>();
					String id = UUID.randomUUID().toString();
					String parent = "fdaaa222-ad6b-44a6-87e9-4cd76b1bb8b4";
					String parentPath = "1.a0c9a158-3ba3-467d-83af-5d6ed25713b4.fdaaa222-ad6b-44a6-87e9-4cd76b1bb8b4.";
					String menuUrl = "/wsd/" + tableName + "/cx/list.do";
					parameterList.add(id);
					parameterList.add(parent);
					parameterList.add(parentPath + id + ".");
					parameterList.add(tableComment);
					parameterList.add(menuUrl);
					parameterList.add("10");
					parameterList.add("1");
					jdbcUtil.execute(conn, sql, parameterList);
				}
			}
			if (false) {
				// 账号
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.sys", "c.x.platform.sys",
						"c.x.platform.sys", "cx", "sys_account");
			}
			if (false) {
				// if (false) {
				// fun_single_simple_t
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.gen", "c.x.platform.admin.gen",
						"c.x.platform.admin.gen", "gen_single_simple", "fun_single_simple");
			}
			if (false) {
				// 微信公共号,业务类
				gen.gen_singleSimple(final_folderPath, "config.mvc.all.wx.pub.pc",
						"all.wx.pub.pc", "all.wx.pub.pc", "gen",
						"wx_p_article_t");
				gen.gen_singleSimple(final_folderPath, "config.mvc.all.wx.pub.pc",
						"all.wx.pub.pc", "all.wx.pub.pc", "gen",
						"wx_p_config_t");
				gen.gen_singleSimple(final_folderPath, "config.mvc.all.wx.pub.pc",
						"all.wx.pub.pc", "all.wx.pub.pc", "gen",
						"wx_p_material_t");
				gen.gen_singleSimple(final_folderPath, "config.mvc.all.wx.pub.pc",
						"all.wx.pub.pc", "all.wx.pub.pc", "gen",
						"wx_p_menu_t");
				// 微信公共号,业务类
				// 上行消息，文本消息
				// gen.gen_single_simple(final_folderPath,
				// "config.mvc.all.wx.pub.pc",
				// "all.wx.pub.pc",
				// "all.wx.pub.pc", "gen",
				// "wx_p_mo_text_t");
				// 微信公共号,业务类
				gen.gen_singleSimple(final_folderPath, "config.mvc.all.wx.pub.pc",
						"all.wx.pub.pc", "all.wx.pub.pc", "gen",
						"wx_p_mt_article_t");
				gen.gen_singleSimple(final_folderPath, "config.mvc.all.wx.pub.pc",
						"all.wx.pub.pc", "all.wx.pub.pc", "gen",
						"wx_p_mt_material_temp_t");
				gen.gen_singleSimple(final_folderPath, "config.mvc.all.wx.pub.pc",
						"all.wx.pub.pc", "all.wx.pub.pc", "gen",
						"wx_p_mt_news_t");
				gen.gen_singleSimple(final_folderPath, "config.mvc.all.wx.pub.pc",
						"all.wx.pub.pc", "all.wx.pub.pc", "gen",
						"wx_p_mt_user_news_t");
				gen.gen_singleSimple(final_folderPath, "config.mvc.all.wx.pub.pc",
						"all.wx.pub.pc", "all.wx.pub.pc", "gen",
						"wx_p_news_t_article");
				gen.gen_singleSimple(final_folderPath, "config.mvc.all.wx.pub.pc",
						"all.wx.pub.pc", "all.wx.pub.pc", "gen",
						"wx_p_news_t_t");
				gen.gen_singleSimple(final_folderPath, "config.mvc.all.wx.pub.pc",
						"all.wx.pub.pc", "all.wx.pub.pc", "gen",
						"wx_p_user_t");
				gen.gen_singleSimple(final_folderPath, "config.mvc.all.wx.pub.pc",
						"all.wx.pub.pc", "all.wx.pub.pc", "gen",
						"wx_p_user_news_t_t");
				// 微信企业号第三方授权，业务类
				gen.gen_singleSimple(final_folderPath, "config.mvc.all.wx.ent_third.pc",
						"all.wx.ent_third.pc", "all.wx.ent_third.pc", "cx",
						"wx_qy_third_config_t");
			}
			if (false) {
				// fun_type
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.gen", "c.x.platform.admin.gen",
						"c.x.platform.admin.gen", "fun", "fun_type_str");
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.gen", "c.x.platform.admin.gen",
						"c.x.platform.admin.gen", "fun", "fun_type_int");
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.gen", "c.x.platform.admin.gen",
						"c.x.platform.admin.gen", "fun", "fun_type_long");
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.gen", "c.x.platform.admin.gen",
						"c.x.platform.admin.gen", "fun", "fun_type_all");
			}
			// if (true) {
			if (false) {
				// sys_user
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.sys", "c.x.platform.sys",
						"c.x.platform.sys", "cx", "sys_user");
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.sys", "c.x.platform.sys",
						"c.x.platform.sys", "cx_role", "sys_group");
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.sys", "c.x.platform.sys",
						"c.x.platform.sys", "cx", "sys_group_user");
			}
			if (false) {
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.sys", "c.x.platform.sys",
						"c.x.platform.sys", "cx", "sys_request_token");
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.sys", "c.x.platform.sys",
						"c.x.platform.sys", "cx", "sys_menu");
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.sys", "c.x.platform.sys",
						"c.x.platform.sys", "cx", "sys_exception");
			}
			if (false) {

				String filePath = "d://gen//mvc.txt";
				FileUtil fileUtil = new FileUtil();
				String tableName = null;
				String tableNameClass = null;
				String content = null;
				tableName = "sys_list";
				tableNameClass = StringUtil.findClassName(tableName);
				content = "/config/mvc/c/x/platform/sys/" + tableName + "/" + tableNameClass + "Cx.xml";
				fileUtil.appendByAbsolutePath(filePath, content);
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.sys", "c.x.platform.sys",
						"c.x.platform.sys", "cx", tableName);

				tableName = "sys_map";
				tableNameClass = StringUtil.findClassName(tableName);
				content = "/config/mvc/c/x/platform/sys/" + tableName + "/" + tableNameClass + "Cx.xml";
				fileUtil.appendByAbsolutePath(filePath, content);

				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.sys", "c.x.platform.sys",
						"c.x.platform.sys", "cx", tableName);
				//
				// gen.gen_single_simple(final_folderPath,
				// "config.mvc.c.x.platform.sys",
				// "c.x.platform.sys", "c.x.platform.sys", "gen",
				// "sys_quartz_job");

			}
			if (false) {
				// 主键
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.b.feng.crud.crud.admin",
						"pages.c.x.b.feng.crud.crud.admin", "c.x.b.feng.crud.crud.admin", "gen", "SYS_ID");
			}
			if (false) {
				// 微信企业号，业务类
				gen.gen_singleSimple(final_folderPath, "config.mvc.all.wx.ent.pc",
						"all.wx.ent.pc", "all.wx.ent.pc", "gen",
						"wx_qy_config_t");
			}
			if (false) {
				// 上行消息，文本消息
				gen.gen_singleSimple(final_folderPath, "config.mvc.all.wx.pub.pc",
						"all.wx.pub.pc", "all.wx.pub.pc", "gen",
						"wx_p_mo_text_t");
			}
			if (false) {
				// 工作流
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.feng.wf",
						"c.x.platform.admin.feng.wf", "c.x.platform.admin.feng.wf", "cx", "wf_define_business_t");
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.feng.wf",
						"c.x.platform.admin.feng.wf", "c.x.platform.admin.feng.wf", "cx", "wf_instance_business_t");
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.feng.wf",
						"c.x.platform.admin.feng.wf", "c.x.platform.admin.feng.wf", "cx", "wf_def_form_t");
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.feng.wf",
						"c.x.platform.admin.feng.wf", "c.x.platform.admin.feng.wf", "cx", "wf_ins_form_t");
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.feng.wf",
						"c.x.platform.admin.feng.wf", "c.x.platform.admin.feng.wf", "cx", "wf_def_field_t");
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.feng.wf",
						"c.x.platform.admin.feng.wf", "c.x.platform.admin.feng.wf", "cx", "wf_def_field_permission_t");
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.feng.wf",
						"c.x.platform.admin.feng.wf", "c.x.platform.admin.feng.wf", "cx", "wf_ins_field_t");
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.feng.wf",
						"c.x.platform.admin.feng.wf", "c.x.platform.admin.feng.wf", "cx", "wf_def_process_t");
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.feng.wf",
						"c.x.platform.admin.feng.wf", "c.x.platform.admin.feng.wf", "cx", "wf_ins_process_t");
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.feng.wf",
						"c.x.platform.admin.feng.wf", "c.x.platform.admin.feng.wf", "cx", "wf_def_transition_t");
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.feng.wf",
						"c.x.platform.admin.feng.wf", "c.x.platform.admin.feng.wf", "cx", "wf_def_node_t");
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.feng.wf",
						"c.x.platform.admin.feng.wf", "c.x.platform.admin.feng.wf", "cx", "wf_ins_node_t");
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.feng.wf",
						"c.x.platform.admin.feng.wf", "c.x.platform.admin.feng.wf", "cx", "wf_ins_task_t");
			}
			if (false) {
				gen.gen_singleSimple(final_folderPath, "config.c.mvc.c.x.platform.root.compo.token",
						"pages.c.x.platform.root.compo.token", "c.x.platform.root.compo.token", "token",
						"sys_request_token");
			}
			if (false) {
				// 生成代码_商品_简单
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.feng.pss",
						"pages.c.x.platform.admin.feng.pss", "c.x.platform.admin.feng.pss", "gen1", "pss_goods_t");
			}
			if (false) {
				gen.gen_singleSimple(final_folderPath, "config.co.fengyiwen.business.baidu",
						"pages.co.fengyiwen.business.baidu", "co.fengyiwen.business.baidu", "sys",
						"baidu_place_edu_place");
			}
			if (false) {
				gen.gen_singleSimple(final_folderPath, "config.co.fengyiwen.business.baidu",
						"pages.co.fengyiwen.business.baidu", "co.fengyiwen.business.baidu", "sys", "baidu_querykey_t");
				gen.gen_singleSimple(final_folderPath, "config.co.fengyiwen.business.baidu",
						"pages.co.fengyiwen.business.baidu", "co.fengyiwen.business.baidu", "sys", "baidu_place_t");
				gen.gen_singleSimple(final_folderPath, "config.co.fengyiwen.business.weixin",
						"pages.co.fengyiwen.business.weixin_public", "co.fengyiwen.business.weixin_public", "sys",
						"weixin_public_config_t");
				gen.gen_singleSimple(final_folderPath, "config.co.fengyiwen.business.weixin",
						"pages.co.fengyiwen.business.weixin_public", "co.fengyiwen.business.weixin_public", "sys",
						"weixin_public_user_t");
			}
			if (false) {
				//
				// 驾校 场地 老师 teacher 学员 student
				//
				//
				// 班级 class
				//
				// school
				// place
				// edu_place_teacher
				// edu_school_place
				// edu_school_class
				// edu_class_teacher
				// edu_class_student
				// edu_class_place
				//
				// Latitude 地理位置纬度
				// Longitude 地理位置经度
				// Precision 地理位置精度
				// location
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.feng.edu",
						"c.x.platform.admin.feng.edu", "c.x.platform.admin.feng.edu", "cx", "edu_search_t");
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.feng.edu",
						"c.x.platform.admin.feng.edu", "c.x.platform.admin.feng.edu", "cx", "edu_place_teacher");
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.feng.edu",
						"c.x.platform.admin.feng.edu", "c.x.platform.admin.feng.edu", "cx", "edu_school_place");
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.feng.edu",
						"c.x.platform.admin.feng.edu", "c.x.platform.admin.feng.edu", "cx", "edu_school_class");
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.feng.edu",
						"c.x.platform.admin.feng.edu", "c.x.platform.admin.feng.edu", "cx", "edu_class_teacher");
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.feng.edu",
						"c.x.platform.admin.feng.edu", "c.x.platform.admin.feng.edu", "cx", "edu_class_student");
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.feng.edu",
						"c.x.platform.admin.feng.edu", "c.x.platform.admin.feng.edu", "cx", "edu_class_place");
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.feng.edu",
						"c.x.platform.admin.feng.edu", "c.x.platform.admin.feng.edu", "cx", "edu_class_t");
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.feng.edu",
						"c.x.platform.admin.feng.edu", "c.x.platform.admin.feng.edu", "cx", "edu_place_t");
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.feng.edu",
						"c.x.platform.admin.feng.edu", "c.x.platform.admin.feng.edu", "cx", "edu_teacher_t");
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.feng.edu",
						"c.x.platform.admin.feng.edu", "c.x.platform.admin.feng.edu", "cx", "edu_student_t");
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.feng.edu",
						"c.x.platform.admin.feng.edu", "c.x.platform.admin.feng.edu", "cx", "edu_school_t");
			}
			if (false) {
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.feng.fas",
						"pages.c.x.platform.admin.feng.fas", "c.x.platform.admin.feng.fas", "business", "fas_bill_t");
			}
			if (false) {
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.feng.fas",
						"pages.c.x.platform.admin.feng.fas", "c.x.platform.admin.feng.fas", "sys", "fas_business_t");
			}
			if (false) {
				gen.gen_singleSimple(final_folderPath, "config.mvc.c.x.platform.admin.feng.fas",
						"pages.c.x.platform.admin.feng.fas", "c.x.platform.admin.feng.fas", "sys", "fas_subject_t");
			}
			if (false) {
				// test_type
				gen.gen_singleSimple(final_folderPath, "config_a", "pages_b", "co_c", "gen", "test_type");
			}
			if (false) {
				// test_type
				gen.gen_singleSimple(final_folderPath, "config.a", "pages.b", "co.c", "gen", "test_type");
			}
			if (false) {
				// test_type
				gen.gen_singleSimple(final_folderPath, "", "c.x.platform.admin.gen", "c.x.platform.admin.gen", "gen",
						"test_type");
			}
			if (false) {
				// test_type
				gen.gen_singleSimple(final_folderPath, "", "com.cxy.simple.fun", "cxy.simple.fun", "gen", "test_type");
			}
			if (false) {
				// jbpm4.4 流程部署
				gen.gen_singleSimple(final_folderPath, "", "com.fjy.jbpm4_4", "fjy.jbpm4_4", "deploy", "jbpm4_lob");
			}
			if (false) {
				// 测试上传
				gen.gen_singleSimple(final_folderPath, "", "com.cxy.simple.upload", "cxy.simple.upload", "upload",
						"sys_lob_t");
			}
			if (false) {
				gen.gen_singleSimple(final_folderPath, "", "org1.zzsoft2.sysadmin3", "pages5.zzsoft6.sysadmin7", "fun8",
						"sys_menu");
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		log.trace("end");
	}
}
