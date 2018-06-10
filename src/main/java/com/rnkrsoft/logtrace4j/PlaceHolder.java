package com.rnkrsoft.logtrace4j;


import com.rnkrsoft.message.MessageFormatter;

/**
 * Created by devops4j on 2017/1/6.
 */
public class PlaceHolder {
    String format;
    Object[] args;

    PlaceHolder(String format, Object[] args) {
        this.format = format;
        this.args = args;
    }

    @Override
    public String toString() {
        return MessageFormatter.format((format == null ? "" : format), (args == null ? new Object[0] : args));
    }
}