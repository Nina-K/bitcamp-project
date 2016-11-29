/* ObjectInputStream와 ObjectOutputStream는 객체형태(ArrayList)를 입출력할 수 있다.
 * list에 있는 내용을 통째로 Input해서, Output으로 담은 것 전체를 출력해주는 것
 * list = (ArrayList<Contact>)in.readObject();
   => readObject가 Contact 형태의 ArrayList이다(타입캐스팅). 이걸 list에 담아라
 * out.writeObject(list); - 통째로 출력
 * */

package bitcamp.java89.ems.server.dao;

import java.util.ArrayList;

import bitcamp.java89.ems.server.annotation.Component;
import bitcamp.java89.ems.server.vo.Contact;

@Component // ApplicationContext가 객체를 관리하는 클래스임을 표시하기 위해 태그를 단다.
public class ContactDao extends AbstractDao<Contact> { 
  
  public ContactDao() { //어차피 객체 생성하는 쪽에서 try~catch하니까 예외를 던짐
    this.setFilename("contact-v1.8.data");
      try {this.load();} catch (Exception e) {} //기본 생성자가 생기면 파일 로드하고 처리하도록
      }
  
  public ArrayList<Contact> getList() {
    return this.list;
  }
  
  public ArrayList<Contact> getListByName(String name) { //이름으로 목록을 찾음 / doView 역할
    ArrayList<Contact> results = new ArrayList<>();
    for (Contact contact : list) {
      if (contact.getName().equals(name)) {
        results.add(contact);
      }
    }
    return results;
  }
  
  synchronized public void insert(Contact contact) { //doAdd 역할
    list.add(contact);
    try {this.save();} catch (Exception e) {} //무조건 save한다. 에러 나도 무시
  }
  
  synchronized public void update(Contact contact) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getEmail().equals(contact.getEmail())) {
        list.set(i, contact);
        try {this.save();} catch (Exception e) {} //무조건 save한다. 에러 나도 무시
        return;
      }
    }
  }
  
  synchronized public void delete(String email) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getEmail().equals(email)) {
        list.remove(i);
        try {this.save();} catch (Exception e) {} //무조건 save한다. 에러 나도 무시
        break;
      }
     } 
   } 
  
  public boolean existEmail(String email) { 
    for (Contact contact : list) {
      if (contact.getEmail().toLowerCase().equals(email.toLowerCase())) {
        return true;
      }
    }
    return false;
}
  
  
  
}
    
