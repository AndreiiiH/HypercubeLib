package andreiiih.hypercubelib.cache;

public abstract class CacheItem {

    private final CacheFile file;

    public CacheItem(String name) {
        file = new CacheFile(name);
    }
}
