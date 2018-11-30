alter table DDCIL_LAUNCHER_CMD add column TYPE_ varchar(50) ^
update DDCIL_LAUNCHER_CMD set TYPE_ = 'SCRIPT' where TYPE_ is null ;
alter table DDCIL_LAUNCHER_CMD alter column TYPE_ set not null ;
alter table DDCIL_LAUNCHER_CMD add column WINDOW_ALIAS varchar(255) ;
alter table DDCIL_LAUNCHER_CMD add column OPEN_TYPE varchar(50) ^
update DDCIL_LAUNCHER_CMD set OPEN_TYPE = 'NEW_TAB' where OPEN_TYPE is null ;
alter table DDCIL_LAUNCHER_CMD alter column OPEN_TYPE set not null ;
alter table DDCIL_LAUNCHER_CMD add column DTYPE varchar(100) ;
