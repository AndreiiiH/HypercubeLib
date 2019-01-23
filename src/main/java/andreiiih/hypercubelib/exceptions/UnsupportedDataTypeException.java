package andreiiih.hypercubelib.exceptions;

public class UnsupportedDataTypeException extends Exception {

    public UnsupportedDataTypeException(byte got) {
        super(String.format("Unsupported data type encountered! Got 0x%x", got));
    }
}
