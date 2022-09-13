package emotionalsongs;

import java.lang.management.ManagementFactory;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

/*
 * Progetto svolto da:
 * 
 * Della Chiesa Mattia 749904, Ateneo di Varese
 * 
 */

/**
 * La classe principale dell'applicazione.
*/
public class EmotionalSongs{

    private static Utente loggedUser = null;

    /**
     * Un oggetto di tipo {@code ResourceBundle} contenente tutte le stringhe dell'applicazione nel linguaggio desiderato.
     */
    public static ResourceBundle languageBundle;

    /**
     * Un oggetto di tipo {@code String} che determina quale {@code ResourceBundle} è attualemente in uso.
     */
    protected static String chosenLanguageBundle = "emotionalsongs.Language";

    /**
     * Il metodo main dell'applicazione.
     * @param args gli argomenti forniti al momento dell'esecuzione
     */
    public static void main(String[] args) {  
        
        /*
         * Ottiene il PID assegnato all'applicazione, questo verrà utilizzato per chiudere automaticamente il prompt dei comandi una volta terminata l'esecuzione dell'applicazione.
         */
        String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        boolean exit = false;

        String input;

        TextUtils.cls();

        languageBundle = ResourceBundle.getBundle(chosenLanguageBundle);

        if(args.length>0){
            EmotionalSongs.parseArgs(args);
        }

        System.out.println( "\033[38;5;231m\n" +StringUtils.repeat(" ", TextUtils.SPACESBEFORELOGO)+"═══ " +
                            "\033[38;5;127m"+"E"+
                            "\033[38;5;25m"+"M"+
                            "\033[38;5;25m"+"O"+
                            "\033[38;5;66m"+"T"+
                            "\033[38;5;107m"+"I"+
                            "\033[38;5;148m"+"O"+
                            "\033[38;5;184m"+"N"+
                            "\033[38;5;178m"+"A"+
                            "\033[38;5;173m"+"L "+
                            "\033[38;5;172m"+"S"+
                            "\033[38;5;166m"+"O"+
                            "\033[38;5;160m"+"N"+
                            "\033[38;5;125m"+"G"+
                            "\033[38;5;126m"+"S"+
                            "\033[38;5;231m"+" ═══\n"+
                            TextUtils.RESET
        );

        // Aggiungere un messaggio che spiega il motivo?

        System.out.print(EmotionalSongs.languageBundle.getString("VerifyANSISupport"));
        
        TextUtils.disableANSIColorCodes(TextUtils.readYesOrNo(false));

        if(TextUtils.isANSIDisabled()){ // Se il terminale non supporta i codici ANSI sarà necessario ottenere un nuovo languagebundle privo di colori.
            languageBundle = ResourceBundle.getBundle(chosenLanguageBundle);
        }
        
        //=================== DEBUG AREA =================

        //TextUtils.setDebug(true);

        //================= END DEBUG AREA =================

        TextUtils.printDebug("PID: "+ pid, true, true);
        TextUtils.printDebug("Current path: "+System.getProperty("user.dir"), true, true);

        SongRepositoryManager.build();
        EmotionsRepositoryManager.build(); 

        if(TextUtils.getDebugStatus()){
            TextUtils.pause();
        }

        exit = false;

        do{

            EmotionalSongs.printMenu();

            System.out.print(languageBundle.getString("MenuSelection"));
            
            input = TextUtils.readStringInput(true);

            try{

                if(TextUtils.isNumeric(input)){

                    switch(Integer.parseInt(input)){
                        case(1):
                            EmotionalSongs.printInformazioni();
                            break;
                        case(2):

                            SongRepositoryManager.consultaRepository();

                            break;

                        case(3):

                            if(loggedUser != null){
                                
                                loggedUser = null;

                                PlaylistManager.clearPlaylists();

                            }else{

                                loggedUser = UserAuthenticationManager.loginUtente();

                                if(loggedUser == null){

                                    TextUtils.printErrorMessage(languageBundle.getString("IncorrectLogin"), true);

                                    TextUtils.pause();
                                    break;
                                    
                                }else if(loggedUser.getCodiceFiscale().equals("-1")){

                                    /*
                                    * Vedi commenti in UserAuthenticationManager.loginUtente()
                                    */

                                    loggedUser = null;

                                    break;

                                }else{

                                    /*
                                    * Il login è avvenuto con successo.
                                    */
                                    long start = System.currentTimeMillis();
                                    if(PlaylistManager.build()){
                                        TextUtils.printDebug(languageBundle.getString("DEBUG_PlaylistBuildSuccess"), true, false);
                                        TextUtils.printDebug(languageBundle.getString("DEBUG_ProcessDuration") + (System.currentTimeMillis()-start) + "ms", false, true);
                                    }

                                }

                            } 

                            break;

                        case (4):

                            if(loggedUser==null){loggedUser = UserAuthenticationManager.Registrazione();}

                            break;
                        
                        case (5):

                            if(loggedUser!= null){

                                do{

                                    if(loggedUser!=null){

                                        printPlaylistMenu();

                                        System.out.print(languageBundle.getString("OptionSelection"));
                                        input = TextUtils.readStringInput(true);
                                        
                                        if(TextUtils.isNumeric(input)){

                                            switch(Integer.parseInt(input)){

                                                case 1:
                                                    
                                                    Playlist.registraPlaylist();
                                                    break;

                                                case 2:

                                                    PlaylistManager.consultaPlaylists();
                                                    break;

                                                default:

                                                    TextUtils.printErrorMessage(languageBundle.getString("ERROR_InvalidInput"), true);
                                                    TextUtils.pause();
                                                    break;

                                            }
                                            
                                        } else if(input.equalsIgnoreCase("cancel")){

                                            break;
                            
                                        } else {TextUtils.printErrorMessage(languageBundle.getString("ERROR_UnrecognizedCommand"), true); TextUtils.pause();}
                                        
                                    } 
                            
                                } while(!input.equalsIgnoreCase("cancel"));

                            } else{

                                TextUtils.printErrorMessage(languageBundle.getString("ERROR_InvalidInput"), false);TextUtils.pause();

                            }

                            break;

                        default: //main switch

                            TextUtils.printErrorMessage(languageBundle.getString("ERROR_InvalidInput"), false);TextUtils.pause();

                            break;

                    
                    }

                } else if(input.equalsIgnoreCase("exit")){

                        TextUtils.printLogo("\n"+ TextUtils.centerString(languageBundle.getString("GoodbyeMessage")));
                        exit = true;

                } else if(input.toLowerCase().startsWith("change language")){

                    TextUtils.printLogo(EmotionalSongs.languageBundle.getString("LanguageOptionsSubtitle"));
                    System.out.print(String.format(EmotionalSongs.languageBundle.getString("SupportedLanguagesOptions"), StringUtils.repeat(" ", TextUtils.SPACESBEFORELOGO-2)));

                    input = TextUtils.readStringInput(true);

                    if(input.equals("1")){
                        EmotionalSongs.languageBundle = ResourceBundle.getBundle("emotionalsongs.Language");
                    } else if (input.equals("2")){
                        EmotionalSongs.languageBundle = ResourceBundle.getBundle("emotionalsongs.LanguageEnglish");
                    } else{
                        System.out.println(languageBundle.getString("ERROR_InvalidInput"));
                    }
                    
                } else {TextUtils.printErrorMessage(languageBundle.getString("ERROR_UnrecognizedCommand"), false); TextUtils.pause();}

            }catch(NumberFormatException e){TextUtils.printErrorMessage(languageBundle.getString("ERROR_InvalidInput"), false);TextUtils.pause();}

        } while(!exit);
        
        //Il seguente blocco di codice tenterà di chiudere il prompt dei comandi in automatico dopo il termine del programma.
        try{

            Thread.sleep(2000);

            Runtime.getRuntime().exec(String.format("taskkill /f /PID %s", pid));
       
        } catch(Exception e){TextUtils.printDebug(EmotionalSongs.languageBundle.getString("DEBUG_ExceptionWhileClosingCMD"), true, false);}

        
    }

    private static void printInformazioni() {
        
        TextUtils.printLogo(languageBundle.getString("ProjectInfoSubtitle"));

        System.out.println(TextUtils.splitLongStringInput(languageBundle.getString("ProjectInfo1"), "\n ", false)+"\n");

        System.out.print(" \t");

        for(var i = 0; i<8; i++){
            System.out.print(Emotions.getColorFromOrdinal(i) + Emotions.values()[i]+ TextUtils.RESET+", ");
        }
        System.out.print(Emotions.getColorFromOrdinal(8) + Emotions.values()[8]+ TextUtils.RESET+"\n\n");
        
        System.out.println(TextUtils.splitLongStringInput(languageBundle.getString("ProjectInfo2"), "\n ", false)+"\n");

        TextUtils.pause();

    }

    /**
     * Effettua la stampa a schermo del menù principale.
     */
    private static void printMenu(){
        
        TextUtils.printLogo(languageBundle.getString("MenuSubtitle"));
        
        System.out.println(StringUtils.repeat(" ", TextUtils.SPACESBEFORELOGO-5) + " [" + TextUtils.BLUE +"1"+ TextUtils.RESET + "]"+ languageBundle.getString("MenuOption1"));

        System.out.println(StringUtils.repeat(" ", TextUtils.SPACESBEFORELOGO-5) + " [" + TextUtils.BLUE +"2"+ TextUtils.RESET + "]"+ languageBundle.getString("MenuOption2"));

        if(loggedUser == null){
            System.out.println(StringUtils.repeat(" ", TextUtils.SPACESBEFORELOGO-5) + " [" + TextUtils.BLUE +"3"+ TextUtils.RESET + "]"+ languageBundle.getString("MenuOption3")+TextUtils.RESET);
            System.out.println(StringUtils.repeat(" ", TextUtils.SPACESBEFORELOGO-5) + " [" + TextUtils.BLUE +"4"+ TextUtils.RESET+ "]"+languageBundle.getString("MenuOption4"));
        } else{
            System.out.println(StringUtils.repeat(" ", TextUtils.SPACESBEFORELOGO-5) + " [" + TextUtils.BLUE +"3"+ TextUtils.RESET + "]"+ languageBundle.getString("MenuOption3_1")+TextUtils.RESET);
            System.out.println(StringUtils.repeat(" ", TextUtils.SPACESBEFORELOGO-5) + TextUtils.GREY + " [4]" + languageBundle.getString("MenuOption4") +  TextUtils.RESET);
            System.out.println(StringUtils.repeat(" ", TextUtils.SPACESBEFORELOGO-5) + " [" + TextUtils.BLUE +"5"+ TextUtils.RESET + "]"+ languageBundle.getString("MenuOption5"));
        }

        System.out.println();

    }

    /**
     * Effettua la stampa a schermo del menù "Le mie playlist" visibile solamente una volta che l'utente ha effettuato il login/sign up.
     */
    private static void printPlaylistMenu(){

        TextUtils.printLogo(languageBundle.getString("MyPlaylistSubtitle"));
        
        System.out.println(StringUtils.repeat(" ", TextUtils.SPACESBEFORELOGO-5) + " [" + TextUtils.BLUE +"1"+ TextUtils.RESET + "] "+ languageBundle.getString("CreatePlaylist"));
        System.out.println(StringUtils.repeat(" ", TextUtils.SPACESBEFORELOGO-5) + " [" + TextUtils.BLUE +"2"+ TextUtils.RESET + "] " + languageBundle.getString("ViewPlaylist"));

        System.out.println();

    }

    /**
     * Restituisce l'utente attualmente loggato nella piattaforma.
     * @return un oggetto di tipo {@code Utente} che rappresenta l'utente attualmente loggato all'interno della piattaforma; {@code null} se nessuno ha effettuato il login.
     */
    protected static Utente getLoggedUser(){return loggedUser;}

    /**
     * Metodo che permette di elaborare gli argomenti passati al programma.
     * @param args gli argomenti passati al programma.
     */
    protected static void parseArgs(String[] args){

        for(var command : args){
            if(command.equals("setDebug:true")){ // non serve valutare il false.
                TextUtils.setDebug(true);

            }else if (command.startsWith("setLanguage")){
                if(command.split(":")[1].equalsIgnoreCase("English")){
                    EmotionalSongs.chosenLanguageBundle = "emotionalsongs.LanguageEnglish";
                } else if(command.split(":")[1].equalsIgnoreCase("Italian")){
                    EmotionalSongs.languageBundle = ResourceBundle.getBundle("emotionalsongs.Language");
                } else{
                    TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_LanguageNotSupported"), true); TextUtils.pause();
                }
            }
        }

    }

}
