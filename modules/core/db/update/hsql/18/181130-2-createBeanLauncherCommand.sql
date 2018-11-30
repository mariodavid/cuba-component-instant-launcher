alter table DDCIL_BEAN_LAUNCHER_COMMAND add constraint FK_DDCIL_BEAN_LAUNCHER_COMMAND_ON_ID foreign key (ID) references DDCIL_LAUNCHER_CMD(ID) on delete CASCADE;
