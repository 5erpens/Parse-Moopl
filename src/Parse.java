
/**
 * A harness to test the Moopl parser.
 */
public class Parse {

    public static void main(String[] args) throws ParseException {
        MooplParser parser;
        if (args.length == 0) {
            // Read program to be parsed from standard input
            parser = new MooplParser(System.in);
        } else {
            // Read program to be parsed from file
            try {
                parser = new MooplParser(new java.io.FileInputStream(args[0]));
            } catch (java.io.FileNotFoundException e) {
                System.err.println("Unable to read file " + args[0]);
                return;
            }
        }
        System.out.print("parsing...");
        System.out.flush();
        parser.nt_Program();
        parser.eof();
        System.out.println("...parse completed.");
    }
}
