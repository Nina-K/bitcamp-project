package bitcamp.java89.ems.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

import bitcamp.java89.ems.server.annotation.RequestParam;
import bitcamp.java89.ems.server.context.RequestHandlerMapping;
import bitcamp.java89.ems.server.context.RequestHandlerMapping.RequestHandler;

public class RequestThread extends Thread {
  private Socket socket;
  private Scanner in;
  private PrintStream out;

  private RequestHandlerMapping handlerMapping;
  //요청을 다루는 자에 대한 연결정보

  public RequestThread(Socket socket, RequestHandlerMapping handlerMapping) {
    this.socket = socket;
    this.handlerMapping = handlerMapping;
  }

  @Override
  public void run() {
    // 스레드가 독립적으로 실행한다 => 클라이언트 요청을 읽어서 그에 대한 응답을 하는 코드
    try { 
      in = new Scanner(new BufferedInputStream(socket.getInputStream()));
      out = new PrintStream(new BufferedOutputStream(socket.getOutputStream()), true);

      out.println("비트캠프 관리시스템에 오신걸 환영합니다.");

      while (true) {
        //클라이언트에게 데이터를 전송한다.
        out.println("명령>");
        out.println(); 

        String[] command = in.nextLine().split("\\?"); //문자를 받아서 바로 쪼갬

        HashMap<String,String> dataMap = new HashMap<>();
        // 클라이언트가 보낸 데이터가 저장된 맵
        // 파라미터문자열이 있다면, 이 문자열을 분석하여 HashMap에 보관한다.
        if (command.length == 2) {          
          String[] params = command[1].split("&");        
          for (String value : params) {
            String[] kv = value.split("=");
            dataMap.put(kv[0], kv[1]);
          }
        }

        RequestHandler requestHandler = handlerMapping.getRequestHandler(command[0]);
        // 일반객체로 만듦

        if (requestHandler == null) {
          if (command[0].equals("quit")) {
            doQuit();
            break;
          }
          out.println("지원하지 않는 명령어입니다.");
          continue; // 다음 줄을 실행하지 않고, 반복문 조건 검사로 건너 뛴다. (while쪽으로)
        } 
        //클라이언트가 보낸 명령을 처리할 객체가 있다면, 작업을 실행한다.
        try {
          requestHandler.method.invoke(
              requestHandler.obj, 
              getArguments(requestHandler.method, dataMap, out));
          //메소드가 원하는 값만 넘기겠다 --> 이게 핵심!

        } catch (Exception e) {
          out.println("작업 중 오류가 발생했습니다.");
          e.printStackTrace();
        }

      }

    } catch (Exception e) {
    } finally {
      try {in.close();} catch (Exception e) {}
      try {out.close();} catch (Exception e) {}
      try {socket.close();} catch (Exception e) {}
    }
  }

  private Object[] getArguments(Method method, HashMap<String,String> dataMap, PrintStream out) {
    //호출할 메서드의 파라미터 정보를 추출한다.
    Parameter[] params = method.getParameters();
    
    //파라미터 값을 저장할 배열을 준비한다.
    Object[] args = new Object[params.length];
    
    //파라미터 정보를 꺼내서 그에 맞는 값을 준비한다.
    for (int i = 0; i < params.length; i++) { //인덱스 필요
      // 파라미터에 @RequestParam이라는 애노테이션이 붙은 경우
      RequestParam anno = params[i].getAnnotation(RequestParam.class);
      if (anno != null) {
        String value = anno.value();
        if (params[i].getType() == int.class) {
        args[i] = Integer.parseInt(dataMap.get(value));
      } else if (params[i].getType() == boolean.class) {
          args[i] = Boolean.parseBoolean(dataMap.get(value));
        } else if (params[i].getType() == String.class) {
          args[i] = dataMap.get(value);
        } else {
          args[i] = null;
        }
      } else {
        if (params[i].getType() == PrintStream.class) {
          args[i] = out;
        } else if (params[i].getType() == PrintStream.class) {
          args[i] = dataMap;
        } else {
          args[i] = null;
        }
      }
    }
    return args;
  }

  private boolean doQuit() {
    System.out.println("클라이언트 연결 종료!");
    return true;
  }
}
