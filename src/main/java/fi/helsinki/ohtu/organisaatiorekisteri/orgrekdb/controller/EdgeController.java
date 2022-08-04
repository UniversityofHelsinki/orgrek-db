package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.EdgeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/edge")
public class EdgeController {

    @Autowired
    private EdgeDao edgeDao;

    @RequestMapping("/types")
    public List<String> getHierarchyTypes() throws IOException {
        return edgeDao.getHierarchyTypes();
    }


}
