// $ANTLR 3.4 PLSQLLexer.g 2012-02-08 17:26:45

/**
 * Oracle(c) PL/SQL 11g Parser  
 *
 * Copyright (c) 2009-2011 Alexandre Porcelli <alexandre.porcelli@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.net.cvtt.resource.database;

import java.util.LinkedList;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

/**
 * Oracle(c) PL/SQL 11g Parser  
 *
 * Copyright (c) 2009-2011 Alexandre Porcelli <alexandre.porcelli@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@SuppressWarnings({"all", "warnings", "unchecked"})
public class PLSQLLexer extends Lexer {
    public static final int EOF=-1;
    public static final int AMPERSAND=4;
    public static final int APPROXIMATE_NUM_LIT=5;
    public static final int ASSIGN_OP=6;
    public static final int ASTERISK=7;
    public static final int AT_SIGN=8;
    public static final int BIT_STRING_LIT=9;
    public static final int CARRET_OPERATOR_PART=10;
    public static final int CHAR_STRING=11;
    public static final int CHAR_STRING_PERL=12;
    public static final int COLON=13;
    public static final int COMMA=14;
    public static final int COMMENT=15;
    public static final int CONCATENATION_OP=16;
    public static final int DELIMITED_ID=17;
    public static final int DOUBLE_ASTERISK=18;
    public static final int DOUBLE_PERIOD=19;
    public static final int EQUALS_OP=20;
    public static final int EXACT_NUM_LIT=21;
    public static final int EXCLAMATION_OPERATOR_PART=22;
    public static final int FOR_NOTATION=23;
    public static final int GREATER_THAN_OP=24;
    public static final int GREATER_THAN_OR_EQUALS_OP=25;
    public static final int HEX_STRING_LIT=26;
    public static final int INTRODUCER=27;
    public static final int LEFT_BRACKET=28;
    public static final int LEFT_PAREN=29;
    public static final int LESS_THAN_OP=30;
    public static final int LESS_THAN_OR_EQUALS_OP=31;
    public static final int MINUS_SIGN=32;
    public static final int NATIONAL_CHAR_STRING_LIT=33;
    public static final int NEWLINE=34;
    public static final int NOT_EQUAL_OP=35;
    public static final int PERCENT=36;
    public static final int PERIOD=37;
    public static final int PLSQL_NON_RESERVED_CONNECT_BY_ROOT=38;
    public static final int PLSQL_NON_RESERVED_ELSIF=39;
    public static final int PLSQL_NON_RESERVED_MODEL=40;
    public static final int PLSQL_NON_RESERVED_PIVOT=41;
    public static final int PLSQL_NON_RESERVED_UNPIVOT=42;
    public static final int PLSQL_NON_RESERVED_USING=43;
    public static final int PLSQL_RESERVED_CLUSTERS=44;
    public static final int PLSQL_RESERVED_COLAUTH=45;
    public static final int PLSQL_RESERVED_COLUMNS=46;
    public static final int PLSQL_RESERVED_COMPRESS=47;
    public static final int PLSQL_RESERVED_CRASH=48;
    public static final int PLSQL_RESERVED_EXCLUSIVE=49;
    public static final int PLSQL_RESERVED_IDENTIFIED=50;
    public static final int PLSQL_RESERVED_IF=51;
    public static final int PLSQL_RESERVED_INDEX=52;
    public static final int PLSQL_RESERVED_INDEXES=53;
    public static final int PLSQL_RESERVED_LOCK=54;
    public static final int PLSQL_RESERVED_MINUS=55;
    public static final int PLSQL_RESERVED_MODE=56;
    public static final int PLSQL_RESERVED_NOCOMPRESS=57;
    public static final int PLSQL_RESERVED_NOWAIT=58;
    public static final int PLSQL_RESERVED_RESOURCE=59;
    public static final int PLSQL_RESERVED_SHARE=60;
    public static final int PLSQL_RESERVED_START=61;
    public static final int PLSQL_RESERVED_TABAUTH=62;
    public static final int PLSQL_RESERVED_VIEWS=63;
    public static final int PLUS_SIGN=64;
    public static final int QS_ANGLE=65;
    public static final int QS_BRACE=66;
    public static final int QS_BRACK=67;
    public static final int QS_OTHER=68;
    public static final int QS_OTHER_CH=69;
    public static final int QS_PAREN=70;
    public static final int QUESTION_MARK=71;
    public static final int QUOTE=72;
    public static final int REGULAR_ID=73;
    public static final int RIGHT_BRACKET=74;
    public static final int RIGHT_PAREN=75;
    public static final int SEMICOLON=76;
    public static final int SEPARATOR=77;
    public static final int SIMPLE_LETTER=78;
    public static final int SOLIDUS=79;
    public static final int SPACE=80;
    public static final int SQL92_RESERVED_ALL=81;
    public static final int SQL92_RESERVED_ALTER=82;
    public static final int SQL92_RESERVED_AND=83;
    public static final int SQL92_RESERVED_ANY=84;
    public static final int SQL92_RESERVED_AS=85;
    public static final int SQL92_RESERVED_ASC=86;
    public static final int SQL92_RESERVED_BEGIN=87;
    public static final int SQL92_RESERVED_BETWEEN=88;
    public static final int SQL92_RESERVED_BY=89;
    public static final int SQL92_RESERVED_CASE=90;
    public static final int SQL92_RESERVED_CHECK=91;
    public static final int SQL92_RESERVED_CONNECT=92;
    public static final int SQL92_RESERVED_CREATE=93;
    public static final int SQL92_RESERVED_CURRENT=94;
    public static final int SQL92_RESERVED_CURSOR=95;
    public static final int SQL92_RESERVED_DATE=96;
    public static final int SQL92_RESERVED_DECLARE=97;
    public static final int SQL92_RESERVED_DEFAULT=98;
    public static final int SQL92_RESERVED_DELETE=99;
    public static final int SQL92_RESERVED_DESC=100;
    public static final int SQL92_RESERVED_DISTINCT=101;
    public static final int SQL92_RESERVED_DROP=102;
    public static final int SQL92_RESERVED_ELSE=103;
    public static final int SQL92_RESERVED_END=104;
    public static final int SQL92_RESERVED_EXCEPTION=105;
    public static final int SQL92_RESERVED_EXISTS=106;
    public static final int SQL92_RESERVED_FALSE=107;
    public static final int SQL92_RESERVED_FETCH=108;
    public static final int SQL92_RESERVED_FOR=109;
    public static final int SQL92_RESERVED_FROM=110;
    public static final int SQL92_RESERVED_GOTO=111;
    public static final int SQL92_RESERVED_GRANT=112;
    public static final int SQL92_RESERVED_GROUP=113;
    public static final int SQL92_RESERVED_HAVING=114;
    public static final int SQL92_RESERVED_IN=115;
    public static final int SQL92_RESERVED_INSERT=116;
    public static final int SQL92_RESERVED_INTERSECT=117;
    public static final int SQL92_RESERVED_INTO=118;
    public static final int SQL92_RESERVED_IS=119;
    public static final int SQL92_RESERVED_LIKE=120;
    public static final int SQL92_RESERVED_NOT=121;
    public static final int SQL92_RESERVED_NULL=122;
    public static final int SQL92_RESERVED_OF=123;
    public static final int SQL92_RESERVED_ON=124;
    public static final int SQL92_RESERVED_OPTION=125;
    public static final int SQL92_RESERVED_OR=126;
    public static final int SQL92_RESERVED_ORDER=127;
    public static final int SQL92_RESERVED_OVERLAPS=128;
    public static final int SQL92_RESERVED_PRIOR=129;
    public static final int SQL92_RESERVED_PROCEDURE=130;
    public static final int SQL92_RESERVED_PUBLIC=131;
    public static final int SQL92_RESERVED_REVOKE=132;
    public static final int SQL92_RESERVED_SELECT=133;
    public static final int SQL92_RESERVED_SIZE=134;
    public static final int SQL92_RESERVED_SQL=135;
    public static final int SQL92_RESERVED_TABLE=136;
    public static final int SQL92_RESERVED_THEN=137;
    public static final int SQL92_RESERVED_TO=138;
    public static final int SQL92_RESERVED_TRUE=139;
    public static final int SQL92_RESERVED_UNION=140;
    public static final int SQL92_RESERVED_UNIQUE=141;
    public static final int SQL92_RESERVED_UPDATE=142;
    public static final int SQL92_RESERVED_VALUES=143;
    public static final int SQL92_RESERVED_VIEW=144;
    public static final int SQL92_RESERVED_WHEN=145;
    public static final int SQL92_RESERVED_WHERE=146;
    public static final int SQL92_RESERVED_WITH=147;
    public static final int TILDE_OPERATOR_PART=148;
    public static final int UNDERSCORE=149;
    public static final int UNSIGNED_INTEGER=150;
    public static final int VERTICAL_BAR=151;

        // buffer (queue) to hold the emit()'d tokens
        private LinkedList<Token> tokenBuffer = new LinkedList<Token>();

        public void emit(Token t) {
            tokenBuffer.add(t);
        }

        private void advanceInput(){
            state.tokenStartCharIndex = input.index();
            state.tokenStartCharPositionInLine = input.getCharPositionInLine();
            state.tokenStartLine = input.getLine();
        }


        /**
         * Return a token from this source; i.e., match a token on the char stream.
         */
        public Token nextToken() {
            while (true) {
                if (tokenBuffer.size() == 0) {
                    state.token = null;
                    state.channel = Token.DEFAULT_CHANNEL;
                    state.tokenStartCharIndex = input.index();
                    state.tokenStartCharPositionInLine = input.getCharPositionInLine();
                    state.tokenStartLine = input.getLine();
                    state.text = null;
                    if (input.LA(1) == CharStream.EOF) {
                        return Token.EOF_TOKEN;
                    }
                    try {
                        int m = input.mark();
                        state.backtracking = 1;
                        state.failed = false;
                        mTokens();
                        state.backtracking = 0;

                        if (state.failed) {
                            input.rewind(m);
                            input.consume();
                        } else {
                            emit();
                        }
                    } catch (RecognitionException re) {
                        // shouldn't happen in backtracking mode, but...
                        reportError(re);
                        recover(re);
                    }
                } else {
                    Token result = tokenBuffer.poll();
                    if (result != Token.SKIP_TOKEN || result != null) { // discard
                        // SKIP
                        // tokens
                        return result;
                    }
                }
            }
        }


    // delegates
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public PLSQLLexer() {} 
    public PLSQLLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public PLSQLLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "PLSQLLexer.g"; }

    // $ANTLR start "FOR_NOTATION"
    public final void mFOR_NOTATION() throws RecognitionException {
        try {
            int _type = FOR_NOTATION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:112:5: ( UNSIGNED_INTEGER '..' UNSIGNED_INTEGER )
            // PLSQLLexer.g:112:10: UNSIGNED_INTEGER '..' UNSIGNED_INTEGER
            {
            mUNSIGNED_INTEGER(); 


            state.type = UNSIGNED_INTEGER; emit(); advanceInput();

            match(".."); 



            state.type = DOUBLE_PERIOD; emit(); advanceInput();

            mUNSIGNED_INTEGER(); 


            state.type = UNSIGNED_INTEGER; emit(); advanceInput(); _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "FOR_NOTATION"

    // $ANTLR start "NATIONAL_CHAR_STRING_LIT"
    public final void mNATIONAL_CHAR_STRING_LIT() throws RecognitionException {
        try {
            int _type = NATIONAL_CHAR_STRING_LIT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:123:5: ( ( 'N' | 'n' ) '\\'' ( options {greedy=true; } :~ ( '\\'' | '\\r' | '\\n' ) | '\\'' '\\'' | NEWLINE )* '\\'' )
            // PLSQLLexer.g:123:10: ( 'N' | 'n' ) '\\'' ( options {greedy=true; } :~ ( '\\'' | '\\r' | '\\n' ) | '\\'' '\\'' | NEWLINE )* '\\''
            {
            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            match('\''); 

            // PLSQLLexer.g:123:27: ( options {greedy=true; } :~ ( '\\'' | '\\r' | '\\n' ) | '\\'' '\\'' | NEWLINE )*
            loop1:
            do {
                int alt1=4;
                int LA1_0 = input.LA(1);

                if ( (LA1_0=='\'') ) {
                    int LA1_1 = input.LA(2);

                    if ( (LA1_1=='\'') ) {
                        alt1=2;
                    }


                }
                else if ( ((LA1_0 >= '\u0000' && LA1_0 <= '\t')||(LA1_0 >= '\u000B' && LA1_0 <= '\f')||(LA1_0 >= '\u000E' && LA1_0 <= '&')||(LA1_0 >= '(' && LA1_0 <= '\uFFFF')) ) {
                    alt1=1;
                }
                else if ( (LA1_0=='\n'||LA1_0=='\r') ) {
                    alt1=3;
                }


                switch (alt1) {
            	case 1 :
            	    // PLSQLLexer.g:123:51: ~ ( '\\'' | '\\r' | '\\n' )
            	    {
            	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '&')||(input.LA(1) >= '(' && input.LA(1) <= '\uFFFF') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;
            	case 2 :
            	    // PLSQLLexer.g:123:76: '\\'' '\\''
            	    {
            	    match('\''); 

            	    match('\''); 

            	    }
            	    break;
            	case 3 :
            	    // PLSQLLexer.g:123:88: NEWLINE
            	    {
            	    mNEWLINE(); 


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            match('\''); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NATIONAL_CHAR_STRING_LIT"

    // $ANTLR start "BIT_STRING_LIT"
    public final void mBIT_STRING_LIT() throws RecognitionException {
        try {
            int _type = BIT_STRING_LIT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:130:5: ( ( 'B' | 'b' ) ( '\\'' ( '0' | '1' )* '\\'' ( SEPARATOR )? )+ )
            // PLSQLLexer.g:130:10: ( 'B' | 'b' ) ( '\\'' ( '0' | '1' )* '\\'' ( SEPARATOR )? )+
            {
            if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            // PLSQLLexer.g:130:22: ( '\\'' ( '0' | '1' )* '\\'' ( SEPARATOR )? )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0=='\'') ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // PLSQLLexer.g:130:23: '\\'' ( '0' | '1' )* '\\'' ( SEPARATOR )?
            	    {
            	    match('\''); 

            	    // PLSQLLexer.g:130:28: ( '0' | '1' )*
            	    loop2:
            	    do {
            	        int alt2=2;
            	        int LA2_0 = input.LA(1);

            	        if ( ((LA2_0 >= '0' && LA2_0 <= '1')) ) {
            	            alt2=1;
            	        }


            	        switch (alt2) {
            	    	case 1 :
            	    	    // PLSQLLexer.g:
            	    	    {
            	    	    if ( (input.LA(1) >= '0' && input.LA(1) <= '1') ) {
            	    	        input.consume();
            	    	    }
            	    	    else {
            	    	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	    	        recover(mse);
            	    	        throw mse;
            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop2;
            	        }
            	    } while (true);


            	    match('\''); 

            	    // PLSQLLexer.g:130:46: ( SEPARATOR )?
            	    int alt3=2;
            	    int LA3_0 = input.LA(1);

            	    if ( ((LA3_0 >= '\t' && LA3_0 <= '\n')||LA3_0=='\r'||LA3_0==' '||LA3_0=='-'||LA3_0=='/') ) {
            	        alt3=1;
            	    }
            	    switch (alt3) {
            	        case 1 :
            	            // PLSQLLexer.g:130:46: SEPARATOR
            	            {
            	            mSEPARATOR(); 


            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "BIT_STRING_LIT"

    // $ANTLR start "HEX_STRING_LIT"
    public final void mHEX_STRING_LIT() throws RecognitionException {
        try {
            int _type = HEX_STRING_LIT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:138:5: ( ( 'X' | 'x' ) ( '\\'' ( 'a' .. 'f' | 'A' .. 'F' | '0' .. '9' )* '\\'' ( SEPARATOR )? )+ )
            // PLSQLLexer.g:138:10: ( 'X' | 'x' ) ( '\\'' ( 'a' .. 'f' | 'A' .. 'F' | '0' .. '9' )* '\\'' ( SEPARATOR )? )+
            {
            if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            // PLSQLLexer.g:138:22: ( '\\'' ( 'a' .. 'f' | 'A' .. 'F' | '0' .. '9' )* '\\'' ( SEPARATOR )? )+
            int cnt7=0;
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0=='\'') ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // PLSQLLexer.g:138:23: '\\'' ( 'a' .. 'f' | 'A' .. 'F' | '0' .. '9' )* '\\'' ( SEPARATOR )?
            	    {
            	    match('\''); 

            	    // PLSQLLexer.g:138:28: ( 'a' .. 'f' | 'A' .. 'F' | '0' .. '9' )*
            	    loop5:
            	    do {
            	        int alt5=2;
            	        int LA5_0 = input.LA(1);

            	        if ( ((LA5_0 >= '0' && LA5_0 <= '9')||(LA5_0 >= 'A' && LA5_0 <= 'F')||(LA5_0 >= 'a' && LA5_0 <= 'f')) ) {
            	            alt5=1;
            	        }


            	        switch (alt5) {
            	    	case 1 :
            	    	    // PLSQLLexer.g:
            	    	    {
            	    	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'F')||(input.LA(1) >= 'a' && input.LA(1) <= 'f') ) {
            	    	        input.consume();
            	    	    }
            	    	    else {
            	    	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	    	        recover(mse);
            	    	        throw mse;
            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop5;
            	        }
            	    } while (true);


            	    match('\''); 

            	    // PLSQLLexer.g:138:67: ( SEPARATOR )?
            	    int alt6=2;
            	    int LA6_0 = input.LA(1);

            	    if ( ((LA6_0 >= '\t' && LA6_0 <= '\n')||LA6_0=='\r'||LA6_0==' '||LA6_0=='-'||LA6_0=='/') ) {
            	        alt6=1;
            	    }
            	    switch (alt6) {
            	        case 1 :
            	            // PLSQLLexer.g:138:67: SEPARATOR
            	            {
            	            mSEPARATOR(); 


            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt7 >= 1 ) break loop7;
                        EarlyExitException eee =
                            new EarlyExitException(7, input);
                        throw eee;
                }
                cnt7++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "HEX_STRING_LIT"

    // $ANTLR start "PERIOD"
    public final void mPERIOD() throws RecognitionException {
        try {
            int _type = PERIOD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:143:5: ( '.' )
            // PLSQLLexer.g:143:10: '.'
            {
            match('.'); 

                if ((char) input.LA(1) == '.') {
                        input.consume();
                        _type = DOUBLE_PERIOD;
                    }
                

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PERIOD"

    // $ANTLR start "EXACT_NUM_LIT"
    public final void mEXACT_NUM_LIT() throws RecognitionException {
        try {
            int _type = EXACT_NUM_LIT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:157:5: ( UNSIGNED_INTEGER ( '.' UNSIGNED_INTEGER |) ( ( 'E' | 'e' ) ( '+' | '-' )? UNSIGNED_INTEGER )? | '.' UNSIGNED_INTEGER ( ( 'E' | 'e' ) ( '+' | '-' )? UNSIGNED_INTEGER )? )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( ((LA13_0 >= '0' && LA13_0 <= '9')) ) {
                alt13=1;
            }
            else if ( (LA13_0=='.') ) {
                alt13=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;

            }
            switch (alt13) {
                case 1 :
                    // PLSQLLexer.g:157:10: UNSIGNED_INTEGER ( '.' UNSIGNED_INTEGER |) ( ( 'E' | 'e' ) ( '+' | '-' )? UNSIGNED_INTEGER )?
                    {
                    mUNSIGNED_INTEGER(); 


                    // PLSQLLexer.g:158:13: ( '.' UNSIGNED_INTEGER |)
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0=='.') ) {
                        alt8=1;
                    }
                    else {
                        alt8=2;
                    }
                    switch (alt8) {
                        case 1 :
                            // PLSQLLexer.g:158:15: '.' UNSIGNED_INTEGER
                            {
                            match('.'); 

                            mUNSIGNED_INTEGER(); 


                            }
                            break;
                        case 2 :
                            // PLSQLLexer.g:159:18: 
                            {
                            _type = UNSIGNED_INTEGER;

                            }
                            break;

                    }


                    // PLSQLLexer.g:160:15: ( ( 'E' | 'e' ) ( '+' | '-' )? UNSIGNED_INTEGER )?
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( (LA10_0=='E'||LA10_0=='e') ) {
                        alt10=1;
                    }
                    switch (alt10) {
                        case 1 :
                            // PLSQLLexer.g:160:17: ( 'E' | 'e' ) ( '+' | '-' )? UNSIGNED_INTEGER
                            {
                            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                                input.consume();
                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                recover(mse);
                                throw mse;
                            }


                            // PLSQLLexer.g:160:29: ( '+' | '-' )?
                            int alt9=2;
                            int LA9_0 = input.LA(1);

                            if ( (LA9_0=='+'||LA9_0=='-') ) {
                                alt9=1;
                            }
                            switch (alt9) {
                                case 1 :
                                    // PLSQLLexer.g:
                                    {
                                    if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                                        input.consume();
                                    }
                                    else {
                                        MismatchedSetException mse = new MismatchedSetException(null,input);
                                        recover(mse);
                                        throw mse;
                                    }


                                    }
                                    break;

                            }


                            mUNSIGNED_INTEGER(); 


                            _type = APPROXIMATE_NUM_LIT;

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // PLSQLLexer.g:161:10: '.' UNSIGNED_INTEGER ( ( 'E' | 'e' ) ( '+' | '-' )? UNSIGNED_INTEGER )?
                    {
                    match('.'); 

                    mUNSIGNED_INTEGER(); 


                    // PLSQLLexer.g:161:31: ( ( 'E' | 'e' ) ( '+' | '-' )? UNSIGNED_INTEGER )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0=='E'||LA12_0=='e') ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // PLSQLLexer.g:161:33: ( 'E' | 'e' ) ( '+' | '-' )? UNSIGNED_INTEGER
                            {
                            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                                input.consume();
                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                recover(mse);
                                throw mse;
                            }


                            // PLSQLLexer.g:161:45: ( '+' | '-' )?
                            int alt11=2;
                            int LA11_0 = input.LA(1);

                            if ( (LA11_0=='+'||LA11_0=='-') ) {
                                alt11=1;
                            }
                            switch (alt11) {
                                case 1 :
                                    // PLSQLLexer.g:
                                    {
                                    if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                                        input.consume();
                                    }
                                    else {
                                        MismatchedSetException mse = new MismatchedSetException(null,input);
                                        recover(mse);
                                        throw mse;
                                    }


                                    }
                                    break;

                            }


                            mUNSIGNED_INTEGER(); 


                            _type = APPROXIMATE_NUM_LIT;

                            }
                            break;

                    }


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "EXACT_NUM_LIT"

    // $ANTLR start "CHAR_STRING"
    public final void mCHAR_STRING() throws RecognitionException {
        try {
            int _type = CHAR_STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:168:5: ( '\\'' ( options {greedy=true; } :~ ( '\\'' | '\\r' | '\\n' ) | '\\'' '\\'' | NEWLINE )* '\\'' )
            // PLSQLLexer.g:168:10: '\\'' ( options {greedy=true; } :~ ( '\\'' | '\\r' | '\\n' ) | '\\'' '\\'' | NEWLINE )* '\\''
            {
            match('\''); 

            // PLSQLLexer.g:168:15: ( options {greedy=true; } :~ ( '\\'' | '\\r' | '\\n' ) | '\\'' '\\'' | NEWLINE )*
            loop14:
            do {
                int alt14=4;
                int LA14_0 = input.LA(1);

                if ( (LA14_0=='\'') ) {
                    int LA14_1 = input.LA(2);

                    if ( (LA14_1=='\'') ) {
                        alt14=2;
                    }


                }
                else if ( ((LA14_0 >= '\u0000' && LA14_0 <= '\t')||(LA14_0 >= '\u000B' && LA14_0 <= '\f')||(LA14_0 >= '\u000E' && LA14_0 <= '&')||(LA14_0 >= '(' && LA14_0 <= '\uFFFF')) ) {
                    alt14=1;
                }
                else if ( (LA14_0=='\n'||LA14_0=='\r') ) {
                    alt14=3;
                }


                switch (alt14) {
            	case 1 :
            	    // PLSQLLexer.g:168:39: ~ ( '\\'' | '\\r' | '\\n' )
            	    {
            	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '&')||(input.LA(1) >= '(' && input.LA(1) <= '\uFFFF') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;
            	case 2 :
            	    // PLSQLLexer.g:168:63: '\\'' '\\''
            	    {
            	    match('\''); 

            	    match('\''); 

            	    }
            	    break;
            	case 3 :
            	    // PLSQLLexer.g:168:75: NEWLINE
            	    {
            	    mNEWLINE(); 


            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);


            match('\''); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CHAR_STRING"

    // $ANTLR start "CHAR_STRING_PERL"
    public final void mCHAR_STRING_PERL() throws RecognitionException {
        try {
            int _type = CHAR_STRING_PERL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:173:21: ( ( 'q' | 'Q' ) ( QS_ANGLE | QS_BRACE | QS_BRACK | QS_PAREN | QS_OTHER ) )
            // PLSQLLexer.g:173:23: ( 'q' | 'Q' ) ( QS_ANGLE | QS_BRACE | QS_BRACK | QS_PAREN | QS_OTHER )
            {
            if ( input.LA(1)=='Q'||input.LA(1)=='q' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            // PLSQLLexer.g:173:33: ( QS_ANGLE | QS_BRACE | QS_BRACK | QS_PAREN | QS_OTHER )
            int alt15=5;
            int LA15_0 = input.LA(1);

            if ( (LA15_0=='\'') ) {
                int LA15_1 = input.LA(2);

                if ( (LA15_1=='<') ) {
                    alt15=1;
                }
                else if ( (LA15_1=='{') ) {
                    alt15=2;
                }
                else if ( (LA15_1=='[') ) {
                    alt15=3;
                }
                else if ( (LA15_1=='(') ) {
                    alt15=4;
                }
                else if ( ((LA15_1 >= '\u0000' && LA15_1 <= '\b')||(LA15_1 >= '\u000B' && LA15_1 <= '\f')||(LA15_1 >= '\u000E' && LA15_1 <= '\u001F')||(LA15_1 >= '!' && LA15_1 <= '\'')||(LA15_1 >= ')' && LA15_1 <= ';')||(LA15_1 >= '=' && LA15_1 <= 'Z')||(LA15_1 >= '\\' && LA15_1 <= 'z')||(LA15_1 >= '|' && LA15_1 <= '\uFFFF')) ) {
                    alt15=5;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 15, 1, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;

            }
            switch (alt15) {
                case 1 :
                    // PLSQLLexer.g:173:35: QS_ANGLE
                    {
                    mQS_ANGLE(); 


                    }
                    break;
                case 2 :
                    // PLSQLLexer.g:173:46: QS_BRACE
                    {
                    mQS_BRACE(); 


                    }
                    break;
                case 3 :
                    // PLSQLLexer.g:173:57: QS_BRACK
                    {
                    mQS_BRACK(); 


                    }
                    break;
                case 4 :
                    // PLSQLLexer.g:173:68: QS_PAREN
                    {
                    mQS_PAREN(); 


                    }
                    break;
                case 5 :
                    // PLSQLLexer.g:173:79: QS_OTHER
                    {
                    mQS_OTHER(); 


                    }
                    break;

            }


            _type = CHAR_STRING;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CHAR_STRING_PERL"

    // $ANTLR start "QUOTE"
    public final void mQUOTE() throws RecognitionException {
        try {
            // PLSQLLexer.g:174:21: ( '\\'' )
            // PLSQLLexer.g:174:23: '\\''
            {
            match('\''); 

            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "QUOTE"

    // $ANTLR start "QS_ANGLE"
    public final void mQS_ANGLE() throws RecognitionException {
        try {
            // PLSQLLexer.g:175:21: ( QUOTE '<' ( options {greedy=false; } : . )* '>' QUOTE )
            // PLSQLLexer.g:175:23: QUOTE '<' ( options {greedy=false; } : . )* '>' QUOTE
            {
            mQUOTE(); 


            match('<'); 

            // PLSQLLexer.g:175:33: ( options {greedy=false; } : . )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0=='>') ) {
                    int LA16_1 = input.LA(2);

                    if ( (LA16_1=='\'') ) {
                        alt16=2;
                    }
                    else if ( ((LA16_1 >= '\u0000' && LA16_1 <= '&')||(LA16_1 >= '(' && LA16_1 <= '\uFFFF')) ) {
                        alt16=1;
                    }


                }
                else if ( ((LA16_0 >= '\u0000' && LA16_0 <= '=')||(LA16_0 >= '?' && LA16_0 <= '\uFFFF')) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // PLSQLLexer.g:175:61: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);


            match('>'); 

            mQUOTE(); 


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "QS_ANGLE"

    // $ANTLR start "QS_BRACE"
    public final void mQS_BRACE() throws RecognitionException {
        try {
            // PLSQLLexer.g:176:21: ( QUOTE '{' ( options {greedy=false; } : . )* '}' QUOTE )
            // PLSQLLexer.g:176:23: QUOTE '{' ( options {greedy=false; } : . )* '}' QUOTE
            {
            mQUOTE(); 


            match('{'); 

            // PLSQLLexer.g:176:33: ( options {greedy=false; } : . )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0=='}') ) {
                    int LA17_1 = input.LA(2);

                    if ( (LA17_1=='\'') ) {
                        alt17=2;
                    }
                    else if ( ((LA17_1 >= '\u0000' && LA17_1 <= '&')||(LA17_1 >= '(' && LA17_1 <= '\uFFFF')) ) {
                        alt17=1;
                    }


                }
                else if ( ((LA17_0 >= '\u0000' && LA17_0 <= '|')||(LA17_0 >= '~' && LA17_0 <= '\uFFFF')) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // PLSQLLexer.g:176:61: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);


            match('}'); 

            mQUOTE(); 


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "QS_BRACE"

    // $ANTLR start "QS_BRACK"
    public final void mQS_BRACK() throws RecognitionException {
        try {
            // PLSQLLexer.g:177:21: ( QUOTE '[' ( options {greedy=false; } : . )* ']' QUOTE )
            // PLSQLLexer.g:177:23: QUOTE '[' ( options {greedy=false; } : . )* ']' QUOTE
            {
            mQUOTE(); 


            match('['); 

            // PLSQLLexer.g:177:33: ( options {greedy=false; } : . )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==']') ) {
                    int LA18_1 = input.LA(2);

                    if ( (LA18_1=='\'') ) {
                        alt18=2;
                    }
                    else if ( ((LA18_1 >= '\u0000' && LA18_1 <= '&')||(LA18_1 >= '(' && LA18_1 <= '\uFFFF')) ) {
                        alt18=1;
                    }


                }
                else if ( ((LA18_0 >= '\u0000' && LA18_0 <= '\\')||(LA18_0 >= '^' && LA18_0 <= '\uFFFF')) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // PLSQLLexer.g:177:61: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);


            match(']'); 

            mQUOTE(); 


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "QS_BRACK"

    // $ANTLR start "QS_PAREN"
    public final void mQS_PAREN() throws RecognitionException {
        try {
            // PLSQLLexer.g:178:21: ( QUOTE '(' ( options {greedy=false; } : . )* ')' QUOTE )
            // PLSQLLexer.g:178:23: QUOTE '(' ( options {greedy=false; } : . )* ')' QUOTE
            {
            mQUOTE(); 


            match('('); 

            // PLSQLLexer.g:178:33: ( options {greedy=false; } : . )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==')') ) {
                    int LA19_1 = input.LA(2);

                    if ( (LA19_1=='\'') ) {
                        alt19=2;
                    }
                    else if ( ((LA19_1 >= '\u0000' && LA19_1 <= '&')||(LA19_1 >= '(' && LA19_1 <= '\uFFFF')) ) {
                        alt19=1;
                    }


                }
                else if ( ((LA19_0 >= '\u0000' && LA19_0 <= '(')||(LA19_0 >= '*' && LA19_0 <= '\uFFFF')) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // PLSQLLexer.g:178:61: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop19;
                }
            } while (true);


            match(')'); 

            mQUOTE(); 


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "QS_PAREN"

    // $ANTLR start "QS_OTHER_CH"
    public final void mQS_OTHER_CH() throws RecognitionException {
        try {
            // PLSQLLexer.g:180:21: (~ ( '<' | '{' | '[' | '(' | ' ' | '\\t' | '\\n' | '\\r' ) )
            // PLSQLLexer.g:
            {
            if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\b')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '\u001F')||(input.LA(1) >= '!' && input.LA(1) <= '\'')||(input.LA(1) >= ')' && input.LA(1) <= ';')||(input.LA(1) >= '=' && input.LA(1) <= 'Z')||(input.LA(1) >= '\\' && input.LA(1) <= 'z')||(input.LA(1) >= '|' && input.LA(1) <= '\uFFFF') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "QS_OTHER_CH"

    // $ANTLR start "QS_OTHER"
    public final void mQS_OTHER() throws RecognitionException {
        try {
            CommonToken delimiter=null;

            // PLSQLLexer.g:190:3: ( QUOTE delimiter= QS_OTHER_CH ({...}? => . )* ({...}? => . ) QUOTE )
            // PLSQLLexer.g:191:3: QUOTE delimiter= QS_OTHER_CH ({...}? => . )* ({...}? => . ) QUOTE
            {
            mQUOTE(); 


            int delimiterStart780 = getCharIndex();
            int delimiterStartLine780 = getLine();
            int delimiterStartCharPos780 = getCharPositionInLine();
            mQS_OTHER_CH(); 
            delimiter = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, delimiterStart780, getCharIndex()-1);
            delimiter.setLine(delimiterStartLine780);
            delimiter.setCharPositionInLine(delimiterStartCharPos780);


            // PLSQLLexer.g:193:5: ({...}? => . )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( ((LA20_0 >= '\u0000' && LA20_0 <= '\uFFFF')) && ((( input.LT(1) != (delimiter!=null?delimiter.getText():null).charAt(0) || ( input.LT(1) == (delimiter!=null?delimiter.getText():null).charAt(0) && input.LT(2) != '\'') )||( input.LT(1) == (delimiter!=null?delimiter.getText():null).charAt(0) && input.LT(2) == '\'' )))) {
                    int LA20_1 = input.LA(2);

                    if ( (LA20_1=='\'') && ((( input.LT(1) != (delimiter!=null?delimiter.getText():null).charAt(0) || ( input.LT(1) == (delimiter!=null?delimiter.getText():null).charAt(0) && input.LT(2) != '\'') )||( input.LT(1) == (delimiter!=null?delimiter.getText():null).charAt(0) && input.LT(2) == '\'' )))) {
                        int LA20_2 = input.LA(3);

                        if ( ((LA20_2 >= '\u0000' && LA20_2 <= '\uFFFF')) && (( input.LT(1) != (delimiter!=null?delimiter.getText():null).charAt(0) || ( input.LT(1) == (delimiter!=null?delimiter.getText():null).charAt(0) && input.LT(2) != '\'') ))) {
                            alt20=1;
                        }


                    }
                    else if ( ((LA20_1 >= '\u0000' && LA20_1 <= '&')||(LA20_1 >= '(' && LA20_1 <= '\uFFFF')) && (( input.LT(1) != (delimiter!=null?delimiter.getText():null).charAt(0) || ( input.LT(1) == (delimiter!=null?delimiter.getText():null).charAt(0) && input.LT(2) != '\'') ))) {
                        alt20=1;
                    }


                }


                switch (alt20) {
            	case 1 :
            	    // PLSQLLexer.g:193:7: {...}? => .
            	    {
            	    if ( !(( input.LT(1) != (delimiter!=null?delimiter.getText():null).charAt(0) || ( input.LT(1) == (delimiter!=null?delimiter.getText():null).charAt(0) && input.LT(2) != '\'') )) ) {
            	        throw new FailedPredicateException(input, "QS_OTHER", " input.LT(1) != $delimiter.text.charAt(0) || ( input.LT(1) == $delimiter.text.charAt(0) && input.LT(2) != '\\'') ");
            	    }

            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);


            // PLSQLLexer.g:194:5: ({...}? => . )
            // PLSQLLexer.g:194:7: {...}? => .
            {
            if ( !(( input.LT(1) == (delimiter!=null?delimiter.getText():null).charAt(0) && input.LT(2) == '\'' )) ) {
                throw new FailedPredicateException(input, "QS_OTHER", " input.LT(1) == $delimiter.text.charAt(0) && input.LT(2) == '\\'' ");
            }

            matchAny(); 

            }


            mQUOTE(); 


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "QS_OTHER"

    // $ANTLR start "DELIMITED_ID"
    public final void mDELIMITED_ID() throws RecognitionException {
        try {
            int _type = DELIMITED_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:204:5: ( '\"' (~ ( '\"' | '\\r' | '\\n' ) | '\"' '\"' )+ '\"' )
            // PLSQLLexer.g:204:10: '\"' (~ ( '\"' | '\\r' | '\\n' ) | '\"' '\"' )+ '\"'
            {
            match('\"'); 

            // PLSQLLexer.g:204:14: (~ ( '\"' | '\\r' | '\\n' ) | '\"' '\"' )+
            int cnt21=0;
            loop21:
            do {
                int alt21=3;
                int LA21_0 = input.LA(1);

                if ( (LA21_0=='\"') ) {
                    int LA21_1 = input.LA(2);

                    if ( (LA21_1=='\"') ) {
                        alt21=2;
                    }


                }
                else if ( ((LA21_0 >= '\u0000' && LA21_0 <= '\t')||(LA21_0 >= '\u000B' && LA21_0 <= '\f')||(LA21_0 >= '\u000E' && LA21_0 <= '!')||(LA21_0 >= '#' && LA21_0 <= '\uFFFF')) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // PLSQLLexer.g:204:15: ~ ( '\"' | '\\r' | '\\n' )
            	    {
            	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '!')||(input.LA(1) >= '#' && input.LA(1) <= '\uFFFF') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;
            	case 2 :
            	    // PLSQLLexer.g:204:38: '\"' '\"'
            	    {
            	    match('\"'); 

            	    match('\"'); 

            	    }
            	    break;

            	default :
            	    if ( cnt21 >= 1 ) break loop21;
                        EarlyExitException eee =
                            new EarlyExitException(21, input);
                        throw eee;
                }
                cnt21++;
            } while (true);


            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DELIMITED_ID"

    // $ANTLR start "PERCENT"
    public final void mPERCENT() throws RecognitionException {
        try {
            int _type = PERCENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:210:5: ( '%' )
            // PLSQLLexer.g:210:10: '%'
            {
            match('%'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PERCENT"

    // $ANTLR start "AMPERSAND"
    public final void mAMPERSAND() throws RecognitionException {
        try {
            int _type = AMPERSAND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:214:5: ( '&' )
            // PLSQLLexer.g:214:10: '&'
            {
            match('&'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "AMPERSAND"

    // $ANTLR start "LEFT_PAREN"
    public final void mLEFT_PAREN() throws RecognitionException {
        try {
            int _type = LEFT_PAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:218:5: ( '(' )
            // PLSQLLexer.g:218:10: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LEFT_PAREN"

    // $ANTLR start "RIGHT_PAREN"
    public final void mRIGHT_PAREN() throws RecognitionException {
        try {
            int _type = RIGHT_PAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:222:5: ( ')' )
            // PLSQLLexer.g:222:10: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "RIGHT_PAREN"

    // $ANTLR start "DOUBLE_ASTERISK"
    public final void mDOUBLE_ASTERISK() throws RecognitionException {
        try {
            int _type = DOUBLE_ASTERISK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:226:5: ( '**' )
            // PLSQLLexer.g:226:10: '**'
            {
            match("**"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DOUBLE_ASTERISK"

    // $ANTLR start "ASTERISK"
    public final void mASTERISK() throws RecognitionException {
        try {
            int _type = ASTERISK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:230:5: ( '*' )
            // PLSQLLexer.g:230:10: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ASTERISK"

    // $ANTLR start "PLUS_SIGN"
    public final void mPLUS_SIGN() throws RecognitionException {
        try {
            int _type = PLUS_SIGN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:234:5: ( '+' )
            // PLSQLLexer.g:234:10: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLUS_SIGN"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:238:5: ( ',' )
            // PLSQLLexer.g:238:10: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMMA"

    // $ANTLR start "SOLIDUS"
    public final void mSOLIDUS() throws RecognitionException {
        try {
            int _type = SOLIDUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:242:5: ( '/' )
            // PLSQLLexer.g:242:10: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SOLIDUS"

    // $ANTLR start "AT_SIGN"
    public final void mAT_SIGN() throws RecognitionException {
        try {
            int _type = AT_SIGN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:246:5: ( '@' )
            // PLSQLLexer.g:246:10: '@'
            {
            match('@'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "AT_SIGN"

    // $ANTLR start "ASSIGN_OP"
    public final void mASSIGN_OP() throws RecognitionException {
        try {
            int _type = ASSIGN_OP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:250:5: ( ':=' )
            // PLSQLLexer.g:250:10: ':='
            {
            match(":="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ASSIGN_OP"

    // $ANTLR start "COLON"
    public final void mCOLON() throws RecognitionException {
        try {
            int _type = COLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:254:5: ( ':' )
            // PLSQLLexer.g:254:10: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COLON"

    // $ANTLR start "SEMICOLON"
    public final void mSEMICOLON() throws RecognitionException {
        try {
            int _type = SEMICOLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:258:5: ( ';' )
            // PLSQLLexer.g:258:10: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SEMICOLON"

    // $ANTLR start "LESS_THAN_OR_EQUALS_OP"
    public final void mLESS_THAN_OR_EQUALS_OP() throws RecognitionException {
        try {
            int _type = LESS_THAN_OR_EQUALS_OP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:262:5: ( '<=' )
            // PLSQLLexer.g:262:10: '<='
            {
            match("<="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LESS_THAN_OR_EQUALS_OP"

    // $ANTLR start "LESS_THAN_OP"
    public final void mLESS_THAN_OP() throws RecognitionException {
        try {
            int _type = LESS_THAN_OP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:266:5: ( '<' )
            // PLSQLLexer.g:266:10: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LESS_THAN_OP"

    // $ANTLR start "GREATER_THAN_OR_EQUALS_OP"
    public final void mGREATER_THAN_OR_EQUALS_OP() throws RecognitionException {
        try {
            int _type = GREATER_THAN_OR_EQUALS_OP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:270:5: ( '>=' )
            // PLSQLLexer.g:270:10: '>='
            {
            match(">="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "GREATER_THAN_OR_EQUALS_OP"

    // $ANTLR start "NOT_EQUAL_OP"
    public final void mNOT_EQUAL_OP() throws RecognitionException {
        try {
            int _type = NOT_EQUAL_OP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:274:5: ( '!=' | '<>' | '^=' | '~=' )
            int alt22=4;
            switch ( input.LA(1) ) {
            case '!':
                {
                alt22=1;
                }
                break;
            case '<':
                {
                alt22=2;
                }
                break;
            case '^':
                {
                alt22=3;
                }
                break;
            case '~':
                {
                alt22=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;

            }

            switch (alt22) {
                case 1 :
                    // PLSQLLexer.g:274:10: '!='
                    {
                    match("!="); 



                    }
                    break;
                case 2 :
                    // PLSQLLexer.g:275:10: '<>'
                    {
                    match("<>"); 



                    }
                    break;
                case 3 :
                    // PLSQLLexer.g:276:10: '^='
                    {
                    match("^="); 



                    }
                    break;
                case 4 :
                    // PLSQLLexer.g:277:10: '~='
                    {
                    match("~="); 



                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NOT_EQUAL_OP"

    // $ANTLR start "CARRET_OPERATOR_PART"
    public final void mCARRET_OPERATOR_PART() throws RecognitionException {
        try {
            int _type = CARRET_OPERATOR_PART;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:280:5: ( '^' )
            // PLSQLLexer.g:280:10: '^'
            {
            match('^'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CARRET_OPERATOR_PART"

    // $ANTLR start "TILDE_OPERATOR_PART"
    public final void mTILDE_OPERATOR_PART() throws RecognitionException {
        try {
            int _type = TILDE_OPERATOR_PART;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:284:5: ( '~' )
            // PLSQLLexer.g:284:10: '~'
            {
            match('~'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "TILDE_OPERATOR_PART"

    // $ANTLR start "EXCLAMATION_OPERATOR_PART"
    public final void mEXCLAMATION_OPERATOR_PART() throws RecognitionException {
        try {
            int _type = EXCLAMATION_OPERATOR_PART;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:288:5: ( '!' )
            // PLSQLLexer.g:288:10: '!'
            {
            match('!'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "EXCLAMATION_OPERATOR_PART"

    // $ANTLR start "GREATER_THAN_OP"
    public final void mGREATER_THAN_OP() throws RecognitionException {
        try {
            int _type = GREATER_THAN_OP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:292:5: ( '>' )
            // PLSQLLexer.g:292:10: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "GREATER_THAN_OP"

    // $ANTLR start "QUESTION_MARK"
    public final void mQUESTION_MARK() throws RecognitionException {
        try {
            int _type = QUESTION_MARK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:296:5: ( '?' )
            // PLSQLLexer.g:296:10: '?'
            {
            match('?'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "QUESTION_MARK"

    // $ANTLR start "CONCATENATION_OP"
    public final void mCONCATENATION_OP() throws RecognitionException {
        try {
            int _type = CONCATENATION_OP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:301:5: ( '||' )
            // PLSQLLexer.g:301:10: '||'
            {
            match("||"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CONCATENATION_OP"

    // $ANTLR start "VERTICAL_BAR"
    public final void mVERTICAL_BAR() throws RecognitionException {
        try {
            int _type = VERTICAL_BAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:305:5: ( '|' )
            // PLSQLLexer.g:305:10: '|'
            {
            match('|'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "VERTICAL_BAR"

    // $ANTLR start "EQUALS_OP"
    public final void mEQUALS_OP() throws RecognitionException {
        try {
            int _type = EQUALS_OP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:309:5: ( '=' )
            // PLSQLLexer.g:309:10: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "EQUALS_OP"

    // $ANTLR start "LEFT_BRACKET"
    public final void mLEFT_BRACKET() throws RecognitionException {
        try {
            int _type = LEFT_BRACKET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:314:5: ( '[' )
            // PLSQLLexer.g:314:10: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LEFT_BRACKET"

    // $ANTLR start "RIGHT_BRACKET"
    public final void mRIGHT_BRACKET() throws RecognitionException {
        try {
            int _type = RIGHT_BRACKET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:318:5: ( ']' )
            // PLSQLLexer.g:318:10: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "RIGHT_BRACKET"

    // $ANTLR start "INTRODUCER"
    public final void mINTRODUCER() throws RecognitionException {
        try {
            int _type = INTRODUCER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:325:5: ( '_' ( SEPARATOR )? )
            // PLSQLLexer.g:325:10: '_' ( SEPARATOR )?
            {
            match('_'); 

            // PLSQLLexer.g:325:14: ( SEPARATOR )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( ((LA23_0 >= '\t' && LA23_0 <= '\n')||LA23_0=='\r'||LA23_0==' '||LA23_0=='-'||LA23_0=='/') ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // PLSQLLexer.g:325:15: SEPARATOR
                    {
                    mSEPARATOR(); 


                    _type = UNDERSCORE;

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "INTRODUCER"

    // $ANTLR start "SEPARATOR"
    public final void mSEPARATOR() throws RecognitionException {
        try {
            int _type = SEPARATOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:331:5: ( '-' | COMMENT | ( SPACE | NEWLINE )+ )
            int alt25=3;
            switch ( input.LA(1) ) {
            case '-':
                {
                int LA25_1 = input.LA(2);

                if ( (LA25_1=='-') ) {
                    alt25=2;
                }
                else {
                    alt25=1;
                }
                }
                break;
            case '/':
                {
                alt25=2;
                }
                break;
            case '\t':
            case '\n':
            case '\r':
            case ' ':
                {
                alt25=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 25, 0, input);

                throw nvae;

            }

            switch (alt25) {
                case 1 :
                    // PLSQLLexer.g:331:10: '-'
                    {
                    match('-'); 

                    _type = MINUS_SIGN;

                    }
                    break;
                case 2 :
                    // PLSQLLexer.g:332:10: COMMENT
                    {
                    mCOMMENT(); 


                     _channel=HIDDEN; 

                    }
                    break;
                case 3 :
                    // PLSQLLexer.g:333:10: ( SPACE | NEWLINE )+
                    {
                    // PLSQLLexer.g:333:10: ( SPACE | NEWLINE )+
                    int cnt24=0;
                    loop24:
                    do {
                        int alt24=3;
                        int LA24_0 = input.LA(1);

                        if ( (LA24_0=='\t'||LA24_0==' ') ) {
                            alt24=1;
                        }
                        else if ( (LA24_0=='\n'||LA24_0=='\r') ) {
                            alt24=2;
                        }


                        switch (alt24) {
                    	case 1 :
                    	    // PLSQLLexer.g:333:11: SPACE
                    	    {
                    	    mSPACE(); 


                    	    }
                    	    break;
                    	case 2 :
                    	    // PLSQLLexer.g:333:19: NEWLINE
                    	    {
                    	    mNEWLINE(); 


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt24 >= 1 ) break loop24;
                                EarlyExitException eee =
                                    new EarlyExitException(24, input);
                                throw eee;
                        }
                        cnt24++;
                    } while (true);


                     _channel=HIDDEN; 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SEPARATOR"

    // $ANTLR start "SIMPLE_LETTER"
    public final void mSIMPLE_LETTER() throws RecognitionException {
        try {
            // PLSQLLexer.g:341:5: ( 'a' .. 'z' | 'A' .. 'Z' )
            // PLSQLLexer.g:
            {
            if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SIMPLE_LETTER"

    // $ANTLR start "UNSIGNED_INTEGER"
    public final void mUNSIGNED_INTEGER() throws RecognitionException {
        try {
            // PLSQLLexer.g:350:5: ( ( '0' .. '9' )+ )
            // PLSQLLexer.g:350:10: ( '0' .. '9' )+
            {
            // PLSQLLexer.g:350:10: ( '0' .. '9' )+
            int cnt26=0;
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( ((LA26_0 >= '0' && LA26_0 <= '9')) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // PLSQLLexer.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt26 >= 1 ) break loop26;
                        EarlyExitException eee =
                            new EarlyExitException(26, input);
                        throw eee;
                }
                cnt26++;
            } while (true);


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "UNSIGNED_INTEGER"

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            // PLSQLLexer.g:357:5: ( '--' (~ ( '\\r' | '\\n' ) )* ( NEWLINE | EOF ) | '/*' ( options {greedy=false; } : . )* '*/' )
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0=='-') ) {
                alt30=1;
            }
            else if ( (LA30_0=='/') ) {
                alt30=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 30, 0, input);

                throw nvae;

            }
            switch (alt30) {
                case 1 :
                    // PLSQLLexer.g:357:10: '--' (~ ( '\\r' | '\\n' ) )* ( NEWLINE | EOF )
                    {
                    match("--"); 



                    // PLSQLLexer.g:357:15: (~ ( '\\r' | '\\n' ) )*
                    loop27:
                    do {
                        int alt27=2;
                        int LA27_0 = input.LA(1);

                        if ( ((LA27_0 >= '\u0000' && LA27_0 <= '\t')||(LA27_0 >= '\u000B' && LA27_0 <= '\f')||(LA27_0 >= '\u000E' && LA27_0 <= '\uFFFF')) ) {
                            alt27=1;
                        }


                        switch (alt27) {
                    	case 1 :
                    	    // PLSQLLexer.g:
                    	    {
                    	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '\uFFFF') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop27;
                        }
                    } while (true);


                    // PLSQLLexer.g:357:35: ( NEWLINE | EOF )
                    int alt28=2;
                    int LA28_0 = input.LA(1);

                    if ( (LA28_0=='\n'||LA28_0=='\r') ) {
                        alt28=1;
                    }
                    else {
                        alt28=2;
                    }
                    switch (alt28) {
                        case 1 :
                            // PLSQLLexer.g:357:36: NEWLINE
                            {
                            mNEWLINE(); 


                            }
                            break;
                        case 2 :
                            // PLSQLLexer.g:357:44: EOF
                            {
                            match(EOF); 


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // PLSQLLexer.g:358:10: '/*' ( options {greedy=false; } : . )* '*/'
                    {
                    match("/*"); 



                    // PLSQLLexer.g:358:15: ( options {greedy=false; } : . )*
                    loop29:
                    do {
                        int alt29=2;
                        int LA29_0 = input.LA(1);

                        if ( (LA29_0=='*') ) {
                            int LA29_1 = input.LA(2);

                            if ( (LA29_1=='/') ) {
                                alt29=2;
                            }
                            else if ( ((LA29_1 >= '\u0000' && LA29_1 <= '.')||(LA29_1 >= '0' && LA29_1 <= '\uFFFF')) ) {
                                alt29=1;
                            }


                        }
                        else if ( ((LA29_0 >= '\u0000' && LA29_0 <= ')')||(LA29_0 >= '+' && LA29_0 <= '\uFFFF')) ) {
                            alt29=1;
                        }


                        switch (alt29) {
                    	case 1 :
                    	    // PLSQLLexer.g:358:41: .
                    	    {
                    	    matchAny(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop29;
                        }
                    } while (true);


                    match("*/"); 



                    }
                    break;

            }

        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMMENT"

    // $ANTLR start "NEWLINE"
    public final void mNEWLINE() throws RecognitionException {
        try {
            // PLSQLLexer.g:365:5: ( '\\r' ( options {greedy=true; } : '\\n' )? | '\\n' )
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0=='\r') ) {
                alt32=1;
            }
            else if ( (LA32_0=='\n') ) {
                alt32=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 32, 0, input);

                throw nvae;

            }
            switch (alt32) {
                case 1 :
                    // PLSQLLexer.g:365:10: '\\r' ( options {greedy=true; } : '\\n' )?
                    {
                    match('\r'); 

                    // PLSQLLexer.g:365:15: ( options {greedy=true; } : '\\n' )?
                    int alt31=2;
                    int LA31_0 = input.LA(1);

                    if ( (LA31_0=='\n') ) {
                        alt31=1;
                    }
                    switch (alt31) {
                        case 1 :
                            // PLSQLLexer.g:365:39: '\\n'
                            {
                            match('\n'); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // PLSQLLexer.g:366:10: '\\n'
                    {
                    match('\n'); 

                    }
                    break;

            }

        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NEWLINE"

    // $ANTLR start "SPACE"
    public final void mSPACE() throws RecognitionException {
        try {
            // PLSQLLexer.g:372:10: ( ' ' | '\\t' )
            // PLSQLLexer.g:
            {
            if ( input.LA(1)=='\t'||input.LA(1)==' ' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SPACE"

    // $ANTLR start "APPROXIMATE_NUM_LIT"
    public final void mAPPROXIMATE_NUM_LIT() throws RecognitionException {
        try {
            // PLSQLLexer.g:377:29: ()
            // PLSQLLexer.g:377:31: 
            {
            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "APPROXIMATE_NUM_LIT"

    // $ANTLR start "MINUS_SIGN"
    public final void mMINUS_SIGN() throws RecognitionException {
        try {
            // PLSQLLexer.g:378:20: ()
            // PLSQLLexer.g:378:22: 
            {
            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "MINUS_SIGN"

    // $ANTLR start "UNDERSCORE"
    public final void mUNDERSCORE() throws RecognitionException {
        try {
            // PLSQLLexer.g:379:20: ()
            // PLSQLLexer.g:379:22: 
            {
            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "UNDERSCORE"

    // $ANTLR start "DOUBLE_PERIOD"
    public final void mDOUBLE_PERIOD() throws RecognitionException {
        try {
            // PLSQLLexer.g:380:23: ()
            // PLSQLLexer.g:380:25: 
            {
            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DOUBLE_PERIOD"

    // $ANTLR start "SQL92_RESERVED_ALL"
    public final void mSQL92_RESERVED_ALL() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_ALL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:386:5: ( 'all' )
            // PLSQLLexer.g:386:10: 'all'
            {
            match("all"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_ALL"

    // $ANTLR start "SQL92_RESERVED_ALTER"
    public final void mSQL92_RESERVED_ALTER() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_ALTER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:390:5: ( 'alter' )
            // PLSQLLexer.g:390:10: 'alter'
            {
            match("alter"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_ALTER"

    // $ANTLR start "SQL92_RESERVED_AND"
    public final void mSQL92_RESERVED_AND() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_AND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:394:5: ( 'and' )
            // PLSQLLexer.g:394:10: 'and'
            {
            match("and"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_AND"

    // $ANTLR start "SQL92_RESERVED_ANY"
    public final void mSQL92_RESERVED_ANY() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_ANY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:398:5: ( 'any' )
            // PLSQLLexer.g:398:10: 'any'
            {
            match("any"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_ANY"

    // $ANTLR start "SQL92_RESERVED_AS"
    public final void mSQL92_RESERVED_AS() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_AS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:402:5: ( 'as' )
            // PLSQLLexer.g:402:10: 'as'
            {
            match("as"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_AS"

    // $ANTLR start "SQL92_RESERVED_ASC"
    public final void mSQL92_RESERVED_ASC() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_ASC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:406:5: ( 'asc' )
            // PLSQLLexer.g:406:10: 'asc'
            {
            match("asc"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_ASC"

    // $ANTLR start "SQL92_RESERVED_BEGIN"
    public final void mSQL92_RESERVED_BEGIN() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_BEGIN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:414:5: ( 'begin' )
            // PLSQLLexer.g:414:10: 'begin'
            {
            match("begin"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_BEGIN"

    // $ANTLR start "SQL92_RESERVED_BETWEEN"
    public final void mSQL92_RESERVED_BETWEEN() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_BETWEEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:418:5: ( 'between' )
            // PLSQLLexer.g:418:10: 'between'
            {
            match("between"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_BETWEEN"

    // $ANTLR start "SQL92_RESERVED_BY"
    public final void mSQL92_RESERVED_BY() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_BY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:422:5: ( 'by' )
            // PLSQLLexer.g:422:10: 'by'
            {
            match("by"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_BY"

    // $ANTLR start "SQL92_RESERVED_CASE"
    public final void mSQL92_RESERVED_CASE() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_CASE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:426:5: ( 'case' )
            // PLSQLLexer.g:426:10: 'case'
            {
            match("case"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_CASE"

    // $ANTLR start "SQL92_RESERVED_CHECK"
    public final void mSQL92_RESERVED_CHECK() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_CHECK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:430:5: ( 'check' )
            // PLSQLLexer.g:430:10: 'check'
            {
            match("check"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_CHECK"

    // $ANTLR start "PLSQL_RESERVED_CLUSTERS"
    public final void mPLSQL_RESERVED_CLUSTERS() throws RecognitionException {
        try {
            int _type = PLSQL_RESERVED_CLUSTERS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:434:5: ( 'clusters' )
            // PLSQLLexer.g:434:10: 'clusters'
            {
            match("clusters"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLSQL_RESERVED_CLUSTERS"

    // $ANTLR start "PLSQL_RESERVED_COLAUTH"
    public final void mPLSQL_RESERVED_COLAUTH() throws RecognitionException {
        try {
            int _type = PLSQL_RESERVED_COLAUTH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:438:5: ( 'colauth' )
            // PLSQLLexer.g:438:10: 'colauth'
            {
            match("colauth"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLSQL_RESERVED_COLAUTH"

    // $ANTLR start "PLSQL_RESERVED_COLUMNS"
    public final void mPLSQL_RESERVED_COLUMNS() throws RecognitionException {
        try {
            int _type = PLSQL_RESERVED_COLUMNS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:442:5: ( 'columns' )
            // PLSQLLexer.g:442:10: 'columns'
            {
            match("columns"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLSQL_RESERVED_COLUMNS"

    // $ANTLR start "PLSQL_RESERVED_COMPRESS"
    public final void mPLSQL_RESERVED_COMPRESS() throws RecognitionException {
        try {
            int _type = PLSQL_RESERVED_COMPRESS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:446:5: ( 'compress' )
            // PLSQLLexer.g:446:10: 'compress'
            {
            match("compress"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLSQL_RESERVED_COMPRESS"

    // $ANTLR start "SQL92_RESERVED_CONNECT"
    public final void mSQL92_RESERVED_CONNECT() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_CONNECT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:450:5: ( 'connect' )
            // PLSQLLexer.g:450:10: 'connect'
            {
            match("connect"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_CONNECT"

    // $ANTLR start "PLSQL_NON_RESERVED_CONNECT_BY_ROOT"
    public final void mPLSQL_NON_RESERVED_CONNECT_BY_ROOT() throws RecognitionException {
        try {
            int _type = PLSQL_NON_RESERVED_CONNECT_BY_ROOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:454:5: ( 'connect_by_root' )
            // PLSQLLexer.g:454:10: 'connect_by_root'
            {
            match("connect_by_root"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLSQL_NON_RESERVED_CONNECT_BY_ROOT"

    // $ANTLR start "PLSQL_RESERVED_CRASH"
    public final void mPLSQL_RESERVED_CRASH() throws RecognitionException {
        try {
            int _type = PLSQL_RESERVED_CRASH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:458:5: ( 'crash' )
            // PLSQLLexer.g:458:10: 'crash'
            {
            match("crash"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLSQL_RESERVED_CRASH"

    // $ANTLR start "SQL92_RESERVED_CREATE"
    public final void mSQL92_RESERVED_CREATE() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_CREATE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:462:5: ( 'create' )
            // PLSQLLexer.g:462:10: 'create'
            {
            match("create"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_CREATE"

    // $ANTLR start "SQL92_RESERVED_CURRENT"
    public final void mSQL92_RESERVED_CURRENT() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_CURRENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:466:5: ( 'current' )
            // PLSQLLexer.g:466:10: 'current'
            {
            match("current"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_CURRENT"

    // $ANTLR start "SQL92_RESERVED_CURSOR"
    public final void mSQL92_RESERVED_CURSOR() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_CURSOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:470:5: ( 'cursor' )
            // PLSQLLexer.g:470:10: 'cursor'
            {
            match("cursor"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_CURSOR"

    // $ANTLR start "SQL92_RESERVED_DATE"
    public final void mSQL92_RESERVED_DATE() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_DATE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:474:5: ( 'date' )
            // PLSQLLexer.g:474:10: 'date'
            {
            match("date"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_DATE"

    // $ANTLR start "SQL92_RESERVED_DECLARE"
    public final void mSQL92_RESERVED_DECLARE() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_DECLARE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:478:5: ( 'declare' )
            // PLSQLLexer.g:478:10: 'declare'
            {
            match("declare"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_DECLARE"

    // $ANTLR start "SQL92_RESERVED_DEFAULT"
    public final void mSQL92_RESERVED_DEFAULT() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_DEFAULT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:482:5: ( 'default' )
            // PLSQLLexer.g:482:10: 'default'
            {
            match("default"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_DEFAULT"

    // $ANTLR start "SQL92_RESERVED_DELETE"
    public final void mSQL92_RESERVED_DELETE() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_DELETE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:486:5: ( 'delete' )
            // PLSQLLexer.g:486:10: 'delete'
            {
            match("delete"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_DELETE"

    // $ANTLR start "SQL92_RESERVED_DESC"
    public final void mSQL92_RESERVED_DESC() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_DESC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:490:5: ( 'desc' )
            // PLSQLLexer.g:490:10: 'desc'
            {
            match("desc"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_DESC"

    // $ANTLR start "SQL92_RESERVED_DISTINCT"
    public final void mSQL92_RESERVED_DISTINCT() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_DISTINCT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:494:5: ( 'distinct' )
            // PLSQLLexer.g:494:10: 'distinct'
            {
            match("distinct"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_DISTINCT"

    // $ANTLR start "SQL92_RESERVED_DROP"
    public final void mSQL92_RESERVED_DROP() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_DROP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:498:5: ( 'drop' )
            // PLSQLLexer.g:498:10: 'drop'
            {
            match("drop"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_DROP"

    // $ANTLR start "SQL92_RESERVED_ELSE"
    public final void mSQL92_RESERVED_ELSE() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_ELSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:502:5: ( 'else' )
            // PLSQLLexer.g:502:10: 'else'
            {
            match("else"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_ELSE"

    // $ANTLR start "SQL92_RESERVED_END"
    public final void mSQL92_RESERVED_END() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_END;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:506:5: ( 'end' )
            // PLSQLLexer.g:506:10: 'end'
            {
            match("end"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_END"

    // $ANTLR start "SQL92_RESERVED_EXCEPTION"
    public final void mSQL92_RESERVED_EXCEPTION() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_EXCEPTION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:510:5: ( 'exception' )
            // PLSQLLexer.g:510:10: 'exception'
            {
            match("exception"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_EXCEPTION"

    // $ANTLR start "PLSQL_RESERVED_EXCLUSIVE"
    public final void mPLSQL_RESERVED_EXCLUSIVE() throws RecognitionException {
        try {
            int _type = PLSQL_RESERVED_EXCLUSIVE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:514:5: ( 'exclusive' )
            // PLSQLLexer.g:514:10: 'exclusive'
            {
            match("exclusive"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLSQL_RESERVED_EXCLUSIVE"

    // $ANTLR start "SQL92_RESERVED_EXISTS"
    public final void mSQL92_RESERVED_EXISTS() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_EXISTS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:518:5: ( 'exists' )
            // PLSQLLexer.g:518:10: 'exists'
            {
            match("exists"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_EXISTS"

    // $ANTLR start "SQL92_RESERVED_FALSE"
    public final void mSQL92_RESERVED_FALSE() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_FALSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:522:5: ( 'false' )
            // PLSQLLexer.g:522:10: 'false'
            {
            match("false"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_FALSE"

    // $ANTLR start "SQL92_RESERVED_FETCH"
    public final void mSQL92_RESERVED_FETCH() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_FETCH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:526:5: ( 'fetch' )
            // PLSQLLexer.g:526:10: 'fetch'
            {
            match("fetch"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_FETCH"

    // $ANTLR start "SQL92_RESERVED_FOR"
    public final void mSQL92_RESERVED_FOR() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_FOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:530:5: ( 'for' )
            // PLSQLLexer.g:530:10: 'for'
            {
            match("for"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_FOR"

    // $ANTLR start "SQL92_RESERVED_FROM"
    public final void mSQL92_RESERVED_FROM() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_FROM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:534:5: ( 'from' )
            // PLSQLLexer.g:534:10: 'from'
            {
            match("from"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_FROM"

    // $ANTLR start "SQL92_RESERVED_GOTO"
    public final void mSQL92_RESERVED_GOTO() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_GOTO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:538:5: ( 'goto' )
            // PLSQLLexer.g:538:10: 'goto'
            {
            match("goto"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_GOTO"

    // $ANTLR start "SQL92_RESERVED_GRANT"
    public final void mSQL92_RESERVED_GRANT() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_GRANT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:542:5: ( 'grant' )
            // PLSQLLexer.g:542:10: 'grant'
            {
            match("grant"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_GRANT"

    // $ANTLR start "SQL92_RESERVED_GROUP"
    public final void mSQL92_RESERVED_GROUP() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_GROUP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:546:5: ( 'group' )
            // PLSQLLexer.g:546:10: 'group'
            {
            match("group"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_GROUP"

    // $ANTLR start "SQL92_RESERVED_HAVING"
    public final void mSQL92_RESERVED_HAVING() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_HAVING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:550:5: ( 'having' )
            // PLSQLLexer.g:550:10: 'having'
            {
            match("having"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_HAVING"

    // $ANTLR start "PLSQL_RESERVED_IDENTIFIED"
    public final void mPLSQL_RESERVED_IDENTIFIED() throws RecognitionException {
        try {
            int _type = PLSQL_RESERVED_IDENTIFIED;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:554:5: ( 'identified' )
            // PLSQLLexer.g:554:10: 'identified'
            {
            match("identified"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLSQL_RESERVED_IDENTIFIED"

    // $ANTLR start "PLSQL_RESERVED_IF"
    public final void mPLSQL_RESERVED_IF() throws RecognitionException {
        try {
            int _type = PLSQL_RESERVED_IF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:558:5: ( 'if' )
            // PLSQLLexer.g:558:10: 'if'
            {
            match("if"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLSQL_RESERVED_IF"

    // $ANTLR start "SQL92_RESERVED_IN"
    public final void mSQL92_RESERVED_IN() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_IN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:562:5: ( 'in' )
            // PLSQLLexer.g:562:10: 'in'
            {
            match("in"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_IN"

    // $ANTLR start "PLSQL_RESERVED_INDEX"
    public final void mPLSQL_RESERVED_INDEX() throws RecognitionException {
        try {
            int _type = PLSQL_RESERVED_INDEX;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:566:5: ( 'index' )
            // PLSQLLexer.g:566:10: 'index'
            {
            match("index"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLSQL_RESERVED_INDEX"

    // $ANTLR start "PLSQL_RESERVED_INDEXES"
    public final void mPLSQL_RESERVED_INDEXES() throws RecognitionException {
        try {
            int _type = PLSQL_RESERVED_INDEXES;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:570:5: ( 'indexes' )
            // PLSQLLexer.g:570:10: 'indexes'
            {
            match("indexes"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLSQL_RESERVED_INDEXES"

    // $ANTLR start "SQL92_RESERVED_INSERT"
    public final void mSQL92_RESERVED_INSERT() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_INSERT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:574:5: ( 'insert' )
            // PLSQLLexer.g:574:10: 'insert'
            {
            match("insert"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_INSERT"

    // $ANTLR start "SQL92_RESERVED_INTERSECT"
    public final void mSQL92_RESERVED_INTERSECT() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_INTERSECT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:578:5: ( 'intersect' )
            // PLSQLLexer.g:578:10: 'intersect'
            {
            match("intersect"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_INTERSECT"

    // $ANTLR start "SQL92_RESERVED_INTO"
    public final void mSQL92_RESERVED_INTO() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_INTO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:582:5: ( 'into' )
            // PLSQLLexer.g:582:10: 'into'
            {
            match("into"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_INTO"

    // $ANTLR start "SQL92_RESERVED_IS"
    public final void mSQL92_RESERVED_IS() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_IS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:586:5: ( 'is' )
            // PLSQLLexer.g:586:10: 'is'
            {
            match("is"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_IS"

    // $ANTLR start "SQL92_RESERVED_LIKE"
    public final void mSQL92_RESERVED_LIKE() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_LIKE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:590:5: ( 'like' )
            // PLSQLLexer.g:590:10: 'like'
            {
            match("like"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_LIKE"

    // $ANTLR start "PLSQL_RESERVED_LOCK"
    public final void mPLSQL_RESERVED_LOCK() throws RecognitionException {
        try {
            int _type = PLSQL_RESERVED_LOCK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:594:5: ( 'lock' )
            // PLSQLLexer.g:594:10: 'lock'
            {
            match("lock"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLSQL_RESERVED_LOCK"

    // $ANTLR start "PLSQL_RESERVED_MINUS"
    public final void mPLSQL_RESERVED_MINUS() throws RecognitionException {
        try {
            int _type = PLSQL_RESERVED_MINUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:598:5: ( 'minus' )
            // PLSQLLexer.g:598:10: 'minus'
            {
            match("minus"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLSQL_RESERVED_MINUS"

    // $ANTLR start "PLSQL_RESERVED_MODE"
    public final void mPLSQL_RESERVED_MODE() throws RecognitionException {
        try {
            int _type = PLSQL_RESERVED_MODE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:602:5: ( 'mode' )
            // PLSQLLexer.g:602:10: 'mode'
            {
            match("mode"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLSQL_RESERVED_MODE"

    // $ANTLR start "PLSQL_RESERVED_NOCOMPRESS"
    public final void mPLSQL_RESERVED_NOCOMPRESS() throws RecognitionException {
        try {
            int _type = PLSQL_RESERVED_NOCOMPRESS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:606:5: ( 'nocompress' )
            // PLSQLLexer.g:606:10: 'nocompress'
            {
            match("nocompress"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLSQL_RESERVED_NOCOMPRESS"

    // $ANTLR start "SQL92_RESERVED_NOT"
    public final void mSQL92_RESERVED_NOT() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_NOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:610:5: ( 'not' )
            // PLSQLLexer.g:610:10: 'not'
            {
            match("not"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_NOT"

    // $ANTLR start "PLSQL_RESERVED_NOWAIT"
    public final void mPLSQL_RESERVED_NOWAIT() throws RecognitionException {
        try {
            int _type = PLSQL_RESERVED_NOWAIT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:614:5: ( 'nowait' )
            // PLSQLLexer.g:614:10: 'nowait'
            {
            match("nowait"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLSQL_RESERVED_NOWAIT"

    // $ANTLR start "SQL92_RESERVED_NULL"
    public final void mSQL92_RESERVED_NULL() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_NULL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:618:5: ( 'null' )
            // PLSQLLexer.g:618:10: 'null'
            {
            match("null"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_NULL"

    // $ANTLR start "SQL92_RESERVED_OF"
    public final void mSQL92_RESERVED_OF() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_OF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:622:5: ( 'of' )
            // PLSQLLexer.g:622:10: 'of'
            {
            match("of"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_OF"

    // $ANTLR start "SQL92_RESERVED_ON"
    public final void mSQL92_RESERVED_ON() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_ON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:626:5: ( 'on' )
            // PLSQLLexer.g:626:10: 'on'
            {
            match("on"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_ON"

    // $ANTLR start "SQL92_RESERVED_OPTION"
    public final void mSQL92_RESERVED_OPTION() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_OPTION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:630:5: ( 'option' )
            // PLSQLLexer.g:630:10: 'option'
            {
            match("option"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_OPTION"

    // $ANTLR start "SQL92_RESERVED_OR"
    public final void mSQL92_RESERVED_OR() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_OR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:634:5: ( 'or' )
            // PLSQLLexer.g:634:10: 'or'
            {
            match("or"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_OR"

    // $ANTLR start "SQL92_RESERVED_ORDER"
    public final void mSQL92_RESERVED_ORDER() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_ORDER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:638:5: ( 'order' )
            // PLSQLLexer.g:638:10: 'order'
            {
            match("order"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_ORDER"

    // $ANTLR start "SQL92_RESERVED_OVERLAPS"
    public final void mSQL92_RESERVED_OVERLAPS() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_OVERLAPS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:642:5: ( 'overlaps' )
            // PLSQLLexer.g:642:10: 'overlaps'
            {
            match("overlaps"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_OVERLAPS"

    // $ANTLR start "SQL92_RESERVED_PRIOR"
    public final void mSQL92_RESERVED_PRIOR() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_PRIOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:646:5: ( 'prior' )
            // PLSQLLexer.g:646:10: 'prior'
            {
            match("prior"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_PRIOR"

    // $ANTLR start "SQL92_RESERVED_PROCEDURE"
    public final void mSQL92_RESERVED_PROCEDURE() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_PROCEDURE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:650:5: ( 'procedure' )
            // PLSQLLexer.g:650:10: 'procedure'
            {
            match("procedure"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_PROCEDURE"

    // $ANTLR start "SQL92_RESERVED_PUBLIC"
    public final void mSQL92_RESERVED_PUBLIC() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_PUBLIC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:654:5: ( 'public' )
            // PLSQLLexer.g:654:10: 'public'
            {
            match("public"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_PUBLIC"

    // $ANTLR start "PLSQL_RESERVED_RESOURCE"
    public final void mPLSQL_RESERVED_RESOURCE() throws RecognitionException {
        try {
            int _type = PLSQL_RESERVED_RESOURCE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:658:5: ( 'resource' )
            // PLSQLLexer.g:658:10: 'resource'
            {
            match("resource"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLSQL_RESERVED_RESOURCE"

    // $ANTLR start "SQL92_RESERVED_REVOKE"
    public final void mSQL92_RESERVED_REVOKE() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_REVOKE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:662:5: ( 'revoke' )
            // PLSQLLexer.g:662:10: 'revoke'
            {
            match("revoke"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_REVOKE"

    // $ANTLR start "SQL92_RESERVED_SELECT"
    public final void mSQL92_RESERVED_SELECT() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_SELECT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:666:5: ( 'select' )
            // PLSQLLexer.g:666:10: 'select'
            {
            match("select"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_SELECT"

    // $ANTLR start "PLSQL_RESERVED_SHARE"
    public final void mPLSQL_RESERVED_SHARE() throws RecognitionException {
        try {
            int _type = PLSQL_RESERVED_SHARE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:670:5: ( 'share' )
            // PLSQLLexer.g:670:10: 'share'
            {
            match("share"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLSQL_RESERVED_SHARE"

    // $ANTLR start "SQL92_RESERVED_SIZE"
    public final void mSQL92_RESERVED_SIZE() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_SIZE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:674:5: ( 'size' )
            // PLSQLLexer.g:674:10: 'size'
            {
            match("size"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_SIZE"

    // $ANTLR start "SQL92_RESERVED_SQL"
    public final void mSQL92_RESERVED_SQL() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_SQL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:678:5: ( 'sql' )
            // PLSQLLexer.g:678:10: 'sql'
            {
            match("sql"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_SQL"

    // $ANTLR start "PLSQL_RESERVED_START"
    public final void mPLSQL_RESERVED_START() throws RecognitionException {
        try {
            int _type = PLSQL_RESERVED_START;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:682:5: ( 'start' )
            // PLSQLLexer.g:682:10: 'start'
            {
            match("start"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLSQL_RESERVED_START"

    // $ANTLR start "PLSQL_RESERVED_TABAUTH"
    public final void mPLSQL_RESERVED_TABAUTH() throws RecognitionException {
        try {
            int _type = PLSQL_RESERVED_TABAUTH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:686:5: ( 'tabauth' )
            // PLSQLLexer.g:686:10: 'tabauth'
            {
            match("tabauth"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLSQL_RESERVED_TABAUTH"

    // $ANTLR start "SQL92_RESERVED_TABLE"
    public final void mSQL92_RESERVED_TABLE() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_TABLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:690:5: ( 'table' )
            // PLSQLLexer.g:690:10: 'table'
            {
            match("table"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_TABLE"

    // $ANTLR start "SQL92_RESERVED_THEN"
    public final void mSQL92_RESERVED_THEN() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_THEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:694:5: ( 'then' )
            // PLSQLLexer.g:694:10: 'then'
            {
            match("then"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_THEN"

    // $ANTLR start "SQL92_RESERVED_TO"
    public final void mSQL92_RESERVED_TO() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_TO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:698:5: ( 'to' )
            // PLSQLLexer.g:698:10: 'to'
            {
            match("to"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_TO"

    // $ANTLR start "SQL92_RESERVED_TRUE"
    public final void mSQL92_RESERVED_TRUE() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_TRUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:702:5: ( 'true' )
            // PLSQLLexer.g:702:10: 'true'
            {
            match("true"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_TRUE"

    // $ANTLR start "SQL92_RESERVED_UNION"
    public final void mSQL92_RESERVED_UNION() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_UNION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:706:5: ( 'union' )
            // PLSQLLexer.g:706:10: 'union'
            {
            match("union"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_UNION"

    // $ANTLR start "SQL92_RESERVED_UNIQUE"
    public final void mSQL92_RESERVED_UNIQUE() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_UNIQUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:710:5: ( 'unique' )
            // PLSQLLexer.g:710:10: 'unique'
            {
            match("unique"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_UNIQUE"

    // $ANTLR start "SQL92_RESERVED_UPDATE"
    public final void mSQL92_RESERVED_UPDATE() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_UPDATE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:714:5: ( 'update' )
            // PLSQLLexer.g:714:10: 'update'
            {
            match("update"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_UPDATE"

    // $ANTLR start "SQL92_RESERVED_VALUES"
    public final void mSQL92_RESERVED_VALUES() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_VALUES;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:718:5: ( 'values' )
            // PLSQLLexer.g:718:10: 'values'
            {
            match("values"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_VALUES"

    // $ANTLR start "SQL92_RESERVED_VIEW"
    public final void mSQL92_RESERVED_VIEW() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_VIEW;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:722:5: ( 'view' )
            // PLSQLLexer.g:722:10: 'view'
            {
            match("view"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_VIEW"

    // $ANTLR start "PLSQL_RESERVED_VIEWS"
    public final void mPLSQL_RESERVED_VIEWS() throws RecognitionException {
        try {
            int _type = PLSQL_RESERVED_VIEWS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:726:5: ( 'views' )
            // PLSQLLexer.g:726:10: 'views'
            {
            match("views"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLSQL_RESERVED_VIEWS"

    // $ANTLR start "SQL92_RESERVED_WHEN"
    public final void mSQL92_RESERVED_WHEN() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_WHEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:730:5: ( 'when' )
            // PLSQLLexer.g:730:10: 'when'
            {
            match("when"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_WHEN"

    // $ANTLR start "SQL92_RESERVED_WHERE"
    public final void mSQL92_RESERVED_WHERE() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_WHERE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:734:5: ( 'where' )
            // PLSQLLexer.g:734:10: 'where'
            {
            match("where"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_WHERE"

    // $ANTLR start "SQL92_RESERVED_WITH"
    public final void mSQL92_RESERVED_WITH() throws RecognitionException {
        try {
            int _type = SQL92_RESERVED_WITH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:738:5: ( 'with' )
            // PLSQLLexer.g:738:10: 'with'
            {
            match("with"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL92_RESERVED_WITH"

    // $ANTLR start "PLSQL_NON_RESERVED_USING"
    public final void mPLSQL_NON_RESERVED_USING() throws RecognitionException {
        try {
            int _type = PLSQL_NON_RESERVED_USING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:742:5: ( 'using' )
            // PLSQLLexer.g:742:10: 'using'
            {
            match("using"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLSQL_NON_RESERVED_USING"

    // $ANTLR start "PLSQL_NON_RESERVED_MODEL"
    public final void mPLSQL_NON_RESERVED_MODEL() throws RecognitionException {
        try {
            int _type = PLSQL_NON_RESERVED_MODEL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:746:5: ( 'model' )
            // PLSQLLexer.g:746:10: 'model'
            {
            match("model"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLSQL_NON_RESERVED_MODEL"

    // $ANTLR start "PLSQL_NON_RESERVED_ELSIF"
    public final void mPLSQL_NON_RESERVED_ELSIF() throws RecognitionException {
        try {
            int _type = PLSQL_NON_RESERVED_ELSIF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:750:5: ( 'elsif' )
            // PLSQLLexer.g:750:10: 'elsif'
            {
            match("elsif"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLSQL_NON_RESERVED_ELSIF"

    // $ANTLR start "PLSQL_NON_RESERVED_PIVOT"
    public final void mPLSQL_NON_RESERVED_PIVOT() throws RecognitionException {
        try {
            int _type = PLSQL_NON_RESERVED_PIVOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:754:5: ( 'pivot' )
            // PLSQLLexer.g:754:10: 'pivot'
            {
            match("pivot"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLSQL_NON_RESERVED_PIVOT"

    // $ANTLR start "PLSQL_NON_RESERVED_UNPIVOT"
    public final void mPLSQL_NON_RESERVED_UNPIVOT() throws RecognitionException {
        try {
            int _type = PLSQL_NON_RESERVED_UNPIVOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:758:5: ( 'unpivot' )
            // PLSQLLexer.g:758:10: 'unpivot'
            {
            match("unpivot"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLSQL_NON_RESERVED_UNPIVOT"

    // $ANTLR start "REGULAR_ID"
    public final void mREGULAR_ID() throws RecognitionException {
        try {
            int _type = REGULAR_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // PLSQLLexer.g:762:5: ( ( SIMPLE_LETTER ) ( SIMPLE_LETTER | '_' | '0' .. '9' )* )
            // PLSQLLexer.g:762:10: ( SIMPLE_LETTER ) ( SIMPLE_LETTER | '_' | '0' .. '9' )*
            {
            if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            // PLSQLLexer.g:762:26: ( SIMPLE_LETTER | '_' | '0' .. '9' )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( ((LA33_0 >= '0' && LA33_0 <= '9')||(LA33_0 >= 'A' && LA33_0 <= 'Z')||LA33_0=='_'||(LA33_0 >= 'a' && LA33_0 <= 'z')) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // PLSQLLexer.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop33;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "REGULAR_ID"

    public void mTokens() throws RecognitionException {
        // PLSQLLexer.g:1:8: ( FOR_NOTATION | NATIONAL_CHAR_STRING_LIT | BIT_STRING_LIT | HEX_STRING_LIT | PERIOD | EXACT_NUM_LIT | CHAR_STRING | CHAR_STRING_PERL | DELIMITED_ID | PERCENT | AMPERSAND | LEFT_PAREN | RIGHT_PAREN | DOUBLE_ASTERISK | ASTERISK | PLUS_SIGN | COMMA | SOLIDUS | AT_SIGN | ASSIGN_OP | COLON | SEMICOLON | LESS_THAN_OR_EQUALS_OP | LESS_THAN_OP | GREATER_THAN_OR_EQUALS_OP | NOT_EQUAL_OP | CARRET_OPERATOR_PART | TILDE_OPERATOR_PART | EXCLAMATION_OPERATOR_PART | GREATER_THAN_OP | QUESTION_MARK | CONCATENATION_OP | VERTICAL_BAR | EQUALS_OP | LEFT_BRACKET | RIGHT_BRACKET | INTRODUCER | SEPARATOR | SQL92_RESERVED_ALL | SQL92_RESERVED_ALTER | SQL92_RESERVED_AND | SQL92_RESERVED_ANY | SQL92_RESERVED_AS | SQL92_RESERVED_ASC | SQL92_RESERVED_BEGIN | SQL92_RESERVED_BETWEEN | SQL92_RESERVED_BY | SQL92_RESERVED_CASE | SQL92_RESERVED_CHECK | PLSQL_RESERVED_CLUSTERS | PLSQL_RESERVED_COLAUTH | PLSQL_RESERVED_COLUMNS | PLSQL_RESERVED_COMPRESS | SQL92_RESERVED_CONNECT | PLSQL_NON_RESERVED_CONNECT_BY_ROOT | PLSQL_RESERVED_CRASH | SQL92_RESERVED_CREATE | SQL92_RESERVED_CURRENT | SQL92_RESERVED_CURSOR | SQL92_RESERVED_DATE | SQL92_RESERVED_DECLARE | SQL92_RESERVED_DEFAULT | SQL92_RESERVED_DELETE | SQL92_RESERVED_DESC | SQL92_RESERVED_DISTINCT | SQL92_RESERVED_DROP | SQL92_RESERVED_ELSE | SQL92_RESERVED_END | SQL92_RESERVED_EXCEPTION | PLSQL_RESERVED_EXCLUSIVE | SQL92_RESERVED_EXISTS | SQL92_RESERVED_FALSE | SQL92_RESERVED_FETCH | SQL92_RESERVED_FOR | SQL92_RESERVED_FROM | SQL92_RESERVED_GOTO | SQL92_RESERVED_GRANT | SQL92_RESERVED_GROUP | SQL92_RESERVED_HAVING | PLSQL_RESERVED_IDENTIFIED | PLSQL_RESERVED_IF | SQL92_RESERVED_IN | PLSQL_RESERVED_INDEX | PLSQL_RESERVED_INDEXES | SQL92_RESERVED_INSERT | SQL92_RESERVED_INTERSECT | SQL92_RESERVED_INTO | SQL92_RESERVED_IS | SQL92_RESERVED_LIKE | PLSQL_RESERVED_LOCK | PLSQL_RESERVED_MINUS | PLSQL_RESERVED_MODE | PLSQL_RESERVED_NOCOMPRESS | SQL92_RESERVED_NOT | PLSQL_RESERVED_NOWAIT | SQL92_RESERVED_NULL | SQL92_RESERVED_OF | SQL92_RESERVED_ON | SQL92_RESERVED_OPTION | SQL92_RESERVED_OR | SQL92_RESERVED_ORDER | SQL92_RESERVED_OVERLAPS | SQL92_RESERVED_PRIOR | SQL92_RESERVED_PROCEDURE | SQL92_RESERVED_PUBLIC | PLSQL_RESERVED_RESOURCE | SQL92_RESERVED_REVOKE | SQL92_RESERVED_SELECT | PLSQL_RESERVED_SHARE | SQL92_RESERVED_SIZE | SQL92_RESERVED_SQL | PLSQL_RESERVED_START | PLSQL_RESERVED_TABAUTH | SQL92_RESERVED_TABLE | SQL92_RESERVED_THEN | SQL92_RESERVED_TO | SQL92_RESERVED_TRUE | SQL92_RESERVED_UNION | SQL92_RESERVED_UNIQUE | SQL92_RESERVED_UPDATE | SQL92_RESERVED_VALUES | SQL92_RESERVED_VIEW | PLSQL_RESERVED_VIEWS | SQL92_RESERVED_WHEN | SQL92_RESERVED_WHERE | SQL92_RESERVED_WITH | PLSQL_NON_RESERVED_USING | PLSQL_NON_RESERVED_MODEL | PLSQL_NON_RESERVED_ELSIF | PLSQL_NON_RESERVED_PIVOT | PLSQL_NON_RESERVED_UNPIVOT | REGULAR_ID )
        int alt34=132;
        alt34 = dfa34.predict(input);
        switch (alt34) {
            case 1 :
                // PLSQLLexer.g:1:10: FOR_NOTATION
                {
                mFOR_NOTATION(); 


                }
                break;
            case 2 :
                // PLSQLLexer.g:1:23: NATIONAL_CHAR_STRING_LIT
                {
                mNATIONAL_CHAR_STRING_LIT(); 


                }
                break;
            case 3 :
                // PLSQLLexer.g:1:48: BIT_STRING_LIT
                {
                mBIT_STRING_LIT(); 


                }
                break;
            case 4 :
                // PLSQLLexer.g:1:63: HEX_STRING_LIT
                {
                mHEX_STRING_LIT(); 


                }
                break;
            case 5 :
                // PLSQLLexer.g:1:78: PERIOD
                {
                mPERIOD(); 


                }
                break;
            case 6 :
                // PLSQLLexer.g:1:85: EXACT_NUM_LIT
                {
                mEXACT_NUM_LIT(); 


                }
                break;
            case 7 :
                // PLSQLLexer.g:1:99: CHAR_STRING
                {
                mCHAR_STRING(); 


                }
                break;
            case 8 :
                // PLSQLLexer.g:1:111: CHAR_STRING_PERL
                {
                mCHAR_STRING_PERL(); 


                }
                break;
            case 9 :
                // PLSQLLexer.g:1:128: DELIMITED_ID
                {
                mDELIMITED_ID(); 


                }
                break;
            case 10 :
                // PLSQLLexer.g:1:141: PERCENT
                {
                mPERCENT(); 


                }
                break;
            case 11 :
                // PLSQLLexer.g:1:149: AMPERSAND
                {
                mAMPERSAND(); 


                }
                break;
            case 12 :
                // PLSQLLexer.g:1:159: LEFT_PAREN
                {
                mLEFT_PAREN(); 


                }
                break;
            case 13 :
                // PLSQLLexer.g:1:170: RIGHT_PAREN
                {
                mRIGHT_PAREN(); 


                }
                break;
            case 14 :
                // PLSQLLexer.g:1:182: DOUBLE_ASTERISK
                {
                mDOUBLE_ASTERISK(); 


                }
                break;
            case 15 :
                // PLSQLLexer.g:1:198: ASTERISK
                {
                mASTERISK(); 


                }
                break;
            case 16 :
                // PLSQLLexer.g:1:207: PLUS_SIGN
                {
                mPLUS_SIGN(); 


                }
                break;
            case 17 :
                // PLSQLLexer.g:1:217: COMMA
                {
                mCOMMA(); 


                }
                break;
            case 18 :
                // PLSQLLexer.g:1:223: SOLIDUS
                {
                mSOLIDUS(); 


                }
                break;
            case 19 :
                // PLSQLLexer.g:1:231: AT_SIGN
                {
                mAT_SIGN(); 


                }
                break;
            case 20 :
                // PLSQLLexer.g:1:239: ASSIGN_OP
                {
                mASSIGN_OP(); 


                }
                break;
            case 21 :
                // PLSQLLexer.g:1:249: COLON
                {
                mCOLON(); 


                }
                break;
            case 22 :
                // PLSQLLexer.g:1:255: SEMICOLON
                {
                mSEMICOLON(); 


                }
                break;
            case 23 :
                // PLSQLLexer.g:1:265: LESS_THAN_OR_EQUALS_OP
                {
                mLESS_THAN_OR_EQUALS_OP(); 


                }
                break;
            case 24 :
                // PLSQLLexer.g:1:288: LESS_THAN_OP
                {
                mLESS_THAN_OP(); 


                }
                break;
            case 25 :
                // PLSQLLexer.g:1:301: GREATER_THAN_OR_EQUALS_OP
                {
                mGREATER_THAN_OR_EQUALS_OP(); 


                }
                break;
            case 26 :
                // PLSQLLexer.g:1:327: NOT_EQUAL_OP
                {
                mNOT_EQUAL_OP(); 


                }
                break;
            case 27 :
                // PLSQLLexer.g:1:340: CARRET_OPERATOR_PART
                {
                mCARRET_OPERATOR_PART(); 


                }
                break;
            case 28 :
                // PLSQLLexer.g:1:361: TILDE_OPERATOR_PART
                {
                mTILDE_OPERATOR_PART(); 


                }
                break;
            case 29 :
                // PLSQLLexer.g:1:381: EXCLAMATION_OPERATOR_PART
                {
                mEXCLAMATION_OPERATOR_PART(); 


                }
                break;
            case 30 :
                // PLSQLLexer.g:1:407: GREATER_THAN_OP
                {
                mGREATER_THAN_OP(); 


                }
                break;
            case 31 :
                // PLSQLLexer.g:1:423: QUESTION_MARK
                {
                mQUESTION_MARK(); 


                }
                break;
            case 32 :
                // PLSQLLexer.g:1:437: CONCATENATION_OP
                {
                mCONCATENATION_OP(); 


                }
                break;
            case 33 :
                // PLSQLLexer.g:1:454: VERTICAL_BAR
                {
                mVERTICAL_BAR(); 


                }
                break;
            case 34 :
                // PLSQLLexer.g:1:467: EQUALS_OP
                {
                mEQUALS_OP(); 


                }
                break;
            case 35 :
                // PLSQLLexer.g:1:477: LEFT_BRACKET
                {
                mLEFT_BRACKET(); 


                }
                break;
            case 36 :
                // PLSQLLexer.g:1:490: RIGHT_BRACKET
                {
                mRIGHT_BRACKET(); 


                }
                break;
            case 37 :
                // PLSQLLexer.g:1:504: INTRODUCER
                {
                mINTRODUCER(); 


                }
                break;
            case 38 :
                // PLSQLLexer.g:1:515: SEPARATOR
                {
                mSEPARATOR(); 


                }
                break;
            case 39 :
                // PLSQLLexer.g:1:525: SQL92_RESERVED_ALL
                {
                mSQL92_RESERVED_ALL(); 


                }
                break;
            case 40 :
                // PLSQLLexer.g:1:544: SQL92_RESERVED_ALTER
                {
                mSQL92_RESERVED_ALTER(); 


                }
                break;
            case 41 :
                // PLSQLLexer.g:1:565: SQL92_RESERVED_AND
                {
                mSQL92_RESERVED_AND(); 


                }
                break;
            case 42 :
                // PLSQLLexer.g:1:584: SQL92_RESERVED_ANY
                {
                mSQL92_RESERVED_ANY(); 


                }
                break;
            case 43 :
                // PLSQLLexer.g:1:603: SQL92_RESERVED_AS
                {
                mSQL92_RESERVED_AS(); 


                }
                break;
            case 44 :
                // PLSQLLexer.g:1:621: SQL92_RESERVED_ASC
                {
                mSQL92_RESERVED_ASC(); 


                }
                break;
            case 45 :
                // PLSQLLexer.g:1:640: SQL92_RESERVED_BEGIN
                {
                mSQL92_RESERVED_BEGIN(); 


                }
                break;
            case 46 :
                // PLSQLLexer.g:1:661: SQL92_RESERVED_BETWEEN
                {
                mSQL92_RESERVED_BETWEEN(); 


                }
                break;
            case 47 :
                // PLSQLLexer.g:1:684: SQL92_RESERVED_BY
                {
                mSQL92_RESERVED_BY(); 


                }
                break;
            case 48 :
                // PLSQLLexer.g:1:702: SQL92_RESERVED_CASE
                {
                mSQL92_RESERVED_CASE(); 


                }
                break;
            case 49 :
                // PLSQLLexer.g:1:722: SQL92_RESERVED_CHECK
                {
                mSQL92_RESERVED_CHECK(); 


                }
                break;
            case 50 :
                // PLSQLLexer.g:1:743: PLSQL_RESERVED_CLUSTERS
                {
                mPLSQL_RESERVED_CLUSTERS(); 


                }
                break;
            case 51 :
                // PLSQLLexer.g:1:767: PLSQL_RESERVED_COLAUTH
                {
                mPLSQL_RESERVED_COLAUTH(); 


                }
                break;
            case 52 :
                // PLSQLLexer.g:1:790: PLSQL_RESERVED_COLUMNS
                {
                mPLSQL_RESERVED_COLUMNS(); 


                }
                break;
            case 53 :
                // PLSQLLexer.g:1:813: PLSQL_RESERVED_COMPRESS
                {
                mPLSQL_RESERVED_COMPRESS(); 


                }
                break;
            case 54 :
                // PLSQLLexer.g:1:837: SQL92_RESERVED_CONNECT
                {
                mSQL92_RESERVED_CONNECT(); 


                }
                break;
            case 55 :
                // PLSQLLexer.g:1:860: PLSQL_NON_RESERVED_CONNECT_BY_ROOT
                {
                mPLSQL_NON_RESERVED_CONNECT_BY_ROOT(); 


                }
                break;
            case 56 :
                // PLSQLLexer.g:1:895: PLSQL_RESERVED_CRASH
                {
                mPLSQL_RESERVED_CRASH(); 


                }
                break;
            case 57 :
                // PLSQLLexer.g:1:916: SQL92_RESERVED_CREATE
                {
                mSQL92_RESERVED_CREATE(); 


                }
                break;
            case 58 :
                // PLSQLLexer.g:1:938: SQL92_RESERVED_CURRENT
                {
                mSQL92_RESERVED_CURRENT(); 


                }
                break;
            case 59 :
                // PLSQLLexer.g:1:961: SQL92_RESERVED_CURSOR
                {
                mSQL92_RESERVED_CURSOR(); 


                }
                break;
            case 60 :
                // PLSQLLexer.g:1:983: SQL92_RESERVED_DATE
                {
                mSQL92_RESERVED_DATE(); 


                }
                break;
            case 61 :
                // PLSQLLexer.g:1:1003: SQL92_RESERVED_DECLARE
                {
                mSQL92_RESERVED_DECLARE(); 


                }
                break;
            case 62 :
                // PLSQLLexer.g:1:1026: SQL92_RESERVED_DEFAULT
                {
                mSQL92_RESERVED_DEFAULT(); 


                }
                break;
            case 63 :
                // PLSQLLexer.g:1:1049: SQL92_RESERVED_DELETE
                {
                mSQL92_RESERVED_DELETE(); 


                }
                break;
            case 64 :
                // PLSQLLexer.g:1:1071: SQL92_RESERVED_DESC
                {
                mSQL92_RESERVED_DESC(); 


                }
                break;
            case 65 :
                // PLSQLLexer.g:1:1091: SQL92_RESERVED_DISTINCT
                {
                mSQL92_RESERVED_DISTINCT(); 


                }
                break;
            case 66 :
                // PLSQLLexer.g:1:1115: SQL92_RESERVED_DROP
                {
                mSQL92_RESERVED_DROP(); 


                }
                break;
            case 67 :
                // PLSQLLexer.g:1:1135: SQL92_RESERVED_ELSE
                {
                mSQL92_RESERVED_ELSE(); 


                }
                break;
            case 68 :
                // PLSQLLexer.g:1:1155: SQL92_RESERVED_END
                {
                mSQL92_RESERVED_END(); 


                }
                break;
            case 69 :
                // PLSQLLexer.g:1:1174: SQL92_RESERVED_EXCEPTION
                {
                mSQL92_RESERVED_EXCEPTION(); 


                }
                break;
            case 70 :
                // PLSQLLexer.g:1:1199: PLSQL_RESERVED_EXCLUSIVE
                {
                mPLSQL_RESERVED_EXCLUSIVE(); 


                }
                break;
            case 71 :
                // PLSQLLexer.g:1:1224: SQL92_RESERVED_EXISTS
                {
                mSQL92_RESERVED_EXISTS(); 


                }
                break;
            case 72 :
                // PLSQLLexer.g:1:1246: SQL92_RESERVED_FALSE
                {
                mSQL92_RESERVED_FALSE(); 


                }
                break;
            case 73 :
                // PLSQLLexer.g:1:1267: SQL92_RESERVED_FETCH
                {
                mSQL92_RESERVED_FETCH(); 


                }
                break;
            case 74 :
                // PLSQLLexer.g:1:1288: SQL92_RESERVED_FOR
                {
                mSQL92_RESERVED_FOR(); 


                }
                break;
            case 75 :
                // PLSQLLexer.g:1:1307: SQL92_RESERVED_FROM
                {
                mSQL92_RESERVED_FROM(); 


                }
                break;
            case 76 :
                // PLSQLLexer.g:1:1327: SQL92_RESERVED_GOTO
                {
                mSQL92_RESERVED_GOTO(); 


                }
                break;
            case 77 :
                // PLSQLLexer.g:1:1347: SQL92_RESERVED_GRANT
                {
                mSQL92_RESERVED_GRANT(); 


                }
                break;
            case 78 :
                // PLSQLLexer.g:1:1368: SQL92_RESERVED_GROUP
                {
                mSQL92_RESERVED_GROUP(); 


                }
                break;
            case 79 :
                // PLSQLLexer.g:1:1389: SQL92_RESERVED_HAVING
                {
                mSQL92_RESERVED_HAVING(); 


                }
                break;
            case 80 :
                // PLSQLLexer.g:1:1411: PLSQL_RESERVED_IDENTIFIED
                {
                mPLSQL_RESERVED_IDENTIFIED(); 


                }
                break;
            case 81 :
                // PLSQLLexer.g:1:1437: PLSQL_RESERVED_IF
                {
                mPLSQL_RESERVED_IF(); 


                }
                break;
            case 82 :
                // PLSQLLexer.g:1:1455: SQL92_RESERVED_IN
                {
                mSQL92_RESERVED_IN(); 


                }
                break;
            case 83 :
                // PLSQLLexer.g:1:1473: PLSQL_RESERVED_INDEX
                {
                mPLSQL_RESERVED_INDEX(); 


                }
                break;
            case 84 :
                // PLSQLLexer.g:1:1494: PLSQL_RESERVED_INDEXES
                {
                mPLSQL_RESERVED_INDEXES(); 


                }
                break;
            case 85 :
                // PLSQLLexer.g:1:1517: SQL92_RESERVED_INSERT
                {
                mSQL92_RESERVED_INSERT(); 


                }
                break;
            case 86 :
                // PLSQLLexer.g:1:1539: SQL92_RESERVED_INTERSECT
                {
                mSQL92_RESERVED_INTERSECT(); 


                }
                break;
            case 87 :
                // PLSQLLexer.g:1:1564: SQL92_RESERVED_INTO
                {
                mSQL92_RESERVED_INTO(); 


                }
                break;
            case 88 :
                // PLSQLLexer.g:1:1584: SQL92_RESERVED_IS
                {
                mSQL92_RESERVED_IS(); 


                }
                break;
            case 89 :
                // PLSQLLexer.g:1:1602: SQL92_RESERVED_LIKE
                {
                mSQL92_RESERVED_LIKE(); 


                }
                break;
            case 90 :
                // PLSQLLexer.g:1:1622: PLSQL_RESERVED_LOCK
                {
                mPLSQL_RESERVED_LOCK(); 


                }
                break;
            case 91 :
                // PLSQLLexer.g:1:1642: PLSQL_RESERVED_MINUS
                {
                mPLSQL_RESERVED_MINUS(); 


                }
                break;
            case 92 :
                // PLSQLLexer.g:1:1663: PLSQL_RESERVED_MODE
                {
                mPLSQL_RESERVED_MODE(); 


                }
                break;
            case 93 :
                // PLSQLLexer.g:1:1683: PLSQL_RESERVED_NOCOMPRESS
                {
                mPLSQL_RESERVED_NOCOMPRESS(); 


                }
                break;
            case 94 :
                // PLSQLLexer.g:1:1709: SQL92_RESERVED_NOT
                {
                mSQL92_RESERVED_NOT(); 


                }
                break;
            case 95 :
                // PLSQLLexer.g:1:1728: PLSQL_RESERVED_NOWAIT
                {
                mPLSQL_RESERVED_NOWAIT(); 


                }
                break;
            case 96 :
                // PLSQLLexer.g:1:1750: SQL92_RESERVED_NULL
                {
                mSQL92_RESERVED_NULL(); 


                }
                break;
            case 97 :
                // PLSQLLexer.g:1:1770: SQL92_RESERVED_OF
                {
                mSQL92_RESERVED_OF(); 


                }
                break;
            case 98 :
                // PLSQLLexer.g:1:1788: SQL92_RESERVED_ON
                {
                mSQL92_RESERVED_ON(); 


                }
                break;
            case 99 :
                // PLSQLLexer.g:1:1806: SQL92_RESERVED_OPTION
                {
                mSQL92_RESERVED_OPTION(); 


                }
                break;
            case 100 :
                // PLSQLLexer.g:1:1828: SQL92_RESERVED_OR
                {
                mSQL92_RESERVED_OR(); 


                }
                break;
            case 101 :
                // PLSQLLexer.g:1:1846: SQL92_RESERVED_ORDER
                {
                mSQL92_RESERVED_ORDER(); 


                }
                break;
            case 102 :
                // PLSQLLexer.g:1:1867: SQL92_RESERVED_OVERLAPS
                {
                mSQL92_RESERVED_OVERLAPS(); 


                }
                break;
            case 103 :
                // PLSQLLexer.g:1:1891: SQL92_RESERVED_PRIOR
                {
                mSQL92_RESERVED_PRIOR(); 


                }
                break;
            case 104 :
                // PLSQLLexer.g:1:1912: SQL92_RESERVED_PROCEDURE
                {
                mSQL92_RESERVED_PROCEDURE(); 


                }
                break;
            case 105 :
                // PLSQLLexer.g:1:1937: SQL92_RESERVED_PUBLIC
                {
                mSQL92_RESERVED_PUBLIC(); 


                }
                break;
            case 106 :
                // PLSQLLexer.g:1:1959: PLSQL_RESERVED_RESOURCE
                {
                mPLSQL_RESERVED_RESOURCE(); 


                }
                break;
            case 107 :
                // PLSQLLexer.g:1:1983: SQL92_RESERVED_REVOKE
                {
                mSQL92_RESERVED_REVOKE(); 


                }
                break;
            case 108 :
                // PLSQLLexer.g:1:2005: SQL92_RESERVED_SELECT
                {
                mSQL92_RESERVED_SELECT(); 


                }
                break;
            case 109 :
                // PLSQLLexer.g:1:2027: PLSQL_RESERVED_SHARE
                {
                mPLSQL_RESERVED_SHARE(); 


                }
                break;
            case 110 :
                // PLSQLLexer.g:1:2048: SQL92_RESERVED_SIZE
                {
                mSQL92_RESERVED_SIZE(); 


                }
                break;
            case 111 :
                // PLSQLLexer.g:1:2068: SQL92_RESERVED_SQL
                {
                mSQL92_RESERVED_SQL(); 


                }
                break;
            case 112 :
                // PLSQLLexer.g:1:2087: PLSQL_RESERVED_START
                {
                mPLSQL_RESERVED_START(); 


                }
                break;
            case 113 :
                // PLSQLLexer.g:1:2108: PLSQL_RESERVED_TABAUTH
                {
                mPLSQL_RESERVED_TABAUTH(); 


                }
                break;
            case 114 :
                // PLSQLLexer.g:1:2131: SQL92_RESERVED_TABLE
                {
                mSQL92_RESERVED_TABLE(); 


                }
                break;
            case 115 :
                // PLSQLLexer.g:1:2152: SQL92_RESERVED_THEN
                {
                mSQL92_RESERVED_THEN(); 


                }
                break;
            case 116 :
                // PLSQLLexer.g:1:2172: SQL92_RESERVED_TO
                {
                mSQL92_RESERVED_TO(); 


                }
                break;
            case 117 :
                // PLSQLLexer.g:1:2190: SQL92_RESERVED_TRUE
                {
                mSQL92_RESERVED_TRUE(); 


                }
                break;
            case 118 :
                // PLSQLLexer.g:1:2210: SQL92_RESERVED_UNION
                {
                mSQL92_RESERVED_UNION(); 


                }
                break;
            case 119 :
                // PLSQLLexer.g:1:2231: SQL92_RESERVED_UNIQUE
                {
                mSQL92_RESERVED_UNIQUE(); 


                }
                break;
            case 120 :
                // PLSQLLexer.g:1:2253: SQL92_RESERVED_UPDATE
                {
                mSQL92_RESERVED_UPDATE(); 


                }
                break;
            case 121 :
                // PLSQLLexer.g:1:2275: SQL92_RESERVED_VALUES
                {
                mSQL92_RESERVED_VALUES(); 


                }
                break;
            case 122 :
                // PLSQLLexer.g:1:2297: SQL92_RESERVED_VIEW
                {
                mSQL92_RESERVED_VIEW(); 


                }
                break;
            case 123 :
                // PLSQLLexer.g:1:2317: PLSQL_RESERVED_VIEWS
                {
                mPLSQL_RESERVED_VIEWS(); 


                }
                break;
            case 124 :
                // PLSQLLexer.g:1:2338: SQL92_RESERVED_WHEN
                {
                mSQL92_RESERVED_WHEN(); 


                }
                break;
            case 125 :
                // PLSQLLexer.g:1:2358: SQL92_RESERVED_WHERE
                {
                mSQL92_RESERVED_WHERE(); 


                }
                break;
            case 126 :
                // PLSQLLexer.g:1:2379: SQL92_RESERVED_WITH
                {
                mSQL92_RESERVED_WITH(); 


                }
                break;
            case 127 :
                // PLSQLLexer.g:1:2399: PLSQL_NON_RESERVED_USING
                {
                mPLSQL_NON_RESERVED_USING(); 


                }
                break;
            case 128 :
                // PLSQLLexer.g:1:2424: PLSQL_NON_RESERVED_MODEL
                {
                mPLSQL_NON_RESERVED_MODEL(); 


                }
                break;
            case 129 :
                // PLSQLLexer.g:1:2449: PLSQL_NON_RESERVED_ELSIF
                {
                mPLSQL_NON_RESERVED_ELSIF(); 


                }
                break;
            case 130 :
                // PLSQLLexer.g:1:2474: PLSQL_NON_RESERVED_PIVOT
                {
                mPLSQL_NON_RESERVED_PIVOT(); 


                }
                break;
            case 131 :
                // PLSQLLexer.g:1:2499: PLSQL_NON_RESERVED_UNPIVOT
                {
                mPLSQL_NON_RESERVED_UNPIVOT(); 


                }
                break;
            case 132 :
                // PLSQLLexer.g:1:2526: REGULAR_ID
                {
                mREGULAR_ID(); 


                }
                break;

        }

    }


    protected DFA34 dfa34 = new DFA34(this);
    static final String DFA34_eotS =
        "\1\uffff\1\65\3\64\1\76\1\uffff\1\64\5\uffff\1\101\2\uffff\1\102"+
        "\1\uffff\1\104\1\uffff\1\107\1\111\1\112\1\113\1\114\1\uffff\1\116"+
        "\5\uffff\24\64\4\uffff\3\64\1\u008e\23\uffff\2\64\1\u0094\25\64"+
        "\1\u00b2\1\u00b6\1\u00b7\4\64\1\u00bc\1\u00bd\1\64\1\u00c0\14\64"+
        "\1\u00cf\10\64\1\uffff\1\64\1\u00da\4\64\1\uffff\1\u00df\1\64\1"+
        "\u00e1\1\u00e2\1\u00e3\1\uffff\21\64\1\u00f8\4\64\1\u00fe\6\64\1"+
        "\uffff\3\64\2\uffff\4\64\2\uffff\2\64\1\uffff\12\64\1\u0119\3\64"+
        "\1\uffff\12\64\1\uffff\1\64\1\u012b\2\64\1\uffff\1\64\3\uffff\1"+
        "\u012f\12\64\1\u013a\3\64\1\u013e\1\64\1\u0140\1\u0141\1\64\1\uffff"+
        "\5\64\1\uffff\1\u0148\1\u0149\7\64\1\u0151\1\u0152\1\u0153\1\64"+
        "\1\u0156\13\64\1\u0162\1\uffff\3\64\1\u0166\1\u0167\6\64\1\u016f"+
        "\1\u0170\1\64\1\u0172\2\64\1\uffff\1\u0175\1\64\1\u0177\1\uffff"+
        "\1\u0178\5\64\1\u017e\3\64\1\uffff\3\64\1\uffff\1\64\2\uffff\1\u0186"+
        "\3\64\1\u018a\1\u018b\2\uffff\1\u018c\1\u018d\2\64\1\u0191\2\64"+
        "\3\uffff\1\u0194\1\u0195\1\uffff\1\64\1\u0197\1\64\1\u0199\2\64"+
        "\1\u019c\3\64\1\u01a0\1\uffff\1\u01a1\1\64\1\u01a3\2\uffff\1\u01a4"+
        "\3\64\1\u01a8\1\64\1\u01aa\2\uffff\1\u01ab\1\uffff\1\64\1\u01ad"+
        "\1\uffff\1\64\2\uffff\5\64\1\uffff\1\u01b4\1\64\1\u01b6\2\64\1\u01b9"+
        "\1\64\1\uffff\2\64\1\u01bd\4\uffff\1\u01be\2\64\1\uffff\1\u01c1"+
        "\1\64\2\uffff\1\u01c3\1\uffff\1\64\1\uffff\1\64\1\u01c6\1\uffff"+
        "\1\64\1\u01c8\1\u01c9\2\uffff\1\64\2\uffff\1\u01cb\1\64\1\u01cd"+
        "\1\uffff\1\u01ce\2\uffff\1\64\1\uffff\1\u01d0\1\64\1\u01d2\1\u01d3"+
        "\1\64\1\u01d6\1\uffff\1\u01d7\1\uffff\1\u01d8\1\u01d9\1\uffff\3"+
        "\64\2\uffff\1\64\1\u01de\1\uffff\1\64\1\uffff\2\64\1\uffff\1\64"+
        "\2\uffff\1\u01e3\1\uffff\1\u01e4\2\uffff\1\64\1\uffff\1\u01e6\2"+
        "\uffff\1\u01e7\1\64\4\uffff\1\u01e9\3\64\1\uffff\1\64\1\u01ee\1"+
        "\64\1\u01f0\2\uffff\1\64\2\uffff\1\64\1\uffff\1\u01f3\1\u01f4\1"+
        "\64\1\u01f6\1\uffff\1\u01f7\1\uffff\1\u01f8\1\64\2\uffff\1\u01fa"+
        "\3\uffff\1\64\1\uffff\3\64\1\u01ff\1\uffff";
    static final String DFA34_eofS =
        "\u0200\uffff";
    static final String DFA34_minS =
        "\1\11\1\56\3\47\1\60\1\uffff\1\47\5\uffff\1\52\2\uffff\1\52\1\uffff"+
        "\1\75\1\uffff\5\75\1\uffff\1\174\5\uffff\1\154\1\47\2\141\1\154"+
        "\1\141\1\157\1\141\1\144\2\151\1\47\1\146\1\151\2\145\1\141\1\156"+
        "\1\141\1\150\2\uffff\1\56\1\uffff\1\143\1\154\1\147\1\60\23\uffff"+
        "\1\154\1\144\1\60\1\163\1\145\1\165\1\154\1\141\1\162\1\164\1\143"+
        "\1\163\1\157\1\163\1\144\1\143\1\154\1\164\1\162\1\157\1\164\1\141"+
        "\1\166\1\145\3\60\1\153\1\143\1\156\1\144\2\60\1\164\1\60\1\145"+
        "\1\151\1\142\1\166\1\163\1\154\1\141\1\172\1\154\1\141\1\142\1\145"+
        "\1\60\1\165\1\151\1\144\1\151\1\154\2\145\1\164\1\uffff\1\157\1"+
        "\60\1\141\1\154\1\151\1\167\1\uffff\1\60\1\145\3\60\1\uffff\1\145"+
        "\1\143\1\163\1\141\1\160\1\156\1\163\1\141\1\162\1\145\1\154\1\141"+
        "\1\145\1\143\1\164\1\160\1\145\1\60\1\145\2\163\1\143\1\60\1\155"+
        "\1\157\1\156\1\165\1\151\1\156\1\uffff\3\145\2\uffff\1\145\1\153"+
        "\1\165\1\145\2\uffff\1\151\1\145\1\uffff\1\162\1\157\1\143\1\154"+
        "\3\157\1\145\1\162\1\145\1\60\1\162\1\141\1\156\1\uffff\1\145\1"+
        "\157\1\151\1\141\1\156\1\165\1\167\1\156\1\150\1\155\1\uffff\1\151"+
        "\1\60\1\156\1\145\1\uffff\1\162\3\uffff\1\60\1\153\1\164\1\165\1"+
        "\155\1\162\1\145\1\150\1\164\1\145\1\157\1\60\1\141\1\165\1\164"+
        "\1\60\1\151\2\60\1\146\1\uffff\1\160\1\165\1\164\1\145\1\150\1\uffff"+
        "\2\60\1\164\1\160\1\156\1\164\1\170\2\162\3\60\1\163\1\60\1\157"+
        "\1\162\1\154\1\162\1\145\1\151\1\164\1\165\1\153\1\143\1\145\1\60"+
        "\1\uffff\1\164\1\165\1\145\2\60\1\156\1\165\1\166\1\164\1\147\1"+
        "\145\2\60\1\145\1\60\1\160\1\164\1\uffff\1\60\1\145\1\60\1\uffff"+
        "\1\60\1\145\1\164\1\156\1\145\1\143\1\60\1\145\1\156\1\162\1\uffff"+
        "\1\162\1\154\1\145\1\uffff\1\156\2\uffff\1\60\1\164\2\163\2\60\2"+
        "\uffff\2\60\1\147\1\151\1\60\1\164\1\163\3\uffff\2\60\1\uffff\1"+
        "\156\1\60\1\141\1\60\1\144\1\143\1\60\1\162\1\145\1\164\1\60\1\uffff"+
        "\1\60\1\164\1\60\2\uffff\1\60\1\145\1\157\1\145\1\60\1\163\1\60"+
        "\2\uffff\1\60\1\uffff\1\162\1\60\1\uffff\1\156\2\uffff\1\162\1\150"+
        "\2\163\1\164\1\uffff\1\60\1\164\1\60\1\145\1\164\1\60\1\143\1\uffff"+
        "\2\151\1\60\4\uffff\1\60\1\146\1\163\1\uffff\1\60\1\145\2\uffff"+
        "\1\60\1\uffff\1\160\1\uffff\1\165\1\60\1\uffff\1\143\2\60\2\uffff"+
        "\1\150\2\uffff\1\60\1\164\1\60\1\uffff\1\60\2\uffff\1\145\1\uffff"+
        "\1\60\1\163\2\60\1\163\1\60\1\uffff\1\60\1\uffff\2\60\1\uffff\1"+
        "\164\1\157\1\166\2\uffff\1\151\1\60\1\uffff\1\143\1\uffff\1\163"+
        "\1\162\1\uffff\1\145\2\uffff\1\60\1\uffff\1\60\2\uffff\1\163\1\uffff"+
        "\1\60\2\uffff\1\60\1\142\4\uffff\1\60\1\156\2\145\1\uffff\1\164"+
        "\1\60\1\145\1\60\2\uffff\1\163\2\uffff\1\171\1\uffff\2\60\1\144"+
        "\1\60\1\uffff\1\60\1\uffff\1\60\1\137\2\uffff\1\60\3\uffff\1\162"+
        "\1\uffff\2\157\1\164\1\60\1\uffff";
    static final String DFA34_maxS =
        "\1\176\1\71\1\165\1\171\1\47\1\71\1\uffff\1\47\5\uffff\1\52\2\uffff"+
        "\1\52\1\uffff\1\75\1\uffff\1\76\4\75\1\uffff\1\174\5\uffff\1\163"+
        "\1\47\1\165\1\162\1\170\2\162\1\141\1\163\2\157\1\47\1\166\1\165"+
        "\1\145\1\164\1\162\1\163\2\151\2\uffff\1\71\1\uffff\1\167\1\154"+
        "\1\164\1\172\23\uffff\1\164\1\171\1\172\1\163\1\145\1\165\1\156"+
        "\1\145\1\162\1\164\2\163\1\157\1\163\1\144\1\151\1\154\1\164\1\162"+
        "\1\157\1\164\1\157\1\166\1\145\3\172\1\153\1\143\1\156\1\144\2\172"+
        "\1\164\1\172\1\145\1\157\1\142\2\166\1\154\1\141\1\172\1\154\1\141"+
        "\1\142\1\145\1\172\1\165\1\160\1\144\1\151\1\154\2\145\1\164\1\uffff"+
        "\1\157\1\172\1\141\1\154\1\151\1\167\1\uffff\1\172\1\145\3\172\1"+
        "\uffff\1\145\1\143\1\163\1\165\1\160\1\156\1\163\1\141\1\163\1\145"+
        "\1\154\1\141\1\145\1\143\1\164\1\160\1\151\1\172\1\154\2\163\1\143"+
        "\1\172\1\155\1\157\1\156\1\165\1\151\1\156\1\uffff\2\145\1\157\2"+
        "\uffff\1\145\1\153\1\165\1\145\2\uffff\1\151\1\145\1\uffff\1\162"+
        "\1\157\1\143\1\154\3\157\1\145\1\162\1\145\1\172\1\162\1\154\1\156"+
        "\1\uffff\1\145\1\161\1\151\1\141\1\156\1\165\1\167\1\162\1\150\1"+
        "\155\1\uffff\1\151\1\172\1\156\1\145\1\uffff\1\162\3\uffff\1\172"+
        "\1\153\1\164\1\165\1\155\1\162\1\145\1\150\1\164\1\145\1\157\1\172"+
        "\1\141\1\165\1\164\1\172\1\151\2\172\1\146\1\uffff\1\160\1\165\1"+
        "\164\1\145\1\150\1\uffff\2\172\1\164\1\160\1\156\1\164\1\170\2\162"+
        "\3\172\1\163\1\172\1\157\1\162\1\154\1\162\1\145\1\151\1\164\1\165"+
        "\1\153\1\143\1\145\1\172\1\uffff\1\164\1\165\1\145\2\172\1\156\1"+
        "\165\1\166\1\164\1\147\1\145\2\172\1\145\1\172\1\160\1\164\1\uffff"+
        "\1\172\1\145\1\172\1\uffff\1\172\1\145\1\164\1\156\1\145\1\143\1"+
        "\172\1\145\1\156\1\162\1\uffff\1\162\1\154\1\145\1\uffff\1\156\2"+
        "\uffff\1\172\1\164\2\163\2\172\2\uffff\2\172\1\147\1\151\1\172\1"+
        "\164\1\163\3\uffff\2\172\1\uffff\1\156\1\172\1\141\1\172\1\144\1"+
        "\143\1\172\1\162\1\145\1\164\1\172\1\uffff\1\172\1\164\1\172\2\uffff"+
        "\1\172\1\145\1\157\1\145\1\172\1\163\1\172\2\uffff\1\172\1\uffff"+
        "\1\162\1\172\1\uffff\1\156\2\uffff\1\162\1\150\2\163\1\164\1\uffff"+
        "\1\172\1\164\1\172\1\145\1\164\1\172\1\143\1\uffff\2\151\1\172\4"+
        "\uffff\1\172\1\146\1\163\1\uffff\1\172\1\145\2\uffff\1\172\1\uffff"+
        "\1\160\1\uffff\1\165\1\172\1\uffff\1\143\2\172\2\uffff\1\150\2\uffff"+
        "\1\172\1\164\1\172\1\uffff\1\172\2\uffff\1\145\1\uffff\1\172\1\163"+
        "\2\172\1\163\1\172\1\uffff\1\172\1\uffff\2\172\1\uffff\1\164\1\157"+
        "\1\166\2\uffff\1\151\1\172\1\uffff\1\143\1\uffff\1\163\1\162\1\uffff"+
        "\1\145\2\uffff\1\172\1\uffff\1\172\2\uffff\1\163\1\uffff\1\172\2"+
        "\uffff\1\172\1\142\4\uffff\1\172\1\156\2\145\1\uffff\1\164\1\172"+
        "\1\145\1\172\2\uffff\1\163\2\uffff\1\171\1\uffff\2\172\1\144\1\172"+
        "\1\uffff\1\172\1\uffff\1\172\1\137\2\uffff\1\172\3\uffff\1\162\1"+
        "\uffff\2\157\1\164\1\172\1\uffff";
    static final String DFA34_acceptS =
        "\6\uffff\1\7\1\uffff\1\11\1\12\1\13\1\14\1\15\1\uffff\1\20\1\21"+
        "\1\uffff\1\23\1\uffff\1\26\5\uffff\1\37\1\uffff\1\42\1\43\1\44\1"+
        "\45\1\46\24\uffff\1\u0084\1\6\1\uffff\1\2\4\uffff\1\3\1\4\1\5\1"+
        "\10\1\16\1\17\1\22\1\24\1\25\1\27\1\32\1\30\1\31\1\36\1\35\1\33"+
        "\1\34\1\40\1\41\70\uffff\1\1\6\uffff\1\57\5\uffff\1\53\35\uffff"+
        "\1\121\3\uffff\1\122\1\130\4\uffff\1\141\1\142\2\uffff\1\144\16"+
        "\uffff\1\164\12\uffff\1\136\4\uffff\1\47\1\uffff\1\51\1\52\1\54"+
        "\24\uffff\1\104\5\uffff\1\112\32\uffff\1\157\21\uffff\1\140\3\uffff"+
        "\1\60\12\uffff\1\74\3\uffff\1\100\1\uffff\1\102\1\103\6\uffff\1"+
        "\113\1\114\7\uffff\1\127\1\131\1\132\2\uffff\1\134\13\uffff\1\156"+
        "\3\uffff\1\163\1\165\7\uffff\1\172\1\174\1\uffff\1\176\2\uffff\1"+
        "\55\1\uffff\1\50\1\61\5\uffff\1\70\7\uffff\1\u0081\3\uffff\1\110"+
        "\1\111\1\115\1\116\3\uffff\1\123\2\uffff\1\133\1\u0080\1\uffff\1"+
        "\145\1\uffff\1\147\2\uffff\1\u0082\3\uffff\1\155\1\160\1\uffff\1"+
        "\162\1\166\3\uffff\1\177\1\uffff\1\173\1\175\1\uffff\1\137\6\uffff"+
        "\1\71\1\uffff\1\73\2\uffff\1\77\3\uffff\1\107\1\117\2\uffff\1\125"+
        "\1\uffff\1\143\2\uffff\1\151\1\uffff\1\153\1\154\1\uffff\1\167\1"+
        "\uffff\1\170\1\171\1\uffff\1\56\1\uffff\1\63\1\64\2\uffff\1\66\1"+
        "\72\1\75\1\76\4\uffff\1\124\4\uffff\1\161\1\u0083\1\uffff\1\62\1"+
        "\65\1\uffff\1\101\4\uffff\1\146\1\uffff\1\152\2\uffff\1\105\1\106"+
        "\1\uffff\1\126\1\150\1\135\1\uffff\1\120\4\uffff\1\67";
    static final String DFA34_specialS =
        "\u0200\uffff}>";
    static final String[] DFA34_transitionS = {
            "\2\37\2\uffff\1\37\22\uffff\1\37\1\26\1\10\2\uffff\1\11\1\12"+
            "\1\6\1\13\1\14\1\15\1\16\1\17\1\37\1\5\1\20\12\1\1\22\1\23\1"+
            "\24\1\33\1\25\1\31\1\21\1\64\1\41\13\64\1\53\2\64\1\7\6\64\1"+
            "\4\2\64\1\34\1\uffff\1\35\1\27\1\36\1\uffff\1\40\1\3\1\42\1"+
            "\43\1\44\1\45\1\46\1\47\1\50\2\64\1\51\1\52\1\2\1\54\1\55\1"+
            "\7\1\56\1\57\1\60\1\61\1\62\1\63\1\4\2\64\1\uffff\1\32\1\uffff"+
            "\1\30",
            "\1\66\1\uffff\12\1",
            "\1\67\107\uffff\1\70\5\uffff\1\71",
            "\1\74\75\uffff\1\72\23\uffff\1\73",
            "\1\75",
            "\12\65",
            "",
            "\1\77",
            "",
            "",
            "",
            "",
            "",
            "\1\100",
            "",
            "",
            "\1\37",
            "",
            "\1\103",
            "",
            "\1\105\1\106",
            "\1\110",
            "\1\106",
            "\1\106",
            "\1\106",
            "",
            "\1\115",
            "",
            "",
            "",
            "",
            "",
            "\1\117\1\uffff\1\120\4\uffff\1\121",
            "\1\74",
            "\1\122\6\uffff\1\123\3\uffff\1\124\2\uffff\1\125\2\uffff\1"+
            "\126\2\uffff\1\127",
            "\1\130\3\uffff\1\131\3\uffff\1\132\10\uffff\1\133",
            "\1\134\1\uffff\1\135\11\uffff\1\136",
            "\1\137\3\uffff\1\140\11\uffff\1\141\2\uffff\1\142",
            "\1\143\2\uffff\1\144",
            "\1\145",
            "\1\146\1\uffff\1\147\7\uffff\1\150\4\uffff\1\151",
            "\1\152\5\uffff\1\153",
            "\1\154\5\uffff\1\155",
            "\1\67",
            "\1\156\7\uffff\1\157\1\uffff\1\160\1\uffff\1\161\3\uffff\1"+
            "\162",
            "\1\165\10\uffff\1\163\2\uffff\1\164",
            "\1\166",
            "\1\167\2\uffff\1\170\1\171\7\uffff\1\172\2\uffff\1\173",
            "\1\174\6\uffff\1\175\6\uffff\1\176\2\uffff\1\177",
            "\1\u0080\1\uffff\1\u0081\2\uffff\1\u0082",
            "\1\u0083\7\uffff\1\u0084",
            "\1\u0085\1\u0086",
            "",
            "",
            "\1\u0087\1\uffff\12\65",
            "",
            "\1\u0088\20\uffff\1\u0089\2\uffff\1\u008a",
            "\1\u008b",
            "\1\u008c\14\uffff\1\u008d",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\u008f\7\uffff\1\u0090",
            "\1\u0091\24\uffff\1\u0092",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\2\64\1\u0093\27\64",
            "\1\u0095",
            "\1\u0096",
            "\1\u0097",
            "\1\u0098\1\u0099\1\u009a",
            "\1\u009b\3\uffff\1\u009c",
            "\1\u009d",
            "\1\u009e",
            "\1\u009f\2\uffff\1\u00a0\5\uffff\1\u00a1\6\uffff\1\u00a2",
            "\1\u00a3",
            "\1\u00a4",
            "\1\u00a5",
            "\1\u00a6",
            "\1\u00a7\5\uffff\1\u00a8",
            "\1\u00a9",
            "\1\u00aa",
            "\1\u00ab",
            "\1\u00ac",
            "\1\u00ad",
            "\1\u00ae\15\uffff\1\u00af",
            "\1\u00b0",
            "\1\u00b1",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\3\64\1\u00b3\16\64"+
            "\1\u00b4\1\u00b5\6\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u00b8",
            "\1\u00b9",
            "\1\u00ba",
            "\1\u00bb",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u00be",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\3\64\1\u00bf\26\64",
            "\1\u00c1",
            "\1\u00c2\5\uffff\1\u00c3",
            "\1\u00c4",
            "\1\u00c5",
            "\1\u00c6\2\uffff\1\u00c7",
            "\1\u00c8",
            "\1\u00c9",
            "\1\u00ca",
            "\1\u00cb",
            "\1\u00cc",
            "\1\u00cd",
            "\1\u00ce",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u00d0",
            "\1\u00d1\6\uffff\1\u00d2",
            "\1\u00d3",
            "\1\u00d4",
            "\1\u00d5",
            "\1\u00d6",
            "\1\u00d7",
            "\1\u00d8",
            "",
            "\1\u00d9",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u00db",
            "\1\u00dc",
            "\1\u00dd",
            "\1\u00de",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u00e0",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "\1\u00e4",
            "\1\u00e5",
            "\1\u00e6",
            "\1\u00e7\23\uffff\1\u00e8",
            "\1\u00e9",
            "\1\u00ea",
            "\1\u00eb",
            "\1\u00ec",
            "\1\u00ed\1\u00ee",
            "\1\u00ef",
            "\1\u00f0",
            "\1\u00f1",
            "\1\u00f2",
            "\1\u00f3",
            "\1\u00f4",
            "\1\u00f5",
            "\1\u00f6\3\uffff\1\u00f7",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u00f9\6\uffff\1\u00fa",
            "\1\u00fb",
            "\1\u00fc",
            "\1\u00fd",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u00ff",
            "\1\u0100",
            "\1\u0101",
            "\1\u0102",
            "\1\u0103",
            "\1\u0104",
            "",
            "\1\u0105",
            "\1\u0106",
            "\1\u0107\11\uffff\1\u0108",
            "",
            "",
            "\1\u0109",
            "\1\u010a",
            "\1\u010b",
            "\1\u010c",
            "",
            "",
            "\1\u010d",
            "\1\u010e",
            "",
            "\1\u010f",
            "\1\u0110",
            "\1\u0111",
            "\1\u0112",
            "\1\u0113",
            "\1\u0114",
            "\1\u0115",
            "\1\u0116",
            "\1\u0117",
            "\1\u0118",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u011a",
            "\1\u011b\12\uffff\1\u011c",
            "\1\u011d",
            "",
            "\1\u011e",
            "\1\u011f\1\uffff\1\u0120",
            "\1\u0121",
            "\1\u0122",
            "\1\u0123",
            "\1\u0124",
            "\1\u0125",
            "\1\u0126\3\uffff\1\u0127",
            "\1\u0128",
            "\1\u0129",
            "",
            "\1\u012a",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u012c",
            "\1\u012d",
            "",
            "\1\u012e",
            "",
            "",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0130",
            "\1\u0131",
            "\1\u0132",
            "\1\u0133",
            "\1\u0134",
            "\1\u0135",
            "\1\u0136",
            "\1\u0137",
            "\1\u0138",
            "\1\u0139",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u013b",
            "\1\u013c",
            "\1\u013d",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u013f",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0142",
            "",
            "\1\u0143",
            "\1\u0144",
            "\1\u0145",
            "\1\u0146",
            "\1\u0147",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u014a",
            "\1\u014b",
            "\1\u014c",
            "\1\u014d",
            "\1\u014e",
            "\1\u014f",
            "\1\u0150",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0154",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\13\64\1\u0155\16"+
            "\64",
            "\1\u0157",
            "\1\u0158",
            "\1\u0159",
            "\1\u015a",
            "\1\u015b",
            "\1\u015c",
            "\1\u015d",
            "\1\u015e",
            "\1\u015f",
            "\1\u0160",
            "\1\u0161",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "\1\u0163",
            "\1\u0164",
            "\1\u0165",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0168",
            "\1\u0169",
            "\1\u016a",
            "\1\u016b",
            "\1\u016c",
            "\1\u016d",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\22\64\1\u016e\7\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0171",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0173",
            "\1\u0174",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0176",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0179",
            "\1\u017a",
            "\1\u017b",
            "\1\u017c",
            "\1\u017d",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u017f",
            "\1\u0180",
            "\1\u0181",
            "",
            "\1\u0182",
            "\1\u0183",
            "\1\u0184",
            "",
            "\1\u0185",
            "",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0187",
            "\1\u0188",
            "\1\u0189",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u018e",
            "\1\u018f",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\4\64\1\u0190\25\64",
            "\1\u0192",
            "\1\u0193",
            "",
            "",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "\1\u0196",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0198",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u019a",
            "\1\u019b",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u019d",
            "\1\u019e",
            "\1\u019f",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u01a2",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u01a5",
            "\1\u01a6",
            "\1\u01a7",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u01a9",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "\1\u01ac",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "\1\u01ae",
            "",
            "",
            "\1\u01af",
            "\1\u01b0",
            "\1\u01b1",
            "\1\u01b2",
            "\1\u01b3",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u01b5",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u01b7",
            "\1\u01b8",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u01ba",
            "",
            "\1\u01bb",
            "\1\u01bc",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "",
            "",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u01bf",
            "\1\u01c0",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u01c2",
            "",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "\1\u01c4",
            "",
            "\1\u01c5",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "\1\u01c7",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "",
            "\1\u01ca",
            "",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u01cc",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "",
            "\1\u01cf",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u01d1",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u01d4",
            "\12\64\7\uffff\32\64\4\uffff\1\u01d5\1\uffff\32\64",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "\1\u01da",
            "\1\u01db",
            "\1\u01dc",
            "",
            "",
            "\1\u01dd",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "\1\u01df",
            "",
            "\1\u01e0",
            "\1\u01e1",
            "",
            "\1\u01e2",
            "",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "",
            "\1\u01e5",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u01e8",
            "",
            "",
            "",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u01ea",
            "\1\u01eb",
            "\1\u01ec",
            "",
            "\1\u01ed",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u01ef",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "",
            "\1\u01f1",
            "",
            "",
            "\1\u01f2",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u01f5",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u01f9",
            "",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "",
            "",
            "\1\u01fb",
            "",
            "\1\u01fc",
            "\1\u01fd",
            "\1\u01fe",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            ""
    };

    static final short[] DFA34_eot = DFA.unpackEncodedString(DFA34_eotS);
    static final short[] DFA34_eof = DFA.unpackEncodedString(DFA34_eofS);
    static final char[] DFA34_min = DFA.unpackEncodedStringToUnsignedChars(DFA34_minS);
    static final char[] DFA34_max = DFA.unpackEncodedStringToUnsignedChars(DFA34_maxS);
    static final short[] DFA34_accept = DFA.unpackEncodedString(DFA34_acceptS);
    static final short[] DFA34_special = DFA.unpackEncodedString(DFA34_specialS);
    static final short[][] DFA34_transition;

    static {
        int numStates = DFA34_transitionS.length;
        DFA34_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA34_transition[i] = DFA.unpackEncodedString(DFA34_transitionS[i]);
        }
    }

    class DFA34 extends DFA {

        public DFA34(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 34;
            this.eot = DFA34_eot;
            this.eof = DFA34_eof;
            this.min = DFA34_min;
            this.max = DFA34_max;
            this.accept = DFA34_accept;
            this.special = DFA34_special;
            this.transition = DFA34_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( FOR_NOTATION | NATIONAL_CHAR_STRING_LIT | BIT_STRING_LIT | HEX_STRING_LIT | PERIOD | EXACT_NUM_LIT | CHAR_STRING | CHAR_STRING_PERL | DELIMITED_ID | PERCENT | AMPERSAND | LEFT_PAREN | RIGHT_PAREN | DOUBLE_ASTERISK | ASTERISK | PLUS_SIGN | COMMA | SOLIDUS | AT_SIGN | ASSIGN_OP | COLON | SEMICOLON | LESS_THAN_OR_EQUALS_OP | LESS_THAN_OP | GREATER_THAN_OR_EQUALS_OP | NOT_EQUAL_OP | CARRET_OPERATOR_PART | TILDE_OPERATOR_PART | EXCLAMATION_OPERATOR_PART | GREATER_THAN_OP | QUESTION_MARK | CONCATENATION_OP | VERTICAL_BAR | EQUALS_OP | LEFT_BRACKET | RIGHT_BRACKET | INTRODUCER | SEPARATOR | SQL92_RESERVED_ALL | SQL92_RESERVED_ALTER | SQL92_RESERVED_AND | SQL92_RESERVED_ANY | SQL92_RESERVED_AS | SQL92_RESERVED_ASC | SQL92_RESERVED_BEGIN | SQL92_RESERVED_BETWEEN | SQL92_RESERVED_BY | SQL92_RESERVED_CASE | SQL92_RESERVED_CHECK | PLSQL_RESERVED_CLUSTERS | PLSQL_RESERVED_COLAUTH | PLSQL_RESERVED_COLUMNS | PLSQL_RESERVED_COMPRESS | SQL92_RESERVED_CONNECT | PLSQL_NON_RESERVED_CONNECT_BY_ROOT | PLSQL_RESERVED_CRASH | SQL92_RESERVED_CREATE | SQL92_RESERVED_CURRENT | SQL92_RESERVED_CURSOR | SQL92_RESERVED_DATE | SQL92_RESERVED_DECLARE | SQL92_RESERVED_DEFAULT | SQL92_RESERVED_DELETE | SQL92_RESERVED_DESC | SQL92_RESERVED_DISTINCT | SQL92_RESERVED_DROP | SQL92_RESERVED_ELSE | SQL92_RESERVED_END | SQL92_RESERVED_EXCEPTION | PLSQL_RESERVED_EXCLUSIVE | SQL92_RESERVED_EXISTS | SQL92_RESERVED_FALSE | SQL92_RESERVED_FETCH | SQL92_RESERVED_FOR | SQL92_RESERVED_FROM | SQL92_RESERVED_GOTO | SQL92_RESERVED_GRANT | SQL92_RESERVED_GROUP | SQL92_RESERVED_HAVING | PLSQL_RESERVED_IDENTIFIED | PLSQL_RESERVED_IF | SQL92_RESERVED_IN | PLSQL_RESERVED_INDEX | PLSQL_RESERVED_INDEXES | SQL92_RESERVED_INSERT | SQL92_RESERVED_INTERSECT | SQL92_RESERVED_INTO | SQL92_RESERVED_IS | SQL92_RESERVED_LIKE | PLSQL_RESERVED_LOCK | PLSQL_RESERVED_MINUS | PLSQL_RESERVED_MODE | PLSQL_RESERVED_NOCOMPRESS | SQL92_RESERVED_NOT | PLSQL_RESERVED_NOWAIT | SQL92_RESERVED_NULL | SQL92_RESERVED_OF | SQL92_RESERVED_ON | SQL92_RESERVED_OPTION | SQL92_RESERVED_OR | SQL92_RESERVED_ORDER | SQL92_RESERVED_OVERLAPS | SQL92_RESERVED_PRIOR | SQL92_RESERVED_PROCEDURE | SQL92_RESERVED_PUBLIC | PLSQL_RESERVED_RESOURCE | SQL92_RESERVED_REVOKE | SQL92_RESERVED_SELECT | PLSQL_RESERVED_SHARE | SQL92_RESERVED_SIZE | SQL92_RESERVED_SQL | PLSQL_RESERVED_START | PLSQL_RESERVED_TABAUTH | SQL92_RESERVED_TABLE | SQL92_RESERVED_THEN | SQL92_RESERVED_TO | SQL92_RESERVED_TRUE | SQL92_RESERVED_UNION | SQL92_RESERVED_UNIQUE | SQL92_RESERVED_UPDATE | SQL92_RESERVED_VALUES | SQL92_RESERVED_VIEW | PLSQL_RESERVED_VIEWS | SQL92_RESERVED_WHEN | SQL92_RESERVED_WHERE | SQL92_RESERVED_WITH | PLSQL_NON_RESERVED_USING | PLSQL_NON_RESERVED_MODEL | PLSQL_NON_RESERVED_ELSIF | PLSQL_NON_RESERVED_PIVOT | PLSQL_NON_RESERVED_UNPIVOT | REGULAR_ID );";
        }
    }
 

}