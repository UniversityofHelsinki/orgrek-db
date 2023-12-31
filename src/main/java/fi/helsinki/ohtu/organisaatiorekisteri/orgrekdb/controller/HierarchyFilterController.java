package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.HierarchyFilterDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.HierarchyFilter;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.OtherAttribute;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.Constants;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.DateUtil;

@RestController
@RequestMapping("/api/hierarchyfilter")
public class HierarchyFilterController {

    @Autowired
    private HierarchyFilterDao hierarchyFilterDao;

    @RequestMapping(method = GET, value = "/{date}/{whichtime}")
    public List<HierarchyFilter> getAllHierarchyFilters(@PathVariable("date") String date, @PathVariable("whichtime") String whichtime) {
        Date dateObj = DateUtil.parseDate(date);
        List<HierarchyFilter> hierarchyFilters = new ArrayList<>();

        if (whichtime.equalsIgnoreCase(Constants.NOW)) {
            hierarchyFilters = hierarchyFilterDao.getCurrentHierarchyFilter(dateObj);
        } else if (whichtime.equalsIgnoreCase(Constants.FUTURE)) {
            hierarchyFilters = hierarchyFilterDao.getFutureAndCurrentHierarchyFilter(dateObj);
        } else if (whichtime.equalsIgnoreCase(Constants.HISTORY)) {
            hierarchyFilters = hierarchyFilterDao.getHistoryAndCurrentHierarchyFilter(dateObj);
        }
        return hierarchyFilters;
    }


    @RequestMapping(method = GET, value = "/all")
    public List<HierarchyFilter> getHierarchyFilters() throws IOException  {
        return hierarchyFilterDao.getHierarchyFilters();
    }
    @RequestMapping(method = PUT)
    public HierarchyFilter updateHierarchyFilter(@RequestBody HierarchyFilter hierarchyFilter) throws IOException {
        hierarchyFilterDao.updateHierarchyFilter(hierarchyFilter);
        List<HierarchyFilter> allHierarchyFilters = hierarchyFilterDao.getHierarchyFilters();
        return allHierarchyFilters.stream().filter(hf ->
                hf.getId() == hierarchyFilter.getId()
        ).findFirst().get();
    }

    @RequestMapping(method = DELETE)
    public HierarchyFilter deleteHierarchyFilter(@RequestBody HierarchyFilter hierarchyFilter) throws IOException {
        hierarchyFilterDao.deleteHierarchyFilter(hierarchyFilter);
        return hierarchyFilter;
    }

    @RequestMapping(method = POST)
    public List<HierarchyFilter> addHierarchyFilter(@RequestBody List<HierarchyFilter> hierarchyFilters) throws IOException {
        List<HierarchyFilter> beforeSave = hierarchyFilterDao.getHierarchyFilters();
        hierarchyFilterDao.insertHierarchyFilters(hierarchyFilters);
        return hierarchyFilterDao.getHierarchyFilters().stream().filter(hierarchyFilter ->
                beforeSave.stream().noneMatch(b -> b.getId() == hierarchyFilter.getId())
        ).collect(Collectors.toList());
    }

    @RequestMapping(method = GET, value = "/{selectedHierarchies}/{sections}/attributes/keys")
    public List<String> getAttributeKeys(
            @PathVariable("selectedHierarchies") String selectedHierarchies,
            @PathVariable("sections") String rawSections
    ) throws IOException {
        List<String> hierarchies = Arrays.asList(selectedHierarchies.split(","));
        List<String> sections = List.of(rawSections.split(","));
        List<String> attributeKeys = hierarchyFilterDao.getAttributeKeys(hierarchies, sections);
        if (sections.contains(Constants.CODE_SECTION)) {
            attributeKeys.add(Constants.PARENT_ABBREVIATION);
            attributeKeys.add(Constants.ABBREVIATION);
        }
        return attributeKeys;
    }

    @RequestMapping(method = GET, value = "/{selectedHierarchies}/{sections}/{attributes}/attributes/hierarchies")
    public List<OtherAttribute> getHierarchiesBySections(
            @PathVariable("selectedHierarchies") String selectedHierarchies,
            @PathVariable("sections") String rawSections,
            @PathVariable("attributes") String selectedAttributes
    ) throws IOException {
        List<String> hierarchies = Arrays.asList(selectedHierarchies.split(","));
        List<String> sections = List.of(rawSections.split(","));
        List<String> attributes = Arrays.asList(selectedAttributes.split(","));
        List<OtherAttribute> otherAttributes = hierarchyFilterDao.getHierarchiesBySections(attributes, hierarchies, sections);

        return otherAttributes;
    }

    @RequestMapping(method = GET, value = "/hierarchyFiltersByKey/{keys}")
    public List<HierarchyFilter> getHierarchyFiltersByKey(@PathVariable("keys") String keys) throws IOException {
        List<String> keysList = Arrays.asList(keys.split(","));
        return  hierarchyFilterDao.getHierarchyFiltersByKeys(keysList);
    }

    @GetMapping("/distinctHierarchyFilterKeys")
    public List<String> getDistinctHierarchyFilterKeys() throws IOException {
        List<String> distinctHierarchyFilterKeys = hierarchyFilterDao.getDistinctHierarchyFilterKeys();
        return distinctHierarchyFilterKeys;
    }
}
