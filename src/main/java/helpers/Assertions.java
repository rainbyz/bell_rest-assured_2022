package helpers;

import io.qameta.allure.Step;
import org.testng.Assert;

import java.util.Collection;

public class Assertions {
    @Step("Проверяет, что аргумент равен true")
    public static void assertTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message);
    }

    @Step("Проверяет, что две коллекции равны")
    public static void assertEquals(Collection<?> collection1, Collection<?> collection2, String message) {
        Assert.assertEquals(collection1, collection2, message);
    }

    @Step("Проверяет, что две переменные типа «int» равны")
    public static void assertEquals(int param1, int param2, String message) {
        Assert.assertEquals(param1, param2, message);
    }

    @Step("Проверяет, что две объекта типа «String» равны")
    public static void assertEquals(String param1, String param2, String message) {
        Assert.assertEquals(param1, param2, message);
    }
}
