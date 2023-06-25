package HW11;

import java.util.stream.Stream;

public class StreamUtils {
    public static <T> Stream<T> zip(Stream<T> first, Stream<T> second) {
        Object[] firstArray = first.toArray();
        Object[] secondArray = second.toArray();

        int minLength = Math.min(firstArray.length, secondArray.length);

        return Stream.iterate(0, i -> i + 1)
                .limit(minLength)
                .map(i -> {
                    if (i % 2 == 0) {
                        return (T) firstArray[i / 2];
                    } else {
                        return (T) secondArray[i / 2];
                    }
                });
    }
}