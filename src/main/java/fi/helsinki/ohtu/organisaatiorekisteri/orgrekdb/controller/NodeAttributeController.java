package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.AttributeDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.SectionDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Attribute;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.NodeAttributeKeyValueDTO;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.SectionAttribute;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.service.NodeAttributeService;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.Constants;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.DateUtil;

@RestController
@RequestMapping("/api/node")
public class NodeAttributeController {

    @Autowired
    private OrgUnitDao orgUnitDao;

    @Autowired
    private AttributeDao attributeDao;

    @Autowired
    private SectionDao sectionDao;

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
            List<Attribute> nodeNameAttributes = attributeDao.getSectionAttributesBySectionAndNodeId(Constants.NAME_SECTION, node.getId());
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
            List<Attribute> nodeTypeAttributes = attributeDao.getSectionAttributesBySectionAndNodeId(Constants.TYPE_SECTION, node.getId());
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
            List<Attribute> nodeCodeAttributes = attributeDao.getSectionAttributesBySectionAndNodeId(Constants.CODE_SECTION, node.getId());
            return new ResponseEntity<>(nodeCodeAttributes, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/other/attributes")
    public ResponseEntity<Map<String, List<Attribute>>> updateOtherAttributes(@RequestBody Map<String, List<Attribute>> attributesMap) {
        try {
            nodeAttributeService.updateDeleteOrSaveNodeOtherAttributes(attributesMap);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/other/attributes/{id}")
    public ResponseEntity<List<Attribute>> getNodeOtherAttributes(@PathVariable("id") int nodeUniqueId) {
        try {
            Node node = orgUnitDao.getNodeByUniqueId(nodeUniqueId);
            List<Attribute> nodeCodeAttributes = attributeDao.getSectionAttributesBySectionAndNodeId(Constants.OTHER_SECTION, node.getId());
            return new ResponseEntity<>(nodeCodeAttributes, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/section/{sectionType}/attributes")
    public ResponseEntity<List<SectionAttribute>> getSectionAttributes(@PathVariable("sectionType") String sectionType) {
        try {
            List<SectionAttribute> sectionAttributes = sectionDao.getSectionAttributesBySection(sectionType);
            return new ResponseEntity<>(sectionAttributes, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/distinctattributes/")
    public ResponseEntity<List<NodeAttributeKeyValueDTO>> getDistinctNodeAttrs() {
        try {
            List<NodeAttributeKeyValueDTO> sectionAttributes = attributeDao.getDistinctNodeAttrs();
            return new ResponseEntity<>(sectionAttributes, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/attribute/sortable")
    public ResponseEntity<Map<String, List<String>>> getSortableByValue() {
        try {
            Map<String, List<String>> sortableByValueAttributes = attributeDao.sortableByValueAttributes();
            return new ResponseEntity<>(sortableByValueAttributes, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}/{hierarchies}/{date}/attributes")
    public ResponseEntity<?> getNodeAttributes(
        @PathVariable("id") Integer uniqueId,
        @PathVariable("date") String date,
        @PathVariable("hierarchies") String commaSeparatedHierarchies) throws IOException {
        Node target = orgUnitDao.getNodeByUniqueId(uniqueId);
        if (target == null) {
            return ResponseEntity.notFound().build();
        }

        List<String> hierarchies = Arrays.asList(commaSeparatedHierarchies.split(","));

        Map<String, List<Attribute>> attributes = nodeAttributeService.getAttributes(target.getId(), hierarchies, date);
        return ResponseEntity.ok(attributes);
    }

}
