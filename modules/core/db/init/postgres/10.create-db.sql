-- begin DDCIL_LAUNCHER_CMD
create table DDCIL_LAUNCHER_CMD (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    DTYPE varchar(31),
    --
    NAME varchar(255) not null,
    GROUP_ID uuid,
    DESCRIPTION text,
    TYPE_ varchar(50) not null,
    SHOW_IN_MAIN_MENU boolean,
    SHORTCUT varchar(255),
    --
    primary key (ID)
)^
-- end DDCIL_LAUNCHER_CMD
-- begin DDCIL_LAUNCHER_CMD_TRANSLATION
create table DDCIL_LAUNCHER_CMD_TRANSLATION (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    LOCALE_ varchar(255) not null,
    TEXT varchar(255) not null,
    LAUNCHER_COMMAND_ID uuid not null,
    --
    primary key (ID)
)^
-- end DDCIL_LAUNCHER_CMD_TRANSLATION
-- begin DDCIL_SCREEN_LAUNCHER_CMD
create table DDCIL_SCREEN_LAUNCHER_CMD (
    ID uuid,
    --
    SCREEN_ID varchar(255) not null,
    SCREEN_LAUNCHER_COMMAND_TYPE varchar(50) not null,
    SCREEN_PARAMETERS_SCRIPT text,
    OPEN_TYPE varchar(50) not null,
    SCREEN_ENTITY varchar(255),
    --
    primary key (ID)
)^
-- end DDCIL_SCREEN_LAUNCHER_CMD
-- begin DDCIL_SCRIPT_LAUNCHER_CMD
create table DDCIL_SCRIPT_LAUNCHER_CMD (
    ID uuid,
    --
    LAUNCH_SCRIPT text not null,
    --
    primary key (ID)
)^
-- end DDCIL_SCRIPT_LAUNCHER_CMD
-- begin DDCIL_BEAN_LAUNCHER_COMMAND
create table DDCIL_BEAN_LAUNCHER_COMMAND (
    ID uuid,
    --
    BEAN_NAME varchar(255) not null,
    --
    primary key (ID)
)^
-- end DDCIL_BEAN_LAUNCHER_COMMAND
-- begin DDCIL_LAUNCHER_COMMAND_GROUP
create table DDCIL_LAUNCHER_COMMAND_GROUP (
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
    CODE varchar(255),
    SHOW_IN_MAIN_MENU boolean,
    --
    primary key (ID)
)^
-- end DDCIL_LAUNCHER_COMMAND_GROUP
-- begin DDCIL_USER_INPUT_PARAMETER
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
    ENTITY_CLASS varchar(255),
    --
    primary key (ID)
)^
-- end DDCIL_USER_INPUT_PARAMETER
-- begin DDCIL_INPUT_PARAMETER_TRANSLATION
create table DDCIL_INPUT_PARAMETER_TRANSLATION (
    ID uuid,
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
    INPUT_PARAMETER_ID uuid not null,
    --
    primary key (ID)
)^
-- end DDCIL_INPUT_PARAMETER_TRANSLATION
-- begin DDCIL_LAUNCHER_COMMAND_GROUP_TRANSLATION
create table DDCIL_LAUNCHER_COMMAND_GROUP_TRANSLATION (
    ID uuid,
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
    LAUNCHER_COMMAND_GROUP_ID uuid not null,
    --
    primary key (ID)
)^
-- end DDCIL_LAUNCHER_COMMAND_GROUP_TRANSLATION
