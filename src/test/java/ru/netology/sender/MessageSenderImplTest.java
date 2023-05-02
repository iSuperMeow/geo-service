package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class MessageSenderImplTest {

    @ParameterizedTest(name = "#{index} - Test with Argument={0},{1}")
    @MethodSource("provider")
    void senderTextTest(String ip, String message, List<Location> list) {
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(ip))
                .thenReturn(list.get(0));
        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(list.get(0).getCountry()))
                .thenReturn(message);

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        Assertions.assertEquals(message, messageSender.send(headers));
    }


    static Stream<Arguments> provider() {
        return Stream.of(
                arguments("172.123.12.19", "Добро пожаловать", Arrays.asList(new Location("Moscow", Country.RUSSIA, null, 0))),
                arguments("96.123.12.19", "Welcome", Arrays.asList(new Location("New York", Country.USA, " 10th Avenue", 32))));
    }
}
