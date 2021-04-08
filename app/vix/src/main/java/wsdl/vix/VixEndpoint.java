package wsdl.vix;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import uk.co.fleetplanner.vix.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

@Endpoint
public class VixEndpoint {

    @PayloadRoot(namespace = VixConfig.VIX_NAMESPACE, localPart = "LoginRequest")
    @ResponsePayload
    public LoginResponse login(@RequestPayload LoginRequest request) throws DatatypeConfigurationException {
        ObjectFactory f = new ObjectFactory();
        LoginResponse response = f.createLoginResponse();
        LoginStatus status = f.createLoginStatus();
        status.setStatus(Status.OK);
        status.setSession(request.getToken());
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeZone(TimeZone.getTimeZone("UTC"));
        status.setExpiryDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(gc));
        response.setLoginStatus(status);
        return response;
    }

    @PayloadRoot(namespace = VixConfig.VIX_NAMESPACE, localPart = "DocumentRequest")
    @ResponsePayload
    public DocumentResponse document(@RequestPayload DocumentRequest request) throws DatatypeConfigurationException {
        ObjectFactory f = new ObjectFactory();
        DocumentResponse response = f.createDocumentResponse();
        Document document = f.createDocument();

       Document.Fields fields = new Document.Fields();
        Document.Fields.Field field = new Document.Fields.Field();
       fields.getField().add(field);


        response.setDocument(document);
        return response;
    }

}
