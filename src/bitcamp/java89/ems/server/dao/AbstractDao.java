package bitcamp.java89.ems.server.dao;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public abstract class AbstractDao<T> {
  private String filename;
  protected ArrayList<T> list;
  
  // 파일명을 객체 생성 후 외부에서 주입 받는다.
  public void setFilename(String filename) {
    this.filename = filename;
  }

  @SuppressWarnings("unchecked")
  public void load() throws Exception {
  //이젠 바깥쪽에 해야하므로 공개해야한다.
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