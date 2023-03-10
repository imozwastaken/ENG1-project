import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import com.mygdx.game.Food.Burger;

public class BurgerTests {

    @Test
    public void testIngredients(){
        Burger burger = new Burger();
        burger.addIngredient("patty");
        burger.addIngredient("buns");
        burger.addIngredient("lettuce");

        ArrayList<String> ingredients = burger.getIngredients();
        
        assertTrue(ingredients.contains("patty"));
        assertTrue(ingredients.contains("buns"));
        assertTrue(ingredients.contains("lettuce"));
        assertEquals(3, ingredeints.size());
    }

}