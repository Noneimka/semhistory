package history.config;

import history.filter.DataOnAllPagesFilter;
import history.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class FilterConfig {

    @Autowired
    @Bean
    public FilterRegistrationBean registrationBean(UserService userService) {

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new DataOnAllPagesFilter(userService));
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}
