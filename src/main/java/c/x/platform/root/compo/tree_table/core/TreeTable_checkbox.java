package c.x.platform.root.compo.tree_table.core;

import c.x.platform.root.compo.tree_table.parameter.TreeTableParameter;
import c.x.platform.root.compo.tree_table.config.TreeTableConfig_Img;
import c.x.platform.root.compo.tree_table.bean.TreeTableNodeBaseBean;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.string.StringUtil;

/**
 * 
 * 树与菜单
 * 
 * 
 * 
 */
public class TreeTable_checkbox extends TreeTable_By {
	/**
	 * 
	 * 重写html
	 */
	public void findChilds_fromRootMenu(TreeTableParameter treeTableParameter,
			StringBuilder buffer, TreeTableNodeBaseBean rootNode) {
		if (treeTableParameter.isModel$simple()) {
			treeTableParameter
					.setModel(c.x.platform.root.compo.tree_table.config.TreeTableConfig.model_simple);
			this.model_simple(treeTableParameter, buffer, rootNode);
			return;
		}
		if (treeTableParameter.isModel$tree_table()) {
			treeTableParameter
					.setModel(c.x.platform.root.compo.tree_table.config.TreeTableConfig.model_table);
			this.model_table(treeTableParameter, buffer, rootNode);
			return;
		}
		if (treeTableParameter.isModel$tree_menu()) {
			treeTableParameter
					.setModel(c.x.platform.root.compo.tree_table.config.TreeTableConfig.model_menu);
			this.model_menu(treeTableParameter, buffer, rootNode);
			return;
		}
		if (treeTableParameter.isModel$tree_checkbox()) {
			treeTableParameter
					.setModel(c.x.platform.root.compo.tree_table.config.TreeTableConfig.model_checkbox);
			this.model_checkbox(treeTableParameter, buffer, rootNode);
			return;
		}
	}

	/**
	 * 
	 * model_checkbox;
	 * 
	 * 
	 * 
	 * 返回html;
	 * 
	 * @param treeTableParameter
	 * @param buffer
	 * @param rootNode
	 */
	public void model_checkbox(TreeTableParameter treeTableParameter,
			StringBuilder buffer, TreeTableNodeBaseBean rootNode) {
		// 设置span_nbsp
		String span_nbsp = "<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>";
		// root是否显示(是否添加或追加到html)
		boolean is_append = true;
		if (rootNode.getId().equals(treeTableParameter.getRoot_id())) {
			if (treeTableParameter.isRoot_enable()) {
				is_append = true;
			} else {
				is_append = false;
			}
		} else {
			is_append = true;
		}
		// 线条图片
		String strImgLine = span_nbsp;
		String strImgBottomLine = span_nbsp;
		// 深度
		// {
		int layer = rootNode.getShow_layer();
		// String strLayer = String.valueOf(layer);
		String strLayer = "";
		for (int i = 1; i <= layer; i++) {
			if (i == layer) {
				/**
				 * 是否最后子节点
				 */
				if (rootNode.isLast_leaf()) {
					strLayer = strLayer + strImgBottomLine;
				} else {
					strLayer = strLayer + strImgLine;
				}
			} else {
				strLayer = strLayer + strImgLine;
			}
		}
		// Baselog.trace("深度="+strLayer ,this);
		// }
		// 深度
		// 事件名称
		String event_change_key = "ayTreeTable_change_"
				+ treeTableParameter.getKey();
		String event_file_click_key = "ayTreeTable_file_click_"
				+ treeTableParameter.getKey();
		String event_folder_click_key = "ayTreeTable_folder_click_"
				+ treeTableParameter.getKey();
		// 有根 根节点
		// 有根 非根节点
		// 无根 根节点 不追加
		// 无根 非根节点 显示第二层
		//
		String display = "";
		// 状态
		String state = " state=open ";
		// 系统图片
		String folder = TreeTableConfig_Img.folder_v2;
		// 自定义图片
		String folder_custom = TreeTableConfig_Img.folder_v1;
		if (treeTableParameter.isOpen_all()) {
			// 状态
			state = " state=open ";
			// 系统图片
			folder = TreeTableConfig_Img.folder_v2;
			// 自定义图片
			if (StringUtil.isBlank(rootNode.getPic_open())) {
				folder_custom = TreeTableConfig_Img.folder_v1;
			} else {
				folder_custom = rootNode.getPic_open();
			}
		}
		if (treeTableParameter.isClose_all()) {
			// 系统图片
			folder = TreeTableConfig_Img.folder_closed_v2;
			// 自定义图片
			if (StringUtil.isBlank(rootNode.getPic_close())) {
				folder_custom = TreeTableConfig_Img.folder_closed_v1;
			} else {
				folder_custom = rootNode.getPic_close();
			}
			if (treeTableParameter.isRoot_enable()) {
				// 根节点
				if (rootNode.getId().equals(treeTableParameter.getRoot_id())) {
					state = " state=close ";
				} else {
					state = " state=close ";
					display = " style='display: none;'  ";
				}
			} else {
				// 第二层
				if (rootNode.getLayer() == 2) {
					state = " state=close ";
				} else {
					state = " state=close ";
					display = " style='display: none;'  ";
				}
			}
		}
		// html与id
		String id_tr = "id_tr_" + rootNode.getId() + "_"
				+ treeTableParameter.getKey();
		String parent_id_tr = "id_tr_" + rootNode.getParent_id() + "_"
				+ treeTableParameter.getKey();
		// 系统图片id
		String id_tr_img = "id_tr_img_" + rootNode.getId() + "_"
				+ treeTableParameter.getKey();
		// 自定义图片id
		String id_tr_img_custom = "id_tr_img_custom_" + rootNode.getId() + "_"
				+ treeTableParameter.getKey();
		// 自定义图片的地址
		String url_img_custom_open = TreeTableConfig_Img.folder_v1;
		String url_img_custom_close = TreeTableConfig_Img.folder_closed_v1;
		if (StringUtil.isBlank(rootNode.getPic_open())) {
			url_img_custom_open = TreeTableConfig_Img.folder_v1;
		} else {
			url_img_custom_open = rootNode.getPic_open();
		}
		if (StringUtil.isBlank(rootNode.getPic_close())) {
			url_img_custom_close = TreeTableConfig_Img.folder_closed_v1;
		} else {
			url_img_custom_close = rootNode.getPic_close();
		}
		// 事件名称与html
		// change事件
		String html_event_change_key = "  onclick=\"" + event_change_key
				+ "(  '" + rootNode.getId() + "','"
				+ treeTableParameter.getModel() + "',        '"
				+ treeTableParameter.getContext_path() + "','" + id_tr
				+ "'   ,'" + id_tr_img + "'   ,'" + id_tr_img_custom
				+ "'     ,'" + url_img_custom_open + "'  ,'"
				+ url_img_custom_close + "' );\"  ";
		String html_event_file_click_key = " onclick=\"" + event_file_click_key
				+ "('" + rootNode.getId() + "','" + id_tr + "','"
				+ rootNode.getUrl() + "');\" ";
		String html_event_folder_click_key = " onclick=\""
				+ event_folder_click_key + "('" + rootNode.getId() + "','"
				+ id_tr + "','" + rootNode.getUrl() + "');\" ";
		if (rootNode.isLeaf()) {
			// 是否添加或追加
			if (is_append) {
				// 如果是叶子结点，比如文件
				// 叶子结点添加隐藏事件
				buffer.append("<tr leaf=true    " + display + "   " + state
						+ " path=" + rootNode.getPath() + " id=" + id_tr
						+ " parent_id=" + parent_id_tr + ">");
				buffer.append("<td>");
				// 添加深度，线条图片
				buffer.append(strLayer);
				// 添加系统的图片
				buffer.append(span_nbsp);
				// 添加自定义的图片
				if (StringUtil.isBlank(rootNode.getPic())) {
					buffer.append("<img  id=" + id_tr_img_custom + " src="
							+ treeTableParameter.getContext_path()
							+ TreeTableConfig_Img.file_v1 + "   >");
					buffer.append("</img>");
				} else {
					buffer.append("<img   id=" + id_tr_img_custom + " src="
							+ treeTableParameter.getContext_path()
							+ rootNode.getPic() + "   >");
					buffer.append("</img>");
				}
				// 添加复选框checkbox
				// buffer.append("<input type=checkbox value=''></input>");
				buffer.append("<input alt='" + rootNode.getPath()
						+ "' onclick=ayTreeTable_checkbox_all(this,'"
						+ treeTableParameter.getKey()
						+ "');  name='name_checkbox_"
						+ treeTableParameter.getKey() + "'  value='"
						+ rootNode.getId() + "' type='checkbox' ></input>");
				// 添加名称
				buffer.append("<button class=\"btn btn-link\" "
						+ html_event_file_click_key + " >");
				buffer.append(rootNode.getName());
				buffer.append("</button>");
				buffer.append("</td>");
				// 添加其它的td
				if (treeTableParameter.isModel$tree_checkbox()) {
					String html = treeTableParameter.getTds()
							.get(rootNode.getId()).toString();
					if (html != null) {
						buffer.append(treeTableParameter.getTds().get(
								rootNode.getId()));
					}
				}
				buffer.append("</tr>");
			}
		} else {
			if (is_append) {
				// 如果是非叶子结点，比如文件夹
				// 非叶子结点也要添加隐藏事件
				buffer.append("<tr leaf=false   " + display + "   " + state
						+ " path=" + rootNode.getPath() + "  id=" + id_tr
						+ " parent_id=" + parent_id_tr + ">");
				buffer.append("<td>");
				// 添加深度，线条图片
				buffer.append(strLayer);
				// 添加系统的图片
				buffer.append("<img id=" + id_tr_img + "  src="
						+ treeTableParameter.getContext_path() + folder
						+ html_event_change_key + " >");
				// buffer.append(rootNode.getName());
				buffer.append("</img>");
				// 添加自定义的图片
				buffer.append("<img   id=" + id_tr_img_custom + " src="
						+ treeTableParameter.getContext_path() + folder_custom
						+ "  " + html_event_change_key + "   >");
				buffer.append("</img>");
				// 添加复选框checkbox
				// buffer.append("<input type=checkbox value=''></input>");
				buffer.append("<input alt='" + rootNode.getPath()
						+ "' onclick=ayTreeTable_checkbox_all(this,'"
						+ treeTableParameter.getKey()
						+ "');  name='name_checkbox_"
						+ treeTableParameter.getKey() + "'  value='"
						+ rootNode.getId() + "' type='checkbox' ></input>");
				// 添加名称
				buffer.append("<button class=\"btn btn-link\" "
						+ html_event_folder_click_key + " >");
				buffer.append(rootNode.getName());
				buffer.append("</button>");
				buffer.append("</td>");
				// 添加其它的td
				if (treeTableParameter.isModel$tree_checkbox()) {
					String html = treeTableParameter.getTds()
							.get(rootNode.getId()).toString();
					if (html != null) {
						buffer.append(treeTableParameter.getTds().get(
								rootNode.getId()));
					}
				}
				buffer.append("</tr>");
			}
			for (TreeTableNodeBaseBean dbMenu : rootNode.getChilds()) {
				this.findChilds_fromRootMenu(treeTableParameter, buffer, dbMenu);
			}
		}
	}

	/**
	 * 
	 * model_menu;
	 * 
	 * 
	 * 
	 * 返回html;
	 * 
	 * @param treeTableParameter
	 * @param buffer
	 * @param rootNode
	 */
	public void model_menu(TreeTableParameter treeTableParameter,
			StringBuilder buffer, TreeTableNodeBaseBean rootNode) {
		// 设置span_nbsp
		String span_nbsp = "<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>";
		// root是否显示(是否添加或追加到html)
		boolean is_append = true;
		if (rootNode.getId().equals(treeTableParameter.getRoot_id())) {
			if (treeTableParameter.isRoot_enable()) {
				is_append = true;
			} else {
				is_append = false;
			}
		} else {
			is_append = true;
		}
		// 线条图片
		String strImgLine = span_nbsp;
		String strImgBottomLine = span_nbsp;
		// 深度
		// {
		int layer = rootNode.getShow_layer();
		// String strLayer = String.valueOf(layer);
		String strLayer = "";
		for (int i = 1; i <= layer; i++) {
			if (i == layer) {
				/**
				 * 是否最后子节点
				 */
				if (rootNode.isLast_leaf()) {
					strLayer = strLayer + strImgBottomLine;
				} else {
					strLayer = strLayer + strImgLine;
				}
			} else {
				strLayer = strLayer + strImgLine;
			}
		}
		// Baselog.trace("深度="+strLayer ,this);
		// }
		// 深度
		// html与id
		String id_tr = "id_tr_" + rootNode.getId() + "_"
				+ treeTableParameter.getKey();
		String parent_id_tr = "id_tr_" + rootNode.getParent_id() + "_"
				+ treeTableParameter.getKey();
		// 节点名称id
		String id_name = "id_name_" + rootNode.getId() + "_"
				+ treeTableParameter.getKey();
		// 系统图片id
		String id_tr_img = "id_tr_img_" + rootNode.getId() + "_"
				+ treeTableParameter.getKey();
		// 自定义图片id
		String id_tr_img_custom = "id_tr_img_custom_" + rootNode.getId() + "_"
				+ treeTableParameter.getKey();
		// 自定义图片的地址
		String url_img_custom_open = TreeTableConfig_Img.folder_v1;
		String url_img_custom_close = TreeTableConfig_Img.folder_closed_v1;
		if (StringUtil.isBlank(rootNode.getPic_open())) {
			url_img_custom_open = TreeTableConfig_Img.folder_v1;
		} else {
			url_img_custom_open = rootNode.getPic_open();
		}
		if (StringUtil.isBlank(rootNode.getPic_close())) {
			url_img_custom_close = TreeTableConfig_Img.folder_closed_v1;
		} else {
			url_img_custom_close = rootNode.getPic_close();
		}
		// 事件名称
		String event_change_key = "ayTreeTable_change_"
				+ treeTableParameter.getKey();
		String event_file_click_key = "ayTreeTable_file_click_"
				+ treeTableParameter.getKey();
		String event_folder_click_key = "ayTreeTable_folder_click_"
				+ treeTableParameter.getKey();
		// 有根 根节点
		// 有根 非根节点
		// 无根 根节点 不追加
		// 无根 非根节点 显示第二层
		//
		String display = "";
		// 状态
		String state = " state='open' ";
		// 系统图片
		String folder = " class='icon-chevron-down' ";
		// 自定义图片
		String folder_custom = TreeTableConfig_Img.folder_v1;
		if (treeTableParameter.isOpen_all()) {
			// 状态
			state = " state='open' ";
			// 系统图片
			folder = " class='icon-chevron-down'  ";
			// 自定义图片
			if (StringUtil.isBlank(rootNode.getPic_open())) {
				folder_custom = TreeTableConfig_Img.folder_v1;
			} else {
				folder_custom = rootNode.getPic_open();
			}
		}
		if (treeTableParameter.isClose_all()) {
			// 系统图片
			folder = "class='icon-chevron-right' ";
			// 自定义图片
			if (StringUtil.isBlank(rootNode.getPic_close())) {
				folder_custom = TreeTableConfig_Img.folder_closed_v1;
			} else {
				folder_custom = rootNode.getPic_close();
			}
			if (treeTableParameter.isRoot_enable()) {
				// 根节点
				if (rootNode.getId().equals(treeTableParameter.getRoot_id())) {
					state = " state='close' ";
				} else {
					state = " state='close' ";
					display = " style='display: none;'  ";
				}
			} else {
				// 第二层
				if (rootNode.getLayer() == 2) {
					state = " state='close' ";
				} else {
					state = " state='close' ";
					display = " style='display: none;'  ";
				}
			}
		}
		// 事件名称与html
		// change事件
		String html_event_change_key = "  onclick=\"" + event_change_key
				+ "(  '" + rootNode.getId() + "','"
				+ treeTableParameter.getModel() + "',        '"
				+ treeTableParameter.getContext_path() + "','" + id_tr
				+ "'   ,'" + id_tr_img + "'   ,'" + id_tr_img_custom
				+ "'     ,'" + url_img_custom_open + "'  ,'"
				+ url_img_custom_close + "');\"  ";
		/**
		 * 文件事件
		 */

		String html_event_file_click_key = " onclick=\"" + event_file_click_key
				+ "(          '" + rootNode.getId() + "','" + id_tr + "','"
				+ id_name + "','" + rootNode.getUrl() + "');\" ";

		// 文件夹事件
		String html_event_folder_click_key = " onclick=\""
				+ event_folder_click_key + "('" + rootNode.getId() + "','"
				+ id_tr + "','" + id_name + "','" + rootNode.getUrl()
				+ "');\" ";
		if (rootNode.isLeaf()) {
			// 是否添加或追加
			if (is_append) {
				// 如果是叶子结点，比如文件
				// 叶子结点添加隐藏事件
				buffer.append("<tr leaf='true'    " + display + "   " + state
						+ " path='" + rootNode.getPath() + "' id='" + id_tr
						+ "' parent_id='" + parent_id_tr + "' >");
				buffer.append("<td>");
				// 添加深度，线条图片
				buffer.append(strLayer);
				// 添加系统的图片
				buffer.append(span_nbsp);
				// 添加自定义的图片
				if (StringUtil.isBlank(rootNode.getPic())) {
					buffer.append("<img  id='" + id_tr_img_custom + "' src='"
							+ treeTableParameter.getContext_path()
							+ TreeTableConfig_Img.file_v1 + "'   >");
					buffer.append("</img>");
				} else {
					buffer.append("<img   id='" + id_tr_img_custom + "' src='"
							+ treeTableParameter.getContext_path()
							+ rootNode.getPic() + "'   >");
					buffer.append("</img>");
				}
				// 添加名称
				buffer.append("<span class=\"text-info\" "
						+ html_event_file_click_key + ">");
				buffer.append("<span class=\"\" id=" + id_name + "  >");
				buffer.append(rootNode.getName());
				buffer.append("</span>");
				buffer.append("</span>");
				buffer.append("</td>");
				buffer.append("</tr>");
			}
		} else {
			if (is_append) {
				// 如果是非叶子结点，比如文件夹
				// 非叶子结点也要添加隐藏事件
				buffer.append("<tr leaf='false'   " + display + "   " + state
						+ " path='" + rootNode.getPath() + "'  id='" + id_tr
						+ "' parent_id='" + parent_id_tr + "'>");
				buffer.append("<td>");
				// 添加深度，线条图片
				buffer.append(strLayer);
				// 添加系统的图片
				buffer.append("<i ");
				buffer.append("   id='i_" + id_tr + "'   ");
				buffer.append(html_event_change_key);
				buffer.append(folder);
				buffer.append(" ></i>");
				// 添加自定义的图片
				buffer.append("<img   id='" + id_tr_img_custom + "' src="
						+ treeTableParameter.getContext_path() + folder_custom
						+ "  " + html_event_change_key + "   >");
				buffer.append("</img>");
				// 添加名称
				buffer.append("<span " + html_event_folder_click_key + " >");
				buffer.append("<span  class=\"\" id=" + id_name + "  >");
				buffer.append(rootNode.getName());
				buffer.append("</span>");
				buffer.append("</span>");
				buffer.append("</td>");
				buffer.append("</tr>");
			}
			for (TreeTableNodeBaseBean dbMenu : rootNode.getChilds()) {
				this.findChilds_fromRootMenu(treeTableParameter, buffer, dbMenu);
			}
		}
	}

	/**
	 * 
	 * model_table;
	 * 
	 * 
	 * 
	 * 返回html;
	 * 
	 * @param treeTableParameter
	 * @param buffer
	 * @param rootNode
	 */
	public void model_table(TreeTableParameter treeTableParameter,
			StringBuilder buffer, TreeTableNodeBaseBean rootNode) {
		// 设置span_nbsp
		String span_nbsp = "<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>";
		// root是否显示(是否添加或追加到html)
		boolean is_append = true;
		if (rootNode.getId().equals(treeTableParameter.getRoot_id())) {
			if (treeTableParameter.isRoot_enable()) {
				is_append = true;
			} else {
				is_append = false;
			}
		} else {
			is_append = true;
		}
		// 线条图片
		String strImgLine = span_nbsp;
		String strImgBottomLine = span_nbsp;
		// 深度
		// {
		int layer = rootNode.getShow_layer();
		// String strLayer = String.valueOf(layer);
		String strLayer = "";
		for (int i = 1; i <= layer; i++) {
			if (i == layer) {
				/**
				 * 是否最后子节点
				 */
				if (rootNode.isLast_leaf()) {
					strLayer = strLayer + strImgBottomLine;
				} else {
					strLayer = strLayer + strImgLine;
				}
			} else {
				strLayer = strLayer + strImgLine;
			}
		}
		// Baselog.trace("深度="+strLayer ,this);
		// }
		// 深度
		// 事件名称
		String event_change_key = "ayTreeTable_change_"
				+ treeTableParameter.getKey();
		String event_file_click_key = "ayTreeTable_file_click_"
				+ treeTableParameter.getKey();
		String event_folder_click_key = "ayTreeTable_folder_click_"
				+ treeTableParameter.getKey();
		// 有根 根节点
		// 有根 非根节点
		// 无根 根节点 不追加
		// 无根 非根节点 显示第二层
		//
		String display = "";
		// 状态
		String state = " state=open ";
		// 系统图片
		String folder = TreeTableConfig_Img.folder_v2;
		// 自定义图片
		String folder_custom = TreeTableConfig_Img.folder_v1;
		if (treeTableParameter.isOpen_all()) {
			// 状态
			state = " state=open ";
			// 系统图片
			folder = TreeTableConfig_Img.folder_v2;
			// 自定义图片
			if (StringUtil.isBlank(rootNode.getPic_open())) {
				folder_custom = TreeTableConfig_Img.folder_v1;
			} else {
				folder_custom = rootNode.getPic_open();
			}
		}
		if (treeTableParameter.isClose_all()) {
			// 系统图片
			folder = TreeTableConfig_Img.folder_closed_v2;
			// 自定义图片
			if (StringUtil.isBlank(rootNode.getPic_close())) {
				folder_custom = TreeTableConfig_Img.folder_closed_v1;
			} else {
				folder_custom = rootNode.getPic_close();
			}
			if (treeTableParameter.isRoot_enable()) {
				// 根节点
				if (rootNode.getId().equals(treeTableParameter.getRoot_id())) {
					state = " state=close ";
				} else {
					state = " state=close ";
					display = " style='display: none;'  ";
				}
			} else {
				// 第二层
				if (rootNode.getLayer() == 2) {
					state = " state=close ";
				} else {
					state = " state=close ";
					display = " style='display: none;'  ";
				}
			}
		}
		// html与id
		String id_tr = "id_tr_" + rootNode.getId() + "_"
				+ treeTableParameter.getKey();
		String parent_id_tr = "id_tr_" + rootNode.getParent_id() + "_"
				+ treeTableParameter.getKey();
		// 系统图片id
		String id_tr_img = "id_tr_img_" + rootNode.getId() + "_"
				+ treeTableParameter.getKey();
		// 自定义图片id
		String id_tr_img_custom = "id_tr_img_custom_" + rootNode.getId() + "_"
				+ treeTableParameter.getKey();
		// 自定义图片的地址
		String url_img_custom_open = TreeTableConfig_Img.folder_v1;
		String url_img_custom_close = TreeTableConfig_Img.folder_closed_v1;
		if (StringUtil.isBlank(rootNode.getPic_open())) {
			url_img_custom_open = TreeTableConfig_Img.folder_v1;
		} else {
			url_img_custom_open = rootNode.getPic_open();
		}
		if (StringUtil.isBlank(rootNode.getPic_close())) {
			url_img_custom_close = TreeTableConfig_Img.folder_closed_v1;
		} else {
			url_img_custom_close = rootNode.getPic_close();
		}
		// 事件名称与html
		// change事件
		String html_event_change_key = "  onclick=\"" + event_change_key
				+ "(  '" + rootNode.getId() + "','"
				+ treeTableParameter.getModel() + "',        '"
				+ treeTableParameter.getContext_path() + "','" + id_tr
				+ "'   ,'" + id_tr_img + "'   ,'" + id_tr_img_custom
				+ "'     ,'" + url_img_custom_open + "'  ,'"
				+ url_img_custom_close + "' );\"  ";
		String html_event_file_click_key = " onclick=\"" + event_file_click_key
				+ "('" + rootNode.getId() + "','" + id_tr + "','"
				+ rootNode.getUrl() + "');\" ";
		String html_event_folder_click_key = " onclick=\""
				+ event_folder_click_key + "('" + rootNode.getId() + "','"
				+ id_tr + "','" + rootNode.getUrl() + "');\" ";
		if (rootNode.isLeaf()) {
			// 是否添加或追加
			if (is_append) {
				// 如果是叶子结点，比如文件
				// 叶子结点添加隐藏事件
				buffer.append("<tr leaf=true    " + display + "   " + state
						+ " path=" + rootNode.getPath() + " id=" + id_tr
						+ " parent_id=" + parent_id_tr + ">");
				buffer.append("<td>");
				// 添加深度，线条图片
				buffer.append(strLayer);
				// 添加系统的图片
				buffer.append(span_nbsp);
				// 添加自定义的图片
				if (StringUtil.isBlank(rootNode.getPic())) {
					buffer.append("<img  id=" + id_tr_img_custom + " src="
							+ treeTableParameter.getContext_path()
							+ TreeTableConfig_Img.file_v1 + "   >");
					buffer.append("</img>");
				} else {
					buffer.append("<img   id=" + id_tr_img_custom + " src="
							+ treeTableParameter.getContext_path()
							+ rootNode.getPic() + "   >");
					buffer.append("</img>");
				}
				// 添加名称
				buffer.append("<button class=\"btn btn-link\" "
						+ html_event_file_click_key + " >");
				buffer.append(rootNode.getName());
				buffer.append("</button>");
				buffer.append("</td>");
				// 添加其它的td
				if (treeTableParameter.isModel$tree_table()) {
					String html = BeanThreadLocal.findThreadLocal().get().find( treeTableParameter.getTds()
							.get(rootNode.getId()), "");
					if (StringUtil.isNotBlank(html)) {
						buffer.append(html);
					}
				}
				buffer.append("</tr>");
			}
		} else {
			if (is_append) {
				// 如果是非叶子结点，比如文件夹
				// 非叶子结点也要添加隐藏事件
				buffer.append("<tr leaf=false   " + display + "   " + state
						+ " path=" + rootNode.getPath() + "  id=" + id_tr
						+ " parent_id=" + parent_id_tr + ">");
				buffer.append("<td>");
				// 添加深度，线条图片
				buffer.append(strLayer);
				// 添加系统的图片
				buffer.append("<img id=" + id_tr_img + "  src="
						+ treeTableParameter.getContext_path() + folder
						+ html_event_change_key + " >");
				// buffer.append(rootNode.getName());
				buffer.append("</img>");
				// 添加自定义的图片
				buffer.append("<img   id=" + id_tr_img_custom + " src="
						+ treeTableParameter.getContext_path() + folder_custom
						+ "  " + html_event_change_key + "   >");
				buffer.append("</img>");
				// 添加名称
				buffer.append("<button class=\"btn btn-link\" "
						+ html_event_folder_click_key + " >");
				buffer.append(rootNode.getName());
				buffer.append("</button>");
				buffer.append("</td>");
				// 添加其它的td
				if (treeTableParameter.isModel$tree_table()) {
					String html = BeanThreadLocal.findThreadLocal().get().find(treeTableParameter.getTds()
							.get(rootNode.getId()), "");
					if (StringUtil.isNotBlank(html)) {
						buffer.append(html);
					}
				}
				buffer.append("</tr>");
			}
			for (TreeTableNodeBaseBean dbMenu : rootNode.getChilds()) {
				this.findChilds_fromRootMenu(treeTableParameter, buffer, dbMenu);
			}
		}
	}

	/**
	 * 
	 * model_simple;
	 * 
	 * 
	 * 
	 * 返回html;
	 * 
	 * @param treeTableParameter
	 * @param buffer
	 * @param rootNode
	 */
	public void model_simple(TreeTableParameter treeTableParameter,
			StringBuilder buffer, TreeTableNodeBaseBean rootNode) {
		// root是否显示(是否添加或追加到html)
		boolean is_append = true;
		if (rootNode.getId().equals(treeTableParameter.getRoot_id())) {
			if (treeTableParameter.isRoot_enable()) {
				is_append = true;
			} else {
				is_append = false;
			}
		} else {
			is_append = true;
		}
		// 线条图片
		String strImgLine = "<img src=\"   "
				+ treeTableParameter.getContext_path()
				+ TreeTableConfig_Img.line + "\"></img>";
		String strImgBottomLine = "<img src=\""
				+ treeTableParameter.getContext_path()
				+ TreeTableConfig_Img.line_joinbottom + "\"></img>";
		// 深度
		// {
		int layer = rootNode.getShow_layer();
		// String strLayer = String.valueOf(layer);
		String strLayer = "";
		for (int i = 1; i <= layer; i++) {
			if (i == layer) {
				/**
				 * 是否最后子节点
				 */
				if (rootNode.isLast_leaf()) {
					strLayer = strLayer + strImgBottomLine;
				} else {
					strLayer = strLayer + strImgLine;
				}
			} else {
				strLayer = strLayer + strImgLine;
			}
		}
		// Baselog.trace("深度="+strLayer ,this);
		// }
		// 深度
		// 事件名称
		String event_change_key = "ayTreeTable_change_"
				+ treeTableParameter.getKey();
		String event_file_click_key = "ayTreeTable_file_click_"
				+ treeTableParameter.getKey();
		String event_folder_click_key = "ayTreeTable_folder_click_"
				+ treeTableParameter.getKey();
		// 有根 根节点
		// 有根 非根节点
		// 无根 根节点 不追加
		// 无根 非根节点 显示第二层
		//
		String display = "";
		// 状态
		String state = " state=open ";
		// 系统图片
		String folder = TreeTableConfig_Img.folder_v2;
		// 自定义图片
		String folder_custom = TreeTableConfig_Img.folder_v1;
		if (treeTableParameter.isOpen_all()) {
			// 状态
			state = " state=open ";
			// 系统图片
			folder = TreeTableConfig_Img.folder_v2;
			// 自定义图片
			if (StringUtil.isBlank(rootNode.getPic_open())) {
				folder_custom = TreeTableConfig_Img.folder_v1;
			} else {
				folder_custom = rootNode.getPic_open();
			}
		}
		if (treeTableParameter.isClose_all()) {
			// 系统图片
			folder = TreeTableConfig_Img.folder_closed_v2;
			// 自定义图片
			if (StringUtil.isBlank(rootNode.getPic_close())) {
				folder_custom = TreeTableConfig_Img.folder_closed_v1;
			} else {
				folder_custom = rootNode.getPic_close();
			}
			if (treeTableParameter.isRoot_enable()) {
				// 根节点
				if (rootNode.getId().equals(treeTableParameter.getRoot_id())) {
					state = " state=close ";
				} else {
					state = " state=close ";
					display = " style='display: none;'  ";
				}
			} else {
				// 第二层
				if (rootNode.getLayer() == 2) {
					state = " state=close ";
				} else {
					state = " state=close ";
					display = " style='display: none;'  ";
				}
			}
		}
		// html与id
		String id_tr = "id_tr_" + rootNode.getId() + "_"
				+ treeTableParameter.getKey();
		String parent_id_tr = "id_tr_" + rootNode.getParent_id() + "_"
				+ treeTableParameter.getKey();
		// 系统图片id
		String id_tr_img = "id_tr_img_" + rootNode.getId() + "_"
				+ treeTableParameter.getKey();
		// 自定义图片id
		String id_tr_img_custom = "id_tr_img_custom_" + rootNode.getId() + "_"
				+ treeTableParameter.getKey();
		// 自定义图片的地址
		String url_img_custom_open = TreeTableConfig_Img.folder_v1;
		String url_img_custom_close = TreeTableConfig_Img.folder_closed_v1;
		if (StringUtil.isBlank(rootNode.getPic_open())) {
			url_img_custom_open = TreeTableConfig_Img.folder_v1;
		} else {
			url_img_custom_open = rootNode.getPic_open();
		}
		if (StringUtil.isBlank(rootNode.getPic_close())) {
			url_img_custom_close = TreeTableConfig_Img.folder_closed_v1;
		} else {
			url_img_custom_close = rootNode.getPic_close();
		}
		// 事件名称与html
		// change事件
		String html_event_change_key = "  onclick=\"" + event_change_key
				+ "(  '" + rootNode.getId() + "','"
				+ treeTableParameter.getModel() + "',        '"
				+ treeTableParameter.getContext_path() + "','" + id_tr
				+ "'   ,'" + id_tr_img + "'   ,'" + id_tr_img_custom
				+ "'     ,'" + url_img_custom_open + "'  ,'"
				+ url_img_custom_close + "' );\"  ";
		String html_event_file_click_key = " onclick=\"" + event_file_click_key
				+ "('" + rootNode.getId() + "','" + id_tr + "','"
				+ rootNode.getUrl() + "');\" ";
		String html_event_folder_click_key = " onclick=\""
				+ event_folder_click_key + "('" + rootNode.getId() + "','"
				+ id_tr + "','" + rootNode.getUrl() + "');\" ";
		if (rootNode.isLeaf()) {
			// 是否添加或追加
			if (is_append) {
				// 如果是叶子结点，比如文件
				// 叶子结点添加隐藏事件
				buffer.append("<tr leaf=true    " + display + "   " + state
						+ " path=" + rootNode.getPath() + " id=" + id_tr
						+ " parent_id=" + parent_id_tr + ">");
				buffer.append("<td>");
				// 添加深度，线条图片
				buffer.append(strLayer);
				// 添加系统的图片
				buffer.append("<img id=" + id_tr_img + " src="
						+ treeTableParameter.getContext_path()
						+ TreeTableConfig_Img.file_v2 + "   "
						+ html_event_change_key + " >");
				// buffer.append(rootNode.getName());
				buffer.append("</img>");
				// 添加自定义的图片
				if (StringUtil.isBlank(rootNode.getPic())) {
					buffer.append("<img  id=" + id_tr_img_custom + " src="
							+ treeTableParameter.getContext_path()
							+ TreeTableConfig_Img.file_v1 + "   >");
					buffer.append("</img>");
				} else {
					buffer.append("<img   id=" + id_tr_img_custom + " src="
							+ treeTableParameter.getContext_path()
							+ rootNode.getPic() + "   >");
					buffer.append("</img>");
				}
				// 添加名称
				buffer.append("<span " + html_event_file_click_key + ">");
				buffer.append(rootNode.getName());
				buffer.append("</span>");
				buffer.append("</td>");
				buffer.append("</tr>");
			}
		} else {
			if (is_append) {
				// 如果是非叶子结点，比如文件夹
				// 非叶子结点也要添加隐藏事件
				buffer.append("<tr leaf=false   " + display + "   " + state
						+ " path=" + rootNode.getPath() + "  id=" + id_tr
						+ " parent_id=" + parent_id_tr + ">");
				buffer.append("<td>");
				// 添加深度，线条图片
				buffer.append(strLayer);
				// 添加系统的图片
				buffer.append("<img id=" + id_tr_img + "  src="
						+ treeTableParameter.getContext_path() + folder
						+ html_event_change_key + " >");
				// buffer.append(rootNode.getName());
				buffer.append("</img>");
				// 添加自定义的图片
				buffer.append("<img   id=" + id_tr_img_custom + " src="
						+ treeTableParameter.getContext_path() + folder_custom
						+ "  " + html_event_change_key + "   >");
				buffer.append("</img>");
				// 添加名称
				buffer.append("<span " + html_event_folder_click_key + " >");
				buffer.append(rootNode.getName());
				buffer.append("</span>");
				buffer.append("</td>");
				buffer.append("</tr>");
			}
			for (TreeTableNodeBaseBean dbMenu : rootNode.getChilds()) {
				this.findChilds_fromRootMenu(treeTableParameter, buffer, dbMenu);
			}
		}
	}
}
