package staticanalysis;

import java.util.List;

/**
 * Exception thrown to report a type error during type checking.
 */
public class TypeCheckingException extends StaticAnalysisException {

    /**
     * Creates a new instance of <code>TypeCheckingException</code> without detail message.
     */
    public TypeCheckingException() {
    }

    /**
     * Constructs an instance of <code>TypeCheckingException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public TypeCheckingException(String msg) {
        super(msg);
    }
    
    public TypeCheckingException(String msg, List<String> tags) {
        super(msg, tags);
    }
    
}
