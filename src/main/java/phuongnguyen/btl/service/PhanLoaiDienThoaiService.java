package phuongnguyen.btl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phuongnguyen.btl.entity.PhanLoaiDienThoai;
import phuongnguyen.btl.repository.PhanLoaiDienThoaiRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PhanLoaiDienThoaiService {

  @Autowired
  private PhanLoaiDienThoaiRepository phanLoaiDienThoaiRepository;

  public List<PhanLoaiDienThoai> themMoiHoacCapNhat(List<PhanLoaiDienThoai> phanLoaiDienThoaiList) {
    return phanLoaiDienThoaiRepository.saveAll(phanLoaiDienThoaiList);
  }

  public List<PhanLoaiDienThoai> getByDienThoaiId(Integer dienThoaiId) {
    return phanLoaiDienThoaiRepository.getByDienThoaiId(dienThoaiId);
  }

  public PhanLoaiDienThoai getById(Integer id) {
    Optional<PhanLoaiDienThoai> optionalPhanLoaiDienThoai = phanLoaiDienThoaiRepository.findById(id);
    return optionalPhanLoaiDienThoai.orElse(null);
  }
}
