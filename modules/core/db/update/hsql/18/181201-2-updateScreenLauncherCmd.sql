update DDCIL_SCREEN_LAUNCHER_CMD set OPEN_TYPE = 'NEW_TAB' where OPEN_TYPE is null ;
alter table DDCIL_SCREEN_LAUNCHER_CMD alter column OPEN_TYPE set not null ;
