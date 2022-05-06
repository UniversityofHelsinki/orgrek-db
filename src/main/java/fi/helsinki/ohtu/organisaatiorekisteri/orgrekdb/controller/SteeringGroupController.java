package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.DegreeProgrammeDTO;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.SteeringGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/steering")
public class SteeringGroupController {

    @Autowired
    private OrgUnitDao orgUnitDao;

    @RequestMapping(method = GET, value="/")
    public List<DegreeProgrammeDTO> getSteeringGroups() {
        List<DegreeProgrammeDTO> dtos = orgUnitDao.getDegreeProgrammesAndAttributes();
        Map<String, SteeringGroup> steeringGroups = orgUnitDao.getSteeringGroupNames();
        for(DegreeProgrammeDTO dto : dtos) {
            String id = dto.getNodeId();
            if((steeringGroups.get(id)!=null)){
                dto.setSteeringGroup(steeringGroups.get(id));
            }
        }
        return dtos;
    }


}
