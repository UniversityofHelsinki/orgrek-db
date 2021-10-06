package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.TextsDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
public class TextsDaoTests {
    @Autowired
    private TextsDao textsDao;

    @Test
    public void testGetAllTexts() {
        List<Map<String, String>> allTexts = textsDao.getAllTexts();
        assertEquals(19, allTexts.size());
    }

    @Test
    public void testGetAllFinnishTexts() {
        Map<String, String> allTexts = textsDao.getTextsByLang("fi");
        assertEquals(10, allTexts.size());
    }

    @Test
    public void testGetAllEnglishTexts() {
        Map<String, String> allTexts = textsDao.getTextsByLang("en");
        assertEquals(6, allTexts.size());
    }

    @Test
    public void testGetAllSwedishTexts() {
        Map<String, String> allTexts = textsDao.getTextsByLang("sv");
        assertEquals(3, allTexts.size());
    }

}
