package HW10;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberIdentifier {
    private static final String ABSOLUTE_PATH = "C:\\Users\\dmala\\IdeaProjects\\aquawarine\\file.txt";

    public static void main(String[] args) {
        File file = new File(ABSOLUTE_PATH);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                if (isValidPhoneNumber(line)) {
                    System.out.println(line);
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("\\(\\d{3}\\) \\d{3}-\\d{4}|\\d{3}-\\d{3}-\\d{4}");
        Pattern pattern1 = Pattern.compile("\\d{3}-\\d{3}-\\d{4}");
        Matcher matcher = pattern.matcher(phoneNumber);
        Matcher matcher1 = pattern1.matcher(phoneNumber);
        return matcher.matches() || matcher1.matches();
    }
}
