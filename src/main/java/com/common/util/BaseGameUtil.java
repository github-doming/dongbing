package com.common.util;


import com.common.game.factory.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 游戏工具类
 *
 * @Author: Dongming
 * @Date: 2020-05-09 16:17
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class BaseGameUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	//region 初始化

	private static volatile BaseGameUtil instance = null;

	public BaseGameUtil() {
	}

	public static BaseGameUtil findInstance() {
		if (instance == null) {
			synchronized (BaseGameUtil.class) {
				if (instance == null) {
					BaseGameUtil gameUtil = new BaseGameUtil();
					gameUtil.init();
					instance = gameUtil;
				}
			}
		}
		return instance;
	}

	private void init() {
	}
	//endregion

	//region 编码

	public enum Code {
		/**
		 * 游戏
		 */
		//号码类游戏
		PK10("北京赛车") {

			@Override
			public Pk10Factory getGameFactory() {
				return Pk10Factory.build();
			}
		}, XYFT("幸运飞艇") {

			@Override
			public XyftFactory getGameFactory() {
				return XyftFactory.build();
			}
		}, JS10("极速赛车") {

			@Override
			public Js10Factory getGameFactory() {
				return Js10Factory.build();
			}
		}, SELF_10_2("自有2分彩赛车") {

			@Override
			public Self102Factory getGameFactory() {
				return Self102Factory.build();
			}
		}, SELF_10_5("自有5分彩赛车") {
			@Override
			public Selt105Factory getGameFactory() {
				return Selt105Factory.build();
			}
		}, COUNTRY_10("国家赛车") {

			@Override
			public Country10Factory getGameFactory() {
				return Country10Factory.build();
			}

			//球号类游戏
		}, CQSSC("重庆时时彩") {

			@Override
			public CqsscFactory getGameFactory() {
				return CqsscFactory.build();
			}
		}, JSSSC("极速时时彩") {

			@Override
			public JssscFactory getGameFactory() {
				return JssscFactory.build();
			}
		}, SELF_SSC_5("自有5分彩时时彩") {

			@Override
			public SelfSsc5Factory getGameFactory() {
				return SelfSsc5Factory.build();
			}
		}, COUNTRY_SSC("国家时时彩") {

			@Override
			public CountrySscFactory getGameFactory() {
				return CountrySscFactory.build();
			}

			//快乐类游戏
		}, XYNC("幸运农场") {

			@Override
			public XyncFactory getGameFactory() {
				return XyncFactory.build();
			}
		}, GDKLC("广东快乐十分") {

			@Override
			public GdklcFactory getGameFactory() {
				return GdklcFactory.build();
			}

			//快乐十分类游戏
		}, GXKLSF("广西快乐十分") {

			@Override
			public GxklsfFactory getGameFactory() {
				return GxklsfFactory.build();
			}
		};

		String name;

		Code(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public abstract <T extends GameFactory<?>> T getGameFactory();

	}
	//endregion

	//region 基础方法

	/**
	 * 判断是否存在该游戏
	 *
	 * @param obj 游戏编码
	 * @return 存在返回游戏，不存在返回 NULL
	 */
	public static Code value(Object obj) {
		try {
			return Code.valueOf(obj.toString());
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	//endregion

}
