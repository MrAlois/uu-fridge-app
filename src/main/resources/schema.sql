drop table if exists PUBLIC.FOOD_LISTING_CLAIM;
drop table if exists PUBLIC.FOOD_LISTING_PHOTO;
drop table if exists PUBLIC.FOOD_LISTING;
drop table if exists PUBLIC.APP_USER;

create table PUBLIC.APP_USER
(
    USER_ID              INTEGER auto_increment,
    NAME                 CHARACTER VARYING(64) not null,
    DEFAULT_LOCATION     CHARACTER VARYING(64),
    DIETARY_RESTRICTIONS CHARACTER VARYING(128),
    EMAIL                CHARACTER VARYING(64) not null,
    PHONE                CHARACTER VARYING(64),
    constraint USER_PK
        primary key (USER_ID)
);

create table PUBLIC.FOOD_LISTING
(
    LISTING_ID        INTEGER auto_increment,
    DONOR_ID          INTEGER                                         not null,
    SHORT_DESCRIPTION CHARACTER VARYING(64)                           not null,
    DESCRIPTION       CHARACTER VARYING(256)                          not null,
    QUANTITY          INTEGER                                         not null,
    EXPIRY_DATE       TIMESTAMP                                       not null,
    PICKUP_LOCATION   CHARACTER VARYING(128)                          not null,
    CREATED           TIMESTAMP             default CURRENT_TIMESTAMP not null,
    DIETARY_INFO      CHARACTER VARYING(64),
    constraint FOOD_LISTING_PK
        primary key (LISTING_ID),
    constraint FOOD_LISTING_APP_USER_USER_ID_FK
        foreign key (DONOR_ID) references APP_USER
);

create table PUBLIC.FOOD_LISTING_CLAIM
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
        foreign key (LISTING_ID) references FOOD_LISTING
);

create table PUBLIC.FOOD_LISTING_PHOTO
(
    PHOTO_ID   INTEGER auto_increment,
    LISTING_ID INTEGER             not null,
    DATA       BINARY LARGE OBJECT not null,
    constraint FOOD_LISTING_PHOTO_PK
        primary key (PHOTO_ID),
    constraint FOOD_LISTING_PHOTO_FOOD_LISTING_LISTING_ID_FK
        foreign key (LISTING_ID) references FOOD_LISTING
);
