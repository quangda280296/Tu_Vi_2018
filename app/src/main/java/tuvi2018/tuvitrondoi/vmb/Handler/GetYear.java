package tuvi2018.tuvitrondoi.vmb.Handler;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import java.util.Calendar;

import tuvi2018.tuvitrondoi.vmb.Interface.IYearChoosen;
import tuvi2018.tuvitrondoi.vmb.R;

/**
 * Created by Manh Dang on 01/09/2018.
 */

public class GetYear {

    private IYearChoosen listener;
    private Context context;

    public GetYear(IYearChoosen listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    public void getYear() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_date_picker);
        dialog.show();

        final Calendar mCalendar = Calendar.getInstance();
        final DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.date_picker);
        Button date_time_set = (Button) dialog.findViewById(R.id.date_time_set);
        datePicker.init(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH), null);

        LinearLayout ll = (LinearLayout) datePicker.getChildAt(0);
        LinearLayout ll1 = (LinearLayout) ll.getChildAt(0);

        ll1.getChildAt(0).setVisibility(View.GONE);
        ll1.getChildAt(1).setVisibility(View.GONE);

        date_time_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.onYearReturn(datePicker.getYear());
            }
        });
    }
}

