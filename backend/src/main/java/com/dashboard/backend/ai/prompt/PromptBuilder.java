package com.dashboard.backend.ai.prompt;

import com.dashboard.backend.ai.dto.WorkspaceAnalyticsData;
import org.springframework.stereotype.Component;

@Component
public class PromptBuilder {

    public String buildWorkspaceInsightsPrompt(WorkspaceAnalyticsData data) {
        return """
            You are an AI analytics assistant for a SaaS Workspace Analytics Dashboard.

            Your task is to analyze the provided dashboard metrics and generate concise, professional admin insights.

            RULES:
            1. Use ONLY the provided analytics data.
            2. Do NOT invent metrics, percentages, or trends not present in the data.
            3. If data is limited, explicitly mention that observations are based on limited analytics.
            4. Focus on meaningful operational insights for an admin dashboard.
            5. Keep the response concise, professional, and actionable.
            6. Return ONLY the exact structure below.

            REQUIRED OUTPUT FORMAT:

            EXECUTIVE SUMMARY:
            <2 concise sentences>

            KEY OBSERVATIONS:
            1. <important observation from metrics>
            2. <important observation from metrics>
            3. <important observation or note limited data>

            ACTIONABLE RECOMMENDATIONS:
            1. <practical dashboard/product recommendation>
            2. <practical dashboard/product recommendation>
            3. <practical dashboard/product recommendation>

            ANALYTICS DATA:
            Total Users: %d
            Total Workspaces: %d
            Workspaces Per User: %s
            Recent Workspace Names: %s
            """.formatted(
                data.getTotalUsers(),
                data.getTotalWorkspaces(),
                data.getWorkspacesPerUser(),
                data.getRecentWorkspaceNames()
        );
    }
}