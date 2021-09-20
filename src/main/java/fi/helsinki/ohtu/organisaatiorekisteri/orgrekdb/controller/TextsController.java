package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.TextsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
