package phuongnguyen.btl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import phuongnguyen.btl.entity.*;
import phuongnguyen.btl.service.DienThoaiService;
import phuongnguyen.btl.service.DungLuongService;
import phuongnguyen.btl.service.HangService;
import phuongnguyen.btl.service.MauSacService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/dien-thoai")
public class DienThoaiController {

  @Autowired
  private DienThoaiService dienThoaiService;

  @Autowired
  private HangService hangService;

  @Autowired
  private MauSacService mauSacService;

  @Autowired
  private DungLuongService dungLuongService;

  @GetMapping("/chi-tiet/{id}")
  public String gdDienThoaiChiTiet(@PathVariable("id") Integer id, Model model) {

    DienThoai dienThoai = dienThoaiService.findById(id);

    model.addAttribute("dienThoai", dienThoai);
    model.addAttribute("selectedDienThoai", new PhanLoaiDienThoai());
    return "chi-tiet-dien-thoai";
  }

  @PostMapping("/tim-kiem")
  public String gdDienThoaiTimKiem(@ModelAttribute("selectedHang") SelectedHang selectedHang,
                                   @ModelAttribute("selectedPhanLoaiDienThoai") SelectedPhanLoaiDienThoai selectedPhanLoaiDienThoai,
                                   @ModelAttribute("selectedMoney") SelectedMoney selectedMoney,
                                   Model model) {
    List<Integer> hangIdList = new ArrayList<>();
    List<Integer> mauSacIdList = new ArrayList<>();
    List<Integer> dungLuongIdList = new ArrayList<>();
    BigDecimal min = null;
    BigDecimal max = null;

    if (selectedHang != null && selectedHang.getSelectedHangList() != null) {
      List<Hang> selectedHangList = Arrays.asList(selectedHang.getSelectedHangList());
      if (!selectedHangList.isEmpty()) {
        hangIdList = selectedHangList.stream().map(Hang::getId).collect(Collectors.toList());
      }
    }

    if (selectedPhanLoaiDienThoai != null && selectedPhanLoaiDienThoai.getSelectedMauSacList() != null) {
      List<MauSac> selectedMauSacList = Arrays.asList(selectedPhanLoaiDienThoai.getSelectedMauSacList());
      if (!selectedMauSacList.isEmpty()) {
        mauSacIdList = selectedMauSacList.stream().map(MauSac::getId).collect(Collectors.toList());
      }
    }

    if (selectedPhanLoaiDienThoai != null && selectedPhanLoaiDienThoai.getSelectedDungLuongList() != null) {
      List<DungLuong> selectedDungLuongList = Arrays.asList(selectedPhanLoaiDienThoai.getSelectedDungLuongList());
      if (!selectedDungLuongList.isEmpty()) {
        dungLuongIdList = selectedDungLuongList.stream().map(DungLuong::getId).collect(Collectors.toList());
      }
    }

    if (selectedMoney != null) {
      min = selectedMoney.getMin();
      max = selectedMoney.getMax();
    }

    List<DienThoai> dienThoaiList = dienThoaiService.timKiem(hangIdList, mauSacIdList, dungLuongIdList, min, max);

    List<Hang> hangList = hangService.getAll();
    List<MauSac> mauSacList = mauSacService.getAll();
    List<DungLuong> dungLuongList = dungLuongService.getAll();


    model.addAttribute("dienThoaiList", dienThoaiList);
    model.addAttribute("hangList", hangList);
    model.addAttribute("mauSacList", mauSacList);
    model.addAttribute("dungLuongList", dungLuongList);
    model.addAttribute("selectedHang", selectedHang);
    model.addAttribute("selectedPhanLoaiDienThoai", selectedPhanLoaiDienThoai);
    model.addAttribute("selectedMoney", selectedMoney);

    return "tim-kiem-dien-thoai";
  }

}
