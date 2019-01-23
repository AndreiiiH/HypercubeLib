package andreiiih.hypercubelib.cache;

import andreiiih.hypercubelib.HypercubeLibConstants;
import andreiiih.hypercubelib.exceptions.UnknownModIdException;
import andreiiih.hypercubelib.exceptions.UnsupportedIdException;
import andreiiih.hypercubelib.serialization.Serializer;

import java.util.Arrays;

public final class CacheItemHeader {

    private byte[] fileId = HypercubeLibConstants.CACHE_FILE_ID;
    private byte type;
    private short version;
    private String modId = HypercubeLibConstants.getModId();

    public CacheItemHeader(byte type, short version) {
        this.type = type;
        this.version = version;
    }

    public int serialize(byte[] data, int offset) {
        offset = Serializer.writeData(data, 0, fileId);
        offset = Serializer.writeData(data, offset, type);
        offset = Serializer.writeData(data, offset, version);
        offset = Serializer.writeData(data, offset, modId);

        return offset;
    }

    public int deserialize(byte[] data)
            throws UnsupportedIdException, UnknownModIdException {
        fileId = Serializer.readBytes(data, 0, 4);
        if (!Arrays.equals(fileId, HypercubeLibConstants.CACHE_FILE_ID)) {
            throw new UnsupportedIdException(fileId);
        }

        modId = Serializer.readString(data, 7);
        if (!modId.equals(HypercubeLibConstants.getModId())) {
            throw new UnknownModIdException(modId);
        }

        type = Serializer.readByte(data, 4);
        version = Serializer.readShort(data, 5);

        return modId.length() + 10;
    }
}
