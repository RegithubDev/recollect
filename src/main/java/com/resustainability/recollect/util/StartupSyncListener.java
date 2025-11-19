package com.resustainability.recollect.util;

import com.resustainability.recollect.commons.FileUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class StartupSyncListener implements ApplicationListener<ContextRefreshedEvent> {
    private final String fileUploadPath;
    private final AtomicBoolean alreadySynced;
    private final Logger log;

    @Autowired
    public StartupSyncListener(
            @Value("${app.file.uploadPath}") String fileUploadPath
    ) {
        this.fileUploadPath = fileUploadPath;
        this.alreadySynced = new AtomicBoolean(false);
        this.log = LoggerFactory.getLogger(StartupSyncListener.class);
    }

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        if (alreadySynced.compareAndSet(false, true)) {
            FileUtils.ensureDirectory(
                    Path.of(fileUploadPath)
            );
        }
    }
}
