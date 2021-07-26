/*
 *
 *  *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  *  Copyright 2021 rielly donnell
 *
 */

package ucf.assignments;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppControllerTest {

    @Test
    void getListForTable_test() {
        Inventory test = new Inventory();
        Item item = new Item("Test", "TESTTEST01", 1.0);
        test.addItem(item);
        ObservableList<Item> observableList = AppController.getListForTable(test, null);
        assertFalse(observableList.isEmpty());
    }

    @Test
    void validateName_test_true() {
        String name = "Good";
        assertTrue(AppController.validateName(name));
    }

    @Test
    void validateName_test_false() {
        String name = "B";
        assertFalse(AppController.validateName(name));
    }

    @Test
    void validateSerialNumber_test_true() {
        String sn = "testtest01";
        assertTrue(AppController.validateSerialNumber(sn));
    }

    @Test
    void validateSerialNumber_test_false() {
        String sn = "testtest";
        assertFalse(AppController.validateSerialNumber(sn));
    }

    @Test
    void validateValue_test_false() {
        String value = "a";
        assertFalse(AppController.validateValue(value));
    }

    @Test
    void validateValue_test_true() {
        String value = "1.33";
        assertTrue(AppController.validateValue(value));
    }
}