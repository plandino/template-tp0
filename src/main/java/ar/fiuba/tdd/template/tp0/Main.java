//package ar.fiuba.tdd.template.tp0;
//
//import java.util.List;
//
///**
// * Created by panchoubuntu on 18/03/16.
// */
//public class Main {
//
//    private static RegExGenerator generador = new RegExGenerator(5);
//    private static List<String> listaDePalabras;
//
//    public static void main(String[] args) {
//
//        listaDePalabras = generador.generate("\\@..", 3);
//        int size = listaDePalabras.size();
//        String result = listaDePalabras.get(0);
//        System.out.println(result.matches("^\\@..$"));
//
//        System.out.println(result.matches("^\\@..$"));
//        System.out.println(result);
//
//        for(int i = 0; i < size; i++){
//            String palabra = listaDePalabras.get(i);
//            System.out.println(palabra);
//        }
////        listaDePalabras = generador.generate("*.l.s", 3);
////        size = listaDePalabras.size();
////        for(int i = 0; i < size; i++){
////            String palabra = listaDePalabras.get(i);
////            System.out.println(palabra);
////        }
//        System.out.println("Goodbye, World!");
//    }
//}
