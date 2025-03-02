package printer;

import java.util.List;

public class Printer {
    public static void printBookText(List<String> books) {
        for (String book : books) {
            System.out.println(book);
        }
    }

    public static void printUserText(List<String> users) {
        for (String user : users) {
            System.out.println(user);
        }
    }
}

