package bitcamp.java89.ems;

public class Classroom  {
  String name;
  int floors;
  int maximumSeating;
  String maximumPeriod;
  String time;
  String password;
  boolean projector;
  boolean locker;
  boolean conditioner;

  public Classroom() {}

  public Classroom(String name, int floors, int maximumSeating, String maximumPeriod, String time, String password, boolean projector, boolean locker, boolean conditioner) {
    this.name = name;
    this.floors = floors;
    this.maximumSeating = maximumSeating;
    this.maximumPeriod = maximumPeriod;
    this.time = time;
    this.password = password;
    this.projector = projector;
    this.locker = locker;
    this.conditioner = conditioner;

  }
}
