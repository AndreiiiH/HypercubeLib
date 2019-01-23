package andreiiih.hypercubelib.serialization;

import andreiiih.hypercubelib.core.BiMap;
import andreiiih.hypercubelib.exceptions.IdentifierNotFoundException;
import andreiiih.hypercubelib.exceptions.TypeAlreadyRegisteredException;

public final class ObjectTypeRegistry {

    private static final BiMap<Class<? extends SDObject>, Byte> idMap = new BiMap<>();
    private static byte nextId = 0;

    public static byte register(Class<? extends SDObject> type) throws TypeAlreadyRegisteredException {
        if (idMap.containsKey(type)) {
            throw new TypeAlreadyRegisteredException(type);
        }

        idMap.put(type, nextId);

        return nextId++;
    }

    public static byte get(Class<? extends SDObject> type) throws IdentifierNotFoundException {
        if (!idMap.containsKey(type)) {
            throw new IdentifierNotFoundException(type);
        }

        return idMap.getValue(type);
    }

    public static Class<? extends SDObject> get(byte id) throws IdentifierNotFoundException {
        if (!idMap.containsValue(id)) {
            throw new IdentifierNotFoundException(id);
        }

        return idMap.getKey(id);
    }
}
