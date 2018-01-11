package com.devops4j.logtrace4j;


import com.devops4j.interfaces.EnumBase;
import com.devops4j.interfaces.EnumIntegerCode;
import com.devops4j.interfaces.EnumStringCode;

import java.lang.*;
import java.util.List;

/**
 * Created by devops4j on 2017/1/6.
 * 错误上下文
 */
public interface ErrorContext extends Error {
    String LINE_SEPARATOR = System.getProperty("line.separator", "\n");
    /**
     * 保存一个上下文在当前上下文中
     * @param ctx 上下文
     * @return 上下文
     */
    ErrorContext stored(ErrorContext ctx);

    /**
     * 获取已保存的上下文
     * @return 上下文
     */
    ErrorContext stored();
    /**
     * 设置错误码
     * @param code 错误码
     * @param format 格式
     * @param args 参数
     * @return 上下文对象
     */
    ErrorContext code(String code, String format, Object... args);
    /**
     * 设置错误码
     * @param code 错误码
     * @return 上下文对象
     */
    ErrorContext code(EnumBase code);
    /**
     * 设置正在进行的活动信息
     * @param format 格式
     * @param args 参数
     * @return 上下文对象
     */
    ErrorContext activity(String format, Object... args);

    /**
     * 设置该提示信息对应的解决方案
     * @param format 格式
     * @param args 参数
     * @return 上下文对象
     */
    ErrorContext solution(String format, Object... args);

    /**
     * 设置错误提示信息
     * @param format 格式
     * @param args 参数
     * @return 上下文对象
     */
    ErrorContext message(String format, Object... args);

    /**
     * 额外的提示信息
     * @param name 提示信息的名字
     * @param format 格式
     * @param args 参数
     * @return 上下文对象
     */
    ErrorContext extra(String name, String format, Object... args);

    /**
     * 子错误数量
     * @return 子错误数量
     */
    int subErrorSize();
    /**
     * 增加子错误信息
     * @param error 子错误信息
     * @return 上下文对象
     */
    ErrorContext addSubError(SubError error);

    /**
     * 增加子错误信息
     * @param code 代码
     * @param format 格式
     * @param args 描述参数
     * @return 上下文对象
     */
    ErrorContext addSubError(String code, String format, Object...args);
    /**
     * 增加子错误信息
     * @param error 子错误信息
     * @return 上下文对象
     */
    ErrorContext addSubError(EnumStringCode error);

    /**
     * 获取子错误码
     * @return 错误码列表
     */
    List<String> getSubErrorCodes();

    /**
     * 获取子错误描述
     * @param subErrorCode 子错误码
     * @return 子错误描述
     */
    String getSubErrorDesc(String subErrorCode);
    /**
     * 增加子错误信息
     * @param error 子错误信息
     * @return 上下文对象
     */
    ErrorContext addSubError(EnumIntegerCode error);

    /**
     * 设置导致错误的异常
     * @param cause 错误的异常
     * @return 上下文对象
     */
    ErrorContext cause(Throwable cause);

    /**
     * 错误异常
     * @return 异常
     */
    Throwable getCause();
    /**
     * 重置上下文对象
     * @return 上下文对象
     */
    ErrorContext reset();

    /**
     * 包装为运行时异常
     * @return 运行时异常
     */
    RuntimeException runtimeException();

    /**
     * 包装为受检异常
     * @return 受检异常
     */
    Exception exception();
    /**
     * 抛出异常
     */
    void throwError();
}
