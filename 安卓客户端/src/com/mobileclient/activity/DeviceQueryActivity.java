package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Device;
import com.mobileclient.domain.LabInfo;
import com.mobileclient.service.LabInfoService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class DeviceQueryActivity extends Activity {
	// ������ѯ��ť
	private Button btnQuery;
	// �����豸���������
	private EditText ET_deviceName;
	// ��������ʵ����������
	private Spinner spinner_labObj;
	private ArrayAdapter<String> labObj_adapter;
	private static  String[] labObj_ShowText  = null;
	private List<LabInfo> labInfoList = null; 
	/*ʵ���ҹ���ҵ���߼���*/
	private LabInfoService labInfoService = new LabInfoService();
	/*��ѯ�����������浽���������*/
	private Device queryConditionDevice = new Device();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.device_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("����ʵ���豸��ѯ����");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_deviceName = (EditText) findViewById(R.id.ET_deviceName);
		spinner_labObj = (Spinner) findViewById(R.id.Spinner_labObj);
		// ��ȡ���е�ʵ����
		try {
			labInfoList = labInfoService.QueryLabInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int labInfoCount = labInfoList.size();
		labObj_ShowText = new String[labInfoCount+1];
		labObj_ShowText[0] = "������";
		for(int i=1;i<=labInfoCount;i++) { 
			labObj_ShowText[i] = labInfoList.get(i-1).getLabName();
		} 
		// ����ѡ������ArrayAdapter��������
		labObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labObj_ShowText);
		// ��������ʵ���������б�ķ��
		labObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_labObj.setAdapter(labObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_labObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionDevice.setLabObj(labInfoList.get(arg2-1).getLabId()); 
				else
					queryConditionDevice.setLabObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_labObj.setVisibility(View.VISIBLE);
		/*������ѯ��ť*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��ȡ��ѯ����*/
					queryConditionDevice.setDeviceName(ET_deviceName.getText().toString());
					Intent intent = getIntent();
					//����ʹ��bundle��������������
					Bundle bundle =new Bundle();
					//�����������Ȼ�Ǽ�ֵ�Ե���ʽ
					bundle.putSerializable("queryConditionDevice", queryConditionDevice);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
