package com.gsoe.nuba;
import org.apache.http.conn.util.*;

public class Spielfeld
{
	Stein[][] belegung=new Stein[5][6];
	MainActivity GUI;
	int aFeldNr;
	public Spielfeld(MainActivity pGUI,int pFeldNr)
	{
		GUI=pGUI;
		aFeldNr=pFeldNr;
		for (int j=0;j<6;j++)
		{
			for (int i=0;i<5;i++)
			{
				belegung[i][j]=null;
			}
		}
	}
	public void setBelegung(Stein pStein,int pX,int pY)
	{
		belegung[pX][pY]=pStein;
		GUI.zeichneStein(pStein,aFeldNr,pX,pY);
	}
	public Stein getBelegung(int pX,int pY)
	{
		return belegung[pX][pY];
	}
}
