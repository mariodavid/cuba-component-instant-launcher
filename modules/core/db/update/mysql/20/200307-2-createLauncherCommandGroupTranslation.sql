alter table DDCIL_LAUNCHER_COMMAND_GROUP_TRANSLATION add constraint FK_DDCIL_LCGT_LAUNCHER_COMMAND_GROUP foreign key (LAUNCHER_COMMAND_GROUP_ID) references DDCIL_LAUNCHER_COMMAND_GROUP(ID);
create index IDX_DDCIL_LCGT_LAUNCHER_COMMAND_GROUP on DDCIL_LAUNCHER_COMMAND_GROUP_TRANSLATION (LAUNCHER_COMMAND_GROUP_ID);