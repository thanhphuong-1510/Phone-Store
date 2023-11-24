package phuongnguyen.btl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import phuongnguyen.btl.entity.DienThoai;
import phuongnguyen.btl.entity.PhanLoaiDienThoai;
import phuongnguyen.btl.service.DienThoaiService;

@Controller
@RequestMapping("/dien-thoai")
public class DienThoaiController {

  @Autowired
  private DienThoaiService dienThoaiService;

  @GetMapping("/chi-tiet/{id}")
  public String dienThoaiChiTiet(@PathVariable("id") Integer id, Model model) {

    DienThoai dienThoai = dienThoaiService.findById(id);

    model.addAttribute("dienThoai", dienThoai);
    model.addAttribute("selectedDienThoai", new PhanLoaiDienThoai());
    return "chi-tiet-dien-thoai";
  }

}
