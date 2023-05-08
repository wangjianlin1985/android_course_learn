package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.HomeworkUpload;
import com.mobileclient.domain.HomeworkTask;
import com.mobileclient.service.HomeworkTaskService;
import com.mobileclient.domain.Student;
import com.mobileclient.service.StudentService;

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
public class HomeworkUploadQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明作业标题下拉框
	private Spinner spinner_homeworkTaskObj;
	private ArrayAdapter<String> homeworkTaskObj_adapter;
	private static  String[] homeworkTaskObj_ShowText  = null;
	private List<HomeworkTask> homeworkTaskList = null; 
	/*作业任务管理业务逻辑层*/
	private HomeworkTaskService homeworkTaskService = new HomeworkTaskService();
	// 声明提交的学生下拉框
	private Spinner spinner_studentObj;
	private ArrayAdapter<String> studentObj_adapter;
	private static  String[] studentObj_ShowText  = null;
	private List<Student> studentList = null; 
	/*学生信息管理业务逻辑层*/
	private StudentService studentService = new StudentService();
	/*查询过滤条件保存到这个对象中*/
	private HomeworkUpload queryConditionHomeworkUpload = new HomeworkUpload();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.homeworkupload_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置上传的作业查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		spinner_homeworkTaskObj = (Spinner) findViewById(R.id.Spinner_homeworkTaskObj);
		// 获取所有的作业任务
		try {
			homeworkTaskList = homeworkTaskService.QueryHomeworkTask(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int homeworkTaskCount = homeworkTaskList.size();
		homeworkTaskObj_ShowText = new String[homeworkTaskCount+1];
		homeworkTaskObj_ShowText[0] = "不限制";
		for(int i=1;i<=homeworkTaskCount;i++) { 
			homeworkTaskObj_ShowText[i] = homeworkTaskList.get(i-1).getTitle();
		} 
		// 将可选内容与ArrayAdapter连接起来
		homeworkTaskObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, homeworkTaskObj_ShowText);
		// 设置作业标题下拉列表的风格
		homeworkTaskObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_homeworkTaskObj.setAdapter(homeworkTaskObj_adapter);
		// 添加事件Spinner事件监听
		spinner_homeworkTaskObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionHomeworkUpload.setHomeworkTaskObj(homeworkTaskList.get(arg2-1).getHomeworkId()); 
				else
					queryConditionHomeworkUpload.setHomeworkTaskObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_homeworkTaskObj.setVisibility(View.VISIBLE);
		spinner_studentObj = (Spinner) findViewById(R.id.Spinner_studentObj);
		// 获取所有的学生信息
		try {
			studentList = studentService.QueryStudent(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int studentCount = studentList.size();
		studentObj_ShowText = new String[studentCount+1];
		studentObj_ShowText[0] = "不限制";
		for(int i=1;i<=studentCount;i++) { 
			studentObj_ShowText[i] = studentList.get(i-1).getName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		studentObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, studentObj_ShowText);
		// 设置提交的学生下拉列表的风格
		studentObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_studentObj.setAdapter(studentObj_adapter);
		// 添加事件Spinner事件监听
		spinner_studentObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionHomeworkUpload.setStudentObj(studentList.get(arg2-1).getStudentNumber()); 
				else
					queryConditionHomeworkUpload.setStudentObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_studentObj.setVisibility(View.VISIBLE);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionHomeworkUpload", queryConditionHomeworkUpload);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
