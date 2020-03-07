create table DDCIL_LAUNCHER_COMMAND_GROUP_TRANSLATION (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    LOCALE varchar(255) not null,
    TEXT varchar(255) not null,
    LAUNCHER_COMMAND_GROUP_ID varchar(36) not null,
    --
    primary key (ID)
);