package com.android.hubblering;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.muserapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private JSONArray muserdata;

    public UserAdapter(JSONArray mJsonObject)
    {
        this.muserdata=mJsonObject;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_data_layout,null);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position)
    {
        try {
            JSONObject mjson = muserdata.getJSONObject(position);

                Iterator<String> keys = mjson.keys();
                String firstValue = keys.next();
                String value = mjson.optString(firstValue);
                holder.txtField1.setText(firstValue+" : "+ value);


            if(mjson.getString(Constants.AGE)!=null)
            {
                holder.txtField2.setText(Constants.AGE+" : "+mjson.getString(Constants.AGE)+"");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return muserdata.length();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder
    {

        private TextView txtField1,txtField2;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtField1 = (TextView)itemView.findViewById(R.id.txtField1);
            txtField2 = (TextView)itemView.findViewById(R.id.txtField2);
        }
    }
}
