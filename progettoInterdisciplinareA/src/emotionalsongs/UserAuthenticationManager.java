package emotionalsongs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class UserAuthenticationManager {

    private static final String USER_FILE = "progettoInterdisciplinareA\\data\\UtentiRegistrati.dati";
    private static final String REGISTRATION = "SIGN UP";
    private static final int REGISTRATION_PADDING = 7;
    
    private UserAuthenticationManager() {super();}

    public static Utente Registrazione(){

        String str;
        Utente user = new Utente();
        Indirizzo indirizzo = null;

        Scanner in = TextUtils.getScanner();

        TextUtils.printLogo(REGISTRATION, REGISTRATION_PADDING);

        do{ // controlli sul nominativo
        
            System.out.print("Inserisci il tuo nome e cognome separati da uno spazio: ");

            str = in.nextLine();

            str = TextUtils.stripCommas(str);

            if((str.split(" ").length <2)){TextUtils.printErrorMessage("E' stato inserito un valore che non rispecchia le specifiche richieste.", true);}

        } while (str.split(" ").length <2);

        user.setNominativo(TextUtils.formatTitleCapital(str));

        boolean valid = false;

        do{ // controlli sul codice fiscale

            do{

                System.out.print("Inserisci il tuo codice fiscale: ");

                str = in.nextLine().toUpperCase();

                if(str.length() != 16){TextUtils.printErrorMessage("Il codice fiscale dev'essere lungo 16 caratteri.", true);} 

            } while(str.length() != 16);

            valid = TextUtils.isValidCF(str);

            if(!valid){TextUtils.printErrorMessage("Il valore inserito non rappresenta un codice fiscale regolare.", false); TextUtils.pause();}

        }while(!valid);

        if(esisteCF(str)){ // verifica se esiste il codice fiscale
            TextUtils.printErrorMessage("Esiste gia' un utente con quel codice fiscale, effettuare il login.", true);
            TextUtils.pause();
            return null;
        }

        user.setCodiceFiscale(str);

        do{

            try{

                System.out.println("Inserisci il tuo indirizzo nel formato:\n\t<Nome della Via/piazza>, <numero civico>, <cap>, <citta'>, <provincia>");

                System.out.print("Indirizzo: ");

                indirizzo = new Indirizzo(in.nextLine());

            } catch (AddressNotValidException e){

                System.out.println("Indirizzo non valido\n");
                
            }

        } while(indirizzo == null);

        user.setIndirizzo(indirizzo);

        System.out.print("Inserisci il tuo indirizzo email: ");

        do{ // controlli di base sull'indirizzo email
        
            str = in.nextLine();
            if(str.contains(",") && !str.contains("@")){TextUtils.printErrorMessage("L'indirizzo email inserito non e' valido\nInserire nuovamente: ",true);}

        } while(str.contains(",") && !str.contains("@"));

        user.setEmail(str);
        
        boolean userIdExists = true;

        do{ // controlli sul nome utente (univoco)

            System.out.print("Inserisci il tuo nome utente: ");

            str = in.nextLine();
            str = TextUtils.stripCommas(str);

            userIdExists = UserAuthenticationManager.utenteEsiste(str);

            if(userIdExists){
                System.out.println("L'username inserito esiste gia'\n");
            }

        } while(userIdExists);

        user.setUserId(str);

        do{

            System.out.print("Inserisci la tua password: ");

            str = in.nextLine(); 
            str = TextUtils.stripCommas(str);

            if (TextUtils.isEmptyString(str)){TextUtils.printErrorMessage("E' stata immessa una password vuot.a", true);}

        } while (TextUtils.isEmptyString(str));

        user.setPassword(str);

        TextUtils.printLogo(REGISTRATION, REGISTRATION_PADDING);

        System.out.println("Confermare i dati inseriti: \n");

        System.out.println(user.toString());

        System.out.print("\nConferma? (s/n): ");

        boolean answer = TextUtils.readYesOrNo();

        if(answer){

            System.out.println("\nRegistrazione avvenuta con successo\n");

            writeUserToArchive(user);

            return user;

        } else {

            System.out.println("\nRegistrazione annullata\n");

            return null;

        }
        
    }

    public static Utente loginUtente(){

        Scanner in = TextUtils.getScanner();
        String userId;
        String password;

        TextUtils.printLogo("LOGIN UTENTE", 2);
        System.out.print("Inserisci il tuo nome utente: ");

        do{
            userId = in.nextLine();

            if(TextUtils.isEmptyString(userId)){
                TextUtils.printErrorMessage("E' stato inserito un valore non ammesso, inserire nuovamente: ", false);
            }

        } while(!TextUtils.isEmptyString(userId));

        System.out.print("Inserisci la tua password: ");

        do{
            password = in.nextLine();

            if(TextUtils.isEmptyString(password)){
                TextUtils.printErrorMessage("E' stato inserito un valore non ammesso, inserire nuovamente: ", false);
            }

        } while(!TextUtils.isEmptyString(password));

        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {

            String line;
            String[] credenziali;
           
            while ((line = br.readLine()) != null) {

                credenziali = UserAuthenticationManager.parseCredentials(line).split(",");

                if(credenziali[0].equals(userId) && credenziali[1].equals(password)){

                    try{
                        
                        System.out.println("\nLogin effettuato, bentornato " + userId + "\n");
                        return new Utente(line);
    
                    } catch (AddressNotValidException e){

                        System.out.println("Indirizzo non valido\n");

                    }
                
                }

            }

        } catch (IOException e)  {

           TextUtils.printErrorMessage("Impossibile effettuare il login, non è stato trovato l'archivio utenti.", true);

        } 

        return null;

    }

    private static String parseCredentials(String line){

        return line.substring(line.lastIndexOf(',' , line.lastIndexOf(',')-1)+1, line.length());

    }

    public static boolean esisteCF(String cf){

        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {

            String line;
            int first = -1;

            while ((line = br.readLine()) != null) {

                first = line.indexOf(",")+1;

                if(line.substring(first, line.indexOf(",", first)).equalsIgnoreCase(cf)){
                   
                   return true;

                }

            }

        } catch (IOException e)  {

            TextUtils.printErrorMessage("IO error:", true);

            e.printStackTrace();

            return true;

        } 

        return false;

    }

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

    private static boolean writeUserToArchive(Utente user){

        File file = new File(USER_FILE);

        try{

            if(!file.exists()){file.createNewFile();}
            
            FileWriter fw = new FileWriter(file,true);
            BufferedWriter bw = new BufferedWriter(fw);

            if(file.length()!=0){
                bw.newLine();
            } 
            System.out.println("CSV: " + user.toCSV());
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

