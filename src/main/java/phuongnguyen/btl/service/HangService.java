package phuongnguyen.btl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phuongnguyen.btl.entity.Hang;
import phuongnguyen.btl.repository.HangRepository;

import java.util.List;

@Service
public class HangService {

  @Autowired
  private HangRepository hangRepository;

  public List<Hang> getAll() {
    return hangRepository.findAll();
  }
}
