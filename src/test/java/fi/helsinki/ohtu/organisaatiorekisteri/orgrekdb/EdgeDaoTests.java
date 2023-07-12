package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.EdgeDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.EdgeWrapper;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.NewNodeDTO;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.ConstantsTest;
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
public class EdgeDaoTests {

    @Autowired
    private EdgeDao edgeDao;

    @Test
    public void testHierarchyTypes() throws IOException {
        List<String> expectedTypes = new ArrayList<>();
        expectedTypes.add(ConstantsTest.HIERARCHY_TYPE_HENKILOSTO);
        expectedTypes.add(ConstantsTest.HIERARCHY_TYPE_HISTORY);
        expectedTypes.add(ConstantsTest.HIERARCHY_TYPE_OPETUS);
        expectedTypes.add(ConstantsTest.HIERARCHY_TYPE_TALOUS);
        expectedTypes.add(ConstantsTest.HIERARCHY_TYPE_TOIMINNANOHJAUS);
        expectedTypes.add(ConstantsTest.HIERARCHY_TYPE_TREE_SPECIFIC_1);
        expectedTypes.add(ConstantsTest.HIERARCHY_TYPE_TREE_SPECIFIC_2);
        expectedTypes.add(ConstantsTest.HIERARCHY_TYPE_TUTKIMUS);
        List<String> hierarchyTypes = edgeDao.getHierarchyTypes();
        assertEquals(expectedTypes.size(), hierarchyTypes.size());
        assertEquals(expectedTypes, hierarchyTypes);
    }

    @Test
    public void testAddHierarchies() throws IOException {
        NewNodeDTO newNodeDTO = new NewNodeDTO();
        List<String> hierarchies = new ArrayList<>();
        hierarchies.add("talous");
        hierarchies.add("tutkimus");
        newNodeDTO.setHierarchies(hierarchies);
        List<EdgeWrapper> edgeWrappers = getEdgeWrappers(newNodeDTO);
        int[] hierarchySize = edgeDao.insertEdges(edgeWrappers);
        assertEquals(2, hierarchySize.length);
    }

    private static List<EdgeWrapper> getEdgeWrappers(NewNodeDTO newNodeDTO) {
        List<EdgeWrapper> edgeWrappers = new ArrayList<>();
        if (newNodeDTO.getHierarchies() != null && newNodeDTO.getHierarchies().size() > 0)
            for (String hierarchy : newNodeDTO.getHierarchies()) {
                EdgeWrapper edgeWrapper = new EdgeWrapper();
                edgeWrapper.setParentNodeId(newNodeDTO.getParentNodeId());
                edgeWrapper.setChildNodeId(newNodeDTO.getChildNodeId());
                edgeWrapper.setStartDate(newNodeDTO.getStartDate());
                edgeWrapper.setEndDate(newNodeDTO.getEndDate());
                edgeWrapper.setHierarchy(hierarchy);
                edgeWrappers.add(edgeWrapper);
            }
        return edgeWrappers;
    }

}
