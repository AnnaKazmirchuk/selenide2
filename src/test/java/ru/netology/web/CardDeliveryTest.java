package ru.netology.web;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));

    }

    @Test
    void shouldCardDeliver() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Петров-Водкин Василий");
        $("[data-test-id='phone'] input").setValue("+79110000000");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(".notification__title")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldBe(Condition.text("Успешно!"));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate))
                .shouldBe(Condition.visible);
    }

}



