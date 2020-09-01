package cf.scenecho.dinner.account.controller;

import cf.scenecho.dinner.account.service.SignUpForm;

public class TestAccount {
    public static final String USERNAME = "username";
    public static final String EMAIL = "user@email.com";
    public static final String PASSWORD = "password";

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
