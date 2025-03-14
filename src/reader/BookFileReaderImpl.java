package reader;

import enity.Book;

import java.io.BufferedReader;

import java.io.FileReader;

import java.io.IOException;

import java.nio.file.Files;

import java.nio.file.Path;

import java.nio.file.Paths;

import java.util.ArrayList;

import java.util.List;

public class BookFileReaderImpl implements BookFileReader {

    private final String bookFilePath;

    public BookFileReaderImpl(){
    bookFilePath = "resources/book.txt";
    }

    public void createFile() {
        try {
            Path bookPath = Files.createFile(Paths.get("resources/book.txt"));
            System.out.println("Файл был создан?");
            System.out.println(Files.exists(bookPath));

        } catch (IOException e) {
            System.out.println("Файл уже был создан ");
        }
    }

   @Override
   public List<Book> readBooksFromFile() throws IOException {
       BufferedReader reader = new BufferedReader(new FileReader(bookFilePath));
       List<String> readLinesFromBookFile = new ArrayList<>();

       for (int i = 0; i < 3; i++) {// колво линий
           String bookLine = reader.readLine();
           readLinesFromBookFile.add(bookLine);
       }

       List<Book> books = new ArrayList<>();

       for (String line : readLinesFromBookFile) {
           Book book = convertLineToBook(line);
           books.add(book);
       }
       return books;
    }

    @Override
    public Book convertLineToBook(String line) {
        String[] parameters = line.split(",");

        Book book = new Book();
        book.setBookID(Integer.parseInt(parameters[0]));
        book.setBookName(parameters[1]);
        book.setAuthor(parameters[2]);
        book.setPagesNumber(Integer.parseInt(parameters[3]));

        return new Book(book.getBookID(), book.getBookName(), book.getAuthor(), book.getPagesNumber());
    }


   /* public static List<Book> addBookStringToArray(String filePath) {
        List<Book> books = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            BookDaoImpl bookDAOImplementation = new BookDaoImpl();
            for (int i = 0; i < 3; i++) {
                String bookText = reader.readLine();
                books.add(bookDAOImplementation.initializeBook(bookText)); // вернется только одна книга и с каждой итерацией книги будут пополняться
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

    public static List<User> addUserStringToArray(String filePath) {
        List<User> users = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            UserDAOImpl userDAOImplementation = new UserDAOImpl();
            for (int i = 0; i < 5; i++) {
                String userText = reader.readLine();
                users.add(userDAOImplementation.initializeUser(userText));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Ошибка при вводе/выводе данных из файла!");
            e.printStackTrace();
        }
        return users;
    }*/
}