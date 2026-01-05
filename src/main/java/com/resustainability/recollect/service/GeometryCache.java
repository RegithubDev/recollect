package com.resustainability.recollect.service;

import com.resustainability.recollect.repository.LocalBodyRepository;
import com.resustainability.recollect.repository.ScrapRegionRepository;
import com.resustainability.recollect.util.GeometryNormalizer;

import org.locationtech.jts.geom.MultiPolygon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class GeometryCache {
    private final ScrapRegionRepository scrapRegionRepository;
    private final LocalBodyRepository localBodyRepository;
    private final GeometryNormalizer geometryNormalizer;

    // Thread-safe visibility
    private volatile MultiPolygon scrapRegionGeometry;
    private volatile MultiPolygon localBodyGeometry;

    @Autowired
    public GeometryCache(
            ScrapRegionRepository scrapRegionRepository,
            LocalBodyRepository localBodyRepository,
            GeometryNormalizer geometryNormalizer
    ) {
        this.scrapRegionRepository = scrapRegionRepository;
        this.localBodyRepository = localBodyRepository;
        this.geometryNormalizer = geometryNormalizer;
    }

    /**
     * Rebuild merged geometry from DB
     */
    @Transactional(readOnly = true)
    public void refreshScrapRegion() {
        try (var stream = scrapRegionRepository.streamAllActiveGeometries()) {
            List<MultiPolygon> polygons = stream
                    .filter(Objects::nonNull)
                    .map(geometryNormalizer::toMultiPolygon)
                    .toList();
            this.scrapRegionGeometry = geometryNormalizer.merge(polygons);
        }
    }

    @Transactional(readOnly = true)
    public void refreshLocalBody() {
        try (var stream = localBodyRepository.streamAllActiveGeometries()) {
            List<MultiPolygon> polygons = stream
                    .filter(Objects::nonNull)
                    .map(geometryNormalizer::toMultiPolygon)
                    .toList();
            this.localBodyGeometry = geometryNormalizer.merge(polygons);
        }
    }

    /**
     * Read-only access
     */
    public MultiPolygon getScrapRegionGeometry() {
        if (scrapRegionGeometry == null) {
            throw new IllegalStateException("Scrap region geometry not initialized");
        }
        return scrapRegionGeometry;
    }

    public MultiPolygon getLocalBodyGeometry() {
        if (localBodyGeometry == null) {
            throw new IllegalStateException("Local body geometry not initialized");
        }
        return localBodyGeometry;
    }
}
