# fang

## Fang项目简介

Fang是Java语言的系统架构，使用Spring整合开源框架。

## 技术选型

- 核心框架：spring-4.3.0
- MVC框架：spring-webmvc-4.3.0
- 分布式服务框架：dubbo-2.5.3
- 数据库：mysql（读写分离），mongoDB
- 缓存：redis
- Session管理：spring-session +　redis
- ORM：mybatis-3.4.0，hibernate（用来生成数据库表）
- 数据库连接池：druid-1.0.18
- 消息中间价：activemq-5.7.0
- 日志管理：slf4j
- 前端框架：Bootstrap + Jquery
- 代码生成：freemarker-2.3.9

## 主要模块

- 数据库：mysql负责主要的业务数据存储，mongodb暂时主要存储操作日志和邮件信息
- redis缓存： 1.分布式缓存字典和基础数据，2.注解缓存活跃数据（主义缓存命中率）,3.通过spring-session整合，实现分布式session同步
- activemq消息中间价:  1.发送邮件并将邮件信息存储到mongodb 2.记录操作日志
- activiti工作流：工作流在线设计器整合，流程demo
- 代码生成类：根据实体生成一系列的Controller，Service,Mapper,Html,js
- dubbo：提供分布式接口调用
- WebSocket：浏览器与服务器双向通信，服务器想浏览器推送消息
- slf4j:日志管理，基于时间分割日志文件

## note
- 需要安装的软件：mysql，mongodb，tomcat，nginx，redis，activemq
- 安装zookeeper，或者删除配置文件dubbo.xml

