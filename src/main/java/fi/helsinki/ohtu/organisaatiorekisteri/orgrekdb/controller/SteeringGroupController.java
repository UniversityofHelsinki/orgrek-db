package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.TextsDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.SteeringGroup;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.TextDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

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
    public List<SteeringGroup> getSteeringGroups() throws IOException {
        return orgUnitDao.getSteeringGroups();
    }

    @RequestMapping("/degreeTitles")
    public List<TextDTO> getDegreeTitles() throws IOException {
        return textsDao.getDegreeTitles();
    }

    @RequestMapping("/degreeProgrammes/{id}")
    public List<Node> getDegreeProgrammes(@PathVariable("id") int uniqueId) throws IOException {
        return orgUnitDao.getDegreeProgrammes(uniqueId);
    }

    @RequestMapping("/humanResources")
    public List<SteeringGroup> getHumanResources() throws IOException {
        return orgUnitDao.getHumanResources();
    }

    @RequestMapping("/researchGroups")
    public List<SteeringGroup> getResearchGroups() throws IOException {
        return orgUnitDao.getResearchGroups();
    }

    @RequestMapping("/finance")
    public List<SteeringGroup> getFinance() throws IOException {
        return orgUnitDao.getFinance();
    }

}
