package part_3;

import java.lang.reflect.Field;

/**
 * Java Reflection을 통해 실행 시, 클래스나 필드 객체의 정보를 동적으로 가져올 수 있다.
 * 이 기능으로 객체 타입을 미리 알 수 없거나 프로그램이 실행되기 전까지 존재하지 않는
 * 제네릭 알고리즘과 라이브러리를 작성할 수 있다.
 * 또한, 실행 시 클래스 필드를 검사하는 동안 Java 컴파일러가 숨겨놓은 작업을 확인할 수 있다.
 */
public class JavaField {

  public static void main(String[] args) throws NoSuchFieldException {
//    printDeclaredFieldsInfo(Movie.class);
//    printDeclaredFieldsInfo(Movie.MovieStats.class);
    Movie movie = new Movie("Lord of the Rings", 2001, 12.99, true, Category.ADVENTURE);
    // printDeclaredFieldsInfo(movie.getClass(), movie);

    Field minPriceStaticField = Movie.class.getDeclaredField("MINIMUM_PRICE");
    System.out.println(String.format("static MINIMUM_PRICE value : $f", minPriceStaticField));
  }

  public static <T> void printDeclaredFieldsInfo(Class<? extends  T> clazz, T instance) throws IllegalAccessException {
    for (Field field : clazz.getDeclaredFields()) {
      System.out.println(String.format("Field name : %s type : %s",
          field.getName(),
          field.getType().getName()));

      System.out.println(String.format("Is synthetic field : %s", field.isSynthetic()));
      System.out.println(String.format("Field value is %s", field.get(instance)));

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

    // Nested class
    public class MovieStats {
      private double timesWatched;

      public MovieStats(double timesWatched) {
        this.timesWatched = timesWatched;
      }

      public double getRevenue() {
        return timesWatched * actualPrice;
      }
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
