package bitcamp.java89.ems;

import java.util.Scanner;

public class ClassroomController {
  // 아래 인스턴스 변수들은 외부에서 사용할 일이 없기 때문에
  // private로 접근을 제한한다.
  private Classroom[] classrooms = new Classroom[100];
  private int length = 0;
  private Scanner keyScan;

  public ClassroomController(Scanner keyScan) {
    this.keyScan = keyScan;
  }

  public void service() {
    loop:
    while (true) {
      System.out.print("강의실관리> ");
      String command = keyScan.nextLine().toLowerCase();

      switch (command) {
        case "add": this.doAdd(); break;
        case "list": this.doList(); break;
        case "view": this.doView(); break;
        case "delete": this.doDelete(); break;
        case "updata": this.doUpdata(); break;
        case "main":
          break loop;
        default:
          System.out.println("지원하지 않는 명령어입니다.");
      }
    }
  }

  // 아래 doXXX() 메서드들은 오직 service()에서만 호출하기 때문에
  // private으로 접근을 제한한다.
  private void doList() {
    for (int i = 0; i < this.length; i++) {
      Classroom classroom = this.classrooms[i];
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

  private void doAdd() {
    while(length < this.classrooms.length) {
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

   private void doView() {
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

    private void doDelete() {
      System.out.print("삭제할 강의실 이름은? ");
      String name = this.keyScan.nextLine().toLowerCase();

      for (int i = 0; i < this.length; i++) {
        if (this.classrooms[i].name.toLowerCase().equals(name)) {

          for (int x = i + 1; x < this.length; x++, i++){
            this.classrooms[i] = this.classrooms[x];
          }
          this.classrooms[--length] = null;

          System.out.printf("%s 강의실 정보를 삭제하였습니다.\n", name);
          return;
         }
       }
       System.out.printf("%s 강의실이 없습니다.\n", name);
     }

    private void doUpdata() {
      System.out.print("변경할 강의실 이름은? ");
      String name = this.keyScan.nextLine().toLowerCase();

      for (int i = 0; i < this.length; i++) {
        if (this.classrooms[i].name.toLowerCase().equals(name)) {


            Classroom classroom = new Classroom();

            System.out.print("층수? ");
            classroom.floors = Integer.parseInt(this.keyScan.nextLine());

            System.out.print("최대수용인원? ");
            classroom.maximumSeating = Integer.parseInt(this.keyScan.nextLine());

            System.out.print("최대이용기간? ");
            classroom.maximumPeriod = this.keyScan.nextLine();

            System.out.print("이용시간? ");
            classroom.time = this.keyScan.nextLine();

            System.out.print("암호? ");
            classroom.password = this.keyScan.nextLine();

            System.out.print("프로젝터 여부? ");
            classroom.projector = (this.keyScan.nextLine().equals("y")) ? true : false;

            System.out.print("사물함 여부? ");
            classroom.locker = (this.keyScan.nextLine().equals("y")) ? true : false;

            System.out.print("에어컨 여부? ");
            classroom.conditioner = (this.keyScan.nextLine().equals("y")) ? true : false;

            System.out.print("저장하시겠습니까? ");
            if (this.keyScan.nextLine().equals("y")) {
              this.classrooms[length] = classroom;
              this.classrooms[i].floors = classroom.floors;
              this.classrooms[i].maximumSeating = classroom.maximumSeating;
              this.classrooms[i].maximumPeriod = classroom.maximumPeriod;
              this.classrooms[i].time = classroom.time;
              this.classrooms[i].password = classroom.password;
              this.classrooms[i].projector = classroom.projector;
              this.classrooms[i].locker = classroom.locker;
              this.classrooms[i].conditioner = classroom.conditioner;
              System.out.println("저장하였습니다.");
                break;
            } else {
                System.out.println("변경을 취소하였습니다.");
                break;
          }

        }
          System.out.printf("%s 강의실이 없습니다.\n", name);
      }
    }
  }
