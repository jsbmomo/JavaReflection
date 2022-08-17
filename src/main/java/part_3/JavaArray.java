package part_3;

import java.lang.reflect.Array;

public class JavaArray {

  public static void main(String[] args) {
    int[] oneDimentsionalArray = { 1, 2 };

    double[][] twoDimensionalArray = { {1.5, 2.5}, { 3.5, 4.5 } };

    System.out.println("===== Array Object =====");
    inspectArrayObject(oneDimentsionalArray);
    inspectArrayObject(twoDimensionalArray);

    // Java Reflection 으로 런타임에서 배열 요소 값 가져오기
    System.out.println("===== Array Values =====");
    inspectArrayValues(oneDimentsionalArray);
    inspectArrayValues(twoDimensionalArray);
  }

  public static void inspectArrayValues(Object objectArray) {
    int arrayLength = Array.getLength(objectArray);

    System.out.println("[");

    for (int i = 0; i < arrayLength; i++) {
      Object element = Array.get(objectArray, i);

      // 다차원 배열일 경우 실행
      if (element.getClass().isArray()) {
        inspectArrayValues(element);
      } else {
        System.out.println(element);
      }

      if (i != arrayLength - 1) {
        System.out.println(" , ");
      }
    }

    System.out.println("]");
  }

  public static void inspectArrayObject(Object objectArray) {
    // 클래스 유형 검사를 위해서는 항상 클래스 객체를 먼저 얻어야한다.
    Class<?> clazz = objectArray.getClass();

    System.out.println(String.format("Is Array : %s,", clazz.isArray()));

    Class<?> arrayComponentType = clazz.getComponentType();

    System.out.println(String.format("This is an array of type : %s", arrayComponentType.getTypeName()));
  }
}
