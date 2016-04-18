package com.example.vaishnavigalgali.minesweeper;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.Button;

public class Tile extends Button
{
    private boolean Flagged;
    private boolean AbleToClick;
    private int numberOfMinesNearBy;
    private boolean isCovered;
    private boolean isMined;
    public Tile(Context context)
    {
        super(context);
    }
    public Tile(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    public Tile(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }
    public void setDefaults()
    {
        isCovered = true;
        isMined = false;
        Flagged = false;
        AbleToClick = true;
        numberOfMinesNearBy = 0;
        this.setBackgroundResource(R.drawable.greytile);

    }
    public void setNumberOfSurroundingMines(int number)
    {
        this.setBackgroundResource(R.drawable.bluetile);

        updateBlock(number);
    }
    public void setMineIcon(boolean enabled)
    {
        this.setBackgroundResource(R.drawable.mine);
        if (!enabled)
        {
            this.setBackgroundResource(R.drawable.bluetile);
            this.setTextColor(Color.RED);
            this.setBackgroundResource(R.drawable.mine);
        }
        else
        {
            this.setTextColor(Color.BLACK);
        }
    }
    public boolean isClickable()
    {
        return AbleToClick;
    }
    public void setClickable(boolean clickable)
    {
        AbleToClick = clickable;
    }

    public void DisableBlock(boolean enabled)
    {
        if (!enabled)
        {
            this.setBackgroundResource(R.drawable.bluetile);
        }
        else
        {
            this.setBackgroundResource(R.drawable.greytile);
        }
    }
    public void clearAllIcons()
    {
        this.setText("");
    }
    public void OpenBlock()
    {
        if (!isCovered)
            return;
        DisableBlock(false);
        isCovered = false;
        if (hasMine())
        {
            setMineIcon(false);
        }else
        {
            setNumberOfSurroundingMines(numberOfMinesNearBy);
        }
    }
    public void Open() {
        if (!hasMine()) {
            setNumberOfSurroundingMines(numberOfMinesNearBy);

        }
    }
    public void updateBlock(int text)
    {
        if (text != 0)
        {
           this.setText(Integer.toString(text));
            if(text==1){
                this.setTextColor(getResources().getColor(R.color.BLUE, getResources().newTheme()));
            }else if(text==2){
                this.setTextColor(getResources().getColor(R.color.case2, getResources().newTheme()));
            }else if(text==3){
                this.setTextColor(getResources().getColor(R.color.RED, getResources().newTheme()));
            }else if(text==4){
                this.setTextColor(getResources().getColor(R.color.case4, getResources().newTheme()));
            }else if(text==5){
                this.setTextColor(getResources().getColor(R.color.case5, getResources().newTheme()));
            }else if(text==6){
                this.setTextColor(getResources().getColor(R.color.case6, getResources().newTheme()));
            }else if(text==7){
                this.setTextColor(getResources().getColor(R.color.case7, getResources().newTheme()));
            }else if(text==8){
                this.setTextColor(getResources().getColor(R.color.case8, getResources().newTheme()));
            }else if(text==9){
                this.setTextColor(getResources().getColor(R.color.case9, getResources().newTheme()));
            }
        }
    }
    public void placeMine()
    {
        isMined = true;
    }
    public void triggerMine()
    {
        setMineIcon(true);
        this.setTextColor(Color.RED);
    }
    public boolean isCovered()
    {
        return isCovered;
    }
    public boolean hasMine()
    {
        return isMined;
    }
    public void setNumberOfMinesInSurrounding(int number)
    {
        numberOfMinesNearBy = number;
    }
    public int getNumberOfNearByMines()
    {
        return numberOfMinesNearBy;
    }
    public boolean isFlagged()
    {
        return Flagged;
    }
    public void setFlagged(boolean flagged)
    {
        Flagged = flagged;
    }

}
