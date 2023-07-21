package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain;
import java.util.Date;

public class SectionAttribute {
    private int id;
    private String section;
    private String attr;
    private Date startDate;
    private Date endDate;

    private int orderNro;

    public SectionAttribute() {
    }

    public SectionAttribute(int id, String section, String attr, Date startDate, Date endDate, int orderNro) {
        this.id = id;
        this.section = section;
        this.attr = attr;
        this.startDate = startDate;
        this.endDate = endDate;
        this.orderNro = orderNro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getOrderNro() {
        return orderNro;
    }

    public void setOrderNro(int orderNro) {
        this.orderNro = orderNro;
    }
}
