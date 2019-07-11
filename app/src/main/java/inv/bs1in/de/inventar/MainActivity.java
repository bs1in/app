package inv.bs1in.de.inventar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private Button btnCamera;
    private Button btnReport;

    private Button btnFinish;

    private IntentIntegrator qrScan;

    private TextView txtId;
    private TextView txtDesc;
    private CheckBox chkConfirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFinish = findViewById(R.id.btn_finish);
        qrScan = new IntentIntegrator(this);
        qrScan.setBeepEnabled(false);
        qrScan.setOrientationLocked(false);

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrScan.initiateScan();
            }
        });

        this.txtId = findViewById(R.id.txtId);
        this.txtDesc = findViewById(R.id.txtDesc);
        this.chkConfirm = findViewById(R.id.chkConfirm);
        this.btnReport = findViewById(R.id.btnConfirm);
        this.btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean confirm = MainActivity.this.chkConfirm.isChecked();

                if (confirm) {
                    Ticket t = MainActivity.this.createTicket();
                    sendMessage("Dein Ticket wurde angelegt!", "Erfolgreich!");

                    try {
                        Ticket.createTicket(t);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                } else {
                    sendMessage("Du musst die Checkbox bestätigen!", "Fehler!");
                }
            }
        });
    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Test", Toast.LENGTH_LONG).show();
            } else {
                txtId.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private Ticket createTicket() {
        Ticket t = new Ticket();
        t.device = this.txtId.getText().toString();
        t.description = this.txtDesc.getText().toString();
        t.done = false;
        return t;
    }

    private void sendMessage(String message, String title) {
        AlertDialog.Builder msg = new AlertDialog.Builder(this);
        msg.setTitle(title);
        msg.setMessage(message);
        msg.setCancelable(true);

        AlertDialog msgDialog = msg.create();
        msgDialog.show();
    }


}
