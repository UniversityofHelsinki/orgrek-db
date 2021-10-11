package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.EdgeDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.ConstantsTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
public class EdgeDaoTests {

    @Autowired
    private EdgeDao edgeDao;

    @Test
    public void testHierarchyTypes() {
        List<String> expectedTypes = new ArrayList<>();
        expectedTypes.add(ConstantsTest.HIERARCHY_TYPE_HENKILOSTO);
        expectedTypes.add(ConstantsTest.HIERARCHY_TYPE_HISTORY);
        expectedTypes.add(ConstantsTest.HIERARCHY_TYPE_OPETUS);
        expectedTypes.add(ConstantsTest.HIERARCHY_TYPE_TALOUS);
        expectedTypes.add(ConstantsTest.HIERARCHY_TYPE_TOIMINNANOHJAUS);
        expectedTypes.add(ConstantsTest.HIERARCHY_TYPE_TUTKIMUS);
        List<String> hierarchyTypes = edgeDao.getHierarchyTypes();
        assertEquals(6, hierarchyTypes.size());
        assertEquals(expectedTypes, hierarchyTypes);
    }

}
