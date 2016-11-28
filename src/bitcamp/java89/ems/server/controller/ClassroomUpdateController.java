package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.HashMap;

import bitcamp.java89.ems.server.AbstractCommand;
import bitcamp.java89.ems.server.dao.ClassroomDao;
import bitcamp.java89.ems.server.vo.Classroom;

public class ClassroomUpdateController extends AbstractCommand {
  // 의존 객체 DAO를 저장할 변수 선언
  ClassroomDao classroomDao;

  // 의존 객체 주입할 때 호출할 셋터 추가
  public void setClassroomDao(ClassroomDao classroomDao) {
    this.classroomDao = classroomDao;
  }

  @Override
  public String getCommandString() {
    return "classroom/update";  //자신이 무슨 일을 수행하는지 이름표를 달아준다
  }

  @Override
  protected void doResponse(HashMap<String,String> paramMap, PrintStream out) throws Exception {
    // 주입 받은 ClassroomDao를 사용할 것이기 때문에 더 이상 이 메서드에서 ClassroomDao 객체를 준비하지 않는다.
    // => 단 이 메서드가 호출되기 전에 반드시 ContactDao가 주입되어 있어야 한다.
    if (!classroomDao.existName(paramMap.get("name"))) {
      out.println("이름을 찾지 못했습니다.");
      return;
    }

    Classroom classroom = new Classroom();
    classroom.setName(paramMap.get("name"));
    classroom.setFloors(Integer.parseInt(paramMap.get("floors")));
    classroom.setMaximumSeating(Integer.parseInt(paramMap.get("maximumSeating")));
    classroom.setMaximumPeriod(paramMap.get("maximumPeriod"));
    classroom.setTime(paramMap.get("time"));
    classroom.setPassword(paramMap.get("password"));
    classroom.setProjector((paramMap.get("projector").equals("y")) ? true : false);
    classroom.setLocker((paramMap.get("locker").equals("y")) ? true : false);
    classroom.setConditioner((paramMap.get("conditioner").equals("y")) ? true : false);

    classroomDao.update(classroom);
    out.println("저장하였습니다.");
  }
}


