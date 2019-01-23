package andreiiih.hypercubelib.exceptions;

import andreiiih.hypercubelib.HypercubeLibConstants;

public class UnsupportedIdException extends Exception {

    public UnsupportedIdException(byte[] foundId) {
        super(String.format("Unsupported file ID found during deserialization! Expected %s, found %s",
                new String(HypercubeLibConstants.CACHE_FILE_ID), new String(foundId)));
    }
}
