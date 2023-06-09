package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.DegreeProgrammeDTO;

import java.util.*;
import java.util.stream.Collectors;

import static fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.Constants.*;

public class OrgUnitDbUtil {

    public static List<DegreeProgrammeDTO> extractDegreeProgrammeDTOs(List<Map<String, Object>> rows) {
        List<DegreeProgrammeDTO> degreeProgrammes = new ArrayList<>();

        Map<String, List<Map<String, Object>>> byNodeId =
                rows.stream().collect(Collectors.groupingBy(r -> r.get("NODE_ID").toString()));

        byNodeId.forEach((nodeId, nodeRows) -> {
            List<DegreeProgrammeDTO> constructedDegreeProgrammes = new ArrayList<>();

            nodeRows.forEach(nodeRow -> {
                if (IAM_JOHTORYHMA_FIELD.equals(nodeRow.get(KEY_FIELD))) {
                    DegreeProgrammeDTO current = new DegreeProgrammeDTO();
                    current.setNodeId(nodeId);
                    current.setIamGroup("" + nodeRow.get(VALUE_FIELD));
                    constructedDegreeProgrammes.add(current);
                }
            });

            nodeRows.forEach(nodeRow -> {
                if (NAME_EN_FIELD.equals(nodeRow.get(KEY_FIELD))) {
                    constructedDegreeProgrammes.forEach(current -> {
                        current.setProgrammeNameEn("" + nodeRow.get(VALUE_FIELD));
                    });
                }
                if (NAME_FI_FIELD.equals(nodeRow.get(KEY_FIELD))) {
                    constructedDegreeProgrammes.forEach(current -> {
                        current.setProgrammeNameFi("" + nodeRow.get(VALUE_FIELD));
                    });
                }
                if (NAME_SV_FIELD.equals(nodeRow.get(KEY_FIELD))) {
                    constructedDegreeProgrammes.forEach(current -> {
                        current.setProgrammeNameSv("" + nodeRow.get(VALUE_FIELD));
                    });
                }
                if (TYPE_FIELD.equals(nodeRow.get(KEY_FIELD))) {
                    if(!(nodeRow.get(VALUE_FIELD).equals("koulutusohjelma"))) {
                        constructedDegreeProgrammes.forEach(current -> {
                            current.setType("" + nodeRow.get(VALUE_FIELD));
                        });
                    }
                }
                if (OPPIAINE_TUNNUS_FIELD.equals(nodeRow.get(KEY_FIELD))) {
                    constructedDegreeProgrammes.forEach(current -> {
                        current.setProgrammeCode("" + nodeRow.get(VALUE_FIELD));
                    });
                }
            });

            degreeProgrammes.addAll(constructedDegreeProgrammes);

        });
        return degreeProgrammes;
    }


}
