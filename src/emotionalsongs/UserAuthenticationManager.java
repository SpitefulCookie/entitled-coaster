package emotionalsongs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

/*
 * Progetto svolto da:
 * 
 * Della Chiesa Mattia 749904, Ateneo di Varese
 * 
 */

/**
 * La classe {@code UserAuthenticationManager} offre operazioni volte alla gestione della parte autenticativa degli utenti presenti all'interno della piattaforma.
 * <p>Queste operazioni includono:
 *  <ul>
 *  <li> Registrazione di nuovi utenti
 *  <li> Verifica delle credenziali
 *  <li> Controlli sugli utenti già presenti in archivio
 *  </ul>
 * 
 *  @see emotionalsongs.Utente
 *  @author <a href="https://github.com/SpitefulCookie">Della Chiesa Mattia</a>
 *  
 */
public class UserAuthenticationManager {

    private static final String USER_FILE = System.getProperty("user.dir")+"\\data\\UtentiRegistrati.dati";
    
    private UserAuthenticationManager() {super();}

    /***
     * Permette agli utenti del'applicazione di registrarsi alla piattaforma tramite una procedura guidata. 
     * @return un oggetto di tipo {@code Utente} contenente i dati dell'utente appena registrato; {@code null} 
     * se il codice fiscale fornito è già presente in archivio oppure se l'utente ha annullato il processo
     * di registazione
     * 
     */
    public static Utente Registrazione(){

        String str;
        Utente user = new Utente();
        Indirizzo indirizzo = null;

        TextUtils.printLogo(EmotionalSongs.languageBundle.getString("RegistrationSubtitle"));

        do{ // controlli sul nominativo

            System.out.print(EmotionalSongs.languageBundle.getString("InsertNameSurname"));
            
            str = StringUtils.trim(TextUtils.readStringInput(false));

            str = TextUtils.stripCommas(str);

            if((str.split(" ").length <2)){TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_InvalidInputFormat"), true);}

        } while (str.split(" ").length <2);

        user.setNominativo(TextUtils.formatTitleCapital(str));

        //---

        boolean valid = false;

        do{ // controlli sul codice fiscale

            do{

                System.out.print(EmotionalSongs.languageBundle.getString("InsertCF"));
                str = StringUtils.trim(TextUtils.readStringInput(false).toUpperCase());

                if(str.length() != 16){TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_CFMustBe16CharLong"), true);} 

            } while(str.length() != 16);

            valid = TextUtils.isValidCF(StringUtils.trim(str));

            if(!valid){TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_InvalidCF"), false); TextUtils.pause();}

        }while(!valid);

        if(esisteCF(str)){ // verifica se esiste il codice fiscale
            TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_CFAlreadyInUse"), true);
            TextUtils.pause();
            return null;
        }

        user.setCodiceFiscale(str);

        do{

            try{

                System.out.print(EmotionalSongs.languageBundle.getString("InsertAddressInFormat"));

                indirizzo = new Indirizzo(StringUtils.trim(TextUtils.readStringInput(false)));

            } catch (AddressNotValidException e){

                TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_InvalidAddressPrompt"), true);
                
            }

        } while(indirizzo == null);

        user.setIndirizzo(indirizzo);

        do{ // controlli di base sull'indirizzo email

            System.out.print(EmotionalSongs.languageBundle.getString("InsertEmail"));
            str = StringUtils.trim(TextUtils.readStringInput(false));

            if(str.contains(",") || !str.contains("@") || !str.contains(".")){TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_InvalidEmailAddressPrompt"),true);}

        } while(str.contains(",") || !str.contains("@") || !str.contains("."));

        user.setEmail(str);
        
        boolean userIdExists = true;

        do{ // controlli sul nome utente (univoco)

            System.out.print(EmotionalSongs.languageBundle.getString("InsertUsername"));
            str = StringUtils.trim(TextUtils.readStringInput(false));
            str = TextUtils.stripCommas(str);

            userIdExists = UserAuthenticationManager.utenteEsiste(str);

            if(userIdExists){
                TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_UsernameAlreadyExistsPrompt"), true);
            }

        } while(userIdExists);

        user.setUserId(str);

        do{
            System.out.print(EmotionalSongs.languageBundle.getString("InsertPassword"));
            str = StringUtils.trim(TextUtils.readStringInput(false));
            str = TextUtils.stripCommas(str);

            if (TextUtils.isEmptyString(str)){TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_PasswordIsEmpty"), true);}

        } while (TextUtils.isEmptyString(str));

        user.setPassword(str);

        TextUtils.printLogo(EmotionalSongs.languageBundle.getString("RegistrationSubtitle"));

        System.out.println(EmotionalSongs.languageBundle.getString("ConfirmData") + "\n");

        System.out.println(user.toString());

        System.out.print(EmotionalSongs.languageBundle.getString("Confirm"));

        boolean answer = TextUtils.readYesOrNo(true);

        if(answer){

            System.out.println(EmotionalSongs.languageBundle.getString("RegistrationSucceeded"));

            writeUserToArchive(user);

            return user;

        } else {

            System.out.println(EmotionalSongs.languageBundle.getString("RegistrationCancelled"));

            return null;

        }
        
    }

    /***
     * Permette agli utenti del'applicazione di effettuare l'accesso alla piattaforma tramite le loro credenziali. 
     * @return un oggetto di tipo {@code Utente} se la procedura di login è andata a buon fine. Nel caso la procedura di login
     * è andata a buon fine ma si è verificato un errore nella costruzione di un oggetto di tipo {@code Indirizzo}, il campo codice ficale dell'utente verrà
     * inizilizzato con valore "-1". {@code null} se le credenziali inserite non risultano corrette
     * 
     */
    public static Utente loginUtente(){

        String userId;
        String password;

        TextUtils.printLogo(EmotionalSongs.languageBundle.getString("UserLoginSubtitle"));

        System.out.print(EmotionalSongs.languageBundle.getString("InsertUsername"));
        userId = TextUtils.readStringInput(false);

        System.out.print(EmotionalSongs.languageBundle.getString("InsertPassword"));
        password = new String (System.console().readPassword());

        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {

            String line;
            String[] credenzialiLette;
           
            while ((line = br.readLine()) != null){

                credenzialiLette = UserAuthenticationManager.extractCredentials(line);                

                if(credenzialiLette.length!=0 && credenzialiLette[0].equals(userId) && credenzialiLette[1].equals(password)){

                    Utente loggedUser;

                    try{

                        loggedUser = new Utente(line);
    
                        System.out.println(String.format(EmotionalSongs.languageBundle.getString("WelcomeUserOnLogin"), loggedUser.getUserId()));
                        TextUtils.pause();
                        
                        return loggedUser; 
    
                    } catch (AddressNotValidException e){ //Documentazione

                        /*
                         * L'eccezione AddressNotValid viene lanciata dal costruttore di Utente qualora il formato dell'indirizzo inserito dall'utente
                         * non rispetti il formato prestabilito. Di norma, in questo punto del codice è estremamente improbabile che l'eccezione
                         * venga lanciata in quanto implicherebbe che i dati presenti nell'archivio UtentiRegistrati.dati siano stati manipolati 
                         * e/o corrotti/danneggiati.
                         * Questo problema può essere semplicemente risolto facendo confermare ed eventualmente inserire nuovamente l'indirizzo all'utente.
                         * Tuttavia, data la natura del progetto, si è deciso di inserire un semplice messaggio di errore che suggerisce
                         * all'utente della piattaforma di contattare un ipotetico supporto utenti.
                         * 
                         */ 

                        System.out.println();
                        TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_LoginFailed"), true);
                        TextUtils.pause();

                        /*
                         * Per demarcare che l'utente ha inserito credenziali corrette ma è stato rilevato un errore in fase di login 
                         * (poichè altrimenti verrebbe visualizzato l'errore 'Username o password errati!' su return null), si è 
                         * deciso di inizializzare il campo CF con flag '-1', in quanto quest'ultimo non potrà essere inizializzato 
                         * a piacere dall'utente (causando così falsi positivi).
                         * 
                         */

                        loggedUser = new Utente();
                        loggedUser.setCodiceFiscale("-1");

                        return loggedUser;

                    }
                
                }

            }

        } catch (IOException e) { // Documentazione

           TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_UserArchiveNotFound"), true);

        }

        return null;

    }

    /***
     * Estrae le credenziali di un utente da un'oggetto di tipo {@code String} in formato CSV
     * @param line un oggetto di tipo {@code String} in formato CSV contentente i dati di un utente della piattaforma.
     * @return un oggetto di tipo {@code String[]} contenente l'user id e la password dell'utente letto oppure un array vuoto se i dati forniti in input non risultano validi.
     * parametro attuale non è valido.
     * 
     */
    public static String[] extractCredentials(String line){

        //Ormiste Milani,CVTFWG90A56I444P,"Vicolo Adsorbimenti,121,29686,Ronco Briantino,Monza e della Brianza",ormisteMilani@email.it,ormiste.Milani46,acquattastiabbracciarci29
        // => ormiste.Milani46,acquattastiabbracciarci29 => [ormiste.Milani46 , acquattastiabbracciarci29]
        try{
            return line.substring(line.lastIndexOf(',' , line.lastIndexOf(',')-1)+1, line.length()).split(",");

        } catch (IndexOutOfBoundsException e){return new String[0];}

    }

    /***
     * Verifica se il codice fiscale fornito come parametro esiste già all'interno dell'archivio utenti
     * @param cf un oggetto di tipo {@code String} che rappresenta il codice fiscale di un utente.
     * @return {@code true} se il codice fiscale fornito risulta essere già presente in archivio, {@code false} nel caso contrario.
     * 
     */
    public static boolean esisteCF(String cf){

        File fileCredenziali = new File(USER_FILE);        

        try {   

            if(!fileCredenziali.exists()){

                fileCredenziali.createNewFile();

                return false;

            }     

            BufferedReader br = new BufferedReader(new FileReader(USER_FILE));
            
            String line;
            int firstElementIDX = -1;

            while ((line = br.readLine()) != null) {

                firstElementIDX = line.indexOf(",")+1;

                if(line.substring(firstElementIDX, line.indexOf(",", firstElementIDX)).equalsIgnoreCase(cf)){

                    br.close();
                   
                   return true;

                }

            }

            br.close();

        } catch (IOException e)  { //Documentazione

            TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_GenericIOError"), true);
            e.printStackTrace();

        } 

        return false;

    }

    /***
     * Verifica se il nome utente fornito come parametro esiste già all'interno dell'archivio utenti
     * @param user un oggetto di tipo {@code String} che rappresenta l'userid di un utente.
     * @return {@code true} se il nome utente risulta essere già presente in archivio, {@code false} nel caso contrario.
     * 
     */
    public static boolean utenteEsiste(String user){

        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {

            String line;

            while ((line = br.readLine()) != null) {

                if(line.contains(CharSequence.class.cast(user))){ //non usare contains
                   
                   return true;

                }

            }

        } catch (IOException e)  {

            return false;

        } 

        return false;

    }

    /***
     * Scrive i dati relativi all'utente in memoria di massa.
     * @param user un oggetto di tipo {@code Utente} contenente le informazioni relative all'utente.
     * @return {@code true} se la scrittura su file è andata a buon fine, {@code false} nel caso contrario.
     * 
     */
    private static boolean writeUserToArchive(Utente user){

        File file = new File(USER_FILE);

        try{

            if(!file.exists()){file.createNewFile();}
            
            FileWriter fw = new FileWriter(file,true);
            BufferedWriter bw = new BufferedWriter(fw);

            if(file.length()!=0){
                bw.newLine();
            } 
            
            TextUtils.printDebug(EmotionalSongs.languageBundle.getString("DEBUG_UserCSV")+ user.toCSV(), true, true);
            bw.write(user.toCSV());
            bw.flush();
            
            bw.close();
            fw.close();

        } catch (IOException e) {

            return false;

        }

        return true;

    }
}

