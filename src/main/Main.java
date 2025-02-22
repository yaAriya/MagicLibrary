package main;

import DAOClass.BookDAO;

import DAOClass.UserDAO;

import enity.Book;

import enity.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List books = BookDAO.initializeBooks();
        System.out.println(books);

        List users = UserDAO.initializeUsers();
        System.out.println(users);
    }
}