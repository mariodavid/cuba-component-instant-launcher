-- begin DDCIL_LAUNCHER_CMD
alter table DDCIL_LAUNCHER_CMD add constraint FK_DDCIL_LAUNCHER_CMD_ON_GROUP foreign key (GROUP_ID) references DDCIL_LAUNCHER_COMMAND_GROUP(ID)^
create index IDX_DDCIL_LAUNCHER_CMD_ON_GROUP on DDCIL_LAUNCHER_CMD (GROUP_ID)^
-- end DDCIL_LAUNCHER_CMD
-- begin DDCIL_LAUNCHER_CMD_TRANSLATION
alter table DDCIL_LAUNCHER_CMD_TRANSLATION add constraint FK_DDCIL_LAUNCHER_CMD_TRANSLATION_ON_LAUNCHER_COMMAND foreign key (LAUNCHER_COMMAND_ID) references DDCIL_LAUNCHER_CMD(ID)^
create index IDX_DDCIL_LAUNCHER_CMD_TRANSLATION_ON_LAUNCHER_COMMAND on DDCIL_LAUNCHER_CMD_TRANSLATION (LAUNCHER_COMMAND_ID)^
create index IDX_DDCIL_LAUNCHER_COMMAND_TRANSLATION on DDCIL_LAUNCHER_CMD_TRANSLATION (LOCALE_, TEXT) ^
-- end DDCIL_LAUNCHER_CMD_TRANSLATION
-- begin DDCIL_SCREEN_LAUNCHER_CMD
alter table DDCIL_SCREEN_LAUNCHER_CMD add constraint FK_DDCIL_SCREEN_LAUNCHER_CMD_ON_ID foreign key (ID) references DDCIL_LAUNCHER_CMD(ID) on delete CASCADE^
-- end DDCIL_SCREEN_LAUNCHER_CMD
-- begin DDCIL_SCRIPT_LAUNCHER_CMD
alter table DDCIL_SCRIPT_LAUNCHER_CMD add constraint FK_DDCIL_SCRIPT_LAUNCHER_CMD_ON_ID foreign key (ID) references DDCIL_LAUNCHER_CMD(ID) on delete CASCADE^
-- end DDCIL_SCRIPT_LAUNCHER_CMD
-- begin DDCIL_BEAN_LAUNCHER_COMMAND
alter table DDCIL_BEAN_LAUNCHER_COMMAND add constraint FK_DDCIL_BEAN_LAUNCHER_COMMAND_ON_ID foreign key (ID) references DDCIL_LAUNCHER_CMD(ID) on delete CASCADE^
-- end DDCIL_BEAN_LAUNCHER_COMMAND
