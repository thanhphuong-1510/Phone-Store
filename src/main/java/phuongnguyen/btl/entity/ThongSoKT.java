package phuongnguyen.btl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbl_thong_so_kt")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThongSoKT {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "kich_thuoc")
  private String kichThuoc;

  @Column(name = "do_phan_giai")
  private String doPhanGiai;

  @Column(name = "camera")
  private String camera;

  @Column(name = "chip")
  private String chip;

  @Column(name = "the_sim")
  private String theSim;

  @Column(name = "he_dieu_hanh")
  private String heDieuHanh;
}
