update DDCIL_USER_INPUT_PARAMETER set NAME = '' where NAME is null ;
alter table DDCIL_USER_INPUT_PARAMETER alter column NAME set not null ;
