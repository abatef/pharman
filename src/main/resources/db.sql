create table employee
(
    emp_id      serial primary key,
    name        varchar(60) not null,
    username    varchar(20) not null unique,
    age         smallint,
    sex         varchar(2) check ( sex in ('F', 'M') ),
    salary      real check ( salary >= 0 ),
    work_status varchar(20),
    password    text not null,
    created_at  timestamp default CURRENT_TIMESTAMP,
    updated_at  timestamp default CURRENT_TIMESTAMP
);


create table emp_role
(
    emp_id integer,
    role   varchar(10) check ( role in ('ADMIN', 'SELL', 'ORDER') ),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    foreign key (emp_id) references employee(emp_id) on delete cascade,
    primary key (emp_id, role)
);
