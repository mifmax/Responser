package home.maximv.responser;

import home.maximv.utils.SpeechRecognition;
import home.maximv.utils.WikiRequest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class ResponseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm
                .queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() != 0) {
        } else {
        }
        // new SpeechToText("Здравствуйте, представьтесь пожалуйста!").start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_response, menu);
        return true;
    }
    
    public void gogo(View v) throws ParserConfigurationException, SAXException, IOException {
         Toast.makeText(this,getResponse("кто такой крокодил") , Toast.LENGTH_LONG).show();
      }

      public void recognize(View v) {
          SpeechRecognition.run(this);
      }

      @Override
      protected void onActivityResult(int requestCode, int resultCode, Intent data) {
          if (requestCode == SpeechRecognition.VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
              ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
              String resString = "";
              for (String s : matches) {
                  resString += s;
              }
              resString.trim();
     //         EditText rtext = (EditText) findViewById(R.id.nameKids);
        //      rtext.setText(resString);
          }
          super.onActivityResult(requestCode, resultCode, data);
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
