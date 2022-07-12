package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.FullName;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/node/fullname/")
public class FullNameController {

    @Autowired
    public OrgUnitDao orgUnitDao;

    @RequestMapping(method = GET, value = "{id}/{date}")
    public List<FullName> getFullNames(@PathVariable("id") int uniqueId, @PathVariable("date") String date) {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        /*
        Date selectedDay = DateUtil.parseDate(date);
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("fi"));
        if (node.getEndDate() != null && selectedDay.after(node.getEndDate())) {
            return orgUnitDao.getFullNames(node.getId(), df.format(node.getEndDate()));
        } else if (node.getStartDate() != null && selectedDay.before(node.getStartDate())) {
            return orgUnitDao.getFullNames(node.getId(), df.format(node.getStartDate()));
        }
        */
        return orgUnitDao.getFullNames(node.getId(), date);
    }

    @RequestMapping(method = GET, value = "historyandcurrent/{id}/{date}")
    public List<FullName> getHistoryAndCurrentFullNames(@PathVariable("id") int uniqueId, @PathVariable("date") String date) {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return orgUnitDao.getHistoryAndCurrentFullNames(node.getId(), date);
    }

    @RequestMapping(method = GET, value = "futureandcurrent/{id}/{date}")
    public List<FullName> getFutureAndCurrentFullNames(@PathVariable("id") int uniqueId, @PathVariable("date") String date) {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return orgUnitDao.getFutureAndCurrentFullNames(node.getId(), date);
    }

    @RequestMapping(method = GET, value = "all/{id}")
    public List<FullName> getFullNames(@PathVariable("id") int uniqueId) {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return orgUnitDao.getAllFullNames(node.getId());
    }

    /*

    @RequestMapping(method = POST, value = "{date}/mass")
    public List<FullName> getFullNames(@PathVariable("date") String date, @RequestBody List<Integer> uniqueIds) {
        if (uniqueIds.size() == 0) {
            return new ArrayList<>();
        }
        return orgUnitDao.getFullNames(uniqueIds, date);
    }

    @RequestMapping(method = GET, value = "history/{id}/{date}")
    public List<FullName> getFullNamesUntil(@PathVariable("id") int uniqueId, @PathVariable("date") String date) {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return orgUnitDao.getFullNamesUntil(node.getId(), date);
    }

    @RequestMapping(method = GET, value = "future/{id}/{date}")
    public List<FullName> getFullNamesFrom(@PathVariable("id") int uniqueId, @PathVariable("date") String date) {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return orgUnitDao.getFullNamesFrom(node.getId(), date);
    }

     */

}
