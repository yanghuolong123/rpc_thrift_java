package cn.womem.server.config;

import cn.womem.api.HelloWorldService;
import cn.womem.server.common.ThriftProxyServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.servlet.http.HttpServlet;

/**
 * Created by yanghuolong on 17-11-15.
 *
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ThriftConfig {

    @Bean
    public ServletRegistrationBean hello(HelloWorldService.Iface impl) {
        ServletRegistrationBean reg = new ServletRegistrationBean();

        ThriftProxyServlet httpServlet = null;
        try {
            httpServlet = new ThriftProxyServlet("cn.womem.api.HelloWorldService", null, impl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        reg.setName("helloWorldService");
        reg.addUrlMappings("/api/hello");
        reg.setServlet(httpServlet);

        return reg;
    }
}
