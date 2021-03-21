import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyCollection<E> implements Collection<E> {
    private int size;
    private Object[] elementData;

    /*public static void main(final String[] argc) {

        Integer[] nums = {null, 12, null, 6, 2, 1};

        MyCollection<Integer> collection = new MyCollection<>(nums);
        Iterator<Integer> it = collection.iterator();
        //collection.add(3);
        System.out.println("src :");
        while (it.hasNext()) {
            System.out.println(it.next());
        }
        Integer[] ints = {null, 2, 6, 10};
        MyCollection<Integer> c = new MyCollection<>(ints);
        //System.out.println(collection.removeAll(c));
        //System.out.println(collection.remove(null));
        //System.out.println(collection.containsAll(c));
        //System.out.println(collection.elementData.length);
        collection.addAll(c);
        //System.out.println(collection.retainAll(c));
        //Iterator<Integer> iter = collection.iterator();
        collection.add(3);
        collection.add(66);
        //System.out.println(collection.contains(null));
        //System.out.println(collection.size +" size");
        //it.remove();
        //it.next();
        //it.remove();
        //collection.clear();
        //collection.add(3);
        System.out.println("rez :");

        for (Object elem : collection.elementData) {
            System.out.println(elem);
        }
        //System.out.println(collection.contains(null));
    }*/

    public MyCollection(final Object[] elementData) {
        this.elementData = elementData.clone();
        size = elementData.length;
    }

    // Метод меняющий размер массива на число равное count. Для remove оказался ненужным (count > 0 не заходят).
    private void turnSize(final Object[] e, final int count) {
        elementData = new Object[e.length - count];
        if (count >= 0) {
            System.arraycopy(e, 0, elementData, 0, e.length - count);
        } else {
            System.arraycopy(e, 0, elementData, 0, e.length);
        }
    }

    @Override
    public final boolean add(final E e) {
        if (size == elementData.length) {
            elementData = Arrays.copyOf(elementData, (int) (size * 1.5f));
        }
        elementData[size++] = e;
        return true;
    }

    @Override
    public final int size() {
        return this.size;
    }

    @Override
    public final boolean isEmpty() {
        return false;
    }

    @Override
    public final Iterator<E> iterator() {
        return new MyIterator<>();
    }

    @Override
    public final boolean contains(final Object o) {
        for (int i = 0; i < size; i++) {
            if (o == null) {
                if (elementData[i] == null) {
                    return true;
                }
                continue;
            }
            if (o.equals(elementData[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public final Object[] toArray() {
        Object[] obj = new Object[size];
        System.arraycopy(elementData, 0, obj, 0, size);
        return obj;
    }

    @Override
    public final <T> T[] toArray(final T[] a) {
        T[] puff = a;
        if (puff.length <= size) {
            puff = (T[]) elementData;
        } else {
            for (int i = 0; i < size; i++) {
                puff[i] = (T) elementData[i];
            }
        }
        return puff;
    }

    @Override
    public final boolean remove(final Object o) {
        int i = 0;
        int p = 0;
        //int count = 0;
        boolean rez = false;
        Object[] puff = new Object[elementData.length];
        while (i < size) {
            if (o == null) {
                if ((elementData[i] == null) && !(rez)) {
                    rez = true;
                    i++;
                    //count++;
                    continue;
                }
                puff[p] = elementData[i];
                i++;
                p++;
                continue;
            }
            if ((o.equals(elementData[i])) && !(rez)) {
                rez = true;
                i++;
                //count++;
                continue;
            }
            puff[p] = elementData[i];
            i++;
            p++;
        }
        if (rez) {
            size--;
            // Метод менющий размер elementData. Здесь оказался ненужным.
            //turnSize(puff, count);
            elementData = puff;
        }
        return rez;
    }

    @Override
    public final boolean containsAll(final Collection<?> c) {
        int i = 0;
        Object[] puff = c.toArray();
        while (i < puff.length) {
            if (contains(puff[i])) {
                i++;
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public final boolean addAll(final Collection<? extends E> c) {
        int count = 0;
        int i = 0;
        Object[] puff = this.toArray();
        count = count - c.toArray().length;
        turnSize(puff, count);
        while (i < c.toArray().length) {
            elementData[size] = c.toArray()[i];
            size++;
            i++;
        }
        return true;
    }

    @Override
    public final boolean removeAll(final Collection<?> c) {
        boolean rez = false;
        int i = 0;
        Object[] puff = c.toArray();
        while (i < puff.length) {
            if (remove(puff[i])) {
                rez = true;
            }
            if (contains(puff[i])) {
                continue;
            }
            i++;
        }
        return rez;
    }

    @Override
    public final boolean retainAll(final Collection<?> c) {
        Object[] elems = elementData;
        int sze = size;
        boolean rez = false;
        int i = 0;
        while (i < sze) {
            if (!(c.contains(elems[i]))) {
                remove(elems[i]);
                rez = true;
            }
            i++;
        }
        return rez;
    }

    @Override
    public final void clear() {
        elementData = new Object[5];
        size = 0;
    }

    private class MyIterator<T> implements Iterator<T> {

        private int cursor = 0;
        private boolean key = true;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
            key = true;
            return (T) elementData[cursor++];
        }

        @Override
        public void remove() {
            try {
                if (key) {
                    MyCollection.this.remove(elementData[cursor - 1]);
                    cursor--;
                    key = false;
                } else {
                    throw new IllegalStateException();
                }


            } catch (ArrayIndexOutOfBoundsException | IllegalStateException e) {
                System.out.println(new IllegalStateException());
            }
        }
    }
}

