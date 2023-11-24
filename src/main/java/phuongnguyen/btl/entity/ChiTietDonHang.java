package phuongnguyen.btl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbl_chi_tiet_dh")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietDonHang {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "so_luong")
  private Integer soLuong;

  @Column(name = "don_gia")
  private Double donGia;

  @ManyToOne
  @JoinColumn(name = "phan_loai_dt_id")
  private PhanLoaiDienThoai phanLoaiDienThoai;

  @ManyToOne
  @JoinColumn(name = "don_hang_id")
  private DonHang donHang;
}
