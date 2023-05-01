import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

public class GeoTest {

    @Test
    public void russianTextTest() {
        Map<String, String> headers = new HashMap<>();
        String ip = "172.123.12.19";
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(ip))
                .thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.RUSSIA))
                .thenReturn("Добро пожаловать");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        Assertions.assertEquals("Добро пожаловать", messageSender.send(headers));
    }


    @Test
    public void englishTextTest() {
        Map<String, String> headers = new HashMap<>();
        String ip = "96.123.12.19";
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(ip))
                .thenReturn(new Location("New York", Country.USA, null, 0));
        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.USA))
                .thenReturn("Welcome");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        Assertions.assertEquals("Welcome", messageSender.send(headers));
    }

    @Test
    public void geoServiceNYTest() {
        String ip = "96.123.12.19";
        GeoService geoService = Mockito.spy(GeoServiceImpl.class);
        Location location = geoService.byIp(ip);
        Assertions.assertEquals(new Location("New York", Country.USA, " 10th Avenue", 32), location);
    }

    @Test
    public void geoServiceMoscowTest() {
        String ip = "172.123.12.19";
        GeoService geoService = Mockito.spy(GeoServiceImpl.class);
        Location location = geoService.byIp(ip);
        Assertions.assertEquals(new Location("Moscow", Country.RUSSIA, "Lenina", 15), location);
    }

    @Test
    public void localRussiaTest() {
        LocalizationService localizationService = Mockito.spy(LocalizationServiceImpl.class);
        String locale = localizationService.locale(Country.RUSSIA);
        Assertions.assertEquals(locale, "Добро пожаловать");

    }

    @Test
    public void localEngTest() {
        LocalizationService localizationService = Mockito.spy(LocalizationServiceImpl.class);
        String locale = localizationService.locale(Country.USA);
        Assertions.assertEquals(locale, "Welcome");

    }


}
