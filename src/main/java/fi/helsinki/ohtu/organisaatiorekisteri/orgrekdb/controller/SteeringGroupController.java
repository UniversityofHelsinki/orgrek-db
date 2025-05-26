package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.EdgeDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.TextsDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.EducationUnit;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.FinanceAndOldResearch;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.FinanceUnit;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.HumanResourceIamGroupPrefix;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.OfficialUnit;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.PersonnelUnitMap;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.ResearchResource;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.SteeringGroup;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.TextDTO;

/*
Public API to get Steering Groups from database among other units.

See https://workgroups.helsinki.fi/pages/viewpage.action?pageId=338205803
 */
@RestController
@RequestMapping("/api/public")
public class SteeringGroupController {

    @Autowired
    private OrgUnitDao orgUnitDao;

    @Autowired
    private TextsDao textsDao;

    @Autowired
    private EdgeDao edgeDao;

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
    @JsonView(OfficialUnit.DefaultView.class)
    public List<OfficialUnit> getOfficialUnits() throws IOException {
      return orgUnitDao.getOfficialUnits();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/officialUnitsWithEducationQualifier")
    @JsonView(OfficialUnit.WithEducationQualifierView.class)
    public List<OfficialUnit> getOfficialUnitsWithEducationQualifier() throws IOException {
      return orgUnitDao.getOfficialUnitsWithEducationQualifier();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/educationUnits")
    @JsonView(EducationUnit.DefaultView.class)
    public List<EducationUnit> getEducationUnits() throws IOException {
      return orgUnitDao.getEducationUnits();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/educationUnitsWithEducationQualifier")
    @JsonView(EducationUnit.WithEducationQualifierView.class)
    public List<EducationUnit> getEducationUnitsWithEducationQualifier() throws IOException {
      return orgUnitDao.getEducationUnitsWithEducationQualifier();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/concernGroups")
    public List<SteeringGroup> getConcernGroups() throws IOException {
      return orgUnitDao.getConcernGroups();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/humanResourcesIamGroupPrefix")
    public List<HumanResourceIamGroupPrefix> getHumanResourcesIamGroupPrefix() throws IOException {
      return orgUnitDao.getHumanResourcesIamGroupPrefix();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/NodesInMultipleHierarchies")
    public ResponseEntity<?> getPublicHierarchyNodes(@RequestParam("hierarchy") String rawHierarchies) throws IOException {
      List<String> parsedHierarchies = Arrays.asList(rawHierarchies.split(","));
      Set<String> hierarchies = new HashSet<>(parsedHierarchies);
      if (!containsOnlyPublicHierarchies(hierarchies)) {
          return ResponseEntity.status(HttpStatus.FORBIDDEN).body(String.format(
                "Request parameter 'hierarchy' can only contain public hierarchies."
          ));
      }

      return ResponseEntity.ok(orgUnitDao.getPublicHierarchyNodes(parsedHierarchies));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/FinanceAndOldResearch")
    public List<FinanceAndOldResearch> getFinanceAndOldResearchUnits() throws IOException {
      return orgUnitDao.getFinanceAndOldResearchUnits();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/PersonnelUnitMap")
    public List<PersonnelUnitMap> getPersonnelUnitMaps() throws IOException {
      return orgUnitDao.getPersonnelUnitMaps();
    }

    private boolean containsOnlyPublicHierarchies(Set<String> hierarchies) throws IOException {
      Set<String> copy = new HashSet<>(hierarchies);
      List<String> publicHierarchies = edgeDao.getPublicHierarchyTypes();
      copy.removeAll(publicHierarchies);
      return copy.isEmpty();
    }

}
