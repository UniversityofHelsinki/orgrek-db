package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.AttributeOrderDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.AttributeOrder;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.AttributeOrderUpdateRequest;

@RestController
@RequestMapping("/api/attributeorder")
public class AttributeOrderController {

  @Autowired
  private AttributeOrderDao attributeOrderDao;

  @GetMapping
  public List<AttributeOrder> getAll() throws IOException {
    return attributeOrderDao.getAll();
  }

  @PostMapping
  public ResponseEntity<AttributeOrder> create(@RequestBody AttributeOrder attributeOrder) throws IOException {
    return ResponseEntity.ok(attributeOrderDao.insert(attributeOrder));
  }

  @DeleteMapping
  public ResponseEntity<AttributeOrder> delete(@RequestBody AttributeOrder attributeOrder) throws IOException {
    return ResponseEntity.ok(attributeOrderDao.delete(attributeOrder));
  }

  @PutMapping
  public ResponseEntity<Void> update(@RequestBody AttributeOrderUpdateRequest request) throws IOException {
    attributeOrderDao.update(request.getOld(), request.getUpdated());
    return ResponseEntity.ok().build();
  }
}
