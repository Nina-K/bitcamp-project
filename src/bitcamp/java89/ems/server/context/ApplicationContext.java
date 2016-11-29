/*역할 : 빈 컨테이너(Bean Container) == IoC 컨테이너 
 * => bean = object = instance
 * => 객체의 생성과 소멸을 관리한다.
 * => 또한 객체가 사용하는 의존 객체 주입을 수행한다.
 *    (스프링에서는 이걸 모두 IoC 컨테이너라고 한다)
 *    */

package bitcamp.java89.ems.server.context;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import bitcamp.java89.ems.server.annotation.Component;

public class ApplicationContext {
  HashMap<String,Object> objPool = new HashMap<>(); //objPool에는 객체가 담겨있다
  // 패키지가 주어지면 해당 클래스들을 찾아서 패키지 안에 있는 클래스를 모두 객체를 생성해서 objPool에 담는다.
  
  public ApplicationContext(String[] packages) { //생성자에 배열로 패키지명을 받는다
    ArrayList<Class<?>> classList = getClassLis(packages);
    prepareObjects(classList); //그 클래스 내용을 가지고 내용을 뽑는다
    injectDependencies();
  }

  public Object getBean(String name) {
    return objPool.get(name);
    }
  
  // 이 보관소에 저장된 모든 객체를 리턴한다.
  public Collection<Object> getAllBeans() {
    return objPool.values();
  }
  
  private void injectDependencies() {
    // HashMap에 저장된 객체 목록을 뽑아 온다.
    Collection<Object> objects = objPool.values();
    for (Object obj : objects) {
      System.out.println(obj.getClass().getName());
      
      //각 객체의 public 메서드 목록을 뽑는다.
      Method[] methods = obj.getClass().getMethods();
      for (Method m : methods) {
        if (!m.getName().startsWith("set")) { //set이라는 메서드가 있다는건 꼽아달라는 것, 때문에 셋터가 아니면 제외시킨다
          continue;
        }
        
        if (m.getParameterCount() != 1)  { //파라미터가 1개가 아니라면 제외시킨다
          continue;
        }
        
        // 셋터의 0번째 파라미터 타입을 알아낸다.
        Class<?> paramType = m.getParameterTypes()[0]; //리턴하는 게 배열
        
        // 그 타입에 해당하는 객체를 ObjPool에서 찾는다.
        Object dependency = findDependency(paramType);
        
        
        if (dependency != null) { // 찾았다면
          try {
            System.out.println("==>" + m.getName());
            m.invoke(obj, dependency);} catch (Exception e) {} // 의존 객체를 주입하기 위해 셋터를 호출한다.
        }
      }
    }
  }

  private Object findDependency(Class<?> paramType) {
    Collection<Object> objects = objPool.values();
    for (Object obj : objects) {
      if (paramType.isInstance(obj)){
        return obj;
      }
    }
    return null;
  }

  private ArrayList<Class<?>> getClassLis(String[] packages) {
    ArrayList<Class<?>> classList = new ArrayList<>();

    for (String packageName : packages) {

      try {
        findClasses(packageNameToFile(packageName),classList); //클래스 목록을 가져와라
        //packageNameToFile(packageName) 여기에서 찾아서, classList(빈바구니)에 담는다.
        //이런식으로 리펙토링해나가라
        //packageNameToFile(packageName),classList를 넘기면 그걸 처리해줌 재귀호출하면서 ArrayList에 클래스 정보를 담아줌
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return classList;
  }

  private void prepareObjects(ArrayList<Class<?>> classList) {
    for (Class<?> clazz : classList) {
      try { //예외가 떴으면 예외가 뜬 클래스만 무시하자
        Object obj = clazz.newInstance(); //클래스에 대해서 인스턴스 생성
        
        // 클래스에 태깅된 Component 애노테이션 정보를 꺼낸다.
        Component compAnno = clazz.getAnnotation(Component.class);
        
        // 애노테이션의 값을 저장할 때는 변수처럼, 값을 꺼낼 때는 메서드처럼 사용한다.
        if (compAnno.value().length() == 0) { // 빈 문자열이면,
          objPool.put(clazz.getName(), obj); // 클래스 이름으로 객체를 저장하고,
          System.out.println(clazz.getName());
        } else {
          objPool.put(compAnno.value(), obj); // 애노테이션에 기록한 이름으로 객체를 저장한다.
          System.out.println(compAnno.value());
        }                                     // => Component라는 애노테이션을 사용하는 이유
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private File packageNameToFile(String packageName) {
    // 클래스 파일을 찾을 디렉토리 경로를 정의한다.
    // 그런데 파라미터로 넘어오는 값은 순수한 패키지 이름(예:bitcamp.java89.ems.server.controller)으로 되어있다.
    // 그래서 다음과 같이 파일 경로로 만들려면 "."을 "/"로 변경해야한다.
    // 예) ./bin/bitcamp/java89/ems/server/cotroller
    return new File("./bin/" + packageName.replaceAll("\\.","/")); // .을 /로 바꿔라 //변수명도 필요없이 리턴함
             //기준점은 현재 폴더에서 ./bin/
  }

  private void findClasses(File dir, ArrayList<Class<?>> classList) throws Exception {
    //Class<?> : 그 클래스가 어느 클래스인지 상관없다.

    if (!dir.isDirectory()) {
      return;
    }

    File[] files = dir.listFiles(new FileFilter() {
      @Override
      public boolean accept(File pathname) {
        if (pathname.isDirectory())
          return true;
        if (pathname.getName().endsWith(".class") && !pathname.getName().contains("$")) 
          return true;
        return false;

      } 
    }); 

    for (File file : files) {
      if (file.isDirectory()) {
        findClasses(file, classList);
      } else {
        try {
          Class<?> c = loadClass(file);
         
          // 구체적으로 어떤 클래스인지 알 수 없고, 아무거나 다 받아야 하므로 제네릭으로 하는 경우, <?>하는 게 좋다.
          // 이제 인스턴스를 무조건 다 만들 것이다. 단, 추상클래스가 아닌 것들만! 

          if (!isAbstract(c) && isComponent(c)) {
            //추상클래스 아니어야하고 두 조건 모두 true여야 한다. 이 때 리스트를 뽑아낸다.
            classList.add(c); // 아니라면 클래스에 추가시켜라
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  private boolean isComponent(Class<?> c) {
    // @Component 애너테이션이 있다면 리턴 값은 null이 아니다.
    return c.getAnnotation(Component.class) != null;
    //왜? Component라는 애노테이션 태크가 클래스에 붙었는지 아닌지 알고싶은 것!
    //클래스, 인터페이스, 애노테이션 모두 내부적으로는 "클래스"로 취급한다.
  }

  private Class<?> loadClass(File file) throws IOException, ClassNotFoundException {
    String path = file.getCanonicalPath().replaceAll("\\\\", "/")
        .replaceAll(".class","");
    int pos = path.indexOf("/bin/");
    return Class.forName(path.substring(pos + 5).replaceAll("/", "."));
    //굳이 임시변수 쓸 필요 없으므로 정리한다
  }

  private boolean isAbstract(Class<?> clazz) {
    if ((clazz.getModifiers() & Modifier.ABSTRACT) == Modifier.ABSTRACT) {
      return true; //다시 for문으로 올라가라
    }
    return false;
  }

  // 이 클래스만 테스트하고 싶을 때 main메소드를 만들어서 테스트해보면 된다.
//  public static void main(String[] args) throws Exception {
//    ApplicationContext appContext = new ApplicationContext(new String[]{
//        "bitcamp.java89.ems.server.controller",
//    "bitcamp.java89.ems.server.dao"});
//  }
}

/*실행결과
 * C:\Users\BitCamp\git\bitcamp-project\bin\bitcamp\java89\ems\server\controller
   C:\Users\BitCamp\git\bitcamp-project\bin\bitcamp\java89\ems\server\dao  */