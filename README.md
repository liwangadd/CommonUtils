# CommonUtils
在进行java web开发时，有许多第三方库提供了常见的工具类，如Apache的commons系列，spring中的utils包和guava中的工具类。
为了使用某些功能需要导入整个依赖包，增加了可运行文件的体积。
该仓库整合了以上框架的常用工具类，并提供了简单易理解的调用函数接口。

主要模块如下
- Base64Utils 提供了Base64编解码实现
- CloseUtils  关闭实现了Closeable接口的类
- EmptyUtils  用于判断字符串、对象和集合是否为空或null
- EncryptUtils  提供了常见的甲乙算法实现
- FileIOUtils 提供了对文件输入输出流的操作
- FileUtils   提供了文件的基本操作，判断文件是否存在、是否为目录、创建文件等
- ReflectUtils  反射操作工具类，简化反射编程
- RegexUtils  正则表达式匹配工具类，同时提供了常见的正则匹配模式字符串常量（`RegexConstant.java`）
- StringUtils 字符串操作工具类
- TimeUtils 时间操作工具类
