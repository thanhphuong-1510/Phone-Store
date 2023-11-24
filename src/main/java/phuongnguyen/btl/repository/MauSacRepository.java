package phuongnguyen.btl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phuongnguyen.btl.entity.MauSac;

@Repository
public interface MauSacRepository extends JpaRepository<MauSac, Integer> {
}
