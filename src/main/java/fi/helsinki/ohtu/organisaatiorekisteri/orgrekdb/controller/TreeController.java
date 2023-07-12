package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.EdgeDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.service.TreeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/tree")
public class TreeController {

    @Autowired
    private EdgeDao edgeDao;

    @Autowired
    private TreeService treeService;

    @RequestMapping(method = GET, value = "/{hierarchies}/{date}")
    public ResponseEntity<?> getTree(@PathVariable("hierarchies") String hierarchies, @PathVariable("date") String date) throws IOException {
        Set<String> hierarchySet = new HashSet<>(Arrays.asList(hierarchies.split(",")));
        if (containsUnknownHierarchies(hierarchySet)) {
            return ResponseEntity.badRequest().body(String.format(
                "Invalid hierarchy types provided, accepted are: %s", edgeDao.getHierarchyTypes()
            ));
        }
        return ResponseEntity.ok(treeService.getTree(hierarchySet, date));
    }

    private boolean containsUnknownHierarchies(Set<String> hierarchies) throws IOException {
        Set<String> copy = new HashSet<>(hierarchies);
        List<String> validHierarchies = edgeDao.getHierarchyTypes();
        validHierarchies.remove("history");
        copy.removeAll(validHierarchies);
        return copy.size() > 0;
    }


}
