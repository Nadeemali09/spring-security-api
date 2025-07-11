
package com.springSecurity.api.BasicSecurity.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserModal {
    private long id;

   
    private String username;

 
    private String password;


    private String email;

   
    private String role;
}
