package ar.fiuba.tdd.template.tp0;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
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

    public RegExGenerator( int maxLength ) {
        this.maxLength = maxLength + 1;     // Le sumo 1, porque en el Random no se incluye el valor limite.
        this.random = new Random();
        this.lista = new ArrayList<>();
    }

    // TODO: Uncomment parameters
    public List<String> generate( String regEx, int numberOfResults ) {

        int cantidadDeOcurrencias;
        int minimaCantidadDeOcurrencias = 1;

        String[] tokens = regEx.split("(?=\\.)|(?<=\\+)|(?<=\\*)|(?<=\\?)|(?=\\[)");

        System.out.println("Los tokens quedan: ");
        for( String token : tokens ) {

            System.out.println(token);
        }

        for( String token : tokens ){

            if( appendTokens ) {

                appendTokens = false;
                if( token.equals( PUNTO ) && auxString.length() == 0 ) {
                    addCharacter( token , PUNTO );
                    continue;
                } else if ( token.indexOf( PUNTO ) == 0 && ! isReservedChar( Character.toString(token.charAt( token.length() - 1 )))) {
                    addCharacter( PUNTO, PUNTO);
                    token = token.substring( 1 );
                } else {

                    if( auxString.length() != 0 && token.equals( PUNTO ) ) {

                        addCharacter( auxString , null );
                        addCharacter( token , null );
                        continue;
                    } else {
                        auxString += token;
                        token = auxString;

                    }
                }
            }

            System.out.println("El token es: " + token);

            int lastCharIndex = 1;
            String lastChar = "";

            if( ! token.equals( PUNTO ) &&  token.length() > 1) {
                lastCharIndex = token.length() - 1;
                lastChar = token.substring( lastCharIndex );
            }

            System.out.println("LastCharIndex: " + lastCharIndex);
            System.out.println("LastChar: " + lastChar);


            if( isBackSlashed(token, lastCharIndex) ) {
                System.out.println("Es Backslahsed");

                auxString = token.replace( CONTRA_BARRA, "" );
                appendTokens = true;
            } else {


                String set = null;
                String continuacion = null;
                String lastCharDeContinuacion = null;

                if( isASet( token ) ) {

                    if( token.lastIndexOf( "]" ) + 2 >= token.length() ) {
                        continuacion = token.substring( token.lastIndexOf( "]" ) + 1);
                    } else {
                        continuacion = token.substring( token.lastIndexOf( "]" ) + 1, token.length() - 1);
                    }

                    if( isReservedChar(continuacion) || continuacion.equals("")) continuacion = null;
                    lastCharDeContinuacion = lastChar;

                    token = token.substring( token.indexOf( "[" ) + 1, token.lastIndexOf( "]" ) );
                    lastChar = Character.toString(token.charAt( token.indexOf( "]" ) + 1));
                    set = token;
                } else {
                    if( token.length() > 1 )
                        token = token.substring( 0, lastCharIndex );
                }
                System.out.println("El token quedo: " + token);
                boolean primeraVez = true;
                do {

                    if ( ! primeraVez ){
                        token = continuacion;
                        set = null;
                        continuacion = null;
                        lastChar = lastCharDeContinuacion;

                        System.out.println("El token queasdasddo: " + token);

                    }

                    switch ( lastChar ) {

                        case PREGUNTA:

                            boolean agregar = random.nextBoolean();

                            if( agregar )
                                addCharacter( token, set );
                            break;

                        case ASTERISCO:

                            cantidadDeOcurrencias = minimaCantidadDeOcurrencias + ( random.nextInt( maxLength - minimaCantidadDeOcurrencias ) );

                            for( int i = 0; i < cantidadDeOcurrencias; i++ ) {
                                addCharacter( token, set );
                            }
                            break;

                        case MAS:

                            cantidadDeOcurrencias = 1 + ( random.nextInt( maxLength ) );

                            for( int i = 0; i < cantidadDeOcurrencias; i++ ) {
                                addCharacter( token, set );
                            }
                            break;

                        default:

                            if( ! token.equals( PUNTO ) ) {
                                token += lastChar;
                                addCharacter( token, set);
                            } else {
                                if( ! token.equals( "" ) ) {
                                    addCharacter( token, set );
                                }
                                if( ! lastChar.equals( "" ) ) {
                                    addCharacter( lastChar, set);
                                }

                            }
                            break;
                    }
                    primeraVez = false;
                } while ( continuacion != null );
            }
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

    private void addCharacter( String token, String set) {

        if ( set != null ) {
            char caracter = set.charAt(random.nextInt(set.length()));
            lista.add(String.valueOf(caracter));
            System.out.println("Agregue: " + caracter);
        } else if( token.equals( PUNTO )  ) {
            char caracter = ( char ) random.nextInt( TAMANIO_ASCII );
            lista.add( "a" );
            System.out.println("Agregue  aaa: " + caracter);
        } else {
            lista.add( token );
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