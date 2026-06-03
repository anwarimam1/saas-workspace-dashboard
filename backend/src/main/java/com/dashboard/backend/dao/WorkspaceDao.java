package com.dashboard.backend.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dashboard.backend.entity.User;
import com.dashboard.backend.entity.Workspace;
import com.dashboard.backend.repository.WorkspaceRepository;

@Repository
public class WorkspaceDao {

    @Autowired
    private WorkspaceRepository workspaceRepository;

    public Workspace saveWorkspace(Workspace workspace) {
        return workspaceRepository.save(workspace);
    }

    public List<Workspace> getAllWorkspaces() {
        return workspaceRepository.findAll();
    }
    
    public void deleteWorkspace(Integer id) {
        workspaceRepository.deleteById(id);
    }
    
    public Workspace findWorkspaceById(Integer id) {
	    return workspaceRepository.findById(id).orElse(null);
	}
    
    public List<Workspace> getWorkspacesByOwnerId(Long long1) {
        return workspaceRepository.findByOwnerId(long1);
    }
    
}
