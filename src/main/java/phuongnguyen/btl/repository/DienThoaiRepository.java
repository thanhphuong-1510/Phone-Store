package phuongnguyen.btl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phuongnguyen.btl.entity.DienThoai;

@Repository
public interface DienThoaiRepository extends JpaRepository<DienThoai, Integer> {
}
