/* ObjectInputStream와 ObjectOutputStream는 객체형태(ArrayList)를 입출력할 수 있다.
 * list에 있는 내용을 통째로 Input해서, Output으로 담은 것 전체를 출력해주는 것
 * list = (ArrayList<Contact>)in.readObject();
   => readObject가 Contact 형태의 ArrayList이다(타입캐스팅). 이걸 list에 담아라
 * out.writeObject(list); - 통째로 출력
 * */

package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import bitcamp.java89.ems.server.AbstractCommand;
import bitcamp.java89.ems.server.dao.ContactDao;
import bitcamp.java89.ems.server.vo.Contact;

public class ContactListController extends AbstractCommand {
  //의존 객체 DAO를 저장할 변수 선언
  ContactDao contactDao;

  // 의존 객체 주입할 때 호출할 셋터 추가
  public void setContactDao(ContactDao contactDao) {
    this.contactDao = contactDao;
  }

  @Override
  public String getCommandString() {
    return "contact/list"; //자신이 무슨 일을 수행하는지 이름표를 달아준다
  }

  /*한 클래스에 대해서 하나의 명령어만 집중하면 된다.
  EduAppServer가 호출하게 되어있다.
  (앞으로도 호출자가 누구인지를 확실히 찾아야한다.) (내가 구현하는 입장인가, 사용하는 입장인가) */ 
  @Override
  protected void doResponse(HashMap<String,String> paramMap, PrintStream out) throws Exception {
    // 주입 받은 ContactDao를 사용할 것이기 때문에 더 이상 이 메서드에서 ContactDao 객체를 준비하지 않는다.
    // => 단 이 메서드가 호출되기 전에 반드시 ContactDao가 주입되어 있어야 한다.
    ArrayList<Contact> list = contactDao.getList();
    for (Contact contact : list) {
      out.printf("%s, %s, %s, %s\n",
          contact.getName(),
          contact.getPosition(),
          contact.getTel(),
          contact.getEmail());
    }
  }

}

