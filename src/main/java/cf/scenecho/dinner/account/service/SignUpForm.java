package cf.scenecho.dinner.account.service;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter @Setter
public class SignUpForm {

    @NotBlank @Min(2) @Max(20)
    private String username;

    @NotBlank @Email
    private String email;

    @NotBlank @Min(8) @Max(50)
    private String password;
}
