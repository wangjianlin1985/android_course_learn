package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.CourseInfo;
import com.mobileclient.service.CourseInfoService;
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

public class CourseInfoEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明记录编号TextView
	private TextView TV_id;
	// 声明课程简介输入框
	private EditText ET_jianjie;
	// 声明课程大纲输入框
	private EditText ET_dagan;
	protected String carmera_path;
	/*要保存的课程信息信息*/
	CourseInfo courseInfo = new CourseInfo();
	/*课程信息管理业务逻辑层*/
	private CourseInfoService courseInfoService = new CourseInfoService();

	private int id;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.courseinfo_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑课程信息信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_id = (TextView) findViewById(R.id.TV_id);
		ET_jianjie = (EditText) findViewById(R.id.ET_jianjie);
		ET_dagan = (EditText) findViewById(R.id.ET_dagan);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		id = extras.getInt("id");
		/*单击修改课程信息按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取课程简介*/ 
					if(ET_jianjie.getText().toString().equals("")) {
						Toast.makeText(CourseInfoEditActivity.this, "课程简介输入不能为空!", Toast.LENGTH_LONG).show();
						ET_jianjie.setFocusable(true);
						ET_jianjie.requestFocus();
						return;	
					}
					courseInfo.setJianjie(ET_jianjie.getText().toString());
					/*验证获取课程大纲*/ 
					if(ET_dagan.getText().toString().equals("")) {
						Toast.makeText(CourseInfoEditActivity.this, "课程大纲输入不能为空!", Toast.LENGTH_LONG).show();
						ET_dagan.setFocusable(true);
						ET_dagan.requestFocus();
						return;	
					}
					courseInfo.setDagan(ET_dagan.getText().toString());
					/*调用业务逻辑层上传课程信息信息*/
					CourseInfoEditActivity.this.setTitle("正在更新课程信息信息，稍等...");
					String result = courseInfoService.UpdateCourseInfo(courseInfo);
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
	    courseInfo = courseInfoService.GetCourseInfo(id);
		this.TV_id.setText(id+"");
		this.ET_jianjie.setText(courseInfo.getJianjie());
		this.ET_dagan.setText(courseInfo.getDagan());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
