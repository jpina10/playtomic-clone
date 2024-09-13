package auth.service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CacheServiceImpl implements CacheService {

    @Override
    @Cacheable(value = "tokens", key = "#email")
    public String getCachedToken(String email) {

        log.info("cache miss, adding {} and token to cache", email);
        return null;
    }

}
