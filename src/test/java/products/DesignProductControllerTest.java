package products;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import products.model.Ingredient;
import products.model.Ingredient.Type;
import products.repository.IngredientRepository;
import products.repository.OrderRepository;
import products.repository.ProductRepository;
import products.repository.UserRepository;
import products.model.Product;
import products.model.User;
import products.controller.DesignProductController;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DesignProductController.class)
public class DesignProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  private List<Ingredient> ingredients;

  private Product design;

  @MockBean
  private IngredientRepository ingredientRepository;

  @MockBean
  private ProductRepository designRepository;

  @MockBean
  private OrderRepository orderRepository;

  @MockBean
  private UserRepository userRepository;

  @BeforeEach
  public void setup() {
    ingredients = Arrays.asList(
    new Ingredient("ZPLK", "Ziplock", Type.PACK),
    new Ingredient("DBLC", "Double cup", Type.PACK),
    new Ingredient("GRBF", "Афганка", Type.MJ),
    new Ingredient("GOGL", "Gorilla Glue", Type.MJ),
    new Ingredient("LEAN", "Lean", Type.SYRUP),
    new Ingredient("PPDR", "Purlple Drank", Type.SYRUP),
    new Ingredient("MDMA", "MDMA", Type.MOLLY),
    new Ingredient("MOLY", "Molly", Type.MOLLY),
    new Ingredient("XANY", "Xanny", Type.PERK),
    new Ingredient("PERC", "Percocet", Type.PERK)
    );

    when(ingredientRepository.findAll())
        .thenReturn(ingredients);

    when(ingredientRepository.findById("ZPLK")).thenReturn(Optional.of(new Ingredient("ZPLK", "Ziplock", Type.PACK)));
    when(ingredientRepository.findById("GRBF")).thenReturn(Optional.of(new Ingredient("GRBF", "Афганка", Type.MJ)));
    when(ingredientRepository.findById("PPDR")).thenReturn(Optional.of(new Ingredient("PPDR", "Purlple Drank", Type.SYRUP)));

    design = new Product();
    design.setName("Test Product");

    design.setIngredients(Arrays.asList(
        new Ingredient("ZPLK", "Ziplock", Type.PACK),
        new Ingredient("GRBF", "Афганка", Type.MJ),
        new Ingredient("PPDR", "Purlple Drank", Type.SYRUP)
    	));

    when(userRepository.findByUsername("testuser"))
    		.thenReturn(new User("testuser", "testpass", "Test User", "123 Street", "Someville", "CO", "12345", "123-123-1234"));
  }

  @Test
  @WithMockUser(username="testuser", password="testpass")
  public void testShowDesignForm() throws Exception {
	mockMvc.perform(get("/design"))
        .andExpect(status().isOk())
        .andExpect(view().name("design"))
        .andExpect(model().attribute("pack", ingredients.subList(0, 2)))
        .andExpect(model().attribute("mj", ingredients.subList(2, 4)))
        .andExpect(model().attribute("syrup", ingredients.subList(4, 6)))
        .andExpect(model().attribute("molly", ingredients.subList(6, 8)))
        .andExpect(model().attribute("perk", ingredients.subList(8, 10)));
  }

  @Test
  @WithMockUser(username="testuser", password="testpass", authorities="ROLE_USER")
  public void processProduct() throws Exception {
    when(designRepository.save(design))
        .thenReturn(design);

    when(userRepository.findByUsername("testuser"))
	.thenReturn(new User("testuser", "testpass", "Test User", "123 Street", "Someville", "CO", "12345", "123-123-1234"));

    mockMvc.perform(post("/design").with(csrf())
        .content("name=Test+Product&ingredients=ZPLK,GRBF,PPDR")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().is3xxRedirection())
        .andExpect(header().stringValues("Location", "/orders/current"));
  }

}
