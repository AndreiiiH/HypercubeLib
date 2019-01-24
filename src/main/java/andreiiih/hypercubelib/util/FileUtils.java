/**
 * ATTRIBUTION-NONCOMMERCIAL-NODERIVATIVES 4.0 International (CC BY-NC-ND 4.0)
 * https://creativecommons.org/licenses/by-nc-nd/4.0/legalcode
 *
 * You CAN:
 *     Share           Copy and redistribute the material in any medium or format.
 *                     The licensor cannot revoke these freedoms as long as you follow the license terms.
 *
 * You MUST:
 *     Attribution     You must give appropriate credit, provide a link to the license, and indicate if changes were made.
 *                     You may do so in any reasonable manner, but not in any way that suggests the licensor
 *                     endorses you or your use.
 *
 * You CAN'T:
 *     NonCommercial   You may not use the material for commercial purposes.
 *
 *     NoDerivatives   If you remix, transform, or build upon the material, you may not distribute the modified material.
 *
 *     No additional   You may not apply legal terms or technological measures that legally restrict others
 *     restrictions    from doing anything the license permits.
 *
 * Notices:
 *     You do not have to comply with the license for elements of the material in the public domain or where your use
 *     is permitted by an applicable exception or limitation.
 *
 *     No warranties are given. The license may not give you all of the permissions necessary for your intended use.
 *     For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 *
 *
 * Copyright (c) 2018-2019 AndreiiiH <hava.ionut@gmail.com>
 */
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
