package com.example.session1.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.session1.HistoryActivity;
import com.example.session1.InfoActivity;
import com.example.session1.MainActivity;
import com.example.session1.R;
import com.example.session1.TransferActivity;
import com.example.session1.models.AssetCatalogues;

import java.util.List;

public class MainAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<AssetCatalogues> list;

    public MainAdapter(Context context, int layout, List<AssetCatalogues> list) {
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
            assert inflater != null;
            convertView = inflater.inflate(layout, null);
            holder.imgAsset = convertView.findViewById(R.id.imageViewAsset);
            holder.textAssetName = convertView.findViewById(R.id.textViewAssetName);
            holder.textDepartmentName = convertView.findViewById(R.id.textViewDepartmentName);
            holder.textAssetSN = convertView.findViewById(R.id.textViewAssetSN);
            holder.buttonEdit = convertView.findViewById(R.id.imageButtonEdit);
            holder.buttonTransfer = convertView.findViewById(R.id.imageButtonTransfer);
            holder.buttonHistory = convertView.findViewById(R.id.imageButtonHistory);

            ViewHolder finalHolder = holder;

            holder.buttonEdit.setOnClickListener(v -> {
                Intent intent = new Intent(context, InfoActivity.class);
                intent.putExtra("data", finalHolder.textAssetName.getText());
                //startActivity(intent);
            });

            holder.buttonTransfer.setOnClickListener(v -> {
                Intent intent = new Intent(context, TransferActivity.class);
                intent.putExtra("data0", finalHolder.textAssetName.getText());
                intent.putExtra("data1", finalHolder.textDepartmentName.getText());
                intent.putExtra("data2", finalHolder.textAssetSN.getText());
                //startActivity(intent);
            });

            holder.buttonHistory.setOnClickListener(v -> {
                Intent intent = new Intent(context, HistoryActivity.class);
                //startActivity(intent);
            });

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final AssetCatalogues ac = list.get(position);
        if (ac.getAssetphoto() != null) {
            holder.imgAsset.setImageBitmap(ac.getAssetphoto());
        } else {
            holder.imgAsset.setImageResource(R.drawable.image_layout);
        }
        holder.textAssetName.setText(ac.getAssetname());
        holder.textDepartmentName.setText(ac.getDepartmentname());
        holder.textAssetSN.setText(ac.getAssetsn());

        return convertView;
    }

    public static class ViewHolder {
        ImageView imgAsset;
        TextView textAssetName, textDepartmentName, textAssetSN;
        ImageButton buttonEdit, buttonTransfer, buttonHistory;
    }
}
