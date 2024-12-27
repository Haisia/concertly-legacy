create table if not exists users
(
  id          int         not null auto_increment comment 'pk'  primary key,
  email       varchar(255) not null comment '이메일',
  password    varchar(255) not null comment '패스워드',
  point       bigint           not null comment '보유 포인트',

  deleted     varchar(1) not null default 'N' check (deleted in ('N', 'Y')),
  created_at  datetime(6) not null default now(),
  created_by  varchar(255) null,
  updated_at  datetime(6) null,
  updated_by  varchar(255) null,
  constraint uk_users_email unique (email)
);
