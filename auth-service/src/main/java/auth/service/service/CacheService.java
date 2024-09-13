package auth.service.service;

public interface CacheService {

    String getCachedToken(String email);
    void removeCachedToken(String email);
}
