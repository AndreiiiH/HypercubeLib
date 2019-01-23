package andreiiih.hypercubelib.util;

import andreiiih.hypercubelib.HypercubeLib;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FileUtils {

    public static File getLibDir() {
        File libDir = new File(HypercubeLib.configDir, "hypercubelib");

        return mkdirIfNotExist(libDir);
    }

    public static File getConfigDir() {
        File configDir = new File(getLibDir(), "config");

        return mkdirIfNotExist(configDir);
    }

    public static File getCacheDir() {
        File cacheDir = new File(getLibDir(), "cache");

        return mkdirIfNotExist(cacheDir);
    }

    public static File getModCacheDir(String modId) {
        File modCacheDir = new File(getCacheDir(), modId);

        return mkdirIfNotExist(modCacheDir);
    }

    public static byte[] readFile(Path file) throws IOException {
        return Files.readAllBytes(file);
    }

    public static void writeFile(Path file, byte[] data) throws IOException {
        Files.write(file, data);
    }

    protected static File mkdirIfNotExist(File dir) {
        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdir();
        }

        return dir;
    }
}
