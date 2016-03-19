package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RegExGenerator {
    // TODO: Uncomment this field
    private int maxLength;
    final private int TAMANIO_ASCII = 256;

    private Random random;
    private String CONTRA_BARRA = "\\";
    private String PUNTO = ".";
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
        for( String token : tokens ){

            if( appendTokens ) {
                auxString += token;
                token = auxString;
                appendTokens = false;
                if(token.equals( PUNTO ) ){
                    addCharacter(token, PUNTO );
                    continue;
                }
            }

            System.out.println("El token es: " + token);

            int lastCharIndex = 1;
            String lastChar = "";

            if( ! token.equals( PUNTO ) ) {
                lastCharIndex = token.length() - 1;
                lastChar = token.substring( lastCharIndex );
            }

//            if( token.contains( PUNTO )  ) {
//                addCharacter( PUNTO, null);
//                addCharacter( lastChar, null );
//            }

            System.out.println("LastCharIndex: " + lastCharIndex);
            System.out.println("LastChar: " + lastChar);


            if( isBackSlashed(token, lastCharIndex) ) {
                System.out.println("Es Backslahsed");

                auxString = token.replace( CONTRA_BARRA, "" );
                appendTokens = true;
            } else {

                String set = null;

                if( isASet( token ) ) {

                    // TODO: Ver que no estan backslashed
                    token = token.substring( token.indexOf( "[" ) + 1, token.lastIndexOf( "]" ) );
                    set = token;
                } else {
                    if( token.length() > 1 )
                        token = token.substring( 0, lastCharIndex );
                }
                System.out.println("El token quedo: " + token);

                switch ( lastChar ) {

                    case "?":

                        boolean agregar = random.nextBoolean();

                        if( agregar )
                            addCharacter( token, set );
                        break;

                    case "*":

                        cantidadDeOcurrencias = minimaCantidadDeOcurrencias + ( random.nextInt( maxLength - minimaCantidadDeOcurrencias ) );

                        for( int i = 0; i < cantidadDeOcurrencias; i++ ) {
                            addCharacter( token, set );
                        }
                        break;

                    case "+":

                        cantidadDeOcurrencias = 1 + ( random.nextInt( maxLength ) );

                        for( int i = 0; i < cantidadDeOcurrencias; i++ ) {
                            addCharacter( token, set );
                        }
                        break;

                    default:

                        if( ! token.equals( "" ) ) {
                            addCharacter( token, set );
                        }
                        if( ! lastChar.equals( "" ) ) {
                            addCharacter( lastChar, set);
                        }
                        break;
                }
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
        } else if( token.equals( PUNTO ) ) {
            char caracter = ( char ) random.nextInt( TAMANIO_ASCII );
            lista.add( "a" );
            System.out.println("Agregue  aaa: " + caracter);
        } else {
            lista.add( token );
            System.out.println("Agregue: " + token);

        }
    }

    // TODO: ver que no esta backslashed
    private boolean isASet( String token ) {
        return token.contains( "[" );
    }

    private boolean isReservedChar( String token ) {

        boolean esPunto = ( token.equals( PUNTO ) );
        boolean esPregunta = ( token.equals( "?" ) );
        boolean esAsterisco = ( token.equals( "*" ) );

        System.out.println("Es caracter reservado: " + (esPunto || esAsterisco || esPregunta) );

        return esPunto || esAsterisco || esPregunta;
    }

}