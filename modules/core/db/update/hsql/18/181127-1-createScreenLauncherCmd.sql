create table DDCIL_SCREEN_LAUNCHER_CMD (
    ID varchar(36) not null,
    --
    SCREEN_ID varchar(255) not null,
    SCREEN_LAUNCHER_COMMAND_TYPE varchar(50) not null,
    SCREEN_PARAMETERS_SCRIPT longvarchar,
    OPEN_TYPE varchar(50),
    SCREEN_ENTITY varchar(255),
    --
    primary key (ID)
);
