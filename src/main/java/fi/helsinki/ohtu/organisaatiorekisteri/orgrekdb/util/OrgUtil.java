package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Attribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class OrgUtil {

    public static Map<String, List<Attribute>> getAttributeListAsMap(List<Attribute> attributes) {
        Map<String, List<Attribute>> map = new TreeMap<String, List<Attribute>>();
        for (Attribute attribute : attributes) {
            String key = attribute.getKey();
            if (key != null) {
                if (!map.containsKey(key)) {
                    map.put(key, new ArrayList<Attribute>());
                }
                map.get(key).add(attribute);
            }
        }
        return map;
    }
}
