package phuongnguyen.btl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("don-hang")
public class DonHangController {

  @Autowired
  private DonHangService donHangService;

  @Autowired
  private ThanhVienService thanhVienService;

  @Autowired
  private PhanLoaiDienThoaiService phanLoaiDienThoaiService;

  @GetMapping("")
  public String gdDienThoaiDonHang(Model model) {

    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String username = userDetails.getUsername();
    ThanhVien thanhVien = thanhVienService.timThanhVienTheoUsername(username);

    List<DonHang> donHangList = donHangService.timDonHang(thanhVien.getId());

    model.addAttribute("donHangList", donHangList);

    return "don-hang";
  }

  @GetMapping("da-nhan/{donHangId}")
  public String gdDienThoaiDonHangDaNhan(@PathVariable("donHangId") Integer donHangId,
                                         RedirectAttributes redirectAttrs) {
    DonHang donHang = donHangService.timDonHangTheoId(donHangId);
    if (donHang == null) {
      redirectAttrs.addFlashAttribute("donHang_error", "Lỗi cập nhật đơn hàng");
      return "redirect:/don-hang";
    }

    donHang.setTrangThai(3);
    donHangService.luuDonHang(donHang);

    return "redirect:/don-hang";

  }

  @GetMapping("da-huy/{donHangId}")
  public String gdDienThoaiDonHangDaHuy(@PathVariable("donHangId") Integer donHangId,
                                        RedirectAttributes redirectAttrs) {
    DonHang donHang = donHangService.timDonHangTheoId(donHangId);
    if (donHang == null) {
      redirectAttrs.addFlashAttribute("donHang_error", "Lỗi cập nhật đơn hàng");
      return "redirect:/don-hang";
    }

    donHang.setTrangThai(4);
    donHangService.luuDonHang(donHang);

    PhanLoaiDienThoai phanLoaiDienThoai = phanLoaiDienThoaiService.getById(donHang.getPhanLoaiDienThoai().getId());
    phanLoaiDienThoai.setSoLuong(phanLoaiDienThoai.getSoLuong() + donHang.getSoLuong());
    phanLoaiDienThoaiService.themMoiHoacCapNhat(Collections.singletonList(phanLoaiDienThoai));

    return "redirect:/don-hang";

  }

}
