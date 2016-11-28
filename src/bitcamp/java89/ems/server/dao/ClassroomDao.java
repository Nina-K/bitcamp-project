/* ObjectInputStream와 ObjectOutputStream는 객체형태(ArrayList)를 입출력할 수 있다.
 * list에 있는 내용을 통째로 Input해서, Output으로 담은 것 전체를 출력해주는 것
 * list = (ArrayList<Classroom>)in.readObject();
   => readObject가 Classroom 형태의 ArrayList이다(타입캐스팅). 이걸 list에 담아라
 * out.writeObject(list); - 통째로 출력
 * */

package bitcamp.java89.ems.server.dao;

import java.util.ArrayList;

import bitcamp.java89.ems.server.vo.Classroom;

public class ClassroomDao extends AbstractDao<Classroom> {
 
  public ArrayList<Classroom> getList() {
    return this.list;
  }

  public ArrayList<Classroom> getListByName(String name) { // doView 역할
    ArrayList<Classroom> results = new ArrayList<>();
    for (Classroom classroom : list) {
      if (classroom.getName().equals(name)) {
        results.add(classroom);
      }
    }
    return results;
  }


  synchronized public void insert(Classroom classroom) { // doAdd
    list.add(classroom);
    try {this.save();} catch (Exception e) {}
  }

  synchronized public void delete(String name) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getName().equals(name)) {
        list.remove(i);
        try {this.save();} catch (Exception e) {}
        break;
      }
    }
  }

  //update?name=강의실301&floors=4&maximumSeating=400&maximumPeriod=4&time=08:00~14:00&password=4000&projector=n&locker=y&conditioner=y
  synchronized public void update(Classroom classroom) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getName().equals(classroom.getName())) {
        list.set(i, classroom);
        try {this.save();} catch (Exception e) {}
        return;
      }
    } 
  }

  public boolean existName(String name) { 
    for (Classroom classroom : list) {
      if (classroom.getName().toLowerCase().equals(name.toLowerCase())) {
        return true;
      }
    }
    return false;
  }
}
