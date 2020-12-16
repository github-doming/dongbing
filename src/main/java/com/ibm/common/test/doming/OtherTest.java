package com.ibm.common.test.doming;

import com.ibm.common.core.CommTest;
import com.ibm.common.tools.GameTool;
import com.ibm.common.utils.game.GameUtil;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.common.servlet.AsynAction;
import org.doming.core.common.servlet.WebServletContent;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.doming.develop.http.HttpConfig;
import org.doming.example.clazz.ge4.single.Son;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * @Author: Dongming
 * @Date: 2019-12-23 14:59
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class OtherTest extends CommTest {

	public static void main(String[] args) throws Exception {
		GameUtil.Code gameCode = GameUtil.Code.XYNC;
		String betContent = "第二球|双|2000#第二球|尾大|2000#第二球|尾小|2000#第二球|合单|2000#第二球|合双|2000#第二球|大|2000#第二球|小|2000#第二球|单|2000#";

		System.out.println(betContent);

		//获取投注code
		Map<Integer, Map<Integer, Integer>> betCode = GameTool.getBetCode(gameCode, betContent.split("#"), 1);
		if (ContainerTool.isEmpty(betCode)) {
			return;
		}
		StringBuilder content = new StringBuilder();
		//获取投注详情信息
		for (Map.Entry<Integer, Map<Integer, Integer>> codeEntry : betCode.entrySet()) {
			for (Map.Entry<Integer, Integer> typeEntry : codeEntry.getValue().entrySet()) {
				content.append(GameTool.itemStr(gameCode, codeEntry.getKey(), typeEntry.getKey(), typeEntry.getValue()))
						.append("#");
			}
		}
		System.out.println(content.toString());
	}


	@Test public void testLog(){
		System.out.println(Long.MAX_VALUE);
		System.out.println(Long.MIN_VALUE);

		System.out.println(DateTool.get(new Date(Long.MAX_VALUE)));
	}




	@Test public void test(){


		Class<?> clazz = Son.class;
		ActionMapping annotation = clazz.getDeclaredAnnotation(ActionMapping.class);
		AsynAction	asynAction = clazz.getAnnotation(AsynAction.class);

		System.out.println(asynAction.code().code());
		System.out.println(Arrays.toString(annotation.value()));
		System.out.println(annotation.name());


	}

	@AsynAction(code = HttpConfig.Code.BASE)
	class Parent{

	}

	@ActionMapping(name = "Son")
	class Son extends Parent{

	}

}
