package part_2;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  생성자 탐색 및 객체 생성.
 *  Java의 생성자는 Constructor의 인스턴스로 나타낸다.
 *  이 객체는 클래스 생성자의 모든 정보를 포함하고 있다.
 *
 *  Java Reflection이 없다면 Enum 등으로 생성하고자 하는 객체의 타입을
 *  직접 전달해야 하므로, 특정 클래스에 구체적인 객체를 생성하는 코드가 필요하다.
 *
 *
 */

public class CreatorSearch {

  public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
    printConstructorData(Person.class);
    printConstructorData(Address.class);

    // Java Reflection 을 활용하여 객체를 생성하는 방법
//    Address address = (Address) createInstanceWithArguments(Address.class, "First Street", 101);
//    Person person = (Person) createInstanceWithArguments(Person.class, address,"John", 25);

    // 제네릭 (T)인수를 사용하여 캐스팅 코드 제거
    Address address = createInstanceWithArguments(Address.class, "First Street", 101);
    Person person = createInstanceWithArguments(Person.class, address,"John", 25);

    System.out.println(person);
  }

  // 제네릭 팩토리 메서드를 활용, 클래스의 객체를 생성
  // 인수를 여러개 받아서 알맞은 생성자를 찾은 뒤 원하는 객체로 인스턴스화 한다.
  public static <T> T createInstanceWithArguments(Class<T> clazz, Object ...args) throws IllegalAccessException, InvocationTargetException, InstantiationException {
    for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
      if(constructor.getParameterTypes().length == args.length) {
        return (T) constructor.newInstance(args);
      }
    }
    System.out.println("An appropriate constructor was not found");
    return null;
  }

  public static void printConstructorData(Class<?> clazz) {
    Constructor<?>[] constructors = clazz.getDeclaredConstructors();

    System.out.println(String.format("class %s has %d declared constructors.", clazz.getSimpleName(), constructors.length));

    for (int i = 0; i < constructors.length; i++) {
      Class<?>[] parameterType = constructors[i].getParameterTypes();

      List<String> parameterTypeNames = Arrays.stream(parameterType)
          .map(type -> type.getSimpleName())
          .collect(Collectors.toList());

      System.out.println(parameterTypeNames);
    }
  }

  public static class Person {
    private final Address address;
    private final String name;
    private final int age;

    public Person() {
      this.address = null;
      this.name = "anonymous";
      this.age = 0;
    }

    public Person(String name) {
      this.address = null;
      this.name = name;
      this.age = 0;
    }

    public Person(String name, int age) {
      this.address = null;
      this.name = name;
      this.age = age;
    }

    public Person(Address address, String name, int age) {
      this.address = address;
      this.name = name;
      this.age = age;
    }

    @Override
    public String toString() {
      return "Person{" +
          "address=" + address +
          ", name='" + name + '\'' +
          ", age=" + age +
          '}';
    }
  }

  public static class Address {
    private String street;
    private int number;

    public Address(String street, int number) {
      this.street = street;
      this.number = number;
    }

    @Override
    public String toString() {
      return "Address{" +
          "street='" + street + '\'' +
          ", number=" + number +
          '}';
    }
  }

}
