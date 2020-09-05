package cf.scenecho.dinner.account;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter @Setter
public class SignupForm {

    @NotBlank @Length(min = 2, max = 10)
    @Pattern(regexp = "^[가-힣a-zA-Z0-9\\-_.]{2,10}$")
    private String username;

    @NotBlank @Email
    private String email;

    @NotBlank @Length(min = 8, max = 50)
    private String password;
}
