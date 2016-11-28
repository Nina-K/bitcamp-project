package bitcamp.java89.ems.server.dao;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import bitcamp.java89.ems.server.vo.Student;

public class StudentDao {
  static StudentDao obj;
  
  private String filename = "student.data";
  static ArrayList<Student> list;
  private boolean changed;
  
  private StudentDao() {
    this.load();
  }
  
  public static StudentDao getInstance() {
    if (obj == null) {
      obj = new StudentDao();
    }
    return obj;
  }

  public boolean isChanged() {
    return changed;
  }

  @SuppressWarnings("unchecked")
  private void load() {
    FileInputStream in0 = null;
    ObjectInputStream in = null;

    try {
      in0 = new FileInputStream(this.filename);
      in = new ObjectInputStream(in0);

    list = (ArrayList<Student>)in.readObject();
    // readObject가 Classroom 형태의 ArrayList이다(타입캐스팅). 이걸 list에 담아라

    } catch (EOFException e) {

    } catch (Exception e) {
      System.out.println("학생 데이터 로딩 중 오류 발생!");
      list = new ArrayList<>();
      
    } finally {
      try {
        in.close();
        in0.close();
      } catch (Exception e) {

      }
    }
  }

  public void save() throws Exception {

    FileOutputStream out0 = new FileOutputStream(this.filename);
    ObjectOutputStream out = new ObjectOutputStream(out0);

    out.writeObject(list); //list를 통째로 출력해버린 것
    changed = false;

    out.close();
    out0.close();
  }
  
  public ArrayList<Student> getList() {
    return this.list;
  }
  
  public ArrayList<Student> getListByName(String userId) {
    ArrayList<Student> results = new ArrayList<>();
    for (Student student : list) {
      if (student.getName().equals(userId)) {
        results.add(student);
      }
    }
    return results;
  }
  
  synchronized public void insert(Student student) {
    list.add(student);
    changed = true;
  }
  
  synchronized public void update(Student student) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getEmail().equals(student.getEmail())) {
        list.set(i, student);
        changed = true;
        return;
      }
    }
  }
  
  synchronized public void delete(String userId) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getUserId().equals(userId)) {
        list.remove(i);
        changed = true;
        break;
      }
    }
  }
  
  public boolean existUserId(String userId) {
    for (Student student : list) {
      if (student.getUserId().toLowerCase().equals(userId.toLowerCase())) {
        return true;
      }
    }
    return false;
  }
}
