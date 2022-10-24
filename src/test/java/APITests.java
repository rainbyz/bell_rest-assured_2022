import data.listsResponse.Resource;
import data.listsResponse.ResourceData;
import data.listsResponse.UserData;
import data.listsResponse.UserResource;
import data.login.LoginRequest;
import data.login.SuccessfulLoginResponse;
import data.login.UnsuccessfulLoginResponse;
import helpers.DataProvider;
import io.qameta.allure.Feature;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static helpers.Assertions.assertEquals;
import static helpers.Assertions.assertTrue;
import static helpers.Specification.*;
import static helpers.StringUtils.getFileNameWithoutExtension;
import static io.restassured.RestAssured.given;

public class APITests {
    @Feature("Проверка, что имена файлов аватарок пользователей уникальны")
    @Test(dataProvider = "uniqueAvatars", dataProviderClass = DataProvider.class)
    public void test2_1(String subUrl) {
        installSpec(requestSpecReqres(), responseSpec200());

        UserResource userResource = given()
                .when()
                .get(subUrl)
                .then()
                .extract().body().as(UserResource.class);
        List<UserData> userDataList = userResource.getData();
        List<String> usersAvatarNamesList = userDataList.stream()
                .map(user -> getFileNameWithoutExtension(user.getAvatar()))
                .collect(Collectors.toList());
        Set<String> usersAvatarNamesSet = new HashSet<>(usersAvatarNamesList);

        assertEquals(usersAvatarNamesList.size(), usersAvatarNamesSet.size(),
                "Имена файлов аватарок пользователей не уникальны");

        deleteSpec();
    }

    @Feature("Проверка, что пользователь был успешно авторизован")
    @Test(dataProvider = "loginWithPassword", dataProviderClass = DataProvider.class)
    public void test2_2_1(String subUrl, LoginRequest userLoginRequest) {
        installSpec(requestSpecReqres(), responseSpec200());

        SuccessfulLoginResponse successfulLoginResponse = given()
                .body(userLoginRequest)
                .when()
                .post(subUrl)
                .then()
                .extract().body().as(SuccessfulLoginResponse.class);
        String token = successfulLoginResponse.getToken();
        assertTrue(token != null && !token.isEmpty(),
                "Пользователь \"" + userLoginRequest.getEmail() + "\" не был авторизован," +
                        "так как токен не был присвоен");

        deleteSpec();
    }

    @Feature("Проверка, что пользователь не был авторизован, так как не ввел пароль")
    @Test(dataProvider = "loginWithoutPassword", dataProviderClass = DataProvider.class)
    public void test2_2_2(String subUrl, LoginRequest userLoginRequest, String message) {
        installSpec(requestSpecReqres(), responseSpec400());

        UnsuccessfulLoginResponse unsuccessfulLoginResponse = given()
                .body(userLoginRequest)
                .when()
                .post(subUrl)
                .then()
                .extract().body().as(UnsuccessfulLoginResponse.class);
        String error = unsuccessfulLoginResponse.getError();
        assertEquals(error, message,
                "Поле 'message' содержит следующую ошибку: \"" + error + "\", " +
                        "когда ожидалась ошибка \"" + message + "\"");

        deleteSpec();
    }

    @Feature("Проверка, что операция «LIST <RESOURCE>» возвращает данные, отсортированные по годам")
    @Test(dataProvider = "sortedYears", dataProviderClass = DataProvider.class)
    public void test2_3(String subUrl) {
        installSpec(requestSpecReqres(), responseSpec200());

        Resource resource = given()
                .when()
                .get(subUrl)
                .then()
                .extract().body().as(Resource.class);
        List<ResourceData> resourceDataList = resource.getData();
        List<Integer> resourceYearsList = resourceDataList.stream()
                .map(ResourceData::getYear)
                .collect(Collectors.toList());
        List<Integer> resourceYearsSortedList = resourceYearsList.stream()
                .sorted(Integer::compareTo)
                .collect(Collectors.toList());

        /*
        Assert.assertEquals(list1, lists2) тоже самое, что list1.equals(list2).
        Из документации к java.util.List#equals следует: два списка считаются равными,
        если они содержат одни и те же элементы в одном и том же порядке
        */
        assertEquals(resourceYearsList, resourceYearsSortedList,
                "Полученные данные не отсортированы по годам");

        deleteSpec();
    }

    @Feature("Проверка, что количество HTML-тегов в ответе на запрос равно 14")
    @Test(dataProvider = "numberOfTags", dataProviderClass = DataProvider.class)
    public void test2_4(Integer expectedNumber) {
        installSpec(requestSpecGateway(), responseSpec200());

        String body = given()
                .when()
                .get()
                .then()
                .extract().body().xmlPath().prettify();
        int tagsNumber = Jsoup.parse(body, "", Parser.xmlParser()).getAllElements().size();
        assertEquals(tagsNumber, expectedNumber,
                "Количество тегов равно \"" + tagsNumber + "\", когда ожидалось \"" + expectedNumber + "\"");

        deleteSpec();
    }
}