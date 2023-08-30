package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.EdgeWrapper;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.NewNodeDTO;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.service.NodeService;


@RestController
@RequestMapping("/api/node")
public class NodeController {

    @Autowired
    private OrgUnitDao orgUnitDao;

    @Autowired
    private NodeService nodeService;

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
    public ResponseEntity<Node> updateNodeProperties(@PathVariable("id") int uniqueId, @RequestBody Node node) throws IOException {
        try {
            orgUnitDao.updateNodeProperties(node);
            Node foundNode = orgUnitDao.getNodeByUniqueId(uniqueId);
            return new ResponseEntity<>(foundNode, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<NewNodeDTO> insertNode(@RequestBody NewNodeDTO newNodeDTO) throws Exception {
        try {
            newNodeDTO = nodeService.insertNode(newNodeDTO);
            return new ResponseEntity<>(newNodeDTO, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}

