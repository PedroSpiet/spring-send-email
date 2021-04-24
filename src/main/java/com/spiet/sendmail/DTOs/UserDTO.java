package com.spiet.sendmail.DTOs;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 979190837442684819L;

    private String name;

    private String email;
}
