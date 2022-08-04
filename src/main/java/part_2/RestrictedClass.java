package part_2;

import part_2.web.WebServer;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 기본적으로 private 를 사용하면 클래스 외부에서 호출이 불가능하다.
 * 하지만 Reflection을 사용하면 private 생성자에 접근할 수 있다.
 * 생성자 접근 뿐만 아니라 새로운 인스턴스 메서드로 제한된 생성자 중 하나를 이용해
 * 클래스 객체를 생성할 수 있다.
 *
 * 이러한 기능은 인스턴스화해야만 접근할 수 있는 클래스 생성자를 인스턴스화 할 때 유용.
 */
public class RestrictedClass {
  public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
    initConfiguration();
    WebServer webServer = new WebServer();
    webServer.startServer();
  }

  // 단일 private 생성자를 가지고, 변경 불가능한 싱글톤 클래스 인스턴스를 생성.
  public static void initConfiguration() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    Constructor<ServerConfiguration> constructor =
        ServerConfiguration.class.getDeclaredConstructor(int.class, String.class); // 생성자 인수의 객체 타입을 전달.

    // 이 경우 private 생성자이므로 접근할 수 없어 setAccessible 속성을 true로 설정.
    // 접근 불가능한 생성자에 접근 시에 반드시 필요
    constructor.setAccessible(true); // true가 아닐 경우 프로그램 충돌을 일으키는 RuntimeException 발생.
    constructor.newInstance(8080, "Good day !!!");
  }

}
