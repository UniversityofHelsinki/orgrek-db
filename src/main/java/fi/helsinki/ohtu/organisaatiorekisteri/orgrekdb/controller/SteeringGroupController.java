package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.TextsDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.DegreeProgrammeDTO;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.SteeringGroup;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.TextDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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
    public List<DegreeProgrammeDTO> getSteeringGroups() {
        List<DegreeProgrammeDTO> dtos = orgUnitDao.getDegreeProgrammesAndAttributes();
        Map<String, SteeringGroup> steeringGroups = orgUnitDao.getSteeringGroups();
        dtos.forEach(dto -> {
            String id = dto.getNodeId();
            if ((steeringGroups.get(id) != null)) {
                dto.setSteeringGroupNameEn(steeringGroups.get(id).getEn());
                dto.setSteeringGroupNameFi(steeringGroups.get(id).getFi());
                dto.setSteeringGroupNameSv(steeringGroups.get(id).getSv());
            }
        });
        return dtos;
    }

    @RequestMapping("/degreeTitles")
    public List<TextDTO> getDegreeTitles() {
        return textsDao.getDegreeTitles();
    }

    @RequestMapping("/degreeProgrammes/{id}")
    public List<Node> getDegreeProgrammes(@PathVariable("id") int uniqueId) {
        return orgUnitDao.getDegreeProgrammes(uniqueId);
    }
}
