package com.food.sas.security.admin;

import com.food.sas.data.dto.AdministratorDTO;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author Created by ygdxd_admin at 2019-11-09 3:31 PM
 */
@Configuration
public class AdministratorTokenStore {

    @Bean
    public LoadingCache<String, AdministratorDTO> adminTokenCache() {
        return CacheBuilder.newBuilder()
                .maximumSize(1000)
                .refreshAfterWrite(1, TimeUnit.MINUTES)
                .expireAfterWrite(5L, TimeUnit.MINUTES)
                .build(
                        new CacheLoader<String, AdministratorDTO>() {

                            @Override
                            public AdministratorDTO load(String key) throws Exception {
                                return getDefault(key);
                            }
                        }
                );
    }

    private AdministratorDTO getDefault(String key) {
        return new AdministratorDTO();
    }
}
