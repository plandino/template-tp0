package ar.fiuba.tdd.template.tp0;

import java.util.List;

/**
 * Created by panchoubuntu on 18/03/16.
 */
public class Main {

    private static RegExGenerator generador = new RegExGenerator(3);
    private static List<String> listaDePalabras;

    public static void main(String[] args) {

        listaDePalabras = generador.generate(".....+[abcd]*a+bss?h*vara\\*asd*.+", 3);
        int size = listaDePalabras.size();

        for(int i = 0; i < size; i++){
            String palabra = listaDePalabras.get(i);
            System.out.println(palabra);
        }
//        listaDePalabras = generador.generate("*.l.s", 3);
//        size = listaDePalabras.size();
//        for(int i = 0; i < size; i++){
//            String palabra = listaDePalabras.get(i);
//            System.out.println(palabra);
//        }
        System.out.println("Goodbye, World!");
    }
}
