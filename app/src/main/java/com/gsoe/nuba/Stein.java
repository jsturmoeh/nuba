package com.gsoe.nuba;

public class Stein
{
	private int aNr;
	public static int blau=0;
	public static int hellblau=1;
	public static int gelb=2;
	public static int hellgelb=3;
	private int aFarbe=0;
	public Stein(int pNr,int pFarbe)
	{
		aNr=pNr;
		aFarbe=pFarbe;
	}
	public void highlight()
	{
		switch (aFarbe)
		{
			case 0:aFarbe=hellblau; break;
			case 2:aFarbe=hellgelb;break;
		}
	}
	public void lowlight()
	{
		switch (aFarbe)
		{
			case 1:aFarbe=blau; break;
			case 3:aFarbe=gelb;break;
		}
	}
	public int gibFarbe()
	{
		return aFarbe;
	}
	public int gibNr()
	{
		return aNr;
	}
}
