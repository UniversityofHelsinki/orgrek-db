package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.TextsDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.SteeringGroup;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.TextDTO;

/*
Public API to get Steering Groups from database among other units.
 */
@RestController
@RequestMapping("/api/public")
public class SteeringGroupController {

    @Autowired
    private OrgUnitDao orgUnitDao;

    @Autowired
    private TextsDao textsDao;

    @RequestMapping(method = RequestMethod.GET, value="/steeringGroups")
    public List<SteeringGroup> getSteeringGroups() throws IOException {
        return orgUnitDao.getSteeringGroups();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/degreeTitles")
    public List<TextDTO> getDegreeTitles() throws IOException {
        return textsDao.getDegreeTitles();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/degreeProgrammes/{id}")
    public List<Node> getDegreeProgrammes(@PathVariable("id") int uniqueId) throws IOException {
        return orgUnitDao.getDegreeProgrammes(uniqueId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/humanResources")
    public List<SteeringGroup> getHumanResources() throws IOException {
        return orgUnitDao.getHumanResources();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/researchGroups")
    public List<SteeringGroup> getResearchGroups() throws IOException {
        return orgUnitDao.getResearchGroups();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/financeUnits")
    public List<SteeringGroup> getFinance() throws IOException {
        return orgUnitDao.getFinanceUnits();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/educationUnits")
    public List<SteeringGroup> getEducationUnits() throws IOException {
        return orgUnitDao.getEducationUnits();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/officialUnits")
    public List<SteeringGroup> getOfficialUnits() throws IOException {
        return orgUnitDao.getOfficialUnits();
    }

}
