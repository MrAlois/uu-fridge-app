drop table if exists FOOD_LISTING_CLAIM;
drop table if exists FOOD_LISTING_PHOTO;
drop table if exists FOOD_LISTING;
drop table if exists APP_USER;

create table APP_USER
(
    USER_ID              INTEGER auto_increment,
    NAME                 CHARACTER VARYING(64)                  not null,
    DEFAULT_LOCATION     CHARACTER VARYING(128),
    DEFAULT_LAT          DOUBLE PRECISION      default 0.0,
    DEFAULT_LNG          DOUBLE PRECISION      default 0.0,
    ALLERGIC_TO          CHARACTER VARYING(64),
    EMAIL                CHARACTER VARYING(64)                  not null,
    PHONE                CHARACTER VARYING(64),
    constraint USER_PK
        primary key (USER_ID),
    constraint USER_UK
        unique (EMAIL)
);

create table FOOD_LISTING
(
    LISTING_ID        INTEGER auto_increment,
    DONOR_ID          INTEGER                                         not null,
    SHORT_DESCRIPTION CHARACTER VARYING(64)                           not null,
    DESCRIPTION       CHARACTER VARYING(512)                          not null,
    EXPIRY_DATE       TIMESTAMP                                       not null,
    PICKUP_LOCATION   CHARACTER VARYING(128)                          not null,
    PICKUP_LAT        DOUBLE PRECISION      default 0.0               not null,
    PICKUP_LNG        DOUBLE PRECISION      default 0.0               not null,
    CREATED           TIMESTAMP             default CURRENT_TIMESTAMP not null,
    ALLERGENS         CHARACTER VARYING(64),
    constraint FOOD_LISTING_PK
        primary key (LISTING_ID),
    constraint FOOD_LISTING_APP_USER_USER_ID_FK
        foreign key (DONOR_ID) references APP_USER
);

create table FOOD_LISTING_CLAIM
(
    CLAIM_ID   INTEGER auto_increment,
    USER_ID    INTEGER                                         not null,
    LISTING_ID INTEGER                                         not null,
    CLAIMED    TIMESTAMP             default CURRENT_TIMESTAMP not null,
    STATE      CHARACTER VARYING(32) default 'WAITING'         not null,
    constraint FOOD_LISTING_CLAIM_PK
        primary key (CLAIM_ID),
    constraint FOOD_LISTING_CLAIM_APP_USER_USER_ID_FK
        foreign key (USER_ID) references APP_USER,
    constraint FOOD_LISTING_CLAIM_FOOD_LISTING_LISTING_ID_FK
        foreign key (LISTING_ID) references FOOD_LISTING on delete cascade
);

create table FOOD_LISTING_PHOTO
(
    PHOTO_ID   INTEGER auto_increment,
    LISTING_ID INTEGER             not null,
    DATA       BINARY LARGE OBJECT not null,
    constraint FOOD_LISTING_PHOTO_PK
        primary key (PHOTO_ID),
    constraint FOOD_LISTING_PHOTO_FOOD_LISTING_LISTING_ID_FK
        foreign key (LISTING_ID) references FOOD_LISTING on delete cascade
);

