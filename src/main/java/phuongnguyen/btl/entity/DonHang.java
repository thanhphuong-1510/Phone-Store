package phuongnguyen.btl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
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

  @Column(name = "so_luong")
  private Integer soLuong;

  @Column(name = "tong_tien")
  private BigDecimal tongTien;

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

  @ManyToOne
  @JoinColumn(name = "phan_loai_id")
  private PhanLoaiDienThoai phanLoaiDienThoai;
}
