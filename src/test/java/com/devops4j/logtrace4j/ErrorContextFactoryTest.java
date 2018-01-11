package com.devops4j.logtrace4j;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by devops4j on 2017/11/19.
 */
public class ErrorContextFactoryTest {

    void inner(){
        ErrorContext ctx = ErrorContextFactory.instance();
        ctx.message("{}.{}", "1", "2").solution("错误{}", "xxxx").activity("测试{}", "1");
        ctx.throwError();
    }

    void outter(){
        try {
            inner();
        }catch (Exception e){
            ErrorContext ctx = ErrorContextFactory.instance();
            ctx.message("{}.{}1", "1", "2").solution("错误1{}", "xxxx").activity("测试1{}", "1").cause(e);
            ctx.throwError();
        }
    }

    void inner1(){
       String name = null;
        name.toString();
    }

    void outter1(){
        try {
            inner1();
        }catch (Exception e){
            ErrorContext ctx = ErrorContextFactory.instance();
            ctx.message("{}.{}1", "1", "2").solution("错误1{}", "xxxx").activity("测试1{}", "1").cause(e);
            ctx.throwError();
        }
    }
    @Test
    public void testInstance2() throws Exception {
        try {
            outter();
            Assert.fail();
        }catch (TraceableRuntimeException e){
            System.out.println(e);
        }
    }
    @Test
    public void testInstance3() throws Exception {
        try {
            outter1();
            Assert.fail();
        }catch (TraceableRuntimeException e){
            System.out.println(e);
        }
    }
    @Test
    public void testInstance() throws Exception {
       try {
           ErrorContext ctx = ErrorContextFactory.instance();
           ctx.message("{}.{}", "1", "2").solution("错误{}", "xxxx").activity("测试{}", "1");
           ctx.throwError();
           Assert.fail();
       }catch (TraceableRuntimeException e){
           System.out.println(e);
       }
    }

    @Test
    public void testInstance4() throws Exception {
        try {
            ErrorContext ctx = ErrorContextFactory.instance();
            ctx.message("{}.{}", "1", "2")
                    .code("0001", "错误提示{}", 1)
                    .addSubError(new SubError("0001", "错误提示{}", 1))
                    .addSubError(new SubError("0002", "错误提示{}", 2))
                    .extra("信息", "错误提示{}.{}", "sdsd", 1)
                    .solution("错误{}", 1)
                    .activity("测试{}", 1);
            throw ctx.runtimeException();
        }catch (TraceableRuntimeException e){
            System.out.println(e);
        }
    }
    @Test
    public void testStore() throws Exception {
        ErrorContext ctx = ErrorContextFactory.instance();
        ctx.message("{}.{}", "1", "2")
                .code("0001", "错误提示{}", 1)
                .addSubError(new SubError("0001", "错误提示{}", 1))
                .addSubError(new SubError("0002", "错误提示{}", 2))
                .extra("信息", "错误提示{}.{}", "sdsd", 1)
                .solution("错误{}", 1)
                .activity("测试{}", 1);
        ErrorContextFactory.store();
        ErrorContext ctx1 = ErrorContextFactory.instance();
        System.out.println(ctx1);
        ErrorContext ctx2 = ErrorContextFactory.recall();
        System.out.println(ctx2);
        Assert.assertEquals(2, ctx.getSubErrorSize());
    }
}