package tinylanglexer;

/**
 * Consists of all possible inputs
 * of dfsa representing TinyLang's grammar.
 * <p>
 * Total number of inputs : 16
 *
 * @author andre
 */
public enum InputCategory {
    /* LETTER ∈ {a,b,...,z,A,B,...,Z} ≡ ASCII LETTER ∈ {[0x41,0x5a],[0x61,0x7a]} */
    LETTER,
    /* DIGIT ∈ {0,1,2,...,9} ≡ ASCII DIGIT ∈ {[0x30,0x39]]} */
    DIGIT,
    /* UNDERSCORE ∈ {_} ≡ ASCII UNDERSCORE ∈ {0x5f} */
    UNDERSCORE,
    /* SLASH_DIVIDE ∈ {/} ≡ ASCII SLASH_DIVIDE ∈ {0x2f} */
    SLASH_DIVIDE,
    /* ASTERISK ∈ {*} ≡ ASCII ASTERISK ∈ {0x2a} */
    ASTERISK,
    /* LESS_THAN ∈ {<} ≡ ASCII LESS_THAN ∈ {0x3c} */
    LESS_THAN,
    /* FORWARD_SLASH ∈ {>} ≡ ASCII FORWARD_SLASH ∈ {0x3e} */
    GREATER_THAN,
    /* PLUS ∈ {+} ≡ ASCII FORWARD_SLASH ∈ {0x2B} */
    PLUS,
    /* HYPHEN_MINUS ∈ {-} ≡ ASCII HYPHEN_MINUS ∈ {0x2d} */
    HYPHEN_MINUS,
    /* EQUAL ∈ {=} ≡ ASCII HYPHEN_MINUS ∈ {0x3d} */
    EQUAL,
    /* EXCLAMATION_MARK ∈ {!} ≡ ASCII EXCLAMATION_MARK ∈ {0x21} */
    EXCLAMATION_MARK,
    /* DOT ∈ {.} ≡ ASCII HYPHEN_MINUS ∈ {0x2e} */
    DOT,
    /* SINGLE_QUOTE ∈ {'} ≡ ASCII HYPHEN_MINUS ∈ {0x27} */
    SINGLE_QUOTE,
    /* PUNCTUATION ∈ {( ,) ,, ‚: ,;,{ ,} } ≡ ASCII PUNCTUATION ∈ {0x28, 0x29,0x2c, 0x3a, 0x3b,0x7b ,0x7d} */
    PUNCT,
    /* ASCII : OTHER_PRINTABLE ∈ {[0x20,0x7e]}
     * \ (LETTERS ∪ DIGITS ∪ UNDERSCORE ∪ FORWARD_SLASH ∪ ASTERISK ∪ LESS_THAN
     * 	  ∪ GREATER_THAN ∪ PLUS,MINUS ∪ EQUAL ∪ EXCLAMATION_MARK ∪ DOT
     *    ∪ SINGLE_QUOTE ∪ PUNCTUATION) */
    OTHER_PRINTABLE,
    /* LINE_FEED ∈ {\n} ≡ ASCII LINE_FEED ∈ {0x0a} */
    LINE_FEED
}