package com.uniSync.uniSync_api.Controller;

import com.uniSync.uniSync_api.Model.Society;
import com.uniSync.uniSync_api.Service.SocietyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/societies")
public class SocietyController {

    @Autowired
    private SocietyService societyService;

    @PostMapping
    public ResponseEntity<Society> createSociety(@RequestBody Society society) {
        return ResponseEntity.ok(societyService.createSociety(society));
    }

    @GetMapping
    public ResponseEntity<List<Society>> getAllSocieties() {
        return ResponseEntity.ok(societyService.getAllSocieties());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Society> getSocietyById(@PathVariable Long id) {
        return societyService.getSocietyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSociety(@PathVariable Long id) {
        societyService.deleteSociety(id);
        return ResponseEntity.noContent().build();
    }
}

