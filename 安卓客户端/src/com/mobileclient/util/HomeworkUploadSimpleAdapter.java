package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.HomeworkTaskService;
import com.mobileclient.service.StudentService;
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

public class HomeworkUploadSimpleAdapter extends SimpleAdapter { 
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

    public HomeworkUploadSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.homeworkupload_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_homeworkTaskObj = (TextView)convertView.findViewById(R.id.tv_homeworkTaskObj);
	  holder.tv_studentObj = (TextView)convertView.findViewById(R.id.tv_studentObj);
	  holder.tv_uploadTime = (TextView)convertView.findViewById(R.id.tv_uploadTime);
	  holder.tv_pigaiTime = (TextView)convertView.findViewById(R.id.tv_pigaiTime);
	  holder.tv_pigaiFlag = (TextView)convertView.findViewById(R.id.tv_pigaiFlag);
	  holder.tv_pingyu = (TextView)convertView.findViewById(R.id.tv_pingyu);
	  /*设置各个控件的展示内容*/
	  holder.tv_homeworkTaskObj.setText("作业标题：" + (new HomeworkTaskService()).GetHomeworkTask(Integer.parseInt(mData.get(position).get("homeworkTaskObj").toString())).getTitle());
	  holder.tv_studentObj.setText("提交的学生：" + (new StudentService()).GetStudent(mData.get(position).get("studentObj").toString()).getName());
	  holder.tv_uploadTime.setText("提交时间：" + mData.get(position).get("uploadTime").toString());
	  holder.tv_pigaiTime.setText("批改时间：" + mData.get(position).get("pigaiTime").toString());
	  holder.tv_pigaiFlag.setText("是否批改：" + mData.get(position).get("pigaiFlag").toString());
	  holder.tv_pingyu.setText("评语：" + mData.get(position).get("pingyu").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_homeworkTaskObj;
    	TextView tv_studentObj;
    	TextView tv_uploadTime;
    	TextView tv_pigaiTime;
    	TextView tv_pigaiFlag;
    	TextView tv_pingyu;
    }
} 
