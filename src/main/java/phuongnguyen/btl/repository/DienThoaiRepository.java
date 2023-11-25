package phuongnguyen.btl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import phuongnguyen.btl.entity.DienThoai;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface DienThoaiRepository extends JpaRepository<DienThoai, Integer> {

  @Query(value = "select distinct tdt.* from tbl_dien_thoai tdt " +
      "join tbl_phan_loai_dien_thoai tpldt on " +
      "tdt.id = tpldt.dien_thoai_id where  " +
      "(coalesce(:hangIdList, null) is null or tdt.hang_id in :hangIdList) and " +
      "(coalesce(:mauSacIdList, null) is null or tpldt.mau_sac_id in :mauSacIdList) and " +
      "(coalesce(:dungLuongIdList, null) is null or tpldt.dung_luong_id in :dungLuongIdList) and " +
      "(:min is null or tpldt.gia_tien >= :min) and " +
      "(:max is null or tpldt.gia_tien <= :max)", nativeQuery = true)
  List<DienThoai> timKiem(List<Integer> hangIdList, List<Integer> mauSacIdList, List<Integer> dungLuongIdList, BigDecimal min, BigDecimal max);

}
