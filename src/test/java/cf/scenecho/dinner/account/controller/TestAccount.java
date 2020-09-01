package cf.scenecho.dinner.account.controller;

import cf.scenecho.dinner.account.domain.SignUpForm;

public class TestAccount {
    public static final String USERNAME = "username";
    public static final String EMAIL = "user@email.com";
    public static final String PASSWORD = "password";

    public static final String USERNAME_INVALID = "s";
    public static final String EMAIL_INVALID = "not email";
    public static final String PASSWORD_INVALID = "short";

    private TestAccount() {
    }

    public static SignUpForm createSignUpForm() {
        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setUsername(USERNAME);
        signUpForm.setEmail(EMAIL);
        signUpForm.setPassword(PASSWORD);
        return signUpForm;
    }
}
