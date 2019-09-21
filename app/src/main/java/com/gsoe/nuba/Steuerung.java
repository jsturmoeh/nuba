package com.gsoe.nuba;
import java.security.spec.*;
import android.graphics.*;


enum zustand
{
	Spieler0Ausgewaehlt,
	Spieler1Ausgewaehlt,
	Spieler0Gesetzt,
	Spieler1Gesetzt,
	Spieler0Markiert,
	Spieler1Markiert,
	Spieler0Gezogen,
	Spieler1Gezogen,
	Ende
	}
public class Steuerung
{
	MainActivity GUI;
	Spielfeld bank0;
	Spielfeld bank1;
	Spielfeld dasSpielfeld;
	zustand z;
	int zugzahl;
	int x=0,y=0;
	int[] SpPkte={0,0};
	public Steuerung(MainActivity pGUI)
	{
		GUI = pGUI;
		bank0 = new Spielfeld(GUI, 1);
		bank1 = new Spielfeld(GUI, 2);
		dasSpielfeld = new Spielfeld(GUI, 0);

		z = zustand.Spieler1Gesetzt;
		zugzahl = 1;
		for (int j=0;j < 2;j++)
		{
			for (int i=0;i < 5;i++)
			{
				bank0.setBelegung(new Stein(zugzahl, Stein.blau), i, j);
				bank1.setBelegung(new Stein(zugzahl, Stein.gelb), i, j);
				zugzahl++;
			}
		}
		for (int j=0;j < 6;j++)
		{
			for (int i=0;i < 5;i++)
			{
				dasSpielfeld.setBelegung(null, i, j);


			}
		}
		zugzahl = 0;
	}
	public void bank0Click(int pSp, int pZ)
	{   Stein stein;
		switch (z)
		{
			case Spieler0Ausgewaehlt: break;
			case Spieler1Ausgewaehlt: break;
			case Spieler0Gesetzt:

				break;

			case Spieler1Gesetzt: 
				if (zugzahl < 20)
				{
					stein = bank0.getBelegung(pSp, pZ);
					if (stein != null)
					{
						stein.highlight();
						bank0.setBelegung(stein, pSp, pZ);
						GUI.zeigeAnweisung("Spieler0 Ziel wählen");
						x = pSp;
						y = pZ;
						z = zustand.Spieler0Ausgewaehlt;
					}
					else 
					{
						GUI.zeigeAnweisung("Erneut wählen");
					}
				}
				break;
			case Spieler0Markiert: 
				break;
			case Spieler1Markiert: break;
			case Spieler0Gezogen: break;
			case Spieler1Gezogen: break;
		}
	}
	public void bank1Click(int pSp, int pZ)
	{
		Stein stein;
		switch (z)
		{
			case Spieler0Ausgewaehlt: break;
			case Spieler1Ausgewaehlt: break;
			case Spieler0Gesetzt:
				if (zugzahl < 20)
				{
					stein = bank1.getBelegung(pSp, pZ);
					if (stein != null)
					{
						stein.highlight();
						bank1.setBelegung(stein, pSp, pZ);
						GUI.zeigeAnweisung("Spieler1 Ziel wählen");
						x = pSp;
						y = pZ;
						z = zustand.Spieler1Ausgewaehlt;
					}
					else
					{
						GUI.zeigeAnweisung("Erneut wählen");
					}
				}
				break;
			case Spieler1Gesetzt: break;
			case Spieler0Markiert: break;
			case Spieler1Markiert: break;
			case Spieler0Gezogen: break;
			case Spieler1Gezogen: break;
		}
	}
	public void feldClick(int pSp, int pZ)
	{
		Stein stein;
		switch (z)
		{
			case Spieler0Ausgewaehlt: 
				if (pZ < 2)
				{
					stein = dasSpielfeld.getBelegung(pSp, pZ);
					if (stein == null)
					{
						stein = bank0.getBelegung(x, y);
						bank0.setBelegung(null, x, y);
						stein.lowlight();
						dasSpielfeld.setBelegung(stein, pSp, pZ);
						GUI.zeigeAnweisung("Spieler1 wählen");
						z = zustand.Spieler0Gesetzt;
						zugzahl++;
						if (zugzahl == 20)
						{
							zugzahl = 0;
							z = zustand.Spieler0Gezogen;
							GUI.zeigeAnweisung("Spieler1 Stein für Zug markieren");

						}
					}
				}
				else
				{
					GUI.zeigeAnweisung("falsches Feld, erneut setzen");
				}
				break;
			case Spieler1Ausgewaehlt:
				if (pZ > 3)
				{
					stein = dasSpielfeld.getBelegung(pSp, pZ);
					if (stein == null)
					{
						stein = bank1.getBelegung(x, y);
						bank1.setBelegung(null, x, y);
						stein.lowlight();
						dasSpielfeld.setBelegung(stein, pSp, pZ);
						GUI.zeigeAnweisung("Spieler0 wählen");
						z = zustand.Spieler1Gesetzt;
						zugzahl++;
						if (zugzahl == 20)
						{
							zugzahl = 0;
							z = zustand.Spieler1Gezogen;
							GUI.zeigeAnweisung("Spieler0 Stein für Zug markieren");

						}
					}
				}
				else
				{
					GUI.zeigeAnweisung("falsches Feld, erneut setzen");
				}
				break;
			case Spieler0Gesetzt: break;
			case Spieler1Gesetzt: break;
			case Spieler0Markiert:
				if (pruefeZugMoeglich2(pSp, pZ, 0))
				{
					stein = dasSpielfeld.getBelegung(x, y);
					stein.lowlight();
					dasSpielfeld.setBelegung(stein, pSp, pZ);
					dasSpielfeld.setBelegung(null, x, y);
					z = zustand.Spieler0Gezogen;
					GUI.zeigeAnweisung("Spieler1 Stein wählen");
					if (pZ == 5)
					{
						dasSpielfeld.setBelegung(null, pSp, pZ);
						if (SpPkte[0] < stein.gibNr())
						{
							GUI.zeigePunkte(0, stein.gibNr());
							SpPkte[0] = stein.gibNr();
						}
					}

//					if (pruefeGewonnen(0))
//					{
//						GUI.zeigeAnweisung("Spieler0 hat gewonnen");
//						z = zustand.Ende;
//					}
					if (pruefeGewonnen(0))
					{
						GUI.zeigeAnweisung("Spieler0 hat gewonnen");
						z = zustand.Ende;
					}
					if (pruefeVerloren(0))
					{
						GUI.zeigeAnweisung("Spieler0 hat verloren");
						z = zustand.Ende;
					}
					if (pruefeVerloren(1))
					{
						GUI.zeigeAnweisung("Spieler1 hat verloren");
						z = zustand.Ende;
					}


				}
				else
				{
					GUI.zeigeAnweisung("Spieler0 Ziel ungültig, erneut waehlen");
				}
				break;
			case Spieler1Markiert: 
				if (pruefeZugMoeglich2(pSp, pZ, 1))
				{
					stein = dasSpielfeld.getBelegung(x, y);
					stein.lowlight();
					dasSpielfeld.setBelegung(stein, pSp, pZ);
					dasSpielfeld.setBelegung(null, x, y);
					z = zustand.Spieler1Gezogen;
					GUI.zeigeAnweisung("Spieler0 Stein wählen");
					if (pZ == 0)
					{
						dasSpielfeld.setBelegung(null, pSp, pZ);
						if (SpPkte[1] < stein.gibNr())
						{
							GUI.zeigePunkte(1, stein.gibNr());
							SpPkte[1] = stein.gibNr();
						}
					}

					if (pruefeGewonnen(1))
					{
						GUI.zeigeAnweisung("Spieler1 hat gewonnen");
						z = zustand.Ende;
					}

					if (pruefeVerloren(1))
					{
						GUI.zeigeAnweisung("Spieler1 hat verloren");
						z = zustand.Ende;
					}

					if (pruefeVerloren(0))
					{
						GUI.zeigeAnweisung("Spieler0 hat verloren");
						z = zustand.Ende;
					}

				}
				else
				{
					GUI.zeigeAnweisung("Spieler1 Ziel ungültig, erneut waehlen");
				}
				break;
			case Spieler0Gezogen:
				if (pruefeZugMoeglich1(pSp, pZ, 1))
				{ 
					stein = dasSpielfeld.getBelegung(pSp, pZ);
					stein.highlight();
					dasSpielfeld.setBelegung(stein, pSp, pZ);
					z = zustand.Spieler1Markiert;
					x = pSp;
					y = pZ;
					GUI.zeigeAnweisung("Spielerl Ziel wählen");

				}
				else
				{
					GUI.zeigeAnweisung("Erneut wählen");
				}
				break;
			case Spieler1Gezogen:
				if (pruefeZugMoeglich1(pSp, pZ, 0))
				{ 
					stein = dasSpielfeld.getBelegung(pSp, pZ);
					stein.highlight();
					dasSpielfeld.setBelegung(stein, pSp, pZ);
					z = zustand.Spieler0Markiert;
					x = pSp;
					y = pZ;
					GUI.zeigeAnweisung("Spieler0 Ziel wählen");

				}
				else
				{
					GUI.zeigeAnweisung("Erneut wählen");
				}
				break;
		}
	}

	private boolean pruefeZugMoeglich2(int pX, int pY, int pSpieler)
	{
		Stein stein=dasSpielfeld.getBelegung(x, y);
		Stein stein2=dasSpielfeld.getBelegung(pX, pY);
		//Zielkoordonaten pruefen
		if (pSpieler == 0)
		{
			if (pY != y + 1) return false;
			if ((pX < x - 1) || (pX > x + 1)) return false;
			if (stein2 == null)return true;
			if (stein2.gibFarbe() / 2 == stein.gibFarbe() / 2)return false;
			if (stein2.gibNr() >= stein.gibNr()) return true;
			if (stein.gibNr() == 10) return true;

		}
		else
		{
			if (pY != y - 1) return false;
			if ((pX < x - 1) || (pX > x + 1)) return false;
			if (stein2 == null)return true;
			if (stein2.gibFarbe() / 2 == stein.gibFarbe() / 2)return false;
			if (stein2.gibNr() >= stein.gibNr()) return true;
			if (stein.gibNr() == 10) return true;
		}
		return false;
	}
	/* prueft, op die gewaehlte Figur beweglich ist
	 */
	private boolean pruefeZugMoeglich1(int pX, int pY, int pSpieler)
	{
		Stein stein=dasSpielfeld.getBelegung(pX, pY);
		Stein stein2;
		if (stein == null) return false;
		if (stein.gibFarbe() / 2 != pSpieler) return false;
		if (pSpieler == 0)
		{
			if ((pX - 1) >= 0)
			{
				stein2 = dasSpielfeld.getBelegung(pX - 1, pY + 1);
				if (stein2 == null) return true;
				if (stein2.gibFarbe() / 2 != pSpieler)
				{
					if (stein.gibNr() <= stein2.gibNr()) return true;
					if (stein.gibNr() == 10) return true;
				}
			}
			if ((pX + 1) < 5)
			{
				stein2 = dasSpielfeld.getBelegung(pX + 1, pY + 1);
				if (stein2 == null) return true;
				if (stein2.gibFarbe() / 2 != pSpieler)
				{
					if (stein.gibNr() <= stein2.gibNr()) return true;
					if (stein.gibNr() == 10) return true;
				}
			}		
			stein2 = dasSpielfeld.getBelegung(pX, pY + 1);
			if (stein2 == null) return true;
			if (stein2.gibFarbe() / 2 != pSpieler)
			{
				if (stein.gibNr() <= stein2.gibNr()) return true;
				if (stein.gibNr() == 10) return true;
			}
		}
		else
		{
			if ((pX - 1) >= 0)
			{
				stein2 = dasSpielfeld.getBelegung(pX - 1, pY - 1);
				if (stein2 == null) return true;
				if (stein2.gibFarbe() / 2 != pSpieler)
				{
					if (stein.gibNr() <= stein2.gibNr()) return true;
					if (stein.gibNr() == 10) return true;
				}
			}
			if ((pX + 1) < 5)
			{
				stein2 = dasSpielfeld.getBelegung(pX + 1, pY - 1);
				if (stein2 == null) return true;
				if (stein2.gibFarbe() / 2 != pSpieler)
				{
					if (stein.gibNr() <= stein2.gibNr()) return true;
					if (stein.gibNr() == 10) return true;
				}
			}		
			stein2 = dasSpielfeld.getBelegung(pX, pY - 1);
			if (stein2 == null) return true;
			if (stein2.gibFarbe() / 2 != pSpieler)
			{
				if (stein.gibNr() <= stein2.gibNr()) return true;
				if (stein.gibNr() == 10) return true;
			}
		}

		return false;
	}
	private boolean pruefeVerloren(int pAktSpieler)
	{
		int anz=0;
		int maxGegner;
		if (pAktSpieler == 0) maxGegner = SpPkte[1];
		else maxGegner = SpPkte[0];
		Stein stein;
		for (int j=0;j < 6;j++)
		{
			for (int i=0;i < 5;i++)
			{
				stein = dasSpielfeld.getBelegung(i, j);
				if (stein != null)
				{ 
					if (stein.gibFarbe() / 2 == pAktSpieler)
					{
						anz++;
					}
					else
					{
						if (stein.gibNr() > maxGegner)
						{
							maxGegner = stein.gibNr();
						}
					}
				}
			}
		}
		if (anz == 0 && maxGegner > SpPkte[pAktSpieler]) return true;
		return false;
	}
	private boolean pruefeGewonnen(int pAktSpieler)
	{
		Stein stein;
		for (int j=0;j < 6;j++)
		{
			for (int i=0;i < 5;i++)
			{
				stein = dasSpielfeld.getBelegung(i, j);
				if (stein != null)
				{
					if (stein.gibFarbe() / 2 != pAktSpieler)
					{
						if (stein.gibNr() > SpPkte[pAktSpieler])return false;
					}
				}
			}
			if (pAktSpieler == 0)
			{
				if (SpPkte[1] >= SpPkte[0])return false;
			}
			if (pAktSpieler == 1)
			{
				if (SpPkte[0] >= SpPkte[1])return false;
			}
		}
		return true;
	}
}
