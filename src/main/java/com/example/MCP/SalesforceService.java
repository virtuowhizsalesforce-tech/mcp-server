package com.example.MCP;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class SalesforceService {

    private final RestClient restClient;
    private final SalesforceAuthService authService;

    public SalesforceService(SalesforceAuthService authService) {
        this.authService = authService;
        this.restClient = RestClient.create();
    }

    public String process(Map<String, String> body) {
        String psName = body.get("psName");
        String psLabel = body.get("psLabel");

        return createPermissionSet(psName, psLabel);
    }

    public String createPermissionSet(String psName, String psLabel) {

        try {
            String accessToken = authService.getAccessToken();
            String instanceUrl = authService.getInstanceUrl();

            String body =
"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
"<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
"  <env:Header>\n" +
"    <urn:SessionHeader xmlns:urn=\"http://soap.sforce.com/2006/04/metadata\">\n" +
"      <urn:sessionId>"+accessToken+"</urn:sessionId>\n" +
"    </urn:SessionHeader>\n" +
"  </env:Header>\n" +
"  <env:Body>\n" +
"    <createMetadata xmlns=\"http://soap.sforce.com/2006/04/metadata\">\n" +
"      <metadata xsi:type=\"PermissionSet\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
"        <fullName>"+psName+"</fullName>\n" +
"        <label>"+psLabel+"</label>\n" +
"      </metadata>\n" +
"    </createMetadata>\n" +
"  </env:Body>\n" +
"</env:Envelope>";

            ResponseEntity<String> response = restClient.post()
        .uri(instanceUrl + "/services/Soap/m/64.0")
        .body(body)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_XML_VALUE)
        .header("SOAPAction", "\"\"")   
        .retrieve()
        .toEntity(String.class);

            return "✅ Permission Set Created: " + response.getBody();

        } catch (Exception e) {
            return "❌ Error: " + e.getMessage();
        }
    }
}