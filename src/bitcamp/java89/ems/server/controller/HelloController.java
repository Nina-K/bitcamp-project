package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.HashMap;

import bitcamp.java89.ems.server.AbstractCommand;

public class HelloController extends AbstractCommand {
  @Override
  public String getCommandString() {
    return "hello";  //자신이 무슨 일을 수행하는지 이름표를 달아준다
  }

  @Override
  protected void doResponse(HashMap<String,String> paramMap, PrintStream out) throws Exception {
    out.println("안녕하세요~!");
  }
}
