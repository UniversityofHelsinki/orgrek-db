package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.TextsDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.EducationUnit;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.SteeringGroup;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.TextDTO;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.Constants;

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
      return orgUnitDao.getHierarchyOrgUnits(
          Constants.PUBLIC_API_START_NODE, 
          new Date(), 
          Constants.PUBLIC_API_HIERARCHY_RESEARCH_GROUP, 
          Constants.PUBLIC_API_CODE_RESEARCH_GROUP
      );
    }

    @RequestMapping(method = RequestMethod.GET, value = "/financeUnits")
    public List<SteeringGroup> getFinance() throws IOException {
      return orgUnitDao.getHierarchyOrgUnits(
          Constants.PUBLIC_API_START_NODE, 
          new Date(), 
          Constants.PUBLIC_API_HIERARCHY_FINANCE_UNIT, 
          Constants.PUBLIC_API_CODE_FINANCE_UNIT
      );
    }

    @RequestMapping(method = RequestMethod.GET, value = "/educationUnits")
    public List<EducationUnit> getEducationUnits() throws IOException {
      return orgUnitDao.getEducationUnits();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/officialUnits")
    public List<SteeringGroup> getOfficialUnits() throws IOException {
      return orgUnitDao.getHierarchyOrgUnits(
          Constants.PUBLIC_API_START_NODE, 
          new Date(), 
          Constants.PUBLIC_API_HIERARCHY_OFFICIAL_UNIT, 
          Constants.PUBLIC_API_CODE_OFFICIAL_UNIT
      );
    }

}
