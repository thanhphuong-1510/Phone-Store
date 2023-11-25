package phuongnguyen.btl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_dien_thoai")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DienThoai {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String ten;

  private String moTaDienThoai;

  private String anh;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "thong_so_kt_id")
  private ThongSoKT thongSoKT;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "bao_hanh_id")
  private BaoHanh baoHanh;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "tbl_dien_thoai_ma_gg",
      joinColumns = @JoinColumn(name = "dien_thoai_id"),
      inverseJoinColumns = @JoinColumn(name = "ma_gg_id"))
  private List<MaGG> maGGList = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "hang_id")
  private Hang hang;

  @OneToMany(mappedBy = "dienThoai")
  private List<PhanLoaiDienThoai> phanLoaiDienThoaiList;

}
