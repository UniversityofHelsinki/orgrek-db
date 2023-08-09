package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.HierarchyPublicityDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.HierarchyPublicity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
public class HierarchyPublicityTests {
    @Autowired
    private HierarchyPublicityDao hierarchyPublicityDao;

    @Test
    public void testHierarchiesWithPublicityInformationShouldReturnAllHierarchies() throws IOException {
        List<HierarchyPublicity> hierarchyPublicityList = hierarchyPublicityDao.getHierarchiesWithPublicityInformation();
        assertEquals(8, hierarchyPublicityList.size());
    }
}
