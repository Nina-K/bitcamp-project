/* command 인터페이스를 구현한 클래스를 찾아내기 위해(클래스 로딩을 위해)
 * (이너클래스나 다른 파일 제외한) 순수한 클래스 이름만을 추출해서 저장해둔다. */

package bitcamp.java89.ems.server;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public class ReflectionUtil {
 
  
//  public static void main(String[] args) throws Exception {
//    File file = new File("./bin"); //현재폴더와 그 밑의 bin폴더를 찾음
//    displayFilename(file);
//  }

  public static void getCommandClasses(File dir, ArrayList<Class> classList) {
    //dir : 디렉토리 //getCommandClasses : 클래스정보추출 //클래스 이름을 담음 //클래스 정보를 담는 어레이리스트 바구니
    //클래스 인스턴스를 만들지않고 static을 붙여서 클래스 이름으로 직접 호출이 가능하다
    // !!! ArrayList<Class> classList - 빈가방을 가져와서 담는 것 !!!
    if (!dir.isDirectory()) {
      //isDirecotory() = 디렉토리가 아니라면
      return; //떠나라, 실행하지마라
    }


    //  class MyFilefilter implements FileFilter {
    //    @Override
    //    public boolean accept(File pathname) {
    //      return false;
    //    }
    //  }

    //  FileFilter filter = new FileFilter() {
    //    @Override
    //    public boolean accept(File pathname) {
    //      return false;
    //    } 
    //  };
    //할당문의 끝, 정의는 ; 세미콜론이므로 세미콜론이 나와야한다.
    File[] files = dir.listFiles(new FileFilter() {
      @Override
      public boolean accept(File pathname) {
        if (pathname.isDirectory())
          return true;
        if (pathname.getName().endsWith(".class") && !pathname.getName().contains("$")) //$는 이너클래스라서 제외
          // 두 개의 조건을 한 번에
          return true;
        return false; //그밖에는 통과하지 말것
        /*메소드를 호출할 때마다 클래스가 정의되는 것이 아니라,
         *컴파일하는 과정에 테스트와 익명 클래스가 분리되어서 만들어지고,
         *이 자리(dir.listFiles(new 이자리())에는 익명 클래스의 객체를 만드는 코드로 바뀐다는 것 */
      } //FileFilter 클래스 코드를 주고받을 수 없다.
    }); 

    for (File file : files) {
      if (file.isDirectory()) {
        getCommandClasses(file, classList); //재귀호출 //재귀호출할 때 바구니를 같이 줘야하는 것
        /*어느 디렉토리를 뒤질지 정보를 달라 , 디렉토리를 뒤져서 Ab 를 하는 걸 담아줄테니 바구니를 달라 */
      } else {
        // 파일명에서 "\\"를 "/"로 바꿔서 OS 간의 차이가 없도록 한다.
        String path = file.getAbsolutePath().replaceAll("\\\\", "/")
                                            .replaceAll(".class",""); // => "패키지이름.클래스"가 완벽하게 만들어진다 // .class 확장자 제거
        // "패키지명 + 클래스명"만 추출
        int pos = path.indexOf("/bin/");
        //위치, 포지션이 나올 것(int pos), 문자열이 시작하는 위치를 찾은 것 /./
        
        //System.out.println(path.substring(pos + 6).replaceAll("/", ".")); //우리가 의도한대로 뽑아냈는지 출력해서 확인
        //classnames.add(path.substring(pos + 6).replaceAll("/", ".")); // => 이렇게까지해야 깔끔하게 패키지명이 나옴
        
        try {
          // "패키지명 + 클래스명"에 해당하는 클래스파일을 찾아서 Method Area 영역에 로딩한다.
          // => 리턴 값은 로딩된 클래스의 정보이다.
          //System.out.println(path); //클래스 파일의 전체 경로 
          String classname = path.substring(pos + 5).replaceAll("/", "."); // Fully-qualified class name
          Class c = Class.forName(classname);
        
        // 로딩된 클래스가 AbstractCommand를 상속받았는지 검사한다.
        // 이 클래스가 구현한 인터페이스 목록을 추출한다.
        //Class[] interfaceList = c.getInterfaces(); //(직접 구현해 낸) 목록을 뽑아냄
        Class superClass = c.getSuperclass();
        // 어떤 인터페이스를 구현했는지 궁금해짐
        
        if (superClass == AbstractCommand.class) {
          //System.out.println("=>>>" + c.getName());
          classList.add(c); // AbstractCommand의 서브 클래스라면 목록에 저장한다.
          }
        } catch (Exception e) {
          //만약 클래스를 로딩하지 못하면 무시한다.
          e.printStackTrace();
        }
        //classnames에 add로 담아낸다 (ArrayList에 담김)
        //pos 위치부터 끝까지 나오도록
      }
    }
    //()안에 아무것도 안주면, 현재 디렉토리의 모든 파일을 리턴한다.
    /*필터는 내가 만들어서 파라미터에 넣어줘야한다.
                              파라미터에는 클래스 자체를 주고받는게 아닌, 인스턴스의 주소를 주고받는 것
                              때문에 클래스를 만든 후 인스턴스를 만들어 인스턴스의 주소를 주는 것*/
  }
}
