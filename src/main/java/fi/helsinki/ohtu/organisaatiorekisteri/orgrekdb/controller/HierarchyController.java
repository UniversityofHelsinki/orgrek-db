package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.NodeWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/node")
public class HierarchyController {

    @Autowired
    private OrgUnitDao orgUnitDao;

    @RequestMapping(method = GET, value = "/parents/{id}/{date}")
    public List<Node> getParentNodesByIdAndDate(@PathVariable("id") String nodeId, @PathVariable("date") String date) {
        return orgUnitDao.getCurrentParentsByChildNodeId(nodeId, date);
    }

    @RequestMapping(method = GET, value = "/parents/types/{id}/{date}")
    public List<NodeWrapper> getParentNodeTypesByIdAndDate(@PathVariable("id") String nodeId, @PathVariable("date") String date) {
        return orgUnitDao.getCurrentTypesByChildNodeId(nodeId, date);
    }
}
