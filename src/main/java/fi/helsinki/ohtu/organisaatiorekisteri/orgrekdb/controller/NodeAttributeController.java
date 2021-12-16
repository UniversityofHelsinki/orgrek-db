package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Attribute;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/node")
public class NodeAttributeController {

    @Autowired
    private OrgUnitDao orgUnitDao;

    @RequestMapping(method = GET, value = "/{id}/{date}/attributes")
    public List<Attribute> getAttributes(@PathVariable("id") int id, @PathVariable("date") String date) {
        Node node = orgUnitDao.getNodeByUniqueId(id);
        Date dateObj = DateUtil.parseDate(date);
        return orgUnitDao.getAttributeListByDate(node.getId(), dateObj);

    }
    @RequestMapping(method = GET, value = "/history/{id}/{date}/attributes")
    public List<Attribute> getHistoryAttributes(@PathVariable("id") int id, @PathVariable("date") String date) {
        Node node = orgUnitDao.getNodeByUniqueId(id);
        Date dateObj = DateUtil.parseDate(date);
        return orgUnitDao.getHistoryAttributeListByDate(node.getId(), dateObj);

    }


}
