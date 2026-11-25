import java.util.*;
import java.io.*;

public class Library {
    static class Book {
        String title, author;
        Book(String t, String a) { title = t; author = a; }
        public String toString() { return title + " | " + author; }
    }

    static ArrayList<Book> library = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    static void sort() {
        for (int i = 1; i < library.size(); i++) {
            Book key = library.get(i);
            int j = i - 1;
            while (j >= 0 && library.get(j).title.compareToIgnoreCase(key.title) > 0)
                library.set(j + 1, library.get(j--));
            library.set(j + 1, key);
        }
    }

    static void addBook() {
        System.out.print("Title: ");
        String t = sc.nextLine().trim();
        System.out.print("Author: ");
        String a = sc.nextLine().trim();

        if (t.isEmpty() || a.isEmpty()) {
            System.out.println("Invalid input!");
            return;
        }
        library.add(new Book(t, a));
        sort();
        System.out.println("Book added.");
    }

    static void display() {
        for (Book b : library) System.out.println(b);
    }

    static void searchTitle() {
        System.out.print("Title: ");
        String t = sc.nextLine();
        int l = 0, h = library.size() - 1;

        while (l <= h) {
            int m = (l + h) / 2;
            int c = library.get(m).title.compareToIgnoreCase(t);
            if (c == 0) { System.out.println(library.get(m)); return; }
            if (c < 0) l = m + 1;
            else h = m - 1;
        }
        System.out.println("Book not found.");
    }

    static void searchAuthor() {
        System.out.print("Author: ");
        String a = sc.nextLine().toLowerCase();
        for (Book b : library)
            if (b.author.toLowerCase().contains(a)) System.out.println(b);
    }

    static void load() {
        try (BufferedReader br = new BufferedReader(new FileReader("books.txt"))) {
            String s;
            while ((s = br.readLine()) != null) {
                String[] p = s.split("\\|", 2);
                if (p.length == 2) library.add(new Book(p[0], p[1]));
            }
        } catch (IOException e) {}
        sort();
    }

    static void save() {
        try (PrintWriter pw = new PrintWriter("books.txt")) {
            for (Book b : library) pw.println(b.title + "|" + b.author);
        } catch (IOException e) {
            System.out.println("Error saving!");
        }
    }

    public static void main(String[] args) {
        load();
        while (true) {
            System.out.println("\n1.Add  2.Display  3.Search Title  4.Search Author  5.Exit");
            System.out.print("Choice: ");

            switch (sc.nextLine()) {
                case "1": addBook(); break;
                case "2": display(); break;
                case "3": searchTitle(); break;
                case "4": searchAuthor(); break;
                case "5": save(); return;
                default: System.out.println("Invalid choice!");
            }
        }
    }
}