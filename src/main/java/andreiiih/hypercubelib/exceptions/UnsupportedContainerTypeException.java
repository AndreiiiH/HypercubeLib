package andreiiih.hypercubelib.exceptions;

public class UnsupportedContainerTypeException extends Exception {

    public UnsupportedContainerTypeException(byte expected, byte got) {
        super(String.format("Unsupported container type encountered! Expected 0x%x, got 0x%x", expected, got));
    }
}
