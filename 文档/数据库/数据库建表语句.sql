create table admin(
    id bigint primary key NOT NULL AUTO_INCREMENT,
    name  varchar(200),
    password  varchar(200)
)

create table user(
    id  bigint  primary key NOT NULL AUTO_INCREMENT, 
    name  varchar(200),
    password  varchar(200),

    -- 用户等级
    level  int
)

create table device(
    id  bigint  primary key NOT NULL AUTO_INCREMENT,
    name  varchar(200),
    addressOfDevice  varchar(200), -- 设备安装地址
    organizationId Long, -- 组织
    type int,
    attributionOfDevice  varchar(200) -- 设备归属地
)

create table config(
    max_temperature  int, -- 温度阈值
    min_temperature  int,-- 温度阈值
    max_humidity  int,   -- 湿度阈值
    min_humidity  int -- 湿度阈值
)

create table temperatureRecord(
    id bigint primary key NOT NULL AUTO_INCREMENT,
    updateDate  DATETIME,
    temperature varchar(10)
)

create table humidityRecord(
    id bigint primary key NOT NULL AUTO_INCREMENT,
    updateDate  DATETIME,
    humidity varchar(10)
)

create table organization(
   id  bigint  primary key NOT NULL AUTO_INCREMENT,
   name varchar(200)
)

create table deviceInfo(
    deviceId bigint primary key,
    times int,
    signalIntensity int,
    statusOfCharge int,
    statusOfLight int,
    temperature varchar(10),
    humidity varchar(10),
    filed1  varchar(10),
    filed2  varchar(10),
    filed3  varchar(10),
    filed4  varchar(10),
    updateTime timestamp
)


