package com.example.aarafaelyukilistview;

public class Item {
    private String chave;
    private String mesa;
    private String item;
    private String produto;
    private boolean atendido;

    public Item(String c, String m, String i, String p) {
        chave = c;
        mesa = m;
        item = i;
        produto = p;
        atendido = false;
    }
    public Item() {}

    public String getChave() { return chave; }

    public void setChave(String chave) { this.chave = chave; }

    public String getMesa() { return mesa; }

    public void setMesa(String mesa) { this.mesa = mesa; }

    public String getItemProd() { return item; }

    public void setItemProd(String item) { this.item = item; }

    public String getProduto() { return produto; }

    public void setProduto(String produto) { this.produto = produto; }

    public boolean isAtendido() { return atendido; }

    public void setAtendido(boolean atendido) { this.atendido = atendido; }

}
