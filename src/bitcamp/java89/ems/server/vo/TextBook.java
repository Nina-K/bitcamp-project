package bitcamp.java89.ems.server.vo;

public class TextBook {
  //인스턴스 변수
  String title;
  String author;
  String press;
  String releaseDate;
  String language;
  String description;
  int page;
  int price;

  public TextBook(){
    this("자바의 정석", "남궁성", 30000);
  }

  public TextBook(String title, String author, int price){
    this.title = title;
    this.author = author;
    this.price = price;
  }

}
