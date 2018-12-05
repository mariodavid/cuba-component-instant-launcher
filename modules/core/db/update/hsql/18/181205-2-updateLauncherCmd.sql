alter table DDCIL_LAUNCHER_CMD alter column CODE rename to CODE__U02960 ^
alter table DDCIL_LAUNCHER_CMD alter column CODE__U02960 set null ;
alter table DDCIL_LAUNCHER_CMD add column NAME varchar(255) ^
update DDCIL_LAUNCHER_CMD set NAME = '' where NAME is null ;
alter table DDCIL_LAUNCHER_CMD alter column NAME set not null ;
alter table DDCIL_LAUNCHER_CMD add column DESCRIPTION longvarchar ;
