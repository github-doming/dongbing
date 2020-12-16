package org.doming.core.common.servlet;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 * @Description: 视图
 * @Author: Dongming
 * @Date: 2019-05-21 15:57
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ModelAndView {

	public enum Type {
		/**
		 * 视图
		 */
		VIEW,
		/**
		 * 模型
		 */
		MODEL
	}

	private Type type;
	private String view;
	private Map model;
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	public Map getModel() {
		return model;
	}
	public void setModel(Map model) {
		this.model = model;
	}

	/**
	 * 放入模型
	 *
	 * @param key 模型 key
	 * @param val 模型 val
	 */
	public void putModel(String key, String val) {
		type = Type.VIEW;
		if (model == null) {
			model = new LinkedHashMap<>();
		}
		model.put(key, val);
	}
	public ModelAndView() {
	}
	public ModelAndView(String view) {
		type = Type.VIEW;
		this.view = view;
	}
	public ModelAndView(Map model) {
		type = Type.MODEL;
		this.model = model;
	}
	public ModelAndView(String view, Map model) {
		type = Type.VIEW;
		this.view = view;
		this.model = model;
	}
}
