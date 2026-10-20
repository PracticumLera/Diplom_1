package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class BunTest {

    private static final float DELTA = 0.001f;

    private final String bunName;
    private final float bunPrice;

    private Bun bun;

    public BunTest(String bunName, float bunPrice) {
        this.bunName = bunName;
        this.bunPrice = bunPrice;
    }

    @Parameters(name = "{0} - {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"black bun", 100.0f},
                {"white bun", 80.0f},
                {"red bun", 120.0f},
                {"sesame bun", 90.0f}
        });
    }

    @Before
    public void setUp() {
        bun = new Bun(bunName, bunPrice);
    }

    @Test
    public void getNameShouldReturnName() {
        assertEquals(bunName, bun.getName());
    }

    @Test
    public void getPriceShouldReturnPrice() {
        assertEquals(bunPrice, bun.getPrice(), DELTA);
    }
}
