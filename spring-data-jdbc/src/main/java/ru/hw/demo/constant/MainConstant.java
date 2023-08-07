package ru.hw.demo.constant;

/**
 * @author Artem
 * Константы
 */

public class MainConstant {

    private MainConstant() {
    }

    /**
     * Размер страницы. Данные возвращаемые запросом
     */
    public static final int PAGE_REQUEST_SIZE = 10;

//    ================================================================================================================
//                        Константы для SQL-запросов
//    ================================================================================================================

    /**
     * Сохранение Аналога
     */
    public static final String SQL_INSERT_ANALOG = "insert into analogs " +
            " (" +
            "  CAR_PART_ID," +
            "  VENDOR" +
            " ) " +
            "values " +
            " (" +
            "  ?," +
            "  ?" +
            " )";

    /**
     * Поиск Аналогов со всей связанной информацией
     */
    public static final String SQL_SELECT_ANALOG_WITH_ALL_RELATED_INFO =
// analogs
            "select a.id as analog_id," +
                    " a.vendor as analog_vendor, " +
// car_parts
                    "       cp.car_part_id as car_part_id, " +
                    "cp.vendor_code as car_part_vendor_code, " +
                    "cp.sku as car_part_sku, " +
                    "       cp.name as car_part_name, " +
                    "cp.description as car_part_description, " +
                    "cp.price as car_part_price, " +
                    "       cp.manufacturer as car_part_manufacturer, " +
                    "cp.rating as car_part_rating, " +
// brands
                    "      b.id as brand_id, " +
                    "b.name as brand_name, " +
// models
                    "      m.id as model_id, " +
                    "m.name as model_name, " +
                    "m.year_release as model_year_release, " +
// engines
                    "      e.id as engine_id, " +
                    "e.name as engine_name, " +
// countries
                    "      c.id as country_id, " +
                    "c.name as country_name, " +
                    "  from analogs a " +
                    " inner join car_parts cp " +
                    "         on cp.car_part_id = a.car_part_id " +
                    " inner join brands b " +
                    "         on b.id = cp.brand_id " +
                    " inner join models m " +
                    "         on m.id = cp.model_id_fk " +
                    " inner join engines e " +
                    "         on e.id = cp.engine_id " +
                    " inner join countries c " +
                    "         on c.id = cp.country_id" +
                    " where a.id = ?";


    /**
     * Поиск всех существующих Аналогов
     */
    public static final String SQL_SELECT_ANALOG_ALL =
// analogs
            "select a.id as analog_id," +
            " a.vendor as analog_vendor, " +
// car_parts
            "       cp.car_part_id as car_part_id, " +
            "cp.vendor_code as car_part_vendor_code, " +
            "cp.sku as car_part_sku, " +
            "       cp.name as car_part_name, " +
            "cp.description as car_part_description, " +
            "cp.price as car_part_price, " +
            "       cp.manufacturer as car_part_manufacturer, " +
            "cp.rating as car_part_rating, " +
// brands
            "      b.id as brand_id, " +
            "b.name as brand_name, " +
// models
            "      m.id as model_id, " +
            "m.name as model_name, " +
            "m.year_release as model_year_release, " +
// engines
            "      e.id as engine_id, " +
            "e.name as engine_name, " +
// countries
            "      c.id as country_id, " +
            "c.name as country_name, " +
            "  from analogs a " +
            " inner join car_parts cp " +
            "         on cp.car_part_id = a.car_part_id " +
            " inner join brands b " +
            "         on b.id = cp.brand_id " +
            " inner join models m " +
            "         on m.id = cp.model_id_fk " +
            " inner join engines e " +
            "         on e.id = cp.engine_id " +
            " inner join countries c " +
            "         on c.id = cp.country_id" +
            " where a.id > 0";
}