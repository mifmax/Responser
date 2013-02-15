package home.maximv.responser;

import home.maximv.db.service.DbService;
import home.maximv.utils.SpeechActivationListener;
import home.maximv.utils.SpeechRecognition;
import home.maximv.utils.SpeechToText;
import home.maximv.utils.WikiRequest;
import home.maximv.utils.WordActivator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ResponseActivity extends Activity implements SpeechActivationListener {
    EditText ed;

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
        DbService dbService = new DbService(this);
        SQLiteDatabase db = dbService.getReadableDatabase();
        db.close();
        ed = (EditText) findViewById(R.id.editText1);
        ed.setText("где слоны");
        // new SpeechToText("Здравствуйте, представьтесь пожалуйста!").start();
        WordActivator wa = new WordActivator(this, this, "Привет");
        wa.detectActivation();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_response, menu);
        return true;
    }

    public void gogo(View v) throws ParserConfigurationException, SAXException, IOException {
        Toast.makeText(this, getResponse(ed.getText().toString()), Toast.LENGTH_LONG).show();
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
            // EditText rtext = (EditText) findViewById(R.id.nameKids);
            // rtext.setText(resString);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getResponse(String quest) {
        boolean isQuestion = false;
        String newString = "";
        String answer = "";
        String[] q = quest.split(" ");
        String[] a = getResources().getStringArray(R.array.questions);
        for (int i = 0; i < q.length; i++) {
            for (String questions : a) {
                if (questions.equalsIgnoreCase(q[i])) {
                    isQuestion = true;
                    q[i] = "";
                }
            }
        }
        if (isQuestion) {
            for (String questions : q) {
                newString = newString + questions + " ";
            }
        }
        // if () есть в базе данных
        // else идем на wiki
        newString = newString.trim();
        try {
            WikiRequest wikiRequest = new WikiRequest();
            answer = wikiRequest.sendWikiRequest(newString);
            Toast.makeText(this, answer, Toast.LENGTH_LONG).show();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer;

    }

    @Override
    public void activated(boolean success) {
        new SpeechToText("Здравствуйте, представьтесь пожалуйста!").start();
        SpeechRecognition.run(this);
    }
}
