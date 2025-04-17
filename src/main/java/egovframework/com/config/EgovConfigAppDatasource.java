package egovframework.com.config;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import io.github.cdimascio.dotenv.Dotenv;

/**
 * @ClassName : EgovConfigAppDatasource.java
 * @Description : DataSource 설정
 *
 * @author : 윤주호
 * @since  : 2021. 7. 20
 * @version : 1.0
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일              수정자               수정내용
 *  -------------  ------------   ---------------------
 *   2021. 7. 20    윤주호               최초 생성
 * </pre>
 *
 */
@Configuration
public class EgovConfigAppDatasource {
	private static final Logger logger = LoggerFactory.getLogger(EgovConfigAppDatasource.class);

	/**
	 *  @Value 을 어노테이션을 이용하는 방법
	 */
	//	@Value("${Globals.DbType}")
	//	private String dbType;
	//
	//	@Value("${Globals.DriverClassName}")
	//	private String className;
	//
	//	@Value("${Globals.Url}")
	//	private String url;
	//
	//	@Value("${Globals.UserName}")
	//	private String userName;
	//
	//	@Value("${Globals.Password}")
	//	private String password;

	/**
	 *  Environment 의존성 주입하여 사용하는 방법
	 */

	@Autowired
	Environment env;

	private String dbType;

	private String className;

	private String url;

	private String userName;

	private String password;

	@PostConstruct
	void init() {
		try {
			// Load .env file first
			logger.info("Loading .env file from: {}", System.getProperty("user.dir"));
			Dotenv dotenv = Dotenv.configure()
				.directory(".")
				.load();
			
			// Set system properties from .env
			String dbUrl = dotenv.get("SPRING_DATASOURCE_URL");
			String dbUsername = dotenv.get("SPRING_DATASOURCE_USERNAME");
			String dbPassword = dotenv.get("SPRING_DATASOURCE_PASSWORD");
			
			logger.info("Setting system properties from .env");
			System.setProperty("SPRING_DATASOURCE_URL", dbUrl);
			System.setProperty("SPRING_DATASOURCE_USERNAME", dbUsername);
			System.setProperty("SPRING_DATASOURCE_PASSWORD", dbPassword);
			
			// Use environment variables for datasource configuration
			this.url = System.getProperty("SPRING_DATASOURCE_URL", dbUrl);
			this.userName = System.getProperty("SPRING_DATASOURCE_USERNAME", dbUsername);
			this.password = System.getProperty("SPRING_DATASOURCE_PASSWORD", dbPassword);
			this.className = "com.mysql.cj.jdbc.Driver";
			this.dbType = "mysql";
			
			logger.info("Database configuration loaded - URL: {}, Username: {}", this.url, this.userName);
		} catch (Exception e) {
			logger.error("Error loading environment variables", e);
			throw new RuntimeException("Failed to load environment variables", e);
		}
	}

	/**
	 * @return [dataSource 설정] HSQL 설정
	 */
	private DataSource dataSourceHSQL() {
		return new EmbeddedDatabaseBuilder()
			.setType(EmbeddedDatabaseType.HSQL)
			.setScriptEncoding("UTF8")
			.addScript("classpath:/db/shtdb.sql")
			//			.addScript("classpath:/otherpath/other.sql")
			.build();
	}

	/**
	 * @return [dataSource 설정] basicDataSource 설정
	 */
	private DataSource basicDataSource() {
		logger.info("Creating BasicDataSource with URL: {}, Username: {}", url, userName);
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(className);
		basicDataSource.setUrl(url);
		basicDataSource.setUsername(userName);
		basicDataSource.setPassword(password);
		return basicDataSource;
	}

	/**
	 * @return [DataSource 설정]
	 */
	@Bean(name = {"dataSource", "egov.dataSource", "egovDataSource"})
	public DataSource dataSource() {
		logger.info("Creating DataSource with dbType: {}", dbType);
		if ("hsql".equals(dbType)) {
			return dataSourceHSQL();
		} else {
			return basicDataSource();
		}
	}
}
