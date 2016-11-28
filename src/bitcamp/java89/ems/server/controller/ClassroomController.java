package bitcamp.java89.ems.server.controller;

import java.util.Scanner;

import bitcamp.java89.ems.server.vo.Classroom;

public class ClassroomController {
  private Box head;
  private Box tail;
  private int length;
  private Scanner keyScan;

  public ClassroomController(Scanner keyScan) {
    head = new Box();
    tail = head;
    length = 0;
    //스태틱 변수는 스태틱 블록에서 초기화하지만, 인스턴스는 인스턴스블록 또는 생성자에서 초기화한다.
    //여기서는 생성자에서 초기화
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
        case "update": this.doUpdate(); break;
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
    Box currentBox = head; //currentBox = 현재 상자, 현재 박스가 head를 가리킴
    while (currentBox != null && currentBox != tail) {
      Classroom classroom = (Classroom)currentBox.value; //currentBox는 현재 상자를 가리킨다.
        //현재상자에 담겨있는 값을 classroom에 담아야한다(꺼낸다).
        //현재박스에 있는 클래스룸 값을 꺼내는 것
        //Object타입이라서 Object는 상위라서 넣을 수 없다뜨기때문에, 타입캐스팅으로 알려준다.
        //value는 Object 타입이므로
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
      currentBox = currentBox.next; //다음박스 주소를 현재 박스에 담는다.
    } //현재 박스를 계속 증가시켜가면서, 앞으로 나아가면서 반복시켜간다.
  }

  private void doAdd() {
    while(true) { //예전엔 배열 길이에 한계가 있었지만, 링크드리스트는 무한이다 -> 멈출때까지
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

      // tail이 가리키는 빈 상자에 Classroom 인스턴스의 주소를 담는다.
      // 그리고 새 상자를 만든 다음, 현재 상자에 연결한다.
      // tail은 다시 맨 마지막 빈 상자를 가리킨다.
      tail.value = classroom; // 단, 여긴 강의실의 인스턴스 주소를 저장하는 것이기 때문에, classroom
      tail.next = new Box();
      tail = tail.next;
      length++;
      //배열에 저장하는 대신, 빈박스에 저장한다.

      System.out.print("계속 입력하시겠습니까(y/n)? ");
      if (!this.keyScan.nextLine().equals("y"))
          break;
    }
  }

   private void doView() {
     System.out.print("강의실의 인덱스? ");
     int index = Integer.parseInt(this.keyScan.nextLine().toLowerCase());
     //조회할 강의실 이름을 찾던걸 강의실의 인덱스로 바꾼다. => int index로 수정
     //인덱스를 받아서 유효 범위에 없다면 유효하지 않다는 걸 출력하고 나간다.
     //아니라면 현재 박스 head부터 뒤져서 현재 박스에 들어있는 classroom 정보를 뒤져서,
     //그 각각의 항목을 출력한다.

     if (index < 0 || index >= length) {
       System.out.println("인덱스가 유효하지 않습니다.");
       return;
     }

     Box currentBox = head;
     for (int i = 0; i < index; i++) {
       currentBox = currentBox.next;
       //해당 위치까지 찾아간다.
     }
     Classroom classroom = (Classroom)currentBox.value; //Classroom 주소니까, (Classroom)을 호출하겠다.
     System.out.printf("강의실 이름: %s\n", classroom.name);
     System.out.printf("층수: %d\n", classroom.floors);
     System.out.printf("최대수용인원: %d\n", classroom.maximumSeating);
     System.out.printf("최대이용기간: %s\n", classroom.maximumPeriod);
     System.out.printf("이용시간: %s\n", classroom.time);
     System.out.printf("암호: (****)\n");
     System.out.printf("프로젝터 여부: %b\n", (classroom.projector) ? "Yes" : "No");
     System.out.printf("사물함 여부: %b\n", (classroom.locker) ? "Yes" : "No");
     System.out.printf("에어컨 여부: %b\n", (classroom.conditioner) ? "Yes" : "No");

    }

    private void doDelete() {
      System.out.print("삭제할 강의실의 인덱스? ");
      int index = Integer.parseInt(keyScan.nextLine());

      if (index < 0 || index >= length) {
        System.out.println("인덱스가 유효하지 않습니다.");
        return;
      }

      Classroom deletedClassroom = null;
      //삭제될 학생의 주소를 if 위에 둔다.
      if (index == 0) {
        deletedClassroom = (Classroom)head.value; //다음껄 가리키기 전, 현재 head의 value값이 삭제될 학생이다.
        head = head.next;
        length--;
      } else { //else를 써서, 인덱스가 0일 경우와 인덱스가 0이 아닐 경우로 나눈다.
        Box currentBox = head;
        for (int i = 0; i < (index-1); i++) {
          currentBox = currentBox.next;
        }
        deletedClassroom = (Classroom)currentBox.next.value;
        //삭제될 강의실은 현재 박스의 다음 박스에 있는데, 그 들어있는 강의실 주소를 따로 꺼내둔다(알아야하는 것).
        currentBox.next = currentBox.next.next;
      }
      length--;
      System.out.printf("%s 강의실 정보를 삭제하였습니다.\n", deletedClassroom.name);
      //이렇게 출력 후 현재 박스에서 다음 박스로 연결시켜 버린다.
      //복붙 후 조건에 맞춰야함. 반드시 어떤 강의실 정보를 지웠는지 출력하게 해놨다.
    }

    private void doUpdate() {
      System.out.print("변경할 강의실 인덱스? ");
      int index = Integer.parseInt(keyScan.nextLine());

      // 일단, 유효한 인덱스인지 검사
      if (index < 0 || index >= length) {
        System.out.println("인덱스가 유효하지 않습니다.");
        return;
      }

      // 변경하려는 학생 정보가 저장된 상자를 찾는다.
      // 학생 인스턴스 주소의 정보가 저장된 것
      Box currentBox = head;
      for (int i = 0; i < index; i++) {
        currentBox = currentBox.next;
      }

        // 찾은 상자에서 변경할 학생의 정보를 꺼낸다.
        Classroom oldClassroom = (Classroom)currentBox.value;
                                 // 클래스룸의 인스턴스 주소를 꺼내는것

        //새 학생 정보를 입력받는다
        Classroom classroom = new Classroom();

        System.out.print("층수? ");
        classroom.floors = Integer.parseInt(this.keyScan.nextLine());

        System.out.printf("최대수용인원(%d)? ", oldClassroom.maximumSeating);
        classroom.maximumSeating = Integer.parseInt(this.keyScan.nextLine());

        System.out.printf("최대이용기간(%s)? ", oldClassroom.maximumPeriod);
        classroom.maximumPeriod = this.keyScan.nextLine();

        System.out.printf("이용시간(%s)? ", oldClassroom.time);
        classroom.time = this.keyScan.nextLine();

        System.out.printf("암호(%s)? ", oldClassroom.password);
        classroom.password = this.keyScan.nextLine();

        System.out.print("프로젝터 여부(y/n)? ");
        classroom.projector = (this.keyScan.nextLine().equals("y")) ? true : false;

        System.out.print("사물함 여부(y/n)? ");
        classroom.locker = (this.keyScan.nextLine().equals("y")) ? true : false;

        System.out.print("에어컨 여부(y/n)? ");
        classroom.conditioner = (this.keyScan.nextLine().equals("y")) ? true : false;

        System.out.print("저장하시겠습니까? ");
        if (keyScan.nextLine().toLowerCase().equals("y")) {
          classroom.name = oldClassroom.name;
          currentBox.value = classroom;
          System.out.println("저장하였습니다.");
            } else {
              System.out.println("변경을 취소하였습니다.");
            }
        }
    }
