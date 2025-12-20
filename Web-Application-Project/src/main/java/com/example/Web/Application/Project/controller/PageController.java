package com.example.Web.Application.Project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String root() {
        return "forward:/auth/login.html";
    }

    @GetMapping("/auth/login")
    public String login() {
        return "forward:/auth/login.html";
    }

    @GetMapping("/auth/register")
    public String register() {
        return "forward:/auth/register.html";
    }

    @GetMapping("/game")
    public String game() {
        return "forward:/game.html";
    }

    @GetMapping("/leaderboard")
    public String leaderboard() {
        return "forward:/leaderboard.html";
    }

    @GetMapping("/admin")
    public String adminDashboard() {
        return "forward:/admin/adminDashboard.html";
    }
    
    @GetMapping("/profile")
    public String profile() {
        return "forward:/profile.html";
    }

    @GetMapping("/admin/users")
    public String adminUsers() {
        return "forward:/admin/admin-users.html";
    }
    @GetMapping("/admin/categories")
    public String adminCategories() {
        return "forward:/admin/admin-categories.html";
    } 
    @GetMapping("/admin/words")
    public String adminWords() {
    return "forward:/admin/admin-words.html";
    } 
    @GetMapping("/admin/achievements")
    public String adminAchievements() {
    return "forward:/admin/admin-achievements.html";
    }
    @GetMapping("/admin/sessions")
    public String adminSessions() {
    return "forward:/admin/admin-sessions.html";
    }
}