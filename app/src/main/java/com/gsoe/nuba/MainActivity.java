package com.gsoe.nuba;

import android.app.*;
import android.os.*;
import android.view.View.*;
import android.widget.*;
import android.graphics.*;
import android.opengl.*;
import java.util.concurrent.*;
import android.graphics.drawable.*;
import android.view.*;
import android.bluetooth.*;
import android.content.res.*;

class ImageView1 extends ImageView
{
	public int posx,posy;
	public ImageView1(Activity a)
	{
	  super(a);
	}
}
public class MainActivity extends Activity 
{
TableLayout tl;
TableRow tr;
ImageView iv;
TextView tv,tv2,tv_Anweisung;
LinearLayout l1;
Steuerung dieSteuerung;

ImageView1[][] iv1=new ImageView1[5][6];
	ImageView1[][] iv2=new ImageView1[5][2];
	ImageView1[][] iv3=new ImageView1[5][2];
Bitmap bm;
Canvas c;

OnClickListener ocl = new OnClickListener()
{

	@Override
	public void onClick(View p1)
	{
		// TODO: Implement this method
		tbclick(p1);
	}
	
};
OnClickListener oclgelb=new OnClickListener()
{

	@Override
	public void onClick(View p1)
	{
		// TODO: Implement this metho
		onclickgelb(p1);
	}
}; 

	OnClickListener oclblau=new OnClickListener()
	{

		@Override
		public void onClick(View p1)
		{
			// TODO: Implement this metho
			onclickblau(p1);
		}
	}; 
Paint p=new Paint(Color.BLUE);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
		
        super.onCreate(savedInstanceState);
        
		setContentView(R.layout.main);
		tv=findViewById(R.id.mainTextView);
		l1=findViewById(R.id.mainLinearLayout1);
		tv2=findViewById(R.id.mainTextView1);
		tv2.setRotation(180);
		tv_Anweisung= findViewById(R.id.mainTextView2);
		//l1.setRotation(90);
		tl=findViewById(R.id.mainTableLayout);
		
		for(int i=0;i<6;i++)
		{
			tr=new TableRow(this);
		
			for (int j=0;j<5;j++)
			{
				iv1[j][i]=new ImageView1(this);
				iv1[j][i].setPadding(1,1,1,1);
				tr.addView(iv1[j][i]);
				iv1[j][i].setOnClickListener(ocl);
				iv1[j][i].posx=j;
				iv1[j][i].posy=i;
			}
			tl.addView(tr);
		}
	
		
		tl=findViewById(R.id.mainTableLayout1);

		for(int i=0;i<2;i++)
		{
			tr=new TableRow(this);

			for (int j=0;j<5;j++)
			{
				iv2[j][i]=new ImageView1(this);
				iv2[j][i].setPadding(1,1,1,1);
				tr.addView(iv2[j][i]);
				iv2[j][i].setOnClickListener(oclblau);
				iv2[j][i].posx=j;
				iv2[j][i].posy=i;
			}
			tl.addView(tr);
		}

		tl=findViewById(R.id.mainTableLayout2);

		for(int i=0;i<2;i++)
		{
			tr=new TableRow(this);

			for (int j=0;j<5;j++)
			{
				iv3[j][i]=new ImageView1(this);
				iv3[j][i].setPadding(1,1,1,1);
				tr.addView(iv3[j][i]);
				iv3[j][i].setOnClickListener(oclgelb);
				iv3[j][i].posx=j;
				iv3[j][i].posy=i;
			}
			tl.addView(tr);
		}
		
		dieSteuerung=new Steuerung(this);
    }
	public void tbclick(View v)
	{
		//tv.setText(Float.toString(v.getWidth()));
		
	
		dieSteuerung.feldClick(((ImageView1)v).posx,((ImageView1)v).posy);
	}
	public void onclickgelb(View v)
	{
		//Spieler1 Bank
		
		
		dieSteuerung.bank1Click(((ImageView1)v).posx,((ImageView1)v).posy);
	}
	public void onclickblau(View v)
	{
//Spieler0 Bank
		
		dieSteuerung.bank0Click(((ImageView1)v).posx,((ImageView1)v).posy);
	}

	
	

 	public void zeichneStein(Stein pStein, int pFeld, int  pX, int pY)
	{
		int farbe=0,ziffernfarbe=0,pNr=0;
		int g;
		int s=Resources.getSystem().getDisplayMetrics().heightPixels;
		g=s*80/1208;
		Bitmap bm1;
		Bitmap bm2=BitmapFactory.decodeResource(getResources(),R.drawable.holz1);
		//Canvas c1 =new Canvas(bm.copy(Bitmap.Config.ARGB_4444,true));
		Canvas c1=new Canvas();
		
		bm1=Bitmap.createBitmap(g,g,Bitmap.Config.ARGB_4444);
		
		c1.setBitmap(bm1);
		if (pStein!=null)
		{
			pNr=pStein.gibNr();
			switch(pStein.gibFarbe())
			{
				case 0: farbe=0xFF013ABA;ziffernfarbe=0xFFFFFFFF; c1.rotate(180,c1.getWidth()/2,c1.getHeight()/2); break;// dunkelblau
				case 1: farbe=0xFF57B0EE;ziffernfarbe=0xFF000000;  c1.rotate(180,c1.getWidth()/2,c1.getHeight()/2); break;// dunkelblau
				case 2: farbe=0xFFD71608;ziffernfarbe=0xFFFFFFFF;  c1.rotate(0); break;// dunkelblau
				case 3: farbe=0xFFF39887;ziffernfarbe=0xFF000000; c1.rotate(0);  break;// dunkelblau
			}
		p.setColor(farbe);
		c1.drawColor(Color.WHITE);
		c1.drawBitmap(bm2,0,0,null);
		c1.drawCircle(g/2,g/2,g/2,p);
		p.setColor(ziffernfarbe);
		p.setTextSize(g*5/8);
		p.setUnderlineText(false);
		p.setStrikeThruText(false);
		if(pNr<10)
			c1.drawText(Integer.toString(pNr),g/8,g*55/80,p);
		else
			c1.drawText("X",g/8,g*55/80,p);

		}
		else
		{
			c1.drawColor(Color.WHITE);
			c1.drawBitmap(bm2,0,0,null);
		}
		switch(pFeld)
		{
			case 0:
				iv1[pX][pY].setImageBitmap(bm1); 
				break;
			case 1:
				bm=bm1;
				iv2[pX][pY].setImageBitmap(bm); 
				break;
			case 2:
				iv3[pX][pY].setImageBitmap(bm1);
				break;
		}
	}
	
	
	public void zeigePunkte(int pSpieler,int pP)
	{
		switch (pSpieler)
		{
			case 1: tv.setText("Spieler1: "+Integer.toString(pP)+" Punkte"); break;
			case 0: tv2.setText("Spieler0: "+Integer.toString(pP)+" Punkte"); break;
		}
	}
	public void zeigeAnweisung(String pAnw)
	{
		tv_Anweisung.setText(pAnw);
	}
	public void button1click(View v)
	{
		finish();
		startActivity(getIntent());
	}
	public void finishclick(View v)
	{
		finish();
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(1);
	}
}
