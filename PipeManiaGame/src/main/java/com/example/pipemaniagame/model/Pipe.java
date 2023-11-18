package com.example.pipemaniagame.model;

public class Pipe{

    private PipeType pipeType;

    public Pipe(PipeType pipeType){

        this.pipeType = pipeType;
    }

    public PipeType getPipeType() {
        return pipeType;
    }

    public void setPipeType(PipeType pipeType) {
        this.pipeType = pipeType;
    }

    @Override
    public String toString() {
        String msg = "";
        if(pipeType.equals(PipeType.CIRCULAR)){
            msg = "°";
        }
        else if(pipeType.equals(PipeType.DRAIN)){
            msg = "D";
        }
        else if(pipeType.equals(PipeType.HORIZONTAL)){
            msg = "=";
        }
        else if(pipeType.equals(PipeType.VERTICAL)){
            msg = "||";
        }
        else if(pipeType.equals(PipeType.WATERSOURCE)){
            msg = "F";
        }

        return msg;
    }
}