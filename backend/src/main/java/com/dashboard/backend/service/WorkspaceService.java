package com.dashboard.backend.service;
import com.dashboard.backend.ai.service.AIInsightsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dashboard.backend.dao.WorkspaceDao;
import com.dashboard.backend.dto.ResponseStructure;
import com.dashboard.backend.entity.User;
import com.dashboard.backend.entity.Workspace;
import com.dashboard.backend.repository.UserRepository;

@Service
public class WorkspaceService {

    @Autowired
    private WorkspaceDao workspaceDao;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AIInsightsService aiInsightsService;

    public ResponseEntity<ResponseStructure<Workspace>> saveWorkspace(
            Workspace workspace,
            Authentication auth) {

        // ✅ Logged-in user email from JWT
        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✅ Assign owner automatically
        workspace.setOwner(user);

        Workspace savedWorkspace = workspaceDao.saveWorkspace(workspace);
        aiInsightsService.clearInsightsCache();

        ResponseStructure<Workspace> response =
                new ResponseStructure<>();

        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Workspace created successfully");
        response.setData(savedWorkspace);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ✅ GET ALL WORKSPACES (ROLE BASED)
    public ResponseEntity<ResponseStructure<List<Map<String, Object>>>> getAllWorkspaces(
            Authentication auth) {

        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Workspace> workspaces;

        // ✅ ADMIN → SEE ALL WORKSPACES
        if (user.getRole().name().contains("ADMIN")) {

            workspaces = workspaceDao.getAllWorkspaces();

            System.out.println("ADMIN ACCESS");
            System.out.println("Logged in user: " + email);

        } else {

            // ✅ USER → SEE ONLY THEIR WORKSPACES
            workspaces = workspaceDao.getWorkspacesByOwnerId(user.getId());

            System.out.println("USER ACCESS");
            System.out.println("Logged in user: " + email);
        }

        // ✅ CUSTOM RESPONSE TO AVOID RECURSION
        List<Map<String, Object>> workspaceResponse = workspaces.stream().map(ws -> {

            Map<String, Object> map = new HashMap<>();

            map.put("id", ws.getId());
            map.put("name", ws.getName());

            // ✅ OWNER DETAILS
            if (ws.getOwner() != null) {

                map.put("ownerId", ws.getOwner().getId());
                map.put("ownerName", ws.getOwner().getName());
                map.put("ownerEmail", ws.getOwner().getEmail());
            }

            return map;

        }).toList();

        ResponseStructure<List<Map<String, Object>>> response =
                new ResponseStructure<>();

        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Workspaces retrieved successfully");
        response.setData(workspaceResponse);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // ✅ DELETE WORKSPACE
    public ResponseEntity<ResponseStructure<String>> deleteWorkspace(Integer id) {

        workspaceDao.deleteWorkspace(id);
        aiInsightsService.clearInsightsCache();

        ResponseStructure<String> response = new ResponseStructure<>();

        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Workspace deleted successfully");
        response.setData("Deleted");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // ✅ UPDATE WORKSPACE
    @Transactional
    public ResponseEntity<ResponseStructure<Workspace>> updateWorkspace(
            Integer id,
            Workspace workspace) {

        Workspace existingWorkspace = workspaceDao.findWorkspaceById(id);

        if (existingWorkspace == null) {
            throw new RuntimeException("Workspace not found");
        }

        // ✅ UPDATE NAME
        if (workspace.getName() != null &&
                !workspace.getName().trim().isEmpty()) {

            existingWorkspace.setName(workspace.getName());
        }

        // ✅ UPDATE OWNER
        if (workspace.getOwner() != null) {

            existingWorkspace.setOwner(workspace.getOwner());
        }

        Workspace updatedWorkspace =
                workspaceDao.saveWorkspace(existingWorkspace);
        aiInsightsService.clearInsightsCache();

        ResponseStructure<Workspace> response =
                new ResponseStructure<>();

        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Workspace updated successfully");
        response.setData(updatedWorkspace);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}