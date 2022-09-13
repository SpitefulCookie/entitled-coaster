package emotionalsongs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.StringUtils;


/*
 * Progetto svolto da:
 * 
 * Della Chiesa Mattia 749904, Ateneo di Varese
 * 
 */

 /**
  * La classe enumerativa {@code Emotions} contiene una lista delle emozioni supportate dall'applicazione:
  * <ul>
  * <li> {@link #AMAZEMENT} - descrive una sensazione equiparabile alla sorpresa.
  * <li> {@link #SOLEMNITY} - descrive una sensazione equiparabile alla solennità.
  * <li> {@link #TENDERNESS} - descrive una sensazione equiparabile alla gentilezza.
  * <li> {@link #NOSTALGIA} - descrive una sensazione equiparabile alla nostalgia.
  * <li> {@link #CALMNESS} - descrive una sensazione equiparabile alla calma.
  * <li> {@link #POWER} - descrive una sensazione equiparabile al potere.
  * <li> {@link #JOY} - descrive una sensazione equiparabile alla gioia.
  * <li> {@link #TENSION} - descrive una sensazione equiparabile alla tensione.
  * <li> {@link #SADNESS} - descrive una sensazione equiparabile alla tristezza.
  * </ul>
  */
enum Emotions{

    AMAZEMENT, 
    SOLEMNITY, 
    TENDERNESS, 
    NOSTALGIA, 
    CALMNESS,
    POWER,
    JOY,
    TENSION,
    SADNESS;

    /**
     * Restituisce una descrizione dell'emozione.
     * @return un oggetto di tipo {@code String} che contiene una breve descrizione di un'emozione.
     */
    public String description(){
        
        switch(this){
            case AMAZEMENT:
                return "Feeling of wonder or happiness";
            case CALMNESS:
                return "Relaxation, serenity, meditativeness";
            case JOY:
                return "Feels like dancing, bouncy feeling, animated, amused";
            case NOSTALGIA:
                return "Dreamy, melancholic, sentimental feelings";
            case POWER:
                return "Feeling strong, heroic, triumphant, energetic";
            case SADNESS:
                return "Feeling Depressed, sorrowful";
            case SOLEMNITY:
                return "Feeling of transcendence, inspiration. Thrills.";
            case TENDERNESS:
                return "Sensuality, affect, feeling of love";
            case TENSION:
                return "Feeling Nervous, impatient, irritated";
            default:    
                return "";
        }
        
    }

    /**
     * Restituisce il nome di un'emozione.
     * @return un oggetto di tipo {@code String} che contiene il nome dell'emozione con solo la prima lettera maiuscola.
     */
    @Override
    public String toString(){
        
        switch(this){
            case AMAZEMENT:
                return "Amazement"; 
            case CALMNESS:
                return "Calmness"; 
            case JOY:
                return "Joy"; 
            case NOSTALGIA:
                return "Nostalgia"; 
            case POWER:
                return "Power"; 
            case SADNESS:
                return "Sadness"; 
            case SOLEMNITY:
                return "Solemnity"; 
            case TENDERNESS:
                return "Tenderness";
            case TENSION:
                return "Tension"; 
            default:    
                return "";
        }
        
    }

    /**
     * Restituisce una stringa contenente un codice ANSI che permette di effettuare il print di una stringa con il colore associato all'emozione.
     * Qualora l'utente abbia disattivato i codici ANSI all'interno dell'applicazione verrà restituita una stringa vuota.
     * @return un oggetto di tipo {@code String} che un codice ANSI che pemette di colorare del testo con il colore associato ad un'emozione.
     */
    public String emotionColors(){

        if(TextUtils.isANSIDisabled()){

            return "";

        } else{
        
            switch(this){

                case AMAZEMENT:
                    return TextUtils.fromRGB(102, 204, 0); //TextUtils.getRGB(0, 220, 0); 
                case CALMNESS:
                    return TextUtils.fromRGB(102, 204, 255);//TextUtils.getRGB(0, 153, 153); //TextUtils.getRGB(93, 173, 226); 
                case JOY:
                    return TextUtils.fromRGB(255, 204, 0); //TextUtils.getRGB(255, 195, 0); 
                case NOSTALGIA:
                    return TextUtils.fromRGB(102, 102, 255); //TextUtils.BLUE; 
                case POWER:
                    return TextUtils.fromRGB(255, 51, 0); 
                case SADNESS:
                    return TextUtils.fromRGB(0, 51, 204); //TextUtils.BLUE; 
                case SOLEMNITY:
                    return TextUtils.fromRGB(0, 153, 102); //TextUtils.CYAN;
                case TENDERNESS:
                    return TextUtils.fromRGB(204, 0, 153); //TextUtils.PURPLE;
                case TENSION:
                    return TextUtils.fromRGB(255, 0, 51); //TextUtils.getRGB(231, 76, 60); 
                default:    
                    return "";
            }

        }
        
    }

    /**
     * Restituisce una stringa contenente un codice ANSI che permette di effettuare il print di una stringa con il colore associato all'emozione desiderata.
     * Qualora l'utente abbia disattivato i codici ANSI all'interno dell'applicazione verrà restituita una stringa vuota.
     * @param ordinalValue un valore di tipo {@code int} che indica la posizione ordinale (da {@code 0} a {@code 8}) di un'emozione. 
     * @return un oggetto di tipo {@code String} che un codice ANSI che pemette di colorare del testo con il colore associato ad un'emozione.
     */
    public static String getColorFromOrdinal(int ordinalValue) {
        
        if(TextUtils.isANSIDisabled()){

            return "";

        } else{
        
            switch(ordinalValue){
                
                case 0:
                    return TextUtils.fromRGB(102, 204, 0);       // Amazement 
                case 1: 
                    return TextUtils.fromRGB(0, 153, 102);       // Solemnity
                case 2: 
                    return TextUtils.fromRGB(204, 0, 153);       // Tenderness
                case 3: 
                    return TextUtils.fromRGB(102, 102, 255);     // Nostalgia
                case 4: 
                    return TextUtils.fromRGB(102, 204, 255);     // Calmness
                case 5: 
                    return TextUtils.fromRGB(255, 51, 0);        // Power    
                case 6: 
                    return TextUtils.fromRGB(255, 204, 0);       // Joy 
                case 7: 
                    return TextUtils.fromRGB(255, 0, 51);        // Tension  
                case 8: 
                    return TextUtils.fromRGB(0, 51, 204);        //Sadness  
                default:    
                    return "";
            }

        }

    }

}

/**
 * La classe {@code SongEmotions} descrive il feedback fornito da parte di un utente durante l'ascolto di una canzone presente all'interno della repository delle canzoni.<p>
 * Ogni istanza di quest'oggetto viene descritto da quattro campi:
 * <ul>
 * <li> L'UUID della canzone
 * <li> L'userid associato all'utente che ha fornito il feedback.
 * <li> I punteggi associati a ciascuna delle nove emozioni
 * <li> I commenti relativi a ciascuna di queste emozioni.
 * </ul><p>
 * I punteggi variano da {@code 0} a {@code 5} dove {@code 0} rappresenta la mancanza di feedback nei confronti di un emozione; {@code 1} indica il quantitativo "<i>per niente</i>" e {@code 5} indica il quantitativo "<i>molto</i>".<p>
 * I commenti possono raggiungere una lunghezza massiama di 256 caratteri e, nel caso l'utente non abbia fornito alcun commento nei confronti di un'emozione, essi verranno rappresentati mediante una stringa vuota. 
 * @author <a href="https://github.com/SpitefulCookie">Della Chiesa Mattia</a> 
 */
class SongEmotions{

    private String songUUID;
    private String userId;

    
    private int[] emotionsFelt = new int[9];   
    private String[] userComments = new String[9];

    /**
     * Costruisce un oggetto {@code SongEmotions} a partire da i singoli elementi che lo costituiscono.<p>
     * L'oggetto costruito descrive le emozioni provate e i commenti forniti da parte dell'utente in seguito all'ascolto di una canzone presente all'interno della repository delle canzoni.
     * @param songUUID un oggetto di tipo {@code String} che contiene l'UUID di tipo 3 associato ad una canzone presente all'iterno della repository canzoni.
     * @param userId un oggetto di tipo {@code String} che contiene l'userId dell'utente che ha fornito il feedback.
     * @param emotions un array di tipo {@code int} contenente i punteggi associati a ciascuna delle nove emozioni supportate dalla piattaforma. I valori di queste sono mappati secondo l'ordine definito dalla classe enumerativa {@link Emotions}.
     * @param comments un array di oggetti di tipo {@code String} contenente i commenti forniti da parte dell'utente nei confronti di ciascuna di queste emeozioni.
     */
    public SongEmotions(String songUUID, String userId, int[] emotions, String[] comments){ //throws exception??

        this.userId = userId;
        
        this.emotionsFelt = emotions;

        this.userComments = comments;

        this.songUUID = songUUID;

    }

    /**
     * Costruisce un oggetto {@code SongEmotions} a partire da una stringa, formattata secondo lo standard CSV, fornita in input.<p>
     * L'oggetto costruito descrive le emozioni provate e i commenti forniti da parte dell'utente in seguito all'ascolto di una canzone presente all'interno della repository delle canzoni.
     * @param line un oggetto di tipo {@code String}, formattato secondo lo standard CSV, contentente le informazioni relative al feedback utente.
     */
    public SongEmotions(String line){

        this.songUUID = line.substring(0, line.indexOf(",", 0));

        /*
         * Essendo tutti i commenti nel formato:
         * 
         *      {a},{b},{c},{d},{e},{f},{g},{h},{i}
         *      
         * 
         * di seguito verrà rimossa l'apertura delle parentesi graffe:
         * 
         *      a},b},c},d},e},f},g},h},i}
         * 
         * la parentesi graffa rimasta in coda tramite il metodo StringUtils.chop(String):
         * 
         *      a},b},c},d},e},f},g},h},i
         * 
         * ed effettuato lo split della stringa su "}," ottenendo così l'array di stringhe:
         * 
         *      ["a","b","c","d","e","f","g","h","i"]
         * 
         */

        this.userComments = StringUtils.chop(StringUtils.remove(line.substring(line.indexOf("{"), line.length()), "{")).split("},", -1);

        int userIdEnd = line.indexOf(",", this.songUUID.length()+1); //calcola la posizione dove termina l'user id.
        this.userId = line.substring(this.songUUID.length()+1, userIdEnd);

        String[] punteggiCanzone = line.substring(userIdEnd+1, line.indexOf("{")).split(",");

        for(int i= 0; i<this.emotionsFelt.length; i++){
            this.emotionsFelt[i] = Integer.parseInt(punteggiCanzone[i]);
        }

    }

    /**
     * Restituisce l'userid associato alla emozioni provate per una canzone.
     * @return un oggetto di tipo {@code String} contenente l'userid associato alle emozioni di una data canzone.
     * 
     */
    public String getUserid(){return this.userId;}

    /**
     * Restituisce l'UUID di tipo 3 che associa le emozioni provate da un utente ad una canzone presente all'interno della repository canzoni.
     * @return un oggetto di tipo {@code String} contenente l'UUID associato ad una canzone presente nella repository delle canzoni.
     * 
     */
    public String getSongUUID(){return this.songUUID;}

    /**
     * Restituisce il punteggio relativo ad una singola emozione provata da parte dell'utente durante l'ascolto di una canzone.
     * I valori delle emozioni sono mappati secondo l'ordine definito dalla classe enumerativa {@link Emotions}.
     * @param emotion un oggetto di tipo {@code Emotion} che descrive una delle nove emozioni supportate dalla piattaforma.
     * @return un valore di tipo {@code int} che rappresenta il punteggio attribuiti dall'utente nei confronti dell'emeozione desiderata.
     */
    public int getEmotionScore(Emotions emotion){return this.emotionsFelt[emotion.ordinal()];}

    /**
     * Restituisce un array di interi contenente i punteggi attribuiti da parte dell'utente nei confronti delle singole emozioni.
     * I valori delle emozioni sono mappati secondo l'ordine definito dalla classe enumerativa {@link Emotions}.
     * @return un array di tipo {@code int} che rappresenta i punteggi attribuiti dall'utente.
     */
    public int[] getAllEmotions(){return this.emotionsFelt;}
    
    /**
     * Restituisce un array di stringhe contenente i commenti forniti dall'utente nei confronti di una canzone ascoltata.
     * @return un array di oggetti di tipo {@code String} che rappresentano i commenti forniti da un utente.
     */
    public String[] getComments() {return this.userComments;}

    /**
     * Restituisce un valore booleano che indica se l'array contiene solamente stringhe vuote.
     * @return un valore di tipo {@code boolean} che indica se l'array contiene solamente stringhe vuote.
     */
    public boolean containsComments(){
        for(String s : this.userComments){
            if(!s.isEmpty()){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Restituisce il commento associato ad una specifica emozione.
     * @param emotion un oggetto di tipo {@code Emotion} che descrive l'emozione desiderata.
     * @return un oggetto di tipo {@code String} contenente il commento associato all'emozione desiderata.
     */
    public String getEmotionComment(Emotions emotion) {return this.userComments[emotion.ordinal()];}

    /**
     * Restituisce una stringa, formattata secondo lo standard CSV, contenente tutti i dati relativi al feedback fornito dall'utente nei confronti di una canzone.
     * @return un oggetto di tipo {@code String} in formato CSV che contiene i dati realativi al feedback utente.
     */
    public String toCSV(){

        StringBuilder sb = new StringBuilder();
        sb.append(this.songUUID+ "," + this.userId+",");

        for(int i = 0; i<Emotions.values().length; i++){sb.append(this.emotionsFelt[i]+",");}
        
        for(int i = 0; i<Emotions.values().length; i++){sb.append("{"+this.userComments[i]+"},");}
        sb.delete(sb.length()-1, sb.length());
        return sb.toString();

    }

}

/**
 * La classe {@code EmotionsRepositoryManager} si occupa di implementare tutte le funzionalità necessarie alla gestione della repository 
 * delle emozioni come la visualizzazione e l'inserimento delle emozioni provate durante l'ascolto di un brano e le funzionalità di I/O.
 */
public class EmotionsRepositoryManager{
    
    private static final String EMOTIONS_REPOSITORY_PATH = System.getProperty("user.dir")+"\\data\\Emozioni.dati";
    private static final int UUID_LENGTH = 36;
    private static final char BAR_UNIT_FILLED = '█';
    private static final char BAR_UNIT_EMPTY = '░';

    // Questa è la repository delle emozioni.
    private static HashMap<String, List<SongEmotions>>  repositoryEmozioni = new HashMap<>(); 
    /*
     * Per ogni canzone che possiede una valutazione viene tenuta traccia, mediante un lista di emozioni, dei punteggi assegnati dai singoli utenti. 
     * Questo serve per il prospetto riassuntivo "emozionale" della canzone.
     */

    /**
     * Costruisce un nuovo oggetto di tipo {@code EmotionsRepositoryManager}.
     */
    private EmotionsRepositoryManager(){super();}

    /**
     * Restituisce la media dei punteggi relativi alle emozioni provate dagli utenti della piattaforma durante l'ascolto di una data canzone.
     * @param songUUID un oggetto di tipo {@code String} contente l'UUID di tipo 3 associato ad una canzone presente all'interno della repository.
     * @return una matrice 9x2 di tipo {@code double} dove la prima componente contiene la media dei punteggi associati ad un'emozione 
     * (mappati secondo l'ordine definito dalla classe {@link emotionalsongs.Emotions}), mentre la seconda componente contiene il numero 
     * di utenti cha hanno provato l'emozione durante l'ascolto del brano musicale.
     */
    private static double[][] getSongsAverageScores(String songUUID){

        double[][] averages = new double[9][2];
        Arrays.fill(averages[0], 0);

        if(repositoryEmozioni.containsKey(songUUID)){

            List<SongEmotions> emotionList = repositoryEmozioni.get(songUUID);

            for(int i = 0; i<9;i++){

                for(SongEmotions se : emotionList){
                    averages[i][0] += se.getEmotionScore(Emotions.values()[i]);
                    if(se.getEmotionScore(Emotions.values()[i])>0){averages[i][1]++;}
                }

                averages[i][0] = averages[i][0]/emotionList.size();

            }

        } else{

            Arrays.fill(averages[0], -1);

        }
        
        return averages;
 
    }

    /**
     * Costruisce la repository delle emozioni a partire da un file presente in memoria di massa.
     * @return un valore {@code boolean} che stabilisce se la procedura di inizializzazione della repository è andata a buon fine.
     */
    public static boolean build(){

        TextUtils.printDebug(EmotionalSongs.languageBundle.getString("DEBUG_BeginEmotionRepositoryInitialization"), true, true);

        try{

            File fileEmozioni = new File(EMOTIONS_REPOSITORY_PATH);
            
            if(!fileEmozioni.exists()){

                fileEmozioni.createNewFile();
                TextUtils.printDebug(String.format(EmotionalSongs.languageBundle.getString("DEBUG_FileNotFound"), "Emozioni.dati"), true, true);

            }
             
        } catch (IOException f){ // Documentazione

            TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_IOExceptionBuildEmotionRepo"), true);

        }

        try (BufferedReader br = new BufferedReader(new FileReader(EMOTIONS_REPOSITORY_PATH))) {

            long start = System.currentTimeMillis();
            
            String line;

            while ((line = br.readLine()) != null) {

                String songUUID = line.substring(0, UUID_LENGTH);

                if(!repositoryEmozioni.containsKey(songUUID)){ 

                    repositoryEmozioni.put(songUUID, new ArrayList<>());
                    repositoryEmozioni.get(songUUID).add(new SongEmotions(line));

                } else{

                    //TextUtils.printDebug(EmotionalSongs.languageBundle.getString("DEBUG_RepositoryContainsElement") + songUUID + EmotionalSongs.languageBundle.getString("DEBUG_RepositoryContainsElement2"), false);
                    repositoryEmozioni.get(songUUID).add(new SongEmotions(line));

                }

            }

            TextUtils.printDebug(EmotionalSongs.languageBundle.getString("DEBUG_RepositoryBuildSuccess"), true, true); 

            TextUtils.printDebug(String.format(EmotionalSongs.languageBundle.getString("DEBUG_EmotionsInRepository"), repositoryEmozioni.size()), false, true); 

            TextUtils.printDebug(EmotionalSongs.languageBundle.getString("DEBUG_ProcessDuration") + (System.currentTimeMillis() - start) + "ms\n", false, true); 

            return true;

        } catch (IOException e)  { //Documentazione

            TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_GenericIOError"), false);

            return false;

        }

    }

    /**
     * Permette di visualizzare le emozioni provate da parte degli utenti della piattaforma durante l'ascolto di una canzone.
     * Qualora canzone non possiede emozioni registrate e un utente ha effettuato il log in sulla piattaforma, a quest'ultimo 
     * verra chiesto se desidera registrare delle emozioni provate durante l'ascolto della canzone.
     * @param canzoneScelta un oggetto di tipo {@code Canzone} che rappresenta una canzone presente all'interno della repository.
     */
    public static void visualizzaEmozioneBrano(Canzone canzoneScelta){

        if(canzoneScelta==null){return;}
        
        double[][] scores = getSongsAverageScores(canzoneScelta.getSongUUID());

        if(scores[0][0]==-1){ // Questo identifica il caso in cui non ci sono emozioni registrate per la canzone scelta

            TextUtils.printLogo(EmotionalSongs.languageBundle.getString("SongDetailsSubtitle"));
            System.out.println(EmotionalSongs.languageBundle.getString("NoEmotionsRegisteredForSong") + "\n\n " +
                               TextUtils.YELLOW + TextUtils.centerString(canzoneScelta.toString()) + TextUtils.RESET + "\n");

            if(EmotionalSongs.getLoggedUser() != null){ // Se l'utente è loggato, chiede se desidera registrare delle emozioni

                System.out.print(EmotionalSongs.languageBundle.getString("PromptUserToRegisterEmotions"));

                if(TextUtils.readYesOrNo(true)){

                    if(PlaylistManager.getUserPlaylists().size()!=0){

                        Playlist playlistScelta = PlaylistManager.selezionaPlaylist();
                        playlistScelta.addCanzone(canzoneScelta);
                        inserisciEmozioniBrano(canzoneScelta);

                    } else{

                        TextUtils.printLogo(EmotionalSongs.languageBundle.getString("SongDetailsSubtitle"));

                        System.out.println(EmotionalSongs.languageBundle.getString("ERROR_NoPlaylistsPresent"));
                        TextUtils.pause();
                    }
                    
                }

            } else{
                System.out.println(EmotionalSongs.languageBundle.getString("PromptUserToRegisterAccount"));
                TextUtils.pause();
            }
            
        } else{ // Esistono delle emozioni registrate per la canzone scelta

            boolean isInvalidInput = false;

            do{
            
                TextUtils.printLogo(EmotionalSongs.languageBundle.getString("SongDetailsSubtitle"));

                EmotionsRepositoryManager.printScores(canzoneScelta, scores);

                List<SongEmotions> listaEmozioniCanzone = EmotionsRepositoryManager.retrieveSongEmotions(canzoneScelta);


                System.out.print(EmotionalSongs.languageBundle.getString("ListCommandsViewSongEmotion"));

                String input = TextUtils.readStringInput(true);

                boolean showComments = false;

                do{

                    if(input.equalsIgnoreCase("show comments")){

                        isInvalidInput = false;
                        showComments = true;

                    } else if(input.equalsIgnoreCase("register emotions")){

                        if(EmotionalSongs.getLoggedUser() != null ){

                            if(PlaylistManager.getUserPlaylists().size()!=0){

                                Playlist playlistScelta = PlaylistManager.selezionaPlaylist();
                                playlistScelta.addCanzone(canzoneScelta);
                                inserisciEmozioniBrano(canzoneScelta);

                                isInvalidInput = false;
        
                            } else{

                                TextUtils.printLogo(EmotionalSongs.languageBundle.getString("SongDetailsSubtitle"));

                                System.out.println(EmotionalSongs.languageBundle.getString("ERROR_NoPlaylistsPresent"));
                                TextUtils.pause();

                                isInvalidInput = false; 
                            }

                        } else{

                            TextUtils.printLogo(EmotionalSongs.languageBundle.getString("SongDetailsSubtitle"));
                            System.out.println(EmotionalSongs.languageBundle.getString("LoginRequired"));
                            TextUtils.pause();

                            isInvalidInput = false; 
                        }

                    
                    }else if(input.equalsIgnoreCase("cancel")){

                        isInvalidInput = false;
                        

                    } else{
                    
                        TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_UnrecognizedCommandPrompt"), false);
                        input = TextUtils.readStringInput(true);

                        isInvalidInput = true;
                        
                    }

                } while(isInvalidInput);

                int i = 0;

                while(showComments){

                    SongEmotions emozioniProvate = listaEmozioniCanzone.get(i);
                    String[] commentiCanzone = emozioniProvate.getComments();
                    boolean containsComments = false;

                    do{
                        
                        if(TextUtils.isEmptyArray(commentiCanzone) && i+1 <= listaEmozioniCanzone.size()){ // le seconda parte della condizione è necessaria perchè altrimenti non valuterebbe la seconda condizione
                            
                            i++;

                        }else if (i+1 > listaEmozioniCanzone.size()){

                            TextUtils.printLogo(EmotionalSongs.languageBundle.getString("SongDetailsSubtitle"));
                            System.out.println(EmotionalSongs.languageBundle.getString("NoSongCommentsFound"));
                            TextUtils.pause();
                            return;

                        } else{

                            containsComments = true;

                        }

                    }while(!containsComments);

                    TextUtils.printLogo(EmotionalSongs.languageBundle.getString("SongDetailsSubtitle"));
                    
                    System.out.println( EmotionalSongs.languageBundle.getString("CommentsLeftByUser")+
                                        TextUtils.fromRGB(ThreadLocalRandom.current().nextInt(0, 256), ThreadLocalRandom.current().nextInt(0, 256), ThreadLocalRandom.current().nextInt(0, 256)) 
                                        + emozioniProvate.getUserid()+TextUtils.RESET+":\n");

                    for(var j = 0; j<9; j++){

                        if(!TextUtils.isEmptyString(commentiCanzone[j])){

                            System.out.print(" "+Emotions.values()[j].emotionColors() + Emotions.values()[j].toString() + ":    \t" + TextUtils.RESET );
                            System.out.print(
                                TextUtils.splitLongStringInput( 
                                    commentiCanzone[j].replaceFirst(commentiCanzone[j].charAt(0)+"", Character.toUpperCase(commentiCanzone[j].charAt(0))+""), "\n" + // Assicura che il commento inizi con lettera maiuscola
                                    StringUtils.repeat(" ", Emotions.values()[j].toString().length()+2)+"\t", false  // Fa si che ogni riga del commento abbia una tab. pari alla lunghezza del nome dell'emozione, il +2 deriva dal fatto che vi è anche ": "
                                ) + "\n"
                            );
                        }

                    }

                    System.out.print(EmotionalSongs.languageBundle.getString("NavigateResults"));
                    input = TextUtils.readStringInput(true);

                    do{
                        
                        if(input.equalsIgnoreCase("next")){
                            
                            if(i+1<listaEmozioniCanzone.size()){i++;} else{TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_NoMoreComments"), false);TextUtils.pause();}
                            isInvalidInput = false;
                            
                        } else if(input.equalsIgnoreCase("previous")){

                            if(i>0){i--;} else{TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_NoPreviousPages"), false);TextUtils.pause();}
                            isInvalidInput = false;

                        }else if(input.equalsIgnoreCase("cancel")){

                            showComments = false;
                            isInvalidInput = false;

                        }else{
                            isInvalidInput = true;
                            TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_UnrecognizedCommandPrompt"), false);
                            input = TextUtils.readStringInput(true);
                            
                            
                        }

                    } while(isInvalidInput);

                    isInvalidInput = true;

                } 
                
                System.out.println();

            }while(isInvalidInput);

        }

    }

    /**
     * Questo metodo è una versione più semplificata del metodo visualizzaEmozioneBrano(Canzone) in quanto può essere invocato
     * esclusivamente da utente registrati stanno visualizzando i dettagli di una canzone all'interno di una loro playlist.
     * @param p la playlist in cui si trova la canzone da visualizzare.
     * @param canzoneScelta un oggetto di tipo {@code Canzone} che rappresenta una canzone presente all'interno della repository.
     */
    public static void visualizzaEmozioneBrano(Playlist p, Canzone canzoneScelta) {
        
        if(canzoneScelta==null){return;}
        
        double[][] scores = getSongsAverageScores(canzoneScelta.getSongUUID());

        if(scores[0][0]==-1){ // Questo identifica il caso in cui non ci sono emozioni registrate per la canzone scelta

            TextUtils.printLogo(EmotionalSongs.languageBundle.getString("SongDetailsSubtitle"));
            System.out.println(EmotionalSongs.languageBundle.getString("NoEmotionsRegisteredForSong") + "\n\n " +
                               TextUtils.YELLOW + TextUtils.centerString(canzoneScelta.toString()) + TextUtils.RESET + "\n");


            System.out.print(EmotionalSongs.languageBundle.getString("PromptUserToRegisterEmotions"));

            if(TextUtils.readYesOrNo(true)){

                p.addCanzone(canzoneScelta);
                inserisciEmozioniBrano(canzoneScelta);

            }
            
        } else{ // Esistono delle emozioni registrate per la canzone scelta

            boolean isInvalidInput = false;
            boolean continueDisplaying = true;

            do{
            
                TextUtils.printLogo(EmotionalSongs.languageBundle.getString("SongDetailsSubtitle"));

                EmotionsRepositoryManager.printScores(canzoneScelta, scores);

                List<SongEmotions> listaEmozioniCanzone = EmotionsRepositoryManager.retrieveSongEmotions(canzoneScelta);


                System.out.print(EmotionalSongs.languageBundle.getString("ListCommandsViewSongEmotion"));

                String input = TextUtils.readStringInput(true);

                boolean showComments = false;

                do{

                    if(input.equalsIgnoreCase("show comments")){
                        isInvalidInput = false;
                        continueDisplaying = true;
                        showComments = true;

                    } else if(input.equalsIgnoreCase("register emotions")){ 

                        p.addCanzone(canzoneScelta);
                        inserisciEmozioniBrano(canzoneScelta);

                        continueDisplaying = true; 
                        isInvalidInput = false;
                       
                    }else if(input.equalsIgnoreCase("cancel")){

                        continueDisplaying = false;
                        isInvalidInput = false;      

                    } else{
                    
                        TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_UnrecognizedCommandPrompt"), false);
                        input = TextUtils.readStringInput(true);

                        continueDisplaying = true;
                        isInvalidInput = true;
                    }

                } while(isInvalidInput);

                int i = 0;

                while(showComments){

                    SongEmotions emozioniProvate = listaEmozioniCanzone.get(i);
                    String[] commentiCanzone = emozioniProvate.getComments();
                    boolean containsComments = false;

                    do{
                        
                        if(TextUtils.isEmptyArray(commentiCanzone) && i+1 <= listaEmozioniCanzone.size()){ // le seconda parte della condizione è necessaria perchè altrimenti non valuterebbe la seconda condizione
                            
                            i++;

                        }else if (i+1 > listaEmozioniCanzone.size()){

                            TextUtils.printLogo(EmotionalSongs.languageBundle.getString("SongDetailsSubtitle"));
                            System.out.println(EmotionalSongs.languageBundle.getString("NoSongCommentsFound"));
                            TextUtils.pause();
                            return;

                        } else{

                            containsComments = true;

                        }

                    }while(!containsComments);

                    TextUtils.printLogo(EmotionalSongs.languageBundle.getString("SongDetailsSubtitle"));
                    
                    System.out.println( EmotionalSongs.languageBundle.getString("CommentsLeftByUser")+
                                        TextUtils.fromRGB(ThreadLocalRandom.current().nextInt(0, 256), ThreadLocalRandom.current().nextInt(0, 256), ThreadLocalRandom.current().nextInt(0, 256)) 
                                        + emozioniProvate.getUserid()+TextUtils.RESET+":\n");

                    for(var j = 0; j<9; j++){

                        if(!TextUtils.isEmptyString(commentiCanzone[j])){

                            System.out.print(" "+Emotions.values()[j].emotionColors() + Emotions.values()[j].toString() + ":    \t" + TextUtils.RESET );
                            System.out.print(
                                TextUtils.splitLongStringInput( 
                                    commentiCanzone[j].replaceFirst(commentiCanzone[j].charAt(0)+"", Character.toUpperCase(commentiCanzone[j].charAt(0))+""), "\n" + // Assicura che il commento inizi con lettera maiuscola
                                    StringUtils.repeat(" ", Emotions.values()[j].toString().length()+2)+"\t", false  // Fa si che ogni riga del commento abbia una tab. pari alla lunghezza del nome dell'emozione, il +2 deriva dal fatto che vi è anche ": "
                                ) + "\n"
                            );
                        }

                    }

                    System.out.print(EmotionalSongs.languageBundle.getString("NavigateResults"));
                    input = TextUtils.readStringInput(true);

                    do{

                        if(input.equalsIgnoreCase("next")){
                            
                            if(i+1<listaEmozioniCanzone.size()){i++;} else{TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_NoMoreComments"), false);TextUtils.pause();}
                            isInvalidInput = false;
                            
                        } else if(input.equalsIgnoreCase("previous")){

                            if(i>0){i--;} else{TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_NoPreviousPages"), false);TextUtils.pause();}
                            isInvalidInput = false;

                        }else if(input.equalsIgnoreCase("cancel")){

                            showComments = false;
                            isInvalidInput = false;

                        }else{

                            TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_UnrecognizedCommandPrompt"), false);
                            input = TextUtils.readStringInput(true);
                            isInvalidInput = true;
                            
                        }

                    } while(isInvalidInput);

                    continueDisplaying = true;

                } 
                
                System.out.println();

            }while(continueDisplaying);

        }

    }

    /**
     * Permette di visualizzare, mediante un grafico a barre, il prospetto riassuntivo delle emozioni provate durante l'ascolto di una canzone presente all'interno della repository.
     * 
     * @param canzoneScelta un oggetto di tipo {@code Canzone} che rappresenta una canzone presente all'interno della repository.
     * @param scores una matrice 9x2 contenente la media dei punteggi associati alle emozioni provate dagli utenti.
     */
    private static void printScores(Canzone canzoneScelta, double[][] scores) {

        StringBuilder sb = new StringBuilder();

            System.out.println(EmotionalSongs.languageBundle.getString("EmotionsFeltForSong")+TextUtils.YELLOW+canzoneScelta.toString()+"\n"+TextUtils.RESET);

        for (int i = 0; i < scores.length; i++) {

            int filledBars = (int)(scores[i][0]*5);
            sb.delete(0, sb.capacity()); //Svuota lo stringBuilder (contiene caratteri dell'iterazione precedente)

            sb.append("|");
            sb.append(TextUtils.getBarColor((int)scores[i][0]));

            if(filledBars != 0){
                sb.append(StringUtils.repeat(BAR_UNIT_FILLED, filledBars));
            }

            sb.append(StringUtils.repeat(BAR_UNIT_EMPTY, (25-filledBars)));
            sb.append(TextUtils.RESET);

            if(scores[i][0]%1 == 0){
                sb.append("|    "+(int)scores[i][0]+"/5");
            } else{
                sb.append("|  "+scores[i][0]+"/5");
            }

            System.out.print("\t"+Emotions.values()[i].emotionColors()+ Emotions.values()[i].toString()+ TextUtils.RESET + ":"+StringUtils.repeat(" ", 15-Emotions.values()[i].toString().length())+ sb.toString());
            if(scores[i][1]!=0){
                System.out.println(" (" + (int)scores[i][1] +")");
            }else{
                System.out.println();
            }
            
        }
            
    }

    /**
     * Restituisce una lista di feedback lasciati dagli utenti della piattaforma nei confronti di un brano presente all'interno della repository.
     * @param brano un oggetto di tipo {@code Canzone} che rappresenta una canzone presente all'interno della repository.
     * @return un oggetto di tipo {@code List<SongEmotions>} contenente i feedback individuali lasciati dagli utenti.
     */
    public static List<SongEmotions> retrieveSongEmotions(Canzone brano){return repositoryEmozioni.get(brano.getSongUUID());}

    /**
     * Guida l'utente nella registrazione delle emozioni provate durante l'ascolto di una canzone presente all'interno della repository.<p>
     * Una volta registrato un punteggio associato ad una emozione, all'utente verrà chiesto se desidera includere anche un commento.
     * @param canzoneSelezionata un oggetto di tipo {@code Canzone} che descrive una canzone presente all'interno della repository.
     */
    public static void inserisciEmozioniBrano(Canzone canzoneSelezionata){
        
        String input;
        int idxEmozioneScelta = -1;
        int valore;
        boolean stop = false;
        boolean hasGivenFeedback = false;
        /*
         * Questa variabile permette di identificare se l'utente ha già fornito un feedback per la canzone selezionata. 
         * In tal caso, una volta registrate le emozioni, all'utente verrà chiesto se desidera sovrascrivere il suo feedback precedente.
         */
        boolean substitute = false; 

        int[] emotions = new int[9];
        String[] comments = new String[9];

        Arrays.fill(emotions, 0);
        Arrays.fill(comments, ""); 

        List<SongEmotions> listaEmozioni = repositoryEmozioni.get(canzoneSelezionata.getSongUUID());
        if(listaEmozioni!=null){
            for(var emozioni : listaEmozioni){
                if (emozioni.getUserid().equals(EmotionalSongs.getLoggedUser().getUserId())){
                    substitute = true;
                }
            }
        }
        
        String spaces = StringUtils.repeat(" ", TextUtils.SPACESBEFORELOGO+5);

        do{

            TextUtils.printLogo(EmotionalSongs.languageBundle.getString("RegisterEmotionSubtitle"));

            System.out.println(String.format(EmotionalSongs.languageBundle.getString("RegisteringEmotionsForSong"), TextUtils.formatTitleCapital(canzoneSelezionata.getTitolo())));

            System.out.print(   spaces + "[" + TextUtils.BLUE + "1"+ TextUtils.RESET +"] " + Emotions.getColorFromOrdinal(0) + Emotions.values()[0].toString() + TextUtils.RESET +"\n" +
                                spaces + "[" + TextUtils.BLUE + "2"+ TextUtils.RESET +"] " + Emotions.getColorFromOrdinal(1) + Emotions.values()[1].toString() + TextUtils.RESET +"\n"+
                                spaces + "[" + TextUtils.BLUE + "3"+ TextUtils.RESET +"] " + Emotions.getColorFromOrdinal(2) + Emotions.values()[2].toString() + TextUtils.RESET +"\n"+
                                spaces + "[" + TextUtils.BLUE + "4"+ TextUtils.RESET +"] " + Emotions.getColorFromOrdinal(3) + Emotions.values()[3].toString() + TextUtils.RESET +"\n"+
                                spaces + "[" + TextUtils.BLUE + "5"+ TextUtils.RESET +"] " + Emotions.getColorFromOrdinal(4) + Emotions.values()[4].toString() + TextUtils.RESET +"\n"+
                                spaces + "[" + TextUtils.BLUE + "6"+ TextUtils.RESET +"] " + Emotions.getColorFromOrdinal(5) + Emotions.values()[5].toString() + TextUtils.RESET +"\n"+
                                spaces + "[" + TextUtils.BLUE + "7"+ TextUtils.RESET +"] " + Emotions.getColorFromOrdinal(6) + Emotions.values()[6].toString() + TextUtils.RESET +"\n"+
                                spaces + "[" + TextUtils.BLUE + "8"+ TextUtils.RESET +"] " + Emotions.getColorFromOrdinal(7) + Emotions.values()[7].toString() + TextUtils.RESET +"\n"+
                                spaces + "[" + TextUtils.BLUE + "9"+ TextUtils.RESET +"] " + Emotions.getColorFromOrdinal(8) + Emotions.values()[8].toString() + TextUtils.RESET +"\n");
            
            System.out.print(EmotionalSongs.languageBundle.getString("EmotionsSelection"));
                                
            input = TextUtils.readStringInput(true);

            // L'input inserito è un numero che rappresenta l'emozione scelta
            if(TextUtils.isNumeric(input) && Integer.parseInt(input)>0 && Integer.parseInt(input)<= 9){

                idxEmozioneScelta = Integer.parseInt(input) -1;

                TextUtils.cls();
                TextUtils.printLogo(EmotionalSongs.languageBundle.getString("RegisterEmotionSubtitle"));

                System.out.print(EmotionalSongs.languageBundle.getString("ChosenEmotion")+ Emotions.values()[idxEmozioneScelta].emotionColors() + Emotions.values()[idxEmozioneScelta] + TextUtils.RESET);
                System.out.println(" - "+Emotions.values()[idxEmozioneScelta].emotionColors()+Emotions.values()[idxEmozioneScelta].description()+ TextUtils.RESET);
        
                System.out.print(EmotionalSongs.languageBundle.getString("RateEmotion"));
                
                do{

                    valore = TextUtils.readInt(true);

                    if(valore < 1 || valore > 5 ){TextUtils.printErrorMessage(String.format(EmotionalSongs.languageBundle.getString("ERROR_ValueMustBeInsideIntervalPrompt"), 1,5), false);}
                    
                } while (valore < 1 || valore > 5);

                emotions[idxEmozioneScelta] = valore;

                System.out.print(EmotionalSongs.languageBundle.getString("InsertCommentPrompt"));
        
                if(TextUtils.readYesOrNo(true)){

                    TextUtils.printLogo(EmotionalSongs.languageBundle.getString("RegisterEmotionSubtitle"));

                    boolean exceedsCharacterLimit = false;

                    do{
                    
                        System.out.print(String.format(EmotionalSongs.languageBundle.getString("InsertComment"), canzoneSelezionata.getTitolo(), (Emotions.values()[idxEmozioneScelta].emotionColors() + Emotions.values()[idxEmozioneScelta].toString())));
                        input = TextUtils.readStringInput(true);

                        /*
                        // DEBUG:
                        input = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum blandit convallis massa, vitae condimentum mauris tincidunt quis. " +
                                "Proin at ullamcorper eros. Duis vel lobortis mauris. Vestibulum aliquet venenatis augue, quis tincidunt mauris mattis sit amet. "       +
                                "Etiam sit amet eleifend eros. Integer porta ultrices quam eu accumsan."; 
                        */

                        if(input.length()>256){

                            TextUtils.cls();
                            TextUtils.printLogo(EmotionalSongs.languageBundle.getString("RegisterEmotionSubtitle"));

                            exceedsCharacterLimit = true;

                            System.out.println(EmotionalSongs.languageBundle.getString("WARNING_CommentExceedsCharacterLimit"));
                            System.out.print(TextUtils.splitLongStringInput(input.substring(0, 256), "\n\t", true)+EmotionalSongs.languageBundle.getString("PromptUserToModifyComment"));

                            if(!TextUtils.readYesOrNo(true)){

                                input = input.substring(0, 255);
                                exceedsCharacterLimit = false;

                            }
                            
                        }

                    }while(exceedsCharacterLimit);

                    comments[idxEmozioneScelta] = input;

                    System.out.println(EmotionalSongs.languageBundle.getString("FeedbackRegistered"));                    
                    TextUtils.pause();

                }

                hasGivenFeedback = true;

            } else if(input.equalsIgnoreCase("cancel")){ 
                
                /*
                 * Durante la fase di test questo punto l'applicazione si è trovata un un 'loop' continuo dove il programma chiedeva all'utente se voleva cancellare il processo.
                 * A risposta affermativa veniva mostrata nuovamente la stessa schermata avente le 9 emozioni elencate.
                 * Non è stato possibile replicare questo bug ne tantomeno risalire a ciò che l'abbia provocato
                 *  in quanto, dopo avere eseguito nuovamente l'applicazione ed effettuato ulteriori test, tutto ha funzionato correttamente.
                 */
                if(idxEmozioneScelta != -1){
                    System.out.print(EmotionalSongs.languageBundle.getString("CancelEmotionRegistrationPrompt"));
                    if(TextUtils.readYesOrNo(true)){return;} 
                }else{
                    return;
                }
                
            } else if(input.equalsIgnoreCase("save") && hasGivenFeedback){

                if(substitute){

                    System.out.print(EmotionalSongs.languageBundle.getString("WARNING_FeedbackWillBeOverriden"));
                    
                    if(TextUtils.readYesOrNo(true)){

                        SongEmotions feedbackFornito = new SongEmotions(canzoneSelezionata.getSongUUID(),EmotionalSongs.getLoggedUser().getUserId(), emotions, comments);

                        EmotionsRepositoryManager.saveFeedback(feedbackFornito, substitute);
                        EmotionsRepositoryManager.update(feedbackFornito);
                        

                        System.out.println(EmotionalSongs.languageBundle.getString("FeedbackRegistered"));
                        TextUtils.pause();

                        return;

                    } else{return;}
                    
                }

                EmotionsRepositoryManager.saveFeedback(new SongEmotions(canzoneSelezionata.getSongUUID(),EmotionalSongs.getLoggedUser().getUserId(), emotions, comments), substitute);
                
                TextUtils.printDebug(EmotionalSongs.languageBundle.getString("DEBUG_ResultsObtained")+ new SongEmotions(canzoneSelezionata.getSongUUID(),EmotionalSongs.getLoggedUser().getUserId(), emotions, comments).toCSV()+"'", false, false); //Questo verrà salvato su file

                /* 
                 * Questo valore permette di determinare se l'utente ha già dato un feedback o meno quindi permetterà di utilizzare il comando cancel 
                 * senza che venga visualizzato il messaggio che afferma che i progressi verranno persi
                 */
                idxEmozioneScelta = -1; 
                stop = true;

            } else{

                if(TextUtils.isNumeric(input)){
                    TextUtils.printErrorMessage(String.format(EmotionalSongs.languageBundle.getString("ERROR_ValueMustBeInsideIntervalPrompt"), 1,9), true);
                } else if(!hasGivenFeedback){

                    TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_NoFeedbackPresent"), true);

                } else{
                    TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_UnrecognizedCommand"), true);
                }

                TextUtils.pause();
            }

        }while(!stop);
    
    }

    /**
     * Effettua il salvataggio del feedback utente in memoria di massa.
     * @param songEmotions un oggetto di tipo {@code SongEmotions} contenente il feedback fornito dall'utente
     * @param substitute un valore {@code boolean} che determina il comportamento della funzione. Con valore {@code true} la funzione sovrascriverà il feedback precedentemente lasciato da parte dell'utente sulla canzone.
     */
    private static void saveFeedback(SongEmotions songEmotions, boolean substitute) {
        
        if(!substitute){

            try{ // Se l'utente non ha già fornito un feedback sulla canzone

                File fileOut = new File(EMOTIONS_REPOSITORY_PATH);
                BufferedWriter bw = new BufferedWriter(new FileWriter(fileOut, true));

                if(!fileOut.exists()){
                    fileOut.createNewFile();
                    TextUtils.printDebug(String.format(EmotionalSongs.languageBundle.getString("DEBUG_FileNotFound"), "Emozioni.dati"), true, true);
                }

                bw.append("\n"+songEmotions.toCSV());
                bw.close();
                EmotionsRepositoryManager.update(songEmotions);

            } catch (IOException e){

                TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_IOExceptionOnSaveFeedback"), false);
                TextUtils.pause();
            }

        }else{ 

            try {
                
                BufferedReader br = new BufferedReader(new FileReader(EMOTIONS_REPOSITORY_PATH));
                StringBuilder inputBuffer = new StringBuilder();
                
                String line;
                String lineToSwap = "";
        
                while ((line = br.readLine()) != null) {
                    inputBuffer.append(line);
                    inputBuffer.append('\n');
                    if(line.startsWith(songEmotions.getSongUUID()+","+EmotionalSongs.getLoggedUser().getUserId())){
                        lineToSwap = line;
                    }
                }

                br.close();

                inputBuffer.replace(inputBuffer.length()-"\n".length(), inputBuffer.length(), ""); //rimuove il new line alla fine
                String inputStr = inputBuffer.toString();                
                inputStr = inputStr.replace(lineToSwap, songEmotions.toCSV()); // Come si comporta con line to swap == ""? non fa nulla? DEBUG
                
                FileWriter fileOut = new FileWriter(EMOTIONS_REPOSITORY_PATH);
                fileOut.append(inputStr);
                fileOut.close();
        
            } catch (FileNotFoundException e) { //Documentazione

                TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_FileNotFoundExceptionOnSaveFeedback"), false);
                TextUtils.pause();

            } catch (IOException e) { //Documentazione

                TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_IOExceptionOnSaveFeedback"), false);
                TextUtils.pause();

            }

        }

    }
    
    /**
     * Aggiorna la repository delle emozioni con il feedback appena aggiunto/modificato
     * @param songEmotions un oggetto di tipo {@code SongEmotions} contenente il feedback fornito dall'utente appena salvato in memoria di massa.
     */
    public static void update(SongEmotions songEmotions){
        
        if(!repositoryEmozioni.containsKey(songEmotions.getSongUUID())){

            repositoryEmozioni.put(songEmotions.getSongUUID(), new ArrayList<>());
            repositoryEmozioni.get(songEmotions.getSongUUID()).add(new SongEmotions(songEmotions.toCSV()));

        } else{

            boolean containsUserFeedback = false;

            List<SongEmotions> emozioni = repositoryEmozioni.get(songEmotions.getSongUUID());

            for(var i : emozioni){
                if(i.getUserid().equals(songEmotions.getUserid())){
                    emozioni.remove(i);
                    emozioni.add(songEmotions);
                    repositoryEmozioni.replace(songEmotions.getSongUUID(), emozioni);
                    containsUserFeedback = true;
                }
            }
            
            if(containsUserFeedback){

                TextUtils.printDebug(String.format(EmotionalSongs.languageBundle.getString("DEBUG_UserAlreadyProvidedFeedback"),songEmotions.getUserid(), songEmotions.getSongUUID()), false, true);

            } else{

                repositoryEmozioni.get(songEmotions.getSongUUID()).add(new SongEmotions(songEmotions.toCSV()));

            }
            
        }
    }

 
}
