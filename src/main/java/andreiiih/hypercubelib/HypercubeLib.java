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
package andreiiih.hypercubelib;

import andreiiih.hypercubelib.core.IntRef;
import andreiiih.hypercubelib.serialization.ObjectTypeRegistry;
import andreiiih.hypercubelib.serialization.SDHeader;
import andreiiih.hypercubelib.util.FileUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = HypercubeLib.ID, name = HypercubeLib.NAME, version = HypercubeLib.VERSION, useMetadata = true)
public class HypercubeLib {

    public final static String ID = "hypercubelib";
    public final static String NAME = "HypercubeLib";
    public final static String VERSION = "1.0.0";

    public final static Logger LOG = LogManager.getLogger();

    public static File configDir;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        configDir = event.getModConfigurationDirectory();

        HypercubeLibConstants.setModId("hypercubelib");

        SDHeader header;

        long start = System.currentTimeMillis();
        try {
            ObjectTypeRegistry.register(SDHeader.class);
//
//            header = new SDHeader();
//            header.setDataLength(50);
//
//            byte[] serialized = new byte[header.size()];
//            header.copyToBuffer(serialized, new IntRef(0));
//
//            FileUtils.writeFile(
//                    new File(
//                            FileUtils.getModCacheDir(
//                                    HypercubeLibConstants.getModId()
//                            ), "test" + HypercubeLibConstants.CACHE_FILE_EXT).toPath()
//                    , serialized);
        } catch (Exception e) {
            LOG.error("Congrats! A unicorn!", e);
        }
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;

//        LOG.warn("Serialization finished! Time elapsed: " + timeElapsed + " ms");

        start = System.currentTimeMillis();
        try {
            byte[] data = FileUtils.readFile(new File(
                    FileUtils.getModCacheDir(
                            HypercubeLibConstants.getModId()
                    ), "test" + HypercubeLibConstants.CACHE_FILE_EXT).toPath());

            header = new SDHeader().copyFromBuffer(data, new IntRef(0));
        } catch (Exception e) {
            LOG.error("Congrats! A unicorn!", e);
        }
        finish = System.currentTimeMillis();
        timeElapsed = finish - start;

        LOG.warn("Deserialization finished! Time elapsed: " + timeElapsed + " ms");
    }
}
