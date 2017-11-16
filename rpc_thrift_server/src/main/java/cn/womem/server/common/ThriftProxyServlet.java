package cn.womem.server.common;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TTransport;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;

/**
 * Created by yanghuolong on 17-11-15.
 */
public class ThriftProxyServlet extends HttpServlet {
    private final TProcessor processor;

    private final TProtocolFactory inProtocolFactory;

    private final TProtocolFactory outProtocolFactory;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ThriftProxyServlet(String serviceInterface, String serviceIface,
                              Object serviceImplObject) throws Exception {
        super();


        Class Processor = Class.forName(serviceInterface + "$Processor");
        Class Iface = Class
                .forName(StringUtils.hasText(serviceIface) ? serviceIface
                        : serviceInterface + "$Iface");
        Constructor con = Processor.getConstructor(Iface);
        TProcessor processor = (TProcessor) con.newInstance(serviceImplObject);

        this.processor = processor;
        this.inProtocolFactory = new TCompactProtocol.Factory();
        this.outProtocolFactory = new TCompactProtocol.Factory();

    }

    @SuppressWarnings("rawtypes")
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        TTransport inTransport = null;
        TTransport outTransport = null;

        //String tmpname = Service.getName();

        try {
            response.setContentType("application/x-thrift");



            InputStream in = request.getInputStream();
            OutputStream out = response.getOutputStream();

            TTransport transport = new TIOStreamTransport(in, out);
            inTransport = transport;
            outTransport = transport;

            TProtocol inProtocol = inProtocolFactory.getProtocol(inTransport);
            TProtocol outProtocol = outProtocolFactory
                    .getProtocol(outTransport);

            processor.process(inProtocol, outProtocol);

            out.flush();
        } catch (TException te) {
            throw new ServletException(te);
        }
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

}
