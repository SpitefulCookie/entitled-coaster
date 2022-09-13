package emotionalsongs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/*
 * Progetto svolto da:
 * 
 * Della Chiesa Mattia 749904, Ateneo di Varese
 * 
 */

/**
 * La classe {@code Playlist} rappresenta una playlist di canzoni realizzata da un utente.
 * Ogni playlist è caratterizzata da i seguenti campi:
 * <ul>
 * <li>Il nome della playlist</li>
 * <li>Una lista di canzoni;</li>
 * <li>Il nome utente associato alla playlist</li>
 * </ul>
 */
public class Playlist{

    private ArrayList<Canzone> canzoni;
    private String nome;
    private String user;
    
    /**
     * Costruisce una playlist vuota a partire dal nome utente e dal nome della playlist.
     * @param nomePlaylist un oggetto di tipo {@code String} che rappresenta il nome della playlist.
     * @param userId un oggetto di tipo {@code String} che rappresenta il nome utente associato alla playlist.
     */
    public Playlist(String nomePlaylist, String userId){

        this.canzoni = new ArrayList<>();
        this.nome = nomePlaylist;
        this.user = userId;

    }

    /**
     * Costruisce una playlist a partire dagli elementi che la definiscono.
     * @param userID un oggetto di tipo {@code String} che contiene l'userID dell'utente che ha creato la playlist.
     * @param nomePlaylist un oggetto di tipo {@code String} che contiene il nome della playlist.
     * @param songUUIDs un array di stringhe che contiene gli UUID di tutte le canzoni presenti nella playlist.
     */
    protected Playlist(String userID, String nomePlaylist, String[] songUUIDs){

        this.canzoni = new ArrayList<>();
        this.nome = nomePlaylist;
        this.user = userID;

        for(String s : songUUIDs){
            Canzone c = SongRepositoryManager.getCanzone(s);
            if(c!=null){
                this.canzoni.add(c);
            }
        }

    }

    /**
     * Restituisce il numero di canzoni presenti all'interno della playlist.
     * @return un valore di tipo {@code int} che rappresenta il numero di canzoni presenti all'interno della playlist.
     */
    protected int getSize(){return this.canzoni.size();}

    /**
     * Restituisce il nome della playlist.
     * @return un oggetto di tipo {@code String} che rappresenta il nome della playlist.
     */
    protected String getNome(){return this.nome;}

    /**
     * Effettua il print a schermo dei brani presenti all'interno della playlist sotto forma di lista numerata:
     * <pre>
     * "[1] Titolo canzone"
     *"[2] Titolo canzone"
     * </pre>
     */
    protected void printPlaylist(){
        
        for(int i = 0; i<this.canzoni.size(); i++){

            System.out.println(StringUtils.repeat(" ", TextUtils.SPACESBEFORELOGO-5) + " [" + TextUtils.BLUE + (i+1) + TextUtils.RESET + "] " +canzoni.get(i).toString());
        
        }
    
    }

    /**
     * Aggiunge una canzone alla playlist.
     * @param canzone un oggetto di tipo {@code Canzone} che rappresenta la canzone da aggiungere alla playlist.
     */
    public void addCanzone(Canzone canzone){
        if(!this.canzoni.contains(canzone)){
            this.canzoni.add(canzone);
        }
    }
    
    /**
     * Verifica se la canzone passata come parametro si trova all'interno della playlist.
     * @param canzone un oggetto di tipo {@code Canzone} che rappresenta la canzone da controllare.
     * @return un valore di tipo {@code boolean} che indica se la canzone è presente all'interno della playlist.
     */
    protected boolean contains(Canzone canzone){

        for(Canzone c : this.canzoni){
            if(c.getSongUUID().equals(canzone.getSongUUID())){return true;}
        }
        return false;

    }

    /**
     * Guida l'utente nella creazione di una nuova playlist.
     */
    protected static void registraPlaylist(){
        
        // per creare una playlist l'utente registrato deve inserire il nome della playlist e l'elenco di brani da aggiungere. o brani singoli o brani di un autore specifico
        TextUtils.printLogo(EmotionalSongs.languageBundle.getString("CreatePlaylistSubtitle"));

        System.out.print(EmotionalSongs.languageBundle.getString("InsertPlaylistName"));
        String input = TextUtils.readStringInput(false);

        List<Canzone> canzoniTrovate;
        
        Playlist playlist = new Playlist(input, EmotionalSongs.getLoggedUser().getUserId());

        do{ // Questo ciclo do-while si ripeterà finchè l'utente desidera aggiungere brani alla playlist

            do { // Questo ciclo, invece, verrà ripetuto finchè la funzione di ricerca non restituisce dei risultati o finchè l'utente non decide di interrompere la ricerca.
                
                canzoniTrovate = SongRepositoryManager.cercaBranoMusicale();

                if(canzoniTrovate.isEmpty()){ // La ricerca non ha restituito risultati

                    System.out.print(EmotionalSongs.languageBundle.getString("NoSearchResultsYielded"));

                    if(!TextUtils.readYesOrNo(true)){

                        /*
                        * Non sono stati trovati risultati all'interno della ricerca e l'utente ha deciso di non effettuarne una seconda.
                        * Nel caso l'utente abbia già inserito dei brani nella playlist, all'utente verrà chiesto se desidera salvare i progressi effettuati
                        */
                        
                        confirmCancel(playlist); return;

                    } 

                } else if(canzoniTrovate.get(0) == null ){ // La ricerca è stata cancellata dall'utente
                    
                    /* 
                     * Questo blocco di codice identifica il caso in cui l'utente abbia deciso di cancellare il processo di ricerca delle canzoni. 
                     * Anche qui l'utente sarà in grado di salvare i progressi effettuati.
                     */
                    
                    confirmCancel(playlist); return;

                }

            } while (canzoniTrovate.isEmpty()); 

            Canzone canzoneScelta = null;
            int idx = TextUtils.selezionaRisultati(canzoniTrovate);

            /*
             * La variabile idx può assumere n+1 valori dove n è pari al numero di canzoni trovate mediante la ricerca e
             * 1 è il valore -1 che rappresenta il caso in cui l'utente ha deciso di interrompere il processo di inserimento 
             * del brano ricercato.
             */

            if(idx != -1){ 
            
                canzoneScelta = canzoniTrovate.get(idx);
                TextUtils.printLogo(EmotionalSongs.languageBundle.getString("SearchSongSubtitle"));

                System.out.print(EmotionalSongs.languageBundle.getString("SongSelectionEcho") + "\n\n\t" +TextUtils.YELLOW + canzoneScelta.toString() +TextUtils.RESET+"\n\n"+ EmotionalSongs.languageBundle.getString("RequestConfirmation"));

                if(TextUtils.readYesOrNo(true)){

                    // Viene verificato se la canzone scelta si trova già all'interno della playlist

                    if(playlist.contains(canzoneScelta)){

                        System.out.print(EmotionalSongs.languageBundle.getString("ChosenSongIsAlreadyInPlaylist"));
                        TextUtils.pause();
                    
                    } else{
                            
                        playlist.addCanzone(canzoneScelta);

                        TextUtils.printLogo(EmotionalSongs.languageBundle.getString("CreatePlaylistSubtitle"));

                        System.out.println(String.format(EmotionalSongs.languageBundle.getString("SongHasBeenAddedToPlaylist"), canzoneScelta.getTitolo()));
                        TextUtils.pause();
                    
                    }

                }

            }

            /*
             * Qualora la playlist contiene dei risultati, essa verrà visualizzata all'utente il quale potrà 
             * scegliere se continuare ad aggiungere brani oppure terminare il processo.
             */

            if(!playlist.isEmpty()){

                TextUtils.printLogo(EmotionalSongs.languageBundle.getString("CreatePlaylistSubtitle"));

                System.out.print(EmotionalSongs.languageBundle.getString("SongsInPlaylist"));
                
                playlist.printPlaylist();

                System.out.print(EmotionalSongs.languageBundle.getString("ContinueAddingSongsPrompt"));

            }else if(idx ==-1){
                return;
            }
                    

        } while (TextUtils.readYesOrNo(true));

        /*
         * A questo punto l'utente è soddisfatto dei brani presenti nella playlist e prima di completare il procedimento, gli verrà chiesto se desidera
         * registrare immediatamente le emozioni provate durante l'ascolto di uno (o più) brani presenti nella playlist.
         */

        System.out.print(EmotionalSongs.languageBundle.getString("InsertPlaylistEmotionsPrompt"));       

        if(TextUtils.readYesOrNo(true)){ 
            
            PlaylistManager.registerEmotionsInPlaylist(playlist);

        }

        TextUtils.printDebug(EmotionalSongs.languageBundle.getString("DEBUG_ResultsObtained"), true, true);
        for(Canzone c : playlist.canzoni){TextUtils.printDebug(c.toString(), false, true);}
        
        // Qui viene effettuato il salvataggio della playlist nel file Playlist.dati
        if(PlaylistManager.scriviPlaylist(playlist)){
            
            System.out.println(EmotionalSongs.languageBundle.getString("PlaylistSuccessfullySaved"));
            TextUtils.pause();

        }

    }

    /**
     * Questo metodo consente all'utente di scegliere se desidera interrompere la creazione della playlist.
     * @param playlist la playlist in fase di creazione.
     * @return true se l'utente ha deciso di interrompere la creazione della playlist, false altrimenti.
     */
    private static boolean confirmCancel(Playlist playlist){

        boolean sceltaBoolean = false;

        if(!playlist.isEmpty()){

            TextUtils.printLogo(EmotionalSongs.languageBundle.getString("CreatePlaylistSubtitle"));

            System.out.println(String.format(EmotionalSongs.languageBundle.getString("CancelPlaylistWithSongs"), playlist.getSize()));

           for (int i = 0; i < playlist.canzoni.size(); i++) {System.out.println("\t[" + TextUtils.BLUE  + (i+1) +TextUtils.RESET + "] " + playlist.canzoni.get(i));}
            
            System.out.println();
            System.out.print(EmotionalSongs.languageBundle.getString("PromptPreserveProgressInPlaylistCreation"));
            sceltaBoolean = TextUtils.readYesOrNo(true);

        }

        if(sceltaBoolean && PlaylistManager.scriviPlaylist(playlist)){System.out.println(EmotionalSongs.languageBundle.getString("PlaylistHasBeenCreated"));}
        else {System.out.println(EmotionalSongs.languageBundle.getString("PlaylistCreationAborted"));}

        TextUtils.pause();

        return !sceltaBoolean; // Il valore è stato negato perchè all'utente viene chiesto se desidera *mantenere* il progresso nell creazione della playlist.

    }

    /**
     * Permette di formattare i dati contenuti nella playlist secondo lo standard CSV.
     * @return un oggetto {@code String} contenente i dati della playlist in formato CSV.
     */
    public String toCSV(){

        StringBuilder sb = new StringBuilder(this.user+","+this.nome+",{");

        for(Canzone c : this.canzoni){

            sb.append(c.getSongUUID()+",");
            
        }      
        
        sb.deleteCharAt(sb.length()-1); //rimuove l'ultima virgola
        sb.append("}");

        return sb.toString();

    }

    /**
     * Verifica se la playlist è vuota.
     * @return un valore {@code boolean} che indica se la playlist risulta essere vuota.
     */
    protected boolean isEmpty(){return this.canzoni.isEmpty();}
    
    /**
     * Restituisce il nome dell'utente associato alla playlist.
     * @return un oggetto di tipo {@code String} che contiene il nome dell'utente associato alla playlist.
     */
    protected String getUser(){return this.user;}

    /**
     * Restituisce un oggetto di tipo {@code String} contenente il nome della playlist.
     */
    @Override
    public String toString(){return this.nome;}

    /**
     * Restituisce un oggetto di tipo {@code Canzone} contenente la canzone presente all'indice passato come parametro.
     * @param index l'indice della canzone desiderata.
     * @return un oggetto di tipo {@code Canzone} contenente la canzone presente all'indice passato come parametro.
     */
    protected Canzone getCanzone(int index) {
        if(index < 0 || index >= this.canzoni.size()){return null;}
        return this.canzoni.get(index);
    }

    /**
     * Restituisce l'UUID della canzone presente all'indice passato come parametro.
     * @param index l'indice della canzone desiderata.
     * @return un oggetto di tipo {@code String} che contiene l'UUID di tipo 3 della canzone presente all'indice passato come parametro.
     */
    protected String getSongUUID(int index) {

        if(index>=0 && index < this.canzoni.size()){

            return this.canzoni.get(index).getSongUUID();

        }else{

            return "";

        }

    }

    /**
     * Restituisce le canzoni presenti nella playlist sotto forma di lista.
     * @return un oggetto di tipo {@code ArrayList} che contiene le canzoni presenti nella playlist.
     */
    public List<Canzone> asList() {
        return this.canzoni;
    }
    
}
/**
 * La classe {@code PlaylistManager} implementa operazioni utili alla gestione delle playlist dell'utente che ha effettuato l'accesso alla piattaforma.
 */
class PlaylistManager {

    private static HashMap<String, Playlist> userPlaylists; 
    private static Set<String> playlistNames;

    private static final String PLAYLIST_FILE = System.getProperty("user.dir")+"\\data\\Playlist.dati";
    
    /**
     * Costruttore esplicito ad object.
     */
    private PlaylistManager(){super();}

    /**
     * Restituisce le playlist dell'utente attualmente che ha effettuato l'accesso alla piattaforma.
     * @return un oggetto di tipo {@code HashMap} che contiene le playlist dell'utente che ha effettuato l'accesso alla piattaforma.
     */
    protected static Map<String, Playlist> getUserPlaylists(){return userPlaylists;}

    /**
     * Restituisce i nomi delle playlist create dall'utente.
     * @return un oggetto di tipo {@code Set} che contiene un insieme dei nomi delle playlist create dall'utente.
     */
    protected static Set<String> getKeys(){return playlistNames;}

    /**
     * Costruisce una repository contenente le playlist dell'utente che ha effettuato l'accesso alla piattaforma.
     * @return
     */
    protected static boolean build(){

        if(EmotionalSongs.getLoggedUser() == null){return false;}

        userPlaylists = new HashMap<>();

        boolean found = false;

        try{

            File file = new File(PLAYLIST_FILE);

            if(!file.exists()){ // se il file "Playlist.dati" non esiste lo creo e restituisco immediatamente il valore false (nessuna playlist trovata per l'utente passato)
                
                TextUtils.printDebug(String.format(EmotionalSongs.languageBundle.getString("DEBUG_FileNotFound"), "Emozioni.dati"), true, true);
            
                file.createNewFile();

                return false;
                
            } else{

                Scanner sc = new Scanner(file);
                sc.useDelimiter(",");

                while(sc.hasNextLine()){

                    String line = sc.nextLine();

                    if(!line.equals("")){ //Evita che possa venir lanciata una StringIndexOutOfBoundsException se è presente una linea vuota all'interno del file playlist.dati

                        String[] userdata = line.substring(0, line.indexOf("{")).split(",");
                        
                        String user = userdata[0];
                        String playlistName = userdata[1];
                        String[] songs = line.substring(line.indexOf("{")+1,line.indexOf("}")).split(",");

                        if(user.equals(EmotionalSongs.getLoggedUser().getUserId())){
                            
                            found = true;
                            
                        userPlaylists.put(playlistName, new Playlist(user, playlistName, songs));
                            
                        }

                    }

                }

                sc.close();

                return found;

            }

        } catch (IOException e) { //Documentazione

            TextUtils.printDebug(EmotionalSongs.languageBundle.getString("ERROR_IOExceptionBuildPlaylist"),true, true); //DOCUMENTAZIONE
            TextUtils.pause();

        }

        return false;

    }

    /**
     * Svuota la repository contenente le playlist dell'utente dopo che quest'ultimo ha effettuato il log-off dalla piattaforma.
     */
    protected static void clearPlaylists(){
        userPlaylists = null;
        playlistNames = null;
    }

    /**
     * Restituisce la playlist associata al nome passato come parametro.
     * @param name il nome della playlist desiderata.
     * @return un oggetto di tipo {@code Playlist} che contiene la playlist associata al nome passato come parametro.
     */
    protected static Playlist getPlaylist(String name){return userPlaylists.get(name);}

    /**
     * Effettua la scrittura in memoria di massa di una playlist passata come parametro.
     * @param p un oggetto di tipo {@code Playlist} che contiene la playlist da scrivere in memoria.
     * @return un valore booleano che indica se la scrittura è andata a buon fine.
     */
    public static boolean scriviPlaylist(Playlist p){

        try{

            File file = new File(PLAYLIST_FILE);

            if(!file.exists()){file.createNewFile();}

            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));

            TextUtils.printDebug("Playlist's CSV:\n" + p.toCSV(), true, false);

            bw.append("\n"+p.toCSV());

            bw.close();

        } catch (IOException e) {e.printStackTrace();
            return false;
        } 
        
        userPlaylists.put(p.getNome(), p);

        return true;

    }

    protected static Playlist selezionaPlaylist(){

        int pagina = 0;

        while(true){

            TextUtils.printLogo(EmotionalSongs.languageBundle.getString("SelectPlaylistSubtitle"));

            TextUtils.elencaRisultati(pagina, userPlaylists);

            String[] chiavi = userPlaylists.keySet().toArray(new String[userPlaylists.size()]);

            System.out.print(EmotionalSongs.languageBundle.getString("PlaylistSelection"));

            String input = TextUtils.readStringInput(true);

            if(TextUtils.isNumeric(input)){ //viene visualizzata la playlist scelta

                int inputNumerico = Integer.parseInt(input);

                if(inputNumerico<1 || inputNumerico>chiavi.length){

                    TextUtils.printErrorMessage(String.format(EmotionalSongs.languageBundle.getString("ERROR_ValueMustBeInsideIntervalPrompt"),1, chiavi.length), false);
                    inputNumerico = TextUtils.readInt(1, chiavi.length, true);

                }

                return getPlaylist(chiavi[(inputNumerico-1)+(pagina*9)]); //non va bene è inputnumerico-1+(pagina*9)
        
            }else {

                if(input.equalsIgnoreCase("previous")){

                    if(pagina>0){pagina--;} else{TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_NoPreviousPages"), false); TextUtils.pause();}
                    
        
                } else if(input.equalsIgnoreCase("next")){
        
                    if(pagina == (Math.ceil((userPlaylists.size()/9.00))-1)){TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_NoNextPages"), false); TextUtils.pause();} else{pagina++;}
        
        
                } else if(input.equalsIgnoreCase("cancel")){

                    return null;
        
                }else {TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_UnrecognizedCommand"), false); TextUtils.pause();}

            }
        }
    }

    /**
     * Questo metodo permette all'utente di consultare le playlist che ha creato.
     */
    protected static void consultaPlaylists(){
        
        int inputNumerico = 0;
        String input;

        TextUtils.printLogo(EmotionalSongs.languageBundle.getString("ViewPlaylistSubtitle"));

        if (userPlaylists != null && !userPlaylists.isEmpty()){ // L'utente possiede delle playlist in archivio

            Playlist playlistScelta = PlaylistManager.selezionaPlaylist();

            if(playlistScelta!=null){

                int pagina = 0;
        
                while(true){

                    TextUtils.printLogo(EmotionalSongs.languageBundle.getString("ViewPlaylistSubtitle"));
                    System.out.println(String.format(EmotionalSongs.languageBundle.getString("ListOfSongsInPlaylist"),playlistScelta.getNome()));
                    
                    TextUtils.elencaRisultati(pagina, playlistScelta.asList());

                    System.out.print(EmotionalSongs.languageBundle.getString("SongSelectionFromPlaylist"));

                    input = TextUtils.readStringInput(true);

                    if(TextUtils.isNumeric(input)){ // il valore immesso rappresenta una canzone

                        inputNumerico = Integer.parseInt(input);

                        if(inputNumerico<1 || (inputNumerico-1)+(pagina*9)>playlistScelta.getSize() || inputNumerico > 9){
                            
                            TextUtils.printErrorMessage(String.format(EmotionalSongs.languageBundle.getString("ERROR_ValueMustBeInsideIntervalPrompt"),1, playlistScelta.getSize()-(pagina*9)), false);
                            inputNumerico = TextUtils.readInt(1, playlistScelta.getSize(), true);

                        }
  
                        EmotionsRepositoryManager.visualizzaEmozioneBrano(playlistScelta, playlistScelta.getCanzone((inputNumerico-1)+(pagina*9)));

                        
                    }else { // il Valore immesso è un comando

                        if(input.equalsIgnoreCase("previous")){
            
                            if(pagina>0){pagina--;} else{TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_NoPreviousPages"), false); TextUtils.pause();}
                            
                
                        } else if(input.equalsIgnoreCase("next")){
                            
                            if(pagina == (Math.ceil((playlistScelta.getSize()/9.00))-1)){TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_NoNextPages"), false); TextUtils.pause();} else{pagina++;}
                
                        } else if(input.equalsIgnoreCase("complete")){

                            System.out.print(EmotionalSongs.languageBundle.getString("PromptReturn"));

                            if(TextUtils.readYesOrNo(true)){
                                break;
                            }
                
                        }else {TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_UnrecognizedCommand"), false); TextUtils.pause();}

                    }

                   
                }

            }
            

        }else{ // Se l'utente non possiede alcuna playlist registrata

            TextUtils.printLogo(EmotionalSongs.languageBundle.getString("ViewPlaylistSubtitle"));

            System.out.println(TextUtils.YELLOW + EmotionalSongs.languageBundle.getString("NoPlaylistPresentInArchive")+ TextUtils.RESET +EmotionalSongs.languageBundle.getString("RegistrationNecessary"));
            
            TextUtils.pause();

        }

    }
    
    /**
     * Questo metodo permette all'utente di registrare le emozioni provate per i brani presenti all'interno di una playlist
     * @param p la playlist desiderata
     */
    protected static void registerEmotionsInPlaylist(Playlist p){

        if(p==null){return;}

        String subtitle = EmotionalSongs.languageBundle.getString("RegisterEmotionSubtitle");

        String input;
        int inputNumerico;

        int pagina = 0;
    
        while(true){ // Questo ciclo while verrà interrotto solamente quando l'utente avrà completato la procedura di inserimento delle emozioni mediante il comando 'complete'

            TextUtils.printLogo(subtitle);
            System.out.println(String.format(EmotionalSongs.languageBundle.getString("ListOfSongsInPlaylist"), p.getNome()));
            
            TextUtils.elencaRisultati(pagina, p.asList());
       
            System.out.print(EmotionalSongs.languageBundle.getString("SongSelectionFromPlaylistFeedback"));
           
            input = TextUtils.readStringInput(true);

            if(TextUtils.isNumeric(input)){ 

                inputNumerico = Integer.parseInt(input);

                if(inputNumerico<1 || inputNumerico>p.getSize()){

                    TextUtils.printErrorMessage(String.format(EmotionalSongs.languageBundle.getString("ERROR_ValueMustBeInsideIntervalPrompt"), 1, p.getSize()), false);
                    inputNumerico = TextUtils.readInt(1, p.getSize(), true);

                }

                TextUtils.printLogo(subtitle);
                EmotionsRepositoryManager.inserisciEmozioniBrano(p.getCanzone((inputNumerico+(9*pagina))-1));


            }else {

                /*
                 * Questo blocco di codice gestisce l'incremento e il decremento delle pagine e il termine della procedura di registrazione delle emozioni.
                 */

                if(input.equalsIgnoreCase("previous")){
    
                    if(pagina>0){pagina--;} else{TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_NoPreviousPages"), false); TextUtils.pause();}
        
                } else if(input.equalsIgnoreCase("next")){
        
                    if(pagina == (Math.ceil((p.getSize()/9.00))-1)){TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_NoNextPages"), false); TextUtils.pause();} else{pagina++;}
        
                } else if(input.equalsIgnoreCase("complete")){

                    System.out.print(EmotionalSongs.languageBundle.getString("CompleteFeedbackPrompt"));
                    if(TextUtils.readYesOrNo(true)){
                        break; // Questo è costituito da un solo break in quanto, come detto in un commento precedente, il salvataggio del feedback è gestito dalla procedura inserisciEmozioniBrano(Canzone)
                    }
        
                } else {TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_UnrecognizedCommand"), false); TextUtils.pause();}

            }

        }

    }
        
}

