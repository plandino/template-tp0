package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RegExGenerator {
    // TODO: Uncomment this field
    private int maxLength;
    private Random random;

    public RegExGenerator( int maxLength ) {
        this.maxLength = maxLength + 1;     // Le sumo 1, porque en el Random no se incluye el valor limite.
        this.random = new Random();
    }

    // TODO: Uncomment parameters
    public List<String> generate( String regEx, int numberOfResults ) {

        int cantidadDeOcurrencias;
        int minimaCantidadDeOcurrencias = 1;

        ArrayList<String> lista = new ArrayList<>();
        
        String[] tokens = regEx.split("(?<=\\+)|(?<=\\*)|(?<=\\?)|(?=\\[)");


        for( String token : tokens ){
            System.out.println("El token es: " + token);

            int lastCharIndex = token.length() - 1;
            System.out.println("Last char index: " + lastCharIndex );

            String lastChar = token.substring( lastCharIndex );

            switch ( lastChar ) {
                case "?":

                    boolean agregar = random.nextBoolean();

                    if( agregar )
                        lista.add( token.substring( 0, lastCharIndex ) );
                    break;

                case "*":

                    cantidadDeOcurrencias = minimaCantidadDeOcurrencias + ( random.nextInt( maxLength - minimaCantidadDeOcurrencias ) );

                    for( int i = 0; i < cantidadDeOcurrencias; i++ ) {
                        lista.add( token.substring( 0, lastCharIndex ) );
                    }
                    break;

                case "+":

                    cantidadDeOcurrencias = 1 + ( random.nextInt( maxLength ) );

                    for( int i = 0; i < cantidadDeOcurrencias; i++ ) {
                        lista.add( token.substring( 0, lastCharIndex ) );
                    }
                    break;
            }

            switch ( token ) {

                case "\\":
                    lista.add("barra");
                    break;
                case "[":
                    lista.add("corchete");
                    break;
                case ".":
                    char caracter = ( char ) random.nextInt(255);
                    lista.add( String.valueOf( caracter ) );
                    break;
                default:
                    lista.add( token );
                    break;
            }
            System.out.println("Agregue: " + lista.get(lista.size() - 1));

        }

        return lista;
    }
}