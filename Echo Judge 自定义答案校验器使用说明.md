﻿# Echo Judge 自定义答案校验器使用说明

------



首先，我们衷心的感谢您对 Echo Judge 的信任。

如果您在使用传统比较方式的基础上想扩展答案比较的方法，请您仔细阅读本篇文章。



------



## 交互器的编写方法



Echo Judge 提供了交互测评模式，在此模式下出题人被要求编写一个交互器以完成对选手的交互信息进行应答，下面我们将介绍 Echo Judge 交互器 的编写方法。



在运行您的交互器之前，我们将会通过命令行提供五个入口参数，您可以在交互器中获取这些参数的值，它们分别是。



```
argv[1] 输入文件

argv[2] 自定义校验器输入文件

argv[3] 测试点分值

argv[4] 得分输出文件

argv[5] 错误报告输出文件
```



以上的五个入口参数将在出题人提交题目时被确定，当且仅当题目发生改变时其会被更改。



`Echo Judge` 会在选手程序开始运行时将 **输入文件** 中的内容 **输入进选手程序** 中，同时您也可以通过文件读取的方式获取这些内容。与此不同的是，**自定义校验器输入文件** 中的内容不会被选手程序得到，这仅供您的 **自定义校验器** 使用。



值得注意的是，如果题目在提交时并未指定 **输入文件** 或者自定义 **校验器输入文件** 那么它们将会通过一个长度为 **4** 的字符串 **null** 传入您的程序。



测试点分值内容为当前测试点的 **最高分值** ，您可以在得分输出文件中输出本测试点选手得分，若您输出的得分高于测试点分值，将会以 **自定义校验器崩溃** 作为结果返还给选手，且选手在本测试点的得分将记作 **0** 。



您可以将您想显示给选手的信息输出至 **错误报告输出文件** 中，只要您错误报告中含有任何错误信息，无论当前测试点的选手程序是否正确，错误报告都会被返还给选手。



当然，您所生成的 **得分情况** 以及 **错误报告** 会在当前测试点测试结束时被 **删除** 。



------

## 交互器与选手程序交互的方法



您的交互器将可以通过 **标准输入输出流** 与选手程序进行交互。



由于系统默认在缓冲区被 **充满** 后发送缓冲区内容，因此请您在输出信息后 **手动刷新缓冲区** 。



您可以使用以下的方法手动刷新缓冲区。



C++ 

``` cpp
fflush(stdout);
```



Java:

```java
System.out.flush();
```



Python:

``` python
stdout.flush()
```



Pascal:

``` pascal
flush(output)
```



对于其他语言, 请参见该语言文档。



------



## 交互器的注意事项



您的交互器可以随时通过 **标准错误输出流** 输出长度为 **15** 的字符串 **System_Shutdown** 来停止选手程序的运行.

输出结束信息后请 **刷新标准错误输出流** 。



Echo Judge 不会检测选手交互语句的格式是否合法，若有需要，请您在交互器中对选手交互语句进行检查。

若您的交互器并未处理这一特殊情况且选手程序所输出的交互语句格式不合法，那将会导致选手程序以及您的交互器均因 **超时** 结束运行，超时时限见下方交互器终止运行条件。



### 当出现以下情况时，选手的程序会被终止运行

- 选手程序正常退出
- 选手程序因访问无效内存等错误崩溃
- 选手程序运行超时
- 交互器通过标准错误输出流输出长度为 **15** 的字符串 **System_Shutdown**
- 交互器结束运行



### 当出现以下情况时，交互器会被终止运行

- 交互器正常退出
- 交互器因访问无效内存等错误崩溃
- 交互器运行超时 (选手程序限制时间 + **5s**)




------



## SpecialJudge 的编写方法



Echo Judge  提供了SpecialJudge 答案比较方法，通过此答案比较方法，您可以轻松测评拥有多组解的题目。Echo Judge 的 SpecialJudge 采用了 Testlib 标准，您可以参阅Testlib的 [工程文档](https://github.com/MikeMirzayanov/testlib) 来了解他们



在开始编写您的 SpecialJudge 之前，请前往 Testlib 开源工程中下载头文件。



在程序开始时，您需要初始化 checker 工作环境，通过

```c++
registerTestlibCmd(argc, argv);
```

解析命令行参数。



您可以通过 readInt() 等一系列函数从选手和答案文件中读入数据



我们将帮助您通过 Testlib 开发团队提供的 [checker](https://github.com/MikeMirzayanov/testlib/tree/master/checkers) 来了解它的使用方法



```c++
#include "testlib.h"

int main(int argc, char *argv[])
{
    //解析命令行参数
    registerTestlibCmd(argc, argv);

    int pans = ouf.readInt(-2000, 2000);//从选手输出中读入一个 [-2000, 2000] 范围内的 32位整数
    int jans = ans.readInt();//从答案文件中读入一个整数

    if (pans == jans)
        quitf(_ok, "The sum is correct.");//正确的答案
    else
        quitf(_wa, "The sum is wrong: expected = %d, found = %d", jans, pans);//错误的答案

    return 0;
}
```



我们将通过表格向您展示 Testlib 提供的几个预定义结果宏：



|         结果         |         宏         |                 含义                 |
| :----------------: | :---------------: | :--------------------------------: |
|         Ok         |     ```_ok```     |               正确的答案                |
|    Wrong Answer    |    ``` _wa```     |               错误的答案                |
| Presentation Error |     ```_pe```     |              错误的输出格式               |
| Partially Correct  | ``` _pc(score)``` | 部分正确。得分为[0, 100]间的一个整数，表示测试点得分的百分比 |
|        Fail        |   ``` _fail```    |           checker 遇到严重错误           |



以下是 Testlib 提供的常用 函数 和 对象：



|                    定义                    |                  说明                   |
| :--------------------------------------: | :-----------------------------------: |
|            ```InStream inf```            |                 输入文件                  |
|           ``` InStream ouf```            |                 选手输出                  |
|           ``` InStream ans```            |                 参考输出                  |
| ```int InStream::readInt(int minv, int maxv)``` | 读取一个 [minv, maxv] 范围内的 32 位整数（忽略空白字符） |
| ``` long long InStream::readLong(long long minv, long long maxv)``` | 读取一个 [minv, maxv] 范围内的 64 位整数（忽略空白字符） |
| ``` double InStream::readDouble(double minv, double maxv)``` |   读取一个 [minv, maxv] 范围内的实数（忽略空白字符）    |
| ```std::string InStream::readToken()```  |       读取一个不包含空白字符的连续字符串（忽略空白字符）       |
| ``` std::string InStream::readToken(const std::string& ptrn)``` |   读取一个匹配给定模式的不包含空白字符的连续字符串（忽略空白字符）    |
|     ``` char InStream::readChar()```     |                读取单个字符                 |
|    ``` char InStream::readSpace()```     |                读取一个空格                 |
|     ``` void InStream::readEoln()```     |                读取一个换行符                |
|     ``` void InStream::readEof()```      |                读取一个文末符                |



## 结语



Testlib 不仅提供了 [checker](https://github.com/MikeMirzayanov/testlib/tree/master/checkers)，还提供了 [generator](https://github.com/MikeMirzayanov/testlib/tree/master/generators) 、[interactor](https://github.com/MikeMirzayanov/testlib/tree/master/interactors)、[validator](https://github.com/MikeMirzayanov/testlib/tree/master/validators) 。EchoJudge将会在之后的更新中逐步添加对这些功能的支持。



------

## 使用过程中遇到任何错误，请您联系开发组



我们将提供以下的联系方式：

- 大喊大叫
- 群发邮件
- 打当当



**但我们不保证您所反馈的信息会被全部接收**


<style>
  .page-header{
    background-image:url(doc/images/BackGround.jpg);
    background-size: cover;
    background-position: center;
  }
</style>
