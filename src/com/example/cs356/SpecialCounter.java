package com.example.cs356;
import java.util.Scanner;

public class SpecialCounter extends ScoreCounter{
	private int counter_number;
//	private String name;
	private ScoreCounter[] spCounter;
	private int spScore;
<<<<<<< HEAD
	
=======
	/*
>>>>>>> Empire_Rafik
	public SpecialCounter(){
		counter_number = 1;
		spCounter = new ScoreCounter[counter_number];
		spCounter[0].setName("C1");
	}
	public SpecialCounter(int count){
		this.counter_number = count;
		spCounter = new ScoreCounter[count];
		Scanner sc = new Scanner(System.in);
		String nm = sc.next();
		for(int i = 0; i < counter_number; i++){
			setName(i,nm);//need to be changed to gui interface
		}
	}
	public void setInitial(int ini){
		for(int i = 0; i < counter_number; i++)
			spCounter[i].setInitial(ini);
	}
	public void increment(int i){
		spCounter[i].increment();		
	}
	public void decrement(int i){
		spCounter[i].decrement();	
	}
	public void reset(int i){
		spCounter[i].reset();
	}
<<<<<<< HEAD
	
=======
>>>>>>> Empire_Rafik
	public int getSpScore(int i){
		return spCounter[i].getScore();
	}
	public void setName(int i, String counterName){
		spCounter[i].setName(counterName);
	}
	public String getName(int i){
		return spCounter[i].getName();
	}
<<<<<<< HEAD
=======
	*/
>>>>>>> Empire_Rafik
}
