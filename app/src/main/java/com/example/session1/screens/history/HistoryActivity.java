package com.example.session1.screens.history;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.session1.R;
import com.example.session1.models.AssetTransferLogs;
import com.example.session1.services.APIUtilities;
import com.example.session1.services.IDataClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {
    ListView listViewHistory;
    Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Transfer History");
        actionBar.setDisplayHomeAsUpEnabled(true);

        Mapping();

        buttonBack.setOnClickListener(v -> onBackPressed());

        //GetTransferHistory();
    }

    private void Mapping() {
        listViewHistory = findViewById(R.id.listViewHistory);
        buttonBack = findViewById(R.id.buttonBack);
    }

//    private void GetTransferHistory() {
//        IDataClient client = APIUtilities.getData();
//        Call<List<AssetTransferLogs>> call = client.getAssetTransferLogs();
//        call.enqueue(new Callback<List<AssetTransferLogs>>() {
//            @Override
//            public void onResponse(Call<List<AssetTransferLogs>> call, Response<List<AssetTransferLogs>> response) {
//                ArrayList<AssetTransferLogs> assetTransferLogsArrayList = (ArrayList<AssetTransferLogs>) response.body();
//                final TransferHistoryAdapter adapter = new TransferHistoryAdapter(HistoryActivity.this, R.layout.history_layout, assetTransferLogsArrayList);
//                listViewHistory.setAdapter(adapter);
//            }
//
//            @Override
//            public void onFailure(Call<List<AssetTransferLogs>> call, Throwable t) {
//
//            }
//        });
//    }

    // Custom adapter for ListView
    public static class TransferHistoryAdapter extends BaseAdapter {

        private Context context;
        private int layout;
        private List<AssetTransferLogs> list;

        public TransferHistoryAdapter(Context context, int layout, List<AssetTransferLogs> list) {
            this.context = context;
            this.layout = layout;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = new ViewHolder();

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(layout, null);
                holder.textTransferDate = convertView.findViewById(R.id.textViewTransferDate);
                holder.textOldDL = convertView.findViewById(R.id.textView29);
                holder.textOldAssetSN = convertView.findViewById(R.id.textViewOldAssetSN);
                holder.textNewDL = convertView.findViewById(R.id.textViewNewDL);
                holder.textNewAssetSN = convertView.findViewById(R.id.textViewNewAssetSN);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            AssetTransferLogs history = list.get(position);
            holder.textTransferDate.setText(history.getTransfer_date());
            holder.textOldDL.setText(history.getOld_dl());
            holder.textOldAssetSN.setText(history.getOld_assetsn());
            holder.textNewDL.setText(history.getNew_dl());
            holder.textNewAssetSN.setText(history.getNew_assetsn());

            return convertView;
        }

        public static class ViewHolder {
            TextView textTransferDate, textOldDL, textOldAssetSN, textNewDL, textNewAssetSN;
        }
    }
}