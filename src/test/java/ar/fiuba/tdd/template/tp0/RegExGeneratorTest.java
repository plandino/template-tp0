package ar.fiuba.tdd.template.tp0;

import org.junit.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class RegExGeneratorTest {

    private boolean validate(String regEx, int numberOfResults) {
        RegExGenerator generator = new RegExGenerator(5);
        // TODO: Uncomment parameters
        List<String> results = generator.generate(regEx, numberOfResults);
        // force matching the beginning and the end of the strings
        Pattern pattern = Pattern.compile("^" + regEx + "$");

        for(int i = 0; i < results.size(); i++){
            String palabra = results.get(i);
            System.out.println(palabra);
        }
        return results
                .stream()
                .reduce(true,
                    (acc, item) -> {
                        Matcher matcher = pattern.matcher(item);
//                        System.out.println(results.matches("^\\@..$"));

                        return acc && matcher.find();
                    },
                    (item1, item2) -> item1 && item2);
    }

    //TODO: Uncomment these tests

    @Test
    public void testAnyCharacter() {
        assertTrue(validate(".", 1));
    }

    @Test
    public void testMultipleCharacters() {
        assertTrue(validate("...", 1));
    }

    @Test
    public void testLiteral() {
        assertTrue(validate("\\@", 1));
    }

    @Test
    public void testLiteralDotCharacter() {
        assertTrue(validate("\\@..", 1));
    }

    @Test
    public void testZeroOrOneCharacter() {
        assertTrue(validate("\\@.h?", 1));
    }

    @Test
    public void testCharacterSet() {
        assertTrue(validate("[abc]", 1));
    }

    @Test
    public void testCharacterSetWithQuantifiers() {
        assertTrue(validate("[abc]+", 1));
    }

    // TODO: Add more tests!!!


    @Test
    public void testAnyCharacterWithAsterisk() {
        assertTrue(validate(".*", 1));
    }

    @Test
    public void testLiteralWithAsterisk() {
        assertTrue(validate("&*", 1));
    }

    @Test
    public void testAnyCharacterWithPlus() {
        assertTrue(validate(".+", 1));
    }

    @Test
    public void testLiteralWithPlus() {
        assertTrue(validate("p+", 1));
    }

    @Test
    public void testAnyCharacterWithQuestionMark() {
        assertTrue(validate(".?", 1));
    }

    @Test
    public void testLiteralWithQuestionMark() {
        assertTrue(validate("1?", 1));
    }

//    @Test
//    public void testMultipleAnyCharacters() {
//        assertTrue(validate(".*.*.*", 1));
//    }

    @Test
    public void testOneLiteralAndDots() {
        assertTrue(validate("..5.*", 1));
    }

    @Test
    public void testMultipleLiteralsAndDots() {
        assertTrue(validate("..5sadd.*", 1));
    }

    @Test
    public void testBackslashedDotWithLiteralAndCuantifiers() {
        assertTrue(validate("\\..5.*", 1));
    }

    @Test
    public void testBackslashedDot() {
        assertTrue(validate("\\.", 1));
    }

//    @Test
//    public void testBackslashedLiteral() {
//        assertTrue(validate("\\5", 1));
//    }

    @Test
    public void testBackslashedDotWithLiteral() {
        assertTrue(validate("\\.6.8.2", 1));
    }

//    @Test
//    public void testBackslashedDotWithLiteralsAndCuantifier() {
//        assertTrue(validate("\\.abc?.6.8.2", 1));
//    }

//    @Test
//    public void testBackslashedAsterisk() {
//        assertTrue(validate("\\*abc?.6.8.2", 1));
//    }

    @Test
    public void testSetContinuedWithLiterals() {
        assertTrue(validate("[asvg]llld", 1));
    }

    @Test
    public void testBackslashedBackslashWithLiterals() {
        assertTrue(validate("sads\\\\asb", 1));
    }

    @Test
    public void testBackslashedBackslashWithLiteralsAndCuantifier() {
        assertTrue(validate("sads\\\\?sb", 1));
    }

//     Si toca d en el set, falla, sino anda
//    @Test
//    public void testBackslashedInsideSet() {
//        assertTrue(validate("[as\\db]", 1));
//    }


    // Si toca d en el set, falla, sino anda
    @Test
    public void testBackslashedCuantifierInsideSet() {
        assertTrue(validate("[as\\*db]", 1));
    }


//    @Test
//    public void testSetContinuedWithLiteralsAndCuantifier() {
//        assertTrue(validate("[asvg]llld*", 1));
//    }
}
