package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class IngredientTest {

    private static final float DELTA = 0.001f;

    private final String scenarioName;
    private final IngredientType type;
    private final String name;
    private final float price;

    private Ingredient ingredient;

    public IngredientTest(String scenarioName, IngredientType type, String name, float price) {
        this.scenarioName = scenarioName;
        this.type = type;
        this.name = name;
        this.price = price;
    }

    @Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Hot sauce", IngredientType.SAUCE, "hot sauce", 100.0f},
                {"Sour cream", IngredientType.SAUCE, "sour cream", 200.0f},
                {"Cutlet filling", IngredientType.FILLING, "cutlet", 150.0f},
                {"Dinosaur filling", IngredientType.FILLING, "dinosaur", 250.0f},
                {"Free ingredient", IngredientType.SAUCE, "test sauce", 0.0f},
                {"Expensive ingredient", IngredientType.FILLING, "premium filling", 999.99f},
                {"Empty name", IngredientType.SAUCE, "", 50.0f}
        });
    }

    @Before
    public void setUp() {
        ingredient = new Ingredient(type, name, price);
    }

    @Test
    public void ingredientShouldNotBeNull() {
        assertNotNull(ingredient);
    }

    @Test
    public void ingredientShouldReturnCorrectType() {
        assertEquals(type, ingredient.getType());
    }

    @Test
    public void ingredientShouldReturnCorrectName() {
        assertEquals(name, ingredient.getName());
    }

    @Test
    public void ingredientShouldReturnCorrectPrice() {
        assertEquals(price, ingredient.getPrice(), DELTA);
    }
}