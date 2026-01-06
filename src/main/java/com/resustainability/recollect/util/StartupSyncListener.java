package com.resustainability.recollect.util;

import com.resustainability.recollect.commons.FileUtils;
import com.resustainability.recollect.service.GeometryCache;
import com.resustainability.recollect.service.LocalBodyService;
import com.resustainability.recollect.service.ScrapRegionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class StartupSyncListener implements ApplicationListener<ContextRefreshedEvent> {
    private final String fileUploadPath;
    private final AtomicBoolean alreadySynced;
    private final Logger log;

    private final ScrapRegionService scrapRegionService;
    private final LocalBodyService localBodyService;
    private final GeometryCache geometryCache;

    @Autowired
    public StartupSyncListener(
            @Value("${app.file.uploadPath}") String fileUploadPath,
            ScrapRegionService scrapRegionService,
            LocalBodyService localBodyService,
            GeometryCache geometryCache
    ) {
        this.fileUploadPath = fileUploadPath;
        this.scrapRegionService = scrapRegionService;
        this.localBodyService = localBodyService;
        this.geometryCache = geometryCache;
        this.alreadySynced = new AtomicBoolean(false);
        this.log = LoggerFactory.getLogger(StartupSyncListener.class);
    }

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        if (alreadySynced.compareAndSet(false, true)) {
            FileUtils.ensureDirectory(
                    Paths.get(fileUploadPath)
            );

            scrapRegionService.normalizeAllToGeometry();
            localBodyService.normalizeAllToGeometry();

            geometryCache.requestScrapRegionRefresh();
            geometryCache.requestLocalBodyRefresh();
        }
    }
}
