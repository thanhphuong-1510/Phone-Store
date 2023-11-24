package phuongnguyen.btl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phuongnguyen.btl.entity.MaGG;
import phuongnguyen.btl.repository.MaGGRepository;

import java.util.List;

@Service
public class MaGGService {

  @Autowired
  private MaGGRepository maGGRepository;

  public List<MaGG> getAll() {
    return maGGRepository.findAll();
  }
}
