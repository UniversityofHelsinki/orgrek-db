package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.HierarchyPublicityDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.HierarchyFilter;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.HierarchyPublicity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/hierarchyPublicity")
public class HierarchyPublicityController {
    @Autowired
    private HierarchyPublicityDao hierarchyPublicityDao;

    @RequestMapping(method = GET, value = "/all")
    public List<HierarchyPublicity> getHierarchiesWithPublicityInformation() throws IOException {
        return hierarchyPublicityDao.getHierarchiesWithPublicityInformation();
    };

};
