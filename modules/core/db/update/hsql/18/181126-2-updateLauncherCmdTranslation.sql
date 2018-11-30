alter table DDCIL_LAUNCHER_CMD_TRANSLATION alter column LANGUAGE_ rename to LANGUAGE___U87146 ^
alter table DDCIL_LAUNCHER_CMD_TRANSLATION alter column LANGUAGE___U87146 set null ;
alter table DDCIL_LAUNCHER_CMD_TRANSLATION add column LOCALE varchar(255) ^
update DDCIL_LAUNCHER_CMD_TRANSLATION set LOCALE = '' where LOCALE is null ;
alter table DDCIL_LAUNCHER_CMD_TRANSLATION alter column LOCALE set not null ;
