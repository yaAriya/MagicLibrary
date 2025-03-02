package reader;

import java.io.BufferedReader;

import java.io.FileNotFoundException;

import java.io.FileReader;

import java.io.IOException;

import java.nio.file.Files;

import java.nio.file.Path;

import java.nio.file.Paths;

import java.util.ArrayList;

import java.util.List;

public class ResourceFileReader {
    public static void main(String[] args) {
        String bookFilePath = "resources/book.txt";
        String userFilePath = "resources/user.txt";
        try {
            Path bookPath = Files.createFile(Paths.get("resources/book.txt"));
            System.out.println("Файл был создан?");
            System.out.println(Files.exists(bookPath));

            Path userPath = Files.createFile(Paths.get("resources/user.txt"));
            System.out.println("Файл был создан?");
            System.out.println(Files.exists(userPath));
        } catch (IOException e) {
            System.out.println("Файл уже был создан ");
        }
    }

    public static List<String> addBookStringToArray(String filePath) {
        List<String> books = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            for (int i = 0; i < 3; i++) {
                String bookText = reader.readLine();
                books.add(bookText);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Ошибка при вводе/выводе данных из файла!");
            e.printStackTrace();
        }
        return books;
    }

    public static List<String> addUserStringToArray(String filePath) {
        List<String> users = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            for (int i = 0; i < 5; i++) {
                String userText = reader.readLine();
                users.add(userText);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Ошибка при вводе/выводе данных из файла!");
            e.printStackTrace();
        }
        return users;
    }
}