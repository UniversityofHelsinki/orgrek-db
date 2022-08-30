package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.HierarchyFilterDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.HierarchyFilter;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.DateUtil;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.*;

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
        int[] results = hierarchyFilterDao.insertHierarchyFilters(hierarchyFilters);
        return hierarchyFilterDao.getHierarchyFilters().stream().filter(hierarchyFilter ->
                beforeSave.stream().noneMatch(b -> b.getId() == hierarchyFilter.getId())
        ).collect(Collectors.toList());
    }
}
