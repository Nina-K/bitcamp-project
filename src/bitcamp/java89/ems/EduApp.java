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
        case "menu": doMenu(); break;
        case "go 3": classroomController.service(); break;
        case "quit":
          System.out.println("Good bye!");
          break loop;
        default:
          System.out.println("지원하지 않는 명령어입니다.");
      }
    }
  }

  static void doMenu() {
    System.out.println("[메뉴]");
    System.out.println("1. 강의실관리");
    System.out.println("메뉴 이동은 'go 메뉴번호'를 입력하세요.");
  }
}
