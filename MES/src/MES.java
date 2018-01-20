
   import java.awt.*;      

import java.awt.Image;
import java.awt.MediaTracker;



import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;




import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class MES
{

   public static void main(String[] args) 
   {      MESA ramka = new MESA();
      ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      ramka.setVisible(true); 
   }
}

class MESA extends JFrame 
{
	private static final long serialVersionUID = 1L;
	float Temp[];
	public float K[][];
	float P[];
	float a[][];
	float b[];
	float c[];
	public int  Nd;
	public int Ni;
	float T0;
	public String filename;
	float r0;
	float r1;
	float gamp;
	float gam1;
	float ro;
	float alfa;
    JLabel etyk1 = new JLabel("  Autor : Mateusz Krawczyk  ");
      JTextField wyopinia1=new JTextField(10); //obiekty do wyswietlania danych
      JTextField wyopinia2=new JTextField(10); // na ekranie typu JTextField
      JTextField wyopinia3=new JTextField(10);
      JTextField wyopinia4=new JTextField(10);
      JTextField wyopinia5=new JTextField(10);
      JTextField wyopinia6=new JTextField(10); 
      JTextField wyopinia7=new JTextField(10);
      JTextField wyopinia8=new JTextField(10);
      JTextField wyopinia9=new JTextField(10);
      JTextField wyopinia10=new JTextField(10);
      Dane dana = new Dane();
      JPanel panel=new JPanel();
   public MESA()
   { 
	   setTitle("Rozwiazywanie rownan przewodnictwa cieplnego metoda MES");
       setSize(750,650);
       
   	JPanel panel2=new JPanel();
   	panel.setLayout(new GridLayout(2, 1 ) );
   	panel2.setLayout(new GridLayout(25, 25));
   	dodajPrzelacznik();
   	panel.add(etyk1);
   	JLabel eopinia1= new JLabel("Podaj dane do obliczeñ :",SwingConstants.LEFT);
   panel2.add(eopinia1); //wstawianie etykiety i
   //	panel2.add(wyopinia1); //pola tekstowego do wyswietlania do kontenera
   	JLabel eopinia2= new JLabel("Promien wewnêtrzny Ro?",SwingConstants.LEFT);
   	panel2.add(eopinia2);
   	panel2.add(wyopinia2);
   	JLabel eopinia3= new JLabel("Promien zewnetrzny R1?",SwingConstants.LEFT);
   	panel2.add(eopinia3);
   	panel2.add(wyopinia3);
   	JLabel eopinia4= new JLabel("Wspolczynnik przewodzenia drutu",SwingConstants.LEFT);
   	panel2.add(eopinia4);
   	panel2.add(wyopinia4);
   	JLabel eopinia5= new JLabel("Wspolczynnik przewodzenia izolacji",SwingConstants.LEFT);
   	panel2.add(eopinia5);
   	panel2.add(wyopinia5);
   	JLabel eopinia6= new JLabel("Temperatura na zewnatrz.",SwingConstants.LEFT);
   	panel2.add(eopinia6);
   	panel2.add(wyopinia6);
   	JLabel eopinia7= new JLabel("Na ile ilementów podzieliæ obszar drutu?",SwingConstants.LEFT);
   	panel2.add(eopinia7);
   	panel2.add(wyopinia7);
   	JLabel eopinia8= new JLabel("Na ile ilementów podzieliæ obszar izolacji?",SwingConstants.LEFT);
   	panel2.add(eopinia8);
   	panel2.add(wyopinia8);
   	JLabel eopinia9= new JLabel("Wydajnoœæ wewnetrznego ¿ród³a ciep³a?",SwingConstants.LEFT);
   	panel2.add(eopinia9);
   	panel2.add(wyopinia9);
   	JLabel eopinia10= new JLabel("Strumien na zewnatrz ?",SwingConstants.LEFT);
   	panel2.add(eopinia10);
   	panel2.add(wyopinia10);
   	setLayout( new BorderLayout() );
   	add( panel, BorderLayout.SOUTH );
   	add(panel2,BorderLayout.EAST);
   	Obrazek obrr=new Obrazek();
   	Container powZawartosci = getContentPane();
    powZawartosci.add(obrr);
   	
   // dana.opinia1=new String("dane.txt");//poczatkowe dane wstawiane do pol danych 
	//wyopinia1.setText(dana.opinia1);
	dana.opinia2=new String("0.00125");
	wyopinia2.setText(dana.opinia2);
	dana.opinia3=new String("0.00325");
	wyopinia3.setText(dana.opinia3);
	dana.opinia4=new String("400");
	wyopinia4.setText(dana.opinia4);
	dana.opinia5=new String("0.15");
	wyopinia5.setText(dana.opinia5);
	dana.opinia6=new String("300");
	wyopinia6.setText(dana.opinia6);
	dana.opinia7=new String("10");
	wyopinia7.setText(dana.opinia7);
	dana.opinia8=new String("10");
	wyopinia8.setText(dana.opinia8);
	dana.opinia9=new String("6175000");
	wyopinia9.setText(dana.opinia9);
	dana.opinia10=new String("0.000000002");
	wyopinia10.setText(dana.opinia10);

   }

   public void licz2()//funkcja odpowiedzialna za ustalenie odpowiednich wyrazów macierzy 
   {
	   Temp=new float[(Ni+Nd)+1];
	   
	   P=new float[(Ni+Nd)+2];
	   a=new float[(Ni+Nd)+2][4];
	   b=new float[(Ni+Nd)+2];
	   c=new float[(Ni+Nd)+2];
	   a[1][1]=0;
	   a[(Ni+Nd)+1][3]=0;
	   float pi= (float) 3.1415927;
	   float delr0=r0/Nd;
	   float delr1=(r1-r0)/Ni;
	   float przew;
	   float krok=delr1;
	   P[1]=0;//?????????????????????????????????????????????????????????????????
	   for(int i=2; i<=((Ni+Nd));i++)   {P[i]=0;}
float e=r0;
			 for(int i=1; i<=((Ni+Nd));i++)
			  { if (i<(Nd+1))  {

				   przew=gamp;
				 krok=delr0;
				  float df=(float)1/krok;
				float s=(float)1/3;
			
				 float ss=(float)2/3;
				 
					P[i]=(float) (P[i]+pi*krok*ro*((i-1)*krok+s*krok));
				  P[i+1]=(float) (P[i+1]+pi*krok*ro*((i-1)*krok+ss*krok));
				 
		
					 
					  a[i][2]=(float)(a[i][2]+pi*przew*df*(2*(i-1)*krok+krok)); 
					  a[i+1][2]=(float) (a[i+1][2]+pi*przew*df*(2*(i-1)*krok+krok)); 
					 a[i+1][1]=(float) (-pi*przew*df*(2*(i-1)*krok+krok)); 
					  a[i][3]=(float) (-pi*przew*df*(2*(i-1)*krok+krok)); 
			  }
			  else
			   { 
				  przew=gam1;
		  krok=delr1;	
			  
		  float df=(float)1/krok;
			  
			 a[i][2]=(float)(a[i][2]+pi*przew*df*(2*e+krok)); 
		  a[i+1][2]=(float) (a[i+1][2]+pi*przew*df*(2*e+krok)); 
		  a[i+1][1]=(float) (-pi*przew*df*(2*e+krok)); 
		  a[i][3]=(float) (-pi*przew*df*(2*e+krok));
		  e=e+krok;
		  
			  }}
			 krok=(float)0.00001;
			 float df=(float)1/krok;
			 przew=(float)alfa/krok;		 
			 a[Ni+Nd+1][2]=(float)(a[Ni+Nd+1][2]+pi*przew*df*(2*r1+krok));
			 float koniec=(float)(-pi*przew*df*(2*r1+krok));
			 P[Ni+Nd+1]=P[Ni+Nd+1]-koniec*T0;


			  rozwiazmacierz();	  	  
   }
   

   public void zapisz() throws IOException
   {
       PrintWriter wy = new PrintWriter(new FileWriter("dane.txt"));
       wy.println(Ni);
       wy.println(Nd);
       wy.println(r1);
       wy.println(r0);
       for (int i=0;i<((Ni+Nd)+1);i++){	
       	wy.println(Temp[i]);
               // zapisz tekst do pliku
       }

       
     wy.close();	
   }


public void rozwiazmacierz() //zastosowana zosta³a metoda Thomasa dla uk³adów trójprzek¹tniowych
{ int n=Ni+Nd+1;

b[1]=(float)((-a[1][3])/a[1][2]);
c[1]=(float)((P[1])/a[1][2]);

for(int i=2; i<=n ;i++){	
	b[i]=(float)((-a[i][3])/((a[i][1]*b[i-1])+a[i][2]));
		c[i]=(float)(( P[i]-a[i][1]*c[i-1])/((a[i][1]*b[i-1])+a[i][2]));
} 
Temp[n-1]=c[n];
for(int i=(n-2); i>=0 ;i--){
	Temp[i]=(float) b[i+1]*Temp[i+1]+c[i+1];

}

}

   public void dodajPrzelacznik()
   { JButton przycisk = new JButton("LICZ ROZKLAD TEMPERATURY ");
      panel.add(przycisk);
      ActionListener sluchacz = new 
         ActionListener()
         {
           			public void actionPerformed(ActionEvent zd)
            {
         
			String filename = wyopinia1.getText().trim();
            
         	String tempCel = wyopinia2.getText().trim();
             r0 = Float.parseFloat(tempCel);
             tempCel = wyopinia3.getText().trim();
             r1 = Float.parseFloat(tempCel);
             tempCel = wyopinia4.getText().trim();
             gamp = Float.parseFloat(tempCel);
             tempCel = wyopinia5.getText().trim();
             gam1 = Float.parseFloat(tempCel);
             tempCel = wyopinia6.getText().trim();
             T0 = Float.parseFloat(tempCel);
             tempCel = wyopinia7.getText().trim();
           Nd = Integer.parseInt(tempCel);
             tempCel = wyopinia8.getText().trim();
             Ni = Integer.parseInt(tempCel);
             tempCel = wyopinia9.getText().trim();
             ro = Float.parseFloat(tempCel);
             tempCel = wyopinia10.getText().trim();
             alfa = Float.parseFloat(tempCel);
             licz2();
             try {
				zapisz();
			} catch (IOException e) {
				e.printStackTrace();
			}
             FillFrame ramka2 = new FillFrame();
             ramka2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             ramka2.setVisible(true);
            }
         }; 

      przycisk.addActionListener(sluchacz);
   }
   

}

class Dane
{ String opinia1,opinia2,opinia3,opinia4,opinia5,opinia6,opinia7,opinia8,opinia9,opinia10;
Dane()
{ opinia1=opinia2=opinia3=opinia4=opinia5=opinia6=opinia7=opinia8=opinia9=opinia10=null;}}



 class FillFrame extends JFrame
{
	
	private static final long serialVersionUID = 1L;
	public int width, height; 
public FillFrame()
   {width = 800;
   height = 650;
      setTitle("Wykres");
      setSize(width, height);
      PanelWypelnienia panel = new PanelWypelnienia();
      Container powZawartosci = getContentPane();
      powZawartosci.add(panel);

   }
}

 class PanelWypelnienia extends JPanel
{  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
int width = 800;
int height = 600;
public float SD[];
public int N1,N2;
public float rp,rc;
public String aaa;
   public void paintComponent(Graphics a)
   {  
	   a.setColor(new Color(0,0,0));
       
       a.drawLine (10, height-10 , width-10, height-10 );
       a.drawLine (10, 10, 10, height-10);
       a.drawLine (10, 10 ,15, 15 );
       a.drawLine (10, 10, 5, 15);
       a.drawLine (width-10, height-10,width-15, height-5);
       a.drawLine (width-10, height-10,width-15, height-15);
       a.drawString("Wykres przedstawiaj¹cy zaleznoœc temperatury dla poszczególnych wêz³ów", 100, 20);
       a.drawString("T",2,25) ;
       a.drawString("R",width-20,height) ;
       a.drawString("Tmax",12,35) ;
       a.drawString("Tmin",12,590) ;
       a.drawString("0",6,600) ;
       
       
       
try {
	odczytaj();
} catch (IOException e) {
	// TODO Automatycznie generowany blok catch
	e.printStackTrace();
}

float xx=SD[0];
float min=SD[0];

for(int l=0; l<(N1+N2+1);l++){
if (SD[l]>xx){
xx=SD[l];}
if (SD[l]<min){
min=SD[l];
	}
}




PrintWriter wy;
try {
	wy = new PrintWriter(new FileWriter("wartosci.txt"));
	wy.println("Wartoœæ maksymalna temperatury : "+xx);
	wy.println("Wartoœæ minimalna temperatury :"+min);
	wy.println("Wartoœæ temperatury na brzegu izolacji :"+SD[N1+N2]);
	wy.println("Wartoœæ temperatury na brzegu przewodnika :"+SD[N2]);
	wy.close();
} catch (IOException e) {
	// TODO Automatycznie generowany blok catch
	e.printStackTrace();
}



int delx=(int)(780/((N1+N2)+1));
int y1 =(int) (590-550*((float)(SD[0]-min)/(xx-min)));
int x1 = 10;
int x2=10;
a.drawString("Tmax : "+xx+"K",16,400) ;
a.drawString("Tmin : "+min+"K",16,415) ;
a.drawString("Wartoœæ temperatury na brzegu izolacji :"+SD[N1+N2]+"K",16,430) ;
a.drawString("Wartoœæ temperatury na brzegu przewodnika :"+SD[N1]+"K",16,445) ;
a.drawString("Wartoœæ temperatury w srodku przewodnika :"+SD[0]+"K",16,460) ;
a.drawString("Izolacja",delx*N1+20,600) ;
a.drawLine( delx*N1+10  ,580 , delx*N1+10  ,600);
a.drawString("Przewodnik",50,600) ;

       for(int i=0; i<(N1+N2+1);i++){
    	 
                   int y2 =(int)( 590-(550*((SD[i]-min)/(xx-min))));
                  x2 = i*delx+10;
                       a.setColor(new Color(255,0,0));
                        a.drawLine( x1  ,y1 , x2  ,y2);
            x1=x2;
            y1=y2;
                        
       }   } 
   

   public void odczytaj() throws IOException
   {File file=new File("dane.txt");
	  RandomAccessFile we=new RandomAccessFile(file,"r");
	aaa=we.readLine();
	N1=Integer.parseInt(aaa);
	aaa=we.readLine();
	N2=Integer.parseInt(aaa); 
	aaa=we.readLine();
	rp=Float.parseFloat(aaa);
	aaa=we.readLine();
	rc=Float.parseFloat(aaa); 
	

	
	
	SD=new float[(N1+N2)+1];
	
	for(int i=0; i<(N1+N2+1);i++)
	{		aaa=we.readLine();
		SD[i] = Float.parseFloat(aaa);

	}
	we.close();
   }

}

	
class Obrazek extends JPanel
{  
public Obrazek()
{
    obraz = Toolkit.getDefaultToolkit().getImage("b.gif");
    MediaTracker trop = new MediaTracker(this);
    trop.addImage(obraz, 0);
    try { trop.waitForID(0); } 
    catch (InterruptedException exception) {}
	}
   public void paintComponent(Graphics g)
   {
	   super.paintComponent(g);  
       g.drawImage(obraz, 0,50, null);
       g.drawString("Schematyczny rysunek przedstawiaj¹cy badane zagadnienie",25,45);
   }
   

   private Image obraz;
   }
