package phuongnguyen.btl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectedHang {

  private Hang selectedHang = new Hang();
  private Hang[] selectedHangList;
}
