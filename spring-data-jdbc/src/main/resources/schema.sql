drop table IF EXISTS PHOTOS;
drop table IF EXISTS CAR_PART_ANALOGS;
drop table IF EXISTS ANALOGS;
drop table IF EXISTS CAR_PARTS;
drop table IF EXISTS BRANDS;
drop table IF EXISTS MODELS;
drop table IF EXISTS ENGINES;
drop table IF EXISTS COUNTRIES;

-- марки
create TABLE BRANDS(
                    ID   BIGSERIAL    NOT NULL PRIMARY KEY,  --BIGSERIAL это автоикрементное поле
                    NAME VARCHAR(255) NOT NULL UNIQUE -- наименование
                   );

-- модели
create TABLE MODELS(
                     ID           BIGSERIAL    NOT NULL PRIMARY KEY,
                     NAME         VARCHAR(255) NOT NULL UNIQUE, -- наименование
                     YEAR_RELEASE INTEGER      NOT NULL        -- год выпуска
                     --INDEX XIE1_MODELS(YEAR_RELEASE)
                   );
--CREATE INDEX XIE1_MODELS ON MODELS (YEAR_RELEASE);

-- двигатели
create TABLE ENGINES(
                     ID   BIGSERIAL    NOT NULL PRIMARY KEY,
                     NAME VARCHAR(255) NOT NULL UNIQUE  -- наименование
                   );

-- страны производства
create TABLE COUNTRIES(
                       ID   BIGSERIAL    NOT NULL PRIMARY KEY,
                       NAME VARCHAR(255) NOT NULL UNIQUE  -- наименование
                      );

-- запасные части
create TABLE CAR_PARTS(
                       ID           BIGSERIAL NOT NULL PRIMARY KEY,
                       BRAND_ID     BIGINT,  -- марка
                       MODEL_ID     BIGINT,  -- модель
                       ENGINE_ID    BIGINT,  -- двигатель
                       COUNTRY_ID   BIGINT REFERENCES COUNTRIES(ID)   ON delete CASCADE,  -- страна производства
                       VENDOR_CODE  VARCHAR(50)   NOT NULL UNIQUE,   -- каталожный номер
                       SKU          VARCHAR(50)   NOT NULL UNIQUE,   -- артикул
                       NAME         VARCHAR(255)  NOT NULL,          -- наименование
                       DESCRIPTION  VARCHAR(1024) NOT NULL,          -- описание
                       PRICE        FLOAT         NOT NULL,          -- стоимость
                       MANUFACTURER VARCHAR(50)   NOT NULL,          -- производитель
                       RATING       FLOAT         NOT NULL,          -- рейтинг
                       CONSTRAINT `FK01_BRAND`  FOREIGN KEY (BRAND_ID)  REFERENCES BRANDS(ID)  ON delete CASCADE,
                       CONSTRAINT `FK02_MODEL`  FOREIGN KEY (MODEL_ID)  REFERENCES MODELS (ID) ON delete CASCADE,
                       CONSTRAINT `FK03_ENGINE` FOREIGN KEY (ENGINE_ID) REFERENCES ENGINES(ID) ON delete CASCADE
                      );

-- фотографии
create TABLE PHOTOS(
                    ID          BIGSERIAL NOT NULL PRIMARY KEY,
                    CAR_PART_ID BIGINT,  -- запасная часть
                    URL         VARCHAR(8000),
                    FOREIGN KEY(CAR_PART_ID) REFERENCES CAR_PARTS(ID) ON delete CASCADE
                   );

-- аналоги
create TABLE ANALOGS
             (
               ID          BIGSERIAL NOT NULL PRIMARY KEY,
               CAR_PART_ID BIGINT,                            -- вся информация по самой запасной части
               VENDOR      VARCHAR(255)  NOT NULL,            -- продавец
               FOREIGN KEY (CAR_PART_ID) REFERENCES CAR_PARTS(ID) ON delete CASCADE
             );

create TABLE CAR_PART_ANALOGS
             (
               CAR_PART_ID BIGINT ,                  -- запасная часть
               ANALOG_ID   BIGINT,                   -- аналог запасной части
               PRIMARY KEY (CAR_PART_ID, ANALOG_ID),
               FOREIGN KEY (CAR_PART_ID) REFERENCES CAR_PARTS(ID) ON delete CASCADE,
               FOREIGN KEY (ANALOG_ID)   REFERENCES ANALOGS(ID)
             );