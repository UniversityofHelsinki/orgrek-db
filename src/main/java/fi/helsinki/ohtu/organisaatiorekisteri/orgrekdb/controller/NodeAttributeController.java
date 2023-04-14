package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.AttributeDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Attribute;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.SectionAttribute;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.service.NodeAttributeService;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.Constants;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/node")
public class NodeAttributeController {

    @Autowired
    private OrgUnitDao orgUnitDao;

    @Autowired
    private AttributeDao attributeDao;

    @Autowired
    private NodeAttributeService nodeAttributeService;

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

    @PutMapping("/attributes/{nodeId}/{skipValidation}")
    public ResponseEntity<List<Attribute>> updateAttributes(@PathVariable("nodeId") String nodeId,
                                                            @PathVariable("skipValidation") boolean skipValidation,
                                                            @RequestBody List<Attribute> attributes) throws IOException {
        List<Attribute> conflictingAttributes = new ArrayList<>();
        if(!skipValidation) {
            conflictingAttributes = validate(attributes, skipValidation);
        }
        if(skipValidation || conflictingAttributes.isEmpty()) {
            attributeDao.updateAttributes(attributes);
            return new ResponseEntity<>(attributes, HttpStatus.OK);
        }
        if(!conflictingAttributes.isEmpty()) {
            return new ResponseEntity<>(conflictingAttributes, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private List<Attribute> validate(List<Attribute> attributes, boolean skipValidation) throws IOException {
        List<Attribute> ret = new ArrayList<>();
        for(Attribute attribute : attributes) {
            if(!skipValidation || !isFullnameAttribute(attribute)) {
                Attribute attr = attributeDao.checkIfExists(attribute);
                if (attr != null) {
                    ret.add(attr);
                }
            }
        }
        return ret;
    }

    @PostMapping("/attributes/{nodeId}/{skipValidation}")
    public ResponseEntity<Attribute> insertAttribute(@PathVariable("nodeId") String nodeId,
                                                     @PathVariable("skipValidation") boolean skipValidation,
                                                     @RequestBody Attribute attribute) throws IOException {
        Attribute existingAttribute = null;
        if(!skipValidation || !isFullnameAttribute(attribute)) {
            existingAttribute = attributeDao.getExistingAttribute(attribute);
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


    @PutMapping("/name/attributes")
    public ResponseEntity<Map<String, List<Attribute>>> updateNameAttributes(@RequestBody Map<String, List<Attribute>> attributesMap) {
        try {
            nodeAttributeService.updateDeleteOrSaveNodeNameAttributes(attributesMap);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/name/attributes/{id}")
    public ResponseEntity<List<Attribute>> getNodeNameAttributes(@PathVariable("id") int nodeUniqueId) {
        try {
            Node node = orgUnitDao.getNodeByUniqueId(nodeUniqueId);
            List<SectionAttribute> sectionTypeAttributes = attributeDao.getSectionAttributesBySection(Constants.NAME_SECTION);
            List<Attribute> nodeNameAttributes = attributeDao.getSectionAttributesByNodeId(node.getId(), sectionTypeAttributes);
            return new ResponseEntity<>(nodeNameAttributes, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/type/attributes")
    public ResponseEntity<Map<String, List<Attribute>>> updateTypeAttributes(@RequestBody Map<String, List<Attribute>> attributesMap) {
        try {
            nodeAttributeService.updateDeleteOrSaveNodeTypeAttributes(attributesMap);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/type/attributes/{id}")
    public ResponseEntity<List<Attribute>> getNodeTypeAttributes(@PathVariable("id") int nodeUniqueId) {
        try {
            Node node = orgUnitDao.getNodeByUniqueId(nodeUniqueId);
            List<SectionAttribute> sectionTypeAttributes = attributeDao.getSectionAttributesBySection(Constants.TYPE_SECTION);
            List<Attribute> nodeTypeAttributes = attributeDao.getSectionAttributesByNodeId(node.getId(), sectionTypeAttributes);
            return new ResponseEntity<>(nodeTypeAttributes, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/code/attributes")
    public ResponseEntity<Map<String, List<Attribute>>> updateCodeAttributes(@RequestBody Map<String, List<Attribute>> attributesMap) {
        try {
            nodeAttributeService.updateDeleteOrSaveNodeCodeAttributes(attributesMap);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/code/attributes/{id}")
    public ResponseEntity<List<Attribute>> getNodeCodeAttributes(@PathVariable("id") int nodeUniqueId) {
        try {
            Node node = orgUnitDao.getNodeByUniqueId(nodeUniqueId);
            List<SectionAttribute> sectionCodeAttributes = attributeDao.getSectionAttributesBySection(Constants.CODE_SECTION);
            List<Attribute> nodeCodeAttributes = attributeDao.getSectionAttributesByNodeId(node.getId(), sectionCodeAttributes);
            return new ResponseEntity<>(nodeCodeAttributes, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/section/{sectionType}/attributes")
    public ResponseEntity<List<SectionAttribute>> getSectionAttributes(@PathVariable("sectionType") String sectionType) {
        try {
            List<SectionAttribute> sectionAttributes = attributeDao.getSectionAttributesBySection(sectionType);
            return new ResponseEntity<>(sectionAttributes, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
