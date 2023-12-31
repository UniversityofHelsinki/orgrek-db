package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.AttributeDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.SectionDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Attribute;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.NewNodeDTO;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.SectionAttribute;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.Constants;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AttributeDaoTests {
    @Autowired
    private AttributeDao attributeDao;

    @Autowired
    private SectionDao sectionDao;

    @Test
    @Order(1)
    public void testAddNameAttributes() throws IOException {
        List<Attribute> attributeList = new ArrayList<>();
        Attribute nameFiAttributeToBeAdded = new Attribute( "5283", 555, "name_fi", "Uusi nimi", null, null);
        Attribute nameEnAttributeToBeAdded = new Attribute("5283", 666, "name_en", "New name", null, null);
        Attribute nameSvAttributeToBeAdded = new Attribute("5283", 777,"name_sv", "Nytt namn", null, null);
        attributeList.add(nameFiAttributeToBeAdded);
        attributeList.add(nameEnAttributeToBeAdded);
        attributeList.add(nameSvAttributeToBeAdded);

        List<SectionAttribute> sectionAttributeList = new ArrayList<>();
        SectionAttribute sectionAttribute_fi = new SectionAttribute(1, "names", "name_fi", null, null, 1);
        SectionAttribute sectionAttribute_sv = new SectionAttribute(1, "names", "name_sv", null, null, 2);
        SectionAttribute sectionAttribute_en = new SectionAttribute(1, "names", "name_en", null, null, 3);
        sectionAttributeList.add(sectionAttribute_fi);
        sectionAttributeList.add(sectionAttribute_sv);
        sectionAttributeList.add(sectionAttribute_en);

        assertEquals(0, attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList).size());
        attributeDao.addAttributes(attributeList);
        assertEquals(3, attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList).size());
    }

    @Test
    @Order(2)
    public void testUpdateNameAttributes() throws IOException {
        List<SectionAttribute> sectionAttributeList = new ArrayList<>();
        SectionAttribute sectionAttribute_fi = new SectionAttribute(1, "names", "name_fi", null, null, 1);
        SectionAttribute sectionAttribute_sv = new SectionAttribute(1, "names", "name_sv", null, null, 2);
        SectionAttribute sectionAttribute_en = new SectionAttribute(1, "names", "name_en", null, null,3);
        sectionAttributeList.add(sectionAttribute_fi);
        sectionAttributeList.add(sectionAttribute_sv);
        sectionAttributeList.add(sectionAttribute_en);
        assertEquals(3, attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList).size());

        List<Attribute> attributeList = attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList);
        attributeList.get(0).setValue("Uusi muokattu nimi");
        attributeList.get(1).setValue("New modified name");
        attributeList.get(2).setValue("Nytt ändrat namn");
        attributeDao.updateAttributes(attributeList);
        assertEquals(3, attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList).size());
        assertEquals("Uusi muokattu nimi", attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList).get(0).getValue());
        assertEquals("New modified name", attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList).get(1).getValue());
        assertEquals("Nytt ändrat namn", attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList).get(2).getValue());
    }

    @Test
    @Order(3)
    public void testDeleteNameAttributes() throws IOException {
        List<SectionAttribute> sectionAttributeList = new ArrayList<>();
        SectionAttribute sectionAttribute_fi = new SectionAttribute(1, "names", "name_fi", null, null, 1);
        SectionAttribute sectionAttribute_sv = new SectionAttribute(1, "names", "name_sv", null, null,2);
        SectionAttribute sectionAttribute_en = new SectionAttribute(1, "names", "name_en", null, null, 3);
        sectionAttributeList.add(sectionAttribute_fi);
        sectionAttributeList.add(sectionAttribute_sv);
        sectionAttributeList.add(sectionAttribute_en);
        assertEquals(3, attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList).size());

        List<Attribute> attributeList =  attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList);
        attributeList.remove(0);
        attributeDao.deleteAttributes(attributeList);
        assertEquals(1, attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList).size());
        assertEquals("Uusi muokattu nimi", attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList).get(0).getValue());
    }

    @Test
    @Order(4)
    public void testAddTypeAttributes() throws IOException {
        List<Attribute> attributeList = new ArrayList<>();
        Attribute typeAttributeToBeAdded = new Attribute("5283", 555, "type", "Uusi nimi", null, null);
        attributeList.add(typeAttributeToBeAdded);

        List<SectionAttribute> sectionAttributeList = new ArrayList<>();
        SectionAttribute sectionAttribute = new SectionAttribute(1, "types", "type", null, null, 1);
        sectionAttributeList.add(sectionAttribute);

        assertEquals(0, attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList).size());
        attributeDao.addAttributes(attributeList);
        assertEquals(1, attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList).size());
    }

    @Test
    @Order(5)
    public void testUpdateTypeAttributes() throws IOException {
        List<SectionAttribute> sectionAttributeList = new ArrayList<>();
        SectionAttribute sectionAttribute = new SectionAttribute(1, "types", "type", null, null, 1);
        sectionAttributeList.add(sectionAttribute);
        assertEquals(1, attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList).size());

        List<Attribute> attributeList = attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList);
        attributeList.get(0).setValue("Uusi muokattu nimi");
        attributeDao.updateAttributes(attributeList);
        assertEquals(1, attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList).size());
        assertEquals("Uusi muokattu nimi", attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList).get(0).getValue());
    }

    @Test
    @Order(6)
    public void testDeleteTypeAttributes() throws IOException {
        List<SectionAttribute> sectionAttributeList = new ArrayList<>();
        SectionAttribute sectionAttribute = new SectionAttribute(1, "types", "type", null, null,1);
        sectionAttributeList.add(sectionAttribute);
        assertEquals(1, attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList).size());

        List<Attribute> attributeList =  attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList);
        attributeDao.deleteAttributes(attributeList);
        assertEquals(0, attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList).size());
    }

    @Test
    @Order(7)
    public void testAddCodeAttributes() throws IOException {
        List<Attribute> attributeList = new ArrayList<>();
        Attribute codeAttributeToBeAdded = new Attribute("5283", 888, "tutkimus_tunnus", "TST", null, null);
        attributeList.add(codeAttributeToBeAdded);

        List<SectionAttribute> sectionAttributeList = new ArrayList<>();
        SectionAttribute sectionAttribute = new SectionAttribute(1, "codes", "tutkimus_tunnus", null, null, 1);
        sectionAttributeList.add(sectionAttribute);

        assertEquals(0, attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList).size());
        attributeDao.addAttributes(attributeList);
        assertEquals(1, attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList).size());
    }

    @Test
    @Order(8)
    public void testUpdateCodeAttributes() throws IOException {
        List<SectionAttribute> sectionAttributeList = new ArrayList<>();
        SectionAttribute sectionAttribute = new SectionAttribute(1, "codes", "tutkimus_tunnus", null, null, 1);
        sectionAttributeList.add(sectionAttribute);
        assertEquals(1, attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList).size());
        List<Attribute> attributeList = attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList);
        attributeList.get(0).setValue("TT-TST");
        attributeDao.updateAttributes(attributeList);
        assertEquals(1, attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList).size());
        assertEquals("TT-TST", attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList).get(0).getValue());
    }



    @Test
    @Order(9)
    public void testDeleteCodeAttributes() throws IOException {
        List<SectionAttribute> sectionAttributeList = new ArrayList<>();
        SectionAttribute sectionAttribute = new SectionAttribute(1, "codes", "tutkimus_tunnus", null, null, 1);
        sectionAttributeList.add(sectionAttribute);

        assertEquals(1, attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList).size());
        List<Attribute> attributeList = attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList);
        attributeDao.deleteAttributes(attributeList);
        assertEquals(0, attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList).size());
    }
    @Test
    @Order(10)
    public void testGetCodeAttributes() throws IOException {
        List<SectionAttribute> sectionCodeAttributes = sectionDao.getSectionAttributesBySection(Constants.CODE_SECTION);
        assertEquals(10, sectionCodeAttributes.size());
        sectionCodeAttributes.stream().forEach(sectionAttribute -> {
            assertEquals("codes", sectionAttribute.getSection());
        });
    }

    @Test
    @Order(7)
    public void testAddOtherAttributes() throws IOException {
        List<Attribute> attributeList = new ArrayList<>();
        Attribute codeAttributeToBeAdded = new Attribute("5283", 888, "iam-johtoryhma", "hy-ypa-talous", null, null);
        attributeList.add(codeAttributeToBeAdded);

        List<SectionAttribute> sectionAttributeList = new ArrayList<>();
        SectionAttribute sectionAttribute = new SectionAttribute(1, "other_attributes", "iam-johtoryhma", null, null, 1);
        sectionAttributeList.add(sectionAttribute);

        assertEquals(0, attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList).size());
        attributeDao.addAttributes(attributeList);
        assertEquals(1, attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList).size());
    }

    @Test
    @Order(8)
    public void testUpdateOtherAttributes() throws IOException {
        List<SectionAttribute> sectionAttributeList = new ArrayList<>();
        SectionAttribute sectionAttribute = new SectionAttribute(1, "other_attributes", "iam-johtoryhma", null, null, 1);
        sectionAttributeList.add(sectionAttribute);
        assertEquals(1, attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList).size());
        List<Attribute> attributeList = attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList);
        attributeList.get(0).setValue("hy-mmtdk-mikro");
        attributeDao.updateAttributes(attributeList);
        assertEquals(1, attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList).size());
        assertEquals("hy-mmtdk-mikro", attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList).get(0).getValue());
    }

    @Test
    @Order(9)
    public void testDeleteOtherAttributes() throws IOException {
        List<SectionAttribute> sectionAttributeList = new ArrayList<>();
        SectionAttribute sectionAttribute = new SectionAttribute(1, "other_attributes", "iam-johtoryhma", null, null, 1);
        sectionAttributeList.add(sectionAttribute);

        assertEquals(1, attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList).size());
        List<Attribute> attributeList = attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList);
        attributeDao.deleteAttributes(attributeList);
        assertEquals(0, attributeDao.getSectionAttributesByNodeId("5283", sectionAttributeList).size());
    }

    @Test
    @Order(10)
    public void testGetAttributeAbbreviation() throws IOException {
        String abbreviation = attributeDao.getAttributeAbbreviationByNodeId("a1");
        assertEquals("HY", abbreviation);
    }
}
