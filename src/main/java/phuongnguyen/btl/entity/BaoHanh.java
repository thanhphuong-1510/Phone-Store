package phuongnguyen.btl.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tbl_bao_hanh")
@Data
public class BaoHanh {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @Column(name = "thoi_gian")
  private LocalDate thoiGian;

  @Column(name = "mo_ta")
  private String moTa;
}
