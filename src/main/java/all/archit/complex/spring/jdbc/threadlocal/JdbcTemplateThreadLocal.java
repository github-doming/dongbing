package all.archit.complex.spring.jdbc.threadlocal;
import java.sql.Connection;
import org.springframework.jdbc.core.JdbcTemplate;
public class JdbcTemplateThreadLocal {
	/**
	 * JdbcTemplate
	 */
	private static ThreadLocal<JdbcTemplate> threadLocal_jdbcTemplate = null;
	/**
	 * 
	 * @return
	 */
	public static ThreadLocal<JdbcTemplate> findThreadLocalJdbcTemplate() {
		if (threadLocal_jdbcTemplate == null) {
			threadLocal_jdbcTemplate = new ThreadLocal<JdbcTemplate>();
		}
		return threadLocal_jdbcTemplate;
	}
	// -- set与get --//
	// {
	public static ThreadLocal<JdbcTemplate> getThreadLocal_jdbcTemplate() {
		return threadLocal_jdbcTemplate;
	}
	public static void setThreadLocal_jdbcTemplate(
			ThreadLocal<JdbcTemplate> threadLocalJdbcTemplate) {
		threadLocal_jdbcTemplate = threadLocalJdbcTemplate;
	}
	// }
	// -- set与get --//
}
