package phuongnguyen.btl.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbl_ma_gg")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaGG implements Serializable {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "code")
  private String code;

  @Column(name = "mo_ta")
  private String moTa;

  @Column(name = "status")
  private Integer status;

//  @Override
//  public String toString() {
//    try {
//      return new ObjectMapper().writeValueAsString(this);
//    } catch (JsonProcessingException e) {
//      return null;
//    }
//  }
}
