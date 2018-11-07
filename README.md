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
