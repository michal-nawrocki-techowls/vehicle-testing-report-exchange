package wsdl.vix;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.util.Date;
import java.util.GregorianCalendar;

@Endpoint
public class VixEndpoint {

    @PayloadRoot(namespace = "http://wsdl/vix", localPart = "LoginRequest")
    @ResponsePayload
    public LoginResponse login(@RequestPayload LoginRequest request) {
        ObjectFactory f = new ObjectFactory();
        LoginResponse response = f.createLoginResponse();
        LoginStatus status = f.createLoginStatus();
        status.setStatus(Status.OK);
        status.setSession(request.getToken());
//        status.setExpires();
        response.setLoginStatus(status);
        return response;
    }

    @PayloadRoot(namespace = "http://wsdl/vix", localPart = "DocumentRequest")
    @ResponsePayload
    public DocumentResponse document(@RequestPayload DocumentRequest request) throws DatatypeConfigurationException {
        ObjectFactory f = new ObjectFactory();
        DocumentResponse response = f.createDocumentResponse();
        Document document = f.createDocument();
        Declaration declaration = f.createDeclaration();
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(new Date());
        declaration.setDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gc));
        declaration.setSafeAndRoadworthyCondition(true);
        declaration.setMadeByName("Michal Nawrocki");
        declaration.setMadeByPosition("Driver");
        Defect d = new Defect();
        d.setType(DefectType.DANGEROUS);
        d.setDescription("Flat tire");
        document.getDefectList().add(d);
        document.setDeclaration(declaration);
        response.setDocument(document);
        return response;
    }

}
