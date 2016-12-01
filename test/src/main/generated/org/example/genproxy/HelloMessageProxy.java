package org.example.genproxy;

//这个文件是在编译时生成的
public class HelloMessageProxy extends org.example.apt.HelloMessage{
//{enableProxyGen=yes}
//...
  public void echo() {
      System.out.println("--------------before--------------");
      super.echo();
      System.out.println("--------------after--------------");
  }
}