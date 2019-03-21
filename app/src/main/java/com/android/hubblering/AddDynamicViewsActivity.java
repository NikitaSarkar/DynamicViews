package com.android.hubblering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.muserapp.R;
import com.android.muserapp.databinding.ActivityAddDynamicViewsBinding;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddDynamicViewsActivity extends AppCompatActivity {

    private ActivityAddDynamicViewsBinding mActivityAddUserDataBinding;
    private String TAG = AddDynamicViewsActivity.class.getSimpleName();
    private List<UIModel> modelList = new ArrayList<>();
    private String gender="";
    LinearLayout ll;
    private List<String> values;
    private int min=0,max=0;
    private List<Validation> mvalidlist=new ArrayList<>();
    private boolean isreq;
    private Validation mval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAddUserDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_dynamic_views);
        ll = mActivityAddUserDataBinding.linearContainer;
        mActivityAddUserDataBinding.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateJsonData();
            }
        });
        inflateDynamicUI();
    }

    private void inflateDynamicUI()
    {
        Utils.showLog(TAG, "Json data :" + loadJSONFromAsset());
        try {
            JSONArray jsonArray = new JSONArray(loadJSONFromAsset());
            for (int x = 0; x < jsonArray.length(); x++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(x);
                switch (jsonObject.getString("type"))
                {
                    case "text":
                        isreq=false;
                        if(jsonObject.has("required"))
                        {
                            isreq=jsonObject.getBoolean("required");
                        }
                        mval = new Validation(jsonObject.getString("field-name"),isreq);
                        mvalidlist.add(mval);
                        addMultiLineView(jsonObject.getString("field-name"));
                        break;

                    case "number":
                        addNumberView(jsonObject.getString("field-name"));
                        isreq=false;
                        if(jsonObject.has("required"))
                        {
                            isreq=jsonObject.getBoolean("required");
                        }
                        if(jsonObject.has("min") || jsonObject.has("max"))
                        {
                            min=jsonObject.getInt("min");
                            max=jsonObject.getInt("max");
                            mval = new Validation(jsonObject.getString("field-name"),isreq,min,max);
                        }
                        else
                        {
                            mval = new Validation(jsonObject.getString("field-name"),isreq);
                        }
                        mvalidlist.add(mval);

                        break;

                    case "dropdown":
                        JSONArray array = jsonObject.getJSONArray("options");
                        isreq=false;
                        if(jsonObject.has("required"))
                        {
                            isreq=jsonObject.getBoolean("required");
                        }
                        mval = new Validation(jsonObject.getString("field-name"),isreq);
                        mvalidlist.add(mval);
                        values = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++)
                        {
                            values.add(array.getString(i));
                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        addSpinnerView(jsonObject.getString("field-name"), dataAdapter);
                        break;

                    case "multiline":
                        isreq=false;
                        if(jsonObject.has("required"))
                        {
                            isreq=jsonObject.getBoolean("required");
                        }
                        mval = new Validation(jsonObject.getString("field-name"),isreq);
                        mvalidlist.add(mval);

                        addMultiLineView(jsonObject.getString("field-name"));
                        break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("users.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    public void addMultiLineView(String hint)
    {
        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.text_field_item, null);
        TextView txtitle = (TextView) v.findViewById(R.id.txtTitle);
        EditText editText = (EditText) v.findViewById(R.id.etField);
        txtitle.setText(hint);
        ll.addView(v);
    }
    public void addNumberView(String hint)
    {
        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.num_field_item, null);
        TextView txtitle = (TextView) v.findViewById(R.id.txtTitle);
        EditText editText = (EditText) v.findViewById(R.id.etField);
        txtitle.setText(hint);
        ll.addView(v);
    }
    public void addSpinnerView(String hint, SpinnerAdapter spinnerAdapter) {
        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = vi.inflate(R.layout.spinner_item, null);
        TextView txtitle = (TextView) v.findViewById(R.id.txtTitle);
        Spinner spinner = (Spinner) v.findViewById(R.id.spinnerField);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender=values.get(i);
                Log.e("************","**************"+values.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        txtitle.setText(hint);
        ll.addView(v);
    }

    private void generateJsonData()
    {
        try {
            JSONObject jsonObject = new JSONObject();
            for(int i = 0 ; i < ll.getChildCount(); i++){
                View view = (View)ll.getChildAt(i);
                TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
                if(txtTitle.getText().toString().equalsIgnoreCase("Gender"))
                {
                    jsonObject.put(txtTitle.getText().toString(),gender);
                }
                else
                {
                    EditText editText = (EditText) view.findViewById(R.id.etField);

                    switch (txtTitle.getText().toString().toLowerCase()) {
                        case "name":
                            for (int c = 0; c < mvalidlist.size(); c++) {
                                if (mvalidlist.get(c).field_name.equalsIgnoreCase("name")) {
                                    if (mvalidlist.get(i).isRequired) {
                                        if (editText.getText().toString().length() == 0) {
                                            Toast.makeText(AddDynamicViewsActivity.this, getResources().getString(R.string.name_validation_msg), Toast.LENGTH_SHORT).show();

                                            return;
                                        }
                                    }
                                    jsonObject.put(txtTitle.getText().toString(), editText.getText().toString());

                                    break;
                                } else {
                                }
                            }

                            break;
                        case "age":
                            for (int c = 0; c < mvalidlist.size(); c++)
                            {
                                if (mvalidlist.get(c).field_name.equalsIgnoreCase("age"))
                                {
                                    if (mvalidlist.get(c).isRequired)
                                    {
                                        if (editText.getText().toString().length() > 0)
                                        {

                                        }
                                        else
                                        {
                                            Toast.makeText(AddDynamicViewsActivity.this, "Enter valid age", Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                    } else {

                                    }

                                    if (editText.getText().toString().length() > 0)
                                    {
                                        try
                                        {
                                            Integer mInteger = Integer.parseInt(editText.getText().toString());
                                            if(min!=0 && max!=0)
                                            {
                                                if (mInteger <= max && mInteger >= min)
                                                {
                                                } else {
                                                    Toast.makeText(AddDynamicViewsActivity.this, getResources().getString(R.string.age_validation_msg), Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                            }

                                        } catch (Exception e) {
                                            Toast.makeText(AddDynamicViewsActivity.this, getResources().getString(R.string.age_validation_msg), Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    } else {

                                    }
                                    jsonObject.put(txtTitle.getText().toString(), editText.getText().toString());
                                    break;
                                }
                            }


                            break;
                        case "gender":

                            break;
                        case "address":
                            for (int c = 0; c < mvalidlist.size(); c++) {
                                if (mvalidlist.get(c).field_name.equalsIgnoreCase("address")) {
                                    if (mvalidlist.get(c).isRequired) {
                                        if (editText.getText().toString().length() == 0) {
                                            Toast.makeText(AddDynamicViewsActivity.this, getResources().getString(R.string.address_validation_msg), Toast.LENGTH_SHORT).show();
                                            return;
                                        } else {
                                            jsonObject.put(txtTitle.getText().toString(), editText.getText().toString());
                                        }
                                    } else {
                                        jsonObject.put(txtTitle.getText().toString(), editText.getText().toString());
                                    }
                                } else {

                                }
                            }
                            break;
                    }
                }
            }
            Constants.musersdata.put(jsonObject);
            Utils.showLog("TAG","Json array: "+Constants.musersdata);
            AddDynamicViewsActivity.this.onBackPressed();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
