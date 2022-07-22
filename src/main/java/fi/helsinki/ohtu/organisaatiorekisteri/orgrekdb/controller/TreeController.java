package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.TreeNodeWrapper;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/tree")
public class TreeController {

    @Autowired
    private OrgUnitDao orgUnitDao;

    @RequestMapping(method = GET, value = "/{hierarchyType}/{date}")
    public List<TreeNodeWrapper> getTree(@PathVariable("hierarchyType") String hierarchyType, @PathVariable String date) {
        Date dateObj = DateUtil.parseDate(date);
        List<TreeNodeWrapper> treeNodes = orgUnitDao.getTreeNodes(hierarchyType, dateObj);
        return treeNodes;
    }

}
