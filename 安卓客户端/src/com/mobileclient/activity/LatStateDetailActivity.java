package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.LatState;
import com.mobileclient.service.LatStateService;
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
public class LatStateDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明状态id控件
	private TextView TV_stateId;
	// 声明状态名称控件
	private TextView TV_stateName;
	/* 要保存的实验室状态信息 */
	LatState latState = new LatState(); 
	/* 实验室状态管理业务逻辑层 */
	private LatStateService latStateService = new LatStateService();
	private String stateId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.latstate_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看实验室状态详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_stateId = (TextView) findViewById(R.id.TV_stateId);
		TV_stateName = (TextView) findViewById(R.id.TV_stateName);
		Bundle extras = this.getIntent().getExtras();
		stateId = extras.getString("stateId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				LatStateDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    latState = latStateService.GetLatState(stateId); 
		this.TV_stateId.setText(latState.getStateId());
		this.TV_stateName.setText(latState.getStateName());
	} 
}
