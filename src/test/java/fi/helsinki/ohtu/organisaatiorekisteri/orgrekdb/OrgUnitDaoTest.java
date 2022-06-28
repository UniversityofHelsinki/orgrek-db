package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.*;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.ConstantsTest;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

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
        List<Attribute> attributeList =  orgUnitDao.getAttributeListByDate(node.getId(), dateObj);
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
        List<Attribute> attributeList =  orgUnitDao.getAttributeListByDate(node.getId(), dateObj);
        assertEquals(16, attributeList.size());
        assertTrue(attributeList.stream().anyMatch(a -> a.getKey().equals("name_fi") && a.getValue().equals(ConstantsTest.ROOT_UNIT_NAME_FI)));
        assertTrue(attributeList.stream().anyMatch(a -> a.getKey().equals("name_sv") && a.getValue().equals(ConstantsTest.ROOT_UNIT_NAME_SV)));
        assertTrue(attributeList.stream().anyMatch(a -> a.getKey().equals("name_en") && a.getValue().equals(ConstantsTest.ROOT_UNIT_NAME_EN)));
    }

    @Test
    public void testFindNodeAttributesWithUniqueIdAfterSeptemberOfYear2018() {
        Node node = orgUnitDao.getNodeByUniqueId(ConstantsTest.ROOT_UNIT_UNIQUE_ID);
        assertEquals(ConstantsTest.ROOT_UNIT_NODE_ID, node.getId());
        Date dateObj = DateUtil.parseDate("1.8.2018");
        List<Attribute> attributeList =  orgUnitDao.getAttributeListByDate(node.getId(), dateObj);
        assertEquals(14, attributeList.size());
        assertTrue(attributeList.stream().anyMatch(a -> a.getKey().equals("name_fi") && a.getValue().equals(ConstantsTest.ROOT_UNIT_NAME_FI)));
        assertTrue(attributeList.stream().anyMatch(a -> a.getKey().equals("name_sv") && a.getValue().equals(ConstantsTest.ROOT_UNIT_NAME_SV)));
        assertTrue(attributeList.stream().anyMatch(a -> a.getKey().equals("name_en") && a.getValue().equals(ConstantsTest.ROOT_UNIT_NAME_EN)));
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
    public void testGetCurrentTypesByChildNodeIdShouldReturnCorrectTypes() {
        List<NodeWrapper> wrapperList = orgUnitDao.getCurrentTypesByChildNodeId("5935", "01.01.2021");
        assertEquals(1, wrapperList.size());
        assertEquals(ConstantsTest.HIERARCHY_TYPE_OPETUS, wrapperList.get(0).getType());
        assertEquals(ConstantsTest.ROOT_UNIT_NODE_ID, wrapperList.get(0).getNodeId());
    }

    @Test
    public void testGetCurrentTypesByChildNodeIdShouldReturnCorrectTypesByDate() {
        List<NodeWrapper> wrapperList = orgUnitDao.getCurrentTypesByChildNodeId("3201", "01.01.2021");
        assertEquals(0, wrapperList.size());
        wrapperList = orgUnitDao.getCurrentTypesByChildNodeId("3201", "01.01.2015");
        assertEquals(ConstantsTest.HIERARCHY_TYPE_HENKILOSTO, wrapperList.get(0).getType());
        assertEquals(ConstantsTest.ROOT_UNIT_NODE_ID, wrapperList.get(0).getNodeId());
    }

    @Test
    public void testGetCurrentChildrenByParentNodeIdShouldReturnCorrectChildren() {
        List<Node> children = orgUnitDao.getCurrentChildrenByParentNodeId(ConstantsTest.ROOT_UNIT_NODE_ID, "01.01.2021");
        assertEquals(2, children.size());
        assertEquals("5935", children.get(0).getId());
        assertEquals("6777", children.get(1).getId());
    }

    @Test
    public void testGetCurrentChildrenByParentNodeIdShouldReturnCorrectChildrenByDate() {
        List<Node> children = orgUnitDao.getCurrentChildrenByParentNodeId(ConstantsTest.ROOT_UNIT_NODE_ID, "01.01.2021");
        assertEquals(2, children.size());
        assertEquals("5935", children.get(0).getId());
        assertEquals("6777", children.get(1).getId());

        children = orgUnitDao.getCurrentChildrenByParentNodeId(ConstantsTest.ROOT_UNIT_NODE_ID, "01.01.2015");
        assertEquals(5 , children.size());
        assertEquals("3201", children.get(0).getId());
        assertEquals("3288", children.get(1).getId());
        assertEquals("3459", children.get(2).getId());
        assertEquals("5935", children.get(3).getId());
        assertEquals("6777", children.get(4).getId());
    }

    @Test
    public void testGetCurrentTypesByParentNodeIdShouldNotReturnAnythingWithHistoryType() {
        List<NodeWrapper> wrapperList = orgUnitDao.getCurrentTypesByParentNodeId(ConstantsTest.ROOT_UNIT_NODE_ID, "01.01.2021");
        assertEquals(2, wrapperList.size());
        assertEquals("6777", wrapperList.get(0).getNodeId());
        assertEquals(ConstantsTest.HIERARCHY_TYPE_TOIMINNANOHJAUS, wrapperList.get(0).getType());
        assertEquals("5935", wrapperList.get(1).getNodeId());
        assertEquals(ConstantsTest.HIERARCHY_TYPE_OPETUS, wrapperList.get(1).getType());
    }

    @Test
    public void testGetCurrentTypesByParentNodeIdShouldReturnCorrectTypesByDate() {
        List<NodeWrapper> wrapperList = orgUnitDao.getCurrentTypesByParentNodeId(ConstantsTest.ROOT_UNIT_NODE_ID, "01.01.2015");
        assertEquals(5, wrapperList.size());
        assertEquals(ConstantsTest.HIERARCHY_TYPE_TALOUS, wrapperList.get(0).getType());
        assertEquals("3459", wrapperList.get(0).getNodeId());
        assertEquals(ConstantsTest.HIERARCHY_TYPE_HENKILOSTO, wrapperList.get(1).getType());
        assertEquals("3201", wrapperList.get(1).getNodeId());
        assertEquals(ConstantsTest.HIERARCHY_TYPE_TOIMINNANOHJAUS, wrapperList.get(2).getType());
        assertEquals("6777", wrapperList.get(2).getNodeId());
        assertEquals(ConstantsTest.HIERARCHY_TYPE_OPETUS, wrapperList.get(3).getType());
        assertEquals("5935", wrapperList.get(3).getNodeId());
        assertEquals(ConstantsTest.HIERARCHY_TYPE_TUTKIMUS, wrapperList.get(4).getType());
        assertEquals("3288", wrapperList.get(4).getNodeId());
    }

    @Test
    public void testGetHistoryAndCurrentParentsByChildNodeAndByDate(){
        List<Node> parentNodes = orgUnitDao.getHistoryAndCurrentParentsByChildNodeId("5935", "23.12.2021");
        assertEquals(2, parentNodes.size());
        assertEquals("6770", parentNodes.get(0).getId());
        assertEquals("a1", parentNodes.get(1).getId());
    }

    @Test
    public void testGetFutureAndCurrentParentsByChildNodeAndByDate(){
        List<Node> parentNodes = orgUnitDao.getFutureAndCurrentParentsByChildNodeId("5935", "23.12.2021");
        assertEquals(2, parentNodes.size());
        assertEquals("3130", parentNodes.get(0).getId());
        assertEquals("a1", parentNodes.get(1).getId());
    }

    @Test
    public void testGetHistoryAndCurrentParentTypesByChildNodeAndByDate(){
        List<NodeWrapper> wrapperList = orgUnitDao.getHistoryAndCurrentTypesByChildNodeId("5935", "23.12.2021");
        assertEquals(2, wrapperList.size());
        assertEquals("a1", wrapperList.get(0).getNodeId());
        assertEquals(ConstantsTest.HIERARCHY_TYPE_OPETUS, wrapperList.get(0).getType());
        assertEquals("6770", wrapperList.get(1).getNodeId());
        assertEquals(ConstantsTest.HIERARCHY_TYPE_TOIMINNANOHJAUS, wrapperList.get(1).getType());
    }

    @Test
    public void testGetFutureAndCurrentParentTypesByChildNodeAndByDate(){
        List<NodeWrapper> wrapperList = orgUnitDao.getFutureAndCurrentTypesByChildNodeId("5935", "23.12.2021");
        assertEquals(3, wrapperList.size());
        assertEquals("a1", wrapperList.get(0).getNodeId());
        assertEquals(ConstantsTest.HIERARCHY_TYPE_OPETUS, wrapperList.get(0).getType());
        assertEquals("a1", wrapperList.get(1).getNodeId());
        assertEquals(ConstantsTest.HIERARCHY_TYPE_TALOUS, wrapperList.get(1).getType());
        assertEquals("3130", wrapperList.get(2).getNodeId());
        assertEquals(ConstantsTest.HIERARCHY_TYPE_TOIMINNANOHJAUS, wrapperList.get(2).getType());
    }

    @Test
    public void testGetHistoryAndCurrentChildrenByParentNodeAndByDate(){
        List<Node> childNodes = orgUnitDao.getHistoryAndCurrentChildrenByParentNodeId(ConstantsTest.ROOT_UNIT_NODE_ID, "23.12.2021");
        assertEquals(6, childNodes.size());
        assertEquals("3201", childNodes.get(0).getId());
        assertEquals("3288", childNodes.get(1).getId());
        assertEquals("3368", childNodes.get(2).getId());
        assertEquals("3459", childNodes.get(3).getId());
        assertEquals("5935", childNodes.get(4).getId());
        assertEquals("6777", childNodes.get(5).getId());
    }

    @Test
    public void testGetFutureAndCurrentChildrenByParentNodeAndByDate(){
        List<Node> childNodes = orgUnitDao.getFutureAndCurrentChildrenByParentNodeId(ConstantsTest.ROOT_UNIT_NODE_ID, "23.12.2021");
        assertEquals(3, childNodes.size());
        assertEquals("3130", childNodes.get(0).getId());
        assertEquals("5935", childNodes.get(1).getId());
        assertEquals("6777", childNodes.get(2).getId());
    }

    @Test
    public void testGetHistoryAndCurrentChildTypesByParentNodeAndByDate(){
        List<NodeWrapper> wrapperList = orgUnitDao.getHistoryAndCurrentTypesByParentNodeId(ConstantsTest.ROOT_UNIT_NODE_ID, "23.12.2021");
        assertEquals(6, wrapperList.size());
        assertEquals("3368", wrapperList.get(0).getNodeId());
        assertEquals(ConstantsTest.HIERARCHY_TYPE_TALOUS, wrapperList.get(0).getType());
        assertEquals("3459", wrapperList.get(1).getNodeId());
        assertEquals(ConstantsTest.HIERARCHY_TYPE_TALOUS, wrapperList.get(1).getType());
        assertEquals("3201", wrapperList.get(2).getNodeId());
        assertEquals(ConstantsTest.HIERARCHY_TYPE_HENKILOSTO, wrapperList.get(2).getType());
        assertEquals("6777", wrapperList.get(3).getNodeId());
        assertEquals(ConstantsTest.HIERARCHY_TYPE_TOIMINNANOHJAUS, wrapperList.get(3).getType());
        assertEquals("5935", wrapperList.get(4).getNodeId());
        assertEquals(ConstantsTest.HIERARCHY_TYPE_OPETUS, wrapperList.get(4).getType());
        assertEquals("3288", wrapperList.get(5).getNodeId());
        assertEquals(ConstantsTest.HIERARCHY_TYPE_TUTKIMUS, wrapperList.get(5).getType());
    }

    @Test
    public void testGetFutureAndCurrentChildTypesByParentNodeAndByDate(){
        List<NodeWrapper> wrapperList = orgUnitDao.getFutureAndCurrentTypesByParentNodeId(ConstantsTest.ROOT_UNIT_NODE_ID, "23.12.2021");
        assertEquals(4, wrapperList.size());
        assertEquals("6777", wrapperList.get(0).getNodeId());
        assertEquals(ConstantsTest.HIERARCHY_TYPE_TOIMINNANOHJAUS, wrapperList.get(0).getType());
        assertEquals("3130", wrapperList.get(1).getNodeId());
        assertEquals(ConstantsTest.HIERARCHY_TYPE_TALOUS, wrapperList.get(1).getType());
        assertEquals("5935", wrapperList.get(2).getNodeId());
        assertEquals(ConstantsTest.HIERARCHY_TYPE_OPETUS, wrapperList.get(2).getType());
        assertEquals("5935", wrapperList.get(3).getNodeId());
        assertEquals(ConstantsTest.HIERARCHY_TYPE_TALOUS, wrapperList.get(3).getType());
    }

    @Test
    public void testGetPredecessorsOfNode(){
        List<NodeEdgeHistoryWrapper> wrapperList = orgUnitDao.getPredecessors(ConstantsTest.ROOT_UNIT_NODE_ID, ConstantsTest.HIERARCHY_TYPE_HISTORY);
        assertEquals(2, wrapperList.size());
        assertEquals("3337", wrapperList.get(0).getId());
        assertEquals("3471", wrapperList.get(1).getId());
    }

    @Test
    public void testGetSuccessorsOfNode(){
        List<NodeEdgeHistoryWrapper> wrapperList = orgUnitDao.getSuccessors(ConstantsTest.ROOT_UNIT_NODE_ID, ConstantsTest.HIERARCHY_TYPE_HISTORY);
        assertEquals(1, wrapperList.size());
        assertEquals("9999", wrapperList.get(0).getId());
    }

    @Test
    public void testOneSteeringGroupIsReturned() {
        Map<String, List<SteeringGroup>> steeringGroups = orgUnitDao.getSteeringGroups();
        assertEquals(steeringGroups.size(), 1);
        assertTrue(steeringGroups.get("19063").stream().map(SteeringGroup::getIamGroup).collect(Collectors.toList()).contains("hy-humtdk-spt-jory"));
    }
    @Test
    public void testSteeringGroupHasNamesInThreeLanguages() {
        Map<String, List<SteeringGroup>> steeringGroups = orgUnitDao.getSteeringGroups();
        steeringGroups.get("19063").forEach(steeringGroup -> {
            assertNotNull(steeringGroup.getEn());
            assertNotNull(steeringGroup.getFi());
            assertNotNull(steeringGroup.getSv());
        });
    }

    @Test
    public void testOneDegreeProgrammeIsReturned() {
        List<DegreeProgrammeDTO> programmes = orgUnitDao.getDegreeProgrammesAndAttributes();
        assertEquals(programmes.size(), 1);
    }

    @Test
    public void testDegreeProgrammeHasNamesInThreeLanguages() {
        List<DegreeProgrammeDTO> programmes = orgUnitDao.getDegreeProgrammesAndAttributes();
        assertEquals("Master's Programme in International Business Law", programmes.get(0).getProgrammeNameEn() );
        assertEquals("Magisterprogramme", programmes.get(0).getProgrammeNameSv());
        assertEquals("Kansainvalisen liikejuridiikan maisteriohjelma", programmes.get(0).getProgrammeNameFi());
    }

}
