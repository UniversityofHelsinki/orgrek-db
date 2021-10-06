package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Attribute;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.Constants;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
public class OrgUnitDaoTest {
    @Autowired
    private OrgUnitDao orgUnitDao;

    @Test
    public void testFindNodeByUniqueId() {
        Node node = orgUnitDao.getNodeByUniqueId(Constants.ROOT_UNIT_UNIQUE_ID);
        assertEquals(Constants.ROOT_UNIT_NODE_ID, node.getId());
    }

    @Test
    public void testFindNodeAttributesWithUniqueIdAfterYear2021() {
        Node node = orgUnitDao.getNodeByUniqueId(Constants.ROOT_UNIT_UNIQUE_ID);
        assertEquals(Constants.ROOT_UNIT_NODE_ID, node.getId());
        Date dateObj = DateUtil.parseDate("1.1.2021");
        Map<String, List<Attribute>> attributeMap =  orgUnitDao.getCurrentAttributeMap(node.getId(), dateObj);
        assertEquals(10, attributeMap.size());
        assertEquals(Constants.ROOT_UNIT_NAME_FI, attributeMap.get("name_fi").get(0).getValue());
        assertEquals(Constants.ROOT_UNIT_NAME_EN, attributeMap.get("name_en").get(0).getValue());
        assertEquals(Constants.ROOT_UNIT_NAME_SV, attributeMap.get("name_sv").get(0).getValue());
    }

    @Test
    public void testFindNodeAttributesWithUniqueIdAfterYear2018() {
        Node node = orgUnitDao.getNodeByUniqueId(Constants.ROOT_UNIT_UNIQUE_ID);
        assertEquals(Constants.ROOT_UNIT_NODE_ID, node.getId());
        Date dateObj = DateUtil.parseDate("1.1.2018");
        Map<String, List<Attribute>> attributeMap =  orgUnitDao.getCurrentAttributeMap(node.getId(), dateObj);
        assertEquals(18, attributeMap.size());
        assertEquals(Constants.ROOT_UNIT_NAME_FI, attributeMap.get("name_fi").get(0).getValue());
        assertEquals(Constants.ROOT_UNIT_NAME_EN, attributeMap.get("name_en").get(0).getValue());
        assertEquals(Constants.ROOT_UNIT_NAME_SV, attributeMap.get("name_sv").get(0).getValue());
    }

    @Test
    public void testFindNodeAttributesWithUniqueIdAfterSeptemberOfYear2018() {
        Node node = orgUnitDao.getNodeByUniqueId(Constants.ROOT_UNIT_UNIQUE_ID);
        assertEquals(Constants.ROOT_UNIT_NODE_ID, node.getId());
        Date dateObj = DateUtil.parseDate("1.8.2018");
        Map<String, List<Attribute>> attributeMap =  orgUnitDao.getCurrentAttributeMap(node.getId(), dateObj);
        assertEquals(14, attributeMap.size());
        assertEquals(Constants.ROOT_UNIT_NAME_FI, attributeMap.get("name_fi").get(0).getValue());
        assertEquals(Constants.ROOT_UNIT_NAME_EN, attributeMap.get("name_en").get(0).getValue());
        assertEquals(Constants.ROOT_UNIT_NAME_SV, attributeMap.get("name_sv").get(0).getValue());
    }

}
