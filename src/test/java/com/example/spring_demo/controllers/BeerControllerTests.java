package com.example.spring_demo.controllers;

import com.example.spring_demo.exceptions.DuplicateEntityException;
import com.example.spring_demo.exceptions.EntityNotFoundException;
import com.example.spring_demo.exceptions.UnauthorizedAccessException;
import com.example.spring_demo.helpers.AuthenticationHelper;
import com.example.spring_demo.helpers.BeerMapper;
import com.example.spring_demo.models.Beer;
import com.example.spring_demo.models.BeerDto;
import com.example.spring_demo.models.FilterOptions;
import com.example.spring_demo.models.User;
import com.example.spring_demo.services.BeerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.example.spring_demo.Helpers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class BeerControllerTests {
    private static final String BEER_ID_NOT_FOUND = "Beer with id %d not found.";
    private static final String BEER_NAME_NOT_FOUND = "Beer with name %s not found.";

    @MockitoBean
    private BeerService beerService;

    @MockitoBean
    private BeerMapper beerMapper;

    @MockitoBean
    private AuthenticationHelper authenticationHelper;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getById_Should_return_StatusOK_When_BeerExists() throws Exception {
        var mockBeer = createMockBeer();

        Mockito.when(beerService.getBeerById(mockBeer.getId()))
                .thenReturn(mockBeer);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/beers/{id}", mockBeer.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(mockBeer.getName()));
    }

    @Test
    public void getByName_Should_return_StatusOK_When_BeerExists() throws Exception {
        var mockBeer = createMockBeer();

        Mockito.when(beerService.getBeerByName(mockBeer.getName()))
                .thenReturn(mockBeer);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/beers/search")
                        .queryParam("name", mockBeer.getName()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(mockBeer.getName()));

    }


    // new tests

    @Test
    public void getById_Should_Return_StatusOK_When_BeerExists() throws Exception {

        Beer mockBeer = createMockBeer();

        Mockito.when(beerService.getBeerById(mockBeer.getId()))
                .thenReturn(mockBeer);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/beers/{id}", mockBeer.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(mockBeer.getName()));
    }

    @Test
    public void getById_Should_Return_StatusNotFound_When_BeerDoesnotExists() throws Exception {
        // Arrange
        Beer mockBeer = createMockBeer();
        Mockito.when(beerService.getBeerById(mockBeer.getId()))
                .thenThrow(EntityNotFoundException.class);

        // Act, Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/beers/{id}", mockBeer.getId()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void getByName_Should_Return_StatusOK_When_BeerExists() throws Exception {

        Beer mockBeer = createMockBeer();

        Mockito.when(beerService.getBeerByName(mockBeer.getName()))
                .thenReturn(mockBeer);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/beers/search")
                        .param("name", mockBeer.getName()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(mockBeer.getName()));
    }

    @Test
    public void getByName_Should_Return_StatusNotFound_When_BeerDoesnotExists() throws Exception {
        // Arrange
        Beer mockBeer = createMockBeer();
        Mockito.when(beerService.getBeerByName(mockBeer.getName()))
                .thenThrow(EntityNotFoundException.class);

        // Act, Assert
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/beers/search")
                        .param("name", mockBeer.getName()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void getById_Should_Return_StatusNotFound_When_BeerDoesntExist() throws Exception {
        // Arrange
        Mockito.when(beerService.getBeerById(2))
                .thenThrow(new ResponseStatusException
                        (HttpStatus.NOT_FOUND, String.format(BEER_ID_NOT_FOUND, 2)));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/beers/{id}", 2))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void getByName_Should_Return_StatusNotFound_When_BeerDoesntExist() throws Exception {


        Mockito.when(beerService.getBeerByName("Mock"))
                .thenThrow(new ResponseStatusException
                        (HttpStatus.NOT_FOUND, String.format(BEER_NAME_NOT_FOUND, "Mock")));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/search", "Mock"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void get_Should_Return_AllBeers_When_NoFiltersAreApplied() throws Exception {
        // Arrange
        List<Beer> mockBeers = List.of(
                new Beer(1, "Beer1", 5.5),
                new Beer(2, "Beer2", 5.2)
        );

        Mockito.when(beerService.getAll(Mockito.any(FilterOptions.class))).thenReturn(mockBeers);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/beers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(mockBeers.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Beer1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Beer2"));
    }

    @Test
    public void createBeer_Should_Return_CreatedBeer_When_ValidBeerDto() throws Exception {
        // Arrange
        BeerDto beerDto = createMockBeerDto();
        Beer mockBeer = createMockBeer();

        Mockito.when(beerMapper.fromDto(Mockito.any(BeerDto.class))).thenReturn(createMockBeerFromBeerDto());
        Mockito.when(authenticationHelper.tryGetUser(Mockito.any())).thenReturn(createMockUserAdmin());
        Mockito.when(beerService.create(Mockito.any(Beer.class), Mockito.any(User.class))).thenReturn(mockBeer);

        // Act, Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/beers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"MockBeerName\", \"abv\": 5.5, \"styleId\": 1}")
                        .header(HttpHeaders.AUTHORIZATION, "valid"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("MockBeerName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.style.name").value("MockStyleName"))  // Corrected path to style name
                .andExpect(MockMvcResultMatchers.jsonPath("$.abv").value(5.5));
    }

    @Test
    public void createBeer_ShouldReturn_NotFound_When_StyleId_IsInvalid() throws Exception {
        // Arrange
        Mockito.when(beerMapper.fromDto(Mockito.any(BeerDto.class))).thenThrow(EntityNotFoundException.class);

        // Act, Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/beers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"MockBeerName\", \"abv\": 5.5, \"styleId\": 1}")
                        .header(HttpHeaders.AUTHORIZATION, "valid"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void createBeer_ShouldReturn_CONFLICT_When_DuplicateName() throws Exception {
        // Arrange
        Mockito.when(beerService.create(Mockito.any(), Mockito.any()))
                .thenThrow(DuplicateEntityException.class);

        // Act, Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/beers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"MockBeerName\", \"abv\": 5.5, \"styleId\": 1}")
                        .header(HttpHeaders.AUTHORIZATION, "valid"))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void createBeer_ShouldReturn_Unauthorized_When_UnauthorizedExcThrown() throws Exception {
        // Arrange
        Mockito.when(authenticationHelper.tryGetUser(Mockito.any()))
                .thenThrow(UnauthorizedAccessException.class);

        // Act, Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/beers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"MockBeerName\", \"abv\": 5.5, \"styleId\": 1}")
                        .header(HttpHeaders.AUTHORIZATION, "valid"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }


    // UPDATE


    @Test
    public void updateBeer_ShouldReturn_UpdatedBeer_When_ValidRequest() throws Exception {
        // Arrange
        Beer mockBeer = createMockBeerFromBeerDtoWithId();

        Mockito.when(beerMapper.fromDto(Mockito.anyInt(), Mockito.any(BeerDto.class))).thenReturn(mockBeer);
        Mockito.when(authenticationHelper.tryGetUser(Mockito.any())).thenReturn(createMockUserAdmin());
        Mockito.when(beerService.update(Mockito.any(Beer.class), Mockito.any(User.class))).thenReturn(mockBeer);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/beers/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"MockBeerName\", \"abv\": 5.5, \"styleId\": 1}")
                        .header(HttpHeaders.AUTHORIZATION, "valid"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("MockBeerName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.style.name").value("MockStyleName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.abv").value(5.5));
    }

    @Test
    public void updateBeer_ShouldReturn_NotFound_When_StyleId_IsInvalid() throws Exception {
        // Arrange
        Mockito.when(beerMapper.fromDto(Mockito.anyInt(), Mockito.any(BeerDto.class)))
                .thenThrow(EntityNotFoundException.class);

        // Act, Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/beers/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"MockBeerName\", \"abv\": 5.5, \"styleId\": 1}")
                        .header(HttpHeaders.AUTHORIZATION, "valid"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void updateBeer_ShouldReturn_CONFLICT_When_DuplicateName() throws Exception {
        // Arrange
        Mockito.when(beerService.update(Mockito.any(), Mockito.any()))
                .thenThrow(DuplicateEntityException.class);

        // Act, Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/beers/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"MockBeerName\", \"abv\": 5.5, \"styleId\": 1}")
                        .header(HttpHeaders.AUTHORIZATION, "valid"))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void updateBeer_ShouldReturn_Unauthorized_When_UnauthorizedExcThrown() throws Exception {
        // Arrange
        Mockito.when(authenticationHelper.tryGetUser(Mockito.any()))
                .thenThrow(UnauthorizedAccessException.class);

        // Act, Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/beers/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"MockBeerName\", \"abv\": 5.5, \"styleId\": 1}")
                        .header(HttpHeaders.AUTHORIZATION, "valid"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    // DELETE

    @Test
    public void deleteBeer_ShouldReturn_StatusOK_When_ValidRequest() throws Exception {
        // Arrange
        Mockito.when(authenticationHelper.tryGetUser(Mockito.any())).thenReturn(createMockUserAdmin());
        Mockito.when(beerService.delete(Mockito.anyInt(), Mockito.any(User.class))).thenReturn(createMockBeer());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/beers/{id}", 1)
                        .header(HttpHeaders.AUTHORIZATION, "valid"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteBeer_ShouldReturn_NotFound_When_BeerId_IsInvalid() throws Exception {
        // Arrange
        Mockito.when(beerService.delete(Mockito.anyInt(), Mockito.any()))
                .thenThrow(EntityNotFoundException.class);

        // Act, Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/beers/{id}", 1)
                        .header(HttpHeaders.AUTHORIZATION, "valid"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    public void deleteBeer_ShouldReturn_Unauthorized_When_UnauthorizedExcThrown() throws Exception {
        // Arrange
        Mockito.when(authenticationHelper.tryGetUser(Mockito.any()))
                .thenThrow(UnauthorizedAccessException.class);

        // Act, Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/beers/{id}", 1)
                        .header(HttpHeaders.AUTHORIZATION, "valid"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

}
