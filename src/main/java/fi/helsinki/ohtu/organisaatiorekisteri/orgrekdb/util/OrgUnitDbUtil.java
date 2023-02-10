package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.DegreeProgrammeDTO;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.SteeringGroup;

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

    public static Map<String, List<SteeringGroup>> extractSteeringProgrammes(List<Map<String, Object>> rows) {
        Map<String, List<SteeringGroup>> groups = new HashMap<>();

        Map<String, List<Map<String,Object>>> byNodeId =
                rows.stream().collect(Collectors.groupingBy(r -> r.get("NODE_ID").toString()));

        byNodeId.forEach((nodeId, nodeRows) -> {
            List<SteeringGroup> nodeSteeringGroups = new ArrayList<>();

            Set<String> iamGroups = nodeRows.stream().map(row ->
                    "" + row.get(KEY_FIELD)
            ).collect(Collectors.toSet());

            iamGroups.forEach(iamGroup -> {
                SteeringGroup steeringGroup = new SteeringGroup();
                steeringGroup.setNodeId(nodeId);
                steeringGroup.setIamGroup(iamGroup);
                nodeSteeringGroups.add(steeringGroup);
            });

            nodeRows.forEach(nodeRow -> {
                String iamGroup = "" + nodeRow.get(KEY_FIELD);
                nodeSteeringGroups.forEach(steeringGroup -> {
                    if (iamGroup.equals(steeringGroup.getIamGroup())) {
                        if (LANG_CODE_FI.equals(nodeRow.get(LANGUAGE_FIELD))) {
                            steeringGroup.setFi("" + nodeRow.get(VALUE_FIELD));
                        }
                        if (LANG_CODE_SV.equals(nodeRow.get(LANGUAGE_FIELD))) {
                            steeringGroup.setSv("" + nodeRow.get(VALUE_FIELD));
                        }
                        if (LANG_CODE_EN.equals(nodeRow.get(LANGUAGE_FIELD))) {
                            steeringGroup.setEn("" + nodeRow.get(VALUE_FIELD));
                        }
                    }
                });

            });

            groups.put(nodeId, nodeSteeringGroups);

        });
        return groups;
    }


}
