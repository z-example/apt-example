# APT

APT是在编译器时生成类, 不影响性能。

因为是是在编译时生成类， 所以这个例子分成两个模块，一个是gen模块一个是test模块

gen模块提供了javax.annotation.processing.Processor的实现

```
$ javac -cp xxx\gen\build\libs\gen-1.0.jar -d generated   java\org\example\apt\HelloMessage.java
```
