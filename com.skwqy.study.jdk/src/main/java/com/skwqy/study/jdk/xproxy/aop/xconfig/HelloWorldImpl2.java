package com.skwqy.study.jdk.xproxy.aop.xconfig;

public class HelloWorldImpl2 implements HelloWorld
{
    @Override
    public void printHelloWorld()
    {
        System.out.println("Enter HelloWorldImpl2.printHelloWorld()");
    }
    
    @Override
    public void doPrint()
    {
        System.out.println("Enter HelloWorldImpl2.doPrint()");
        return ;
    }
}