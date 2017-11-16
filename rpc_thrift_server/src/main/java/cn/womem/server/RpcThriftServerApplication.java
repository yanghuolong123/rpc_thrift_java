package cn.womem.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
//@EnableAspectJAutoProxy(proxyTargetClass = true)
public class RpcThriftServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RpcThriftServerApplication.class, args);
	}
}
