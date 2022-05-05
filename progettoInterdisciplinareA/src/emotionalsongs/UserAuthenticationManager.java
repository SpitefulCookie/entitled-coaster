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
    
    private UserAuthenticationManager() {super();}

    public static Utente Registrazione(){

        String str;
        Utente user = new Utente();
        Indirizzo indirizzo = null;

        Scanner in = TextUtils.getScanner();

        System.out.println("\tREGISTRAZIONE UTENTE\n");
        
        System.out.print("Inserisci il tuo nome e cognome separati da uno spazio: ");

        str = in.nextLine();

        str = TextUtils.stripCommas(str);

        user.setNominativo( TextUtils.formatTitleCapital(str));

        // assicura che il CF abbia lunghezza pari a 16 caratteri
        do{

            System.out.print("Inserisci il tuo codice fiscale: ");

            str = in.nextLine().toUpperCase();

            str = TextUtils.stripCommas(str);

            if(str.length() != 1){System.out.println("Il codice fiscale deve essere lungo 16 caratteri\n");} //DEBUG SET TO 1

        } while(str.length() != 1); // IDEM

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
        do{
        
            str = in.nextLine();
            if(str.contains(",") && !str.contains("@")){TextUtils.printErrorMessage("L'indirizzo emaiil inserito non e' valido\nInserire nuovamente: ",true);}

        } while(str.contains(",") && !str.contains("@"));

        user.setEmail(str);
        
        boolean userIdExists = true;

        do{

            System.out.print("Inserisci il tuo nome utente: ");

            str = in.nextLine();
            str = TextUtils.stripCommas(str);

            userIdExists = UserAuthenticationManager.esiste(str);

            if(userIdExists){
                System.out.println("L'username inserito esiste gia'\n");
            }

        } while(userIdExists);

        user.setUserId(str);

        System.out.print("Inserisci la tua password: ");

        str = in.nextLine(); //controlli?
        str = TextUtils.stripCommas(str);

        user.setPassword(str);

        System.out.println("Confermare i dati inseriti: \n");

        System.out.println(user.toString());

        do{

            System.out.print("\nConferma? (s/n): ");

            str = in.nextLine();

        } while (!str.toLowerCase().equals("s") && !str.toLowerCase().equals("n"));

        if(str.toLowerCase().equals("s")){

            System.out.println("\nRegistrazione avvenuta con successo\n");

            in.close();

            writeUserToArchive(user);

            return user;

        } else {

            System.out.println("\nRegistrazione annullata\n");

            in.close();

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

    public static boolean esiste(String user){

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

