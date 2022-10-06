package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static Date parseDate(String displayDateString) {
        Date displayDate = new Date();
        try {
            displayDate = tryDate(displayDateString);
        } catch (ParseException ex) {
            //Default to today but warn.
            //LOGGER.warn("Invalid date string '" + displayDateString + "'. Defaulting to today.");
        }
        return displayDate;
    }

    public static Date tryDate(String date) throws ParseException {
        Locale myLocale = new Locale("fi");
        Date myDate = null;
        try {
            if (StringUtils.hasText(date)) {
                myDate = DateUtils.parseDate(date, new String[]{Constants.ISO8601_DATE_FORMAT_STRING, "dd.MM.yyyy", "dd.MM.yy"});
            }
        } catch (ParseException pe) {
            DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, myLocale);
            myDate = df.parse(date);
        }
        return myDate;
    }

}
