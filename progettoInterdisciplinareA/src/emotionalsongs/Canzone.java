package emotionalsongs;

class Canzone{

    private String titolo;
    private String autore;
    private int anno;

    public Canzone(String[] input) throws SongNotValidException{

        if (input.length != 3) {
            throw new SongNotValidException(input.length);
        } else{

            this.titolo = input[0];
            this.autore = input[1];
            this.anno = Integer.parseInt(input[2]);

        }

    }

    public Canzone(String titolo, String autore, int anno){

        this.titolo = titolo;
        this.autore = autore;
        this.anno = anno;

    }

    public Canzone(String dati){ //ottimizzabile

        if(dati.contains("\"")){

            String result = dati;
            String value;

            int begin;
            int last=-1;

            do{

                begin = dati.indexOf("\"", last+1);
                TextUtils.printDebug("Begin index: " +  begin);

                if(begin!=-1){

                    last = dati.indexOf('\"', begin+1);
                    TextUtils.printDebug("Last index: " +  last);

                    if(last!=-1){

                        value = dati.substring(begin+1, last).replace(',', '§');
                        result = result.replace(dati.substring(begin, last+1), value);

                    }

                }

            } while (last != -1 && begin != -1);
                        
            String[] val = result.split(",");

            for(int i=0; i<val.length; i++){val[i] = val[i].replace("§", ",");}
    
            this.titolo = val[1];
            this.autore = val[2];
            this.anno = Integer.parseInt(val[3]);

        } else{

            String[] val = dati.split(",");

            this.titolo = val[1];
            this.autore = val[2];
            this.anno = Integer.parseInt(val[3]);

        }       
        
    }

    public String getAutore(){return this.autore;}
    public String getTitolo(){return this.titolo;}
    public int getAnno(){return this.anno;}

    @Override
    public String toString(){
        return this.titolo + " - " + this.autore + " (" + this.anno + ")";
    }

}

class SongNotValidException extends Exception{

    public SongNotValidException(int num){

        super("Input non valido. Sono stati forniti " + num + " valori, mentre sono richiesti 3.");

    }

    public SongNotValidException(String msg){

        super(msg);

    }

}