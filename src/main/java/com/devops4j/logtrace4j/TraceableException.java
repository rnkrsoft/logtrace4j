package com.devops4j.logtrace4j;

/**
 * Created by devops4j on 2017/1/6.
 * 日志跟踪异常
 */
public class TraceableException extends Exception {
    ErrorContext context;

    public TraceableException(String code, String desc, String activity, String message, String solution, Throwable cause) {
        this(ErrorContextFactory.instance().code(code, desc).activity(activity).message(message).solution(solution).cause(cause));
    }

    public TraceableException(ErrorContext context) {
        this.context = context;
        if (this.context == null) {
            this.context = ErrorContextFactory.instance();
        }
    }

    public ErrorContext getContext() {
        return context;
    }

    @Override
    public String toString() {
        return this.context.toString();
    }
}
