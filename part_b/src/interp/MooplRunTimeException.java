package interp;

import java.util.List;

/**
 * Moopl interpreter run time exception.
 */
public class MooplRunTimeException extends RuntimeException {

    /**
     * Creates a new instance of <code>MooplRunTimeException</code> without detail message.
     */
    public MooplRunTimeException() {
    }

    /**
     * Constructs an instance of <code>MooplRunTimeException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public MooplRunTimeException(String msg) {
        super(msg);
    }
    
    public MooplRunTimeException(String msg, List<String> tags) {
        super(msg + tagString(tags));
    }
    
    private static String tagString(List<String> tags) {
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
