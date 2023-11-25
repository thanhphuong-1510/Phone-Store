package phuongnguyen.btl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phuongnguyen.btl.entity.DonHang;
import phuongnguyen.btl.repository.DonHangRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DonHangService {

  @Autowired
  private DonHangRepository donHangRepository;

  public List<DonHang> getAll() {
    return donHangRepository.findAll();
  }

  public List<DonHang> timKiem(Integer trangThai) {
    return donHangRepository.timDonHangTheoTrangThai(trangThai);
  }

  public DonHang luuDonHang(DonHang donHang) {
    return donHangRepository.save(donHang);
  }

  public List<DonHang> timDonHang(Integer khachHangId) {
    return donHangRepository.timDonHang(khachHangId);
  }

  public DonHang timDonHangTheoId(Integer donHangId) {
    Optional<DonHang> optionalDonHang = donHangRepository.findById(donHangId);
    return optionalDonHang.orElseGet(null);
  }

}
