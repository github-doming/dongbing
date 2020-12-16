package com.common.util;

import com.common.handicap.Handicap;
import com.common.handicap.HandicapFactory;
import com.common.handicap.MemberFactory;
import com.common.handicap.com.ComMemberFactory;
import com.common.handicap.fc.FcMemberFactory;
import com.common.handicap.hq.HqMemberFactory;
import com.common.handicap.idc.IdcMemberFactory;
import com.common.handicap.newcc.NewCcMemberFactory;
import com.common.handicap.newws.NewWsMemberFactory;
import com.common.handicap.sgwin.SgWinMemberFactory;
import com.common.handicap.un.UnMemberFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 * 盘口工具类
 *
 * @Author: Dongming
 * @Date: 2020-05-09 17:09
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class BaseHandicapUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	//region 初始化

	private static volatile BaseHandicapUtil instance = null;

	public BaseHandicapUtil() {
	}

	public static BaseHandicapUtil findInstance() {
		if (instance == null) {
			synchronized (BaseHandicapUtil.class) {
				if (instance == null) {
					BaseHandicapUtil handicapUtil = new BaseHandicapUtil();
					handicapUtil.init();
					instance = handicapUtil;
				}
			}
		}
		return instance;
	}

	/**
	 * 初始化
	 */
	private void init() {
	}
	//endregion

	//region 编码

	public enum Code {
		/**
		 * 盘口
		 */
		IDC("IDC") {
			@Override
			public IdcMemberFactory getMemberFactory() {
				return IdcMemberFactory.getInstance();
			}
		}, SGWIN("138") {
			@Override
			public SgWinMemberFactory getMemberFactory() {
				return SgWinMemberFactory.getInstance();
			}
		}, HQ("环球") {
			@Override
			public HqMemberFactory getMemberFactory() {
				return HqMemberFactory.getInstance();
			}
		}, COM("COM") {
			@Override
			public ComMemberFactory getMemberFactory() {
				return ComMemberFactory.getInstance();
			}
		}, NCOM1("NCOM1") {
			@Override
			public IdcMemberFactory getMemberFactory() {
				return IdcMemberFactory.getInstance();
			}
		}, NCOM2("NCOM2") {
			@Override
			public IdcMemberFactory getMemberFactory() {
				return IdcMemberFactory.getInstance();
			}
		}, BW("BW") {
			@Override
			public IdcMemberFactory getMemberFactory() {
				return IdcMemberFactory.getInstance();
			}
		},NEWCC("新cc"){
			@Override
			public NewCcMemberFactory getMemberFactory() {
				return NewCcMemberFactory.getInstance();
			}
		},UN("UN"){
			@Override
			public UnMemberFactory getMemberFactory() {
				return UnMemberFactory.getInstance();
			}
		},NEWWS("新濠"){
			@Override
			public NewWsMemberFactory getMemberFactory() {
				return NewWsMemberFactory.getInstance();
			}
		},NEWMOA("新MOA"){
			@Override
			public IdcMemberFactory getMemberFactory() {
				return IdcMemberFactory.getInstance();
			}
		},FC("FC"){
			@Override
			public FcMemberFactory getMemberFactory() {
				return FcMemberFactory.getInstance();
			}
		},VR("VR"){
			@Override
			public FcMemberFactory getMemberFactory() {
				return FcMemberFactory.getInstance();
			}
		};
		String name;

		Code(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public abstract <T extends HandicapFactory<?> & MemberFactory> T getMemberFactory();

		public boolean equal(String code) {
			return this.name().equals(code);
		}

		@Override
		public String toString() {
			return name();
		}

		/**
		 * 判断是否存在该游戏
		 *
		 * @param obj 游戏编码
		 * @return 存在返回游戏，不存在返回 NULL
		 */
		public static BaseHandicapUtil.Code value(Object obj) {
			try {
				return BaseHandicapUtil.Code.valueOf(obj.toString());
			} catch (IllegalArgumentException e) {
				return null;
			}
		}
	}
	//endregion


	//region 快速获取方法

	/**
	 * 获取盘口类
	 *
	 * @param handicapCode 盘口编码
	 * @return 盘口类
	 */
	public static Handicap handicap(Code handicapCode) {
		return handicapCode.getMemberFactory().handicap();
	}
	//endregion

}
