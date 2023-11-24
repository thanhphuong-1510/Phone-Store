package phuongnguyen.btl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbl_thanh_vien")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThanhVien {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "username", nullable = false)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "ho_ten")
  private String hoTen;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "sdt")
  private String sdt;

  @Column(name = "vi_tri")
  private Integer viTri = 0;
}
