package com.example.portfolio.controller;

import com.example.portfolio.model.AlertRule;
import com.example.portfolio.service.AlertRuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertRuleController {

    private final AlertRuleService alertService;

    public AlertRuleController(AlertRuleService alertService) {
        this.alertService = alertService;
    }

    @PostMapping
    public ResponseEntity<AlertRule> create(@RequestBody AlertRule rule) {
        return ResponseEntity.ok(alertService.create(rule));
    }

    @GetMapping
    public ResponseEntity<List<AlertRule>> getAll() {
        return ResponseEntity.ok(alertService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alertService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
