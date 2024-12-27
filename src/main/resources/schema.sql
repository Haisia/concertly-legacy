create table if not exists users
(
  id          int         not null auto_increment comment 'pk' primary key,
  email       varchar(255) not null comment '이메일',
  password    varchar(255) not null comment '패스워드',
  point       bigint           not null comment '보유 포인트',

  deleted     varchar(1) not null default 'N',
  created_at  datetime(6) not null default now(),
  created_by  varchar(255) null,
  updated_at  datetime(6) null,
  updated_by  varchar(255) null,
  constraint uk_users_email unique (email)
) comment '유저 테이블';

create table if not exists concerts
(
  id          int            not null auto_increment comment 'pk' primary key,
  title       varchar(255)   not null comment '콘서트 이름',
  location    varchar(255)   not null comment '콘서트 위치',
  start_time  datetime(6)    not null comment '콘서트 시작 일시',
  end_time    datetime(6)    not null comment '콘서트 종료 일시',

  deleted     varchar(1) not null default 'N',
  created_at  datetime(6) not null default now(),
  created_by  varchar(255) null,
  updated_at  datetime(6) null,
  updated_by  varchar(255) null
) comment '콘서트 테이블';

create table if not exists seats
(
  id            int           not null auto_increment comment 'pk' primary key,
  concert_id    int           not null comment '콘서트 pk',
  seat_number   varchar(255)  not null comment '좌석 번호 ex) a1, a2, ... , e1, e2, ...',
  status        enum('AVAILABLE', 'RESERVED')   not null default 'AVAILABLE' comment '좌석 상태 ex) AVAILABLE, RESERVED',
  price         BIGINT        not null comment '좌석 가격',

  deleted     varchar(1) not null default 'N',
  created_at  datetime(6) not null default now(),
  created_by  varchar(255) null,
  updated_at  datetime(6) null,
  updated_by  varchar(255) null,
  constraint fk_seats_concert_id foreign key (concert_id) references concerts (id)
) comment '콘서트 좌석 테이블';

create table if not exists reservation
(
  id              int           not null auto_increment comment 'pk' primary key,
  seat_id         int           not null comment '좌석 pk',
  user_id         int  not null comment '예약자',

  deleted     varchar(1) not null default 'N',
  created_at  datetime(6) not null default now(),
  created_by  varchar(255) null,
  updated_at  datetime(6) null,
  updated_by  varchar(255) null,
  constraint fk_reservation_seat_id foreign key (seat_id) references concerts (id),
  constraint fk_reservation_user_id foreign key (user_id) references concerts (id)
) comment '콘서트 예약 테이블';