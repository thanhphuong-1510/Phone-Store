package phuongnguyen.btl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phuongnguyen.btl.entity.MauSac;
import phuongnguyen.btl.repository.MauSacRepository;

import java.util.List;

@Service
public class MauSacService {

  @Autowired
  private MauSacRepository mauSacRepository;

  public List<MauSac> getAll() {
    return mauSacRepository.findAll();
  }
}
