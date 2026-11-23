package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.OrganizationalChart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(locations = "classpath:application.properties")
class OrganizationalChartTest {
    private static final String HIERARCHY = "kaavio";
    private static final String TYPE = "organizational-chart-test";
    private static final int ID_OFFSET = 991000;

    @Autowired
    private OrgUnitDao orgUnitDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void insertTestData() {
        Timestamp yesterday = Timestamp.valueOf(LocalDate.now().minusDays(1).atStartOfDay());
        Timestamp today = Timestamp.valueOf(LocalDate.now().atStartOfDay());
        Timestamp tomorrow = Timestamp.valueOf(LocalDate.now().plusDays(1).atStartOfDay());

        insertNode("organizationalChartRoot", "95000001", null, null);
        insertNode("organizationalChartChild", "95000002", null, null);
        insertNode("organizationalChartUnnamed", "95000003", null, null);
        insertNode("organizationalChartExpiredNode", "95000004", null, yesterday);
        insertNode("organizationalChartFutureNode", "95000005", tomorrow, null);
        insertNode("organizationalChartExpiredEdge", "95000006", null, null);
        insertNode("organizationalChartFutureEdge", "95000007", null, null);
        insertNode("organizationalChartOtherHierarchy", "95000008", null, null);
        insertNode("organizationalChartFutureFilter", "95000009", null, null);
        insertNode("organizationalChartExpiredType", "95000010", null, null);
        insertNode("organizationalChartFutureType", "95000011", null, null);
        insertNode("organizationalChartExpiredFilter", "95000012", null, null);
        insertNode("organizationalChartDatedNames", "95000013", null, null);

        insertType("organizationalChartRoot", TYPE, null, null);
        insertType("organizationalChartUnnamed", TYPE, null, null);
        insertType("organizationalChartExpiredNode", TYPE, null, null);
        insertType("organizationalChartFutureNode", TYPE, null, null);
        insertType("organizationalChartExpiredEdge", TYPE, null, null);
        insertType("organizationalChartFutureEdge", TYPE, null, null);
        insertType("organizationalChartOtherHierarchy", TYPE, null, null);
        insertType("organizationalChartFutureFilter", "future-chart-type", null, null);
        insertType("organizationalChartExpiredType", "expired-chart-type", null, yesterday);
        insertType("organizationalChartFutureType", "future-chart-type-current-filter", tomorrow, null);
        insertType("organizationalChartExpiredFilter", "expired-filter-type", null, null);
        insertType("organizationalChartDatedNames", TYPE, null, null);
        insertType("organizationalChartChild", TYPE, today, today);
        insertFilter(TYPE, today, today);
        insertFilter("expired-chart-type", null, null);
        insertFilter("future-chart-type-current-filter", null, null);
        insertFilter("expired-filter-type", null, yesterday);
        insertFilter("future-chart-type", tomorrow, null);

        insertAttribute("organizationalChartRoot", "name_fi", "Kaaviojuuri", null, null);
        insertAttribute("organizationalChartRoot", "name_en", "Chart root", null, null);
        insertAttribute("organizationalChartRoot", "name_sv", "Diagramrot", null, null);
        insertAttribute("organizationalChartChild", "name_fi", "Kaaviolapsi", today, today);
        insertAttribute("organizationalChartDatedNames", "name_fi", "Old name", null, yesterday);
        insertAttribute("organizationalChartDatedNames", "name_en", "Future name", tomorrow, null);
        insertAttribute("organizationalChartDatedNames", "name_sv", "Current name", today, today);

        insertEdge("organizationalChartRoot", null, null, null);
        insertEdge("organizationalChartChild", "organizationalChartRoot", today, today);
        insertEdge("organizationalChartUnnamed", "organizationalChartRoot", null, null);
        insertEdge("organizationalChartExpiredNode", "organizationalChartRoot", null, null);
        insertEdge("organizationalChartFutureNode", "organizationalChartRoot", null, null);
        insertEdge("organizationalChartExpiredEdge", "organizationalChartRoot", null, yesterday);
        insertEdge("organizationalChartFutureEdge", "organizationalChartRoot", tomorrow, null);
        insertEdge("organizationalChartOtherHierarchy", "organizationalChartRoot", null, null, "other-hierarchy");
        insertEdge("organizationalChartFutureFilter", "organizationalChartRoot", null, null);
        insertEdge("organizationalChartExpiredType", "organizationalChartRoot", null, null);
        insertEdge("organizationalChartFutureType", "organizationalChartRoot", null, null);
        insertEdge("organizationalChartExpiredFilter", "organizationalChartRoot", null, null);
        insertEdge("organizationalChartDatedNames", "organizationalChartRoot", null, null);
    }

    @Test
    @Transactional
    void returnsCurrentKaavioConnectionsIncludingTheRootWithoutRequiringHrCode() throws Exception {
        List<OrganizationalChart> chart = orgUnitDao.getOrganizationalChart();
        Map<String, OrganizationalChart> units = chart.stream()
                .collect(Collectors.toMap(OrganizationalChart::getUniqueId, Function.identity()));

        OrganizationalChart root = units.get("95000001");
        assertNotNull(root);
        assertNull(root.getParent());
        assertEquals(TYPE, root.getType());
        assertEquals("Kaaviojuuri", root.getNameFi());
        assertEquals("Chart root", root.getNameEn());
        assertEquals("Diagramrot", root.getNameSv());

        OrganizationalChart child = units.get("95000002");
        assertNotNull(child);
        assertEquals("95000001", child.getParent());
        assertEquals("Kaaviolapsi", child.getNameFi());
        assertNull(child.getNameEn());
        assertNull(child.getNameSv());

        OrganizationalChart unnamed = units.get("95000003");
        assertNotNull(unnamed);
        assertEquals("95000001", unnamed.getParent());
        assertNull(unnamed.getNameFi());
        OrganizationalChart datedNames = units.get("95000013");
        assertNotNull(datedNames);
        assertNull(datedNames.getNameFi());
        assertNull(datedNames.getNameEn());
        assertEquals("Current name", datedNames.getNameSv());
        assertFalse(units.containsKey("95000004"));
        assertFalse(units.containsKey("95000005"));
        assertFalse(units.containsKey("95000006"));
        assertFalse(units.containsKey("95000007"));
        assertFalse(units.containsKey("95000008"));
        assertFalse(units.containsKey("95000009"));
        assertFalse(units.containsKey("95000010"));
        assertFalse(units.containsKey("95000011"));
        assertFalse(units.containsKey("95000012"));

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(get("/api/public/organizationalChart")).andExpect(status().isOk());
    }

    private void insertNode(String id, String uniqueId, Timestamp start, Timestamp end) {
        jdbcTemplate.update("INSERT INTO NODE (ID, UNIQUE_ID, START_DATE, END_DATE) VALUES (?, ?, ?, ?)",
                id, uniqueId, start, end);
    }

    private void insertType(String nodeId, String type, Timestamp start, Timestamp end) {
        insertAttribute(nodeId, "type", type, start, end);
    }

    private void insertAttribute(String nodeId, String key, String value, Timestamp start, Timestamp end) {
        jdbcTemplate.update("INSERT INTO NODE_ATTR (ID, NODE_ID, \"KEY\", \"VALUE\", START_DATE, END_DATE) VALUES (?, ?, ?, ?, ?, ?)",
                ID_OFFSET + attributeId++, nodeId, key, value, start, end);
    }

    private int attributeId = 1;
    private int edgeId = 1;
    private int filterId = 1;

    private void insertFilter(String type, Timestamp start, Timestamp end) {
        jdbcTemplate.update("INSERT INTO HIERARCHY_FILTER (ID, HIERARCHY, \"KEY\", \"VALUE\", START_DATE, END_DATE) VALUES (?, ?, 'type', ?, ?, ?)",
                ID_OFFSET + filterId++, HIERARCHY, type, start, end);
    }

    private void insertEdge(String child, String parent, Timestamp start, Timestamp end) {
        insertEdge(child, parent, start, end, HIERARCHY);
    }

    private void insertEdge(String child, String parent, Timestamp start, Timestamp end, String hierarchy) {
        jdbcTemplate.update("INSERT INTO EDGE (ID, CHILD_NODE_ID, PARENT_NODE_ID, START_DATE, END_DATE, HIERARCHY) VALUES (?, ?, ?, ?, ?, ?)",
                ID_OFFSET + edgeId++, child, parent, start, end, hierarchy);
    }
}