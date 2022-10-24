package helpers;

import data.login.LoginRequest;

public class DataProvider {
    @org.testng.annotations.DataProvider(name = "uniqueAvatars")
    public static Object[][] uniqueAvatars() {
        return new Object[][]{
                {
                        "/api/users?page=2"
                }
        };
    }

    @org.testng.annotations.DataProvider(name = "loginWithPassword")
    public static Object[][] loginWithPassword() {
        return new Object[][]{
                {
                        "/api/login",
                        new LoginRequest("eve.holt@reqres.in", "cityslicka")
                }
        };
    }

    @org.testng.annotations.DataProvider(name = "loginWithoutPassword")
    public static Object[][] loginWithoutPassword() {
        return new Object[][]{
                {
                        "/api/login",
                        new LoginRequest("peter@klaven"),
                        "Missing password"
                }
        };
    }

    @org.testng.annotations.DataProvider(name = "sortedYears")
    public static Object[][] sortedYears() {
        return new Object[][]{
                {
                        "/api/unknown"
                }
        };
    }

    @org.testng.annotations.DataProvider(name = "numberOfTags")
    public static Object[][] numberOfTags() {
        return new Object[][]{
                {
                        14
                }
        };
    }
}