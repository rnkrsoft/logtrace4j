package com.devops4j.logtrace4j;


import com.devops4j.message.MessageFormatter;

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
        return MessageFormatter.format(format, args);
    }
}
