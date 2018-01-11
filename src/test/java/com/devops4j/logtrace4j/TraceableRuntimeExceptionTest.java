package com.devops4j.logtrace4j;

import com.devops4j.interfaces.EnumIntegerCode;
import com.devops4j.interfaces.EnumStringCode;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by devops4j on 2018/1/11.
 */
public class TraceableRuntimeExceptionTest {
     enum Error1 implements EnumStringCode{
        FAIL("-1","失败");
        String code;
        String desc;

        Error1(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }

    enum Error2 implements EnumIntegerCode{
        FAIL(-2,"失败2");
        int code;
        String desc;

        Error2(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }

    @Test(expected = TraceableRuntimeException.class)
    public void testGetContext1() throws Exception {
        throw ErrorContextFactory.instance()
                .code("000", "success")
                .addSubError(Error1.FAIL)
                .addSubError(Error2.FAIL)
                .addSubError("1", "msg1")
                .message("message{}", "-------------")
                .solution("solution{}", "-------------")
                .activity("activity{}", "-------------")
                .runtimeException();
    }

//    @Test
//    public void testGetContext2() throws Exception {
//        throw ErrorContextFactory.instance()
//                .code(Error1.FAIL)
//                .addSubError("1", "msg1")
//                .message("message{}", "-------------")
//                .solution("solution{}", "-------------")
//                .activity("activity{}", "-------------")
//                .runtimeException();
//    }
}