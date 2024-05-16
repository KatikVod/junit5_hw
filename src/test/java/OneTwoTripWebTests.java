import data.Language;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.currentFrameUrl;

public class OneTwoTripWebTests extends TestBase {

    @CsvFileSource(resources = "PageShouldChangeUrlAfterLanguageChange.csv")
    @ParameterizedTest(name = "Для языка {0} должен отображаться url {1}")
    @DisplayName("После выбора языка должен измениться url страницы")
    void pageShouldChangeUrlAfterLanguageChange(String language, String expectedLink) {
        $("[data-locator='currentLocale']").click();
        $$("[data-locator='localeLink']").find(text(language)).click();
        webdriver().shouldHave(currentFrameUrl(expectedLink));
    }

    @EnumSource(Language.class)
    @ParameterizedTest(name = "Для языка {0} должен отображаться верный заголовок")
    @DisplayName("После выбора языка должен измениться заголовок страницы")
    void siteShouldDisplayCorrectTitle(Language language) {
        $("[data-locator='currentLocale']").click();
        $$("[data-locator='localeLink']").find(text(language.name())).click();
        $("[data-locator='Deals'] h2").shouldHave(text(language.title));
    }

    @MethodSource
    @ParameterizedTest(name = "Пункт меню {0} должен содержать набор ссылок {1}")
    @DisplayName("Пункт меню должен содержать необходимые ссылки")
    void dropdownMenuShouldHaveCorrectLinks(String menuItem, List<String> expectedLinks) {
        $("[data-locator='nav']").$(byText(menuItem)).click();
        $$("._56Ky8 [data-locator='nav-link']").shouldHave(texts(expectedLinks));

    }

    static Stream<Arguments> dropdownMenuShouldHaveCorrectLinks() {
        return Stream.of(
                Arguments.of(
                        "Бизнесу",
                        List.of("Командировки", "Отельерам", "Партнёрам")
                ),
                Arguments.of(
                        "Сервисы",
                        List.of("Подарочные сертификаты", "Хочу на море", "Горнолыжные курорты")
                ),
                Arguments.of(
                        "Путешествуйте чаще",
                        List.of("Персональные предложения", "Награды", "Кудаблин", "Блог", "Ребусы", "Спецпроекты")
                ),
                Arguments.of(
                        "Поддержка 24 / 7",
                        List.of("Вопрос-ответ", "Контакты")
                )
        );
    }


}
