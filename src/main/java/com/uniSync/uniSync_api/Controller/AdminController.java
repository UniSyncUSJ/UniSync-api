package com.uniSync.uniSync_api.Controller;

import com.uniSync.uniSync_api.DTO.EventPatchRequest;
import com.uniSync.uniSync_api.Model.Admin;
import com.uniSync.uniSync_api.Model.Event;
import com.uniSync.uniSync_api.Service.AdminService;
import com.uniSync.uniSync_api.Service.EventServiceImpl;
import com.uniSync.uniSync_api.config.JwtUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private EventServiceImpl eventServiceImpl;

    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        return ResponseEntity.ok(adminService.createAdmin(admin));
    }

    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        return adminService.getAdminById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdminById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/api/admin/events/{id}")
    public ResponseEntity<Event> patchEvent(
            @PathVariable Long id,
            @RequestBody EventPatchRequest request,
            @AuthenticationPrincipal JwtUserDetails userDetails
    ) {
        Event updated = eventServiceImpl.patchEventByAdmin(id, request, userDetails);
        return ResponseEntity.ok(updated);
    }


}

