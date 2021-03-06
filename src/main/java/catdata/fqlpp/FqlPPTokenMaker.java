/* The following code was generated by JFlex 1.4.3 on 3/8/16 3:04 PM */

/*
 * Generated on 3/8/16 3:04 PM
 */
package catdata.fqlpp;

import java.io.*;
import javax.swing.text.Segment;

import org.fife.ui.rsyntaxtextarea.*;


/**
 * 
 */
@SuppressWarnings({"unused"})
public class FqlPPTokenMaker extends AbstractJFlexCTokenMaker {

  /** This character denotes the end of file */
  private static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  private static final int EOL_COMMENT = 4;
  private static final int YYINITIAL = 0;
  private static final int MLC = 2;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1,  1,  2, 2
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\17\1\7\1\0\1\17\1\15\22\0\1\17\1\24\1\14"+
    "\1\16\1\1\1\24\1\24\1\6\1\25\1\25\1\21\1\67\1\24"+
    "\1\70\1\23\1\20\1\4\1\64\1\64\1\4\4\4\2\3\1\36"+
    "\1\24\1\15\1\72\1\71\1\24\1\16\1\47\1\5\1\53\3\5"+
    "\5\1\1\51\3\1\1\50\1\1\1\55\1\56\1\1\1\54\3\1"+
    "\1\52\1\1\1\25\1\10\1\25\1\73\1\2\1\0\1\41\1\13"+
    "\1\40\1\57\1\35\1\32\1\42\1\26\1\33\1\65\1\60\1\34"+
    "\1\46\1\12\1\43\1\30\1\61\1\44\1\31\1\27\1\11\1\62"+
    "\1\37\1\63\1\45\1\66\1\22\1\73\1\22\1\24\uff81\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\3\0\2\1\1\2\1\3\2\1\1\4\1\5\1\1"+
    "\1\6\1\7\23\1\1\6\1\10\1\11\5\10\1\12"+
    "\3\10\1\0\1\13\2\1\2\4\1\14\1\15\1\16"+
    "\1\17\1\1\1\17\10\1\1\17\30\1\1\20\11\0"+
    "\3\1\1\4\1\21\1\4\5\1\1\22\33\1\11\0"+
    "\2\1\1\4\23\1\2\0\1\23\2\0\1\24\1\0"+
    "\1\4\10\1\1\17\3\1\5\0\1\4\23\1";

  @SuppressWarnings("UnusedAssignment")
  private static int [] zzUnpackAction() {
    int [] result = new int[215];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int... result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\74\0\170\0\264\0\360\0\u012c\0\264\0\u0168"+
    "\0\u01a4\0\u01e0\0\u021c\0\u0258\0\264\0\264\0\u0294\0\u02d0"+
    "\0\u030c\0\u0348\0\u0384\0\u03c0\0\u03fc\0\u0438\0\u0474\0\u04b0"+
    "\0\u04ec\0\u0528\0\u0564\0\u05a0\0\u05dc\0\u0618\0\u0654\0\u0690"+
    "\0\u06cc\0\u06cc\0\u0708\0\264\0\u0744\0\u0780\0\u07bc\0\u07f8"+
    "\0\u0834\0\264\0\u0870\0\u08ac\0\u08e8\0\u0924\0\u0960\0\u099c"+
    "\0\u09d8\0\u0a14\0\u0a50\0\264\0\264\0\264\0\360\0\u0a8c"+
    "\0\u0ac8\0\u0b04\0\u0b40\0\u0b7c\0\u0bb8\0\u0bf4\0\u0c30\0\u0c6c"+
    "\0\u0ca8\0\u0ce4\0\u0d20\0\u0d5c\0\u0d98\0\u0dd4\0\u0e10\0\u0e4c"+
    "\0\u0e88\0\u0ec4\0\u0f00\0\u0f3c\0\u0f78\0\u0fb4\0\u0ff0\0\u102c"+
    "\0\u1068\0\u10a4\0\u10e0\0\u111c\0\u1158\0\u1194\0\u11d0\0\u120c"+
    "\0\u1248\0\u1284\0\264\0\u12c0\0\u12fc\0\u1338\0\u1374\0\u13b0"+
    "\0\u13ec\0\u1428\0\u1464\0\u14a0\0\u14dc\0\u1518\0\u1554\0\u1590"+
    "\0\264\0\u15cc\0\u1608\0\u1644\0\u1680\0\u16bc\0\u16f8\0\360"+
    "\0\u1734\0\u1770\0\u17ac\0\u17e8\0\u1824\0\u1860\0\u189c\0\u18d8"+
    "\0\u1914\0\u1950\0\u198c\0\u19c8\0\u1a04\0\u1a40\0\u1a7c\0\u1ab8"+
    "\0\u1af4\0\u1b30\0\u1b6c\0\u1ba8\0\u1be4\0\u1c20\0\u1c5c\0\u1c98"+
    "\0\u1cd4\0\u1d10\0\u1d4c\0\u1d88\0\u1dc4\0\u1e00\0\u1e3c\0\u1e78"+
    "\0\u1eb4\0\u1ef0\0\u1f2c\0\u1f68\0\u0ac8\0\u1fa4\0\u1fe0\0\u201c"+
    "\0\u2058\0\u2094\0\u20d0\0\u210c\0\u2148\0\u2184\0\u21c0\0\u21fc"+
    "\0\u2238\0\u2274\0\u22b0\0\u22ec\0\u2328\0\u2364\0\u23a0\0\u23dc"+
    "\0\u2418\0\u2454\0\u2490\0\u24cc\0\u2508\0\u2544\0\u2580\0\u25bc"+
    "\0\u25f8\0\u2634\0\u2670\0\u26ac\0\u26e8\0\u2724\0\u2760\0\u279c"+
    "\0\u27d8\0\u2814\0\u279c\0\u2850\0\u288c\0\u28c8\0\u2904\0\u2508"+
    "\0\u2940\0\u25bc\0\u297c\0\u29b8\0\u29f4\0\u2a30\0\u2a6c\0\u2aa8"+
    "\0\u2ae4\0\u2b20\0\u2b5c\0\u2b98\0\u2bd4\0\u2c10\0\u2c4c\0\u2c88"+
    "\0\u2cc4\0\u2d00\0\u2d3c\0\u2d78\0\u2db4\0\u2df0\0\u2e2c";

  @SuppressWarnings("UnusedAssignment")
  private static int [] zzUnpackRowMap() {
    int [] result = new int[215];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int... result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\4\2\5\2\6\1\5\1\4\1\7\1\4\1\10"+
    "\1\11\1\5\1\12\2\4\1\13\1\14\1\15\1\16"+
    "\2\4\1\16\1\5\1\17\1\20\1\21\1\22\1\23"+
    "\1\24\1\25\1\15\1\26\1\27\1\30\1\5\1\31"+
    "\1\32\2\5\1\33\3\5\1\34\2\5\1\35\1\36"+
    "\1\37\1\5\1\40\1\5\1\6\2\5\1\15\1\41"+
    "\1\4\1\42\1\15\7\43\1\44\11\43\1\45\4\43"+
    "\1\46\3\43\1\47\4\43\1\50\34\43\7\51\1\52"+
    "\16\51\1\53\3\51\1\54\4\51\1\55\34\51\75\0"+
    "\5\5\2\0\1\56\3\5\12\0\10\5\1\0\30\5"+
    "\5\0\3\57\2\6\1\57\2\0\4\57\2\0\1\57"+
    "\7\0\10\57\1\0\25\57\1\6\2\57\6\0\5\5"+
    "\2\0\1\56\1\5\1\60\1\5\12\0\10\5\1\0"+
    "\30\5\6\0\5\5\2\0\1\56\3\5\12\0\10\5"+
    "\1\0\4\5\1\61\23\5\5\0\7\12\1\62\1\63"+
    "\3\12\1\64\57\12\17\0\1\13\74\0\1\65\1\66"+
    "\53\0\5\5\2\0\1\56\3\5\12\0\1\5\1\67"+
    "\6\5\1\0\5\5\1\70\22\5\6\0\5\5\2\0"+
    "\1\56\3\5\12\0\5\5\1\71\2\5\1\0\2\5"+
    "\1\72\2\5\1\73\22\5\6\0\5\5\2\0\1\56"+
    "\1\5\1\74\1\5\12\0\5\5\1\75\1\5\1\76"+
    "\1\0\30\5\6\0\5\5\2\0\1\56\1\77\2\5"+
    "\12\0\3\5\1\61\1\67\3\5\1\0\2\5\1\100"+
    "\1\5\1\101\23\5\6\0\5\5\2\0\1\56\1\5"+
    "\1\102\1\5\12\0\3\5\1\103\4\5\1\0\7\5"+
    "\1\104\10\5\1\67\7\5\6\0\5\5\2\0\1\56"+
    "\3\5\12\0\7\5\1\105\1\0\30\5\6\0\5\5"+
    "\2\0\1\56\3\5\12\0\10\5\1\0\22\5\1\106"+
    "\1\107\1\110\3\5\6\0\5\5\2\0\1\56\3\5"+
    "\12\0\1\111\7\5\1\0\30\5\6\0\5\5\2\0"+
    "\1\56\1\112\2\5\12\0\1\113\7\5\1\0\2\5"+
    "\1\114\1\5\1\115\23\5\6\0\5\5\2\0\1\56"+
    "\1\5\1\74\1\5\12\0\2\5\1\116\5\5\1\0"+
    "\5\5\1\117\22\5\6\0\5\5\2\0\1\56\1\5"+
    "\1\67\1\120\12\0\10\5\1\0\5\5\1\67\22\5"+
    "\6\0\5\5\2\0\1\56\3\5\12\0\5\5\1\121"+
    "\1\5\1\122\1\0\2\5\1\123\25\5\6\0\5\5"+
    "\2\0\1\56\3\5\12\0\10\5\1\0\11\5\1\124"+
    "\16\5\6\0\5\5\2\0\1\56\3\5\12\0\10\5"+
    "\1\0\2\5\1\61\12\5\1\125\12\5\6\0\5\5"+
    "\2\0\1\56\3\5\12\0\7\5\1\61\1\0\30\5"+
    "\6\0\5\5\2\0\1\56\3\5\12\0\7\5\1\126"+
    "\1\0\4\5\1\127\23\5\6\0\5\5\2\0\1\56"+
    "\3\5\12\0\6\5\1\130\1\131\1\0\30\5\6\0"+
    "\5\5\2\0\1\56\3\5\12\0\10\5\1\0\4\5"+
    "\1\132\23\5\76\0\1\15\2\0\7\43\1\0\11\43"+
    "\1\0\4\43\1\0\3\43\1\0\4\43\1\0\34\43"+
    "\20\0\1\133\102\0\1\134\73\0\1\135\3\0\1\136"+
    "\77\0\1\137\34\0\7\51\1\0\16\51\1\0\3\51"+
    "\1\0\4\51\1\0\34\51\27\0\1\140\73\0\1\141"+
    "\3\0\1\142\77\0\1\143\45\0\1\144\62\0\6\57"+
    "\2\0\4\57\2\0\1\57\7\0\10\57\1\0\30\57"+
    "\6\0\5\5\2\0\1\56\3\5\12\0\2\5\1\145"+
    "\2\5\1\146\2\5\1\0\1\5\1\147\26\5\6\0"+
    "\5\5\2\0\1\56\3\5\12\0\1\5\1\67\6\5"+
    "\1\0\30\5\5\0\10\62\1\150\3\62\1\151\63\62"+
    "\1\12\1\62\1\12\1\0\1\12\1\152\3\12\12\62"+
    "\1\12\2\62\1\12\11\62\1\12\17\62\1\12\7\62"+
    "\1\0\5\5\2\0\1\56\1\153\2\5\12\0\10\5"+
    "\1\0\2\5\1\154\25\5\6\0\5\5\2\0\1\56"+
    "\3\5\12\0\10\5\1\0\23\5\1\11\4\5\6\0"+
    "\5\5\2\0\1\56\3\5\12\0\1\5\1\155\6\5"+
    "\1\0\30\5\6\0\5\5\2\0\1\56\3\5\12\0"+
    "\10\5\1\0\4\5\1\156\23\5\6\0\5\5\2\0"+
    "\1\56\3\5\12\0\10\5\1\0\20\5\1\67\7\5"+
    "\6\0\5\5\2\0\1\56\3\5\12\0\10\5\1\0"+
    "\3\5\1\157\24\5\6\0\5\5\2\0\1\56\3\5"+
    "\12\0\1\5\1\160\6\5\1\0\30\5\6\0\5\5"+
    "\2\0\1\56\1\5\1\161\1\5\12\0\10\5\1\0"+
    "\30\5\6\0\5\5\2\0\1\56\3\5\12\0\6\5"+
    "\1\162\1\5\1\0\30\5\6\0\5\5\2\0\1\56"+
    "\3\5\12\0\10\5\1\0\5\5\1\163\22\5\6\0"+
    "\5\5\2\0\1\56\3\5\12\0\1\5\1\164\4\5"+
    "\1\67\1\5\1\0\5\5\1\67\22\5\6\0\5\5"+
    "\2\0\1\56\3\5\12\0\10\5\1\0\4\5\1\165"+
    "\23\5\6\0\5\5\2\0\1\56\3\5\12\0\2\5"+
    "\1\166\5\5\1\0\30\5\6\0\5\5\2\0\1\56"+
    "\3\5\12\0\4\5\1\61\3\5\1\0\30\5\6\0"+
    "\5\5\2\0\1\56\1\167\2\5\12\0\10\5\1\0"+
    "\30\5\6\0\5\5\2\0\1\56\3\5\12\0\10\5"+
    "\1\0\2\5\1\170\25\5\6\0\5\5\2\0\1\56"+
    "\3\5\12\0\5\5\1\171\2\5\1\0\30\5\6\0"+
    "\5\5\2\0\1\56\3\5\12\0\5\5\1\172\2\5"+
    "\1\0\30\5\6\0\5\5\2\0\1\56\3\5\12\0"+
    "\10\5\1\0\5\5\1\173\22\5\6\0\5\5\2\0"+
    "\1\56\3\5\12\0\10\5\1\0\2\5\1\174\25\5"+
    "\6\0\5\5\2\0\1\56\3\5\12\0\1\5\1\175"+
    "\6\5\1\0\30\5\6\0\5\5\2\0\1\56\3\5"+
    "\12\0\10\5\1\0\5\5\1\176\12\5\1\67\1\177"+
    "\6\5\6\0\5\5\2\0\1\56\3\5\12\0\2\5"+
    "\1\200\5\5\1\0\30\5\6\0\5\5\2\0\1\56"+
    "\3\5\12\0\10\5\1\0\5\5\1\201\22\5\6\0"+
    "\5\5\2\0\1\56\3\5\12\0\10\5\1\0\26\5"+
    "\1\202\1\5\6\0\5\5\2\0\1\56\3\5\12\0"+
    "\10\5\1\0\3\5\1\203\24\5\6\0\5\5\2\0"+
    "\1\56\3\5\12\0\1\5\1\204\4\5\1\205\1\5"+
    "\1\0\30\5\6\0\5\5\2\0\1\56\1\5\1\206"+
    "\1\5\12\0\10\5\1\0\30\5\6\0\5\5\2\0"+
    "\1\56\3\5\12\0\10\5\1\0\11\5\1\207\16\5"+
    "\6\0\5\5\2\0\1\56\3\5\12\0\10\5\1\0"+
    "\16\5\1\210\11\5\6\0\5\5\2\0\1\56\3\5"+
    "\12\0\6\5\1\211\1\5\1\0\30\5\6\0\5\5"+
    "\2\0\1\56\3\5\12\0\10\5\1\0\7\5\1\67"+
    "\20\5\6\0\5\5\2\0\1\56\3\5\12\0\7\5"+
    "\1\212\1\0\30\5\6\0\5\5\2\0\1\56\3\5"+
    "\12\0\10\5\1\0\5\5\1\213\22\5\6\0\5\5"+
    "\2\0\1\56\3\5\12\0\5\5\1\74\2\5\1\0"+
    "\30\5\34\0\1\214\74\0\1\215\77\0\1\216\76\0"+
    "\1\217\63\0\1\220\74\0\1\221\77\0\1\222\76\0"+
    "\1\223\37\0\3\224\5\0\1\224\16\0\1\224\2\0"+
    "\1\224\2\0\2\224\5\0\1\224\3\0\1\224\3\0"+
    "\1\224\4\0\1\224\10\0\5\5\2\0\1\56\3\5"+
    "\12\0\5\5\1\225\2\5\1\0\30\5\6\0\5\5"+
    "\2\0\1\56\3\5\12\0\1\5\1\67\6\5\1\0"+
    "\4\5\1\226\23\5\6\0\5\5\2\0\1\56\1\112"+
    "\2\5\12\0\10\5\1\0\30\5\5\0\7\62\1\0"+
    "\67\62\3\227\2\62\1\150\2\62\1\227\1\151\15\62"+
    "\1\227\2\62\1\227\2\62\2\227\5\62\1\227\3\62"+
    "\1\227\3\62\1\227\4\62\1\227\7\62\1\0\5\5"+
    "\2\0\1\56\3\5\12\0\7\5\1\67\1\0\30\5"+
    "\6\0\5\5\2\0\1\56\1\5\1\230\1\5\12\0"+
    "\10\5\1\0\30\5\6\0\5\5\2\0\1\56\3\5"+
    "\12\0\1\67\7\5\1\0\30\5\6\0\5\5\2\0"+
    "\1\56\3\5\12\0\2\5\1\67\5\5\1\0\30\5"+
    "\6\0\5\5\2\0\1\56\3\5\12\0\10\5\1\0"+
    "\7\5\1\231\20\5\6\0\5\5\2\0\1\56\3\5"+
    "\12\0\10\5\1\0\1\5\1\232\26\5\6\0\5\5"+
    "\2\0\1\56\3\5\12\0\3\5\1\153\4\5\1\0"+
    "\30\5\6\0\5\5\2\0\1\56\3\5\12\0\10\5"+
    "\1\0\2\5\1\233\25\5\6\0\5\5\2\0\1\56"+
    "\3\5\12\0\7\5\1\234\1\0\30\5\6\0\5\5"+
    "\2\0\1\56\3\5\12\0\10\5\1\0\25\5\1\67"+
    "\2\5\6\0\5\5\2\0\1\56\3\5\12\0\6\5"+
    "\1\235\1\5\1\0\30\5\6\0\5\5\2\0\1\56"+
    "\3\5\12\0\10\5\1\0\2\5\1\236\25\5\6\0"+
    "\5\5\2\0\1\56\3\5\12\0\6\5\1\67\1\5"+
    "\1\0\30\5\6\0\5\5\2\0\1\56\3\5\12\0"+
    "\3\5\1\237\4\5\1\0\30\5\6\0\5\5\2\0"+
    "\1\56\3\5\12\0\3\5\1\240\4\5\1\0\30\5"+
    "\6\0\5\5\2\0\1\56\3\5\12\0\10\5\1\0"+
    "\5\5\1\241\22\5\6\0\5\5\2\0\1\56\3\5"+
    "\12\0\10\5\1\0\5\5\1\67\22\5\6\0\5\5"+
    "\2\0\1\56\3\5\12\0\7\5\1\242\1\0\30\5"+
    "\6\0\5\5\2\0\1\56\3\5\12\0\7\5\1\243"+
    "\1\0\30\5\6\0\5\5\2\0\1\56\3\5\12\0"+
    "\6\5\1\130\1\5\1\0\30\5\6\0\5\5\2\0"+
    "\1\56\3\5\12\0\6\5\1\241\1\5\1\0\30\5"+
    "\6\0\5\5\2\0\1\56\3\5\12\0\10\5\1\0"+
    "\4\5\1\244\23\5\6\0\5\5\2\0\1\56\3\5"+
    "\12\0\7\5\1\245\1\0\30\5\6\0\5\5\2\0"+
    "\1\56\3\5\12\0\1\61\7\5\1\0\30\5\6\0"+
    "\5\5\2\0\1\56\1\246\2\5\12\0\10\5\1\0"+
    "\30\5\6\0\5\5\2\0\1\56\3\5\12\0\10\5"+
    "\1\0\2\5\1\247\25\5\6\0\5\5\2\0\1\56"+
    "\3\5\12\0\10\5\1\0\3\5\1\153\24\5\6\0"+
    "\5\5\2\0\1\56\3\5\12\0\10\5\1\0\12\5"+
    "\1\250\15\5\6\0\5\5\2\0\1\56\3\5\12\0"+
    "\10\5\1\0\16\5\1\250\11\5\6\0\5\5\2\0"+
    "\1\56\3\5\12\0\1\5\1\231\6\5\1\0\30\5"+
    "\6\0\5\5\2\0\1\56\3\5\12\0\5\5\1\251"+
    "\2\5\1\0\30\5\6\0\5\5\2\0\1\56\1\5"+
    "\1\252\1\5\12\0\10\5\1\0\30\5\35\0\1\253"+
    "\101\0\1\254\72\0\1\215\61\0\1\255\100\0\1\256"+
    "\101\0\1\257\72\0\1\221\61\0\1\260\53\0\3\261"+
    "\5\0\1\261\16\0\1\261\2\0\1\261\2\0\2\261"+
    "\5\0\1\261\3\0\1\261\3\0\1\261\4\0\1\261"+
    "\10\0\5\5\2\0\1\56\1\5\1\67\1\5\12\0"+
    "\10\5\1\0\30\5\5\0\3\62\3\262\2\62\1\150"+
    "\2\62\1\262\1\151\15\62\1\262\2\62\1\262\2\62"+
    "\2\262\5\62\1\262\3\62\1\262\3\62\1\262\4\62"+
    "\1\262\7\62\1\0\5\5\2\0\1\56\3\5\12\0"+
    "\3\5\1\263\4\5\1\0\30\5\6\0\5\5\2\0"+
    "\1\56\3\5\12\0\10\5\1\0\2\5\1\67\25\5"+
    "\6\0\5\5\2\0\1\56\3\5\12\0\1\5\1\264"+
    "\6\5\1\0\30\5\6\0\5\5\2\0\1\56\3\5"+
    "\12\0\6\5\1\170\1\5\1\0\30\5\6\0\5\5"+
    "\2\0\1\56\3\5\12\0\10\5\1\0\5\5\1\265"+
    "\22\5\6\0\5\5\2\0\1\56\3\5\12\0\5\5"+
    "\1\266\2\5\1\0\30\5\6\0\5\5\2\0\1\56"+
    "\3\5\12\0\1\5\1\267\6\5\1\0\30\5\6\0"+
    "\5\5\2\0\1\56\3\5\12\0\1\5\1\270\6\5"+
    "\1\0\30\5\6\0\5\5\2\0\1\56\3\5\12\0"+
    "\10\5\1\0\21\5\1\271\6\5\6\0\5\5\2\0"+
    "\1\56\3\5\12\0\10\5\1\0\6\5\1\67\21\5"+
    "\6\0\5\5\2\0\1\56\3\5\12\0\10\5\1\0"+
    "\3\5\1\272\24\5\6\0\5\5\2\0\1\56\3\5"+
    "\12\0\1\5\1\204\6\5\1\0\30\5\6\0\5\5"+
    "\2\0\1\56\3\5\12\0\10\5\1\0\1\273\27\5"+
    "\6\0\5\5\2\0\1\56\3\5\12\0\10\5\1\0"+
    "\1\5\1\274\26\5\6\0\5\5\2\0\1\56\3\5"+
    "\12\0\10\5\1\0\5\5\1\226\22\5\6\0\5\5"+
    "\2\0\1\56\3\5\12\0\1\5\1\275\6\5\1\0"+
    "\30\5\6\0\5\5\2\0\1\56\3\5\12\0\10\5"+
    "\1\0\13\5\1\67\14\5\6\0\5\5\2\0\1\56"+
    "\3\5\12\0\3\5\1\276\4\5\1\0\30\5\6\0"+
    "\5\5\2\0\1\56\3\5\12\0\7\5\1\170\1\0"+
    "\30\5\36\0\1\215\4\0\1\254\55\0\1\277\54\0"+
    "\1\255\1\300\3\255\1\300\2\0\3\255\2\0\1\300"+
    "\1\0\1\255\1\300\1\0\3\300\10\255\1\300\30\255"+
    "\2\300\1\0\1\300\32\0\1\221\4\0\1\257\55\0"+
    "\1\301\54\0\1\260\1\302\3\260\1\302\2\0\3\260"+
    "\2\0\1\302\1\0\1\260\1\302\1\0\3\302\10\260"+
    "\1\302\30\260\2\302\1\0\1\302\4\0\3\303\5\0"+
    "\1\303\16\0\1\303\2\0\1\303\2\0\2\303\5\0"+
    "\1\303\3\0\1\303\3\0\1\303\4\0\1\303\7\0"+
    "\3\62\3\304\2\62\1\150\2\62\1\304\1\151\15\62"+
    "\1\304\2\62\1\304\2\62\2\304\5\62\1\304\3\62"+
    "\1\304\3\62\1\304\4\62\1\304\7\62\1\0\5\5"+
    "\2\0\1\56\3\5\12\0\4\5\1\305\3\5\1\0"+
    "\30\5\6\0\5\5\2\0\1\56\3\5\12\0\5\5"+
    "\1\306\2\5\1\0\4\5\1\307\23\5\6\0\5\5"+
    "\2\0\1\56\3\5\12\0\3\5\1\310\4\5\1\0"+
    "\30\5\6\0\5\5\2\0\1\56\3\5\12\0\7\5"+
    "\1\270\1\0\30\5\6\0\5\5\2\0\1\56\3\5"+
    "\12\0\5\5\1\311\2\5\1\0\30\5\6\0\5\5"+
    "\2\0\1\56\3\5\12\0\3\5\1\67\4\5\1\0"+
    "\30\5\6\0\5\5\2\0\1\56\3\5\12\0\7\5"+
    "\1\174\1\0\30\5\6\0\5\5\2\0\1\56\3\5"+
    "\12\0\10\5\1\0\4\5\1\312\23\5\6\0\5\5"+
    "\2\0\1\56\3\5\12\0\1\5\1\273\6\5\1\0"+
    "\30\5\6\0\5\5\2\0\1\56\3\5\12\0\5\5"+
    "\1\313\2\5\1\0\30\5\6\0\5\5\2\0\1\56"+
    "\3\5\12\0\6\5\1\314\1\5\1\0\30\5\25\0"+
    "\1\255\73\0\1\260\56\0\3\5\5\0\1\5\16\0"+
    "\1\5\2\0\1\5\2\0\2\5\5\0\1\5\3\0"+
    "\1\5\3\0\1\5\4\0\1\5\7\0\3\62\3\12"+
    "\2\62\1\150\2\62\1\12\1\151\15\62\1\12\2\62"+
    "\1\12\2\62\2\12\5\62\1\12\3\62\1\12\3\62"+
    "\1\12\4\62\1\12\7\62\1\0\5\5\2\0\1\56"+
    "\3\5\12\0\10\5\1\0\4\5\1\315\23\5\6\0"+
    "\5\5\2\0\1\56\3\5\12\0\10\5\1\0\4\5"+
    "\1\316\23\5\6\0\5\5\2\0\1\56\3\5\12\0"+
    "\10\5\1\0\5\5\1\160\22\5\6\0\5\5\2\0"+
    "\1\56\3\5\12\0\7\5\1\317\1\0\30\5\6\0"+
    "\5\5\2\0\1\56\3\5\12\0\10\5\1\0\4\5"+
    "\1\320\23\5\6\0\5\5\2\0\1\56\3\5\12\0"+
    "\10\5\1\0\5\5\1\321\22\5\6\0\5\5\2\0"+
    "\1\56\3\5\12\0\10\5\1\0\4\5\1\322\23\5"+
    "\6\0\5\5\2\0\1\56\3\5\12\0\5\5\1\67"+
    "\2\5\1\0\30\5\6\0\5\5\2\0\1\56\3\5"+
    "\12\0\10\5\1\0\5\5\1\323\22\5\6\0\5\5"+
    "\2\0\1\56\1\5\1\160\1\5\12\0\10\5\1\0"+
    "\30\5\6\0\5\5\2\0\1\56\3\5\12\0\10\5"+
    "\1\0\1\5\1\61\26\5\6\0\5\5\2\0\1\56"+
    "\1\5\1\270\1\5\12\0\10\5\1\0\30\5\6\0"+
    "\5\5\2\0\1\56\3\5\12\0\10\5\1\0\6\5"+
    "\1\160\21\5\6\0\5\5\2\0\1\56\1\5\1\324"+
    "\1\5\12\0\10\5\1\0\30\5\6\0\5\5\2\0"+
    "\1\56\3\5\12\0\10\5\1\0\7\5\1\160\20\5"+
    "\6\0\5\5\2\0\1\56\3\5\12\0\10\5\1\0"+
    "\2\5\1\325\25\5\6\0\5\5\2\0\1\56\3\5"+
    "\12\0\6\5\1\326\1\5\1\0\30\5\6\0\5\5"+
    "\2\0\1\56\3\5\12\0\5\5\1\327\2\5\1\0"+
    "\30\5\6\0\5\5\2\0\1\56\3\5\12\0\10\5"+
    "\1\0\27\5\1\153\5\0";

  @SuppressWarnings("UnusedAssignment")
  private static int [] zzUnpackTrans() {
    int [] result = new int[11880];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int... result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\3\0\1\11\2\1\1\11\5\1\2\11\25\1\1\11"+
    "\5\1\1\11\3\1\1\0\5\1\3\11\44\1\1\11"+
    "\11\0\4\1\1\11\42\1\11\0\26\1\2\0\1\1"+
    "\2\0\1\1\1\0\15\1\5\0\24\1";

  @SuppressWarnings("UnusedAssignment")
  private static int [] zzUnpackAttribute() {
    int [] result = new int[215];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int... result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the textposition at the last state to be included in yytext */
  private int zzPushbackPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /* user code: */


	/**
	 * Constructor.  This must be here because JFlex does not generate a
	 * no-parameter constructor.
	 */
	public FqlPPTokenMaker() {
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param tokenType The token's type.
	 * @see #addToken(int, int, int)
	 */
	private void addHyperlinkToken(int start, int end, int tokenType) {
		int so = start + offsetShift;
		addToken(zzBuffer, start,end, tokenType, so, true);
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param tokenType The token's type.
	 */
	private void addToken(int tokenType) {
		addToken(zzStartRead, zzMarkedPos-1, tokenType);
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param tokenType The token's type.
	 * @see #addHyperlinkToken(int, int, int)
	 */
	private void addToken(int start, int end, int tokenType) {
		int so = start + offsetShift;
		addToken(zzBuffer, start,end, tokenType, so, false);
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param array The character array.
	 * @param start The starting offset in the array.
	 * @param end The ending offset in the array.
	 * @param tokenType The token's type.
	 * @param startOffset The offset in the document at which this token
	 *        occurs.
	 * @param hyperlink Whether this token is a hyperlink.
	 */
	@Override
	public void addToken(char[] array, int start, int end, int tokenType,
						int startOffset, boolean hyperlink) {
		super.addToken(array, start,end, tokenType, startOffset, hyperlink);
		zzStartRead = zzMarkedPos;
	}


	/**
	 * Returns the text to place at the beginning and end of a
	 * line to "comment" it in a this programming language.
	 *
	 * @return The start and end strings to add to a line to "comment"
	 *         it out.
	 */
	public static String[] getLineCommentStartAndEnd() {
		return new String[] { "//", null };
	}


	/**
	 * Returns the first token in the linked list of tokens generated
	 * from <code>text</code>.  This method must be implemented by
	 * subclasses so they can correctly implement syntax highlighting.
	 *
	 * @param text The text from which to get tokens.
	 * @param initialTokenType The token type we should start with.
	 * @param startOffset The offset into the document at which
	 *        <code>text</code> starts.
	 * @return The first <code>Token</code> in a linked list representing
	 *         the syntax highlighted text.
	 */
	@SuppressWarnings("UnusedAssignment")
    @Override
	public Token getTokenList(Segment text, int initialTokenType, int startOffset) {

		resetTokenList();
        offsetShift = -text.offset + startOffset;

		// Start off in the proper state.
		int state = TokenTypes.NULL;
		switch (initialTokenType) {
						case TokenTypes.COMMENT_MULTILINE:
				state = MLC;
				start = text.offset;
				break;

			/* No documentation comments */
			default:
				state = TokenTypes.NULL;
		}

		s = text;
        yyreset(zzReader);
        yybegin(state);
        return yylex();


	}


	/**
	 * Refills the input buffer.
	 *
	 * @return      <code>true</code> if EOF was reached, otherwise
	 *              <code>false</code>.
	 */
	private boolean zzRefill() {
		return zzCurrentPos>=s.offset+s.count;
	}


	/**
	 * Resets the scanner to read from a new input stream.
	 * Does not close the old reader.
	 *
	 * All internal variables are reset, the old input stream 
	 * <b>cannot</b> be reused (internal buffer is discarded and lost).
	 * Lexical state is set to <tt>YY_INITIAL</tt>.
	 *
	 * @param reader   the new input stream 
	 */
    private void yyreset(Reader reader) {
		// 's' has been updated.
		zzBuffer = s.array;
		/*
		 * We replaced the line below with the two below it because zzRefill
		 * no longer "refills" the buffer (since the way we do it, it's always
		 * "full" the first time through, since it points to the segment's
		 * array).  So, we assign zzEndRead here.
		 */
		//zzStartRead = zzEndRead = s.offset;
		zzStartRead = s.offset;
		zzEndRead = zzStartRead + s.count - 1;
		zzCurrentPos = zzMarkedPos = zzPushbackPos = s.offset;
		zzLexicalState = YYINITIAL;
		zzReader = reader;
		zzAtBOL  = true;
		zzAtEOF  = false;
	}




  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  private FqlPPTokenMaker(Reader in) {
      zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public FqlPPTokenMaker(InputStream in) {
    this(new InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 178) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Closes the input stream.
   */
  public final void yyclose() throws IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  @Override
public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  private int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private static void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token

   */
  @SuppressWarnings("ConstantConditions")
  private Token yylex() {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 3:
          addNullToken();
          return firstToken;
        case 21: break;
        case 14:
          start = zzMarkedPos-2;
          yybegin(MLC);
        case 22: break;
        case 5:
          addToken(TokenTypes.WHITESPACE);
        case 23: break;
        case 17:
          addToken(TokenTypes.ERROR_STRING_DOUBLE);
        case 24: break;
        case 18:
          addToken(TokenTypes.RESERVED_WORD);
        case 25: break;
        case 7:
          addToken(TokenTypes.SEPARATOR);
        case 26: break;
        case 1:
          addToken(TokenTypes.IDENTIFIER);
        case 27: break;
        case 10:
          addToken(start,zzStartRead-1, TokenTypes.COMMENT_EOL);
          addNullToken();
          return firstToken;
        case 28: break;
        case 13:
          start = zzMarkedPos-2;
          yybegin(EOL_COMMENT);
        case 29: break;
        case 4:
          addToken(TokenTypes.ERROR_STRING_DOUBLE);
          addNullToken();
          return firstToken;
        case 30: break;
        case 16:
          yybegin(YYINITIAL);
          addToken(start,zzStartRead+2-1, TokenTypes.COMMENT_MULTILINE);
        case 31: break;
        case 12:
          addToken(TokenTypes.LITERAL_STRING_DOUBLE_QUOTE);
        case 32: break;
        case 20: 
          { int temp=zzStartRead; addToken(start,zzStartRead-1, TokenTypes.COMMENT_EOL); addHyperlinkToken(temp,zzMarkedPos-1, TokenTypes.COMMENT_EOL); start = zzMarkedPos;
          }
        case 33: break;
        case 19:
          int temp=zzStartRead;
          addToken(start,zzStartRead-1, TokenTypes.COMMENT_MULTILINE);
          addHyperlinkToken(temp,zzMarkedPos-1, TokenTypes.COMMENT_MULTILINE);
          start = zzMarkedPos;
        case 34: break;
        case 15:
          addToken(TokenTypes.RESERVED_WORD_2);
        case 35: break;
        case 11:
          addToken(TokenTypes.ERROR_NUMBER_FORMAT);
        case 36: break;
        case 2:
          addToken(TokenTypes.LITERAL_NUMBER_DECIMAL_INT);
        case 37: break;
        case 6:
          addToken(TokenTypes.OPERATOR);
        case 38: break;
        case 8:
        case 39: break;
        case 9:
          addToken(start,zzStartRead-1, TokenTypes.COMMENT_MULTILINE);
          return firstToken;
        case 40: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            switch (zzLexicalState) {
            case EOL_COMMENT:
              addToken(start,zzStartRead-1, TokenTypes.COMMENT_EOL);
              addNullToken();
              return firstToken;
              case 216: break;
            case YYINITIAL:
              addNullToken();
              return firstToken;
              case 217: break;
            case MLC:
              addToken(start,zzStartRead-1, TokenTypes.COMMENT_MULTILINE);
              return firstToken;
              case 218: break;
            default:
            return null;
            }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
