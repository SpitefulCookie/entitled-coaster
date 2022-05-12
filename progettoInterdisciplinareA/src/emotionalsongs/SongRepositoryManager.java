package emotionalsongs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

class SongRepositoryManager{

    private static final DecimalFormat DF = new DecimalFormat("#.##");
    
    //Search constants:
    private static final String SONG_REPOSITORY = "progettoInterdisciplinareA\\data\\Canzoni.dati";
    private static final double SEARCH_THRESHOLD = 40;
    public static final int MAX_SEARCH_RESULTS = 27;

    //Subtitle constants:
    private static final String RICERCA_CANZONE = "RICERCA CANZONE";

    //Class variables
    private static LinkedList<Canzone> repository;

    public SongRepositoryManager(){
        
        repository = new LinkedList<>();

        if(initRepository(repository)){TextUtils.printDebug("Repository inizializzata correttamente!");
        } else{TextUtils.printDebug("Errore IO");}
       
    }

    private boolean initRepository(LinkedList<Canzone> repository){

        try (BufferedReader br = new BufferedReader(new FileReader(SONG_REPOSITORY))) {

            br.readLine(); //Salta la prima linea (header della repository)

            long start = System.currentTimeMillis();
            
            String line;

            TextUtils.printDebug("Inizializzazione repository canzoni in corso...");

            while ((line = br.readLine()) != null) {

                repository.add(new Canzone(line));

            }

            long end = System.currentTimeMillis();

            TextUtils.printDebug("Tempo impiegato: " + (end-start) + "ms"); 

            return true;

        } catch (IOException e)  {

            TextUtils.printErrorMessage("IO error!", false);
            return false;

        } 

    }

    public static List<Canzone> getRepository(){return repository;}

    public List<Canzone> cercaBranoMusicale(){

        String input;
        Scanner in = TextUtils.getScanner();

        do{
            TextUtils.printLogo(RICERCA_CANZONE, 3);
            
            System.out.print("Seleziona il metodo di ricerca:\n\n\t[1] Ricerca per titolo\n\t[2] Ricerca per autore e anno\n\nScelta: ");

            input = in.nextLine();
            
        }while(input.equals("1") && input.equals("2"));
        
        if(input.equals("1")){ // EFFETTUA UNA RICERCA UTILIZZANDO IL TITOLO DELLA CANZONE

            TextUtils.printLogo(RICERCA_CANZONE, 3);

            System.out.print("Inserisci il titolo della canzone: ");

            do{

                input = in.nextLine();

                if(TextUtils.isEmptyString(input)){
                    TextUtils.printErrorMessage("E' stato inserito un valore nullo o non valido, inserire nuovamente: ", false);
                }

            }while(TextUtils.isEmptyString(input));

            TextUtils.printDebug("Sto ricercando: " + input);

            return ordinaRisultati(cercaBranoMusicale(input));

            
        } else{ // EFFETTUA UNA RICERCA UTILIZZANDO L'AUTORE E L'ANNO

            String autore;

            TextUtils.printLogo(RICERCA_CANZONE, 3);

            System.out.print("Inserisci il nome dell'autore: ");

            do{

                autore = in.nextLine();

                if(TextUtils.isEmptyString(input)){
                    TextUtils.printErrorMessage("E' stato inserito un valore nullo o non valido, inserire nuovamente: ", false);
                }

            }while(TextUtils.isEmptyString(input));

            System.out.print("Inserisci l'anno di pubblicazione: ");

            do{

                input = in.nextLine();

                if(!TextUtils.isNumeric(input)){
                    TextUtils.printErrorMessage("E' stato inserito un valore nullo o non valido, inserire nuovamente: ", false);
                }

            }while(!TextUtils.isNumeric(input));

            return ordinaRisultati(cercaBranoMusicale(autore, Integer.parseInt(input)));

        }
       
    }

    private List<SearchResult> cercaBranoMusicale(String titolo){ //Effettua una ricerca della canzone per titolo

         /*
          * La funzione itera all'interno della repository delle canzoni e per ogni canzone ne ottiene il 
          * titolo e lo compara con il titolo ricercato privato di duplicati. 
          * 
          */

        ArrayList<SearchResult> canzoniTrovate = new ArrayList<>();

        String[] titoloEstratto;
        String[] titoloRicerca = SongRepositoryManager.removeDuplicate(titolo.toLowerCase().split(" "));

        int occorrenze;

        for(Canzone c : repository){

            occorrenze = 0;

            /*
             * Verifica subito se il titolo della canzone abbia un match del 100% così evitando ulteriori costi computazionali;
             * il valore 101 è stato immesso puramente per assicurare che il risultato sia posto in cima alla lista.
             * 
             */

            if(c.getTitolo().toLowerCase().equalsIgnoreCase(titolo)){ 
                
                canzoniTrovate.add(new SearchResult(c, 101));

            } else{

                //titoloEstratto = RepositoryManager.removeDuplicate(c.getTitolo().toLowerCase().split(" "));
                titoloEstratto = c.getTitolo().toLowerCase().split(" ");

                for(int i = 0; i<titoloEstratto.length; i++){

                    for( int j = 0; j<titoloRicerca.length; j++){

                        if(titoloEstratto[i].equals(titoloRicerca[j])){occorrenze++;}
                        
                    }

                }
                
                if(occorrenze!=0 && ((double)occorrenze/titoloEstratto.length*100)>SEARCH_THRESHOLD){ // Prende solamente i valori che hanno un uguaglianza maggiore del 40%
                    //canzoniTrovate.add(new SearchResult(c, ((float)occorrenze/titoloRicerca.length)*100));
                    canzoniTrovate.add(new SearchResult(c, ((double)occorrenze/titoloEstratto.length*100)));

                }

            }

        }

        return canzoniTrovate;

    }
    
    private List<SearchResult> cercaBranoMusicale(String autore, int anno){ //Effettua una ricerca della canzone per autore e anno

        ArrayList<SearchResult> canzoni = new ArrayList<>();
        
        for(Canzone c : repository){

            if(c.getAutore().equalsIgnoreCase(autore) && c.getAnno() == anno){
                canzoni.add(new SearchResult(c));
            }

        }

        return canzoni;

    }

    private static String[] removeDuplicate(String[] words) {

        Set<String> wordSet = new LinkedHashSet<>();

        for (String word : words) {wordSet.add(word);}

        return wordSet.toArray(new String[wordSet.size()]);

    }

    private static List<Canzone> ordinaRisultati(List<SearchResult> risultati){

        SearchResult temp;
        int n = risultati.size();

        for(int i=0; i < n; i++){ 

            for(int j=1; j < (n-i); j++){

                if(risultati.get(j-1).compareTo(risultati.get(j)) < 0 ){  
                    
                    temp = risultati.get(j-1); 

                    risultati.set(j-1, risultati.get(j)); 

                    risultati.set(j, temp);  

                }  
                
            } 

        }  
        
        for(SearchResult c : risultati){
            TextUtils.printDebug(c.getCanzone().toString()+"\n\tPercentage: " + DF.format(c.getScore())+"%");
        }

        return SearchResult.toCanzoni(risultati);

    }

    public static Canzone getCanzone(String songUUID){

        for (Canzone canzone : repository) {
           if(canzone.getSongUUID().equals(songUUID)){
               return canzone;
           }
        }

        return null;

    }

    public void consultaRepository(){

        Scanner in = TextUtils.getScanner();
        String input;

        boolean sceltaBoolean = true;

        List<Canzone> canzoniTrovate;
    
        do {
            
            canzoniTrovate = cercaBranoMusicale();

            TextUtils.printDebug("Sono stati trovati " + canzoniTrovate.size() + " risultati");
    
            if(canzoniTrovate.isEmpty()){
    
                System.out.print("Non sono state trovate canzoni all'interno della repository oppure sono stati restituiti troppi risultati.\nVuoi effettuare un'altra ricerca? s/n\nScelta: ");
                sceltaBoolean = TextUtils.readYesOrNo();
    
            }
    
        } while (canzoniTrovate.isEmpty() && sceltaBoolean);

        if(!sceltaBoolean){return;}
    
        int pagina = 0;
        sceltaBoolean = false;
    
        while(!sceltaBoolean){
    
            TextUtils.printLogo("CONSULTA REPOSITORY", 1);
    
            TextUtils.printDebug("Numero risultati ottenuti: " + canzoniTrovate.size()+"\n");
    
            for (int i = 0; i < 9 && i<canzoniTrovate.size(); i++) {System.out.println("\t[" + TextUtils.BLUE_BOLD + (i+1) + TextUtils.RESET + "] " + canzoniTrovate.get(i+(pagina*9)).toString());}
    
            System.out.print("\n\t\t   [Pagina " + (pagina+1) + " di " + (int)Math.ceil((canzoniTrovate.size()/9.00)) +"]\n\n");
    
            System.out.print("Inserisci 'next' o 'previous' per navigare tra le pagine dei risultati oppure 'cancel' per tornare indietro.");
    
            System.out.print("\nScelta: ");
    
            input = in.nextLine();
    
            if(input.equalsIgnoreCase("previous")){
    
                if(pagina>0){pagina--;} else{TextUtils.printErrorMessage("Non ci sono pagine precedenti.", true); TextUtils.pause();}
                
    
            } else if(input.equalsIgnoreCase("next")){
    
                if(pagina == (Math.ceil((canzoniTrovate.size()/9.00))-1)){TextUtils.printErrorMessage("Non ci sono altre pagine.", true); TextUtils.pause();} else{pagina++;}
    
    
            } else if(input.equalsIgnoreCase("cancel")){
    
                TextUtils.printLogo( "CONSULTA REPOSITORY", 1);
    
                System.out.print("Vuoi tornare indietro? s/n\nScelta: ");
                sceltaBoolean = TextUtils.readYesOrNo();
    
            }else {TextUtils.printErrorMessage("Il comando inserito non è stato riconosciuto", true); TextUtils.pause();}
    
        }
    
    }  

    /*
    @Deprecated
    public static void refactorRepository(){

        File file = new File(SONG_REPOSITORY);
        File file2 = new File(SONG_REPOSITORY+".copy");

        try{
            file2.createNewFile();

            FileWriter fw = new FileWriter(file2,true);
            BufferedWriter bw = new BufferedWriter(fw);

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line;
            StringBuilder sb = new StringBuilder();

            while((line = br.readLine()) != null){
               sb.append(UUID.nameUUIDFromBytes(line.getBytes()).toString()+","+line);
               bw.append(sb.toString());
               bw.newLine();
               sb.delete(0, sb.length());
               bw.flush();
            }

            br.close();
            
            bw.close();
            fw.close();

        } catch (IOException e) {

            e.printStackTrace();

        }
        
    }
    */

}

class SearchResult implements Comparable<SearchResult>{

    private Canzone canzone;
    private double score = 0;

    public SearchResult(String line, float value){
        this.canzone = new Canzone(line);
        this.score = value;
    }

    public SearchResult(String line, double value){
        this.canzone = new Canzone(line);
        this.score = value;
    }

    public SearchResult(Canzone canzoneTrovata, double value){
        this.canzone = canzoneTrovata;
        this.score = value;
    }

    public SearchResult(Canzone canzoneTrovata) {

        this.canzone = canzoneTrovata;
        this.score = -1;

    }

    public double getScore(){
        return this.score;
    }

    public Canzone getCanzone(){
        return this.canzone;
    }

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

    public static List<Canzone> toCanzoni(List<SearchResult> risultati){
        
        List<Canzone> canzoni = new LinkedList<>();
        
        for(int i = 0; i<SongRepositoryManager.MAX_SEARCH_RESULTS && i<risultati.size(); i++){
            canzoni.add(risultati.get(i).getCanzone());
        }

        return canzoni;

    }

}