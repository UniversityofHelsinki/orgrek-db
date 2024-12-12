package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.TextsDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.EducationUnit;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.FinanceUnit;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.HumanResourceIamGroupPrefix;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.OfficialUnit;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.ResearchResource;
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

    @RequestMapping(method = RequestMethod.GET, value = "/researchResources")
    public List<ResearchResource> getResearchResources() throws IOException {
        return orgUnitDao.getResearchResources();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/financeUnits")
    @JsonView(FinanceUnit.WithPublicityView.class)
    public List<FinanceUnit> getFinance() throws IOException {
      return orgUnitDao.getFinanceUnits();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/financeUnitsPublic")
    @JsonView(FinanceUnit.DefaultView.class)
    public List<FinanceUnit> getPublicFinanceUnits() throws IOException {
      return orgUnitDao.getPublicFinanceUnits();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/financeUnitsWithUniqueCode")
    @JsonView(FinanceUnit.WithUniqueCodeView.class)
    public List<FinanceUnit> getFinanceUnitsWithUniqueCode() throws IOException {
      return orgUnitDao.getFinanceUnitsWithUniqueCode();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/financeUnitsWithUniqueCodeExclusive")
    @JsonView(FinanceUnit.DefaultView.class)
    public List<FinanceUnit> getFinanceUnitsWithUniqueCodeExclusive() throws IOException {
      return orgUnitDao.getFinanceUnitsWithUniqueCodeExclusive();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/officialUnits")
    public List<OfficialUnit> getOfficialUnits() throws IOException {
      return orgUnitDao.getOfficialUnits();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/educationUnits")
    public List<EducationUnit> getEducationUnits() throws IOException {
      return orgUnitDao.getEducationUnits();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/concernGroups")
    public List<SteeringGroup> getConcernGroups() throws IOException {
      return orgUnitDao.getConcernGroups();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/humanResourcesIamGroupPrefix")
    public List<HumanResourceIamGroupPrefix> getHumanResourcesIamGroupPrefix() throws IOException {
      return orgUnitDao.getHumanResourcesIamGroupPrefix();
    }

}
