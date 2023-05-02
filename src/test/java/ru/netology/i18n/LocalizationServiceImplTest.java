package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class LocalizationServiceImplTest {

    @ParameterizedTest(name = "#{index} - Test with Argument={0},{1}")
    @MethodSource("provider")
    void localizationTest(String message, List<Country> list) {
        LocalizationService localizationService = Mockito.spy(LocalizationServiceImpl.class);
        String locale = localizationService.locale(list.get(0));
        Assertions.assertEquals(locale, message);    }


    static Stream<Arguments> provider() {
        return Stream.of(
                arguments("Добро пожаловать", Arrays.asList(Country.RUSSIA)),
                arguments("Welcome", Arrays.asList(Country.USA)));
    }
}
