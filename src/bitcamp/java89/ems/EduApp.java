package bitcamp.java89.ems;

import java.util.Scanner;

public class EduApp {
  public static void main(String[] args) {
    System.out.println("비트캠프 관리시스템에 오신걸 환영합니다.");

   //1)사용자로부터 값을 입력받을 때 사용할 입력 도구 준비
    Scanner keyScan = new Scanner(System.in);
    Classroom classroom = new Classroom();

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


//Integer.parseInt(keyScan.nextLine()); -> 숫자입력일 경우
