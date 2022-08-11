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
        public void testCurrentHierarchyFilters() {
            Date dateObj = DateUtil.parseDate("04.08.2022");
            List<HierarchyFilter> hierarchyFilters = hierarchyFilterDao.getCurrentHierarchyFilter(dateObj);
            assertEquals(11, hierarchyFilters.size());
        }

        @Test
        public void testHistoryAndCurrentHierarchyFilter() {
            Date dateObj = DateUtil.parseDate("21.01.2021"); //2021-01-30
            List<HierarchyFilter> hierarchyFilters = hierarchyFilterDao.getHistoryAndCurrentHierarchyFilter(dateObj);
            assertEquals(4, hierarchyFilters.size());
        }

        @Test
        public void testFutureAndCurrentHierarchyFilter() {
            Date dateObj = DateUtil.parseDate("04.08.2021");
            List<HierarchyFilter> hierarchyFilters = hierarchyFilterDao.getFutureAndCurrentHierarchyFilter(dateObj);
            assertEquals(12, hierarchyFilters.size());
        }
    }
