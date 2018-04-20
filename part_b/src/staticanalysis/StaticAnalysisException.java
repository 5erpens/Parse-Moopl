package staticanalysis;

import java.util.List;

/**
 * Exception thrown during static analysis (typically during symbol
 * table construction and type checking).
 */
public class StaticAnalysisException extends RuntimeException {

    /**
     * Creates a new instance of <code>StaticAnalysisException</code> without detail message.
     */
    public StaticAnalysisException() {
    }

    /**
     * Constructs an instance of <code>StaticAnalysisException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public StaticAnalysisException(String msg) {
        super(msg);
    }
    
    public StaticAnalysisException(String msg, List<String> tags) {
        super(msg + tagString(tags));
    }
    
    public static String tagString(List<String> tags) {
        String tagString = "";
        if (tags != null && tags.size() > 0) {
            tagString += " (" + tags.get(0);
            for (int i = 1; i < tags.size(); ++i) {
                tagString += ":" + tags.get(i);
            }
            tagString += ")";
        }
        return tagString;
    }
}
