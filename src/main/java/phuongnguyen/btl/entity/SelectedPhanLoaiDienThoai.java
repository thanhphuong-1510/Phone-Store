package phuongnguyen.btl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectedPhanLoaiDienThoai {

  Integer[] selectedIdList;
  MauSac[] selectedMauSacList;
  DungLuong[] selectedDungLuongList;
  Integer[] selectedSoLuong;
  BigDecimal[] selectedGiaTien;
}
