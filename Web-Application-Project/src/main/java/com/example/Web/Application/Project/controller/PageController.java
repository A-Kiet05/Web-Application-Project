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

    @GetMapping("/auth/game")
    public String game() {
        return "forward:/auth/game.html";
    }

    @GetMapping("/auth/leaderboard")
    public String leaderboard() {
        return "forward:/auth/leaderboard.html";
    }

    @GetMapping("/auth/admin")
    public String adminDashboard() {
        return "forward:/auth/admin/dashboard.html";
    }
}
