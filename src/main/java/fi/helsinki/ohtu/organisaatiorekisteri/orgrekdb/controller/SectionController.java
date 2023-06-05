package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.SectionAttribute;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/section")
public class SectionController {
    @Autowired
    private SectionService sectionService;
    @RequestMapping("/all")
    ResponseEntity<List<SectionAttribute>> getAllSectionAttributes() {
        try {
            List<SectionAttribute> sectionAttributeList = sectionService.getAllSectionAttributes();
            return new ResponseEntity<>(sectionAttributeList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    ResponseEntity<SectionAttribute> updateSectionAttribute(@RequestBody SectionAttribute sectionAttribute) {
        try {
            int updateOK = sectionService.updateSectionAttribute(sectionAttribute);
            if (updateOK > 0) {
                return new ResponseEntity<>(sectionAttribute, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
