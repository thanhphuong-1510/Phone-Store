package phuongnguyen.btl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import phuongnguyen.btl.entity.*;
import phuongnguyen.btl.service.DienThoaiService;
import phuongnguyen.btl.service.DungLuongService;
import phuongnguyen.btl.service.HangService;
import phuongnguyen.btl.service.MauSacService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("trang-chu")
public class TrangChuController {

  @Autowired
  private DienThoaiService dienThoaiService;

  @Autowired
  private HangService hangService;

  @Autowired
  private MauSacService mauSacService;

  @Autowired
  private DungLuongService dungLuongService;

  @GetMapping("")
  public String trangChu(Model model) {
    List<DienThoai> dienThoaiList = dienThoaiService.getAll();
    List<Hang> hangList = hangService.getAll();
    List<MauSac> mauSacList = mauSacService.getAll();
    List<DungLuong> dungLuongList = dungLuongService.getAll();

    model.addAttribute("dienThoaiList", dienThoaiList);
    model.addAttribute("hangList", hangList);
    model.addAttribute("mauSacList", mauSacList);
    model.addAttribute("dungLuongList", dungLuongList);
    model.addAttribute("selectedHang", new SelectedHang());
    model.addAttribute("selectedPhanLoaiDienThoai", new SelectedPhanLoaiDienThoai());
    model.addAttribute("selectedMoney", new SelectedMoney());

    return "trang-chu";
  }

  @GetMapping("/403")
  public String error403() {
    return "error-403";
  }

}