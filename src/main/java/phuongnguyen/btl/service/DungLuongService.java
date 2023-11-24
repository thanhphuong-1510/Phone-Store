package phuongnguyen.btl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phuongnguyen.btl.entity.DungLuong;
import phuongnguyen.btl.repository.DungLuongRepository;

import java.util.List;

@Service
public class DungLuongService {

  @Autowired
  private DungLuongRepository dungLuongRepository;

  public List<DungLuong> getAll() {
    return dungLuongRepository.findAll();
  }
}
