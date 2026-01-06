package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.RateLimitedRefresher;
import com.resustainability.recollect.repository.LocalBodyRepository;
import com.resustainability.recollect.repository.ScrapRegionRepository;
import com.resustainability.recollect.util.GeometryNormalizer;

import org.locationtech.jts.geom.MultiPolygon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Objects;

@Service
public class GeometryCache {
    private final RateLimitedRefresher<MultiPolygon> scrapRegionRefresher;
    private final RateLimitedRefresher<MultiPolygon> localBodyRefresher;

    @Autowired
    public GeometryCache(
            ScrapRegionRepository scrapRegionRepository,
            LocalBodyRepository localBodyRepository,
            GeometryNormalizer geometryNormalizer
    ) {
        this.scrapRegionRefresher =
                new RateLimitedRefresher<>(
                        Duration.ofSeconds(5),
                        Duration.ofSeconds(30),
                        () -> loadScrapRegion(scrapRegionRepository, geometryNormalizer)
                );

        this.localBodyRefresher =
                new RateLimitedRefresher<>(
                        Duration.ofSeconds(5),
                        Duration.ofSeconds(30),
                        () -> loadLocalBody(localBodyRepository, geometryNormalizer)
                );
    }

    @Transactional(readOnly = true)
    protected MultiPolygon loadScrapRegion(
            ScrapRegionRepository repo,
            GeometryNormalizer normalizer
    ) {
        try (var stream = repo.streamAllActiveGeometries()) {
            return normalizer.merge(
                    stream.filter(Objects::nonNull)
                            .map(normalizer::toMultiPolygon)
                            .toList()
            );
        }
    }

    @Transactional(readOnly = true)
    protected MultiPolygon loadLocalBody(
            LocalBodyRepository repo,
            GeometryNormalizer normalizer
    ) {
        try (var stream = repo.streamAllActiveGeometries()) {
            return normalizer.merge(
                    stream.filter(Objects::nonNull)
                            .map(normalizer::toMultiPolygon)
                            .toList()
            );
        }
    }

    public void requestScrapRegionRefresh() {
        scrapRegionRefresher.requestRefresh();
    }

    public void requestLocalBodyRefresh() {
        localBodyRefresher.requestRefresh();
    }

    public MultiPolygon getScrapRegionGeometry() {
        return scrapRegionRefresher.get();
    }

    public MultiPolygon getLocalBodyGeometry() {
        return localBodyRefresher.get();
    }
}
