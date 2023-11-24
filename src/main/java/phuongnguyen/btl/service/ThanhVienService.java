package phuongnguyen.btl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import phuongnguyen.btl.entity.ThanhVien;
import phuongnguyen.btl.repository.ThanhVienRepository;

@Service
public class ThanhVienService {

  @Autowired
  private ThanhVienRepository thanhVienRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public ThanhVien dangKy(ThanhVien thanhVien) {
    String encodedPassword = passwordEncoder.encode(thanhVien.getPassword());
    thanhVien.setPassword(encodedPassword);
    return thanhVienRepository.save(thanhVien);
  }

  public ThanhVien dangNhap(String username) {
    return thanhVienRepository.findByUsernameOrEmail(username);
  }

  public ThanhVien timThanhVienTheoUsername(String username) {
    return thanhVienRepository.findByUsername(username);
  }

  public ThanhVien timThanhVienTheoEmail(String email) {
    return thanhVienRepository.findByEmail(email);
  }

}
