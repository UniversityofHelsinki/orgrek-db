package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.service;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.SectionDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.SectionAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SectionService {

    @Autowired
    private SectionDao sectionDao;

    public List<SectionAttribute> getAllSectionAttributes() throws IOException {
        List<SectionAttribute> sectionAttributeList = sectionDao.getAllSectionAttributes();
        return sectionAttributeList;
    }

    public int updateSectionAttribute(SectionAttribute sectionAttribute) throws IOException {
        return sectionDao.updateSectionAttribute(sectionAttribute);
    }

    public SectionAttribute insertSectionAttribute(SectionAttribute sectionAttribute) throws IOException {
        return sectionDao.insertSectionAttribute(sectionAttribute);
    }

    public int deleteSectionAttribute(int sectionId) throws IOException {
        return sectionDao.deleteSectionAttribute(sectionId);
    }

    public SectionAttribute getSectionAttributeById(int id) throws IOException {
        return sectionDao.getSectionAttributeById(id);
    }
}
