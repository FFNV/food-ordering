package products.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import products.model.Ingredient;
import products.model.Ingredient.Type;
import products.repository.IngredientRepository;
import products.repository.UserRepository;
import products.model.User;

@Profile("!prod")
@Configuration
public class DevelopmentConfig {

  @Bean
  public CommandLineRunner dataLoader(IngredientRepository repo,
        UserRepository userRepo, PasswordEncoder encoder) {
    return new CommandLineRunner() {
      @Override
      public void run(String... args) throws Exception {
    	repo.deleteAll();
        userRepo.deleteAll();

        repo.save(new Ingredient("ZPLK", "Ziplock", Type.PACK));
        repo.save(new Ingredient("DBLC", "Double cup", Type.PACK));
        repo.save(new Ingredient("GRBF", "Афганка", Type.MJ));
        repo.save(new Ingredient("GOGL", "Gorilla Glue", Type.MJ));
        repo.save(new Ingredient("LEAN", "Lean", Type.SYRUP));
        repo.save(new Ingredient("PPDR", "Purlple Drank", Type.SYRUP));
        repo.save(new Ingredient("MDMA", "MDMA", Type.MOLLY));
        repo.save(new Ingredient("MOLY", "Molly", Type.MOLLY));
        repo.save(new Ingredient("XANY", "Xanny", Type.PERK));
        repo.save(new Ingredient("PERC", "Percocet", Type.PERK));
                
        userRepo.save(new User("ffnv", encoder.encode("password"),
            "Maksimka", "Popova 25", "Arhangelsk", "ARH",
            "29", "123-123-1234"));
      }
    };
  }
  
}
