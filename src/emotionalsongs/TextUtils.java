package emotionalsongs;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;

/*
 * Progetto svolto da:
 * 
 * Della Chiesa Mattia 749904, Ateneo di Varese
 * 
 */

/**
 * La classe {@code TextUtils} fornisce funzionalità utili alla gestione, lettura e visualizzazione delle 
 * informazioni necessarie per il corretto funzionamento della piattaforma.
 *
 *  @author <a href="https://github.com/SpitefulCookie">Della Chiesa Mattia</a>
 *  
 */
public class TextUtils {

    private static boolean debug = false;
    private static boolean hasDisabledANSI = false;
    private static final Scanner inputScanner = new Scanner(System.in);
    private static String[] colorValues = new String [256];
    private static int charlength = 102; //137
    /**
     * Variabile che indica il numero di spazi vuoti presenti a sinistra del logo.
     */
    public static final int SPACESBEFORELOGO = 25; // 16
    
    private TextUtils() {super();}

    /*  
     * Le seguenti variabili non sono state poste come constanti in quanto, nel caso il terminale non abbia attivato il supporto per gli
     * ANSI escape codes (che necessita l'aggiunta di una DWORD nei registri di Windows), il loro valore verrà modificato ad una stringa vuota. 
     */

    /**
     * La variabile {@code RESET} contiene il codice ANSI per resettare il colore del terminale.
     */
    public static String RESET = "\033[38;5;7m\033[48;5;233m";
    /**
     * La variabile {@code RED} contiene il codice ANSI per colorare il testo del terminale in grigio.
     */
    public static String GREY = "\033[38;5;239m";
    /**
     * La variabile {@code RED} contiene il codice ANSI per colorare il testo del terminale in rosso.
     */
    public static String RED = "\033[38;5;160m";
    /**
     * La variabile {@code GREEN} contiene il codice ANSI per colorare il testo del terminale in verde.
     */
    public static String GREEN = "\033[38;5;112m";
    /**
     * La variabile {@code YELLOW} contiene il codice ANSI per colorare il testo del terminale in giallo.
     */
    public static String YELLOW = "\033[38;5;220m";
    /**
     * La variabile {@code BLUE} contiene il codice ANSI per colorare il testo del terminale in blu.
     */
    public static String BLUE = "\033[38;5;27m";
    /**
     * La variabile {@code PURPLE} contiene il codice ANSI per colorare il testo del terminale in viola.
     */
    public static String PURPLE = "\033[38;5;127m"; //\033[38;5;171m
    /**
     * La variabile {@code CYAN} contiene il codice ANSI per colorare il testo del terminale in ciano.
     */
    public static String CYAN = "\033[38;5;75m";
    /**
     * La variabile {@code WHITE} contiene il codice ANSI per colorare il testo del terminale in bianco.
     */
    public static String WHITE = "\033[38;5;15m";

    /**
     * Effettua il print del logo dell'applicazione seguito da un sottotitolo.
     * 
     * @param subtitle un oggetto di tipo {@code String} che contiene il sottotitolo desiderato.
     * 
     */
    protected static void printLogo(String subtitle){

        TextUtils.cls();
        if(TextUtils.getDebugStatus()){
            
            TextUtils.printDebug(EmotionalSongs.languageBundle.getString("DEBUG_CurrentlyUsedMemory") + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1000000 + " MB", false, false);
            
        }

        System.out.println();

        if(!hasDisabledANSI){
            System.out.println( StringUtils.repeat(" ", SPACESBEFORELOGO) +
                                TextUtils.fromRGB(255, 255, 255) +"═══ " +
                                TextUtils.fromRGB(200, 50, 200)+"E"+
                                TextUtils.fromRGB(45, 58, 165)+"M"+
                                TextUtils.fromRGB(9, 102, 201)+"O"+
                                TextUtils.fromRGB(70, 145, 129)+"T"+
                                TextUtils.fromRGB(131, 198, 56)+"I"+
                                TextUtils.fromRGB(191, 220, 40)+"O"+
                                TextUtils.fromRGB(250, 241, 24)+"N"+
                                TextUtils.fromRGB(246, 187, 43)+"A"+
                                TextUtils.fromRGB(242, 132, 62)+"L "+
                                TextUtils.fromRGB(236, 109, 48)+"S"+
                                TextUtils.fromRGB(230, 87, 34)+"O"+
                                TextUtils.fromRGB(217, 41, 6)+"N"+
                                TextUtils.fromRGB(197, 35, 63)+"G"+
                                TextUtils.fromRGB(176, 29, 120)+"S" + 
                                TextUtils.fromRGB(255, 255, 255) + " ═══" 
                                + TextUtils.RESET
            );
        } else{
            System.out.println(StringUtils.repeat(" ", SPACESBEFORELOGO) + "═══ EMOTIONAL SONGS ═══" );  
        }

        if(subtitle != null){ 
            System.out.println(TextUtils.BLUE + StringUtils.repeat(" ",SPACESBEFORELOGO+("═══ EMOTIONAL SONGS ═══".length()/2)-(subtitle.length()/2)) + subtitle.toUpperCase());
        } 

        System.out.println(TextUtils.RESET);
    
    }

    /***
     * Permette l'attivazione o la disattivazione della modalità di debug.
     * In questa modalità verranno visualizzate informazioni relative allo stato dell'applicazione durante l'esecuzione.
     * @param value un valore {@code boolean} che rappresenta lo stato desiderato.
     */
    public static void setDebug(boolean value){
    
        debug = value; 

        if(value){TextUtils.printDebug(EmotionalSongs.languageBundle.getString("DEBUG_DebugModeEnabled"), true, true);}
        else{TextUtils.printDebug(EmotionalSongs.languageBundle.getString("DEBUG_DebugModeDisabled"), true, true);}
        
    }

    /**
     * Permette di determinare se la funzione di debug risulta essere attivata.
     * 
     * @return {@code true} se la modalità di debug risulta essere attivata; {@code false} nel caso contrario.
     * 
     */
    protected static boolean getDebugStatus() {return debug;}

    /***
     * Simile al metodo {@code system("cls")} di C, questo metodo permette di svuotare la schermata visualizzata mediante l'utilizzo di codici ANSI.
     * 
     * <p>Con modalità di debug attivata, invece di svuotare la schermata, il metodo si limiterà a
     * visualizzare una notifica su schermo.
     * 
     */
    public static void cls() { 

        if(!hasDisabledANSI){ // Colore di background
            System.out.print("\033[48;5;233m"); 
        }else{
            System.out.print("\u001b[0m");
        }

        if(!debug){
            System.out.print("\033[H\033[2J");  
            System.out.flush();
        } else{
            TextUtils.printDebug("\n" + StringUtils.repeat("-", 100)+"\n\t\t\t\t" +EmotionalSongs.languageBundle.getString("DEBUG_ClearScreen") + "\n" + StringUtils.repeat("-", 100), false, true);
        }

    }  

    /***
     * Formatta la stringa ricevuta in input in modo tale da rendere le prime lettere di tutte le parole maiuscole.
     * <pre>
     * TextUtils.formatTitleCapital("lorem ipsum dolor sit amet.")  
     * =>  "Lorem Ipsum Dolor Sit Amet."
     * </pre>
     * @param str un oggetto di tipo {@code String} contenente la stringa da formattare.
     * @return un oggetto di tipo {@code String} contenente la stringa formattata; una stringa vuota con {@code str} avente valore nullo o vuoto.
     */
    public static String formatTitleCapital(String str){

        if(str == null || str.length() == 0){return"";}

        String [] words = str.split(" ");
        StringBuilder result = new StringBuilder();

        for(String s : words){

            result.append(s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase());
            result.append(" ");

        }

        return result.toString().substring(0, (result.length()-1));

    }

    /***
     * Formatta la stringa ricevuta in input in modo tale da rimuovere tutte le virgole presenti all'interno.
     * <pre>
     * TextUtils.stripCommas("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")  
     * =>  "Lorem ipsum dolor sit amet consectetur adipiscing elit."
     * </pre>
     * @param in un oggetto di tipo {@code String} contenente la stringa da formattare.
     * @return un oggetto di tipo {@code String} contenente la stringa formattata; una stringa vuota con {@code str} avente valore nullo o vuoto.
     */
    protected static String stripCommas(String in){

        if(in == null || in.length() == 0){return"";}
        
        String result = in.replace(",", " ");

        return result.replace("  "," ");

    }

    /**
	 * Validates a regular CF.
     * Source: <a href = "http://www.icosaedro.it/cf-pi/vedi-codice.cgi?f=cf-java.txt"> www.icosaedro.it </a>
     * 
	 * @param cf Normalized, 16 characters CF.
	 * @return {@code true} if valid, or {@code false} if the CF must be rejected.
     * @author Umberto Salsi salsi@icosaedro.it
     *
	 */
    public static boolean isValidCF(String cf){ 

		if(!cf.matches("^[0-9A-Z]{16}$")){
            TextUtils.printDebug(EmotionalSongs.languageBundle.getString("DEBUG_CFContainsInvalidCharacters"), true, true);
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
            TextUtils.printDebug(EmotionalSongs.languageBundle.getString("DEBUG_CFHasFailedChecksum"), true, true);
            return false;
        }

        TextUtils.printDebug(EmotionalSongs.languageBundle.getString("DEBUG_CFIsValid"), true, true);

		return true;

	}

    /**
     * Verifica se la stringa fornita in input risulta essere vuota, nulla o consistente solo di spazi bianchi.
     * 
     * <pre>
     * TextUtils.isEmptyString(null)             = true
     *TextUtils.isEmptyString(null, "foo")      = false
     *TextUtils.isEmptyString(null, null)       = true
     *TextUtils.isEmptyString("", "bar")        = false
     *TextUtils.isEmptyString("bob", "")        = false
     *TextUtils.isEmptyString("  bob  ", null)  = false
     *TextUtils.isEmptyString(" ", "bar")       = false
     *TextUtils.isEmptyString("foo", "bar")     = false
     *TextUtils.isEmptyString(new String[] {})  = true
     * </pre>
     *
     * @param input un oggetto di tipo {@code String} contenente la stringa da valutare.
     * @return un valore {@code boolean} che identifica se la stringa risulta essere vuota o meno.
     * @see StringUtils#isAllBlank(CharSequence...) StringUtils.isAllBlank(CharSequence...)
     */
    public static boolean isEmptyString(String input){return StringUtils.isAllBlank(input);}

    /**
     * Verifica se la stringa fornita in input è costituita interamente da numeri.
     *
     * @param input un oggetto di tipo {@code String} contenente la stringa da valutare.
     * @return un valore {@code boolean} che identifica se la stringa fornita risulta essere numerica o meno. Con stringherappresentanti valori superiori a {@code Integer.MAX_VALUE} verrà restituito il valore {@code false}.
     * @see StringUtils#isNumeric(CharSequence) StringUtils.isNumeric
     */
    public static boolean isNumeric(String input){

        try{

            /* 
            * Con input di grandi dimensioni (superiori a Integer.MAX_VALUE come 1832471894614) Integer.parseInt(String) solleva una NumberFormatException.
            * https://stackoverflow.com/questions/8422818/java-integer-parseint-not-working-for-large-numbers
            * Per evitare che questo possa dare problemi all'interno dell'applicazione, si è deciso di porre una chiamata a Integer.parseInt() all'interno
            * di un blocco try-catch.
            */
            Integer.parseInt(input);

            return StringUtils.isNumeric(input);

        }catch (NumberFormatException e){ 
            return false;
        }

        
    }
    
    /**
     * Effettua il print su schermo di un messaggio di debug. Questo verrà visualizzato solamente se la modalità di debug è stata attivata. 
     *
     * @param msg un oggetto di tipo {@code String} contenente il messaggio di debug da visualizzare. 
     * @param printDebugTag un valore {@code boolean} che determina se si desidera includere un tag "[DEBUG]" all'inizio del messaggio.
     * Questo tag verrà sempre incluso nel messaggio se {@code hasDisabledANSI} assume valore {@code true}.
     * 
     * @param includeSpaces un valore {@code boolean} che determina se si desidera includere uno spazio all'inizio del messaggio.
     * 
     */
    public static void printDebug(String msg, boolean printDebugTag, boolean includeSpaces){

        System.out.print(TextUtils.GREEN);
       
        if(debug && printDebugTag)  {System.out.println(" [DEBUG] " + msg + TextUtils.RESET);}
        else if(debug && includeSpaces) {System.out.println("\t "+ msg + TextUtils.RESET);}
        else if(debug){System.out.println(" "+ msg + TextUtils.RESET);}

        System.out.print(TextUtils.RESET);
    }

    /**
     * Effettua il print su schermo di un messaggio di errore. 
     *
     * @param msg un oggetto di tipo {@code String} contenente l'errore da visualizzare. 
     * @param newLine un valore {@code boolean} che determina se si desidera avere il cursore posizionato su una nuova riga dopo aver effettuato il print. 
     * 
     */
    public static void printErrorMessage(String msg, boolean newLine) {
        
        System.out.print(" [" + TextUtils.RED + EmotionalSongs.languageBundle.getString("ERROR_Tag") + TextUtils.RESET + "] " + msg);
        if(newLine){System.out.println();}

    }
    
    /**
     * Permette all'utente di inserire una risposta ad una domanda di natura binaria (Si/No). <p>
     * Supporta risposte sia in Italiano che in Inglese.
     * @param choiceTag un valore {@code boolean} che determina se si desidera visualizzare il tag "Scelta:" (o "Choice:") alla fine del messaggio.
     * @return un valore {@code boolean} che identifica una risposta positiva o negativa. 
     * 
     */
    public static boolean readYesOrNo(boolean choiceTag) {

        String ans;

        if(choiceTag) System.out.print(EmotionalSongs.languageBundle.getString("Choice"));
        
        do{
            
            ans = inputScanner.nextLine();

            if(ans.equalsIgnoreCase("s") || ans.equalsIgnoreCase("y")){

                return true;

            } else if(ans.equalsIgnoreCase("n")){

                return false;

            } else{TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_InvalidInputPrompt"), false);}

        }while (true);
        
    }

    /**
     * Permette all'utente di inserire un valore numerico.
     * 
     * @param choiceTag un valore {@code boolean} che determina se si desidera visualizzare il tag "Scelta:" (o "Choice:") alla fine del messaggio.
     * @return un valore {@code int} che rappresenta il numero inserito. 
     * 
     */
    public static int readInt(boolean choiceTag){
        
        String input;

        if(choiceTag) System.out.print(EmotionalSongs.languageBundle.getString("Choice"));

        do{

            input = inputScanner.nextLine();

            if(!TextUtils.isNumeric(input)){
                TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_InvalidIntegerInputPrompt"), false);
            }

        }while (!TextUtils.isNumeric(input));

        return Integer.parseInt(input);

    }

    /**
     * Permette all'utente di inserire un valore numerico contenuto all'interno di un range di valori.
     * 
     * @param choiceTag un valore {@code boolean} che determina se si desidera visualizzare il tag "Scelta:" (o "Choice:") alla fine del messaggio.
     * @param lowerBound il limite inferiore (inclusivo)
     * @param higherBound il limite superiore (inclusivo)
     * 
     * @return un valore {@code int} che rappresenta il numero inserito. 
     * 
     */
    public static int readInt(int lowerBound, int higherBound, boolean choiceTag) {

        String input;

        if(choiceTag) System.out.print(EmotionalSongs.languageBundle.getString("Choice"));

        while (true){

            input = inputScanner.nextLine();

            if(TextUtils.isNumeric(input)){
                int value = Integer.parseInt(input);

                if(value<lowerBound || value> higherBound){

                    TextUtils.printErrorMessage(String.format(EmotionalSongs.languageBundle.getString("ERROR_ValueMustBeInsideIntervalPrompt"), lowerBound, higherBound), false);

                } else{

                    return Integer.parseInt(input);

                }

            } else {

                TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_InvalidIntegerInputPrompt"), false);

            }

        }

    }

    /***
     * Simile al metodo {@code system("pause")} di C, questo metodo permette di fermare l'esecuzione dell'applicazione finchè non viene ricevuto un input da parte dell'utente.
     * 
     */
    public static void pause(){
        System.out.print(EmotionalSongs.languageBundle.getString("PressEnterToContinue"));
        TextUtils.inputScanner.nextLine();
    }

    /**
     * Permette di generare un identificatore univoco universale (Type 3 UUID) di 128bit a partire dalla stringa fornita in input
     * @param in la stringa che si desidera rappresentare tramite UUID.
     * @return un oggetto di tipo {@code String} contentente l'etichetta generata. 
     * 
     */
    public static String generateUUID(String in){return UUID.nameUUIDFromBytes(in.getBytes()).toString();}

    /**
     * Restituisce un'oggetto di tipo {@code String} contenente un codice ANSI che varia a fronte del valore ricevuto in input.
     * <p> Il codice restituito permette alle funzioni di print di effettuare la stampa a schermo di caratteri colorati.
     * <pre>
     * TextUtils.getBarColor(0) => Bianco
     *TextUtils.getBarColor(1) => Rosso
     *TextUtils.getBarColor(2) => Giallo
     *TextUtils.getBarColor(3) => Giallo
     *TextUtils.getBarColor(4) => Verde
     *TextUtils.getBarColor(5) => Ciano 
     * </pre>
     * Con qualsiasi altro valore verra sempre restituito il colore bianco.
     * @param intensity un valore {@code int} che rappresenta l'intensità.
     * @return un valore {@code int} che rappresenta il numero inserito. 
     * 
     */
    protected static String getBarColor(int intensity){
        if(!TextUtils.hasDisabledANSI){
            switch (intensity) {
                case 0: 
                    return TextUtils.RESET;
                case 1:
                    return TextUtils.fromRGB(102, 0, 0);
                case 2: 
                    return TextUtils.fromRGB(204, 51, 0);
                case 3: 
                    return TextUtils.fromRGB(153, 153, 0);
                case 4: 
                    return TextUtils.fromRGB(153, 204, 0);
                case 5:
                    return TextUtils.fromRGB(102, 204, 0);
                default:
                    return TextUtils.RESET;
            }
        }else{
            return "";
        }
    }

    /**
     * Permette all'utente di inserire una stringa.
     * 
     * @param choiceTag un valore {@code boolean} che determina se si desidera visualizzare il tag "Scelta:" (o "Choice:") alla fine del messaggio.
     * @return un oggetto di tipo {@code String} contenente l'input utente. 
     * 
     */
    public static String readStringInput(boolean choiceTag){

        String input;

        if(choiceTag) System.out.print(EmotionalSongs.languageBundle.getString("Choice"));

        do{

            input = inputScanner.nextLine();

            if(TextUtils.isEmptyString(input)){

                TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_InvalidInputPrompt"), false);

            }

        }while(TextUtils.isEmptyString(input));

        return input;

    }

    /**
     * Permette di visualizzare, mediante elenco, parte delle playlist dell'utente.
     * @param pagina una valore {@code int} che rappresenta il numero della pagina da visualizzare. Ogni pagina contiene al massimo nove risultati.
     * @param datiIn un oggetto di tipo {@code Map<String, Playlist>} che contiene le playlist dell'utente. La chiave per la mappatura è il nome della playlist.
     * 
     */
    protected static void elencaRisultati(int pagina, Map<String, Playlist> datiIn){

        String[] dati = datiIn.keySet().toArray(new String[datiIn.size()]);
        
        for (int i = 0; i<9 && (i+(pagina*9))<dati.length; i++) {
            System.out.println(StringUtils.repeat(" ", TextUtils.SPACESBEFORELOGO-5) + " [" + TextUtils.BLUE + (i+1) + TextUtils.RESET + "] " + dati[i+(pagina*9)]);
        }
    
        //System.out.print("\n\t\t   "+ +"\n\n");
        System.out.print("\n"+TextUtils.centerString(String.format(EmotionalSongs.languageBundle.getString("PageCounter"), (pagina+1) ,(int)Math.ceil(dati.length/9.00))));
        System.out.print("\n\n");
    }   

     /**
     * Permette di visualizzare gli oggetti desiderati tramite chiamata al metodo {@code toString()} e di elencarli in una lista numerata da 1 a 9.
     * @param <E> una classe che implementa l'interfaccia List.
     * @param pagina una valore {@code int} che rappresenta il numero della pagina da visualizzare. Ogni pagina contiene al massimo nove risultati.
     * @param dati un oggetto di tipo {@code List<E>} che contiene una lista di elementi da visualizzare.
     * 
     */
    protected static <E> void elencaRisultati(int pagina, List<E> dati){

        for (int i = 0 ; i<9 && (i+(pagina*9))<dati.size(); i++) {

            if(dati.get(i)!=null){
                System.out.println(StringUtils.repeat(" ", TextUtils.SPACESBEFORELOGO-10) + " [" + TextUtils.BLUE + (i+1) + TextUtils.RESET + "] " + dati.get(i+(pagina*9)).toString());
            } else{
                dati.remove(i);
                i--;
                //System.out.println("\t   " +TextUtils.RED+"[" +(i+1) +"] " + EmotionalSongs.languageBundle.getString("ERROR_CannotRetrieveElementFromRepository") + TextUtils.RESET); //limite: una data canzone non è più presente nella repository
            }
        }
    
        System.out.print("\n"+TextUtils.centerString(String.format(EmotionalSongs.languageBundle.getString("PageCounter"), (pagina+1) ,(int)Math.ceil((dati.size()/9.00))) +"\n\n"));

    }

    /**
     * Calcola la dissimilarità tra due stringhe in maniera simile alla distanza di Hemming senza case sensitive.
     * <p>Il metodo compara due stringhe tra di loro: una stringa "corretta", che si assume essere priva di errori, 
     * ed una stringa "da controllare". In base a quanti caratteri l'ultima stringa differisce dalla prima, verrà restituito un valore intero.
     * 
     * <pre>
     * TextUtils.characterDissimilarity("", "Lorem Ipsum") => Integer.MAX_VALUE
     *TextUtils.characterDissimilarity("Lorem Ipsum", "") => 11
     *TextUtils.characterDissimilarity("Lorem Ipsum", "Lorem Ipusum") => 1
     *                                                         ^
     *TextUtils.characterDissimilarity("Lorem Ipsum", "Dolor sit amet") => 10
     *                                                 ^^   ^^^^^^^ ^
     * </pre>
     * 
     * @param correctString un oggetto di tipo {@code String} contenente la stringa priva di errori
     * @param stringToBeChecked un oggetto di tipo {@code String} contenente la stringa da verificare
     * 
     * @return un valore {@code int} che rappresenta il numero di caratteri estranei presenti all'interno della seconda stringa. 
     * 
     */
    protected static int characterDissimilarity(String correctString, String stringToBeChecked){

        /*
         * Questo metodo opera in maniera simile alla distanza di Hemming con l'eccezione di non essere case sensitive 
         * ed essere in grado di processare stringhe di diversa dimensione. 
         * Inoltre, eseguito su stringhe simili come "AC/DC" e "acdc", restituirà come valore 1 invece del valore 3
         * (assumendo che, alla stringa più breve, venga accodato uno spazio per renderla di ugual dimensione alla prima).
         * 
         */

        if(StringUtils.isEmpty(correctString)){return Integer.MAX_VALUE;} // Viene restituito MAX_VALUE in quanto nell'applicazione viene effettuato un confronto di natura <= quindi valori come -1 causerebbero un falso positivo
        if(StringUtils.isEmpty(stringToBeChecked)){return correctString.length();}

        else if(correctString.equalsIgnoreCase(stringToBeChecked)){return 0;} // Le stringhe sono identiche; differiscono di 0 caratteri.
        
        else{

            int i = 0; 
            int j = 0;
            int differences = 0;

            while(j<stringToBeChecked.length() && i<correctString.length()){
                
                if(correctString.toLowerCase().charAt(i)!=stringToBeChecked.toLowerCase().charAt(j)){
                    
                    differences++;

                    if(correctString.length()>stringToBeChecked.length()){
                        i++;
                        j--;
                    }

                }else{ 
                    i++;
                }

                j++;

            }

            return differences;
        }

    }
   
    /**
     * Spezza la stringa fornita in input ogni {@code n} caratteri utilizzando il divisorio passato come parametro. 
     * Il numero di caratteri presenti in ogni sottostringa può essere modificato tramite il metodo: <pre>TextUtils.setCharSplitLength()</pre>
     * <p>La suddivisione della stringa avverà all'ultimo spazio vuoto ({@code \u0020}) rilevato prima dell'{@code n-esimo} carattere.
     * @param inputString un oggetto di tipo {@code String} che descrive la stringa da spezzare.
     * @param divider un oggetto di tipo {@code String} da porre tra due sottostringhe.
     * @param startWithDivider un valore {@code boolean} che definisce se si desidera che la stringa risultante inizi con il divisorio.
     * @return un oggetto di tipo {@code String} contenente la stringa suddivisa ogni {@code n} caratteri mediante il valore fornito tramite il parametro {@code divider}.
     */
    public static String splitLongStringInput(String inputString, String divider, boolean startWithDivider){

        /*
         * Questo metodo non è perfetto in quanto considera '\n' come uno spazio piuttosto che l'inizio di una nuova riga.
         * Ciò può portare a situazioni in cui venga stampata una linea di testo contentente poche parole.
         * Similmente, la presenza di codici ANSI all'interno della stringa passata come parametro, va a influenzare la suddivisione delle righe
         * rendendo la stringa risultante più breve di quanto previsto.
         * Tuttavia, poiché questa funzione esiste puramente per motivi estetici, si è deciso di non modificare il codice.
         */

        if(inputString.length()<= charlength){
            if(startWithDivider){return divider+inputString;}
            else{return inputString;}
        }

        StringBuilder sb = new StringBuilder();

        int[] lastSpace = new int[(int)Math.ceil((double)inputString.length()/charlength)];
        Arrays.fill(lastSpace, -1);

        // Di seguito viene determinato dove si trovano gli spazi vuoti all'interno della stringa, utili per effettuare la suddivisione.
        
        for(int i = 0; i<inputString.length() ; i++){

            if(inputString.charAt(i) == ' ' || inputString.charAt(i) == '\n'){lastSpace[i/charlength] = i+1;}

        }

        lastSpace[(int)Math.ceil((double)inputString.length()/charlength)-1] = inputString.length();

        if(startWithDivider){sb.append(divider);}

        // Viene costruita la stringa risultante

        for (int arrayIdx = 0, idxLastAppend = 0; arrayIdx<lastSpace.length; arrayIdx++){
            
            if(lastSpace[arrayIdx] == -1){ //gestisce il caso in cui l'indice è pari a -1 (stringa priva di spazi negli n caratteri)
                sb.append(inputString.substring(idxLastAppend, (arrayIdx+1)*charlength));
                sb.append("-");
            
                idxLastAppend = (arrayIdx+1)*charlength;

            }else{
                sb.append(inputString.substring(idxLastAppend, lastSpace[arrayIdx]));
                idxLastAppend = lastSpace[arrayIdx];
            }

            if(arrayIdx != (lastSpace.length-1)){
                sb.append(divider);
            }
            
        }
        
        return sb.toString();

    }

    /**
     * Permette di modificare il numero massimo di caratteri presenti in ogni sottostringa all'interno del metodo:
     * <pre>TextUtils.splitLongStringInput()</pre>
     * @param value un valore {@code int} che rappresenta il numero di caratteri desiderato.
     * @return un valore {@code boolean} che determina se la modifica è avvenuta con successo.
     */
    public static boolean setCharSplitLength(int value){

        if(value>0 && value < 500){

            TextUtils.charlength = value;
            return true;

        } else{ return false; } // In una applicazione reale si potrebbe sollevare una NumberFormatException
        
    }
    
    /**
     * Permmette di selezionare un elemento all'interno di una lista di oggetti.
     * @param <E> una classe che implementa l'interfaccia {@code List<E>}.
     * @param dati un oggetto di tipo {@code List<£>} che contiene la lista di dati da cui scegliere.
     * @return un valore {@code int} che rappresenta l'indice dell'oggetto desiderato all'interno della lista.
     */
    protected static <E> int selezionaRisultati(List<E> dati) {

        int pagina = 0;
        int numeroSelezionato = -1;
        int upperBoundary = 9;

        if(dati.size()<9){ upperBoundary = dati.size();}
    
        while(numeroSelezionato<1 || numeroSelezionato > upperBoundary){
            
            if(dati.size()-(pagina*9)<9){
                upperBoundary = dati.size()-(pagina*9);
            }

            TextUtils.printLogo(EmotionalSongs.languageBundle.getString("ViewPlaylistSubtitle"));
        
            TextUtils.elencaRisultati(pagina, dati);
    
            System.out.print(EmotionalSongs.languageBundle.getString("SongSelection"));
    
            System.out.print(EmotionalSongs.languageBundle.getString("Choice"));
    
            String input = inputScanner.nextLine();
    
            if(TextUtils.isNumeric(input)){

                try{
        
                    numeroSelezionato = Integer.parseInt(input);

                    if(numeroSelezionato < 0 || numeroSelezionato > upperBoundary){
                        TextUtils.printErrorMessage(String.format(EmotionalSongs.languageBundle.getString("ERROR_ValueMustBeInsideInterval"),1,upperBoundary), false);
                        TextUtils.pause();
                    }

                } catch (NumberFormatException e){
                   
                    /* 
                     * Con input di grandi dimensioni (superiori a Integer.MAX_VALUE come 1832471894614) Integer.parseInt(String) solleva una NumberFormatException.
                     * https://stackoverflow.com/questions/8422818/java-integer-parseint-not-working-for-large-numbers
                     * 
                     */

                    TextUtils.printErrorMessage(String.format(EmotionalSongs.languageBundle.getString("ERROR_ValueMustBeInsideIntervalPrompt"),1,upperBoundary), false);
                    TextUtils.pause();

                }
            
            }else if(input.equalsIgnoreCase("previous")){
    
                if(pagina>0){pagina--;} else{TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_NoPreviousPages"), true); TextUtils.pause();}
                
    
            } else if(input.equalsIgnoreCase("next")){
    
                if(pagina == (Math.ceil((dati.size()/9.00))-1)){TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_NoNextPages"), true); TextUtils.pause();} else{pagina++;}
    
    
            } else if(input.equalsIgnoreCase("cancel")){

                return -1;

            }else {TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_UnrecognizedCommand"), true); TextUtils.pause();}
            
        }

        switch (numeroSelezionato) {

            case (1): return (1+(9*pagina))-1;

            case (2): return (2+(9*pagina))-1;

            case (3): return (3+(9*pagina))-1;

            case (4): return (4+(9*pagina))-1;

            case (5): return (5+(9*pagina))-1;

            case (6): return (6+(9*pagina))-1;

            case (7): return (7+(9*pagina))-1;

            case (8): return (8+(9*pagina))-1;

            case (9): return (9+(9*pagina))-1;
            
            default: return -1;

        } 

    }

    /**
     * Modifica tutti i codici ANSI utilizzati per colorare il testo in stringhe vuote, evitando così che il testo risulti illeggibile su 
     * terminali non aventi la chiave di registro {@code Computer\HKEY_CURRENT_USER\Console\VirtualTerminalLevel} settata a {@code 1}.
     * @param value un valore {@code boolean} che, con valore {@code true} indica il mancato supporto di questa funzionalità. 
     * 
     */
    protected static void disableANSIColorCodes(boolean value){

        /*
         * Questo problema si potrebbe risolvere abilitando questa funzionalità (disattivata di default) direttamente dall'applicazione 
         * tuttavia questo richiederebbe l'esecuzione di comandi BATCH (https://ss64.com/nt/reg.html) oppure l'utilizzo di librerie esterne come 
         * Ansicon (https://github.com/adoxa/ansicon) che aggiungerebbero uno strato di complessità non necessario (https://www.nag.com/industryArticles/CallingCLibraryRoutinesfromJava.pdf).
         * Pertanto si è deciso di utilizzare un approccio che tende a circumnavigare il problema, lasciando all'utente la scelta di utilizzare la piattaforma priva di supporto ansi,
         * o se lo preferisce, di abilitare questa funzionalità di Windows manualmente. 
         * 
         * 
         */

        if(value){

            RESET = "";
            GREY = ""; 
            RED = ""; 
            GREEN = ""; 
            YELLOW = ""; 
            BLUE = ""; 
            PURPLE = ""; 
            CYAN = ""; 
            WHITE = ""; 

            hasDisabledANSI = true;

        } else{

            initColorRGB();

            RED = TextUtils.fromRGB(217, 41, 6);
            GREEN = TextUtils.fromRGB(0, 153, 0);
            YELLOW = TextUtils.fromRGB(244, 184, 25);
            BLUE = TextUtils.fromRGB(39, 126, 255);
            PURPLE = TextUtils.fromRGB(200, 50, 200);
            CYAN = TextUtils.fromRGB(0, 184, 234);
            WHITE = TextUtils.fromRGB(255, 255, 255);

        }

    }

    /**
     * Inizializza gli strumenti necessari per permettere l'utilizzo di codici ANSI volti a modificare il colore di foreground del testo all'interno dell'applicazione.
     */
    protected static void initColorRGB(){

        if(!hasDisabledANSI){

            for(var i = 0; i<256; i++){
                colorValues[i] = "\033[38;5;"+(i)+"m";
            }

            colorValues = Arrays.copyOfRange(colorValues, 16, 232);

        } else{

            Arrays.fill(colorValues, "");

        }
    }

    /**
     * Restituisce un codice ANSI che permette di cambiare il colore del testo stampato sul terminale.
     * <p>L'applicazione, permette di effettuare il print di stringhe di testo con 216 colori,
     * purchè la chiave di registro di Windows {@code HKEY_CURRENT_USER\Console\VirtualTerminalLevel} sia impostata a {@code 1}.
     * @param r la quantità di rosso espressa tramite un valore compreso tra {@code 0} e {@code 255} (inclusivo)
     * @param g la quantità di verde espressa tramite un valore compreso tra {@code 0} e {@code 255} (inclusivo)
     * @param b la quantità di blu espressa tramite un valore compreso tra {@code 0} e {@code 255} (inclusivo)
     * @return un oggetto di tipo {@code String} che rappresenta il codice ANSI più vicino al valori RGB fornito 
     *         oppure una stringa vuota se i parametri forniti non risultano compresi tra {@code 0} e {@code 255}
     */
    protected static String fromRGB(int r, int g, int b){

        //adds support for 217 colours 

        if(!hasDisabledANSI && (r>=0 && r<=255) && (g>=0 && g<=255) && (b>=0 && b<=255)){

            int[] values = new int[3];
            
            // (int)Math.round(r*5.0/255) sarebbe una formula più adatta ma si è preferito lasciare la formula precedente puramente per motivi estetici
            values[0] = r*5/255;
            values[1] = g*5/255;
            values[2] = b*5/255;

            return colorValues[36*values[0] + 6*values[1] + values[2]];

        } else{ return "";}

    }

    /**
     * Funzione di debug volta ad effettuare il test dei colori mediante un print su schermo. 
     * <p>Ogni elemento stampato consiste in 3 cifre da 0 a 5 che rappresentano la quantità di un colore tra rosso, verde e blu:
     * <pre>
     *500 => "5 unità di rosso, 0 di verde e 0 di blu. Il colore risultante sarà Rosso"
     *000 => "0 unità di rosso, 0 di verde e 0 di blu. Il colore risultante sarà Nero"
     *505 => "5 unità di rosso, 0 di verde e 5 di blu. Il colore risultante sarà Fucsia"
     *143 => "1 unità di rosso, 4 di verde e 3 di blu. Il colore risultante sarà Turchese"
     * </pre>
     * @see <a href = https://imgur.com/a/qkwhXLw> Screenshot dei colori effettivi</a>
     */
    protected static void testColorOutput(){
        
        for(int r = 0; r<6; r++){
            for(int g = 0; g<6; g++){
                for(int b = 0; b<6; b++){

                    System.out.print(colorValues[36*r + 6*g + b]+r+g+b+" ");

                }
                System.out.println();
            }
        }

        // DEBUG
        System.out.println(); System.out.println();
        for(var i = 232; i<256; i++){
            System.out.print("\033[38;5;"+(i)+"m " + (i) + "");
        }
 
    }

    /**
     * Restituisce un valore booleano che descrive se l'utente ha disabilitato i codici ANSI. 
     * @return un valore {@code boolean} descrive se l'utente ha disabilitato i codici ANSI.
     * 
     */
    protected static boolean isANSIDisabled(){return hasDisabledANSI;}

    /**
     * Restituisce una stringa centrata con il logo dell'applicazione. 
     * @param testoDaCentrare un oggetto di tipèo {@code String} contenente il testo da centrare.
     * @return un oggetto di tipo {@code String} contenente una stringa la cui metà risulta allineata con la metà del logo dell'applicazione.
     * 
     */
    public static String centerString(String testoDaCentrare){

        /*
         * |                ═══ EMOTIONAL SONGS ═══
         * |                   DETTAGLI  CANZONE
         * |
         * | Non sono presenti emozioni registrate per la canzone:
         * |             
         * |              Night Prowler - AC/DC (1979)
         * |
         * 
         */

        return StringUtils.repeat(" ",SPACESBEFORELOGO+("═══ EMOTIONAL SONGS ═══".length()/2)-(testoDaCentrare.length()/2)) + testoDaCentrare;

    }

    /**
     * Restituisce lo scanner presente all'interno della classe. 
     * @return un oggetto di tipo {@code Scanner}
     */
    protected static Scanner getScanner(){return inputScanner;}

    /**
     * Genera un set (un'insieme) di parole a partire da un array di Stringhe. 
     * Per definizione di insieme, il set di parole generato non contiene elementi duplicati.
     * @param words un oggetto di tipo {@code String[]} contenente le parole da inserire nel set.
     * @return un oggetto di tipo {@code Set} contenente l'insieme delle parole.
     */
    public static Set<String> generateWordSet(String[] words) {

        Set<String> wordSet = new LinkedHashSet<>();

        for (String word : words) {wordSet.add(word);}

        return wordSet;

    }

    /**
     * Restituisce un valore {@code boolean} che determina se l'array passato come parametro risulta contenere esclusivamente stringhe vuote.
     * @param commentiCanzone un oggetto di tipo {@code String[]} contenente i commenti della canzone.
     * @return un valore {@code boolean} che descrive se l'array passato come parametro contiene solo stringhe vuote.
     */
    public static boolean isEmptyArray(String[] commentiCanzone) {
        for(var s : commentiCanzone){
            if(!TextUtils.isEmptyString(s)){
                return false;
            }
        }

        return true;
    }

}


