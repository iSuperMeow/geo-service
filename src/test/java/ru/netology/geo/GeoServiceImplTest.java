package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class GeoServiceImplTest {

    @ParameterizedTest(name = "#{index} - Test with Argument={0},{1}")
    @MethodSource("provider")
    void geoServiceTest(String ip, List<Location> list) {
        GeoService geoService = Mockito.spy(GeoServiceImpl.class);
        Location location = geoService.byIp(ip);
        Assertions.assertEquals(list.get(0), location);
    }


    static Stream<Arguments> provider() {
        return Stream.of(
                arguments("172.123.12.19", Arrays.asList(new Location("Moscow", Country.RUSSIA, "Lenina", 15))),
                arguments("96.123.12.19", Arrays.asList(new Location("New York", Country.USA, " 10th Avenue", 32))));
    }
}
