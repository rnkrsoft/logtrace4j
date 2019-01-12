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
 * 如果您经营一家企业，则禁止在任何设备上阅读Rnkrsoft开源源代码,禁止分析、禁止研究Rnkrsoft开源源代码。
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
 * 本协议所对应的软件为 Rnkrsoft 所拥有的自主知识产权，如果基于本软件进行二次开发，在不改变本软件的任何组成部分的情况下的而二次开发源代码所属版权为贵公司所有。
 */
package com.rnkrsoft.logtrace4j;


import com.rnkrsoft.interfaces.EnumBase;
import com.rnkrsoft.interfaces.EnumIntegerCode;
import com.rnkrsoft.interfaces.EnumStringCode;

import java.util.List;

/**
 * Created by rnkrsoft.com on 2017/1/6.
 * 错误上下文
 */
public interface ErrorContext extends Error {
    String LINE_SEPARATOR = System.getProperty("line.separator", "\n");

    /**
     * 保存一个上下文在当前上下文中
     *
     * @param ctx 上下文
     * @return 上下文
     */
    ErrorContext stored(ErrorContext ctx);

    /**
     * 获取已保存的上下文
     *
     * @return 上下文
     */
    ErrorContext stored();

    /**
     * 设置错误码
     *
     * @param code   错误码
     * @param format 格式
     * @param args   参数
     * @return 上下文对象
     */
    ErrorContext code(String code, String format, Object... args);

    /**
     * 设置错误码
     *
     * @param code 错误码
     * @return 上下文对象
     */
    ErrorContext code(EnumBase code);

    /**
     * 设置正在进行的活动信息
     *
     * @param format 格式
     * @param args   参数
     * @return 上下文对象
     */
    ErrorContext activity(String format, Object... args);

    /**
     * 设置该提示信息对应的解决方案
     *
     * @param format 格式
     * @param args   参数
     * @return 上下文对象
     */
    ErrorContext solution(String format, Object... args);

    /**
     * 设置错误提示信息
     *
     * @param format 格式
     * @param args   参数
     * @return 上下文对象
     */
    ErrorContext message(String format, Object... args);

    /**
     * 额外的提示信息
     *
     * @param name   提示信息的名字
     * @param format 格式
     * @param args   参数
     * @return 上下文对象
     */
    ErrorContext extra(String name, String format, Object... args);

    /**
     * 子错误数量
     *
     * @return 子错误数量
     */
    int subErrorSize();

    /**
     * 增加子错误信息
     *
     * @param error 子错误信息
     * @return 上下文对象
     */
    ErrorContext addSubError(SubError error);

    /**
     * 增加子错误信息
     *
     * @param code   代码
     * @param format 格式
     * @param args   描述参数
     * @return 上下文对象
     */
    ErrorContext addSubError(String code, String format, Object... args);

    /**
     * 增加子错误信息
     *
     * @param error 子错误信息
     * @return 上下文对象
     */
    ErrorContext addSubError(EnumStringCode error);

    /**
     * 增加子错误信息
     *
     * @param error 子错误信息
     * @return 上下文对象
     */
    ErrorContext addSubError(EnumIntegerCode error);

    /**
     * 获取子错误个数
     *
     * @return 个数
     */
    int getSubErrorSize();

    /**
     * 获取子错误码
     *
     * @return 错误码列表
     */
    List<SubError> getSubErrors();

    /**
     * 设置导致错误的异常
     *
     * @param cause 错误的异常
     * @return 上下文对象
     */
    ErrorContext cause(Throwable cause);

    /**
     * 错误异常
     *
     * @return 异常
     */
    Throwable getCause();

    /**
     * 重置上下文对象
     *
     * @return 上下文对象
     */
    ErrorContext reset();

    /**
     * 包装为运行时异常
     *
     * @return 运行时异常
     */
    RuntimeException runtimeException();

    /**
     * 包装为受检异常
     *
     * @return 受检异常
     */
    Exception exception();

    /**
     * 抛出异常
     */
    void throwError();
}
