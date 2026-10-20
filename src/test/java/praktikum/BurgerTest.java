package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    private static final float DELTA = 0.001f;
    private static final int BUNS_COUNT = 2;

    private static final float BUN_PRICE = 100.0f;
    private static final float FIRST_INGREDIENT_PRICE = 50.0f;
    private static final float SECOND_INGREDIENT_PRICE = 75.0f;

    private static final String BUN_NAME = "black bun";
    private static final String FIRST_INGREDIENT_NAME = "hot sauce";

    @Mock
    private Bun bun;

    @Mock
    private Ingredient firstIngredient;

    @Mock
    private Ingredient secondIngredient;

    private Burger burger;

    @Before
    public void setUp() {
        burger = new Burger();
        setupBunMock();
    }

    private void setupBunMock() {
        when(bun.getName()).thenReturn(BUN_NAME);
        when(bun.getPrice()).thenReturn(BUN_PRICE);
    }

    private void setupFirstIngredientMock() {
        when(firstIngredient.getName()).thenReturn(FIRST_INGREDIENT_NAME);
        when(firstIngredient.getType()).thenReturn(IngredientType.SAUCE);
        when(firstIngredient.getPrice()).thenReturn(FIRST_INGREDIENT_PRICE);
    }

    private void setupSecondIngredientPriceMock() {
        when(secondIngredient.getPrice()).thenReturn(SECOND_INGREDIENT_PRICE);
    }

    @Test
    public void setBunsShouldSetBun() {
        burger.setBuns(bun);
        assertEquals(bun, burger.getBun());
    }

    @Test
    public void addIngredientShouldAddIngredient() {
        burger.addIngredient(firstIngredient);
        assertEquals(firstIngredient, burger.getIngredients().get(0));
    }

    @Test
    public void addIngredientShouldIncreaseSize() {
        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);
        assertEquals(2, burger.getIngredients().size());
    }

    @Test
    public void removeIngredientShouldDecreaseSize() {
        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);

        burger.removeIngredient(0);

        assertEquals(1, burger.getIngredients().size());
    }

    @Test
    public void removeIngredientShouldRemoveCorrectOne() {
        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);

        burger.removeIngredient(0);

        assertEquals(secondIngredient, burger.getIngredients().get(0));
    }

    @Test
    public void moveIngredientShouldPlaceSecondAtFirstPosition() {
        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);

        burger.moveIngredient(0, 1);

        assertEquals(secondIngredient, burger.getIngredients().get(0));
    }

    @Test
    public void moveIngredientShouldPlaceFirstAtSecondPosition() {
        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);

        burger.moveIngredient(0, 1);

        assertEquals(firstIngredient, burger.getIngredients().get(1));
    }

    @Test
    public void getPriceShouldCalculateCorrectly() {
        setupFirstIngredientMock();
        setupSecondIngredientPriceMock();

        burger.setBuns(bun);
        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);

        float expected = BUN_PRICE * BUNS_COUNT + FIRST_INGREDIENT_PRICE + SECOND_INGREDIENT_PRICE;
        assertEquals(expected, burger.getPrice(), DELTA);
    }

    @Test
    public void getPriceShouldReturnOnlyBunPrice() {
        burger.setBuns(bun);

        float expected = BUN_PRICE * BUNS_COUNT;
        assertEquals(expected, burger.getPrice(), DELTA);
    }

    @Test
    public void getPriceShouldReturnZeroForEmptyBurger() {
        Bun emptyBun = mock(Bun.class);
        when(emptyBun.getPrice()).thenReturn(0.0f);
        burger.setBuns(emptyBun);

        assertEquals(0.0f, burger.getPrice(), DELTA);
    }

    @Test
    public void getReceiptShouldContainBunName() {
        burger.setBuns(bun);

        String receipt = burger.getReceipt();
        assertTrue(receipt.contains(BUN_NAME));
    }

    @Test
    public void getReceiptShouldContainIngredientName() {
        setupFirstIngredientMock();
        burger.setBuns(bun);
        burger.addIngredient(firstIngredient);

        String receipt = burger.getReceipt();
        assertTrue(receipt.contains(FIRST_INGREDIENT_NAME));
    }

    @Test
    public void getReceiptShouldContainPriceLabel() {
        burger.setBuns(bun);

        String receipt = burger.getReceipt();
        assertTrue(receipt.contains("Price:"));
    }

    @Test
    public void getReceiptShouldContainCorrectPrice() {
        setupFirstIngredientMock();
        burger.setBuns(bun);
        burger.addIngredient(firstIngredient);

        String receipt = burger.getReceipt();
        float expectedPrice = BUN_PRICE * BUNS_COUNT + FIRST_INGREDIENT_PRICE;
        String expectedPriceFormat = String.format("Price: %.1f", expectedPrice);

        assertTrue(receipt.contains(expectedPriceFormat));
    }

    @Test
    public void getReceiptShouldContainBothBunNamesTopAndBottom() {
        burger.setBuns(bun);

        String receipt = burger.getReceipt();
        int firstIndex = receipt.indexOf(BUN_NAME);
        int lastIndex = receipt.lastIndexOf(BUN_NAME);

        assertTrue("Bun should appear twice", firstIndex >= 0 && firstIndex != lastIndex);
    }
}