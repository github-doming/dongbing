package com.common.config.handicap;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * COM 盘口 快乐彩 玩法配置类
 *
 * @Author: Dongming
 * @Date: 2020-06-10 11:38
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface ComHappyConfig extends ComConfig, HappyConfig {
	/**
	 * 类型编码
	 */
	Map<String, String> TYPE_CODE = ImmutableMap.<String, String>builder().put("dobleSides", "lmp")
			.put("ballNO", "d1_8").put("sumDT", "d1").build();
	/**
	 * 投注类型
	 */
	Map<String, String> BET_TYPE = ImmutableMap.<String, String>builder().put("dobleSides",
			"100,102,103,104,105,107,108,109,11,110,112,113,114,115,117,118,119,12,120,13,80,82,83,84,85,87,88,89,90,92,93,94,95,97,98,99")
			.put("ballNO", "81,86,91,96,101,106,111,116")
			.put("sumDT", "121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136").build();



	Map<String, String> ITEM_CODE = ImmutableMap.<String, String>builder().put("第一球|1", "81_1").put("第一球|2", "81_2")
			.put("第一球|3", "81_3").put("第一球|4", "81_4").put("第一球|5", "81_5").put("第一球|6", "81_6")
			.put("第一球|7", "81_7").put("第一球|8", "81_8").put("第一球|9", "81_9").put("第一球|10", "81_10")
			.put("第一球|11", "81_11").put("第一球|12", "81_12").put("第一球|13", "81_13").put("第一球|14", "81_14")
			.put("第一球|15", "81_15").put("第一球|16", "81_16").put("第一球|17", "81_17").put("第一球|18", "81_18")
			.put("第一球|19", "81_19").put("第一球|20", "81_20")

			.put("第一球|大", "82_21").put("第一球|小", "82_22").put("第一球|单", "83_23").put("第一球|双", "83_24")
			.put("第一球|合单", "85_25").put("第一球|合双", "85_26").put("第一球|尾大", "84_27").put("第一球|尾小", "84_28")
			.put("第一球|东", "121_251").put("第一球|南", "121_252").put("第一球|西", "121_253").put("第一球|北", "121_254")
			.put("第一球|中", "122_283").put("第一球|发", "122_284").put("第一球|白", "122_285")

			.put("第二球|1", "86_29").put("第二球|2", "86_30").put("第二球|3", "86_31").put("第二球|4", "86_32")
			.put("第二球|5", "86_33").put("第二球|6", "86_34").put("第二球|7", "86_35").put("第二球|8", "86_36")
			.put("第二球|9", "86_37").put("第二球|10", "86_38").put("第二球|11", "86_39").put("第二球|12", "86_40")
			.put("第二球|13", "86_41").put("第二球|14", "86_42").put("第二球|15", "86_43").put("第二球|16", "86_44")
			.put("第二球|17", "86_45").put("第二球|18", "86_46").put("第二球|19", "86_47").put("第二球|20", "86_48")

			.put("第二球|大", "87_49").put("第二球|小", "87_50").put("第二球|单", "88_51").put("第二球|双", "88_52")
			.put("第二球|合单", "90_53").put("第二球|合双", "90_54").put("第二球|尾大", "89_55").put("第二球|尾小", "89_56")
			.put("第二球|东", "123_255").put("第二球|南", "123_256").put("第二球|西", "123_257").put("第二球|北", "123_258")
			.put("第二球|中", "124_286").put("第二球|发", "124_287").put("第二球|白", "124_288")

			.put("第三球|1", "91_57").put("第三球|2", "91_58").put("第三球|3", "91_59").put("第三球|4", "91_60")
			.put("第三球|5", "91_61").put("第三球|6", "91_62").put("第三球|7", "91_63").put("第三球|8", "91_64")
			.put("第三球|9", "91_65").put("第三球|10", "91_66").put("第三球|11", "91_67").put("第三球|12", "91_68")
			.put("第三球|13", "91_69").put("第三球|14", "91_70").put("第三球|15", "91_71").put("第三球|16", "91_72")
			.put("第三球|17", "91_73").put("第三球|18", "91_74").put("第三球|19", "91_75").put("第三球|20", "91_76")

			.put("第三球|大", "92_77").put("第三球|小", "92_78").put("第三球|单", "93_79").put("第三球|双", "93_80")
			.put("第三球|合单", "95_81").put("第三球|合双", "95_82").put("第三球|尾大", "94_83").put("第三球|尾小", "94_84")
			.put("第三球|东", "125_259").put("第三球|南", "125_260").put("第三球|西", "125_261").put("第三球|北", "125_262")
			.put("第三球|中", "126_289").put("第三球|发", "126_290").put("第三球|白", "126_291")

			.put("第四球|1", "96_85").put("第四球|2", "96_86").put("第四球|3", "96_87").put("第四球|4", "96_88")
			.put("第四球|5", "96_89").put("第四球|6", "96_90").put("第四球|7", "96_91").put("第四球|8", "96_92")
			.put("第四球|9", "96_93").put("第四球|10", "96_94").put("第四球|11", "96_95").put("第四球|12", "96_96")
			.put("第四球|13", "96_97").put("第四球|14", "96_98").put("第四球|15", "96_99").put("第四球|16", "96_100")
			.put("第四球|17", "96_101").put("第四球|18", "96_102").put("第四球|19", "96_103").put("第四球|20", "96_104")

			.put("第四球|大", "97_105").put("第四球|小", "97_106").put("第四球|单", "98_107").put("第四球|双", "98_108")
			.put("第四球|合单", "100_109").put("第四球|合双", "100_110").put("第四球|尾大", "99_111").put("第四球|尾小", "99_112")
			.put("第四球|东", "127_263").put("第四球|南", "127_264").put("第四球|西", "127_265").put("第四球|北", "127_266")
			.put("第四球|中", "128_292").put("第四球|发", "128_293").put("第四球|白", "128_294")

			.put("第五球|1", "101_113").put("第五球|2", "101_114").put("第五球|3", "101_115").put("第五球|4", "101_116")
			.put("第五球|5", "101_117").put("第五球|6", "101_118").put("第五球|7", "101_119").put("第五球|8", "101_120")
			.put("第五球|9", "101_121").put("第五球|10", "101_122").put("第五球|11", "101_123").put("第五球|12", "101_124")
			.put("第五球|13", "101_125").put("第五球|14", "101_126").put("第五球|15", "101_127").put("第五球|16", "101_128")
			.put("第五球|17", "101_129").put("第五球|18", "101_130").put("第五球|19", "101_131").put("第五球|20", "101_132")

			.put("第五球|大", "102_133").put("第五球|小", "102_134").put("第五球|单", "103_135").put("第五球|双", "103_136")
			.put("第五球|合单", "105_137").put("第五球|合双", "105_138").put("第五球|尾大", "104_139").put("第五球|尾小", "104_140")
			.put("第五球|东", "129_267").put("第五球|南", "129_268").put("第五球|西", "129_269").put("第五球|北", "129_270")
			.put("第五球|中", "130_295").put("第五球|发", "130_296").put("第五球|白", "130_297")

			.put("第六球|1", "106_141").put("第六球|2", "106_142").put("第六球|3", "106_143").put("第六球|4", "106_144")
			.put("第六球|5", "106_145").put("第六球|6", "106_146").put("第六球|7", "106_147").put("第六球|8", "106_148")
			.put("第六球|9", "106_149").put("第六球|10", "106_150").put("第六球|11", "106_151").put("第六球|12", "106_152")
			.put("第六球|13", "106_153").put("第六球|14", "106_154").put("第六球|15", "106_155").put("第六球|16", "106_156")
			.put("第六球|17", "106_157").put("第六球|18", "106_158").put("第六球|19", "106_159").put("第六球|20", "106_160")

			.put("第六球|大", "107_161").put("第六球|小", "107_162").put("第六球|单", "108_163").put("第六球|双", "108_164")
			.put("第六球|合单", "110_165").put("第六球|合双", "110_166").put("第六球|尾大", "109_167").put("第六球|尾小", "109_168")
			.put("第六球|东", "131_271").put("第六球|南", "131_272").put("第六球|西", "131_273").put("第六球|北", "131_274")
			.put("第六球|中", "132_298").put("第六球|发", "132_299").put("第六球|白", "132_300")

			.put("第七球|1", "111_169").put("第七球|2", "111_170").put("第七球|3", "111_171").put("第七球|4", "111_172")
			.put("第七球|5", "111_173").put("第七球|6", "111_174").put("第七球|7", "111_175").put("第七球|8", "111_176")
			.put("第七球|9", "111_177").put("第七球|10", "111_178").put("第七球|11", "111_179").put("第七球|12", "111_180")
			.put("第七球|13", "111_181").put("第七球|14", "111_182").put("第七球|15", "111_183").put("第七球|16", "111_184")
			.put("第七球|17", "111_185").put("第七球|18", "111_186").put("第七球|19", "111_187").put("第七球|20", "111_188")

			.put("第七球|大", "112_189").put("第七球|小", "112_190").put("第七球|单", "113_191").put("第七球|双", "113_192")
			.put("第七球|合单", "115_193").put("第七球|合双", "115_194").put("第七球|尾大", "114_195").put("第七球|尾小", "114_196")
			.put("第七球|东", "133_275").put("第七球|南", "133_276").put("第七球|西", "133_277").put("第七球|北", "133_278")
			.put("第七球|中", "134_301").put("第七球|发", "134_302").put("第七球|白", "134_303")

			.put("第八球|1", "116_197").put("第八球|2", "116_198").put("第八球|3", "116_199").put("第八球|4", "116_200")
			.put("第八球|5", "116_201").put("第八球|6", "116_202").put("第八球|7", "116_203").put("第八球|8", "116_204")
			.put("第八球|9", "116_205").put("第八球|10", "116_206").put("第八球|11", "116_207").put("第八球|12", "116_208")
			.put("第八球|13", "116_209").put("第八球|14", "116_210").put("第八球|15", "116_211").put("第八球|16", "116_212")
			.put("第八球|17", "116_213").put("第八球|18", "116_214").put("第八球|19", "116_215").put("第八球|20", "116_216")

			.put("第八球|大", "117_217").put("第八球|小", "117_218").put("第八球|单", "118_219").put("第八球|双", "118_220")
			.put("第八球|合单", "119_223").put("第八球|合双", "119_224").put("第八球|尾大", "120_221").put("第八球|尾小", "120_222")
			.put("第八球|东", "135_279").put("第八球|南", "135_280").put("第八球|西", "135_281").put("第八球|北", "135_282")
			.put("第八球|中", "136_304").put("第八球|发", "136_305").put("第八球|白", "136_306")

			.put("总和|大", "11_245").put("总和|小", "11_246").put("总和|单", "12_247").put("总和|双", "12_248")
			.put("总和|尾大", "13_249").put("总和|尾小", "13_250").put("总和|龙", "80_307").put("总和|虎", "80_308")
			.build();



}