package alura.com.br.agendacontatos.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.SmsMessage;
import android.widget.Toast;

import alura.com.br.agendacontatos.R;
import alura.com.br.agendacontatos.dao.AlunoDAO;

/**
 * Created by root on 13/04/17.
 */

public class SMSReceiver extends BroadcastReceiver{


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");

        byte[] pdu = (byte[]) pdus[0];
        String formato = (String) intent.getSerializableExtra("format");

        SmsMessage sms = SmsMessage.createFromPdu(pdu, formato);

        String telefone = sms.getDisplayOriginatingAddress();

        AlunoDAO dao = new AlunoDAO(context);

        if(dao.ehAluno(telefone)) {
            Toast.makeText(context, "Chegou SMS de um Aluno!", Toast.LENGTH_SHORT).show();

            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.msg);
            mediaPlayer.start();
        }

        dao.close();
    }
}
