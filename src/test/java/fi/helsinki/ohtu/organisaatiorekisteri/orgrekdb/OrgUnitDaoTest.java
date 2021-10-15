package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Attribute;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.ConstantsTest;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
public class OrgUnitDaoTest {
    @Autowired
    private OrgUnitDao orgUnitDao;

    @Test
    public void testFindNodeByUniqueId() {
        Node node = orgUnitDao.getNodeByUniqueId(ConstantsTest.ROOT_UNIT_UNIQUE_ID);
        assertEquals(ConstantsTest.ROOT_UNIT_NODE_ID, node.getId());
    }

    @Test
    public void testFindNodeAttributesWithUniqueIdAfterYear2021() {
        Node node = orgUnitDao.getNodeByUniqueId(ConstantsTest.ROOT_UNIT_UNIQUE_ID);
        assertEquals(ConstantsTest.ROOT_UNIT_NODE_ID, node.getId());
        Date dateObj = DateUtil.parseDate("1.1.2021");
        List<Attribute> attributeList =  orgUnitDao.getCurrentAttributeList(node.getId(), dateObj);
        assertEquals(10, attributeList.size());
        assertTrue(attributeList.stream().anyMatch(a -> a.getKey().equals("name_fi") && a.getValue().equals(ConstantsTest.ROOT_UNIT_NAME_FI)));
        assertTrue(attributeList.stream().anyMatch(a -> a.getKey().equals("name_sv") && a.getValue().equals(ConstantsTest.ROOT_UNIT_NAME_SV)));
        assertTrue(attributeList.stream().anyMatch(a -> a.getKey().equals("name_en") && a.getValue().equals(ConstantsTest.ROOT_UNIT_NAME_EN)));
    }

    @Test
    public void testFindNodeAttributesWithUniqueIdAfterYear2018() {
        Node node = orgUnitDao.getNodeByUniqueId(ConstantsTest.ROOT_UNIT_UNIQUE_ID);
        assertEquals(ConstantsTest.ROOT_UNIT_NODE_ID, node.getId());
        Date dateObj = DateUtil.parseDate("1.1.2018");
        List<Attribute> attributeList =  orgUnitDao.getCurrentAttributeList(node.getId(), dateObj);
        assertEquals(18, attributeList.size());
        assertTrue(attributeList.stream().anyMatch(a -> a.getKey().equals("name_fi") && a.getValue().equals(ConstantsTest.ROOT_UNIT_NAME_FI)));
        assertTrue(attributeList.stream().anyMatch(a -> a.getKey().equals("name_sv") && a.getValue().equals(ConstantsTest.ROOT_UNIT_NAME_SV)));
        assertTrue(attributeList.stream().anyMatch(a -> a.getKey().equals("name_en") && a.getValue().equals(ConstantsTest.ROOT_UNIT_NAME_EN)));
    }

    @Test
    public void testFindNodeAttributesWithUniqueIdAfterSeptemberOfYear2018() {
        Node node = orgUnitDao.getNodeByUniqueId(ConstantsTest.ROOT_UNIT_UNIQUE_ID);
        assertEquals(ConstantsTest.ROOT_UNIT_NODE_ID, node.getId());
        Date dateObj = DateUtil.parseDate("1.8.2018");
        List<Attribute> attributeList =  orgUnitDao.getCurrentAttributeList(node.getId(), dateObj);
        assertEquals(14, attributeList.size());
        assertTrue(attributeList.stream().anyMatch(a -> a.getKey().equals("name_fi") && a.getValue().equals(ConstantsTest.ROOT_UNIT_NAME_FI)));
        assertTrue(attributeList.stream().anyMatch(a -> a.getKey().equals("name_sv") && a.getValue().equals(ConstantsTest.ROOT_UNIT_NAME_SV)));
        assertTrue(attributeList.stream().anyMatch(a -> a.getKey().equals("name_en") && a.getValue().equals(ConstantsTest.ROOT_UNIT_NAME_EN)));
    }

}
