package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.TextsDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.TextDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.*;

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

    @RequestMapping(method = PUT, path = "/api/texts")
    public Map<String, String> updateText(@RequestBody Map<String, String> text) {
        textsDao.updateText(text);
        List<Map<String, String>> allTexts = textsDao.getAllTexts();
        return allTexts.stream().filter(t ->
                t.get("key").equals(text.get("key")) &&
                t.get("language").equals(text.get("language"))
        ).findFirst().get();
    }

    @RequestMapping(method = DELETE, path = "/api/texts")
    public Map<String, String> deleteText(@RequestBody Map<String, String> text) {
        textsDao.deleteText(text);
        return text;
    }

    @RequestMapping(method = POST, path = "/api/texts")
    public List<Map<String, String>> addText(@RequestBody List<Map<String, String>> texts) {
        textsDao.insertTexts(texts);
        return textsDao.getAllTexts().stream().filter(text ->
                texts.stream().anyMatch(requestText ->
                        text.get("key").equals(requestText.get("key")) &&
                        text.get("language").equals(requestText.get("language"))
                )
        ).collect(Collectors.toList());
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
}
