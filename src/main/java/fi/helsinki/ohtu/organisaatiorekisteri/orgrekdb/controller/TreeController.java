package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.EdgeDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/tree")
public class TreeController {

    @Autowired
    private OrgUnitDao orgUnitDao;

    @Autowired
    private EdgeDao edgeDao;

    @RequestMapping(method = GET, value = "/{hierarchyTypes}/{date}")
    public ResponseEntity<?> getTreeNodes(@PathVariable("hierarchyTypes") String hierarchyTypes, @PathVariable("date") String date) throws IOException {
        Set<String> hierarchies = new HashSet<>(Arrays.asList(hierarchyTypes.split(",")));
        if (containsUnknownHierarchies(hierarchies)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "Invalid hierarchy types provided: '"
                            + hierarchyTypes
                            + "'. Accepted are "
                            + edgeDao.getHierarchyTypes().stream().filter(
                                    h -> !h.equals("history")
                                ).collect(Collectors.toList())
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(orgUnitDao.getTreeNodes("a1", hierarchies, date));
    }

    private boolean containsUnknownHierarchies(Set<String> hierarchies) throws IOException {
        Set<String> copy = new HashSet<>(hierarchies);
        List<String> validHierarchies = edgeDao.getHierarchyTypes();
        validHierarchies.remove("history");
        copy.removeAll(validHierarchies);
        return copy.size() > 0;
    }

}
