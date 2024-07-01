package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain;

import com.fasterxml.jackson.annotation.JsonView;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.FinanceUnit.DefaultView;

@JsonView(DefaultView.class)
public class FinanceUnit {

    public interface DefaultView {};
    public interface WithPublicityView extends DefaultView {};
    public interface WithUniqueCodeView extends WithPublicityView {};

    private String uniqueId;

    private String code;

    private String type;

    private String publicity;

    private String uniqueCode;

    private String nameFi;

    private String nameEn;

    private String nameSv;

    private String parent;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonView(WithPublicityView.class)
    public String getPublicity() {
        return publicity;
    }

    public void setPublicity(String publicity) {
        this.publicity = publicity;
    }

    @JsonView(WithUniqueCodeView.class)
    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }


    public String getNameFi() {
        return nameFi;
    }

    public void setNameFi(String nameFi) {
        this.nameFi = nameFi;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameSv() {
        return nameSv;
    }

    public void setNameSv(String nameSv) {
        this.nameSv = nameSv;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }


}
