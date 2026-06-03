package com.dashboard.backend.repository;

import com.dashboard.backend.entity.Workspace;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceRepository extends JpaRepository<Workspace, Integer> {
	
	List<Workspace> findByOwnerId(Long ownerId);
	
}