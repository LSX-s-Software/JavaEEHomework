package org.example;

public class BookServiceImpl implements BookService {
    private BookDao bookDao;

    private String name;
    private int id;
    private int readonlyInt;
    private boolean aBoolean;
    private float aFloat;
    private double aDouble;
    private long aLong;
    private short aShort;
    private byte aByte;
    private char aChar;

    public void setABoolean(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public void setAFloat(float aFloat) {
        this.aFloat = aFloat;
    }

    public void setADouble(double aDouble) {
        this.aDouble = aDouble;
    }

    public void setALong(long aLong) {
        this.aLong = aLong;
    }

    public void setAShort(short aShort) {
        this.aShort = aShort;
    }

    public void setAByte(byte aByte) {
        this.aByte = aByte;
    }

    public void setAChar(char aChar) {
        this.aChar = aChar;
    }

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public void setName(String name) {
        this.name = name;
        System.out.println("name is set to: " + name);
    }

    public void setId(int id) {
        this.id = id;
        System.out.println("id is set to: " + id);
    }

    public void init() {
        System.out.println("BookService init");
    }

    public void save() {
        System.out.println("BookService save");
        bookDao.save();
    }
}
