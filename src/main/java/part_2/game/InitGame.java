package part_2.game;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * package-private 클래스를 검사하고 인스턴스화 하는 기능을 확인.
 * 객체 지향 방식으로 틱택토 게임 구현.
 */
public class InitGame {

  public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
    Game game = (Game) createObjectRecursively(Class.forName("part_2.game.internal.TicTacToeGame"));
    game.startGame();
  }

  public static <T> T createObjectRecursively(Class<T> clazz) throws IllegalAccessException, InvocationTargetException, InstantiationException {
    Constructor<?> constructor = getFirstConstructor(clazz);

    List<Object> constructorArguments = new ArrayList<>();

    // 생성자의 매개변수 타입을 인수로 넣는다.
    for (Class<?> argumentType : constructor.getParameterTypes()) {
      Object argumentValue = createObjectRecursively(argumentType);

      // 해당 메서드로부터 결과를 받으면 매개변수 타입의 객체가 생기고,
      // 생성자 인수 목록에 이를 추가.
      constructorArguments.add(argumentValue);
    }

    constructor.setAccessible(true);
    return (T) constructor.newInstance(constructorArguments.toArray());
  }

  private static Constructor<?> getFirstConstructor(Class<?> clazz) {
    Constructor<?>[] constructors = clazz.getDeclaredConstructors();
    if (constructors.length == 0) { // 클래스가 있는지 확인
      throw new IllegalStateException(String.format("No constructor has been found for class %s", clazz.getSimpleName()));
    }

    return constructors[0];
  }
}
