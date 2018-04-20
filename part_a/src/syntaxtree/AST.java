package syntaxtree;

import java.util.LinkedList;
import java.util.List;

/**
 * Parent class for all abstract syntax. Provides the ability to tag
 * ASTs with additional information to aid error reporting.
 * @author seb
 */
public abstract class AST {
    
    private static List<String> globalTags;
    
    private List<String> tags;
    
    /**
     * Initialise the error-reporting tag list.
     * @see AST.globalTag(String)
     */
    public AST() {
        if (globalTags != null) {
            tags = new LinkedList<>();
            tags.addAll(globalTags);
        }
    }
    
    /**
     * Add a tag for error-reporting.
     * @param t the tag
     */
    public void tag(String t) {
        if (tags == null) tags = new LinkedList<>();
        tags.add(t);
    }
    
    /**
     * Add a tag describing a position in a source-code file (line and column).
     * @param line the line number
     * @param column the column number
     */
    public void tag(int line, int column) {
        tag("line " + line + ", column " + column);
    }
    
    /**
     * Add a list of tags for error-reporting.
     * @param ts the tags
     */
    public void tag(List<String> ts) {
        for (String t : ts) {
            tag(t);
        }
    }
    
    /**
     * Add a global tag. A global tag is inherited by all AST objects when
     * they are created.
     * @param t the tag
     */
    public static void globalTag(String t) {
        if (globalTags == null) globalTags = new LinkedList<>();
        globalTags.add(t);
    }
    
    /**
     * Reset the global tags to empty.
     * Note: this does not remove global tags from AST objects which have
     * already been created.
     */
    public static void clearGlobalTags() {
        globalTags = null;
    }
    
    /**
     * The error-reporting tags for this AST object. If no tags have
     * been added, this list will be null.
     * @return the error reporting tags (may be null)
     */
    public List<String> getTags() {
        return tags;
    }
}
