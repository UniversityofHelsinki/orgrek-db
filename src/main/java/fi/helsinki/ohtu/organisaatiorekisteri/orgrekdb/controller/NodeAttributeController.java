package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.AttributeDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Attribute;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
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

    @PostMapping("/attributes/{nodeId}/{skipValidation}")
    public ResponseEntity<Attribute> insertAttribute(@PathVariable("nodeId") String nodeId,
                                            @PathVariable("skipValidation") boolean skipValidation,
                                            @RequestBody Attribute attribute) throws IOException {
        Attribute existingAttribute = null;
        if(!skipValidation || !isFullnameAttribute(attribute)) {
            existingAttribute = attributeDao.getExistingAttribute(nodeId, attribute);
        }
        if(skipValidation || existingAttribute==null) {
            Attribute created = attributeDao.insertAttribute(attribute);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(existingAttribute, HttpStatus.CONFLICT);
        }
    }

    private boolean isFullnameAttribute(Attribute attribute) {
        List fullnameAttributes = Arrays.asList(new String[]{"emo_lyhenne", "lyhenne", "name_fi", "name_en", "name_sv"});
        return fullnameAttributes.contains(attribute.getKey());
    }


}
