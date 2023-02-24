# idea-bitbyte-plugin

这是一个集成了各种小工具的插件，旨在通过各种小功能简化日常开发，提高开发效率。目前已提供以下功能，后续将添加其他功能：

1. 增强复制功能。可以一键复制当前类的完全限定类名、JSON Schema信息、枚举类描述信息、当前类的 JSON 字符串（使用默认值生成）。
2. 简化测试类的创建。支持快速创建当前类指定方法的测试方法。
3. 字符串处理。快速转换当前选定的字符串，支持大小写转换和命名方法转换。

# 快速开始

- 插件地址：https://plugins.jetbrains.com/plugin/20704-bitbyte/

# 功能简介

1. 复制增强

- Copy Json Schema - 复制当前类的JSON Schema 信息
- Copy Enum Description - 复制当前枚举类的枚举描述（可配置）
- Copy Class Name With Package - 复制当前类的全限定类名
- Copy Json With Default Value - 复制当前类的Json字符串（使用默认值生成）
  ![](https://files.catbox.moe/rqsy25.png)

2. 快速创建测试类（目前仅支持junit5）

- 点击方法左侧的按钮即可
  ![](https://files.catbox.moe/mdr3d5.png)
- 生成的测试方法内容
  ![](https://files.catbox.moe/flmyz7.png)

3. 字符串处理

- 字符串大写转换
- 驼峰转下划线
  ![](https://files.catbox.moe/nrpmct.png)
