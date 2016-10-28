package bitcamp.java89.ems;

import java.util.Scanner;

public class EduApp {
  static Scanner keyScan = new Scanner(System.in);

  public static void main(String[] args) {

    ClassroomController classroomController = new ClassroomController(keyScan);

    System.out.println("비트캠프 관리시스템에 오신걸 환영합니다.");

    loop:
    while (true) {
      System.out.print("명령> ");
      String command = keyScan.nextLine().toLowerCase();

      switch (command) {
        case "add": classroomController.doAdd(); break; //이제 클래스 대신 인스턴스 주소를 주는 것
        case "list": classroomController.doList(); break;
        case "view": classroomController.doView(); break;
        case "quit":
          System.out.println("Good bye!");
          break loop;
        default:
          System.out.println("지원하지 않는 명령어입니다.");
      }
    }
  }
}
