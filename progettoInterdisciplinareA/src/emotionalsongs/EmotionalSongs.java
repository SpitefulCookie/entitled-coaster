package emotionalsongs;

import java.util.Scanner;

/*
 * Progetto svolto da:
 * 
 * Della Chiesa Mattia 749904, Ateneo di Varese
 * 
 */


public class EmotionalSongs{

    public static void main(String[] args) {

        if(args.length!=0) TextUtils.setDebug(args[0]);

        SongRepositoryManager songRepository = new SongRepositoryManager();
        Scanner in = TextUtils.getScanner();

        TextUtils.setDebug(true);

        EmotionsRepositoryManager emotionRepository = new EmotionsRepositoryManager();
        emotionRepository.viewSongSummary(new Canzone("d5b971ee-fcad-3b4b-8964-41515059d558,The sun,Me!,2022"));
        
        TextUtils.pause();

        Utente loggedUser = null;
        String input;

        //TextUtils.printDebug(Emotions.values()[4].name() +": " + Emotions.CLAMNESS.toString() + " - " + Emotions.CLAMNESS.description());

        boolean exit = false;

        do{

            EmotionalSongs.printMenu(loggedUser);
            System.out.println("Inserire un numero per selezionare l'opzione desiderata oppure 'exit' per uscire dall'applicazione.");
            
            do{ //ponibile in un metodo a sè

                System.out.print("Scelta: ");

                input = in.nextLine();

                if(TextUtils.isEmptyString(input)){

                    TextUtils.printErrorMessage("Il valore inserito non è valido. Premere un tasto per continuare", false);
                    in.nextLine();

                }

            } while(TextUtils.isEmptyString(input));

            if(TextUtils.isNumeric(input)){

                switch(Integer.parseInt(input)){

                    case(1):

                        songRepository.consultaRepository();

                        break;

                    case(2):

                        if(loggedUser != null){
                            
                            loggedUser = null;

                            System.out.println("E' stato effettuato il logout!");

                        }else{loggedUser = UserAuthenticationManager.loginUtente();} // rivedere!!!!

                        break;

                    case (3):

                        if(loggedUser==null){ loggedUser = UserAuthenticationManager.Registrazione();} // refactorare con nuovo look!

                        break;

                    default:

                        TextUtils.printErrorMessage("Il valore inserito non è valido. Premere un tasto per continuare", false);
                        in.nextLine();

                        break;

                
                }

            } else{

                if(input.equalsIgnoreCase("exit")){

                    TextUtils.printLogo("\nArrivederci, grazie per aver utilizzato la nostra piattaforma!", 0);
                    //System.out.println("Arrivederci, grazie per aver utilizzato la nostra piattaforma!");
                    exit = true;

                }

            }

        } while(!exit);
                
        //Playlist.registraPlaylist("userId", songRepository);

        //RepositoryHandler.cercaBranoMusicale();  
        
        //UserAuthenticationManager.Registrazione().toString();

        //System.out.println(UserAuthenticationManager.loginUtente("kuro", "birb").toString());

        //System.out.println("to string: " + UserAuthenticationManager.loginUtente("ptrmug", "1234"));
        
    }

    private static void printMenu(Utente user){

        TextUtils.printLogo("MENU PRINCIPALE", 3);
        
        System.out.println("\t   [" + TextUtils.BLUE_BOLD +"1"+ TextUtils.WHITE_BOLD + "] Consulta repository canzoni");
        System.out.print("\t   [" + TextUtils.BLUE_BOLD +"2"+ TextUtils.WHITE_BOLD + "] Effettua il");

        if(user==null){
            System.out.println(" login");
            System.out.println("\t   [" + TextUtils.BLUE_BOLD +"3"+ TextUtils.WHITE_BOLD+ "] Registrati all'applicazione");

        } else{
            
            System.out.println(" logout");
            System.out.println(TextUtils.RED + "\t   [3] Registrati all'applicazione" +  TextUtils.RESET);
    
        }

        System.out.println();

    }

}
