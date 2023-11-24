package phuongnguyen.btl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import phuongnguyen.btl.entity.DienThoai;
import phuongnguyen.btl.service.DienThoaiService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("trang-chu")
public class TrangChuController {

  @Autowired
  private DienThoaiService dienThoaiService;

  @GetMapping("")
  public String trangChu(Model model,
                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                         @RequestParam(value = "size", defaultValue = "20") Integer size) {
    Page<DienThoai> dienThoaiList = dienThoaiService.getAll(page, size);
    int totalPages = dienThoaiList.getTotalPages();
    if (totalPages > 0) {
      List<Integer> soTrang = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
      model.addAttribute("soTrang", soTrang);
    }
    model.addAttribute("dienThoaiList", dienThoaiList);
    return "trang-chu";
  }

  @GetMapping("/403")
  public String error403() {
    return "error-403";
  }

}