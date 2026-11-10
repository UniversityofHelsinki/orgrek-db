package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.config;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

class HelsinkiDateFormat extends SimpleDateFormat {

    private static final ZoneId HELSINKI = ZoneId.of("Europe/Helsinki");

    HelsinkiDateFormat() {
        super("yyyy-MM-dd", Locale.ROOT);
        setTimeZone(TimeZone.getTimeZone(HELSINKI));
        setLenient(false);
    }

    @Override
    public Date parse(String source, ParsePosition position) {
        try {
            TemporalAccessor parsed = DateTimeFormatter.ISO_DATE_TIME.parse(source);
            Instant instant = parsed.isSupported(ChronoField.INSTANT_SECONDS)
                    ? Instant.from(parsed)
                    : LocalDateTime.from(parsed).atZone(HELSINKI).toInstant();
            position.setIndex(source.length());
            return Date.from(instant);
        } catch (DateTimeParseException ex) {
            return super.parse(source, position);
        }
    }
}