package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.LatState;
import com.mobileclient.service.LatStateService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class LatStateAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// ����״̬id�����
	private EditText ET_stateId;
	// ����״̬���������
	private EditText ET_stateName;
	protected String carmera_path;
	/*Ҫ�����ʵ����״̬��Ϣ*/
	LatState latState = new LatState();
	/*ʵ����״̬����ҵ���߼���*/
	private LatStateService latStateService = new LatStateService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.latstate_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("���ʵ����״̬");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_stateId = (EditText) findViewById(R.id.ET_stateId);
		ET_stateName = (EditText) findViewById(R.id.ET_stateName);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*�������ʵ����״̬��ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ״̬id*/
					if(ET_stateId.getText().toString().equals("")) {
						Toast.makeText(LatStateAddActivity.this, "״̬id���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_stateId.setFocusable(true);
						ET_stateId.requestFocus();
						return;
					}
					latState.setStateId(ET_stateId.getText().toString());
					/*��֤��ȡ״̬����*/ 
					if(ET_stateName.getText().toString().equals("")) {
						Toast.makeText(LatStateAddActivity.this, "״̬�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_stateName.setFocusable(true);
						ET_stateName.requestFocus();
						return;	
					}
					latState.setStateName(ET_stateName.getText().toString());
					/*����ҵ���߼����ϴ�ʵ����״̬��Ϣ*/
					LatStateAddActivity.this.setTitle("�����ϴ�ʵ����״̬��Ϣ���Ե�...");
					String result = latStateService.AddLatState(latState);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
