package phuongnguyen.btl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import phuongnguyen.btl.entity.ThanhVien;

@Repository
public interface ThanhVienRepository extends JpaRepository<ThanhVien, Integer> {

  @Query(value = "select * from tbl_thanh_vien ttv where "
      + "ttv.username = :username or ttv.email = :username", nativeQuery = true)
  ThanhVien findByUsernameOrEmail(String username);

  @Query(value = "select * from tbl_thanh_vien ttv where ttv.username = :username", nativeQuery = true)
  ThanhVien findByUsername(String username);

  @Query(value = "select * from tbl_thanh_vien ttv where ttv.email = :email", nativeQuery = true)
  ThanhVien findByEmail(String email);
}
