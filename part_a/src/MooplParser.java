/* Generated By:JavaCC: Do not edit this line. MooplParser.java */
  import syntaxtree.*;
  import syntaxtree.interp.*;
  import java.util.List;
  import java.util.LinkedList;
  public class MooplParser implements MooplParserConstants {

  final public void testTokens() throws ParseException {
  Token t;
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PROC:
      case FUN:
      case CLASS:
      case TRUE:
      case FALSE:
      case EXTENDS:
      case RETURN:
      case ARRAYOF:
      case BOOLEAN:
      case INT:
      case IF:
      case THEN:
      case ELSE:
      case WHILE:
      case DO:
      case OUTPUT:
      case LENGTH:
      case SELF:
      case NEW:
      case OBJECT:
      case ISNULL:
      case OP:
      case OPENCBA:
      case CLOSECBA:
      case OPENRBA:
      case CLOSERBA:
      case OPENSBA:
      case CLOSESBA:
      case SEMICOL:
      case COMMA:
      case EQUAL:
      case NOT:
      case DOT:
      case INTEGER_LITERAL:
      case ID:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PROC:
      case FUN:
      case CLASS:
      case TRUE:
      case FALSE:
      case EXTENDS:
      case RETURN:
      case ARRAYOF:
      case BOOLEAN:
      case INT:
      case IF:
      case THEN:
      case ELSE:
      case WHILE:
      case DO:
      case OUTPUT:
      case LENGTH:
      case SELF:
      case NEW:
      case OBJECT:
      case ISNULL:
      case OP:
      case OPENCBA:
      case CLOSECBA:
      case OPENRBA:
      case CLOSERBA:
      case OPENSBA:
      case CLOSESBA:
      case SEMICOL:
      case COMMA:
      case EQUAL:
      case NOT:
      case DOT:
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case PROC:
          t = jj_consume_token(PROC);
          break;
        case FUN:
          t = jj_consume_token(FUN);
          break;
        case CLASS:
          t = jj_consume_token(CLASS);
          break;
        case TRUE:
          t = jj_consume_token(TRUE);
          break;
        case FALSE:
          t = jj_consume_token(FALSE);
          break;
        case EXTENDS:
          t = jj_consume_token(EXTENDS);
          break;
        case RETURN:
          t = jj_consume_token(RETURN);
          break;
        case ARRAYOF:
          t = jj_consume_token(ARRAYOF);
          break;
        case BOOLEAN:
          t = jj_consume_token(BOOLEAN);
          break;
        case INT:
          t = jj_consume_token(INT);
          break;
        case IF:
          t = jj_consume_token(IF);
          break;
        case THEN:
          t = jj_consume_token(THEN);
          break;
        case ELSE:
          t = jj_consume_token(ELSE);
          break;
        case WHILE:
          t = jj_consume_token(WHILE);
          break;
        case DO:
          t = jj_consume_token(DO);
          break;
        case OUTPUT:
          t = jj_consume_token(OUTPUT);
          break;
        case LENGTH:
          t = jj_consume_token(LENGTH);
          break;
        case SELF:
          t = jj_consume_token(SELF);
          break;
        case NEW:
          t = jj_consume_token(NEW);
          break;
        case OBJECT:
          t = jj_consume_token(OBJECT);
          break;
        case ISNULL:
          t = jj_consume_token(ISNULL);
          break;
        case OP:
          t = jj_consume_token(OP);
          break;
        case OPENCBA:
          t = jj_consume_token(OPENCBA);
          break;
        case CLOSECBA:
          t = jj_consume_token(CLOSECBA);
          break;
        case OPENRBA:
          t = jj_consume_token(OPENRBA);
          break;
        case CLOSERBA:
          t = jj_consume_token(CLOSERBA);
          break;
        case OPENSBA:
          t = jj_consume_token(OPENSBA);
          break;
        case CLOSESBA:
          t = jj_consume_token(CLOSESBA);
          break;
        case SEMICOL:
          t = jj_consume_token(SEMICOL);
          break;
        case COMMA:
          t = jj_consume_token(COMMA);
          break;
        case EQUAL:
          t = jj_consume_token(EQUAL);
          break;
        case NOT:
          t = jj_consume_token(NOT);
          break;
        case DOT:
          t = jj_consume_token(DOT);
          break;
        default:
          jj_la1[1] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        System.out.println("Recognised as valid token: " + t.image);
        break;
      case INTEGER_LITERAL:
        t = jj_consume_token(INTEGER_LITERAL);
        System.out.println("Recognised as INTEGER_LITERAL: " + t.image);
        break;
      case ID:
        t = jj_consume_token(ID);
        System.out.println("Recognised as ID: " + t.image);
        break;
      default:
        jj_la1[2] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
  }

  final public void nt_Program() throws ParseException {
    label_2:
    while (true) {
      nt_ProcDecl();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PROC:
        ;
        break;
      default:
        jj_la1[3] = jj_gen;
        break label_2;
      }
    }
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case CLASS:
        ;
        break;
      default:
        jj_la1[4] = jj_gen;
        break label_3;
      }
      nt_ClassDecl();
    }
  }

  final public void nt_ClassDecl() throws ParseException {
    jj_consume_token(CLASS);
    jj_consume_token(ID);
    nt_ClassDecl2();
  }

  final public void nt_ClassDecl2() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case OPENCBA:
      jj_consume_token(OPENCBA);
      label_4:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case ARRAYOF:
        case BOOLEAN:
        case INT:
        case ID:
          ;
          break;
        default:
          jj_la1[5] = jj_gen;
          break label_4;
        }
        nt_FieldDecl();
      }
      label_5:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case PROC:
        case FUN:
          ;
          break;
        default:
          jj_la1[6] = jj_gen;
          break label_5;
        }
        nt_MethodDecl();
      }
      jj_consume_token(CLOSECBA);
      break;
    case EXTENDS:
      jj_consume_token(EXTENDS);
      jj_consume_token(ID);
      jj_consume_token(OPENCBA);
      label_6:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case ARRAYOF:
        case BOOLEAN:
        case INT:
        case ID:
          ;
          break;
        default:
          jj_la1[7] = jj_gen;
          break label_6;
        }
        nt_FieldDecl();
      }
      label_7:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case PROC:
        case FUN:
          ;
          break;
        default:
          jj_la1[8] = jj_gen;
          break label_7;
        }
        nt_MethodDecl();
      }
      jj_consume_token(CLOSECBA);
      break;
    default:
      jj_la1[9] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void nt_FieldDecl() throws ParseException {
    nt_Type();
    jj_consume_token(ID);
    jj_consume_token(SEMICOL);
  }

  final public void nt_MethodDecl() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PROC:
      nt_ProcDecl();
      break;
    case FUN:
      nt_FunDecl();
      break;
    default:
      jj_la1[10] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void nt_ProcDecl() throws ParseException {
    jj_consume_token(PROC);
    jj_consume_token(ID);
    jj_consume_token(OPENRBA);
    nt_FormalList();
    jj_consume_token(CLOSERBA);
    jj_consume_token(OPENCBA);
    label_8:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case TRUE:
      case FALSE:
      case ARRAYOF:
      case BOOLEAN:
      case INT:
      case IF:
      case WHILE:
      case OUTPUT:
      case SELF:
      case NEW:
      case ISNULL:
      case OPENCBA:
      case OPENRBA:
      case NOT:
      case INTEGER_LITERAL:
      case ID:
        ;
        break;
      default:
        jj_la1[11] = jj_gen;
        break label_8;
      }
      nt_Statement();
    }
    jj_consume_token(CLOSECBA);
  }

  final public void nt_FunDecl() throws ParseException {
    jj_consume_token(FUN);
    nt_Type();
    jj_consume_token(ID);
    jj_consume_token(OPENRBA);
    nt_FormalList();
    jj_consume_token(CLOSERBA);
    jj_consume_token(OPENCBA);
    label_9:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case TRUE:
      case FALSE:
      case ARRAYOF:
      case BOOLEAN:
      case INT:
      case IF:
      case WHILE:
      case OUTPUT:
      case SELF:
      case NEW:
      case ISNULL:
      case OPENCBA:
      case OPENRBA:
      case NOT:
      case INTEGER_LITERAL:
      case ID:
        ;
        break;
      default:
        jj_la1[12] = jj_gen;
        break label_9;
      }
      nt_Statement();
    }
    jj_consume_token(RETURN);
    nt_Exp();
    jj_consume_token(SEMICOL);
    jj_consume_token(CLOSECBA);
  }

  final public void nt_FormalList() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ARRAYOF:
    case BOOLEAN:
    case INT:
    case ID:
      nt_Type();
      jj_consume_token(ID);
      label_10:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case COMMA:
          ;
          break;
        default:
          jj_la1[13] = jj_gen;
          break label_10;
        }
        nt_FormerRest();
      }
      break;
    default:
      jj_la1[14] = jj_gen;

    }
  }

  final public void nt_FormerRest() throws ParseException {
    jj_consume_token(COMMA);
    nt_Type();
    jj_consume_token(ID);
  }

  final public void nt_Type() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ARRAYOF:
      jj_consume_token(ARRAYOF);
      jj_consume_token(OPENRBA);
      nt_Type();
      jj_consume_token(CLOSERBA);
      break;
    case BOOLEAN:
      jj_consume_token(BOOLEAN);
      break;
    case INT:
      jj_consume_token(INT);
      break;
    case ID:
      jj_consume_token(ID);
      break;
    default:
      jj_la1[15] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void nt_Statement() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case OPENCBA:
      nt_Block();
      break;
    default:
      jj_la1[17] = jj_gen;
      if (jj_2_1(2)) {
        nt_Type();
        jj_consume_token(ID);
        jj_consume_token(SEMICOL);
      } else if (jj_2_2(2)) {
        nt_Var();
        jj_consume_token(EQUAL);
        nt_Exp();
        jj_consume_token(SEMICOL);
      } else if (jj_2_3(2)) {
        nt_PrimaryExp();
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case OPENSBA:
          jj_consume_token(OPENSBA);
          nt_Exp();
          jj_consume_token(CLOSESBA);
          jj_consume_token(EQUAL);
          nt_Exp();
          jj_consume_token(SEMICOL);
          break;
        case DOT:
          jj_consume_token(DOT);
          jj_consume_token(ID);
          jj_consume_token(OPENRBA);
          nt_ExpList();
          jj_consume_token(CLOSERBA);
          jj_consume_token(SEMICOL);
          break;
        default:
          jj_la1[16] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      } else {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case IF:
          jj_consume_token(IF);
          jj_consume_token(OPENRBA);
          nt_Exp();
          jj_consume_token(CLOSERBA);
          jj_consume_token(THEN);
          nt_Block();
          jj_consume_token(ELSE);
          nt_Block();
          break;
        case WHILE:
          jj_consume_token(WHILE);
          jj_consume_token(OPENRBA);
          nt_Exp();
          jj_consume_token(CLOSERBA);
          jj_consume_token(DO);
          nt_Block();
          break;
        case OUTPUT:
          jj_consume_token(OUTPUT);
          nt_Exp();
          jj_consume_token(SEMICOL);
          break;
        default:
          jj_la1[18] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    }
  }

  final public void nt_Block() throws ParseException {
    jj_consume_token(OPENCBA);
    label_11:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case TRUE:
      case FALSE:
      case ARRAYOF:
      case BOOLEAN:
      case INT:
      case IF:
      case WHILE:
      case OUTPUT:
      case SELF:
      case NEW:
      case ISNULL:
      case OPENCBA:
      case OPENRBA:
      case NOT:
      case INTEGER_LITERAL:
      case ID:
        ;
        break;
      default:
        jj_la1[19] = jj_gen;
        break label_11;
      }
      nt_Statement();
    }
    jj_consume_token(CLOSECBA);
  }

  final public void nt_Exp() throws ParseException {
    nt_PrimaryExp();
    nt_Exp2();
  }

  final public void nt_Exp2() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case OP:
      jj_consume_token(OP);
      nt_PrimaryExp();
      break;
    case OPENSBA:
      jj_consume_token(OPENSBA);
      nt_Exp();
      jj_consume_token(CLOSESBA);
      break;
    case DOT:
      jj_consume_token(DOT);
      nt_Exp3();
      break;
    default:
      jj_la1[20] = jj_gen;

    }
  }

  final public void nt_Exp3() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LENGTH:
      jj_consume_token(LENGTH);
      break;
    case ID:
      jj_consume_token(ID);
      jj_consume_token(OPENRBA);
      nt_ExpList();
      jj_consume_token(CLOSERBA);
      break;
    default:
      jj_la1[21] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void nt_PrimaryExp() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INTEGER_LITERAL:
      jj_consume_token(INTEGER_LITERAL);
      break;
    case TRUE:
      jj_consume_token(TRUE);
      break;
    case FALSE:
      jj_consume_token(FALSE);
      break;
    case ID:
      nt_Var();
      break;
    case SELF:
      jj_consume_token(SELF);
      break;
    default:
      jj_la1[22] = jj_gen;
      if (jj_2_4(2)) {
        jj_consume_token(NEW);
        jj_consume_token(ARRAYOF);
        jj_consume_token(OPENRBA);
        nt_Type();
        jj_consume_token(CLOSERBA);
        jj_consume_token(OPENSBA);
        nt_Exp();
        jj_consume_token(CLOSESBA);
      } else {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case NEW:
          jj_consume_token(NEW);
          jj_consume_token(OBJECT);
          jj_consume_token(ID);
          jj_consume_token(OPENRBA);
          nt_ExpList();
          jj_consume_token(CLOSERBA);
          break;
        case NOT:
          jj_consume_token(NOT);
          nt_PrimaryExp();
          break;
        case ISNULL:
          jj_consume_token(ISNULL);
          nt_PrimaryExp();
          break;
        case OPENRBA:
          jj_consume_token(OPENRBA);
          nt_Exp();
          jj_consume_token(CLOSERBA);
          break;
        default:
          jj_la1[23] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    }
  }

  final public void nt_Var() throws ParseException {
    jj_consume_token(ID);
  }

  final public void nt_ExpList() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case TRUE:
    case FALSE:
    case SELF:
    case NEW:
    case ISNULL:
    case OPENRBA:
    case NOT:
    case INTEGER_LITERAL:
    case ID:
      nt_Exp();
      label_12:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case COMMA:
          ;
          break;
        default:
          jj_la1[24] = jj_gen;
          break label_12;
        }
        nt_ExpRest();
      }
      break;
    default:
      jj_la1[25] = jj_gen;

    }
  }

  final public void nt_ExpRest() throws ParseException {
    jj_consume_token(COMMA);
    nt_Exp();
  }

  final public void eof() throws ParseException {
    jj_consume_token(0);
  }

  private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  private boolean jj_2_2(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_2(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  private boolean jj_2_3(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_3(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(2, xla); }
  }

  private boolean jj_2_4(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_4(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(3, xla); }
  }

  private boolean jj_3_4() {
    if (jj_scan_token(NEW)) return true;
    if (jj_scan_token(ARRAYOF)) return true;
    return false;
  }

  private boolean jj_3_1() {
    if (jj_3R_13()) return true;
    if (jj_scan_token(ID)) return true;
    return false;
  }

  private boolean jj_3R_15() {
    if (jj_scan_token(OPENSBA)) return true;
    return false;
  }

  private boolean jj_3R_22() {
    if (jj_3R_14()) return true;
    return false;
  }

  private boolean jj_3R_14() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_scan_token(41)) {
    jj_scanpos = xsp;
    if (jj_scan_token(11)) {
    jj_scanpos = xsp;
    if (jj_scan_token(12)) {
    jj_scanpos = xsp;
    if (jj_scan_token(42)) {
    jj_scanpos = xsp;
    if (jj_scan_token(25)) {
    jj_scanpos = xsp;
    if (jj_3_4()) {
    jj_scanpos = xsp;
    if (jj_3R_18()) {
    jj_scanpos = xsp;
    if (jj_3R_19()) {
    jj_scanpos = xsp;
    if (jj_3R_20()) {
    jj_scanpos = xsp;
    if (jj_3R_21()) return true;
    }
    }
    }
    }
    }
    }
    }
    }
    }
    return false;
  }

  private boolean jj_3R_16() {
    if (jj_scan_token(DOT)) return true;
    return false;
  }

  private boolean jj_3R_17() {
    if (jj_scan_token(ARRAYOF)) return true;
    if (jj_scan_token(OPENRBA)) return true;
    return false;
  }

  private boolean jj_3R_13() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_17()) {
    jj_scanpos = xsp;
    if (jj_scan_token(16)) {
    jj_scanpos = xsp;
    if (jj_scan_token(17)) {
    jj_scanpos = xsp;
    if (jj_scan_token(42)) return true;
    }
    }
    }
    return false;
  }

  private boolean jj_3R_21() {
    if (jj_scan_token(OPENRBA)) return true;
    if (jj_3R_22()) return true;
    return false;
  }

  private boolean jj_3R_20() {
    if (jj_scan_token(ISNULL)) return true;
    if (jj_3R_14()) return true;
    return false;
  }

  private boolean jj_3R_19() {
    if (jj_scan_token(NOT)) return true;
    if (jj_3R_14()) return true;
    return false;
  }

  private boolean jj_3_3() {
    if (jj_3R_14()) return true;
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_15()) {
    jj_scanpos = xsp;
    if (jj_3R_16()) return true;
    }
    return false;
  }

  private boolean jj_3R_18() {
    if (jj_scan_token(NEW)) return true;
    if (jj_scan_token(OBJECT)) return true;
    return false;
  }

  private boolean jj_3_2() {
    if (jj_scan_token(42)) return true;
    if (jj_scan_token(EQUAL)) return true;
    return false;
  }

  /** Generated Token Manager. */
  public MooplParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  private int jj_gen;
  final private int[] jj_la1 = new int[26];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0xffffff00,0xffffff00,0xffffff00,0x100,0x400,0x38000,0x300,0x38000,0x300,0x40002000,0x300,0x56a79800,0x56a79800,0x0,0x38000,0x38000,0x0,0x40000000,0xa40000,0x56a79800,0x20000000,0x1000000,0x2001800,0x14000000,0x0,0x16001800,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x7ff,0x1ff,0x7ff,0x0,0x0,0x400,0x0,0x400,0x0,0x0,0x0,0x681,0x681,0x20,0x400,0x400,0x104,0x0,0x0,0x681,0x104,0x400,0x600,0x81,0x20,0x681,};
   }
  final private JJCalls[] jj_2_rtns = new JJCalls[4];
  private boolean jj_rescan = false;
  private int jj_gc = 0;

  /** Constructor with InputStream. */
  public MooplParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public MooplParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new MooplParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 26; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 26; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public MooplParser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new MooplParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 26; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 26; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public MooplParser(MooplParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 26; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(MooplParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 26; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;
  private int[] jj_lasttokens = new int[100];
  private int jj_endpos;

  private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              continue jj_entries_loop;
            }
          }
          jj_expentries.add(jj_expentry);
          break jj_entries_loop;
        }
      }
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[43];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 26; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 43; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

  private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 4; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
            case 1: jj_3_2(); break;
            case 2: jj_3_3(); break;
            case 3: jj_3_4(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

  }
