package HW11;

import java.util.Collections;
import java.util.List;

class StringFormatter {
    public static List<String> formatAndSortStrings(List<String> strings) {
        strings.replaceAll(String::toUpperCase);
        Collections.sort(strings, Collections.reverseOrder());
        return strings;
    }
}