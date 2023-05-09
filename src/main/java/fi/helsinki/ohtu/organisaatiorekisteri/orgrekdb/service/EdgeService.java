package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.service;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.EdgeDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.EdgeWrapper;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class EdgeService {

    @Autowired
    private EdgeDao edgeDao;

    @Transactional
    public void modifyEdges(Map<String, List<EdgeWrapper>> edges) throws IOException {
        List<EdgeWrapper> newEdges = edges.get(Constants.NEW_EDGES);
        List<EdgeWrapper> updatedEdges = edges.get(Constants.UPDATED_EDGES);
        List<EdgeWrapper> deletedEdges = edges.get(Constants.DELETED_EDGES);

        if (newEdges != null) {
            edgeDao.insertEdges(newEdges);
        }

        if (updatedEdges != null) {
            edgeDao.updateEdges(updatedEdges);
        }

        if (deletedEdges != null) {
            edgeDao.deleteEdges(deletedEdges);
        }

    }


}
