-- марки
insert into BRANDS(NAME)
values('Kamaz'), ('Ural'), ('Gaz');

-- модели
insert into MODELS(NAME, YEAR_RELEASE)
values('Gaz-66', 1964), ('Ural-4320', 1977), ('Kamaz 65201', 2000);

-- двигатели
insert into ENGINES(NAME)
values('Diesel'), ('Petrol'), ('Turbocharged diesel');

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
             'GZ-750Z370-S',
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
              'SKU-202212-01/20',
              'КОТЕЛ ПОДОГРЕВАТЕЛЯ',
              'Котел подогревателя предназначен для нагрева жидкости в системе охлаждения и масла в картере двигателя перед его пуском в холодный период времени.',
              28390.99,
              'Ural',
              9.0
            );

-- ID = 3
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
              'URL-4320-02',
              'SKU-202212-3703010',
              'Аккумулятор',
              'Аккумулятор – химический источник тока, выполняющий функции благодаря которым производится старт и движение машины.',
              14187,
              'Ural',
              10.0
            );

-- аналоги
-- ID = 1
insert into ANALOGS (CAR_PART_ID, VENDOR) values (3, 'No name (China)');
insert into CAR_PART_ANALOGS (CAR_PART_ID, ANALOG_ID) values (1, 1);

-- ID = 2
insert into ANALOGS (CAR_PART_ID, VENDOR) values (2, 'KAMAZ (Russia)');
insert into CAR_PART_ANALOGS (CAR_PART_ID, ANALOG_ID) values (1, 2);
