package andreiiih.hypercubelib.exceptions;

import java.lang.reflect.Type;

public class TypeAlreadyRegisteredException extends Exception {

    public TypeAlreadyRegisteredException(Type type) {
        super(String.format("The given type, %s, is already registered!", type.getTypeName()));
    }
}
