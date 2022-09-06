package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.AttributeDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Attribute;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/node")
public class NodeAttributeController {

    @Autowired
    private OrgUnitDao orgUnitDao;

    @Autowired
    private AttributeDao attributeDao;

    @GetMapping("/{id}/{date}/attributes")
    public List<Attribute> getAttributes(@PathVariable("id") int id, @PathVariable("date") String date) throws IOException {
        Node node = orgUnitDao.getNodeByUniqueId(id);
        Date dateObj = DateUtil.parseDate(date);
        return orgUnitDao.getAttributeListByDate(node.getId(), dateObj);
    }
    @GetMapping("/historyandcurrent/{id}/{date}/attributes")
    public List<Attribute> getHistoryAndCurrentAttributes(@PathVariable("id") int id, @PathVariable("date") String date) throws IOException {
        Node node = orgUnitDao.getNodeByUniqueId(id);
        Date dateObj = DateUtil.parseDate(date);
        return orgUnitDao.getHistoryAndCurrentAttributeListByDate(node.getId(), dateObj);
    }

    @GetMapping("/futureandcurrent/{id}/{date}/attributes")
    public List<Attribute> getFutureAndCurrentAttributes(@PathVariable("id") int id, @PathVariable("date") String date) throws IOException {
        Node node = orgUnitDao.getNodeByUniqueId(id);
        Date dateObj = DateUtil.parseDate(date);
        return orgUnitDao.getFutureAndCurrentAttributeListByDate(node.getId(), dateObj);
    }

    @PutMapping("/attributes/{nodeId}")
    public List<Attribute> updateAttributes(@PathVariable("nodeId") String nodeId, @RequestBody List<Attribute> attributes) throws IOException {
        attributeDao.updateAttributes(attributes);
        return attributeDao.getAttributesByNodeId(nodeId);
    }

    @PostMapping("/attributes/{nodeId}")
    public List<Attribute> insertAttributes(@PathVariable("nodeId") String nodeId, @RequestBody List<Attribute> attributes) throws IOException {
        attributeDao.insertAttributes(attributes);
        return attributeDao.getAttributesByNodeId(nodeId);
    }

}
