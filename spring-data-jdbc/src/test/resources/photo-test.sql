-- марки
insert into BRANDS(NAME) values('Kamaz');
insert into BRANDS(NAME) values('Ural');
insert into BRANDS(NAME) values('Gaz');

-- модели
insert into MODELS(NAME, YEAR_RELEASE) values('Gaz-66', 1964);
insert into MODELS(NAME, YEAR_RELEASE) values('Ural-4320', 1977);
insert into MODELS(NAME, YEAR_RELEASE) values('Kamaz 65201', 2000);

-- двигатели
insert into ENGINES(NAME) values('Diesel');
insert into ENGINES(NAME) values('Petrol');
insert into ENGINES(NAME) values('Turbocharged diesel');

-- страны производства
insert into COUNTRIES(NAME) values('Russia');

-- запасные части
-- ID = 1
insert into CAR_PARTS
            (
              BRAND_ID,
              MODEL_ID_FK,
              ENGINE_ID,
              COUNTRY_ID,
              VENDOR_CODE,
              SKU,
              NAME,
              DESCRIPTION,
              PRICE,
              MANUFACTURER,
              RATING
            )
           values
           (
             3,
             1,
             2,
             1,
             'VLG-750Z370-S',
             '201902-0057',
             'ДИСК СЦЕПЛЕНИЯ',
             'Диск сцепления передает крутящий момент от ДВС к трансмиссии, выступает в качестве составляющей трения.',
             59720.66,
             'ZF SACHS',
             9.7
           );

-- ID = 2
insert into CAR_PARTS
            (
              BRAND_ID,
              MODEL_ID_FK,
              ENGINE_ID,
              COUNTRY_ID,
              VENDOR_CODE,
              SKU,
              NAME,
              DESCRIPTION,
              PRICE,
              MANUFACTURER,
              RATING
            )
            values
            (
              2,
              2,
              1,
              1,
              'URL-4320-01',
              '202212-01/20',
              'КОТЕЛ ПОДОГРЕВАТЕЛЯ',
              'Котел подогревателя предназначен для нагрева жидкости в системе охлаждения и масла в картере двигателя перед его пуском в холодный период времени.',
              28390.99,
              'Ural',
              9.0
            );

-- фотографии
insert into PHOTOS(CAR_PART_ID, URL) values(1, 'https://localhost:8080/carpart/1/#&gid=1&pid=1');
insert into PHOTOS(CAR_PART_ID, URL) values(1, 'https://localhost:8080/carpart/1/#&gid=1&pid=2');
insert into PHOTOS(CAR_PART_ID, URL) values(1, 'https://localhost:8080/carpart/1/#&gid=1&pid=3');
insert into PHOTOS(CAR_PART_ID, URL) values(2, 'https://localhost:8080/carpart/2/#&gid=2&pid=1');