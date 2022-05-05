package emotionalsongs;

//import java.util.List;

/*
 * Progetto svolto da:
 * 
 * Della Chiesa Mattia 749904, Ateneo di Varese
 * 
 */


public class EmotionalSongs{

    private static RepositoryManager repository;

    public static void main(String[] args) {

        repository = new RepositoryManager();

        //Utente utenteConnesso = null;

        //System.out.println("\nRicerca canzoni:\n\n");
        //List<Canzone> canzoni = RepositoryHandler.cercaBranoMusicale(); 

        /*Playlist p = Playlist.registraPlaylist("userId");

        for(Canzone canzone : canzoni){

            System.out.print("Sto aggiungendo: \t");
            System.out.println(canzone.toString());
            p.addCanzone(canzone);
            
        }*/

        //EmotionalSongs.printMenu(false);

        TextUtils.setDebug(true);

        //repository.cercaBranoMusicale();
        
        //Playlist.registraPlaylist("userId");

        //RepositoryHandler.cercaBranoMusicale();  
        
        //UserAuthenticationManager.Registrazione().toString();

        //System.out.println(UserAuthenticationManager.loginUtente("kuro", "birb").toString());

        //System.out.println("to string: " + UserAuthenticationManager.loginUtente("ptrmug", "1234"));
        
    }

    private static void printMenu(boolean connected){

        TextUtils.printLogo("MENU PRINCIPALE", 3);
        
        System.out.println("\t   [" + TextUtils.BLUE_BOLD +"1"+ TextUtils.WHITE_BOLD + "] Consulta repository canzoni");
        System.out.print("\t   [" + TextUtils.BLUE_BOLD +"2"+ TextUtils.WHITE_BOLD + "] Effettua il");

        if(!connected){
            System.out.println(" login");
            System.out.println("\t   [" + TextUtils.BLUE_BOLD +"3"+ TextUtils.WHITE_BOLD+ "] Registrati all'applicazione");
        } else{
            
            System.out.println(" logout");
            System.out.println(TextUtils.RED + "\t   [3] Registrati all'applicazione" +  TextUtils.RESET);
    
        }

    }

}
