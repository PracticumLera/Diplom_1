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
public class BurgerMovePositiveTest {

    private static final int INGREDIENTS_COUNT = 3;

    private final String scenarioName;
    private final int fromIndex;
    private final int toIndex;
    private final int[] expectedOrder;

    @Mock
    private Ingredient ingredient0;

    @Mock
    private Ingredient ingredient1;

    @Mock
    private Ingredient ingredient2;

    private Burger burger;
    private Ingredient[] allIngredients;

    public BurgerMovePositiveTest(String scenarioName, int fromIndex, int toIndex, int[] expectedOrder) {
        this.scenarioName = scenarioName;
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
        this.expectedOrder = expectedOrder;
    }

    @Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Move first to last", 0, 2, new int[]{1, 2, 0}},
                {"Move last to first", 2, 0, new int[]{2, 0, 1}},
                {"Move middle to first", 1, 0, new int[]{1, 0, 2}},
                {"Move first to middle", 0, 1, new int[]{1, 0, 2}},
                {"Move last to middle", 2, 1, new int[]{0, 2, 1}},
                {"No move - same index", 0, 0, new int[]{0, 1, 2}},
                {"No move - same index (last)", 2, 2, new int[]{0, 1, 2}}
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

    @Test
    public void moveIngredientShouldKeepSize() {
        burger.moveIngredient(fromIndex, toIndex);

        assertEquals("Size should stay the same after moving",
                INGREDIENTS_COUNT, burger.getIngredients().size());
    }

    @Test
    public void moveIngredientShouldChangeOrder() {
        burger.moveIngredient(fromIndex, toIndex);

        List<Ingredient> result = burger.getIngredients();

        assertEquals("Result size should match expected order",
                expectedOrder.length, result.size());

        for (int i = 0; i < expectedOrder.length; i++) {
            assertSame("Position " + i + " should contain ingredient from original index " + expectedOrder[i],
                    allIngredients[expectedOrder[i]], result.get(i));
        }
    }

    @Test
    public void moveIngredientShouldRemoveFromOldPosition() {
        burger.moveIngredient(fromIndex, toIndex);

        List<Ingredient> result = burger.getIngredients();
        Ingredient movedIngredient = allIngredients[fromIndex];

        // Проверяем что перемещённый ингредиент появляется только один раз и на правильной позиции
        int occurrences = 0;
        int actualPosition = -1;

        for (int i = 0; i < result.size(); i++) {
            if (result.get(i) == movedIngredient) {
                occurrences++;
                actualPosition = i;
            }
        }

        assertEquals("Moved ingredient should appear exactly once", 1, occurrences);
        assertEquals("Moved ingredient should be at position " + toIndex, toIndex, actualPosition);
    }
}