package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Question;
import com.mobileclient.service.QuestionService;
import com.mobileclient.domain.Teacher;
import com.mobileclient.service.TeacherService;
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

public class QuestionEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明记录编号TextView
	private TextView TV_id;
	// 声明提问的老师下拉框
	private Spinner spinner_teacherId;
	private ArrayAdapter<String> teacherId_adapter;
	private static  String[] teacherId_ShowText  = null;
	private List<Teacher> teacherList = null;
	/*提问的老师管理业务逻辑层*/
	private TeacherService teacherService = new TeacherService();
	// 声明提问者输入框
	private EditText ET_questioner;
	// 声明提问内容输入框
	private EditText ET_content;
	// 声明回复内容输入框
	private EditText ET_reply;
	// 声明提问时间输入框
	private EditText ET_addTime;
	protected String carmera_path;
	/*要保存的在线问答信息*/
	Question question = new Question();
	/*在线问答管理业务逻辑层*/
	private QuestionService questionService = new QuestionService();

	private int id;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.question_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑在线问答信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_id = (TextView) findViewById(R.id.TV_id);
		spinner_teacherId = (Spinner) findViewById(R.id.Spinner_teacherId);
		// 获取所有的提问的老师
		try {
			teacherList = teacherService.QueryTeacher(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int teacherCount = teacherList.size();
		teacherId_ShowText = new String[teacherCount];
		for(int i=0;i<teacherCount;i++) { 
			teacherId_ShowText[i] = teacherList.get(i).getName();
		}
		// 将可选内容与ArrayAdapter连接起来
		teacherId_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, teacherId_ShowText);
		// 设置图书类别下拉列表的风格
		teacherId_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_teacherId.setAdapter(teacherId_adapter);
		// 添加事件Spinner事件监听
		spinner_teacherId.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				question.setTeacherId(teacherList.get(arg2).getId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_teacherId.setVisibility(View.VISIBLE);
		ET_questioner = (EditText) findViewById(R.id.ET_questioner);
		ET_content = (EditText) findViewById(R.id.ET_content);
		ET_reply = (EditText) findViewById(R.id.ET_reply);
		ET_addTime = (EditText) findViewById(R.id.ET_addTime);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		id = extras.getInt("id");
		/*单击修改在线问答按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取提问者*/ 
					if(ET_questioner.getText().toString().equals("")) {
						Toast.makeText(QuestionEditActivity.this, "提问者输入不能为空!", Toast.LENGTH_LONG).show();
						ET_questioner.setFocusable(true);
						ET_questioner.requestFocus();
						return;	
					}
					question.setQuestioner(ET_questioner.getText().toString());
					/*验证获取提问内容*/ 
					if(ET_content.getText().toString().equals("")) {
						Toast.makeText(QuestionEditActivity.this, "提问内容输入不能为空!", Toast.LENGTH_LONG).show();
						ET_content.setFocusable(true);
						ET_content.requestFocus();
						return;	
					}
					question.setContent(ET_content.getText().toString());
					/*验证获取回复内容*/ 
					if(ET_reply.getText().toString().equals("")) {
						Toast.makeText(QuestionEditActivity.this, "回复内容输入不能为空!", Toast.LENGTH_LONG).show();
						ET_reply.setFocusable(true);
						ET_reply.requestFocus();
						return;	
					}
					question.setReply(ET_reply.getText().toString());
					/*验证获取提问时间*/ 
					if(ET_addTime.getText().toString().equals("")) {
						Toast.makeText(QuestionEditActivity.this, "提问时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_addTime.setFocusable(true);
						ET_addTime.requestFocus();
						return;	
					}
					question.setAddTime(ET_addTime.getText().toString());
					/*调用业务逻辑层上传在线问答信息*/
					QuestionEditActivity.this.setTitle("正在更新在线问答信息，稍等...");
					String result = questionService.UpdateQuestion(question);
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
	    question = questionService.GetQuestion(id);
		this.TV_id.setText(id+"");
		for (int i = 0; i < teacherList.size(); i++) {
			if (question.getTeacherId() == teacherList.get(i).getId()) {
				this.spinner_teacherId.setSelection(i);
				break;
			}
		}
		this.ET_questioner.setText(question.getQuestioner());
		this.ET_content.setText(question.getContent());
		this.ET_reply.setText(question.getReply());
		this.ET_addTime.setText(question.getAddTime());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
