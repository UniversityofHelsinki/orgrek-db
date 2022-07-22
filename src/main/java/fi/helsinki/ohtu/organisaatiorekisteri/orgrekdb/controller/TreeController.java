package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.TreeNodeWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/tree")
public class TreeController {

    @Autowired
    private OrgUnitDao orgUnitDao;

    @RequestMapping(method = GET, value = "/{hierarchyType}")
    public List<TreeNodeWrapper> getTree(@PathVariable("hierarchyType") String hierarchyType) {
        List<TreeNodeWrapper> treeNodes = orgUnitDao.getTreeNodes(hierarchyType);
        return treeNodes;
    }

}
