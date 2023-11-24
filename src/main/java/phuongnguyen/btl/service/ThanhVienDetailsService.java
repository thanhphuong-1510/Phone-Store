package phuongnguyen.btl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import phuongnguyen.btl.entity.ThanhVien;

import java.util.ArrayList;
import java.util.List;

@Service
public class ThanhVienDetailsService implements UserDetailsService {

  @Autowired
  private ThanhVienService thanhVienService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    ThanhVien thanhVien = thanhVienService.dangNhap(username);
    if (thanhVien != null) {
      List<SimpleGrantedAuthority> simpleGrantedAuthorityList = new ArrayList<>();
      if (thanhVien.getViTri() == 0) {
        simpleGrantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
      } else {
        simpleGrantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
      }
      return new User(thanhVien.getUsername(), thanhVien.getPassword(), simpleGrantedAuthorityList);
    }
    return null;
  }
}
