package HW11;

import java.util.List;

public class NameFormatter {
    public static String formatNames(List<String> names) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < names.size(); i++) {
            if (i % 2 == 0) {
                result.append((i + 1) + ". " + names.get(i) + ", ");
            }
        }
        if (result.length() > 0) {
            result.delete(result.length() - 2, result.length());
        }
        return result.toString();
    }
}