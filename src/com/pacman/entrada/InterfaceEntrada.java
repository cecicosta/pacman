package com.pacman.entrada;

public class InterfaceEntrada {

}
interface Estado {
    public Estado proximo(Entrada in);
}
class Entrada {
    private String entrada;
    private int atual;
    public Entrada(String entrada) {this.entrada = entrada;}
    char leia() { return atual>=entrada.length() ? 0 : entrada.charAt(atual++); }
}