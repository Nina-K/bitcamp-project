/* ObjectInputStream와 ObjectOutputStream는 객체형태(ArrayList)를 입출력할 수 있다.
 * list에 있는 내용을 통째로 Input해서, Output으로 담은 것 전체를 출력해주는 것
 * list = (ArrayList<Classroom>)in.readObject();
   => readObject가 Classroom 형태의 ArrayList이다(타입캐스팅). 이걸 list에 담아라
 * out.writeObject(list); - 통째로 출력
 * */

package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import bitcamp.java89.ems.server.dao.ClassroomDao;
import bitcamp.java89.ems.server.vo.Classroom;

public class ClassroomController2 {
  private Scanner in;
  private PrintStream out;
  
  private ClassroomDao classroomDao;
  
  public ClassroomController2(Scanner in, PrintStream out) throws Exception {
    this.in = in;
    this.out = out;

    classroomDao = ClassroomDao.getInstance();
  }

  public void save() throws Exception {
    if (classroomDao.isChanged()) { //contactDao가 바뀌었으면,
      classroomDao.save(); //contactDao에게 save하라고 전달하는 역할      
    }
  }

  public boolean service() {
    while (true) {
      out.println("강의실관리> ");
      out.println();
      
      String[] commands = in.nextLine().split("\\?");

      try {
        switch (commands[0]) {
          case "add": this.doAdd(commands[1]); break;
          case "list": this.doList(); break;
          case "view": this.doView(commands[1]); break;
          case "delete": this.doDelete(commands[1]); break;
          case "update": this.doUpdate(commands[1]); break;
          case "main": return true;
          case "quit": return false;
        //main이나 quit을 할때만 나갈 수 있도록한다. 단 더 이상 실행하지 않겠다는걸로 quit은 faluse. loop가 필요없어져서 지움
          default:
            System.out.println("지원하지 않는 명령어입니다.");
        }
      } catch (IndexOutOfBoundsException e) {
        System.out.println("인덱스가 유효하지 않습니다.");
      } catch (Exception e) {
        System.out.println("실행 중 오류가 발생했습니다.");
      }
    }
  }

  private void doList() {
    ArrayList<Classroom> list = classroomDao.getList();
    for (Classroom classroom : list) {
      out.printf("%s, %d, %d, %s, %s, %s, %s, %s, %s\n",
        classroom.getName(),
        classroom.getFloors(),
        classroom.getMaximumSeating(),
        classroom.getMaximumPeriod(),
        classroom.getTime(),
        classroom.getPassword(),
        ((classroom.isProjector())?"yes":"no"),
        ((classroom.isLocker())?"yes":"no"),
        ((classroom.isConditioner())?"yes":"no"));
    }
  }

  //add?name=강의실301&floors=3&maximumSeating=200&maximumPeriod=6&time=08:00~19:00&password=0000&projector=y&locker=n&conditioner=y
  private void doAdd(String params) {
      String[] values = params.split("&");
      HashMap<String,String> paramMap = new HashMap<>();
      
      for (String value : values) {
        String[] kv = value.split("=");
        paramMap.put(kv[0], kv[1]);
      }
      if (classroomDao.existName(paramMap.get("name"))) {
        out.println("같은 강의실이 존재합니다. 등록을 취소합니다.");
        return;
      }
      
      Classroom classroom = new Classroom();
      classroom.setName(paramMap.get("name"));
      classroom.setFloors(Integer.parseInt(paramMap.get("floors")));
      classroom.setMaximumSeating(Integer.parseInt(paramMap.get("maximumSeating")));
      classroom.setMaximumPeriod(paramMap.get("MaximumPeriod"));
      classroom.setTime(paramMap.get("time"));
      classroom.setPassword(paramMap.get("password"));
      classroom.setProjector((paramMap.get("projector").equals("y")) ? true : false);
      classroom.setLocker((paramMap.get("locker").equals("y")) ? true : false);
      classroom.setConditioner((paramMap.get("conditioner").equals("y")) ? true : false);

      classroomDao.insert(classroom);
      out.println("등록하였습니다.");
      }
  
   //view?name=홍길동
   private void doView(String params) {
     String[] values = params.split("="); 
     
     ArrayList<Classroom> list = classroomDao.getListByName(values[1]);
     for (Classroom classroom : list) {
         out.println("-----------------------------");
         out.printf("강의실 이름: %s\n", classroom.getName());
         out.printf("층수: %d\n", classroom.getFloors());
         out.printf("최대수용인원: %d\n", classroom.getMaximumSeating());
         out.printf("최대이용기간: %s\n", classroom.getMaximumPeriod());
         out.printf("이용시간: %s\n", classroom.getTime());
         out.printf("암호: (****)\n");
         out.printf("프로젝터 여부: %b\n", (classroom.isProjector()) ? "Yes" : "No");
         out.printf("사물함 여부: %b\n", (classroom.isLocker()) ? "Yes" : "No");
         out.printf("에어컨 여부: %b\n", (classroom.isConditioner()) ? "Yes" : "No");
     }
    }
   
   //delete?name=강의실이름
    private void doDelete(String params) {
      String[] values = params.split("=");
      
      if (!classroomDao.existName(values[1])) {
        out.println("해당 데이터가 없습니다.");
        return;
      }
      
      classroomDao.delete(values[1]);
      out.println("해당 데이터 삭제 완료하였습니다.");
      }
    

    //update?name=강의실301&floors=4&maximumSeating=400&maximumPeriod=4&time=08:00~14:00&password=4000&projector=n&locker=y&conditioner=y
    private void doUpdate(String params) {
      String[] values = params.split("&");
      HashMap<String,String> paramMap = new HashMap<>();
      
      for (String value : values) {
        String[] kv = value.split("=");
        paramMap.put(kv[0], kv[1]);
      } 
      
      if (!classroomDao.existName(paramMap.get("name"))) {
        out.println("이름을 찾지 못했습니다.");
        return;
      }
      
        Classroom classroom = new Classroom();
        classroom.getName().toLowerCase().equals(paramMap.get("name")); 
        classroom.setName(paramMap.get("name"));
        classroom.setFloors(Integer.parseInt(paramMap.get("floors")));
        classroom.setMaximumSeating(Integer.parseInt(paramMap.get("maximumSeating")));
        classroom.setMaximumPeriod(paramMap.get("MaximumPeriod"));
        classroom.setTime(paramMap.get("time"));
        classroom.setPassword(paramMap.get("password"));
        classroom.setProjector((paramMap.get("projector").equals("y")) ? true : false);
        classroom.setLocker((paramMap.get("locker").equals("y")) ? true : false);
        classroom.setConditioner((paramMap.get("conditioner").equals("y")) ? true : false);
        
        classroomDao.update(classroom);
        out.println("저장하였습니다.");
      }
    } 
    
