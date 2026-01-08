package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.RateLimitedRefresher;

import org.locationtech.jts.geom.MultiPolygon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class GeometryCache {
    public static final Duration DEFAULT_DEBOUNCE = Duration.ofSeconds(5);
    public static final Duration DEFAULT_MAX_STALENESS = Duration.ofSeconds(30);

    private final RateLimitedRefresher<MultiPolygon> scrapRegionRefresher;
    private final RateLimitedRefresher<MultiPolygon> localBodyRefresher;
    private final RateLimitedRefresher<Void> addressRefresher;

    @Autowired
    public GeometryCache(
            CustomerAddressService customerAddressService
    ) {
        this.scrapRegionRefresher =
                new RateLimitedRefresher<>(
                        DEFAULT_DEBOUNCE,
                        DEFAULT_MAX_STALENESS,
                        customerAddressService::loadMergedScrapRegionBoundaries
                );

        this.localBodyRefresher =
                new RateLimitedRefresher<>(
                        DEFAULT_DEBOUNCE,
                        DEFAULT_MAX_STALENESS,
                        customerAddressService::loadMergedLocalBodyBoundaries
                );

        this.addressRefresher =
                new RateLimitedRefresher<>(
                        DEFAULT_DEBOUNCE,
                        DEFAULT_MAX_STALENESS,
                        () -> customerAddressService
                                .evaluateAddressesUsingBoundaryGeometry(
                                        getScrapRegionGeometry(),
                                        getLocalBodyGeometry()
                                )
                );
    }

    public void requestScrapRegionRefresh() {
        scrapRegionRefresher.requestRefresh();
    }

    public void requestLocalBodyRefresh() {
        localBodyRefresher.requestRefresh();
    }

    public void requestAddressRefresh() {
        addressRefresher.requestRefresh();
    }

    public MultiPolygon getScrapRegionGeometry() {
        return scrapRegionRefresher.get();
    }

    public MultiPolygon getLocalBodyGeometry() {
        return localBodyRefresher.get();
    }
}
