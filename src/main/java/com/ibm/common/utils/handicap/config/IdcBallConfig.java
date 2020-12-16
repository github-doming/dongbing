package com.ibm.common.utils.handicap.config;
import com.google.common.collect.ImmutableMap;
import com.ibm.common.core.configs.IdcConfig;

import java.util.Map;
/**
 * @Description: 球号
 * @Author: zjj
 * @Date: 2019-10-17 15:43
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public interface IdcBallConfig extends IdcConfig, BallConfig {

	Map<String, String> BALL_CODE = ImmutableMap.<String, String>builder().put("第一球|0", "205:0").put("第一球|1", "205:1")
			.put("第一球|2", "205:2").put("第一球|3", "205:3").put("第一球|4", "205:4").put("第一球|5", "205:5")
			.put("第一球|6", "205:6").put("第一球|7", "205:7").put("第一球|8", "205:8").put("第一球|9", "205:9")
			.put("第一球|单", "215:1").put("第一球|双", "215:2").put("第一球|大", "210:1").put("第一球|小", "210:2")

			.put("第二球|0", "206:0").put("第二球|1", "206:1").put("第二球|2", "206:2").put("第二球|3", "206:3")
			.put("第二球|4", "206:4").put("第二球|5", "206:5").put("第二球|6", "206:6").put("第二球|7", "206:7")
			.put("第二球|8", "206:8").put("第二球|9", "206:9").put("第二球|单", "216:1").put("第二球|双", "216:2")
			.put("第二球|大", "211:1").put("第二球|小", "211:2")

			.put("第三球|0", "207:0").put("第三球|1", "207:1").put("第三球|2", "207:2").put("第三球|3", "207:3")
			.put("第三球|4", "207:4").put("第三球|5", "207:5").put("第三球|6", "207:6").put("第三球|7", "207:7")
			.put("第三球|8", "207:8").put("第三球|9", "207:9").put("第三球|单", "217:1").put("第三球|双", "217:2")
			.put("第三球|大", "212:1").put("第三球|小", "212:2")

			.put("第四球|0", "208:0").put("第四球|1", "208:1").put("第四球|2", "208:2").put("第四球|3", "208:3")
			.put("第四球|4", "208:4").put("第四球|5", "208:5").put("第四球|6", "208:6").put("第四球|7", "208:7")
			.put("第四球|8", "208:8").put("第四球|9", "208:9").put("第四球|单", "218:1").put("第四球|双", "218:2")
			.put("第四球|大", "213:1").put("第四球|小", "213:2")

			.put("第五球|0", "209:0").put("第五球|1", "209:1").put("第五球|2", "209:2").put("第五球|3", "209:3")
			.put("第五球|4", "209:4").put("第五球|5", "209:5").put("第五球|6", "209:6").put("第五球|7", "209:7")
			.put("第五球|8", "209:8").put("第五球|9", "209:9").put("第五球|单", "219:1").put("第五球|双", "219:2")
			.put("第五球|大", "214:1").put("第五球|小", "214:2")

			.put("总和|大", "346:1").put("总和|小", "346:2").put("总和|单", "345:1").put("总和|双", "345:2").put("龙虎和|龙", "347:1")
			.put("龙虎和|虎", "347:2").put("龙虎和|和", "347:3").put("前三|豹子", "330:1").put("中三|豹子", "331:1")
			.put("后三|豹子", "332:1").put("前三|顺子", "333:1").put("中三|顺子", "334:1").put("后三|顺子", "335:1")
			.put("前三|对子", "336:1").put("中三|对子", "337:1").put("后三|对子", "338:1").put("前三|半顺", "339:1")
			.put("中三|半顺", "340:1").put("后三|半顺", "341:1").put("前三|杂六", "342:1").put("中三|杂六", "343:1")
			.put("后三|杂六", "344:1")

			.put("牛牛|无牛", "358:0").put("牛牛|牛1", "358:1").put("牛牛|牛2", "358:2").put("牛牛|牛3", "358:3")
			.put("牛牛|牛4", "358:4").put("牛牛|牛5", "358:5").put("牛牛|牛6", "358:6").put("牛牛|牛7", "358:7")
			.put("牛牛|牛8", "358:8").put("牛牛|牛9", "358:9").put("牛牛|牛牛", "358:10").put("牛牛|单", "359:1")
			.put("牛牛|双", "359:2").put("牛牛|大", "360:1").put("牛牛|小", "360:2")

			.put("梭哈|五梅", "362:1").put("梭哈|四带一", "362:2").put("梭哈|葫芦", "362:3").put("梭哈|顺子", "362:4")
			.put("梭哈|三条", "362:5").put("梭哈|两对", "362:6").put("梭哈|单对", "362:7").put("梭哈|五不靠", "362:8")
			.put("梭哈|散号", "362:9")

			.put("合数万千|单", "348:1").put("合数万千|双", "348:2").put("合数万百|单", "349:1").put("合数万百|双", "349:2")
			.put("合数万十|单", "350:1").put("合数万十|双", "350:2").put("合数万个|单", "351:1").put("合数万个|双", "351:2")
			.put("合数千百|单", "352:1").put("合数千百|双", "352:2").put("合数千十|单", "353:1").put("合数千十|双", "353:2")
			.put("合数千个|单", "354:1").put("合数千个|双", "354:2").put("合数百十|单", "355:1").put("合数百十|双", "355:2")
			.put("合数百个|单", "356:1").put("合数百个|双", "356:2").put("合数十个|单", "357:1").put("合数十个|双", "357:2")

			.put("合数尾数万千|大", "368:1").put("合数尾数万千|小", "368:2").put("合数尾数万百|大", "369:1").put("合数尾数万百|小", "369:2")
			.put("合数尾数万十|大", "370:1").put("合数尾数万十|小", "370:2").put("合数尾数万个|大", "371:1").put("合数尾数万个|小", "371:2")
			.put("合数尾数千百|大", "372:1").put("合数尾数千百|小", "372:2").put("合数尾数千十|大", "373:1").put("合数尾数千十|小", "373:2")
			.put("合数尾数千个|大", "374:1").put("合数尾数千个|小", "374:2").put("合数尾数百十|大", "375:1").put("合数尾数百十|小", "375:2")
			.put("合数尾数百个|大", "376:1").put("合数尾数百个|小", "376:2").put("合数尾数十个|大", "377:1").put("合数尾数十个|小", "377:2")
			.build();

	Map<String, String> BALL_ITEM = ImmutableMap.<String, String>builder().put("205:0", "第一球|0").put("205:1", "第一球|1")
			.put("205:2", "第一球|2").put("205:3", "第一球|3").put("205:4", "第一球|4").put("205:5", "第一球|5")
			.put("205:6", "第一球|6").put("205:7", "第一球|7").put("205:8", "第一球|8").put("205:9", "第一球|9")
			.put("215:1", "第一球|单").put("215:2", "第一球|双").put("210:1", "第一球|大").put("210:2", "第一球|小")

			.put("206:0", "第二球|0").put("206:1", "第二球|1").put("206:2", "第二球|2").put("206:3", "第二球|3")
			.put("206:4", "第二球|4").put("206:5", "第二球|5").put("206:6", "第二球|6").put("206:7", "第二球|7")
			.put("206:8", "第二球|8").put("206:9", "第二球|9").put("216:1", "第二球|单").put("216:2", "第二球|双")
			.put("211:1", "第二球|大").put("211:2", "第二球|小")

			.put("207:0", "第三球|0").put("207:1", "第三球|1").put("207:2", "第三球|2").put("207:3", "第三球|3")
			.put("207:4", "第三球|4").put("207:5", "第三球|5").put("207:6", "第三球|6").put("207:7", "第三球|7")
			.put("207:8", "第三球|8").put("207:9", "第三球|9").put("217:1", "第三球|单").put("217:2", "第三球|双")
			.put("212:1", "第三球|大").put("212:2", "第三球|小")

			.put("208:0", "第四球|0").put("208:1", "第四球|1").put("208:2", "第四球|2").put("208:3", "第四球|3")
			.put("208:4", "第四球|4").put("208:5", "第四球|5").put("208:6", "第四球|6").put("208:7", "第四球|7")
			.put("208:8", "第四球|8").put("208:9", "第四球|9").put("218:1", "第四球|单").put("218:2", "第四球|双")
			.put("213:1", "第四球|大").put("213:2", "第四球|小")

			.put("209:0", "第五球|0").put("209:1", "第五球|1").put("209:2", "第五球|2").put("209:3", "第五球|3")
			.put("209:4", "第五球|4").put("209:5", "第五球|5").put("209:6", "第五球|6").put("209:7", "第五球|7")
			.put("209:8", "第五球|8").put("209:9", "第五球|9").put("219:1", "第五球|单").put("219:2", "第五球|双")
			.put("214:1", "第五球|大").put("214:2", "第五球|小")

			.put("346:1", "总和|大").put("346:2", "总和|小").put("345:1", "总和|单").put("345:2", "总和|双").put("347:1", "龙虎和|龙")
			.put("347:2", "龙虎和|虎").put("347:3", "龙虎和|和").put("330:1", "前三|豹子").put("331:1", "中三|豹子")
			.put("332:1", "后三|豹子").put("333:1", "前三|顺子").put("334:1", "中三|顺子").put("335:1", "后三|顺子")
			.put("336:1", "前三|对子").put("337:1", "中三|对子").put("338:1", "后三|对子").put("339:1", "前三|半顺")
			.put("340:1", "中三|半顺").put("341:1", "后三|半顺").put("342:1", "前三|杂六").put("343:1", "中三|杂六")
			.put("344:1", "后三|杂六").build();

	Map<String, String> BET_LIMIT_CODE = ImmutableMap.<String, String>builder().put("第一球|0", "00").put("第一球|1", "00")
			.put("第一球|2", "00").put("第一球|3", "00").put("第一球|4", "00").put("第一球|5", "00").put("第一球|6", "00")
			.put("第一球|7", "00").put("第一球|8", "00").put("第一球|9", "00").put("第一球|大", "01").put("第一球|小", "01")
			.put("第一球|单", "01").put("第一球|双", "01")

			.put("第二球|0", "00").put("第二球|1", "00").put("第二球|2", "00").put("第二球|3", "00").put("第二球|4", "00")
			.put("第二球|5", "00").put("第二球|6", "00").put("第二球|7", "00").put("第二球|8", "00").put("第二球|9", "00")
			.put("第二球|大", "01").put("第二球|小", "01").put("第二球|单", "01").put("第二球|双", "01")

			.put("第三球|0", "00").put("第三球|1", "00").put("第三球|2", "00").put("第三球|3", "00").put("第三球|4", "00")
			.put("第三球|5", "00").put("第三球|6", "00").put("第三球|7", "00").put("第三球|8", "00").put("第三球|9", "00")
			.put("第三球|大", "01").put("第三球|小", "01").put("第三球|单", "01").put("第三球|双", "01")

			.put("第四球|0", "00").put("第四球|1", "00").put("第四球|2", "00").put("第四球|3", "00").put("第四球|4", "00")
			.put("第四球|5", "00").put("第四球|6", "00").put("第四球|7", "00").put("第四球|8", "00").put("第四球|9", "00")
			.put("第四球|大", "01").put("第四球|小", "01").put("第四球|单", "01").put("第四球|双", "01")

			.put("第五球|0", "00").put("第五球|1", "00").put("第五球|2", "00").put("第五球|3", "00").put("第五球|4", "00")
			.put("第五球|5", "00").put("第五球|6", "00").put("第五球|7", "00").put("第五球|8", "00").put("第五球|9", "00")
			.put("第五球|大", "01").put("第五球|小", "01").put("第五球|单", "01").put("第五球|双", "01")

			.put("总和|大", "07").put("总和|小", "07").put("总和|单", "07").put("总和|双", "07").put("龙虎和|龙", "08")
			.put("龙虎和|虎", "08").put("龙虎和|和", "08").put("前三|豹子", "02").put("中三|豹子", "02").put("后三|豹子", "02")
			.put("前三|顺子", "03").put("中三|顺子", "03").put("后三|顺子", "03").put("前三|对子", "04").put("中三|对子", "04")
			.put("后三|对子", "04").put("前三|半顺", "05").put("中三|半顺", "05").put("后三|半顺", "05").put("前三|杂六", "06")
			.put("中三|杂六", "06").put("后三|杂六", "06").build();

	Map<String, String> BET_INFO_CODE = ImmutableMap.<String, String>builder().put("第一球0", "第一球|0").put("第一球1", "第一球|1")
			.put("第一球2", "第一球|2").put("第一球3", "第一球|3").put("第一球4", "第一球|4").put("第一球5", "第一球|5").put("第一球6", "第一球|6")
			.put("第一球7", "第一球|7").put("第一球8", "第一球|8").put("第一球9", "第一球|9").put("第一球单双单", "第一球|单")
			.put("第一球单双双", "第一球|双").put("第一球大小大", "第一球|大").put("第一球大小小", "第一球|小")

			.put("第二球0", "第二球|0").put("第二球1", "第二球|1").put("第二球2", "第二球|2").put("第二球3", "第二球|3").put("第二球4", "第二球|4")
			.put("第二球5", "第二球|5").put("第二球6", "第二球|6").put("第二球7", "第二球|7").put("第二球8", "第二球|8").put("第二球9", "第二球|9")
			.put("第二球单双单", "第二球|单").put("第二球单双双", "第二球|双").put("第二球大小大", "第二球|大").put("第二球大小小", "第二球|小")

			.put("第三球0", "第三球|0").put("第三球1", "第三球|1").put("第三球2", "第三球|2").put("第三球3", "第三球|3").put("第三球4", "第三球|4")
			.put("第三球5", "第三球|5").put("第三球6", "第三球|6").put("第三球7", "第三球|7").put("第三球8", "第三球|8").put("第三球9", "第三球|9")
			.put("第三球单双单", "第三球|单").put("第三球单双双", "第三球|双").put("第三球大小大", "第三球|大").put("第三球大小小", "第三球|小")

			.put("第四球0", "第四球|0").put("第四球1", "第四球|1").put("第四球2", "第四球|2").put("第四球3", "第四球|3").put("第四球4", "第四球|4")
			.put("第四球5", "第四球|5").put("第四球6", "第四球|6").put("第四球7", "第四球|7").put("第四球8", "第四球|8").put("第四球9", "第四球|9")
			.put("第四球单双单", "第四球|单").put("第四球单双双", "第四球|双").put("第四球大小大", "第四球|大").put("第四球大小小", "第四球|小")

			.put("第五球0", "第五球|0").put("第五球1", "第五球|1").put("第五球2", "第五球|2").put("第五球3", "第五球|3").put("第五球4", "第五球|4")
			.put("第五球5", "第五球|5").put("第五球6", "第五球|6").put("第五球7", "第五球|7").put("第五球8", "第五球|8").put("第五球9", "第五球|9")
			.put("第五球单双单", "第五球|单").put("第五球单双双", "第五球|双").put("第五球大小大", "第五球|大").put("第五球大小小", "第五球|小")

			.put("总和大小大", "总和|大").put("总和大小小", "总和|小").put("总和单双单", "总和|单").put("总和单双双", "总和|双").put("龙虎和龙", "龙虎和|龙")
			.put("龙虎和虎", "龙虎和|虎").put("龙虎和和", "龙虎和|和").put("豹子(前三)豹子", "前三|豹子").put("豹子(中三)豹子", "中三|豹子")
			.put("豹子(后三)豹子", "后三|豹子").put("顺子(前三)顺子", "前三|顺子").put("顺子(中三)顺子", "中三|顺子").put("顺子(后三)顺子", "后三|顺子")
			.put("对子(前三)对子", "前三|对子").put("对子(中三)对子", "中三|对子").put("对子(后三)对子", "后三|对子").put("半顺(前三)半顺", "前三|半顺")
			.put("半顺(中三)半顺", "中三|半顺").put("半顺(后三)半顺", "后三|半顺").put("杂六(前三)杂六", "前三|杂六").put("杂六(中三)杂六", "中三|杂六")
			.put("杂六(后三)杂六", "后三|杂六").build();
/*

	List<String> NUMBER_BALL = ImmutableList.<String>builder().add("205").add("206").add("207").add("208").add("209")
			.add("210").add("211").add("212").add("213").add("214").add("215").add("216").add("217").add("218")
			.add("219").add("330").add("331").add("332").add("333").add("334").add("335").add("336").add("337")
			.add("338").add("339").add("340").add("341").add("342").add("343").add("344").add("345").add("346")
			.add("347").build();

	List<String> POKER_BALL = ImmutableList.<String>builder().add("358").add("359").add("360").add("362").build();

	List<String> SUM_BALL = ImmutableList.<String>builder().add("348").add("349").add("350").add("351").add("352")
			.add("353").add("354").add("355").add("356").add("357").add("368").add("369").add("370").add("371")
			.add("372").add("373").add("374").add("375").add("376").add("377").build();

	Map<String, String> NUMBER_CODE = ImmutableMap.<String, String>builder().put("第一球|0", "205:0").put("第一球|1", "205:1")
			.put("第一球|2", "205:2").put("第一球|3", "205:3").put("第一球|4", "205:4").put("第一球|5", "205:5")
			.put("第一球|6", "205:6").put("第一球|7", "205:7").put("第一球|8", "205:8").put("第一球|9", "205:9")
			.put("第一球|单", "215:1").put("第一球|双", "215:2").put("第一球|大", "210:1").put("第一球|小", "210:2")

			.put("第二球|0", "206:0").put("第二球|1", "206:1").put("第二球|2", "206:2").put("第二球|3", "206:3")
			.put("第二球|4", "206:4").put("第二球|5", "206:5").put("第二球|6", "206:6").put("第二球|7", "206:7")
			.put("第二球|8", "206:8").put("第二球|9", "206:9").put("第二球|单", "216:1").put("第二球|双", "216:2")
			.put("第二球|大", "211:1").put("第二球|小", "211:2")

			.put("第三球|0", "207:0").put("第三球|1", "207:1").put("第三球|2", "207:2").put("第三球|3", "207:3")
			.put("第三球|4", "207:4").put("第三球|5", "207:5").put("第三球|6", "207:6").put("第三球|7", "207:7")
			.put("第三球|8", "207:8").put("第三球|9", "207:9").put("第三球|单", "217:1").put("第三球|双", "217:2")
			.put("第三球|大", "212:1").put("第三球|小", "212:2")

			.put("第四球|0", "208:0").put("第四球|1", "208:1").put("第四球|2", "208:2").put("第四球|3", "208:3")
			.put("第四球|4", "208:4").put("第四球|5", "208:5").put("第四球|6", "208:6").put("第四球|7", "208:7")
			.put("第四球|8", "208:8").put("第四球|9", "208:9").put("第四球|单", "218:1").put("第四球|双", "218:2")
			.put("第四球|大", "213:1").put("第四球|小", "213:2")

			.put("第五球|0", "209:0").put("第五球|1", "209:1").put("第五球|2", "209:2").put("第五球|3", "209:3")
			.put("第五球|4", "209:4").put("第五球|5", "209:5").put("第五球|6", "209:6").put("第五球|7", "209:7")
			.put("第五球|8", "209:8").put("第五球|9", "209:9").put("第五球|单", "219:1").put("第五球|双", "219:2")
			.put("第五球|大", "214:1").put("第五球|小", "214:2")

			.put("总和|大", "346:1").put("总和|小", "346:2").put("总和|单", "345:1").put("总和|双", "345:2").put("龙虎和|龙", "347:1")
			.put("龙虎和|虎", "347:2").put("龙虎和|和", "347:3").put("前三|豹子", "330:1").put("中三|豹子", "331:1").put("后三|豹子", "332:1")
			.put("前三|顺子", "333:1").put("中三|顺子", "334:1").put("后三|顺子", "335:1").put("前三|对子", "336:1")
			.put("中三|对子", "337:1").put("后三|对子", "338:1").put("前三|半顺", "339:1").put("中三|半顺", "340:1")
			.put("后三|半顺", "341:1").put("前三|杂六", "342:1").put("中三|杂六", "343:1").put("后三|杂六", "344:1").build();

	Map<String, String> POKER_CODE = ImmutableMap.<String, String>builder().put("牛牛|无牛", "358:0").put("牛牛|牛1", "358:1")
			.put("牛牛|牛2", "358:2").put("牛牛|牛3", "358:3").put("牛牛|牛4", "358:4").put("牛牛|牛5", "358:5")
			.put("牛牛|牛6", "358:6").put("牛牛|牛7", "358:7").put("牛牛|牛8", "358:8").put("牛牛|牛9", "358:9")
			.put("牛牛|牛牛", "358:10").put("牛牛|单", "359:1").put("牛牛|双", "359:2").put("牛牛|大", "360:1").put("牛牛|小", "360:2")

			.put("梭哈|五梅", "362:1").put("梭哈|四带一", "362:2").put("梭哈|葫芦", "362:3").put("梭哈|顺子", "362:4")
			.put("梭哈|三条", "362:5").put("梭哈|两对", "362:6").put("梭哈|单对", "362:7").put("梭哈|五不靠", "362:8")
			.put("梭哈|散号", "362:9").build();

	Map<String, String> SUM_CODE = ImmutableMap.<String, String>builder().put("合数万千|单", "348:1").put("合数万千|双", "348:2")
			.put("合数万百|单", "349:1").put("合数万百|双", "349:2").put("合数万十|单", "350:1").put("合数万十|双", "350:2")
			.put("合数万个|单", "351:1").put("合数万个|双", "351:2").put("合数千百|单", "352:1").put("合数千百|双", "352:2")
			.put("合数千十|单", "353:1").put("合数千十|双", "353:2").put("合数千个|单", "354:1").put("合数千个|双", "354:2")
			.put("合数百十|单", "355:1").put("合数百十|双", "355:2").put("合数百个|单", "356:1").put("合数百个|双", "356:2")
			.put("合数十个|单", "357:1").put("合数十个|双", "357:2")

			.put("合数尾数万千|大", "368:1").put("合数尾数万千|小", "368:2").put("合数尾数万百|大", "369:1").put("合数尾数万百|小", "369:2")
			.put("合数尾数万十|大", "370:1").put("合数尾数万十|小", "370:2").put("合数尾数万个|大", "371:1").put("合数尾数万个|小", "371:2")
			.put("合数尾数千百|大", "372:1").put("合数尾数千百|小", "372:2").put("合数尾数千十|大", "373:1").put("合数尾数千十|小", "373:2")
			.put("合数尾数千个|大", "374:1").put("合数尾数千个|小", "374:2").put("合数尾数百十|大", "375:1").put("合数尾数百十|小", "375:2")
			.put("合数尾数百个|大", "376:1").put("合数尾数百个|小", "376:2").put("合数尾数十个|大", "377:1").put("合数尾数十个|小", "377:2")
			.build();
	*/

}
