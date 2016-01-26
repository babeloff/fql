/* The following code was generated by JFlex 1.4.3 on 12/19/15 9:12 PM */

/*
 * Generated on 12/19/15 9:12 PM
 */
package fql_lib.A;

import java.io.*;
import javax.swing.text.Segment;

import org.fife.ui.rsyntaxtextarea.*;


/**
 * 
 */

public class ATokenMaker extends AbstractJFlexCTokenMaker {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int EOL_COMMENT = 6;
  public static final int STRING = 2;
  public static final int YYINITIAL = 0;
  public static final int MLC = 4;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1,  1,  2,  2,  3, 3
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\16\1\6\1\0\1\16\1\14\22\0\1\16\1\5\1\13"+
    "\1\15\1\1\1\5\1\5\1\5\1\23\1\23\1\20\1\52\1\5"+
    "\1\53\1\22\1\17\1\3\3\3\4\3\2\3\1\34\1\5\1\14"+
    "\1\55\1\54\1\5\1\15\1\42\5\4\24\1\1\23\1\7\1\23"+
    "\1\56\1\2\1\0\1\36\1\12\1\47\1\41\1\33\1\30\1\45"+
    "\1\24\1\31\1\46\1\1\1\32\1\50\1\11\1\44\1\26\1\51"+
    "\1\40\1\27\1\25\1\10\1\43\1\35\1\1\1\37\1\1\1\21"+
    "\1\56\1\21\1\5\uff81\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\2\0\2\2\1\3\1\4\1\2\1\5"+
    "\1\6\1\2\1\7\1\10\16\2\1\7\1\1\1\11"+
    "\1\1\1\12\5\1\1\13\3\1\1\0\1\14\1\2"+
    "\1\15\1\16\15\2\1\17\7\2\1\20\6\2\1\21"+
    "\11\0\15\2\1\20\15\2\11\0\22\2\2\0\1\22"+
    "\2\0\1\23\1\0\15\2\5\0\24\2\1\17\12\2";

  private static int [] zzUnpackAction() {
    int [] result = new int[195];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
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
    "\0\0\0\57\0\136\0\215\0\274\0\353\0\u011a\0\274"+
    "\0\u0149\0\274\0\u0178\0\u01a7\0\274\0\274\0\u01d6\0\u0205"+
    "\0\u0234\0\u0263\0\u0292\0\u02c1\0\u02f0\0\u031f\0\u034e\0\u037d"+
    "\0\u03ac\0\u03db\0\u040a\0\u0439\0\u0439\0\u0468\0\274\0\u0497"+
    "\0\274\0\u04c6\0\u04f5\0\u0524\0\u0553\0\u0582\0\274\0\u05b1"+
    "\0\u05e0\0\u060f\0\u063e\0\u066d\0\u069c\0\274\0\274\0\u06cb"+
    "\0\u06fa\0\u0729\0\u0758\0\u0787\0\u07b6\0\u07e5\0\u0814\0\u0843"+
    "\0\u0872\0\u08a1\0\u08d0\0\u08ff\0\353\0\u092e\0\u095d\0\u098c"+
    "\0\u09bb\0\u09ea\0\u0a19\0\u0a48\0\353\0\u0a77\0\u0aa6\0\u0ad5"+
    "\0\u0b04\0\u0b33\0\u0b62\0\274\0\u0b91\0\u0bc0\0\u0bef\0\u0c1e"+
    "\0\u0c4d\0\u0c7c\0\u0cab\0\u0cda\0\u0d09\0\u0d38\0\u0d67\0\u0d96"+
    "\0\u0dc5\0\u0df4\0\u0e23\0\u0e52\0\u0e81\0\u0eb0\0\u0edf\0\u0f0e"+
    "\0\u0f3d\0\u0f6c\0\u0f9b\0\u0fca\0\u0ff9\0\u1028\0\u1057\0\u1086"+
    "\0\u10b5\0\u10e4\0\u1113\0\u1142\0\u1171\0\u11a0\0\u11cf\0\u11fe"+
    "\0\u122d\0\u125c\0\u128b\0\u12ba\0\u12e9\0\u1318\0\u1347\0\u1376"+
    "\0\u13a5\0\u13d4\0\u1403\0\u1432\0\u1461\0\u1490\0\u14bf\0\u14ee"+
    "\0\u151d\0\u154c\0\u157b\0\u15aa\0\u15d9\0\u1608\0\u1637\0\u1666"+
    "\0\u1695\0\u16c4\0\u16f3\0\u1722\0\u1751\0\u1780\0\u17af\0\u17de"+
    "\0\u180d\0\u183c\0\u186b\0\u189a\0\u18c9\0\u18f8\0\u1927\0\u1956"+
    "\0\u1985\0\u19b4\0\u19e3\0\u1a12\0\u1a41\0\u1a70\0\u1a9f\0\u1ace"+
    "\0\u1780\0\u1afd\0\u180d\0\u1b2c\0\u1b5b\0\u1b8a\0\u1bb9\0\u1be8"+
    "\0\u1c17\0\u1c46\0\u1c75\0\u1ca4\0\u1cd3\0\u1d02\0\u1d31\0\u1d60"+
    "\0\u1d8f\0\u1dbe\0\u1ded\0\u1e1c\0\u1e4b\0\u1e7a\0\u1ea9\0\u1ed8"+
    "\0\u1f07\0\u1f36\0\u1f65\0\u1f94\0\u1fc3\0\u1ff2\0\u2021\0\u2050"+
    "\0\u207f\0\u20ae\0\u20dd";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[195];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
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
    "\1\5\2\6\1\7\1\6\1\5\1\10\1\5\1\11"+
    "\2\6\1\12\2\5\1\13\1\14\1\15\1\16\1\5"+
    "\1\16\1\6\1\17\1\20\1\21\1\22\1\23\1\6"+
    "\1\24\1\15\1\25\1\26\2\6\1\27\3\6\1\30"+
    "\1\31\1\6\1\32\1\33\1\15\1\34\1\5\1\35"+
    "\1\15\13\36\1\37\43\36\6\40\1\41\11\40\1\42"+
    "\3\40\1\43\3\40\1\44\4\40\1\45\21\40\6\46"+
    "\1\47\15\46\1\50\3\46\1\51\4\46\1\52\21\46"+
    "\60\0\4\6\2\0\1\53\3\6\11\0\10\6\1\0"+
    "\15\6\5\0\3\54\1\7\1\54\2\0\4\54\2\0"+
    "\1\54\6\0\10\54\1\0\15\54\6\0\4\6\2\0"+
    "\1\53\1\6\1\55\1\6\11\0\10\6\1\0\15\6"+
    "\23\0\1\13\57\0\1\56\1\57\37\0\4\6\2\0"+
    "\1\53\3\6\11\0\1\60\7\6\1\0\2\6\1\61"+
    "\1\62\11\6\6\0\4\6\2\0\1\53\3\6\11\0"+
    "\10\6\1\0\3\6\1\63\11\6\6\0\4\6\2\0"+
    "\1\53\3\6\11\0\5\6\1\64\1\6\1\65\1\0"+
    "\1\6\1\66\1\67\4\6\1\70\2\6\1\71\2\6"+
    "\6\0\4\6\2\0\1\53\3\6\11\0\6\6\1\72"+
    "\1\6\1\0\3\6\1\73\3\6\1\74\5\6\6\0"+
    "\4\6\2\0\1\53\3\6\11\0\10\6\1\0\4\6"+
    "\1\75\10\6\6\0\4\6\2\0\1\53\1\6\1\76"+
    "\1\6\11\0\10\6\1\0\4\6\1\77\1\6\1\100"+
    "\5\6\1\101\6\0\4\6\2\0\1\53\3\6\11\0"+
    "\1\102\7\6\1\0\15\6\6\0\4\6\2\0\1\53"+
    "\3\6\11\0\1\6\1\103\1\104\1\105\4\6\1\0"+
    "\15\6\6\0\4\6\2\0\1\53\3\6\11\0\7\6"+
    "\1\106\1\0\15\6\6\0\4\6\2\0\1\53\3\6"+
    "\11\0\7\6\1\107\1\0\15\6\6\0\4\6\2\0"+
    "\1\53\3\6\11\0\10\6\1\0\1\6\1\110\13\6"+
    "\6\0\4\6\2\0\1\53\3\6\11\0\10\6\1\0"+
    "\1\6\1\111\5\6\1\112\5\6\6\0\4\6\2\0"+
    "\1\53\1\113\2\6\11\0\10\6\1\0\15\6\61\0"+
    "\1\15\2\0\13\36\1\0\43\36\6\40\1\0\11\40"+
    "\1\0\3\40\1\0\3\40\1\0\4\40\1\0\21\40"+
    "\17\0\1\114\64\0\1\115\56\0\1\116\3\0\1\117"+
    "\62\0\1\120\21\0\6\46\1\0\15\46\1\0\3\46"+
    "\1\0\4\46\1\0\21\46\25\0\1\121\56\0\1\122"+
    "\3\0\1\123\62\0\1\124\31\0\1\125\46\0\5\54"+
    "\2\0\4\54\2\0\1\54\6\0\10\54\1\0\15\54"+
    "\6\0\4\6\2\0\1\53\3\6\11\0\3\6\1\126"+
    "\4\6\1\0\15\6\6\0\4\6\2\0\1\53\3\6"+
    "\11\0\7\6\1\127\1\0\15\6\6\0\4\6\2\0"+
    "\1\53\3\6\11\0\2\6\1\130\5\6\1\0\15\6"+
    "\6\0\4\6\2\0\1\53\3\6\11\0\10\6\1\0"+
    "\1\6\1\131\13\6\6\0\4\6\2\0\1\53\3\6"+
    "\11\0\7\6\1\132\1\0\15\6\6\0\4\6\2\0"+
    "\1\53\3\6\11\0\10\6\1\0\10\6\1\133\4\6"+
    "\6\0\4\6\2\0\1\53\3\6\11\0\6\6\1\134"+
    "\1\6\1\0\15\6\6\0\4\6\2\0\1\53\3\6"+
    "\11\0\1\6\1\135\6\6\1\0\15\6\6\0\4\6"+
    "\2\0\1\53\3\6\11\0\10\6\1\0\13\6\1\136"+
    "\1\6\6\0\4\6\2\0\1\53\3\6\11\0\10\6"+
    "\1\0\3\6\1\137\11\6\6\0\4\6\2\0\1\53"+
    "\3\6\11\0\1\140\7\6\1\0\15\6\6\0\4\6"+
    "\2\0\1\53\3\6\11\0\10\6\1\0\7\6\1\141"+
    "\5\6\6\0\4\6\2\0\1\53\3\6\11\0\10\6"+
    "\1\0\7\6\1\142\5\6\6\0\4\6\2\0\1\53"+
    "\3\6\11\0\10\6\1\0\3\6\1\143\11\6\6\0"+
    "\4\6\2\0\1\53\3\6\11\0\1\6\1\144\6\6"+
    "\1\0\15\6\6\0\4\6\2\0\1\53\3\6\11\0"+
    "\10\6\1\0\10\6\1\145\4\6\6\0\4\6\2\0"+
    "\1\53\3\6\11\0\10\6\1\0\1\6\1\146\13\6"+
    "\6\0\4\6\2\0\1\53\1\147\2\6\11\0\10\6"+
    "\1\0\15\6\6\0\4\6\2\0\1\53\3\6\11\0"+
    "\7\6\1\150\1\0\15\6\6\0\4\6\2\0\1\53"+
    "\3\6\11\0\1\6\1\151\6\6\1\0\15\6\6\0"+
    "\4\6\2\0\1\53\3\6\11\0\2\6\1\152\5\6"+
    "\1\0\15\6\6\0\4\6\2\0\1\53\3\6\11\0"+
    "\6\6\1\153\1\6\1\0\15\6\6\0\4\6\2\0"+
    "\1\53\1\6\1\154\1\6\11\0\10\6\1\0\15\6"+
    "\6\0\4\6\2\0\1\53\3\6\11\0\10\6\1\0"+
    "\6\6\1\155\6\6\6\0\4\6\2\0\1\53\3\6"+
    "\11\0\2\6\1\156\5\6\1\0\15\6\6\0\4\6"+
    "\2\0\1\53\3\6\11\0\10\6\1\0\4\6\1\157"+
    "\10\6\6\0\4\6\2\0\1\53\3\6\11\0\7\6"+
    "\1\160\1\0\15\6\32\0\1\161\57\0\1\162\62\0"+
    "\1\163\61\0\1\164\46\0\1\165\57\0\1\166\62\0"+
    "\1\167\61\0\1\170\24\0\2\171\5\0\1\171\15\0"+
    "\1\171\2\0\1\171\2\0\1\171\2\0\2\171\4\0"+
    "\1\171\10\0\4\6\2\0\1\53\3\6\11\0\10\6"+
    "\1\0\1\6\1\66\13\6\6\0\4\6\2\0\1\53"+
    "\3\6\11\0\10\6\1\0\7\6\1\160\5\6\6\0"+
    "\4\6\2\0\1\53\3\6\11\0\7\6\1\172\1\0"+
    "\15\6\6\0\4\6\2\0\1\53\1\6\1\173\1\6"+
    "\11\0\10\6\1\0\15\6\6\0\4\6\2\0\1\53"+
    "\3\6\11\0\3\6\1\174\4\6\1\0\15\6\6\0"+
    "\4\6\2\0\1\53\3\6\11\0\10\6\1\0\13\6"+
    "\1\175\1\6\6\0\4\6\2\0\1\53\3\6\11\0"+
    "\7\6\1\176\1\0\15\6\6\0\4\6\2\0\1\53"+
    "\1\177\2\6\11\0\10\6\1\0\15\6\6\0\4\6"+
    "\2\0\1\53\2\6\1\200\11\0\10\6\1\0\15\6"+
    "\6\0\4\6\2\0\1\53\3\6\11\0\1\6\1\201"+
    "\6\6\1\0\15\6\6\0\4\6\2\0\1\53\3\6"+
    "\11\0\7\6\1\133\1\0\15\6\6\0\4\6\2\0"+
    "\1\53\3\6\11\0\10\6\1\0\1\202\14\6\6\0"+
    "\4\6\2\0\1\53\3\6\11\0\10\6\1\0\13\6"+
    "\1\105\1\6\6\0\4\6\2\0\1\53\3\6\11\0"+
    "\10\6\1\0\1\6\1\203\13\6\6\0\4\6\2\0"+
    "\1\53\3\6\11\0\5\6\1\204\2\6\1\0\15\6"+
    "\6\0\4\6\2\0\1\53\3\6\11\0\7\6\1\201"+
    "\1\0\15\6\6\0\4\6\2\0\1\53\3\6\11\0"+
    "\6\6\1\75\1\6\1\0\15\6\6\0\4\6\2\0"+
    "\1\53\3\6\11\0\10\6\1\0\1\6\1\205\13\6"+
    "\6\0\4\6\2\0\1\53\3\6\11\0\10\6\1\0"+
    "\3\6\1\206\11\6\6\0\4\6\2\0\1\53\3\6"+
    "\11\0\10\6\1\0\3\6\1\207\11\6\6\0\4\6"+
    "\2\0\1\53\3\6\11\0\6\6\1\210\1\6\1\0"+
    "\15\6\6\0\4\6\2\0\1\53\3\6\11\0\1\6"+
    "\1\175\6\6\1\0\15\6\6\0\4\6\2\0\1\53"+
    "\3\6\11\0\7\6\1\211\1\0\15\6\6\0\4\6"+
    "\2\0\1\53\3\6\11\0\10\6\1\0\1\6\1\212"+
    "\13\6\6\0\4\6\2\0\1\53\3\6\11\0\2\6"+
    "\1\213\5\6\1\0\15\6\6\0\4\6\2\0\1\53"+
    "\3\6\11\0\7\6\1\146\1\0\15\6\6\0\4\6"+
    "\2\0\1\53\3\6\11\0\10\6\1\0\3\6\1\210"+
    "\11\6\33\0\1\214\64\0\1\215\55\0\1\162\45\0"+
    "\1\216\62\0\1\217\64\0\1\220\55\0\1\166\45\0"+
    "\1\221\37\0\2\222\5\0\1\222\15\0\1\222\2\0"+
    "\1\222\2\0\1\222\2\0\2\222\4\0\1\222\10\0"+
    "\4\6\2\0\1\53\3\6\11\0\3\6\1\75\4\6"+
    "\1\0\15\6\6\0\4\6\2\0\1\53\3\6\11\0"+
    "\3\6\1\223\4\6\1\0\15\6\6\0\4\6\2\0"+
    "\1\53\3\6\11\0\7\6\1\224\1\0\15\6\6\0"+
    "\4\6\2\0\1\53\3\6\11\0\10\6\1\0\1\6"+
    "\1\75\13\6\6\0\4\6\2\0\1\53\3\6\11\0"+
    "\10\6\1\0\12\6\1\225\2\6\6\0\4\6\2\0"+
    "\1\53\3\6\11\0\10\6\1\0\3\6\1\226\11\6"+
    "\6\0\4\6\2\0\1\53\3\6\11\0\10\6\1\0"+
    "\7\6\1\227\5\6\6\0\4\6\2\0\1\53\3\6"+
    "\11\0\3\6\1\105\4\6\1\0\15\6\6\0\4\6"+
    "\2\0\1\53\3\6\11\0\7\6\1\230\1\0\15\6"+
    "\6\0\4\6\2\0\1\53\3\6\11\0\6\6\1\231"+
    "\1\6\1\0\15\6\6\0\4\6\2\0\1\53\3\6"+
    "\11\0\1\6\1\232\6\6\1\0\15\6\6\0\4\6"+
    "\2\0\1\53\3\6\11\0\1\6\1\233\6\6\1\0"+
    "\15\6\6\0\4\6\2\0\1\53\3\6\11\0\7\6"+
    "\1\105\1\0\15\6\6\0\4\6\2\0\1\53\3\6"+
    "\11\0\5\6\1\234\2\6\1\0\15\6\6\0\4\6"+
    "\2\0\1\53\3\6\11\0\10\6\1\0\2\6\1\75"+
    "\12\6\6\0\4\6\2\0\1\53\3\6\11\0\10\6"+
    "\1\0\3\6\1\235\11\6\6\0\4\6\2\0\1\53"+
    "\3\6\11\0\3\6\1\236\4\6\1\0\15\6\6\0"+
    "\4\6\2\0\1\53\3\6\11\0\5\6\1\237\2\6"+
    "\1\0\15\6\34\0\1\162\4\0\1\215\41\0\1\240"+
    "\40\0\1\216\1\241\2\216\1\241\2\0\3\216\2\0"+
    "\1\241\1\0\1\216\1\241\1\0\2\241\10\216\1\241"+
    "\15\216\2\241\1\0\1\241\30\0\1\166\4\0\1\220"+
    "\41\0\1\242\40\0\1\221\1\243\2\221\1\243\2\0"+
    "\3\221\2\0\1\243\1\0\1\221\1\243\1\0\2\243"+
    "\10\221\1\243\15\221\2\243\1\0\1\243\4\0\2\244"+
    "\5\0\1\244\15\0\1\244\2\0\1\244\2\0\1\244"+
    "\2\0\2\244\4\0\1\244\10\0\4\6\2\0\1\53"+
    "\3\6\11\0\2\6\1\245\1\6\1\246\3\6\1\0"+
    "\15\6\6\0\4\6\2\0\1\53\1\6\1\247\1\6"+
    "\11\0\10\6\1\0\15\6\6\0\4\6\2\0\1\53"+
    "\3\6\11\0\1\6\1\105\6\6\1\0\15\6\6\0"+
    "\4\6\2\0\1\53\3\6\11\0\10\6\1\0\1\6"+
    "\1\250\13\6\6\0\4\6\2\0\1\53\3\6\11\0"+
    "\6\6\1\201\1\6\1\0\15\6\6\0\4\6\2\0"+
    "\1\53\3\6\11\0\10\6\1\0\3\6\1\75\11\6"+
    "\6\0\4\6\2\0\1\53\3\6\11\0\6\6\1\105"+
    "\1\6\1\0\15\6\6\0\4\6\2\0\1\53\3\6"+
    "\11\0\5\6\1\251\2\6\1\0\15\6\6\0\4\6"+
    "\2\0\1\53\3\6\11\0\5\6\1\252\2\6\1\0"+
    "\15\6\6\0\4\6\2\0\1\53\2\6\1\253\11\0"+
    "\10\6\1\0\15\6\6\0\4\6\2\0\1\53\3\6"+
    "\11\0\10\6\1\0\1\6\1\254\13\6\6\0\4\6"+
    "\2\0\1\53\3\6\11\0\10\6\1\0\12\6\1\255"+
    "\2\6\6\0\4\6\2\0\1\53\1\6\1\256\1\6"+
    "\11\0\10\6\1\0\15\6\24\0\1\216\56\0\1\221"+
    "\42\0\2\6\5\0\1\6\15\0\1\6\2\0\1\6"+
    "\2\0\1\6\2\0\2\6\4\0\1\6\10\0\4\6"+
    "\2\0\1\53\3\6\11\0\10\6\1\0\3\6\1\130"+
    "\11\6\6\0\4\6\2\0\1\53\3\6\11\0\10\6"+
    "\1\0\7\6\1\257\5\6\6\0\4\6\2\0\1\53"+
    "\3\6\11\0\1\6\1\260\6\6\1\0\15\6\6\0"+
    "\4\6\2\0\1\53\3\6\11\0\1\6\1\261\6\6"+
    "\1\0\15\6\6\0\4\6\2\0\1\53\3\6\11\0"+
    "\7\6\1\262\1\0\15\6\6\0\4\6\2\0\1\53"+
    "\3\6\11\0\10\6\1\0\7\6\1\263\5\6\6\0"+
    "\4\6\2\0\1\53\1\264\2\6\11\0\10\6\1\0"+
    "\15\6\6\0\4\6\2\0\1\53\3\6\11\0\1\6"+
    "\1\265\6\6\1\0\15\6\6\0\4\6\2\0\1\53"+
    "\3\6\11\0\10\6\1\0\3\6\1\266\11\6\6\0"+
    "\4\6\2\0\1\53\3\6\11\0\10\6\1\0\10\6"+
    "\1\75\4\6\6\0\4\6\2\0\1\53\3\6\11\0"+
    "\10\6\1\0\3\6\1\267\11\6\6\0\4\6\2\0"+
    "\1\53\3\6\11\0\10\6\1\0\1\6\1\270\13\6"+
    "\6\0\4\6\2\0\1\53\3\6\11\0\7\6\1\75"+
    "\1\0\15\6\6\0\4\6\2\0\1\53\3\6\11\0"+
    "\3\6\1\271\4\6\1\0\15\6\6\0\4\6\2\0"+
    "\1\53\1\6\1\201\1\6\11\0\10\6\1\0\15\6"+
    "\6\0\4\6\2\0\1\53\3\6\11\0\1\6\1\130"+
    "\6\6\1\0\15\6\6\0\4\6\2\0\1\53\3\6"+
    "\11\0\10\6\1\0\7\6\1\272\5\6\6\0\4\6"+
    "\2\0\1\53\3\6\11\0\5\6\1\273\2\6\1\0"+
    "\15\6\6\0\4\6\2\0\1\53\3\6\11\0\10\6"+
    "\1\0\13\6\1\75\1\6\6\0\4\6\2\0\1\53"+
    "\3\6\11\0\1\6\1\274\6\6\1\0\15\6\6\0"+
    "\4\6\2\0\1\53\3\6\11\0\10\6\1\0\5\6"+
    "\1\275\7\6\6\0\4\6\2\0\1\53\3\6\11\0"+
    "\10\6\1\0\3\6\1\172\11\6\6\0\4\6\2\0"+
    "\1\53\3\6\11\0\2\6\1\276\5\6\1\0\15\6"+
    "\6\0\4\6\2\0\1\53\3\6\11\0\5\6\1\277"+
    "\2\6\1\0\15\6\6\0\4\6\2\0\1\53\1\6"+
    "\1\300\1\6\11\0\10\6\1\0\15\6\6\0\4\6"+
    "\2\0\1\53\3\6\11\0\1\6\1\75\6\6\1\0"+
    "\15\6\6\0\4\6\2\0\1\53\3\6\11\0\10\6"+
    "\1\0\7\6\1\301\5\6\6\0\4\6\2\0\1\53"+
    "\3\6\11\0\10\6\1\0\4\6\1\302\10\6\6\0"+
    "\4\6\2\0\1\53\1\6\1\75\1\6\11\0\10\6"+
    "\1\0\15\6\6\0\4\6\2\0\1\53\3\6\11\0"+
    "\10\6\1\0\5\6\1\303\7\6\6\0\4\6\2\0"+
    "\1\53\3\6\11\0\1\6\1\103\6\6\1\0\15\6"+
    "\5\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[8460];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
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
    "\1\0\1\1\2\0\1\11\2\1\1\11\1\1\1\11"+
    "\2\1\2\11\20\1\1\11\1\1\1\11\5\1\1\11"+
    "\3\1\1\0\2\1\2\11\34\1\1\11\11\0\33\1"+
    "\11\0\22\1\2\0\1\1\2\0\1\1\1\0\15\1"+
    "\5\0\37\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[195];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
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
  private java.io.Reader zzReader;

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
	public ATokenMaker() {
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
	public String[] getLineCommentStartAndEnd() {
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
	public Token getTokenList(Segment text, int initialTokenType, int startOffset) {

		resetTokenList();
		this.offsetShift = -text.offset + startOffset;

		// Start off in the proper state.
		int state = Token.NULL;
		switch (initialTokenType) {
						case Token.COMMENT_MULTILINE:
				state = MLC;
				start = text.offset;
				break;

			/* No documentation comments */
			default:
				state = Token.NULL;
		}

		s = text;
		try {
			yyreset(zzReader);
			yybegin(state);
			return yylex();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		}

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
	public final void yyreset(Reader reader) {
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
  public ATokenMaker(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public ATokenMaker(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
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
    while (i < 148) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
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
  public final int yylength() {
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
  private void zzScanError(int errorCode) {
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
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public org.fife.ui.rsyntaxtextarea.Token yylex() throws java.io.IOException {
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
        case 4: 
          { addNullToken(); return firstToken;
          }
        case 20: break;
        case 14: 
          { start = zzMarkedPos-2; yybegin(MLC);
          }
        case 21: break;
        case 6: 
          { addToken(Token.WHITESPACE);
          }
        case 22: break;
        case 15: 
          { addToken(Token.RESERVED_WORD);
          }
        case 23: break;
        case 8: 
          { addToken(Token.SEPARATOR);
          }
        case 24: break;
        case 2: 
          { addToken(Token.IDENTIFIER);
          }
        case 25: break;
        case 11: 
          { addToken(start,zzStartRead-1, Token.COMMENT_EOL); addNullToken(); return firstToken;
          }
        case 26: break;
        case 13: 
          { start = zzMarkedPos-2; yybegin(EOL_COMMENT);
          }
        case 27: break;
        case 17: 
          { yybegin(YYINITIAL); addToken(start,zzStartRead+2-1, Token.COMMENT_MULTILINE);
          }
        case 28: break;
        case 19: 
          { int temp=zzStartRead; addToken(start,zzStartRead-1, Token.COMMENT_EOL); addHyperlinkToken(temp,zzMarkedPos-1, Token.COMMENT_EOL); start = zzMarkedPos;
          }
        case 29: break;
        case 18: 
          { int temp=zzStartRead; addToken(start,zzStartRead-1, Token.COMMENT_MULTILINE); addHyperlinkToken(temp,zzMarkedPos-1, Token.COMMENT_MULTILINE); start = zzMarkedPos;
          }
        case 30: break;
        case 16: 
          { addToken(Token.RESERVED_WORD_2);
          }
        case 31: break;
        case 12: 
          { addToken(Token.ERROR_NUMBER_FORMAT);
          }
        case 32: break;
        case 5: 
          { start = zzMarkedPos-1; yybegin(STRING);
          }
        case 33: break;
        case 3: 
          { addToken(Token.LITERAL_NUMBER_DECIMAL_INT);
          }
        case 34: break;
        case 7: 
          { addToken(Token.OPERATOR);
          }
        case 35: break;
        case 9: 
          { yybegin(YYINITIAL); addToken(start,zzStartRead, Token.LITERAL_STRING_DOUBLE_QUOTE);
          }
        case 36: break;
        case 1: 
          { 
          }
        case 37: break;
        case 10: 
          { addToken(start,zzStartRead-1, Token.COMMENT_MULTILINE); return firstToken;
          }
        case 38: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            switch (zzLexicalState) {
            case EOL_COMMENT: {
              addToken(start,zzStartRead-1, Token.COMMENT_EOL); addNullToken(); return firstToken;
            }
            case 196: break;
            case STRING: {
              addToken(start,zzStartRead-1, Token.LITERAL_STRING_DOUBLE_QUOTE); return firstToken;
            }
            case 197: break;
            case YYINITIAL: {
              addNullToken(); return firstToken;
            }
            case 198: break;
            case MLC: {
              addToken(start,zzStartRead-1, Token.COMMENT_MULTILINE); return firstToken;
            }
            case 199: break;
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
