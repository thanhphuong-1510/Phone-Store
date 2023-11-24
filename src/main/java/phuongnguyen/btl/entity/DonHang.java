package phuongnguyen.btl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tbl_don_hang")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonHang {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "noi_nhan")
  private String noiNhan;

  @Column(name = "tong_tien")
  private Double tongTien;

  @Column(name = "trang_thai")
  private Integer trangThai;

  @Column(name = "ghi_chu")
  private String ghiChu;

  @ManyToOne
  @JoinColumn(name = "khach_hang_id")
  private ThanhVien khachHang;

  @ManyToOne
  @JoinColumn(name = "nhan_vien_id")
  private ThanhVien nhanVien;

  @OneToMany(mappedBy = "donHang")
  private List<ChiTietDonHang> chiTietDonHangList;
}
