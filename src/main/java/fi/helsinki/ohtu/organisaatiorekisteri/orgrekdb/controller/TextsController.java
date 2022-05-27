package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.TextsDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.TextDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class TextsController {

    @Autowired
    private TextsDao textsDao;

    @RequestMapping("/")
    public String hello() {
        return "Hello, world";
    }

    @RequestMapping("/api/texts")
    public List<Map<String, String>> getAllTexts() {
        return textsDao.getAllTexts();
    }

    @CrossOrigin
    @RequestMapping("/api/texts/{language}/{ns}")
    public Map<String, String> getTextsByLang(@PathVariable("language") String language, @PathVariable("ns") String namespace) {
        if (namespace.equals("texts")) {
            return textsDao.getTextsByLang(language);
        }
        else if (namespace.contains("nodeattr")) {
            String currentDate = namespace.substring("nodeattr".length());
            return textsDao.getAttributeNamesByLang(language, currentDate);
        }
        else return Collections.emptyMap();
    }

    @RequestMapping("/api/degreeTitles")
    public List<TextDTO> getDegreeTitles() {
        return textsDao.getDegreeTitles();
    }
}
