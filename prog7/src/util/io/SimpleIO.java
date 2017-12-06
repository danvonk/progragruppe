package io;

import java.awt.GraphicsEnvironment;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * Class SimpleIO - class for input of simple input types
 * via simple dialog box (or if headless System.in use) 
 * and output of strings (vie dialog box or System.out)
 */

public class SimpleIO
{
    //Running without display support?
    private static final boolean HEADLESS = GraphicsEnvironment.isHeadless();
    
    /**
     * Prompting the user for input (If Headless, will be prompted with System.in and System.out)
     * @param title Title of the input
     * @param messages Message that will be shown to the user
     * @param options Optional: Give the user options
     * @param initialOption Optional: The initial option
     * @return The input string or the index of the selected option
     */
    private static String getInputString(String title, Object messages[], 
            Object[] options, Object initialOption){
        return HEADLESS ? headlessImpl(title, messages, options, initialOption) : 
            displayImpl(title, messages, options, initialOption);
    }
    
    /**
     * Prompting the user for input (If Headless, will be prompted with System.in and System.out)
     * @param title Title of the input
     * @param messages Message that will shown
     * @return The inputstring
     */
    private static String getInputString(String title, Object messages[]){
        return getInputString(title, messages, null, null);
    }
    
    private static String headlessImpl(String title, Object messages[], 
            Object[] options, Object initialOption){
        @SuppressWarnings("resource")//System.in should not be closed
        Scanner scanner = new Scanner(System.in);
        
        System.out.println(title);
        
        for(Object o : messages)
            System.out.println(o.toString());
        
        if(options != null){
            System.out.print("Choose one: |");
            
            for(Object option : options){
                if(option == initialOption){
                    System.out.print(" " + option.toString() + " (INITIAL) |");
                }
                else{
                    System.out.print(" " + option.toString() + " |");
                }
            }
            
            System.out.println("");
        }
        
        String result = scanner.nextLine();
        
        if(options != null){
            for(int i = 0; i < options.length; i++){
                if(options[i].toString().equals(result))
                    return i + "";
            }
            
            return null;
        }
        
        
        return result;
    }
    
    /**
     * Prompting the user with a gui
     * @param title The title from the frame
     * @param messages The message the user will be shown
     * @param options Optional: Give the user options
     * @param initialOption Optional: The initial option
     * @return The inputstring or the index of the option if given
     */
    private static String displayImpl(String title, Object messages[], 
            Object[] options, Object initialOption){
        if(options == null){
            return JOptionPane.showInputDialog(null, messages, title, JOptionPane.QUESTION_MESSAGE);
        }
        else{
            return "" + JOptionPane.showOptionDialog(null, messages, title, 
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, 
                    null, options, initialOption);
        }
    }
    
    /**
     * Getting information from the user and returning it
     * @param type The type of information
     * @param prompt The question prompted to the user
     * @return the information as Object (It can safely casted to the given type)
     */
    private static Object getInputLine(Type type, String prompt){
        Object result = null;
        Object[] commentArray = {prompt, "", ""};
        
        while(result == null){
            String response;
            
            if(type == Type.Boolean){
                response = getInputString(type.getDescription() + " eingeben", commentArray, 
                        new String[]{"True", "False"}, "True");
            }
            else{
                response = getInputString(type.getDescription() + " eingeben", commentArray);
            }
            
            
            if(response != null){
                try{
                    switch (type) {
                    case String:{
                        result = response;
                        break;
                    }
                    case Boolean:{
                        result = response.equals("0");
                        break;
                    }
                    case Int:{
                        result = Integer.parseInt(response);
                        break;
                    }
                    case Char:{
                        if(response.length() == 1){
                            result = response.charAt(0);
                        }
                        
                        break;
                    }
                    case Float:{
                        result = Float.parseFloat(response);
                        break;
                    }
                    case Double:{
                        result = Double.parseDouble(response);
                        break;
                    }
                    default:
                        throw new IllegalStateException("Type incorrect");
                    }  
                } 
                catch(NumberFormatException exception){
                    result = null;
                }
            }
            
            if(result == null){
                commentArray[1] = "Invalid input: " + response;
                commentArray[2] = "Enter a valid " + type.getDescription();
            }
        }
        
        return result;
    }
    
    /**
     ** String input from the user via a simple dialog.
     ** @param prompt the message string to be displayed inside dialog    
     ** @return String input from the user.
     **/
    public static String getString(String prompt){
        return (String) getInputLine(Type.String, prompt);
    }

    /**
     ** char input from the user via a simple dialog.
     ** @param prompt the message string to be displayed inside dialog  
     ** @return char input from the user.
     **/
    public static char getChar(String prompt){
        return (char) getInputLine(Type.Char, prompt);
    }

    /**
     ** boolean selection from the user via a simple dialog.
     ** @param  prompt message to appear in dialog
     ** @return boolean selection from the user
     **/
    public static boolean getBoolean(String prompt){
        return (boolean) getInputLine(Type.Boolean, prompt);
    }


   /**
    ** returns integer input from the user via a simple dialog.
    ** @param prompt the message string to be displayed inside dialog
    ** @return the input integer
    **/
    public static int getInt(String prompt){
        return (int) getInputLine(Type.Int, prompt);
    }


   /**
    ** returns a float input from the user via a simple dialog.
    ** @param prompt the message string to be displayed inside dialog
    ** @return the input float
    **/
    public static float getFloat(String prompt){
        return (float) getInputLine(Type.Float, prompt);
    }

   /**
    ** returns a double input from the user via a simple dialog.
    ** @param prompt the message string to be displayed inside dialog
    ** @return the input double
    **/
    public static double getDouble(String prompt){
        return (double) getInputLine(Type.Double, prompt);
    }
    
    /**
     * Defines the type from the user-question
     */
    private enum Type{
        String("String"), Char("Char"), Int("int"), 
        Boolean("Boolean"), Float("Float"), Double("Double");
        
        private String description;
        
        private Type(String input){
            this.description = input;
        }
        
        public String getDescription(){
            return description;
        }
    }

    /** prints the string "content" in a window with the title "title"
     ** @param content the string to be displayed
     ** @param title the title of the display window
     **/
    public static void output (String content, String title) {
        JOptionPane.showMessageDialog (null,content,title,JOptionPane.PLAIN_MESSAGE); 
    }
}
