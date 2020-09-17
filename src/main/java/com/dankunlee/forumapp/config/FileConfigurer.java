package com.dankunlee.forumapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration  // v provides easy access to defined properties
@ConfigurationProperties(prefix = "file") // file from application.xml
public class FileConfigurer {
    private String path; // path under file from application.xml

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
