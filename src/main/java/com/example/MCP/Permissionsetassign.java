package com.example.MCP;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class Permissionsetassign {

    private final RestClient restClient;
    private final SalesforceAuthService authService;

    public Permissionsetassign(SalesforceAuthService authService) {
        this.authService = authService;
        this.restClient = RestClient.create();
    }

    // ✅ Called from Controller
    public String assign(Map<String, String> body) {
        String username = body.get("username");
        String permissionSetName = body.get("permissionSetName");

        return assignPermissionSetByName(username, permissionSetName);
    }

    public String assignPermissionSetByName(String username, String permissionSetName) {

        try {
            String accessToken = authService.getAccessToken();
            String instanceUrl = authService.getInstanceUrl();

            // Simplified success response (you can expand later)
            return "✅ Assigning PermissionSet '" + permissionSetName + "' to " + username;

        } catch (Exception e) {
            return "❌ Error: " + e.getMessage();
        }
    }
}