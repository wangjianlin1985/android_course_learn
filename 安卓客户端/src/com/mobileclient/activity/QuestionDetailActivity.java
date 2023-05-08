package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Question;
import com.mobileclient.service.QuestionService;
import com.mobileclient.domain.Teacher;
import com.mobileclient.service.TeacherService;
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
public class QuestionDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_id;
	// 声明提问的老师控件
	private TextView TV_teacherId;
	// 声明提问者控件
	private TextView TV_questioner;
	// 声明提问内容控件
	private TextView TV_content;
	// 声明回复内容控件
	private TextView TV_reply;
	// 声明提问时间控件
	private TextView TV_addTime;
	/* 要保存的在线问答信息 */
	Question question = new Question(); 
	/* 在线问答管理业务逻辑层 */
	private QuestionService questionService = new QuestionService();
	private TeacherService teacherService = new TeacherService();
	private int id;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.question_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看在线问答详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_id = (TextView) findViewById(R.id.TV_id);
		TV_teacherId = (TextView) findViewById(R.id.TV_teacherId);
		TV_questioner = (TextView) findViewById(R.id.TV_questioner);
		TV_content = (TextView) findViewById(R.id.TV_content);
		TV_reply = (TextView) findViewById(R.id.TV_reply);
		TV_addTime = (TextView) findViewById(R.id.TV_addTime);
		Bundle extras = this.getIntent().getExtras();
		id = extras.getInt("id");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				QuestionDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    question = questionService.GetQuestion(id); 
		this.TV_id.setText(question.getId() + "");
		Teacher teacherId = teacherService.GetTeacher(question.getTeacherId());
		this.TV_teacherId.setText(teacherId.getName());
		this.TV_questioner.setText(question.getQuestioner());
		this.TV_content.setText(question.getContent());
		this.TV_reply.setText(question.getReply());
		this.TV_addTime.setText(question.getAddTime());
	} 
}
