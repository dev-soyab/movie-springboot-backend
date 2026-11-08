package com.project.movie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/user/dashboard")
    public String userDashboard() {
        return "user_dashboard"; // Return view name for user dashboard
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin_dashboard"; // Return view name for admin dashboard
    }
}
