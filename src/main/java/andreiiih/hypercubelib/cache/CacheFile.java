package andreiiih.hypercubelib.cache;

import andreiiih.hypercubelib.HypercubeLibConstants;
import andreiiih.hypercubelib.util.FileUtils;

import java.io.File;
import java.nio.file.Path;

public final class CacheFile {

    private final String fileName;
    private final Path filePath;

    public CacheFile(String fileName) {
        this.fileName = fileName;
        this.filePath = new File(FileUtils.getModCacheDir(HypercubeLibConstants.getModId()),
                fileName + HypercubeLibConstants.CACHE_FILE_EXT).toPath();
    }
}
