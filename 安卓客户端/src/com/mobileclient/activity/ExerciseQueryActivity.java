package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Exercise;
import com.mobileclient.domain.Chapter;
import com.mobileclient.service.ChapterService;

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
public class ExerciseQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明习题名称输入框
	private EditText ET_title;
	// 声明所在章下拉框
	private Spinner spinner_chapterId;
	private ArrayAdapter<String> chapterId_adapter;
	private static  String[] chapterId_ShowText  = null;
	private List<Chapter> chapterList = null; 
	/*章信息管理业务逻辑层*/
	private ChapterService chapterService = new ChapterService();
	/*查询过滤条件保存到这个对象中*/
	private Exercise queryConditionExercise = new Exercise();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.exercise_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置习题信息查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_title = (EditText) findViewById(R.id.ET_title);
		spinner_chapterId = (Spinner) findViewById(R.id.Spinner_chapterId);
		// 获取所有的章信息
		try {
			chapterList = chapterService.QueryChapter(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int chapterCount = chapterList.size();
		chapterId_ShowText = new String[chapterCount+1];
		chapterId_ShowText[0] = "不限制";
		for(int i=1;i<=chapterCount;i++) { 
			chapterId_ShowText[i] = chapterList.get(i-1).getTitle();
		} 
		// 将可选内容与ArrayAdapter连接起来
		chapterId_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, chapterId_ShowText);
		// 设置所在章下拉列表的风格
		chapterId_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_chapterId.setAdapter(chapterId_adapter);
		// 添加事件Spinner事件监听
		spinner_chapterId.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionExercise.setChapterId(chapterList.get(arg2-1).getId()); 
				else
					queryConditionExercise.setChapterId(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_chapterId.setVisibility(View.VISIBLE);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionExercise.setTitle(ET_title.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionExercise", queryConditionExercise);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
