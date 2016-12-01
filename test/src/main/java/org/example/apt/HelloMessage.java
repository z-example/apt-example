package org.example.apt;

/**
 * @author Zero
 *         Created on 2016/12/1.
 */
@ProxyGen
public class HelloMessage implements IEchoMessage{

    public void echo() {
        System.out.println("Hello world!");

    }

}
