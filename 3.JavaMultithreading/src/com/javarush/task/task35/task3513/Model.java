package com.javarush.task.task35.task3513;

import java.util.*;

public class Model {
    private static final int FIELD_WIDTH = 4;
    private Tile[][] gameTiles;
    int score = 0;
    int maxTile = 0;
    private Stack previousStates = new Stack();
    private Stack previousScores = new Stack();
    private boolean isSaveNeeded = true;

    public Model() {
        resetGameTiles();
    }

    public Tile[][] getGameTiles() {
        return gameTiles;
    }

    private void saveState(Tile[][] tiles){
        Tile[][] lastGameState = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        isSaveNeeded = false;
        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles.length; j++){
                lastGameState[i][j] = new Tile(tiles[i][j].value);
            }
        }
        previousStates.push(lastGameState);
        int lastGameScore = score;
        previousScores.push(lastGameScore);
    }

    public void rollback(){
        if(!previousScores.isEmpty() && !previousStates.isEmpty()) {
            gameTiles = (Tile[][]) previousStates.pop();
            score = (int) previousScores.pop();
        }
    }

    public boolean canMove() {
        if (!getEmptyTiles().isEmpty()) {
            return true;
        }
        for (int i = 0; i < gameTiles.length; i++) {
            for (int j = 0; j < gameTiles.length - 1; j++) {
                if (gameTiles[i][j].value == gameTiles[i][j + 1].value) {
                    return true;
                }
            }
        }
        for (int j = 0; j < gameTiles.length; j++) {
            for (int i = 0; i < gameTiles.length - 1; i++) {
                if (gameTiles[i][j].value == gameTiles[i + 1][j].value) {
                    return true;
                }
            }
        }
        return false;
    }


    public void addTile() {
        List<Tile> list = getEmptyTiles();
        if (list.size() != 0) {
            int randomTile = (int) (Math.random() * list.size());
            for (int i = 0; i < list.size(); i++) {
                if (i == randomTile) {
                    list.get(i).value = (Math.random() < 0.9 ? 2 : 4);
                }
            }
        }
    }

    private List<Tile> getEmptyTiles() {
        List<Tile> list = new ArrayList<>();
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                if (gameTiles[i][j].isEmpty()) {
                    list.add(gameTiles[i][j]);
                }
            }
        }
        return list;
    }

    public void resetGameTiles() {
        this.score = 0;
        this.maxTile = 0;
        gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                gameTiles[i][j] = new Tile();
            }
        }
        addTile();
        addTile();
    }

    private boolean compressTiles(Tile[] tiles) {
        boolean changes = false;
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].isEmpty()) {
                int j = i;
                while (tiles[j].isEmpty() && j < tiles.length - 1) {
                    j++;
                }
                if (!tiles[j].isEmpty()) changes = true;
                tiles[i] = tiles[j];
                tiles[j] = new Tile();
            }
        }
        return changes;
    }

    private boolean mergeTiles(Tile[] tiles) {
        boolean changes = false;
        for (int i = 0; i < tiles.length - 1; i++) {
            if ((!tiles[i].isEmpty()) && (tiles[i].value == tiles[i + 1].value)) {
                tiles[i].value += tiles[i].value;
                tiles[i + 1].value = 0;
                score += tiles[i].value;
                changes = true;
                if (tiles[i].value > maxTile) maxTile = tiles[i].value;
                compressTiles(tiles);

            }
        }
        return changes;
    }

    public void left() {
        if(isSaveNeeded){
            saveState(gameTiles);
        }
        boolean isChange = false;
        for (int i = 0; i < gameTiles.length; i++) {
            if (compressTiles(gameTiles[i]) | mergeTiles(gameTiles[i])) {
                isChange = true;
            }
        }
        if (isChange && getEmptyTiles().size() != 0) addTile();
        isSaveNeeded = true;
    }

    public void right() {
        saveState(gameTiles);
        gameTiles = rotateCW(gameTiles);
        gameTiles = rotateCW(gameTiles);
        left();
        gameTiles = rotateCW(gameTiles);
        gameTiles = rotateCW(gameTiles);
    }

    public void up() {
        saveState(gameTiles);
        gameTiles = rotateCW(gameTiles);
        gameTiles = rotateCW(gameTiles);
        gameTiles = rotateCW(gameTiles);
        left();
        gameTiles = rotateCW(gameTiles);
    }

    public void down() {
        saveState(gameTiles);
        gameTiles = rotateCW(gameTiles);
        left();
        gameTiles = rotateCW(gameTiles);
        gameTiles = rotateCW(gameTiles);
        gameTiles = rotateCW(gameTiles);
    }

    static Tile[][] rotateCW(Tile[][] mat) {
        final int M = mat.length;
        final int N = mat[0].length;
        Tile[][] ret = new Tile[N][M];
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                ret[c][M - 1 - r] = mat[r][c];
            }
        }
        return ret;
    }

    public void randomMove(){
        int n = ((int) (Math.random() * 100)) % 4;
        if(n == 0){
            left();
        }else if(n == 1){
            right();
        }else if(n == 2){
            up();
        }else {
            down();
        }
    }

    public boolean hasBoardChanged(){
        int allValueDefault = 0;
        int allValuePrevious = 0;
        for(int i = 0; i < gameTiles.length; i++){
            for(int j = 0; j < gameTiles.length; j++){
                allValueDefault += gameTiles[i][j].value;
            }
        }
        Tile[][] previous = (Tile[][]) previousStates.peek();
        for(int i = 0; i < previous.length; i++){
            for(int j = 0; j < previous.length; j++){
                allValuePrevious += previous[i][j].value;
            }
        }

        if(allValueDefault != allValuePrevious){
            return true;
        }else {
            return false;
        }
    }

    public MoveEfficiency getMoveEfficiency(Move move){
        move.move();
        if (!hasBoardChanged()) {
            rollback();
            return new MoveEfficiency(-1, 0, move);
        }
        int emptyTilesCount = getEmptyTiles().size();

        MoveEfficiency moveEfficiency = new MoveEfficiency(emptyTilesCount, score, move);
        rollback();

        return moveEfficiency;
    }

    public void autoMove(){
        PriorityQueue<MoveEfficiency> pq = new PriorityQueue<>(4, Collections.reverseOrder());
        pq.offer(getMoveEfficiency(this::up));
        pq.offer(getMoveEfficiency(this::left));
        pq.offer(getMoveEfficiency(this::down));
        pq.offer(getMoveEfficiency(this::left));
        pq.peek().getMove().move();
    }

}
