package com.devops4j.logtrace4j;

import javax.interfaces.EnumIntegerCode;
import javax.interfaces.EnumBase;
import javax.interfaces.EnumStringCode;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.*;
import java.util.*;

/**
 * Created by devops4j on 2017/1/6.
 */
public class SimpleErrorContext implements ErrorContext {
    ErrorContext stored;
    /**
     * 错误码
     */
    String code;
    /**
     * 错误码描述
     */
    String desc;
    /**
     * 正在进行的行为
     */
    PlaceHolder activity;
    /**
     * 发生错误信息说明
     */
    PlaceHolder message;
    /**
     * 解决方案
     */
    PlaceHolder solution;
    /**
     * 额外提示信息的键值
     */
    Map<String, PlaceHolder> extras = new HashMap();
    /**
     * 自错误信息
     */
    final Map<String, Error> subErrors = new HashMap();
    /**
     * 导致错误的异常原因
     */
    Throwable cause;


    public ErrorContext stored(ErrorContext ctx) {
        this.stored = ctx;
        return this;
    }

    public ErrorContext stored() {
        return this.stored;
    }

    public ErrorContext code(String code, String format, Object... args) {
        this.code = code;
        PlaceHolder placeHolder = new PlaceHolder(format, args);
        this.desc = format;
        this.desc = placeHolder.toString();
        return this;
    }

    public ErrorContext code(EnumBase code) {
        if(code instanceof EnumStringCode){
            EnumStringCode stringCode = (EnumStringCode)code;
            this.code = stringCode.getCode();
            this.desc = stringCode.getDesc();
            return this;
        }else if(code instanceof EnumIntegerCode){
            EnumIntegerCode stringCode = (EnumIntegerCode)code;
            this.code = Integer.toString(stringCode.getCode());
            this.desc = stringCode.getDesc();
            return this;
        }else {
            this.desc = code.getDesc();
            return this;
        }
    }

    public ErrorContext activity(String format, Object... args) {
        if (this.activity == null) {
            this.activity = new PlaceHolder(format, args);
        }
        this.activity.format = format;
        this.activity.args = args;
        return this;
    }

    public ErrorContext solution(String format, Object... args) {
        if (this.solution == null) {
            this.solution = new PlaceHolder(format, args);
        }
        this.solution.format = format;
        this.solution.args = args;
        return this;
    }

    public ErrorContext message(String format, Object... args) {
        if (this.message == null) {
            this.message = new PlaceHolder(format, args);
        }
        this.message.format = format;
        this.message.args = args;
        return this;
    }

    public ErrorContext extra(String name, String format, Object... args) {
        PlaceHolder extra = new PlaceHolder(format, args);
        extras.put(name, extra);
        return this;
    }

    public int subErrorSize() {
        return subErrors.size();
    }

    public ErrorContext addSubError(SubError subError) {
        subErrors.put(subError.getCode(), subError);
        return this;
    }

    public ErrorContext addSubError(String code, String format, Object... args) {
        SubError subError = new SubError(code, new PlaceHolder(format, args).toString());
        return this;
    }

    public ErrorContext addSubError(EnumStringCode error) {
        SubError subError = new SubError(error.getCode(), error.getDesc());
        subErrors.put(subError.getCode(), subError);
        return this;
    }

    public List<String> getSubErrorCodes() {
        List<String> codes = new ArrayList(subErrors.keySet());
        Collections.sort(codes);
        return Collections.unmodifiableList(codes);
    }

    public String getSubErrorDesc(String subErrorCode) {
        return subErrors.get(subErrorCode).getCode();
    }

    public ErrorContext addSubError(EnumIntegerCode error) {
        SubError subError = new SubError(Integer.toString(error.getCode()), error.getDesc());
        subErrors.put(subError.getCode(), subError);
        return this;
    }

    public ErrorContext cause(Throwable cause) {
        if (cause != null && cause.getClass() != TraceableRuntimeException.class) {
            this.cause = cause;
        } else {
            //这里没必要，有可能导致死循环
        }
        return this;
    }

    public ErrorContext reset() {
        this.code = null;
        this.desc = null;
        this.activity = null;
        this.message = null;
        this.solution = null;
        this.subErrors.clear();
        this.cause = null;
        return this;
    }

    public RuntimeException runtimeException() {
        return new TraceableRuntimeException(this);
    }

    public Exception exception() {
        return new TraceableException(this);
    }

    public void throwError() {
        throw new TraceableRuntimeException(this);
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(200);
        builder.append("### =============================错误上下文=============================");
        if (this.code != null) {
            builder.append(LINE_SEPARATOR)
                    .append("### Error: ").append(LINE_SEPARATOR)
                    .append("\t")
                    .append(this.code)
                    .append(":")
                    .append(this.desc);
        }
        if (this.subErrors != null && !this.subErrors.isEmpty()) {
            builder.append(LINE_SEPARATOR)
                    .append("### SubErrors: ");
            for (Error subError : this.subErrors.values()) {
                    builder.append(LINE_SEPARATOR)
                            .append("\t")
                            .append(subError.getCode())
                            .append(":")
                            .append(subError.getDesc());
            }

        }
        if (this.activity != null) {
            builder.append(LINE_SEPARATOR)
                    .append("### Activity: ").append(this.activity);
        }
        if (this.message != null) {
            builder.append(LINE_SEPARATOR)
                    .append("### Message: ").append(this.message);
        }
        if (!this.extras.isEmpty()) {
            for (String name : extras.keySet()) {
                PlaceHolder placeHolder = extras.get(name);
                builder.append(LINE_SEPARATOR)
                        .append("### ")
                        .append(name)
                        .append(": ")
                        .append(placeHolder.toString());
            }
        }
        if (this.solution != null) {
            builder.append(LINE_SEPARATOR)
                    .append("### Solution: ").append(this.solution);
        }
        if (this.cause != null) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(os);
            this.cause.printStackTrace(ps);
            builder.append(LINE_SEPARATOR)
                    .append("### Cause: ").append(os.toString());
        }
        builder.append(LINE_SEPARATOR).append("### ================================结束================================");
        return builder.toString();
    }
}
