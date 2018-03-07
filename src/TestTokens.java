
/**
 * A harness to test the Moopl token definitions.
 */
public class TestTokens {

    public static void main(String[] args) throws ParseException {
        MooplParser parser;
        if (args.length == 0) {
            // Read program to be tested from standard input
            parser = new MooplParser(System.in);
        } else {
            // Read program to be tested from file
            try {
                parser = new MooplParser(new java.io.FileInputStream(args[0]));
            } catch (java.io.FileNotFoundException e) {
                System.err.println("Unable to read file " + args[0]);
                return;
            }
        }
        System.out.println("testing token definitions...");
        parser.testTokens();
        parser.eof();
        System.out.println("...test completed.");
    }
}
