package products.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import products.Ingredient;
import products.Ingredient.Type;
import products.Product;
import products.ProductOrder;
import products.User;
import products.data.IngredientRepository;
import products.data.ProductRepository;
import products.data.UserRepository;

@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignProductController {

  private final IngredientRepository ingredientRepo;

  private ProductRepository productRepo;

  private UserRepository userRepo;

  @Autowired
  public DesignProductController(
        IngredientRepository ingredientRepo,
        ProductRepository productRepo,
        UserRepository userRepo) {
    this.ingredientRepo = ingredientRepo;
    this.productRepo = productRepo;
    this.userRepo = userRepo;
  }

  @ModelAttribute
  public void addIngredientsToModel(Model model) {
    List<Ingredient> ingredients = new ArrayList<>();
    ingredientRepo.findAll().forEach(i -> ingredients.add(i));

    Type[] types = Ingredient.Type.values();
    for (Type type : types) {
      model.addAttribute(type.toString().toLowerCase(),
          filterByType(ingredients, type));
    }
  }

  @ModelAttribute(name = "order")
  public ProductOrder order() {
    return new ProductOrder();
  }

  @ModelAttribute(name = "product")
  public Product product() {
    return new Product();
  }

  @ModelAttribute(name = "user")
  public User user(Principal principal) {
	    String username = principal.getName();
	    User user = userRepo.findByUsername(username);
	    return user;
  }


  @GetMapping
  public String showDesignForm() {
    return "design";
  }

  @PostMapping
  public String processTaco(
      @Valid Product product, Errors errors,
      @ModelAttribute ProductOrder order) {

    if (errors.hasErrors()) {
      return "design";
    }

    Product saved = productRepo.save(product);
    order.addProduct(saved);

    return "redirect:/orders/current";
  }

  private Iterable<Ingredient> filterByType(
      List<Ingredient> ingredients, Type type) {
    return ingredients
              .stream()
              .filter(x -> x.getType().equals(type))
              .collect(Collectors.toList());
  }

}
