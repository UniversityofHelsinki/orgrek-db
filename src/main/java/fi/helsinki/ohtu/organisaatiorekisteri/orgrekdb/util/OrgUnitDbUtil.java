package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.DegreeProgrammeDTO;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.SteeringGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.Constants.*;

public class OrgUnitDbUtil {

    public static List<DegreeProgrammeDTO> extractDegreeProgrammeDTOs(List<Map<String, Object>> rows) {
        List<DegreeProgrammeDTO> degreeProgrammes = new ArrayList<>();

        Map<String, List<Map<String, Object>>> byNodeId =
                rows.stream().collect(Collectors.groupingBy(r -> r.get("NODE_ID").toString()));

        byNodeId.forEach((nodeId, nodeRows) -> {
            DegreeProgrammeDTO degreeProgramme = new DegreeProgrammeDTO();
            degreeProgramme.setNodeId(nodeId);

            nodeRows.forEach(nodeRow -> {
                if (IAM_JOHTORYHMA_FIELD.equals(nodeRow.get(KEY_FIELD))) {
                    degreeProgramme.setIamGroup("" + nodeRow.get(VALUE_FIELD));
                }
                if (NAME_EN_FIELD.equals(nodeRow.get(KEY_FIELD))) {
                    degreeProgramme.setProgrammeNameEn("" + nodeRow.get(VALUE_FIELD));
                }
                if (NAME_FI_FIELD.equals(nodeRow.get(KEY_FIELD))) {
                    degreeProgramme.setProgrammeNameFi("" + nodeRow.get(VALUE_FIELD));
                }
                if (NAME_SV_FIELD.equals(nodeRow.get(KEY_FIELD))) {
                    degreeProgramme.setProgrammeNameSv("" + nodeRow.get(VALUE_FIELD));
                }
                if (TYPE_FIELD.equals(nodeRow.get(KEY_FIELD))) {
                    if(!(nodeRow.get(VALUE_FIELD).equals("koulutusohjelma"))) {
                        degreeProgramme.setType("" + nodeRow.get(VALUE_FIELD));
                    }
                }
                if (OPPIAINE_TUNNUS_FIELD.equals(nodeRow.get(KEY_FIELD))) {
                    degreeProgramme.setProgrammeCode("" + nodeRow.get(VALUE_FIELD));
                }
            });

            degreeProgrammes.add(degreeProgramme);

        });
        return degreeProgrammes;
    }

    public static Map<String, SteeringGroup> extractSteeringProgrammes(List<Map<String, Object>> rows) {
        Map<String, SteeringGroup> groups = new HashMap<>();

        Map<String, List<Map<String,Object>>> byNodeId =
                rows.stream().collect(Collectors.groupingBy(r -> r.get("NODE_ID").toString()));

        byNodeId.forEach((nodeId, nodeRows) -> {
            SteeringGroup steeringGroup = new SteeringGroup();
            steeringGroup.setNodeId(nodeId);

            nodeRows.forEach(nodeRow -> {
                if (LANG_CODE_FI.equals(nodeRow.get(LANGUAGE_FIELD))) {
                    steeringGroup.setFi("" + nodeRow.get(VALUE_FIELD));
                }
                if (LANG_CODE_SV.equals(nodeRow.get(LANGUAGE_FIELD))) {
                    steeringGroup.setSv("" + nodeRow.get(VALUE_FIELD));
                }
                if (LANG_CODE_EN.equals(nodeRow.get(LANGUAGE_FIELD))) {
                    steeringGroup.setEn("" + nodeRow.get(VALUE_FIELD));
                }

                steeringGroup.setIamGroup("" + nodeRow.get(KEY_FIELD));
            });

            groups.put(steeringGroup.getNodeId(), steeringGroup);
        });
        return groups;
    }


}
