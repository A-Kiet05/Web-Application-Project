package com.example.Web.Application.Project.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;


public class EnvLoader {
    public static final Dotenv dotenv = Dotenv.configure().directory("C:/Users/ACER/Desktop/Web-Application-Project/Web-Application-Project")
                      .load();
}