package com.dashboard.backend.ai.controller;

import com.dashboard.backend.ai.dto.AIInsightsResponse;
import com.dashboard.backend.ai.service.AIInsightsService;
import com.dashboard.backend.dto.ResponseStructure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/insights")
public class AIInsightsController {

    private final AIInsightsService aiInsightsService;

    public AIInsightsController(
            AIInsightsService aiInsightsService) {

        this.aiInsightsService = aiInsightsService;
    }

    @GetMapping("/workspaces")
    public ResponseEntity<ResponseStructure<AIInsightsResponse>>
    generateWorkspaceInsights() {

        AIInsightsResponse response =
                aiInsightsService.generateWorkspaceInsights();

        ResponseStructure<AIInsightsResponse> structure =
                new ResponseStructure<>();

        structure.setStatusCode(HttpStatus.OK.value());
        structure.setMessage("AI insights generated successfully");
        structure.setData(response);

        return new ResponseEntity<>(
                structure,
                HttpStatus.OK
        );
    }
}