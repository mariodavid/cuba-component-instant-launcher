alter table DDCIL_SCRIPT_LAUNCHER_CMD add constraint FK_DDCIL_SCRIPT_LAUNCHER_CMD_ON_ID foreign key (ID) references DDCIL_LAUNCHER_CMD(ID) on delete CASCADE;
