/* 생성자에서 다른 생성자 호출하기
    =>this()사용전
*/
package step10;

public class Exam67_0 {

  static class Book {
    String title;
    String author;
    String press;
    int pages;
    boolean local;

    public Book() {}

    public Book(String title) {
      this.title = title;
    }

    public Book(String title, String press) {
      this.title = title;
      this.press = press;
    }

    public Book(String title, String press, String author, int pages) {
      this.title = title;
      this.press = press;
      this.author = author;
      this.pages = pages;
    }


  }

  public static void main(String[] args) {
    Book book = new Book();
    Book book2 = new Book("자바프로그래밍");
    Book book3 = new Book("자바프로그래밍", "비트출판사");
    Book book4 = new Book("자바프로그래밍", "비트출판사", "홍길동", 700);

   }

}
