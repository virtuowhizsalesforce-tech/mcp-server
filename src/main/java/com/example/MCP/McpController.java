package com.example.MCP;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/mcp")
public class McpController {

    private final WeatherService weatherService;
    private final SalesforceService salesforceService;
    private final Permissionsetassign permissionsetassign;

    
    public McpController(WeatherService weatherService,
                         SalesforceService salesforceService,
                         Permissionsetassign permissionsetassign) {
        this.weatherService = weatherService;
        this.salesforceService = salesforceService;
        this.permissionsetassign = permissionsetassign;
    }

   
    @GetMapping("/hello")
    public String hello(@RequestParam String name) {
        return "Hello " + name + ", server is working!";
    }

    
    @GetMapping("/weather")
    public String getWeather(@RequestParam String city) {
        return weatherService.getWeather(city);
    }

    
    @PostMapping("/salesforce")
    public String callSalesforce(@RequestBody Map<String, String> body) {
        return salesforceService.process(body);
    }


    @PostMapping("/permission")
    public String assignPermission(@RequestBody Map<String, String> body) {
        return permissionsetassign.assign(body);
    }
}