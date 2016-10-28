package bitcamp.java89.ems;

import java.util.Scanner;

public class EduApp {
  static Scanner keyScan = new Scanner(System.in);

  public static void main(String[] args) {
    //자기가 쓰고있는 keyScan을 공유하자는 것, 쓸데없이 중복해서 객체를 만들지 말자는 것
    //EduApp에서 사용하는 keyScan을 ClassroomController와 공유한다.
    ClassroomController.keyScan = keyScan;

    System.out.println("비트캠프 관리시스템에 오신걸 환영합니다.");

    loop: //라벨 붙임. while문에 대해서 loop를 붙인 것
    while (true) {
      System.out.print("명령> ");
      String command = keyScan.nextLine().toLowerCase();

      switch (command) {
        case "add": ClassroomController.doAdd(); break;
        case "list": ClassroomController.doList(); break;
        case "view": ClassroomController.doView(); break;
        case "quit":
          System.out.println("Good bye!");
          break loop; //while문 밖을 아예 나가버리는 것
        default:
          System.out.println("지원하지 않는 명령어입니다.");
      }
    }
  }
}
