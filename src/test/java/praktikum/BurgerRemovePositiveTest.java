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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

@RunWith(Parameterized.class)
public class BurgerRemovePositiveTest {

    private final String scenarioName;
    private final int removeIndex;
    private final int[] expectedIndices;

    @Mock
    private Ingredient firstIngredient;

    @Mock
    private Ingredient secondIngredient;

    @Mock
    private Ingredient thirdIngredient;

    private Burger burger;

    public BurgerRemovePositiveTest(String scenarioName, int removeIndex, int[] expectedIndices) {
        this.scenarioName = scenarioName;
        this.removeIndex = removeIndex;
        this.expectedIndices = expectedIndices;
    }

    @Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Remove first ingredient", 0, new int[]{1, 2}},
                {"Remove middle ingredient", 1, new int[]{0, 2}},
                {"Remove last ingredient", 2, new int[]{0, 1}}
        });
    }

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        burger = new Burger();
        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);
        burger.addIngredient(thirdIngredient);
    }

    @Test
    public void removeIngredientShouldDecreaseSize() {
        burger.removeIngredient(removeIndex);

        assertEquals(expectedIndices.length, burger.getIngredients().size());
    }

    @Test
    public void removeIngredientShouldRemoveCorrectIngredient() {
        Ingredient[] allIngredients = {firstIngredient, secondIngredient, thirdIngredient};

        burger.removeIngredient(removeIndex);

        List<Ingredient> result = burger.getIngredients();

        for (int i = 0; i < expectedIndices.length; i++) {
            assertSame(allIngredients[expectedIndices[i]], result.get(i));
        }
    }
}