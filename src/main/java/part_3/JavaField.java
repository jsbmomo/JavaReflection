package part_3;

import java.lang.reflect.Field;

public class JavaField {

  public static void main(String[] args) {
    printDeclaredFieldsInfo(Movie.class);
  }

  public static void printDeclaredFieldsInfo(Class<?> clazz) {
    for (Field field : clazz.getDeclaredFields()) {
      System.out.println(String.format("Field name : %s type : %s",
          field.getName(),
          field.getType().getName()));

      System.out.println();
    }
  }

  public static class Movie extends Product {
    public static final double MININUM_PRICE = 10.99;

    private boolean isReleased;
    private Category category;
    private double actualPrice;

    public Movie(String name, int year, double price, boolean isReleased, Category category) {
      super(name, year);
      this.isReleased = isReleased;
      this.category = category;
      this.actualPrice = Math.max(price, MININUM_PRICE);
    }
  }

  // super class
  public static class Product {
    protected String name;
    protected int year;
    protected  double actualPrice;

    public Product(String name, int year) {
      this.name = name;
      this.year = year;
    }
  }

  public enum Category {
    ADVENTURE,
    ACTION,
    COMEDY
  }
}
