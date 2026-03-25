package com.example.MCP;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class WeatherService {

   
    public String getWeather(String city) {
        return "Weather in " + city + " is Sunny (Demo)";
    }

   
    public String createLead(Map<String, String> body) {

        String firstName = body.get("firstName");
        String lastName = body.get("lastName");
        String email = body.get("email");
        String company = body.get("company");

        String accessToken = "YOUR_ACCESS_TOKEN"; // ⚠️ replace later
        String instanceUrl = "https://ne1744285445077.my.salesforce.com";

        try {
            String leadUrl = instanceUrl + "/services/data/v57.0/sobjects/Lead/";

            Map<String, String> lead = new HashMap<>();
            lead.put("FirstName", firstName);
            lead.put("LastName", lastName);
            lead.put("Email", email);
            lead.put("Company", company);

            String response = RestClient.create()
                    .post()
                    .uri(leadUrl)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(lead)
                    .retrieve()
                    .body(String.class);

            return "✅ Lead created: " + response;

        } catch (Exception e) {
            return "❌ Error: " + e.getMessage();
        }
    }
}