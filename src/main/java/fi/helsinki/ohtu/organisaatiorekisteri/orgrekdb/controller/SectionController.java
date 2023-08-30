package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.SectionAttribute;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.service.SectionService;

@RestController
@RequestMapping("/api/section")
public class SectionController {
    @Autowired
    private SectionService sectionService;
    @GetMapping("/all")
    ResponseEntity<List<SectionAttribute>> getAllSectionAttributes() {
        try {
            List<SectionAttribute> sectionAttributeList = sectionService.getAllSectionAttributes();
            return new ResponseEntity<>(sectionAttributeList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/alldistinct")
    ResponseEntity<List<String>> getAllDistinctSectionAttributes() {
        try {
            List<String> sectionAttributeList = sectionService.getAllDistinctSectionAttributes();
            return new ResponseEntity<>(sectionAttributeList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/insert")
    ResponseEntity<SectionAttribute> insertSectionAttribute(@RequestBody SectionAttribute sectionAttribute) {
        try {
            SectionAttribute insertedSectionAttribute = sectionService.insertSectionAttribute(sectionAttribute);
            return new ResponseEntity<>(insertedSectionAttribute, HttpStatus.OK);
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

    @DeleteMapping("/{id}/delete")
    ResponseEntity deleteSectionAttribute(@PathVariable int id) {
        try {
            int deleteOk = sectionService.deleteSectionAttribute(id);
            if (deleteOk > 0) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}/get")
    ResponseEntity<SectionAttribute> getSectionAttributeById(@PathVariable int id) {
        try {
            SectionAttribute sectionAttribute = sectionService.getSectionAttributeById(id);
            return new ResponseEntity<>(sectionAttribute, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
