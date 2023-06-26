package HW11;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class StreamZip {
    public static <T> Stream<T> zip(Stream<T> first, Stream<T> second) {
        Iterator<T> firstIterator = first.iterator();
        Iterator<T> secondIterator = second.iterator();

        Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize(new Iterator<T>() {
            private boolean useFirst = true;

            @Override
            public boolean hasNext() {
                return (useFirst && firstIterator.hasNext()) || (!useFirst && secondIterator.hasNext());
            }

            @Override
            public T next() {
                useFirst = !useFirst;
                return useFirst ? firstIterator.next() : secondIterator.next();
            }
        }, Spliterator.ORDERED);

        return StreamSupport.stream(spliterator, false);
    }
}