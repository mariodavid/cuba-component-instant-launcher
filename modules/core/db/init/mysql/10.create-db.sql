-- begin DDCIL_USER_INPUT_PARAMETER
create table DDCIL_USER_INPUT_PARAMETER (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    REQUIRED boolean,
    TYPE_ varchar(50) not null,
    LAUNCHER_COMMAND_ID varchar(32) not null,
    ENUMERATION_CLASS varchar(255),
    ENTITY_CLASS varchar(255),
    --
    primary key (ID)
)^
-- end DDCIL_USER_INPUT_PARAMETER
-- begin DDCIL_LAUNCHER_CMD_TRANSLATION
create table DDCIL_LAUNCHER_CMD_TRANSLATION (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    LOCALE_ varchar(255) not null,
    TEXT varchar(255) not null,
    LAUNCHER_COMMAND_ID varchar(32) not null,
    --
    primary key (ID)
)^
-- end DDCIL_LAUNCHER_CMD_TRANSLATION
-- begin DDCIL_INPUT_PARAMETER_TRANSLATION
create table DDCIL_INPUT_PARAMETER_TRANSLATION (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    TEST varchar(255) not null,
    LOCALE varchar(255) not null,
    INPUT_PARAMETER_ID varchar(32) not null,
    --
    primary key (ID)
)^
-- end DDCIL_INPUT_PARAMETER_TRANSLATION
-- begin DDCIL_LAUNCHER_CMD
create table DDCIL_LAUNCHER_CMD (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    DTYPE varchar(31),
    --
    NAME varchar(255) not null,
    GROUP_ID varchar(32),
    DESCRIPTION longtext,
    TYPE_ varchar(50) not null,
    SHORTCUT varchar(255),
    --
    primary key (ID)
)^
-- end DDCIL_LAUNCHER_CMD
-- begin DDCIL_LAUNCHER_COMMAND_GROUP
create table DDCIL_LAUNCHER_COMMAND_GROUP (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    CODE varchar(255),
    --
    primary key (ID)
)^
-- end DDCIL_LAUNCHER_COMMAND_GROUP
-- begin DDCIL_SCREEN_LAUNCHER_CMD
create table DDCIL_SCREEN_LAUNCHER_CMD (
    ID varchar(32),
    --
    SCREEN_ID varchar(255) not null,
    SCREEN_LAUNCHER_COMMAND_TYPE varchar(50) not null,
    SCREEN_PARAMETERS_SCRIPT longtext,
    OPEN_TYPE varchar(50) not null,
    SCREEN_ENTITY varchar(255),
    --
    primary key (ID)
)^
-- end DDCIL_SCREEN_LAUNCHER_CMD
-- begin DDCIL_SCRIPT_LAUNCHER_CMD
create table DDCIL_SCRIPT_LAUNCHER_CMD (
    ID varchar(32),
    --
    LAUNCH_SCRIPT longtext not null,
    --
    primary key (ID)
)^
-- end DDCIL_SCRIPT_LAUNCHER_CMD
-- begin DDCIL_BEAN_LAUNCHER_COMMAND
create table DDCIL_BEAN_LAUNCHER_COMMAND (
    ID varchar(32),
    --
    BEAN_NAME varchar(255) not null,
    --
    primary key (ID)
)^
-- end DDCIL_BEAN_LAUNCHER_COMMAND
