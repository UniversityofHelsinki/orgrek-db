package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Attribute;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.NodeWrapper;
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
        Map<String, List<Attribute>> attributeMap =  orgUnitDao.getCurrentAttributeMap(node.getId(), dateObj);
        assertEquals(10, attributeMap.size());
        assertEquals(ConstantsTest.ROOT_UNIT_NAME_FI, attributeMap.get("name_fi").get(0).getValue());
        assertEquals(ConstantsTest.ROOT_UNIT_NAME_EN, attributeMap.get("name_en").get(0).getValue());
        assertEquals(ConstantsTest.ROOT_UNIT_NAME_SV, attributeMap.get("name_sv").get(0).getValue());
    }

    @Test
    public void testFindNodeAttributesWithUniqueIdAfterYear2018() {
        Node node = orgUnitDao.getNodeByUniqueId(ConstantsTest.ROOT_UNIT_UNIQUE_ID);
        assertEquals(ConstantsTest.ROOT_UNIT_NODE_ID, node.getId());
        Date dateObj = DateUtil.parseDate("1.1.2018");
        Map<String, List<Attribute>> attributeMap =  orgUnitDao.getCurrentAttributeMap(node.getId(), dateObj);
        assertEquals(18, attributeMap.size());
        assertEquals(ConstantsTest.ROOT_UNIT_NAME_FI, attributeMap.get("name_fi").get(0).getValue());
        assertEquals(ConstantsTest.ROOT_UNIT_NAME_EN, attributeMap.get("name_en").get(0).getValue());
        assertEquals(ConstantsTest.ROOT_UNIT_NAME_SV, attributeMap.get("name_sv").get(0).getValue());
    }

    @Test
    public void testFindNodeAttributesWithUniqueIdAfterSeptemberOfYear2018() {
        Node node = orgUnitDao.getNodeByUniqueId(ConstantsTest.ROOT_UNIT_UNIQUE_ID);
        assertEquals(ConstantsTest.ROOT_UNIT_NODE_ID, node.getId());
        Date dateObj = DateUtil.parseDate("1.8.2018");
        Map<String, List<Attribute>> attributeMap =  orgUnitDao.getCurrentAttributeMap(node.getId(), dateObj);
        assertEquals(14, attributeMap.size());
        assertEquals(ConstantsTest.ROOT_UNIT_NAME_FI, attributeMap.get("name_fi").get(0).getValue());
        assertEquals(ConstantsTest.ROOT_UNIT_NAME_EN, attributeMap.get("name_en").get(0).getValue());
        assertEquals(ConstantsTest.ROOT_UNIT_NAME_SV, attributeMap.get("name_sv").get(0).getValue());
    }

    @Test
    public void testRootUnitGetCurrentParentsByChildNodeIdShouldNotReturnAnything() {
        List<Node> parents = orgUnitDao.getCurrentParentsByChildNodeId(ConstantsTest.ROOT_UNIT_NODE_ID, "01.01.2021");
        assertEquals(0, parents.size());
    }

    @Test
    public void testGetCurrentParentsByChildNodeIdShouldNotReturnAnythingWithHistoryType() {
        List<Node> parents = orgUnitDao.getCurrentParentsByChildNodeId("11116", "01.01.2021");
        assertEquals(0, parents.size());
    }

    @Test
    public void testGetCurrentParentsByChildNodeIdShouldReturnCorrectParents() {
        List<Node> parents = orgUnitDao.getCurrentParentsByChildNodeId("6777", "01.01.2021");
        assertEquals(1, parents.size());
        assertEquals(ConstantsTest.ROOT_UNIT_NODE_ID, parents.get(0).getId());
    }

    @Test
    public void testGetCurrentParentsByChildNodeIdShouldReturnCorrectParentsByDate() {
        List<Node> parents = orgUnitDao.getCurrentParentsByChildNodeId("3201", "01.01.2021");
        assertEquals(0, parents.size());

        parents = orgUnitDao.getCurrentParentsByChildNodeId("3201", "01.01.2015");
        assertEquals(ConstantsTest.ROOT_UNIT_NODE_ID, parents.get(0).getId());
    }


    @Test
    public void testGetCurrentTypesByChildNodeIdShouldNotReturnAnythingWithHistoryType() {
        List<NodeWrapper> wrapperList = orgUnitDao.getCurrentTypesByChildNodeId("3337", "01.01.2021");
        assertEquals(0, wrapperList.size());
    }

    @Test
    public void testGetCurrentTypesByChildNodeIdShouldNotReturnCorrectTypes() {
        List<NodeWrapper> wrapperList = orgUnitDao.getCurrentTypesByChildNodeId("5935", "01.01.2021");
        assertEquals(1, wrapperList.size());
        assertEquals(ConstantsTest.HIERARCHY_TYPE_OPETUS, wrapperList.get(0).getType());
        assertEquals(ConstantsTest.ROOT_UNIT_NODE_ID, wrapperList.get(0).getParentNodeId());
    }

    @Test
    public void testGetCurrentTypesByChildNodeIdShouldNotReturnCorrectTypesByDate() {
        List<NodeWrapper> wrapperList = orgUnitDao.getCurrentTypesByChildNodeId("3201", "01.01.2021");
        assertEquals(0, wrapperList.size());
        wrapperList = orgUnitDao.getCurrentTypesByChildNodeId("3201", "01.01.2015");
        assertEquals(ConstantsTest.HIERARCHY_TYPE_HENKILOSTO, wrapperList.get(0).getType());
        assertEquals(ConstantsTest.ROOT_UNIT_NODE_ID, wrapperList.get(0).getParentNodeId());
    }

}
