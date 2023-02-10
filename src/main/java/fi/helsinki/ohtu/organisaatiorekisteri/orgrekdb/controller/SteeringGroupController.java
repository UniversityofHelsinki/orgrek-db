package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.TextsDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.DegreeProgrammeDTO;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.SteeringGroup;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.TextDTO;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/*
Public API to get Steering Groups from database
 */
@RestController
@RequestMapping("/api/public")
public class SteeringGroupController {

    @Autowired
    private OrgUnitDao orgUnitDao;

    @Autowired
    private TextsDao textsDao;

    @RequestMapping(method = GET, value="/steeringGroups")
    public List<DegreeProgrammeDTO> getSteeringGroups() throws IOException {
        Set<String> degreeProgrammeNodeIds = orgUnitDao.getDegreeProgrammes(Constants.DEGREE_PROGRAMME_KOONTIYKSIKKO_UNIQUE_ID)
                .stream()
                .map(Node::getId)
                .collect(Collectors.toSet());
        List<DegreeProgrammeDTO> dtos = orgUnitDao.getDegreeProgrammesAndAttributes()
                .stream()
                .filter(dto -> degreeProgrammeNodeIds.contains(dto.getNodeId()))
                .collect(Collectors.toList());
        Map<String, List<SteeringGroup>> steeringGroups = orgUnitDao.getSteeringGroups();
        dtos.forEach(dto -> {
            String id = dto.getNodeId();
            if ((steeringGroups.containsKey(id))) {
                List<SteeringGroup> nodeSteeringGroups = steeringGroups.get(id);
                nodeSteeringGroups.forEach(nsg -> {
                    if (nsg.getIamGroup().equals(dto.getIamGroup())) {
                        dto.setSteeringGroupNameEn(nsg.getEn());
                        dto.setSteeringGroupNameFi(nsg.getFi());
                        dto.setSteeringGroupNameSv(nsg.getSv());
                    }
                });
            }
        });
        return dtos;
    }

    @RequestMapping("/degreeTitles")
    public List<TextDTO> getDegreeTitles() throws IOException {
        return textsDao.getDegreeTitles();
    }

    @RequestMapping("/degreeProgrammes/{id}")
    public List<Node> getDegreeProgrammes(@PathVariable("id") int uniqueId) throws IOException {
        return orgUnitDao.getDegreeProgrammes(uniqueId);
    }
}
