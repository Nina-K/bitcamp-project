package bitcamp.java89.ems.server;

import java.io.File;
import java.net.ServerSocket;
import java.util.ArrayList;
//import bitcamp.java89.ems.server.controller.StudentController;
import java.util.HashMap;

public class EduAppServer2bak {
  //클라이언트 요청을 처리할 Command 구현체들을 보관한다(Command 구현체 보관소).
  //HashMap<명령문자열,요청처리객체> commandMap
  HashMap<String,Command> commandMap = new HashMap<>();
  
  public EduAppServer2bak() {
    /*bin 폴더를 뒤져서 AbstracrCommand의 서브 클래스를 찾아낸다.*/
    ArrayList<Class> classList = new ArrayList<>();
    ReflectionUtil.getCommandClasses(new File("./bin"), classList); //classList 바구니를 넘겨주면 바구니에 담아넣는다.
    
    for (Class c : classList) {
      //System.out.println(c.getName()); //정말 들어가는가해서 확인해봤더니 정말 제대로 들어갔음
      try {
        // 클라이언트 요청을 처리할 Command 객체 등록
      AbstractCommand command = (AbstractCommand)c.newInstance(); //타입캐스팅
      commandMap.put(command.getCommandString(), command);
      //command.getCommandString() = 각 Controller 마다 명찰을 달아서 어떤 일을 수행하는지 알려준다.
      //이걸 가져와서 넣어주면 각 명찰마다 일을 수행하면 된다.
      } catch (Exception e) {}
    }
    
//    commandMap.put("contact/list", new ContactListController());
//    commandMap.put("contact/view", new ContactViewController());
//    commandMap.put("contact/add", new ContactAddController());
//    commandMap.put("contact/delete", new ContactDeleteController());
//    commandMap.put("contact/update", new ContactUpdateController());
//    
//    commandMap.put("classroom/list", new ClassroomListController());
//    commandMap.put("classroom/view", new ClassroomViewController());
//    commandMap.put("classroom/add", new ClassroomAddController());
//    commandMap.put("classroom/delete", new ClassroomDeleteController());
//    commandMap.put("classroom/update", new ClassroomUpdateController());
  }
  
  private void service() throws Exception {
    ServerSocket ss = new ServerSocket(8888);
    System.out.println("..서버 실행 중..");
    
    while (true) {
      new RequestThread(ss.accept(), commandMap).start();
    } 
  }
  
  public static void main(String[] args) throws Exception {
    EduAppServer2bak eduServer = new EduAppServer2bak();
    eduServer.service();
  }
}
