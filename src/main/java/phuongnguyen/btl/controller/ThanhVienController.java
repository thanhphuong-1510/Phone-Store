package phuongnguyen.btl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import phuongnguyen.btl.entity.ThanhVien;
import phuongnguyen.btl.service.ThanhVienService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/thanh-vien")
public class ThanhVienController {

  @Autowired
  private AuthenticationManager authManager;

  @Autowired
  private ThanhVienService thanhVienService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @GetMapping("/dang-nhap")
  public String gdDangNhap(Model model) {
    ThanhVien thanhVien = new ThanhVien();
    model.addAttribute("thanhVien", thanhVien);
    return "dang-nhap";
  }

  @PostMapping("/dang-nhap/save")
  public String gdDangNhapSave(@ModelAttribute("thanhVien") ThanhVien thanhVien, BindingResult bindingResult, Model model, HttpServletRequest request) {
    String username = thanhVien.getUsername();
    String password = thanhVien.getPassword();
    if (username == null || username.isEmpty()) {
      bindingResult.rejectValue("username", "empty_username", "Username/Email không được phép bỏ trống");
    } else if (password == null || password.isEmpty()) {
      bindingResult.rejectValue("password", "empty_password", "Password không được phép bỏ trống");
    }
    if (bindingResult.hasErrors()) {
      model.addAttribute("thanhVien", thanhVien);
      return "dang-nhap";
    }

    ThanhVien result = thanhVienService.dangNhap(username);
    if (result == null) {
      model.addAttribute("thanhVien", thanhVien);
      model.addAttribute("not_found_error", "Không tìm thấy tài khoản");
      return "dang-nhap";
    }

    String encodedPassword = result.getPassword();
    if (!passwordEncoder.matches(password, encodedPassword)) {
      model.addAttribute("thanhVien", thanhVien);
      model.addAttribute("invalid_credentials_error", "Thông tin đăng nhập không chính xác");
      return "dang-nhap";
    }

    List<SimpleGrantedAuthority> simpleGrantedAuthorityList = new ArrayList<>();
    if (result.getViTri() == 0) {
      simpleGrantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
    } else {
      simpleGrantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(username, password, simpleGrantedAuthorityList);
    Authentication auth = authManager.authenticate(authReq);
    SecurityContext securityContext = SecurityContextHolder.getContext();
    securityContext.setAuthentication(auth);

    return "redirect:/trang-chu";
  }


  @GetMapping("/dang-ky")
  public String gdDangKy(Model model) {
    ThanhVien thanhVien = new ThanhVien();
    model.addAttribute("thanhVien", thanhVien);
    model.addAttribute("dang_ky_error", "");
    return "dang-ky";
  }


  @PostMapping("/dang-ky/save")
  public String gdDangKySave(@ModelAttribute("thanhVien") ThanhVien thanhVien, BindingResult bindingResult, Model model, RedirectAttributes redirectAttrs) {
    String username = thanhVien.getUsername();
    String password = thanhVien.getPassword();
    String email = thanhVien.getEmail();

    if (username == null || username.isEmpty()) {
      bindingResult.rejectValue("username", "empty_username", "Username không được phép bỏ trống");
    } else if (password == null || password.isEmpty()) {
      bindingResult.rejectValue("password", "empty_password", "Password không được phép bỏ trống");
    } else if (email == null || email.isEmpty()) {
      bindingResult.rejectValue("email", "empty_email", "Email không được phép bỏ trống");
    }

    ThanhVien tonTaiUsernameThanhVien = thanhVienService.timThanhVienTheoUsername(username);
    ThanhVien tonTaiEmailThanhVien = thanhVienService.timThanhVienTheoEmail(email);
    if (tonTaiUsernameThanhVien != null) {
      bindingResult.rejectValue("username", "ton_tai_username", "Username đã được sử dụng");
    } else if (tonTaiEmailThanhVien != null) {
      bindingResult.rejectValue("email", "ton_tai_email", "Email đã được sử dụng");
    }

    if (bindingResult.hasErrors()) {
      model.addAttribute("thanhVien", thanhVien);
      model.addAttribute("dang_ky_error", "");
      return "dang-ky";
    }

    ThanhVien result = thanhVienService.dangKy(thanhVien);
    if (result == null) {
      bindingResult.rejectValue("dang_ky_error", "dang_ky_error", "Không thể đăng ký tài khoản. Vui lòng liên hệ quản trị viên!");
    }
    if (bindingResult.hasErrors()) {
      model.addAttribute("thanhVien", thanhVien);
      model.addAttribute("dang_ky_error", "");
      return "dang-ky";
    }

    redirectAttrs.addFlashAttribute("dang_ky_success", "Đăng ký tài khoản thành công, vui lòng đăng nhập ở đây!");

    return "redirect:/thanh-vien/dang-nhap";
  }


  @GetMapping("/dang-xuat")
  public String gdDangXuat(HttpServletRequest request) {
    if (request != null) {
      HttpSession session = request.getSession(false);
      if (session != null) {
        session.invalidate();
      }
    }
    SecurityContext context = SecurityContextHolder.getContext();
    SecurityContextHolder.clearContext();
    context.setAuthentication(null);
    return "redirect:/trang-chu";
  }
}
