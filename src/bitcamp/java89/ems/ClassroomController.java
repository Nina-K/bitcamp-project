package bitcamp.java89.ems;

import java.util.Scanner;

public class ClassroomController {
  Classroom[] classrooms = new Classroom[100];
  int length = 0;
  Scanner keyScan;

  //생성자는 객체를 만들 때 유효한 값들로 만드는 것
  public ClassroomController(Scanner keyScan) {
    this.keyScan = keyScan;
  }

  public void doList() {
    for (int i = 0; i < this.length; i++) {
      Classroom classroom = this.classrooms[i]; //this에 저장된 인스턴스를 가져와라 / 인스턴스에 접근하기 위해
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

  public void doAdd() {
    while(length < this.classrooms.length) { //this는 ClassroomController 객체, 그 변수를 가리킴
      Classroom classroom = new Classroom();

      System.out.print("강의실이름(예:강의실301호)? ");
      classroom.name = this.keyScan.nextLine();

      System.out.print("층수(예:3)? ");
      classroom.floors = Integer.parseInt(this.keyScan.nextLine());

      System.out.print("최대수용인원(예:30)? ");
      classroom.maximumSeating = Integer.parseInt(this.keyScan.nextLine());

      System.out.print("최대이용기간(예:6개월)? ");
      classroom.maximumPeriod = this.keyScan.nextLine();

      System.out.print("이용시간(예:08:00~22:00)? ");
      classroom.time = this.keyScan.nextLine();

      System.out.print("암호(예:0000)? ");
      classroom.password = this.keyScan.nextLine();

      System.out.print("프로젝터 여부(y/n)? ");
      classroom.projector = (this.keyScan.nextLine().equals("y")) ? true : false;

      System.out.print("사물함 여부(y/n)? ");
      classroom.locker = (this.keyScan.nextLine().equals("y")) ? true : false;

      System.out.print("에어컨 여부(y/n)? ");
      classroom.conditioner = (this.keyScan.nextLine().equals("y")) ? true : false;

      this.classrooms[length++] = classroom;

      System.out.print("계속 입력하시겠습니까(y/n)? ");
      if (!this.keyScan.nextLine().equals("y"))
          break;
    }
  }

   public void doView() {
     System.out.print("조회할 강의실 이름은? ");
     String name = this.keyScan.nextLine().toLowerCase();
     for (int i = 0; i < this.length; i++) {
       if (this.classrooms[i].name.toLowerCase().equals(name)) {
         System.out.printf("강의실 이름: %s\n", this.classrooms[i].name);
         System.out.printf("층수: %d\n", this.classrooms[i].floors);
         System.out.printf("최대수용인원: %d\n", this.classrooms[i].maximumSeating);
         System.out.printf("최대이용기간: %s\n", this.classrooms[i].maximumPeriod);
         System.out.printf("이용시간: %s\n", this.classrooms[i].time);
         System.out.printf("암호: (****)\n");
         System.out.printf("프로젝터 여부: %b\n", this.classrooms[i].projector);
         System.out.printf("사물함 여부: %b\n", this.classrooms[i].locker);
         System.out.printf("에어컨 여부: %b\n", this.classrooms[i].conditioner);
         break;
        }
      }
    }
  }
