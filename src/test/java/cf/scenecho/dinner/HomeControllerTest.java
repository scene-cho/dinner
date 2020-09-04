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

    @Autowired MockMvc mockMvc;

    @Test
    void get_provideLayout() throws Exception {
        String XPATH_HEAD = "html/head/";
        String XPATH_BODY = "html/body/";
        mockMvc.perform(get(HomeController.URL))
                .andExpect(status().isOk())
                .andExpect(view().name(HomeController.VIEW_NAME))
                .andExpect(header().string("Content-Type", "text/html;charset=UTF-8"))

                .andExpect(xpath(XPATH_HEAD + "title").string("Dinner"))
                .andExpect(xpath(XPATH_HEAD + "link").nodeCount(2))
                .andExpect(xpath(XPATH_HEAD + "script").nodeCount(3))

                .andExpect(xpath(XPATH_BODY + "nav").exists())
                .andExpect(xpath(XPATH_BODY + "div/header").exists())
                .andExpect(xpath(XPATH_BODY + "div/section").exists())
                .andExpect(xpath(XPATH_BODY + "div/footer").exists())
        ;
    }

}