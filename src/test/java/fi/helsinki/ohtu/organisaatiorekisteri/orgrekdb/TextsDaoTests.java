package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.TextsDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.TextDTO;
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
        assertEquals(31, allTexts.size());
    }

    @Test
    public void testGetAllFinnishTexts() {
        Map<String, String> allTexts = textsDao.getTextsByLang("fi");
        assertEquals(14, allTexts.size());
        assertEquals("julkinen", allTexts.get("public"));
    }

    @Test
    public void testGetAllEnglishTexts() {
        Map<String, String> allTexts = textsDao.getTextsByLang("en");
        assertEquals(10, allTexts.size());
        assertEquals("Public", allTexts.get("public"));
    }

    @Test
    public void testGetAllSwedishTexts() {
        Map<String, String> allTexts = textsDao.getTextsByLang("sv");
        assertEquals(7, allTexts.size());
        assertEquals("offentlig", allTexts.get("public"));
    }

    @Test
    public void testDegreeTitles() {
        List<TextDTO> textDTOS = textsDao.getDegreeTitles();
        assertEquals(9, textDTOS.size());
    }
}
