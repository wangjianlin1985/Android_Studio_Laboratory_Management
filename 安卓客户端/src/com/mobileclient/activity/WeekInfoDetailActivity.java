package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.WeekInfo;
import com.mobileclient.service.WeekInfoService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class WeekInfoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明周信息Id控件
	private TextView TV_weekId;
	// 声明周信息名称控件
	private TextView TV_weekName;
	/* 要保存的周信息信息 */
	WeekInfo weekInfo = new WeekInfo(); 
	/* 周信息管理业务逻辑层 */
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
		setContentView(R.layout.weekinfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看周信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_weekId = (TextView) findViewById(R.id.TV_weekId);
		TV_weekName = (TextView) findViewById(R.id.TV_weekName);
		Bundle extras = this.getIntent().getExtras();
		weekId = extras.getInt("weekId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				WeekInfoDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    weekInfo = weekInfoService.GetWeekInfo(weekId); 
		this.TV_weekId.setText(weekInfo.getWeekId() + "");
		this.TV_weekName.setText(weekInfo.getWeekName());
	} 
}
