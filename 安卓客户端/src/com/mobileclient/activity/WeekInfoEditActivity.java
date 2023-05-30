package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.WeekInfo;
import com.mobileclient.service.WeekInfoService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class WeekInfoEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明周信息IdTextView
	private TextView TV_weekId;
	// 声明周信息名称输入框
	private EditText ET_weekName;
	protected String carmera_path;
	/*要保存的周信息信息*/
	WeekInfo weekInfo = new WeekInfo();
	/*周信息管理业务逻辑层*/
	private WeekInfoService weekInfoService = new WeekInfoService();

	private int weekId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.weekinfo_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑周信息信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_weekId = (TextView) findViewById(R.id.TV_weekId);
		ET_weekName = (EditText) findViewById(R.id.ET_weekName);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		weekId = extras.getInt("weekId");
		/*单击修改周信息按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取周信息名称*/ 
					if(ET_weekName.getText().toString().equals("")) {
						Toast.makeText(WeekInfoEditActivity.this, "周信息名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_weekName.setFocusable(true);
						ET_weekName.requestFocus();
						return;	
					}
					weekInfo.setWeekName(ET_weekName.getText().toString());
					/*调用业务逻辑层上传周信息信息*/
					WeekInfoEditActivity.this.setTitle("正在更新周信息信息，稍等...");
					String result = weekInfoService.UpdateWeekInfo(weekInfo);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    weekInfo = weekInfoService.GetWeekInfo(weekId);
		this.TV_weekId.setText(weekId+"");
		this.ET_weekName.setText(weekInfo.getWeekName());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
