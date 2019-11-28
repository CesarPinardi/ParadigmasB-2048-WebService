    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2048teste;

import com.google.gson.Gson;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author cesar
 */
public class GameBoard {
    private final String USER_AGENT = "Mozilla/5.0";

    public static final int ROWS = 4;
    public static final int COLS = 4;
    
    private final int startingTiles = 2;
    private Tile[][] board;
    private boolean dead;
    private boolean win;
    private BufferedImage gameBoard;
    private BufferedImage finalBoard;
    private int x;
    private int y;
    
    
    private boolean won;
    private boolean hasStarted;
    
    private static int SPACING = 10;
    public static int board_WIDTH = (COLS + 1) * SPACING + COLS * Tile.WIDTH;
    public static int board_HEIGHT = (ROWS + 1) * SPACING + ROWS * Tile.HEIGHT;
    
    
    public GameBoard(int x, int y){
        this.x = x;
        this.y = y;
        board = new Tile[ROWS][COLS];
        gameBoard = new BufferedImage(board_WIDTH, board_HEIGHT, BufferedImage.TYPE_INT_RGB);
        finalBoard = new BufferedImage(board_WIDTH, board_HEIGHT, BufferedImage.TYPE_INT_RGB);
    
        createBoardImage();
        start();
    }
    
    private void createBoardImage(){
        Graphics2D g = (Graphics2D) gameBoard.getGraphics();
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, board_WIDTH, board_HEIGHT);
        g.setColor(Color.lightGray);
        
        for(int row = 0; row < ROWS; row++){
           for(int col = 0; col < COLS; col++){
               int x = SPACING + SPACING * col + Tile.WIDTH * col;
               int y = SPACING + SPACING * row + Tile.HEIGHT * row;
               g.fillRoundRect(x, y, Tile.WIDTH, Tile.HEIGHT, Tile.ARC_WIDTH, Tile.ARC_HEIGHT);
           }
        }    
    }
    
    private void start(){
        for (int i = 0; i < startingTiles; i++) {
            spawnRandom();
        }
    }
    
    
    private void spawnRandom(){
        Random random = new Random();
        boolean notValid = true;
        while (notValid) {            
            int location = random.nextInt(ROWS*COLS); //linhas x col
            int row = location / ROWS;
            int col = location % COLS;
            
            Tile current = board[row][col];
            if(current == null){
                int value = random.nextInt(2);
                if(value == 0 || value == 1){
                    value = 2;
                }
                else{
                    value = 4;
                }
                Tile tile = new Tile(value, getTileX(col), getTileY(row));
                board[row][col] = tile;
                notValid = false;            
            }
            
            
        }
    }
    
    public int getTileX(int col){
        return SPACING + col * Tile.WIDTH + col * SPACING;
    }
    
    public int getTileY(int row){
        return SPACING + row * Tile.HEIGHT + row * SPACING;
    }
    
    
    
    public void render(Graphics2D g){
        Graphics2D g2d = (Graphics2D)finalBoard.getGraphics();
        g2d.drawImage(gameBoard, 0, 0, null);
    
        //desenha
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Tile current = board[row][col];
                if(current == null) continue;
                current.render(g2d);
            }
        }
        
        g.drawImage(finalBoard, x, y, null);
        g2d.dispose();
    }
    
    public void update() throws Exception{
        checkKeys();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Tile current = board[row][col];
                if(current == null) continue;
                current.update();
                resetPosition(current, row, col);
                if(current.getValue()== 2048){
                    won = true;
                }
            }
        }
        
    }
    
    private void resetPosition(Tile current, int row, int col){
        if(current == null) return;
        
        int x = getTileX(col);
        int y = getTileY(row);
        
        int distX = current.getX() - x;
        int distY = current.getY() - y;
        
        if(Math.abs(distX) < Tile.SLIDE_UPDATE){
            current.setX(current.getX() - distX);
        }
        
        
        if(Math.abs(distY) < Tile.SLIDE_UPDATE){
            current.setY(current.getY() - distY);
        }
        
        if(distX < 0){
            current.setX(current.getX() + Tile.SLIDE_UPDATE);
        }
        
        if(distY < 0){
            current.setY(current.getY() + Tile.SLIDE_UPDATE);
        }
        
        if(distX > 0){
            current.setX(current.getX() - Tile.SLIDE_UPDATE);
        }
        
        if(distY > 0){
            current.setY(current.getY() - Tile.SLIDE_UPDATE);
        }
    }

   
    
    private boolean move(int row, int col, int horizontalDr, int verticalDr, Direction dr){
        boolean canMove = false;
        
        Tile current = board[row][col];
        if(current == null) return false;
        boolean move = true;
        int newCol = col;
        int newRow = row;
        
        while(move){
            newCol += horizontalDr;
            newRow += verticalDr;
            if(checkOutOfBounds(dr, newRow, newCol)) break;
            if(board[newRow][newCol] == null){
                board[newRow][newCol] = current;
                board[newRow - verticalDr][newCol - horizontalDr] = null;
                board[newRow][newCol].setSlideTo(new Point(newRow,newCol));
                canMove = true;
            }
            else if(board[newRow][newCol].getValue() == current.getValue() && board[newRow][newCol].CanCombine()){
                board[newRow][newCol].setCanCombine(false);
                board[newRow][newCol].setValue(board[newRow][newCol].getValue()* 2);
                canMove = true;
                board[newRow - verticalDr][newCol - horizontalDr] = null;
                board[newRow][newCol].setSlideTo(new Point(newRow,newCol));
                //board[newRow][newCol].setCombineAnimation = true;
                //add to score
            }
            else{
                move = false;
            }
                
         }
        
        
        return canMove;
    }
    
    public void moveTiles(Direction dr){
        boolean canMove = false;
        int horizDir = 0;
        int vertDir = 0;
        
        if(dr == Direction.LEFT){
            horizDir = -1;
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    if(!canMove){
                        canMove = move(row, col, horizDir, vertDir, dr);
                    }
                    else move(row,col,horizDir,vertDir, dr);
                }
            }
        }
        else if(dr == Direction.RIGHT){
            horizDir = 1;
            for (int row = 0; row < ROWS; row++) {
                for (int col = COLS -1; col >= 0; col--) {
                    if(!canMove){
                        canMove = move(row, col, horizDir, vertDir, dr);
                    }
                    else move(row,col,horizDir,vertDir, dr);
                }
            }
        }
        else if(dr == Direction.UP){
          vertDir = -1;
          for (int row = 0; row < ROWS; row++) {
              for (int col = 0; col < COLS; col++) {
                  if(!canMove){
                      canMove = move(row, col, horizDir, vertDir, dr);
                  }
                  else move(row,col,horizDir,vertDir, dr);
              }
          }
        }
        else if(dr == Direction.DOWN){
         vertDir = 1;
         for (int row = ROWS - 1; row >= 0; row--) {
             for (int col = 0; col < COLS; col++) {
                 if(!canMove){
                     canMove = move(row, col, horizDir, vertDir, dr);
                 }
                 else move(row,col,horizDir,vertDir, dr);
             }
         }
        }
        else{
            System.out.println(dr + "is not valid direction");
        }
        
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Tile current = board[row][col];
                if(current == null) continue;
                current.setCanCombine(true);
            }
        }
        
        if(canMove){
            spawnRandom();
            checkDead(); 
        }
        
    }
    
    private void checkDead(){
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if(board[row][col] == null)return;
                if(checkSurroundingTiles(row,col,board[row][col])){
                    return;
                }
            }
        }
        dead = true;
        //setHighScore
    }
    private boolean checkSurroundingTiles(int row, int col, Tile current){
        if(row>0){
            Tile check = board[row - 1][col];
            if(check == null) return true;
            if(current.getValue() == check.getValue())return true;
        }
        if(row < ROWS -1){
            Tile check = board[row + 1][col];
            if(check == null) return true;
            if(current.getValue() == check.getValue())return true;
        }
        if(col > 0){
            Tile check = board[row][col - 1];
            if(check == null) return true;
            if(current.getValue() == check.getValue())return true;
        }
        if(col < COLS -1){
            Tile check = board[row][col + 1];
            if(check == null) return true;
            if(current.getValue() == check.getValue())return true;
        }
        return false;
    }
    
    private void checkKeys() throws Exception{
          
       
        HttpExemplo http = new HttpExemplo();
        String chamadaWSLeft;
        String chamadaWSRight;
        String chamadaWSUp;
        String chamadaWSDown;


        Scanner ler = new Scanner(System.in);
        
        int dir;
        do {            
            System.out.print("Direction (1-Left, 2-Right, 3-Up, 4-Down, 0- Sair): ");

            dir = ler.nextInt();

            if(dir == 1){
            
                chamadaWSLeft = "http://localhost:8080/WebService2048/webresources/generic/Usuario/2048/left";

                String json = http.sendGet(chamadaWSLeft);
                System.out.println("Json: " + json);
                moveTiles(Direction.LEFT);
                if(!hasStarted) hasStarted = true;
            }
            if(dir == 2){
            
                chamadaWSRight = "http://localhost:8080/WebService2048/webresources/generic/Usuario/2048/right";

                String json = http.sendGet(chamadaWSRight);
                System.out.println("Json: " + json);
                moveTiles(Direction.RIGHT);
                if(!hasStarted) hasStarted = true;
            }
            if(dir == 3){
            
                chamadaWSUp = "http://localhost:8080/WebService2048/webresources/generic/Usuario/2048/up";

                String json = http.sendGet(chamadaWSUp);
                System.out.println("Json: " + json);
                moveTiles(Direction.UP);
                if(!hasStarted) hasStarted = true;
            }
            if(dir == 4){
            
                chamadaWSDown = "http://localhost:8080/WebService2048/webresources/generic/Usuario/2048/down";

                String json = http.sendGet(chamadaWSDown);
                System.out.println("Json: " + json);
                moveTiles(Direction.DOWN);
                if(!hasStarted) hasStarted = true;
            }
           
        } while (dir != 0);
        
    }

    private boolean checkOutOfBounds(Direction dr, int row, int col) {
        if(dr == Direction.LEFT){
            return col <0;
        }
        else if(dr == Direction.RIGHT){
            return col > COLS -1;
        }
        else if(dr == Direction.UP){
            return row < 0;
        }
        else if(dr == Direction.DOWN){
            return row > ROWS - 1;
        }
        return false;
    }

    private String sendGet(String url) throws Exception {
               
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		// optional default is GET
		con.setRequestMethod("GET");
 
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);  
		}
                
		in.close();
 
		return response.toString();
                
        }
    
}
