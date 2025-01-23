package com.example.spring_demo;

import com.example.spring_demo.models.*;

public class Helpers {

    public static User createMockUser() {
        var mockUser = new User();
        mockUser.setId(1);
        mockUser.setEmail("mock@user.com");
        mockUser.setUsername("MockUsername");
        mockUser.setPassword("MockPassword");
        mockUser.setFirstName("MockFirstName");
        mockUser.setLastName("MockLastName");
        mockUser.setAdmin(false);
        return mockUser;
    }

    public static User createMockUserAdmin() {
        var mockUser = new User();
        mockUser.setId(2);
        mockUser.setEmail("mock@userAdmin.com");
        mockUser.setUsername("MockUserAdminName");
        mockUser.setPassword("MockAdminPassword");
        mockUser.setFirstName("MockFirstNameAdmin");
        mockUser.setLastName("MockLastNameAdmin");
        mockUser.setAdmin(true);
        return mockUser;
    }


    public static Beer createMockBeer() {
        var mockBeer = new Beer();
        mockBeer.setId(1);
        mockBeer.setName("MockBeerName");
        mockBeer.setAbv(5.5);
        mockBeer.setCreatedBy(createMockUserAdmin());
        mockBeer.setStyle(createMockStyle());
        return mockBeer;
    }

    public static Beer createMockBeerFromBeerDto() {
        Beer beer = new Beer();
        beer.setName("MockBeerName");
        beer.setAbv(5.5);
        beer.setStyle(createMockStyle());
        return beer;
    }

    public static Beer createMockBeerFromBeerDtoWithId() {
        Beer beer = new Beer();
        beer.setId(1);
        beer.setName("MockBeerName");
        beer.setAbv(5.5);
        beer.setStyle(createMockStyle());
        return beer;
    }

    public static BeerDto createMockBeerDto() {
        return new BeerDto("MockBeerName", 5.5, 1);
    }


    public static Style createMockStyle() {
        var mockStyle = new Style();
        mockStyle.setId(1);
        mockStyle.setName("MockStyleName");
        return mockStyle;
    }

    public static FilterOptions createMockFilterOptions() {
        return new FilterOptions(
                "MockName",
                4.0,
                6.0,
                1,
                "name",
                "acs");
    }
}
