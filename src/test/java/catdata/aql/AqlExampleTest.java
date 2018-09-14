package catdata.aql;

import catdata.Program;
import catdata.aql.exp.AqlEnv;
import catdata.aql.exp.AqlMultiDriver;
import catdata.aql.exp.AqlParserFactory;
import catdata.aql.exp.Exp;
import catdata.ide.Examples;
import catdata.ide.Language;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.fail;

public class AqlExampleTest {

    @ParameterizedTest
    @MethodSource("loadExamples")
    public void testSourceText(String description, String src) {
        System.out.println("testing example: " + description);
        try {
            final Program<Exp<?>> prog = AqlParserFactory.getParser().parseProgram(src);
            final AqlMultiDriver driver = new AqlMultiDriver(prog, new String[1], null);
            driver.start();
            final AqlEnv lastEnv = driver.env;
            if (lastEnv.exn != null) {
                throw lastEnv.exn;
            }
            final String msg = new StringBuilder()
                    .append("Test failed AS EXPECTED for test case '")
                    .append(description)
                    .append("' \n")
                    .toString();
        } catch (Exception e) {
            final String msg = new StringBuilder()
                    .append("Test failed for test case '")
                    .append(description)
                    .append("' \n")
                    .append(src)
                    .append("'.\n")
                    .append(e.getStackTrace())
                    .toString();
            fail(msg);
        }
    }

    public static Stream<Arguments> loadExamples() {
        return Examples
                .getExamples(Language.AQL).stream()
                .map(example -> Arguments.of(example.getName(), example.getText()));
    }

    @ParameterizedTest
    @MethodSource("loadDefectiveExamples")
    public void testDefectiveSourceText(String description, String src) {
        System.out.println("testing example: " + description);
        try {
            final Program<Exp<?>> prog = AqlParserFactory.getParser().parseProgram(src);
            final AqlMultiDriver driver = new AqlMultiDriver(prog, new String[1], null);
            driver.start();
            final AqlEnv lastEnv = driver.env;
            if (lastEnv.exn != null) {
                throw lastEnv.exn;
            }
            final String msg = new StringBuilder()
                    .append("Test succeeded for test case '")
                    .append(description)
                    .append("' \n")
                    .toString();
            fail(msg);
        } catch (Exception e) {
        }
    }

    public static Stream<Arguments> loadDefectiveExamples() {
        return Examples
                .getExamples(Language.AQL_ALT).stream()
                .map(example -> Arguments.of(example.getName(), example.getText()));
    }

}
