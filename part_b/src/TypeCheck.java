
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import syntaxtree.*;
import staticanalysis.*;

/**
 * A harness to run the type checker.
 */
public class TypeCheck {

    public static void main(String[] args) {
        Program root;
        try {
            if (args.length < 1) {
                System.err.println("No Moopl source file specified!");
                return;
            } else {
                try (InputStream source = new FileInputStream(args[0])) {
                    System.out.print("Parsing...");
                    System.out.flush();
                    root = new MooplParser(source).nt_Program();
                    System.out.println("...parsed OK.");
                } catch (IOException e) {
                    System.err.println("Unable to read file " + args[0]);
                    return;
                }
            }
            SymbolTable symTab;
            {
                SymbolTableBuilder stvisit = new SymbolTableBuilder();
                System.out.print("Building Symbol Table...");
                System.out.flush();
                root.accept(stvisit);
                System.out.println("...done");
                symTab = stvisit.getSymTab();
            }
            System.out.print("Type checking...");
            System.out.flush();
            root.accept(new TypeChecker(symTab));
            System.out.println("...type checked OK.");
        } catch (ParseException | TokenMgrError e) {
            System.out.println("\nSyntax error: " + e.getMessage());
        } catch (StaticAnalysisException e) {
            System.out.println("\nStatic semantics error: " + e.getMessage());
        }
    }
}
