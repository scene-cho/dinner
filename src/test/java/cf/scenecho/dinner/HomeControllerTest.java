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
    void When_visit_Should_provideLayout() throws Exception {
        mockMvc.perform(get(HomeController.URL))
                .andExpect(status().isOk())
                .andExpect(view().name(HomeController.VIEW_NAME))
                .andExpect(header().string("Content-Type", "text/html;charset=UTF-8"))
                .andExpect(xpath("html/head/title").string("Dinner"))
                .andExpect(xpath("html/head/link").nodeCount(1))
                .andExpect(xpath("html/head/script").nodeCount(2))
                .andExpect(xpath("html/body/nav").exists())
                .andExpect(xpath("html/body/footer").exists())
        ;
    }

}