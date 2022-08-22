package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class ReadSqlFiles {
    public static String sqlString(String file) throws IOException {
        ClassPathResource resource = new ClassPathResource("sql/" + file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        String sql = reader.lines().map(line -> line + "\n").collect(Collectors.joining());
        return sql;
    }
}
