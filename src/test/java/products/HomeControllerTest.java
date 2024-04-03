package products;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import products.repository.IngredientRepository;
import products.repository.OrderRepository;
import products.repository.ProductRepository;
import products.repository.UserRepository;
import products.model.DiscountCodeProps;
import products.model.OrderProps;

@ExtendWith(SpringExtension.class)
@WebMvcTest // (secure=false)
public class HomeControllerTest {

  @Autowired
  private MockMvc mockMvc;
  
  @MockBean
  private IngredientRepository ingredientRepository;

  @MockBean
  private ProductRepository designRepository;

  @MockBean
  private OrderRepository orderRepository;

  @MockBean
  private UserRepository userRepository;
  
  @MockBean
  private PasswordEncoder passwordEncoder;
  
  @MockBean
  private DiscountCodeProps discountProps;

  @MockBean
  private OrderProps orderProps;

  @Test
  public void testHomePage() throws Exception {
    mockMvc.perform(get("/"))
      .andExpect(status().isOk())
      .andExpect(view().name("home"))
      .andExpect(content().string(
          containsString("Добро пожаловать в дымок")));
  }

}
