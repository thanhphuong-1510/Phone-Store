package phuongnguyen.btl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phuongnguyen.btl.entity.MaGG;

@Repository
public interface MaGGRepository extends JpaRepository<MaGG, Integer> {
}
