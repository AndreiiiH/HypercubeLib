package andreiiih.hypercubelib.serialization;

public final class DataType {

    public static final byte NULL   = 0;
    public static final byte BYTE   = 1;
    public static final byte SHORT  = 2;
    public static final byte INT    = 3;
    public static final byte LONG   = 4;
    public static final byte FLOAT  = 5;
    public static final byte DOUBLE = 6;
    public static final byte STRING = 7;

    public static short getDataTypeSize(byte dataType) {
        switch (dataType) {
            case BYTE:
                return 1;
            case SHORT:
                return 2;
            case INT:
                return 4;
            case LONG:
                return 8;
            case FLOAT:
                return 4;
            case DOUBLE:
                return 8;
        }

        return 0;
    }
}
