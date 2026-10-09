package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    private static final float DELTA = 0.001f;
    private static final int BUNS_COUNT = 2;

    private static final float BUN_PRICE = 100.0f;
    private static final float INGREDIENT_1_PRICE = 50.0f;
    private static final float INGREDIENT_2_PRICE = 75.0f;

    private static final String BUN_NAME = "black bun";
    private static final String INGREDIENT_1_NAME = "hot sauce";
    private static final String INGREDIENT_2_NAME = "cutlet";

    @Mock
    private Bun bun;

    @Mock
    private Ingredient ingredient1;

    @Mock
    private Ingredient ingredient2;

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

    private void setupIngredient1Mock() {
        when(ingredient1.getName()).thenReturn(INGREDIENT_1_NAME);
        when(ingredient1.getType()).thenReturn(IngredientType.SAUCE);
        when(ingredient1.getPrice()).thenReturn(INGREDIENT_1_PRICE);
    }

    private void setupIngredient2PriceMock() {
        when(ingredient2.getPrice()).thenReturn(INGREDIENT_2_PRICE);
    }

    @Test
    public void setBunsShouldSetBun() {
        burger.setBuns(bun);
        assertEquals(bun, burger.getBun());
    }

    @Test
    public void addIngredientShouldAddIngredient() {
        burger.addIngredient(ingredient1);
        assertEquals(ingredient1, burger.getIngredients().get(0));
    }

    @Test
    public void addIngredientShouldIncreaseSize() {
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);
        assertEquals(2, burger.getIngredients().size());
    }

    @Test
    public void removeIngredientShouldDecreaseSizeAndRemoveCorrectOne() {
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);

        burger.removeIngredient(0);

        assertEquals(1, burger.getIngredients().size());
        assertEquals(ingredient2, burger.getIngredients().get(0));
    }

    @Test
    public void moveIngredientShouldChangeOrder() {
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);

        burger.moveIngredient(0, 1);

        assertEquals(ingredient2, burger.getIngredients().get(0));
        assertEquals(ingredient1, burger.getIngredients().get(1));
    }

    @Test
    public void getPriceShouldCalculateCorrectly() {
        setupIngredient1Mock();
        setupIngredient2PriceMock();

        burger.setBuns(bun);
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);

        float expected = BUN_PRICE * BUNS_COUNT + INGREDIENT_1_PRICE + INGREDIENT_2_PRICE;
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
        setupIngredient1Mock();
        burger.setBuns(bun);
        burger.addIngredient(ingredient1);

        String receipt = burger.getReceipt();
        assertTrue(receipt.contains(INGREDIENT_1_NAME));
    }

    @Test
    public void getReceiptShouldContainPriceLabel() {
        burger.setBuns(bun);

        String receipt = burger.getReceipt();
        assertTrue(receipt.contains("Price:"));
    }

    @Test
    public void getReceiptShouldContainCorrectPrice() {
        setupIngredient1Mock();
        burger.setBuns(bun);
        burger.addIngredient(ingredient1);

        String receipt = burger.getReceipt();
        float expectedPrice = BUN_PRICE * BUNS_COUNT + INGREDIENT_1_PRICE;
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