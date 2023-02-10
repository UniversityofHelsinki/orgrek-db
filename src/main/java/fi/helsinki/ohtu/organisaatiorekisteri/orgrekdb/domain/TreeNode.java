package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreeNode {

    private String parentNodeId;
    private String childNodeId;
    private Integer parentNodeUniqueId;
    private Integer childNodeUniqueId;
    private String parentCode;
    private String childCode;
    private String parentName;
    private String childName;

    private String language;
    private List<String> hierarchies;

    public Integer getParentNodeUniqueId() {
        return parentNodeUniqueId;
    }

    public void setParentNodeUniqueId(Integer parentNodeUniqueId) {
        this.parentNodeUniqueId = parentNodeUniqueId;
    }

    public Integer getChildNodeUniqueId() {
        return childNodeUniqueId;
    }

    public void setChildNodeUniqueId(Integer childNodeUniqueId) {
        this.childNodeUniqueId = childNodeUniqueId;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getChildCode() {
        return childCode;
    }

    public void setChildCode(String childCode) {
        this.childCode = childCode;
    }

    public String getParentNodeId() {
        return parentNodeId;
    }

    public void setParentNodeId(String parentNodeId) {
        this.parentNodeId = parentNodeId;
    }

    public String getChildNodeId() {
        return childNodeId;
    }

    public void setChildNodeId(String childNodeId) {
        this.childNodeId = childNodeId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getHierarchies() {
        return hierarchies;
    }

    public void setHierarchies(String hierarchies) {
        this.hierarchies = Arrays.asList(hierarchies.split(" "));
    }

}
