package auth.service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {

    private final CacheManager cacheManager;

    @Override
    @Cacheable(value = "tokens", key = "#email")
    public String getCachedToken(String email) {

        log.info("cache miss, adding {} and token to cache", email);
        return null;
    }

    @Override
    public void removeCachedToken(String email) {
        Objects.requireNonNull(cacheManager.getCache("tokens")).evict(email);
    }

}
