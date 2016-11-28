package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import bitcamp.java89.ems.server.dao.StudentDao;
import bitcamp.java89.ems.server.vo.Student;

public class StudentController {
  private Scanner in;
  private PrintStream out;
  
  private StudentDao studentDao;

  public StudentController(Scanner in, PrintStream out) throws Exception {
    this.in = in;
    this.out = out;
    
    studentDao = StudentDao.getInstance();
  }
  
  public void save() throws Exception {
    if (studentDao.isChanged()) {
      studentDao.save();
    }
  }
  
  public boolean service() {
    while (true) {
      out.println("학생관리> ");
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
        default:
          out.println("지원하지 않는 명령어입니다.");
        }
      } catch (IndexOutOfBoundsException e) {
        out.println("인덱스가 유효하지 않습니다.");
      } catch (Exception e) {
        out.println("실행 중 오류가 발생했습니다.");
        e.printStackTrace();
      }
    }
  }

  private void doList() {
    ArrayList<Student> list = studentDao.getList();
    for (Student student : list) {
      out.printf("%s,%s,%s,%s,%s,%s,%d,%s\n",
        student.getUserId(),
        student.getPassword(),
        student.getName(),
        student.getTel(),
        student.getEmail(),
        ((student.isWorking())?"yes":"no"),
        student.getBirthYear(),
        student.getSchool());
    }
  }



  private void doUpdate(String params) {
    String[] values = params.split("&");
    HashMap<String,String> paramMap = new HashMap<>();
    
    for (String value : values) {
      String[] kv = value.split("=");
      paramMap.put(kv[0], kv[1]);
    }
    
    if (!studentDao.existUserId(paramMap.get("userId"))) {
      out.println("이메일을 찾지 못했습니다.");
      return;
    }
    
    Student student = new Student();
    student.getUserId().toLowerCase().equals(paramMap.get("userId")); 
    student.setUserId(paramMap.get("userId"));
    student.setPassword(paramMap.get("password"));
    student.setName(paramMap.get("name"));
    student.setTel(paramMap.get("Tel"));
    student.setEmail(paramMap.get("email"));
    student.setWorking((paramMap.get("working").equals("y")) ? true : false);
    student.setBirthYear(Integer.parseInt(paramMap.get("birthYear")));
    student.setSchool(paramMap.get("school"));
  } 
  
  
  private void doAdd(String params) {
    String[] values = params.split("&");
    HashMap<String,String> paramMap = new HashMap<>();
    
    for (String value : values) {
      String[] kv = value.split("=");
      paramMap.put(kv[0], kv[1]);
    }
    if (studentDao.existUserId(paramMap.get("userId"))) {
      out.println("같은 학생이 존재합니다. 등록을 취소합니다.");
      return;
    }
    
      Student student = new Student();
      student.setUserId(paramMap.get("userId"));
      student.setPassword(paramMap.get("password"));
      student.setName(paramMap.get("name"));
      student.setTel(paramMap.get("Tel"));
      student.setEmail(paramMap.get("email"));
      student.setWorking(paramMap.get("working").equals("y") ? true : false);
      student.setBirthYear(Integer.parseInt(paramMap.get("birthYear")));
      student.setSchool(paramMap.get("school"));

      studentDao.insert(student);
      out.println("등록하였습니다.");
      }

  private void doView(String params) {
    String[] kv = params.split("="); 
    
    ArrayList<Student> list = studentDao.getListByName(kv[1]);
    for (Student student : list){
        out.println("-----------------------------");
        out.printf("아이디: %s\n", student.getUserId());
        out.printf("암호: (***)\n");
        out.printf("이름: %s\n", student.getName());
        out.printf("전화: %s\n", student.getTel());
        out.printf("이메일: %s\n", student.getEmail());
        out.printf("재직중: %s\n", (student.isWorking()) ? "Yes" : "No");
        out.printf("태어난 해: %d\n", student.getBirthYear());
        out.printf("학교: %s\n", student.getSchool());
      }
    }
  

  private void doDelete(String params) {
    String[] values = params.split("=");
   
    if (!studentDao.existUserId(values[1])) {
      out.println("해당 데이터가 없습니다.");
      return;
    }
    
    studentDao.delete(values[1]);
    out.println("해당 데이터 삭제 완료하였습니다.");
    }
}
