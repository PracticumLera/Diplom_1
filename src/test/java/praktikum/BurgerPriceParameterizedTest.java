package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class BurgerPriceParameterizedTest {

    private static final float DELTA = 0.001f;

    private final String scenarioName;
    private final float bunPrice;
    private final float firstIngredientPrice;
    private final float secondIngredientPrice;
    private final float expectedPrice;

    @Mock
    private Bun bun;

    @Mock
    private Ingredient firstIngredient;

    @Mock
    private Ingredient secondIngredient;

    private Burger burger;

    public BurgerPriceParameterizedTest(String scenarioName,
                                        float bunPrice,
                                        float firstIngredientPrice,
                                        float secondIngredientPrice,
                                        float expectedPrice) {
        this.scenarioName = scenarioName;
        this.bunPrice = bunPrice;
        this.firstIngredientPrice = firstIngredientPrice;
        this.secondIngredientPrice = secondIngredientPrice;
        this.expectedPrice = expectedPrice;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Standard prices", 100.0f, 50.0f, 30.0f, 280.0f},
                {"Premium prices", 200.0f, 100.0f, 100.0f, 600.0f},
                {"Free ingredients", 50.0f, 0.0f, 0.0f, 100.0f},
                {"Expensive burger", 300.0f, 150.0f, 250.0f, 1000.0f},
                {"Cheap bun with expensive ingredients", 10.0f, 500.0f, 400.0f, 920.0f},
                {"Fractional prices", 99.99f, 49.99f, 29.99f, 279.96f}
        });
    }

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        burger = new Burger();
    }

    @Test
    public void getPriceShouldReturnCorrectPrice() {
        when(bun.getPrice()).thenReturn(bunPrice);
        when(firstIngredient.getPrice()).thenReturn(firstIngredientPrice);
        when(secondIngredient.getPrice()).thenReturn(secondIngredientPrice);

        burger.setBuns(bun);
        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);

        float actualPrice = burger.getPrice();
        assertEquals("Scenario: " + scenarioName, expectedPrice, actualPrice, DELTA);
    }
}