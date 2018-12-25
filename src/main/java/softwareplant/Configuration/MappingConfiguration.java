package softwareplant.Configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
public class MappingConfiguration implements WebMvcConfigurer {

    private static final Logger logger = LogManager.getLogger(MappingConfiguration.class);

    @Override
    public void addInterceptors(InterceptorRegistry registry) {}

}