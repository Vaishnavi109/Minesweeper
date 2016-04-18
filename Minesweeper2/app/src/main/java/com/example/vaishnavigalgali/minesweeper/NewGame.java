package com.example.vaishnavigalgali.minesweeper; /**
 * Created by vaishnavigalgali on 3/1/16.
 */
import android.app.Activity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;

public class NewGame extends Activity {
    private TableLayout mineField;
    private int totalNumberOfMines = 10;
    private int totalRows=8;
    private int totalCols=12;
    private int tileWH=24;
    private int tilePadding=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newgame);

    }
    public void showGameBoard()
    {

        //for every row
        Tile[][] tiles = new Tile[totalRows][totalCols];
        for(int row=0;row<totalRows;row++)
        {

            //create a new table row
            TableRow tableRow = new TableRow(this);
            //set the height and width of the row
            tableRow.setLayoutParams(new LayoutParams((tileWH * tilePadding) * totalCols, tileWH * tilePadding));

            //for every column
            for(int col=0;col<totalCols;col++)
            {
                //set the width and height of the tile
                tiles[row][col].setLayoutParams(new LayoutParams(tileWH * tilePadding,  tileWH * tilePadding));
                //add some padding to the tile
                tiles[row][col].setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
                //add the tile to the table row
                tableRow.addView(tiles[row][col]);
            }
            //add the row to the minefield layout
            mineField.addView(tableRow,new TableLayout.LayoutParams((tileWH * tilePadding) * totalCols, tileWH * tilePadding));
        }
    }
}
