package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.HierarchyPublicityDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.HierarchyPublicity;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.NewHierarchyPublicityDTO;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.service.HierarchyPublicityService;

@RestController
@RequestMapping("/api/hierarchyPublicity")
public class HierarchyPublicityController {
    @Autowired
    private HierarchyPublicityDao hierarchyPublicityDao;

    @Autowired
    private HierarchyPublicityService hierarchyPublicityService;

    @GetMapping("/all")
    public List<HierarchyPublicity> getHierarchiesWithPublicityInformation() throws IOException {
        return hierarchyPublicityDao.getHierarchiesWithPublicityInformation();
    };

    @GetMapping("/{id}/properties")
    public HierarchyPublicity getHierarchyPublicityById(@PathVariable("id") int id) throws IOException{
        return hierarchyPublicityDao.getHierarchyPublicityById(id);
    }

    @PutMapping("/update")
    public HierarchyPublicity updateHierarchyPublicity(@RequestBody HierarchyPublicity hierarchyPublicity) throws IOException{
        return hierarchyPublicityDao.updateHierarchyPublicity(hierarchyPublicity);
    }

    @PostMapping(value = "/insert", headers = "userName")
    public NewHierarchyPublicityDTO insertHierarchyPublicity(@RequestBody NewHierarchyPublicityDTO newHierarchyPublicityDTO, @RequestHeader String userName) throws IOException{
        return hierarchyPublicityService.insertHierarchyPublicity(newHierarchyPublicityDTO, userName);
    }

};
