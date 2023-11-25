package phuongnguyen.btl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import phuongnguyen.btl.entity.*;
import phuongnguyen.btl.service.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin/quan-ly-dien-thoai")
public class AdminDienThoaiController {

  @Autowired
  private ObjectMapper mapper;

  @Autowired
  private DienThoaiService dienThoaiService;

  @Autowired
  private HangService hangService;

  @Autowired
  private MaGGService maGGService;

  @Autowired
  private MauSacService mauSacService;

  @Autowired
  private DungLuongService dungLuongService;

  @Autowired
  private PhanLoaiDienThoaiService phanLoaiDienThoaiService;

  @GetMapping("")
  public String gdAdminQLDienThoai(
      Model model,
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "size", defaultValue = "10") Integer size) {
    Page<DienThoai> dienThoaiList = dienThoaiService.getAllPhanTrang(page, size);
    int totalPages = dienThoaiList.getTotalPages();
    if (totalPages > 0) {
      List<Integer> soTrang = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
      model.addAttribute("soTrang", soTrang);
    }
    model.addAttribute("dienThoaiList", dienThoaiList);
    return "quan-ly-dien-thoai";
  }

  @GetMapping("/them-dien-thoai")
  @Transactional
  public String gdAdminThemDienThoai(Model model) {

    DienThoai dienThoai = new DienThoai();
    List<Hang> hangList = hangService.getAll();
    ThongSoKT thongSoKT = new ThongSoKT();
    BaoHanh baoHanh = new BaoHanh();
    List<MaGG> maGGList = maGGService.getAll();
    List<MauSac> mauSacList = mauSacService.getAll();
    List<DungLuong> dungLuongList = dungLuongService.getAll();
    PhanLoaiDienThoai phanLoaiDienThoai = new PhanLoaiDienThoai();

    model.addAttribute("dienThoai", dienThoai);
    model.addAttribute("hangList", hangList);
    model.addAttribute("thongSoKT", thongSoKT);
    model.addAttribute("baoHanh", baoHanh);
    model.addAttribute("maGGList", maGGList);
    model.addAttribute("mauSacList", mauSacList);
    model.addAttribute("dungLuongList", dungLuongList);
    model.addAttribute("phanLoaiDienThoai", phanLoaiDienThoai);
    model.addAttribute("selectedMaGGList", new SelectedMaGG());
    model.addAttribute("selectedHang", new SelectedHang());
    model.addAttribute("selectedPhanLoaiDienThoaiList", Collections.singletonList(new SelectedPhanLoaiDienThoai()));

    return "them-dien-thoai";
  }

  @PostMapping("/them-dien-thoai/save")
  public String gdAdminThemDienThoaiSave(@ModelAttribute("dienThoai") DienThoai dienThoai,
                                         @ModelAttribute("thongSoKT") ThongSoKT thongSoKT,
                                         @ModelAttribute("baoHanh") BaoHanh baoHanh,
                                         @ModelAttribute("selectedMaGGList") SelectedMaGG selectedMaGGList,
                                         @ModelAttribute("selectedHang") SelectedHang selectedHang,
                                         @ModelAttribute("selectedPhanLoaiDienThoai") SelectedPhanLoaiDienThoai selectedPhanLoaiDienThoai,
                                         @RequestParam("anhDienThoai") MultipartFile anhDienThoai,
                                         BindingResult bindingResult) throws IOException {

    List<PhanLoaiDienThoai> phanLoaiDienThoaiList = new ArrayList<>();
    List<MauSac> selectedMauSacList = Arrays.asList(selectedPhanLoaiDienThoai.getSelectedMauSacList());
    List<DungLuong> selectedDungLuongList = Arrays.asList(selectedPhanLoaiDienThoai.getSelectedDungLuongList());
    List<Integer> selectedSoLuong = Arrays.asList(selectedPhanLoaiDienThoai.getSelectedSoLuong());
    List<BigDecimal> selectedGiaTien = Arrays.asList(selectedPhanLoaiDienThoai.getSelectedGiaTien());

    for (int i = 0; i < selectedPhanLoaiDienThoai.getSelectedSoLuong().length; i++) {
      PhanLoaiDienThoai phanLoaiDienThoai = new PhanLoaiDienThoai();
      phanLoaiDienThoai.setSoLuong(selectedSoLuong.get(i));
      phanLoaiDienThoai.setGiaTien(selectedGiaTien.get(i));
      phanLoaiDienThoai.setMauSac(selectedMauSacList.get(i));
      phanLoaiDienThoai.setDungLuong(selectedDungLuongList.get(i));
      phanLoaiDienThoaiList.add(phanLoaiDienThoai);
    }

    dienThoai.setHang(selectedHang.getSelectedHang());
    dienThoai.setMaGGList(selectedMaGGList.getSelectedMaGGList());
    dienThoai.setThongSoKT(thongSoKT);
    dienThoai.setBaoHanh(baoHanh);
    dienThoai.setPhanLoaiDienThoaiList(phanLoaiDienThoaiList);

    String fileName = StringUtils.cleanPath(anhDienThoai.getOriginalFilename());
    String uploadDir = "D:\\Mon_Tot_Nghiep\\Phone-Store\\src\\main\\resources\\static\\images\\";
    dienThoai.setAnh("\\images\\" + fileName);

    Path uploadPath = Paths.get(uploadDir);
    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }

    InputStream inputStream = anhDienThoai.getInputStream();
    Path filePath = uploadPath.resolve(fileName);
    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

    DienThoai result1 = dienThoaiService.themMoiHoacCapNhat(dienThoai);

    for (PhanLoaiDienThoai phanLoaiDienThoai : phanLoaiDienThoaiList) {
      phanLoaiDienThoai.setDienThoai(dienThoai);
    }
    List<PhanLoaiDienThoai> result2 = phanLoaiDienThoaiService.themMoiHoacCapNhat(phanLoaiDienThoaiList);

    if (result1 == null || result2 == null) {
      bindingResult.rejectValue("them_moi_dt_error", "them_moi_dt_error", "Không thể thêm mới điện thoại!");
    }

    if (bindingResult.hasErrors()) {
      return "/admin/quan-ly-dien-thoai";
    }

    return "redirect:/admin/quan-ly-dien-thoai";
  }

  @GetMapping("/sua-dien-thoai/{id}")
  public String gdAdminSuaDienThoai(@PathVariable("id") String id, Model model) {

    DienThoai dienThoai = dienThoaiService.findById(Integer.parseInt(id));

    List<Hang> hangList = hangService.getAll();
    List<MaGG> maGGList = maGGService.getAll();
    List<MauSac> mauSacList = mauSacService.getAll();
    List<DungLuong> dungLuongList = dungLuongService.getAll();

    SelectedMaGG selectedMaGG = new SelectedMaGG(dienThoai.getMaGGList());
    SelectedHang selectedHang = new SelectedHang(dienThoai.getHang(), new Hang[]{});
    List<SelectedPhanLoaiDienThoai> selectedPhanLoaiDienThoaiList = new ArrayList<>();

    List<PhanLoaiDienThoai> phanLoaiDienThoaiList = phanLoaiDienThoaiService.getByDienThoaiId(dienThoai.getId());
    for (PhanLoaiDienThoai phanLoaiDienThoai : phanLoaiDienThoaiList) {
      SelectedPhanLoaiDienThoai selectedPhanLoaiDienThoai = new SelectedPhanLoaiDienThoai();
      selectedPhanLoaiDienThoai.setSelectedIdList(new Integer[]{phanLoaiDienThoai.getId()});
      selectedPhanLoaiDienThoai.setSelectedSoLuong(new Integer[]{phanLoaiDienThoai.getSoLuong()});
      selectedPhanLoaiDienThoai.setSelectedGiaTien(new BigDecimal[]{phanLoaiDienThoai.getGiaTien()});
      selectedPhanLoaiDienThoai.setSelectedMauSacList(new MauSac[]{phanLoaiDienThoai.getMauSac()});
      selectedPhanLoaiDienThoai.setSelectedDungLuongList(new DungLuong[]{phanLoaiDienThoai.getDungLuong()});
      selectedPhanLoaiDienThoaiList.add(selectedPhanLoaiDienThoai);
    }

    model.addAttribute("dienThoai", dienThoai);
    model.addAttribute("selectedHang", selectedHang);
    model.addAttribute("hangList", hangList);
    model.addAttribute("thongSoKT", dienThoai.getThongSoKT());
    model.addAttribute("baoHanh", dienThoai.getBaoHanh());
    model.addAttribute("selectedMaGGList", selectedMaGG);
    model.addAttribute("maGGList", maGGList);
    model.addAttribute("mauSacList", mauSacList);
    model.addAttribute("dungLuongList", dungLuongList);
    model.addAttribute("selectedPhanLoaiDienThoaiList", selectedPhanLoaiDienThoaiList);

    return "sua-dien-thoai";
  }

  @PostMapping("/sua-dien-thoai/save")
  public String gdAdminSuaDienThoaiSave(@ModelAttribute("dienThoai") DienThoai dienThoai,
                                        @ModelAttribute("thongSoKT") ThongSoKT thongSoKT,
                                        @ModelAttribute("baoHanh") BaoHanh baoHanh,
                                        @ModelAttribute("selectedMaGGList") SelectedMaGG selectedMaGGList,
                                        @ModelAttribute("selectedHang") SelectedHang selectedHang,
                                        @ModelAttribute("selectedPhanLoaiDienThoai") SelectedPhanLoaiDienThoai selectedPhanLoaiDienThoai,
                                        @RequestParam("anhDienThoai") MultipartFile anhDienThoai,
                                        BindingResult bindingResult) throws IOException {

    List<PhanLoaiDienThoai> phanLoaiDienThoaiList = new ArrayList<>();
    List<Integer> selectedIdList = Arrays.asList(selectedPhanLoaiDienThoai.getSelectedIdList());
    List<MauSac> selectedMauSacList = Arrays.asList(selectedPhanLoaiDienThoai.getSelectedMauSacList());
    List<DungLuong> selectedDungLuongList = Arrays.asList(selectedPhanLoaiDienThoai.getSelectedDungLuongList());
    List<Integer> selectedSoLuong = Arrays.asList(selectedPhanLoaiDienThoai.getSelectedSoLuong());
    List<BigDecimal> selectedGiaTien = Arrays.asList(selectedPhanLoaiDienThoai.getSelectedGiaTien());

    for (int i = 0; i < selectedPhanLoaiDienThoai.getSelectedSoLuong().length; i++) {
      PhanLoaiDienThoai phanLoaiDienThoai = new PhanLoaiDienThoai();
      phanLoaiDienThoai.setId(selectedIdList.get(i));
      phanLoaiDienThoai.setSoLuong(selectedSoLuong.get(i));
      phanLoaiDienThoai.setGiaTien(selectedGiaTien.get(i));
      phanLoaiDienThoai.setMauSac(selectedMauSacList.get(i));
      phanLoaiDienThoai.setDungLuong(selectedDungLuongList.get(i));
      phanLoaiDienThoaiList.add(phanLoaiDienThoai);
    }

    dienThoai.setHang(selectedHang.getSelectedHang());
    dienThoai.setMaGGList(selectedMaGGList.getSelectedMaGGList());
    dienThoai.setThongSoKT(thongSoKT);
    dienThoai.setBaoHanh(baoHanh);
    dienThoai.setPhanLoaiDienThoaiList(phanLoaiDienThoaiList);

    String fileName = StringUtils.cleanPath(anhDienThoai.getOriginalFilename());
    if (!fileName.isEmpty()) {
      String uploadDir = "D:\\Mon_Tot_Nghiep\\Phone-Store\\src\\main\\resources\\static\\images\\";
      dienThoai.setAnh("\\images\\" + fileName);

      Path uploadPath = Paths.get(uploadDir);
      if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath);
      }

      InputStream inputStream = anhDienThoai.getInputStream();
      Path filePath = uploadPath.resolve(fileName);
      Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
    } else {
      DienThoai existedDienThoai = dienThoaiService.findById(dienThoai.getId());
      dienThoai.setAnh(existedDienThoai.getAnh());
    }

    DienThoai result1 = dienThoaiService.themMoiHoacCapNhat(dienThoai);

    for (PhanLoaiDienThoai phanLoaiDienThoai : phanLoaiDienThoaiList) {
      phanLoaiDienThoai.setDienThoai(dienThoai);
    }
    List<PhanLoaiDienThoai> result2 = phanLoaiDienThoaiService.themMoiHoacCapNhat(phanLoaiDienThoaiList);

    if (result1 == null || result2 == null) {
      bindingResult.rejectValue("sua_dt_error", "sua_dt_error", "Không thể thêm mới điện thoại!");
    }

    if (bindingResult.hasErrors()) {
      return "/admin/quan-ly-dien-thoai";
    }

    return "redirect:/admin/quan-ly-dien-thoai";
  }
}
