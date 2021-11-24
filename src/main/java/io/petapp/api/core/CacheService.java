/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.core;

import io.petapp.api.core.security.Permission;
import io.petapp.security.SecurityUtils;
import io.petapp.utils.encyption.TravisRsa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author truongtran
 */
@Service
public class CacheService {

    @Autowired
    private CacheManager cacheManager;

    public TravisRsa getTravisRsa() {
        return Objects.requireNonNull(cacheManager.getCache(Constants.Cache.RSA_BY_USER))
            .get(SecurityUtils.getCurrentUserLogin().orElse(""), TravisRsa.class);
    }

    public void setTravisRsa(TravisRsa travisRsa) {
        Objects.requireNonNull(cacheManager.getCache(Constants.Cache.RSA_BY_USER))
            .put(SecurityUtils.getCurrentUserLogin().orElse(""), travisRsa);
    }

    public List<Permission> getPermissions() {
        return Objects.requireNonNull(cacheManager.getCache(Constants.Cache.PERMISSIONS_BY_USER))
            .get(SecurityUtils.getCurrentUserLogin().orElse(""), List.class);
    }

    public void setPermissions(List<Permission> permissions) {
        Objects.requireNonNull(cacheManager.getCache(Constants.Cache.PERMISSIONS_BY_USER))
            .put(SecurityUtils.getCurrentUserLogin().orElse(""), permissions);
    }

}
