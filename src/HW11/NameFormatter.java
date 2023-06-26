package HW11;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class NameFormatter {
    public static String formatNames(List<String> names) {
        return IntStream.range(0, names.size())
                .filter(i -> i % 2 == 0)
                .mapToObj(i -> (i + 1) + ". " + names.get(i))
                .collect(Collectors.joining(", "));
    }
}