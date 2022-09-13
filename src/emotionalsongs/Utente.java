package emotionalsongs;

import org.apache.commons.lang3.StringUtils;

/*
 * Progetto svolto da:
 * 
 * Della Chiesa Mattia 749904, Ateneo di Varese
 *
 */

/**
 * La classe {@code Utente} rappresenta le informazioni relative ad un utente della piattaforma.
 * <p>Ogni utente viene identificato da i seguenti dati:
 *  <ul>
 *  <li> Nominativo
 *  <li> Codice fiscale
 *  <li> Indirizzo
 *  <li> Email
 *  <li> UserId
 *  <li> Password
 *  </ul>
 * 
 *  @see emotionalsongs.Indirizzo
 *  @author <a href="https://github.com/SpitefulCookie">Della Chiesa Mattia</a>
 *  
 */
public class Utente {

    private String nominativo;
    private String codiceFiscale;
    private Indirizzo indirizzo;
    private String email;
    private String userId; 
    private String password;


    /***
     * Costruisce un oggetto {@code Utente} a partire dai parametri forniti.
     * @param nominativo un oggetto di tipo {@code String} dove è contenuto il nome e cognome dell'utente.
     * @param codFiscale un oggetto di tipo {@code String} che rappresenta il codice fiscale dell'utente.
     * @param indirizzo un'array bidimensionale di tipo {@code String} contenente l'indirizzo toponomastico dell'utente.
     * @param email un oggetto di tipo {@code String} che rappresenta l'indirizzo email dell'utente.
     * @param userId un oggetto di tipo {@code String} che rappresenta l'identificativo univoco dell'utente.
     * @param password un oggetto di tipo {@code String} che rappresenta la password dell'utente.
     * @throws AddressNotValidException se l'indirizzo passato non è valido.
     */
    public Utente(String nominativo, String codFiscale, String[] indirizzo, String email, String userId, String password) throws AddressNotValidException {
        
        this.nominativo = nominativo;
        this.codiceFiscale = codFiscale;
        this.indirizzo = new Indirizzo(indirizzo);
        this.email = email;
        this.userId = userId;
        this.password = password;

    }

    /**
     * Crea un nuovo oggetto di tipo {@code Utente} in cui tutti i campi sono vuoti.
     */
    public Utente(){

        this.nominativo = null;
        this.codiceFiscale = null;
        this.indirizzo = null;
        this.email = null;
        this.userId = null;
        this.password = null;

    }

    /**
     * Crea un nuovo oggetto di tipo {@code Utente} a partire dai dati forniti in input.
     * @param nominativo un oggetto di tipo {@code String} dove è contenuto il nome e cognome dell'utente.
     * @param codFiscale un oggetto di tipo {@code String} che rappresenta il codice fiscale dell'utente.
     * @param indirizzo un'array bidimensionale di tipo {@code String} contenente l'indirizzo toponomastico dell'utente.
     * @param email un oggetto di tipo {@code String} che rappresenta l'indirizzo email dell'utente.
     * @param userId un oggetto di tipo {@code String} che rappresenta l'identificativo univoco dell'utente.
     * @param password un oggetto di tipo {@code String} che rappresenta la password dell'utente.
     */
    public Utente(String nominativo, String codFiscale, Indirizzo indirizzo, String email, String userId, String password){
        
        this.nominativo = nominativo;
        this.codiceFiscale = codFiscale;
        this.indirizzo = indirizzo;
        this.email = email;
        this.userId = userId;
        this.password = password;

    }

    /***
     * Costruisce un oggetto {@code Utente} a partire da un array di stringhe
     * @param datiIn un array di stringhe bidimensionali dove ogni indice viene mappato a:
     *  <ul>
     *  <li> 0 -> Nominativo
     *  <li> 1 -> Codice Fiscale
     *  <li> 2 -> Indirizzo
     *  <li> 3 -> Email
     *  <li> 4 -> User ID
     *  <li> 5 -> Password
     *  </ul>
     * @throws AddressNotValidException se l'indirizzo non è valido
     */
    public Utente(String datiIn) throws AddressNotValidException {

        String address = datiIn.substring(datiIn.indexOf(",\""), datiIn.indexOf("\",")+1);
        address = address.substring(1, address.length()); // rimuove gli apici prima e dopo l'indirizzo

        StringBuilder sb = new StringBuilder(datiIn);

        sb.delete(datiIn.indexOf(",\""), datiIn.indexOf("\",")+1); //rimuove la porzione contenente l'indirizzo dalla stringa letta in input

        String[] dati = sb.toString().split(",");

        this.nominativo = dati[0];
        this.codiceFiscale = dati[1];
        this.indirizzo = new Indirizzo(address);
        this.email = dati[2];
        this.userId = dati[3];
        this.password = dati[4];
        
    }

    /***
     * Restituisce una stringa contenente il nominativo dell'utente
     * @return un oggetto di tipo {@code String} contenente il nome dell'utente
     */

    public String getNominativo(){return this.nominativo;}

    /***
     * Restituisce una stringa contenente il codice fiscale dell'utente
     * @return un oggetto di tipo {@code String} contenente il codice ficale dell'utente
     */
    public String getCodiceFiscale(){return this.codiceFiscale;}

    /***
     * Restituisce l'indirizzo toponomastico dell'utente
     * @return un oggetto di tipo {@code Indirizzo} contenente l'indirizzo toponomastico dell'utente suddiviso nelle sue componenti atomiche
     */
    public Indirizzo getIndirizzo(){return this.indirizzo;}

    /***
     * Restituisce l'indirizzo di posta elettronica dell'utente
     * @return un oggetto di tipo {@code String} contenente l'email dell'utente
     */
    public String getEmail(){return this.email;}

    /***
     * Restituisce l'userID dell'utente
     * @return un oggetto di tipo {@code String} contenente l'id utente
     */
    public String getUserId(){return this.userId;}

    /***
     * Restituisce la password dell'utente
     * @return un oggetto di tipo {@code String} contenente la password dell'utente
     */
    public String getPassword(){return this.password;}

    /***
     * Setta il nominativo dell'utente
     * @param nominativo un oggetto di tipo {@code String} contenente il nome e cognome dell'utente
     */
    public void setNominativo(String nominativo){this.nominativo = nominativo;}

    /***
     * Setta il codice fiscale dell'utente
     * @param codiceFiscale un oggetto di tipo {@code String} contenente il codice fiscale dell'utente
     */
    public void setCodiceFiscale(String codiceFiscale){this.codiceFiscale = codiceFiscale;}

    /***
     * Setta l'indirizzo toponomastico dell'utente
     * @param indirizzo un oggetto di tipo {@code Indirizzo} contenente l'indirizzo toponomastico dell'utente
     */
    public void setIndirizzo(Indirizzo indirizzo){this.indirizzo = indirizzo;}

    /***
     * Setta l'indirizzo di posta elettronica dell'utente
     * @param email un oggetto di tipo {@code String} contenente l'indirizzo email dell'utente
     */
    public void setEmail(String email){this.email = email;}

    /***
     * Setta l'userID dell'utente
     * @param userId un oggetto di tipo {@code String} contenente l'id utente
     */
    public void setUserId(String userId){this.userId = userId;}

    /***
     * Setta la password dell'utente
     * @param password un oggetto di tipo {@code String} contenente la password dell'utente
     */  
    public void setPassword(String password){this.password = password;}

    /***
     * Restituisce i dati dell'utente
     * @return un oggetto {@code String} contenente tutti i dati dell'utente
     */ 
    @Override
    public String toString() {

        /*
         * Il seguente blocco di codice si assicura che i risultati siano tabulati correttamente indipendentemente dalla 
         * lunghezza delle parole che precedono i dati (a seconda del linguaggio scelto la tabulazione precedentemente definita non allineava correttamente i dati)
         * 
         * OUTPUT:
         * 
         *      Confermare i dati inseriti:
         *
         *      Nominativo:                    Nome Cognome
         *      Codice fiscale:                CF
         *      Indirizzo:                     Via numero, cap Città (Prov)
         *      Email:                         email
         *      Nome Utente:                   userid
         *      Password:                      password 
         * 
         *      Conferma? (s/n):
         *      Scelta: 
         * 
         */

        StringBuilder sb = new StringBuilder();

        int cfMessageLength = EmotionalSongs.languageBundle.getString("CF").length();

        sb.append(TextUtils.WHITE + EmotionalSongs.languageBundle.getString("Name") + TextUtils.RESET + 
            StringUtils.repeat("", " ", cfMessageLength - EmotionalSongs.languageBundle.getString("Name").length()) +
            " \t"     + this.nominativo + "\n");

        sb.append(TextUtils.WHITE + EmotionalSongs.languageBundle.getString("CF") + TextUtils.RESET +
            " \t"     + this.codiceFiscale + "\n");

        sb.append(TextUtils.WHITE + EmotionalSongs.languageBundle.getString("Address") + TextUtils.RESET +
            StringUtils.repeat("", " ", cfMessageLength - EmotionalSongs.languageBundle.getString("Address").length()) +
            " \t"     + this.indirizzo.toString() + "\n");

        sb.append(TextUtils.WHITE + EmotionalSongs.languageBundle.getString("Email") + TextUtils.RESET +
            StringUtils.repeat("", " ", cfMessageLength - EmotionalSongs.languageBundle.getString("Email").length()) +
            " \t"     + this.email + "\n");

        sb.append(TextUtils.WHITE + EmotionalSongs.languageBundle.getString("Username") + TextUtils.RESET + 
            StringUtils.repeat("", " ", cfMessageLength - EmotionalSongs.languageBundle.getString("Username").length()) +
            " \t"     + this.userId + "\n");

        sb.append(TextUtils.WHITE + EmotionalSongs.languageBundle.getString("Password") + TextUtils.RESET +
            StringUtils.repeat("", " ", cfMessageLength - EmotionalSongs.languageBundle.getString("Password").length()) +
            " \t"     + this.password);

        return sb.toString();
       
    }

    /**
     * Restituisce un oggetto di tipo {@code String} contenente tutti i dati dell'utente in formato CSV
     * @return un oggetto di tipo {@code String} contenente tutti i dati dell'utente in formato CSV
     */
    public String toCSV() {
        return this.nominativo + "," 
        + this.codiceFiscale + ","
        + this.indirizzo.formatForCSV() + "," 
        + this.email + "," 
        + this.userId + "," 
        + this.password;
    }

}

/**
     * La classe {@code Indirizzo} rappresenta un indirizzo toponomastico suddiviso nelle sue componenti atomiche quali:<p>
     *<ul>
    * <li> Via
    * <li> Numero
    * <li> CAP
    * <li> Città
    * <li> Provincia

    * </ul>
    * <p>
    * Autore: @author  <a href=https://github.com/SpitefulCookie">Della Chiesa Mattia</a>
    */

class Indirizzo{

    private String via;
    private String numeroCivico;
    private String cap;
    private String citta;
    private String provincia;

    /***
     * Costruisce un oggetto {@code Indirizzo} a partire da un array di tipo {@code String}.
     * @param indirizzo un array di tipo {@code String} che rappresenta un indirizzo toponomastico
     */
    public Indirizzo(String[] indirizzo) throws AddressNotValidException {

        if (indirizzo.length != 5) {

            throw new AddressNotValidException(indirizzo.length);

        } else{

            this.via = indirizzo[0];
            this.numeroCivico = indirizzo[1];
            this.cap = indirizzo[2];
            this.citta = indirizzo[3];
            this.provincia = indirizzo[4];

        }
        
    }

    /***
     * Costruisce un oggetto {@code Indirizzo} a partire da un oggetto di tipo {@code String}.
     * @param str una stringa che rappresenta un indirizzo toponomastico nel formato: <Nome della Via/piazza>, <numero civico>, <cap>, <citta'>, <provincia>"
     */
    public Indirizzo(String str) throws AddressNotValidException {

        String[] indirizzo;

        if(str.startsWith("\"")){
            indirizzo = str.substring(1,(str.length()-1)).replace(", ", ",").split(","); // in fase di registrazione viene immesso l'indirizzo separto da virgole mentre nel file UtentiRegistrati.dati viene codificato mediante ;. Chiamando il metodo replace seguito da split sulla stringa permette di mantenere un singolo costruttore
        }else{
            indirizzo = str.split(", ");
        }

        if (indirizzo.length != 5) {

            throw new AddressNotValidException(indirizzo.length);
            
        } else{

            for(String v : indirizzo){
                if (StringUtils.trim(v).equals("")){throw new AddressNotValidException(EmotionalSongs.languageBundle.getString("ERROR_NotAllFieldCompiled"));}
            }

            this.via = StringUtils.trim(StringUtils.capitalize(indirizzo[0].toLowerCase())); // Formatta la via cosicchè solamente il primo carattere sia maiuscolo
            this.numeroCivico = StringUtils.trim(indirizzo[1]);
            this.cap = StringUtils.trim(indirizzo[2]);
            this.citta = StringUtils.trim(StringUtils.capitalize(indirizzo[3].toLowerCase())); // Idem
            this.provincia = StringUtils.trim(StringUtils.capitalize(indirizzo[4].toLowerCase()));
        
        }
        
    }

    /***
     * Restituisce il campo via dell'indirizzo
     * @return un oggetto di tipo {@code String} contenente la via
     */
    public String getVia() {return this.via;}

    /***
     * Restituisce il campo CAP dell'indirizzo
     * @return un oggetto di tipo {@code String} contenente il CAP
     */
    public String getCap() {return this.cap;}

    /***
     * Restituisce il campo Città dell'indirizzo
     * @return un oggetto di tipo {@code String} contenente La città
     */
    public String getCitta(){return this.citta;}

    /***
     * Restituisce il campo provincia dell'indirizzo
     * @return un oggetto di tipo {@code String} contenente la provincia
     */
    public String getProvincia(){return this.provincia;}

    /***
     * Restituisce il campo numero civico dell'indirizzo
     * @return un oggetto di tipo {@code String} rappresentante il numero civico
     */
    public String getNumeroCivico(){return this.numeroCivico;}

    /***
     * Restituisce le informazioni sull'indirizzo
     * @return un oggetto {@code String} contenente tutti i dati relativi all'indirizzo toponomastico.
     */
    @Override
    public String toString(){
        return this.via + " " + this.numeroCivico + ", " + this.cap + " " + this.citta + " (" + this.provincia + ")";
    }

    /***
     * Formatta le informazioni relative all'utente in un formato conforme allo standard CSV.
     * @return un oggetto {@code String} racchiuso tra doppi apici e contenente tutti i dati relativi all'utente separati da una virgola.
     */
    public String formatForCSV(){
        return "\""+this.via + "," + this.numeroCivico + "," + this.cap + "," + this.citta + "," + this.provincia+"\"";
    }

}

/**
 * Eccezione lanciata per indicare che i dati forniti in input ad un metodo o costruttore non rappresentano correttamente un indirizzo toponomastico.
 * @author  <a href=https://github.com/SpitefulCookie>Della Chiesa Mattia</a>
 */
class AddressNotValidException extends Exception{

    /**
     * Realizza un {@code AddressNotValidException} che rappresente un errore nella creazione di un oggetto {@code Indirizzo} dovuto a un numero non sufficiente oppure eccessivo di parametri.
     * @param num il numero di parametri forniti in input
     */
    
    public AddressNotValidException(int num){
        super(String.format(EmotionalSongs.languageBundle.getString("ERROR_InvalidAddress"), num, 5));   
    }

    /**
     * Realizza un {@code AddressNotValidException} con un messaggio di errore specificato dall'utente
     * @param message un oggetto di tipo {@code String} contenente il messaggio di errore
     */
    public AddressNotValidException(String message){
        super(message);  
    }

}
