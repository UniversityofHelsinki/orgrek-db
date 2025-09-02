package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain;

import java.util.Date;

public class EdgePublic {
  private String successor;
  private String predecessor;
  private Date startDate;
  private Date endDate;

  public String getSuccessor() {
    return successor;
  }

  public void setSuccessor(String successor) {
    this.successor = successor;
  }

  public String getPredecessor() {
    return predecessor;
  }

  public void setPredecessor(String predecessor) {
    this.predecessor = predecessor;
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
}
