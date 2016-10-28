package bitcamp.java89.ems;

import java.util.Scanner;

public class EduApp {
  public static void main(String[] args) {
    System.out.println("비트캠프 관리시스템에 오신걸 환영합니다.");

    //여러 개의 강의실을 입력하기 위한 레퍼런스 배열을 만든다.
    Classroom[] classrooms = new Classroom[100];
    int length = 0; //몇 개를 저장했는지 알기 위해 length
                    //레퍼런스 배열에 몇 명의 학생 정볼르 저장했는지 개수를 지정한다.
                    //레퍼런스 배열에 몇 개의 Classroom 인스턴스가 들어 있는지 그 개수를 보관한다.

    Scanner keyScan = new Scanner(System.in);

   //반복해서 입력 받는다. 반복문.
   //Classroom classroom = null; //null - 주소없음을 의미한다
     //Classroom classroom;만 써도 OK
/*
     classrooms[length++] = classroom; 배열에 값이 들어가고 반복될때마다,
     레퍼런스의 값이 쓸데없이 반복되기 때문에 그러지않고 주소를 덮어씌울 수 있도록.
*/

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
      //legnth 변수를 두는 게 아니라 현재 값을 두고 아까 두었던 값을 늘리는 것
      //후위연산자 계산 잘 해야한다. 변수의 값을 증가 시키는 것.

      System.out.print("계속 입력하시겠습니까(y/n)? ");
      if (!keyScan.nextLine().equals("y"))
          //키스캔 입력된 값이 와이와 같이 않다면, if를 실행한다.
          break;

    }

    printClassroomList(classrooms, length);


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


//Integer.parseInt(keyScan.nextLine()); -> 숫자입력일 경우
//s 붙이면 배열의 레퍼런스, s없는 것 중 소문자는 개개의 주소를 가르키는 객체
