/**
 * RNKRSOFT OPEN SOURCE SOFTWARE LICENSE TERMS ver.1
 * - 氡氪网络科技(重庆)有限公司 开源软件许可条款(版本1)
 * 氡氪网络科技(重庆)有限公司 以下简称Rnkrsoft。
 * 这些许可条款是 Rnkrsoft Corporation（或您所在地的其中一个关联公司）与您之间达成的协议。
 * 请阅读本条款。本条款适用于所有Rnkrsoft的开源软件项目，任何个人或企业禁止以下行为：
 * .禁止基于删除开源代码所附带的本协议内容、
 * .以非Rnkrsoft的名义发布Rnkrsoft开源代码或者基于Rnkrsoft开源源代码的二次开发代码到任何公共仓库,
 * 除非上述条款附带有其他条款。如果确实附带其他条款，则附加条款应适用。
 * <p/>
 * 使用该软件，即表示您接受这些条款。如果您不接受这些条款，请不要使用该软件。
 * 如下所述，安装或使用该软件也表示您同意在验证、自动下载和安装某些更新期间传输某些标准计算机信息以便获取基于 Internet 的服务。
 * <p/>
 * 如果您遵守这些许可条款，将拥有以下权利。
 * 1.阅读源代码和文档
 * 如果您是个人用户，则可以在任何个人设备上阅读、分析、研究Rnkrsoft开源源代码。
 * 如果您经营一家企业，则可以在无数量限制的设备上阅读Rnkrsoft开源源代码,禁止分析、研究Rnkrsoft开源源代码。
 * 2.编译源代码
 * 如果您是个人用户，可以对Rnkrsoft开源源代码以及修改后产生的源代码进行编译操作，编译产生的文件依然受本协议约束。
 * 如果您经营一家企业，不可以对Rnkrsoft开源源代码以及修改后产生的源代码进行编译操作。
 * 3.二次开发拓展功能
 * 如果您是个人用户，可以基于Rnkrsoft开源源代码进行二次开发，修改产生的元代码同样受本协议约束。
 * 如果您经营一家企业，不可以对Rnkrsoft开源源代码进行任何二次开发，但是可以通过联系Rnkrsoft进行商业授予权进行修改源代码。
 * 完整协议。本协议以及开源源代码附加协议，共同构成了Rnkrsoft开源软件的完整协议。
 * <p/>
 * 4.免责声明
 * 该软件按“原样”授予许可。 使用本文档的风险由您自己承担。Rnkrsoft 不提供任何明示的担保、保证或条件。
 * 5.版权声明
 * 本协议所对应的软件为 Rnkrsoft 所拥有的自主知识版权，如果基于本软件进行二次开发，在不改变本软件的任何组成部分的情况下的而二次开发源代码所属版权为贵公司所有。
 */
package com.rnkrsoft.logtrace4j;

import com.rnkrsoft.interfaces.EnumBase;
import com.rnkrsoft.interfaces.EnumIntegerCode;
import com.rnkrsoft.interfaces.EnumStringCode;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.*;
import java.util.*;

/**
 * Created by rnkrsoft.com on 2017/1/6.
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
        return addSubError(subError);
    }

    public ErrorContext addSubError(EnumStringCode error) {
        SubError subError = new SubError(error.getCode(), error.getDesc());
        subErrors.put(subError.getCode(), subError);
        return this;
    }

    public List<SubError> getSubErrors() {
        return new ArrayList(subErrors.values());
    }


    public ErrorContext addSubError(EnumIntegerCode error) {
        SubError subError = new SubError(Integer.toString(error.getCode()), error.getDesc());
        subErrors.put(subError.getCode(), subError);
        return this;
    }

    public int getSubErrorSize() {
        return subErrors.size();
    }

    public ErrorContext cause(Throwable cause) {
        if (cause != null && cause.getClass() != TraceableRuntimeException.class) {
            this.cause = cause;
        } else {
            //这里没必要，有可能导致死循环
        }
        return this;
    }

    public Throwable getCause(){
        return this.cause;
    }

    public ErrorContext reset() {
        this.code = null;
        this.desc = null;
        this.activity = null;
        this.message = null;
        this.solution = null;
        this.subErrors.clear();
        this.extras.clear();
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
        builder.append(LINE_SEPARATOR).append("### =============================ERROR=============================");
        if (this.activity != null) {
            builder.append(LINE_SEPARATOR)
                    .append("### activity: ").append(this.activity);
        }
        if (this.code != null) {
            builder.append(LINE_SEPARATOR)
                    .append("### error: ").append(LINE_SEPARATOR)
                    .append("\t")
                    .append(this.code)
                    .append(":")
                    .append(this.desc);
        }
        if (this.subErrors != null && !this.subErrors.isEmpty()) {
            builder.append(LINE_SEPARATOR)
                    .append("### subErrors: ");
            for (Error subError : this.subErrors.values()) {
                    builder.append(LINE_SEPARATOR)
                            .append("\t")
                            .append(subError.getCode())
                            .append(":")
                            .append(subError.getDesc());
            }

        }
        if (this.message != null) {
            builder.append(LINE_SEPARATOR)
                    .append("### message: ").append(this.message);
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
                    .append("### solution: ").append(this.solution);
        }
        if (this.cause != null) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(os);
            this.cause.printStackTrace(ps);
            builder.append(LINE_SEPARATOR)
                    .append("### cause: ").append(os.toString());
        }
        builder.append(LINE_SEPARATOR).append("### --------------------------ERROR-END-----------------------------");
        return builder.toString();
    }
}
