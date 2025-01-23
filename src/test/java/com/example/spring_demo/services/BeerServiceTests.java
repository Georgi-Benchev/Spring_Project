package com.example.spring_demo.services;

import com.example.spring_demo.exceptions.DuplicateEntityException;
import com.example.spring_demo.exceptions.EntityNotFoundException;
import com.example.spring_demo.exceptions.UnauthorizedAccessException;
import com.example.spring_demo.models.Beer;
import com.example.spring_demo.models.User;
import com.example.spring_demo.repositorys.BeerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.example.spring_demo.Helpers.*;

@ExtendWith(MockitoExtension.class)
public class BeerServiceTests {

    @Mock
    BeerRepository mockRepository;

    @InjectMocks
    BeerServiceImpl service;

    @Test
    public void getById_ShouldReturnBeer_When_ValidArgs() {
        // Arrange
        Mockito.when(mockRepository.getBeerById(1)).thenReturn(new Beer(1, "Mock Beer", 5.5));

        // Act
        Beer result = service.getBeerById(1);

        // Assert
        Assertions.assertEquals(1, result.getId());
        Assertions.assertEquals("Mock Beer", result.getName());
        Assertions.assertEquals(5.5, result.getAbv());
    }

    @Test
    public void create_ShouldThrowExc_When_BeerIdAlreadyExists() {
        // Arrange
        Beer mockBeer = createMockBeer();
        var mockUser = createMockUser();
        Mockito.when(mockRepository.getByName(mockBeer.getName())).thenThrow(EntityNotFoundException.class);
        Mockito.when(mockRepository.getBeerById(Mockito.anyInt())).thenReturn(mockBeer);

        // Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class, () -> service.create(mockBeer, mockUser));
    }

    @Test
    public void create_ShouldThrowExc_When_BeerNameAlreadyExists2() {
        // Arrange
        Beer mockBeer = createMockBeer();
        var mockUser = createMockUser();
        Mockito.when(mockRepository.getBeerById(Mockito.anyInt())).thenThrow(EntityNotFoundException.class);
        Mockito.when(mockRepository.getByName(Mockito.any())).thenReturn(mockBeer);

        // Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class, () -> service.create(mockBeer, mockUser));
    }

    @Test
    public void create_ShouldCallRepo_When_BeerNameAlreadyExists() {
        // Arrange
        var mockBeer = createMockBeer();
        Mockito.when(mockRepository.getByName(mockBeer.getName()))
                .thenThrow(new EntityNotFoundException("Beer", "name", mockBeer.getName()));
        Mockito.when(mockRepository.getBeerById(Mockito.anyInt()))
                .thenThrow(new EntityNotFoundException("Beer", 1));

        // Act
        service.create(mockBeer, createMockUser());

        // Assert
        Mockito.verify(mockRepository, Mockito.times(1))
                .create(Mockito.any(Beer.class));
    }

    @Test
    public void getBeerByName_ShouldReturnBeer_WhenNameIsValid() {
        // Arrange
        var mockBeer = createMockBeer();
        Mockito.when(mockRepository.getByName(mockBeer.getName())).thenReturn(mockBeer);

        // Act
        Beer result = service.getBeerByName(mockBeer.getName());

        // Assert
        Assertions.assertEquals(result, mockBeer);
    }

    @Test
    public void update_ShouldNotThrowExc_When_IdMatches() {
        // Arrange
        Beer mockBeer = createMockBeer();
        Beer mockBeer2 = createMockBeer();
        mockBeer2.setName("Random");

        User mockUser2 = createMockUserAdmin();
        Mockito.when(mockRepository.getByName(mockBeer2.getName())).thenReturn(mockBeer);

        // Assert
        Assertions.assertDoesNotThrow(() -> service.update(mockBeer2, mockUser2));
    }

    @Test
    public void getAll_ShouldReturnBeers_WhenNameIsValid() {
        // Arrange, Act
        List<Beer> beers = service.getAll(createMockFilterOptions());

        // Assert
        Assertions.assertEquals(beers, service.getAll(createMockFilterOptions()));
    }

    @Test
    public void update_Should_Throw_When_UserIsNotCreatorOrAdmin() {
        // Arrange
        Beer mockBeer = createMockBeer();
        User mockUser = createMockUser();
        Mockito.when(mockRepository.getBeerById(mockBeer.getId())).thenReturn(mockBeer);

        // Act, Assert
        Assertions.assertThrows(
                UnauthorizedAccessException.class, () -> service.update(mockBeer, mockUser));
    }

    @Test
    public void update_Should_Throw_When_BeerNameIsTaken() {
        // Arrange
        var mockBeer = createMockBeer();
        var mockBeer2 = createMockBeer();
        mockBeer2.setId(2);
        var mockUser = createMockUserAdmin();
        Mockito.when(mockRepository.getByName(mockBeer2.getName())).thenReturn(mockBeer2);

        // Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class, () -> service.update(mockBeer, mockUser));
    }

    @Test
    public void update_Should_CallRepository_When_BeerWithSameNameExists() {
        // Arrange
        var mockBeer = createMockBeer();
        Mockito.when(mockRepository.getByName(mockBeer.getName()))
                .thenThrow(new EntityNotFoundException("Beer", "name", mockBeer.getName()));
        // Act
        service.update(mockBeer, new User(1, "Gosho", true));

        // Assert
        Mockito.verify(mockRepository, Mockito.times(1)).update(mockBeer, mockBeer.getId());
    }

    @Test
    public void delete_Should_Throw_When_UserIsNotCreatorOrAdmin() {
        // Arrange
        var mockBeer = createMockBeer();
        var mockUser = createMockUser();
        Mockito.when(mockRepository.getBeerById(mockBeer.getId())).thenReturn(mockBeer);

        // Act, Assert
        Assertions.assertThrows(UnauthorizedAccessException.class,
                () -> service.delete(mockBeer.getId(), mockUser));
    }

    @Test
    public void delete_Should_CallRepository_When_BeerWithSameIdExists() {
        // Arrange
        var mockBeer = createMockBeer();
        Mockito.when(mockRepository.getBeerById(mockBeer.getId())).thenReturn(mockBeer);

        // Act
        service.delete(mockBeer.getId(), new User(1, "Gosho", true));

        // Assert
        Mockito.verify(mockRepository, Mockito.times(1)).delete(mockBeer.getId());
    }

}
