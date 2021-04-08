package wsdl.vix;

import java.util.List;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.interceptor.PayloadLoggingInterceptor;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class VixConfig extends WsConfigurerAdapter {

	public static final String VIX_NAMESPACE = "http://vix.fleetplanner.co.uk";

	@Override
	public void addInterceptors(List<EndpointInterceptor> interceptors) {
		PayloadValidatingInterceptor validator = new PayloadValidatingInterceptor();
		validator.setValidateRequest(true);
		validator.setValidateResponse(true);
		validator.setXsdSchema(vixSchema());
		interceptors.add(validator);
		PayloadLoggingInterceptor logging = new PayloadLoggingInterceptor();
		logging.setLogRequest(true);
		logging.setLogResponse(true);
		interceptors.add(logging);
	}
	
	@Bean
	public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(
			ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean<>(servlet, "/vix/*");
	}

	@Bean("vix")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema vixSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("VixPort");
		wsdl11Definition.setLocationUri("/vix");
		wsdl11Definition.setTargetNamespace(VIX_NAMESPACE);
		wsdl11Definition.setSchema(vixSchema);
		return wsdl11Definition;
	}

	@Bean
	public XsdSchema vixSchema() {
		return new SimpleXsdSchema(new ClassPathResource("xsd/vix.xsd"));
	}
	
}
