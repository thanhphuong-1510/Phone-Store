package phuongnguyen.btl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tbl_hoa_don")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HoaDon {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;


  @Column(name = "ngay_thanh_toan")
  private LocalDate ngayThanhToan;

  @Column(name = "tong_tien")
  private Double tongTien;

  @Column(name = "ghi_chu")
  private String ghiChu;

  @OneToOne
  private DonHang donHang;

  @ManyToOne
  @JoinColumn(name = "khach_hang_id")
  private ThanhVien khachHang;

  @ManyToOne
  @JoinColumn(name = "nhan_vien_id")
  private ThanhVien nhanVien;
}
