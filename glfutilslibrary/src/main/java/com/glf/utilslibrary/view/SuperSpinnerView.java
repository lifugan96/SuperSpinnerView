package com.glf.utilslibrary.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.glf.utilslibrary.R;
import com.glf.utilslibrary.adapter.SuperSpinnerAdapter;


/**
 * 自定义下拉框 修改样式效果
 * 自定义属性
 * 边框：spinnerArgs_spTextSize
 * 边框：spinnerArgs_spTextColor
 * 边框：spinnerArgs_spBackground
 * 高度：spinnerArgs_spHeight
 * 宽度：spinnerArgs_spWidth
 * 图片样式:spinnerArgs_imgBackground
 * <p>
 * pop属性
 * 宽度：spinnerArgs_popHeight
 * 高度：spinnerArgs_popWidth
 * 左边距：spinnerArgs_popMarginLeft
 * 右边距：spinnerArgs_popMarginRight
 */
public class SuperSpinnerView extends LinearLayout {

    private Context context;

    private TextView tv_title;
    private ImageView iv_san;
    private RelativeLayout rl_content;
    private RecyclerView popRvView;
    private PopupWindow popupWindow;

    private int spBackground;
    private float spHeight;
    private float spWidth;
    private int imgBackground;
    private int popHeight;
    private LayoutParams rlContentLayoutParams;
    private int layout_margin;

    public SuperSpinnerView(Context context) {
        this(context, null);
    }

    public SuperSpinnerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuperSpinnerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.custom_spinner_view, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.spinnerArgs);

        //text属性
        spBackground = typedArray.getInteger(R.styleable.spinnerArgs_spBackground, 0);
        spHeight = typedArray.getDimension(R.styleable.spinnerArgs_spHeight, 0);
        spWidth = typedArray.getDimension(R.styleable.spinnerArgs_spWidth, 0);
        imgBackground = typedArray.getInteger(R.styleable.spinnerArgs_imgBackground, 0);
        int spTextSize = (int) typedArray.getDimension(R.styleable.spinnerArgs_spTextSize, 0);
        int spTextColor = typedArray.getColor(R.styleable.spinnerArgs_spTextColor, 0);
        String defaultText = typedArray.getString(R.styleable.spinnerArgs_defaultText);
        layout_margin = (int) typedArray.getDimension(R.styleable.spinnerArgs_spMargin, 0);

        int spTextMarginRight = (int) typedArray.getDimension(R.styleable.spinnerArgs_spTextMarginRight, 0);
        boolean spTextLayoutRight = typedArray.getBoolean(R.styleable.spinnerArgs_spTextLayoutRight, false);

        //弹窗属性
        popHeight = typedArray.getInteger(R.styleable.spinnerArgs_popHeight, 0);

        // 获取控件
        rl_content = (RelativeLayout) view.findViewById(R.id.rl_content);
        rl_content.setVisibility(VISIBLE);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_san = (ImageView) view.findViewById(R.id.iv_san);

        tv_title.setText(defaultText + "");

        if (0 != spTextMarginRight) {
            LayoutParams titleLayoutParams = (LayoutParams) tv_title.getLayoutParams();
            titleLayoutParams.rightMargin = spTextMarginRight;
            tv_title.setLayoutParams(titleLayoutParams);
        }

        if (spTextLayoutRight) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            tv_title.setLayoutParams(lp);
        }


        if (0 != spTextSize)
            tv_title.getPaint().setTextSize(spTextSize);
        if (0 != spTextColor)
            tv_title.setTextColor(spTextColor);

        rlContentLayoutParams = (LayoutParams) rl_content.getLayoutParams();
        rlContentLayoutParams.width = (int) (0 != spWidth ? spWidth : LayoutParams.MATCH_PARENT);
        rlContentLayoutParams.height = (int) (0 != spHeight ? spHeight : LayoutParams.WRAP_CONTENT);
        rlContentLayoutParams.rightMargin = layout_margin;
        rlContentLayoutParams.leftMargin = layout_margin;
//        rlContentLayoutParams.topMargin = layout_margin;
//        rlContentLayoutParams.bottomMargin = layout_margin;

        rl_content.setLayoutParams(rlContentLayoutParams);

        if (0 != spBackground) {
            rl_content.setBackgroundResource(spBackground);
        }

        if (0 != imgBackground) {
            iv_san.setBackgroundResource(imgBackground);
        }

        registerOnclick();
    }

    /**
     * 注册点击事件
     */
    private void registerOnclick() {
        rl_content.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isDowShow) {
                    isDowShow = false;
                    iv_san.setBackgroundResource(R.drawable.go_down);
                    if (null != popupWindow) {
                        popupWindow.showAsDropDown(SuperSpinnerView.this);//设置popupWindow显示,并且告诉它显示在那个View下面
                    }
                } else {
                    isDowShow = true;
                    iv_san.setBackgroundResource(R.drawable.go_next);

                    if (null != popupWindow) {
                        popupWindow.dismiss();
                    }
                }
            }
        });
    }

    /**
     *
     */
    private void createPop() {
        View popView = LayoutInflater.from(context).inflate(R.layout.custom_spinner_view, null, false);
        RelativeLayout titleView = (RelativeLayout) popView.findViewById(R.id.rl_content);
        titleView.setVisibility(GONE);
        popRvView = (RecyclerView) popView.findViewById(R.id.rv_spinner_list);
//        LinearLayout.LayoutParams popViewLayoutParams = (LinearLayout.LayoutParams) popView.getLayoutParams();
//        popViewLayoutParams.leftMargin = layout_margin;
//        popViewLayoutParams.rightMargin = layout_margin;

        //设置宽高  宽度用的父控件宽度 高度自己设置或者包裹内容
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) popRvView.getLayoutParams();
        layoutParams.width = rlContentLayoutParams.width;
        layoutParams.height = (int) (0 != popHeight ? popHeight : LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = layout_margin;
        layoutParams.rightMargin = layout_margin;

        popRvView.setLayoutParams(layoutParams);
        popRvView.setVisibility(VISIBLE);

        LinearLayoutManager layoutManage = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        popRvView.setLayoutManager(layoutManage);
        popRvView.setAdapter(mSpinnerAdapter);
        mSpinnerAdapter.notifyDataSetChanged();

        mSpinnerAdapter.setOnItemClickListener(new SuperSpinnerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                tv_title.setText(mSpinnerAdapter.getItemValue(position));
                if (null != mDataCallback) {
                    mDataCallback.result(position);
                }
                popupWindow.dismiss();
                isDowShow = true;
                iv_san.setBackgroundResource(R.drawable.go_next);
            }
        });

        //参数为1.View 2.宽度 3.高度
        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setOutsideTouchable(true);//设置点击外部区域可以取消popupWindow
        popupWindow.setFocusable(false);

    }

    private boolean isDowShow = true;
    private SuperSpinnerAdapter mSpinnerAdapter;
    private DataCallback mDataCallback;

    /**
     * 设置下拉框适配器
     */
    public void setSpinnerAdapter(SuperSpinnerAdapter spinnerAdapter, DataCallback dataCallback) {
        this.mSpinnerAdapter = spinnerAdapter;
        this.mDataCallback = dataCallback;
        createPop();
    }


    public interface DataCallback {
        void result(int postion);
    }

    /**
     * 是否开启只读模式
     *
     * @param readOnly
     */
    public void readOnlyModel(boolean readOnly) {
        if (readOnly) {
            tv_title.setOnClickListener(null);
            if (null != iv_san) {
                iv_san.setVisibility(GONE);
            }
        } else {
            registerOnclick();
        }

    }

    /**
     * 默认回显显示值
     *
     * @param title 显示值
     * @param tag   实际值
     */
    public void setDefalutValue(String title, String tag) {
        if (null != tv_title) {
            tv_title.setText(title);
            tv_title.setTag(tag);
        }
    }
}
