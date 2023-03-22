package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.AttributeDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Attribute;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
public class AttributeDaoTests {
    @Autowired
    private AttributeDao attributeDao;

    @Test
    public void testAddNameAttributes() throws IOException {
        List<Attribute> attributeList = new ArrayList<>();
        Attribute nameFiAttributeToBeAdded = new Attribute( "5283", 555, "name_fi", "Uusi nimi", null, null);
        Attribute nameEnAttributeToBeAdded = new Attribute("5283", 666, "name_en", "New name", null, null);
        Attribute nameSvAttributeToBeAdded = new Attribute("5283", 777,"name_sv", "Nytt namn", null, null);
        attributeList.add(nameFiAttributeToBeAdded);
        attributeList.add(nameEnAttributeToBeAdded);
        attributeList.add(nameSvAttributeToBeAdded);

        assertEquals(0, attributeDao.getNameAttributesByNodeId("5283").size());
        attributeDao.addAttributes(attributeList);
        assertEquals(3, attributeDao.getNameAttributesByNodeId("5283").size());
    }

    @Test
    public void testUpdateNameAttributes() throws IOException {
        assertEquals(3, attributeDao.getNameAttributesByNodeId("5283").size());

        List<Attribute> attributeList = attributeDao.getNameAttributesByNodeId("5283");

        attributeList.get(0).setValue("Uusi muokattu nimi");
        attributeList.get(1).setValue("New modified name");
        attributeList.get(2).setValue("Nytt ändrat namn");

        attributeDao.updateAttributes(attributeList);
        assertEquals(3, attributeDao.getNameAttributesByNodeId("5283").size());
        assertEquals("Uusi muokattu nimi", attributeDao.getNameAttributesByNodeId("5283").get(0).getValue());
        assertEquals("New modified name", attributeDao.getNameAttributesByNodeId("5283").get(1).getValue());
        assertEquals("Nytt ändrat namn", attributeDao.getNameAttributesByNodeId("5283").get(2).getValue());
    }

    @Test
    public void testDeleteNameAttributes() throws IOException {
        assertEquals(3, attributeDao.getNameAttributesByNodeId("5283").size());
        List<Attribute> attributeList = attributeDao.getNameAttributesByNodeId("5283");
        attributeList.remove(0);
        attributeDao.deleteAttributes(attributeList);
        assertEquals(1, attributeDao.getNameAttributesByNodeId("5283").size());
        assertEquals("Uusi muokattu nimi", attributeDao.getNameAttributesByNodeId("5283").get(0).getValue());
     }

    @Test
    public void testAddTypeAttributes() throws IOException {
        List<Attribute> attributeList = new ArrayList<>();
        Attribute typeAttributeToBeAdded = new Attribute( "5283", 555, "type", "Uusi nimi", null, null);
        attributeList.add(typeAttributeToBeAdded);

        assertEquals(0, attributeDao.getTypeAttributesByNodeId("5283").size());
        attributeDao.addAttributes(attributeList);
        assertEquals(1, attributeDao.getTypeAttributesByNodeId("5283").size());
    }

    @Test
    public void testUpdateTypeAttributes() throws IOException {
        assertEquals(1, attributeDao.getTypeAttributesByNodeId("5283").size());
        List<Attribute> attributeList = attributeDao.getTypeAttributesByNodeId("5283");
        attributeList.get(0).setValue("Uusi muokattu nimi");
        attributeDao.updateAttributes(attributeList);
        assertEquals(1, attributeDao.getTypeAttributesByNodeId("5283").size());
        assertEquals("Uusi muokattu nimi", attributeDao.getTypeAttributesByNodeId("5283").get(0).getValue());
    }

    @Test
    public void testDeleteTypeAttributes() throws IOException {
        assertEquals(1, attributeDao.getTypeAttributesByNodeId("5283").size());
        List<Attribute> attributeList = attributeDao.getTypeAttributesByNodeId("5283");
        attributeDao.deleteAttributes(attributeList);
        assertEquals(0, attributeDao.getTypeAttributesByNodeId("5283").size());
    }
}
