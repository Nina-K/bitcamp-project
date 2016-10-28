package bitcamp.java89.ems;

import java.util.Scanner;

public class EduApp {
  public static void main(String[] args) {
    System.out.println("비트캠프 관리시스템에 오신걸 환영합니다.");

    Classroom[] classrooms = new Classroom[100];
    int length = 0;
    Scanner keyScan = new Scanner(System.in);

    while (true) {
      System.out.println("명령> ");
      String input = keyScan.nextLine();
      if (input.equals("add")) {
        addClassroomList(classrooms, length++);
      } else if (input.equals("list")) {
        printClassroomList(classrooms, length);
      } else if (input.equals("view")) {
        viewClassroomList(classrooms, length);
      } else if (!keyScan.nextLine().equals("add, list, view")) {
        System.out.println("지원하지 않는 명령어입니다.");
        System.out.println("입력 가능한 명령어는 'add', 'list', 'view'입니다.");
      }
      System.out.print("계속 입력하시겠습니까(y/n)? ");
      if (!keyScan.nextLine().equals("y")) {
        break;
      }
      }
  }

  static void viewClassroomList(Classroom[] classrooms, int length) {
    Classroom classroom;
    Scanner keyScan = new Scanner(System.in);

    System.out.println("강의실 이름? ");
    String input = keyScan.nextLine();

    for (int i = 0; i < length; i++) {
      classroom = classrooms[i];
      if (input.equals(classroom.name)) {
        System.out.printf("강의실이름: %s\n", classroom.name);
        System.out.printf("층수: %d\n", classroom.floors);
        System.out.printf("최대수용인원: %d\n", classroom.maximumSeating);
        System.out.printf("최대이용기간: %s\n", classroom.maximumPeriod);
        System.out.printf("이용시간: %s\n", classroom.time);
        System.out.printf("암호: %s\n", classroom.password);
        System.out.printf("프로젝터 여부: %b\n", classroom.projector);
        System.out.printf("사물함 여부: %b\n", classroom.locker);
        System.out.printf("에어컨 여부: %b\n", classroom.conditioner);
      }
    }
  }


  static void addClassroomList(Classroom[] classrooms, int length) {
    Classroom classroom = new Classroom();
    Scanner keyScan = new Scanner(System.in);

      System.out.print("강의실이름(예:강의실301호)? ");
      classroom.name = keyScan.nextLine();

      System.out.print("층수(예:3)? ");
      classroom.floors = Integer.parseInt(keyScan.nextLine());

      System.out.print("최대수용인원(예:30)? ");
      classroom.maximumSeating = Integer.parseInt(keyScan.nextLine());

      System.out.print("최대이용기간(예:6개월)? ");
      classroom.maximumPeriod = keyScan.nextLine();

      System.out.print("이용시간(예:08:00~22:00)? ");
      classroom.time = keyScan.nextLine();

      System.out.print("암호(예:0000)? ");
      classroom.password = keyScan.nextLine();

      System.out.print("프로젝터 여부(y/n)? ");
      classroom.projector = (keyScan.nextLine().equals("y")) ? true : false;

      System.out.print("사물함 여부(y/n)? ");
      classroom.locker = (keyScan.nextLine().equals("y")) ? true : false;

      System.out.print("에어컨 여부(y/n)? ");
      classroom.conditioner = (keyScan.nextLine().equals("y")) ? true : false;

      classrooms[length] = classroom;

  }


  static void printClassroomList(Classroom[] classrooms, int length) {
    for (int i = 0; i < length; i++) {
      Classroom classroom = classrooms[i];
    System.out.printf("%s, %d, %d, %s, %s, %s, %s, %s, %s\n",
       classroom.name,
       classroom.floors,
       classroom.maximumSeating,
       classroom.maximumPeriod,
       classroom.time,
       classroom.password,
       ((classroom.projector)?"yes":"no"),
       ((classroom.locker)?"yes":"no"),
       ((classroom.conditioner)?"yes":"no"));
    }
  }
}
