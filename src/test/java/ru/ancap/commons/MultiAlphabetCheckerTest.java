package ru.ancap.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MultiAlphabetCheckerTest {
    
    @Test
    public void testIsMultiAlphabeticWithSingleAlphabet() {
        // Latin only
        assertFalse(MultiAlphabetChecker.isMultiAlphabetic("Hello"));
        // Cyrillic only
        assertFalse(MultiAlphabetChecker.isMultiAlphabetic("Привет"));
        // Greek only
        assertFalse(MultiAlphabetChecker.isMultiAlphabetic("Γειά"));
    }
    
    @Test
    public void testIsMultiAlphabeticWithMultipleAlphabets() {
        // Latin and Cyrillic
        assertTrue(MultiAlphabetChecker.isMultiAlphabetic("HelloПривет"));
        // Latin and Greek
        assertTrue(MultiAlphabetChecker.isMultiAlphabetic("HelloΓειά"));
        // Cyrillic and Greek
        assertTrue(MultiAlphabetChecker.isMultiAlphabetic("ПриветΓειά"));
    }
    
    @Test
    public void testIsMultiAlphabeticWithSpecialCharacters() {
        // Special characters should not affect the result
        assertFalse(MultiAlphabetChecker.isMultiAlphabetic("Hello, world!"));
        assertTrue(MultiAlphabetChecker.isMultiAlphabetic("Hello, Привет!"));
    }
    
    @Test
    public void testIsMultiAlphabeticWithNonAlphabeticStrings() {
        // Strings without alphabetic characters
        assertFalse(MultiAlphabetChecker.isMultiAlphabetic("12345"));
        assertFalse(MultiAlphabetChecker.isMultiAlphabetic("!@#$%"));
    }
    
    @Test
    public void testIsMultiAlphabeticWithHebrewAndArabic() {
        // Hebrew only
        assertFalse(MultiAlphabetChecker.isMultiAlphabetic("שלום")); // Shalom in Hebrew
        // Arabic only
        assertFalse(MultiAlphabetChecker.isMultiAlphabetic("مرحبا")); // Marhaba in Arabic
        // Hebrew and Arabic
        assertTrue(MultiAlphabetChecker.isMultiAlphabetic("שלוםمرحبا")); // Shalom and Marhaba
    }
    
    @Test
    public void testIsMultiAlphabeticWithGeorgian() {
        // Georgian only
        assertFalse(MultiAlphabetChecker.isMultiAlphabetic("გამარჯობა")); // Gamarjoba in Georgian
        // Georgian and Latin
        assertTrue(MultiAlphabetChecker.isMultiAlphabetic("გამარჯობაHello")); // Gamarjoba and Hello
    }
    
}