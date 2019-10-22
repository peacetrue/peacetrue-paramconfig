DROP TABLE IF EXISTS param_config;
CREATE TABLE param_config
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    code          VARCHAR(31)                       NOT NULL COMMENT '编码',
    name          VARCHAR(255)                      NOT NULL COMMENT '名称',
    value         VARCHAR(1022)                     NOT NULL COMMENT '值',
    remark        VARCHAR(255) COMMENT '备注',
    creator_id    BIGINT                            NOT NULL COMMENT '创建者主键',
    created_time  DATETIME                          NOT NULL COMMENT '创建时间',
    modifier_id   BIGINT                            NOT NULL COMMENT '创建者主键',
    modified_time DATETIME                          NOT NULL COMMENT '创建时间'
);


insert into param_config (code, name, value, remark, creator_id, created_time, modifier_id, modified_time)
values ('remind.time2', '提醒时间', '{"mobiles":["18517648593","12374638349","12834746374","18618262909"],"dates":["17:00:00","19:00:00"],"content":"【存金通】{0}快递包裹，仍未在检测系统进行收货，已超时{1}小时{2}分钟，请尽快完成收货。","enabled":true}', '每天19点', 1, '2010-01-01 01:01:01', 1, '2010-01-01 01:01:01');



