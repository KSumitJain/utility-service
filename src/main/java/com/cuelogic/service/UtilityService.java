package com.cuelogic.service;

import com.cuelogic.component.UtilityServiceComponent;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class UtilityService {
    @Value("${video.directory.path}")
    private String videoDirectoryPath;

    private final UtilityServiceComponent utilityServiceComponent;

    @PostConstruct
    public void startFileMonitoring() {
        Path directory = Paths.get(videoDirectoryPath);
        utilityServiceComponent.watchDirectory(directory);
    }
}
