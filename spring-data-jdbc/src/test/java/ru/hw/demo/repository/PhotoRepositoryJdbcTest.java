package ru.hw.demo.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import ru.hw.demo.domain.CarPart;
import ru.hw.demo.domain.Photo;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = {"spring.datasource.data=photo-test.sql"})
@DisplayName("Репозиторий по работе с Фотографиями")
class PhotoRepositoryJdbcTest {

    @Autowired
    private PhotoRepositoryJdbc photoRepositoryJdbc;
    @Autowired
    private CarPartRepositoryJdbc carPartRepositoryJdbc;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        carPartRepositoryJdbc.deleteAll();
        photoRepositoryJdbc.deleteAll();
    }

    @Test
    @DisplayName("должен корректно сохранять новую фотографию и привязывать её к запасной части")
    void shouldSavePhotoAndAddToCarPart() {
        List<Photo> beforeAllPhotoList = photoRepositoryJdbc.findAll();

        Photo newPhoto = Photo.builder()
                .photoUrl("https://localhost:8080/carpart/2/#&gid=2&pid=2")
                .build();

        Optional<CarPart> carPartFound = carPartRepositoryJdbc.findById(2L);
        carPartFound.ifPresent(cp -> {
            cp.getPhotoList().add(newPhoto);
            carPartRepositoryJdbc.save(cp);
        });

        List<Photo> actualAllPhotoList = photoRepositoryJdbc.findAll();

        assertAll(() -> {
            assertNotNull(actualAllPhotoList);
            assertEquals(beforeAllPhotoList.size() + 1, actualAllPhotoList.size());
        });
    }

    @Test
    @DisplayName("должен удалить фотографию и отвязать её от запасной части")
    void shouldDeletePhotoAndRemoveFromCarPart() {
        List<Photo> beforeAllPhotoList = photoRepositoryJdbc.findAll();

        Optional<CarPart> carPartFound = carPartRepositoryJdbc.findById(1L);
        carPartFound.ifPresent(cp -> {
            cp.getPhotoList().remove(1);
            carPartRepositoryJdbc.save(cp);
        });

        List<Photo> actualAllPhotoList = photoRepositoryJdbc.findAll();

        assertAll(() -> {
            assertNotNull(actualAllPhotoList);
            assertEquals(beforeAllPhotoList.size() - 1, actualAllPhotoList.size());
        });
    }
}