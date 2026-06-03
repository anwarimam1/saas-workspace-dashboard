package com.dashboard.backend.ai.dto;

public class AIInsightsResponse {

    private String insights;

    public AIInsightsResponse() {
    }

    public AIInsightsResponse(String insights) {
        this.insights = insights;
    }

    public String getInsights() {
        return insights;
    }

    public void setInsights(String insights) {
        this.insights = insights;
    }
}