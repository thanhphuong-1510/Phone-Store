package phuongnguyen.btl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectedDonHang {

  private Integer idDonHang;
  private String noiNhan;
  private String ghiChu;
  private Integer soLuong;
  private BigDecimal tongTien;

}
