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
package andreiiih.hypercubelib.cache;

import andreiiih.hypercubelib.HypercubeLib;
import andreiiih.hypercubelib.HypercubeLibConstants;
import andreiiih.hypercubelib.core.IntRef;
import andreiiih.hypercubelib.serialization.SDBody;
import andreiiih.hypercubelib.serialization.SDHeader;
import andreiiih.hypercubelib.serialization.SDObject;
import andreiiih.hypercubelib.util.FileUtils;

import java.io.File;
import java.nio.file.Path;
import java.util.LinkedList;

public final class CacheFile {

    private final String fileName;
    private final Path filePath;

    private SDHeader header;
    private SDBody body;

    public CacheFile(String fileName) {
        this.fileName = fileName;
        this.filePath = new File(FileUtils.getModCacheDir(HypercubeLibConstants.getModId()),
                fileName + HypercubeLibConstants.CACHE_FILE_EXT).toPath();

        try {
            header = new SDHeader();
            body = new SDBody();
        } catch (Exception e) {
            HypercubeLib.LOG.error("Error during cache file instantiation", e);
        }
    }

    public void addData(CacheItem data) {
        body.addObject(data);
        header.setNumObjects(header.getNumObjects() + 1);
    }

    public LinkedList<CacheItem> getData() {
        LinkedList<CacheItem> converted = new LinkedList<>();

        for (SDObject obj : body.getObjects()) {
            converted.add((CacheItem)obj);
        }

        return converted;
    }

    public void save() {
        byte[] data = new byte[header.size() + body.size()];
        IntRef pointer = new IntRef(0);

        header.copyToBuffer(data, pointer);
        body.copyToBuffer(data, pointer);

        try {
            FileUtils.writeFile(filePath, data);
        } catch (Exception e) {
            HypercubeLib.LOG.error("Error during cache file saving", e);
        }
    }

    public void load() {
        try {
            byte[] data = FileUtils.readFile(filePath);
            IntRef pointer = new IntRef(0);

            header.copyFromBuffer(data, pointer);
            body.copyFromBuffer(data, pointer);
        } catch (Exception e) {
            HypercubeLib.LOG.error("Error during cache file loading", e);
        }
    }
}
