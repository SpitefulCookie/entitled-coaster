
## Folder Structure

The workspace contains these folders:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies
- `doc`: the folder to maintain documents such as the User manual and Technical manual
- `data` : the folder to maintain data structures

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

---

# Note sullo sviluppo dell'applicazione:

## Intestazione file java:

Da porre in testa a tutti i file `.java`:

```

    /*
    * Progetto svolto da:
    * 
    * <Cognome> <Nome> <Matricola>, Ateneo di Varese
    * <Cognome> <Nome> <Matricola>, Ateneo di Varese
    * <Cognome> <Nome> <Matricola>, Ateneo di Varese
    *
    */

 ```

## Score:

Il punteggio delle emozioni (scala da 1 a 5) di ogni brano musicale va interpretata dal team di sviluppo.

## Emozioni:

L'utente può inserire più emozioni per canzone e, per ognuna di esse, è possibile aggiungere una nota avente - al massimo - 256 caratteri
Le emozioni non vanno modificate.

## Canzoni:

Va realizzato una repository (*pseudo-realistico, sfidante*) di canzoni realizzato mediante un file testuale `Canzoni.dati` (o `.csv`) contenente i dati di ciascuna di esse:
    
 - Titolo
 - Autore
 - Anno
  
 > opzionali:

 - Album
 - Durata
 - Genere
 - Ulteriori informazioni "a piacere"

Lo scopo dell'applicazione NON è quello di ascoltare musica.
Non vanno incluse informazioni come testi musicali, al massimo si possono porre dei link che portino l'utente a siti esterni.

> **Il file `Canzoni.dati` deve pre-esistere per utilizzare l'applicazione.**

## Utilizzo

L'applicazione potrà essere sviluppata utilizzando un interfaccia grafica oppure a riga di testo.

All'apertura dell'applicazione, ogni utente sarà in grado di:

 - consultare il repository delle canzoni senza aver la necessità di essere registrato. 
 - registrarsi alla piattaforma

Gli utenti registrati saranno in grado di:

 - realizzare una playlist
 - inserire le emozioni provate all'ascolto di determinate canzoni

## Registrazione:

L'utente dovrà inserire:

 - Nome
 - Cognome
 - CF
 - Indirizzo toponomastico
 - email
 - userid
 - password (salvata in chiaro oppure crittografata)

Bisognerà effettuare dei controlli sui dati inseriti i quali, se corretti, andranno salvati all'interno di un file `UtentiRegistrati.dati` anch'esso in formato `.txt` o `.csv`.

## Codice

Documentare il codice in javadoc - **Mandatorio**!

## Documentazione

La stesura dei manuali verrà trattata in una lezione a parte
"C:\Windows"
"progettoInterdisciplinareA\README.md" 