## SuperSpinnerView（超级下拉框）
[![](https://jitpack.io/v/lifugan96/SuperSpinnerView.svg)](https://jitpack.io/#lifugan96/SuperSpinnerView)

#### 引用

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
}

dependencies {
	        implementation 'com.github.lifugan96:SuperSpinnerView:Tag'
}
```

#### 属性
| 属性      | 注释   |
| :---------------- | -------------- |
| spBackground      | 设置背景颜色   |
| spHeight          | 设置高度       |
| spWidth           | 设置宽度       |
| imgBackground     | 设置图形形状   |
| spTextSize        | 设置文字大小   |
| spTextColor       | 设置文件颜色   |
| defaultText       | 设置默认值     |
| spMargin          | 设置左右边距   |
| spTextMarginRight | 设置文本右边距 |
| spTextLayoutRight | 设置文本左边距 |
|                   |                |
| popHeight         | 设置弹窗高度   |



#### 使用教程

```xml
 <com.glf.utilslibrary.view.SuperSpinnerView
        android:id="@+id/custom_spinner"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:layout_margin="10dp"
        android:background="@drawable/gray_ll_border2"
        android:gravity="center"
        app:defaultText="默认值"
        app:spHeight="0dp"
        app:spMargin="10dp"
        app:spTextColor="@color/item_list_gray"
        app:spTextLayoutRight="true"
        app:spTextSize="0dp"
        app:spWidth="0dp" />
```

