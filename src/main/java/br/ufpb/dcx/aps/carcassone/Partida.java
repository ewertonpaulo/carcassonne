package br.ufpb.dcx.aps.carcassone;

import br.ufpb.dcx.aps.carcassone.tabuleiro.TabuleiroFlexivel;
import br.ufpb.dcx.aps.carcassone.tabuleiro.Tile;

import java.util.ArrayList;

public class Partida {

    private String status;
    private BolsaDeTiles tiles;
    private Tile proximoTile;
    private Tile tileAnterior;
    private TabuleiroFlexivel tabuleiro = new TabuleiroFlexivel("  ");
    private ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
    private Turno turno;
    private ArrayList<Turno> turnos = new ArrayList<>();
    private int jogadorIndex = 0;

    Partida(BolsaDeTiles tiles, Cor[] sequencia) {
        this.tiles = tiles;
        this.status = "Em_Andamento";
        adicionarJogadores(sequencia);
        pegarProximoTile();
        turno = new Turno(proximoTile, jogadores.get(jogadorIndex++), null);
        posicionarPrimeiroTile(proximoTile);
    }

    private void adicionarJogadores(Cor[] sequencia) {
        for (Cor cor : sequencia) {
            this.jogadores.add(new Jogador(cor));
        }
    }

    public String relatorioPartida() {
        String sequencia = "";

        for (int i = 0; i < jogadores.size() - 1; i++) {
            sequencia += jogadores.get(i).status() + "; ";
        }
        sequencia += jogadores.get(jogadores.size() - 1).status();
        return "Status: " + status + "\nJogadores: " + sequencia;
    }

    public String relatorioTurno() {
        if ( status.equals("Partida_Finalizada")) throw new ExcecaoJogo("Partida finalizada");
        return "Jogador: " + turno.getJogador() + "\nTile: " + turno.getTile() + "\nStatus: " + turno.getStatus();
    }

    public Partida girarTile() {
        if (this.status == "Partida_Finalizada") {
        	throw new ExcecaoJogo("Não pode girar tiles com a partida finalizada");
        }
        if (turno.getStatus().equals("Tile_Posicionado")) {
            throw new ExcecaoJogo("Não pode girar tile já posicionado");
        }
        else {
        	proximoTile.girar();
        }
        return this;
    }

    private void pegarProximoTile() {
    	tileAnterior=proximoTile;
        proximoTile = tiles.pegar();
        proximoTile.reset();
    }

    public Partida finalizarTurno() {
        turno.setStatus("Finalizado");
        if (tiles.size() > 1) {
            pegarProximoTile();
            novoTurno();
        } else {
           this.status = "Partida_Finalizada";
        }
        return this;
    }

    private void novoTurno() {
        turnos.add(turno);
        this.turno = new Turno(proximoTile, jogadores.get(jogadorIndex++), "Início_Turno");
    }

    public void posicionarPrimeiroTile(Tile tile) {
        tabuleiro.adicionarPrimeiroTile(proximoTile);
        turno.setStatus("Tile_Posicionado");
    }

    public Partida posicionarTile(Tile tileReferencia, Lado ladoTileReferencia) {
        tabuleiro.posicionar(tileReferencia, ladoTileReferencia, proximoTile);
        turno.setStatus("Tile_Posicionado");
        return this;
    }

    public Partida posicionarMeepleEstrada(Lado lado) {
        return this;
    }

    public Partida posicionarMeepleCampo(Vertice vertice) {
        return this;
    }

    public Partida posicionarMeepleCidade(Lado lado) {
        return this;
    }

    public Partida posicionarMeepleMosteiro() {
        return this;
    }

    public String getEstradas() {
        return null;
    }

    public String getCampos() {
        return null;
    }

    public String getCidades() {
        return null;
    }

    public String getMosteiros() {
        return null;
    }
    public String relatorioTabuleiro() {
    	return tabuleiro.toString();
    }
}
