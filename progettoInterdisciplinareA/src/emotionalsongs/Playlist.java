package emotionalsongs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Playlist{

    private LinkedList<Canzone> canzoni;
    private String nome;
    private String user;

    private static final String RICERCA_CANZONI = "RICERCA CANZONI"; 
    private static final String CREAZIONE_PLAYLIST = "CREAZIONE PLAYLIST"; 
    
    public Playlist(String nomePlaylist, Canzone canzone, String userid){

        this.canzoni = new LinkedList<>();
        this.user = userid;
        this.nome = nomePlaylist;
        this.canzoni.add(canzone);

    }

    public Playlist(String nomePlaylist, String userId){

        this.canzoni = new LinkedList<>();
        this.nome = nomePlaylist;
        this.user = userId;

    }

    public int getSize(){return this.canzoni.size();}

    public String getNome(){return this.nome;}

    public void printPlaylist(){
            
            for(Canzone canzone : this.canzoni){
    
                System.out.println(canzone.toString());
    
            }
    
    }

    public void addCanzone(Canzone canzone){this.canzoni.add(canzone);}

    public static Playlist registraPlaylist(String userId, SongRepositoryManager repository){

        // per creare una playlist l'utente registrato deve inserire il nome della playlist e l'elenco di brani da aggiungere. o brani singoli o brani di un autore specifico
        TextUtils.printLogo(CREAZIONE_PLAYLIST, 2);
        System.out.print("Inserire il nome della playlist: ");
        Scanner in = TextUtils.getScanner();
        String input;

        do{
   
            input = in.nextLine();
            if(TextUtils.isEmptyString(input)){TextUtils.printErrorMessage("E' stato immesso un valore non valido, inserire nuovamente: ", false);}

        } while(TextUtils.isEmptyString(input));

        List<Canzone> canzoniTrovate;
        
        Playlist playlist = new Playlist(input, userId);

        boolean sceltaBoolean = true;

        do{

            do {
                
                canzoniTrovate = repository.cercaBranoMusicale();

                if(canzoniTrovate.isEmpty()){

                    System.out.print("Non sono state trovate canzoni all'interno della repository.\nVuoi effettuare un'altra ricerca? s/n\nScelta: ");
                    sceltaBoolean = TextUtils.readYesOrNo();

                    if(!sceltaBoolean){confirmCancel(playlist);}

                }

            } while (canzoniTrovate.isEmpty());

            sceltaBoolean = false;

            int pagina = 0;

            while(!sceltaBoolean){ // Il ciclo while continuerà finché non è stato confermata la scelta

                TextUtils.printLogo(RICERCA_CANZONI, 3);
    
                TextUtils.printDebug("Numero risultati ottenuti: " + canzoniTrovate.size()+"\n");

                for (int i = 0; i < 9 && i<canzoniTrovate.size(); i++) { //effettua il print delle prime n canzoni dove n = i+(pagina*9) (n è un indice)
                    
                    System.out.println("\t[" + TextUtils.BLUE_BOLD + (i+1) + TextUtils.RESET + "] " + canzoniTrovate.get(i+(pagina*9)).toString());
                    
                }

                System.out.print("\n\t\t   [Pagina " + (pagina+1) + " di " + (int)Math.ceil((canzoniTrovate.size()/9.00)) +"]\n\n");

                System.out.print("Inserisci un numero per selezionare la canzone; 'next' o 'previous' per navigare tra le pagine dei risultati oppure 'cancel' per interrompere l'inserimento.");

                System.out.print("\nScelta: ");

                input = in.nextLine();

                if(TextUtils.isNumeric(input)){

                    Canzone canzoneScelta = null;
                    sceltaBoolean = false;

                    switch (Integer.parseInt(input)) {

                        case (1):

                            TextUtils.printLogo(RICERCA_CANZONI, 3);

                            System.out.print("E' stata scelta la canzone:\n\n\t" + canzoniTrovate.get((1+(9*pagina))-1).toString()+"\n\nConfermare? s/n\nScelta: ");
                            sceltaBoolean = TextUtils.readYesOrNo();
                            
                            if(sceltaBoolean){canzoneScelta = canzoniTrovate.get((1+(9*pagina))-1);}
    
                            break;

                        case (2):

                            TextUtils.printLogo(RICERCA_CANZONI, 3);

                            System.out.print("E' stata scelta la canzone:\n\n\t" + canzoniTrovate.get((2+(9*pagina))-1).toString()+"\n\nConfermare? s/n\nScelta: ");
                            sceltaBoolean = TextUtils.readYesOrNo();
                            
                            if(sceltaBoolean){canzoneScelta = canzoniTrovate.get((2+(9*pagina))-1);}

                            break;

                        case (3):

                            TextUtils.printLogo(RICERCA_CANZONI, 3);

                            System.out.print("E' stata scelta la canzone:\n\n\t" + canzoniTrovate.get((3+(9*pagina))-1).toString()+"\n\nConfermare? s/n\nScelta: ");
                            sceltaBoolean = TextUtils.readYesOrNo();
                            
                            if(sceltaBoolean){canzoneScelta = canzoniTrovate.get((3+(9*pagina))-1);}

                            break;

                        case (4):

                            TextUtils.printLogo(RICERCA_CANZONI, 3);

                            System.out.print("E' stata scelta la canzone:\n\n\t" + canzoniTrovate.get((4+(9*pagina))-1).toString()+"\n\nConfermare? s/n\nScelta: ");
                            sceltaBoolean = TextUtils.readYesOrNo();
                            
                            if(sceltaBoolean){canzoneScelta = canzoniTrovate.get((4+(9*pagina))-1);}

                            break;

                        case (5):

                            TextUtils.printLogo(RICERCA_CANZONI, 3);

                            System.out.print("E' stata scelta la canzone:\n\n\t" + canzoniTrovate.get((5+(9*pagina))-1).toString()+"\n\nConfermare? s/n\nScelta: ");
                            sceltaBoolean = TextUtils.readYesOrNo();
                            
                            if(sceltaBoolean){canzoneScelta = canzoniTrovate.get((5+(9*pagina))-1);}

                            break;

                        case (6):

                            TextUtils.printLogo(RICERCA_CANZONI, 3);

                            System.out.print("E' stata scelta la canzone:\n\n\t" + canzoniTrovate.get((6+(9*pagina))-1).toString()+"\n\nConfermare? s/n\nScelta: ");
                            sceltaBoolean = TextUtils.readYesOrNo();
                            
                            if(sceltaBoolean){canzoneScelta = canzoniTrovate.get((6+(9*pagina))-1);}

                            break;

                        case (7):

                            TextUtils.printLogo(RICERCA_CANZONI, 3);

                            System.out.print("E' stata scelta la canzone:\n\n\t" + canzoniTrovate.get((7+(9*pagina))-1).toString()+"\n\nConfermare? s/n\nScelta: ");
                            sceltaBoolean = TextUtils.readYesOrNo();
                            
                            if(sceltaBoolean){canzoneScelta = canzoniTrovate.get((7+(9*pagina))-1);}

                            break;

                        case (8):

                            TextUtils.printLogo(RICERCA_CANZONI, 3);

                            System.out.print("E' stata scelta la canzone:\n\n\t" + canzoniTrovate.get((8+(9*pagina))-1).toString()+"\n\nConfermare? s/n\nScelta: ");
                            sceltaBoolean = TextUtils.readYesOrNo();
                            
                            if(sceltaBoolean){canzoneScelta = canzoniTrovate.get((8+(9*pagina))-1);}

                            break;

                        case (9):

                            TextUtils.printLogo(RICERCA_CANZONI, 3);

                            System.out.print("E' stata scelta la canzone:\n\n\t" + canzoniTrovate.get((9+(9*pagina))-1).toString()+"\n\nConfermare? s/n\nScelta: ");
                            sceltaBoolean = TextUtils.readYesOrNo();
                            
                            if(sceltaBoolean){canzoneScelta = canzoniTrovate.get((9+(9*pagina))-1);}
    
                            break;
                    
                        default:
                            
                            TextUtils.printErrorMessage("Il valore inserito non è valido. Premere un tasto per continuare", false);
                            in.nextLine();

                            break;
                    }

                    if(sceltaBoolean){
                                
                        playlist.addCanzone(canzoneScelta);

                        TextUtils.printLogo(CREAZIONE_PLAYLIST, 3);

                        System.out.println("La canzone \"" + canzoneScelta.getTitolo() + "\" e' stata aggiunta alla playlist!");

                    }
                    

                } else{ // ripulire il seguente blocco di codice

                    if(input.equalsIgnoreCase("previous")){

                        if(pagina>0){pagina--;} else{TextUtils.printErrorMessage("Non ci sono pagine precedenti. Premere un tasto per continuare...", false); in.nextLine();}
                        

                    } else if(input.equalsIgnoreCase("next")){

                        if(pagina == (Math.ceil((canzoniTrovate.size()/9.00))-1)){TextUtils.printErrorMessage("Non ci sono altre pagine. Premere un tasto per continuare...", false); in.nextLine();} else{pagina++;}


                    } else if(input.equalsIgnoreCase("cancel")){

                        TextUtils.printLogo( CREAZIONE_PLAYLIST, 3);

                        System.out.print("Vuoi annullare l'inserimento delle canzoni all'interno della playlist? s/n\nScelta: ");
                        sceltaBoolean = TextUtils.readYesOrNo();

                        if(sceltaBoolean){

                            confirmCancel(playlist);

                        }

                    }else {TextUtils.printErrorMessage("Il comando inserito non è stato riconosciuto, inserire nuovamente. Premere un tasto per continuare...", false); in.nextLine();}

                }

            }            

            System.out.print("\nContinuare con l'inserimento di brani musicali? s/n\nScelta: ");

            sceltaBoolean = TextUtils.readYesOrNo();

        } while (sceltaBoolean);

        for(Canzone c : playlist.canzoni){TextUtils.printDebug(c.toString());}
        
        return playlist;

    }

    private static Playlist confirmCancel(Playlist playlist){

        boolean sceltaBoolean;

        if(!playlist.isEmpty()){

            TextUtils.printLogo( CREAZIONE_PLAYLIST, 2);

            System.out.println("All'interno della playlist ci sono " + playlist.getSize() + " canzoni:\n");

           for (int i = 0; i < playlist.canzoni.size(); i++) {System.out.println("\t[" + TextUtils.BLUE_BOLD  + (i+1) +TextUtils.WHITE + "] " + playlist.canzoni.get(i));}
            
            System.out.print(" \nDesideri mantenerle e procedere alla creazione della playlist? s/n\nScelta: ");
            sceltaBoolean = TextUtils.readYesOrNo();

            if(sceltaBoolean){

                System.out.println("La playlist è stata creata!");
                return playlist;

            } else {

                System.out.println("Creazione playlist annullata!");
                return null;

            }

        }

        return null;

    }

    public String toCSV(){

        StringBuilder sb = new StringBuilder(this.user+","+this.nome+",\"");

        for(Canzone c : this.canzoni){

            sb.append(c.getTitolo()+",");
            
        }        

        return "";

    }

    public boolean isEmpty(){return this.canzoni.isEmpty();}

}

class PlaylistManager {
    
    private PlaylistManager(){super();}

    public static void scriviPlaylist(Playlist p){ // implementare!!!

        try{

            File file = new File("progettoInterdisciplinareA\\data\\Playlist.dati");
            FileOutputStream f = new FileOutputStream(file);

            PrintWriter out = new PrintWriter(f);
            
            out.write("string");

            out.flush();

            out.close();

        } catch (Exception e) {e.printStackTrace();}

    }
}


