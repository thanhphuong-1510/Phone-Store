package phuongnguyen.btl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import phuongnguyen.btl.entity.PhanLoaiDienThoai;

import java.util.List;

@Repository
public interface PhanLoaiDienThoaiRepository extends JpaRepository<PhanLoaiDienThoai, Integer> {


  @Query(value = "select * from tbl_phan_loai_dien_thoai tpldt where tpldt.dien_thoai_id = :dienThoaiId", nativeQuery = true)
  List<PhanLoaiDienThoai> getByDienThoaiId(Integer dienThoaiId);
}
