package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.HierarchyFilterDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.HierarchyFilter;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.ConstantsTest;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
public class HierarchyFilterTests {

        @Autowired
        private HierarchyFilterDao hierarchyFilterDao;

        @Test
        public void testHierarchyFilters() {
            List<String> expectedTypes = new ArrayList<>();
            expectedTypes.add(ConstantsTest.HIERARCHY_TYPE_HENKILOSTO);
            //expectedTypes.add(ConstantsTest.HIERARCHY_TYPE_HISTORY);
            expectedTypes.add(ConstantsTest.HIERARCHY_TYPE_OPETUS);
            expectedTypes.add(ConstantsTest.HIERARCHY_TYPE_TALOUS);
            //expectedTypes.add(ConstantsTest.HIERARCHY_TYPE_TOIMINNANOHJAUS);
            //expectedTypes.add(ConstantsTest.HIERARCHY_TYPE_TUTKIMUS);
            List<HierarchyFilter> hierarchyFilters = hierarchyFilterDao.getHierarchyFilters();
            assertEquals(12, hierarchyFilters.size());
        }

        @Test
        public void testHierarchyFilter() {
            String hierarchy = "henkilosto,talous";
            String[] hierarchyArr = hierarchy.split(",");
            List<String> hierarchies = Arrays.asList(hierarchyArr);

            Date dateObj = DateUtil.parseDate("04.08.2022");
            List<HierarchyFilter> henkilosto_hierarchyFilters = hierarchyFilterDao.getCurrentHierarchyFilter(hierarchies, dateObj);
            assertEquals(8, henkilosto_hierarchyFilters.size());
        }

    }
