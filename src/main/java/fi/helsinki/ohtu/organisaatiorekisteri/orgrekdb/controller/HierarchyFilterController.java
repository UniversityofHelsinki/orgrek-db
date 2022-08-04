package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.HierarchyFilterDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.HierarchyFilter;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/node")
public class HierarchyFilterController {

    @Autowired
    private HierarchyFilterDao hierarchyFilterDao;

    @RequestMapping(method = GET, value = "/hierarchyfilter/all")
    public List<HierarchyFilter> getHierarchyFilters() {
        List<HierarchyFilter>  hierarchyFilters = hierarchyFilterDao.getHierarchyFilters();
        return hierarchyFilters;
    }
    @RequestMapping(method = GET, value = "/hierarchyfilter/{hierarchy}/{date}")
    public List<HierarchyFilter> getHierarchyFilter(@PathVariable("hierarchy") String hierarchy, @PathVariable("date") String date) {
        Date dateObj = DateUtil.parseDate(date);
        String[] hierarchyArr = hierarchy.split(",");
        List<String> hierarchies = Arrays.asList(hierarchyArr);

        List<HierarchyFilter>  hierarchyFilters = hierarchyFilterDao.getHierarchyFilter(hierarchies, dateObj);
        return hierarchyFilters;
    }
}
