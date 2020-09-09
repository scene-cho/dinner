package cf.scenecho.dinner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {
    static final String XPATH_HEAD = "html/head/";
    static final String XPATH_BODY = "html/body/";

    @Autowired MockMvc mockMvc;

    @Test
    void homePage_hasLayout() throws Exception {
        mockMvc.perform(get(HomeController.HOME_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(HomeController.HOME_VIEW))
                .andExpect(model().attributeExists("springProfile"))
                .andExpect(header().string("Content-Type", "text/html;charset=UTF-8"))

                .andExpect(xpath(XPATH_HEAD + "title").string("Dinner"))
                .andExpect(xpath(XPATH_HEAD + "link").nodeCount(3))
                .andExpect(xpath(XPATH_HEAD + "script").nodeCount(3))

                .andExpect(xpath(XPATH_BODY + "nav").exists())
                .andExpect(xpath(XPATH_BODY + "header").exists())
                .andExpect(xpath(XPATH_BODY + "div/section").exists())
                .andExpect(xpath(XPATH_BODY + "footer").exists())
        ;
    }

    @Test
    void aboutPage_hasSignupLink() throws Exception {
        mockMvc.perform(get(HomeController.ABOUT_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(HomeController.ABOUT_VIEW))
                .andExpect(xpath(XPATH_BODY + "div/section/div/div/div/a").string("Signup"));

    }

}