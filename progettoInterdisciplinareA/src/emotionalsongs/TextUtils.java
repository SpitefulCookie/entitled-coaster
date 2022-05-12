package emotionalsongs;

import java.util.Scanner;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public class TextUtils {

    private static boolean debug = false;
    private static final Scanner inputScanner = new Scanner(System.in);
    
    private TextUtils() {super();}

    //normal 
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";


    // Bold
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE
    
    public static void setDebug(String value){

        if(value.equalsIgnoreCase("true")){
            debug = true; 
            TextUtils.printDebug("Debug mode is now enabled");
        }else if (value.equalsIgnoreCase("false")){
            debug = false; 
            TextUtils.printDebug("Debug mode is now disabled");
        }
    }

    public static void setDebug(boolean value){
    
        debug = value; 

        if(value){TextUtils.printDebug("Debug mode is now enabled");}
        else{TextUtils.printDebug("Debug mode is now disabled");}
        
    }

    public static void cls() { 

        if(!debug){
            System.out.print("\033[H\033[2J");  
            System.out.flush();
        } else{
            TextUtils.printDebug("\n" + StringUtils.repeat("-", 100)+"\n\t\t\tIn questo punto avviene un cls()!\n" + StringUtils.repeat("-", 100));
        }

    }  

    public static String formatTitleCapital(String str){

        String [] words = str.split(" ");
        StringBuilder result = new StringBuilder();

        for(String s : words){

            result.append(s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase());
            result.append(" ");

        }

        return result.toString().substring(0, (result.length()-1));

    }

    public static String stripCommas(String in){
        
        String result = in.replace(",", " ");

        return result.replace("  "," ");

    }

    /**
	 * Validates a regular CF.
	 * @param cf Normalized, 16 characters CF.
	 * @return {@code true} if valid, or {@code false} if the CF must be rejected.
     * @author Umberto Salsi <salsi@icosaedro.it>
     * @see Source: http://www.icosaedro.it/cf-pi/vedi-codice.cgi?f=cf-java.txt
     *
	 */
    public static boolean isValidCF(String cf){ 

		if(!cf.matches("^[0-9A-Z]{16}$")){
            TextUtils.printDebug("Il codice fiscale contiene caratteri non ammessi");
			return false;
        }

		int somma = 0;
		String evenMap = "BAFHJNPRTVCESULDGIMOQKWZYX";

		for(int i = 0; i < 15; i++){

			int charIntValue = cf.charAt(i);
			int idxEvenMap;

			if('0' <= charIntValue && charIntValue <= '9' ){
				idxEvenMap = charIntValue - '0';
            }else{
				idxEvenMap = charIntValue - 'A';
            }

			if( (i & 1) == 0 ){
				idxEvenMap = evenMap.charAt(idxEvenMap) - 'A';
            }

			somma += idxEvenMap;

		}

		if(somma%26 + 'A' != cf.charAt(15)){
            TextUtils.printDebug("Il codice fiscale ha fallito il checksum.");
            return false;
        }

        TextUtils.printDebug("Il codice fiscale e' valido");

		return true;

	}

    public static boolean isEmptyString(String input){return StringUtils.isAllBlank(input);}

    public static boolean isNumeric(String input){return StringUtils.isNumeric(input);}

    public static void printDebug(String msg){if(debug)System.out.println(GREEN+"[DEBUG] " + msg +RESET);}

    public static void printErrorMessage(String string, boolean newLine) {
        
        if(newLine){System.out.println("[" + TextUtils.RED + "ERRORE" + TextUtils.RESET + "] " + string);}
        else{System.out.print("[" + TextUtils.RED + "ERRORE" + TextUtils.RESET + "] " + string);}

    }
    
    public static boolean readYesOrNo() {

        String ans;
        
        do{

            //System.out.println("\r");
            ans = inputScanner.nextLine();

            if(ans.equalsIgnoreCase("s") || ans.equalsIgnoreCase("y")){

                return true;

            } else if(ans.equalsIgnoreCase("n")){

                return false;

            } else{TextUtils.printErrorMessage("Scelta non valida, inserire nuovamente: ", false);}

        }while (true); // :$
        
    }

    public static Scanner getScanner(){
        return inputScanner;
    }

    public static void printLogo(String subtitle, int padding){

        TextUtils.cls();

        System.out.println("\n\t\t══ " + TextUtils.GREEN + "EM" + TextUtils.CYAN + "OT" + TextUtils.BLUE + "IO" + TextUtils.PURPLE +"NAL" +TextUtils.RED + " SO" + TextUtils.YELLOW + "NGS" + TextUtils.WHITE + " ══" + TextUtils.RESET);

        if(subtitle != null){ 
            System.out.println("\t" + TextUtils.BLUE + "\t"+ StringUtils.repeat(" ", padding) + subtitle.toUpperCase() + TextUtils.WHITE_BOLD);
        } 

        System.out.println();
    
    }

    public static int readInt(){
        
        String input;

        do{

            input = inputScanner.nextLine();

            if(!TextUtils.isNumeric(input)){
                System.out.print("Il valore inserito non è un numero intero, inserire nuovamente.\nScelta: ");
            }

        }while (!TextUtils.isNumeric(input));

        return Integer.parseInt(input);

    }

    public static void pause(){
        System.out.print(" Premere invio per continuare...");
        TextUtils.getScanner().nextLine();
    }

    public static String generateUUID(String in){return UUID.nameUUIDFromBytes(in.getBytes()).toString();}

    public static String getBarColor(int intensity){
        
        switch (intensity) {
            case 0: 
            case 1:

                return TextUtils.RED;

            case 2: 
            case 3:
                
                return TextUtils.YELLOW;

            case 4:
                
                return TextUtils.GREEN;

            case 5:
                
                return TextUtils.CYAN;
        
            default:
                return TextUtils.WHITE;
        }
    }
}

