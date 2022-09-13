package emotionalsongs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/*
 * Progetto svolto da:
 * 
 * Della Chiesa Mattia 749904, Ateneo di Varese
 * 
 */

 /**
  * La classe {@code SongRepositoryManager} implementa metodi utili alla gestione della repository delle canzoni.
  * 
  */
public class SongRepositoryManager{
    
    private static final String SONG_REPOSITORY = System.getProperty("user.dir")+"\\data\\Canzoni.dati";
    private static final double SEARCH_THRESHOLD = 0.33;
    private static final int HAMMING_DISTANCE_THRESHOLD = 1; //Numero di caratteri diversi per considerare un nome dell'autore come quello ricercato (ad es con val = 1 acdc == ac/dc)

    private static HashMap<String, Canzone> repository = new HashMap<>();

    /**
     * Metodo che costruisce la repository delle canzoni.
     */
    public static void build(){

        try (BufferedReader br = new BufferedReader(new FileReader(SONG_REPOSITORY))) {

            long start = System.currentTimeMillis();
            
            String line;

            TextUtils.printDebug(EmotionalSongs.languageBundle.getString("DEBUG_BeginSongRepositoryInitialization"), true, true);

            int count = 0; //Debugging counter
            
            while ((line = br.readLine()) != null) {

                Canzone c = new Canzone(line);

                repository.put(c.getSongUUID(), c);

                count++;

            }

            TextUtils.printDebug(EmotionalSongs.languageBundle.getString("DEBUG_RepositoryBuildSuccess"), true, true);
            
            TextUtils.printDebug(String.format(EmotionalSongs.languageBundle.getString("DEBUG_SongsInRepository"), count), false, true);

            long end = System.currentTimeMillis();

            TextUtils.printDebug(EmotionalSongs.languageBundle.getString("DEBUG_ProcessDuration") + (end-start) + "ms\n", false, true); 

        } catch (IOException e)  { //Documentazione

            TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_GenericIOError"), false);
            TextUtils.pause();
        } 

    }
    
    /**
     * Costruttore esplicito ad Object.
     */
    private SongRepositoryManager(){super();}

    /**
     * Restituisce la repository delle canzoni.
     * @return un oggetto di tipo {@code List<Canzone>} che rappresenta la repository delle canzoni.
     */
    public static Map<String, Canzone> getRepository(){return repository;}

    /**
     * Guida l'utente durante la ricerca di un brano musicale, permettendogli di effettuare una ricerca tramite titolo oppure tramite autore e anno di pubblicazione del brano desiderato.
     * @return un oggetto {@code List<Canzone>} contenente i risultati ottenuti dalla ricerca.
     */
    protected static List<Canzone> cercaBranoMusicale(){

        String input;

        do{
            
            TextUtils.printLogo(EmotionalSongs.languageBundle.getString("SearchSongSubtitle"));
            
            System.out.print(TextUtils.centerString(String.format(EmotionalSongs.languageBundle.getString("SearchSelectionMethod"), StringUtils.repeat(" ", TextUtils.SPACESBEFORELOGO-5))));
            input = TextUtils.readStringInput(true);

           /*
            * Poiché si è deciso di permettere all'utente di annullare il procedimento di ricerca della canzone e poiché
            * questa casistica necessita di essere differenziata dalla lista vuota (restituita quando non sono stati trovati risultati)
            * verrà restituita una lista avente un solo elemento con valore nullo.   
            */

            if(input.equalsIgnoreCase("cancel")){
                
                List<Canzone> temp = new ArrayList<>();
                temp.add(null);
                return temp;
                
            }

            
        }while(!input.equals("1") && !input.equals("2"));
        
        if(input.equals("1")){ // EFFETTUA UNA RICERCA UTILIZZANDO IL TITOLO DELLA CANZONE

            TextUtils.printLogo(EmotionalSongs.languageBundle.getString("SearchSongSubtitle"));

            System.out.print(EmotionalSongs.languageBundle.getString("InsertSongTitle"));
            input = TextUtils.readStringInput(false);

            TextUtils.printDebug(EmotionalSongs.languageBundle.getString("DEBUG_SongTitleBeingSearched") + input, true, true);
            
            return SearchResult.ordinaRisultati(cercaBranoMusicale(input));

            
        } else{ // EFFETTUA UNA RICERCA UTILIZZANDO L'AUTORE E L'ANNO

            String autore;
            boolean validInput = false;

            TextUtils.printLogo(EmotionalSongs.languageBundle.getString("SearchSongSubtitle"));

            System.out.print(EmotionalSongs.languageBundle.getString("InsertAuthorName"));
            autore = TextUtils.readStringInput(true);

            do{

                System.out.print(EmotionalSongs.languageBundle.getString("InsertYearOfRelease"));
                input = TextUtils.readStringInput(true);

                try{
                
                    if(!TextUtils.isNumeric(input) || Integer.parseInt(input)<1900 || Integer.parseInt(input) > LocalDateTime.now().getYear()){
                        TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_ValueIsNullOrInvalidPrompt"), false);
                    } else{
                        validInput = true;
                    }
                } catch (NumberFormatException e){

                    TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_ValueIsNullOrInvalidPrompt"), false);

                }


            }while(!validInput);

            return SearchResult.ordinaRisultati(cercaBranoMusicale(autore, Integer.parseInt(input)));

        }
       
    }

    /**
     * Permette di effettuare una ricerca all'interno della repository delle canzoni utilizzano il titolo della canzone come criterio di ricerca.
     * @param titolo un oggetto di tipo {@code String} che contiene il titolo della canzone desiderata.
     * @return un oggetto di tipo {@code List<SearchResult>} contenente i risultati ottenuti tramite la ricerca.
     */
    private static List<SearchResult> cercaBranoMusicale(String titolo){ 

        long start = System.currentTimeMillis();
        ArrayList<SearchResult> canzoniTrovate = new ArrayList<>();

        double score = 0;

        Set<String> searchedTitleWordset = TextUtils.generateWordSet(titolo.toLowerCase().split(" "));

        /*
         * Per ogni canzone presente all'interno della repository viene generato un set di parole che ne compongono il titolo, 
         * su questo insieme viene successivamente effettuata l'operazione di intersezione con l'insieme generato sul titolo ricercato.
         * In base al numero di elementi comuni ai due insiemi viene calcolata una percentuale che descrive quante parole del titolo 
         * ricercato sono presenti all'interno del titolo della canzone. Nel caso i due titoli possiedano un match del 100% (determianto tramite il metodo equalsIgnoreCase())
         * questo punteggio assumerà il valore 1.1 . Ciò assicurerà che la canzone apparirà in cima all'elenco una volta effettuato l'ordinamento decrescente.
         */         

        for(Canzone c : repository.values()){

            if(!titolo.equalsIgnoreCase(c.getTitolo())){
                
                Set<String> songWordSet = TextUtils.generateWordSet(c.getTitolo().toLowerCase().split(" "));
                
                int val = songWordSet.size();

                songWordSet.retainAll(searchedTitleWordset);
                
                if(!songWordSet.isEmpty()){

                    score = (double)songWordSet.size()/Math.max(val,searchedTitleWordset.size());
                    
                    if(score>SEARCH_THRESHOLD){
                        canzoniTrovate.add(new SearchResult(c, score));
                    }

                }

            } else {canzoniTrovate.add(new SearchResult(c, 1.1));} // Match del 100%
            
        }
        
        TextUtils.printDebug(String.format(EmotionalSongs.languageBundle.getString("DEBUG_SearchByTitleCompleted"), canzoniTrovate.size()), true, true);
        TextUtils.printDebug(EmotionalSongs.languageBundle.getString("DEBUG_ProcessDuration") + (System.currentTimeMillis()-start) +"ms", false, true);

        return canzoniTrovate;

    }

    /**
     * Effettua la ricerca all'interno della repository utilizzando l'autore e l'anno come criteri di ricerca.
     * @param autore un oggetto di tipo {@code String} contenente il nome dell'autore della canzone.
     * @param anno un valore {@code int} che rappresenta l'anno di pubblicazione del brano musicale.
     * @return un oggetto {@code List<SearchResult>} contenente i risultati ottenuti dalla ricerca.
     */
    private static List<SearchResult> cercaBranoMusicale(String autore, int anno){ 

        ArrayList<SearchResult> canzoni = new ArrayList<>();
        
        for(Canzone c : repository.values()){

            if(TextUtils.characterDissimilarity(c.getAutore(), autore)<=HAMMING_DISTANCE_THRESHOLD && c.getAnno() == anno){
                canzoni.add(new SearchResult(c, 1.1));
            }

        }

        return canzoni;

    }
    
    /**
     * Restituisce un'istanza di un oggetto {@code Canzone} associato all'UUID fornito come parametro.
     * @param songUUID un oggetto di tipo {@code String} che rappresenta l'UUID di tipo 3 associato alla canzone.
     * @return un oggetto di tipo {@code Canzone} associato all'UUID fornito come parametro.
     */
    protected static Canzone getCanzone(String songUUID){

        for (Canzone canzone : repository.values()) {
           if(canzone.getSongUUID().equals(songUUID)){
               return canzone;
           }
        }

        return null;

    }

    /**
     * Guida l'utente durante la consultazione della repository delle canzoni 
     */
    protected static void consultaRepository(){

        boolean sceltaBoolean = true;

        List<Canzone> canzoniTrovate;
    
        do {
            
            canzoniTrovate = cercaBranoMusicale();

            if(canzoniTrovate.size()>27){
                TextUtils.printDebug(String.format(EmotionalSongs.languageBundle.getString("DEBUG_NumberOfResultsKept"), canzoniTrovate.size()), true, true);
            }
    
            if(canzoniTrovate.isEmpty()){
    
                System.out.print(EmotionalSongs.languageBundle.getString("NoSearchResultsYieldedForKeywords"));
                sceltaBoolean = TextUtils.readYesOrNo(true);
    
            } else if(canzoniTrovate.get(0)==null){ // L'utente ha cancellato la ricerca dei brani
                return;
            }
    
        } while (canzoniTrovate.isEmpty() && sceltaBoolean);

        if(!sceltaBoolean){return;}

        int canzoneSelezionata = TextUtils.selezionaRisultati(canzoniTrovate);
        
        if(canzoneSelezionata!=-1){EmotionsRepositoryManager.visualizzaEmozioneBrano(canzoniTrovate.get(canzoneSelezionata));}
    
    }  

    /**
     * Permette di visualizzare, mediante il metodo {@code Canzone.toString()}, le informazioni relative ad una canzone.
     * @param songUUID un oggetto di tipo {@code String} che rappresenta l'UUID di tipo 3 associato alla canzone.
     */
    protected static void viewSongFromUUID(String songUUID){

        if(!TextUtils.isEmptyString(songUUID) && repository.containsKey(songUUID)){
            System.out.println(repository.get(songUUID).toString());
        } else{
            TextUtils.printErrorMessage(EmotionalSongs.languageBundle.getString("ERROR_InvalidSongUUID"), true);
        }

    }

}
/**
 * Classe {@code SearchResult} rappresenta un risultato di ricerca. 
 * Ogni istanza di questo oggetto è caratterizzato da un oggetto di tipo {@code Canzone} e un 
 * punteggio calcolato in base a quanto il titolo della canzone sia simile a quello ricercato.
 */
class SearchResult implements Comparable<SearchResult>{

    /**
     * Numero massimo di risultati da mostrare all'utente durante la ricerca.
     */
    private static final int MAX_SEARCH_RESULTS = 27;

    private Canzone canzone;
    private double score = 0;

    /**
     * Costruisce un oggetto di tipo {@code SearchResult} a partire da una cazone ed un punteggio che varia da {@code 0} a {@code 1.1}.
     * <p> Il valore {@code 1.1} viene utilizzato per rappresentare un match del 100% ottenuto tramite mediante l'utilizzo del metodo {@code String.equalsIgnoreCase()}.
     * @param canzoneTrovata un oggetto di tipo {@code Canzone} che contiene la canzone trovata.
     * @param value un oggetto di tipo {@code double} che rappresenta il punteggio calcolato.
     */
    protected SearchResult(Canzone canzoneTrovata, double value){ 
        this.canzone = canzoneTrovata;
        this.score = value;
    }

    /**
     * Restituisce il punteggio calcolato in base a quanto il titolo della canzone sia simile a quello ricercato.
     * @return un oggetto di tipo {@code double} che rappresenta il punteggio calcolato.
     */    
    protected double getScore(){
        return this.score;
    }

    /**
     * Restituisce la canzone trovata.
     * @return un oggetto di tipo {@code Canzone} che contiene la canzone trovata.
     */
    protected Canzone getCanzone(){
        return this.canzone;
    }

    /**
     * Effettua l'ordinamento decrescente dei risultati ottenuti dalla funzione di ricerca secondo il loro punteggio.
     * @param risultati un oggetto di tipo {@code List<SearchResult>} contente i risultati della ricerca effettuata.
     * @return un oggetto di tipo {@code List<Canzone>} contenente i risultati della ricerca ordinati i maniera decrescente in base al punteggio calcolato.
     */
    protected static List<Canzone> ordinaRisultati(List<SearchResult> risultati){

        SearchResult temp;
        int n = risultati.size();

        for(int i=0; i < n; i++){ 

            for(int j=1; j < (n-i); j++){

                if(risultati.get(j-1).compareTo(risultati.get(j)) < 0 ){  //algoritmo di ordinamento QuickSort??
                    
                    temp = risultati.get(j-1); 

                    risultati.set(j-1, risultati.get(j)); 

                    risultati.set(j, temp);  

                }  
                
            } 

        }  

        return SearchResult.toCanzoni(risultati);

    }

    /**
     * Compara il campo punteggio di due oggetti di tipo {@code SearchResult} e restituisce un valore di tipo 
     * {@code int} che indica la relazione maggiore, minore o uguale tra i due punteggi.<p>
     *  Esempio valori restituiti:<p>
     *  {@code -1} il campo punteggio dell'oggetto su cui è stato invocato il metodo assume un valore inferiore a quello presente nell'istanza dell'oggetto passato come parametro.<p>
     *  {@code 0} i campi punteggo delle due istanze hanno valore uguale<p>
     *  {@code 1} il campo punteggio dell'oggetto passato come parametro assume un valore superiore a quello presente nell'istanza dell'oggetto su cui è stato invocato il metodo.<p>
     * @param o l'oggetto da comparare.
     * @return un valore di tipo {@code int} che indica il tipo di relazione tra i due punteggi.
     */
    @Override
    public int compareTo(SearchResult o) {

        if(this.score == o.getScore()){

            return 0;

        } else if(this.score<o.getScore()){

            return -1;

        } else{

            return 1;
        }
        
    }

    /**
     * Restituisce una lista di oggetti di tipo {@code Canzone} a partire da una lista di oggetti di tipo {@code SearchResult}.
     * @param risultati un oggetto di tipo {@code List<SearchResult>} che contiene una lista di risultati.
     * @return un oggetto di tipo {@code List<Canzone>} che contiene tutte le canzoni presenti all'interno della lista passata come parametro.
     */
    protected static List<Canzone> toCanzoni(List<SearchResult> risultati){
        
        List<Canzone> canzoni = new ArrayList<>();
        
        for(int i = 0; i<MAX_SEARCH_RESULTS && i<risultati.size(); i++){
            canzoni.add(risultati.get(i).getCanzone());
        }

        return canzoni;

    }
    
}