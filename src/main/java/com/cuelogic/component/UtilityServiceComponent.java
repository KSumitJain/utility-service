package com.cuelogic.component;

import com.cuelogic.feignclient.FeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

@Component
@Log4j2
@RequiredArgsConstructor
public class UtilityServiceComponent {
    private final FeignClient feignClient;
    public void watchDirectory(Path directory) {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            directory.register(watchService, ENTRY_CREATE);

            while (true) {
                WatchKey key;
                try {
                    key = watchService.take();
                } catch (InterruptedException e) {
                    return;
                }

                for (WatchEvent<?> event : key.pollEvents()) {
                    if (event.kind() == ENTRY_CREATE) {
                        Path newFile = (Path) event.context();
                        String[] extensions = {".mp4", ".mov", ".avi", ".mkv", ".webm", ".flv"};
                        log.info(newFile.getFileName());
                        if (Arrays.stream(extensions).anyMatch(s -> newFile.getFileName().toString().endsWith(s))) {
                            log.info("New video file added " + newFile.toFile().getAbsolutePath());
                            feignClient.imageProcessing(newFile.toFile().getAbsolutePath());
                        }
                    }
                }
                key.reset();
            }
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }
}
