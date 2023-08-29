//package ru.hw.demo.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.*;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import ru.hw.demo.constant.MainConstant;
//import ru.hw.demo.dto.CarPartFullInfoDto;
//import ru.hw.demo.dto.CarPartRecommendedDto;
//import ru.hw.demo.pojo.FilterCarPart;
//import ru.hw.demo.repository.CarPartRepositoryDataJdbc;
//import ru.hw.demo.service.component.CarPartSpecification;
//import ru.hw.demo.service.convert.ConvertCarPartToFullInfoDtoService;
//import ru.hw.demo.service.convert.ConvertCarPartToRecommendedDtoService;
//import ru.hw.demo.service.exception.CarPartNotFoundException;
//
//import java.util.List;
//import java.util.Objects;
//import java.util.function.Predicate;
//
//@Service
//@RequiredArgsConstructor
//public class CarPartServiceImpl implements CarPartService {
//    private static final Pageable SORT_BY_VENDOR_CODE_AND_ID = PageRequest.of(0, MainConstant.PAGE_REQUEST_SIZE, Sort.by("vendorCode", "id"));
//
//    private final CarPartRepositoryDataJdbc carPartRepositoryDataJdbc;
//    private final ConvertCarPartToRecommendedDtoService toRecommendedDto;
//    private final CarPartSpecification carPartSpecification;
//    private final ConvertCarPartToFullInfoDtoService toFullInfoDto;
//
//    /**
//     * @param vendorCode каталожный номер запчасти
//     * @return возвращает найденную запчасть по её {@code vendorCode}
//     */
//    @Override
//    public CarPartRecommendedDto getByVendorCode(String vendorCode) {
//        return carPartRepositoryDataJdbc.findByVendorCode(vendorCode)
//                .map(cp -> toRecommendedDto.convertToRecommendedDtoList(List.of(cp)).get(0))
//                .orElseThrow(() -> new CarPartNotFoundException(vendorCode));
//    }
//
//    /**
//     * @param filter фильтр поиска по марке, модели, году выпуска или двигатель.
//     * @return возвращает список найденных запчастей по {@code filter}
//     */
//    @Override
//    public Page<CarPartRecommendedDto> getByFilter(FilterCarPart filter) {
//        if (Objects.isNull(filter)) {
//            return new PageImpl<>(List.of());
//        }
//
//
//        Predicate where = carPartSpecification.getPredicateByFilter(filter);
//
////        Predicate where = null;
////
////        carPartRepositoryDataJdbc.findAll(where);
////
////        return carPartRepositoryDataJdbc.findAll(where, SORT_BY_VENDOR_CODE_AND_ID)
////                .map(toRecommendedDto::convertToRecommendedDto);
//
//        return null;
//
////        Page<CarPart> cpFoundPages = carPartRepositoryDataJdbc.findAll(where, SORT_BY_VENDOR_CODE_AND_ID);
////
////        List<CarPartRecommendedDto> cpFoundList = new LinkedList<>();
////        while (true) {
////            cpFoundList.addAll(toRecommendedDto.convertToRecommendedDto(cpFoundPages.getContent()));
////
////            if (cpFoundPages.hasNext()) {
////                cpFoundPages = carPartRepositoryDataJdbc.findAll(where, cpFoundPages.nextPageable());
////            } else {
////                break;
////            }
////        }
////        return cpFoundList;
//    }
//
//    /**
//     * @param vendorCode каталожный номер запчасти
//     * @return возвращает расширенную информацию по {@code vendorCode}
//     */
//    @Transactional(readOnly = true)
//    @Override
//    public CarPartFullInfoDto getExtendedInfoByVendorCode(String vendorCode) {
//        return carPartRepositoryDataJdbc.findByVendorCode(vendorCode)
//                .map(toFullInfoDto::convertToFullInfoDto)
//                .orElseThrow(() -> new CarPartNotFoundException(vendorCode));
//    }
//}
