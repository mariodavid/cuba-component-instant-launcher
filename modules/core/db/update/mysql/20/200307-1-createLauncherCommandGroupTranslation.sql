create table DDCIL_LAUNCHER_COMMAND_GROUP_TRANSLATION (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    LOCALE varchar(255) not null,
    TEXT varchar(255) not null,
    LAUNCHER_COMMAND_GROUP_ID varchar(32) not null,
    --
    primary key (ID)
);