package com.dashboard.backend.ai.dto;

import java.util.List;
import java.util.Map;

public class WorkspaceAnalyticsData {

    private Long totalUsers;

    private Long totalWorkspaces;

    private Map<String, Long> workspacesPerUser;

    private List<String> recentWorkspaceNames;

    public WorkspaceAnalyticsData() {
    }

    public WorkspaceAnalyticsData(
            Long totalUsers,
            Long totalWorkspaces,
            Map<String, Long> workspacesPerUser,
            List<String> recentWorkspaceNames) {

        this.totalUsers = totalUsers;
        this.totalWorkspaces = totalWorkspaces;
        this.workspacesPerUser = workspacesPerUser;
        this.recentWorkspaceNames = recentWorkspaceNames;
    }

    public Long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(Long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public Long getTotalWorkspaces() {
        return totalWorkspaces;
    }

    public void setTotalWorkspaces(Long totalWorkspaces) {
        this.totalWorkspaces = totalWorkspaces;
    }

    public Map<String, Long> getWorkspacesPerUser() {
        return workspacesPerUser;
    }

    public void setWorkspacesPerUser(Map<String, Long> workspacesPerUser) {
        this.workspacesPerUser = workspacesPerUser;
    }

    public List<String> getRecentWorkspaceNames() {
        return recentWorkspaceNames;
    }

    public void setRecentWorkspaceNames(List<String> recentWorkspaceNames) {
        this.recentWorkspaceNames = recentWorkspaceNames;
    }
}