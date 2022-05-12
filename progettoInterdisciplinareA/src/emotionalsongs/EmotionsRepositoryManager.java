package emotionalsongs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

enum Emotions{

    AMAZEMENT, SOLEMNITY, TENDERNESS, NOSTALGIA, CLAMNESS, POWER, JOY, TENSION, SADNESS;

    public String description(){
        
        switch(this){
            case AMAZEMENT:
                return "Feeling of wonder or happiness";
            case CLAMNESS:
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

    @Override
    public String toString(){
        
        switch(this){
            case AMAZEMENT:
                return "Amazement"; 
            case CLAMNESS:
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

}

//TENER CONTO DELLE NOTE CHE UN DATO UTENTE PUò INSERIRE!!!!!!

class SongEmotions{

    private int[] emotionsFelt = new int[9];
    private String userId;
    private String[] userComments = new String[9];

    public SongEmotions(String userId, int[] emotions, String[] comments){ //throws exception??

        this.userId = userId;
        
        this.emotionsFelt = emotions;

        //this.userComments = comments;

    }

    public SongEmotions(String line){

        this.userComments = line.substring(line.indexOf("{")+1, line.indexOf("}")).split(",");

        int userIdEnd = line.indexOf(",");
        this.userId = line.substring(0, userIdEnd);

        String[] dati = line.substring(userIdEnd+1, line.indexOf("{")).split(",");

        for(int i= 0; i<this.emotionsFelt.length; i++){
            this.emotionsFelt[i] = Integer.parseInt(dati[i]);
        }

    }

    public String getUserid(){return this.userId;}

    public int getEmotionScore(Emotions emotion){
        return this.emotionsFelt[emotion.ordinal()];
    }

    public int[] getAllEmotions(){
        return this.emotionsFelt;
    }

    @Override
    public String toString(){
        return ""; //implement
    }

}

public class EmotionsRepositoryManager{
    
    private static final String EMOTIONS_REPOSITORY_PATH = "progettoInterdisciplinareA\\data\\Emozioni.dati";
    private static final int UUID_LENGTH = 36;
    private static final char BAR_UNIT_FILLED = '█';
    private static final char BAR_UNIT_EMPTY = '░';


    private HashMap<String, List<SongEmotions>>  repositoryEmozioni;

    public EmotionsRepositoryManager(){

        if(this.repositoryEmozioni==null){

            this.repositoryEmozioni = new HashMap<>();

            if(initRepository(this.repositoryEmozioni)){TextUtils.printDebug("Repository inizializzata correttamente!");
            } else{TextUtils.printDebug("Errore IO");}

        }

    }

    public double[] getSongsAverageScores(String songUUID){

        double[] averages = new double[9];

        if(this.repositoryEmozioni.containsKey(songUUID)){

            List<SongEmotions> listaEmozioni = this.repositoryEmozioni.get(songUUID);

            for(SongEmotions se : listaEmozioni){
                for(int i = 0; i<9;i++){
                    averages[i] += se.getEmotionScore(Emotions.values()[i]);
                }
            }

            for(int i = 0; i<9;i++){
                averages[i] = averages[i]/listaEmozioni.size();
                TextUtils.printDebug("Emozione: " + Emotions.values()[i].toString()+"\n  "+averages[i]);
            }

        }
        
        return averages;
 
     }

    private boolean initRepository(HashMap<String, List<SongEmotions>> repository){

        try (BufferedReader br = new BufferedReader(new FileReader(EMOTIONS_REPOSITORY_PATH))) {

            br.readLine(); //Salta la prima linea (header della repository)

            long start = System.currentTimeMillis();
            
            String line;

            TextUtils.printDebug("Inizializzazione repository emozioni in corso...");

            while ((line = br.readLine()) != null) {

                String songUUID = line.substring(0, UUID_LENGTH);
                TextUtils.printDebug("SongUUID: " +  songUUID);

                if(!this.repositoryEmozioni.containsKey(songUUID)){
                    TextUtils.printDebug("La repository emozioni non contiene l'elemento [" + songUUID + "] e' stata creata una nuova lista!");
                    this.repositoryEmozioni.put(songUUID, new LinkedList<SongEmotions>());
                    this.repositoryEmozioni.get(songUUID).add(new SongEmotions(line.substring(UUID_LENGTH+1)));
                } else{

                    TextUtils.printDebug("La repository emozioni contiene l'elemento [" + songUUID + "]!");
                    this.repositoryEmozioni.get(songUUID).add(new SongEmotions(line.substring(UUID_LENGTH+1)));

                }

            }

            long end = System.currentTimeMillis();

            TextUtils.printDebug("Tempo impiegato: " + (end-start) + "ms"); 

            return true;

        } catch (IOException e)  {

            TextUtils.printErrorMessage("IO error!", false);
            return false;

        } 

    }

    public void viewSongSummary(Canzone c){
    
        double[] punteggi = getSongsAverageScores(c.getSongUUID());
        StringBuilder sb = new StringBuilder();

        System.out.println("\t\t  Emozioni provate per la canzone:\n\t\t\t"+TextUtils.CYAN+c.toString()+"\n"+TextUtils.RESET);

        for (int i = 0; i < punteggi.length; i++) {

            int filledBars = (int)(punteggi[i]*25)/5;
            sb.delete(0, sb.capacity()); //Svuota lo stringBuilder

            sb.append("|");
            sb.append(TextUtils.getBarColor((int)punteggi[i]));
            sb.append(StringUtils.repeat(BAR_UNIT_FILLED, filledBars));
            sb.append(StringUtils.repeat(BAR_UNIT_EMPTY, (25-filledBars)));
            sb.append(TextUtils.RESET);
            sb.append("|  "+punteggi[i]+"/5");
            System.out.println("\t" + Emotions.values()[i].toString()+":"+StringUtils.repeat(" ", 15-Emotions.values()[i].toString().length())+ sb.toString());

        }

    }

    public HashMap<String, List<SongEmotions>> getRepository(){return repositoryEmozioni;}

}
