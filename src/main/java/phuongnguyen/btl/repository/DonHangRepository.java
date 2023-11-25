package phuongnguyen.btl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import phuongnguyen.btl.entity.DonHang;

import java.util.List;

@Repository
public interface DonHangRepository extends JpaRepository<DonHang, Integer> {

  @Query(value = "select * from tbl_don_hang tdh where tdh.khach_hang_id = :khachHangId", nativeQuery = true)
  List<DonHang> timDonHang(Integer khachHangId);

  @Query(value = "select * from tbl_don_hang tdh where (:trangThai = -1 or tdh.trang_thai = :trangThai)", nativeQuery = true)
  List<DonHang> timDonHangTheoTrangThai(Integer trangThai);

}
