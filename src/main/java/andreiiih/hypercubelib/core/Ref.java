package andreiiih.hypercubelib.core;

public class Ref<E> {

    E ref;

    public Ref(E e){
        ref = e;
    }

    public E g() { return ref; }

    public void s(E e){ this.ref = e; }

    public String toString() {
        return ref.toString();
    }
}
