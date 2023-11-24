package phuongnguyen.btl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phuongnguyen.btl.entity.Hang;

@Repository
public interface HangRepository extends JpaRepository<Hang, Integer> {
}
