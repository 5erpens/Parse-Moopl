import syntaxtree.*;
import syntaxtree.interp.*;
import staticanalysis.*;
import interp.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import pretty.PrettyPrinter;
import visitor.Visitor;

/**
 * A harness to run the interpreter.
 */
public class Interpret {
    //test

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
            TypeChecker typeChecker = new TypeChecker(symTab);
            System.out.print("Type checking...");
            System.out.flush();
            root.accept(typeChecker);
            System.out.println("...type checked OK.");
            System.out.print("Allocating variables...");
            System.out.flush();
            Visitor<Void> varAllocator = new interp.VarAllocator(symTab);
            root.accept(varAllocator);
            System.out.println("...done.");
            root.accept(new PrettyPrinter());
            Interpreter eval = new Interpreter(symTab);
            AST.globalTag("Command Line");
            MooplParser commandLineParser = new MooplParser(System.in);
            while (true) {
                System.out.print("> ");
                try {
                    ICommand command = commandLineParser.nt_ICommand();
                    if (command instanceof IQuit) {
                        break;
                    } else {
                        command.accept(typeChecker);
                        command.accept(varAllocator);
                        command.accept(eval);
                    }
                } catch (ParseException | TokenMgrError e) {
                    System.out.println("Command line syntax error: " + e.getMessage());
                    commandLineParser = new MooplParser(System.in);
                } catch (StaticAnalysisException e) {
                    System.out.println("Command line static semantic error: " + e.getMessage());
                } catch (MooplRunTimeException e) {
                    System.out.println("Runtime error: " + e.getMessage());
                }
            }
            System.out.println("quit");
        } catch (ParseException | TokenMgrError e) {
            System.out.println("\nSyntax error: " + e.getMessage());
        } catch (StaticAnalysisException e) {
            System.out.println("\nStatic semantics error: " + e.getMessage());
        }
    }
}
