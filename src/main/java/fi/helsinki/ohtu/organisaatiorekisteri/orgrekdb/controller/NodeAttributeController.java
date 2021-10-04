package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class NodeAttributeController {

    @Autowired
    private OrgUnitDao orgUnitDao;

    @RequestMapping("/api/{id}")
    public  Map<String, List<Attribute>> getOrgUnit(@PathVariable("id") String id) {
        return orgUnitDao.getAllAttributesMap(id);
    }

}
