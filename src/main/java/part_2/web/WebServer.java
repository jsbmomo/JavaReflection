package part_2.web;

import com.sun.net.httpserver.HttpServer;
import part_2.ServerConfiguration;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Http 요청을 받고 처리할 로직.
 */
public class WebServer {

  public void startServer() throws IOException {
    // ServerConfiguration 싱글톤 인스턴스에서 사용 가능한 서버 주소 전달.
    HttpServer httpServer = HttpServer.create(ServerConfiguration.getInstance().getServerAddress(), 0);

    httpServer.createContext("Greeting.").setHandler(exchange -> {
      String responseMessage = ServerConfiguration.getInstance().getGreetingMessage();

      exchange.sendResponseHeaders(200, responseMessage.length());

      OutputStream responseBody = exchange.getResponseBody();
      responseBody.write(responseMessage.getBytes());
      responseBody.flush();
      responseBody.close();
    });

    System.out.println(String.format("Starting server on address %s : %d",
            ServerConfiguration.getInstance().getServerAddress().getHostName(),
            ServerConfiguration.getInstance().getServerAddress().getPort()));

    httpServer.start();
  }
}
