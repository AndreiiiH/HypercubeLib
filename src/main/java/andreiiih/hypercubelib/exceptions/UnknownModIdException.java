package andreiiih.hypercubelib.exceptions;

import andreiiih.hypercubelib.HypercubeLibConstants;

public class UnknownModIdException extends Exception {

    public UnknownModIdException(String modId) {
        super(String.format("Unexpected Mod ID found during deserialization! Expected %s, found %s",
                HypercubeLibConstants.getModId(), modId));
    }
}
