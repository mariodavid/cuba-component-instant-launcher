alter table DDCIL_INPUT_PARAMETER_TRANSLATION add constraint FK_DDCIL_INPUT_PARAMETER_TRANSLATION_INPUT_PARAMETER foreign key (INPUT_PARAMETER_ID) references DDCIL_USER_INPUT_PARAMETER(ID);
create index IDX_DDCIL_INPUT_PARAMETER_TRANSLATION_INPUT_PARAMETER on DDCIL_INPUT_PARAMETER_TRANSLATION (INPUT_PARAMETER_ID);