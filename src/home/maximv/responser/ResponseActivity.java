package home.maximv.responser;

import home.maximv.utils.WikiRequest;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Toast;

public class ResponseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_response, menu);
        return true;
    }
    public String getResponse(String quest){
        boolean isQuestion=false;
        String newString="";
        String answer="";
        String[] q = quest.split(" ,");
        String[] a = getResources().getStringArray(R.array.questions);
        for (int i = 0; i < q.length; i++) {
            for (String questions : a) {
                if (questions.equalsIgnoreCase(q[i])){
                    isQuestion=true;
                    q[i]="";
                    break;
                }
            }
        }
        if (isQuestion){
            for (String questions : q) {
                newString=newString+questions+" ";   
            }
        }
        //if () есть в базе данных
        //else идем на wiki
        try {
            WikiRequest wikiRequest = new WikiRequest(); 
           answer =  wikiRequest.sendWikiRequest("newString");
           Toast.makeText(this,answer , Toast.LENGTH_LONG).show();

        } catch (URISyntaxException e) {
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
        return answer;

    }    
}
