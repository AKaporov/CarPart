package ru.hw.demo.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.hw.demo.config.TestContainersEnvironment;
import ru.hw.demo.domain.CarPart;
import ru.hw.demo.domain.Photo;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"spring.sql.init.data-locations=photo-test.sql"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Репозиторий по работе с Фотографиями")
class PhotoRepositoryTest extends TestContainersEnvironment {

    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private CarPartRepository carPartRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        carPartRepository.deleteAllInBatch();
        photoRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("должен корректно сохранять новую фотографию и привязывать её к запасной части")
    void shouldSavePhotoAndAddToCarPart() {
        List<Photo> beforeAllPhotoList = photoRepository.findAll();

        Photo newPhoto = Photo.builder()
                .photoUrl("https://localhost:8080/carpart/2/#&gid=2&pid=2")
                .build();

        Optional<CarPart> carPartFound = carPartRepository.findById(2L);
        carPartFound.ifPresent(cp -> {
            cp.getPhotoList().add(newPhoto);
            carPartRepository.save(cp);
        });

        List<Photo> actualAllPhotoList = photoRepository.findAll();

        assertAll(() -> {
            assertNotNull(actualAllPhotoList);
            assertEquals(beforeAllPhotoList.size() + 1, actualAllPhotoList.size());
        });
    }

    @Test
    @DisplayName("должен удалить фотографию и отвязать её от запасной части")
    void shouldDeletePhotoAndRemoveFromCarPart() {
        List<Photo> beforeAllPhotoList = photoRepository.findAll();

        Optional<CarPart> carPartFound = carPartRepository.findById(1L);

        carPartFound.ifPresent(cp -> {
            Photo remove = cp.getPhotoList().remove(1);
            carPartRepository.save(cp);
            photoRepository.delete(remove);

        });

        List<Photo> actualAllPhotoList = photoRepository.findAll();

        assertAll(() -> {
            assertNotNull(actualAllPhotoList);
            assertEquals(beforeAllPhotoList.size() - 1, actualAllPhotoList.size());
        });
    }

}