package inv.bs1in.de.inventar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button btnCamera;
    private Button btnReport;
    private TextView txtId;
    private TextView txtDesc;
    //private CheckBox chkConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.txtId = findViewById(R.id.txtId);
        this.txtDesc = findViewById(R.id.txtDesc);
        //this.chkConfirm = findViewById(R.id.chkConfirm);
        this.btnReport = findViewById(R.id.btnConfirm);
        this.btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                boolean confirm = true;//MainActivity.this.chkConfirm.isChecked();
                if (confirm) {
                    Ticket t = MainActivity.this.createTicket();
                    try {
                        Ticket.createTicket(t);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    private Ticket createTicket() {
        Ticket t = new Ticket();
        t.device = this.txtId.getText().toString();
        t.description = this.txtDesc.getText().toString();
        t.done = false;
        return t;
    }
}
