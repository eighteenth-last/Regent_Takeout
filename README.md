# 📦 菩提阁外卖系统 (Regent Takeout)

> 一个基于 Spring Boot 3.3.5 + MyBatis-Plus + Vue.js 的现代化外卖管理系统

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MyBatis-Plus](https://img.shields.io/badge/MyBatis--Plus-3.5.5-blue.svg)](https://baomidou.com/)
[![Vue.js](https://img.shields.io/badge/Vue.js-2.x-green.svg)](https://vuejs.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue.svg)](https://www.mysql.com/)

---

## 📖 项目简介

菩提阁外卖系统是一个全栈外卖管理平台，提供完整的外卖业务解决方案。系统分为**管理端**和**移动端**两个部分，支持菜品管理、订单处理、用户管理等核心功能。

### ✨ 核心特性

- 🎯 **前后端分离架构** - 后端提供 RESTful API，前端独立部署
- 📱 **移动端适配** - 响应式设计，完美支持移动端访问
- 🔐 **安全认证** - 基于 Session 的登录认证机制
- 💾 **数据持久化** - MyBatis-Plus 简化 CRUD 操作
- 🎨 **现代化 UI** - Element UI + Vant 组件库
- 📦 **文件上传** - 支持菜品图片上传管理
- 🔄 **自动填充** - 创建时间、更新时间等字段自动填充
- 🛒 **购物车功能** - 完整的购物车增删改查
- 📍 **地址管理** - 支持多地址管理和默认地址设置

---

## 🏗️ 技术栈

### 后端技术

| 技术 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 3.3.5 | 核心框架 |
| MyBatis-Plus | 3.5.5 | ORM 框架 |
| MySQL | 8.0+ | 数据库 |
| Redis | - | 缓存（可选） |
| Druid | 1.2.23 | 数据库连接池 |
| Lombok | - | 简化代码 |
| Fastjson2 | 2.0.42 | JSON 处理 |
| Hutool | 5.8.32 | Java 工具库 |
| SpringDoc OpenAPI | 2.5.0 | API 文档 |
| Aliyun SMS SDK | 2.1.0 | 短信服务 |

### 前端技术

| 技术 | 说明 |
|------|------|
| Vue.js 2.x | 渐进式 JavaScript 框架 |
| Element UI | PC 端组件库 |
| Vant | 移动端组件库 |
| Axios | HTTP 客户端 |
| jQuery | DOM 操作库 |

---

## 📁 项目结构

```
Regent_Takeout/
├── src/
│   ├── main/
│   │   ├── java/com/gpt/
│   │   │   ├── Common/              # 公共组件
│   │   │   │   ├── BaseContextCommon.java      # ThreadLocal 用户上下文
│   │   │   │   ├── CustomException.java        # 自定义异常
│   │   │   │   ├── GlobalException.java        # 全局异常处理
│   │   │   │   ├── JacksonObjectMapper.java    # JSON 序列化配置
│   │   │   │   ├── MyMetaObjectHandler.java    # 字段自动填充
│   │   │   │   └── R.java                      # 统一返回结果
│   │   │   ├── Config/              # 配置类
│   │   │   │   ├── MybatisPlusConfig.java      # MyBatis-Plus 配置
│   │   │   │   └── WebMvcConfig.java           # Web MVC 配置
│   │   │   ├── Controller/          # 控制器层
│   │   │   │   ├── AddressBookController.java  # 地址管理
│   │   │   │   ├── CategoryController.java     # 分类管理
│   │   │   │   ├── CommonController.java       # 公共接口（文件上传）
│   │   │   │   ├── DishController.java         # 菜品管理
│   │   │   │   ├── EmployeeController.java     # 员工管理
│   │   │   │   ├── OrdersController.java       # 订单管理
│   │   │   │   ├── SetmealController.java      # 套餐管理
│   │   │   │   ├── ShoppingCartController.java # 购物车管理
│   │   │   │   └── UserController.java         # 用户管理
│   │   │   ├── Dto/                 # 数据传输对象
│   │   │   │   ├── DishDto.java                # 菜品 DTO
│   │   │   │   ├── OrdersDto.java              # 订单 DTO
│   │   │   │   └── SetmealDto.java             # 套餐 DTO
│   │   │   ├── Entity/              # 实体类
│   │   │   │   ├── AddressBookEntity.java      # 地址实体
│   │   │   │   ├── CategoryEntity.java         # 分类实体
│   │   │   │   ├── DishEntity.java             # 菜品实体
│   │   │   │   ├── DishFlavorEntity.java       # 菜品口味实体
│   │   │   │   ├── EmployeeEntity.java         # 员工实体
│   │   │   │   ├── OrderDetailEntity.java      # 订单明细实体
│   │   │   │   ├── OrdersEntity.java           # 订单实体
│   │   │   │   ├── SetmealEntity.java          # 套餐实体
│   │   │   │   ├── ShoppingCartEntity.java     # 购物车实体
│   │   │   │   └── UserEntity.java             # 用户实体
│   │   │   ├── Filter/              # 过滤器
│   │   │   │   └── LoginCheckFilter.java       # 登录校验过滤器
│   │   │   ├── Mapper/              # 数据访问层
│   │   │   ├── Service/             # 业务逻辑层
│   │   │   │   └── Impl/            # 业务实现类
│   │   │   ├── Utils/               # 工具类
│   │   │   │   ├── SMSUtils.java               # 短信工具
│   │   │   │   └── ValidateCodeUtils.java      # 验证码生成工具
│   │   │   └── RegentTakeoutApplication.java   # 启动类
│   │   └── resources/
│   │       ├── application.yml      # 应用配置
│   │       ├── backend/             # 后台管理端前端
│   │       │   ├── api/             # 后台 API 接口
│   │       │   ├── images/          # 后台图片资源
│   │       │   ├── js/              # JavaScript 文件
│   │       │   ├── page/            # 后台页面
│   │       │   ├── plugins/         # 插件（Vue、Element UI、Axios）
│   │       │   ├── styles/          # 样式文件
│   │       │   └── index.html       # 后台首页
│   │       └── front/               # 移动端前端
│   │           ├── api/             # 移动端 API 接口
│   │           ├── fonts/           # 字体文件
│   │           ├── images/          # 移动端图片资源
│   │           ├── js/              # JavaScript 文件
│   │           ├── page/            # 移动端页面
│   │           │   ├── login.html           # 登录页
│   │           │   ├── user.html            # 个人中心
│   │           │   ├── address.html         # 地址列表
│   │           │   ├── address-edit.html    # 地址编辑
│   │           │   ├── order.html           # 订单列表
│   │           │   ├── add-order.html       # 下单页
│   │           │   └── pay-success.html     # 支付成功
│   │           ├── styles/          # 样式文件
│   │           └── index.html       # 移动端首页
│   └── test/                        # 测试代码
├── SQL/
│   └── regent-takeout.sql           # 数据库脚本
├── Images/                          # 上传的图片文件
├── pom.xml                          # Maven 配置
└── README.md                        # 项目说明
```

---

## 🚀 快速开始

### 环境要求

- **JDK**: 17+
- **Maven**: 3.6+
- **MySQL**: 8.0+
- **Redis**: 最新稳定版（可选）
- **IDE**: IntelliJ IDEA

### 安装步骤

#### 1. 克隆项目

```bash
git clone https://github.com/eighteenth-last/Regent_Takeout.git
cd Regent_Takeout
```

#### 2. 导入数据库

```bash
# 创建数据库
mysql -u root -p
CREATE DATABASE `regent-takeout` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# 导入 SQL 文件
mysql -u root -p regent-takeout < SQL/regent-takeout.sql
```

#### 3. 修改配置

编辑 `src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/regent-takeout
    username: root
    password: your_password  # 修改为你的数据库密码
  data:
    redis:
      host: localhost
      port: 6379

reggie:
  path: /your/path/to/Images/  # 修改图片上传路径  我自己用的绝对路径
```

#### 4. 编译项目

```bash
mvn clean compile
```

#### 5. 运行项目

```bash
# 方式 1: 使用 Maven
mvn spring-boot:run

# 方式 2: 运行主类
# 在 IDE 中运行 RegentTakeoutApplication.java
```

#### 6. 访问应用

- **后台管理端**: http://localhost:8080/backend/index.html
  - 默认账号：`admin`
  - 默认密码：`123456`

- **移动端**: http://localhost:8080/front/index.html
  - 使用手机号登录，验证码在控制台查看

- **API 文档**: http://localhost:8080/swagger-ui.html

---

## 🎯 功能模块

### 后台管理端

| 模块 | 功能描述 |
|------|----------|
| 👤 员工管理 | 员工信息的增删改查、状态管理 |
| 📋 分类管理 | 菜品分类、套餐分类的管理 |
| 🍜 菜品管理 | 菜品信息、口味、图片的管理 |
| 🎁 套餐管理 | 套餐信息、套餐内菜品的管理 |
| 📦 订单管理 | 订单查询、状态更新（派送、完成） |

### 移动端

| 模块 | 功能描述 |
|------|----------|
| 🔐 用户登录 | 手机号 + 验证码登录 |
| 🏠 首页展示 | 菜品分类、菜品列表展示 |
| 🛒 购物车 | 添加/删除商品、修改数量 |
| 📍 地址管理 | 新增/编辑/删除地址、设置默认地址 |
| 📝 下单支付 | 选择地址、提交订单 |
| 📋 订单查询 | 查看历史订单、再来一单 |
| 👤 个人中心 | 查看个人信息、最新订单、退出登录 |

---

## 🔑 核心功能实现

### 1. 用户登录与注册

- **手机号验证码登录**：使用阿里云短信服务发送验证码
- **自动注册**：新用户首次登录自动注册，生成随机用户名（格式：`新用户 + 6位随机字符`）
- **Session 管理**：使用 ThreadLocal 存储用户 ID

```java
// 自动生成随机用户名
private String generateRandomUsername() {
    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    Random random = new Random();
    StringBuilder sb = new StringBuilder("新用户");
    for (int i = 0; i < 6; i++) {
        sb.append(chars.charAt(random.nextInt(chars.length())));
    }
    return sb.toString();
}
```

### 2. 地址管理

- **首次添加自动设为默认地址**
- **支持多地址管理**
- **地址编辑/删除权限校验**

```java
// 首次添加地址自动设为默认
if (count == 0) {
    addressBookEntity.setIsDefault(1);
    log.info("用户首次添加地址，自动设置为默认地址");
}
```

### 3. 订单处理

- **下单流程**：购物车 → 选择地址 → 提交订单 → 清空购物车
- **订单状态**：待付款 → 正在派送 → 已派送 → 已完成/已取消
- **再来一单**：将历史订单商品重新添加到购物车

```java
// 再来一单：将订单详情转换为购物车项
List<ShoppingCartEntity> shoppingCartList = orderDetails.stream().map(orderDetail -> {
    ShoppingCartEntity shoppingCart = new ShoppingCartEntity();
    // ... 复制订单详情到购物车
    return shoppingCart;
}).collect(Collectors.toList());
```

### 4. 文件上传

- **图片存储**：本地文件系统存储
- **支持格式**：jpg、jpeg、png、gif
- **路径配置**：通过 `application.yml` 配置上传路径

### 5. 自动填充

使用 MyBatis-Plus 的 `MetaObjectHandler` 实现字段自动填充：

```java
// 插入时自动填充
public void insertFill(MetaObject metaObject) {
    if (metaObject.hasSetter("createTime")) {
        metaObject.setValue("createTime", new Date());
    }
    if (metaObject.hasSetter("createUser")) {
        metaObject.setValue("createUser", BaseContextCommon.getCurrentUserId());
    }
}
```

---

## 📡 API 接口

### 用户相关

| 接口 | 方法 | 路径 | 说明 |
|------|------|------|------|
| 发送验证码 | GET | `/user/code` | 发送手机验证码 |
| 用户登录 | POST | `/user/login` | 手机号 + 验证码登录 |
| 获取用户信息 | GET | `/user/info` | 获取当前登录用户信息 |
| 用户退出 | POST | `/user/loginout` | 退出登录 |

### 地址相关

| 接口 | 方法 | 路径 | 说明 |
|------|------|------|------|
| 地址列表 | GET | `/addressBook/list` | 查询用户地址列表 |
| 添加地址 | POST | `/addressBook` | 新增地址 |
| 修改地址 | PUT | `/addressBook` | 修改地址 |
| 删除地址 | DELETE | `/addressBook` | 删除地址 |
| 设置默认 | PUT | `/addressBook/default` | 设置默认地址 |
| 获取默认 | GET | `/addressBook/default` | 获取默认地址 |

### 订单相关

| 接口 | 方法 | 路径 | 说明 |
|------|------|------|------|
| 提交订单 | POST | `/order/submit` | 提交订单 |
| 订单分页 | GET | `/order/page` | 后台订单分页查询 |
| 用户订单 | GET | `/order/userPage` | 用户历史订单分页 |
| 再来一单 | POST | `/order/again` | 将订单商品添加到购物车 |
| 更新状态 | PUT | `/order` | 更新订单状态 |

### 购物车相关

| 接口 | 方法 | 路径 | 说明 |
|------|------|------|------|
| 添加购物车 | POST | `/shoppingCart/add` | 添加商品到购物车 |
| 购物车列表 | GET | `/shoppingCart/list` | 查看购物车 |
| 清空购物车 | DELETE | `/shoppingCart/clean` | 清空购物车 |

---

## 🎨 页面展示

### 后台管理端

- 登录页面
- 员工管理
- 分类管理
- 菜品管理
- 套餐管理
- 订单管理

### 移动端

- 登录页面
- 首页（菜品列表）
- 购物车
- 地址管理
- 订单列表
- 个人中心

---

## ⚙️ 配置说明

### 数据库配置

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/regent-takeout
    username: root
    password: your_password
    type: com.alibaba.druid.pool.DruidDataSource
```

### MyBatis-Plus 配置

```yaml
mybatis-plus:
  configuration:
    map-underscore-to-camel-case: true  # 驼峰命名转换
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl  # SQL 日志
  global-config:
    db-config:
      id-type: ASSIGN_ID  # ID 生成策略
```

### 文件上传路径

```yaml
reggie:
  path: S:/Code_Repository/Regent_Takeout/Images/
```

---

## 🔧 常见问题

### 1. 端口被占用

修改 `application.yml` 中的端口号：

```yaml
server:
  port: 8081  # 改为其他端口
```

### 2. 数据库连接失败

检查：
- MySQL 服务是否启动
- 数据库名称、用户名、密码是否正确
- MySQL 8.0+ 需要使用 `com.mysql.cj.jdbc.Driver`

### 3. 图片上传失败

检查：
- `application.yml` 中的 `reggie.path` 配置是否正确
- 上传目录是否有写入权限

### 4. 验证码收不到

验证码默认输出在控制台，生产环境需配置阿里云短信服务。

---

## 📝 开发规范

### 代码规范

- 使用 **Lombok** 简化代码
- 统一使用 **驼峰命名**
- Controller 层做参数校验和调用 Service
- Service 层处理业务逻辑
- 使用 **R** 类统一返回结果

### 命名规范

```
Controller:  XXXController
Service:     XXXService
ServiceImpl: XXXServiceImpl
Mapper:      XXXMapper
Entity:      XXXEntity
Dto:         XXXDto
```

### 注释规范

```java
/**
 * @Author: 程序员Eighteen
 * @CreateTime: 2025-10-11
 * @Description: 功能描述
 */
```

---

## 🤝 贡献指南

欢迎提交 Issue 和 Pull Request！

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交改动 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 提交 Pull Request

---

## 📄 许可证

本项目仅供学习交流使用，未经允许不得用于商业用途。

---

## 👨‍💻 作者

**程序员Eighteen**

- 📧 Email: 3273495516@qq.com
- 🔗 GitHub: [https://github.com/eighteenth-last](https://github.com/your-github)

---

## 🙏 致谢

感谢以下开源项目：

- [Spring Boot](https://spring.io/projects/spring-boot)
- [MyBatis-Plus](https://baomidou.com/)
- [Vue.js](https://vuejs.org/)
- [Element UI](https://element.eleme.cn/)
- [Vant](https://vant-ui.github.io/vant/)

---

## 📊 项目统计

- **代码行数**: 10,000+
- **接口数量**: 50+
- **页面数量**: 20+
- **数据表**: 11 张

---

<p align="center">
  <b>如果这个项目对你有帮助，请给个 ⭐️ Star 吧！</b>
</p>

<p align="center">
  Made with ❤️ by 程序员Eighteen
</p>

