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
  
简单的LoadingView加载框
  
//显示加载框

LoadingView.getInstance().showLoading("正在加载...");
  
//关闭加载框

LoadingView.getInstance().hideLoading();



# 11.7
1.增加LogUtils 打印Log工具
2.获取Phone信息工具（部分，未完成）

