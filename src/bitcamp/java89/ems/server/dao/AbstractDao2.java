package bitcamp.java89.ems.server.dao;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public abstract class AbstractDao2<T> { //뭐가 올지 알 수 없으니 제네릭 사용 <T> 타입의 T
  //추상메서드가 있니 없니를 떠나서, 지금처럼 제너럴라이제이션 했을 경우 대부분 추상클래스다.
  //자체로 쓰기 위해서가 아니라, 서브 클래스에게 공통 변수와 메서드 용도를 주기 위해서이다.
  //상속 받아서 개발자들이 만들 때 반드시 이 메서드를 쓰라고 강요하고 싶을 때 & 서브 클래스마다 재정의해야할 때
  private String filename;
  protected ArrayList<T> list;

  public AbstractDao2(String filename) { //filename을 무조건 입력받게끔해버리면 무조건할수밖에 없음
    //super();
    this.filename = filename;
  }

  @SuppressWarnings("unchecked")
  protected void load() throws Exception { //load를 자식클래스에게 물려줄거고 자식클래스는 데이터를 읽어들일 것
    try (
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(this.filename));) {

      list = (ArrayList<T>)in.readObject(); //<T>를 저장한 걸 읽어들임

    } catch (EOFException e) {
      //파일을 모두 읽었다.
    } catch (Exception e) {
      e.printStackTrace();
      list = new ArrayList<T>(); //예외가 발생하면 빈 리스트를 만들되, 리스트를 만든 쪽에 오류 발생 보고를 하는 것
      throw new Exception("데이터 로딩 중 오류 발생!");
    }
  }

  public synchronized void save() throws Exception {
    try (
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.filename));) {

      out.writeObject(list); //list를 통째로 출력해버린 것
    } catch (Exception e) {
      throw e; //오류 난 사실을 호출한 쪽에 알려줌
    }
  }

}