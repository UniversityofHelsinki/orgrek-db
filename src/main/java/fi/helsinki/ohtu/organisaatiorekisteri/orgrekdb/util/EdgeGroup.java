package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util;

import java.util.List;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.EdgeWrapper;

public interface EdgeGroup {
  List<EdgeWrapper> get(String id);
}
