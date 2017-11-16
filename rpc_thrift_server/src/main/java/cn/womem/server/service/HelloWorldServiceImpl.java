package cn.womem.server.service;

import cn.womem.api.HelloWorldService;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

/**
 * Created by yanghuolong on 17-9-29.
 */
@Service
public class HelloWorldServiceImpl implements HelloWorldService.Iface {
    @Override
    public String sayHello(String username) throws TException {
        return "Hi," + username + " Hello World all! ";
    }
}
