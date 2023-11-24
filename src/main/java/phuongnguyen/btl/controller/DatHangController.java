package phuongnguyen.btl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import phuongnguyen.btl.entity.PhanLoaiDienThoai;

@Controller
@RequestMapping("/dat-hang")
public class DatHangController {

  @PostMapping("")
  public String dienThoaiDatHang(@ModelAttribute("dienThoaiId") Integer dienThoaiId,
                                 @ModelAttribute("selectedDienThoai") PhanLoaiDienThoai phanLoaiDienThoai,
                                 RedirectAttributes redirectAttrs,
                                 BindingResult bindingResult,
                                 Model model) {

    if (phanLoaiDienThoai.getId() == null) {
      redirectAttrs.addFlashAttribute("pldt_error", "Vui lòng chọn loại điện thoại muốn đặt");
      return "redirect:/dien-thoai/chi-tiet/" + dienThoaiId;
    }

    return "dat-hang-dien-thoai";
  }

}
