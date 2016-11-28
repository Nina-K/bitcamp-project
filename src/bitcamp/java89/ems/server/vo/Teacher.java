package bitcamp.java89.ems.server.vo;

public class Teacher {
  //인스턴스 변수
  String userId;
  String password;
  String name;
  String email;
  String tel;
  int age;
  String subject;
  int carrer;
  int salary;
  String address;

  public Teacher() {}

  public Teacher(String userId, String password, String name, String tel) {
    this.userId = userId;
    this.password = password;
    this.name = name;
    this.tel = tel;
  }


}
