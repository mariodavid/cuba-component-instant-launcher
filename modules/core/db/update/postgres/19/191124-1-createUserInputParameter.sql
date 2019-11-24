create table DDCIL_USER_INPUT_PARAMETER (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    REQUIRED boolean,
    TYPE_ varchar(50) not null,
    LAUNCHER_COMMAND_ID uuid not null,
    ENUMERATION_CLASS varchar(255),
    --
    primary key (ID)
);