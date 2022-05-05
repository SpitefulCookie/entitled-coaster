package emotionalsongs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;

class RepositoryManager{

    private static final DecimalFormat DF = new DecimalFormat("#.##");
    private static final String SONG_REPOSITORY = "progettoInterdisciplinareA\\data\\Canzoni.dati";
    //private static final int UUID_LENGTH = 36;
    private static final double SEARCH_THRESHOLD = 40; 
    public static final int MAX_SEARCH_RESULTS = 27;

    private LinkedList<Canzone> repository;

    public RepositoryManager(){

        System.out.println("Hi");
        
        this.repository = new LinkedList<>();

        initRepository(this.repository);
       
    }

    private static boolean initRepository( LinkedList<Canzone> repository){

        try (BufferedReader br = new BufferedReader(new FileReader(SONG_REPOSITORY))) {

            br.readLine(); //Salta la prima linea (header della repository)

            long start = System.nanoTime();
            
            String line;

            TextUtils.printDebug("Iniziallizzazione playlist in corso...");

            while ((line = br.readLine()) != null) {

                repository.add(new Canzone(line));

            }

            long end = System.nanoTime();  

            TextUtils.printDebug("Tempo impiegato: " + (end-start)); 

            return true;

        } catch (IOException e)  {

            return false;

        } 
        

    }

    public LinkedList<Canzone> getRepository(){return this.repository;}

    public List<Canzone> cercaBranoMusicale(){

        String input;
        Scanner in = TextUtils.getScanner();

        TextUtils.cls();

        do{
            TextUtils.printLogo("RICERCA CANZONE", 3);
            //System.out.println(TextUtils.BLUE + "\t\tRICERCA CANZONE\n" + TextUtils.RESET);
            System.out.print("Seleziona il metodo di ricerca:\n\n\t[1] Ricerca per titolo\n\t[2] Ricerca per autore e anno\n\nScelta: ");

            input = in.nextLine();
            
        }while(input.equals("1") && input.equals("2"));

        TextUtils.cls();
        
        if(input.equals("1")){ // EFFETTUA UNA RICERCA UTILIZZANDO IL TITOLO DELLA CANZONE

            TextUtils.printLogo("RICERCA CANZONE", 3);
            //System.out.println(TextUtils.BLUE + "\t\tRICERCA CANZONE\n" + TextUtils.RESET);
            System.out.print("Inserisci il titolo della canzone: ");

            do{

                input = in.nextLine();

            }while(input.equals("") || input.equals(" "));

            TextUtils.printDebug("Sto ricercando: " + input);

            return RepositoryManager.ordinaRisultati(cercaBranoMusicale(input));

            
        } else{

            String autore;
            System.out.println(TextUtils.BLUE + "\t\tRICERCA CANZONE" + TextUtils.RESET);
            System.out.print("Inserisci il nome dell'autore: ");

            do{

                autore = in.nextLine();

            }while(autore.equals("") || autore.equals(" "));

            System.out.print("Inserisci l'anno di pubblicazione: ");

            do{

                input = in.nextLine();

            }while(input.equals("") || input.equals(" "));

            return RepositoryManager.ordinaRisultati(RepositoryManager.cercaBranoMusicale(autore, input));

        }
       
    }

    private static List<Canzone> ordinaRisultati(List<SearchResult> risultati){

        SearchResult temp;
        int n = risultati.size();

        for(int i=0; i < n; i++){ 

            for(int j=1; j < (n-i); j++){

                if(risultati.get(j-1).compareTo(risultati.get(j)) == -1 ){  
                    
                    temp = risultati.get(j-1); 

                    risultati.set(j-1, risultati.get(j)); 

                    risultati.set(j, temp);  

                }  
                
            } 

        }  
        
        for(SearchResult c : risultati){
            TextUtils.printDebug(c.getCanzone().toString()+"\n\tPercentage: " + DF.format(c.getPercentage())+"%");
        }

        return SearchResult.toCanzoni(risultati);
    }

    private List<SearchResult> cercaBranoMusicale(String titolo){ //Effettua una ricerca della canzone per titolo

        ArrayList<SearchResult> canzoniTrovate = new ArrayList<>();

        
            String[] titoloEstratto;
            String[] titoloRicerca = RepositoryManager.removeDuplicate(titolo.toLowerCase().split(" "));

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

                    titoloEstratto = RepositoryManager.removeDuplicate(c.getTitolo().toLowerCase().split(" "));

                    for(int i = 0; i<titoloEstratto.length; i++){

                        for( int j = 0; j<titoloRicerca.length; j++){

                            if(titoloEstratto[i].equals(titoloRicerca[j])){occorrenze++;}
                            
                        }

                    }
                    
                    if(occorrenze!=0 && ((float)occorrenze/titoloRicerca.length)*100>SEARCH_THRESHOLD){ // Prende solamente i valori che hanno un uguaglianza maggiore del 40%
                        canzoniTrovate.add(new SearchResult(c, ((float)occorrenze/titoloRicerca.length)*100));
                    }
                }

            }

       

        return canzoniTrovate;

    }
    
    private static String[] removeDuplicate(String[] words) {
        Set<String> wordSet = new LinkedHashSet<>();
        for (String word : words) {
            wordSet.add(word);
        }
        return wordSet.toArray(new String[wordSet.size()]);
    }

    private static List<SearchResult> cercaBranoMusicale(String autore, String anno){

        ArrayList<SearchResult> canzoni = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(SONG_REPOSITORY))) {

            String line;

            while ((line = br.readLine()) != null) {

                if(line.contains(autore) && line.contains(anno+"")){ //ottimizzabile? e modificabile!
                   
                   //canzoni.add(new Canzone(line));

                }

            }

        } catch (IOException e)  {

            e.printStackTrace();

        } 

        return canzoni;

    }

    public static String generateUUID(String in){

        return UUID.nameUUIDFromBytes(in.getBytes()).toString();

    }
    
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

    public SearchResult(Canzone canzone, double value){
        this.canzone = canzone;
        this.score = value;
    }

    public double getPercentage(){
        return this.score;
    }

    public Canzone getCanzone(){
        return this.canzone;
    }

    @Override
    public int compareTo(SearchResult o) {

        if(this.score == o.getPercentage()){

            return 0;

        } else if(this.score<o.getPercentage()){

            return -1;

        } else{

            return 1;
        }
        
    }

    public static List<Canzone> toCanzoni(List<SearchResult> risultati){
        
        List<Canzone> canzoni = new LinkedList<>();
        
        for(int i = 0; i<RepositoryManager.MAX_SEARCH_RESULTS && i<risultati.size(); i++){
            canzoni.add(risultati.get(i).getCanzone());
        }

        return canzoni;

    }

}