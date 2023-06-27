package HW13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;

public class UserAPI {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/users";

    public static String createUser(String jsonInput) throws IOException {
        URL url = new URL(BASE_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(jsonInput.getBytes());
        outputStream.flush();

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_CREATED) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();
        } else {
            throw new IOException("Failed to create user. Response code: " + responseCode);
        }
    }

    public static String updateUser(String jsonInput, int userId) throws IOException {
        String updateUserURL = BASE_URL + "/" + userId;
        URL url = new URL(updateUserURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(jsonInput.getBytes());
        outputStream.flush();

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();
        } else {
            throw new IOException("Failed to update user. Response code: " + responseCode);
        }
    }

    public static void deleteUser(int userId) throws IOException {
        String deleteUserURL = BASE_URL + "/" + userId;
        URL url = new URL(deleteUserURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");

        int responseCode = connection.getResponseCode();
        if (responseCode >= HttpURLConnection.HTTP_OK && responseCode < HttpURLConnection.HTTP_MULT_CHOICE) {
            System.out.println("Detection successful");
        } else {
            throw new IOException("Failed to delete user. Response code: " + responseCode);
        }
    }

    public static String getAllUsers() throws IOException {
        URL url = new URL(BASE_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();
        } else {
            throw new IOException("Failed to get all users. Response code: " + responseCode);
        }
    }

    public static String getUserById(int userId) throws IOException {
        String getUserURL = BASE_URL + "/" + userId;
        URL url = new URL(getUserURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();
        } else {
            throw new IOException("Failed to get user. Response code: " + responseCode);
        }
    }

    public static String getUserByUsername(String username) throws IOException {
        String getUserByUsernameURL = BASE_URL + "?username=" + username;
        URL url = new URL(getUserByUsernameURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();
        } else {
            throw new IOException("Failed to get user by username. Response code: " + responseCode);
        }
    }
    public static void getUserPostComments(int userId) throws IOException {
        String userPostsURL = BASE_URL + "/" + userId + "/posts";
        URL url = new URL(userPostsURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
            }
            scanner.close();

            String[] posts = response.toString().split("\\[|\\]");
            String[] lastPost = posts[1].split(",");
            int postId = Integer.parseInt(lastPost[0].split(":")[1].trim());

            String postCommentsURL = BASE_URL + "/" + postId + "/comments";
            url = new URL(postCommentsURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                scanner = new Scanner(connection.getInputStream());
                response = new StringBuilder();
                while (scanner.hasNextLine()) {
                    response.append(scanner.nextLine());
                }
                scanner.close();

                String fileName = "user-" + userId + "-post-" + postId + "-comments.json";
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                    writer.write(response.toString());
                }
                System.out.println("Comments saved to file: " + fileName);
            } else {
                throw new IOException("Failed to get post comments. Response code: " + responseCode);
            }
        } else {
            throw new IOException("Failed to get user posts. Response code: " + responseCode);
        }
    }

    public static void getOpenTasksForUser(int userId) throws IOException {
        String userTasksURL = BASE_URL + "/" + userId + "/todos";
        URL url = new URL(userTasksURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
            }
            scanner.close();

            String[] tasks = response.toString().split("\\[|\\]");
            for (int i = 1; i < tasks.length; i += 2) {
                String[] taskFields = tasks[i].split(",");
                int taskId = Integer.parseInt(taskFields[0].split(":")[1].trim());
                String taskTitle = taskFields[1].split(":")[1].trim().replaceAll("\"", "");
                boolean taskCompleted = Boolean.parseBoolean(taskFields[2].split(":")[1].trim());

                if (!taskCompleted) {
                    System.out.println("Task ID: " + taskId);
                    System.out.println("Title: " + taskTitle);
                    System.out.println("Completed: " + taskCompleted);
                    System.out.println();
                }
            }
        } else {
            throw new IOException("Failed to get user tasks. Response code: " + responseCode);
        }
    }
}