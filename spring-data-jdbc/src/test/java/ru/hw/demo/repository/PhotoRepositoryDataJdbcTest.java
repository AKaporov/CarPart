package ru.hw.demo.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.hw.demo.domain.Photo;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJdbcTest
@TestPropertySource(properties = {"spring.sql.init.data-locations=classpath:photo-test.sql"})
@DisplayName("Репозиторий на основе Spring Data JDBC по работе с объектом Фотография")
class PhotoRepositoryDataJdbcTest {

    @Autowired
    private PhotoRepositoryDataJdbc photoRepositoryDataJdbc;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        photoRepositoryDataJdbc.deleteAll();
    }

    @Test
    @DisplayName("должен корректно сохранять новое фото")
    void shouldSaveNwPhoto() {
        Photo photo = Photo.builder()
                .photoUrl("https://localhost:8080/carpart/2")
                .build();
        Photo actualPhoto = photoRepositoryDataJdbc.save(photo);

        assertAll(() -> {
            assertNotNull(actualPhoto);
            assertNotNull(actualPhoto.getId());
            assertEquals(photo.getPhotoUrl(), actualPhoto.getPhotoUrl());
        });
    }

    @Test
    @DisplayName("не должен находить фотографию по переданному идентификатору")
    void shouldNotFoundPhotoByNotValidId() {
        Optional<Photo> actualCountry = photoRepositoryDataJdbc.findById(101L);
        assertTrue(actualCountry.isEmpty());
    }

    @Test
    @DisplayName("должен находить фотографию по идентификатору")
    void shouldFindById() {
        Optional<Photo> actualPhoto = photoRepositoryDataJdbc.findById(4L);

        Photo expectedPhoto = Photo.builder()
                .id(4L)
                .photoUrl("https://localhost:8080/carpart/2/#&gid=2&pid=1")
                .build();

        Assertions.assertThat(actualPhoto).isPresent()
                .get()
                .isEqualTo(expectedPhoto);
    }
}