package com.example.vaishnavigalgali.minesweeper;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TableRow.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;

public class MinesweeperActivity extends Activity
{
    private Tile tiles[][];
    private int tDimension = 50;
    private int tPadding = 10;
    private int numberOfRows = 12;
    private int numberOfColumns = 8;
    private int totalNumberOfMines = 30;
    private TableLayout mineField;
    private boolean isMinesSet;
    private boolean GameOver;
    private int mines;
    private ImageButton Smiley;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_minesweeper);
        Smiley = (ImageButton) findViewById(R.id.Smiley);
        Smiley.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                endRunningGame();
                startNewGame();
            }
        });
        mineField = (TableLayout)findViewById(R.id.MineField);

        Alert("Click Smiley to load Grid.!",true);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            tDimension=50;
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
           tDimension=90;
        }
    }

    private void startNewGame()
    {
        constructMineGrid();
        DisplayMineGrid();
        mines= totalNumberOfMines;
        GameOver = false;
    }
    private void constructMineGrid()
    {
        tiles= new Tile[numberOfRows + 2][numberOfColumns + 2];
        for (int row = 0; row < numberOfRows + 2; row++) {
            for (int column = 0; column < numberOfColumns + 2; column++) {
                tiles[row][column] = new Tile(this);
                tiles[row][column].setDefaults();
                final int currentRow = row;
                final int currentColumn = column;
                tiles[row][column].setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!isMinesSet) {
                            isMinesSet = true;
                            setMines(currentRow, currentColumn);
                        }
                        if (!tiles[currentRow][currentColumn].isFlagged()) {
                            UncoverBlock(currentRow, currentColumn);
                            if (tiles[currentRow][currentColumn].hasMine())
                            {
                               EndGame(currentRow,currentColumn);
                            }
                            if (checkStatusOfGame())
                            {
                                win();
                            }
                        }
                    }
                });
            }
        }
    }
    private void DisplayMineGrid()
    {
        for (int row = 1; row < numberOfRows + 1; row++)
        {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new LayoutParams((tDimension + 2 * tPadding) * numberOfColumns, tDimension + 2 * tPadding));
            for (int column = 1; column < numberOfColumns + 1; column++)
            {
                tiles[row][column].setLayoutParams(new LayoutParams(tDimension + 2 * tPadding, tDimension + 2 * tPadding));
                tiles[row][column].setPadding(tPadding, tPadding, tPadding, tPadding);
                tableRow.addView(tiles[row][column]);
            }
            mineField.addView(tableRow,new TableLayout.LayoutParams((tDimension + 2 * tPadding) * numberOfColumns, tDimension + 2 * tPadding));
        }
    }
    private boolean checkStatusOfGame()
    {
        for (int row = 1; row < numberOfRows + 1; row++)
        {
            for (int column = 1; column < numberOfColumns + 1; column++)
            {
                if (!tiles[row][column].hasMine() && tiles[row][column].isCovered())
                {
                    return false;
                }
            }
        }
        return true;
    }

    private void win()
    {
        GameOver = true;
        mines = 0;
        Smiley.setBackgroundResource(R.drawable.sm);
        for (int row = 1; row < numberOfRows+ 1; row++)
        {
            for (int column = 1; column < numberOfColumns + 1; column++)
            {
                tiles[row][column].setClickable(false);
                if (tiles[row][column].hasMine())
                {
                    tiles[row][column].DisableBlock(false);
                }
            }
        }

        Alert("You won ", true);
    }

    private void EndGame(int currentRow, int currentColumn)
    {

        Smiley.setBackgroundResource(R.drawable.sm);
        GameOver = true;
        for (int row = 1; row < numberOfRows + 1; row++)
        {
            for (int column = 1; column < numberOfColumns+ 1; column++) {

                tiles[row][column].DisableBlock(false);

                if (tiles[row][column].hasMine())
                {
                    // set mine icon
                    tiles[row][column].setMineIcon(false);
                }
            }
        }
        tiles[currentRow][currentColumn].triggerMine();
        showALL();

        Alert("Game Over. ",false);
    }
    private void endRunningGame()
    {
        Smiley.setBackgroundResource(R.drawable.sm);
        mineField.removeAllViews();
        isMinesSet = false;
        GameOver = false;
        mines = 0;
    }
    private void showALL()
    {
        for (int row = 1; row < numberOfRows + 1; row++)
        {
            for (int column = 1; column < numberOfColumns + 1; column++) {
                tiles[row][column].Open();
            }
        }
    }
    private void setMines(int currentRow, int currentColumn)
    {
        int nearMine;
        Random rand = new Random();
        int mineRow, mineColumn;
        for (int row = 0; row < totalNumberOfMines; row++)
        {
            mineRow = rand.nextInt(numberOfColumns);
            mineColumn = rand.nextInt(numberOfRows);
            if ((mineRow + 1 != currentColumn) || (mineColumn + 1 != currentRow))
            {
                if (tiles[mineColumn + 1][mineRow + 1].hasMine()) {
                    row--;
                }
                tiles[mineColumn + 1][mineRow + 1].placeMine();
            }            else
            {
                row--;
            }
        }

        for (int row = 0; row < numberOfRows + 2; row++)
        {
            for (int column = 0; column < numberOfColumns+ 2; column++)
            {
                nearMine = 0;
                if ((row != 0) && (row != (numberOfRows+ 1)) && (column != 0) && (column != (numberOfColumns + 1)))
                {
                    for (int prevRow = -1; prevRow < 2; prevRow++)
                    {
                        for (int prevCol = -1; prevCol < 2; prevCol++)
                        {
                            if (tiles[row + prevRow][column + prevCol].hasMine())
                            {
                                nearMine++;
                            }
                        }
                    }

                    tiles[row][column].setNumberOfMinesInSurrounding(nearMine);
                }
                else
                {
                    tiles[row][column].setNumberOfMinesInSurrounding(9);
                    tiles[row][column].OpenBlock();
                }
            }
        }
    }
    public void Alert(String msg,boolean startOfGame){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            if(startOfGame) {
                Toast alert = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
                alert.show();
            }
            else {
                alertDialogBuilder.setMessage(msg + "Do you want to play again?");
                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        endRunningGame();
                        startNewGame();
                    }
                });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
    }

    private void UncoverBlock(int Clickedrow, int Clickedcol)
    {
        if (tiles[Clickedrow][Clickedcol].hasMine())
        {
            return;
        }
        tiles[Clickedrow][Clickedcol].OpenBlock();
        if (tiles[Clickedrow][Clickedcol].getNumberOfNearByMines() != 0 )
        {
            return;
        }
        for (int row = 0; row < 3; row++)
        {
            for (int column = 0; column < 3; column++)
            {
                if (tiles[Clickedrow + row - 1][Clickedcol + column - 1].isCovered()&& (Clickedrow + row - 1 > 0) && (Clickedcol + column - 1 > 0)&& (Clickedrow + row - 1 < numberOfRows + 1) && (Clickedcol + column - 1 < numberOfColumns + 1))
                {
                    UncoverBlock(Clickedrow + row - 1, Clickedcol + column - 1 );
                }
            }
        }
        return;
    }

}