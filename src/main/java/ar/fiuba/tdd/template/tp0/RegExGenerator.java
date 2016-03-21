package ar.fiuba.tdd.template.tp0;

import com.sun.deploy.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class RegExGenerator {
    // TODO: Uncomment this field
    private int maxLength;
    static final private int TAMANIO_ASCII = 256;

    private Random random;
    static final private String CONTRA_BARRA = "\\";
    static final private String PUNTO        = ".";
    static final private String ASTERISCO    = "*";
    static final private String MAS          = "+";
    static final private String PREGUNTA     = "?";
    static final private String CORCHETE     = "[";

    private String auxString = "";

    private boolean appendTokens = false;

    private ArrayList<String> lista;
    private ArrayList<String> listaTemporaria; // En esta se van cargando cada pedazo del string para luego pasar los string completos a la lista

    public RegExGenerator( int maxLength ) {
        this.maxLength = maxLength + 1;     // Le sumo 1, porque en el Random no se incluye el valor limite.
        this.random = new Random();
        this.lista = new ArrayList<>();
    }

    // TODO: Uncomment parameters
    public List<String> generate( String regEx, int numberOfResults ) {

        for( int j = 0; j < numberOfResults; j++ ) {

            this.listaTemporaria = new ArrayList<>();

            int cantidadDeOcurrencias;
            int minimaCantidadDeOcurrencias = 1;

            String[] tokens = regEx.split("(?=\\.)|(?<=\\+)|(?<=\\*)|(?<=\\?)|(?=\\[)");

            System.out.println("Los tokens quedan: " + tokens.length);
            for (String token : tokens) {

                System.out.println(token);
            }



            for( int i  = 0; i < tokens.length || ( appendTokens ); i++) {

                if (appendTokens) {

                    appendTokens = false;
                    if( i > tokens.length - 1 ) {
                        addCharacter( auxString, null);
//                        passFromTemporaryListToFinalList();
                        break;
                    } else  if (tokens[i].equals(PUNTO) && auxString.length() == 0) {
                        addCharacter(tokens[i], PUNTO);
                        continue;
                    } else if (tokens[i].indexOf(PUNTO) == 0 && !isReservedChar(Character.toString(tokens[i].charAt(tokens[i].length() - 1)))) {
                        addCharacter(PUNTO, PUNTO);
                        tokens[i] = tokens[i].substring(1);
                    } else {
                        if (auxString.length() != 0 && tokens[i].equals(PUNTO)) {
                            addCharacter(auxString, null);
                            addCharacter(tokens[i], null);
                            continue;
                        } else {
                            auxString += tokens[i];
                            tokens[i] = auxString;
                        }
                    }
                }

                System.out.println("El token es: " + tokens[i]);

                int lastCharIndex = 1;
                String lastChar = "";

                if (!tokens[i].equals(PUNTO) && tokens[i].length() > 1) {
                    lastCharIndex = tokens[i].length() - 1;
                    lastChar = tokens[i].substring(lastCharIndex);
                }

                System.out.println("LastCharIndex: " + lastCharIndex);
                System.out.println("LastChar: " + lastChar);


                if (isBackSlashed(tokens[i], lastCharIndex)) {
                    System.out.println("Es Backslahsed");

                    auxString = tokens[i].replace(CONTRA_BARRA, "");
                    appendTokens = true;
                } else {


                    String set = null;
                    String continuacion = null;
                    String lastCharDeContinuacion = null;

                    if (isASet(tokens[i])) {

                        if (tokens[i].lastIndexOf("]") + 2 >= tokens[i].length()) {
                            continuacion = tokens[i].substring(tokens[i].lastIndexOf("]") + 1);
                        } else {
                            continuacion = tokens[i].substring(tokens[i].lastIndexOf("]") + 1, tokens[i].length() - 1);
                        }

                        if (isReservedChar(continuacion) || continuacion.equals("")) continuacion = null;
                        lastCharDeContinuacion = lastChar;

                        tokens[i] = tokens[i].substring(tokens[i].indexOf("[") + 1, tokens[i].lastIndexOf("]"));
                        lastChar = Character.toString(tokens[i].charAt(tokens[i].indexOf("]") + 1));
                        set = tokens[i];
                    } else {
                        if (tokens[i].length() > 1)
                            tokens[i] = tokens[i].substring(0, lastCharIndex);
                    }
                    System.out.println("El token quedo: " + tokens[i]);
                    boolean primeraVez = true;
                    do {

                        if (!primeraVez) {
                            tokens[i] = continuacion;
                            set = null;
                            continuacion = null;
                            lastChar = lastCharDeContinuacion;

                            System.out.println("El token queasdasddo: " + tokens[i]);

                        }

                        switch (lastChar) {

                            case PREGUNTA:

                                boolean agregar = random.nextBoolean();

                                if (agregar)
                                    addCharacter(tokens[i], set);
                                break;

                            case ASTERISCO:

                                cantidadDeOcurrencias = minimaCantidadDeOcurrencias + (random.nextInt(maxLength - minimaCantidadDeOcurrencias));

                                for (int k = 0; k < cantidadDeOcurrencias; k++) {
                                    addCharacter(tokens[i], set);
                                }
                                break;

                            case MAS:

                                cantidadDeOcurrencias = 1 + (random.nextInt(maxLength));

                                for (int k = 0; k < cantidadDeOcurrencias; k++) {
                                    addCharacter(tokens[i], set);
                                }
                                break;

                            default:

                                if (!tokens[i].equals(PUNTO)) {
                                    tokens[i] += lastChar;
                                    addCharacter(tokens[i], set);
                                } else {
                                    if (!tokens[i].equals("")) {
                                        addCharacter(tokens[i], set);
                                    }
                                    if (!lastChar.equals("")) {
                                        addCharacter(lastChar, set);
                                    }

                                }
                                break;
                        }
                        primeraVez = false;
                    } while (continuacion != null);
                }
            }

            passFromTemporaryListToFinalList();

        }

        return lista;

    }

    private boolean isBackSlashed( String token, int lastCharIndex ) {
        return token.contains( "\\");
//        if( lastCharIndex > 0 ){
//            String anteUltimoChar = token.substring( lastCharIndex - 1, lastCharIndex);
//            return anteUltimoChar.equals( CONTRA_BARRA );
//        }
//        return false;
    }

    private void passFromTemporaryListToFinalList() {
        String completeString = "";

        for(int m = 0; m < listaTemporaria.size() ; m++){
            completeString += listaTemporaria.get(m);
        }
        lista.add( completeString );
    }

    private void addCharacter( String token, String set) {

        if ( set != null ) {
            char caracter = set.charAt(random.nextInt(set.length()));
            listaTemporaria.add(String.valueOf(caracter));
            System.out.println("Agregue: " + caracter);
        } else if( token.equals( PUNTO )  ) {
            char caracter = ( char ) random.nextInt( TAMANIO_ASCII );
            listaTemporaria.add( String.valueOf(caracter) );
            System.out.println("Agregue : " + caracter);
        } else {
            listaTemporaria.add( token );
            System.out.println("Agregue: " + token);
        }
    }

    private boolean isASet( String token ) {
        return token.contains( "[" );
    }

    private boolean isReservedChar( String token ) {

        boolean esPunto = ( token.equals( PUNTO ) );
        boolean esPregunta = ( token.equals( "?" ) );
        boolean esAsterisco = ( token.equals( "*" ) );
        boolean esMas = ( token.equals( MAS ) );


        System.out.println("Es caracter reservado: " + (esPunto || esAsterisco || esPregunta) );

        return esPunto || esAsterisco || esPregunta || esMas;
    }
}