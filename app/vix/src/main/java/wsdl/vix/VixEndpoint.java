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

    @PayloadRoot(namespace = VixConfig.VIX_NAMESPACE, localPart = "DocumentRequest")
    @ResponsePayload
    public DocumentResponse document(@RequestPayload DocumentRequest request) throws DatatypeConfigurationException {
        ObjectFactory f = new ObjectFactory();
        DocumentResponse response = f.createDocumentResponse();
        SafetyInspection ext = f.createSafetyInspection();
        DocumentListResponse resp = new DocumentListResponse();
        ext.setTitle("Hello");
        ext.setAllSectionsAreAnswered(true);
        response.setDocument(ext);
        return response;
    }

}
