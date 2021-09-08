CREATE TABLE JOB (
     id              uuid            primary key,
     status          integer         not null,
     executor_class  text            not null,
     created_at      timestamp       not null default now(),
     data            text,
     start_time      timestamp,
     end_time        timestamp
);

CREATE INDEX IDX_JOB_LOOKUP ON JOB(status, created_at);