# ![RealWorld Example App](./logo.png)

**中文** | [English](./README.en-US.md)

**Java 21 + SpringBoot 3 + MyBatis** 代码库包含符合[RealWorld](https://github.com/gothinkster/realworld)规范和API的真实示例(CRUD, auth，高级模式等)。  

创建这个代码库是为了演示一个用**Java 21 + SrpingBoot 3 + MyBatis**构建的成熟的全栈应用程序，包括CRUD操作、身份验证、路由、分页等功能。  

我们竭尽全力坚持Java, SpringBoot和MyBatis的社区风格指南和最佳实践。  

有关如何与其他前端/后端一起工作的更多信息，请访问[RealWorld](https://github.com/gothinkster/realworld)。  

# 项目特点

* 使用领域驱动设计的思想来分离业务逻辑和基础设施。  
* 使用MyBatis实现[Data Mapper](https://martinfowler.com/eaaCatalog/dataMapper.html)持久化模块。  
* 将应用程序分离为多个模块，并使用Maven进行组织。  

本项目代码分为4个模块:  
1. app-permission模块包括与权限相关的实体，包括应用的用户、配置文件和安全机制。  
2. app-common通用模块包括关于异常定义的通用实现。  
3. app-article模块包括文章发布/编辑/删除等业务逻辑。  
4. app-main模块是启动应用程序的应用程序入口程序。  

应用程序的逻辑分层组织如下:
1. `application`是由Spring MVC和高层服务实现的web层。  
2. `domain`是业务模型层，包含了领域对象的定义。  
3. `infra`层包含用MyBatis实现的所有数据访问类。  

# 安全  

身份验证和授权管理使用Spring Security来实现，并使用JWT进行基于令牌的身份验证。此外，借助Spring Boot的各种特性来实现异常处理、测试等功能。  

# 数据库  

本项目使用MariaDB数据库来存储应用程序数据，您可以通过执行app-main/src/main/resources目录下的schema.sql来初始化数据库。  


# 如何工作    

需要安装JDK 21。  
需要安装MariaDB。  

## 数据库初始化  

在MariaDB命令窗口中执行app-main/src/main/resources/schema.sql初始化数据库。  
在app-permission.properties和app-article.properties中根据你的数据库参数修改数据库参数。  

## 编译application
在项目的根目录下执行: mvn install

## 测试application
在项目的根目录下执行: mvn test

## 执行application
在app-main的根目录下执行: mvn spring-boot:run

## 项目地址

- [realworld](https://github.com/joyheros/realworld)  

## 如何贡献

**Pull Request:**

1. Fork 代码!
2. 创建自己的分支: `git checkout -b feature/xxxx`
3. 提交你的修改: `git commit -m 'feature: add xxxxx'`
4. 推送您的分支: `git push origin feature/xxxx`
5. 提交: `pull request`

## Git 贡献提交规范

- 参考 [vue](https://github.com/vuejs/vue/blob/dev/.github/COMMIT_CONVENTION.md) 规范

  - `feat` : 新增功能
  - `fix` : 修复缺陷
  - `docs` : 文档变更
  - `style` : 代码格式
  - `refactor` : 代码重构
  - `perf` : 性能优化
  - `test` : 添加疏漏测试或已有测试改动
  - `build` : 构建流程、外部依赖变更 (如升级 npm 包、修改打包配置等)
  - `ci` : 修改 CI 配置、脚本
  - `revert` : 回滚 commit
  - `chore` : 对构建过程或辅助工具和库的更改 (不影响源文件)
  - `wip` : 正在开发中
  - `types` : 类型定义文件修改

## 感谢以下项目提供帮助

- [gothinkster](https://github.com/gothinkster/realworld)  
- [spring-boot-realworld-example-app](https://github.com/gothinkster/spring-boot-realworld-example-app)  

## 维护者

[@joyheors](https://github.com/joyheros)  

## `Star`

非常感谢留下星星的好心人，感谢您的支持 :heart: