package com.dashboard.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.dashboard.backend.dto.ResponseStructure;
import com.dashboard.backend.entity.User;
import com.dashboard.backend.entity.Workspace;
import com.dashboard.backend.service.WorkspaceService;

@RestController
@RequestMapping("/api/workspaces")
@CrossOrigin(origins = "http://localhost:5173")
public class WorkspaceController {

    @Autowired
    private WorkspaceService workspaceService;

    @PostMapping
    public ResponseEntity<ResponseStructure<Workspace>> saveWorkspace(
            @RequestBody Workspace workspace,
            Authentication auth) {

        return workspaceService.saveWorkspace(workspace, auth);
    }

    @GetMapping
    public ResponseEntity<?> getAllWorkspaces(Authentication auth) {
        return workspaceService.getAllWorkspaces(auth);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> deleteWorkspace(@PathVariable Integer id) {
        return workspaceService.deleteWorkspace(id);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ResponseStructure<Workspace>> updateWorkspace(
            @PathVariable Integer id,
            @RequestBody Workspace workspace) {

        return workspaceService.updateWorkspace(id, workspace);
    }
}