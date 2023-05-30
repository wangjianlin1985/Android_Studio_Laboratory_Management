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
	// 声明查询按钮
	private Button btnQuery;
	// 声明设备名称输入框
	private EditText ET_deviceName;
	// 声明所属实验室下拉框
	private Spinner spinner_labObj;
	private ArrayAdapter<String> labObj_adapter;
	private static  String[] labObj_ShowText  = null;
	private List<LabInfo> labInfoList = null; 
	/*实验室管理业务逻辑层*/
	private LabInfoService labInfoService = new LabInfoService();
	/*查询过滤条件保存到这个对象中*/
	private Device queryConditionDevice = new Device();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.device_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置实验设备查询条件");
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
		// 获取所有的实验室
		try {
			labInfoList = labInfoService.QueryLabInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int labInfoCount = labInfoList.size();
		labObj_ShowText = new String[labInfoCount+1];
		labObj_ShowText[0] = "不限制";
		for(int i=1;i<=labInfoCount;i++) { 
			labObj_ShowText[i] = labInfoList.get(i-1).getLabName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		labObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labObj_ShowText);
		// 设置所属实验室下拉列表的风格
		labObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_labObj.setAdapter(labObj_adapter);
		// 添加事件Spinner事件监听
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
		// 设置默认值
		spinner_labObj.setVisibility(View.VISIBLE);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionDevice.setDeviceName(ET_deviceName.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionDevice", queryConditionDevice);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
