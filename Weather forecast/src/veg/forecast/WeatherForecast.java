package veg.forecast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class WeatherForecast extends Activity {
	
	TextView txt;	TextView temp;	TextView vlaznost;	TextView pritisak;	TextView wind;
	TextView izl; 	TextView zlz;	TextView txt2;	TextView txt3;	TextView vis;
	TextView day1;  TextView tmp1;	TextView fore1;		TextView high1;		TextView low1;	
	TextView day2;  TextView tmp2;	TextView fore2;		TextView high2;		TextView low2;
	Spinner Grad;
	String adresa;
	
	private OnItemSelectedListener listener = new OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			switch(Grad.getSelectedItemPosition()){
			case 0:
				adresa = "http://weather.yahooapis.com/forecastrss?w=535620&u=c";prikaziPodatke();
				break;
			case 1:
				adresa = "http://weather.yahooapis.com/forecastrss?w=532697&u=c";prikaziPodatke(); 
				break;
			case 2:
				adresa = "http://weather.yahooapis.com/forecastrss?w=535658&u=c";prikaziPodatke(); 
				break;
			}
		}
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub			
		}		
	};
			
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
                 
        Grad=(Spinner)findViewById(R.id.grad);
        Grad.setOnItemSelectedListener(listener);
                             
        txt = (TextView)findViewById(R.id.first);
        temp = (TextView)findViewById(R.id.temp);
        vlaznost = (TextView) findViewById(R.id.vlaznost);
        pritisak = (TextView) findViewById(R.id.pritisak);  
        wind = (TextView)findViewById(R.id.vetar);
        vis = (TextView) findViewById(R.id.vidljivost);
        
        izl = (TextView) findViewById(R.id.rise);
        zlz = (TextView) findViewById(R.id.set);
        txt2 = (TextView) findViewById(R.id.text);
        txt3 = (TextView) findViewById(R.id.latlong);
                
        day1 = (TextView)findViewById(R.id.dan1);
        fore1 = (TextView)findViewById(R.id.najava1);      
        tmp1 = (TextView) findViewById(R.id.temper1);
        high1 = (TextView) findViewById(R.id.gornja1);      
        low1 = (TextView)findViewById(R.id.donja1);
        
        day2 = (TextView)findViewById(R.id.dan2);
        fore2 = (TextView)findViewById(R.id.najava2);    
        tmp2 = (TextView) findViewById(R.id.temper2);
        high2 = (TextView) findViewById(R.id.gornja2);     
        low2 = (TextView)findViewById(R.id.donja2);
                
    }
    
    
	private void prikaziPodatke() {
		Document doc = null;
		try {
			URL url = new URL(adresa);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(url.openStream());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String kod = postavi("yweather:condition", "code", 0, doc);
		ImageView img = (ImageView) findViewById(R.id.slika);
		img.setImageBitmap(Slika(kod));
		
		txt2.setText(postavi("title","", 2, doc));
		txt3.setText("Lat: "+ postavi("geo:lat", "", 0, doc)+ ", Long: " + postavi("geo:long", "", 0, doc));
		
		txt.setText(postavi("yweather:condition", "text", 0, doc));
		temp.setText("Temperature: " + postavi("yweather:condition", "temp", 0, doc) + "°C");
		vlaznost.setText("Humidity: "+postavi("yweather:atmosphere", "humidity", 0, doc)+"%");
		pritisak.setText("Pressure: "+postavi("yweather:atmosphere", "pressure", 0, doc)+" mb");
		wind.setText("Wind: " + postavi("yweather:wind", "speed", 0, doc) + "km/h");
		vis.setText("Visibility: " + postavi("yweather:atmosphere", "visibility", 0, doc) + "km" + "\n\n");
		izl.setText("Sunrise: " + postavi("yweather:astronomy", "sunrise", 0, doc));
		zlz.setText("Sunset: " + postavi("yweather:astronomy", "sunset", 0, doc));
		
		String kod1 = postavi("yweather:forecast", "code", 0, doc);
		ImageView imgSml = (ImageView) findViewById(R.id.slika1);
		imgSml.setImageBitmap(Slika(kod1));
		
		day1.setText(postavi("yweather:forecast", "day", 0, doc)+", "+postavi("yweather:forecast", "date", 0, doc));
		fore1.setText(postavi("yweather:forecast", "text", 0, doc));
		tmp1.setText("Temperature:");
		high1.setText("Max: " + postavi("yweather:forecast", "high", 0, doc)+ "°C");
		low1.setText("Min: " + postavi("yweather:forecast", "low", 0, doc)+ "°C");
		
		String kod2 = postavi("yweather:forecast", "code", 1, doc);
		ImageView imgSml1 = (ImageView) findViewById(R.id.slika2);
		imgSml1.setImageBitmap(Slika(kod2));
		
		day2.setText(postavi("yweather:forecast", "day", 1, doc)+", "+postavi("yweather:forecast", "date", 1, doc));
		fore2.setText(postavi("yweather:forecast", "text", 1, doc));
		tmp2.setText("Temperature:");
		high2.setText("Max: " + postavi("yweather:forecast", "high", 1, doc)+ "°C");
		low2.setText("Min: " + postavi("yweather:forecast", "low", 1, doc)+ "°C");
		
		
	} 
	private String postavi(String tag, String item, int n, Document doc){
		String rez; 		
		
		if (doc == null) {
			return rez = "Error! There is no document!";
		}
		
		NodeList lista = doc.getElementsByTagName(tag);
		if (lista.getLength() == 0) {
			return rez = "Error! Searched tag is no available";
		}
		Node node = lista.item(n);		
		NamedNodeMap atributi = node.getAttributes();
		if(atributi.getNamedItem(item) != null)
			rez = atributi.getNamedItem(item).getNodeValue();
		else
			rez = node.getTextContent();	
		return rez;
	}
	private Bitmap Slika(String kod){
		String linkDoSlike = "http://l.yimg.com/a/i/us/we/52/" + kod + ".gif";
		Bitmap slika = null;

		URL slikaUrl;
		try {
			slikaUrl = new URL(linkDoSlike);
			slika = BitmapFactory.decodeStream(slikaUrl.openStream());

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (slika != null) {
			return slika;
		}
		return slika;
		
	}
	
}
