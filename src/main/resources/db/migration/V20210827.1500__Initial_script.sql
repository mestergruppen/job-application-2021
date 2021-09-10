CREATE TABLE JOB (
     id              uuid            primary key,
     status          integer         not null,
     executor_class  text            not null,
     data            text,
     request_id      uuid,
     created_at      timestamp       not null default now(),
     start_time      timestamp,
     end_time        timestamp
);

CREATE INDEX IDX_JOB_LOOKUP ON JOB(status, created_at);
CREATE INDEX IDX_JOB_REQUEST_ID ON JOB(request_id);