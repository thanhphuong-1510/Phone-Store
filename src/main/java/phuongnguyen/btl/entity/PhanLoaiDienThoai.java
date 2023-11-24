package phuongnguyen.btl.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tbl_phan_loai_dien_thoai")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhanLoaiDienThoai {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "gia_tien")
  private Double giaTien;

  @Column(name = "so_luong")
  private Integer soLuong;

  @ManyToOne
  @JoinColumn(name = "dien_thoai_id")
  private DienThoai dienThoai;

  @ManyToOne
  @JoinColumn(name = "mau_sac_id")
  private MauSac mauSac;

  @ManyToOne
  @JoinColumn(name = "dung_luong_id")
  private DungLuong dungLuong;

}
