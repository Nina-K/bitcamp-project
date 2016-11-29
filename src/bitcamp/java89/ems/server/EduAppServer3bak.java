package bitcamp.java89.ems.server;

import java.io.File;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.util.ArrayList;
//import bitcamp.java89.ems.server.controller.StudentController;
import java.util.HashMap;

import bitcamp.java89.ems.server.dao.ClassroomDao;
import bitcamp.java89.ems.server.dao.ContactDao;

public class EduAppServer3bak {
  HashMap<String,Command> commandMap = new HashMap<>();

  public EduAppServer3bak() {
    // Controller가 사용할 DAO 객체 준비
    ContactDao contactDao = new ContactDao();
    contactDao.setFilename("contact-v1.9.data");
    try {
      contactDao.load();
    } catch (Exception e) {
      System.out.println("연락처 로딩 중 오류 발생!");
    }

    ClassroomDao classroomDao = new ClassroomDao();
    classroomDao.setFilename("classroom-v1.9.data");
    try {
      classroomDao.load();
    } catch (Exception e) {
      System.out.println("강의실 정보 로딩 중 오류 발생!");
    }

    ArrayList<Class> classList = new ArrayList<>();
    ReflectionUtil.getCommandClasses(new File("./bin"), classList);

    for (Class c : classList) {
      try {
        AbstractCommand command = (AbstractCommand)c.newInstance(); 

        // commandMap에 저장하기 전에 각 controller에 대해 DAO를 주입한다.
        try {
          Method m = command.getClass().getMethod("setContactDao", ContactDao.class);
          //setContactDao 이런 이름을 가진 ContactDao.class 이런 파라미터를 찾는데, 못 찾으면 에러 발생!
          m.invoke(command, contactDao);
          // System.out.printf("%s:%s\n", command.getClass().getName(), m.getName());
        } catch (Exception e) {
          // 해당 메서드가 없다. //없으면 예외를 던져버린다. 없으면 없는대로 무시함!
        }
        try {
          Method m = command.getClass().getMethod("setClassroomDao", ClassroomDao.class);
          m.invoke(command, classroomDao);
          // System.out.printf("%s:%s\n", command.getClass().getName(), m.getName());
        } catch (Exception e) {
          // 해당 메서드가 없다.
        }

        // 클라이언트 요청을 처리할 Controller를 맵에 등록한다.
        commandMap.put(command.getCommandString(), command);
      } catch (Exception e) {}
    }
  }

  private void service() throws Exception {
    ServerSocket ss = new ServerSocket(8888);
    System.out.println("..서버 실행 중..");

    while (true) {
      new RequestThread(ss.accept(), commandMap).start();
    } 
  }

  public static void main(String[] args) throws Exception {
    EduAppServer3bak eduServer = new EduAppServer3bak();
    eduServer.service();
  }
}
