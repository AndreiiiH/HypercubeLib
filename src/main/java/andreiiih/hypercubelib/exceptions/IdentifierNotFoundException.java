package andreiiih.hypercubelib.exceptions;

import java.lang.reflect.Type;

public class IdentifierNotFoundException extends Exception {

    public IdentifierNotFoundException(byte id) {
        this(String.format("0x%x", id));
    }

    public IdentifierNotFoundException(Type id) {
        this(id.getTypeName());
    }

    private IdentifierNotFoundException(String id) {
        super(String.format("The given ID, %s, was not found in the registry!", id));
    }
}
