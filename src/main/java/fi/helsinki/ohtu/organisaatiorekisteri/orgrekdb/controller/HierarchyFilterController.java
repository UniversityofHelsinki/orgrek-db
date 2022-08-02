package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.HierarchyFilterDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.HierarchyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(method = GET, value = "/hierarchyfilter/{hierarchy}")
    public List<HierarchyFilter> getHierarchyFilter(@PathVariable("hierarchy") String hierarchy) {
        List<HierarchyFilter>  hierarchyFilters = hierarchyFilterDao.getHierarchyFilter(hierarchy);
        return hierarchyFilters;
    }
}
