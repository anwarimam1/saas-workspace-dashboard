package com.dashboard.backend.ai.service;
import java.time.LocalDateTime;
import java.time.Duration;

import com.dashboard.backend.ai.dto.AIInsightsResponse;
import com.dashboard.backend.ai.dto.WorkspaceAnalyticsData;
import com.dashboard.backend.ai.prompt.PromptBuilder;
import com.dashboard.backend.ai.provider.AIProviderService;
import org.springframework.stereotype.Service;

@Service
public class AIInsightsServiceImpl implements AIInsightsService {
	
	private String cachedInsights;
	private LocalDateTime lastGeneratedTime;
	private static final long CACHE_DURATION_MINUTES = 5;

    private final AdminAnalyticsService adminAnalyticsService;

    private final PromptBuilder promptBuilder;

    private final AIProviderService aiProviderService;

    public AIInsightsServiceImpl(
            AdminAnalyticsService adminAnalyticsService,
            PromptBuilder promptBuilder,
            AIProviderService aiProviderService) {

        this.adminAnalyticsService = adminAnalyticsService;
        this.promptBuilder = promptBuilder;
        this.aiProviderService = aiProviderService;
    }

    @Override
    public AIInsightsResponse generateWorkspaceInsights() {

        if (cachedInsights != null && lastGeneratedTime != null) {
            long minutesElapsed =
                    Duration.between(lastGeneratedTime, LocalDateTime.now()).toMinutes();

            if (minutesElapsed < CACHE_DURATION_MINUTES) {
                return new AIInsightsResponse(cachedInsights);
            }
        }

        WorkspaceAnalyticsData analyticsData =
                adminAnalyticsService.collectWorkspaceAnalytics();

        String prompt =
                promptBuilder.buildWorkspaceInsightsPrompt(analyticsData);

        String aiResponse =
                aiProviderService.generateInsights(prompt);

        cachedInsights = aiResponse;
        lastGeneratedTime = LocalDateTime.now();

        return new AIInsightsResponse(aiResponse);
    }

	@Override
	public void clearInsightsCache() {
		 cachedInsights = null;
		 lastGeneratedTime = null;
		
	}
}