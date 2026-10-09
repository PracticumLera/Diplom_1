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

@RunWith(Parameterized.class)
public class BurgerMoveNegativeTest {

    private static final int INGREDIENTS_COUNT = 3;

    private final String scenarioName;
    private final int fromIndex;
    private final int toIndex;

    @Mock
    private Ingredient ingredient0;

    @Mock
    private Ingredient ingredient1;

    @Mock
    private Ingredient ingredient2;

    private Burger burger;
    private Ingredient[] allIngredients;

    public BurgerMoveNegativeTest(String scenarioName, int fromIndex, int toIndex) {
        this.scenarioName = scenarioName;
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
    }

    @Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Invalid fromIndex: negative", -1, 1},
                {"Invalid fromIndex: equals size", INGREDIENTS_COUNT, 1},
                {"Invalid fromIndex: greater than size", 10, 1},
                {"Invalid toIndex: negative", 1, -1},
                {"Invalid toIndex: greater than size", 1, 10}
        });
    }

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        burger = new Burger();
        allIngredients = new Ingredient[]{ingredient0, ingredient1, ingredient2};

        for (Ingredient ingredient : allIngredients) {
            burger.addIngredient(ingredient);
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void moveIngredientShouldThrowExceptionForInvalidIndexes() {
        burger.moveIngredient(fromIndex, toIndex);
    }
}