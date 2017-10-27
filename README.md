# Echo Judge Alpha

------
Echo Judge 提供了适用于 NOI，ACM 系列比赛测评机评测环境，并支持交互题目测评，通过导入 Echo Judge 您可以方便的编写出一个评测环境

Echo Judge 目前仍在测试中，若您在使用中遇到问题，请联系开发者

由于一些特殊原因，于  **2017.10.25** 日及之前更新的版本号错误,我们将不再提供 **v0.1.1_Alpha** 及之前版本的下载方式。
具体版本号请参见下方的 **发布公告**

----------
## 发布公告及下载地址
[https://github.com/SniffesTheRose/EchoJudge/releases](https://github.com/SniffesTheRose/EchoJudge/releases "下载链接")

----------
## 警告

- **当前版本只提供对 c++ 程序的编译与测评**

----------

## 使用说明



您可以使用以下语句编译指定文件

``` java
judger.compile("(String) g++目录(以exe结尾)", "(String) 编译文件目录", "(String) 可执行文件目录");
```



您可以使用如下语句构造一个测试节点

``` java
TestPoint point = new TestPoint("(long) 测试点时限", "(long) 测试点内存限制", "(long) 测试点分值", TestPoint.Ignore_Space, "(String) 输入文件目录", "(String) 输出文件目录");
```



当然，我们也提供其他构造节点的方法，您可以阅读 [Doc文档](https://sniffestherose.github.io/JudgingSystem/doc/index.html?constant-values.html) 来了解他们



当您成功构造一个测试节点后，您可以使用如下语句进行传统程序测试

``` java
EvaluationResult result = judger.simply_Runner("(TestPoint) 测试点信息", "(String) 程序目录", "(String) 临时选手输出文件目录");//通过传统方法运行程序
		
EvaluationResult result = Judger.simplyResultComparison("(TestPoint) 测试点信息", "(String) 临时选手输出文件目录", "(EvaluationResult) 选手程序运行结果");//通过传统方法比较答案
```



当程序测试结束之后，您可以通过以下方法获取程序运行情况

``` java
result.getMaxMemory();//获取程序运行中使用的最大内存
result.getTimeConsum();//获取程序运行耗时
result.getValue();//获取程序运行结果
```

程序运行结果将返回一个 EvaluationResult 类的常量，对应结果信息请参阅 [Doc文档](https://sniffestherose.github.io/JudgingSystem/doc/constant-values.html#codeJudger.EvaluationResult.Accepted) 来了解他们



您可以通过如下语句进行 Special Judge 测试

``` java
EvaluationResult result = judger.simply_Runner("(TestPoint) 测试点信息", "(String) 程序目录", "(String) 临时选手输出文件目录");//通过传统方法运行程序
		
result = Judger.specialResultComparison("(TestPoint) 测试点信息", "(String) SPJ程序目录", "(String) 临时选手输出文件目录", "(EvaluationResult) 选手程序运行结果", "(String) 得分目录", "(String) 错误报告目录");
```



当程序测试结束之后，您可以通过以下方法获取程序运行情况

``` java
result.getCustomVerifierRet();//获取 SPJ 得分
result.getCustomVerifierScore();// 获取 SPJ 返回的错误报告
result.getMaxMemory();//获取程序运行中使用的最大内存
result.getTimeConsum();//获取程序运行耗时
```



您也可以通过如下语句进行 交互 测试



```java
EvaluationResult result = judger.interact_Runner("(TestPoint) 测试点信息", "(String) 程序目录", "(String) 交互器程序目录", "(String) 得分目录", "(String) 错误报告目录");
```



当程序测试结束之后，您可以通过以下方法获取程序运行情况

```java
result.getCustomVerifierRet();//获取 交互 得分
result.getCustomVerifierScore();// 获取 交互 返回的错误报告
result.getMaxMemory();//获取程序运行中使用的最大内存
result.getTimeConsum();//获取程序运行耗时
```



若通过自定义校验器进行测试时选手程序超时、超过内存限制或系统错误，测试结果将是一个传统型结果，您可以通过以下语句判断测试结果是否为 自定义校验器测试结果

```java
result.getCustomVerifier();//当使用自定义校验器是返回 true 否则返回 false
```



自定义交互器的编写请参阅 [自定义答案校验器使用文档](https://sniffestherose.github.io/EchoJudge/Echo%20Judge%20%E8%87%AA%E5%AE%9A%E4%B9%89%E7%AD%94%E6%A1%88%E6%A0%A1%E9%AA%8C%E5%99%A8%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E)



------

## 下载与安装

您可以下载 EchoJudge.jar 并导入到您的工程，SystemTools.dll 请放置于程序根目录

请在程序开始时请使用下列方法导入动态库

	SystemTools.prestrain();

### 详情请参阅 doc 文档

[Echo Judge DOC 文档](https://sniffestherose.github.io/EchoJudge/doc/index.html)