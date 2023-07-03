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
              MODEL_ID,
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
             1,
             1,
             'GZ-750Z370-S',
             'SKU-201902-0057',
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
              MODEL_ID,
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
              MODEL_ID,
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

-- ID = 4
insert into CAR_PARTS
            (
              BRAND_ID,
              MODEL_ID,
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
              1,
              3,
              3,
              1,
              'KMZ-740.51-1117010-01',
              'SKU-202212-1117010',
              'ТОПЛИВНЫЙ ТОНКОЙ ОЧИСТКИ ЕВРО С ПОДОГРЕВОМ',
              'Фильтр топливный тонкой очистки ЕВРО с подогревом купить на автомобиль КАМАЗ оптом и в розницу с доставкой по России. Сертификаты соответствия, схемы и инструкции по установке детали.',
              10760,
              'LAZZ',
              10.0
            );

-- ID = 5
insert into CAR_PARTS
            (
              BRAND_ID,
              MODEL_ID,
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
              1,
              3,
              3,
              1,
              'KMZ-7403-1008021-02',
              'SKU-202212-1008021',
              'КОЛЛЕКТОР ВЫПУСКНОЙ ЕВРО ЛЕВЫЙ',
              'Коллектор выпускной ЕВРО левый купить на автомобиль КАМАЗ оптом и в розницу с доставкой по России. Сертификаты соответствия, схемы и инструкции по установке детали.',
              8515,
              'POO KAMAZ',
              5.0
            );

-- ID = 6
insert into CAR_PARTS
            (
              BRAND_ID,
              MODEL_ID,
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
              1,
              3,
              3,
              1,
              'KMZ-7406-1012010-03',
              'SKU-202212-1012010',
              'ФИЛЬТР МАСЛЯНЫЙ ГРУБОЙ ОЧИСТКИ ЕВРО ЧАСТИЧНОПОТОЧНЫЙ',
              'Фильтр масляный грубой очистки ЕВРО частичнопоточный купить на автомобиль КАМАЗ оптом и в розницу с доставкой по России. Сертификаты соответствия, схемы и инструкции по установке детали.',
              1545,
              'LAAZ',
              7.7
            );

-- ID = 7
insert into CAR_PARTS
            (
              BRAND_ID,
              MODEL_ID,
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
             'GZ-511.1601130-280',
             'SKU-20221228-0948-01',
             'ДИСК СЦЕПЛЕНИЯ ПОД ЛЕПЕСТКОВУЮ КОРЗИНУ ГАЗ-53,3307,66,3308,ПАЗ',
             'диск сцепления под лепестковую корзину ГАЗ-53,3307,66,3308,ПАЗ в упак.(змз).',
             5320,
             'No name',
             3.7
            );

-- ID = 8
insert into CAR_PARTS
            (
            BRAND_ID,
            MODEL_ID,
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
            1,
            3,
            3,
            1,
            'KMZ-740.60-1008025-20',
            'SKU-6520-6020-43-01',
            'Коллектор камаз-евро-3,4,5 выпускной',
            'Коллектор выпускной КАМАЗ левый. ДВС- КАМАЗ 740- Евро 3,4,5. В наличии. Возможно отправка ч/з ТК.',
            5000,
            'KAMAZ (Russia)',
            5.5
            );

-- ID = 9
insert into CAR_PARTS
            (
            BRAND_ID,
            MODEL_ID,
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
            1,
            3,
            3,
            1,
            'KMZ-3937478-2',
            'SKU-3937478-01',
            'Коллектор выпускной 1-2 цилиндров ISLe 3937478',
            'Новый оригинальный! Коллектор выпускной малый (1-2 цилиндра) 3937478, 3943850, 39377477, 3968361 на двигатель Cummins ISLe, L345, ISCE, QSC8.3, QSL9.',
            23000,
            'KAMAZ (Russia)',
            10.0
            );

-- ID = 10
insert into CAR_PARTS
            (
            BRAND_ID,
            MODEL_ID,
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
            'URL-4320-020',
            'SKU-20221228-1032-01',
            'Аккумулятор 190 АЧ',
            'Аккумулятор 190 ач на Камаз. Цена указана с учетом сдачи старого АКБ. Без обмена цена 9500р.',
            7500,
            'Ural',
            10.0
            );

-- фотографии
insert into PHOTOS(CAR_PART_ID, URL) values(1, 'https://localhost:8080/carpart/1/#&gid=1&pid=1');
insert into PHOTOS(CAR_PART_ID, URL) values(1, 'https://localhost:8080/carpart/1/#&gid=1&pid=2');
insert into PHOTOS(CAR_PART_ID, URL) values(1, 'https://localhost:8080/carpart/1/#&gid=1&pid=3');
insert into PHOTOS(CAR_PART_ID, URL) values(2, 'https://localhost:8080/carpart/2/#&gid=1&pid=1');
insert into PHOTOS(CAR_PART_ID, URL) values(2, 'https://localhost:8080/carpart/2/#&gid=1&pid=2');
insert into PHOTOS(CAR_PART_ID, URL) values(3, 'https://localhost:8080/carpart/3/#&gid=1&pid=1');
insert into PHOTOS(CAR_PART_ID, URL) values(4, 'https://localhost:8080/carpart/4/#&gid=1&pid=1');
insert into PHOTOS(CAR_PART_ID, URL) values(6, 'https://localhost:8080/carpart/6/#&gid=1&pid=1');

-- аналоги
-- ID = 1
insert into ANALOGS (CAR_PART_ID, VENDOR) values (7, 'No name (China)'); --'GZ-511.1601130-280')
insert into CAR_PART_ANALOGS (CAR_PART_ID, ANALOG_ID) values (1, 1);

-- ID = 2
insert into ANALOGS (CAR_PART_ID, VENDOR) values (8, 'KAMAZ (Russia)'); --'KMZ-740.60-1008025-20')
insert into CAR_PART_ANALOGS (CAR_PART_ID, ANALOG_ID) values (2, 2);

-- ID = 3
insert into ANALOGS (CAR_PART_ID, VENDOR) values (9, 'Cummins (China)'); --'KMZ-3937478-2')
insert into CAR_PART_ANALOGS (CAR_PART_ID, ANALOG_ID) values (2, 3);

-- ID = 4
insert into ANALOGS (CAR_PART_ID, VENDOR) values (10, 'URAL (Russia)'); --'URL-4320-020')
insert into CAR_PART_ANALOGS (CAR_PART_ID, ANALOG_ID) values (3, 4);