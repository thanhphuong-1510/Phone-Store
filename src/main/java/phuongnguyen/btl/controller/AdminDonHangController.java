package phuongnguyen.btl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import phuongnguyen.btl.entity.DonHang;
import phuongnguyen.btl.entity.PhanLoaiDienThoai;
import phuongnguyen.btl.entity.ThanhVien;
import phuongnguyen.btl.service.DonHangService;
import phuongnguyen.btl.service.PhanLoaiDienThoaiService;
import phuongnguyen.btl.service.ThanhVienService;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin/quan-ly-don-hang")
public class AdminDonHangController {

  @Autowired
  private DonHangService donHangService;

  @Autowired
  private PhanLoaiDienThoaiService phanLoaiDienThoaiService;

  @Autowired
  private ThanhVienService thanhVienService;

  @GetMapping("")
  public String gdAdminDienThoaiDonHang(Model model) {
    List<DonHang> donHangList = donHangService.getAll();

    model.addAttribute("donHangList", donHangList);
    return "quan-ly-don-hang";
  }

  @PostMapping("/tim-kiem")
  public String gdAdminDienThoaiDonHangTimKiem(@ModelAttribute("trangThai") String trangThai,
                                               Model model) {
    List<DonHang> donHangList = donHangService.timKiem(Integer.valueOf(trangThai));

    model.addAttribute("donHangList", donHangList);

    return "quan-ly-don-hang";
  }

  @GetMapping("xac-nhan/{donHangId}")
  public String gdAdminDienThoaiDonHangDaNhan(@PathVariable("donHangId") Integer donHangId,
                                              RedirectAttributes redirectAttrs) {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String username = userDetails.getUsername();
    ThanhVien thanhVien = thanhVienService.timThanhVienTheoUsername(username);

    DonHang donHang = donHangService.timDonHangTheoId(donHangId);
    if (donHang == null) {
      redirectAttrs.addFlashAttribute("donHang_error", "Lỗi cập nhật đơn hàng");
      return "redirect:/admin/quan-ly-don-hang";
    }

    donHang.setTrangThai(1);
    donHang.setNhanVien(thanhVien);
    donHangService.luuDonHang(donHang);

    return "redirect:/admin/quan-ly-don-hang";

  }

  @GetMapping("dang-giao/{donHangId}")
  public String gdAdminDienThoaiDonHangDangGiao(@PathVariable("donHangId") Integer donHangId,
                                                RedirectAttributes redirectAttrs) {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String username = userDetails.getUsername();
    ThanhVien thanhVien = thanhVienService.timThanhVienTheoUsername(username);

    DonHang donHang = donHangService.timDonHangTheoId(donHangId);
    if (donHang == null) {
      redirectAttrs.addFlashAttribute("donHang_error", "Lỗi cập nhật đơn hàng");
      return "redirect:/admin/quan-ly-don-hang";
    }

    donHang.setTrangThai(2);
    donHang.setNhanVien(thanhVien);
    donHangService.luuDonHang(donHang);

    return "redirect:/admin/quan-ly-don-hang";

  }

  @GetMapping("da-huy/{donHangId}")
  public String gdAdminDienThoaiDonHangDaHuy(@PathVariable("donHangId") Integer donHangId,
                                             RedirectAttributes redirectAttrs) {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String username = userDetails.getUsername();
    ThanhVien thanhVien = thanhVienService.timThanhVienTheoUsername(username);

    DonHang donHang = donHangService.timDonHangTheoId(donHangId);
    if (donHang == null) {
      redirectAttrs.addFlashAttribute("donHang_error", "Lỗi cập nhật đơn hàng");
      return "redirect:/admin/quan-ly-don-hang";
    }

    donHang.setTrangThai(4);
    donHang.setNhanVien(thanhVien);
    donHangService.luuDonHang(donHang);


    PhanLoaiDienThoai phanLoaiDienThoai = phanLoaiDienThoaiService.getById(donHang.getPhanLoaiDienThoai().getId());
    phanLoaiDienThoai.setSoLuong(phanLoaiDienThoai.getSoLuong() + donHang.getSoLuong());
    phanLoaiDienThoaiService.themMoiHoacCapNhat(Collections.singletonList(phanLoaiDienThoai));

    return "redirect:/admin/quan-ly-don-hang";

  }

}
