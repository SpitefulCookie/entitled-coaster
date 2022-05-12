package emotionalsongs;

class Canzone{

    private String titolo;
    private String songUUID;
    private String autore;
    private int anno;

    public Canzone(String titolo, String autore, int anno, String uuid){

        this.songUUID = uuid;
        this.titolo = titolo;
        this.autore = autore;
        this.anno = anno;

    }

    public Canzone(String dati){

        String[] val;

        if(dati.contains("\"")){

            TextUtils.printDebug("Trovato un valore contenente una virgola!");

            String result = dati;
            String value;

            int begin;
            int last=-1;

            do{

                begin = dati.indexOf("\"", last+1);

                if(begin!=-1){

                    last = dati.indexOf('\"', begin+1);

                    TextUtils.printDebug("Indice inizio stringa: " +  begin + "\tIndice fine stringa: " +  last);

                    if(last!=-1){

                        value = dati.substring(begin+1, last).replace(',', '§');

                        TextUtils.printDebug("Valore estratto: " + value);
                        
                        result = result.replace(dati.substring(begin, last+1), value);

                    }

                }

            } while (last != -1 && begin != -1);
                        
            val = result.split(",");

            for(int i=0; i<val.length; i++){val[i] = val[i].replace("§", ",");}


        } else{val = dati.split(",");}

        this.songUUID = val[0];
        this.titolo = val[1];
        this.autore = val[2];
        this.anno = Integer.parseInt(val[3]);

    }

    public String getSongUUID(){return this.songUUID;}
    public String getAutore(){return this.autore;}
    public String getTitolo(){return this.titolo;}
    public int getAnno(){return this.anno;}

    @Override
    public String toString(){
        return this.titolo + " - " + this.autore + " (" + this.anno + ")";
    }

}