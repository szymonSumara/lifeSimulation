package agh.cs.lab;


import java.util.Objects;

public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x,int y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return "( " + x + " , " + y +" )";
    }

    public boolean precedes( Vector2d other ){
        if(this.x <= other.x && this.y <= other.y)
            return true;
        return false;
    }

    public boolean follows( Vector2d other ){
        if(this.x >= other.x && this.y >= other.y)
            return true;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    public Vector2d upperRight( Vector2d other){

        int tmpX = 0;
        int tmpY = 0;

        if(this.x > other.x)
            tmpX = this.x;
        else
            tmpX = other.x;

        if(this.y > other.y)
            tmpY = this.y;
        else
            tmpY = other.y;

        return new Vector2d(tmpX,tmpY);
    }

    public Vector2d lowerLeft( Vector2d other){

        int tmpX = 0;
        int tmpY = 0;

        if(this.x < other.x)
            tmpX = this.x;
        else
            tmpX = other.x;

        if(this.y < other.y)
            tmpY = this.y;
        else
            tmpY = other.y;

        return new Vector2d(tmpX,tmpY);
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(this.x + other.x,this.y+other.y);
    }

    public Vector2d subtract(Vector2d other){
        return new Vector2d(this.x - other.x,this.y-other.y);
    }

    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        if (this.x == that.x && this.y == that.y)
            return true;
        else
            return false;
    }

    public Vector2d opposite(){
        return new Vector2d(-1*this.x,-1*this.y);
    }
    public Vector2d convertToBounds(Vector2d southWestCorner,Vector2d northEastCorner){
        int newXValue = this.x;
        int newYValue = this.y;

        if(newXValue > southWestCorner.x ){
            newXValue = (northEastCorner.x);
        }

        if(newXValue < northEastCorner.x){
            newXValue = (southWestCorner.x);
        }

        if(newYValue > southWestCorner.y){
            newYValue = (northEastCorner.y);
        }

        if(newYValue < northEastCorner.y){
            newYValue = (southWestCorner.y);
        }
        return new Vector2d(newXValue,newYValue);
    }
}
