package andreiiih.hypercubelib.core;

public class IntRef extends Ref<Integer> {

    public IntRef(int value) {
        super(value);
    }

    public void incr() {
        this.ref++;
    }

    public void decr() {
        this.ref--;
    }

    public void add(int value) {
        this.ref += value;
    }

    public void sub(int value) {
        this.ref -= value;
    }
}
