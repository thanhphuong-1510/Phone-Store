package phuongnguyen.btl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import phuongnguyen.btl.entity.*;
import phuongnguyen.btl.service.DienThoaiService;
import phuongnguyen.btl.service.DonHangService;
import phuongnguyen.btl.service.PhanLoaiDienThoaiService;
import phuongnguyen.btl.service.ThanhVienService;

import java.math.BigDecimal;
import java.util.Collections;

@Controller
@RequestMapping("/dat-hang")
public class DatHangController {

  @Autowired
  private DienThoaiService dienThoaiService;

  @Autowired
  private ThanhVienService thanhVienService;

  @Autowired
  private PhanLoaiDienThoaiService phanLoaiDienThoaiService;

  @Autowired
  private DonHangService donHangService;

  @PostMapping("")
  public String gdDienThoaiDatHang(@ModelAttribute("dienThoaiId") Integer dienThoaiId,
                                   @ModelAttribute("selectedDienThoai") PhanLoaiDienThoai selectedPhanLoaiDienThoai,
                                   RedirectAttributes redirectAttrs,
                                   BindingResult bindingResult,
                                   Model model) {

    if (selectedPhanLoaiDienThoai.getId() == null) {
      redirectAttrs.addFlashAttribute("pldt_loai_error", "Vui lòng chọn loại điện thoại muốn đặt");
      model.addAttribute("dienThoaiId", dienThoaiId);
      return "redirect:/dien-thoai/chi-tiet/" + dienThoaiId;
    }

    if (selectedPhanLoaiDienThoai.getSoLuong() == null || selectedPhanLoaiDienThoai.getSoLuong() <= 0) {
      redirectAttrs.addFlashAttribute("pldt_soLuong_error", "Vui lòng chọn mua ít nhất 1 cái");
      return "redirect:/dien-thoai/chi-tiet/" + dienThoaiId;
    }

    PhanLoaiDienThoai phanLoaiDienThoai = phanLoaiDienThoaiService.getById(selectedPhanLoaiDienThoai.getId());
    if (phanLoaiDienThoai.getSoLuong() < selectedPhanLoaiDienThoai.getSoLuong()) {
      redirectAttrs.addFlashAttribute("pldt_soLuong_error", "Hiện tại hàng đã hết, vui lòng chọn loại khác");
      return "redirect:/dien-thoai/chi-tiet/" + dienThoaiId;
    }

    DienThoai dienThoai = dienThoaiService.findById(dienThoaiId);

    SelectedDonHang selectedDonHang = new SelectedDonHang();
    selectedDonHang.setTongTien(phanLoaiDienThoai.getGiaTien().multiply(new BigDecimal(selectedPhanLoaiDienThoai.getSoLuong())));
    selectedDonHang.setSoLuong(selectedPhanLoaiDienThoai.getSoLuong());

    model.addAttribute("dienThoai", dienThoai);
    model.addAttribute("selectedPhanLoaiDienThoai", phanLoaiDienThoai);
    model.addAttribute("selectedDonHang", selectedDonHang);
    model.addAttribute("donHang", new DonHang());
    return "luu-don-hang";
  }

  @PostMapping("/save")
  public String gdDienThoaiLuuDonHang(@ModelAttribute("selectedPhanLoaiDienThoai") PhanLoaiDienThoai selectedPhanLoaiDienThoai,
                                      @ModelAttribute("selectedDonHang") SelectedDonHang selectedDonHang,
                                      RedirectAttributes redirectAttrs,
                                      BindingResult bindingResult,
                                      Model model) {

    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String username = userDetails.getUsername();
    ThanhVien thanhVien = thanhVienService.timThanhVienTheoUsername(username);

    PhanLoaiDienThoai phanLoaiDienThoai = phanLoaiDienThoaiService.getById(selectedPhanLoaiDienThoai.getId());
    phanLoaiDienThoai.setSoLuong(phanLoaiDienThoai.getSoLuong() - selectedPhanLoaiDienThoai.getSoLuong());
    phanLoaiDienThoaiService.themMoiHoacCapNhat(Collections.singletonList(phanLoaiDienThoai));

    DonHang donHang = new DonHang();
    donHang.setNoiNhan(selectedDonHang.getNoiNhan());
    donHang.setSoLuong(selectedDonHang.getSoLuong());
    donHang.setTongTien(selectedDonHang.getTongTien());
    donHang.setGhiChu(selectedDonHang.getGhiChu());
    donHang.setTrangThai(0);
    donHang.setKhachHang(thanhVien);
    donHang.setPhanLoaiDienThoai(phanLoaiDienThoai);

    DonHang result = donHangService.luuDonHang(donHang);

    if (result == null) {
      bindingResult.rejectValue("them_moi_dt_error", "them_moi_dt_error", "Không thể thêm mới điện thoại!");
    }

    return "redirect:/don-hang";
  }

}
