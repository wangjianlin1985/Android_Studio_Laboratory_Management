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
	// �������ذ�ť
	private Button btnReturn;
	// ��������ϢId�ؼ�
	private TextView TV_weekId;
	// ��������Ϣ���ƿؼ�
	private TextView TV_weekName;
	/* Ҫ���������Ϣ��Ϣ */
	WeekInfo weekInfo = new WeekInfo(); 
	/* ����Ϣ����ҵ���߼��� */
	private WeekInfoService weekInfoService = new WeekInfoService();
	private int weekId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.weekinfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�鿴����Ϣ����");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// ͨ��findViewById����ʵ�������
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
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    weekInfo = weekInfoService.GetWeekInfo(weekId); 
		this.TV_weekId.setText(weekInfo.getWeekId() + "");
		this.TV_weekName.setText(weekInfo.getWeekName());
	} 
}
