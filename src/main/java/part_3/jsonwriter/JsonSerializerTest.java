package part_3.jsonwriter;

import part_3.data.Address;
import part_3.data.Person;

import java.lang.reflect.Field;

/**
 * Java Reflection의 힘으로 Object -> JSON 변환기 구현.
 * 어떤 추정도 없이 모든 유형의 객체를 완벽히 JSON 포멧의 문자열로 전환 가능.
 * 여기서 어떤 추정도 없이라는 것은 객체 유형을 사전에 알지 못해도 모든 객체를
 * 구현할 수 있다는 것이다.
 *
 * 또한, 반복 구현을 통해 다른 객체를 필드로 가지는 클래스도 JSON으로 변환할 수 있다.
 */
public class JsonSerializerTest {
  public static void main(String[] args) throws IllegalAccessException {
    Address address = new Address("Main street", (short) 1);
    Person person = new Person("John", true, 29, 100.45f, address);

    String addressToJson = objectToJson(address, 0);
    String personToJson = objectToJson(person, 0);

    System.out.println(addressToJson);
    System.out.println(personToJson);
  }

  public static String objectToJson(Object instance, int indentSize) throws IllegalAccessException {
    Field[] fields = instance.getClass().getDeclaredFields();
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(indent(indentSize));
    stringBuilder.append("{");
    stringBuilder.append("\n");

    for (int i = 0; i < fields.length; i++) {
      Field field = fields[i];
      field.setAccessible(true);

      if (field.isSynthetic()) {
        continue;
      }

      stringBuilder.append(indent(indentSize + 1));
      stringBuilder.append(formatStringValue(field.getName()));
      stringBuilder.append(":");

      if (field.getType().isPrimitive()) {
        stringBuilder.append(formatPrimitiveValue(field, instance));
      } else if (field.getType().equals(String.class)) {
        stringBuilder.append(formatStringValue(field.get(instance).toString()));
      } else { // 피드를 객체로 갖고 JSON 메서드에 같은 객체를 반복 호출하여 JSON 문자열 표현
        stringBuilder.append(objectToJson(field.get(instance), indentSize + 1));
      }

      if (i != fields.length - 1) {
        stringBuilder.append(",");
      }
      stringBuilder.append("\n");
    }

    stringBuilder.append(indent(indentSize));
    stringBuilder.append("}");

    return stringBuilder.toString();
  }

  // 많은 필드를 가진 대형 객체를 테스트하고 싶을 경우 (json 시각화) 들여쓰기
  private static String indent(int indentSize) {
    StringBuilder stringBuilder = new StringBuilder();

    for (int i = 0; i < indentSize; i++) {
      stringBuilder.append("\t");
    }

    stringBuilder.append("\t".repeat(Math.max(0, indentSize)));

    return stringBuilder.toString();
  }

  private static String formatPrimitiveValue(Field field, Object parentInstance) throws IllegalAccessException {
    if (field.getType().equals(boolean.class)
        || field.getType().equals(int.class)
        || field.getType().equals(long.class)
        || field.getType().equals(short.class)) {
      return field.get(parentInstance).toString();
    } else if (field.getType().equals(double.class) || field.getType().equals(float.class)) {
      return String.format("%.02f", field.get(parentInstance));
    }

    throw new RuntimeException(String.format("Type : %s is unsupported", field.getType().getName()));
  }

  private static String formatStringValue(String value) {
    return String.format("\" %s \"", value);
  }
}
