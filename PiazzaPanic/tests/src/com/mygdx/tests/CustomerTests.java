package com.mygdx.tests;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(GdxTestRunner.class)
public class CustomerTests {
    private Customer customer;
    @Before
    public void setUp() {
        customer = new Customer(new Actor());
    }

    @After
    public void tearDown(){}

    @Test
    public void testInitBodyX() {
        assertEquals(144, customer.body.getX());
        System.out.println("Customer is at expected x position on initialisation");
    }

    @Test
    public void testInitBodyY() {
        assertEquals(80, customer.body.getY());
        System.out.println("Customer is at expected Y position on initialisation");
    }

    @Test
    public void testInitHeight() {
        assertEquals(23, customer.body.getHeight());
        System.out.println("Customer is the expected initial height");
    }

    @Test
    public void testInitWidth() {
        assertEquals(16, customer.body.getWidth());
        System.out.println("Customer is the expected initial width");
    }

    @Test
    public void testSetY() {
        customer.body.setY(100);
        assertEquals(100, customer.body.getY());
        System.out.println("Customer moved Y position correctly");
    }

    @Test
    public void testSetX() {
        customer.body.setX(24);
        assertEquals(24, customer.body.getX());
        System.out.println("Customer moved X position correctly");

    }

    @Test
    public void testName() {
        String[] names = {"Blue", "Red", "White", "Yellow" };
        assertEquals(true, Arrays.asList(names).contains(customer.name));
        System.out.println("Customer has a valid name");
    }

    @Test
    public void testCustomerOrder() {
        String[] names = {"burger", "salad"};
        String orderName = customer.customerOrder.getName();
        assertEquals(true, Arrays.asList(names).contains(orderName));
    }
}
