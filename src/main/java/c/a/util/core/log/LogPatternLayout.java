package c.a.util.core.log;
import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.helpers.PatternParser;
import org.apache.log4j.spi.LoggingEvent;
import c.a.config.ProjectConfig;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
/**
 * 
 * 重写log4j.PatternLayout
 * 
 * @author cxy
 * @Email: 
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public class LogPatternLayout extends org.apache.log4j.PatternLayout {
	@Override
	protected PatternParser createPatternParser(String pattern) {
		return new PatternParserLog(pattern);
	}
	class PatternParserLog extends PatternParser {
		public PatternParserLog(String pattern) {
			super(pattern);
		}
		@Override
		protected void finalizeConverter(char logChar) {
			if ('s' == logChar) {
				org.apache.log4j.helpers.PatternConverter patternConverter = new PatternConverter() {
					@Override
					protected String convert(LoggingEvent event) {
						if (ProjectConfig.commLocalTag == null) {
							String logLocalTag = null;
							try {
								logLocalTag = BeanThreadLocal
										.find(SysConfig.findInstance().findMap().get("comm.local.project"), "");
							} catch (Exception e) {
								e.printStackTrace();
							}
							ProjectConfig.commLocalTag = logLocalTag;
						}
						return ProjectConfig.commLocalTag;
					}
				};
				this.addConverter(patternConverter);
			} else {
				super.finalizeConverter(logChar);
			}
		}
	}
}
