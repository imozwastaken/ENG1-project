package com.mygdx.tests;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.mygdx.game.Food.Burger;
import com.mygdx.game.Food.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Or;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

@RunWith(GdxTestRunner.class)
public class OrderTests {
    Order order;


    @Before
    public void setUp() throws IOException {
        order = new Order("burger", new Texture("orderBurger.png"), new Burger());
    }

    @Test
    public void testOrderName() {
        assertEquals("burger", order.getName());
        System.out.println("Order has correct name");
    }

    @Test
    public void testOrderTexture() {
        Texture validT = new Texture("orderBurger.png");
        Texture orderTex = order.getOrderTexture();
        Boolean isSame = false;
        if (validT.getWidth() != orderTex.getWidth() || validT.getHeight() != orderTex.getHeight()) {
            isSame = true;
        }
        if (isSame == false) {
            TextureData validMap = validT.getTextureData();
            TextureData orderMap = orderTex.getTextureData();

            if (!validMap.isPrepared()) {
                validMap.prepare();
            }
            if (!orderMap.isPrepared()) {
                orderMap.prepare();
            }

            Pixmap validPMap = validMap.consumePixmap();
            Pixmap orderPMap = orderMap.consumePixmap();
            for (int x=0; x < validPMap.getWidth(); x++ ) {
                for (int y=0; y<validPMap.getHeight();y++) {
                    if (validPMap.getPixel(x, y) != orderPMap.getPixel(x, y)) {
                        isSame=false;
                        break;
                    } else {
                        isSame = true;
                    }
                }
            }
        }
        assertEquals(true, isSame);
        System.out.println("Order has the correct texture");
    }

    @Test
    public void testInitOrderTime() {
        assertEquals(40, order.getOrderTime());
        System.out.println("Initial order time is correct");
    }

    @Test
    public void testOrderTimeSet() {
        order.setOrderTime(50);
        assertEquals(50, order.getOrderTime());
        System.out.println("Order time setter works");
    }


}
