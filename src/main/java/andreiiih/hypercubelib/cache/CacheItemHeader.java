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

import andreiiih.hypercubelib.HypercubeLibConstants;
import andreiiih.hypercubelib.exceptions.UnknownModIdException;
import andreiiih.hypercubelib.exceptions.UnsupportedIdException;
import andreiiih.hypercubelib.serialization.Serializer;

import java.util.Arrays;

public final class CacheItemHeader {

//    private byte[] fileId = HypercubeLibConstants.CACHE_FILE_ID;
//    private byte type;
//    private short version;
//    private String modId = HypercubeLibConstants.getModId();
//
//    public CacheItemHeader(byte type, short version) {
//        this.type = type;
//        this.version = version;
//    }
//
//    public int serialize(byte[] data, int offset) {
//        offset = Serializer.writeData(data, 0, fileId);
//        offset = Serializer.writeData(data, offset, type);
//        offset = Serializer.writeData(data, offset, version);
//        offset = Serializer.writeData(data, offset, modId);
//
//        return offset;
//    }
//
//    public int deserialize(byte[] data)
//            throws UnsupportedIdException, UnknownModIdException {
//        fileId = Serializer.readBytes(data, 0, 4);
//        if (!Arrays.equals(fileId, HypercubeLibConstants.CACHE_FILE_ID)) {
//            throw new UnsupportedIdException(fileId);
//        }
//
//        modId = Serializer.readString(data, 7);
//        if (!modId.equals(HypercubeLibConstants.getModId())) {
//            throw new UnknownModIdException(modId);
//        }
//
//        type = Serializer.readByte(data, 4);
//        version = Serializer.readShort(data, 5);
//
//        return modId.length() + 10;
//    }
}
