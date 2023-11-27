package packages.baby.compiler.LexicalAnalysis;

import java.util.*;

public class Automaton {

    // private char aCh;

    /*
     * Token types ID, STR, CHAR, INT, and DEC are
     * associated with an infinite variety of potential lexemes.
     * Hence, for strings that are not part of fixed lexemes,
     * checking token type through DFA is necessary.
     * We set the five token types as final states.
     * Default: INVALID_STATE
     */

    private Map<State, TokenType> finalStates;

    public Automaton(){
        initFinalStates();
    }

    private void initFinalStates(){
        finalStates = new HashMap<>();
        finalStates.put(State.Q1, TokenType.ID);
        finalStates.put(State.Q13, TokenType.STR);
        finalStates.put(State.Q9, TokenType.CHAR);
        finalStates.put(State.Q3, TokenType.INT);
        finalStates.put(State.Q5, TokenType.DEC);
    }

    private State executeTransition(State currentState, char input) {
        switch (currentState) {
            case START: {
                
                if(input == '_' || isLetter(input))
                    return State.Q1;
                else if(input == '+' || input == '-')
                    return State.Q2;
                else if(isDigit(input))
                    return State.Q3;
                else if(input == '\'') 
                    return State.Q6;
                else if(input == '"') 
                    return State.Q10;
                else
                    return State.INVALID_STATE; // HMMMMMM
            }

            case Q1: {
                return  (input == '_' || isLetter(input) || isDigit(input)) ? 
                        State.Q1 : State.INVALID_STATE;
            }

            case Q2: {
                return  isDigit(input) ? State.Q3 : State.INVALID_STATE;
                
            }

            case Q3: {
                if(isDigit(input))
                    return State.Q3;
                else if(input == '.')
                    return State.Q4;
                else
                    return State.INVALID_STATE; // HMMMMM
            }

            case Q4: {
                return  isDigit(input) ? State.Q5 : State.INVALID_STATE;
            }

            case Q5: {
                return  isDigit(input) ? State.Q5 : State.INVALID_STATE;
            }

            case Q6: {
                // if (isLetter(input) || isDigit(input) || input == ' ' ||   
                //    (32 <= input && input >= 126) && input != '\\' && input != '\'') // redundant

                if (input != '\\' && input != '\'')
                    return State.Q7;
                else if (input == '\\')
                    return State.Q8;
                else
                    return State.INVALID_STATE; // HMMMMM
            }

            case Q7: {
                return  input == '\'' ? State.Q9 : State.INVALID_STATE;
            }

            case Q8: {
                return  input == '\\' || input == '\'' ? State.Q7 : State.INVALID_STATE;
                // if (input == '\\' || input == '\'')
                //     return State.Q7;
                // else
                //     return State.INVALID_STATE; //HMMMMM
            }

            case Q9: {
                return  State.INVALID_STATE; // HMMMMMM
            }

            case Q10: {
                // if (isLetter(input) || isDigit(input) || input == ' ' ||   
                //    (32 <= input && input >= 126) && input != '\\' && input != '"') // redundant

                if (input != '\\' && input != '"')
                    return State.Q11;
                else if (input == '\\')
                    return State.Q12;
                else if (input == '"')
                    return State.Q13;
                else
                    return State.INVALID_STATE; // HMMMMM
            }

            case Q11: {
                // if (isLetter(input) || isDigit(input) || input == ' ' ||   
                //    (32 <= input && input >= 126) && input != '\\' && input != '"') // redundant

                if (input != '\\' && input != '"')
                    return State.Q11;
                else if (input == '\\')
                    return State.Q12;
                else if (input == '"')
                    return State.Q13;
                else
                    return State.INVALID_STATE; // HMMMMM
            }

            case Q12: {
                return  input == '\\' || input == '"' ? State.Q11 : State.INVALID_STATE;
                // if (input == '\\' || input == '"')
                //     return State.Q11;
                // else
                //     return State.INVALID_STATE; //HMMMMM
            }

            case Q13: {
                return State.INVALID_STATE;
            }
        
            default:
                return State.INVALID_STATE;
        }
    }
    
    
    // Check if a character is a digit
    public static boolean isDigit(char ch) {
        return Character.isDigit(ch);
    }

    // Check if a character is a letter (lowercase or uppercase)
    public static boolean isLetter(char ch) {
        return Character.isLetter(ch);
    }

    public TokenType getTokenType(String str){
        State state = State.START;

        // Evaluate character by character
        for(char ch : str.toCharArray()){
            // this.aCh = ch;
            state = executeTransition(state, ch);
        }

        // Check if current state is a final state. If not, then token is invalid.
        return finalStates.getOrDefault(state, TokenType.INVALID);
    }

    // public char getCh(){
    //     return this.aCh;
    // }
}
