package main;

import reader.BookFileReader;

import reader.BookFileReaderImpl;

import reader.UserFileReader;

import reader.UserFileReaderImpl;

import service.UserService;

import service.UserServiceImpl;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
       /* Scanner scanner = new Scanner(System.in);
        System.out.println("Введите свой ID: ");
        int userID = scanner.nextInt();
        System.out.println("Введите ID книги, которую Вы бы хотели найти: ");
        int bookID = scanner.nextInt();
        scanner.close();

        UserServiceImpl userService = new UserServiceImpl();
        User findUserByID = userService.read(userID);
        System.out.println("Результат поиска Ваших пользовательских данных: " + findUserByID);

        BookServiceImplementation bookService = new BookServiceImplementation();
        Book findBookByID = bookService.read(bookID);
        System.out.println("Результат поиска книги: " + findBookByID);
    */
        String bookFilePath = "resources/book.txt";
        String userFilePath = "resources/user.txt";

       /* BookFileReader bookReader = new BookFileReaderImpl();
        bookReader.readBooksFromFile(bookFilePath);

        UserFileReader userReader = new UserFileReaderImpl();
        userReader.readUsersFromFile(userFilePath);
*/
        UserService userService = new UserServiceImpl();
        userService.readUsersFromFile(userFilePath);
    }
}