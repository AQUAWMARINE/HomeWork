package HW10;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class User {
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User [name=" + name + ", age=" + age + "]";
    }
}

class Main {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\dmala\\IdeaProjects\\aquawarine\\file.txt";
        List<User> users = readUsersFromFile(filePath);
        String jsonFilePath = "C:\\Users\\dmala\\IdeaProjects\\aquawarine\\user.json";
        writeUsersToJsonFile(users, jsonFilePath);
    }

    private static List<User> readUsersFromFile(String filePath) {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(" ");
                String name = columns[0];
                int age = Integer.parseInt(columns[1]);
                User user = new User(name, age);
                users.add(user);
            }
        } catch (IOException e) {
            e.getMessage();
        }
        return users;
    }

    private static void writeUsersToJsonFile(List<User> users, String filePath) {
        try (Writer writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.getMessage();
        }
    }
}