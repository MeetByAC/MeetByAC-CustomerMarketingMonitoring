前后端交互统一结果封装对象：

![img](file:///C:\Users\77944\AppData\Local\Temp\QQ_1722333227017.png)

# 1. 营销信息管理





# 2. 用户管理

## 2.1 用户列表查询

### 2.1.1 基本信息

> 请求路径: /users
> 
> 请求方式: GET
>
> 接口描述: 该接口用于用户列表数据的条件分页查询

### 2.1.2 请求参数

参数格式: queryString

参数说明: 

| 参数名    | 类型   | 是否必须 | 备注                                       |
| --------- | ------ | -------- | ------------------------------------------ |
| page      | number | 是       | 分页查询的页码，如果未指定，默认为1        |
| pageSize  | number | 是       | 分页查询的每页记录数，如果未指定，默认为10 |
| jobNumber | string | 否       | 工号                                       |
| dept      | number | 否       | 部门号                                     |
| role      | number | 否       | 角色                                       |

请求数据样例:

```http
/users?page=1&pageSize=10&jobNumber=20240001&dept=1&role=2
```

### 2.1.3 响应数据

参数格式: application/json

参数说明: 

| 参数名        | 类型     | 是否必须 | 备注                           |
| ------------- | -------- | -------- | ------------------------------ |
| code          | number   | 必须     | 响应码，1 代表成功，0 代表失败 |
| msg           | string   | 非必须   | 提示信息                       |
| data          | object[] | 非必须   | 返回的数据                     |
| \|- total | number | 必须 | 查询结果的总记录数 |
| \|- rows | object[] | 必须 | 数据列表，每个item类型为object |
| \|- id        | number   | 非必须   | id                             |
| \|- username  | string   | 非必须   | 用户名                         |
| \|- password  | string   | 非必须   | 密码                           |
| \|- jobNumber | string   | 非必须   | 工号                           |
| \|- dept      | number | 非必须   | 部门号                          |
| \|- role      | number   | 非必须   | 角色                           |
| \|- createTime      | string    | 非必须   | 创建时间                           |
| \|- updateTime      | string   | 非必须   | 修改时间                           |

响应数据样例

```json
{
    "code": 1,
    "msg": "success",
    "data": {
        "total": 2,
        "rows": [
            {
                "id": 7,
                "username": "孙七",
                "password": "123456",
                "jobNumber": "20240007",
                "dept": 2,
                "role": 2,
                "createTime": "2024-07-31T09:55:17",
                "updateTime": "2024-07-31T09:55:17"
            },
            {
                "id": 8,
                "username": "周八",
                "password": "123456",
                "jobNumber": "20240008",
                "dept": 2,
                "role": 2,
                "createTime": "2024-07-31T09:55:17",
                "updateTime": "2024-07-31T09:55:17"
            }
        ]
    }
}
```

## 2.2 删除用户

### 2.2.1 基本信息

> 请求路径: /users/{ids}
> 
> 请求方式: DELETE
>
> 接口描述: 该接口用于批量删除员工的数据信息

备注: 删除单条用户可视为批量删除的特殊情况

### 2.2.2 请求参数

参数格式：路径参数

参数说明：

| 参数名 | 类型       | 是否必须 | 备注       |
| ------ | ---------- | -------- | ---------- |
| ids    | 数组 array | 必须     | 用户ID数组 |

请求参数样例：

```http
/users/1,2,3
```

### 2.2.3 响应数据

参数格式: application/json

参数说明:

| 参数名 | 类型   | 是否必须 | 备注                           |
| ------ | ------ | -------- | ------------------------------ |
| code   | number | 必须     | 响应码，1 代表成功，0 代表失败 |
| msg    | string | 非必须   | 提示信息                       |
| data   | object | 非必须   | 返回的数据                     |

响应数据样例:

```json
{
    "code": 1,
    "msg": "success",
    "data": null
}
```

## 2.3 添加用户

### 2.3.1 基本信息

> 请求路径: /users
> 
> 请求方式: POST
>
> 接口描述: 该接口用于添加用户数据

### 2.3.2 请求参数

参数格式：application/json

参数说明：

| 参数名    | 类型   | 是否必须 | 备注                 |
| --------- | ------ | -------- | -------------------- |
| username  | string | 必须     | 用户名               |
| password  | string | 非必须   | 密码，默认值为123456 |
| jobNumber | string | 必须     | 工号                 |
| dept      | number | 必须     | 部门号               |
| role      | number | 必须     | 角色                 |

请求参数样例：

```json
{
   "username": "王五",
   "password": "123456",
   "jobNumber": "20240011",
   "dept": 3,
   "role": 2,
}
```

### 2.3.3 响应数据

参数格式：application/json 

参数说明：

| 参数名 | 类型   | 是否必须 | 备注                           |
| ------ | ------ | -------- | ------------------------------ |
| code   | number | 必须     | 响应码，1 代表成功，0 代表失败 |
| msg    | string | 非必须   | 提示信息                       |
| data   | object | 非必须   | 返回的数据                     |

响应数据样例:

```json
{
    "code": 1,
    "msg": "success",
    "data": null
}
```

## 2.4 根据ID查询用户

### 2.4.1 基本信息

> 请求路径: /users/{id}
> 
> 请求方式: GET
>
> 接口描述: 该接口用于根据ID查询用户数据

### 2.4.2 请求参数

参数格式：路径参数 

参数说明：

| 参数名 | 类型   | 是否必须 | 备注   |
| ------ | ------ | -------- | ------ |
| id     | number | 必须     | 用户ID |

请求参数样例：

```http
/users/1
```

### 2.4.3 响应数据

参数格式：application/json 

参数说明：

| 参数名        | 类型     | 是否必须 | 备注                           |
| ------------- | -------- | -------- | ------------------------------ |
| code          | number   | 必须     | 响应码，1 代表成功，0 代表失败 |
| msg           | string   | 非必须   | 提示信息                       |
| data          | object[] | 非必须   | 返回的数据                     |
| \|- id        | number   | 非必须   | id                             |
| \|- username  | string   | 非必须   | 用户名                         |
| \|- password  | string   | 非必须   | 密码                           |
| \|- jobNumber | string   | 非必须   | 工号                           |
| \|- dept      | number | 非必须   | 部门号                          |
| \|- role      | number   | 非必须   | 角色                           |
| \|- createTime      | string    | 非必须   | 创建时间                           |
| \|- updateTime      | string   | 非必须   | 修改时间                           |

响应数据样例

```json
{
    "code": 1,
    "msg": "success",
    "data": {
        "id": 20,
        "username": "茅十八",
        "password": "123456",
        "jobNumber": "20240018",
        "dept": 3,
        "role": 2,
        "createTime": "2024-07-31T10:30:10",
        "updateTime": "2024-07-31T10:30:10"
    }
}
```

## 2.5 修改用户

### 2.5.1 基本信息

> 请求路径: /users
> 
> 请求方式: PUT
>
> 接口描述: 该接口用于修改用户数据

### 2.5.2 请求参数

参数格式: application/json

参数说明: 

| 参数名 | 类型   | 是否必须 | 备注   |
| ------ | ------ | -------- | ------ |
| id     | number | 必须     | 用户ID |
| username   | string | 非必须    | 用户名               |
| password   | string | 非必须   | 密码，默认值为123456 |
| jobNumber  | string | 非必须  | 工号                 |
| dept       | number | 非必须  | 部门号               |
| role       | number | 非必须  | 角色                 |

请求参数样例:

```json
{
   "username": "赵六",
   "password": 123456,
   "jobNumber": 20240022,
   "dept": 3,
   "role": 1,
}
```

### 2.5.3 响应数据

参数格式：application/json

参数说明:

| 参数名 | 类型   | 是否必须 | 备注                           |
| ------ | ------ | -------- | ------------------------------ |
| code   | number | 必须     | 响应码，1 代表成功，0 代表失败 |
| msg    | string | 非必须   | 提示信息                       |
| data   | object | 非必须   | 返回的数据                     |

响应数据样例:

```json
{
    "code": 1,
    "msg": "success",
    "data": null
}
```

# 3. 其他接口

## 3.1 登录

### 3.1.1 基本信息

> 请求路径: /login
> 
> 请求方式: POST
>
> 接口描述: 该接口用于用户登录客户营销监测系统，登录完毕后，系统下发JWT令牌。

### 3.1.2 请求参数

参数格式：application/json

参数说明：

| 名称      | 类型   | 是否必须 | 备注                                                         |
| --------- | ------ | -------- | ------------------------------------------------------------ |
| jobNumber | string | 必须     | 工号                                                         |
| password  | string | 必须     | 密码(需要做加密处理后再给后端，后端用相应的加密算法解密后再验证) |
| role      | number | 必须     | 角色权限                                                     |

请求数据样例：

```json
{
    "jobNumber": "20240001",
    "password": "123456",
    "role": 1
}
```

### 3.1.3 响应数据

参数格式: application/json

参数说明:

| 参数名 | 类型   | 是否必须 | 备注                           |
| ------ | ------ | -------- | ------------------------------ |
| code   | number | 必须     | 响应码，1 代表成功，0 代表失败 |
| msg    | string | 非必须   | 提示信息                       |
| data   | string | 必须     | 返回的数据， jwt令牌           |

响应数据样例:

```json
{
    "code": 1,
    "msg": "success",
    "data": "eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoi6YeR5bq4IiwiaWQiOjEsInVzZXJuYW1l
    IjoiamlueW9uZyIsImV4cCI6MTY2MjIwNzA0OH0.KkUc_CXJZJ8Dd063eImx4H9Ojfrr6XMJ-yVzaWCVZCo"
}
```
