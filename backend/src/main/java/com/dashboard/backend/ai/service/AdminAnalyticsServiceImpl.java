package com.dashboard.backend.ai.service;

import com.dashboard.backend.ai.dto.WorkspaceAnalyticsData;
import com.dashboard.backend.entity.User;
import com.dashboard.backend.entity.Workspace;
import com.dashboard.backend.repository.UserRepository;
import com.dashboard.backend.repository.WorkspaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminAnalyticsServiceImpl implements AdminAnalyticsService {
	
	private final UserRepository userRepository;
    private final WorkspaceRepository workspaceRepository;
    
	public AdminAnalyticsServiceImpl(
	        UserRepository userRepository,
	        WorkspaceRepository workspaceRepository) {

	    this.userRepository = userRepository;
	    this.workspaceRepository = workspaceRepository;
	}

    @Override
    public WorkspaceAnalyticsData collectWorkspaceAnalytics() {

        Long totalUsers = userRepository.count();

        Long totalWorkspaces = workspaceRepository.count();

        List<Workspace> workspaces = workspaceRepository.findAll();

        Map<String, Long> workspacesPerUser =
                workspaces.stream()
                        .collect(Collectors.groupingBy(
                                workspace -> workspace.getOwner().getEmail(),
                                Collectors.counting()
                        ));

        List<String> recentWorkspaceNames =
                workspaces.stream()
                        .limit(5)
                        .map(Workspace::getName)
                        .collect(Collectors.toList());

        return new WorkspaceAnalyticsData(
                totalUsers,
                totalWorkspaces,
                workspacesPerUser,
                recentWorkspaceNames
        );
    }
}