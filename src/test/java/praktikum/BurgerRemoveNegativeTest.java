package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class BurgerRemoveNegativeTest {

    private static final int INGREDIENTS_COUNT = 3;

    private final String scenarioName;
    private final int invalidIndex;

    @Mock
    private Ingredient firstIngredient;

    @Mock
    private Ingredient secondIngredient;

    @Mock
    private Ingredient thirdIngredient;

    private Burger burger;

    public BurgerRemoveNegativeTest(String scenarioName, int invalidIndex) {
        this.scenarioName = scenarioName;
        this.invalidIndex = invalidIndex;
    }

    @Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Negative index", -1},
                {"Index equal to size", INGREDIENTS_COUNT},
                {"Index greater than size", 10},
                {"Very large index", 100}
        });
    }

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        burger = new Burger();
        Ingredient[] allIngredients = {firstIngredient, secondIngredient, thirdIngredient};

        for (Ingredient ingredient : allIngredients) {
            burger.addIngredient(ingredient);
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeIngredientShouldThrowExceptionForInvalidIndex() {
        burger.removeIngredient(invalidIndex);
    }

    @Test
    public void removeIngredientShouldNotChangeListWhenThrowingException() {
        int originalSize = burger.getIngredients().size();

        try {
            burger.removeIngredient(invalidIndex);
        } catch (IndexOutOfBoundsException e) {
            // Exception expected
        }

        assertEquals("List size should not change after failed removal",
                originalSize, burger.getIngredients().size());
    }
}