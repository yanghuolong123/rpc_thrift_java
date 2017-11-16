package cn.womem.server;

import cn.womem.api.HelloWorldService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TTransportException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RpcThriftServerApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void thriftTest2() throws TTransportException {
		THttpClient searchClient = new THttpClient("http://localhost:8080/api/hello");
		TProtocol searchProtocol = new TCompactProtocol(searchClient);

		HelloWorldService.Client helloService = new HelloWorldService.Client(searchProtocol);

		try {
			String str = helloService.sayHello("我的第一次thrift测试!");
			System.out.print("================== str:"+str);
		} catch (TException e) {
			e.printStackTrace();
		}
	}

}
