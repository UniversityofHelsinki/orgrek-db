package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.AttributeDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Attribute;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
@DisplayName("Attribute should not be added if there is an attribute with the same name that is valid at the same time")
public class NodeAttributeValidationTest {

    @Autowired
    AttributeDao attributeDao;

    Attribute attr0, attr1, attr2, attr3, attr4, attr5, attr6, attr7, attr8, attr9, attr10, attr11, attr12, attr13, attr14, attr15, attr16, attr17, attr18, attr19;
    String nodeId = "5283";

    @BeforeAll
    private void createAttributes() {
        attr0 = new Attribute("5283", null, "koira", "koira", parseDate("06.01.2018"), parseDate("07.01.2018"));

        attr1 = new Attribute("5283", null, "meduusa", "meduusa", parseDate("02.01.2022"), parseDate("12.01.2022"));
        attr2 = new Attribute("5283", null, "meduusa", "meduusa", parseDate("15.01.2022"), parseDate("07.02.2022"));
        attr3 = new Attribute("5283", null, "meduusa", "meduusa", parseDate("15.12.2021"), parseDate("07.01.2022"));
        attr4 = new Attribute("5283", null, "kissa", "nimi", parseDate("15.12.2021"), parseDate("17.12.2021"));
        attr5 = new Attribute("5283", null, "kissa", "nimi", parseDate("15.02.2022"), parseDate("17.02.2022"));
        attr6 = new Attribute("5283", null, "meduusa", "meduusa", null, parseDate("15.01.2022"));
        attr7 = new Attribute("5283", null, "meduusa", "meduusa", parseDate("17.01.2022"), null);

        attr8 = new Attribute("5283", null, "meduusa", "meduusa", parseDate("02.01.2022"), parseDate("12.01.2022"));
        attr9 = new Attribute("5283", null, "meduusa", "meduusa", parseDate("15.01.2022"), parseDate("07.02.2022"));
        attr10 = new Attribute("5283", null, "meduusa", "meduusa", parseDate("15.12.2021"), parseDate("07.01.2022"));
        attr11 = new Attribute("5283", null, "kani", "nimi", parseDate("15.02.2022"), parseDate("17.02.2022"));
        attr12 = new Attribute("5283", null, "meduusa", "meduusa", null, parseDate("15.01.2022"));
        attr13 = new Attribute("5283", null, "meduusa", "meduusa", parseDate("17.01.2022"), null);

        attr14 = new Attribute("5283", null, "meduusa", "meduusa", parseDate("02.01.2022"), parseDate("12.01.2022"));
        attr15 = new Attribute("5283", null, "meduusa", "meduusa", parseDate("15.01.2022"), parseDate("07.02.2022"));
        attr16 = new Attribute("5283", null, "meduusa", "meduusa", parseDate("15.12.2021"), parseDate("07.01.2022"));
        attr17 = new Attribute("5283", null, "meduusa", "nimi", parseDate("15.12.2021"), parseDate("17.12.2021"));
        attr18 = new Attribute("5283", null, "meduusa", "meduusa", null, parseDate("15.01.2022"));
        attr19 = new Attribute("5283", null, "meduusa", "meduusa", parseDate("17.01.2022"), null);
     }

    private static LocalDate parseDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Nested
    @DisplayName("When existing attribute has no start date or end date")
    class NoDates {

        @Test
        public void testAttributeWithSameNameCannotBeAdded() throws IOException {
            Attribute ret = attributeDao.getExistingAttribute(attr0);
            assertThat(ret, is(notNullValue()));
        }
    }

    @Nested
    @DisplayName("When existing attribute has start date and end date")
    class BothDates {
        @Test
        public void testAttributeInsideExistingAttributesTimeSlotCanNotBeAdded() throws IOException {
            Attribute ret = attributeDao.getExistingAttribute(attr1);
            assertThat(ret, is(notNullValue()));
        }

        @Test
        public void testAttributeBeginningInsideExistingAttributesTimeSlotCanNotBeAdded() throws IOException {
            Attribute ret = attributeDao.getExistingAttribute(attr2);
            assertThat(ret, is(notNullValue()));
        }

        @Test
        public void testAttributeEndingInsideExistingAttributeTimeSlotCanNotBeAdded() throws IOException {
            Attribute ret = attributeDao.getExistingAttribute(attr3);
            assertThat(ret, is(notNullValue()));
        }

        @Test
        public void testAttributeBeforeExistingAttributeCanBeAdded() throws IOException {
            Attribute ret = attributeDao.getExistingAttribute(attr4);
            assertThat(ret, is(nullValue()));
        }

        @Test
        public void testAttributeAfterExistingAttributeCanBeAdded() throws IOException {
            Attribute ret = attributeDao.getExistingAttribute(attr5);
            assertThat(ret, is(nullValue()));
        }

        @Test
        public void testAttributeWithNoStartDateEndingInsideExistingAttributeTimeSlotCanNotBeAdded() throws IOException {
            Attribute ret = attributeDao.getExistingAttribute(attr6);
            assertThat(ret, is(notNullValue()));
        }
        @Test
        public void testAttributeWithNoEndDateBeginningInsideExistingAttributeTimeSlotCanNotBeAdded() throws IOException {
            Attribute ret = attributeDao.getExistingAttribute(attr7);
            assertThat(ret, is(notNullValue()));
        }
    }

    @Nested
    @DisplayName("When existing attribute has end date but not start date")
    class EndDate {
        @Test
        public void testAttributeInsideExistingAttributesTimeSlotCanNotBeAdded() throws IOException {
            Attribute ret = attributeDao.getExistingAttribute(attr8);
            assertThat(ret, is(notNullValue()));
        }
        @Test
        public void testAttributeBeginningInsideExistingAttributesTimeSlotCanNotBeAdded() throws IOException {
            Attribute ret = attributeDao.getExistingAttribute(attr9);
            assertThat(ret, is(notNullValue()));
        }

        @Test
        public void testAttributeEndingInsideExistingAttributeTimeSlotCanNotBeAdded() throws IOException {
            Attribute ret = attributeDao.getExistingAttribute(attr10);
            assertThat(ret, is(notNullValue()));
        }


        @Test
        public void testAttributeAfterExistingAttributeCanBeAdded() throws IOException {
            Attribute ret = attributeDao.getExistingAttribute(attr11);
            assertThat(ret, is(nullValue()));
        }

        @Test
        public void testAttributeWithNoStartDateEndingInsideExistingAttributeTimeSlotCanNotBeAdded() throws IOException {
            Attribute ret = attributeDao.getExistingAttribute(attr12);
            assertThat(ret, is(notNullValue()));
        }
        @Test
        public void testAttributeWithNoEndDateBeginningInsideExistingAttributeTimeSlotCanNotBeAdded() throws IOException {
            Attribute ret = attributeDao.getExistingAttribute(attr13);
            assertThat(ret, is(notNullValue()));
        }

    }

    @Nested
    @DisplayName("When existing attribute has  start date but not end date")
    class StartDate {
        @Test
        public void testAttributeInsideExistingAttributesTimeSlotCanNotBeAdded() throws IOException {
            Attribute ret = attributeDao.getExistingAttribute(attr14);
            assertThat(ret, is(notNullValue()));
        }

        @Test
        public void testAttributeBeginningInsideExistingAttributesTimeSlotCanNotBeAdded() throws IOException {
            Attribute ret = attributeDao.getExistingAttribute(attr15);
            assertThat(ret, is(notNullValue()));
        }

        @Test
        public void testAttributeEndingInsideExistingAttributeTimeSlotCanNotBeAdded() throws IOException {
            Attribute ret = attributeDao.getExistingAttribute(attr16);
            assertThat(ret, is(notNullValue()));
        }

        @Test
        public void testAttributeBeforeExistingAttributeCanBeAdded() throws IOException {
            Attribute ret = attributeDao.getExistingAttribute(attr17);
            assertThat(ret, is(nullValue()));
        }

        @Test
        public void testAttributeWithNoStartDateEndingInsideExistingAttributeTimeSlotCanNotBeAdded() throws IOException {
            Attribute ret = attributeDao.getExistingAttribute(attr18);
            assertThat(ret, is(notNullValue()));
        }
        @Test
        public void testAttributeWithNoEndDateBeginningInsideExistingAttributeTimeSlotCanNotBeAdded() throws IOException {
            Attribute ret = attributeDao.getExistingAttribute(attr19);
            assertThat(ret, is(notNullValue()));
        }
    }


}
