package collections_solutions;

public class EqualsTests {

    private Node<Integer> n1;

    public static void main(String[] args) {


        EqualsTests t1 = new EqualsTests();

        t1.n1 = new Node(1);

        System.out.println(t1.specialMethod(1));




    }


    public boolean specialMethod(Object elem) {
        return n1.elem.equals(elem);
    }


    static class Node<E> {
        private final E elem;

        Node(E elem) {
            this.elem = elem;
        }
    }
}
