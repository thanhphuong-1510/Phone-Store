package phuongnguyen.btl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import phuongnguyen.btl.entity.DienThoai;
import phuongnguyen.btl.repository.DienThoaiRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class DienThoaiService {

  @Autowired
  private DienThoaiRepository dienThoaiRepository;

  public Page<DienThoai> getAllPhanTrang(Integer page, Integer size) {
    if (page <= 0) {
      page = 1;
    }
    Pageable pageable = PageRequest.of(page - 1, size);
    return dienThoaiRepository.findAll(pageable);
  }

  public List<DienThoai> getAll() {
    return dienThoaiRepository.findAll();
  }

  public DienThoai findById(Integer id) {
    Optional<DienThoai> optionalDienThoai = dienThoaiRepository.findById(id);
    return optionalDienThoai.orElse(null);
  }

  public DienThoai themMoiHoacCapNhat(DienThoai dienThoai) {
    return dienThoaiRepository.save(dienThoai);
  }

  public List<DienThoai> timKiem(List<Integer> hangIdList, List<Integer> mauSacIdList, List<Integer> dungLuongIdList, BigDecimal min, BigDecimal max) {
    return dienThoaiRepository.timKiem(hangIdList, mauSacIdList, dungLuongIdList, min, max);
  }
}
