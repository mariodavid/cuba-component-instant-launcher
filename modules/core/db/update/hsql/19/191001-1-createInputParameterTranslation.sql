create table DDCIL_INPUT_PARAMETER_TRANSLATION (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TEST varchar(255) not null,
    LOCALE varchar(255) not null,
    INPUT_PARAMETER_ID varchar(36) not null,
    --
    primary key (ID)
);