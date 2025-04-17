package egovframework.com.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import javax.annotation.PostConstruct;

@Configuration
@PropertySources({
	@PropertySource("classpath:/application.yml")
})
public class EgovConfigApp {

	@PostConstruct
	public void loadEnv() {
		// .env 파일이 있으면 로드하고, 없으면 시스템 환경 변수를 사용
		Dotenv dotenv = Dotenv.configure()
			.ignoreIfMissing()
			.load();
			
		// 환경 변수를 시스템 속성으로 설정
		dotenv.entries().forEach(entry -> {
			if (System.getProperty(entry.getKey()) == null) {
				System.setProperty(entry.getKey(), entry.getValue());
			}
		});
	}
}
