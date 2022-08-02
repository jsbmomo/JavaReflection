package part_1;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * class 객체가 어떤 데이터를 제공할 수 있는가?
 * 사용자 지정, JAVA 내장 클래스 데이터 타입을 분석 (.getClass())
 */
public class ReflectionApi {

  public static void main(String[] args) throws ClassNotFoundException {
    Class<String> stringClass = String.class;

    Map<String, Integer> mapObject = new HashMap<>();

    Class<?> hasMapClass = mapObject.getClass();

    Class<?> squareClass = Class.forName("exerclass.Main$Square");

    // printClassInfo(stringClass, hasMapClass, squareClass);

    var circleObject = new Drawable() {
      @Override
      public int getNumberOfCorners() {
        return 0;
      }
    };

    // collection - interface, boolean - origin
    printClassInfo(Collection. class, boolean.class, int[][].class, Color.class, circleObject.getClass());
  }

  private static void printClassInfo(Class<?> ... classes) { // 가변인수
    for (Class<?> clazz : classes) {
      System.out.println(String.format("class name : %s, class package name : %s",
          clazz.getSimpleName(),
          clazz.getPackageName()));

      Class<?> [] implementedInterfaces = clazz.getInterfaces();

      for (Class<?> implementedInterface : implementedInterfaces) {
        System.out.println(String.format("class %s implements : %s",
            clazz.getSimpleName(),
            implementedInterface.getSimpleName()));
      }

      System.out.println("Is array : " + clazz.isArray());
      System.out.println("Is primitive : " + clazz.isPrimitive());
      System.out.println("Is enum : " + clazz.isEnum());
      System.out.println("Is interfae : " + clazz.isInterface());
      System.out.println("Is enonymous : " + clazz.isAnonymousClass());

      System.out.println();
      System.out.println();
    }
  }

  private static class Square implements Drawable {

    @Override
    public int getNumberOfCorners() {
      return 5;
    }
  }

  private static interface Drawable {
    int getNumberOfCorners();
  }

  private enum Color {
    BLUE,
    RED,
    GREEN
  }

}
