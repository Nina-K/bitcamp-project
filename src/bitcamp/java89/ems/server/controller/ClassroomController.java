package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import bitcamp.java89.ems.server.annotation.Component;
import bitcamp.java89.ems.server.annotation.RequestMapping;
import bitcamp.java89.ems.server.annotation.RequestParam;
import bitcamp.java89.ems.server.dao.ClassroomDao;
import bitcamp.java89.ems.server.vo.Classroom;

@Component // ApplicationContext가 관리하는 대상 클래스임을 태킹(표시)한다.
public class ClassroomController {
  // 의존 객체 DAO를 저장할 변수 선언
  ClassroomDao classroomDao;

  // 의존 객체 주입할 때 호출할 셋터 추가
  public void setClassroomDao(ClassroomDao classroomDao) {
    this.classroomDao = classroomDao;
  }

  @RequestMapping(value="classroom/add")
  public void add(
      @RequestParam("name") String name,
      @RequestParam("floors") int floors,
      @RequestParam("maximumSeating") int maximumSeating,
      @RequestParam("maximumPeriod") String maximumPeriod,
      @RequestParam("time") String time,
      @RequestParam("password") String password,
      @RequestParam("projector") boolean projector,
      @RequestParam("locker") boolean locker,
      @RequestParam("conditioner") boolean conditioner,
      PrintStream out) throws Exception {
    // 주입 받은 ClassroomDao를 사용할 것이기 때문에 더 이상 이 메서드에서 ClassroomDao 객체를 준비하지 않는다.
    // => 단 이 메서드가 호출되기 전에 반드시 ContactDao가 주입되어 있어야 한다.
    if (classroomDao.existName(name)) {
      out.println("같은 강의실이 존재합니다. 등록을 취소합니다.");
      return;
    }

    Classroom classroom = new Classroom();
    classroom.setName(name);
    classroom.setFloors(floors);
    classroom.setMaximumSeating(maximumSeating);
    classroom.setMaximumPeriod(maximumPeriod);
    classroom.setTime(time);
    classroom.setPassword(password);
    classroom.setProjector(projector);
    classroom.setLocker(locker);
    classroom.setConditioner(conditioner);

    classroomDao.insert(classroom);
    out.println("등록하였습니다.");
  }

  @RequestMapping(value="classroom/delete")
  public void delete(@RequestParam("name") String name, PrintStream out) throws Exception {
    // 주입 받은 ClassroomDao를 사용할 것이기 때문에 더 이상 이 메서드에서 ClassroomDao 객체를 준비하지 않는다.
    // => 단 이 메서드가 호출되기 전에 반드시 ContactDao가 주입되어 있어야 한다.
    if (!classroomDao.existName(name)) {
      out.println("해당 데이터가 없습니다.");
      return;
    }

    classroomDao.delete(name);
    out.println("해당 데이터 삭제 완료하였습니다.");
  }

  @RequestMapping(value="classroom/list")
  public void list(HashMap<String,String> paramMap, PrintStream out) throws Exception {
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

  @RequestMapping(value="classroom/update")
  public void update(
      @RequestParam("name") String name,
      @RequestParam("floors") int floors,
      @RequestParam("maximumSeating") int maximumSeating,
      @RequestParam("maximumPeriod") String maximumPeriod,
      @RequestParam("time") String time,
      @RequestParam("password") String password,
      @RequestParam("projector") boolean projector,
      @RequestParam("locker") boolean locker,
      @RequestParam("conditioner") boolean conditioner, PrintStream out) throws Exception {
    // 주입 받은 ClassroomDao를 사용할 것이기 때문에 더 이상 이 메서드에서 ClassroomDao 객체를 준비하지 않는다.
    // => 단 이 메서드가 호출되기 전에 반드시 ContactDao가 주입되어 있어야 한다.
    if (!classroomDao.existName(name)) {
      out.println("이름을 찾지 못했습니다.");
      return;
    }

    Classroom classroom = new Classroom();
    classroom.setName(name);
    classroom.setFloors(floors);
    classroom.setMaximumSeating(maximumSeating);
    classroom.setMaximumPeriod(maximumPeriod);
    classroom.setTime(time);
    classroom.setPassword(password);
    classroom.setProjector(projector);
    classroom.setLocker(locker);
    classroom.setConditioner(conditioner);

    classroomDao.update(classroom);
    out.println("저장하였습니다.");
  }

  @RequestMapping(value="classroom/view")
  public void view(@RequestParam(value="name") String name, PrintStream out) throws Exception {
    // 주입 받은 ClassroomDao를 사용할 것이기 때문에 더 이상 이 메서드에서 ClassroomDao 객체를 준비하지 않는다.
    // => 단 이 메서드가 호출되기 전에 반드시 ContactDao가 주입되어 있어야 한다.
    ArrayList<Classroom> list = classroomDao.getListByName(name);
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
}

