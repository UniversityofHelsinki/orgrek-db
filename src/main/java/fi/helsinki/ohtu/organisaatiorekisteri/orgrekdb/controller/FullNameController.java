package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.FullName;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/node/fullname/")
public class FullNameController {

    @Autowired
    public OrgUnitDao orgUnitDao;

    @RequestMapping(method = GET, value = "{id}/{date}")
    public List<FullName> getFullNames(@PathVariable("id") int uniqueId, @PathVariable("date") String date) throws IOException {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return orgUnitDao.getFullNames(node.getId(), date);
    }

    @RequestMapping(method = GET, value = "historyandcurrent/{id}/{date}")
    public List<FullName> getHistoryAndCurrentFullNames(@PathVariable("id") int uniqueId, @PathVariable("date") String date) throws IOException {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return orgUnitDao.getHistoryAndCurrentFullNames(node.getId(), date);
    }

    @RequestMapping(method = GET, value = "futureandcurrent/{id}/{date}")
    public List<FullName> getFutureAndCurrentFullNames(@PathVariable("id") int uniqueId, @PathVariable("date") String date) throws IOException {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return orgUnitDao.getFutureAndCurrentFullNames(node.getId(), date);
    }

    @RequestMapping(method = GET, value = "all/{id}")
    public List<FullName> getFullNames(@PathVariable("id") int uniqueId) throws IOException {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return orgUnitDao.getAllFullNames(node.getId());
    }

    @RequestMapping(method = GET, value = "favorable/{id}/{date}")
    public List<FullName> getFavorableNames(@PathVariable("id") int uniqueId, @PathVariable("date") String date) throws IOException {
        return orgUnitDao.getFavorableNames(uniqueId, date);
    }
}
