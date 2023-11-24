package phuongnguyen.btl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phuongnguyen.btl.entity.DungLuong;

@Repository
public interface DungLuongRepository extends JpaRepository<DungLuong, Integer> {
}
