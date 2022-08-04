package part_2;

import java.net.InetSocketAddress;

/**
 * 서버 구성 코드, 싱글톤 패턴 사용.
 * ServerConfiguration 클래스는 어느 코드에서든 접근할 수 있으나,
 * 초기화로부터 보호된다.
 *
 * private 를 사용해서, 클래스 외부에서 객체를 생성하는건
 * 컴파일 오류를 발생시키기 때문.
 */
public class ServerConfiguration {
  private static ServerConfiguration serverConfigurationInstance;

  private final InetSocketAddress serverAddress;
  private final String greetingMessage;

  private ServerConfiguration(int port, String greetingMessage) {
    this.greetingMessage = greetingMessage;
    this.serverAddress = new InetSocketAddress("localhost", port);

    // 클래스가 한번도 초기화 되지 않았을 때.
    // 이미 한 번 생성되었다면 해당 코드는 실행 X
    if (serverConfigurationInstance == null) {
      serverConfigurationInstance = this;
    }
  }

  // 서버 구성의 현재 싱글톤 인스턴스를 반환.
  public static ServerConfiguration getInstance() { return serverConfigurationInstance; }

  public InetSocketAddress getServerAddress() { return this.serverAddress; }

  public String getGreetingMessage() { return this.greetingMessage; }

}
