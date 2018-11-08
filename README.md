# UtilsLib

allprojects {

	repositories {
		...
		maven { url 'https://jitpack.io' }

	}
}


dependencies {

	implementation 'com.github.cenlinfeng:UtilsLib:0.101'

}  

###1 加载框
  
简单的LoadingView加载框
  
//显示加载框

LoadingView.getInstance().showLoading("正在加载...");
  
//关闭加载框

LoadingView.getInstance().hideLoading();

###2 流式布局

增加流式布局，属性只有flow_gravity (left,center,right)

###3 Toast工具类

增加Toast工具类

ToastUtils.showToast(context, "测试吐司");

或者

ToastUtils.showToast(this, "测试吐司", Toast.LENGTH_LONG);

