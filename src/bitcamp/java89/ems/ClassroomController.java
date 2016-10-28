package bitcamp.java89.ems;

import java.util.Scanner;

public class ClassroomController {
  static Classroom[] classrooms = new Classroom[100];
  static int length = 0;
  static Scanner keyScan; //변수만 선언, 공유하기 위해서

  static void doList() {
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

  static void doAdd() {
    while(length < classrooms.length) {
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

      classrooms[length++] = classroom;

      System.out.print("계속 입력하시겠습니까(y/n)? ");
      if (!keyScan.nextLine().equals("y"))
          break;
    }
  }

   static void doView() {
     System.out.print("조회할 강의실 이름은? ");
     String name = keyScan.nextLine().toLowerCase();
     for (int i = 0; i < length; i++) {
       if (classrooms[i].name.toLowerCase().equals(name)) {
         System.out.printf("강의실 이름: %s\n", classrooms[i].name);
         System.out.printf("층수: %d\n", classrooms[i].floors);
         System.out.printf("최대수용인원: %d\n", classrooms[i].maximumSeating);
         System.out.printf("최대이용기간: %s\n", classrooms[i].maximumPeriod);
         System.out.printf("이용시간: %s\n", classrooms[i].time);
         System.out.printf("암호: (****)\n");
         System.out.printf("프로젝터 여부: %b\n", classrooms[i].projector);
         System.out.printf("사물함 여부: %b\n", classrooms[i].locker);
         System.out.printf("에어컨 여부: %b\n", classrooms[i].conditioner);
         break;
        }
      }
    }
  }
