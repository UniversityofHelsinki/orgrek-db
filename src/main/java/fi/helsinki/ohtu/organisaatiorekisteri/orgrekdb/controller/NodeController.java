package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.EdgeWrapper;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


@RestController
@RequestMapping("/api/node")
public class NodeController {

    @Autowired
    private OrgUnitDao orgUnitDao;

    @RequestMapping(method = GET, value = "/{id}")
    public Node getNodeById(@PathVariable("id") int uniqueId) throws IOException {
        return orgUnitDao.getNodeByUniqueId(uniqueId);
    }

    @RequestMapping(method = GET, value = "/id/{id}")
    public Node getNodeByNodeId(@PathVariable("id") String nodeId) throws IOException {
        return orgUnitDao.getNodeByNodeId(nodeId);
    }

    @PostMapping("/addNewUpperUnit")
    public EdgeWrapper addNewUpperUnit(@RequestBody EdgeWrapper edgeWrapper) throws IOException {
        return orgUnitDao.addNewUpperUnit(edgeWrapper);
    }

    @PutMapping("/properties/{id}")
    public Node updateNodeProperties(@PathVariable("id") int nodeId, @RequestBody Node node) throws IOException {
        orgUnitDao.updateNodeProperties(node);
        return orgUnitDao.getNodeByUniqueId(nodeId);
    }


}

