package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.TeacherService;
import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class QuestionSimpleAdapter extends SimpleAdapter { 
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
    private SyncImageLoader syncImageLoader;

    public QuestionSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.question_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_id = (TextView)convertView.findViewById(R.id.tv_id);
	  holder.tv_teacherId = (TextView)convertView.findViewById(R.id.tv_teacherId);
	  holder.tv_questioner = (TextView)convertView.findViewById(R.id.tv_questioner);
	  holder.tv_content = (TextView)convertView.findViewById(R.id.tv_content);
	  holder.tv_addTime = (TextView)convertView.findViewById(R.id.tv_addTime);
	  /*设置各个控件的展示内容*/
	  holder.tv_id.setText("记录编号：" + mData.get(position).get("id").toString());
	  holder.tv_teacherId.setText("提问的老师：" + (new TeacherService()).GetTeacher(Integer.parseInt(mData.get(position).get("teacherId").toString())).getName());
	  holder.tv_questioner.setText("提问者：" + mData.get(position).get("questioner").toString());
	  holder.tv_content.setText("提问内容：" + mData.get(position).get("content").toString());
	  holder.tv_addTime.setText("提问时间：" + mData.get(position).get("addTime").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_id;
    	TextView tv_teacherId;
    	TextView tv_questioner;
    	TextView tv_content;
    	TextView tv_addTime;
    }
} 
