package org.example;

public class Main {
    public static void main(String[] args) {
        MiniApplicationContext context = new MiniApplicationContext();
        try {
            context.init("applicationContext.xml");
            BookService bookService = (BookService) context.getBean("bookService");
            bookService.save();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}