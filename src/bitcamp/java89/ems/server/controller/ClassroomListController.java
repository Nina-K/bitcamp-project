package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import bitcamp.java89.ems.server.AbstractCommand;
import bitcamp.java89.ems.server.dao.ClassroomDao;
import bitcamp.java89.ems.server.vo.Classroom;

public class ClassroomListController extends AbstractCommand {
  // 의존 객체 DAO를 저장할 변수 선언
  ClassroomDao classroomDao;

  // 의존 객체 주입할 때 호출할 셋터 추가
  public void setClassroomDao(ClassroomDao classroomDao) {
    this.classroomDao = classroomDao;
  }

  @Override
  public String getCommandString() {
    return "classroom/list";  //자신이 무슨 일을 수행하는지 이름표를 달아준다
  }

  @Override
  protected void doResponse(HashMap<String,String> paramMap, PrintStream out) throws Exception {
    // 주입 받은 ClassroomDao를 사용할 것이기 때문에 더 이상 이 메서드에서 ClassroomDao 객체를 준비하지 않는다.
    // => 단 이 메서드가 호출되기 전에 반드시 ContactDao가 주입되어 있어야 한다.
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
}
