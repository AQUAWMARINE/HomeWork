package HW11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NumberSort {
    public static void main(String[] args) {
        String[] array = {"1, 2, 0", "4, 5"};
        List<Integer> numbersList = new ArrayList<>();

        for (String str : array) {
            String[] numbersArray = str.split(",\\s*");

            for (String number : numbersArray) {
                numbersList.add(Integer.parseInt(number));
            }
        }

        Collections.sort(numbersList);

        StringBuilder result = new StringBuilder();
        result.append('"');
        for (int i = 0; i < numbersList.size(); i++) {
            result.append(numbersList.get(i));
            if (i != numbersList.size() - 1) {
                result.append(", ");
            }
        }
        result.append('"');
        System.out.println(result);
    }
}