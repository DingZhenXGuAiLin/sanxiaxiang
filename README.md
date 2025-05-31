# 三峡乡旅游景点管理系统 (SanXiaXiang Holiday App)

## 项目简介

这是一个基于Spring Boot + MyBatis的旅游景点管理系统后端项目，主要用于管理旅游景点信息、游客信息、管理员信息以及景点评论和评分功能。系统采用RESTful API设计，支持跨域访问，为前端应用提供数据服务。

## 技术栈

- **框架**: Spring Boot 3.3.1
- **数据库**: MySQL
- **ORM**: MyBatis 3.0.3
- **构建工具**: Maven
- **Java版本**: JDK 17
- **其他依赖**:
  - Lombok (简化代码)
  - MySQL Connector
  - Dom4j (XML处理)

## 项目结构

```
sanxiaxiang/
├── end_0/                          # 主项目目录
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/end_0/
│   │   │   │   ├── Controller/      # 控制器层 - REST API接口
│   │   │   │   │   ├── TouristController.java      # 游客管理API
│   │   │   │   │   ├── LandscapeController.java    # 景点管理API
│   │   │   │   │   ├── ManagerController.java      # 管理员管理API
│   │   │   │   │   └── CommentController.java      # 评论管理API
│   │   │   │   ├── Service/         # 服务层 - 业务逻辑
│   │   │   │   │   ├── Impl/        # 服务实现类
│   │   │   │   │   ├── TouristService.java
│   │   │   │   │   ├── LandscapeService.java
│   │   │   │   │   ├── ManagerService.java
│   │   │   │   │   └── CommentService.java
│   │   │   │   ├── Mapper/          # 数据访问层 - MyBatis接口
│   │   │   │   │   ├── TouristMapper.java
│   │   │   │   │   ├── LandscapeMapper.java
│   │   │   │   │   ├── ManagerMapper.java
│   │   │   │   │   ├── CommentMapper.java
│   │   │   │   │   └── t_lMapper.java
│   │   │   │   ├── Pojo/            # 数据模型
│   │   │   │   │   └── entity/      # 实体类
│   │   │   │   │       ├── Tourist.java     # 游客实体
│   │   │   │   │       ├── Landscape.java   # 景点实体
│   │   │   │   │       ├── Manager.java     # 管理员实体
│   │   │   │   │       └── Comment.java     # 评论实体
│   │   │   │   ├── Common/          # 通用组件
│   │   │   │   │   ├── Result/      # 统一返回结果封装
│   │   │   │   │   │   └── Result.java
│   │   │   │   │   └── Constant/    # 常量定义
│   │   │   │   │       └── ResultConstant.java
│   │   │   │   ├── config/          # 配置类
│   │   │   │   │   ├── WebConfig.java           # Web配置
│   │   │   │   │   ├── LogInterceptor.java      # 日志拦截器
│   │   │   │   │   └── StringListTypeHandler.java # 类型处理器
│   │   │   │   └── End0Application.java         # 主启动类
│   │   │   └── resources/
│   │   │       └── application.yml  # 应用配置文件
│   │   └── test/                    # 测试代码
│   ├── target/                      # 编译输出目录
│   ├── pom.xml                      # Maven配置文件
│   ├── mvnw                         # Maven Wrapper脚本
│   └── mvnw.cmd                     # Windows Maven Wrapper脚本
└── README.md                        # 项目说明文档
```

## 数据模型

### 1. Tourist (游客)
- `tourist_id`: 游客ID (主键)
- `tourist_name`: 游客用户名
- `tourist_password`: 游客密码
- `user_pic`: 用户头像

### 2. Landscape (景点)
- `landscape_id`: 景点ID (主键)
- `name`: 景点名称
- `pic_url`: 景点图片URL
- `location`: 景点位置
- `telephone`: 联系电话
- `description`: 景点描述

### 3. Manager (管理员)
- `manager_id`: 管理员ID (主键)
- `manager_name`: 管理员用户名
- `manager_password`: 管理员密码

### 4. Comment (评论)
- `landscape_id`: 景点ID (外键)
- `tourist_id`: 游客ID (外键)
- `content`: 评论内容
- `time`: 评论时间

## API接口说明

### 游客管理 (/tourist)
- `POST /tourist/add` - 注册新游客
- `DELETE /tourist/delete` - 删除游客
- `PUT /tourist/update` - 更新游客信息
- `GET /tourist/getById` - 根据ID获取游客信息
- `GET /tourist/getAll` - 获取所有游客
- `POST /tourist/score` - 游客给景点评分
- `DELETE /tourist/deleteScoring` - 删除评分

### 景点管理 (/landscape)
- `POST /landscape/add` - 添加新景点
- `DELETE /landscape/delete` - 删除景点
- `PUT /landscape/update` - 更新景点信息
- `GET /landscape/getById` - 根据ID获取景点信息
- `GET /landscape/getAll` - 获取所有景点
- `GET /landscape/getScoreOfLandscape` - 获取景点评分

### 管理员管理 (/manager)
- `POST /manager/add` - 添加管理员
- `DELETE /manager/delete` - 删除管理员
- `PUT /manager/update` - 更新管理员信息
- `GET /manager/getById` - 根据ID获取管理员信息
- `GET /manager/getAll` - 获取所有管理员

### 评论管理 (/comment)
- `POST /comment/add` - 添加评论
- `DELETE /comment/delete` - 删除评论
- `PUT /comment/update` - 更新评论
- `GET /comment/getById` - 获取评论信息
- `GET /comment/getAll` - 获取所有评论

## 系统运作方式

### 1. 架构设计
系统采用经典的三层架构模式：
- **Controller层**: 处理HTTP请求，参数验证，调用Service层
- **Service层**: 业务逻辑处理，事务管理
- **Mapper层**: 数据访问，SQL操作

### 2. 数据流转
1. 前端发送HTTP请求到Controller
2. Controller接收请求参数，调用相应的Service方法
3. Service处理业务逻辑，调用Mapper进行数据库操作
4. Mapper执行SQL语句，返回结果
5. Service处理返回数据，Controller封装成统一的Result格式返回给前端

### 3. 统一返回格式
所有API接口都使用统一的Result格式：
```json
{
  "code": 1,        // 1-成功, 0-失败
  "msg": "success", // 返回消息
  "data": {}        // 返回数据
}
```

## 环境配置

### 数据库配置
在 `application.yml` 中配置MySQL数据库连接：
```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost/sxx
    username: hyl
    password: Xsq20050709##
```

### 服务器配置
- 默认端口: 8080
- 支持跨域访问
- 文件上传限制: 单文件10MB，总请求100MB

## 快速开始

### 1. 环境要求
- JDK 17+
- Maven 3.6+
- MySQL 8.0+

### 2. 数据库准备
创建名为 `sxx` 的数据库，并创建相应的表结构。

### 3. 运行项目
```bash
# 进入项目目录
cd end_0

# 使用Maven运行
./mvnw spring-boot:run

# 或者编译后运行
./mvnw clean package
java -jar target/end_0-0.0.1-SNAPSHOT.jar
```

### 4. 访问测试
项目启动后，可以通过以下方式测试：
- 服务器地址: http://localhost:8080
- API测试: 使用Postman或其他API测试工具
- 日志查看: 控制台会显示SQL执行日志

## 开发说明

### 1. 代码规范
- 使用Lombok简化实体类代码
- 统一的异常处理和返回格式
- RESTful API设计规范
- 支持跨域访问

### 2. 扩展功能
- 日志拦截器记录请求信息
- 自定义类型处理器支持复杂数据类型
- 配置文件支持多环境部署

### 3. 注意事项
- 数据库密码等敏感信息应使用环境变量或配置文件管理
- 生产环境需要配置适当的安全策略
- 建议添加输入参数验证和异常处理

## 贡献指南

1. Fork 项目
2. 创建功能分支
3. 提交更改
4. 推送到分支
5. 创建 Pull Request

## 许可证

本项目采用开源许可证，具体信息请查看 LICENSE 文件。