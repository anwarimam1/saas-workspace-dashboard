package com.dashboard.backend.ai.service;

import com.dashboard.backend.ai.dto.AIInsightsResponse;

public interface AIInsightsService {

    AIInsightsResponse generateWorkspaceInsights();
    
    void clearInsightsCache();
}