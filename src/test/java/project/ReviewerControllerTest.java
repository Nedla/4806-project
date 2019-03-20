package project;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReviewerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ReviewerController reviewerController;
    @Autowired
    private ArticleRepository articleRepository;

    @Test
    public void testReviewerViewGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/reviewerView"))
                .andExpect(status().isOk())
                .andExpect(view().name("reviewerView"));
    }
}
